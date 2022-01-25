package com.ssafy.domain.user;

import javax.persistence.*;

import com.ssafy.DTO.UserDTO;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
@Entity
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(nullable = false)
	private String name;
	
	@Column
	private String password;
	
	@Column
	private String nickname;
	
	@Column
	private String email;

	@Column
	private int gender;

	@Column
	private int age;
	
	@Column
	private int host_point;
	
	@Column
	private int guest_point;
	
	@Column
	private String profile_image;
	
	@Column
	private String profile_description;

	public void update(User user) {
		setName(user.getName());
		setPassword(user.getPassword());
		setNickname(user.getNickname());
		setEmail(user.getEmail());
		setGender(user.getGender());
		setAge(user.getAge());
		setProfile_image(user.getProfile_image());
		setProfile_description(user.getProfile_description());
	}

	@Builder
	public User(int id, String name, String password, String nickname, String email, int gender, int age, int host_point, int guest_point, String profile_image, String profile_description) {
		this.id = id;
		this.name = name;
		this.password = password;
		this.nickname = nickname;
		this.email = email;
		this.gender = gender;
		this.age = age;
		this.host_point = host_point;
		this.guest_point = guest_point;
		this.profile_image = profile_image;
		this.profile_description = profile_description;
	}
}
