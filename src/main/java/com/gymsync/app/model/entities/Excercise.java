package com.gymsync.app.model.entities;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import com.google.cloud.firestore.DocumentReference;

public class Excercise implements Serializable {

	private static final long serialVersionUID = -2369886909442387537L;

	private String name = null;
	private String description = null;
	private int breakTime = 0;
	private List<Series> series = null;
	private transient DocumentReference workoutReference = null;
	private boolean completed = false;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getBreakTime() {
		return breakTime;
	}

	public void setBreakTime(int breakTime) {
		this.breakTime = breakTime;
	}

	public List<Series> getSeries() {
		return series;
	}

	public void setSeries(List<Series> series) {
		this.series = series;
	}

	public DocumentReference getWorkoutReference() {
		return workoutReference;
	}

	public void setWorkoutReference(DocumentReference workoutReference) {
		this.workoutReference = workoutReference;
	}

	public boolean isCompleted() {
		return completed;
	}

	public void setCompleted(boolean completed) {
		this.completed = completed;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public int hashCode() {
		return Objects.hash(breakTime, description, name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Excercise other = (Excercise) obj;
		return breakTime == other.breakTime && Objects.equals(description, other.description)
				&& Objects.equals(name, other.name);
	}

	@Override
	public String toString() {
		return "Excercise [name=" + name + ", description=" + description + ", breakTime=" + breakTime + ", series="
				+ series + ", workoutReference=" + workoutReference + ", completed=" + completed + "]";
	}

}
