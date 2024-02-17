package org.gso.brinder.match.service;

import lombok.RequiredArgsConstructor;
import org.gso.brinder.common.exception.NotFoundException;
import org.gso.brinder.match.model.MatchModel;
import org.gso.brinder.match.repository.CustomMatchRepository;
import org.gso.brinder.match.repository.MatchRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MatchService {
}
