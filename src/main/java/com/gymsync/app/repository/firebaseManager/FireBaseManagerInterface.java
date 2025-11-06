package com.gymsync.app.repository.firebaseManager;

import java.util.List;

import com.google.cloud.firestore.DocumentReference;
import com.gymsync.app.model.entities.Excercise;
import com.gymsync.app.model.entities.Series;
import com.gymsync.app.model.entities.User;
import com.gymsync.app.model.entities.Workout;
import com.gymsync.app.services.exceptions.FireBaseException;

public interface FireBaseManagerInterface {

	public List<User> getUsers() throws FireBaseException;

	public List<Series> getUserSeries(User user) throws FireBaseException;

	public List<Series> getSeries() throws FireBaseException;

	public Series getSeriesByRef(DocumentReference seriesRef) throws FireBaseException;

	public List<Excercise> getExcercises() throws FireBaseException;

	public List<Workout> getWorkouts() throws FireBaseException;

	public void addUser(User user) throws FireBaseException;

	public boolean changeUser(User oldUser, User newUser) throws FireBaseException;

	Void addSeriesToUser(Series newSeries, User currentUser) throws FireBaseException;

	boolean changeUserLevel(User oldUser, int newLevel) throws FireBaseException;

}
