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
			kryo.register(Move.class);
			kryo.register(GameStartRequest.class);
			kryo.register(GameStartInfo.class);
			client.start();
			client.connect(5000, address, TripleTriola.TCP_PORT,
					TripleTriola.UDP_PORT);
			Move request = new Move(3, 2, 1);
			client.sendTCP(request);
		} catch (IOException e) {
			e.printStackTrace();
		}

		super.run();
	}
}
