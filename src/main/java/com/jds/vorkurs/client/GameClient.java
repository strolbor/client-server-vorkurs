package com.jds.vorkurs.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

import com.google.gson.Gson;
import com.jds.vorkurs.shared.Command;
import com.jds.vorkurs.shared.Message;

public class GameClient {

	private Socket clientSocket;
	private PrintWriter out;
	private BufferedReader in;

	public void startConnection(String ip, int port) {
		try {
			clientSocket = new Socket(ip, port);
			out = new PrintWriter(clientSocket.getOutputStream(),true,StandardCharsets.UTF_8);
			in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Message sendMessage(Message msg) throws IOException {
		out.println(new Gson().toJson(msg));
		return new Gson().fromJson(in.readLine(), Message.class);
	}

	public void stopConnection() throws IOException {
		in.close();
		out.close();
		clientSocket.close();
	}

	public static void main(String[] args) {
		GameClient client = new GameClient();
		addShutDownHook(client);
		client.startConnection("127.0.0.1", 8443);
		try {
			Message response = client.sendMessage(new Message(Command.MESSAGE, "Message"));
			System.out.println(response);
		} catch (IOException e) {
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
					System.err.println("Could not close client connection");
				}
			}
		});
	}
}