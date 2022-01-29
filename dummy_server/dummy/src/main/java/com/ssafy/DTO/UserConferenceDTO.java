package com.ssafy.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserConferenceDTO {
	private int id;
	private int conferenceId;
	private int userId;
	private int authority;
}
