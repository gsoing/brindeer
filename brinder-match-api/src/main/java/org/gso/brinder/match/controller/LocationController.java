package org.gso.brinder.match.controller;

import org.gso.brinder.match.model.UserLocation;
import org.gso.brinder.match.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/location")
public class LocationController {

    private final LocationService locationService;

    @Autowired
    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @PutMapping("update")
    public ResponseEntity<?> updateUserLocation(@RequestBody UserLocation location) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName(); // Assuming the username is used as the userId
        System.out.println("userId = " + userId);
        location.setUserId(userId); // Set the userId to the UserLocation object based on authenticated user
        locationService.updateUserLocation(location.getUserId(), location.getLatitude(), location.getLongitude());
        return ResponseEntity.ok().build();
    }
}
