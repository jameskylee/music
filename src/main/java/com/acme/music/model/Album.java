package com.acme.music.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Entity
public class Album extends BaseModel {
	private String name;
	private int yearReleased;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "ALBUM_SONG", joinColumns = @JoinColumn(name = "album_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "song_id", referencedColumnName = "id"))
	private List<Song> songs;

	public Album() {
	}

	public Album(String name, int yearReleased) {
		this.name = name;
		this.yearReleased = yearReleased;
		this.songs = new ArrayList<Song>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getYearReleased() {
		return yearReleased;
	}

	public void setYearReleased(int yearReleased) {
		this.yearReleased = yearReleased;
	}

	public List<Song> getSongs() {
		return songs;
	}

	public void setSongs(List<Song> songs) {
		this.songs = songs;
	}

	@Override
	public String toString() {
		return "Album [id=" + id + ", name=" + name + ", yearReleased=" + yearReleased + ", created=" + created + ", lastModified=" + lastModified + ", songs=" + songs + "]";
	}
}