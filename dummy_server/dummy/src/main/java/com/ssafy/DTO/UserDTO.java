package com.ssafy.DTO;

import com.ssafy.domain.user.User;
import lombok.*;

@Getter
@Setter
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

	@Builder
	public UserDTO(int id, String name, String password, String confirm_password, String nickname, String email, int gender, int age, int host_point, int guest_point, String profile_image, String profile_description) {
		this.id = id;
		this.name = name;
		this.password = password;
		this.confirm_password = confirm_password;
		this.nickname = nickname;
		this.email = email;
		this.gender = gender;
		this.age = age;
		this.host_point = host_point;
		this.guest_point = guest_point;
		this.profile_image = profile_image;
		this.profile_description = profile_description;
	}

	public User toEntity(){
		return User.builder()
				.id(id)
				.name(name)
				.password(password)
				.nickname(nickname)
				.email(email)
				.gender(gender)
				.age(age)
				.host_point(host_point)
				.guest_point(guest_point)
				.profile_description(profile_description)
				.profile_image(profile_image)
				.build();
	}
}
