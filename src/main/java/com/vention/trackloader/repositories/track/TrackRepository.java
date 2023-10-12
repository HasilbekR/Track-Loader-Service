package com.vention.trackloader.repositories.track;


import com.vention.trackloader.models.track.Track;

import java.util.List;

public interface TrackRepository {
    String INSERT = "insert into tracks(id, created_date, updated_date, is_blocked, name, url, playCount, listeners, duration, artist_id) values(?,?,?,?,?,?,?,?,?,?)";
    String DELETE_ALL = "delete from tracks";
    String GET_ALL = "select * from tracks";

    void save(Track track);

    void deleteAll();

    List<Track> getAll();

}
