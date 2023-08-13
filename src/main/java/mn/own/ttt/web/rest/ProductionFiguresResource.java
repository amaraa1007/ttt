package mn.own.ttt.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import mn.own.ttt.domain.ProductionFigures;
import mn.own.ttt.repository.ProductionFiguresRepository;
import mn.own.ttt.service.ProductionFiguresService;
import mn.own.ttt.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link mn.own.ttt.domain.ProductionFigures}.
 */
@RestController
@RequestMapping("/api")
public class ProductionFiguresResource {

    private final Logger log = LoggerFactory.getLogger(ProductionFiguresResource.class);

    private static final String ENTITY_NAME = "tttProductionFigures";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProductionFiguresService productionFiguresService;

    private final ProductionFiguresRepository productionFiguresRepository;

    public ProductionFiguresResource(
        ProductionFiguresService productionFiguresService,
        ProductionFiguresRepository productionFiguresRepository
    ) {
        this.productionFiguresService = productionFiguresService;
        this.productionFiguresRepository = productionFiguresRepository;
    }

    /**
     * {@code POST  /production-figures} : Create a new productionFigures.
     *
     * @param productionFigures the productionFigures to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new productionFigures, or with status {@code 400 (Bad Request)} if the productionFigures has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/production-figures")
    public ResponseEntity<ProductionFigures> createProductionFigures(@RequestBody ProductionFigures productionFigures)
        throws URISyntaxException {
        log.debug("REST request to save ProductionFigures : {}", productionFigures);
        if (productionFigures.getId() != null) {
            throw new BadRequestAlertException("A new productionFigures cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProductionFigures result = productionFiguresService.save(productionFigures);
        return ResponseEntity
            .created(new URI("/api/production-figures/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /production-figures/:id} : Updates an existing productionFigures.
     *
     * @param id the id of the productionFigures to save.
     * @param productionFigures the productionFigures to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productionFigures,
     * or with status {@code 400 (Bad Request)} if the productionFigures is not valid,
     * or with status {@code 500 (Internal Server Error)} if the productionFigures couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/production-figures/{id}")
    public ResponseEntity<ProductionFigures> updateProductionFigures(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ProductionFigures productionFigures
    ) throws URISyntaxException {
        log.debug("REST request to update ProductionFigures : {}, {}", id, productionFigures);
        if (productionFigures.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, productionFigures.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!productionFiguresRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ProductionFigures result = productionFiguresService.save(productionFigures);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, productionFigures.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /production-figures/:id} : Partial updates given fields of an existing productionFigures, field will ignore if it is null
     *
     * @param id the id of the productionFigures to save.
     * @param productionFigures the productionFigures to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productionFigures,
     * or with status {@code 400 (Bad Request)} if the productionFigures is not valid,
     * or with status {@code 404 (Not Found)} if the productionFigures is not found,
     * or with status {@code 500 (Internal Server Error)} if the productionFigures couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/production-figures/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ProductionFigures> partialUpdateProductionFigures(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ProductionFigures productionFigures
    ) throws URISyntaxException {
        log.debug("REST request to partial update ProductionFigures partially : {}, {}", id, productionFigures);
        if (productionFigures.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, productionFigures.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!productionFiguresRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ProductionFigures> result = productionFiguresService.partialUpdate(productionFigures);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, productionFigures.getId().toString())
        );
    }

    /**
     * {@code GET  /production-figures} : get all the productionFigures.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of productionFigures in body.
     */
    @GetMapping("/production-figures")
    public ResponseEntity<List<ProductionFigures>> getAllProductionFigures(Pageable pageable) {
        log.debug("REST request to get a page of ProductionFigures");
        Page<ProductionFigures> page = productionFiguresService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /production-figures/:id} : get the "id" productionFigures.
     *
     * @param id the id of the productionFigures to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the productionFigures, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/production-figures/{id}")
    public ResponseEntity<ProductionFigures> getProductionFigures(@PathVariable Long id) {
        log.debug("REST request to get ProductionFigures : {}", id);
        Optional<ProductionFigures> productionFigures = productionFiguresService.findOne(id);
        return ResponseUtil.wrapOrNotFound(productionFigures);
    }

    /**
     * {@code DELETE  /production-figures/:id} : delete the "id" productionFigures.
     *
     * @param id the id of the productionFigures to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/production-figures/{id}")
    public ResponseEntity<Void> deleteProductionFigures(@PathVariable Long id) {
        log.debug("REST request to delete ProductionFigures : {}", id);
        productionFiguresService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
