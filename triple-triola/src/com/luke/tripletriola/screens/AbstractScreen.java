package com.luke.tripletriola.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.luke.tripletriola.TripleTriola;

// TODO lazyload (if null x = new X; return x)
public class AbstractScreen implements Screen {
	protected TripleTriola game;
	protected SpriteBatch batch;
	protected ShapeRenderer shapeRenderer;
	protected Stage stage;

	public AbstractScreen(TripleTriola game) {
		this.game = game;
		this.batch = new SpriteBatch();
		this.shapeRenderer = new ShapeRenderer();
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

		game.camera.update();
		batch.setProjectionMatrix(game.camera.combined);
		shapeRenderer.setProjectionMatrix(game.camera.combined);

		stage.act(delta);
		stage.draw();
	}

	@Override
	public void dispose() {
		batch.dispose();
		stage.dispose();
	}

	@Override
	public void resize(int width, int height) {
		stage.setViewport(width, height, true);
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

}
