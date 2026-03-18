package net.grishmagolla.myJournal.entity;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "/users")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    public ObjectId id;
    @NonNull
    public String userName;
    @NonNull
    public String userPassword;
    private String userEmail;
    private boolean sentimentAnalysis;
    @DBRef
    private List<JournalEntry> journalEntryList = new ArrayList<>();
    private List<String> roles;

    public List<JournalEntry> getJournalEntries() {
        return journalEntryList;
    }
}