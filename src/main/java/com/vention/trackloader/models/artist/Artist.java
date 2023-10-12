package com.vention.trackloader.models.artist;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import com.vention.trackloader.models.BaseModel;

import java.util.UUID;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Artist extends BaseModel {
    @JsonProperty("mbid")
    public void setIdFromJson(String mbid) {
        if (mbid != null && !mbid.isEmpty()) {
            super.id = UUID.fromString(mbid);
        }
    }
}
