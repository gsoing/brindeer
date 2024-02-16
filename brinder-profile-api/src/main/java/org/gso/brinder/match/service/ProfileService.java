package org.gso.brinder.match.service;

import lombok.RequiredArgsConstructor;
import org.gso.brinder.common.exception.NotFoundException;
import org.gso.brinder.match.model.ProfileModel;
import org.gso.brinder.match.repository.CustomProfileRepository;
import org.gso.brinder.match.repository.ProfileRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final CustomProfileRepository customProfileRepository;

    public ProfileModel createProfile(ProfileModel userModel) {
        return profileRepository.save(userModel);
    }

    public ProfileModel getProfile(String profileId) {
        return profileRepository.findById(profileId).orElseThrow(() -> NotFoundException.DEFAULT);
    }

    public ProfileModel updateProfile(ProfileModel profileToUpdate) {
        ProfileModel profileModel = this.getProfile(profileToUpdate.getId());
        profileModel.setUserId(profileToUpdate.getUserId());
        return profileRepository.save(profileModel);
    }

    public ProfileModel updateProfileLocation(ProfileModel profileToUpdate) {
        ProfileModel profileModel = this.getProfile(profileToUpdate.getId());
        profileModel.setLatitude(profileToUpdate.getLatitude());
        profileModel.setLongitude(profileToUpdate.getLongitude());
        return profileRepository.save(profileModel);

    }


    public Page<ProfileModel> searchProfiles(Criteria criteria, Pageable pageable) {
        return customProfileRepository.searchProfiles(criteria, pageable);
    }

    public Page<ProfileModel> searchByMail(String mail, Pageable pageable) {
        return profileRepository.findByMail(mail, pageable);
    }

}
