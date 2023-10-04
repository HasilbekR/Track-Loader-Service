package com.vention.trackloader.models.track;

import com.vention.trackloader.models.artist.Artist;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import com.vention.trackloader.models.BaseModel;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class Track extends BaseModel {
    private Integer duration;
    private Artist artist;
}
