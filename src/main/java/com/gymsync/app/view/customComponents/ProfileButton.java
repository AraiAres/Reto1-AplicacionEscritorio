package com.gymsync.app.view.customComponents;

import javax.swing.JButton;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;

public class ProfileButton extends JButton {

	private static final long serialVersionUID = 640873024579514794L;

	public static final Color PRIMARY_DARK = new Color(0x16, 0x0C, 0x28);

	private int diameter = 50;

	
	public ProfileButton(int diameter) {
		this.diameter = diameter;
		setPreferredSize(new Dimension(diameter, diameter));
		setContentAreaFilled(false);
		setFocusPainted(false);
		setBorderPainted(false);
		setOpaque(false);
		setBackground(PRIMARY_DARK);
		setVisible(true);
	    setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	}

	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g.create();

		// Enable anti-aliasing for smooth edges
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		// Draw circular background
		g2.setColor(getBackground());
		g2.fill(new Ellipse2D.Double(0, 0, diameter, diameter));

		// Draw profile "head" circle
		g2.setColor(Color.WHITE);
		int headDiameter = (int) diameter / 2;
		int headX = (int) (diameter - headDiameter) / 2;
		int headY = (int) diameter / 8;
		g2.fillOval(headX, headY, headDiameter, headDiameter);

		// Draw profile "body" (semi-circle or oval)
		int bodyWidth = (int) (diameter / 1.3);
		int bodyHeight = (int) diameter / 2;
		int bodyX = (int) (diameter - bodyWidth) / 2;
		int bodyY = (int) diameter / 2;
		g2.fillOval(bodyX, bodyY, bodyWidth, bodyHeight);

		g2.dispose();

		super.paintComponent(g);
	}
}
