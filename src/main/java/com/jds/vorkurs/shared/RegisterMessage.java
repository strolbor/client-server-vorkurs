package com.jds.vorkurs.shared;

public class RegisterMessage extends Message{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8720541064607628691L;
	private Player player;
	public RegisterMessage(Player player) {
		super(Command.REGISTER);
		this.setPlayer(player);
	}
	public Player getPlayer() {
		return player;
	}
	public void setPlayer(Player player) {
		this.player = player;
	}

}
