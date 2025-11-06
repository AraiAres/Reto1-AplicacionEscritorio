package com.gymsync.app.model.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import com.google.cloud.firestore.DocumentReference;

public class Series implements Serializable {

	private static final long serialVersionUID = -2255756363389598217L;

	private String name = null;
	private String icon = null;
	private int estimatedDuration = 0;
	private int repetitionCount = 0;
	private Date completionDate = null;
	private transient DocumentReference exerciseRef = null;
	private boolean completed = false;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public int getEstimatedDuration() {
		return estimatedDuration;
	}

	public void setEstimatedDuration(int estimatedDuration) {
		this.estimatedDuration = estimatedDuration;
	}

	public int getRepetitionCount() {
		return repetitionCount;
	}

	public void setRepetitionCount(int repetitionCount) {
		this.repetitionCount = repetitionCount;
	}

	public Date getCompletionDate() {
		return completionDate;
	}

	public void setCompletionDate(Date completionDate) {
		this.completionDate = completionDate;
	}

	public DocumentReference getExerciseRef() {
		return exerciseRef;
	}

	public void setExerciseRef(DocumentReference exerciseRef) {
		this.exerciseRef = exerciseRef;
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
		return Objects.hash(estimatedDuration, exerciseRef, name, repetitionCount);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Series other = (Series) obj;
		return estimatedDuration == other.estimatedDuration && Objects.equals(exerciseRef, other.exerciseRef)
				&& Objects.equals(name, other.name) && repetitionCount == other.repetitionCount;
	}

	@Override
	public String toString() {
		return "Series [name=" + name + ", icon=" + icon + ", estimatedDuration=" + estimatedDuration
				+ ", repetitionCount=" + repetitionCount + ", completionDate=" + completionDate + ", completed="
				+ completed + "]";
	}
}
