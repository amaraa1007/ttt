package mn.own.ttt.service;

import java.util.Optional;
import mn.own.ttt.domain.ProductionFigures;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link ProductionFigures}.
 */
public interface ProductionFiguresService {
    /**
     * Save a productionFigures.
     *
     * @param productionFigures the entity to save.
     * @return the persisted entity.
     */
    ProductionFigures save(ProductionFigures productionFigures);

    /**
     * Partially updates a productionFigures.
     *
     * @param productionFigures the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ProductionFigures> partialUpdate(ProductionFigures productionFigures);

    /**
     * Get all the productionFigures.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ProductionFigures> findAll(Pageable pageable);

    /**
     * Get the "id" productionFigures.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ProductionFigures> findOne(Long id);

    /**
     * Delete the "id" productionFigures.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
