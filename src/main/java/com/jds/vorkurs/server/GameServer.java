package com.jds.vorkurs.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

public class GameServer implements Server {

	private ServerSocket serverSocket;
	private List<ClientHandler> clientHandler = new ArrayList<>();

	@Override
	public void start(int port) {
		System.out.println("Starting Server on port: " + port);
		try {
			serverSocket = new ServerSocket(port);
			System.out.println("Server up, start listening for clients");
			while (true) {
				ClientHandler handler = new ClientHandler(serverSocket.accept());
				handler.start();
				clientHandler.add(handler);
			}
		} catch (IOException e) {
			System.err.println("Could not start server " + e.getMessage());
		} finally {
			stop();
		}

	}

	@Override
	public void stop() {
		try {
			serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

//	public static void main(String[] args) {
//		new GameServer().start(8443);
//	}

}
