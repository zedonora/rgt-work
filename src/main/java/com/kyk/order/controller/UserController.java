package com.kyk.order.controller;

import com.kyk.order.dto.UserRegistrationDto;
import com.kyk.order.dto.UserResponseDto;
import com.kyk.order.security.CustomUserDetails;
import com.kyk.order.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    public UserController(UserService userService, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> register(@RequestBody UserRegistrationDto registrationDto) {
        return ResponseEntity.ok(userService.registerUser(registrationDto));
    }

    @PostMapping(path = "/login",  consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public ResponseEntity<?> login(@RequestParam String username,
                                   @RequestParam String password,
                                   HttpServletRequest request,
                                   HttpServletResponse response) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );
            // 인증된 사용자의 정보를 가져옵니다.
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            Long userId = ((CustomUserDetails) userDetails).getUserId();

            // 세션 ID를 쿠키에 설정
            HttpSession session = request.getSession(true);
            session.setAttribute("userId", userId);

            Cookie sessionCookie = new Cookie("JSESSIONID", session.getId());
            sessionCookie.setHttpOnly(true);
            sessionCookie.setPath("/");
            response.addCookie(sessionCookie);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return ResponseEntity.ok().body("{\"message\":\"로그인 성공\"}");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("{\"error\":\"로그인 실패: " + e.getMessage() + "\"}");
        }
    }
}