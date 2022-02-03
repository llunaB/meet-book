package com.ssafy.DTO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.ssafy.api.requestDto.SignUpReq;

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
	private List<String> roles;
	
	public UserDTO(SignUpReq data) {
		setPassword(data.getPassword());
		setNickname(data.getNickname());
		setEmail(data.getEmail());
		setGender(data.getGender());
		setAge(data.getAge());
		setHostPoint(0);
		setGuestPoint(0);
		setProfileImage(data.getProfileImage());
		setProfileDescription(data.getProfileDescription());
		setRoles(new ArrayList<String>());
		getRoles().add("ROLE_USER");
	}
}
