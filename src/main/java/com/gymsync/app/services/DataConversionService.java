package com.gymsync.app.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;

import com.google.cloud.firestore.DocumentReference;
import com.gymsync.app.model.entities.Series;
import com.gymsync.app.model.entities.User;
import com.gymsync.app.model.entities.Workout;

public class DataConversionService extends AbstractService {

	public int[] secondsToHMS(int totalSeconds) {
		int hours = totalSeconds / 3600;
		int minutes = (totalSeconds / 60) % 60;
		int seconds = totalSeconds % 60;
		return new int[] { hours, minutes, seconds };
	}

	public User createUserFromStrings(String name, String lastname, String email, String password, String birthDateStr,
			String levelStr) {

		User user = new User();

		user.setName(name);
		user.setLastname(lastname);
		user.setEmail(email);
		user.setPassword(password);

		Date birthDate = stringToDateFormatter(birthDateStr);
		user.setBirthDate(birthDate);

		try {
			int level = Integer.valueOf(levelStr);
			user.setLevel(level);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("Level must be an integer.", e);
		}

		return user;
	}

	public Series createSeriesFromStrings(String name, String estimatedDurationStr, String repetionCountStr,
			DocumentReference excerciseRef, String icon) {
		Series series = new Series();

		series.setName(name);
		series.setExerciseRef(excerciseRef);
		series.setIcon(icon);

		try {
			int estimatedDuration = Integer.valueOf(estimatedDurationStr);
			series.setEstimatedDuration(estimatedDuration);

			int repetionCount = Integer.valueOf(repetionCountStr);
			series.setRepetitionCount(repetionCount);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException(e.getMessage());
		}

		return series;
	}

	public Workout createWorkoutFromStrings(String name, String excerciseCountStr, String leveltStr, String videoUrl) {
		Workout workout = new Workout();

		workout.setName(name);
		workout.setUrlVideo(videoUrl);

		try {
			int excerciseCount = Integer.valueOf(excerciseCountStr);
			workout.setNExcercises(excerciseCount);

			int level = Integer.valueOf(leveltStr);
			workout.setLevel(level);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("estimatedDuration and repetionCount must be an integer.", e);
		}
		return workout;
	}

	public String dateToStringFormatter(Date date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		dateFormat.setLenient(false);
		String formattedDate = dateFormat.format(date);
		return formattedDate;
	}

	public Date stringToDateFormatter(String dateString) {
		dateString = dateString.trim().replace("\"", "");
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		Date ret = null;
		try {
			ret = dateFormat.parse(dateString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return ret;
	}

	public LocalTime secondsToLocalTimeFormat(int seconds) {
		LocalTime ret = null;
		int minutes = (seconds % 3600) / 60;
		int secs = seconds % 60;
		ret = LocalTime.of(minutes, secs);
		return ret;
	}
}
