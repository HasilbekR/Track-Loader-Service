package org.example.utils;

import org.example.repositories.artist.ArtistRepository;
import org.example.repositories.artist.ArtistRepositoryImpl;
import org.example.repositories.track.TrackRepository;
import org.example.repositories.track.TrackRepositoryImpl;

public class DatabaseUtil {
    private static final TrackRepository trackRepository = new TrackRepositoryImpl();
    private static final ArtistRepository artistRepository = new ArtistRepositoryImpl();
    public static void clearDatabase(){
        trackRepository.deleteAll();
        artistRepository.deleteAll();
    }
}
