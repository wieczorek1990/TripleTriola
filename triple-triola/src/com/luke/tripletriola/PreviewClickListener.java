package com.luke.tripletriola;

import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.luke.tripletriola.domain.PlayerColor;

public class PreviewClickListener extends ClickListener {
	protected PlayerColor side;

	public PreviewClickListener(PlayerColor side) {
		this.side = side;
	}
}
