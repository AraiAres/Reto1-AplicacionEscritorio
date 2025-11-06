package com.gymsync.app.view.customComponents;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import com.gymsync.app.config.UIConfig;

public class ImagePanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private BufferedImage image = null;

	public ImagePanel(String imageUrl, int size) {
		setPreferredSize(new Dimension(size, size));
		setMaximumSize(new Dimension(size, size));
		setMinimumSize(new Dimension(size, size));
		setOpaque(false);
		loadImage(imageUrl);
	}

	private void loadImage(String imageUrl) {
		image = loadFromPath(imageUrl);

		if (image == null) {
			image = loadFromPath(UIConfig.FALLBACK_ICON_PATH);
		}
	}

	private BufferedImage loadFromPath(String path) {
		if (path == null || path.isEmpty())
			return null;

		try {
			if (path.startsWith("http://") || path.startsWith("https://")) {
				return ImageIO.read(new URL(path));
			}

			File file = new File(path);
			if (file.exists()) {
				return ImageIO.read(file);
			}

			URL resource = getClass().getResource(path.startsWith("/") ? path : "/" + path);
			if (resource != null) {
				return ImageIO.read(resource);
			} else {
			}

		} catch (IOException e) {
		}
		return null;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (image == null)
			return;

		Graphics2D g2d = (Graphics2D) g.create();
		g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

		int panelWidth = getWidth();
		int panelHeight = getHeight();

		double imgAspect = (double) image.getWidth() / image.getHeight();
		double panelAspect = (double) panelWidth / panelHeight;

		int drawWidth, drawHeight;
		if (imgAspect > panelAspect) {
			drawWidth = panelWidth;
			drawHeight = (int) (panelWidth / imgAspect);
		} else {
			drawHeight = panelHeight;
			drawWidth = (int) (panelHeight * imgAspect);
		}

		int x = (panelWidth - drawWidth) / 2;
		int y = (panelHeight - drawHeight) / 2;

		g2d.drawImage(image, x, y, drawWidth, drawHeight, this);
		g2d.dispose();
	}
}
