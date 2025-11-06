package com.gymsync.app.repository.fileManger;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import com.gymsync.app.model.entities.User;
import com.gymsync.app.model.entities.Workout;

public class BinaryFileManager {

	private static final String BACKUP_PATH = "src/main/resources/backups/";
	private static final String USERS_FILE = "users.dat";
	private static final String WORKOUTS_FILE = "workouts.dat";

	public void backupUsers(List<User> users) {
		writeList(BACKUP_PATH + USERS_FILE, users);
	}

	public void backupWorkouts(List<Workout> workouts) {
		writeList(BACKUP_PATH + WORKOUTS_FILE, workouts);
	}

	public List<User> loadUsers() {
		return readList(BACKUP_PATH + USERS_FILE);
	}

	public List<Workout> loadWorkouts() {
		return readList(BACKUP_PATH + WORKOUTS_FILE);
	}

	private <T> void writeList(String fileName, List<T> list) {
		File folder = new File(BACKUP_PATH);
		if (!folder.exists())
			folder.mkdirs();

		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
			oos.writeObject(list);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	private <T> List<T> readList(String fileName) {
		File file = new File(fileName);
		if (!file.exists())
			return new ArrayList<>();

		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
			List<T> list = (List<T>) ois.readObject();
			return list;
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return new ArrayList<>();
	}
}
