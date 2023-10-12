package com.vention.trackloader.utils;

import com.vention.trackloader.models.BaseModel;
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
        Artist artist = new Artist();
        map(resultSet, artist);
        return artist;
    }

    public static Track mapTrack(ResultSet resultSet) {
        try {
            Track track = new Track();
            map(resultSet, track);
            track.setDuration(resultSet.getInt("duration"));
            track.setArtist(artistService.getArtistById((UUID) resultSet.getObject("artist_id")));
            return track;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static void map(ResultSet resultSet, BaseModel baseModel) {
        try {
            baseModel.setName(resultSet.getString("name"));
            baseModel.setUrl(resultSet.getString("url"));
            baseModel.setPlaycount(resultSet.getInt("playcount"));
            baseModel.setListeners(resultSet.getInt("listeners"));
            Timestamp createdDate = resultSet.getTimestamp("created_date");
            baseModel.setCreatedDate(createdDate.toLocalDateTime());
            Timestamp updatedDate = resultSet.getTimestamp("updated_date");
            baseModel.setUpdatedDate(updatedDate.toLocalDateTime());
            baseModel.setIsBlocked(resultSet.getBoolean("is_blocked"));
            baseModel.setId(UUID.fromString(resultSet.getString("id")));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
