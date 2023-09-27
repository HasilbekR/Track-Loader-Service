package org.example.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.model.artist.Artist;
import org.example.model.artist.ArtistWrapper;
import org.example.repositories.artist.ArtistRepository;
import org.example.repositories.artist.ArtistRepositoryImpl;
import org.example.utils.BeanUtil;
import org.example.utils.DatabaseUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ArtistService {
    private final ArtistRepository artistRepository = new ArtistRepositoryImpl();
    private final ObjectMapper objectMapper = BeanUtil.getObjectMapper();

    public Artist save(Artist artist) {
        Artist artistByName = artistRepository.getArtistByName(artist.getName());
        if (artistByName!= null) {
            artist.setId(artistByName.getId());
            artistRepository.update(artist);
            return artistRepository.getArtistByName(artist.getName());
        }
        artistRepository.save(artist);
        return artistRepository.getArtistByName(artist.getName());
    }

    public List<Artist> saveAll(List<Artist> artistList){
        List<Artist> savedArtists = new ArrayList<>();

        for (Artist artist : artistList) {
            Artist savedArtist = save(artist);
            savedArtists.add(savedArtist);
        }
        return savedArtists;
    }
    public String  saveTopArtists(Integer page) throws IOException{
        DatabaseUtil.clearDatabase();
        String apiUrl = "http://ws.audioscrobbler.com/2.0/?format=json&method=chart.gettopartists&api_key=5008d869491ef3e7bf342c431b66f220&page="+page;
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
}
