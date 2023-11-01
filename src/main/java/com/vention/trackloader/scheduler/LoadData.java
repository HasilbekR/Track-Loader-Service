package com.vention.trackloader.scheduler;

import com.vention.trackloader.services.ArtistService;
import com.vention.trackloader.services.TrackService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;

public class LoadData implements Job {
    private final TrackService trackService = new TrackService();
    private final ArtistService artistService = new ArtistService();

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        artistService.saveTopArtists("1");
        trackService.saveTopTracks("1");
    }
}
