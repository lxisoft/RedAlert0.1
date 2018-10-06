package com.lxisoft.redalert.service;

import com.lxisoft.redalert.service.dto.FriendsDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Friends.
 */
public interface FriendsService {

    /**
     * Save a friends.
     *
     * @param friendsDTO the entity to save
     * @return the persisted entity
     */
    FriendsDTO save(FriendsDTO friendsDTO);

    /**
     * Get all the friends.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<FriendsDTO> findAll(Pageable pageable);


    /**
     * Get the "id" friends.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<FriendsDTO> findOne(Long id);

    /**
     * Delete the "id" friends.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
