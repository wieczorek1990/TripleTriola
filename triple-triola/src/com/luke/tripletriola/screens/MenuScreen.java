package com.luke.tripletriola.screens;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.luke.tripletriola.TripleTriola;

public class MenuScreen extends AbstractScreen {

	public MenuScreen(TripleTriola game) {
		super(game);
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);

		Skin skin = super.getSkin();
		Label singleDevice = new Label(Messages.getString("MenuScreen.singleDevice"), skin); //$NON-NLS-1$
		singleDevice.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.setScreen(game.getGameScreen());
				super.clicked(event, x, y);
			}
		});
		Label lanPlay = new Label(Messages.getString("MenuScreen.lanPlay"), skin); //$NON-NLS-1$
		lanPlay.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.setScreen(game.getConnectionScreen());
				super.clicked(event, x, y);
			}
		});
		Label options = new Label(Messages.getString("MenuScreen.options"), skin); //$NON-NLS-1$
		options.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.setScreen(game.getOptionsScreen());
				super.clicked(event, x, y);
			}
		});
		Table table = new Table();
		table.setSize(width, height);
		table.add(singleDevice);
		table.row();
		table.add(lanPlay);
		table.row();
		table.add(options);
		stage.addActor(table);
	}
}
