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

    public String saveTopArtists(String page) throws IOException{
        DatabaseUtils.clearDatabase();
        String apiUrl = Utils.getUrl()+"&method=chart.gettopartists"+"&page="+page;
        URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            try(BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                ArtistWrapper artistsWrapper = objectMapper.readValue(reader, ArtistWrapper.class);
                List<Artist> artistList = artistsWrapper.getArtists().getArtist();
                List<Artist> savedArtists = saveAll(artistList);
                return objectMapper.writeValueAsString(savedArtists);
            }
        }
        return null;
    }

    public Artist getArtistByName(String name) {
        return artistRepository.getArtistByName(name);
    }
}
