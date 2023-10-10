package com.vention.trackloader.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vention.trackloader.models.artist.Artist;
import com.vention.trackloader.models.track.TrackWrapper;
import com.vention.trackloader.repositories.track.TrackRepository;
import com.vention.trackloader.repositories.track.TrackRepositoryImpl;
import com.vention.trackloader.utils.DatabaseUtils;
import com.vention.trackloader.models.track.Track;
import com.vention.trackloader.utils.Utils;

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
    private final ObjectMapper objectMapper = Utils.getObjectMapper();
    private final ArtistService artistService = new ArtistService();

    public Track save(Track track) {
        Artist artist = track.getArtist();
        Artist artistByName = artistService.getArtistByName(artist.getName());
        if (artistByName == null) {
            artistByName = artistService.save(artist);
        }
        track.setArtist(artistByName);
        trackRepository.save(track);
        return track;
    }

    public List<Track> saveAll(List<Track> trackList) {
        List<Track> tracks = new ArrayList<>();
        for (Track track : trackList) {
            tracks.add(save(track));
        }
        return tracks;
    }

    /**
     * Despite clearing this table from artist service, I have to call it here too
     * as when the request comes from main service it calls directly this method
     * @param page-
     * @return writes tracks in json format
     * @throws IOException -
     */
    public String saveTopTracks(String page) throws IOException {
        DatabaseUtils.clearTrackTable();
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
    public String getTopTracks() throws IOException {
        List<Track> tracks = trackRepository.getAll();
        return objectMapper.writeValueAsString(tracks);
    }
    public String getTopTracksByArtist(String artist, String page) throws IOException {
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
                return objectMapper.writeValueAsString(trackList);
            }
        }
        return null;
    }

}
