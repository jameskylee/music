package com.acme.music.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.acme.music.model.Artist;

public interface ArtistRepository extends CrudRepository<Artist, Long> {
	List<Artist> findByName(String name);
}
