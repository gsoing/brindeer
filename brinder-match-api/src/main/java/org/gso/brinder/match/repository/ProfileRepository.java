package org.gso.brinder.match.repository;

import org.gso.brinder.match.model.ProfileModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileRepository extends PagingAndSortingRepository<ProfileModel, String> {

    Page<ProfileModel> findByMail(String mail, Pageable pageable);

}
