package com.vention.trackloader.domain.dto.track;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class TrackWrapper {
    private Tracks tracks;
    private Tracks toptracks;
}
