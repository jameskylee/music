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

import com.acme.music.model.Album;
import com.acme.music.model.Song;
import com.acme.music.repository.AlbumRepository;
import com.acme.music.repository.SongRepository;

@RestController
public class AlbumService {

	@Autowired
	private AlbumRepository albumRepository;

	@Autowired
	private SongRepository songRepository;

	@RequestMapping("/album")
	public ResponseEntity<Iterable<Album>> findAll() {
		try {
			Iterable<Album> albums = albumRepository.findAll();
			return new ResponseEntity<Iterable<Album>>(albums, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Iterable<Album>>(HttpStatus.SERVICE_UNAVAILABLE);
	}

	@RequestMapping(value = "/album/{id}", method = RequestMethod.GET)
	public ResponseEntity<Album> findById(@PathVariable("id") long id) {
		try {
			Album album = albumRepository.findOne(id);
			return new ResponseEntity<Album>(album, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Album>(HttpStatus.SERVICE_UNAVAILABLE);
	}

	@RequestMapping(value = "/album/", method = RequestMethod.POST)
	public ResponseEntity<Album> createAlbum(@RequestBody Album album, UriComponentsBuilder ucBuilder) {
		try {
			album.setCreated(new Date());
			album.setLastModified(album.getCreated());
			albumRepository.save(album);
			return new ResponseEntity<Album>(album, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Album>(HttpStatus.SERVICE_UNAVAILABLE);
	}

	@RequestMapping(value = "/album/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Album> updateAlbum(@PathVariable("id") long id, @RequestBody Album album) {
		try {
			Album found = albumRepository.findOne(id);

			if (found == null) {
				return new ResponseEntity<Album>(album, HttpStatus.BAD_REQUEST);
			}

			found.setName(album.getName());
			found.setLastModified(new Date());

			albumRepository.save(found);
			return new ResponseEntity<Album>(found, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Album>(HttpStatus.SERVICE_UNAVAILABLE);
	}

	@RequestMapping(value = "/album/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Album> deleteAlbum(@PathVariable("id") long id) {
		try {
			Album album = albumRepository.findOne(id);
			if (album == null) {
				return new ResponseEntity<Album>(album, HttpStatus.NOT_FOUND);
			}
			albumRepository.delete(album);
			return new ResponseEntity<Album>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Album>(HttpStatus.SERVICE_UNAVAILABLE);
	}

	@RequestMapping(value = "/album_song/{albumid}/{songid}", method = RequestMethod.POST)
	public ResponseEntity<Album> addSongToAlbum(@PathVariable("albumid") long albumid, @PathVariable("songid") long songid) {
		try {
			Album album = albumRepository.findOne(albumid);
			Song song = songRepository.findOne(songid);
			if (album == null || song == null) {
				return new ResponseEntity<Album>(album, HttpStatus.NOT_FOUND);
			}
			album.getSongs().add(song);
			albumRepository.save(album);
			return new ResponseEntity<Album>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Album>(HttpStatus.SERVICE_UNAVAILABLE);
	}

	@RequestMapping(value = "/album_song/{albumid}/{songid}", method = RequestMethod.DELETE)
	public ResponseEntity<Album> removeSongFromAlbum(@PathVariable("albumid") long albumid, @PathVariable("songid") long songid) {
		try {
			Album album = albumRepository.findOne(albumid);
			Song song = songRepository.findOne(songid);
			if (album == null || song == null) {
				return new ResponseEntity<Album>(album, HttpStatus.NOT_FOUND);
			}
			album.getSongs().remove(song);
			albumRepository.save(album);
			return new ResponseEntity<Album>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Album>(HttpStatus.SERVICE_UNAVAILABLE);
	}
}
