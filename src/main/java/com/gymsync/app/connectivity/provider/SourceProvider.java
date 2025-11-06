package com.gymsync.app.connectivity.provider;

import com.gymsync.app.repository.firebaseManager.FirebaseManager;
import com.gymsync.app.repository.fileManger.BinaryFileManager;
import com.gymsync.app.connectivity.connection.ConnectionManager;
import com.gymsync.app.model.entities.User;
import com.gymsync.app.model.entities.Workout;
import com.gymsync.app.services.exceptions.FireBaseException;

import java.util.ArrayList;
import java.util.List;

public class SourceProvider {

	private BinaryFileManager binaryFileManager = null;
	private ConnectionManager connectionManager = null;

	public SourceProvider() {
		binaryFileManager = new BinaryFileManager();
		connectionManager = new ConnectionManager();
	}

	public List<User> getUsers() throws FireBaseException {
		List<User> users = new ArrayList<User>();
		if (connectionManager.isOnline()) {
			users = FirebaseManager.getInstance().getUsers();
		} else {
			users = binaryFileManager.loadUsers();
		}
		return users;
	}

	public List<Workout> getWorkouts() throws FireBaseException {
		List<Workout> workouts = new ArrayList<Workout>();
		if (connectionManager.isOnline()) {
			workouts = FirebaseManager.getInstance().getWorkouts();
		} else {
			workouts = binaryFileManager.loadWorkouts();
		}
		return workouts;
	}

}
