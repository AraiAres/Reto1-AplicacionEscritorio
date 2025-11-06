package com.gymsync.app.view.customComponents;

import javax.swing.JButton;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Arc2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

public class SignoutButton extends JButton {

    private static final long serialVersionUID = 1L;

    private final Color[] baseGradient;
    private final Color[] hoverGradient;
    private final Color[] pressedGradient;
    private Color[] currentGradient;
    private boolean isPressed = false;

    public SignoutButton(Color[] gradientColors) {
        super();

        this.baseGradient = gradientColors;
        this.hoverGradient = adjustBrightness(gradientColors, 1.15f);
        this.pressedGradient = adjustBrightness(gradientColors, 0.85f);
        this.currentGradient = baseGradient;

        setPreferredSize(new Dimension(50, 50));
        setMinimumSize(new Dimension(50, 50));
        setMaximumSize(new Dimension(50, 50));

        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (!isPressed)
                    currentGradient = hoverGradient;
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (!isPressed)
                    currentGradient = baseGradient;
                repaint();
            }

            @Override
            public void mousePressed(MouseEvent e) {
                isPressed = true;
                currentGradient = pressedGradient;
                repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                isPressed = false;
                currentGradient = hoverGradient;
                repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int width = getWidth();
        int height = getHeight();
        float[] fractions = {0f, 0.5f, 1f};

        LinearGradientPaint gradient = new LinearGradientPaint(0, 0, width, 0, fractions, currentGradient);
        g2d.setPaint(gradient);
        g2d.fill(new Rectangle2D.Double(0, 0, width, height));

        if (!isPressed) {
            g2d.setColor(new Color(0, 0, 0, 40));
            g2d.drawLine(0, height - 1, width, height - 1);
        }

        if (isPressed) g2d.translate(0, 2);

        g2d.setStroke(new BasicStroke(3.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2d.setColor(Color.WHITE);

        int cx = width / 2;
        int cy = height / 2;
        int r = Math.min(width, height) / 4;

        g2d.draw(new Arc2D.Double(cx - r, cy - r, 2 * r, 2 * r, 135, 270, Arc2D.OPEN));
        g2d.draw(new Line2D.Double(cx, cy - r - 4, cx, cy - r / 2));
        g2d.dispose();
    }

    private Color[] adjustBrightness(Color[] colors, float factor) {
        Color[] adjusted = new Color[colors.length];
        for (int i = 0; i < colors.length; i++) {
            adjusted[i] = new Color(
                Math.min((int) (colors[i].getRed() * factor), 255),
                Math.min((int) (colors[i].getGreen() * factor), 255),
                Math.min((int) (colors[i].getBlue() * factor), 255)
            );
        }
        return adjusted;
    }
}
