package com.acme.music.model;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Song extends BaseModel
{
	@Column(unique=true)
	int track;
	String name;
	public Song() {
	}

	public Song(int track, String name) {
		this.track = track;
		this.name = name;
	}
	public int getTrack() {
		return track;
	}
	public void setTrack(int track) {
		this.track = track;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return "Song [track=" + track + ", name=" + name + ", id=" + id + ", created=" + created + ", lastModified="
				+ lastModified + "]";
	}
}
