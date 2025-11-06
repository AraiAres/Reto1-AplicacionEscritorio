package com.gymsync.app.controllers;

import java.util.ArrayList;
import java.util.List;

import com.gymsync.app.connectivity.provider.SourceProvider;
import com.gymsync.app.model.entities.User;
import com.gymsync.app.services.exceptions.FireBaseException;

public class LoginPanelController extends AbstractController {
	SourceProvider userProvider = null;

	public User validateCredentials(String username, char[] pass) {
		userProvider = new SourceProvider();
		String password = new String(pass);
		List<User> users = getUsers();

		for (User user : users) {
			if (username.equalsIgnoreCase(user.getName()) && password.equals(user.getPassword())) {
				return user;
			}
		}
		return null;
	}

	private List<User> getUsers() {
		List<User> users = new ArrayList<>();
		try {
			users = userProvider.getUsers();
		} catch (FireBaseException e) {
			e.printStackTrace();
		}
		return users;
	}

}
