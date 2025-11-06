package com.gymsync.app.repository.firebaseManager;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import com.gymsync.app.model.entities.Excercise;
import com.gymsync.app.model.entities.Series;
import com.gymsync.app.model.entities.User;
import com.gymsync.app.model.entities.Workout;
import com.gymsync.app.services.ServiceFactory;
import com.gymsync.app.services.exceptions.FireBaseException;
import com.gymsync.app.services.DataConversionService;
import com.gymsync.app.services.EntityConversionService;

public class FirebaseManager implements FireBaseManagerInterface {

	private static FirebaseManager instance = null;

	private static final String CREDENTIALS = "/gymsyncBD.json";
	private static final String COLLECTION_USERS = "Clients";
	private static final String COLLECTION_WORKOUTS = "Workouts";
	private static final String COLLECTION_EXCERCISES = "Excercises";
	private static final String COLLECTION_SERIES = "Series";

	private List<User> cachedUsers = null;
	private List<Workout> cachedWorkouts = null;

	public FirebaseManager() throws FireBaseException {
		try {
			if (FirebaseApp.getApps().isEmpty()) {
				InputStream serviceAccount = FirebaseManager.class.getResourceAsStream(CREDENTIALS);
				FirebaseOptions options = FirebaseOptions.builder()
						.setCredentials(GoogleCredentials.fromStream(serviceAccount)).build();
				FirebaseApp.initializeApp(options);
			}
		} catch (Exception e) {
			throw new FireBaseException("Error - " + e.getLocalizedMessage());
		}
	}

	public static synchronized FirebaseManager getInstance() throws FireBaseException {
		if (instance == null) {
			instance = new FirebaseManager();
		}
		return instance;
	}

	@Override
	public List<User> getUsers() throws FireBaseException {
		if (cachedUsers != null) {
			return cachedUsers;
		}
		List<User> ret = null;

		try {

			Firestore dataBase = FirestoreClient.getFirestore();
			ApiFuture<QuerySnapshot> query = dataBase.collection(COLLECTION_USERS).get();

			QuerySnapshot querySnapshot = query.get();
			List<QueryDocumentSnapshot> users = querySnapshot.getDocuments();
			for (QueryDocumentSnapshot user : users) {
				ret = null == ret ? new ArrayList<User>() : ret;
				DataConversionService dataConversionService = (DataConversionService) ServiceFactory.getInstance()
						.getService("dataConversionService");
				User useri = dataConversionService.createUserFromStrings(user.getString("name"),
						user.getString("lastname"), user.getString("email"), user.getString("password"),
						user.getString("birthdate"), user.getString("level"));
				useri.setWorkouts(getUserWorkouts(useri));
				ret.add(useri);
			}
			cachedUsers = ret;
		} catch (Exception e) {
			throw new FireBaseException("Error - " + e.getLocalizedMessage());
		}

		return ret;
	}

	public List<Workout> getUserWorkouts(User user) {
		List<Workout> userWorkouts = new ArrayList<Workout>();
		try {
			List<Series> userSeries = getUserSeries(user);
			EntityConversionService entityConversionService = (EntityConversionService) ServiceFactory.getInstance()
					.getService("entityConversionService");
			userWorkouts = entityConversionService.convertUserSeriesToWorkoutList(user, userSeries);
		} catch (FireBaseException e) {
			e.printStackTrace();
		}
		return userWorkouts;
	}

