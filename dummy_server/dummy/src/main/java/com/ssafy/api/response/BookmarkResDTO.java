package com.ssafy.api.response;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookmarkResDTO {
	private int id;
	private String title;
	private Date date;
	private boolean alarm;
}
