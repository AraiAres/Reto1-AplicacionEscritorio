package com.gymsync.app.view.customComponents;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.BoxLayout;
import javax.swing.Box;
import java.awt.Dimension;
import java.awt.Component;
import java.awt.FlowLayout;
import java.util.List;

import com.gymsync.app.model.entities.Workout;
import com.gymsync.app.view.frame.MainFrame;

public class WorkoutListPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private MainFrame mainFrame = null;

	private JPanel contentPanel = null;
	private JScrollPane scrollPane = null;

	public WorkoutListPanel(List<Workout> workouts, MainFrame mainFrame) {
		this.mainFrame = mainFrame;
		initialize(workouts);
	}

	private void initialize(List<Workout> workouts) {
		setOpaque(false);
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setPreferredSize(new Dimension(1200, 500));

		contentPanel = new JPanel();
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
		contentPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
		contentPanel.setOpaque(false);

		for (Workout workout : workouts) {
			JPanel wrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
			wrapper.setOpaque(false);
			WorkoutItemPanel workoutPanel = new WorkoutItemPanel(workout, mainFrame);
			wrapper.add(workoutPanel);
			contentPanel.add(wrapper);
			contentPanel.add(Box.createVerticalStrut(5));
		}

		scrollPane = new JScrollPane(contentPanel);
		scrollPane.setOpaque(false);
		scrollPane.getViewport().setOpaque(false);
		scrollPane.setBorder(null);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setPreferredSize(new Dimension(1200, 500));
		scrollPane.getVerticalScrollBar().setUnitIncrement(16);

		add(scrollPane);
	}
}
