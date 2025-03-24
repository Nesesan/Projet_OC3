package com.nesesan.chatop.service;

import com.nesesan.chatop.dto.authentication.UserRegisterDTO;
import com.nesesan.chatop.dto.user.UserDTO;
import com.nesesan.chatop.model.User;
import com.nesesan.chatop.repository.UserRepository;
import com.nesesan.chatop.security.JwtUtil;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;


@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public UserService(UserRepository userRepository,
                                 PasswordEncoder passwordEncoder,
                                 JwtUtil jwtUtil) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public User getUserById(Integer id){
        return userRepository.findById(id).orElse(null);
    }

    public Optional<User> findByEmail(String email){
        return userRepository.findByEmail(email);
    }

    public String registerUser(UserRegisterDTO registerDTO) {
        if (userRepository.findByEmail(registerDTO.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already in use");
        }
        User user = new User();
        user.setName(registerDTO.getName());
        user.setEmail(registerDTO.getEmail());
        user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
        userRepository.save(user);
        return jwtUtil.generateToken(user.getEmail());
    }

    public String loginUser(String login, String password) {
        Optional<User> userOptional = userRepository.findByEmail(login);
        if (userOptional.isEmpty() || !passwordEncoder.matches(password, userOptional.get().getPassword())) {
            throw new IllegalArgumentException("Invalid credentials");
        }
        return jwtUtil.generateToken(login);
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.findByEmail(username);
        if (userOptional.isEmpty()) {
            throw new UsernameNotFoundException("User not found with email: " + username);
        }
        User user = userOptional.get();
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), new ArrayList<>());
    }

    public UserDTO getUserDTOByEmail(String email) {
        User user = findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return new UserDTO(Math.toIntExact(user.getId()), user.getName(), user.getEmail(),
                user.getCreatedAt(), user.getUpdatedAt());
    }
    public UserDTO getUserDTOById(Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with id: " + id));
        return new UserDTO(Math.toIntExact(user.getId()), user.getName(), user.getEmail(), user.getCreatedAt(), user.getUpdatedAt());
    }
}


