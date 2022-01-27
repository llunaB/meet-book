package com.ssafy.api.controller;


import com.ssafy.DTO.UserDTO;
import com.ssafy.DTO.request.LoginReqDTO;
import com.ssafy.DTO.request.SingUpUserDto;
import com.ssafy.DTO.request.UpdateUserDto;
import com.ssafy.api.service.JwtUserService;
import com.ssafy.config.JwtTokenProvider;
import com.ssafy.db.entity.JwtUser;
import com.ssafy.db.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/userss")
public class JwtUserController {

//    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
//    private final JwtUserRepository jwtUserRepository;
    private final JwtUserService jwtService;


    // 회원가입
    @PostMapping("/join")
    public ResponseEntity<Map<String, String>> join(@RequestBody SingUpUserDto user) {
        System.out.println(user);
        HashMap<String, String> map = new HashMap<String, String>();

        if(jwtService.createUser(jwtService.Dto2Entity(user))) {
            System.out.println("user created");
            map.put("message", "회원가입 성공");

        }else {
            System.out.println("fail user created");
            map.put("message", "회원가입 실패");

        }
        return new ResponseEntity<Map<String,String>>(map, HttpStatus.OK);
//        return jwtUserRepository.save(JwtUser.builder()
//                .email(user.get("email"))
//                .password(passwordEncoder.encode(user.get("password")))
//                .roles(Collections.singletonList("ROLE_USER")) // 최초 가입시 USER 로 설정
//                .build()).getId();
    }


    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginReqDTO dto){
        HashMap<String, String> map = new HashMap<String, String>();

        JwtUser jwtUser = jwtService.getUserByeEmail(dto.getEmail());

        if(jwtService.matchPassword(dto.getPassword(),jwtUser.getPassword())) {
            map.put("message", "로그인 성공");
            map.put("token",jwtTokenProvider.createToken(jwtUser.getUsername(),jwtUser.getRoles()));
            return new ResponseEntity<Map<String,String>>(map, HttpStatus.OK);
        }

        map.put("message", "로그인 실패");
        return new ResponseEntity<Map<String,String>>(map, HttpStatus.BAD_REQUEST);
    }
    // 로그인 추가
//    @PostMapping("/user/login")
//    public String login(@RequestBody Map<String, String> user) {
//        JwtUser member = jwtUserRepository.findByEmail(user.get("email"))
//                .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 E-MAIL 입니다."));
//        if (!passwordEncoder.matches(user.get("password"), member.getPassword())) {
//            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
//        }
//        return jwtTokenProvider.createToken(member.getUsername(), member.getRoles());
//    }

    //회원정보 조회
    @GetMapping("/userinfo")
    public ResponseEntity<UserDTO> userinfo(@RequestBody Map<String, String> id) {
        JwtUser user = jwtService.getUserByeEmail(id.get("email"));
        return new ResponseEntity<UserDTO>(jwtService.Entity2Dto(user), HttpStatus.OK);
    }


    //회원 수정
    @PutMapping("/userinfo/{email}")
    public ResponseEntity<Map<String, String>> userupdate(@PathVariable("email") String email, @RequestBody UpdateUserDto dto){
        Map map = new HashMap<>();
        if(jwtService.updateUser(email,dto)){
            JwtUser jwtUser = jwtService.getUserByeEmail(dto.getEmail());
            map.put("message", "회원정보 수정 성공");
            map.put("token",jwtTokenProvider.createToken(jwtUser.getUsername(),jwtUser.getRoles()));
            return new ResponseEntity<Map<String,String>>(map, HttpStatus.OK);
        }
        map.put("message", "회원정보 수정 실패");
        return new ResponseEntity<Map<String,String>>(map, HttpStatus.BAD_REQUEST);
    }


    //회원 삭제
    @DeleteMapping("/{email}")
    public ResponseEntity<Map<String, String>> userdelete(@RequestBody Map<String, String> password,@PathVariable("email") String email){
        Map map = new HashMap<>();
        System.out.println(password);

        if(jwtService.deleteUser(email, password.get("password"))) {
            map.put("message", "삭제 성공");
            return new ResponseEntity<Map<String,String>>(map, HttpStatus.OK);
        }else {
            map.put("message", "삭제 실패");
            return new ResponseEntity<Map<String,String>>(map, HttpStatus.BAD_REQUEST);
        }
    }


    @RequestMapping("/test")
    public ResponseEntity<String> hello(){
        return ResponseEntity.ok("hello");
    }

}