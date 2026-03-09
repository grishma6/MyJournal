package net.grishmagolla.myJournal.service;

import lombok.NonNull;
import net.grishmagolla.myJournal.entity.User;
import net.grishmagolla.myJournal.repository.UserEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class UserEntryService {

    private final UserEntryRepository userEntryRepository;

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UserEntryService(UserEntryRepository userEntryRepository) {
        this.userEntryRepository = userEntryRepository;
    }

    public void saveEntry(User user) {
        user.setUserPassword(passwordEncoder.encode(user.getUserPassword()));
        user.setRoles(Arrays.asList("USER"));
        userEntryRepository.save(user);
    }

    public void saveNewUser(User user){
        userEntryRepository.save(user);
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
}