	@Override
	public List<Series> getUserSeries(User user) throws FireBaseException {
		List<Series> ret = new ArrayList<>();

		try {
			Firestore dataBase = FirestoreClient.getFirestore();

			ApiFuture<QuerySnapshot> query = dataBase.collection(COLLECTION_USERS).whereEqualTo("name", user.getName())
					.whereEqualTo("lastname", user.getLastname()).get();

			QuerySnapshot querySnapshot = query.get();
			List<QueryDocumentSnapshot> users = querySnapshot.getDocuments();

			if (!users.isEmpty()) {
				DocumentReference userRef = users.get(0).getReference();

				ApiFuture<QuerySnapshot> seriesQuery = userRef.collection("CompletedSeries").get();
				List<QueryDocumentSnapshot> seriesDocs = seriesQuery.get().getDocuments();

				for (QueryDocumentSnapshot seriesDoc : seriesDocs) {
					DocumentReference seriesRef = seriesDoc.get("seriesRef", DocumentReference.class);
					String completionDateStr = seriesDoc.getString("completedDate");

					DataConversionService dataConversionService = (DataConversionService) ServiceFactory.getInstance()
							.getService("dataConversionService");

					if (seriesRef != null) {
						Series series = getSeriesByRef(seriesRef);
						series.setCompleted(true);
						series.setCompletionDate(dataConversionService.stringToDateFormatter(completionDateStr));
						ret.add(series);
					}
				}
			}

		} catch (Exception e) {
			throw new FireBaseException("Error - " + e.getLocalizedMessage());
		}

		return ret;
	}

	@Override
	public List<Series> getSeries() throws FireBaseException {
		List<Series> ret = null;

		try {

			Firestore dataBase = FirestoreClient.getFirestore();
			ApiFuture<QuerySnapshot> query = dataBase.collection(COLLECTION_SERIES).get();

			QuerySnapshot querySnapshot = query.get();
			List<QueryDocumentSnapshot> serieses = querySnapshot.getDocuments();
			for (QueryDocumentSnapshot series : serieses) {
				ret = null == ret ? new ArrayList<Series>() : ret;
				DataConversionService dataConversionService = (DataConversionService) ServiceFactory.getInstance()
						.getService("dataConversionService");
				Series seriesi = dataConversionService.createSeriesFromStrings(series.getString("name"),
						series.getString("estimatedDuration"), series.getString("repetitionCount"),
						series.get("excerciseRef", DocumentReference.class), series.getString("seriesIcon"));
				ret.add(seriesi);
			}
		} catch (Exception e) {
			throw new FireBaseException("Error - " + e.getLocalizedMessage());
		}

		return ret;
	}

	@Override
	public Series getSeriesByRef(DocumentReference seriesRef) throws FireBaseException {
		Series ret = null;

		try {

			Firestore dataBase = FirestoreClient.getFirestore();
			ApiFuture<QuerySnapshot> query = dataBase.collection(COLLECTION_SERIES).get();

			QuerySnapshot querySnapshot = query.get();
			List<QueryDocumentSnapshot> serieses = querySnapshot.getDocuments();
			for (QueryDocumentSnapshot series : serieses) {
				ret = null == ret ? new Series() : ret;
				if (series.getReference().equals(seriesRef)) {
					DataConversionService dataConversionService = (DataConversionService) ServiceFactory.getInstance()
							.getService("dataConversionService");
					ret = dataConversionService.createSeriesFromStrings(series.getString("name"),
							series.getString("estimatedDuration"), series.getString("repetitionCount"),
							series.get("excerciseRef", DocumentReference.class), series.getString("seriesIcon"));
				}
			}
		} catch (Exception e) {
			throw new FireBaseException("Error - " + e.getLocalizedMessage());
		}

		return ret;
	}

	@Override
	public List<Excercise> getExcercises() throws FireBaseException {
		List<Excercise> ret = null;

		try {

			Firestore dataBase = FirestoreClient.getFirestore();
			ApiFuture<QuerySnapshot> query = dataBase.collection(COLLECTION_EXCERCISES).get();

			QuerySnapshot querySnapshot = query.get();
			List<QueryDocumentSnapshot> excercises = querySnapshot.getDocuments();
			for (QueryDocumentSnapshot excercise : excercises) {
				ret = null == ret ? new ArrayList<Excercise>() : ret;
				Excercise excercisei = new Excercise();

				excercisei.setName(excercise.getString("name"));
				excercisei.setBreakTime(Integer.valueOf(excercise.getString("breakTime")));
				excercisei.setDescription(excercise.getString("description"));
				excercisei.setWorkoutReference(excercise.get("workoutRef", DocumentReference.class));
				List<Series> allSeries = getSeries();
				List<Series> ExcerciseiSeries = new ArrayList<Series>();
				for (Series serie : allSeries) {
					if (serie.getExerciseRef().equals(excercise.getReference())) {
						ExcerciseiSeries.add(serie);
					}
				}
				excercisei.setSeries(ExcerciseiSeries);
				ret.add(excercisei);
			}
		} catch (Exception e) {
			throw new FireBaseException("Error - " + e.getLocalizedMessage());
		}

		return ret;
	}

