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
public class GetUserByProfileRes {
	private int id;
	private String nickname;
	private String profileImage;
	private String profileDescription;
	
	public GetUserByProfileRes(UserDTO data) {
		setId(data.getId());
		setNickname(data.getNickname());
		setProfileImage(data.getProfileImage());
		setProfileDescription(data.getProfileDescription());
	}
}
