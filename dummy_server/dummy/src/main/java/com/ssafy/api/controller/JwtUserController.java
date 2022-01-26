package com.ssafy.api.controller;


import com.ssafy.config.JwtTokenProvider;
import com.ssafy.db.entity.JwtUser;
import com.ssafy.db.repository.JwtUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class JwtUserController {

    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final JwtUserRepository jwtUserRepository;

    // 회원가입
    @PostMapping("/join")
    public Long join(@RequestBody Map<String, String> user) {
        return jwtUserRepository.save(JwtUser.builder()
                .email(user.get("email"))
                .password(passwordEncoder.encode(user.get("password")))
                .roles(Collections.singletonList("ROLE_USER")) // 최초 가입시 USER 로 설정
                .build()).getId();
    }

    // 로그인
    @PostMapping("/login")
    public String login(@RequestBody Map<String, String> user) {
        JwtUser member = jwtUserRepository.findByEmail(user.get("email"))
                .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 E-MAIL 입니다."));
        if (!passwordEncoder.matches(user.get("password"), member.getPassword())) {
            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
        }
        return jwtTokenProvider.createToken(member.getUsername(), member.getRoles());
    }

    @RequestMapping("/test")
    public ResponseEntity<String> hello(){
        return ResponseEntity.ok("hello");
    }

}