	@Override
	public List<Workout> getWorkouts() throws FireBaseException {
		if (cachedWorkouts != null) {
			return cachedWorkouts;
		}
		List<Workout> ret = null;

		try {

			Firestore dataBase = FirestoreClient.getFirestore();
			ApiFuture<QuerySnapshot> query = dataBase.collection(COLLECTION_WORKOUTS).get();

			QuerySnapshot querySnapshot = query.get();
			List<QueryDocumentSnapshot> workouts = querySnapshot.getDocuments();
			for (QueryDocumentSnapshot workout : workouts) {
				ret = null == ret ? new ArrayList<Workout>() : ret;
				DataConversionService dataConversionService = (DataConversionService) ServiceFactory.getInstance()
						.getService("dataConversionService");
				Workout workouti = dataConversionService.createWorkoutFromStrings(workout.getString("name"),
						workout.getString("excerciseCount"), workout.getString("level"), workout.getString("videoURL"));
				List<Excercise> allExcercises = getExcercises();
				List<Excercise> workoutiExcercises = new ArrayList<Excercise>();
				for (Excercise excercise : allExcercises) {
					if (excercise.getWorkoutReference().equals(workout.getReference())) {
						workoutiExcercises.add(excercise);
					}
				}
				workouti.setExcercises(workoutiExcercises);
				ret.add(workouti);
			}
			cachedWorkouts = ret;

		} catch (Exception e) {
			throw new FireBaseException("Error - " + e.getLocalizedMessage());
		}

		return ret;
	}

	@Override
	public void addUser(User user) throws FireBaseException {
		try {
			Firestore dataBase = FirestoreClient.getFirestore();

			CollectionReference query = dataBase.collection(COLLECTION_USERS);

			Map<String, Object> userMap = new HashMap<>();
			userMap.put("name", user.getName());
			userMap.put("lastname", user.getLastname());
			userMap.put("email", user.getEmail());
			userMap.put("password", user.getPassword());
			DataConversionService dataConversionService = (DataConversionService) ServiceFactory.getInstance()
					.getService("dataConversionService");
			userMap.put("birthdate", dataConversionService.dateToStringFormatter(user.getBirthDate()));
			userMap.put("level", String.valueOf(user.getLevel()));
			userMap.put("trainer", false); // solo para que en android no salga error de trainer

			DocumentReference documentReference = query.document();
			documentReference.set(userMap);

		} catch (Exception e) {
			throw new FireBaseException("Error - " + e.getLocalizedMessage());
		}
	}

	@Override
	public boolean changeUser(User oldUser, User newUser) throws FireBaseException {
		boolean ret = false;
		try {

			Firestore dataBase = FirestoreClient.getFirestore();

			ApiFuture<QuerySnapshot> query = dataBase.collection(COLLECTION_USERS)
					.whereEqualTo("name", oldUser.getName()).whereEqualTo("lastname", oldUser.getLastname()).get();

			QuerySnapshot querySnapshot = query.get();
			List<QueryDocumentSnapshot> users = querySnapshot.getDocuments();
			if (!users.isEmpty()) {

				DocumentSnapshot documentSnapshot = users.get(0);
				DocumentReference userReference = documentSnapshot.getReference();

				Map<String, Object> userMap = new HashMap<>();
				userMap.put("name", newUser.getName());
				userMap.put("lastname", newUser.getLastname());
				userMap.put("email", newUser.getEmail());
				userMap.put("password", newUser.getPassword());
				DataConversionService dataConversionService = (DataConversionService) ServiceFactory.getInstance()
						.getService("dataConversionService");
				userMap.put("birthdate", dataConversionService.dateToStringFormatter(newUser.getBirthDate()));
				userMap.put("level", String.valueOf(newUser.getLevel()));
				userMap.put("trainer", false);

				userReference.update(userMap);
				ret = true;
			}

		} catch (Exception e) {
			throw new FireBaseException("Error - " + e.getLocalizedMessage());
		}
		return ret;
	}

