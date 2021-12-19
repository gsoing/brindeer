package org.gso.brinder.match.endpoint;

import com.github.rutledgepaulv.qbuilders.builders.GeneralQueryBuilder;
import com.github.rutledgepaulv.qbuilders.conditions.Condition;
import com.github.rutledgepaulv.qbuilders.visitors.MongoVisitor;
import com.github.rutledgepaulv.rqe.pipes.QueryConversionPipeline;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.gso.brinder.common.dto.PageDto;
import org.gso.brinder.match.dto.UserDto;
import org.gso.brinder.match.model.UserModel;
import org.gso.brinder.match.model.Coordonnee;
import org.gso.brinder.match.service.UserService;
import org.keycloak.adapters.springsecurity.account.SimpleKeycloakAccount;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.keycloak.representations.AccessToken;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.security.Principal;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(

        value = UserController.PATH,
        produces = MediaType.APPLICATION_JSON_VALUE
)
@RequiredArgsConstructor
public class UserController {
    public static final String PATH = "/api/v1/matchs";
    public static int MAX_PAGE_SIZE = 200;

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<String>> searchMatch(Principal principal) {
        AccessToken accessToken = getAccessToken(principal);
        List<String> results = userService.searchMatch(accessToken.getSubject());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(results);
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserModel>> searchAllUsers() {
        List<UserModel> results = userService.findAllUsers();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(results);
    }

    public AccessToken getAccessToken(Principal principal) {
        KeycloakAuthenticationToken kp = (KeycloakAuthenticationToken) principal;
        SimpleKeycloakAccount simpleKeycloakAccount = (SimpleKeycloakAccount) kp.getDetails();
        return simpleKeycloakAccount.getKeycloakSecurityContext().getToken();
    }

    @PostMapping
    public ResponseEntity<UserModel> createUser(Principal principal, @RequestBody Coordonnee coordonnee) {
        AccessToken accessToken = getAccessToken(principal);
        UserModel user = new UserModel(accessToken.getSubject(),accessToken.getGivenName(),accessToken.getFamilyName(),coordonnee.getLongitude(),coordonnee.getLatitude());
        UserModel response = userService.createUser(user);

        return ResponseEntity
                .created(
                        ServletUriComponentsBuilder.fromCurrentContextPath()
                                .path(response.getId())
                                .build()
                                .toUri()
                ).body(response);
    }

    @PutMapping
    public ResponseEntity<UserModel> updateProfile(Principal principal,@RequestBody Coordonnee coordonnee) {
        AccessToken accessToken = getAccessToken(principal);
        UserModel user = new UserModel(accessToken.getSubject(),accessToken.getGivenName(),accessToken.getFamilyName(),coordonnee.getLongitude(),coordonnee.getLatitude());
        UserModel response = userService.update(user);

        return ResponseEntity
                .ok(response);
    }
}
