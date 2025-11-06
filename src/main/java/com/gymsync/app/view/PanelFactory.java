package com.gymsync.app.view;

import com.gymsync.app.view.frame.MainFrame;
import com.gymsync.app.view.panels.AbstractPanel;
import com.gymsync.app.view.panels.ExcercisePanel;
import com.gymsync.app.view.panels.LoginPanel;
import com.gymsync.app.view.panels.ProfilePanel;
import com.gymsync.app.view.panels.SignupPanel;
import com.gymsync.app.view.panels.UserHistoryPanel;
import com.gymsync.app.view.panels.WorkoutPanel;
import com.gymsync.app.view.panels.MainPanel;
import com.gymsync.app.view.utils.SessionManager;

public class PanelFactory {

	private static PanelFactory instance = null;

	public PanelFactory() {
	}

	public static PanelFactory getInstance() {
		return instance = instance == null ? new PanelFactory() : instance;
	}

	public AbstractPanel getPanel(String panelKey, MainFrame mainFrame) {
		switch (panelKey) {
		case "loginPanel":
			return new LoginPanel(mainFrame);
		case "signupPanel":
			return new SignupPanel(mainFrame);
		case "mainPanel":
			return new MainPanel(SessionManager.getInstance().getCurrentUser(), mainFrame);
		case "profilePanel":
			return new ProfilePanel(SessionManager.getInstance().getCurrentUser(), mainFrame);
		case "userHistoryPanel":
			return new UserHistoryPanel(SessionManager.getInstance().getCurrentUser(), mainFrame);
		case "workoutPanel":
			return new WorkoutPanel(SessionManager.getInstance().getCurrentWorkout(), mainFrame);
		case "excercisePanel":
			return new ExcercisePanel(SessionManager.getInstance().getCurrentExcercise(), mainFrame);
		default:
			throw new IllegalArgumentException("Unknown panel key: " + panelKey);
		}
	}
}
