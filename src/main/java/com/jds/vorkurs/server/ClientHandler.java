package com.jds.vorkurs.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Random;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.google.gson.Gson;
import com.jds.vorkurs.shared.ConnectMessage;
import com.jds.vorkurs.shared.Message;
import com.jds.vorkurs.shared.Player;
import com.jds.vorkurs.shared.RegisterMessage;

import de.urs.game.schereSteinPapier.SendEnemyMessage;

public class ClientHandler extends Thread {
	private static final Logger LOGGER = LogManager.getLogger(ClientHandler.class);

	private Socket clientSocket;
	private BufferedReader in;
	private PrintWriter out;



	private HandlerAdapter serverGlue;

	public ClientHandler(Socket clientSocket, HandlerAdapter serverGlue) {
		this.clientSocket = clientSocket;
		this.serverGlue = serverGlue;
	}

	@Override
	public void run() {
		
		LOGGER.log(Level.INFO, "New Client detected on " + clientSocket.getInetAddress());
		try {
			out = new PrintWriter(clientSocket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			LOGGER.log(Level.INFO, "Channels setup done, start listening for messages from client");
			String message;
			while ((message = in.readLine()) != null) {
				LOGGER.info("Received message: " + message);
				Message generalMessage = new Gson().fromJson(message, Message.class);
				switch (generalMessage.getCommand()) {
				case CONNECT:
					ConnectMessage specifiedMessage = new Gson().fromJson(message, ConnectMessage.class);
					// Registering the user on the game server, getting a unique id
					String clientId = serverGlue.registerNewUser(specifiedMessage.getPlayer(), this);
					out.println(clientId);

					break;
				case CONNECTED:
					// Sending informations about lobbys, ping etc.
					
					
					break;
				case SENDGEGNER:
					SendEnemyMessage sem = new Gson().fromJson(message, SendEnemyMessage.class);
					Player feind = serverGlue.getEnemyPlayer(sem.getPlayer());
					if(feind == null) {
						System.err.println("Kein feind");
					}else {
						//Der Feind bin ich nicht
						sem.setPlayer(feind);
						out.println(sem);
					}
						
					break;
				case MESSAGE:
					
					
					break;
				case REGISTER:
					RegisterMessage rm = new Gson().fromJson(message, RegisterMessage.class);
					serverGlue.givePlayer(rm.getPlayer());
					
					break;
				case DISCONNECT:
					LOGGER.info("Received shutdown message, starting teardown for client");
					// close all
					in.close();
					out.close();
					clientSocket.close();
					return;
				default:
					break;
				}
			}
			return;
		} catch (IOException e) {
			LOGGER.error("There was an communication error", e);
		}
	}	
}
