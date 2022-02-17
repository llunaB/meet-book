package com.ssafy.api.responseDto;

import lombok.*;
import org.springframework.http.HttpStatus;

@Data
public class MessageRes {
	private String message;
	private Object data;

	public MessageRes() {
		this.message = null;
		this.data = null;
	}
}
