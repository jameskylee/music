package com.acme.music.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.acme.music.model.Song;
import com.acme.music.repository.SongRepository;

@RestController
public class SongService {

	@Autowired
	private SongRepository songRepository;

	@RequestMapping("/song")
	public ResponseEntity<Iterable<Song>> findAll() {
		try {
			Iterable<Song> songs = songRepository.findAll();
			if (songs == null) {
				return new ResponseEntity<Iterable<Song>>(songs, HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<Iterable<Song>>(songs, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Iterable<Song>>(HttpStatus.SERVICE_UNAVAILABLE);
	}

	@RequestMapping(value = "/song/{id}", method = RequestMethod.GET)
	public ResponseEntity<Song> findById(@PathVariable("id") long id) {
		try {
			Song song = songRepository.findOne(id);
			return new ResponseEntity<Song>(song, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Song>(HttpStatus.SERVICE_UNAVAILABLE);
	}

	@RequestMapping(value = "/song/", method = RequestMethod.POST)
	public ResponseEntity<Song> createSong(@RequestBody Song song, UriComponentsBuilder ucBuilder) {
		try {
			song.setCreated(new Date());
			song.setLastModified(song.getCreated());
			songRepository.save(song);
			return new ResponseEntity<Song>(song, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Song>(HttpStatus.SERVICE_UNAVAILABLE);
	}

	@RequestMapping(value = "/song/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Song> updateSong(@PathVariable("id") long id, @RequestBody Song song) {
		try {
			Song found = songRepository.findOne(id);

			if (found == null) {
				return new ResponseEntity<Song>(song, HttpStatus.BAD_REQUEST);
			}

			found.setName(song.getName());
			found.setLastModified(new Date());

			songRepository.save(found);
			return new ResponseEntity<Song>(found, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Song>(HttpStatus.SERVICE_UNAVAILABLE);
	}

	@RequestMapping(value = "/song/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Song> deleteSong(@PathVariable("id") long id) {
		try {
			Song song = songRepository.findOne(id);
			if (song == null) {
				return new ResponseEntity<Song>(song, HttpStatus.NOT_FOUND);
			}
			songRepository.delete(song);
			return new ResponseEntity<Song>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Song>(HttpStatus.SERVICE_UNAVAILABLE);
	}
}
