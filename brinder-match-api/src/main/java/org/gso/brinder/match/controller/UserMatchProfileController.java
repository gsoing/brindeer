package org.gso.brinder.match.controller;


import lombok.extern.slf4j.Slf4j;
import org.gso.brinder.common.dto.PageDto;
import org.gso.brinder.match.dto.PrincipalData;
import org.gso.brinder.match.model.GeoLocation;
import org.gso.brinder.match.model.UserMatchProfile;
import org.gso.brinder.match.service.UserMatchProfileService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/matchs")
@Slf4j
public class UserMatchProfileController {

    public static int MAX_PAGE_SIZE = 200;
    private final UserMatchProfileService userMatchProfileService;


    public UserMatchProfileController(UserMatchProfileService userMatchProfileService) {
        this.userMatchProfileService = userMatchProfileService;
    }

    @PostMapping("/")
    public  ResponseEntity<UserMatchProfile> saveLocationProfile(@RequestBody GeoLocation geoLocation, Principal principal){
        PrincipalData principalData = getDataFromPrincipal(principal);
        UserMatchProfile userMatchProfile = new UserMatchProfile(principalData.getUserId(),
                principalData.getFirstName(),
                principalData.getLastName(),
                geoLocation);
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/v1/match/create").toUriString());
        return ResponseEntity.created(uri).body(userMatchProfileService.createLocationProfile(userMatchProfile));
    }



    @GetMapping("/allLocations")
    public ResponseEntity<List<UserMatchProfile>> getLocationProfile(@PageableDefault(size = 20) Pageable pageable){
        Pageable checkedPageable = checkPageSize(pageable);
        List<UserMatchProfile> results = userMatchProfileService.findLocationsProfile(checkedPageable);
//        PageDto<UserMatchProfile> pageResults = toPageDto(results);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(results);
    }

    @GetMapping("/")
    public ResponseEntity<UserMatchProfile> getUserMatchProfile(Principal principal){
        JwtAuthenticationToken jwtAuth = (JwtAuthenticationToken) principal;
        String userId = jwtAuth.getToken().getClaimAsString("sub");
        return ResponseEntity.ok().body(userMatchProfileService.getById(userId));
    }

    @GetMapping("/findLocationAround100Miles")
    public ResponseEntity<List<UserMatchProfile>> findLocationByDistance(Principal principal){
        JwtAuthenticationToken jwtAuth = (JwtAuthenticationToken) principal;
        String userId = jwtAuth.getToken().getClaimAsString("sub");
        return ResponseEntity.ok().body(userMatchProfileService.findByDistance(userId));
    }

    @PutMapping("/")
    public ResponseEntity<UserMatchProfile> updateMatchProfile(@RequestBody GeoLocation geoLocation, Principal principal){
        PrincipalData principalData = getDataFromPrincipal(principal);
        UserMatchProfile userMatchProfile = new UserMatchProfile(principalData.getUserId(),
                principalData.getFirstName(),
                principalData.getLastName(),
                geoLocation);
        return ResponseEntity.ok().body(userMatchProfileService.updateProfileMatch(userMatchProfile));

    }

    public PrincipalData getDataFromPrincipal(Principal principal) {
        JwtAuthenticationToken jwtAuth = (JwtAuthenticationToken) principal;
        String userId = jwtAuth.getToken().getClaimAsString("sub");
        String mail = jwtAuth.getToken().getClaimAsString("email");
        String firstName = jwtAuth.getToken().getClaimAsString("family_name");
        String lastName = jwtAuth.getToken().getClaimAsString("given_name");

        return new PrincipalData(userId, mail, firstName, lastName);
    }

    private Pageable checkPageSize(Pageable pageable) {
        if (pageable.getPageSize() > MAX_PAGE_SIZE) {
            return PageRequest.of(pageable.getPageNumber(), MAX_PAGE_SIZE);
        }
        return pageable;
    }

    private PageDto<UserMatchProfile> toPageDto(Page<UserMatchProfile> results) {
        List<UserMatchProfile> users = results.map(UserMatchProfile::toDto).toList();
        PageDto<UserMatchProfile> pageResults = new PageDto<>();
        pageResults.setData(users);
        pageResults.setTotalElements(results.getTotalElements());
        pageResults.setPageSize(results.getSize());
        if (results.hasNext()) {
            results.nextOrLastPageable();
            pageResults.setNext(
                    ServletUriComponentsBuilder.fromCurrentContextPath()
                            .queryParam("page", results.nextOrLastPageable().getPageNumber())
                            .queryParam("size", results.nextOrLastPageable().getPageSize())
                            .build().toUri());
        }
        pageResults.setFirst(
                ServletUriComponentsBuilder.fromCurrentContextPath()
                        .queryParam("page", results.previousOrFirstPageable().getPageNumber())
                        .queryParam("size", results.previousOrFirstPageable().getPageSize())
                        .build().toUri());
        pageResults.setLast(
                ServletUriComponentsBuilder.fromCurrentContextPath()
                        .queryParam("page", results.nextOrLastPageable().getPageNumber())
                        .queryParam("size", results.nextOrLastPageable().getPageSize())
                        .build().toUri());
        return pageResults;
    }

}
