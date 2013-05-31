package com.luke.tripletriola.screens;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.luke.tripletriola.TripleTriola;

public class OptionsScreen extends AbstractScreen {

	public OptionsScreen(TripleTriola game) {
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
				game.setScreen(game.getMenuScreen());
				super.clicked(event, x, y);
			}
		});
		Table table = new Table();
		table.setSize(width, height);
		table.add(back);
		stage.addActor(table);
	}

}
