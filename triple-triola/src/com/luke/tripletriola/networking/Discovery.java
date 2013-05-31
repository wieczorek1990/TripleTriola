package com.luke.tripletriola.networking;

import java.io.IOException;
import java.net.InetAddress;
import java.util.List;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.luke.tripletriola.TripleTriola;
import com.luke.tripletriola.screens.JoinGameScreen;

public class Discovery extends Thread {

	public static void discover(final JoinGameScreen joinGameScreen) {
		Client client = new Client();
		client.addListener(new Listener() {
			@Override
			public void received(Connection connection, Object object) {
				if (object instanceof GameInfo) {
					GameInfo gameInfo = (GameInfo) object;
					joinGameScreen.addGameLabel(gameInfo);
				}
				super.received(connection, object);
			}
		});
		Kryo kryo = client.getKryo();
		kryo.register(GameInfo.class);
		kryo.register(byte[].class);
		client.start();
		List<InetAddress> addresses = client.discoverHosts(
				TripleTriola.UDP_PORT, 1000);
		for (InetAddress address : addresses) {
			try {
				client.connect(5000, address, TripleTriola.TCP_PORT,
						TripleTriola.UDP_PORT);
			} catch (IOException e) {
				e.printStackTrace();
			}
			GameInfo request = new GameInfo();
			request.address = address.getAddress();
			client.sendTCP(request);
		}
	}
}
