package com.lxisoft.redalert.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.lxisoft.redalert.service.UserFeedService;
import com.lxisoft.redalert.web.rest.errors.BadRequestAlertException;
import com.lxisoft.redalert.web.rest.util.HeaderUtil;
import com.lxisoft.redalert.web.rest.util.PaginationUtil;
import com.lxisoft.redalert.service.dto.UserFeedDTO;
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
 * REST controller for managing UserFeed.
 */
@RestController
@RequestMapping("/api")
public class UserFeedResource {

    private final Logger log = LoggerFactory.getLogger(UserFeedResource.class);

    private static final String ENTITY_NAME = "redAlertUserFeed";

    private final UserFeedService userFeedService;

    public UserFeedResource(UserFeedService userFeedService) {
        this.userFeedService = userFeedService;
    }

    /**
     * POST  /user-feeds : Create a new userFeed.
     *
     * @param userFeedDTO the userFeedDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new userFeedDTO, or with status 400 (Bad Request) if the userFeed has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/user-feeds")
    @Timed
    public ResponseEntity<UserFeedDTO> createUserFeed(@RequestBody UserFeedDTO userFeedDTO) throws URISyntaxException {
        log.debug("REST request to save UserFeed : {}", userFeedDTO);
        if (userFeedDTO.getId() != null) {
            throw new BadRequestAlertException("A new userFeed cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserFeedDTO result = userFeedService.save(userFeedDTO);
        return ResponseEntity.created(new URI("/api/user-feeds/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /user-feeds : Updates an existing userFeed.
     *
     * @param userFeedDTO the userFeedDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated userFeedDTO,
     * or with status 400 (Bad Request) if the userFeedDTO is not valid,
     * or with status 500 (Internal Server Error) if the userFeedDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/user-feeds")
    @Timed
    public ResponseEntity<UserFeedDTO> updateUserFeed(@RequestBody UserFeedDTO userFeedDTO) throws URISyntaxException {
        log.debug("REST request to update UserFeed : {}", userFeedDTO);
        if (userFeedDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        UserFeedDTO result = userFeedService.save(userFeedDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, userFeedDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /user-feeds : get all the userFeeds.
     *
     * @param pageable the pagination information
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many)
     * @return the ResponseEntity with status 200 (OK) and the list of userFeeds in body
     */
    @GetMapping("/user-feeds")
    @Timed
    public ResponseEntity<List<UserFeedDTO>> getAllUserFeeds(Pageable pageable, @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get a page of UserFeeds");
        Page<UserFeedDTO> page;
        if (eagerload) {
            page = userFeedService.findAllWithEagerRelationships(pageable);
        } else {
            page = userFeedService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, String.format("/api/user-feeds?eagerload=%b", eagerload));
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /user-feeds/:id : get the "id" userFeed.
     *
     * @param id the id of the userFeedDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the userFeedDTO, or with status 404 (Not Found)
     */
    @GetMapping("/user-feeds/{id}")
    @Timed
    public ResponseEntity<UserFeedDTO> getUserFeed(@PathVariable Long id) {
        log.debug("REST request to get UserFeed : {}", id);
        Optional<UserFeedDTO> userFeedDTO = userFeedService.findOne(id);
        return ResponseUtil.wrapOrNotFound(userFeedDTO);
    }

    /**
     * DELETE  /user-feeds/:id : delete the "id" userFeed.
     *
     * @param id the id of the userFeedDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/user-feeds/{id}")
    @Timed
    public ResponseEntity<Void> deleteUserFeed(@PathVariable Long id) {
        log.debug("REST request to delete UserFeed : {}", id);
        userFeedService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
