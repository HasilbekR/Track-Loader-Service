package com.vention.TrackLoaderService.repositories.artist;

import com.vention.TrackLoaderService.model.artist.Artist;

public interface ArtistRepository {
    String GET_BY_NAME = "select * from artists where name = ?";
    String INSERT = "insert into artists(id, created_date, updated_date, is_blocked, name, url, playCount, listeners) values(?,?,?,?,?,?,?,?)";
    String DELETE_ALL = "DELETE FROM artists";

    Artist getArtistByName(String name);
    void save(Artist artist);
    void deleteAll();
}
