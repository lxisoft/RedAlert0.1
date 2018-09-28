package com.lxisoft.redalert.service;

import com.lxisoft.redalert.service.dto.UserRegistrationDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing UserRegistration.
 */
public interface UserRegistrationService {

    /**
     * Save a userRegistration.
     *
     * @param userRegistrationDTO the entity to save
     * @return the persisted entity
     */
    UserRegistrationDTO save(UserRegistrationDTO userRegistrationDTO);

    /**
     * Get all the userRegistrations.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<UserRegistrationDTO> findAll(Pageable pageable);

    /**
     * Get all the UserRegistration with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    Page<UserRegistrationDTO> findAllWithEagerRelationships(Pageable pageable);
    
    /**
     * Get the "id" userRegistration.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<UserRegistrationDTO> findOne(Long id);

    /**
     * Delete the "id" userRegistration.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
