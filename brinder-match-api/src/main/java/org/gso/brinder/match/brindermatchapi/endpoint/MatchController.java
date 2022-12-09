package org.gso.brinder.match.brindermatchapi.endpoint;

import com.github.rutledgepaulv.rqe.pipes.QueryConversionPipeline;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.gso.brinder.match.brindermatchapi.dto.ProfileMatchDto;
import org.gso.brinder.match.brindermatchapi.service.MatchService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(
        value = MatchController.PATH,
        produces = MediaType.APPLICATION_JSON_VALUE
)
@RequiredArgsConstructor
public class MatchController {

    public static final String PATH = "/api/v1/matches";
    private final MatchService matchService;

    //We always use the id because we make the distinction between the user id and the profile id (which is the id of the profile table)
    @GetMapping(path = "/{id}", consumes = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<List<ProfileMatchDto>> findMatches(@PathVariable @NonNull String id) {
        return ResponseEntity.ok(matchService.findMatches(id));
    }

    @PutMapping(path = "/location/{id}", consumes = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<ProfileMatchDto> updateProfile(@PathVariable @NonNull String profileId,
                                                         @RequestBody @NonNull ProfileMatchDto profileMatchDto,
                                                         Principal principal) {
        profileMatchDto.setId(profileId);
        return ResponseEntity.ok(matchService.updateLocation(profileMatchDto.toModel(principal)).toProfileDto());
    }


}
