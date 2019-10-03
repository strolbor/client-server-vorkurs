package de.urs.game.schereSteinPapier;

import com.jds.vorkurs.shared.Command;
import com.jds.vorkurs.shared.Message;
import com.jds.vorkurs.shared.Player;

public class SendEnemyMessage extends Message{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6112465244898654242L;
	private int Ran;
	Player player;
	public Player getPlayer() {
		return player;
	}
	public void setPlayer(Player player) {
		this.player = player;
	}
	public int getRan() {
		return Ran;
	}
	public void setRan(int ran) {
		Ran = ran;
	}
	public SendEnemyMessage(Player PLayer) {
		super(Command.SENDGEGNER);
		player = PLayer;
	}

}
