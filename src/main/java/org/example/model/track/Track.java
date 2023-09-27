package org.example.model.track;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.example.model.BaseModel;
import org.example.model.artist.Artist;

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
