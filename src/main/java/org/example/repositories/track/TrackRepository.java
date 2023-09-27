package org.example.repositories.track;


import org.example.model.track.Track;

import java.util.UUID;

public interface TrackRepository {
    String GET_TRACK = "select * from tracks where name =? and artist_id =?";
    String INSERT = "insert into tracks(id, created_date, updated_date, is_blocked, name, url, duration, playCount, listeners, artist_id) values(?,?,?,?,?,?,?,?,?,?)";
    String UPDATE = "update tracks set updated_date =?, is_blocked =?, name =?, url =?, playCount =?, listeners =?, artist_id =? where id =?;";
    String DELETE_ALL = "delete from tracks";
    Track getTrackByNameAndArtist(String name, UUID artistId);
    void save(Track track);
    void update(Track track);
    void deleteAll();

}
