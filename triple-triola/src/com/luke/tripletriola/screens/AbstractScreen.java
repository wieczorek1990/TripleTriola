package com.luke.tripletriola.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.luke.tripletriola.TripleTriola;

// TODO lazyload (if null x = new X; return x)
public class AbstractScreen implements Screen {
	protected TripleTriola game;
	protected Stage stage;

	public AbstractScreen(TripleTriola game) {
		this.game = game;
		this.stage = new Stage(0, 0, true);
		Gdx.input.setInputProcessor(stage);
	}

	protected Skin getSkin() {
		return new Skin(Gdx.files.internal("skin/uiskin.json")); //$NON-NLS-1$
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		stage.act(delta);
		stage.draw();
	}

	@Override
	public void dispose() {
		stage.dispose();
	}

	@Override
	public void resize(int width, int height) {
		stage.setViewport(width, height, true);
	}

	@Override
	public void show() {
	}

	@Override
	public void hide() {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

}
