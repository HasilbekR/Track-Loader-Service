package com.vention.trackloader.models.track;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class TrackWrapper {
    private Tracks tracks;
    private TopTracks toptracks;
}
