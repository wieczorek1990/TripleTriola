package com.luke.tripletriola.networking;

import java.io.IOException;
import java.net.InetAddress;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.luke.tripletriola.TripleTriola;

public class ClientThread extends Thread {
	InetAddress address;

	public ClientThread(InetAddress address) {
		this.address = address;
	}

	@Override
	public void run() {
		try {
			Client client = new Client();
			Kryo kryo = client.getKryo();
			kryo.register(GameInfo.class);
			kryo.register(byte[].class);
			kryo.register(GameStart.class);
			kryo.register(int[].class);
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
