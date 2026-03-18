package net.grishmagolla.myJournal.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class userRepositoryImplTest {

    @Autowired
    private UserRepositoryImpl userRepositoryImpl;
    @Autowired
    private UserEntryRepository userEntryRepository;

    @Test
    public void testSaveNewUser(){
        Assertions.assertNotNull(userEntryRepository.getUserForSA());
    }


}
