package com.luke.tripletriola.screens;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.luke.tripletriola.TripleTriola;
import com.luke.tripletriola.networking.ServerThread;

public class HostGameScreen extends AbstractScreen {

	public HostGameScreen(TripleTriola game) {
		super(game);
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);

		Skin skin = super.getSkin();
		Label back = new Label(Messages.getString("goBack"), skin); //$NON-NLS-1$
		back.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.setScreen(game.getConnectionScreen());
				super.clicked(event, x, y);
			}
		});
		Label gameName = new Label(
				Messages.getString("HostGameScreen.gameName"), skin); //$NON-NLS-1$
		final TextField gameNameText = new TextField("TT", skin); //$NON-NLS-1$
		Label hostGame = new Label(
				Messages.getString("HostGameScreen.hostGame"), skin); //$NON-NLS-1$
		hostGame.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				ServerThread serverThread = new ServerThread(game, gameNameText
						.getText());
				game.setServerThread(serverThread);
				serverThread.start();
				game.setScreen(game.getWaitScreen());
				super.clicked(event, x, y);
			}
		});
		Table table = new Table();
		table.setSize(width, height);
		table.add(hostGame);
		table.row();
		table.add(gameName);
		table.add(gameNameText);
		table.row();
		table.add(back);
		stage.addActor(table);
	}
}
