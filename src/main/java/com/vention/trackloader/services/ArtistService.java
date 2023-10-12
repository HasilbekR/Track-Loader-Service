package com.vention.trackloader.services;


import com.vention.trackloader.models.artist.Artist;
import com.vention.trackloader.models.artist.ArtistWrapper;
import com.vention.trackloader.repositories.artist.ArtistRepository;
import com.vention.trackloader.repositories.artist.ArtistRepositoryImpl;
import com.vention.trackloader.utils.DatabaseUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vention.trackloader.utils.Utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ArtistService {
    private final ArtistRepository artistRepository = new ArtistRepositoryImpl();
    private final ObjectMapper objectMapper = Utils.getObjectMapper();

    public Artist save(Artist artist) {
        artistRepository.save(artist);
        return artist;
    }

    public List<Artist> saveAll(List<Artist> artistList) {
        List<Artist> savedArtists = new ArrayList<>();

        for (Artist artist : artistList) {
            Artist savedArtist = save(artist);
            savedArtists.add(savedArtist);
        }
        return savedArtists;
    }

    /**
     * I clear 2 table here as tracks are linked to artists so without deleting them I cannot delete artists
     *
     * @param page - the page of data
     * @return writes artists in json format
     * @throws IOException -
     */
    public String saveTopArtists(String page) throws IOException {
        DatabaseUtils.clearTrackTable();
        DatabaseUtils.clearArtistTable();
        String apiUrl = Utils.getUrl() + "&method=chart.gettopartists" + "&page=" + page;
        URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                ArtistWrapper artistsWrapper = objectMapper.readValue(reader, ArtistWrapper.class);
                List<Artist> artistList = artistsWrapper.getArtists().getArtist();
                List<Artist> savedArtists = saveAll(artistList);
                return objectMapper.writeValueAsString(savedArtists);
            }
        }
        return null;
    }

    public String getTopArtists() throws IOException {
        List<Artist> artistList = artistRepository.getAll();
        return objectMapper.writeValueAsString(artistList);
    }

    public Artist getArtistByName(String name) {
        return artistRepository.getArtistByName(name);
    }

    public Artist getArtistById(UUID id) {
        return artistRepository.getArtistById(id);
    }
}
