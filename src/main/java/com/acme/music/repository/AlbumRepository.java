package com.acme.music.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.acme.music.model.Album;

public interface AlbumRepository extends CrudRepository<Album, Long> {
	List<Album> findByName(String name);
}
