package com.jds.vorkurs.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;
import com.jds.vorkurs.shared.ConnectMessage;
import com.jds.vorkurs.shared.Message;
import com.jds.vorkurs.shared.Player;

import de.urs.game.schereSteinPapier.ChooseMessage;
import de.urs.game.schereSteinPapier.Spiel;


public class GameClient {

	private static final Logger LOGGER = LogManager.getLogger(GameClient.class);

	private Socket clientSocket;
	private PrintWriter out;
	private BufferedReader in;
	//private static Spiel spiel;

	public void startConnection(String ip, int port) {
		try {
			clientSocket = new Socket(ip, port);
			out = new PrintWriter(clientSocket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		} catch (IOException e) {
			LOGGER.error("Error while setting up the communication", e);
		}
	}

	public <T> T sendMessage(Message msg, Class<T> returnType) throws IOException {
		out.println(new Gson().toJson(msg));
		return new Gson().fromJson(in.readLine(), returnType);
	}

	public void sendCommand(Message message) {
		out.println(new Gson().toJson(message));
	}

	public void stopConnection() throws IOException {
		in.close();
		out.close();
		clientSocket.close();
	}

	public static void main(String[] args) {
		System.out.println("Willkommen bleim Client 0.2!");
		Spiel spiel = new Spiel();
		spiel.initPlayer();
		spiel.inputName();
		
		GameClient client = new GameClient();
		addShutDownHook(client);
		client.startConnection("127.0.0.1", 8443);

		ConnectMessage message = new ConnectMessage(spiel.getpMensch());
		String playerId = null;
		try {
			
			playerId = client.sendMessage(message, String.class);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			//System.exit(1);
		}
		spiel.getpMensch().setId(playerId);
		
		spiel.WelcomeMessage();
		spiel.HumanInput();
		
		ChooseMessage cm = new ChooseMessage();
		int Gegner = 0;
		try {
			Gegner = client.sendMessage(cm, Integer.class);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		spiel.enemy(Gegner);
		spiel.pickWinner();
		System.err.println("Nichts gesendet");

	}

	private static void addShutDownHook(GameClient client) {
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				try {
					client.stopConnection();
				} catch (IOException e) {
					LOGGER.error("Could not close client connection", e);
				}
			}
		});
	}
}
