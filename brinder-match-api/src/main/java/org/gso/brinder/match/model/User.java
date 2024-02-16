package org.gso.brinder.match.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.mapping.Document;
import javax.validation.constraints.Email;


@Data
@Builder
@Document
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    private String id;
    private String firstname;
    private String lastname;
    @Email
    private String mail;
    private int age;
    @GeoSpatialIndexed(type = GeoSpatialIndexType.GEO_2DSPHERE) private Point location;

}