	@Override
	public boolean changeUserLevel(User oldUser, int newLevel) throws FireBaseException {
		boolean ret = false;
		try {
			Firestore dataBase = FirestoreClient.getFirestore();

			ApiFuture<QuerySnapshot> query = dataBase.collection(COLLECTION_USERS)
					.whereEqualTo("name", oldUser.getName()).whereEqualTo("lastname", oldUser.getLastname()).get();

			QuerySnapshot querySnapshot = query.get();
			List<QueryDocumentSnapshot> users = querySnapshot.getDocuments();
			if (!users.isEmpty()) {
				DocumentSnapshot documentSnapshot = users.get(0);
				DocumentReference userReference = documentSnapshot.getReference();

				Map<String, Object> updateMap = new HashMap<>();
				updateMap.put("level", String.valueOf(newLevel));

				userReference.update(updateMap);
				ret = true;
			}

		} catch (Exception e) {
			throw new FireBaseException("Error - " + e.getLocalizedMessage());
		}
		return ret;
	}

	@Override
	public Void addSeriesToUser(Series newSeries, User currentUser) throws FireBaseException {
		try {
			Firestore dataBase = FirestoreClient.getFirestore();

			ApiFuture<QuerySnapshot> query = dataBase.collection(COLLECTION_USERS)
					.whereEqualTo("name", currentUser.getName()).whereEqualTo("lastname", currentUser.getLastname())
					.get();

			QuerySnapshot querySnapshot = query.get();
			List<QueryDocumentSnapshot> users = querySnapshot.getDocuments();

			if (!users.isEmpty()) {
				DocumentSnapshot userSnapshot = users.get(0);
				DocumentReference userRef = userSnapshot.getReference();

				DocumentReference seriesRef = getSeriesRefByName(newSeries.getName());

				if (seriesRef == null) {
					throw new FireBaseException("Series reference not found for name: " + newSeries.getName());
				}

				DataConversionService dataConversionService = (DataConversionService) ServiceFactory.getInstance()
						.getService("dataConversionService");

				Map<String, Object> completedSeriesData = new HashMap<>();
				completedSeriesData.put("seriesRef", seriesRef);
				completedSeriesData.put("completedDate",
						dataConversionService.dateToStringFormatter(new java.util.Date()));

				userRef.collection("CompletedSeries").add(completedSeriesData);

			} else {
				throw new FireBaseException("User not found in Firestore.");
			}

		} catch (Exception e) {
			throw new FireBaseException("Error adding completed series: " + e.getLocalizedMessage());
		}

		return null;
	}

	private DocumentReference getSeriesRefByName(String seriesName) throws FireBaseException {
		try {
			Firestore dataBase = FirestoreClient.getFirestore();

			ApiFuture<QuerySnapshot> query = dataBase.collection(COLLECTION_SERIES).whereEqualTo("name", seriesName)
					.get();

			QuerySnapshot querySnapshot = query.get();
			List<QueryDocumentSnapshot> seriesDocs = querySnapshot.getDocuments();

			if (!seriesDocs.isEmpty()) {
				return seriesDocs.get(0).getReference();
			}
		} catch (Exception e) {
			throw new FireBaseException("Error fetching series reference: " + e.getLocalizedMessage());
		}
		return null;
	}

	public void clearCache() {
		cachedUsers = null;
		cachedWorkouts = null;
	}

}
