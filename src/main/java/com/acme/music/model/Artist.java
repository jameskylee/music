package com.acme.music.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Entity
public class Artist extends BaseModel{
	private String name;

	public Artist() {
	}
	
	public Artist(String name) {
		this.name = name;
		this.created = new Date();
		this.lastModified = this.created;
		this.albums = new ArrayList<Album>();
	}

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "ARTIST_SONG", joinColumns = @JoinColumn(name = "artist_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "album_id", referencedColumnName = "id"))
	private List<Album> albums;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Album> getAlbums() {
		return albums;
	}

	public void setAlbums(List<Album> albums) {
		this.albums = albums;
	}

	@Override
	public String toString() {
		return "Artist [id=" + id + ",name=" + name + ", created=" + created + ", lastModified=" 
				+ lastModified + ", albums=" + albums + "]";
	}
}