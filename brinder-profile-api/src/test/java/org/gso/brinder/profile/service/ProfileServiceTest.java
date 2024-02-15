package org.gso.brinder.profile.service;

import java.time.LocalDateTime;

import org.gso.brinder.profile.model.ProfileModel;
import org.gso.brinder.profile.repository.ProfileRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.oauth2.jwt.JwtDecoder;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ProfileServiceTest {

    @Autowired
    ProfileService profileService;

    @MockBean
    ProfileRepository profileRepository;

    @Test
    public void testProfileCreation() {
        ProfileModel profileModel = ProfileModel.builder().userId("toto").build();
        ProfileModel createdProfile = ProfileModel.builder()
                        .id("myId")
                        .userId("toto")
                        .created(LocalDateTime.now())
                        .modified(LocalDateTime.now())
                        .build();
        when(profileRepository.save(profileModel)).thenReturn(createdProfile);
        profileModel = profileService.createProfile(profileModel);
        assertThat(profileModel).isNotNull();
        assertThat(profileModel.getModified()).isNotNull();
        assertThat(profileModel.getUserId()).isEqualTo("toto");
    }
}
