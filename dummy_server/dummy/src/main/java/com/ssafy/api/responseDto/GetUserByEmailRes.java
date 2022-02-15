package com.ssafy.api.responseDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.List;

@Data
@Slf4j
@AllArgsConstructor
@NoArgsConstructor
public class GetUserByEmailRes {

    private int id;
    private String password;
    private String nickname;
    private String email;
    private int gender;
    private Date age;
    private int hostPoint;
    private int guestPoint;
    private String profileImage;
    private String profileDescription;
    private List<String> roles;
}
