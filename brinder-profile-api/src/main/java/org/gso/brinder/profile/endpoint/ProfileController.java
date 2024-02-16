package org.gso.brinder.profile.endpoint;

import java.security.Principal;
import java.util.List;

import com.github.rutledgepaulv.qbuilders.builders.GeneralQueryBuilder;
import com.github.rutledgepaulv.qbuilders.conditions.Condition;
import com.github.rutledgepaulv.qbuilders.visitors.MongoVisitor;
import com.github.rutledgepaulv.rqe.pipes.QueryConversionPipeline;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.gso.brinder.common.dto.PageDto;
import org.gso.brinder.profile.dto.CoreProfileData;
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

    @PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<ProfileDto> createProfile(@RequestBody ProfileDto profileDto, Principal principal) {
        CoreProfileData coreProfileData = getDataFromToken(principal);
        ProfileDto createdProfile = profileService.createProfile(profileDto.toModel(coreProfileData.getUserId(),
                                                                                    coreProfileData.getFirstName(),
                                                                                    coreProfileData.getLastName(),
                                                                                    coreProfileData.getMail())).toDto();
        return ResponseEntity
                .created(
                        ServletUriComponentsBuilder.fromCurrentContextPath()
                                .path(coreProfileData.getUserId())
                                .build()
                                .toUri()
                ).body(createdProfile);
    }

    @GetMapping("/profile")
    public ResponseEntity<ProfileDto> getProfile(Principal principal) {
        JwtAuthenticationToken jwtAuthToken = (JwtAuthenticationToken) principal;
        String userId = jwtAuthToken.getToken().getClaimAsString("sub");
        return ResponseEntity.ok(profileService.getProfile(userId).toDto());
    }

    @PutMapping(path = "/update", consumes = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<ProfileDto> updateProfile(@RequestBody @NonNull ProfileDto profileDto,
                                                    Principal principal) {
      CoreProfileData coreProfileData = getDataFromToken(principal);

      return ResponseEntity.ok(profileService.updateProfile(profileDto.toModel(coreProfileData.getUserId(),
                                                                               coreProfileData.getFirstName(),
                                                                               coreProfileData.getLastName(),
                                                                               coreProfileData.getMail())).toDto());
    }

    @GetMapping("/search")
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

    public CoreProfileData getDataFromToken(Principal principal) {
        JwtAuthenticationToken jwtAuthToken = (JwtAuthenticationToken) principal;
        return new CoreProfileData(jwtAuthToken.getToken().getClaimAsString("sub"),
        jwtAuthToken.getToken().getClaimAsString("email"),
        jwtAuthToken.getToken().getClaimAsString("given_name"),
        jwtAuthToken.getToken().getClaimAsString("family_name"));

    }
}
