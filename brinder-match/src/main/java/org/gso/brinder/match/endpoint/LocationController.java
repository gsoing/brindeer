package org.gso.brinder.match.endpoint;

import lombok.RequiredArgsConstructor;
import org.gso.brinder.match.model.Location;
import org.gso.brinder.match.service.LocationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;


import org.springframework.http.HttpStatus;
import org.gso.brinder.match.model.ProfileModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;


import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class LocationController {

    private final LocationService locationService;

}