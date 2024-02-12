package org.gso.brinder.match.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.gso.brinder.match.dto.MatchDto;
import org.gso.brinder.profile.model.ProfileModel;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Date;
import org.springframework.data.geo.Point;
@Data
@Builder
@Document
@NoArgsConstructor
@AllArgsConstructor
public class MatchModel {

    @Id
    private String id;
    private String userId;
    @DBRef
    private ProfileModel profile;
    @GeoSpatialIndexed(type = GeoSpatialIndexType.GEO_2DSPHERE)
    private Point location;
    private Date lastSeen;

    public MatchDto toDto() {
        return MatchDto.builder()
                .id(this.id)
                .userId(this.userId)
                .profile(this.profile.toDto())
                .location(this.location)
                .lastSeen(this.lastSeen)
                .build();
    }
}