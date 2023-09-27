package org.example.model.artist;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.example.model.BaseModel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
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

    public static Artist map(ResultSet resultSet) {
        try {
            Artist artist = Artist.builder()
                    .name(resultSet.getString("name"))
                    .url(resultSet.getString("url"))
                    .playcount(resultSet.getInt("playcount"))
                    .listeners(resultSet.getInt("listeners"))
                    .build();
            Timestamp createdDate = resultSet.getTimestamp("created_date");
            artist.setCreatedDate(createdDate.toLocalDateTime());
            Timestamp updatedDate = resultSet.getTimestamp("updated_date");
            artist.setUpdatedDate(updatedDate.toLocalDateTime());
            artist.setIsBlocked(resultSet.getBoolean("is_blocked"));
            artist.setId(UUID.fromString(resultSet.getString("id")));
            return artist;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
