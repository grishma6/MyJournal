package net.grishmagolla.myJournal.service;

import net.grishmagolla.myJournal.entity.User;
import net.grishmagolla.myJournal.repository.UserEntryRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.mockito.Mockito.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;


import static org.mockito.Mockito.when;

//@SpringBootTest
@ActiveProfiles("dev")
public class UserDetailsServiceImplTests {

    // this @InjectMocks ,@BeforeEach ,@Mock doesnot load the entire database and it is fast no need of @SpringBootTest


    //@Autowired
    @InjectMocks
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @Mock
    private UserEntryRepository userEntryRepository;

    private AutoCloseable closeable;

    //userEntryRepository as this is null we are intializing @BeforeEach

    @BeforeEach
    void setUp() {
        //Returns an AutoCloseable object
        //Can properly clean up after each test
        //No memory leaks
        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close(); // clean up after each test
    }

    @Test
    void loadUserByUsernameTest() {
        User mockUser = new User("grishma", "grishma");
        when(userEntryRepository.findByUserName(Mockito.anyString())).thenReturn(mockUser);
        UserDetails user = userDetailsServiceImpl.loadUserByUsername("grishma");
        Assertions.assertNotNull(user);
        Assertions.assertEquals("grishma", user.getUsername());
    }
}