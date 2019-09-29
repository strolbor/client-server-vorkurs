package com.jds.vorkurs.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import com.google.gson.Gson;
import com.jds.vorkurs.shared.Message;

public class ClientHandler extends Thread {
	private Socket clientSocket;
	private BufferedReader in;
	private PrintWriter out;

	public ClientHandler(Socket clientSocket) {
		this.clientSocket = clientSocket;
	}

	@Override
	public void run() {
		System.out.println("New Client detected on " + clientSocket.getInetAddress().getHostAddress());
		try {
			out = new PrintWriter(clientSocket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			System.out.println("Channels setup done, start listening for messages from client");
			Message message;
			while ((message = new Gson().fromJson(in.readLine(), Message.class)) != null) {
				System.out.println("received message " + message);
				switch (message.getCommand()) {
				case CONNECT:
					out.println((new Gson().toJson(message)));
					break;
				case MESSAGE:
				case DISCONNECT:
					System.out.println("Shutting down all");
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
			e.printStackTrace();
		}
	}
}
