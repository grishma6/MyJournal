package net.grishmagolla.myJournal.service;

import lombok.NonNull;
import net.grishmagolla.myJournal.entity.User;
import net.grishmagolla.myJournal.repository.UserEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class UserEntryService {

    private final UserEntryRepository userEntryRepository;

    //private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserEntryService(UserEntryRepository userEntryRepository) {
        this.userEntryRepository = userEntryRepository;
    }

    public boolean saveNewUser(User user) {
        try {
            user.setUserPassword(passwordEncoder.encode(user.getUserPassword()));
            userEntryRepository.save(user);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void saveAdmin(User user){
        user.setUserPassword(passwordEncoder.encode(user.getUserPassword()));
        user.setRoles(Arrays.asList("USER", "ADMIN"));
        userEntryRepository.save(user);
    }

    public void saveEntry(User user) {
        userEntryRepository.save(user); // no encoding here
    }

    public List<User> getAll() {
        return userEntryRepository.findAll();
    }

    public Optional<User> findById(ObjectId id) {
        return userEntryRepository.findById(id);
    }

    public void deleteById(ObjectId id) {
        userEntryRepository.deleteById(id);
    }

    public User findByUserName(@NonNull String userName) {
        return userEntryRepository.findByUserName(userName);
    }

    public void saveUser(User user) {
    }
}