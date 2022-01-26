package com.ssafy.api.service;

import com.ssafy.DTO.request.SingUpUserDto;
import com.ssafy.db.entity.JwtUser;
import com.ssafy.db.entity.User;
import com.ssafy.db.repository.JwtUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@RequiredArgsConstructor
@Service
public class JwtUserDetailService implements UserDetailsService {

    private final JwtUserRepository jwtUserRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return jwtUserRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
    }


}
