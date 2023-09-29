package com.vention.TrackLoaderService.repositories.artist;

import com.vention.TrackLoaderService.model.artist.Artist;
import com.vention.TrackLoaderService.utils.ResultSetMapper;
import com.vention.TrackLoaderService.utils.Utils;

import java.sql.*;

public class ArtistRepositoryImpl implements ArtistRepository{
    private final Connection connection = Utils.getConnection();

    @Override
    public Artist getArtistByName(String name) {
        try{
            PreparedStatement statement = connection.prepareStatement(GET_BY_NAME);
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()){
                return ResultSetMapper.mapArtist(resultSet);
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
            if (artist.getPlaycount() == null) {
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
    public void deleteAll() {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_ALL);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
