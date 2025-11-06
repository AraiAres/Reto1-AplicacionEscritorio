package com.gymsync.app.controllers;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.gymsync.app.model.entities.Workout;
import com.gymsync.app.services.ServiceFactory;
import com.gymsync.app.services.UserHistoryService;

public class UserHistoryPanelController extends AbstractController {

	public List<Object[]> convertWorkoutsToTableRowType(List<Workout> workouts) {
		List<Object[]> tableRows = new ArrayList<>();

		if (workouts != null) {
			DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
			SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy");

			for (Workout workout : workouts) {
				UserHistoryService service = (UserHistoryService) ServiceFactory.getInstance()
						.getService("userHistoryPanelService");

				String name = workout.getName();
				int level = workout.getLevel();

				LocalTime estimatedDuration = service.calculateEstimatedDuration(workout);
				Date completionDate = service.calculateCompletionDate(workout);
				int completedPercentage = service.calculateCompletedPercentage(workout);
				String completedPercentageStr = String.valueOf(completedPercentage) + "%";
				Object[] row = new Object[] { name, level,
						estimatedDuration != null ? estimatedDuration.format(timeFormatter) : "",
						completionDate != null ? dateFormatter.format(completionDate) : "", completedPercentageStr };

				tableRows.add(row);
			}
		}

		return tableRows;
	}

}
