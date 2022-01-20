package com.ssafy.DTO;

import java.sql.Date;
import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ConferenceDTO {
	private int id; 
	private int user_id;
	private int book_id;
	private Timestamp call_start_time;
	private Timestamp call_end_time;
	private String question;
	private String password;
	private String thumbnail_url;
	private String title;
	private String description;
	private int max_members;
	private String tags;
	
	public ConferenceDTO() {
		id = 0; 
		user_id = 1;
		book_id =  2;
		call_start_time = Timestamp.valueOf("2016-11-22 11:22:33.4");
		call_end_time = Timestamp.valueOf("2016-11-22 12:34:56.7");
		question = "질문";
		password = "대답";
		thumbnail_url = "url.of.image/conference_id";
		title = "제목";
		description = "회의";
		max_members = 5;
		tags = "#tag";
	}
}
