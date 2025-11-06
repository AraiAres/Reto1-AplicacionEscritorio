package com.gymsync.app.view.customComponents;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;

import javax.swing.*;

import com.gymsync.app.config.UIConfig;

public class RoundedCornersPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	public RoundedCornersPanel() {
		setOpaque(false);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2 = (Graphics2D) g.create();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		int width = getWidth();
		int height = getHeight();

		Shape roundRect = new RoundRectangle2D.Double(0, 0, width - 1, height - 1, 50, 50);

		g2.setColor(UIConfig.PRIMARY_DARK);
		g2.fill(roundRect);

		g2.setColor(new Color(255, 255, 255, 60));
		g2.draw(roundRect);

		g2.dispose();
	}
}
