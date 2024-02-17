package org.gso.brinder.match.endpoint;

import com.github.rutledgepaulv.rqe.pipes.QueryConversionPipeline;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.gso.brinder.match.dto.MatchDto;
import org.gso.brinder.match.model.ProfileModel;
//TODO import org.gso.brinder.profile.model.ProfileModel;
import org.gso.brinder.match.service.MatchService;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@Slf4j
@RestController
@RequestMapping(
        value = MatchController.PATH,
        produces = MediaType.APPLICATION_JSON_VALUE
)
@RequiredArgsConstructor
public class MatchController {

    public static final String PATH = "/api/v1/match";
    public static int MAX_PAGE_SIZE = 200;
    private final MatchService matchService;
    private QueryConversionPipeline pipeline = QueryConversionPipeline.defaultPipeline();

    @GetMapping()
    public List<ProfileModel> searchNearbyUsers(JwtAuthenticationToken principal) {
        String userId = principal.getName();
        return matchService.findNearbyUsers(userId);
    }

    @PutMapping()
    public void updateLocation(JwtAuthenticationToken principal, @RequestBody MatchDto request) {
        String userId = principal.getName();
        matchService.updateUserLocation(userId, request.getLatitude(), request.getLongitude());
    }

}
