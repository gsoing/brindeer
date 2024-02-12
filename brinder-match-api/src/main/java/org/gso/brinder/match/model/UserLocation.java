package org.gso.brinder.match.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
public class UserLocation {
    @Id
    private String userId;
    private double latitude;
    private double longitude;
}
