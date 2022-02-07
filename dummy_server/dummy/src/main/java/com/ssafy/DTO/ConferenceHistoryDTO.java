package com.ssafy.DTO;

import java.util.Date;

import com.ssafy.db.entity.Conference;
import com.ssafy.db.entity.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ConferenceHistoryDTO {
	private int id;
	private int conferenceId;
	private int userId;
	private String action;
	private Date insertedTime;
	
	public ConferenceHistoryDTO(int conferenceId, int userId, String action) {
		this.conferenceId = conferenceId;
		this.userId = userId;
		this.action = action;
		this.insertedTime = new Date();
	}
	
}
