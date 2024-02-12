package org.gso.brinder.match.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.gso.brinder.profile.dto.ProfileDto;
import org.springframework.data.geo.Point;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MatchDto {

    private String id;
    private String userId;
    private ProfileDto profile;
    private Point location;
    private Date lastSeen;

}