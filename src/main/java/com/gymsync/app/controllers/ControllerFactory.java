package com.gymsync.app.controllers;

public class ControllerFactory {

	private static ControllerFactory instance = null;

	public static final String LOGIN_PANEL_CONTROLLER = "loginPanelController";
	public static final String SIGNUP_PANEL_CONTROLLER = "signupPanelController";
	public static final String MAIN_PANEL_CONTROLLER = "mainPanelController";
	public static final String PROFILE_PANEL_CONTROLLER = "profilePanelController";
	public static final String USER_HISTORY_PANEL_CONTROLLER = "userHistoryPanelController";
	public static final String WORKOUT_PANEL_CONTROLLER = "workoutPanelController";
	public static final String EXCERCISE_PANEL_CONTROLLER = "excercisePanelController";

	private ControllerFactory() {
	}

	public static ControllerFactory getInstance() {
		return instance = instance == null ? new ControllerFactory() : instance;
	}

	public AbstractController getController(String controllerOption) {
		AbstractController ret = null;

		switch (controllerOption) {
		case LOGIN_PANEL_CONTROLLER:
			ret = new LoginPanelController();
			break;
		case SIGNUP_PANEL_CONTROLLER:
			ret = new SignupPanelController();
			break;
		case MAIN_PANEL_CONTROLLER:
			ret = new MainPanelController();
			break;
		case PROFILE_PANEL_CONTROLLER:
			ret = new ProfilePanelController();
			break;
		case USER_HISTORY_PANEL_CONTROLLER:
			ret = new UserHistoryPanelController();
			break;
		case WORKOUT_PANEL_CONTROLLER:
			ret = new WorkoutPanelController();
			break;
		case EXCERCISE_PANEL_CONTROLLER:
			ret = new ExcercisePanelController();
			break;
		}

		return ret;
	}

}
