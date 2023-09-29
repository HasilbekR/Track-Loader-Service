package com.vention.TrackLoaderService.model.artist;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import com.vention.TrackLoaderService.model.BaseModel;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class Artist extends BaseModel {
    private String name;
    private String url;
    private Integer playcount;
    private Integer listeners;
    @JsonProperty("mbid")
    public void setIdFromJson(String mbid) {
        if (mbid != null && !mbid.isEmpty()) {
            super.id = UUID.fromString(mbid);
        }
    }
}
