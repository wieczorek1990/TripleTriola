package com.luke.tripletriola.networking;

import java.io.IOException;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.luke.tripletriola.TripleTriola;

public class ServerThread extends Thread {
	@Override
	public void run() {
		try {
			System.out.println("Server thread started."); //$NON-NLS-1$
			Server server = new Server();
			server.addListener(new Listener() {
				public void received(Connection connection, Object object) {
					System.out.println("Received object."); //$NON-NLS-1$
					if (object instanceof Move) {
						Move request = (Move) object;
						System.out.println(request);

						Move response = new Move(1, 2, 3);
						connection.sendTCP(response);
					}
					if (object instanceof GameStartRequest) {
						GameStartInfo response = new GameStartInfo();
						connection.sendTCP(response);
					}
				}
			});
			Kryo kryo = server.getKryo();
			kryo.register(Move.class);
			kryo.register(GameStartRequest.class);
			kryo.register(GameStartInfo.class);
			server.start();
			server.bind(TripleTriola.TCP_PORT, TripleTriola.UDP_PORT);
		} catch (IOException e) {
			e.printStackTrace();
		}
		super.run();
	}
}
