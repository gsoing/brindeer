package org.gso.brinder.match.repository;

import org.gso.brinder.match.model.MatchModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MatchRepository extends PagingAndSortingRepository<MatchModel, String>, CrudRepository<MatchModel, String> {
}
