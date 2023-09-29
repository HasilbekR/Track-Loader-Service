package com.vention.TrackLoaderService.repositories.track;

import com.vention.TrackLoaderService.model.track.Track;
import com.vention.TrackLoaderService.utils.Utils;

import java.sql.*;

public class TrackRepositoryImpl implements TrackRepository{
    private final Connection connection = Utils.getConnection();
    @Override
    public void save(Track track) {
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT);
            preparedStatement.setObject(1, track.getId());
            preparedStatement.setObject(2, track.getCreatedDate());
            preparedStatement.setObject(3, track.getUpdatedDate());
            preparedStatement.setBoolean(4, track.getIsBlocked());
            preparedStatement.setString(5, track.getName());
            preparedStatement.setString(6, track.getUrl());
            if (track.getDuration() == null) {
                preparedStatement.setNull(7, Types.INTEGER);
            } else {
                preparedStatement.setInt(7, track.getDuration());
            }
            preparedStatement.setInt(8, track.getPlaycount());
            preparedStatement.setInt(9, track.getListeners());
            preparedStatement.setObject(10, track.getArtist().getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void deleteAll() {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_ALL);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
