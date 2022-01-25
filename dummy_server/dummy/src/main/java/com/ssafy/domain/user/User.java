package com.ssafy.domain.user;

import javax.persistence.*;

import com.ssafy.DTO.UserDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
//@Table(name = "user")
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

//	@Enumerated(value = EnumType.STRING)  // 중요!
	@Column
	private int gender;
//	private String gender;

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

/*	@Column(updatable = false)  // 변경되지 않도록
	private LocalDateTime createdAt;

	@Column
	private LocalDateTime updatedAt;*/

/*	@PrePersist
	public void prePersist() {
		this.createdAt = LocalDateTime.from(LocalDate.now());  // Insert 전에 setCreatedAt 하지 않아도 자동으로 저장된다.
		this.updatedAt = LocalDateTime.from(LocalDate.now());  // Insert 전에 setCreatedAt 하지 않아도 자동으로 저장된다.
	}

	@PreUpdate
	public void preUpdate() {
		this.updatedAt = LocalDateTime.from(LocalDate.now());
	}*/

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
}
