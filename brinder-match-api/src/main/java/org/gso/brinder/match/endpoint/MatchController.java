package org.gso.brinder.match.endpoint;


import com.github.rutledgepaulv.rqe.pipes.QueryConversionPipeline;
import lombok.extern.slf4j.Slf4j;


import org.gso.brinder.match.dto.ProfileDto;
import org.gso.brinder.match.model.ProfileModel;
import org.gso.brinder.match.service.ProfileService;

import org.springframework.http.HttpStatus;
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
public class MatchController {

    public static final String PATH = "/api/v1/matchs";
    private final ProfileService profileService;
    public MatchController(ProfileService profileService) {this.profileService = profileService;}

    private QueryConversionPipeline pipeline = QueryConversionPipeline.defaultPipeline();
	
	@RequestMapping(value ="/string", method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public String getString() {
        return "match";
    }

    @GetMapping("/profiles")
    public List<ProfileModel> getAllProfiles() {
        return profileService.findAll();
    }

    @GetMapping("/findByLongitudeLatitudeDistance")
    public List<ProfileModel> findByDistance(@RequestParam(value = "longitude") float longitude, @RequestParam(value = "latitude") float latitude, @RequestParam(value = "distance") int distance){
        return profileService.findByDistance(longitude, latitude, distance);
    }

    @PostMapping("/location")
    public ResponseEntity addLocalisation(@RequestBody ProfileDto profileRequest) {
        log.info("Request : {}", profileRequest);
        ProfileModel profile = profileRequest.toProfileModel();
        ProfileModel profileToSave = profileService.getProfileByEmail(profile.getMail());
        if(profileToSave != null){
            profileService.saveProfile(profileToSave);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

}
