package mn.own.ttt.domain;

import static org.assertj.core.api.Assertions.assertThat;

import mn.own.ttt.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProductionFiguresTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductionFigures.class);
        ProductionFigures productionFigures1 = new ProductionFigures();
        productionFigures1.setId(1L);
        ProductionFigures productionFigures2 = new ProductionFigures();
        productionFigures2.setId(productionFigures1.getId());
        assertThat(productionFigures1).isEqualTo(productionFigures2);
        productionFigures2.setId(2L);
        assertThat(productionFigures1).isNotEqualTo(productionFigures2);
        productionFigures1.setId(null);
        assertThat(productionFigures1).isNotEqualTo(productionFigures2);
    }
}
