package com.luke.tripletriola.networking;

import java.io.IOException;
import java.net.InetAddress;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.luke.tripletriola.GameType;
import com.luke.tripletriola.TripleTriola;
import com.luke.tripletriola.screens.GameScreen;

public class ClientThread extends Thread implements MoveSender {
	TripleTriola game;
	InetAddress address;
	Client client;
	GameScreen gameScreen;

	public ClientThread(TripleTriola game, InetAddress address) {
		this.game = game;
		this.address = address;
	}

	@Override
	public void run() {
		try {
			client = new Client();
			client.addListener(new Listener() {
				@Override
				public void received(Connection connection, final Object object) {
					if (object instanceof GameStart) {
						final GameStart response = (GameStart) object;
						Gdx.app.postRunnable(new Runnable() {
							@Override
							public void run() {
								Screen gs = game.getGameScreen(
										GameType.MULTI_CLIENT, response.cards);
								gameScreen = (GameScreen) gs;
								game.setScreen(gs);
							};
						});
					}
					if (object instanceof Move) {
						Gdx.app.postRunnable(new Runnable() {
							@Override
							public void run() {
								Move m = (Move) object;
								gameScreen.placeCard(m.cardNubmer, m.row,
										m.col, false);
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
			kryo.register(Move.class);
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

	@Override
	public void move(Move move) {
		client.sendTCP(move);
	}
}
