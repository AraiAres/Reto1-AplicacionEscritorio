package com.gymsync.app.view.panels;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import com.gymsync.app.config.UIConfig;
import com.gymsync.app.model.entities.Workout;
import com.gymsync.app.view.PanelFactory;
import com.gymsync.app.view.customComponents.BackArrowButton;
import com.gymsync.app.view.customComponents.ExcerciseListPanel;
import com.gymsync.app.view.frame.MainFrame;

public class WorkoutPanel extends AbstractPanel {

	private static final long serialVersionUID = 2275990846113808471L;

	private MainFrame mainFrame = null;
	private Workout workout = null;

	private JLabel title = null;
	private BackArrowButton backButton = null;
	private ExcerciseListPanel excerciseListPanel = null;

	public WorkoutPanel(Workout workout, MainFrame mainFrame) {
		super();
		this.mainFrame = mainFrame;
		this.workout = workout;
		initialize();
	}

	@Override
	protected void initialize() {
		title = new JLabel(workout.getName());
		title.setBounds(450, 50, 300, 70);
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setVerticalAlignment(SwingConstants.CENTER);
		title.setBorder(BorderFactory.createLineBorder(UIConfig.EMIRALD_DARK, 2));
		title.setForeground(UIConfig.EMIRALD);
		title.setFont(UIConfig.TITLE_FONT);

		backButton = new BackArrowButton(UIConfig.PRIMARY_GRADIENT);
		backButton.setBounds(30, 30, 40, 40);
		backButton.setAlignmentX(CENTER_ALIGNMENT);
		backButton.addActionListener(e -> {
			mainFrame.showPanel(PanelFactory.getInstance().getPanel("mainPanel", mainFrame));
		});

		excerciseListPanel = new ExcerciseListPanel(workout.getExcercises(), mainFrame);
		excerciseListPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
		excerciseListPanel.setBounds(0, 150, 1205, 470);

		add(title);
		add(backButton);
		add(excerciseListPanel);
	}

}
