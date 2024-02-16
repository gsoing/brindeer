package org.gso.brinder.match.service;

import org.gso.brinder.common.exception.NotFoundException;
import org.gso.brinder.match.model.ProfileModel;
import org.gso.brinder.match.repository.CustomMatchRepository;
import org.gso.brinder.match.repository.MatchRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MatchService {

    private final MatchRepository matchRepository;
    private final CustomMatchRepository customMatchRepository;

}
