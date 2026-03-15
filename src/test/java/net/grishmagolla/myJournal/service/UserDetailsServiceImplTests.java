package net.grishmagolla.myJournal.service;

import net.grishmagolla.myJournal.entity.User;
import net.grishmagolla.myJournal.repository.UserEntryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.mockito.Mockito.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.bean.override.mockito.MockitoBean;


import static org.mockito.Mockito.when;

@SpringBootTest
public class UserDetailsServiceImplTests {

    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @MockitoBean
    private UserEntryRepository userEntryRepository;

    @Test
    void loadUserByUsernameTest() {
        User mockUser = new User("grishma", "grishma");
        when(userEntryRepository.findByUserName(Mockito.anyString())).thenReturn(mockUser);
        UserDetails user = userDetailsServiceImpl.loadUserByUsername("grishma");
        Assertions.assertNotNull(user);
        Assertions.assertEquals("grishma", user.getUsername());
    }
}
