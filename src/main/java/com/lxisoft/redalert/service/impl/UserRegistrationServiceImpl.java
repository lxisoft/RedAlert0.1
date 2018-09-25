package com.lxisoft.redalert.service.impl;

import com.lxisoft.redalert.service.UserRegistrationService;
import com.lxisoft.redalert.domain.UserRegistration;
import com.lxisoft.redalert.repository.UserRegistrationRepository;
import com.lxisoft.redalert.service.dto.UserRegistrationDTO;
import com.lxisoft.redalert.service.mapper.UserRegistrationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing UserRegistration.
 */
@Service
@Transactional
public class UserRegistrationServiceImpl implements UserRegistrationService {

    private final Logger log = LoggerFactory.getLogger(UserRegistrationServiceImpl.class);

    private final UserRegistrationRepository userRegistrationRepository;

    private final UserRegistrationMapper userRegistrationMapper;

    public UserRegistrationServiceImpl(UserRegistrationRepository userRegistrationRepository, UserRegistrationMapper userRegistrationMapper) {
        this.userRegistrationRepository = userRegistrationRepository;
        this.userRegistrationMapper = userRegistrationMapper;
    }

    /**
     * Save a userRegistration.
     *
     * @param userRegistrationDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public UserRegistrationDTO save(UserRegistrationDTO userRegistrationDTO) {
        log.debug("Request to save UserRegistration : {}", userRegistrationDTO);
        UserRegistration userRegistration = userRegistrationMapper.toEntity(userRegistrationDTO);
        userRegistration = userRegistrationRepository.save(userRegistration);
        return userRegistrationMapper.toDto(userRegistration);
    }

    /**
     * Get all the userRegistrations.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<UserRegistrationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all UserRegistrations");
        return userRegistrationRepository.findAll(pageable)
            .map(userRegistrationMapper::toDto);
    }

    /**
     * Get one userRegistration by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public UserRegistrationDTO findOne(Long id) {
        log.debug("Request to get UserRegistration : {}", id);
        UserRegistration userRegistration = userRegistrationRepository.findOneWithEagerRelationships(id);
        return userRegistrationMapper.toDto(userRegistration);
    }

    /**
     * Delete the userRegistration by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete UserRegistration : {}", id);
        userRegistrationRepository.delete(id);
    }
}
