package com.jds.vorkurs.shared;

import lombok.Data;

@Data
public class ErrorMessage extends Message {

	private String message;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1064763211235290445L;

	public ErrorMessage(String message) {
		super(Command.ERROR);
		this.message = message;
	}
}
