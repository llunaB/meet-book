package com.ssafy.DTO;

import java.sql.Timestamp;
import java.util.Date;

import com.ssafy.db.entity.Conference;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ConferenceDTO {
	private int id; 
	private int userId;
	private int bookId;
	private Date callStartTime;
	private Date callEndTime;
	private String question;
	private String password;
	private String thumbnailUrl;
	private String title;
	private String description;
	private int maxMembers;
	private String tags;
}
