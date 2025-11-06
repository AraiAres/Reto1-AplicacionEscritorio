package com.gymsync.app.controllers;

import java.util.ArrayList;
import java.util.List;

import com.gymsync.app.connectivity.provider.SourceProvider;
import com.gymsync.app.model.entities.Workout;
import com.gymsync.app.services.exceptions.FireBaseException;

public class MainPanelController extends AbstractController {
	SourceProvider userProvider = null;

	public List<Workout> userCompatibleWorkouts(int userLevel) {
		userProvider = new SourceProvider();
		ArrayList<Workout> ret = new ArrayList<Workout>();
		try {
			List<Workout> allWorkouts = userProvider.getWorkouts();
			for (Workout workout : allWorkouts) {
				if (workout.getLevel() <= userLevel) {
					ret.add(workout);
				}
			}
		} catch (FireBaseException e) {
			e.printStackTrace();
		}
		return ret;
	}

}
