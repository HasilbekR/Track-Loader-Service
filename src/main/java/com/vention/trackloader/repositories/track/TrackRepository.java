package com.vention.trackloader.repositories.track;


import com.vention.trackloader.domain.models.track.Track;

import java.util.List;

public interface TrackRepository {
    String GET_TRACKS = """
            select t.id as track_id, t.created_date as track_created_date, t.updated_date as track_updated_date, t.name as track_name, t.url as track_url, t.duration, t.playcount as track_playcount, t.listeners as track_listeners, t.is_blocked as track_is_blocked,
            a.id as artist_id, a.created_date as artist_created_date, a.updated_date as artist_updated_date, a.name as artist_name, a.url as artist_url, a.playcount as artist_playcount, a.listeners as artist_listeners, a.is_blocked as artist_is_blocked
            from tracks t inner join artists a on t.artist_id = a.id""";

    String INSERT = "insert into tracks(id, created_date, updated_date, is_blocked, name, url, playCount, listeners, duration, artist_id) values(?,?,?,?,?,?,?,?,?,?)";
    String DELETE_ALL = "delete from tracks";

    void save(Track track);

    List<Track> getAll();

    void deleteAll();

}
