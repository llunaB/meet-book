package com.ssafy.api.responseDto;

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
	private GetConferencesRes conference;
	private boolean alarm;
}
