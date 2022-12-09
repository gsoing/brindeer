package org.gso.brinder.match.endpoint;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.gso.brinder.match.dto.MatchProfileDto;
import org.gso.brinder.match.model.MatchProfileModel;
import org.gso.brinder.match.service.MatchService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping(

        value = MatchController.PATH,
        produces = MediaType.APPLICATION_JSON_VALUE
)
@RequiredArgsConstructor
public class MatchController {
    public static final String PATH = "/api/v1/match";

    private final MatchService matchService;

    @GetMapping
    public ResponseEntity<List<String>> getMatches(MatchProfileDto matchProfileDto) {
        List<String> results = matchService.getMatches(matchProfileDto.getLatitude(), matchProfileDto.getLongitude(),100)
                .stream().map(MatchProfileModel::getId).collect(Collectors.toList());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(results);
    }

    @PutMapping
    public ResponseEntity<MatchProfileDto> updateLocation(@PathVariable @NonNull String profileId, JwtAuthenticationToken principal, @RequestBody MatchProfileDto matchProfileDto) {
        matchProfileDto.setId(profileId);
        return ResponseEntity.ok(matchService.updateLocation(matchProfileDto.toModel(principal)).toDto());
    }
}
