package com.gymsync.app.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.gymsync.app.model.entities.Excercise;
import com.gymsync.app.model.entities.Series;
import com.gymsync.app.model.entities.User;
import com.gymsync.app.model.entities.Workout;
import com.gymsync.app.repository.firebaseManager.FirebaseManager;
import com.gymsync.app.services.exceptions.FireBaseException;

public class EntityConversionService extends AbstractService {
	FirebaseManager firebaseManager = null;

	public EntityConversionService() {
		try {
			firebaseManager = new FirebaseManager();
		} catch (FireBaseException e) {
			e.printStackTrace();
		}
	}

	public List<Workout> convertUserSeriesToWorkoutList(User user, List<Series> completedSeriesList) {
		List<Workout> completedWorkouts = new ArrayList<>();

		for (Series completedSeries : completedSeriesList) {
			Excercise excercise = getExcerciseOfSeries(completedSeries);
			if (excercise == null)
				continue;

			Workout workout = getWorkoutOfExcercise(excercise);
			if (workout == null)
				continue;

			for (Series series : excercise.getSeries()) {
				if (series.equals(completedSeries)) {
					series.setCompleted(true);
					series.setCompletionDate(completedSeries.getCompletionDate());
				}
			}

			boolean allSeriesDone = excercise.getSeries().stream().allMatch(Series::isCompleted);
			excercise.setCompleted(allSeriesDone);

			if (!completedWorkouts.contains(workout)) {
				updateWorkoutWithCompletedSeries(workout, completedSeriesList);

				completedWorkouts.add(workout);
			}
		}

		return completedWorkouts;
	}

	private Excercise getExcerciseOfSeries(Series searchSeries) {
		Excercise ret = null;
		try {
			List<Excercise> excersices = firebaseManager.getExcercises();
			for (Excercise excersise : excersices) {
				for (Series series : excersise.getSeries()) {
					if (series.equals(searchSeries)) {
						return excersise;
					}
				}
			}
		} catch (FireBaseException e) {
			e.printStackTrace();
		}
		return ret;
	}

	private Workout getWorkoutOfExcercise(Excercise searchExcercise) {
		Workout ret = null;
		try {
			List<Workout> workouts = firebaseManager.getWorkouts();
			for (Workout workout : workouts) {
				for (Excercise excercise : workout.getExcercises()) {
					if (excercise.equals(searchExcercise)) {
						return workout;
					}
				}
			}
		} catch (FireBaseException e) {
			e.printStackTrace();
		}
		return ret;
	}

	private void updateWorkoutWithCompletedSeries(Workout workout, List<Series> completedSeriesList) {
		if (workout.getExcercises() == null)
			return;

		for (Excercise exer : workout.getExcercises()) {
			if (exer.getSeries() == null)
				continue;

			boolean allSeriesDone = true;

			for (Series ser : exer.getSeries()) {
				boolean completed = completedSeriesList.stream().anyMatch(cs -> cs.getName().equals(ser.getName())
						&& Objects.equals(cs.getExerciseRef(), ser.getExerciseRef()));

				ser.setCompleted(completed);

				completedSeriesList.stream()
						.filter(cs -> cs.getName().equals(ser.getName())
								&& Objects.equals(cs.getExerciseRef(), ser.getExerciseRef()))
						.findFirst().ifPresent(cs -> ser.setCompletionDate(cs.getCompletionDate()));

				if (!completed) {
					allSeriesDone = false;
				}
			}

			exer.setCompleted(allSeriesDone);
		}
	}

}
