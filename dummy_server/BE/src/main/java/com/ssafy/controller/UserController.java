package com.ssafy.controller;

import com.ssafy.DTO.UserDto;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping("/info")
    public UserDto info(){
        Optional<UserDto> resp = Optional.of(new UserDto(1, "ssafy@ssafy.com", "1234", "JSW"));
        return  new UserDto(1, "ssafy@ssafy.com", "1234", "JSW");
    }
}
