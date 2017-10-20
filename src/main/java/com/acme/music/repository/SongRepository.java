package com.acme.music.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.acme.music.model.Song;

public interface SongRepository extends CrudRepository<Song, Long> {
	List<Song> findByName(String name);
}
