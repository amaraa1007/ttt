package mn.own.ttt.domain;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;

/**
 * A ProductionFigures.
 */
@Entity
@Table(name = "production_figures")
public class ProductionFigures implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "day_info")
    private String dayInfo;

    @Column(name = "week_info")
    private String weekInfo;

    @Column(name = "month_info")
    private String monthInfo;

    @Column(name = "total_info")
    private String totalInfo;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_date")
    private Instant createdDate;

    @Column(name = "last_modified_by")
    private String lastModifiedBy;

    @Column(name = "last_modified_date")
    private Instant lastModifiedDate;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ProductionFigures id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDayInfo() {
        return this.dayInfo;
    }

    public ProductionFigures dayInfo(String dayInfo) {
        this.setDayInfo(dayInfo);
        return this;
    }

    public void setDayInfo(String dayInfo) {
        this.dayInfo = dayInfo;
    }

    public String getWeekInfo() {
        return this.weekInfo;
    }

    public ProductionFigures weekInfo(String weekInfo) {
        this.setWeekInfo(weekInfo);
        return this;
    }

    public void setWeekInfo(String weekInfo) {
        this.weekInfo = weekInfo;
    }

    public String getMonthInfo() {
        return this.monthInfo;
    }

    public ProductionFigures monthInfo(String monthInfo) {
        this.setMonthInfo(monthInfo);
        return this;
    }

    public void setMonthInfo(String monthInfo) {
        this.monthInfo = monthInfo;
    }

    public String getTotalInfo() {
        return this.totalInfo;
    }

    public ProductionFigures totalInfo(String totalInfo) {
        this.setTotalInfo(totalInfo);
        return this;
    }

    public void setTotalInfo(String totalInfo) {
        this.totalInfo = totalInfo;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public ProductionFigures createdBy(String createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedDate() {
        return this.createdDate;
    }

    public ProductionFigures createdDate(Instant createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getLastModifiedBy() {
        return this.lastModifiedBy;
    }

    public ProductionFigures lastModifiedBy(String lastModifiedBy) {
        this.setLastModifiedBy(lastModifiedBy);
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Instant getLastModifiedDate() {
        return this.lastModifiedDate;
    }

    public ProductionFigures lastModifiedDate(Instant lastModifiedDate) {
        this.setLastModifiedDate(lastModifiedDate);
        return this;
    }

    public void setLastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductionFigures)) {
            return false;
        }
        return id != null && id.equals(((ProductionFigures) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductionFigures{" +
            "id=" + getId() +
            ", dayInfo='" + getDayInfo() + "'" +
            ", weekInfo='" + getWeekInfo() + "'" +
            ", monthInfo='" + getMonthInfo() + "'" +
            ", totalInfo='" + getTotalInfo() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            "}";
    }
}
