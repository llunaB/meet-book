package com.ssafy.api.requestDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserByProfileReq {
	private String nickname;
	private String profileImage;
	private String profileDescription;
}
