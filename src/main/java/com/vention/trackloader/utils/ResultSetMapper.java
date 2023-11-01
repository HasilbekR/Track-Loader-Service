package com.vention.trackloader.utils;

import com.vention.trackloader.domain.models.artist.Artist;
import com.vention.trackloader.domain.models.track.Track;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.UUID;

public class ResultSetMapper {
    private static final Logger log = LoggerFactory.getLogger(ResultSetMapper.class);

    public static Artist mapArtist(ResultSet resultSet) {
        try {
            Artist artist = new Artist();
            artist.setId(UUID.fromString(resultSet.getString("artist_id")));
            Timestamp createdDate = resultSet.getTimestamp("artist_created_date");
            artist.setCreatedDate(createdDate.toLocalDateTime());
            Timestamp updatedDate = resultSet.getTimestamp("artist_updated_date");
            artist.setUpdatedDate(updatedDate.toLocalDateTime());
            artist.setIsBlocked(resultSet.getBoolean("artist_is_blocked"));
            artist.setName(resultSet.getString("artist_name"));
            artist.setUrl(resultSet.getString("artist_url"));
            artist.setPlaycount(resultSet.getInt("artist_playcount"));
            artist.setListeners(resultSet.getInt("artist_listeners"));
            return artist;
        } catch (SQLException e) {
            log.error("Error occurred while mapping track", e);
        }
        return null;
    }

    public static Track mapTrack(ResultSet resultSet) {
        try {
            Track track = new Track();
            track.setId(UUID.fromString(resultSet.getString("track_id")));
            Timestamp createdDate = resultSet.getTimestamp("track_created_date");
            track.setCreatedDate(createdDate.toLocalDateTime());
            Timestamp updatedDate = resultSet.getTimestamp("track_updated_date");
            track.setUpdatedDate(updatedDate.toLocalDateTime());
            track.setIsBlocked(resultSet.getBoolean("track_is_blocked"));
            track.setName(resultSet.getString("track_name"));
            track.setUrl(resultSet.getString("track_url"));
            track.setPlaycount(resultSet.getInt("track_playcount"));
            track.setListeners(resultSet.getInt("track_listeners"));
            track.setDuration(resultSet.getInt("duration"));
            track.setArtist(mapArtist(resultSet));
            return track;
        } catch (SQLException e) {
            log.error("Error occurred while mapping track", e);
        }
        return null;
    }
}
