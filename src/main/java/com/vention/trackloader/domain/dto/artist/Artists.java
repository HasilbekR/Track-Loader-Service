package com.vention.trackloader.domain.dto.artist;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.vention.trackloader.domain.models.artist.Artist;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Artists {
    private List<Artist> artist;
}
