package com.kyk.order.service;

import com.kyk.order.dto.UserRegistrationDto;
import com.kyk.order.dto.UserResponseDto;
import com.kyk.order.entity.User;
import com.kyk.order.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserResponseDto registerUser(UserRegistrationDto registrationDto) {
        User user = User.builder()
                .username(registrationDto.getUsername())
                .password(passwordEncoder.encode(registrationDto.getPassword()))
                .role("CUSTOMER")
                .build();

        userRepository.save(user);

        return UserResponseDto.builder()
                .username(user.getUsername())
                .build();
    }
}
