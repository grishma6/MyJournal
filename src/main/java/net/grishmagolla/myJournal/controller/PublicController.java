package net.grishmagolla.myJournal.controller;

import net.grishmagolla.myJournal.entity.User;
import net.grishmagolla.myJournal.service.UserEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class PublicController {
    @Autowired
    private UserEntryService userEntryService;

    @GetMapping("/health-check")
    public String healthCheck(){
        return "ok";
    }

    //Create User
    @PostMapping("/create-user")
    public void createUser(@RequestBody User user){
        userEntryService.saveEntry(user);
    }
}
