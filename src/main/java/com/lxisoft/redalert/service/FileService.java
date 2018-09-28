package com.lxisoft.redalert.service;

import com.lxisoft.redalert.service.dto.FileDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing File.
 */
public interface FileService {

    /**
     * Save a file.
     *
     * @param attachments the entity to save
     * @return the persisted entity
     */
    FileDTO save(byte[] attachments);

    /**
     * Get all the files.
     *
     * @param pageable the pagination informationre
     * @return the list of entities
     */
    Page<FileDTO> findAll(Pageable pageable);

    /**
     * Get the "id" file.
     *
     * @param id the id of the entity
     * @return the entity
     */
    FileDTO findOne(Long id);

    /**
     * Delete the "id" file.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

	FileDTO save(FileDTO fileDTO);
}
