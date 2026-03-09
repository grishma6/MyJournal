package net.grishmagolla.myJournal.repository;


import net.grishmagolla.myJournal.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserEntryRepository extends MongoRepository<User, ObjectId> {

    User findByUserName(String userName);

    void deleteByUserName(String userName);


}
