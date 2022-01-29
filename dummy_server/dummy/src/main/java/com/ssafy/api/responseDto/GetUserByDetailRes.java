package com.ssafy.api.responseDto;

import com.ssafy.DTO.UserDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetUserByDetailRes {
	private String email;
	private String nickname;
	private String profileImage;
	private String profileDescription;
	
	public GetUserByDetailRes(UserDTO data) {
		setEmail(data.getEmail());
		setNickname(data.getNickname());
		setProfileImage(data.getProfileImage());
		setProfileDescription(data.getProfileDescription());
	}
}
