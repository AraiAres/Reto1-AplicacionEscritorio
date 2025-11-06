package com.gymsync.app.view.customComponents;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.gymsync.app.concurrency.threads.RegressiveCronometer;
import com.gymsync.app.config.UIConfig;
import com.gymsync.app.model.entities.Series;

public class SeriesItemPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private ImagePanel icon = null;
	private JLabel nameLabel = null;
	private JLabel repeticionLabel = null;
	private JLabel completedLabel = null;
	private CronometerLabel seriesTimerLabel = null;

	public SeriesItemPanel(Series series, RegressiveCronometer crono) {
		setPreferredSize(new Dimension(800, 150));
		setMaximumSize(new Dimension(Integer.MAX_VALUE, 150));
		setBackground(UIConfig.PRIMARY_DARK);
		setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(UIConfig.EMIRALD_DARK, 2),
				BorderFactory.createEmptyBorder(10, 10, 10, 10)));
		setLayout(null);

		icon = new ImagePanel(series.getIcon(), 140);
		icon.setBounds(10, 10, 130, 130);

		nameLabel = new JLabel(series.getName());
		nameLabel.setBounds(200, 10, 350, 30);
		nameLabel.setFont(UIConfig.WORKOUT_TITLE_FONT);
		nameLabel.setForeground(Color.WHITE);

		repeticionLabel = new JLabel("Repeticiones: " + series.getRepetitionCount());
		repeticionLabel.setBounds(200, 50, 350, 30);
		repeticionLabel.setFont(UIConfig.INPUT_FONT);
		repeticionLabel.setForeground(UIConfig.EMIRALD);

		completedLabel = new JLabel("Completado");
		completedLabel.setBounds(200, 90, 350, 30);
		completedLabel.setFont(UIConfig.INPUT_FONT);
		completedLabel.setForeground(Color.RED);
		completedLabel.setVisible(false);

		seriesTimerLabel = new CronometerLabel();
		seriesTimerLabel.setForeground(Color.WHITE);
		seriesTimerLabel.setBounds(550, 50, 200, 50);

		add(icon);
		add(nameLabel);
		add(repeticionLabel);
		add(completedLabel);
		add(seriesTimerLabel);
	}

	public void completed() {
		completedLabel.setVisible(true);
		repaint();
		revalidate();
	}

	public CronometerLabel getCronometerLabel() {
		return seriesTimerLabel;
	}

}
