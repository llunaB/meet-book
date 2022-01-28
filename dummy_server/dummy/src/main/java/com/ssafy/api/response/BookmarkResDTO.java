package com.ssafy.api.response;

import java.util.Date;

import com.ssafy.db.entity.Bookmark;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookmarkResDTO {
	private int id;
	private String title;
	private Date date;
	private boolean alarm;
	
	public BookmarkResDTO(Bookmark data) {
		setId(data.getId());
		setTitle(data.getConference().getTitle());
		setDate(data.getConference().getCall_start_time());
		setAlarm(data.getAlarm() == 1);
	}
}
