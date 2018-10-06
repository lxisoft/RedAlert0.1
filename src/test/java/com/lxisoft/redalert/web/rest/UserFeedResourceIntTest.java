package com.lxisoft.redalert.web.rest;

import com.lxisoft.redalert.RedAlertApp;

import com.lxisoft.redalert.domain.UserFeed;
import com.lxisoft.redalert.repository.UserFeedRepository;
import com.lxisoft.redalert.service.UserFeedService;
import com.lxisoft.redalert.service.dto.UserFeedDTO;
import com.lxisoft.redalert.service.mapper.UserFeedMapper;
import com.lxisoft.redalert.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;


import static com.lxisoft.redalert.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.lxisoft.redalert.domain.enumeration.Alert;
/**
 * Test class for the UserFeedResource REST controller.
 *
 * @see UserFeedResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RedAlertApp.class)
public class UserFeedResourceIntTest {

    private static final String DEFAULT_USER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_USER_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_MESSAGE = "AAAAAAAAAA";
    private static final String UPDATED_MESSAGE = "BBBBBBBBBB";

    private static final Alert DEFAULT_CURRENT_TYPE = Alert.RED_ALERT;
    private static final Alert UPDATED_CURRENT_TYPE = Alert.ORANGE_ALERT;

    private static final Boolean DEFAULT_IS_VALID = false;
    private static final Boolean UPDATED_IS_VALID = true;

    private static final String DEFAULT_LATITUDE = "AAAAAAAAAA";
    private static final String UPDATED_LATITUDE = "BBBBBBBBBB";

    private static final String DEFAULT_LONGITUDE = "AAAAAAAAAA";
    private static final String UPDATED_LONGITUDE = "BBBBBBBBBB";

    private static final Integer DEFAULT_SCORE = 1;
    private static final Integer UPDATED_SCORE = 2;

    private static final Instant DEFAULT_CREATED_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private UserFeedRepository userFeedRepository;

    @Mock
    private UserFeedRepository userFeedRepositoryMock;

    @Autowired
    private UserFeedMapper userFeedMapper;
    

    @Mock
    private UserFeedService userFeedServiceMock;

    @Autowired
    private UserFeedService userFeedService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restUserFeedMockMvc;

    private UserFeed userFeed;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final UserFeedResource userFeedResource = new UserFeedResource(userFeedService);
        this.restUserFeedMockMvc = MockMvcBuilders.standaloneSetup(userFeedResource)
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
    public static UserFeed createEntity(EntityManager em) {
        UserFeed userFeed = new UserFeed()
            .userName(DEFAULT_USER_NAME)
            .message(DEFAULT_MESSAGE)
            .currentType(DEFAULT_CURRENT_TYPE)
            .isValid(DEFAULT_IS_VALID)
            .latitude(DEFAULT_LATITUDE)
            .longitude(DEFAULT_LONGITUDE)
            .score(DEFAULT_SCORE)
            .createdTime(DEFAULT_CREATED_TIME);
        return userFeed;
    }

    @Before
    public void initTest() {
        userFeed = createEntity(em);
    }

    @Test
    @Transactional
    public void createUserFeed() throws Exception {
        int databaseSizeBeforeCreate = userFeedRepository.findAll().size();

        // Create the UserFeed
        UserFeedDTO userFeedDTO = userFeedMapper.toDto(userFeed);
        restUserFeedMockMvc.perform(post("/api/user-feeds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userFeedDTO)))
            .andExpect(status().isCreated());

        // Validate the UserFeed in the database
        List<UserFeed> userFeedList = userFeedRepository.findAll();
        assertThat(userFeedList).hasSize(databaseSizeBeforeCreate + 1);
        UserFeed testUserFeed = userFeedList.get(userFeedList.size() - 1);
        assertThat(testUserFeed.getUserName()).isEqualTo(DEFAULT_USER_NAME);
        assertThat(testUserFeed.getMessage()).isEqualTo(DEFAULT_MESSAGE);
        assertThat(testUserFeed.getCurrentType()).isEqualTo(DEFAULT_CURRENT_TYPE);
        assertThat(testUserFeed.isIsValid()).isEqualTo(DEFAULT_IS_VALID);
        assertThat(testUserFeed.getLatitude()).isEqualTo(DEFAULT_LATITUDE);
        assertThat(testUserFeed.getLongitude()).isEqualTo(DEFAULT_LONGITUDE);
        assertThat(testUserFeed.getScore()).isEqualTo(DEFAULT_SCORE);
        assertThat(testUserFeed.getCreatedTime()).isEqualTo(DEFAULT_CREATED_TIME);
    }

    @Test
    @Transactional
    public void createUserFeedWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userFeedRepository.findAll().size();

        // Create the UserFeed with an existing ID
        userFeed.setId(1L);
        UserFeedDTO userFeedDTO = userFeedMapper.toDto(userFeed);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserFeedMockMvc.perform(post("/api/user-feeds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userFeedDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserFeed in the database
        List<UserFeed> userFeedList = userFeedRepository.findAll();
        assertThat(userFeedList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllUserFeeds() throws Exception {
        // Initialize the database
        userFeedRepository.saveAndFlush(userFeed);

        // Get all the userFeedList
        restUserFeedMockMvc.perform(get("/api/user-feeds?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userFeed.getId().intValue())))
            .andExpect(jsonPath("$.[*].userName").value(hasItem(DEFAULT_USER_NAME.toString())))
            .andExpect(jsonPath("$.[*].message").value(hasItem(DEFAULT_MESSAGE.toString())))
            .andExpect(jsonPath("$.[*].currentType").value(hasItem(DEFAULT_CURRENT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].isValid").value(hasItem(DEFAULT_IS_VALID.booleanValue())))
            .andExpect(jsonPath("$.[*].latitude").value(hasItem(DEFAULT_LATITUDE.toString())))
            .andExpect(jsonPath("$.[*].longitude").value(hasItem(DEFAULT_LONGITUDE.toString())))
            .andExpect(jsonPath("$.[*].score").value(hasItem(DEFAULT_SCORE)))
            .andExpect(jsonPath("$.[*].createdTime").value(hasItem(DEFAULT_CREATED_TIME.toString())));
    }
    
    public void getAllUserFeedsWithEagerRelationshipsIsEnabled() throws Exception {
        UserFeedResource userFeedResource = new UserFeedResource(userFeedServiceMock);
        when(userFeedServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restUserFeedMockMvc = MockMvcBuilders.standaloneSetup(userFeedResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restUserFeedMockMvc.perform(get("/api/user-feeds?eagerload=true"))
        .andExpect(status().isOk());

        verify(userFeedServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    public void getAllUserFeedsWithEagerRelationshipsIsNotEnabled() throws Exception {
        UserFeedResource userFeedResource = new UserFeedResource(userFeedServiceMock);
            when(userFeedServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restUserFeedMockMvc = MockMvcBuilders.standaloneSetup(userFeedResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restUserFeedMockMvc.perform(get("/api/user-feeds?eagerload=true"))
        .andExpect(status().isOk());

            verify(userFeedServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getUserFeed() throws Exception {
        // Initialize the database
        userFeedRepository.saveAndFlush(userFeed);

        // Get the userFeed
        restUserFeedMockMvc.perform(get("/api/user-feeds/{id}", userFeed.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(userFeed.getId().intValue()))
            .andExpect(jsonPath("$.userName").value(DEFAULT_USER_NAME.toString()))
            .andExpect(jsonPath("$.message").value(DEFAULT_MESSAGE.toString()))
            .andExpect(jsonPath("$.currentType").value(DEFAULT_CURRENT_TYPE.toString()))
            .andExpect(jsonPath("$.isValid").value(DEFAULT_IS_VALID.booleanValue()))
            .andExpect(jsonPath("$.latitude").value(DEFAULT_LATITUDE.toString()))
            .andExpect(jsonPath("$.longitude").value(DEFAULT_LONGITUDE.toString()))
            .andExpect(jsonPath("$.score").value(DEFAULT_SCORE))
            .andExpect(jsonPath("$.createdTime").value(DEFAULT_CREATED_TIME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingUserFeed() throws Exception {
        // Get the userFeed
        restUserFeedMockMvc.perform(get("/api/user-feeds/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserFeed() throws Exception {
        // Initialize the database
        userFeedRepository.saveAndFlush(userFeed);

        int databaseSizeBeforeUpdate = userFeedRepository.findAll().size();

        // Update the userFeed
        UserFeed updatedUserFeed = userFeedRepository.findById(userFeed.getId()).get();
        // Disconnect from session so that the updates on updatedUserFeed are not directly saved in db
        em.detach(updatedUserFeed);
        updatedUserFeed
            .userName(UPDATED_USER_NAME)
            .message(UPDATED_MESSAGE)
            .currentType(UPDATED_CURRENT_TYPE)
            .isValid(UPDATED_IS_VALID)
            .latitude(UPDATED_LATITUDE)
            .longitude(UPDATED_LONGITUDE)
            .score(UPDATED_SCORE)
            .createdTime(UPDATED_CREATED_TIME);
        UserFeedDTO userFeedDTO = userFeedMapper.toDto(updatedUserFeed);

        restUserFeedMockMvc.perform(put("/api/user-feeds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userFeedDTO)))
            .andExpect(status().isOk());

        // Validate the UserFeed in the database
        List<UserFeed> userFeedList = userFeedRepository.findAll();
        assertThat(userFeedList).hasSize(databaseSizeBeforeUpdate);
        UserFeed testUserFeed = userFeedList.get(userFeedList.size() - 1);
        assertThat(testUserFeed.getUserName()).isEqualTo(UPDATED_USER_NAME);
        assertThat(testUserFeed.getMessage()).isEqualTo(UPDATED_MESSAGE);
        assertThat(testUserFeed.getCurrentType()).isEqualTo(UPDATED_CURRENT_TYPE);
        assertThat(testUserFeed.isIsValid()).isEqualTo(UPDATED_IS_VALID);
        assertThat(testUserFeed.getLatitude()).isEqualTo(UPDATED_LATITUDE);
        assertThat(testUserFeed.getLongitude()).isEqualTo(UPDATED_LONGITUDE);
        assertThat(testUserFeed.getScore()).isEqualTo(UPDATED_SCORE);
        assertThat(testUserFeed.getCreatedTime()).isEqualTo(UPDATED_CREATED_TIME);
    }

    @Test
    @Transactional
    public void updateNonExistingUserFeed() throws Exception {
        int databaseSizeBeforeUpdate = userFeedRepository.findAll().size();

        // Create the UserFeed
        UserFeedDTO userFeedDTO = userFeedMapper.toDto(userFeed);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserFeedMockMvc.perform(put("/api/user-feeds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userFeedDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserFeed in the database
        List<UserFeed> userFeedList = userFeedRepository.findAll();
        assertThat(userFeedList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteUserFeed() throws Exception {
        // Initialize the database
        userFeedRepository.saveAndFlush(userFeed);

        int databaseSizeBeforeDelete = userFeedRepository.findAll().size();

        // Get the userFeed
        restUserFeedMockMvc.perform(delete("/api/user-feeds/{id}", userFeed.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<UserFeed> userFeedList = userFeedRepository.findAll();
        assertThat(userFeedList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserFeed.class);
        UserFeed userFeed1 = new UserFeed();
        userFeed1.setId(1L);
        UserFeed userFeed2 = new UserFeed();
        userFeed2.setId(userFeed1.getId());
        assertThat(userFeed1).isEqualTo(userFeed2);
        userFeed2.setId(2L);
        assertThat(userFeed1).isNotEqualTo(userFeed2);
        userFeed1.setId(null);
        assertThat(userFeed1).isNotEqualTo(userFeed2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserFeedDTO.class);
        UserFeedDTO userFeedDTO1 = new UserFeedDTO();
        userFeedDTO1.setId(1L);
        UserFeedDTO userFeedDTO2 = new UserFeedDTO();
        assertThat(userFeedDTO1).isNotEqualTo(userFeedDTO2);
        userFeedDTO2.setId(userFeedDTO1.getId());
        assertThat(userFeedDTO1).isEqualTo(userFeedDTO2);
        userFeedDTO2.setId(2L);
        assertThat(userFeedDTO1).isNotEqualTo(userFeedDTO2);
        userFeedDTO1.setId(null);
        assertThat(userFeedDTO1).isNotEqualTo(userFeedDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(userFeedMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(userFeedMapper.fromId(null)).isNull();
    }
}
