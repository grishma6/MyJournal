package net.grishmagolla.myJournal.repository;

import net.grishmagolla.myJournal.entity.JournalEntry;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JournalEntryRepository extends MongoRepository<JournalEntry, ObjectId> {
    ObjectId id(ObjectId id);
}
