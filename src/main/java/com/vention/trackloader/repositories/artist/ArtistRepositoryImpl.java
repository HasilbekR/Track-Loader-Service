package com.vention.trackloader.repositories.artist;

import com.vention.trackloader.domain.models.artist.Artist;
import com.vention.trackloader.utils.DatabaseUtils;
import com.vention.trackloader.utils.ResultSetMapper;
import com.vention.trackloader.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class ArtistRepositoryImpl implements ArtistRepository {
    private final Connection connection = Utils.getConnection();
    private static final Logger log = LoggerFactory.getLogger(ArtistRepositoryImpl.class);


    @Override
    public void save(Artist artist) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT);
            DatabaseUtils.setValues(preparedStatement, artist);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            log.error("Error occurred while saving artist", e);
        }
    }

    @Override
    public Artist getArtistByName(String name) {
        try {
            PreparedStatement statement = connection.prepareStatement(GET_BY_NAME);
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return ResultSetMapper.mapArtist(resultSet);
            }
        } catch (SQLException e) {
            log.error("Error occurred while retrieving artist by name: " + name, e);
        }
        return null;
    }

    @Override
    public List<Artist> getAll() {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_ARTIST);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Artist> artists = new LinkedList<>();
            while (resultSet.next()) {
                artists.add(ResultSetMapper.mapArtist(resultSet));
            }
            return artists;
        } catch (SQLException e) {
            log.error("Error occurred while retrieving artists", e);
        }
        return null;
    }

    @Override
    public void deleteAll() {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_ALL);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            log.error("Error occurred while deleting artists", e);
        }
    }
}
