package com.luke.tripletriola.networking;

import java.io.IOException;

import com.badlogic.gdx.Gdx;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.luke.tripletriola.TripleTriola;

public class ServerThread extends Thread {
	String gameName;
	TripleTriola game;

	public ServerThread(TripleTriola game, String gameName) {
		this.game = game;
		this.gameName = gameName;
	}

	@Override
	public void run() {
		try {
			Server server = new Server();
			server.addListener(new Listener() {
				public void received(Connection connection, Object object) {
					if (object instanceof GameInfo) {
						GameInfo response = (GameInfo) object;
						response.gameName = gameName;
						connection.sendTCP(response);
					}
					if (object instanceof GameStart) {
						Gdx.app.postRunnable(new Runnable() {
							public void run() {
								game.setScreen(game.getGameScreen());
							};
						});
					}
				}
			});
			Kryo kryo = server.getKryo();
			kryo.register(GameInfo.class);
			kryo.register(byte[].class);
			kryo.register(GameStart.class);
			kryo.register(int[].class);
			server.start();
			server.bind(TripleTriola.TCP_PORT, TripleTriola.UDP_PORT);
		} catch (IOException e) {
			e.printStackTrace();
		}
		super.run();
	}
}
