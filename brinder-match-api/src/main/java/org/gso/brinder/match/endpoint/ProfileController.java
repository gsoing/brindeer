package org.gso.brinder.match.endpoint;


import com.github.rutledgepaulv.rqe.pipes.QueryConversionPipeline;
import lombok.RequiredArgsConstructor;
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
        value = ProfileController.PATH,
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class ProfileController {

    public static final String PATH = "/api/v1/matchs";
    public static int MAX_PAGE_SIZE = 200;
    private final ProfileService profileService;
    public ProfileController(ProfileService profileService) {this.profileService = profileService;}

    private QueryConversionPipeline pipeline = QueryConversionPipeline.defaultPipeline();
	
	@RequestMapping(value ="/string", method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public String getString() {
        return "Matchs";
    }


    @PostMapping("/localisation")
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

    @GetMapping("/profiles")
    public List<ProfileModel> getAllRestaurant() {
        return profileService.findAll();
    }

    @GetMapping("/findByDistance")
    public List<ProfileModel> findByDistance(@RequestParam(value = "long") float longitude, @RequestParam(value = "lat") float latitude, @RequestParam(value = "distance") int distance){
        return profileService.findByDistance(longitude, latitude, distance);
    }

 

}
