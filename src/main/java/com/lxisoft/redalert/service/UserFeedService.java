package com.lxisoft.redalert.service;

import com.lxisoft.redalert.service.dto.UserFeedDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing UserFeed.
 */
public interface UserFeedService {

    /**
     * Save a userFeed.
     *
     * @param userFeedDTO the entity to save
     * @return the persisted entity
     */
    UserFeedDTO save(UserFeedDTO userFeedDTO);

    /**
     * Get all the userFeeds.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<UserFeedDTO> findAll(Pageable pageable);

    /**
     * Get all the UserFeed with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    Page<UserFeedDTO> findAllWithEagerRelationships(Pageable pageable);
    
    /**
     * Get the "id" userFeed.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<UserFeedDTO> findOne(Long id);

    /**
     * Delete the "id" userFeed.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
