package org.example.repositories.artist;

import org.example.model.artist.Artist;
import org.example.utils.BeanUtil;

import java.sql.*;
import java.util.UUID;

public class ArtistRepositoryImpl implements ArtistRepository{
    private final Connection connection = BeanUtil.getConnection();

    @Override
    public Artist getArtistByName(String name) {
        try{
            PreparedStatement statement = connection.prepareStatement(GET_BY_NAME);
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()){
                return Artist.map(resultSet);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
    @Override
    public void save(Artist artist) {
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT);
            preparedStatement.setObject(1, artist.getId());
            preparedStatement.setObject(2, artist.getCreatedDate());
            preparedStatement.setObject(3, artist.getUpdatedDate());
            preparedStatement.setBoolean(4, artist.getIsBlocked());
            preparedStatement.setString(5, artist.getName());
            preparedStatement.setString(6, artist.getUrl());
            if (artist.getListeners() == null) {
                preparedStatement.setNull(7, Types.INTEGER);
            } else {
                preparedStatement.setInt(7, artist.getPlaycount());
            }
            if (artist.getListeners() == null) {
                preparedStatement.setNull(8, Types.INTEGER);
            } else {
                preparedStatement.setInt(8, artist.getListeners());
            }
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Artist artist) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE);
            preparedStatement.setObject(1, artist.getUpdatedDate());
            preparedStatement.setBoolean(2, artist.getIsBlocked());
            preparedStatement.setString(3, artist.getName());
            preparedStatement.setString(4, artist.getUrl());
            preparedStatement.setInt(5, artist.getPlaycount());
            preparedStatement.setInt(6, artist.getListeners());
            preparedStatement.setObject(7, artist.getId());
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

    @Override
    public Artist getArtistById(UUID id) {
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(GET_BY_ID);
            preparedStatement.setObject(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                return Artist.map(resultSet);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
