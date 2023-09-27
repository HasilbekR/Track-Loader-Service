package org.example.repositories.artist;


import org.example.model.artist.Artist;

import java.util.UUID;

public interface ArtistRepository {
    String GET_BY_NAME = "select * from artists where name = ?";
    String GET_BY_ID = "select * from artists where id = ?";
    String INSERT = "insert into artists(id, created_date, updated_date, is_blocked, name, url, playCount, listeners) values(?,?,?,?,?,?,?,?)";
    String UPDATE = "UPDATE artists SET updated_date=?, is_blocked=?, name=?, url=?, playCount=?, listeners=? WHERE id=?;";
    String DELETE_ALL = "DELETE FROM artists";

    Artist getArtistByName(String name);
    void save(Artist artist);
    void update(Artist artist);
    void deleteAll();
    Artist getArtistById(UUID id);
}
