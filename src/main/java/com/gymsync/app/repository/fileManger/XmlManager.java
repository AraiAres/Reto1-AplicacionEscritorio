package com.gymsync.app.repository.fileManger;

import java.io.File;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.gymsync.app.model.entities.Excercise;
import com.gymsync.app.model.entities.Series;
import com.gymsync.app.model.entities.User;
import com.gymsync.app.model.entities.Workout;

public class XmlManager {

	private static final String XML_PATH = "src/main/resources/xml/";
	private static final String USERS_FILE = "users.xml";
	private static final String WORKOUTS_FILE = "workouts.xml";

	public void saveUsers(List<User> userList) {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.newDocument();

			Element root = document.createElement("Users");
			document.appendChild(root);

			for (User user : userList) {
				Element userElement = document.createElement("User");

				appendTextElement(document, userElement, "name", user.getName());
				appendTextElement(document, userElement, "lastname", user.getLastname());
				appendTextElement(document, userElement, "email", user.getEmail());
				appendTextElement(document, userElement, "password", user.getPassword());
				appendTextElement(document, userElement, "birthDate",
						user.getBirthDate() != null ? user.getBirthDate().toString() : "");
				appendTextElement(document, userElement, "Level", String.valueOf(user.getLevel()));

				Element workoutsElement = document.createElement("Workouts");

				if (user.getWorkouts() != null) {
					for (Workout workout : user.getWorkouts()) {
						Element workoutElement = document.createElement("Workout");

						appendTextElement(document, workoutElement, "name", workout.getName());
						appendTextElement(document, workoutElement, "NExcercises",
								String.valueOf(workout.getNExcercises()));
						appendTextElement(document, workoutElement, "Level", String.valueOf(workout.getLevel()));
						appendTextElement(document, workoutElement, "urlVideo", workout.getUrlVideo());

						Element excercisesElement = document.createElement("Excercises");

						if (workout.getExcercises() != null) {
							for (Excercise exc : workout.getExcercises()) {
								Element excElement = document.createElement("Excercise");

								appendTextElement(document, excElement, "name", exc.getName());
								appendTextElement(document, excElement, "description", exc.getDescription());
								appendTextElement(document, excElement, "breakTime",
										String.valueOf(exc.getBreakTime()));
								appendTextElement(document, excElement, "completed", String.valueOf(exc.isCompleted()));

								Element seriesList = document.createElement("SeriesList");

								if (exc.getSeries() != null) {
									for (Series series : exc.getSeries()) {
										Element seriesElement = getSeriesElement(document, series);
										seriesList.appendChild(seriesElement);
									}
								}

								excElement.appendChild(seriesList);
								excercisesElement.appendChild(excElement);
							}
						}

						workoutElement.appendChild(excercisesElement);
						workoutsElement.appendChild(workoutElement);
					}
				}

				userElement.appendChild(workoutsElement);
				root.appendChild(userElement);
			}

			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();

			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

			DOMSource source = new DOMSource(document);
			StreamResult result = new StreamResult(new File(XML_PATH + USERS_FILE));
			transformer.transform(source, result);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error de XML: " + e.getMessage());
		}
	}

	public void saveWorkouts(List<Workout> workoutList) {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.newDocument();

			Element root = document.createElement("Workouts");
			document.appendChild(root);

			for (Workout workout : workoutList) {
				Element workoutElement = document.createElement("Workout");

				appendTextElement(document, workoutElement, "name", workout.getName());
				appendTextElement(document, workoutElement, "NExcercises", String.valueOf(workout.getNExcercises()));
				appendTextElement(document, workoutElement, "Level", String.valueOf(workout.getLevel()));
				appendTextElement(document, workoutElement, "urlVideo", workout.getUrlVideo());

				Element excercisesElement = document.createElement("Excercises");

				if (workout.getExcercises() != null) {
					for (Excercise exc : workout.getExcercises()) {
						Element excElement = document.createElement("Excercise");

						appendTextElement(document, excElement, "name", exc.getName());
						appendTextElement(document, excElement, "description", exc.getDescription());
						appendTextElement(document, excElement, "breakTime", String.valueOf(exc.getBreakTime()));
						appendTextElement(document, excElement, "completed", String.valueOf(exc.isCompleted()));

						Element seriesList = document.createElement("SeriesList");

						if (exc.getSeries() != null) {
							for (Series series : exc.getSeries()) {
								Element seriesElement = getSeriesElement(document, series);
								seriesList.appendChild(seriesElement);
							}
						}

						excElement.appendChild(seriesList);
						excercisesElement.appendChild(excElement);
					}
				}

				workoutElement.appendChild(excercisesElement);
				root.appendChild(workoutElement);
			}

			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();

			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

			DOMSource source = new DOMSource(document);
			StreamResult result = new StreamResult(new File(XML_PATH + WORKOUTS_FILE));
			transformer.transform(source, result);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error de XML: " + e.getMessage());
		}
	}

	private Element getSeriesElement(Document document, Series series) {
		Element seriesElement = document.createElement("Series");

		appendTextElement(document, seriesElement, "name", series.getName());
		appendTextElement(document, seriesElement, "estimatedDuration", String.valueOf(series.getEstimatedDuration()));
		appendTextElement(document, seriesElement, "repetitionCount", String.valueOf(series.getRepetitionCount()));
		appendTextElement(document, seriesElement, "completionDate",
				series.getCompletionDate() != null ? series.getCompletionDate().toString() : "");
		appendTextElement(document, seriesElement, "completed", String.valueOf(series.isCompleted()));

		return seriesElement;
	}

	private void appendTextElement(Document doc, Element parent, String tag, String value) {
		Element element = doc.createElement(tag);
		element.appendChild(doc.createTextNode(value != null ? value : ""));
		parent.appendChild(element);
	}

}
