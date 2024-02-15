package org.gso.brinder.match.endpoint;

import lombok.RequiredArgsConstructor;
import org.gso.brinder.match.model.UserLocation;
import org.gso.brinder.match.service.UserLocationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;


import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.gso.brinder.match.model.ProfileModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/locations")
@RequiredArgsConstructor
public class UserLocationController {

    private final UserLocationService userLocationService;

    @PostMapping("/update")
    public ResponseEntity<UserLocation> updateLocation(@AuthenticationPrincipal Jwt jwt, @RequestParam double longitude, @RequestParam double latitude) {
        String email = jwt.getClaimAsString("email");
        String userId = jwt.getClaimAsString("sid");
        Page<ProfileModel> existingProfile = userLocationService.searchByMail(email, PageRequest.of(0, 20));
        if (existingProfile.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } else {
            UserLocation updatedLocation = userLocationService.updateLocation(userId, longitude, latitude);
            return ResponseEntity.ok(updatedLocation);
        }

    }

    @GetMapping("/matches")
    public ResponseEntity<List<UserLocation>> findMatchesNearby(@AuthenticationPrincipal Jwt jwt) {
        String email = jwt.getClaimAsString("email");
        String userId = jwt.getClaimAsString("sid");
        Page<ProfileModel> existingProfile = userLocationService.searchByMail(email, PageRequest.of(0, 20));
        if (existingProfile.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } else {
            if (userLocationService.existsById(userId)) {
                List<UserLocation> matches = userLocationService.findMatchesNearby(userLocationService.findById(userId).get().getLocation().getX(), userLocationService.findById(userId).get().getLocation().getY());
                matches.remove(userLocationService.findById(userId).get());
                return ResponseEntity.ok(matches);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        }
    }
}