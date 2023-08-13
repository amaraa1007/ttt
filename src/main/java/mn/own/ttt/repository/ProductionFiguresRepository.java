package mn.own.ttt.repository;

import mn.own.ttt.domain.ProductionFigures;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ProductionFigures entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductionFiguresRepository extends JpaRepository<ProductionFigures, Long> {}
