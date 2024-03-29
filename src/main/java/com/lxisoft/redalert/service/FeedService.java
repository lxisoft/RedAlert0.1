package com.lxisoft.redalert.service;

import com.lxisoft.redalert.service.dto.FeedDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Feed.
 */
public interface FeedService {

    /**
     * Save a feed.
     *
     * @param feedDTO the entity to save
     * @return the persisted entity
     */
    FeedDTO save(FeedDTO feedDTO);

    /**
     * Get all the feeds.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<FeedDTO> findAll(Pageable pageable);

    /**
     * Get the "id" feed.
     *
     * @param id the id of the entity
     * @return the entity
     */
    FeedDTO findOne(Long id);

    /**
     * Delete the "id" feed.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
