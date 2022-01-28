package com.ssafy.api.response;

import com.ssafy.DTO.UserDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDetailInfoResponseDTO {
	private String email;
	private String nickname;
	private String profileImage;
	private String profileDescription;
	
	public UserDetailInfoResponseDTO(UserDTO data) {
		setEmail(data.getEmail());
		setNickname(data.getNickname());
		setProfileImage(data.getProfileImage());
		setProfileDescription(data.getProfileDescription());
	}
}
