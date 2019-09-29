package com.jds.vorkurs.shared;

import lombok.Data;

@Data
public class ConnectMessage extends Message {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3771943950538034158L;
	private Player player;

	public ConnectMessage(Player player) {
		super(Command.CONNECT);
		this.player = player;
	}
}
