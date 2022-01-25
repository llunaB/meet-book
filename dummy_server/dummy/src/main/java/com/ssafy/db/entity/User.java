package com.ssafy.db.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.ssafy.DTO.UserDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column
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
}
