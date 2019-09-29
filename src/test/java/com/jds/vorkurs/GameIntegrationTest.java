package com.jds.vorkurs;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.Executors;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.jds.vorkurs.client.GameClient;
import com.jds.vorkurs.server.GameServer;
import com.jds.vorkurs.shared.ConnectMessage;
import com.jds.vorkurs.shared.DisconnectMessage;
import com.jds.vorkurs.shared.Player;

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
	public void givenClient_whenPlayerConnect_thenCorrectClientId() throws IOException {
		Player player = new Player();
		player.setName("Hans Peter");
		ConnectMessage message = new ConnectMessage(player);
		String playerId = gameClient.sendMessage(message, String.class);
		player.setId(playerId);
		assertTrue(!playerId.isBlank());
	}

	@After
	public void tearDown() throws IOException {
		gameClient.sendCommand(new DisconnectMessage());
		gameClient.stopConnection();
	}

}
