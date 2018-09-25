package com.lxisoft.redalert.service;

import com.lxisoft.redalert.service.dto.UserRegistrationDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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
     * Get the "id" userRegistration.
     *
     * @param id the id of the entity
     * @return the entity
     */
    UserRegistrationDTO findOne(Long id);

    /**
     * Delete the "id" userRegistration.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
