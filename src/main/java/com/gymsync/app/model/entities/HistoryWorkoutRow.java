package com.gymsync.app.model.entities;

import java.io.Serializable;
import java.util.Objects;

public class HistoryWorkoutRow implements Serializable {

	private static final long serialVersionUID = -5773544521021040626L;

	private String name;
	private int level;
	private String totalDuration; 
	private String estimatedDuration;
	private String completionDate;
	private String completedPercentage;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getTotalDuration() {
		return totalDuration;
	}

	public void setTotalDuration(String totalDuration) {
		this.totalDuration = totalDuration;
	}

	public String getEstimatedDuration() {
		return estimatedDuration;
	}

	public void setEstimatedDuration(String estimatedDuration) {
		this.estimatedDuration = estimatedDuration;
	}

	public String getCompletionDate() {
		return completionDate;
	}

	public void setCompletionDate(String completionDate) {
		this.completionDate = completionDate;
	}

	public String getCompletedPercentage() {
		return completedPercentage;
	}

	public void setCompletedPercentage(String completedPercentage) {
		this.completedPercentage = completedPercentage;
	}

	@Override
	public int hashCode() {
		return Objects.hash(name, level, totalDuration, estimatedDuration, completionDate, completedPercentage);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		HistoryWorkoutRow other = (HistoryWorkoutRow) obj;
		return level == other.level && Objects.equals(name, other.name)
				&& Objects.equals(totalDuration, other.totalDuration)
				&& Objects.equals(estimatedDuration, other.estimatedDuration)
				&& Objects.equals(completionDate, other.completionDate)
				&& Objects.equals(completedPercentage, other.completedPercentage);
	}

	@Override
	public String toString() {
		return "HistoryWorkoutRow [name=" + name + ", level=" + level + ", totalDuration=" + totalDuration
				+ ", estimatedDuration=" + estimatedDuration + ", completionDate=" + completionDate
				+ ", completedPercentage=" + completedPercentage + "]";
	}
}
