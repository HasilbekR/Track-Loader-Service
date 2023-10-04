package com.vention.trackloader.repositories.track;

import com.vention.trackloader.models.track.Track;
import com.vention.trackloader.repositories.artist.ArtistRepositoryImpl;
import com.vention.trackloader.utils.Utils;

import java.sql.*;
import java.util.Objects;

public class TrackRepositoryImpl implements TrackRepository {
    private final Connection connection = Utils.getConnection();

    @Override
    public void save(Track track) {
        try {
            PreparedStatement preparedStatement = ArtistRepositoryImpl.save(connection, INSERT, track.getId(), track.getCreatedDate(), track.getUpdatedDate(), track.getIsBlocked(), track.getName(), track.getUrl(), track.getPlaycount(), track.getListeners());
            preparedStatement.setInt(9, Objects.requireNonNullElse(track.getDuration(), 0));
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
