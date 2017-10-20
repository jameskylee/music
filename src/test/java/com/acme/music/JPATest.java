package com.acme.music;

import java.util.Iterator;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.acme.music.model.Album;
import com.acme.music.model.Artist;
import com.acme.music.model.Song;
import com.acme.music.repository.AlbumRepository;
import com.acme.music.repository.ArtistRepository;
import com.acme.music.repository.SongRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
public class JPATest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private SongRepository songRepository;

    @Autowired
    private AlbumRepository albumRepository;

    @Autowired
    private ArtistRepository artistRepository;

    @Test
    public void testSong() throws Exception {
        this.entityManager.persist(new Song(1, "Bang Bang"));
        this.entityManager.persist(new Song(2, "Toxic"));
        List<Song> songs = this.songRepository.findByName("Bang Bang");
        Iterator<Song> it = songs.iterator();
        while(it.hasNext()) {
            Assert.assertTrue(it.next().getName().equals("Bang Bang"));
        }
    }

    @Test
    public void testSongUniqueTrack() throws Exception {
        this.entityManager.persist(new Song(1, "Bang Bang"));
        try {
        	this.entityManager.persist(new Song(1, "Toxic"));
        }
        catch(Exception e) {
        	Assert.assertTrue(e instanceof Exception);
        }
    }

    @Test
    public void testAlbum() throws Exception {
        this.entityManager.persist(new Album("Last Sting", 2011));
        this.entityManager.persist(new Album("Great tour", 2017));
        List<Album> albums = this.albumRepository.findByName("Last Sting");
        Iterator<Album> it = albums.iterator();
        while(it.hasNext()) {
            Assert.assertTrue(it.next().getName().equals("Last Sting"));
        }
    }

    @Test
    public void testArtist() throws Exception {
        this.entityManager.persist(new Artist("Britney"));
        this.entityManager.persist(new Artist("Greenday"));
        List<Artist> artists = this.artistRepository.findByName("Greenday");
        Iterator<Artist> it = artists.iterator();
        while(it.hasNext()) {
            Assert.assertTrue(it.next().getName().equals("Greenday"));
        }
    }

    @Test
    public void testArtistWithChildren() throws Exception {
        Song bangbang = this.entityManager.persist(new Song(1, "Bang Bang"));
        Song toxic = this.entityManager.persist(new Song(2, "Toxic"));

        Album album = this.entityManager.persist(new Album("Great tour", 2017));
        album.getSongs().add(bangbang);
        album.getSongs().add(toxic);

        Artist artist = this.entityManager.persist(new Artist("Britney"));
        artist.getAlbums().add(album);

        List<Artist> artists = this.artistRepository.findByName("Britney");
        Iterator<Artist> it = artists.iterator();
        while(it.hasNext()) {
        	artist = it.next();
            Assert.assertTrue(artist.getName().equals("Britney"));
            Assert.assertTrue(artist.getAlbums().size() == 1);
            Assert.assertTrue(artist.getAlbums().get(0).getSongs().size() == 2);
        }
    }
}
