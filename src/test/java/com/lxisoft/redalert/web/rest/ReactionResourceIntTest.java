package com.lxisoft.redalert.web.rest;

import com.lxisoft.redalert.RedAlertApp;

import com.lxisoft.redalert.domain.Reaction;
import com.lxisoft.redalert.repository.ReactionRepository;
import com.lxisoft.redalert.service.ReactionService;
import com.lxisoft.redalert.service.dto.ReactionDTO;
import com.lxisoft.redalert.service.mapper.ReactionMapper;
import com.lxisoft.redalert.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;


import static com.lxisoft.redalert.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.lxisoft.redalert.domain.enumeration.TypeOfReaction;
/**
 * Test class for the ReactionResource REST controller.
 *
 * @see ReactionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RedAlertApp.class)
public class ReactionResourceIntTest {

    private static final String DEFAULT_USER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_USER_NAME = "BBBBBBBBBB";

    private static final TypeOfReaction DEFAULT_TYPE = TypeOfReaction.FAKE;
    private static final TypeOfReaction UPDATED_TYPE = TypeOfReaction.VALID;

    @Autowired
    private ReactionRepository reactionRepository;

    @Autowired
    private ReactionMapper reactionMapper;
    
    @Autowired
    private ReactionService reactionService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restReactionMockMvc;

    private Reaction reaction;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ReactionResource reactionResource = new ReactionResource(reactionService);
        this.restReactionMockMvc = MockMvcBuilders.standaloneSetup(reactionResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Reaction createEntity(EntityManager em) {
        Reaction reaction = new Reaction()
            .userName(DEFAULT_USER_NAME)
            .type(DEFAULT_TYPE);
        return reaction;
    }

    @Before
    public void initTest() {
        reaction = createEntity(em);
    }

    @Test
    @Transactional
    public void createReaction() throws Exception {
        int databaseSizeBeforeCreate = reactionRepository.findAll().size();

        // Create the Reaction
        ReactionDTO reactionDTO = reactionMapper.toDto(reaction);
        restReactionMockMvc.perform(post("/api/reactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reactionDTO)))
            .andExpect(status().isCreated());

        // Validate the Reaction in the database
        List<Reaction> reactionList = reactionRepository.findAll();
        assertThat(reactionList).hasSize(databaseSizeBeforeCreate + 1);
        Reaction testReaction = reactionList.get(reactionList.size() - 1);
        assertThat(testReaction.getUserName()).isEqualTo(DEFAULT_USER_NAME);
        assertThat(testReaction.getType()).isEqualTo(DEFAULT_TYPE);
    }

    @Test
    @Transactional
    public void createReactionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = reactionRepository.findAll().size();

        // Create the Reaction with an existing ID
        reaction.setId(1L);
        ReactionDTO reactionDTO = reactionMapper.toDto(reaction);

        // An entity with an existing ID cannot be created, so this API call must fail
        restReactionMockMvc.perform(post("/api/reactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reactionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Reaction in the database
        List<Reaction> reactionList = reactionRepository.findAll();
        assertThat(reactionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllReactions() throws Exception {
        // Initialize the database
        reactionRepository.saveAndFlush(reaction);

        // Get all the reactionList
        restReactionMockMvc.perform(get("/api/reactions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(reaction.getId().intValue())))
            .andExpect(jsonPath("$.[*].userName").value(hasItem(DEFAULT_USER_NAME.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())));
    }
    
    @Test
    @Transactional
    public void getReaction() throws Exception {
        // Initialize the database
        reactionRepository.saveAndFlush(reaction);

        // Get the reaction
        restReactionMockMvc.perform(get("/api/reactions/{id}", reaction.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(reaction.getId().intValue()))
            .andExpect(jsonPath("$.userName").value(DEFAULT_USER_NAME.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingReaction() throws Exception {
        // Get the reaction
        restReactionMockMvc.perform(get("/api/reactions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateReaction() throws Exception {
        // Initialize the database
        reactionRepository.saveAndFlush(reaction);

        int databaseSizeBeforeUpdate = reactionRepository.findAll().size();

        // Update the reaction
        Reaction updatedReaction = reactionRepository.findById(reaction.getId()).get();
        // Disconnect from session so that the updates on updatedReaction are not directly saved in db
        em.detach(updatedReaction);
        updatedReaction
            .userName(UPDATED_USER_NAME)
            .type(UPDATED_TYPE);
        ReactionDTO reactionDTO = reactionMapper.toDto(updatedReaction);

        restReactionMockMvc.perform(put("/api/reactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reactionDTO)))
            .andExpect(status().isOk());

        // Validate the Reaction in the database
        List<Reaction> reactionList = reactionRepository.findAll();
        assertThat(reactionList).hasSize(databaseSizeBeforeUpdate);
        Reaction testReaction = reactionList.get(reactionList.size() - 1);
        assertThat(testReaction.getUserName()).isEqualTo(UPDATED_USER_NAME);
        assertThat(testReaction.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingReaction() throws Exception {
        int databaseSizeBeforeUpdate = reactionRepository.findAll().size();

        // Create the Reaction
        ReactionDTO reactionDTO = reactionMapper.toDto(reaction);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restReactionMockMvc.perform(put("/api/reactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reactionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Reaction in the database
        List<Reaction> reactionList = reactionRepository.findAll();
        assertThat(reactionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteReaction() throws Exception {
        // Initialize the database
        reactionRepository.saveAndFlush(reaction);

        int databaseSizeBeforeDelete = reactionRepository.findAll().size();

        // Get the reaction
        restReactionMockMvc.perform(delete("/api/reactions/{id}", reaction.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Reaction> reactionList = reactionRepository.findAll();
        assertThat(reactionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Reaction.class);
        Reaction reaction1 = new Reaction();
        reaction1.setId(1L);
        Reaction reaction2 = new Reaction();
        reaction2.setId(reaction1.getId());
        assertThat(reaction1).isEqualTo(reaction2);
        reaction2.setId(2L);
        assertThat(reaction1).isNotEqualTo(reaction2);
        reaction1.setId(null);
        assertThat(reaction1).isNotEqualTo(reaction2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ReactionDTO.class);
        ReactionDTO reactionDTO1 = new ReactionDTO();
        reactionDTO1.setId(1L);
        ReactionDTO reactionDTO2 = new ReactionDTO();
        assertThat(reactionDTO1).isNotEqualTo(reactionDTO2);
        reactionDTO2.setId(reactionDTO1.getId());
        assertThat(reactionDTO1).isEqualTo(reactionDTO2);
        reactionDTO2.setId(2L);
        assertThat(reactionDTO1).isNotEqualTo(reactionDTO2);
        reactionDTO1.setId(null);
        assertThat(reactionDTO1).isNotEqualTo(reactionDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(reactionMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(reactionMapper.fromId(null)).isNull();
    }
}
