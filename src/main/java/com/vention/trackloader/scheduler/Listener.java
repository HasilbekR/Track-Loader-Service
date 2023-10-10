package com.vention.trackloader.scheduler;

import com.vention.trackloader.services.ArtistService;
import com.vention.trackloader.services.TrackService;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@WebListener
public class Listener implements ServletContextListener {
    private final TrackService trackService = new TrackService();
    private final ArtistService artistService = new ArtistService();

    private ScheduledExecutorService executorService;

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        executorService = Executors.newScheduledThreadPool(1);

        executorService.scheduleAtFixedRate(this::loadDataFromServer, calculateInitialDelay(), TimeUnit.DAYS.toSeconds(1), TimeUnit.SECONDS);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        if (executorService != null) {
            executorService.shutdown();
        }
    }

    public void loadDataFromServer() {
        try{
            artistService.saveTopArtists(String.valueOf(1));
            trackService.saveTopTracks(String.valueOf(1));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private long calculateInitialDelay() {
        long currentTimeMillis = System.currentTimeMillis();
        long currentTimeSeconds = TimeUnit.MILLISECONDS.toSeconds(currentTimeMillis);
        long executionTimeSeconds = TimeUnit.HOURS.toSeconds(13) + TimeUnit.MINUTES.toSeconds(0);
        return executionTimeSeconds - (currentTimeSeconds % TimeUnit.DAYS.toSeconds(1));
    }
}