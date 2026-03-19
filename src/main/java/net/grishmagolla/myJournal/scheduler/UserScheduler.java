package net.grishmagolla.myJournal.scheduler;

import net.grishmagolla.myJournal.cache.AppCache;
import net.grishmagolla.myJournal.entity.JournalEntry;
import net.grishmagolla.myJournal.entity.User;
import net.grishmagolla.myJournal.enums.Sentiment;
import net.grishmagolla.myJournal.repository.UserEntryRepository;
import net.grishmagolla.myJournal.service.EmailService;
import net.grishmagolla.myJournal.service.SentimentAnalysisService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class UserScheduler {

    private final EmailService emailService;
    private final UserEntryRepository userEntryRepository;
    private final AppCache appCache;
    private final SentimentAnalysisService sentimentAnalysisService;

    public UserScheduler(EmailService emailService,
                         UserEntryRepository userEntryRepository,
                         AppCache appCache,
                         SentimentAnalysisService sentimentAnalysisService) {
        this.emailService = emailService;
        this.userEntryRepository = userEntryRepository;
        this.appCache = appCache;
        this.sentimentAnalysisService = sentimentAnalysisService;
    }

    @Scheduled(cron = "0 0 9 * * SUN")
    public void fetchUserAndSendSaMail() {

        List<User> users = userEntryRepository.findAll();

        for (User user : users) {

            if (user == null || user.getUserEmail() == null || user.getUserEmail().isBlank()) {
                continue;
            }

            List<JournalEntry> journalEntries = user.getJournalEntries();

            if (journalEntries == null || journalEntries.isEmpty()) {
                continue;
            }

            List<Sentiment> sentiments = journalEntries.stream()
                    .filter(entry -> entry.getDate() != null &&
                            entry.getDate().isAfter(LocalDateTime.now().minusDays(7)))
                    .map(JournalEntry::getContent)
                    .map(sentimentAnalysisService::getSentiment)
                    .collect(Collectors.toList());

            if (sentiments.isEmpty()) {
                continue;
            }

            Map<Sentiment, Integer> sentimentCounts = new HashMap<>();

            for (Sentiment sentiment : sentiments) {
                if (sentiment != null) {
                    sentimentCounts.put(sentiment, sentimentCounts.getOrDefault(sentiment, 0) + 1);
                }
            }

            Sentiment mostFrequentSentiment = null;
            int maxCount = 0;

            for (Map.Entry<Sentiment, Integer> entry : sentimentCounts.entrySet()) {
                if (entry.getValue() > maxCount) {
                    maxCount = entry.getValue();
                    mostFrequentSentiment = entry.getKey();
                }
            }

            if (mostFrequentSentiment != null) {
                emailService.sendEmail(
                        user.getUserEmail(),
                        "Sentiment for last 7 days",
                        "Your most frequent sentiment in the last 7 days is: " + mostFrequentSentiment
                );
            }
        }
    }

    @Scheduled(cron = "0 5 9 * * SUN")
    public void clearAppCache() {
        appCache.init();
    }
}