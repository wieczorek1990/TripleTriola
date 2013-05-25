package com.luke.tripletriola.networking;

import java.net.InetAddress;
import java.util.List;

import com.esotericsoftware.kryonet.Client;
import com.luke.tripletriola.TripleTriola;

public class Discovery extends Thread {

	public static List<InetAddress> discover() {
		Client client = new Client();
		client.start();
		List<InetAddress> addresses = client.discoverHosts(
				TripleTriola.UDP_PORT, 1000);
		return addresses;
	}
}
