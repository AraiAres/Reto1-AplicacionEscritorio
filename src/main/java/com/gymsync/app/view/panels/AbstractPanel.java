package com.gymsync.app.view.panels;

import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

import com.gymsync.app.config.UIConfig;

public abstract class AbstractPanel extends JPanel {

    private static final long serialVersionUID = 6048681586171933816L;
    private Image backgroundImage = null;

    protected abstract void initialize();

    public AbstractPanel() {
        setBounds(0, 0, 1200, 750);
        setLayout(null);

        try {
            URL imageUrl = getClass().getResource(UIConfig.BACKGROUND_IMAGE_PATH);
            Image originalImage = ImageIO.read(imageUrl);
            backgroundImage = originalImage.getScaledInstance(1200, 750, Image.SCALE_SMOOTH);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, this);
        }
    }
}
