package com.ssafy.api.requestDto.conference;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ForceDisconnectReq {
	private String token;
	private String connectionId;
}
