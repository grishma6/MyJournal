package net.grishmagolla.myJournal.repository;

import net.grishmagolla.myJournal.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRepositoryImpl {

    private final MongoTemplate mongoTemplate;

    public UserRepositoryImpl(MongoTemplate mongoTemplate){
        this.mongoTemplate = mongoTemplate;
    }

    public List<User> findBySA(){
        Query query = new Query();
        Criteria criteria = new Criteria();

        query.addCriteria(Criteria.where("userEmail").exists(true));
        query.addCriteria(Criteria.where("sentimentAnalysis").is("true"));
        query.addCriteria(Criteria.where("userEmail").ne("null").ne(""));

        //using or operator
//        query.addCriteria(criteria.orOperator(Criteria.where("userEmail").exists(true),
//                Criteria.where("sentimentAnalysis").is("true"))
//        );
        List<User> users = mongoTemplate.find(query, User.class);
        return users;
    }
}
