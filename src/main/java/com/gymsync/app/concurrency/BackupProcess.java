package com.gymsync.app.concurrency;

import java.util.List;

import com.gymsync.app.connectivity.connection.ConnectionManager;
import com.gymsync.app.model.entities.User;
import com.gymsync.app.model.entities.Workout;
import com.gymsync.app.repository.fileManger.BinaryFileManager;
import com.gymsync.app.repository.fileManger.XmlManager;
import com.gymsync.app.repository.firebaseManager.FirebaseManager;
import com.gymsync.app.services.exceptions.FireBaseException;

public class BackupProcess {

	public static void main(String[] args) {

		System.out.println("Empezando BackupProcess...");
		ConnectionManager connectionManager = new ConnectionManager();
		BinaryFileManager binaryFileManager = new BinaryFileManager();

		try {
			if (connectionManager.isOnline()) {
				FirebaseManager fbManager = FirebaseManager.getInstance();
				XmlManager xmlManager = new XmlManager();

				List<Workout> workoutsList = fbManager.getWorkouts();
				List<User> userList = fbManager.getUsers();

				binaryFileManager.backupUsers(userList);
				xmlManager.saveUsers(userList);
				binaryFileManager.backupWorkouts(workoutsList);
				xmlManager.saveWorkouts(workoutsList);

				System.out.println("BackupProcess completado.");
				/*
				 * System.out.println("Cargando datos de backup:");
				 * 
				 * List<Workout> loadedWorkouts = binaryFileManager.loadWorkouts(); for (Workout
				 * w : loadedWorkouts) { System.out.println(w.toString()); }
				 * 
				 * List<User> loadedUsers = binaryFileManager.loadUsers(); for (User u :
				 * loadedUsers) { System.out.println(u.toString()); }
				 */
			} else {
				System.out.println("No hay conexión. Saltando backup.");
			}

		} catch (FireBaseException e) {
			System.err.println("Firebase backup error:");
			e.printStackTrace();
		} catch (Exception e) {
			System.err.println("Unexpected backup error:");
			e.printStackTrace();
		}
	}
}
