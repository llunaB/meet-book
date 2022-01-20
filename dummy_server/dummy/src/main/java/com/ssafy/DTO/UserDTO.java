package com.ssafy.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
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
	
	public UserDTO() {
		id = 1;
		name = "dummy";
		password = "1234";
		confirm_password = "1234";
		nickname = "ssafy";
		email = "ssafy@ssafy.com";
		gender = 0;
		age = 22;
		host_point = 0;
		guest_point = 0;
		profile_image = "url.of.image/user_id";
		profile_description="description";
	}
}
