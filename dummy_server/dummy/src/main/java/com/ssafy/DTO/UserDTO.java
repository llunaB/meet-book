package com.ssafy.DTO;

import com.ssafy.db.entity.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
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
