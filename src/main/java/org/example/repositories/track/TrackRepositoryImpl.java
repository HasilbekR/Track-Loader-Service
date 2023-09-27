package org.example.repositories.track;

import org.example.model.track.Track;
import org.example.service.TrackService;
import org.example.utils.BeanUtil;

import java.sql.*;
import java.util.UUID;

public class TrackRepositoryImpl implements TrackRepository{
    private final Connection connection = BeanUtil.getConnection();
    @Override
    public Track getTrackByNameAndArtist(String name, UUID artistId) {
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(GET_TRACK);
            preparedStatement.setString(1, name);
            preparedStatement.setObject(2, artistId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                return TrackService.map(resultSet);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
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
    public void update(Track track) {
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE);
            preparedStatement.setObject(1, track.getUpdatedDate());
            preparedStatement.setBoolean(2, track.getIsBlocked());
            preparedStatement.setString(3, track.getName());
            preparedStatement.setString(4, track.getUrl());
            preparedStatement.setInt(5, track.getPlaycount());
            preparedStatement.setInt(6, track.getListeners());
            preparedStatement.setObject(7, track.getArtist().getId());
            preparedStatement.setObject(8, track.getId());
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
