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

import de.urs.game.schereSteinPapier.WillkommenMessage;

public class GameClient {

	private static final Logger LOGGER = LogManager.getLogger(GameClient.class);

	private Socket clientSocket;
	private PrintWriter out;
	private BufferedReader in;

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
		System.out.println("Willkommen bleim Client 0.1!");
		System.out.println("Wie heiﬂen Sie?");
		Scanner sc = new Scanner(System.in);
		String UserName = sc.nextLine();
		System.out.println("Hallo "+UserName+"!");
		
		
		GameClient client = new GameClient();
		addShutDownHook(client);
		client.startConnection("127.0.0.1", 8443);
		
		//Meinen Quelltext
		//Player initialisieren
		System.out.println("Player init");
		Player p = new Player();
		p.setName(UserName);
		ConnectMessage message = new ConnectMessage(p);
		String playerId = null;
		try {
			playerId = client.sendMessage(message, String.class);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		p.setId(playerId);
		
		//Wellcome message
		System.out.println("Willkommen");
		WillkommenMessage wm = new WillkommenMessage();
		WillkommenMessage a =null;
		try {
			a = client.sendMessage(wm, WillkommenMessage.class);
		}catch(IOException e) {
			e.printStackTrace();
		}
		System.out.println(a.getWillkommen());
		
		try {
			Thread.sleep(5*1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
