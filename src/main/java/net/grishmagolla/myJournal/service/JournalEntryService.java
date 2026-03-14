package net.grishmagolla.myJournal.service;

import net.grishmagolla.myJournal.entity.JournalEntry;
import net.grishmagolla.myJournal.entity.User;
import net.grishmagolla.myJournal.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
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
    public void saveEntry(JournalEntry journalEntry, String userName) {
        try {
            User user = userEntryService.findByUserName(userName);
            if (user == null) {
                throw new RuntimeException("User not found with username: " + userName);
            }
            JournalEntry savedEntry = journalEntryRepository.save(journalEntry);
            user.getJournalEntries().add(savedEntry);
            userEntryService.saveEntry(user);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("An error occurred while saving the entry", e);
        }
    }

    // for updating an existing entry only
    public void saveEntry(JournalEntry journalEntry) {
        journalEntryRepository.save(journalEntry);
    }

    public List<JournalEntry> getAll() {
        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntry> findById(ObjectId id) {
        return journalEntryRepository.findById(id);
    }

    @Transactional
    public boolean deleteById(ObjectId id, String userName) {
        boolean removed = false;
        try {
            User user = userEntryService.findByUserName(userName);
            removed = user.getJournalEntries().removeIf(x -> x.getId().equals(id));
            if (removed) {
                userEntryService.saveEntry(user);
                journalEntryRepository.deleteById(id);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("An error occurred while deleting the entry");
        }
        return removed;
    }

    public List<JournalEntry> findByUserName(String userName) {
        User user = userEntryService.findByUserName(userName);
        return user != null ? user.getJournalEntries() : Collections.emptyList();
    }
}