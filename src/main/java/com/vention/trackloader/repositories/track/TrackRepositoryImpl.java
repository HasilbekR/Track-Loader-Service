package com.vention.trackloader.repositories.track;

import com.vention.trackloader.domain.models.track.Track;
import com.vention.trackloader.utils.DatabaseUtils;
import com.vention.trackloader.utils.ResultSetMapper;
import com.vention.trackloader.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class TrackRepositoryImpl implements TrackRepository {
    private final Connection connection = Utils.getConnection();
    private static final Logger log = LoggerFactory.getLogger(TrackRepositoryImpl.class);

    @Override
    public void save(Track track) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT);
            DatabaseUtils.setValues(preparedStatement, track);
            preparedStatement.setInt(9, Objects.requireNonNullElse(track.getDuration(), 0));
            preparedStatement.setObject(10, track.getArtist().getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            log.error("Error occurred while saving track", e);
        }
    }

    @Override
    public List<Track> getAll() {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_TRACKS);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Track> tracks = new LinkedList<>();
            while (resultSet.next()) {
                tracks.add(ResultSetMapper.mapTrack(resultSet));
            }
            return tracks;
        } catch (SQLException e) {
            log.error("Error occurred while retrieving tracks", e);
        }
        return null;
    }

    @Override
    public void deleteAll() {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_ALL);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            log.error("Error occurred while deleting tracks", e);
        }
    }
}
