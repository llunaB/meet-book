package com.ssafy.api.controller;


import com.ssafy.DTO.UserDTO;
import com.ssafy.api.requestDto.UpdateUserByDetailReq;
import com.ssafy.api.responseDto.MessageRes;
import com.ssafy.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;


@Controller
@RequestMapping("/email")
public class EmailController {

    @Autowired
    MailSender mailSender;
    @Autowired
    UserService userService;

    @RequestMapping("/emailcheck") // AJAX와 URL을 매핑시켜줌
    @ResponseBody  //AJAX후 값을 리턴하기위해 작성
    public Map<String,String> createMail(@RequestParam String mail) {
        Random random=new Random();  //난수 생성을 위한 랜덤 클래스
        String key="";  //인증번호
        Map<String,String> check = new HashMap<>();
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(mail); //스크립트에서 보낸 메일을 받을 사용자 이메일 주소
        //입력 키를 위한 코드
        for(int i =0; i<3;i++) {
            int index=random.nextInt(25)+65; //A~Z까지 랜덤 알파벳 생성
            key+=(char)index;
        }

        int numIndex=random.nextInt(9999)+1000; //4자리 랜덤 정수를 생성
        key+=numIndex;
        message.setSubject("인증번호 입력을 위한 메일 전송");
        message.setText("인증 번호 : "+key);
        mailSender.send(message);
        System.out.println("인증");
        check.put("key",key);
        return check;
    }

    @RequestMapping("/pwdcheck/{id}/{email}") // AJAX와 URL을 매핑시켜줌
    @ResponseBody  //AJAX후 값을 리턴하기위해 작성
    public ResponseEntity<Map<String,String>> updatePwdByMail(@PathVariable("id") String id,@PathVariable("email") String email) {
        UpdateUserByDetailReq updateUserByDetailReq = new UpdateUserByDetailReq();
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
            updateUserByDetailReq.setNewPassword(password);
            if(userService.updateUserByDetail(updateUserByDetailReq, Integer.parseInt(id))){
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
