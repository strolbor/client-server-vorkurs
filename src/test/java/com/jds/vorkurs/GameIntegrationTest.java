package com.jds.vorkurs;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.Executors;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.jds.vorkurs.client.GameClient;
import com.jds.vorkurs.server.GameServer;
import com.jds.vorkurs.shared.Command;
import com.jds.vorkurs.shared.Message;
import static org.junit.Assert.assertEquals;

public class GameIntegrationTest {
	private static int port;
	private GameClient gameClient;

	@BeforeClass
	public static void start() throws InterruptedException, IOException {

		// Take an available port
		ServerSocket s = new ServerSocket(0);
		port = s.getLocalPort();
		s.close();

		Executors.newSingleThreadExecutor().submit(() -> {
			new GameServer().start(port);
		});
		Thread.sleep(500);
	}

	@Before
	public void before() {
		gameClient = new GameClient();
		gameClient.startConnection("127.0.0.1", port);
	}

	@Test
	public void givenClient_whenServerResponds_thenCorrect() throws IOException {
		Message msg1 = gameClient.sendMessage(new Message(Command.CONNECT, "Connect"));
		Message msg2 = gameClient.sendMessage(new Message(Command.CONNECT, "Connect to World"));
		gameClient.sendMessage(new Message(Command.DISCONNECT, ""));

		assertEquals(Command.CONNECT, msg1.getCommand());
		assertEquals("Connect", msg1.getMessage());
		assertEquals(Command.CONNECT, msg2.getCommand());
		assertEquals("Connect to World", msg2.getMessage());

	}

	@After
	public void tearDown() throws IOException {
		gameClient.stopConnection();
	}

}
