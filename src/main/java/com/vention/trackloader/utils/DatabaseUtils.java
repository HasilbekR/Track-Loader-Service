package com.vention.trackloader.utils;

import com.vention.trackloader.repositories.artist.ArtistRepositoryImpl;
import com.vention.trackloader.repositories.track.TrackRepository;
import com.vention.trackloader.repositories.track.TrackRepositoryImpl;
import com.vention.trackloader.repositories.artist.ArtistRepository;

public class DatabaseUtils {
    private static final TrackRepository trackRepository = new TrackRepositoryImpl();
    private static final ArtistRepository artistRepository = new ArtistRepositoryImpl();

    public static void clearArtistTable() {
        artistRepository.deleteAll();
    }
    public static void clearTrackTable() {
        trackRepository.deleteAll();
    }
}
