package com.luke.tripletriola.screens;

import java.net.InetAddress;
import java.util.List;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.luke.tripletriola.GameLabel;
import com.luke.tripletriola.TripleTriola;
import com.luke.tripletriola.networking.ClientThread;
import com.luke.tripletriola.networking.Discovery;

public class JoinGameScreen extends AbstractScreen {
	Discovery discovery;

	public JoinGameScreen(TripleTriola game) {
		super(game);
		this.discovery = new Discovery();
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
		Table table = new Table();
		table.setSize(width, height);
		List<InetAddress> addresses = Discovery.discover();
		for (InetAddress address : addresses) {
			final Label label = new GameLabel(address.toString(), skin, address);
			label.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					System.out.println("Joining game"); //$NON-NLS-1$
					GameLabel lbl = (GameLabel) label;
					ClientThread clientThread = game.getClientThread(lbl.getAddress());
					clientThread.start();
					super.clicked(event, x, y);
				}
			});
			table.add(label);
			table.row();
		}
		if (addresses.size() == 0) {
			Label noGamesFound = new Label(
					Messages.getString("JoinGameScreen.noGamesFound"), skin); //$NON-NLS-1$
			table.add(noGamesFound);
			table.row();
		}
		table.add(back);
		stage.addActor(table);
	}
}
