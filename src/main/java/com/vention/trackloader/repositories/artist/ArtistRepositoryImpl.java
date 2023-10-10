package com.vention.trackloader.repositories.artist;

import com.vention.trackloader.models.artist.Artist;
import com.vention.trackloader.utils.ResultSetMapper;
import com.vention.trackloader.utils.Utils;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public class ArtistRepositoryImpl implements ArtistRepository {
    private final Connection connection = Utils.getConnection();

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
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public Artist getArtistById(UUID id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_ARTIST_BY_ID);
            preparedStatement.setObject(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                return ResultSetMapper.mapArtist(resultSet);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public void save(Artist artist) {
        try {
            PreparedStatement preparedStatement = save(connection, INSERT, artist.getId(), artist.getCreatedDate(), artist.getUpdatedDate(), artist.getIsBlocked(), artist.getName(), artist.getUrl(), artist.getPlaycount(), artist.getListeners());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static PreparedStatement save(Connection connection, String insert, UUID id, LocalDateTime createdDate, LocalDateTime updatedDate, Boolean isBlocked, String name, String url, Integer playcount, Integer listeners) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(insert);
        preparedStatement.setObject(1, id);
        preparedStatement.setObject(2, createdDate);
        preparedStatement.setObject(3, updatedDate);
        preparedStatement.setBoolean(4, isBlocked);
        preparedStatement.setString(5, name);
        preparedStatement.setString(6, url);
        if(playcount != null){
            preparedStatement.setInt(7, playcount);
        }else{
            preparedStatement.setNull(7,Types.INTEGER);
        }
        if(listeners != null){
            preparedStatement.setInt(8, listeners);
        }else {
            preparedStatement.setNull(8, Types.INTEGER);
        }
        return preparedStatement;
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
    public List<Artist> getAll() {
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Artist> artists = new LinkedList<>();
            while (resultSet.next()) {
                artists.add(ResultSetMapper.mapArtist(resultSet));
            }
            return artists;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
