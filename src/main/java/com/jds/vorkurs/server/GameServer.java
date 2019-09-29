package com.jds.vorkurs.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.jds.vorkurs.shared.Player;

public class GameServer implements Server, Glue {
	private static final Logger LOGGER = LogManager.getLogger(GameServer.class);
	private ServerSocket serverSocket;

	private Map<Player, ClientHandler> clients = new HashMap<Player, ClientHandler>();

	@Override
	public void start(int port) {
		LOGGER.info("Starting Server on port: " + port);
		try {
			serverSocket = new ServerSocket(port);
			LOGGER.info("Server up, start listening for clients");
			while (true) {
				ClientHandler handler = new ClientHandler(serverSocket.accept(), this);
				handler.run();
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
}
