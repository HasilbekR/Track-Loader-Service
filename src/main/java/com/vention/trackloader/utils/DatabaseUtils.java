package com.vention.trackloader.utils;

import com.vention.trackloader.models.BaseModel;
import com.vention.trackloader.repositories.artist.ArtistRepositoryImpl;
import com.vention.trackloader.repositories.track.TrackRepository;
import com.vention.trackloader.repositories.track.TrackRepositoryImpl;
import com.vention.trackloader.repositories.artist.ArtistRepository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

public class DatabaseUtils {
    private static final TrackRepository trackRepository = new TrackRepositoryImpl();
    private static final ArtistRepository artistRepository = new ArtistRepositoryImpl();

    public static void clearArtistTable() {
        artistRepository.deleteAll();
    }

    public static void clearTrackTable() {
        trackRepository.deleteAll();
    }

    public static void setValues(PreparedStatement preparedStatement, BaseModel baseModel) throws SQLException {
        preparedStatement.setObject(1, baseModel.getId());
        preparedStatement.setObject(2, baseModel.getCreatedDate());
        preparedStatement.setObject(3, baseModel.getUpdatedDate());
        preparedStatement.setBoolean(4, baseModel.getIsBlocked());
        preparedStatement.setString(5, baseModel.getName());
        preparedStatement.setString(6, baseModel.getUrl());
        if (baseModel.getPlaycount() != null) {
            preparedStatement.setInt(7, baseModel.getPlaycount());
        } else {
            preparedStatement.setNull(7, Types.INTEGER);
        }
        if (baseModel.getListeners() != null) {
            preparedStatement.setInt(8, baseModel.getListeners());
        } else {
            preparedStatement.setNull(8, Types.INTEGER);
        }
    }

}
