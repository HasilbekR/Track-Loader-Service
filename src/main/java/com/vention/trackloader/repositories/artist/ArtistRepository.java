package com.vention.trackloader.repositories.artist;

import com.vention.trackloader.models.artist.Artist;

import java.util.List;
import java.util.UUID;

public interface ArtistRepository {
    String GET_BY_NAME = "select * from artists where name = ?";
    String INSERT = "insert into artists(id, created_date, updated_date, is_blocked, name, url, playcount, listeners) values(?,?,?,?,?,?,?,?)";
    String DELETE_ALL = "DELETE FROM artists";
    String GET_ALL = "select * from artists";
    String GET_ARTIST_BY_ID = "select * from artists where id = ?";

    Artist getArtistByName(String name);

    Artist getArtistById(UUID id);

    void save(Artist artist);

    void deleteAll();

    List<Artist> getAll();
}
