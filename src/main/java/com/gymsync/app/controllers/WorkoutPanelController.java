package com.gymsync.app.controllers;

import java.util.List;

import com.gymsync.app.model.entities.Excercise;
import com.gymsync.app.model.entities.Workout;
import com.gymsync.app.view.utils.SessionManager;

public class WorkoutPanelController extends AbstractController {

	public boolean isCompleted(Excercise inputExcercise) {
		List<Workout> userWorkouts = SessionManager.getInstance().getCurrentUser().getWorkouts();

		for (Workout workout : userWorkouts) {
			for (Excercise excercise : workout.getExcercises()) {
				if (excercise.equals(inputExcercise)) {
					return excercise.isCompleted();
				}
			}
		}

		return false;
	}

}
