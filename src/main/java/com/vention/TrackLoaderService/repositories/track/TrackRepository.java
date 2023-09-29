package com.vention.TrackLoaderService.repositories.track;


import com.vention.TrackLoaderService.model.track.Track;

public interface TrackRepository {
    String INSERT = "insert into tracks(id, created_date, updated_date, is_blocked, name, url, duration, playCount, listeners, artist_id) values(?,?,?,?,?,?,?,?,?,?)";
    String DELETE_ALL = "delete from tracks";
    void save(Track track);
    void deleteAll();

}
