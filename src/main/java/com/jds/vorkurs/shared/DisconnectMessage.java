package com.jds.vorkurs.shared;


public class DisconnectMessage extends Message {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5629804782122309742L;

	public DisconnectMessage() {
		super(Command.DISCONNECT);
	}
}
