package com.kyk.order.security;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.kyk.order.entity.User;
import com.kyk.order.repository.UserRepository;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private static final Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);

    private final UserRepository userRepository;

    CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.debug("사용자 이름으로 사용자 로드 시도: {}", username);

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> {
                    logger.error("사용자를 찾을 수 없음: {}", username);
                    return new UsernameNotFoundException("사용자를 찾을 수 없음: " + username);
                });

        logger.debug("사용자 찾음: {}", user.getUsername());

        return new CustomUserDetails(user.getId(), user.getUsername(), user.getPassword(), user.getRole());
    }
}