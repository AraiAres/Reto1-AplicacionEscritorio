package com.gymsync.app.view.customComponents;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.BoxLayout;
import javax.swing.Box;
import java.awt.Dimension;
import java.awt.Component;
import java.awt.FlowLayout;
import java.util.List;

import com.gymsync.app.model.entities.Excercise;
import com.gymsync.app.view.frame.MainFrame;

public class ExcerciseListPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private MainFrame mainFrame = null;
	private List<Excercise> excercises = null;

	private JPanel contentPanel = null;
	private JScrollPane scrollPane = null;

	public ExcerciseListPanel(List<Excercise> excercises, MainFrame mainFrame) {
		this.mainFrame = mainFrame;
		this.excercises = excercises;
		initialize();
	}

	private void initialize() {
		setOpaque(false);
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setPreferredSize(new Dimension(1200, 500));

		contentPanel = new JPanel();
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
		contentPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
		contentPanel.setOpaque(false);

		for (Excercise excercise : excercises) {
			JPanel wrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
			wrapper.setOpaque(false);
			ExcerciseItemPanel excercisePanel = new ExcerciseItemPanel(excercise, mainFrame);
			wrapper.add(excercisePanel);
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
