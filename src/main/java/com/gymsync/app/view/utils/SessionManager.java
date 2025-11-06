package com.gymsync.app.view.utils;

import com.gymsync.app.model.entities.Excercise;
import com.gymsync.app.model.entities.User;
import com.gymsync.app.model.entities.Workout;
import com.gymsync.app.repository.firebaseManager.FirebaseManager;
import com.gymsync.app.services.exceptions.FireBaseException;

public class SessionManager {
	private static SessionManager instance = null;
	private User currentUser = null;
	private Workout currentWorkout = null;
	private Excercise currentExcercise = null;

	public SessionManager() {
	};

	public static SessionManager getInstance() {
		if (instance == null) {
			instance = new SessionManager();
		}
		return instance;
	}

	public User getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(User user) {
		this.currentUser = user;
	}

	public Workout getCurrentWorkout() {
		return currentWorkout;
	}

	public void setCurrentWorkout(Workout workout) {
		this.currentWorkout = workout;
	}

	public Excercise getCurrentExcercise() {
		return currentExcercise;
	}

	public void setCurrentExcercise(Excercise excercise) {
		this.currentExcercise = excercise;
	}

	public void logout() {
		currentUser = null;
		currentWorkout = null;
		currentExcercise = null;
		try {
			FirebaseManager.getInstance().clearCache();
		} catch (FireBaseException e) {
			e.printStackTrace();
		}
	}

}
