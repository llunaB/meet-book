package com.ssafy.api.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;


@Controller
public class EmailController {

    @Autowired
    MailSender sender;

    @RequestMapping("/CheckMail") // AJAX와 URL을 매핑시켜줌
    @ResponseBody  //AJAX후 값을 리턴하기위해 작성
    public Map<String,String> SendMail(@RequestParam String mail) {
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
        sender.send(message);
        System.out.println("인증");
        check.put("key",key);
        return check;
    }


}
