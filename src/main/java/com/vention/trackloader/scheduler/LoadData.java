package com.vention.trackloader.scheduler;

import com.vention.trackloader.services.ArtistService;
import com.vention.trackloader.services.TrackService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;

import java.io.IOException;

public class LoadData implements Job {

    private final TrackService trackService = new TrackService();
    private final ArtistService artistService = new ArtistService();
    @Override
    public void execute(JobExecutionContext jobExecutionContext){
        try{
            artistService.saveTopArtists(String.valueOf(1));
            trackService.saveTopTracks(String.valueOf(1));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
