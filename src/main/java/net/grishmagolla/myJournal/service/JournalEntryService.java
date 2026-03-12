package net.grishmagolla.myJournal.service;

import net.grishmagolla.myJournal.entity.JournalEntry;
import net.grishmagolla.myJournal.entity.User;
import net.grishmagolla.myJournal.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class JournalEntryService {

    private final JournalEntryRepository journalEntryRepository;
    private final UserEntryService userEntryService;

    public JournalEntryService(JournalEntryRepository journalEntryRepository,
                               UserEntryService userEntryService) {
        this.journalEntryRepository = journalEntryRepository;
        this.userEntryService = userEntryService;
    }

    @Transactional
    // Create journal entry and attach it to a user
    public void saveEntry(JournalEntry journalEntry, String userName) {
        try {
            User user = userEntryService.findByUserName(userName);

            if (user == null) {
                throw new RuntimeException("User not found with username: " + userName);
            }

            //journalEntry.setDate(LocalDateTime.now());
            JournalEntry savedEntry = journalEntryRepository.save(journalEntry);

            user.getJournalEntryList().add(savedEntry);
            user.setUserName(null);
            userEntryService.saveEntry(user);
        } catch(Exception e) {
            System.out.println(e);
            throw new RuntimeException("An error occured while saving the entry", e);
        }
    }
    // Update existing journal entry only
    public void saveEntry(JournalEntry journalEntry) {
        journalEntryRepository.save(journalEntry);
    }

    public List<JournalEntry> getAll() {
        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntry> findById(ObjectId id) {
        return journalEntryRepository.findById(id);
    }

    public void deleteById(ObjectId id, String userName) {

        User user = userEntryService.findByUserName(userName);

        if (user == null) {
            throw new RuntimeException("User not found");
        }

        user.getJournalEntryList().removeIf(entry -> entry.getId().equals(id));
        userEntryService.saveEntry(user);

        journalEntryRepository.deleteById(id);
    }
}