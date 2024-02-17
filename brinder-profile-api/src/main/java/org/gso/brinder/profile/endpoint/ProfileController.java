package org.gso.brinder.profile.endpoint;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

import com.github.rutledgepaulv.qbuilders.builders.GeneralQueryBuilder;
import com.github.rutledgepaulv.qbuilders.conditions.Condition;
import com.github.rutledgepaulv.qbuilders.visitors.MongoVisitor;
import com.github.rutledgepaulv.rqe.pipes.QueryConversionPipeline;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.gso.brinder.common.dto.PageDto;
import org.gso.brinder.profile.dto.ProfileDto;
import org.gso.brinder.profile.model.ProfileModel;
import org.gso.brinder.profile.service.ProfileService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Slf4j
@RestController
@RequestMapping(
        value = ProfileController.PATH,
        produces = MediaType.APPLICATION_JSON_VALUE
)
@RequiredArgsConstructor
public class ProfileController {

    public static final String PATH = "/api/v1/profiles";
    public static int MAX_PAGE_SIZE = 200;

    private final ProfileService profileService;
    private QueryConversionPipeline pipeline = QueryConversionPipeline.defaultPipeline();


    /** *** *** *** POST /profiles *** *** *** */
    @PostMapping
    public ResponseEntity<ProfileDto> createProfile(JwtAuthenticationToken principal) throws Exception {
        String userId = principal.getToken().getClaimAsString("sub");
        String email = principal.getToken().getClaimAsString("email");
        String firstName = principal.getToken().getClaimAsString("given_name");
        String lastName = principal.getToken().getClaimAsString("family_name");

        ProfileDto profile = ProfileDto.builder()
                .userId(userId)
                .mail(email)
                .firstName(firstName)
                .lastName(lastName)
                // age : for the creation of the profile we don't have the age
                // to add it, we need to update the profile
                .created(LocalDateTime.now())
                .modified(LocalDateTime.now())
                .build();

        ProfileDto createdProdile = profileService.createProfile(profile.toModel()).toDto();

        if (createdProdile == null) {
            throw new Exception("Failed to create profile");
        }

        return ResponseEntity
                .created(
                        ServletUriComponentsBuilder.fromCurrentContextPath()
                                .path(createdProdile.getId()).build().toUri()
                ).body(createdProdile);
    }

    /** *** *** *** GET /profiles/{id} *** *** *** */
    // here we can't use the jwt because the user can access to the profile of another user
    @GetMapping("/{id}")
    public ResponseEntity<ProfileDto> getProfile(@PathVariable("id") @NonNull String profileId) {
        return ResponseEntity.ok(profileService.getProfile(profileId).toDto());
    }

    /** *** *** *** PUT /profiles *** *** *** */
    // here the user can only update his profile
    @PutMapping
    public ResponseEntity<ProfileDto> updateProfile(@RequestBody @NonNull ProfileDto profileDto, JwtAuthenticationToken principal) {
        String userId = principal.getToken().getClaimAsString("sub");
        ProfileDto existingProfile = profileService.getProfile(userId).toDto();
        if (existingProfile == null) {
            return ResponseEntity.notFound().build();
        }
        profileDto.setId(existingProfile.getId());
        profileDto.setUserId(userId);

        String email = principal.getToken().getClaimAsString("email");
        String firstName = principal.getToken().getClaimAsString("given_name");
        String lastName = principal.getToken().getClaimAsString("family_name");

        profileDto.setMail(email);
        profileDto.setFirstName(firstName);
        profileDto.setLastName(lastName);

        ProfileDto updatedProfile = profileService.updateProfile(profileDto.toModel()).toDto();
        return ResponseEntity.ok(updatedProfile);
    }

    /** *** *** *** GET /profiles *** *** *** */
    // here we can't use the jwt because we are looking for all the profiles
    @GetMapping
    public ResponseEntity<PageDto<ProfileDto>> searchProfile(@RequestParam(required = false) String query,
                                                             @PageableDefault(size = 20) Pageable pageable) {
        Pageable checkedPageable  = checkPageSize(pageable);
        Criteria criteria = convertQuery(query);
        Page<ProfileModel> results = profileService.searchProfiles(criteria, checkedPageable);
        PageDto<ProfileDto> pageResults = toPageDto(results);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(pageResults);
    }

    /** *** *** *** GET /profiles/current *** *** *** */
    @GetMapping("/current")
    public ResponseEntity<ProfileDto> getCurrentUserProfile(JwtAuthenticationToken principal) {
        String userId = principal.getToken().getClaimAsString("sub");
        String email = principal.getToken().getClaimAsString("email");
        String firstName = principal.getToken().getClaimAsString("given_name");
        String lastName = principal.getToken().getClaimAsString("family_name");

        ProfileDto profile = ProfileDto.builder()
                .userId(userId)
                .mail(email)
                .firstName(firstName)
                .lastName(lastName)
                .build();

        return ResponseEntity.ok(profile);
    }

    /**
     * Convertit une requête RSQL en un objet Criteria compréhensible par la base
     *
     * @param stringQuery
     * @return
     */
    private Criteria convertQuery(String stringQuery) {
        Criteria criteria;
        if (StringUtils.hasText(stringQuery)) {
            Condition<GeneralQueryBuilder> condition = pipeline.apply(stringQuery, ProfileModel.class);
            criteria = condition.query(new MongoVisitor());
        } else {
            criteria = new Criteria();
        }
        return criteria;
    }

    private Pageable checkPageSize(Pageable pageable) {
        if (pageable.getPageSize() > MAX_PAGE_SIZE) {
            return PageRequest.of(pageable.getPageNumber(), MAX_PAGE_SIZE);
        }
        return pageable;
    }

    private PageDto<ProfileDto> toPageDto(Page<ProfileModel> results) {
        List<ProfileDto> profiles = results.map(ProfileModel::toDto).toList();
        PageDto<ProfileDto> pageResults = new PageDto<>();
        pageResults.setData(profiles);
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
