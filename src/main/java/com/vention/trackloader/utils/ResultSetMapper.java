package com.vention.trackloader.utils;

import com.vention.trackloader.models.artist.Artist;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.UUID;

public class ResultSetMapper {
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
}
