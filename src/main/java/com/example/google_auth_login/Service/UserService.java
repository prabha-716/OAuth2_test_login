package com.example.google_auth_login.Service;

import com.example.google_auth_login.Repository.UserRepository;
import com.example.google_auth_login.models.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

public UserService(UserRepository userRepository){
    this.userRepository = userRepository;
}


    public List<User> getAllUsers(){
        return userRepository.findAll();
    }
}
