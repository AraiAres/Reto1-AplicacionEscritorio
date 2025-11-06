package com.gymsync.app.model.entities;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class Workout implements Serializable {

	private static final long serialVersionUID = 2848141802099740650L;

	private String name = null;
	private Integer NExcercises = null;
	private Integer Level = null;
	private String urlVideo = null;
	private List<Excercise> Excercises = null;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getNExcercises() {
		return NExcercises;
	}

	public void setNExcercises(Integer nExcercises) {
		NExcercises = nExcercises;
	}

	public Integer getLevel() {
		return Level;
	}

	public void setLevel(Integer level) {
		Level = level;
	}

	public String getUrlVideo() {
		return urlVideo;
	}

	public void setUrlVideo(String urlVideo) {
		this.urlVideo = urlVideo;
	}

	public List<Excercise> getExcercises() {
		return Excercises;
	}

	public void setExcercises(List<Excercise> excercises) {
		Excercises = excercises;
	}

	@Override
	public int hashCode() {
		return Objects.hash(Level, NExcercises, name, urlVideo);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Workout other = (Workout) obj;
		return Objects.equals(Excercises, other.Excercises) && Objects.equals(Level, other.Level)
				&& Objects.equals(name, other.name) && Objects.equals(urlVideo, other.urlVideo);
	}

	@Override
	public String toString() {
		return "Workout [name=" + name + ", NExcercises=" + NExcercises + ", Level=" + Level + ", urlVideo=" + urlVideo
				+ ", Excercises=" + Excercises + "]";
	}

}
