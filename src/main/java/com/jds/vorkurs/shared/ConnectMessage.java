package com.jds.vorkurs.shared;


public class ConnectMessage extends Message {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3771943950538034158L;
	private Player player;

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public ConnectMessage(Player player) {
		super(Command.CONNECT);
		this.player = player;
	}
}
