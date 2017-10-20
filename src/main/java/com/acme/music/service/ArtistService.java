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
import com.acme.music.model.Artist;
import com.acme.music.repository.AlbumRepository;
import com.acme.music.repository.ArtistRepository;

@RestController
public class ArtistService {

	@Autowired
	private ArtistRepository artistRepository;

	@Autowired
	private AlbumRepository albumRepository;

	@RequestMapping("/artist")
	public ResponseEntity<Iterable<Artist>> findAll() {
		try {
			Iterable<Artist> artists = artistRepository.findAll();
			if (artists == null) {
				return new ResponseEntity<Iterable<Artist>>(artists, HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<Iterable<Artist>>(artists, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Iterable<Artist>>(HttpStatus.SERVICE_UNAVAILABLE);
	}

	@RequestMapping(value = "/artist/{id}", method = RequestMethod.GET)
	public ResponseEntity<Artist> findById(@PathVariable("id") long id) {
		try {
			Artist artist = artistRepository.findOne(id);
			return new ResponseEntity<Artist>(artist, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Artist>(HttpStatus.SERVICE_UNAVAILABLE);
	}

	@RequestMapping(value = "/artist/", method = RequestMethod.POST)
	public ResponseEntity<Artist> createArtist(@RequestBody Artist artist, UriComponentsBuilder ucBuilder) {
		try {
			artist.setCreated(new Date());
			artist.setLastModified(artist.getCreated());
			artistRepository.save(artist);
			return new ResponseEntity<Artist>(artist, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Artist>(HttpStatus.SERVICE_UNAVAILABLE);
	}

	@RequestMapping(value = "/artist/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Artist> updateArtist(@PathVariable("id") long id, @RequestBody Artist artist) {
		try {
			Artist found = artistRepository.findOne(id);

			if (found == null) {
				return new ResponseEntity<Artist>(artist, HttpStatus.BAD_REQUEST);
			}

			found.setName(artist.getName());
			found.setLastModified(new Date());

			artistRepository.save(found);
			return new ResponseEntity<Artist>(found, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Artist>(HttpStatus.SERVICE_UNAVAILABLE);
	}

	@RequestMapping(value = "/artist/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Artist> deleteArtist(@PathVariable("id") long id) {
		try {
			Artist artist = artistRepository.findOne(id);
			if (artist == null) {
				return new ResponseEntity<Artist>(artist, HttpStatus.NOT_FOUND);
			}
			artistRepository.delete(artist);
			return new ResponseEntity<Artist>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Artist>(HttpStatus.SERVICE_UNAVAILABLE);
	}

	@RequestMapping(value = "/artist_album/{artistid}/{albumid}", method = RequestMethod.POST)
	public ResponseEntity<Artist> addAlbumToArtist(@PathVariable("artistid") long artistid, @PathVariable("albumid") long albumid) {
		try {
			Artist artist = artistRepository.findOne(artistid);
			Album album = albumRepository.findOne(albumid);
			if (artist == null || album == null) {
				return new ResponseEntity<Artist>(artist, HttpStatus.NOT_FOUND);
			}
			artist.getAlbums().add(album);
			artistRepository.save(artist);
			return new ResponseEntity<Artist>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Artist>(HttpStatus.SERVICE_UNAVAILABLE);
	}

	@RequestMapping(value = "/artist_album/{artistid}/{albumid}", method = RequestMethod.DELETE)
	public ResponseEntity<Artist> removeAlbumFromArtist(@PathVariable("artistid") long artistid, @PathVariable("albumid") long albumid) {
		try {
			Artist artist = artistRepository.findOne(artistid);
			Album album = albumRepository.findOne(albumid);
			if (artist == null || album == null) {
				return new ResponseEntity<Artist>(artist, HttpStatus.NOT_FOUND);
			}
			artist.getAlbums().remove(album);
			artistRepository.save(artist);
			return new ResponseEntity<Artist>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Artist>(HttpStatus.SERVICE_UNAVAILABLE);
	}
}
