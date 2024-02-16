package org.gso.brinder.match.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;

import org.gso.brinder.match.model.ProfileModel;
import org.gso.brinder.match.repository.MatchRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class MatchServiceTest {

    @Autowired
    MatchService matchService;

    @MockBean
    MatchRepository matchRepository;

}
