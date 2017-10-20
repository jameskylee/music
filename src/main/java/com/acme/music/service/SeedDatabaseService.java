package com.acme.music.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.acme.music.model.Album;
import com.acme.music.model.Artist;
import com.acme.music.model.Song;
import com.acme.music.repository.AlbumRepository;
import com.acme.music.repository.ArtistRepository;
import com.acme.music.repository.SongRepository;

@RestController
public class SeedDatabaseService {

	@Autowired
	private ArtistRepository artistRepository;

	@Autowired
	private AlbumRepository albumRepository;

	@Autowired
	private SongRepository songRepository;

	@RequestMapping("/init")
	public void init() {
		try {
			Song windOfChange = songRepository.save(new Song(1, "Wind of change"));
			Song stillLoveingYou = songRepository.save(new Song(2, "Still loving you"));
			Song noOneLikeYou = songRepository.save(new Song(3, "No one like you"));
	
			Song toxic = songRepository.save(new Song(1, "Toxic"));
			Song gimmeMore = songRepository.save(new Song(2, "Gimme more"));
			Song everyTime = songRepository.save(new Song(3, "Every time"));
	
			albumRepository.findAll().toString();
			Album lastSting = albumRepository.save(new Album("Last sting", 2013));
			lastSting.getSongs().add(windOfChange);
			lastSting.getSongs().add(stillLoveingYou);
			lastSting.getSongs().add(noOneLikeYou);
	
			Album glory = albumRepository.save(new Album("Glory", 2016));
			glory.getSongs().add(toxic);
			glory.getSongs().add(gimmeMore);
			glory.getSongs().add(everyTime);
	
			Artist scorpions = artistRepository.save(new Artist("Scorpions"));
			scorpions.getAlbums().add(lastSting);
			artistRepository.save(scorpions);
	
			Artist britney = artistRepository.save(new Artist("Britney Spears"));
			britney.getAlbums().add(glory);
	
			artistRepository.save(britney);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
}
