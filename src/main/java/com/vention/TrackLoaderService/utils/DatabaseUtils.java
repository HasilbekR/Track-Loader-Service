package com.vention.TrackLoaderService.utils;

import com.vention.TrackLoaderService.repositories.artist.ArtistRepositoryImpl;
import com.vention.TrackLoaderService.repositories.track.TrackRepository;
import com.vention.TrackLoaderService.repositories.track.TrackRepositoryImpl;
import com.vention.TrackLoaderService.repositories.artist.ArtistRepository;

public class DatabaseUtils {
    private static final TrackRepository trackRepository = new TrackRepositoryImpl();
    private static final ArtistRepository artistRepository = new ArtistRepositoryImpl();
    public static void clearDatabase(){
        trackRepository.deleteAll();
        artistRepository.deleteAll();
    }
}
