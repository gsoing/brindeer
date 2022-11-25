package org.gso.brinder.profile.service;

import lombok.RequiredArgsConstructor;
import org.gso.brinder.common.exception.NotFoundException;
import org.gso.brinder.profile.model.ProfileModel;
import org.gso.brinder.profile.repository.CustomProfileRepository;
import org.gso.brinder.profile.repository.ProfileRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final CustomProfileRepository customProfileRepository;

    public ProfileModel createProfile(ProfileModel userModel) {
        LocalDateTime createdDate = LocalDateTime.now();
        userModel.setCreated(createdDate);
        return profileRepository.save(userModel);
    }

    public ProfileModel getProfile(String profileId) {
        return profileRepository.findById(profileId).orElseThrow(() -> NotFoundException.DEFAULT);
    }

    public ProfileModel updateProfile(ProfileModel profileToUpdate) {
        ProfileModel profileModel = this.getProfile(profileToUpdate.getId());
        profileModel.setUserId(profileToUpdate.getUserId());
        profileModel.setAge(profileToUpdate.getAge());
        profileModel.setModified(profileToUpdate.getModified());
        return profileRepository.save(profileModel);
    }

    public Page<ProfileModel> searchProfiles(Criteria criteria, Pageable pageable) {
        return customProfileRepository.searchProfiles(criteria, pageable);
    }

    public Page<ProfileModel> searchByMail(String mail, Pageable pageable) {
        return profileRepository.findByMail(mail, pageable);
    }

}
