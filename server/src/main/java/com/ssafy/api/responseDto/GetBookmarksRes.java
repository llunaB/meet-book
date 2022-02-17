package com.ssafy.api.responseDto;

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
public class GetBookmarksRes {
	private int id;
	private int conferenceId;
	private String title;
	private Date date;
	private boolean alarm;
}
