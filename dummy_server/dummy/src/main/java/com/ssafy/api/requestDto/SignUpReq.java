package com.ssafy.api.requestDto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SignUpReq {
    private String password;
    private String nickname;
    private String email;
    private int gender;
    private Date age;
    private String profileImage;
    private String profileDescription;
	@Override
	public String toString() {
		return "SignUpUserDto [password=" + password + ", nickname=" + nickname + ", email=" + email + ", gender="
				+ gender + ", age=" + age + ", profile_image=" + profileImage + ", profile_description="
				+ profileDescription + "]";
	}
    
    
}
