package net.grishmagolla.myJournal.controller;

import net.grishmagolla.myJournal.entity.User;
import net.grishmagolla.myJournal.repository.UserEntryRepository;
import net.grishmagolla.myJournal.service.UserEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserEntryController {
    @Autowired
    private UserEntryService userEntryService;

    @Autowired
    private UserEntryRepository userEntryRepository;

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody User user){
        userEntryService.saveNewUser(user); // was saveEntry before
        return new ResponseEntity<>("User is created in DB", HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody User user){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User userInDb = userEntryService.findByUserName(userName);
        userInDb.setUserName(user.getUserName());
        userInDb.setUserPassword(user.getUserPassword());
        userEntryService.saveEntry(userInDb);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/deleteAll")
    public ResponseEntity<?> deleteAll(){
        userEntryRepository.deleteAll();
        return new ResponseEntity<>("All users deleted", HttpStatus.OK);
    }
}