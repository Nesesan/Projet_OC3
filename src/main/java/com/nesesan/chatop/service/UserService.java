package com.nesesan.chatop.service;

import com.nesesan.chatop.model.User;
import com.nesesan.chatop.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUserById(Integer id){
        return userRepository.findById(id).orElse(null);
    }
}
