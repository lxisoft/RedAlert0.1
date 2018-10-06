package com.lxisoft.redalert.web.rest;

import com.lxisoft.redalert.RedAlertApp;

import com.lxisoft.redalert.domain.Friends;
import com.lxisoft.redalert.repository.FriendsRepository;
import com.lxisoft.redalert.service.FriendsService;
import com.lxisoft.redalert.service.dto.FriendsDTO;
import com.lxisoft.redalert.service.mapper.FriendsMapper;
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

/**
 * Test class for the FriendsResource REST controller.
 *
 * @see FriendsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RedAlertApp.class)
public class FriendsResourceIntTest {

    private static final Boolean DEFAULT_IMMEDIATE_FRIEND = false;
    private static final Boolean UPDATED_IMMEDIATE_FRIEND = true;

    @Autowired
    private FriendsRepository friendsRepository;

    @Autowired
    private FriendsMapper friendsMapper;
    
    @Autowired
    private FriendsService friendsService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restFriendsMockMvc;

    private Friends friends;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FriendsResource friendsResource = new FriendsResource(friendsService);
        this.restFriendsMockMvc = MockMvcBuilders.standaloneSetup(friendsResource)
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
    public static Friends createEntity(EntityManager em) {
        Friends friends = new Friends()
            .immediateFriend(DEFAULT_IMMEDIATE_FRIEND);
        return friends;
    }

    @Before
    public void initTest() {
        friends = createEntity(em);
    }

    @Test
    @Transactional
    public void createFriends() throws Exception {
        int databaseSizeBeforeCreate = friendsRepository.findAll().size();

        // Create the Friends
        FriendsDTO friendsDTO = friendsMapper.toDto(friends);
        restFriendsMockMvc.perform(post("/api/friends")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(friendsDTO)))
            .andExpect(status().isCreated());

        // Validate the Friends in the database
        List<Friends> friendsList = friendsRepository.findAll();
        assertThat(friendsList).hasSize(databaseSizeBeforeCreate + 1);
        Friends testFriends = friendsList.get(friendsList.size() - 1);
        assertThat(testFriends.isImmediateFriend()).isEqualTo(DEFAULT_IMMEDIATE_FRIEND);
    }

    @Test
    @Transactional
    public void createFriendsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = friendsRepository.findAll().size();

        // Create the Friends with an existing ID
        friends.setId(1L);
        FriendsDTO friendsDTO = friendsMapper.toDto(friends);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFriendsMockMvc.perform(post("/api/friends")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(friendsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Friends in the database
        List<Friends> friendsList = friendsRepository.findAll();
        assertThat(friendsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllFriends() throws Exception {
        // Initialize the database
        friendsRepository.saveAndFlush(friends);

        // Get all the friendsList
        restFriendsMockMvc.perform(get("/api/friends?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(friends.getId().intValue())))
            .andExpect(jsonPath("$.[*].immediateFriend").value(hasItem(DEFAULT_IMMEDIATE_FRIEND.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getFriends() throws Exception {
        // Initialize the database
        friendsRepository.saveAndFlush(friends);

        // Get the friends
        restFriendsMockMvc.perform(get("/api/friends/{id}", friends.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(friends.getId().intValue()))
            .andExpect(jsonPath("$.immediateFriend").value(DEFAULT_IMMEDIATE_FRIEND.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingFriends() throws Exception {
        // Get the friends
        restFriendsMockMvc.perform(get("/api/friends/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFriends() throws Exception {
        // Initialize the database
        friendsRepository.saveAndFlush(friends);

        int databaseSizeBeforeUpdate = friendsRepository.findAll().size();

        // Update the friends
        Friends updatedFriends = friendsRepository.findById(friends.getId()).get();
        // Disconnect from session so that the updates on updatedFriends are not directly saved in db
        em.detach(updatedFriends);
        updatedFriends
            .immediateFriend(UPDATED_IMMEDIATE_FRIEND);
        FriendsDTO friendsDTO = friendsMapper.toDto(updatedFriends);

        restFriendsMockMvc.perform(put("/api/friends")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(friendsDTO)))
            .andExpect(status().isOk());

        // Validate the Friends in the database
        List<Friends> friendsList = friendsRepository.findAll();
        assertThat(friendsList).hasSize(databaseSizeBeforeUpdate);
        Friends testFriends = friendsList.get(friendsList.size() - 1);
        assertThat(testFriends.isImmediateFriend()).isEqualTo(UPDATED_IMMEDIATE_FRIEND);
    }

    @Test
    @Transactional
    public void updateNonExistingFriends() throws Exception {
        int databaseSizeBeforeUpdate = friendsRepository.findAll().size();

        // Create the Friends
        FriendsDTO friendsDTO = friendsMapper.toDto(friends);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFriendsMockMvc.perform(put("/api/friends")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(friendsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Friends in the database
        List<Friends> friendsList = friendsRepository.findAll();
        assertThat(friendsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFriends() throws Exception {
        // Initialize the database
        friendsRepository.saveAndFlush(friends);

        int databaseSizeBeforeDelete = friendsRepository.findAll().size();

        // Get the friends
        restFriendsMockMvc.perform(delete("/api/friends/{id}", friends.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Friends> friendsList = friendsRepository.findAll();
        assertThat(friendsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Friends.class);
        Friends friends1 = new Friends();
        friends1.setId(1L);
        Friends friends2 = new Friends();
        friends2.setId(friends1.getId());
        assertThat(friends1).isEqualTo(friends2);
        friends2.setId(2L);
        assertThat(friends1).isNotEqualTo(friends2);
        friends1.setId(null);
        assertThat(friends1).isNotEqualTo(friends2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FriendsDTO.class);
        FriendsDTO friendsDTO1 = new FriendsDTO();
        friendsDTO1.setId(1L);
        FriendsDTO friendsDTO2 = new FriendsDTO();
        assertThat(friendsDTO1).isNotEqualTo(friendsDTO2);
        friendsDTO2.setId(friendsDTO1.getId());
        assertThat(friendsDTO1).isEqualTo(friendsDTO2);
        friendsDTO2.setId(2L);
        assertThat(friendsDTO1).isNotEqualTo(friendsDTO2);
        friendsDTO1.setId(null);
        assertThat(friendsDTO1).isNotEqualTo(friendsDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(friendsMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(friendsMapper.fromId(null)).isNull();
    }
}
