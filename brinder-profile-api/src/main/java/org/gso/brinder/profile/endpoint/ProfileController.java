package org.gso.brinder.profile.endpoint;

import com.github.rutledgepaulv.qbuilders.builders.GeneralQueryBuilder;
import com.github.rutledgepaulv.qbuilders.conditions.Condition;
import com.github.rutledgepaulv.qbuilders.visitors.MongoVisitor;
import com.github.rutledgepaulv.rqe.pipes.QueryConversionPipeline;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.gso.brinder.common.dto.PageDto;
import org.gso.brinder.profile.dto.PrincipalData;
import org.gso.brinder.profile.dto.ProfileDto;
import org.gso.brinder.profile.dto.ProfileModifiedDto;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.security.Principal;
import java.util.List;


@CrossOrigin("*")
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

    @PostMapping(value = "/", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ProfileDto> createProfile(@RequestBody ProfileModifiedDto profileDto, Principal principal) {
        PrincipalData principalData = getDataFromPrincipal(principal);

        ProfileDto createdProfile = profileService.createProfile(profileDto.toModel(principalData.getUserId()
                , principalData.getFirstName(), principalData.getLastName(), principalData.getMail())).toDto();

        return ResponseEntity
                .created(
                        ServletUriComponentsBuilder.fromCurrentContextPath()
                                .path(createdProfile.getId())
                                .build()
                                .toUri()
                ).body(createdProfile);
    }

    @GetMapping("/data")
    public ResponseEntity<ProfileDto> getProfile(Principal principal) {
        JwtAuthenticationToken jwtAuth = (JwtAuthenticationToken) principal;
        String userId = jwtAuth.getToken().getClaimAsString("sub");
        return ResponseEntity.ok(profileService.getProfile(userId).toDto());
    }

    @PutMapping(value="/modify",consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ProfileDto> updateProfile(@RequestBody @NonNull ProfileModifiedDto profileModifiedDto,
                                                    Principal principal) {
        PrincipalData principalData = getDataFromPrincipal(principal);

        return ResponseEntity.ok(profileService.updateProfile(profileModifiedDto.toModel(principalData.getUserId(), principalData.getFirstName(), principalData.getLastName(), principalData.getMail())).toDto());
    }

    @GetMapping("/search")
    public ResponseEntity<PageDto<ProfileDto>> searchProfile(@RequestParam(required = false) String query,
                                                             @PageableDefault(size = 20) Pageable pageable) {
        Pageable checkedPageable = checkPageSize(pageable);
        Criteria criteria = convertQuery(query);
        Page<ProfileModel> results = profileService.searchProfiles(criteria, checkedPageable);
        PageDto<ProfileDto> pageResults = toPageDto(results);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(pageResults);
    }

    @GetMapping(value="/{mail}")
    public ResponseEntity<PageDto<ProfileDto>> searchByMail(@PathVariable("mail") String mail,
                                                            @PageableDefault(size = 20) Pageable pageable) {
        Page<ProfileModel> results = profileService.searchByMail(mail, pageable);
        PageDto<ProfileDto> pageResults = toPageDto(results);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(pageResults);
    }

    @GetMapping("/current")
    public ResponseEntity getCurrentUserProfile(JwtAuthenticationToken principal) {
        return ResponseEntity.ok(principal);
    }

    public PrincipalData getDataFromPrincipal(Principal principal) {
        JwtAuthenticationToken jwtAuth = (JwtAuthenticationToken) principal;
        String userId = jwtAuth.getToken().getClaimAsString("sub");
        String mail = jwtAuth.getToken().getClaimAsString("email");
        String firstName = jwtAuth.getToken().getClaimAsString("given_name");
        String lastName = jwtAuth.getToken().getClaimAsString("family_name");
        return new PrincipalData(userId, mail, firstName, lastName);
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
