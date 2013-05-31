package com.luke.tripletriola.screens;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.luke.tripletriola.GameLabel;
import com.luke.tripletriola.TripleTriola;
import com.luke.tripletriola.networking.ClientThread;
import com.luke.tripletriola.networking.Discovery;
import com.luke.tripletriola.networking.GameInfo;

public class JoinGameScreen extends AbstractScreen {
	Table table;
	Label back;
	ArrayList<Label> labels;

	public JoinGameScreen(TripleTriola game) {
		super(game);
		labels = new ArrayList<Label>();
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		Skin skin = super.getSkin();
		back = new Label(Messages.getString("goBack"), skin); //$NON-NLS-1$
		back.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.setScreen(game.getConnectionScreen());
				super.clicked(event, x, y);
			}
		});
		table = new Table();
		table.setSize(width, height);
		Label noGamesFound = new Label(
				Messages.getString("JoinGameScreen.noGamesFound"), skin); //$NON-NLS-1$
		table.add(noGamesFound);
		table.row();
		table.add(back);
		stage.addActor(table);
		Discovery.discover(this);
	}

	public void addGameLabel(GameInfo gameInfo) {
		Skin skin = super.getSkin();
		InetAddress address = null;
		try {
			address = InetAddress.getByAddress(gameInfo.address);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		final Label label = new GameLabel(gameInfo.gameName, skin, address);
		label.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				GameLabel lbl = (GameLabel) label;
				ClientThread clientThread = new ClientThread(lbl.getAddress());
				game.setClientThread(clientThread);
				clientThread.start();
				game.setScreen(game.getGameScreen());
				super.clicked(event, x, y);
			}
		});
		labels.add(label);
		table.clear();
		for (Label lbl : labels) {
			table.add(lbl);
			table.row();
		}
		table.add(back);
		table.row();
	}
}
