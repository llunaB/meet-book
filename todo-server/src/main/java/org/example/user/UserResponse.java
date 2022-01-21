package org.example.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {

    private int id;
    private String name;
    private String password;
    private String confirm_password;
    private String nickname;
    private String email;
    private int gender;
    private int age;
    private int host_point;
    private int guest_point;
    private String profile_image;
    private String profile_description;

}
