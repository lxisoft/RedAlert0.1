package com.lxisoft.redalert.service;

import com.lxisoft.redalert.service.dto.ReactionDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Reaction.
 */
public interface ReactionService {

    /**
     * Save a reaction.
     *
     * @param reactionDTO the entity to save
     * @return the persisted entity
     */
    ReactionDTO save(ReactionDTO reactionDTO);

    /**
     * Get all the reactions.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ReactionDTO> findAll(Pageable pageable);

    /**
     * Get the "id" reaction.
     *
     * @param id the id of the entity
     * @return the entity
     */
    ReactionDTO findOne(Long id);

    /**
     * Delete the "id" reaction.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
