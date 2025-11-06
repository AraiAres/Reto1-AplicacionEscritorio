package com.gymsync.app.controllers;

import java.util.List;

import com.gymsync.app.model.entities.Excercise;
import com.gymsync.app.model.entities.Series;
import com.gymsync.app.model.entities.User;
import com.gymsync.app.model.entities.Workout;
import com.gymsync.app.repository.firebaseManager.FirebaseManager;
import com.gymsync.app.services.exceptions.FireBaseException;
import com.gymsync.app.view.utils.SessionManager;

public class ExcercisePanelController extends AbstractController {

	public boolean isCompleted(Series series, User user) {
		for (Workout workout : user.getWorkouts()) {
			for (Excercise excercise : workout.getExcercises()) {
				for (Series userSeries : excercise.getSeries()) {
					if (series.equals(userSeries)) {
						if (userSeries.isCompleted()) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}

	public void saveCompletedSeries(Series completedSeries, User currentUser) {
		try {
			FirebaseManager.getInstance().addSeriesToUser(completedSeries, currentUser);
			FirebaseManager.getInstance().clearCache();
		} catch (FireBaseException e) {
			e.printStackTrace();
		}
	}

	public void refreshUserHistory(User user) {
		try {
			List<Workout> userWorkouts = FirebaseManager.getInstance().getUserWorkouts(user);
			user.setWorkouts(userWorkouts);
		} catch (FireBaseException e) {
			e.printStackTrace();
		}
	}

	public Excercise getNextExcercise(Excercise currentExcercise) {
		Excercise ret = null;
		List<Excercise> currentExList = SessionManager.getInstance().getCurrentWorkout().getExcercises();
		for (int i = 0; i <= currentExList.size(); i++) {
			if (currentExList.get(i).equals(currentExcercise)) {
				return currentExList.get(i + 1);
			}
		}
		return ret;
	}

	public boolean isCompletedWorkout(Workout workout) {
		for (Excercise excercise : workout.getExcercises()) {
			if (!excercise.isCompleted()) {
				return false;
			}
		}
		return true;
	}

	public Workout getUpdatedWorkout(Workout oldWorkout) {
		User user = SessionManager.getInstance().getCurrentUser();
		for (Workout w : user.getWorkouts()) {
			if (w.getName().equals(oldWorkout.getName())) {
				return w;
			}
		}
		return oldWorkout;
	}

	public void updateUserLevel(Workout currentWorkout, User currentUser) {
		List<Workout> userWorkouts = currentUser.getWorkouts();
		int currentLevel = currentWorkout.getLevel();
		boolean hasSameLevelLeft = false;
		int newLevel = currentLevel;

		for (Workout workout : userWorkouts) {
			if (workout.getLevel() == currentLevel && !isCompletedWorkoutForUser(workout, currentUser)) {
				hasSameLevelLeft = true;
				break;
			}
		}

		if (!hasSameLevelLeft) {
			newLevel = currentLevel + 1;
			try {
				FirebaseManager.getInstance().changeUserLevel(currentUser, newLevel);
				FirebaseManager.getInstance().clearCache();
			} catch (FireBaseException e) {
				e.printStackTrace();
			}
		}
	}

	private boolean isCompletedWorkoutForUser(Workout workout, User user) {
		for (Workout userWorkout : user.getWorkouts()) {
			if (userWorkout.getName().equals(workout.getName())) {
				for (Excercise excercise : userWorkout.getExcercises()) {
					if (!excercise.isCompleted()) {
						return false;
					}
				}
				return true;
			}
		}
		return false;
	}

}
