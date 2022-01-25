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
	private int user_id;
	private int book_id;
	private Date call_start_time;
	private Date call_end_time;
	private String question;
	private String password;
	private String thumbnail_url;
	private String title;
	private String description;
	private int max_members;
	private String tags;
}
