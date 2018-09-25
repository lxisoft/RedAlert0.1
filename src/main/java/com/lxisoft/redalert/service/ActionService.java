package com.lxisoft.redalert.service;

import com.lxisoft.redalert.service.dto.ActionDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Action.
 */
public interface ActionService {

    /**
     * Save a action.
     *
     * @param actionDTO the entity to save
     * @return the persisted entity
     */
    ActionDTO save(ActionDTO actionDTO);

    /**
     * Get all the actions.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ActionDTO> findAll(Pageable pageable);

    /**
     * Get the "id" action.
     *
     * @param id the id of the entity
     * @return the entity
     */
    ActionDTO findOne(Long id);

    /**
     * Delete the "id" action.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
