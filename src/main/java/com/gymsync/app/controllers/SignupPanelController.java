package com.gymsync.app.controllers;

import java.util.Date;
import java.util.List;

import com.gymsync.app.model.entities.User;
import com.gymsync.app.repository.firebaseManager.FirebaseManager;
import com.gymsync.app.services.exceptions.FireBaseException;

public class SignupPanelController extends AbstractController {

	public void addNewUserToDB(String name, String lastName, String email, String pass, Date birth, int level) {
		User newUser = newUser(name, lastName, email, pass, birth, level);
		try {
			FirebaseManager.getInstance().addUser(newUser);
		} catch (FireBaseException e) {
			e.printStackTrace();
		}
	}

	public User newUser(String name, String lastName, String email, String pass, Date birth, int level) {
		User user = new User();
		user.setName(name);
		user.setLastname(lastName);
		user.setEmail(email);
		user.setPassword(pass);
		user.setBirthDate(birth);
		user.setLevel(level);
		return user;
	}

	public boolean isUniqueUser(String name, String lastname, Date date) {
		List<User> allUsers;
		try {
			allUsers = FirebaseManager.getInstance().getUsers();
			for (User user : allUsers) {
				System.out.println(user.getName() + name + user.getLastname() + lastname + user.getBirthDate() + date);
				if (user.getName().equalsIgnoreCase(name) && user.getLastname().equalsIgnoreCase(lastname)
						&& user.getBirthDate().equals(date)) {
					return false;
				}
			}
		} catch (FireBaseException e) {
			e.printStackTrace();
		}

		return true;
	}
}