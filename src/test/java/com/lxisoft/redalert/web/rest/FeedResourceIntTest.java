package com.lxisoft.redalert.web.rest;

import com.lxisoft.redalert.RedAlertApp;

import com.lxisoft.redalert.domain.Feed;
import com.lxisoft.redalert.repository.FeedRepository;
import com.lxisoft.redalert.service.FeedService;
import com.lxisoft.redalert.service.dto.FeedDTO;
import com.lxisoft.redalert.service.mapper.FeedMapper;
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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.lxisoft.redalert.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.lxisoft.redalert.domain.enumeration.Alert;
/**
 * Test class for the FeedResource REST controller.
 *
 * @see FeedResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RedAlertApp.class)
public class FeedResourceIntTest {

    private static final String DEFAULT_USER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_USER_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_MATTER = "AAAAAAAAAA";
    private static final String UPDATED_MATTER = "BBBBBBBBBB";

    private static final Alert DEFAULT_TYPE = Alert.RED_ALERT;
    private static final Alert UPDATED_TYPE = Alert.ORANGE_ALERT;

    private static final String DEFAULT_COMMENTS = "AAAAAAAAAA";
    private static final String UPDATED_COMMENTS = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private FeedRepository feedRepository;

    @Autowired
    private FeedMapper feedMapper;

    @Autowired
    private FeedService feedService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restFeedMockMvc;

    private Feed feed;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FeedResource feedResource = new FeedResource(feedService);
        this.restFeedMockMvc = MockMvcBuilders.standaloneSetup(feedResource)
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
    public static Feed createEntity(EntityManager em) {
        Feed feed = new Feed()
            .userName(DEFAULT_USER_NAME)
            .matter(DEFAULT_MATTER)
            .type(DEFAULT_TYPE)
            .comments(DEFAULT_COMMENTS)
            .createdTime(DEFAULT_CREATED_TIME);
        return feed;
    }

    @Before
    public void initTest() {
        feed = createEntity(em);
    }

    @Test
    @Transactional
    public void createFeed() throws Exception {
        int databaseSizeBeforeCreate = feedRepository.findAll().size();

        // Create the Feed
        FeedDTO feedDTO = feedMapper.toDto(feed);
        restFeedMockMvc.perform(post("/api/feeds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(feedDTO)))
            .andExpect(status().isCreated());

        // Validate the Feed in the database
        List<Feed> feedList = feedRepository.findAll();
        assertThat(feedList).hasSize(databaseSizeBeforeCreate + 1);
        Feed testFeed = feedList.get(feedList.size() - 1);
        assertThat(testFeed.getUserName()).isEqualTo(DEFAULT_USER_NAME);
        assertThat(testFeed.getMatter()).isEqualTo(DEFAULT_MATTER);
        assertThat(testFeed.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testFeed.getComments()).isEqualTo(DEFAULT_COMMENTS);
        assertThat(testFeed.getCreatedTime()).isEqualTo(DEFAULT_CREATED_TIME);
    }

    @Test
    @Transactional
    public void createFeedWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = feedRepository.findAll().size();

        // Create the Feed with an existing ID
        feed.setId(1L);
        FeedDTO feedDTO = feedMapper.toDto(feed);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFeedMockMvc.perform(post("/api/feeds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(feedDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Feed in the database
        List<Feed> feedList = feedRepository.findAll();
        assertThat(feedList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllFeeds() throws Exception {
        // Initialize the database
        feedRepository.saveAndFlush(feed);

        // Get all the feedList
        restFeedMockMvc.perform(get("/api/feeds?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(feed.getId().intValue())))
            .andExpect(jsonPath("$.[*].userName").value(hasItem(DEFAULT_USER_NAME.toString())))
            .andExpect(jsonPath("$.[*].matter").value(hasItem(DEFAULT_MATTER.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS.toString())))
            .andExpect(jsonPath("$.[*].createdTime").value(hasItem(DEFAULT_CREATED_TIME.toString())));
    }

    @Test
    @Transactional
    public void getFeed() throws Exception {
        // Initialize the database
        feedRepository.saveAndFlush(feed);

        // Get the feed
        restFeedMockMvc.perform(get("/api/feeds/{id}", feed.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(feed.getId().intValue()))
            .andExpect(jsonPath("$.userName").value(DEFAULT_USER_NAME.toString()))
            .andExpect(jsonPath("$.matter").value(DEFAULT_MATTER.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.comments").value(DEFAULT_COMMENTS.toString()))
            .andExpect(jsonPath("$.createdTime").value(DEFAULT_CREATED_TIME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingFeed() throws Exception {
        // Get the feed
        restFeedMockMvc.perform(get("/api/feeds/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFeed() throws Exception {
        // Initialize the database
        feedRepository.saveAndFlush(feed);
        int databaseSizeBeforeUpdate = feedRepository.findAll().size();

        // Update the feed
        Feed updatedFeed = feedRepository.findOne(feed.getId());
        // Disconnect from session so that the updates on updatedFeed are not directly saved in db
        em.detach(updatedFeed);
        updatedFeed
            .userName(UPDATED_USER_NAME)
            .matter(UPDATED_MATTER)
            .type(UPDATED_TYPE)
            .comments(UPDATED_COMMENTS)
            .createdTime(UPDATED_CREATED_TIME);
        FeedDTO feedDTO = feedMapper.toDto(updatedFeed);

        restFeedMockMvc.perform(put("/api/feeds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(feedDTO)))
            .andExpect(status().isOk());

        // Validate the Feed in the database
        List<Feed> feedList = feedRepository.findAll();
        assertThat(feedList).hasSize(databaseSizeBeforeUpdate);
        Feed testFeed = feedList.get(feedList.size() - 1);
        assertThat(testFeed.getUserName()).isEqualTo(UPDATED_USER_NAME);
        assertThat(testFeed.getMatter()).isEqualTo(UPDATED_MATTER);
        assertThat(testFeed.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testFeed.getComments()).isEqualTo(UPDATED_COMMENTS);
        assertThat(testFeed.getCreatedTime()).isEqualTo(UPDATED_CREATED_TIME);
    }

    @Test
    @Transactional
    public void updateNonExistingFeed() throws Exception {
        int databaseSizeBeforeUpdate = feedRepository.findAll().size();

        // Create the Feed
        FeedDTO feedDTO = feedMapper.toDto(feed);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restFeedMockMvc.perform(put("/api/feeds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(feedDTO)))
            .andExpect(status().isCreated());

        // Validate the Feed in the database
        List<Feed> feedList = feedRepository.findAll();
        assertThat(feedList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteFeed() throws Exception {
        // Initialize the database
        feedRepository.saveAndFlush(feed);
        int databaseSizeBeforeDelete = feedRepository.findAll().size();

        // Get the feed
        restFeedMockMvc.perform(delete("/api/feeds/{id}", feed.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Feed> feedList = feedRepository.findAll();
        assertThat(feedList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Feed.class);
        Feed feed1 = new Feed();
        feed1.setId(1L);
        Feed feed2 = new Feed();
        feed2.setId(feed1.getId());
        assertThat(feed1).isEqualTo(feed2);
        feed2.setId(2L);
        assertThat(feed1).isNotEqualTo(feed2);
        feed1.setId(null);
        assertThat(feed1).isNotEqualTo(feed2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FeedDTO.class);
        FeedDTO feedDTO1 = new FeedDTO();
        feedDTO1.setId(1L);
        FeedDTO feedDTO2 = new FeedDTO();
        assertThat(feedDTO1).isNotEqualTo(feedDTO2);
        feedDTO2.setId(feedDTO1.getId());
        assertThat(feedDTO1).isEqualTo(feedDTO2);
        feedDTO2.setId(2L);
        assertThat(feedDTO1).isNotEqualTo(feedDTO2);
        feedDTO1.setId(null);
        assertThat(feedDTO1).isNotEqualTo(feedDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(feedMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(feedMapper.fromId(null)).isNull();
    }
}
