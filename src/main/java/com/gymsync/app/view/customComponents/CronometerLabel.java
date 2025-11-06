package com.gymsync.app.view.customComponents;

import javax.swing.*;
import java.awt.*;
import com.gymsync.app.config.UIConfig;

public class CronometerLabel extends JLabel {

	private static final long serialVersionUID = 4582740424889004715L;

	private int h = 0;
	private int m = 0;
	private int s = 0;

	public CronometerLabel() {
		this(0, 0, 0);
	}

	public CronometerLabel(int h, int m, int s) {
		this.h = h;
		this.m = m;
		this.s = s;

		setFont(new Font("Monospaced", Font.BOLD, 32));
		setHorizontalAlignment(SwingConstants.CENTER);
		setForeground(Color.WHITE);
		setFont(UIConfig.TITLE_FONT);
		setOpaque(false);
		updateText();
	}

	private void updateText() {
		setText(String.format("%02d:%02d:%02d", h, m, s));
	}

	public void setTime(int h, int m, int s) {
		this.h = h;
		this.m = m;
		this.s = s;
		updateText();
	}

	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g.create();

		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		Color emeraldDark = UIConfig.EMIRALD_DARK;
		g2.setColor(emeraldDark);

		g2.fillRoundRect(0, 0, getWidth(), getHeight(), 25, 25);

		g2.dispose();
		super.paintComponent(g);
	}
}
