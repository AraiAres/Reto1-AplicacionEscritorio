package com.gymsync.app.view.customComponents;

import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.JLabel;
import javax.swing.BorderFactory;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import com.gymsync.app.config.UIConfig;
import com.gymsync.app.model.entities.Workout;
import com.gymsync.app.view.PanelFactory;
import com.gymsync.app.view.frame.MainFrame;
import com.gymsync.app.view.utils.SessionManager;

public class WorkoutItemPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private JLabel nameLabel = null;
	private JPanel infoPanel = null;
	private JLabel nExercisesLabel;
	private JLabel levelLabel = null;
	private JLabel videoLabel = null;
	private ArrowButton openButton = null;

	public WorkoutItemPanel(Workout workout, MainFrame mainFrame) {

		setPreferredSize(new Dimension(700, 110));
		setMaximumSize(new Dimension(700, 110));
		setBackground(UIConfig.PRIMARY_DARK);
		setBorder(BorderFactory.createLineBorder(UIConfig.EMIRALD_DARK, 2));
		setLayout(null);

		nameLabel = new JLabel(workout.getName());
		nameLabel.setBounds(275, 0, 150, 50);
		nameLabel.setFont(UIConfig.WORKOUT_TITLE_FONT);
		nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		nameLabel.setVerticalAlignment(SwingConstants.CENTER);
		nameLabel.setForeground(Color.WHITE);

		infoPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 0));
		infoPanel.setOpaque(false);
		infoPanel.setBounds(0, 50, 700, 40);

		levelLabel = new JLabel("Nivel: " + workout.getLevel());
		levelLabel.setFont(UIConfig.INPUT_FONT);
		levelLabel.setForeground(UIConfig.EMIRALD);

		nExercisesLabel = new JLabel("Ejercicios: " + workout.getNExcercises());
		nExercisesLabel.setFont(UIConfig.INPUT_FONT);
		nExercisesLabel.setForeground(UIConfig.EMIRALD);

		videoLabel = new JLabel("Video: " + workout.getUrlVideo());
		videoLabel.setFont(UIConfig.INPUT_FONT);
		videoLabel.setForeground(UIConfig.EMIRALD);

		infoPanel.add(levelLabel);
		infoPanel.add(nExercisesLabel);
		infoPanel.add(videoLabel);

		openButton = new ArrowButton(UIConfig.PRIMARY_GRADIENT);
		openButton.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
		openButton.setFont(UIConfig.INPUT_FONT);
		openButton.setBounds(660, 70, 40, 40);
		openButton.addActionListener(e -> {
			SessionManager.getInstance().setCurrentWorkout(workout);
			mainFrame.showPanel(PanelFactory.getInstance().getPanel("workoutPanel", mainFrame));
		});

		add(nameLabel);
		add(infoPanel);
		add(openButton);
	}

}
