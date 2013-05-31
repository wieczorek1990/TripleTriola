package com.luke.tripletriola.networking;

import java.io.IOException;
import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.luke.tripletriola.GameType;
import com.luke.tripletriola.TripleTriola;
import com.luke.tripletriola.domain.Board;
import com.luke.tripletriola.screens.GameScreen;

public class ServerThread extends Thread implements MoveSender {
	String gameName;
	TripleTriola game;
	Connection clientConnection;
	GameScreen gameScreen;

	public ServerThread(TripleTriola game, String gameName) {
		this.game = game;
		this.gameName = gameName;
	}

	@Override
	public void run() {
		try {
			Server server = new Server();
			server.addListener(new Listener() {
				@Override
				public void received(final Connection connection,
						final Object object) {
					if (object instanceof GameInfo) {
						GameInfo response = (GameInfo) object;
						response.gameName = gameName;
						connection.sendTCP(response);
					}
					if (object instanceof GameStart) {
						clientConnection = connection;
						Gdx.app.postRunnable(new Runnable() {
							@Override
							public void run() {
								GameStart response = (GameStart) object;
								ArrayList<Integer> cards = Board.prepareCards();
								response.cards = cards;
								connection.sendTCP(response);
								Screen gs = game.getGameScreen(
										GameType.MULTI_SERVER, response.cards);
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
				}
			});
			Kryo kryo = server.getKryo();
			kryo.register(GameInfo.class);
			kryo.register(byte[].class);
			kryo.register(GameStart.class);
			kryo.register(java.util.ArrayList.class);
			kryo.register(Move.class);
			server.start();
			server.bind(TripleTriola.TCP_PORT, TripleTriola.UDP_PORT);
		} catch (IOException e) {
			e.printStackTrace();
		}
		super.run();
	}

	@Override
	public void move(Move move) {
		clientConnection.sendTCP(move);
	}
}
