package net.grishmagolla.myJournal.scheduler;

import net.grishmagolla.myJournal.cache.AppCache;
import net.grishmagolla.myJournal.entity.JournalEntry;
import net.grishmagolla.myJournal.entity.User;
import net.grishmagolla.myJournal.repository.UserEntryRepository;
import net.grishmagolla.myJournal.service.EmailService;
import net.grishmagolla.myJournal.service.SentimentAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserScheduler {

    private final EmailService emailService;
    private final UserEntryRepository userEntryRepository;
    private final SentimentAnalysisService sentimentAnalysisService;

    public UserScheduler(EmailService emailService,
                         UserEntryRepository userEntryRepository,
                         SentimentAnalysisService sentimentAnalysisService) {
        this.emailService = emailService;
        this.userEntryRepository = userEntryRepository;
        this.sentimentAnalysisService = sentimentAnalysisService;
    }

    @Autowired
    private AppCache appCache;

    @Scheduled(cron = "0 0 9 * * SUN")
    public void fetchUserAndSendSaMail() {

        List<User> users = userEntryRepository.findAll();

        for (User user : users) {

            List<JournalEntry> journalEntries = user.getJournalEntries();

            // last 7 days filter
            List<String> filteredContents = journalEntries.stream()
                    .filter(entry -> entry.getDate()
                            .isAfter(LocalDateTime.now().minus(7, ChronoUnit.DAYS)))
                    .map(JournalEntry::getContent)
                    .collect(Collectors.toList());

            if (filteredContents.isEmpty()) continue;

            String combinedText = String.join(" ", filteredContents);

            String sentiment = sentimentAnalysisService.getSentiment(combinedText);

            emailService.sendEmail(
                    user.getUserEmail(),
                    "Sentiment for the last 7 days",
                    sentiment
            );
        }
    }

    @Scheduled(cron = "0 0 9 * * SUN")
    public void clearAppCache(){
        appCache.init();
    }
}