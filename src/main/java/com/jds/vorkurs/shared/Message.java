package com.jds.vorkurs.shared;

import java.io.Serializable;


public class Message implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5525242340331171031L;
	private Command command;
	
	public Message(Command errorCMD) {
		// TODO Auto-generated constructor stub
		this.command = errorCMD;
	}
	public void setCommand(Command command) {
		this.command = command;
	}
	public Command getCommand() {
		return command;
	}
	
	
}
