package com.gymsync.app.services;

import java.time.LocalTime;
import java.util.Date;

import com.gymsync.app.model.entities.Excercise;
import com.gymsync.app.model.entities.Series;
import com.gymsync.app.model.entities.Workout;

public class UserHistoryService extends AbstractService {


	public LocalTime calculateEstimatedDuration(Workout workout) {
		LocalTime ret = null;
		int estimatedTime = 0;

		for (Excercise excercise : workout.getExcercises()) {
			int seriesCount = excercise.getSeries().size();

			for (Series series : excercise.getSeries()) {
				estimatedTime += series.getEstimatedDuration();
			}

			if (seriesCount > 1) {
				estimatedTime += excercise.getBreakTime() * (seriesCount - 1);
			}
		}
		DataConversionService dataConversionService = (DataConversionService) ServiceFactory.getInstance()
				.getService("dataConversionService");
		ret = dataConversionService.secondsToLocalTimeFormat(estimatedTime);
		return ret;
	}

	public Date calculateCompletionDate(Workout workout) {
		Date ret = null;

		for (Excercise excercise : workout.getExcercises()) {
			for (Series series : excercise.getSeries()) {
				Date seriesDate = series.getCompletionDate();
				if (seriesDate != null) {
					if (ret == null || seriesDate.after(ret)) {
						ret = seriesDate;
					}
				}
			}
		}

		return ret;
	}

	public int calculateCompletedPercentage(Workout workout) {
		int completed = 0;
		int total = 0;
		int ret = 0;

		for (Excercise excercise : workout.getExcercises()) {
			for (Series series : excercise.getSeries()) {
				if (series.isCompleted()) {
					completed++;
				}
				total++;
			}
		}
		if (total != 0) {
			return (completed * 100) / total;
		}
		return ret;
	}

}
