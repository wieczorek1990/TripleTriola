package com.luke.tripletriola.screens;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.luke.tripletriola.TripleTriola;

public class ConnectionScreen extends AbstractScreen {

	public ConnectionScreen(TripleTriola game) {
		super(game);
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);

		Skin skin = super.getSkin();
		Label joinGame = new Label(Messages.getString("ConnectionScreen.joinGame"), skin); //$NON-NLS-1$
		joinGame.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.setScreen(game.getJoinGameScreen());
				super.clicked(event, x, y);
			}
		});		
		Label hostGame = new Label(Messages.getString("ConnectionScreen.hostGame"), skin); //$NON-NLS-1$
		hostGame.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.setScreen(game.getHostGameScreen());
				super.clicked(event, x, y);
			}
		});
		Label back = new Label(Messages.getString("goBack"), skin); //$NON-NLS-1$
		back.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.setScreen(game.getMenuScreen());
				super.clicked(event, x, y);
			}
		});
		Table table = new Table();
		table.setSize(width, height);
		table.add(joinGame);
		table.row();
		table.add(hostGame);
		table.row();
		table.add(back);
		stage.addActor(table);
	}
}
