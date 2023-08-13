package mn.own.ttt.service.impl;

import java.util.Optional;
import mn.own.ttt.domain.ProductionFigures;
import mn.own.ttt.repository.ProductionFiguresRepository;
import mn.own.ttt.service.ProductionFiguresService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ProductionFigures}.
 */
@Service
@Transactional
public class ProductionFiguresServiceImpl implements ProductionFiguresService {

    private final Logger log = LoggerFactory.getLogger(ProductionFiguresServiceImpl.class);

    private final ProductionFiguresRepository productionFiguresRepository;

    public ProductionFiguresServiceImpl(ProductionFiguresRepository productionFiguresRepository) {
        this.productionFiguresRepository = productionFiguresRepository;
    }

    @Override
    public ProductionFigures save(ProductionFigures productionFigures) {
        log.debug("Request to save ProductionFigures : {}", productionFigures);
        return productionFiguresRepository.save(productionFigures);
    }

    @Override
    public Optional<ProductionFigures> partialUpdate(ProductionFigures productionFigures) {
        log.debug("Request to partially update ProductionFigures : {}", productionFigures);

        return productionFiguresRepository
            .findById(productionFigures.getId())
            .map(existingProductionFigures -> {
                if (productionFigures.getDayInfo() != null) {
                    existingProductionFigures.setDayInfo(productionFigures.getDayInfo());
                }
                if (productionFigures.getWeekInfo() != null) {
                    existingProductionFigures.setWeekInfo(productionFigures.getWeekInfo());
                }
                if (productionFigures.getMonthInfo() != null) {
                    existingProductionFigures.setMonthInfo(productionFigures.getMonthInfo());
                }
                if (productionFigures.getTotalInfo() != null) {
                    existingProductionFigures.setTotalInfo(productionFigures.getTotalInfo());
                }
                if (productionFigures.getCreatedBy() != null) {
                    existingProductionFigures.setCreatedBy(productionFigures.getCreatedBy());
                }
                if (productionFigures.getCreatedDate() != null) {
                    existingProductionFigures.setCreatedDate(productionFigures.getCreatedDate());
                }
                if (productionFigures.getLastModifiedBy() != null) {
                    existingProductionFigures.setLastModifiedBy(productionFigures.getLastModifiedBy());
                }
                if (productionFigures.getLastModifiedDate() != null) {
                    existingProductionFigures.setLastModifiedDate(productionFigures.getLastModifiedDate());
                }

                return existingProductionFigures;
            })
            .map(productionFiguresRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductionFigures> findAll(Pageable pageable) {
        log.debug("Request to get all ProductionFigures");
        return productionFiguresRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ProductionFigures> findOne(Long id) {
        log.debug("Request to get ProductionFigures : {}", id);
        return productionFiguresRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ProductionFigures : {}", id);
        productionFiguresRepository.deleteById(id);
    }
}
