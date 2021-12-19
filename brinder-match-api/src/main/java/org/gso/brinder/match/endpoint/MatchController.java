package org.gso.brinder.match.endpoint;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.gso.brinder.match.model.Address;
import org.gso.brinder.match.model.ProfileModel;
import org.gso.brinder.match.service.MatchServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RestController
@RequestMapping(
        value = MatchController.PATH,
        produces = MediaType.APPLICATION_JSON_VALUE
)
@RequiredArgsConstructor
public class MatchController {

    public static final String PATH = "/api/v1/matchs";

    @Autowired
    private MatchServiceImpl matchService;
	
	@RequestMapping(value ="/string", method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public String getString() {
        return "Matchs";
    }


    @PostMapping(path = "/updateLocation/{id}", consumes = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<String> updateLocation(@PathVariable @NonNull String id,
                                                     @RequestBody @NonNull Address address) {

        return ResponseEntity.ok(matchService.updateLocation(id, address));
    }

    @GetMapping(path = "/findMatches/{id}", consumes = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<List<ProfileModel>> findMatches(@PathVariable @NonNull String id) {

        return ResponseEntity.ok(matchService.getMatchesWithin100m(id));
    }

}
