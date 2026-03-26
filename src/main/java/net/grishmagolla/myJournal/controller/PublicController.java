package net.grishmagolla.myJournal.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.grishmagolla.myJournal.dto.UserDTO;
import net.grishmagolla.myJournal.entity.User;
import net.grishmagolla.myJournal.service.UserDetailsServiceImpl;
import net.grishmagolla.myJournal.service.UserEntryService;
import net.grishmagolla.myJournal.utilis.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
@Slf4j
@RequiredArgsConstructor
@Tag(name = "Public APIs")
public class PublicController {
    private final UserEntryService userEntryService;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsServiceImpl userDetailsService;
    private final JwtUtil jwtUtil;


    @GetMapping("/health-check")
    public String healthCheck(){
        return "ok";
    }

    //Create User
    @PostMapping("/signup")
    public void signUp(@RequestBody UserDTO user){
        User newUser = new User();
        newUser.setUserEmail(user.getUserEmail());
        newUser.setUserPassword(user.getUserPassword());
        newUser.setSentimentAnalysis(user.isSentimentAnalysis());
        newUser.setUserName(user.getUserName());
        userEntryService.saveEntry(newUser);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user){
        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUserName(), user.getUserPassword()));
            UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUserName());
            String jwt = jwtUtil.generateToken(userDetails.getUsername());
            return new ResponseEntity<>(jwt, HttpStatus.OK);
        }catch(Exception e){
            log.error("Exception occured while createAuthenticationToken ", e);
            return new ResponseEntity<>("Incorrect Username or password", HttpStatus.BAD_REQUEST);
        }

    }
}
