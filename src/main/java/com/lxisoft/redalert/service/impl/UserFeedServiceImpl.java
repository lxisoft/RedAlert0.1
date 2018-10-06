package com.lxisoft.redalert.service.impl;

import com.lxisoft.redalert.service.UserFeedService;
import com.lxisoft.redalert.domain.UserFeed;
import com.lxisoft.redalert.repository.UserFeedRepository;
import com.lxisoft.redalert.service.dto.UserFeedDTO;
import com.lxisoft.redalert.service.mapper.UserFeedMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;
/**
 * Service Implementation for managing UserFeed.
 */
@Service
@Transactional
public class UserFeedServiceImpl implements UserFeedService {

    private final Logger log = LoggerFactory.getLogger(UserFeedServiceImpl.class);

    private final UserFeedRepository userFeedRepository;

    private final UserFeedMapper userFeedMapper;

    public UserFeedServiceImpl(UserFeedRepository userFeedRepository, UserFeedMapper userFeedMapper) {
        this.userFeedRepository = userFeedRepository;
        this.userFeedMapper = userFeedMapper;
    }

    /**
     * Save a userFeed.
     *
     * @param userFeedDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public UserFeedDTO save(UserFeedDTO userFeedDTO) {
        log.debug("Request to save UserFeed : {}", userFeedDTO);
        UserFeed userFeed = userFeedMapper.toEntity(userFeedDTO);
        userFeed = userFeedRepository.save(userFeed);
        return userFeedMapper.toDto(userFeed);
    }

    /**
     * Get all the userFeeds.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<UserFeedDTO> findAll(Pageable pageable) {
        log.debug("Request to get all UserFeeds");
        return userFeedRepository.findAll(pageable)
            .map(userFeedMapper::toDto);
    }

    /**
     * Get all the UserFeed with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    public Page<UserFeedDTO> findAllWithEagerRelationships(Pageable pageable) {
        return userFeedRepository.findAllWithEagerRelationships(pageable).map(userFeedMapper::toDto);
    }
    

    /**
     * Get one userFeed by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<UserFeedDTO> findOne(Long id) {
        log.debug("Request to get UserFeed : {}", id);
        return userFeedRepository.findOneWithEagerRelationships(id)
            .map(userFeedMapper::toDto);
    }

    /**
     * Delete the userFeed by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete UserFeed : {}", id);
        userFeedRepository.deleteById(id);
    }
}
