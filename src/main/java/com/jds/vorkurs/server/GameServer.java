package com.jds.vorkurs.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.jds.vorkurs.shared.Player;

public class GameServer implements Server, HandlerAdapter {
	private static final Logger LOGGER = LogManager.getLogger(GameServer.class);
	private ServerSocket serverSocket;

	private Map<Player, ClientHandler> clients = new HashMap<Player, ClientHandler>();
	Player p1=null,p2=null;

	@Override
	public void start(int port) {
		LOGGER.info("Starting Server on port: " + port);
		try {
			serverSocket = new ServerSocket(port);
			LOGGER.info("Server up, start listening for clients");
			while (true) {
				ClientHandler handler = new ClientHandler(serverSocket.accept(), this);
				handler.start();
			}
		} catch (IOException e) {
			LOGGER.error("Could not start server " + e.getMessage());
		} finally {
			stop();
		}
	}

	@Override
	public void stop() {
		try {
			serverSocket.close();
		} catch (IOException e) {
			LOGGER.warn("Error while closing the server connection", e);
		}
	}

	public static void main(String[] args) {
		new GameServer().start(8443);
	}

	@Override
	public String registerNewUser(Player player, ClientHandler clientHandler) {
		String clientId = UUID.randomUUID().toString();
		player.setId(clientId);
		clients.put(player, clientHandler);
		return clientId;
	}

	@Override
	public void unregisterUser(Player player) {
		clients.remove(player);
	}

	public Player getP1() {
		return p1;
	}

	public void setP1(Player p1) {
		this.p1 = p1;
	}

	public Player getP2() {
		return p2;
	}

	public void setP2(Player p2) {
		this.p2 = p2;
	}

	
	public void givePlayer(Player player) {
		if(p1 == null) {
			p1 = player;
		}else if(p2 == null && p1 !=null) {
			p2 = player;
		}
	}

	@Override
	public Player getEnemyPlayer(Player player) {
		if(player == p1) {
			return p2;
		}else if(player == p2) {
			return p1;
		}else {
			return null;	
		}
	}
}
