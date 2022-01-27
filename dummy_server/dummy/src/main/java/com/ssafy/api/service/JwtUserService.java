package com.ssafy.api.service;

import java.util.Collections;
import java.util.Map;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.DTO.UserDTO;
import com.ssafy.api.request.SignUpUserDto;
import com.ssafy.api.request.UpdateUserDto;
import com.ssafy.db.entity.JwtUser;
import com.ssafy.db.repository.JwtUserRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class JwtUserService {

    private final JwtUserRepository jwtUserRepository;
    private final PasswordEncoder passwordEncoder;

    public JwtUser Dto2Entity(SignUpUserDto data) {
        JwtUser entity = new JwtUser();

        entity.setName(data.getName());
        entity.setPassword(passwordEncoder.encode(data.getPassword()));
        entity.setNickname(data.getNickname());
        entity.setEmail(data.getEmail());
        entity.setGender(data.getGender());
        entity.setAge(data.getAge());
        entity.setProfile_image(data.getProfile_image());
        entity.setProfile_description(data.getProfile_description());
        entity.setHost_point(0);
        entity.setGuest_point(0);
        entity.setRoles(Collections.singletonList("ROLE_USER"));

        return entity;
    }

    @Transactional
    public boolean createUser(JwtUser dto2Entity) {
        try{
            jwtUserRepository.save(dto2Entity);
            return true;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public JwtUser getUserByeEmail(String email) {
        return jwtUserRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
    }

    public boolean matchPassword(String password, String password1) {
        return passwordEncoder.matches(password,password1);
    }

    public JwtUser getUserInfo(Map<String, String> id) {
        return jwtUserRepository.findByEmail(id.get("email")).orElseThrow(() -> new IllegalArgumentException("가입되지 않은 E-MAIL 입니다."));

    }

    public UserDTO Entity2Dto(JwtUser data) {
        UserDTO dto = new UserDTO();

        dto.setId(data.getId());
        dto.setName(data.getName());
        dto.setNickname(data.getNickname());
        dto.setEmail(data.getEmail());
        dto.setGender(data.getGender());
        dto.setAge(data.getAge());
        dto.setProfile_image(data.getProfile_image());
        dto.setProfile_description(data.getProfile_description());
        dto.setHost_point(data.getHost_point());
        dto.setGuest_point(data.getGuest_point());
        return dto;

    }

    @Transactional
    public boolean updateUser(String email, UpdateUserDto dto) {
        try {
            JwtUser user = jwtUserRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("가입되지 않은 E-MAIL 입니다."));
            user.setEmail(dto.getEmail());
            user.setNickname(dto.getNickname());
            user.setProfile_image(dto.getProfile_image());
            user.setProfile_description(dto.getProfile_description());
            jwtUserRepository.save(user);
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Transactional
    public boolean deleteUser(String email, String password) {
        try {
            JwtUser user = jwtUserRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("가입되지 않은 E-MAIL 입니다."));
            user.getPassword();
            if(!passwordEncoder.matches(password,user.getPassword())){
                throw new Exception("아이디, 비밀번호 불일치");
            }
            jwtUserRepository.delete(user);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
