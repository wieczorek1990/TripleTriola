package com.luke.tripletriola.screens;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.luke.tripletriola.TripleTriola;

public class WaitScreen extends AbstractScreen {

	public WaitScreen(TripleTriola game) {
		super(game);
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);

		Skin skin = super.getSkin();
		Label waiting = new Label(
				Messages.getString("OptionsScreen.waiting"), skin); //$NON-NLS-1$
		Table table = new Table();
		table.setSize(width, height);
		table.add(waiting);
		stage.addActor(table);
	}
}
