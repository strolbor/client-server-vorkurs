package com.jds.vorkurs.server;

import com.jds.vorkurs.shared.Player;

public interface HandlerAdapter {
	/**
	 * register a new client on the game Server, generating a unique id for him
	 * 
	 * @param clientHandler the client handler that will do the communication with
	 *                      him
	 * @return a unique id for the client
	 */
	String registerNewUser(Player player, ClientHandler clientHandler);

	/**
	 * removes the client from the active list in the server
	 * 
	 * @param player the client/ player that has to be removed
	 */
	void unregisterUser(Player player);
	
	void givePlayer(Player player);
	
	Player getEnemyPlayer(Player player);
	

}
