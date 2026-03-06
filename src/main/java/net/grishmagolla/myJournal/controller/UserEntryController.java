package net.grishmagolla.myJournal.controller;

import net.grishmagolla.myJournal.entity.User;
import net.grishmagolla.myJournal.service.UserEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserEntryController {
    @Autowired
    private UserEntryService userEntryService;

    @GetMapping
    public List<User> getAllUsers(){
        return userEntryService.getAll();
    }

    //Create User
    @PostMapping
    public void createUser(@RequestBody User user){
        userEntryService.saveEntry(user);
    }

    //Update User
    @PutMapping({"/userName"})
    public ResponseEntity<?> updateUser(@RequestBody User user, @PathVariable String userName){
        User userInDb = userEntryService.findByUserName(userName);
        if(userInDb != null){
            userInDb.setUserName(user.getUserName());
            userInDb.setUserPassword(user.getUserPassword());
            userEntryService.saveEntry(userInDb);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }



}