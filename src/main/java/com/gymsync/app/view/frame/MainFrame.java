package com.gymsync.app.view.frame;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import com.gymsync.app.config.UIConfig;
import com.gymsync.app.view.PanelFactory;
import com.gymsync.app.view.panels.AbstractPanel;
import com.gymsync.app.view.panels.LoginPanel;

public class MainFrame extends JFrame {

	private static final long serialVersionUID = -5368545434453831333L;

	private AbstractPanel currentPanel = null;
	ImageIcon icon = null;

	public MainFrame() {
		initialize();
	}

	public void initialize() {
		setTitle(UIConfig.APP_NAME);
		setResizable(false);
		setSize(new Dimension(1200, 750));
		setLayout(new BorderLayout());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		ImageIcon icon = new ImageIcon(getClass().getResource("/images/appIcon.png"));
		setIconImage(icon.getImage());

		LoginPanel loginPanel = (LoginPanel) PanelFactory.getInstance().getPanel("loginPanel", this);
		showPanel(loginPanel);

		setVisible(true);
	}

	public void showPanel(AbstractPanel newPanel) {
		if (currentPanel != null) {
			remove(currentPanel);
		}
		currentPanel = newPanel;

		add(currentPanel, BorderLayout.CENTER);
		revalidate();
		repaint();
	}
}
