package com.luke.tripletriola;

import java.net.InetAddress;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.luke.tripletriola.networking.ClientThread;
import com.luke.tripletriola.networking.ServerThread;
import com.luke.tripletriola.screens.ConnectionScreen;
import com.luke.tripletriola.screens.GameScreen;
import com.luke.tripletriola.screens.HostGameScreen;
import com.luke.tripletriola.screens.JoinGameScreen;
import com.luke.tripletriola.screens.MenuScreen;
import com.luke.tripletriola.screens.OptionsScreen;
import com.luke.tripletriola.screens.SplashScreen;

public class TripleTriola extends Game {
	public OrthographicCamera camera;
	public static final String LOG = TripleTriola.class.getSimpleName();
	public static final boolean DEBUG = true;
	public static final int TCP_PORT = 54555;
	public static final int UDP_PORT = 54777;
	ServerThread serverThread;
	ClientThread clientThread;

	@Override
	public void create() {
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();

		Texture.setEnforcePotImages(false);

		camera = new OrthographicCamera(w, h);

		if (!DEBUG)
			setScreen(getSplashScreen());
		else
			setScreen(getGameScreen());
	}

	public Screen getConnectionScreen() {
		return new ConnectionScreen(this);
	}

	public Screen getGameScreen() {
		return new GameScreen(this);
	}

	public Screen getHostGameScreen() {
		return new HostGameScreen(this);
	}

	public Screen getJoinGameScreen() {
		return new JoinGameScreen(this);
	}

	public Screen getMenuScreen() {
		return new MenuScreen(this);
	}

	public Screen getOptionsScreen() {
		return new OptionsScreen(this);
	}

	private Screen getSplashScreen() {
		return new SplashScreen(this);
	}

	@SuppressWarnings("nls")
	@Override
	public void setScreen(Screen screen) {
		super.setScreen(screen);
		Gdx.app.log(TripleTriola.LOG, "Setting screen: "
				+ screen.getClass().getSimpleName());
	}

	public ServerThread getServerThread() {
		if (serverThread == null) {
			serverThread = new ServerThread();
		}
		return serverThread;
	}

	public ClientThread getClientThread(InetAddress inetAddress) {
		if (clientThread == null) {
			clientThread = new ClientThread(inetAddress);
		}
		return clientThread;
	}
}