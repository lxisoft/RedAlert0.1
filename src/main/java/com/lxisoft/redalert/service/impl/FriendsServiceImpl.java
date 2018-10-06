package com.lxisoft.redalert.service.impl;

import com.lxisoft.redalert.service.FriendsService;
import com.lxisoft.redalert.domain.Friends;
import com.lxisoft.redalert.repository.FriendsRepository;
import com.lxisoft.redalert.service.dto.FriendsDTO;
import com.lxisoft.redalert.service.mapper.FriendsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;
/**
 * Service Implementation for managing Friends.
 */
@Service
@Transactional
public class FriendsServiceImpl implements FriendsService {

    private final Logger log = LoggerFactory.getLogger(FriendsServiceImpl.class);

    private final FriendsRepository friendsRepository;

    private final FriendsMapper friendsMapper;

    public FriendsServiceImpl(FriendsRepository friendsRepository, FriendsMapper friendsMapper) {
        this.friendsRepository = friendsRepository;
        this.friendsMapper = friendsMapper;
    }

    /**
     * Save a friends.
     *
     * @param friendsDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public FriendsDTO save(FriendsDTO friendsDTO) {
        log.debug("Request to save Friends : {}", friendsDTO);
        Friends friends = friendsMapper.toEntity(friendsDTO);
        friends = friendsRepository.save(friends);
        return friendsMapper.toDto(friends);
    }

    /**
     * Get all the friends.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<FriendsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Friends");
        return friendsRepository.findAll(pageable)
            .map(friendsMapper::toDto);
    }


    /**
     * Get one friends by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<FriendsDTO> findOne(Long id) {
        log.debug("Request to get Friends : {}", id);
        return friendsRepository.findById(id)
            .map(friendsMapper::toDto);
    }

    /**
     * Delete the friends by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Friends : {}", id);
        friendsRepository.deleteById(id);
    }
}
