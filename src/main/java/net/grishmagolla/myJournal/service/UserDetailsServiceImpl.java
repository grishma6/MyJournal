package net.grishmagolla.myJournal.service;

import net.grishmagolla.myJournal.entity.User;
import net.grishmagolla.myJournal.repository.UserEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    public UserEntryRepository userEntryRepository;

    @Override //this is for authentication
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userEntryRepository.findByUserName(username);
        //when user is found
        if(user != null){
            UserDetails userDetails = org.springframework.security.core.userdetails.User.builder()
                    .username(user.getUserName())
                    .password(user.getUserPassword())
                    .roles(user.getRoles().toArray(new String[0]))
                    .build();
            return userDetails;
        }
        //if user is not found will throw an error
        throw new UsernameNotFoundException("User is NOT found: " + username);
    }
}
