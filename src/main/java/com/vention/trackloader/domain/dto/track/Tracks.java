package com.vention.trackloader.domain.dto.track;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.vention.trackloader.domain.models.track.Track;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Tracks {
    private List<Track> track;
}
