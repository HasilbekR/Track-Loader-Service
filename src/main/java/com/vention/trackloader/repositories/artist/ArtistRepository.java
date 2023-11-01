package com.vention.trackloader.repositories.artist;

import com.vention.trackloader.domain.models.artist.Artist;

import java.util.List;

public interface ArtistRepository {
    String GET_ARTIST = "select id as artist_id, created_date as artist_created_date, updated_date as artist_updated_date, name as artist_name, url as artist_url, playcount as artist_playcount, listeners as artist_listeners, is_blocked as artist_is_blocked from artists ";

    String INSERT = "insert into artists(id, created_date, updated_date, is_blocked, name, url, playcount, listeners) values(?,?,?,?,?,?,?,?)";
    String GET_BY_NAME = GET_ARTIST + "where name = ?";
    String DELETE_ALL = "DELETE FROM artists";

    void save(Artist artist);

    Artist getArtistByName(String name);

    List<Artist> getAll();

    void deleteAll();
}
