package com.ssafy.api.responseDto;

import java.util.Date;

import com.ssafy.DTO.BookDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetConferencesRes {
	private int id; 
	private GetUserByProfileRes user;
	private BookDTO book;
	private Date callStartTime;
	private Date callEndTime;
	private String thumbnailUrl;
	private String title;
	private String description;
	private int maxMembers;
	private String tags;
	private String question;
	private String password;
}
