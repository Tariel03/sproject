package com.example.sproject.Services;

import com.example.sproject.Models.User;
import com.example.sproject.Repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class RegistrationService {
    private final PasswordEncoder passwordEncoder;
    private final ClientRepository clientRepository;
    @Autowired
    public RegistrationService(PasswordEncoder passwordEncoder, ClientRepository clientRepository) {
        this.passwordEncoder = passwordEncoder;
        this.clientRepository = clientRepository;
    }

    @Transactional
    public void register(User user){
        String pass = passwordEncoder.encode(user.getPassword());
        user.setPassword(pass);
        clientRepository.save(user);

    }
    public User currentUser (){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails personDetails = (UserDetails) authentication.getPrincipal();
        String username =  personDetails.getUsername();
        Optional<User>userOptional = clientRepository.findByUsername(username);
        return userOptional.orElse(null);
    }

}
