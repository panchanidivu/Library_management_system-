package com.example.librarymanagement.responseDTO;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomResponseEntity{

	private int code;
	
	private int status;
	
	private String message;
	
	private Object data;

	public CustomResponseEntity status(HttpStatus status2) {
		this.status = status2.value();
		return this;
    }
}
