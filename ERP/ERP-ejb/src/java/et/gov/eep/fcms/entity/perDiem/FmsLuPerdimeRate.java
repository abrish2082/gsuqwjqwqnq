package et.gov.eep.fcms.entity.perDiem;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import et.gov.eep.hrms.entity.lookup.HrLuGrades;
import et.gov.eep.hrms.entity.lookup.HrLuJobLevels;

/**
 *
 * @author muller
 */
@Entity
@Table(name = "FMS_LU_PERDIME_RATE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FmsLuPerdimeRate.findAll", query = "SELECT f FROM FmsLuPerdimeRate f"),
    @NamedQuery(name = "FmsLuPerdimeRate.findById", query = "SELECT f FROM FmsLuPerdimeRate f WHERE f.id = :id"),
    @NamedQuery(name = "FmsLuPerdimeRate.findByFood", query = "SELECT f FROM FmsLuPerdimeRate f WHERE f.food = :food"),
    @NamedQuery(name = "FmsLuPerdimeRate.findByLodging", query = "SELECT f FROM FmsLuPerdimeRate f WHERE f.lodging = :lodging"),
    @NamedQuery(name = "FmsLuPerdimeRate.findByLevel", query = "SELECT f FROM FmsLuPerdimeRate f WHERE f.jobLevelId.id = :jobLevelId"),
    @NamedQuery(name = "FmsLuPerdimeRate.findByLevelandGrade", query = "SELECT f FROM FmsLuPerdimeRate f WHERE f.jobLevelId = :jobLevelId and f.jobGradeId = :jobGradeId "),
    @NamedQuery(name = "FmsLuPerdimeRate.findByJobGradeId", query = "SELECT f FROM FmsLuPerdimeRate f WHERE f.jobGradeId = :jobGradeId")})
public class FmsLuPerdimeRate implements Serializable {

    private static final long serialVersionUID = 1L;
    //<editor-fold defaultstate="collapsed" desc="variable declaration">
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FMS_PER_DIEM_RATE_SEQ")
    @SequenceGenerator(name = "FMS_PER_DIEM_RATE_SEQ", sequenceName = "FMS_PER_DIEM_RATE_SEQ", allocationSize = 1)
    @Column(name = "ID")
    private BigDecimal id;
    @Column(name = "FOOD")
    private Double food;
    @Column(name = "LODGING")
    private Double lodging;
    @JoinColumn(name = "JOB_LEVEL_ID", referencedColumnName = "ID")
    @OneToOne
    private HrLuJobLevels jobLevelId;
    @JoinColumn(name = "JOB_GRADE_ID", referencedColumnName = "ID")
    @OneToOne
    private HrLuGrades jobGradeId;
//</editor-fold>

    public FmsLuPerdimeRate() {
    }
//<editor-fold defaultstate="collapsed" desc="getter and setter">

    public FmsLuPerdimeRate(BigDecimal id) {
        this.id = id;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public Double getFood() {
        return food;
    }

    public void setFood(Double food) {
        this.food = food;
    }

    public Double getLodging() {
        return lodging;
    }

    public void setLodging(Double lodging) {
        this.lodging = lodging;
    }

    public HrLuGrades getJobGradeId() {
        return jobGradeId;
    }

    public void setJobGradeId(HrLuGrades jobGradeId) {
        this.jobGradeId = jobGradeId;
    }

    public HrLuJobLevels getJobLevelId() {
        return jobLevelId;
    }

    public void setJobLevelId(HrLuJobLevels jobLevelId) {
        this.jobLevelId = jobLevelId;
    }
//</editor-fold>

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof FmsLuPerdimeRate)) {
            return false;
        }
        FmsLuPerdimeRate other = (FmsLuPerdimeRate) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.FmsLuPerdimeRate[ id=" + id + " ]";
    }

}
