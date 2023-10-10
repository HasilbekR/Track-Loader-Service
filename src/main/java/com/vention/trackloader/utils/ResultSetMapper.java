package com.vention.trackloader.utils;

import com.vention.trackloader.models.artist.Artist;
import com.vention.trackloader.models.track.Track;
import com.vention.trackloader.services.ArtistService;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.UUID;

public class ResultSetMapper {
    private final static ArtistService artistService = new ArtistService();
    public static Artist mapArtist(ResultSet resultSet) {
        try {
            Artist artist = new Artist();
            artist.setName(resultSet.getString("name"));
            artist.setUrl(resultSet.getString("url"));
            artist.setPlaycount(resultSet.getInt("playcount"));
            artist.setListeners(resultSet.getInt("listeners"));
            Timestamp createdDate = resultSet.getTimestamp("created_date");
            artist.setCreatedDate(createdDate.toLocalDateTime());
            Timestamp updatedDate = resultSet.getTimestamp("updated_date");
            artist.setUpdatedDate(updatedDate.toLocalDateTime());
            artist.setIsBlocked(resultSet.getBoolean("is_blocked"));
            artist.setId(UUID.fromString(resultSet.getString("id")));
            return artist;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static Track mapTrack(ResultSet resultSet){
        try {
            Track track = new Track();
            track.setName(resultSet.getString("name"));
            track.setUrl(resultSet.getString("url"));
            track.setPlaycount(resultSet.getInt("playcount"));
            track.setListeners(resultSet.getInt("listeners"));
            Timestamp createdDate = resultSet.getTimestamp("created_date");
            track.setCreatedDate(createdDate.toLocalDateTime());
            Timestamp updatedDate = resultSet.getTimestamp("updated_date");
            track.setUpdatedDate(updatedDate.toLocalDateTime());
            track.setIsBlocked(resultSet.getBoolean("is_blocked"));
            track.setId(UUID.fromString(resultSet.getString("id")));
            track.setDuration(resultSet.getInt("duration"));
            track.setArtist(artistService.getArtistById((UUID) resultSet.getObject("artist_id")));
            return track;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
