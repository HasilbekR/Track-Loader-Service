package com.vention.TrackLoaderService.model.track;

import com.vention.TrackLoaderService.model.artist.Artist;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import com.vention.TrackLoaderService.model.BaseModel;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class Track extends BaseModel {
    private String name;
    private String url;
    private Integer duration;
    private Integer playcount;
    private Integer listeners;
    private Artist artist;
}
