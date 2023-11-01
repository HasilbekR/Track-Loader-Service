package com.vention.trackloader.domain.models.track;

import com.vention.trackloader.domain.models.artist.Artist;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import com.vention.trackloader.domain.models.BaseModel;

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
