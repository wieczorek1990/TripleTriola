package com.luke.tripletriola;

import java.net.InetAddress;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class GameLabel extends Label {
	InetAddress address;

	public GameLabel(CharSequence text, Skin skin, InetAddress address) {
		super(text, skin);
		this.address = address;
	}

	public InetAddress getAddress() {
		return address;
	}

}
