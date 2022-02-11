package com.ssafy.api.controller;


import com.ssafy.DTO.UserDTO;
import com.ssafy.api.requestDto.EmailCheckReq;
import com.ssafy.api.requestDto.EmailReq;
import com.ssafy.api.requestDto.UpdatePasswordReq;
import com.ssafy.api.requestDto.UpdateUserByDetailReq;
import com.ssafy.api.responseDto.MessageRes;
import com.ssafy.api.service.UserService;
import com.ssafy.config.job.EmailService;
import com.ssafy.db.entity.Email;
import com.ssafy.db.repository.UserRepository;
import com.ssafy.error.exception.AlreadyExistEmailException;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;


@Slf4j
@Controller
@RequestMapping("/api/email")
public class EmailController {

    @Autowired
    MailSender mailSender;
    @Autowired
    UserService userService;
    @Autowired
    EmailService emailService;



    @PostMapping("/key") // AJAX와 URL을 매핑시켜줌
    @ResponseBody  //AJAX후 값을 리턴하기위해 작성
    public ResponseEntity<Map<String,String>> createKey(@RequestBody EmailReq emailReq) {
        log.info("emailReq : "+emailReq.getEmail());
        Map<String, String> map = new HashMap<>();

        try{
            if(userService.getUserByEmail(emailReq.getEmail())!=null){
                throw new AlreadyExistEmailException();
            }
            emailService.createMail(emailReq);
            map.put("msg","이메일에 키를 보냈습니다.");
            return new ResponseEntity<Map<String, String>>(map, HttpStatus.OK);
        }catch (AlreadyExistEmailException e){
            e.printStackTrace();
            map.put("msg","이디 등록된 이메일이 있습니다. 확인 후 이용해주세요");
        } catch (Exception e){
            e.printStackTrace();
            map.put("msg", "잘못된 이메일 형식이거나 이미 등록된 이메일이 있습니다.");
        }
        return new ResponseEntity<>(map,HttpStatus.BAD_REQUEST);

    }

    @PostMapping("/key-check") // AJAX와 URL을 매핑시켜줌
    @ResponseBody  //AJAX후 값을 리턴하기위해 작성
    public ResponseEntity<Map<String,String>> getEmailAndKey(@RequestBody EmailCheckReq emailCheckReq) {
        log.info("emailReq : "+emailCheckReq.getEmail());
        Map<String, String> map = new HashMap<>();
        try{
            if(emailService.getEmailandKey(emailCheckReq).isPresent()){
                map.put("msg","인증된 이메일입니다.");
                return new ResponseEntity<Map<String, String>>(map, HttpStatus.OK);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        map.put("msg", "키값이 잘못됐습니다. 확인 후 다시 시도하시길 바랍니다.");
        return new ResponseEntity<>(map,HttpStatus.BAD_REQUEST);
    }

    @RequestMapping("/pwdcheck/{id}/{email}") // AJAX와 URL을 매핑시켜줌
    @ResponseBody  //AJAX후 값을 리턴하기위해 작성
    public ResponseEntity<Map<String,String>> updatePwdByMail(@PathVariable("id") String id,@PathVariable("email") String email) {
        UpdatePasswordReq updatePasswordReq = new UpdatePasswordReq();
        Map<String,String> map = new HashMap<>();
        MessageRes messageRes = new MessageRes();
        Random random=new Random();  //난수 생성을 위한 랜덤 클래스
        String password="";  //임시 비밀번호
        Map<String,String> check = new HashMap<>();
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email); //스크립트에서 보낸 메일을 받을 사용자 이메일 주소
        //입력 키를 위한 코드
        for(int i =0; i<3;i++) {
            int index=random.nextInt(25)+65; //A~Z까지 랜덤 알파벳 생성
            password+=(char)index;
        }

        int numIndex=random.nextInt(9999)+1000; //4자리 랜덤 정수를 생성
        password+=numIndex;
        message.setSubject("변경된 비밀번호 메일 전송");
        message.setText("비밀번호: "+password);

        try {
        	updatePasswordReq.setNewPassword(password);
            if(userService.updatePassword(updatePasswordReq, Integer.parseInt(id))){
                UserDTO user = userService.getUserById(Integer.parseInt(id));
                map.put("message", "비밀번호 수정 성공");
                mailSender.send(message);
                return new ResponseEntity<Map<String,String>>(map, HttpStatus.OK);
            }
        }catch(Exception e){
            e.printStackTrace();
            map.put("message", "비밀번호 수정 실패");
        }
        return new ResponseEntity<Map<String,String>>(map, HttpStatus.BAD_REQUEST);

    }


}
