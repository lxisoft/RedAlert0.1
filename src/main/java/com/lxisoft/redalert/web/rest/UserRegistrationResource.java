package com.lxisoft.redalert.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.lxisoft.redalert.service.UserRegistrationService;
import com.lxisoft.redalert.web.rest.errors.BadRequestAlertException;
import com.lxisoft.redalert.web.rest.util.HeaderUtil;
import com.lxisoft.redalert.web.rest.util.PaginationUtil;
import com.lxisoft.redalert.service.dto.UserRegistrationDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing UserRegistration.
 */
@RestController
@RequestMapping("/api")
public class UserRegistrationResource {

    private final Logger log = LoggerFactory.getLogger(UserRegistrationResource.class);

    private static final String ENTITY_NAME = "userRegistration";

    private final UserRegistrationService userRegistrationService;

    public UserRegistrationResource(UserRegistrationService userRegistrationService) {
        this.userRegistrationService = userRegistrationService;
    }

    /**
     * POST  /user-registrations : Create a new userRegistration.
     *
     * @param userRegistrationDTO the userRegistrationDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new userRegistrationDTO, or with status 400 (Bad Request) if the userRegistration has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/user-registrations")
    @Timed
    public ResponseEntity<UserRegistrationDTO> createUserRegistration(@RequestBody UserRegistrationDTO userRegistrationDTO) throws URISyntaxException {
        log.debug("REST request to save UserRegistration : {}", userRegistrationDTO);
        if (userRegistrationDTO.getId() != null) {
            throw new BadRequestAlertException("A new userRegistration cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserRegistrationDTO result = userRegistrationService.save(userRegistrationDTO);
        return ResponseEntity.created(new URI("/api/user-registrations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /user-registrations : Updates an existing userRegistration.
     *
     * @param userRegistrationDTO the userRegistrationDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated userRegistrationDTO,
     * or with status 400 (Bad Request) if the userRegistrationDTO is not valid,
     * or with status 500 (Internal Server Error) if the userRegistrationDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/user-registrations")
    @Timed
    public ResponseEntity<UserRegistrationDTO> updateUserRegistration(@RequestBody UserRegistrationDTO userRegistrationDTO) throws URISyntaxException {
        log.debug("REST request to update UserRegistration : {}", userRegistrationDTO);
        if (userRegistrationDTO.getId() == null) {
            return createUserRegistration(userRegistrationDTO);
        }
        UserRegistrationDTO result = userRegistrationService.save(userRegistrationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, userRegistrationDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /user-registrations : get all the userRegistrations.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of userRegistrations in body
     */
    @GetMapping("/user-registrations")
    @Timed
    public ResponseEntity<List<UserRegistrationDTO>> getAllUserRegistrations(Pageable pageable) {
        log.debug("REST request to get a page of UserRegistrations");
        Page<UserRegistrationDTO> page = userRegistrationService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/user-registrations");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /user-registrations/:id : get the "id" userRegistration.
     *
     * @param id the id of the userRegistrationDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the userRegistrationDTO, or with status 404 (Not Found)
     */
    @GetMapping("/user-registrations/{id}")
    @Timed
    public ResponseEntity<UserRegistrationDTO> getUserRegistration(@PathVariable Long id) {
        log.debug("REST request to get UserRegistration : {}", id);
        UserRegistrationDTO userRegistrationDTO = userRegistrationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(userRegistrationDTO));
    }

    /**
     * DELETE  /user-registrations/:id : delete the "id" userRegistration.
     *
     * @param id the id of the userRegistrationDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/user-registrations/{id}")
    @Timed
    public ResponseEntity<Void> deleteUserRegistration(@PathVariable Long id) {
        log.debug("REST request to delete UserRegistration : {}", id);
        userRegistrationService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
