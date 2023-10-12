package com.vention.trackloader.repositories.track;

import com.vention.trackloader.models.track.Track;
import com.vention.trackloader.utils.DatabaseUtils;
import com.vention.trackloader.utils.ResultSetMapper;
import com.vention.trackloader.utils.Utils;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class TrackRepositoryImpl implements TrackRepository {
    private final Connection connection = Utils.getConnection();

    @Override
    public void save(Track track) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT);
            PreparedStatement preparedStatement1 = DatabaseUtils.setValues(preparedStatement, track);
            preparedStatement1.setInt(9, Objects.requireNonNullElse(track.getDuration(), 0));
            preparedStatement1.setObject(10, track.getArtist().getId());
            preparedStatement1.executeUpdate();
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

    @Override
    public List<Track> getAll() {
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Track> tracks = new LinkedList<>();
            while (resultSet.next()) {
                tracks.add(ResultSetMapper.mapTrack(resultSet));
            }
            return tracks;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
