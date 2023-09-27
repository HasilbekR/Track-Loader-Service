package org.example.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.model.artist.Artist;
import org.example.model.track.Track;
import org.example.model.track.TrackWrapper;
import org.example.repositories.artist.ArtistRepository;
import org.example.repositories.artist.ArtistRepositoryImpl;
import org.example.repositories.track.TrackRepository;
import org.example.repositories.track.TrackRepositoryImpl;
import org.example.utils.BeanUtil;
import org.example.utils.DatabaseUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TrackService {
    private final TrackRepository trackRepository = new TrackRepositoryImpl();
    private final static ArtistRepository artistRepository = new ArtistRepositoryImpl();
    private final ObjectMapper objectMapper = BeanUtil.getObjectMapper();
    public Track save(Track track) {
        Artist artist = track.getArtist();
        Artist artistByName = artistRepository.getArtistByName(artist.getName());
        if (artistByName== null){
            artistRepository.save(artist);
            artistByName = artistRepository.getArtistByName(artist.getName());
        }
        track.setArtist(artistByName);

        Track trackByNameAndArtist = trackRepository.getTrackByNameAndArtist(track.getName(), track.getArtist().getId());
        if(trackByNameAndArtist != null) {
            track.setId(trackByNameAndArtist.getId());
            trackRepository.update(track);
            return trackRepository.getTrackByNameAndArtist(track.getName(), track.getArtist().getId());
        }

        trackRepository.save(track);
        return trackRepository.getTrackByNameAndArtist(track.getName(), track.getArtist().getId());
    }
    public List<Track> saveAll(List<Track> trackList){
        List<Track> tracks = new ArrayList<>();
        for (Track track : trackList) {
            tracks.add(save(track));
        }
        return tracks;
    }
    public String saveTopTracks(Integer page) throws IOException {
        DatabaseUtil.clearDatabase();
        String apiUrl = "http://ws.audioscrobbler.com/2.0/?format=json&method=chart.gettoptracks&api_key=5008d869491ef3e7bf342c431b66f220&page="+page;
        URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            try(BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                TrackWrapper trackWrapper = objectMapper.readValue(reader, TrackWrapper.class);
                List<Track> trackList = trackWrapper.getTracks().getTrack();
                List<Track> savedTracks = saveAll(trackList);
                return objectMapper.writeValueAsString(savedTracks);
            }
        }
        return null;
    }
    public String saveTopTracksByArtist(String artist, Integer page) throws IOException {
        DatabaseUtil.clearDatabase();
        String encodedArtist = URLEncoder.encode(artist, "UTF-8");
        String apiUrl = "http://ws.audioscrobbler.com/2.0/?format=json&method=artist.gettoptracks&api_key=5008d869491ef3e7bf342c431b66f220&artist="+encodedArtist+"&page="+page;
        URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            try(BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                TrackWrapper trackWrapper = objectMapper.readValue(reader, TrackWrapper.class);
                List<Track> trackList = trackWrapper.getToptracks().getTrack();
                List<Track> savedTracks = saveAll(trackList);
                return objectMapper.writeValueAsString(savedTracks);
            }
        }
        return null;
    }
    public static Track map(ResultSet resultSet) {
        try {
            Track track = Track.builder()
                    .name(resultSet.getString("name"))
                    .duration(resultSet.getInt("duration"))
                    .url(resultSet.getString("url"))
                    .playcount(resultSet.getInt("playcount"))
                    .listeners(resultSet.getInt("listeners"))
                    .artist(artistRepository.getArtistById(UUID.fromString(resultSet.getString("artist_id"))))
                    .build();
            Timestamp createdDate = resultSet.getTimestamp("created_date");
            track.setCreatedDate(createdDate.toLocalDateTime());
            Timestamp updatedDate = resultSet.getTimestamp("updated_date");
            track.setUpdatedDate(updatedDate.toLocalDateTime());
            track.setIsBlocked(resultSet.getBoolean("is_blocked"));
            track.setId(UUID.fromString(resultSet.getString("id")));
            return track;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

}
