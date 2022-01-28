package com.ssafy.DTO;

import java.util.Date;

import com.ssafy.api.request.SignUpUserRequestDTO;

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
	private String password;
	private String nickname;
	private String email;
	private int gender;
	private Date age;
	private int hostPoint;
	private int guestPoint;
	private String profileImage;
	private String profileDescription;
	
	public UserDTO(SignUpUserRequestDTO data) {
		setPassword(data.getPassword());
		setNickname(data.getNickname());
		setEmail(data.getEmail());
		setGender(data.getGender());
		setAge(data.getAge());
		setHostPoint(0);
		setGuestPoint(0);
		setProfileImage(data.getProfile_image());
		setProfileDescription(data.getProfile_description());
	}
}
