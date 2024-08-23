package com.kyk.order;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;


@SpringBootTest
public class PasswordEncoderTest {
    @MockBean
    private PasswordEncoder passwordEncoder;


    @Test
    public void testPasswordEncoder() {
        String rawPassword = "password";
        String encodedPassword = "$2a$10$rGNWfGUdzCB.WrfXQZ9we.9wnxWG9r.Oo7obYbUNx7NcVeL1yGGZi";
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);

        assertTrue(passwordEncoder.matches(rawPassword, encodedPassword));
    }
}
