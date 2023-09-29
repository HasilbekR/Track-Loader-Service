package com.vention.TrackLoaderService.service;

import com.vention.TrackLoaderService.model.artist.Artist;
import com.vention.TrackLoaderService.model.track.TrackWrapper;
import com.vention.TrackLoaderService.repositories.artist.ArtistRepository;
import com.vention.TrackLoaderService.repositories.artist.ArtistRepositoryImpl;
import com.vention.TrackLoaderService.repositories.track.TrackRepository;
import com.vention.TrackLoaderService.repositories.track.TrackRepositoryImpl;
import com.vention.TrackLoaderService.utils.DatabaseUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vention.TrackLoaderService.model.track.Track;
import com.vention.TrackLoaderService.utils.Utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class TrackService {
    private final TrackRepository trackRepository = new TrackRepositoryImpl();
    private final ArtistRepository artistRepository = new ArtistRepositoryImpl();
    private final ObjectMapper objectMapper = Utils.getObjectMapper();
    private final ArtistService artistService = new ArtistService();
    public Track save(Track track) {
        Artist artist = track.getArtist();
        Artist artistByName = artistRepository.getArtistByName(artist.getName());
        if (artistByName== null){
            artistByName = artistService.save(artist);
        }
        track.setArtist(artistByName);
        trackRepository.save(track);
        return track;
    }
    public List<Track> saveAll(List<Track> trackList){
        List<Track> tracks = new ArrayList<>();
        for (Track track : trackList) {
            tracks.add(save(track));
        }
        return tracks;
    }
    public String saveTopTracks(Integer page) throws IOException {
        DatabaseUtils.clearDatabase();
        String apiUrl = Utils.getUrl()+"&method=chart.gettoptracks&page="+page;
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
        DatabaseUtils.clearDatabase();
        String encodedArtist = URLEncoder.encode(artist, StandardCharsets.UTF_8);
        String apiUrl = Utils.getUrl()+"&method=artist.gettoptracks"+"&artist="+encodedArtist+"&page="+page;
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
}
