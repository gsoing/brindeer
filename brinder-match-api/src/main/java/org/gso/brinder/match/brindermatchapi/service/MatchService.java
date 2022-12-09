package org.gso.brinder.match.brindermatchapi.service;

import lombok.RequiredArgsConstructor;
import org.gso.brinder.common.exception.NotFoundException;
import org.gso.brinder.match.brindermatchapi.dto.ProfileMatchDto;
import org.gso.brinder.match.brindermatchapi.model.ProfileModel;
import org.gso.brinder.match.brindermatchapi.repository.ProfileRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MatchService {

    private final ProfileRepository profileRepository;

    public ProfileModel getProfile(String profileId) {
        return profileRepository.findById(profileId).orElseThrow(() -> NotFoundException.DEFAULT);
    }

    public ProfileModel updateLocation(ProfileModel profilModel){
        ProfileModel profile = this.getProfile(profilModel.getId());
        profile.setId(profilModel.getId());
        return profileRepository.save(profile);
    }

    public List<ProfileMatchDto> findMatches(String id){
        List<ProfileMatchDto> profileMatchDtos = new ArrayList<>();
        ProfileModel profileModel = this.getProfile(id);

        List<ProfileModel> profileModels = profileRepository.findAround100m(profileModel.getLongitude(), profileModel.getLatitude());
        for (ProfileModel p : profileModels){
            profileMatchDtos.add(p.toProfileDto());
        }
        return profileMatchDtos;
    }


}
