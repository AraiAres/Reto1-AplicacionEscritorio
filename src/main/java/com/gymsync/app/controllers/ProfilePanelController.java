package com.gymsync.app.controllers;

import java.util.Date;

import com.gymsync.app.model.entities.User;
import com.gymsync.app.repository.firebaseManager.FirebaseManager;
import com.gymsync.app.services.exceptions.FireBaseException;

public class ProfilePanelController extends AbstractController {

	public User updateUserData(User user, String newName, String newLastName, String newEmail, char[] newPassword,
			Date newBirthDate) {
		User newUser = createNewUser(newName, newLastName, newEmail, newPassword, newBirthDate, user.getLevel());
		newUser.setWorkouts(user.getWorkouts());
		try {
			FirebaseManager.getInstance().changeUser(user, newUser);
		} catch (FireBaseException e) {
			e.printStackTrace();
		}
		return newUser;
	}

	private User createNewUser(String name, String lastName, String email, char[] password, Date birthDate, int Level) {
		User user = new User();
		user.setName(name);
		user.setLastname(lastName);
		user.setEmail(email);
		user.setPassword(String.valueOf(password));
		user.setBirthDate(birthDate);
		user.setLevel(Level);

		return user;
	}

}
