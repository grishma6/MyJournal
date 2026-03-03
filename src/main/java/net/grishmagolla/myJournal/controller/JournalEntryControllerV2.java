package net.grishmagolla.myJournal.controller;

import net.grishmagolla.myJournal.entity.JournalEntry;
import net.grishmagolla.myJournal.service.JournalEntryService;
import org.bson.types.ObjectId;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/journal")
public class JournalEntryControllerV2 {

    private final JournalEntryService journalEntryService;

    public JournalEntryControllerV2(JournalEntryService journalEntryService) {
        this.journalEntryService = journalEntryService;
    }

    @GetMapping
    public List<JournalEntry> getAll() {
        return journalEntryService.getAll();
    }

    @PostMapping
    public JournalEntry createEntry(@RequestBody JournalEntry journalEntry) {
        journalEntry.setDate(LocalDateTime.now());
        journalEntryService.saveEntry(journalEntry);
        return journalEntry;
    }

    @GetMapping("id/{myId}")
    public JournalEntry getJournalEntryById(@PathVariable String myId) {
        return journalEntryService.findById(new ObjectId(myId)).orElse(null);
    }

    @DeleteMapping("id/{myId}")
    public boolean deleteJournalEntryById(@PathVariable String myId) {
        journalEntryService.deleteById(new ObjectId(myId));
        return true;
    }

    @PutMapping("id/{id}")
    public JournalEntry updateJournalEntryById(@PathVariable String id,
                                               @RequestBody JournalEntry newEntry) {

        JournalEntry old = journalEntryService.findById(new ObjectId(id)).orElse(null);
        if (old == null) {
            return null;
        }

        if (newEntry.getTitle() != null && !newEntry.getTitle().isBlank()) {
            old.setTitle(newEntry.getTitle());
        }

        if (newEntry.getContent() != null && !newEntry.getContent().isBlank()) {
            old.setContent(newEntry.getContent());
        }

        // optional: update date on edit
        old.setDate(LocalDateTime.now());

        journalEntryService.saveEntry(old);
        return old;
    }
}