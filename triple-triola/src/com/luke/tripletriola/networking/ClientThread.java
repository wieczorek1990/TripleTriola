package com.luke.tripletriola.networking;

import java.io.IOException;
import java.net.InetAddress;

import com.badlogic.gdx.Gdx;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.luke.tripletriola.GameType;
import com.luke.tripletriola.TripleTriola;

public class ClientThread extends Thread {
	TripleTriola game;
	InetAddress address;

	public ClientThread(TripleTriola game, InetAddress address) {
		this.game = game;
		this.address = address;
	}

	@Override
	public void run() {
		try {
			Client client = new Client();
			client.addListener(new Listener() {
				@Override
				public void received(Connection connection, Object object) {
					if (object instanceof GameStart) {
						final GameStart response = (GameStart) object;
						Gdx.app.postRunnable(new Runnable() {
							public void run() {
								game.setScreen(game
										.getGameScreen(GameType.MULTI_CLIENT, response.cards));
							};
						});
					}
					super.received(connection, object);
				}
			});
			Kryo kryo = client.getKryo();
			kryo.register(GameInfo.class);
			kryo.register(byte[].class);
			kryo.register(GameStart.class);
			kryo.register(java.util.ArrayList.class);
			client.start();
			client.connect(5000, address, TripleTriola.TCP_PORT,
					TripleTriola.UDP_PORT);
			GameStart request = new GameStart();
			client.sendTCP(request);
		} catch (IOException e) {
			e.printStackTrace();
		}

		super.run();
	}
}
