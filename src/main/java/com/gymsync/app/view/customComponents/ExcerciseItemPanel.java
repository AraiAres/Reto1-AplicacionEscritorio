package com.gymsync.app.view.customComponents;

import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.JLabel;
import javax.swing.BorderFactory;

import java.awt.Color;
import java.awt.Dimension;

import com.gymsync.app.config.UIConfig;
import com.gymsync.app.controllers.ControllerFactory;
import com.gymsync.app.controllers.WorkoutPanelController;
import com.gymsync.app.model.entities.Excercise;
import com.gymsync.app.view.PanelFactory;
import com.gymsync.app.view.frame.MainFrame;
import com.gymsync.app.view.utils.SessionManager;

public class ExcerciseItemPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private JLabel nameLabel = null;
	private JLabel completedLabel = null;
	private JLabel descriptionLabel = null;
	private JTextArea descriptionTextArea = null;
	private ArrowButton openButton = null;

	public ExcerciseItemPanel(Excercise excercise, MainFrame mainFrame) {

		setPreferredSize(new Dimension(700, 120));
		setMaximumSize(new Dimension(700, 120));
		setBackground(UIConfig.PRIMARY_DARK);
		setBorder(BorderFactory.createLineBorder(UIConfig.EMIRALD_DARK, 2));
		setLayout(null);

		nameLabel = new JLabel(excercise.getName());
		nameLabel.setBounds(275, 5, 150, 25);
		nameLabel.setFont(UIConfig.WORKOUT_TITLE_FONT);
		nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		nameLabel.setVerticalAlignment(SwingConstants.CENTER);
		nameLabel.setForeground(Color.WHITE);

		completedLabel = new JLabel("Completed");
		completedLabel.setBounds(20, 5, 150, 20);
		completedLabel.setFont(UIConfig.LABEL_FONT);
		completedLabel.setVerticalAlignment(SwingConstants.CENTER);
		completedLabel.setForeground(Color.RED);

		descriptionLabel = new JLabel("Descripción: ");
		descriptionLabel.setBounds(10, 25, 150, 25);
		descriptionLabel.setFont(UIConfig.LABEL_FONT);
		descriptionLabel.setForeground(UIConfig.EMIRALD);

		descriptionTextArea = new JTextArea(excercise.getDescription());
		descriptionTextArea.setBounds(15, 55, 650, 55);
		descriptionTextArea.setOpaque(false);
		descriptionTextArea.setLineWrap(true);
		descriptionTextArea.setWrapStyleWord(true);
		descriptionTextArea.setFont(UIConfig.LABEL_FONT);
		descriptionTextArea.setForeground(UIConfig.EMIRALD);

		openButton = new ArrowButton(UIConfig.PRIMARY_GRADIENT);
		openButton.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
		openButton.setFont(UIConfig.INPUT_FONT);
		openButton.setBounds(660, 80, 40, 40);
		openButton.addActionListener(e -> {
			SessionManager.getInstance().setCurrentExcercise(excercise);
			mainFrame.showPanel(PanelFactory.getInstance().getPanel("excercisePanel", mainFrame));
		});

		WorkoutPanelController workoutPanelController = (WorkoutPanelController) ControllerFactory.getInstance()
				.getController("workoutPanelController");
		add(nameLabel);
		if (workoutPanelController.isCompleted(excercise)) {
			add(completedLabel);
		}
		add(descriptionLabel);
		add(descriptionTextArea);
		add(openButton);
	}

}
