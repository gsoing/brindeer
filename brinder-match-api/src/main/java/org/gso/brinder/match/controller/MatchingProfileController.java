package org.gso.brinder.match.controller;

import org.gso.brinder.match.model.GeoCoordinates;
import org.gso.brinder.match.model.MatchedUser;
import org.gso.brinder.match.service.MatchedUserService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import lombok.extern.slf4j.Slf4j;

import java.net.URI;
import java.security.Principal;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/match", produces = MediaType.APPLICATION_JSON_VALUE)
public class MatchingProfileController {

    private final MatchedUserService matchedUserService;

    public MatchingProfileController(MatchedUserService matchedUserService) {
        this.matchedUserService = matchedUserService;
    }

    @PostMapping(value = "/createorupdate", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MatchedUser> createOrUpdateMatchedUserProfile(@RequestBody GeoCoordinates geoCoordinates, Principal principal) {
        log.debug("Received request to create or update matched user profile. Principal name: {}", principal.getName());

        MatchedUser savedProfile = getDataFromToken(principal);
        log.debug("MatchedUser retrieved from token: {}", savedProfile);

        savedProfile.setGeoCoordinates(geoCoordinates);
        log.debug("Set GeoCoordinates for user: {}, {}", geoCoordinates.getLatitude(), geoCoordinates.getLongitude());

        MatchedUser showProfile = matchedUserService.createOrUpdateMatchedUserProfile(savedProfile);
        log.debug("MatchedUser profile saved or updated: {}", showProfile);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(showProfile.getIdMatchedUser()).toUri();
        log.debug("Created location URI for the saved or updated profile: {}", location);

        return ResponseEntity.created(location).body(showProfile);
    }

    @GetMapping("/all")
    public ResponseEntity<List<MatchedUser>> getAllUserMatchProfiles(Pageable pageable) {
        List<MatchedUser> profiles = matchedUserService.findAllMatchedUserProfiles(pageable);
        return ResponseEntity.ok(profiles);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MatchedUser> getUserMatchProfile(@PathVariable String id) {
        MatchedUser profile = matchedUserService.getById(id);
        return ResponseEntity.ok(profile);
    }

    @GetMapping("/nearby")
    public ResponseEntity<List<MatchedUser>> findNearbyUserMatchProfiles(@RequestParam double distance, @AuthenticationPrincipal JwtAuthenticationToken principal) {
        // Assuming distance is provided in kilometers for simplicity; adjust accordingly if needed
        String userId = principal.getToken().getClaimAsString("sub");
        List<MatchedUser> nearbyProfiles = matchedUserService.findMatchedUsersNearby(userId, distance);
        return ResponseEntity.ok(nearbyProfiles);
    }


    public MatchedUser getDataFromToken(Principal principal) {
        JwtAuthenticationToken jwtAuthToken = (JwtAuthenticationToken) principal;
        return new MatchedUser(jwtAuthToken.getToken().getClaimAsString("sub"),
        jwtAuthToken.getToken().getClaimAsString("given_name"),
        jwtAuthToken.getToken().getClaimAsString("family_name"));

    }

}