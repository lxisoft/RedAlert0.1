package com.lxisoft.redalert.service.impl;

import com.lxisoft.redalert.service.ReactionService;
import com.lxisoft.redalert.domain.Reaction;
import com.lxisoft.redalert.repository.ReactionRepository;
import com.lxisoft.redalert.service.dto.ReactionDTO;
import com.lxisoft.redalert.service.mapper.ReactionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Reaction.
 */
@Service
@Transactional
public class ReactionServiceImpl implements ReactionService {

    private final Logger log = LoggerFactory.getLogger(ReactionServiceImpl.class);

    private final ReactionRepository reactionRepository;

    private final ReactionMapper reactionMapper;

    public ReactionServiceImpl(ReactionRepository reactionRepository, ReactionMapper reactionMapper) {
        this.reactionRepository = reactionRepository;
        this.reactionMapper = reactionMapper;
    }

    /**
     * Save a reaction.
     *
     * @param reactionDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ReactionDTO save(ReactionDTO reactionDTO) {
        log.debug("Request to save Reaction : {}", reactionDTO);
        Reaction reaction = reactionMapper.toEntity(reactionDTO);
        reaction = reactionRepository.save(reaction);
        return reactionMapper.toDto(reaction);
    }

    /**
     * Get all the reactions.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ReactionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Reactions");
        return reactionRepository.findAll(pageable)
            .map(reactionMapper::toDto);
    }

    /**
     * Get one reaction by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ReactionDTO findOne(Long id) {
        log.debug("Request to get Reaction : {}", id);
        Reaction reaction = reactionRepository.findOne(id);
        return reactionMapper.toDto(reaction);
    }

    /**
     * Delete the reaction by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Reaction : {}", id);
        reactionRepository.delete(id);
    }
}
