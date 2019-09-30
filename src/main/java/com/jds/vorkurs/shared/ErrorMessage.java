package com.jds.vorkurs.shared;


public class ErrorMessage extends Message {

	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1064763211235290445L;

	public ErrorMessage(String message) {
		super(Command.ERROR);
		this.message = message;
	}
}
