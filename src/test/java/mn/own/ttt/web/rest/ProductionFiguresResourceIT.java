package mn.own.ttt.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import mn.own.ttt.IntegrationTest;
import mn.own.ttt.domain.ProductionFigures;
import mn.own.ttt.repository.ProductionFiguresRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ProductionFiguresResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ProductionFiguresResourceIT {

    private static final String DEFAULT_DAY_INFO = "AAAAAAAAAA";
    private static final String UPDATED_DAY_INFO = "BBBBBBBBBB";

    private static final String DEFAULT_WEEK_INFO = "AAAAAAAAAA";
    private static final String UPDATED_WEEK_INFO = "BBBBBBBBBB";

    private static final String DEFAULT_MONTH_INFO = "AAAAAAAAAA";
    private static final String UPDATED_MONTH_INFO = "BBBBBBBBBB";

    private static final String DEFAULT_TOTAL_INFO = "AAAAAAAAAA";
    private static final String UPDATED_TOTAL_INFO = "BBBBBBBBBB";

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_LAST_MODIFIED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/production-figures";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ProductionFiguresRepository productionFiguresRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProductionFiguresMockMvc;

    private ProductionFigures productionFigures;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductionFigures createEntity(EntityManager em) {
        ProductionFigures productionFigures = new ProductionFigures()
            .dayInfo(DEFAULT_DAY_INFO)
            .weekInfo(DEFAULT_WEEK_INFO)
            .monthInfo(DEFAULT_MONTH_INFO)
            .totalInfo(DEFAULT_TOTAL_INFO)
            .createdBy(DEFAULT_CREATED_BY)
            .createdDate(DEFAULT_CREATED_DATE)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .lastModifiedDate(DEFAULT_LAST_MODIFIED_DATE);
        return productionFigures;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductionFigures createUpdatedEntity(EntityManager em) {
        ProductionFigures productionFigures = new ProductionFigures()
            .dayInfo(UPDATED_DAY_INFO)
            .weekInfo(UPDATED_WEEK_INFO)
            .monthInfo(UPDATED_MONTH_INFO)
            .totalInfo(UPDATED_TOTAL_INFO)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        return productionFigures;
    }

    @BeforeEach
    public void initTest() {
        productionFigures = createEntity(em);
    }

    @Test
    @Transactional
    void createProductionFigures() throws Exception {
        int databaseSizeBeforeCreate = productionFiguresRepository.findAll().size();
        // Create the ProductionFigures
        restProductionFiguresMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(productionFigures))
            )
            .andExpect(status().isCreated());

        // Validate the ProductionFigures in the database
        List<ProductionFigures> productionFiguresList = productionFiguresRepository.findAll();
        assertThat(productionFiguresList).hasSize(databaseSizeBeforeCreate + 1);
        ProductionFigures testProductionFigures = productionFiguresList.get(productionFiguresList.size() - 1);
        assertThat(testProductionFigures.getDayInfo()).isEqualTo(DEFAULT_DAY_INFO);
        assertThat(testProductionFigures.getWeekInfo()).isEqualTo(DEFAULT_WEEK_INFO);
        assertThat(testProductionFigures.getMonthInfo()).isEqualTo(DEFAULT_MONTH_INFO);
        assertThat(testProductionFigures.getTotalInfo()).isEqualTo(DEFAULT_TOTAL_INFO);
        assertThat(testProductionFigures.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testProductionFigures.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testProductionFigures.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testProductionFigures.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void createProductionFiguresWithExistingId() throws Exception {
        // Create the ProductionFigures with an existing ID
        productionFigures.setId(1L);

        int databaseSizeBeforeCreate = productionFiguresRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductionFiguresMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(productionFigures))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductionFigures in the database
        List<ProductionFigures> productionFiguresList = productionFiguresRepository.findAll();
        assertThat(productionFiguresList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllProductionFigures() throws Exception {
        // Initialize the database
        productionFiguresRepository.saveAndFlush(productionFigures);

        // Get all the productionFiguresList
        restProductionFiguresMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productionFigures.getId().intValue())))
            .andExpect(jsonPath("$.[*].dayInfo").value(hasItem(DEFAULT_DAY_INFO)))
            .andExpect(jsonPath("$.[*].weekInfo").value(hasItem(DEFAULT_WEEK_INFO)))
            .andExpect(jsonPath("$.[*].monthInfo").value(hasItem(DEFAULT_MONTH_INFO)))
            .andExpect(jsonPath("$.[*].totalInfo").value(hasItem(DEFAULT_TOTAL_INFO)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));
    }

    @Test
    @Transactional
    void getProductionFigures() throws Exception {
        // Initialize the database
        productionFiguresRepository.saveAndFlush(productionFigures);

        // Get the productionFigures
        restProductionFiguresMockMvc
            .perform(get(ENTITY_API_URL_ID, productionFigures.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(productionFigures.getId().intValue()))
            .andExpect(jsonPath("$.dayInfo").value(DEFAULT_DAY_INFO))
            .andExpect(jsonPath("$.weekInfo").value(DEFAULT_WEEK_INFO))
            .andExpect(jsonPath("$.monthInfo").value(DEFAULT_MONTH_INFO))
            .andExpect(jsonPath("$.totalInfo").value(DEFAULT_TOTAL_INFO))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY))
            .andExpect(jsonPath("$.lastModifiedDate").value(DEFAULT_LAST_MODIFIED_DATE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingProductionFigures() throws Exception {
        // Get the productionFigures
        restProductionFiguresMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewProductionFigures() throws Exception {
        // Initialize the database
        productionFiguresRepository.saveAndFlush(productionFigures);

        int databaseSizeBeforeUpdate = productionFiguresRepository.findAll().size();

        // Update the productionFigures
        ProductionFigures updatedProductionFigures = productionFiguresRepository.findById(productionFigures.getId()).get();
        // Disconnect from session so that the updates on updatedProductionFigures are not directly saved in db
        em.detach(updatedProductionFigures);
        updatedProductionFigures
            .dayInfo(UPDATED_DAY_INFO)
            .weekInfo(UPDATED_WEEK_INFO)
            .monthInfo(UPDATED_MONTH_INFO)
            .totalInfo(UPDATED_TOTAL_INFO)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);

        restProductionFiguresMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedProductionFigures.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedProductionFigures))
            )
            .andExpect(status().isOk());

        // Validate the ProductionFigures in the database
        List<ProductionFigures> productionFiguresList = productionFiguresRepository.findAll();
        assertThat(productionFiguresList).hasSize(databaseSizeBeforeUpdate);
        ProductionFigures testProductionFigures = productionFiguresList.get(productionFiguresList.size() - 1);
        assertThat(testProductionFigures.getDayInfo()).isEqualTo(UPDATED_DAY_INFO);
        assertThat(testProductionFigures.getWeekInfo()).isEqualTo(UPDATED_WEEK_INFO);
        assertThat(testProductionFigures.getMonthInfo()).isEqualTo(UPDATED_MONTH_INFO);
        assertThat(testProductionFigures.getTotalInfo()).isEqualTo(UPDATED_TOTAL_INFO);
        assertThat(testProductionFigures.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testProductionFigures.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testProductionFigures.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testProductionFigures.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingProductionFigures() throws Exception {
        int databaseSizeBeforeUpdate = productionFiguresRepository.findAll().size();
        productionFigures.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductionFiguresMockMvc
            .perform(
                put(ENTITY_API_URL_ID, productionFigures.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productionFigures))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductionFigures in the database
        List<ProductionFigures> productionFiguresList = productionFiguresRepository.findAll();
        assertThat(productionFiguresList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProductionFigures() throws Exception {
        int databaseSizeBeforeUpdate = productionFiguresRepository.findAll().size();
        productionFigures.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductionFiguresMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productionFigures))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductionFigures in the database
        List<ProductionFigures> productionFiguresList = productionFiguresRepository.findAll();
        assertThat(productionFiguresList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProductionFigures() throws Exception {
        int databaseSizeBeforeUpdate = productionFiguresRepository.findAll().size();
        productionFigures.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductionFiguresMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(productionFigures))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProductionFigures in the database
        List<ProductionFigures> productionFiguresList = productionFiguresRepository.findAll();
        assertThat(productionFiguresList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProductionFiguresWithPatch() throws Exception {
        // Initialize the database
        productionFiguresRepository.saveAndFlush(productionFigures);

        int databaseSizeBeforeUpdate = productionFiguresRepository.findAll().size();

        // Update the productionFigures using partial update
        ProductionFigures partialUpdatedProductionFigures = new ProductionFigures();
        partialUpdatedProductionFigures.setId(productionFigures.getId());

        partialUpdatedProductionFigures
            .dayInfo(UPDATED_DAY_INFO)
            .totalInfo(UPDATED_TOTAL_INFO)
            .createdBy(UPDATED_CREATED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);

        restProductionFiguresMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProductionFigures.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProductionFigures))
            )
            .andExpect(status().isOk());

        // Validate the ProductionFigures in the database
        List<ProductionFigures> productionFiguresList = productionFiguresRepository.findAll();
        assertThat(productionFiguresList).hasSize(databaseSizeBeforeUpdate);
        ProductionFigures testProductionFigures = productionFiguresList.get(productionFiguresList.size() - 1);
        assertThat(testProductionFigures.getDayInfo()).isEqualTo(UPDATED_DAY_INFO);
        assertThat(testProductionFigures.getWeekInfo()).isEqualTo(DEFAULT_WEEK_INFO);
        assertThat(testProductionFigures.getMonthInfo()).isEqualTo(DEFAULT_MONTH_INFO);
        assertThat(testProductionFigures.getTotalInfo()).isEqualTo(UPDATED_TOTAL_INFO);
        assertThat(testProductionFigures.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testProductionFigures.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testProductionFigures.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testProductionFigures.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateProductionFiguresWithPatch() throws Exception {
        // Initialize the database
        productionFiguresRepository.saveAndFlush(productionFigures);

        int databaseSizeBeforeUpdate = productionFiguresRepository.findAll().size();

        // Update the productionFigures using partial update
        ProductionFigures partialUpdatedProductionFigures = new ProductionFigures();
        partialUpdatedProductionFigures.setId(productionFigures.getId());

        partialUpdatedProductionFigures
            .dayInfo(UPDATED_DAY_INFO)
            .weekInfo(UPDATED_WEEK_INFO)
            .monthInfo(UPDATED_MONTH_INFO)
            .totalInfo(UPDATED_TOTAL_INFO)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);

        restProductionFiguresMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProductionFigures.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProductionFigures))
            )
            .andExpect(status().isOk());

        // Validate the ProductionFigures in the database
        List<ProductionFigures> productionFiguresList = productionFiguresRepository.findAll();
        assertThat(productionFiguresList).hasSize(databaseSizeBeforeUpdate);
        ProductionFigures testProductionFigures = productionFiguresList.get(productionFiguresList.size() - 1);
        assertThat(testProductionFigures.getDayInfo()).isEqualTo(UPDATED_DAY_INFO);
        assertThat(testProductionFigures.getWeekInfo()).isEqualTo(UPDATED_WEEK_INFO);
        assertThat(testProductionFigures.getMonthInfo()).isEqualTo(UPDATED_MONTH_INFO);
        assertThat(testProductionFigures.getTotalInfo()).isEqualTo(UPDATED_TOTAL_INFO);
        assertThat(testProductionFigures.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testProductionFigures.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testProductionFigures.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testProductionFigures.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingProductionFigures() throws Exception {
        int databaseSizeBeforeUpdate = productionFiguresRepository.findAll().size();
        productionFigures.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductionFiguresMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, productionFigures.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productionFigures))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductionFigures in the database
        List<ProductionFigures> productionFiguresList = productionFiguresRepository.findAll();
        assertThat(productionFiguresList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProductionFigures() throws Exception {
        int databaseSizeBeforeUpdate = productionFiguresRepository.findAll().size();
        productionFigures.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductionFiguresMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productionFigures))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductionFigures in the database
        List<ProductionFigures> productionFiguresList = productionFiguresRepository.findAll();
        assertThat(productionFiguresList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProductionFigures() throws Exception {
        int databaseSizeBeforeUpdate = productionFiguresRepository.findAll().size();
        productionFigures.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductionFiguresMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productionFigures))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProductionFigures in the database
        List<ProductionFigures> productionFiguresList = productionFiguresRepository.findAll();
        assertThat(productionFiguresList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProductionFigures() throws Exception {
        // Initialize the database
        productionFiguresRepository.saveAndFlush(productionFigures);

        int databaseSizeBeforeDelete = productionFiguresRepository.findAll().size();

        // Delete the productionFigures
        restProductionFiguresMockMvc
            .perform(delete(ENTITY_API_URL_ID, productionFigures.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProductionFigures> productionFiguresList = productionFiguresRepository.findAll();
        assertThat(productionFiguresList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
