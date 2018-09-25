package com.lxisoft.redalert.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.lxisoft.redalert.service.ReactionService;
import com.lxisoft.redalert.web.rest.errors.BadRequestAlertException;
import com.lxisoft.redalert.web.rest.util.HeaderUtil;
import com.lxisoft.redalert.web.rest.util.PaginationUtil;
import com.lxisoft.redalert.service.dto.ReactionDTO;
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
 * REST controller for managing Reaction.
 */
@RestController
@RequestMapping("/api")
public class ReactionResource {

    private final Logger log = LoggerFactory.getLogger(ReactionResource.class);

    private static final String ENTITY_NAME = "reaction";

    private final ReactionService reactionService;

    public ReactionResource(ReactionService reactionService) {
        this.reactionService = reactionService;
    }

    /**
     * POST  /reactions : Create a new reaction.
     *
     * @param reactionDTO the reactionDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new reactionDTO, or with status 400 (Bad Request) if the reaction has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/reactions")
    @Timed
    public ResponseEntity<ReactionDTO> createReaction(@RequestBody ReactionDTO reactionDTO) throws URISyntaxException {
        log.debug("REST request to save Reaction : {}", reactionDTO);
        if (reactionDTO.getId() != null) {
            throw new BadRequestAlertException("A new reaction cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ReactionDTO result = reactionService.save(reactionDTO);
        return ResponseEntity.created(new URI("/api/reactions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /reactions : Updates an existing reaction.
     *
     * @param reactionDTO the reactionDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated reactionDTO,
     * or with status 400 (Bad Request) if the reactionDTO is not valid,
     * or with status 500 (Internal Server Error) if the reactionDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/reactions")
    @Timed
    public ResponseEntity<ReactionDTO> updateReaction(@RequestBody ReactionDTO reactionDTO) throws URISyntaxException {
        log.debug("REST request to update Reaction : {}", reactionDTO);
        if (reactionDTO.getId() == null) {
            return createReaction(reactionDTO);
        }
        ReactionDTO result = reactionService.save(reactionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, reactionDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /reactions : get all the reactions.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of reactions in body
     */
    @GetMapping("/reactions")
    @Timed
    public ResponseEntity<List<ReactionDTO>> getAllReactions(Pageable pageable) {
        log.debug("REST request to get a page of Reactions");
        Page<ReactionDTO> page = reactionService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/reactions");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /reactions/:id : get the "id" reaction.
     *
     * @param id the id of the reactionDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the reactionDTO, or with status 404 (Not Found)
     */
    @GetMapping("/reactions/{id}")
    @Timed
    public ResponseEntity<ReactionDTO> getReaction(@PathVariable Long id) {
        log.debug("REST request to get Reaction : {}", id);
        ReactionDTO reactionDTO = reactionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(reactionDTO));
    }

    /**
     * DELETE  /reactions/:id : delete the "id" reaction.
     *
     * @param id the id of the reactionDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/reactions/{id}")
    @Timed
    public ResponseEntity<Void> deleteReaction(@PathVariable Long id) {
        log.debug("REST request to delete Reaction : {}", id);
        reactionService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
