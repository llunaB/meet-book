package com.ssafy.api.controller;

import com.ssafy.api.requestDto.LoginReq;
import com.ssafy.api.requestDto.SignUpReq;
import com.ssafy.api.service.OAuthService;
import com.ssafy.db.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/oauth")
public class OauthController {

    private final OAuthService oAuthService;
    private final UserRepository repository;

    @GetMapping("/kakao")
    public ResponseEntity<Map<String, String>> kakao(@RequestParam String code) throws Exception {
        log.info("Map : " + code);
//        log.info(map.get("code"));
//        log.info(map.get("state"));
//        log.info(map.get("error"));
//        log.info(map.get("error_description"));
        Map<String, String> map = new HashMap<>();

        try {
            String accessToken = oAuthService.getKakaoAccessToken(code);
            SignUpReq signUpReq = oAuthService.createKakaoUser(accessToken);
            System.out.println("Controller" + signUpReq);
            String token = oAuthService.getToken(signUpReq);
            map.put("token", token);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<Map<String, String>>(map, HttpStatus.OK);
//        oAuthService.getToken(email);


//        try {
//            String token = userService.login(loginReq);
//            if(!token.equals("")) {
//                map.put("message", "로그인 성공");
//                map.put("token",token);
//                return new ResponseEntity<Map<String,String>>(map, HttpStatus.OK);
//            }
//            map.put("message", "로그인 실패");
//        }catch(Exception e){
//            e.printStackTrace();
//            map.put("message", "로그인 실패");
//            return new ResponseEntity<Map<String,String>>(map, HttpStatus.BAD_REQUEST);
//        }
//        map.put("message", "로그인 실패");
//        return new ResponseEntity<Map<String,String>>(map, HttpStatus.BAD_REQUEST);

    }

//    @PostMapping("/login")
//    public ResponseEntity<Map<String, String>> login(@RequestBody LoginReq loginReq){
//        HashMap<String, String> map = new HashMap<String, String>();
//
//        try {
//            String token = userService.login(loginReq);
//            if(!token.equals("")) {
//                map.put("message", "로그인 성공");
//                map.put("token",token);
//                return new ResponseEntity<Map<String,String>>(map, HttpStatus.OK);
//            }
//            map.put("message", "로그인 실패");
//        }catch(Exception e){
//            e.printStackTrace();
//            map.put("message", "로그인 실패");
//            return new ResponseEntity<Map<String,String>>(map, HttpStatus.BAD_REQUEST);
//        }
//        map.put("message", "로그인 실패");
//        return new ResponseEntity<Map<String,String>>(map, HttpStatus.BAD_REQUEST);
//    }

    @GetMapping("/naver")
    public ResponseEntity<Map<String, String>> naver(@RequestParam String code) throws Exception {
        log.info("Map : " + code);
//        log.info(map.get("code"));
//        log.info(map.get("state"));
//        log.info(map.get("error"));
//        log.info(map.get("error_description"));
        Map<String, String> map = new HashMap<>();

        try {
            String accessToken = oAuthService.getNaverAccessToken(code);
            SignUpReq signUpReq = oAuthService.createNaverUser(accessToken);
            System.out.println("Controller" + signUpReq);
            String token = oAuthService.getToken(signUpReq);
            map.put("token", token);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<Map<String, String>>(map, HttpStatus.OK);
    }
}
