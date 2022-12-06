package org.gso.brinder.match.endpoint;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.gso.brinder.match.service.UserService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

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


}
