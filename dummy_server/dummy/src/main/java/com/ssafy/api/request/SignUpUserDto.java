package com.ssafy.api.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SignUpUserDto {
    private String name;
    private String password;
    private String nickname;
    private String email;
    private int gender;
    private int age;
    private String profile_image;
    private String profile_description;

    @Override
    public String toString() {
        return "SingUpUserDto{" +
                "name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", nickname='" + nickname + '\'' +
                ", email='" + email + '\'' +
                ", gender=" + gender +
                ", age=" + age +
                ", profile_image='" + profile_image + '\'' +
                ", profile_description='" + profile_description + '\'' +
                '}';
    }
}
