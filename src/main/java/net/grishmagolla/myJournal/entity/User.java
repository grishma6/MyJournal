package net.grishmagolla.myJournal.entity;


import lombok.Data;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Document(collection = "users")
@Data
public class User {

    @Id
    //@Indexed(unique = true)
    public ObjectId id;
    @NonNull
    public String userName;
    @NonNull
    public String userPassword;
    @DBRef
    private List<JournalEntry> journalEntryList = new ArrayList<>();
    private List<String> roles; //roles can be admin, user

}
