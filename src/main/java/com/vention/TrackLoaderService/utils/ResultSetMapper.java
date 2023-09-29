package com.vention.TrackLoaderService.utils;

import com.vention.TrackLoaderService.model.artist.Artist;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.UUID;

public class ResultSetMapper {
    public static Artist mapArtist(ResultSet resultSet){
        try {
            Artist artist = Artist.builder()
                    .name(resultSet.getString("name"))
                    .url(resultSet.getString("url"))
                    .playcount(resultSet.getInt("playcount"))
                    .listeners(resultSet.getInt("listeners"))
                    .build();
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
