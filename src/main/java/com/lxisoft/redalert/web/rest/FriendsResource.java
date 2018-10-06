package com.lxisoft.redalert.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.lxisoft.redalert.service.FriendsService;
import com.lxisoft.redalert.web.rest.errors.BadRequestAlertException;
import com.lxisoft.redalert.web.rest.util.HeaderUtil;
import com.lxisoft.redalert.web.rest.util.PaginationUtil;
import com.lxisoft.redalert.service.dto.FriendsDTO;
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
 * REST controller for managing Friends.
 */
@RestController
@RequestMapping("/api")
public class FriendsResource {

    private final Logger log = LoggerFactory.getLogger(FriendsResource.class);

    private static final String ENTITY_NAME = "redAlertFriends";

    private final FriendsService friendsService;

    public FriendsResource(FriendsService friendsService) {
        this.friendsService = friendsService;
    }

    /**
     * POST  /friends : Create a new friends.
     *
     * @param friendsDTO the friendsDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new friendsDTO, or with status 400 (Bad Request) if the friends has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/friends")
    @Timed
    public ResponseEntity<FriendsDTO> createFriends(@RequestBody FriendsDTO friendsDTO) throws URISyntaxException {
        log.debug("REST request to save Friends : {}", friendsDTO);
        if (friendsDTO.getId() != null) {
            throw new BadRequestAlertException("A new friends cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FriendsDTO result = friendsService.save(friendsDTO);
        return ResponseEntity.created(new URI("/api/friends/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /friends : Updates an existing friends.
     *
     * @param friendsDTO the friendsDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated friendsDTO,
     * or with status 400 (Bad Request) if the friendsDTO is not valid,
     * or with status 500 (Internal Server Error) if the friendsDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/friends")
    @Timed
    public ResponseEntity<FriendsDTO> updateFriends(@RequestBody FriendsDTO friendsDTO) throws URISyntaxException {
        log.debug("REST request to update Friends : {}", friendsDTO);
        if (friendsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        FriendsDTO result = friendsService.save(friendsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, friendsDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /friends : get all the friends.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of friends in body
     */
    @GetMapping("/friends")
    @Timed
    public ResponseEntity<List<FriendsDTO>> getAllFriends(Pageable pageable) {
        log.debug("REST request to get a page of Friends");
        Page<FriendsDTO> page = friendsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/friends");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /friends/:id : get the "id" friends.
     *
     * @param id the id of the friendsDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the friendsDTO, or with status 404 (Not Found)
     */
    @GetMapping("/friends/{id}")
    @Timed
    public ResponseEntity<FriendsDTO> getFriends(@PathVariable Long id) {
        log.debug("REST request to get Friends : {}", id);
        Optional<FriendsDTO> friendsDTO = friendsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(friendsDTO);
    }

    /**
     * DELETE  /friends/:id : delete the "id" friends.
     *
     * @param id the id of the friendsDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/friends/{id}")
    @Timed
    public ResponseEntity<Void> deleteFriends(@PathVariable Long id) {
        log.debug("REST request to delete Friends : {}", id);
        friendsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
