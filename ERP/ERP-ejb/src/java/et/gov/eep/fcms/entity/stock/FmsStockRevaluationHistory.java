package et.gov.eep.fcms.entity.stock;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author memube
 */
@Entity
@Table(name = "FMS_STOCK_REVALUATION_HISTORY")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FmsStockRevaluationHistory.findAll", query = "SELECT f FROM FmsStockRevaluationHistory f"),
    @NamedQuery(name = "FmsStockRevaluationHistory.findById", query = "SELECT f FROM FmsStockRevaluationHistory f WHERE f.id = :id"),
    @NamedQuery(name = "FmsStockRevaluationHistory.findByOldPrice", query = "SELECT f FROM FmsStockRevaluationHistory f WHERE f.oldPrice = :oldPrice"),
    @NamedQuery(name = "FmsStockRevaluationHistory.findByNewPrice", query = "SELECT f FROM FmsStockRevaluationHistory f WHERE f.newPrice = :newPrice"),
    @NamedQuery(name = "FmsStockRevaluationHistory.findByRevaluedBy", query = "SELECT f FROM FmsStockRevaluationHistory f WHERE f.revaluedBy = :revaluedBy"),
    @NamedQuery(name = "FmsStockRevaluationHistory.findByRevaluationDate", query = "SELECT f FROM FmsStockRevaluationHistory f WHERE f.revaluationDate = :revaluationDate")})
public class FmsStockRevaluationHistory implements Serializable {

    private static final long serialVersionUID = 1L;
    //<editor-fold defaultstate="collapsed" desc="variable declaration">
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FMS_STOCK_REVALUE_HISTORY_SEQ")
    @SequenceGenerator(name = "FMS_STOCK_REVALUE_HISTORY_SEQ", sequenceName = "FMS_STOCK_REVALUE_HISTORY_SEQ", allocationSize = 1)
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private BigDecimal id;
    @Column(name = "OLD_PRICE")
    private BigDecimal oldPrice;
    @Column(name = "NEW_PRICE")
    private BigDecimal newPrice;
    @Size(max = 45)
    @Column(name = "REVALUED_BY")
    private String revaluedBy;
    @Column(name = "REVALUATION_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date revaluationDate;
    @JoinColumn(name = "SLC_ID", referencedColumnName = "ID")
    @ManyToOne
    private FmsStockLedgerCard slcId;
//</editor-fold>

    public FmsStockRevaluationHistory() {
    }

    //<editor-fold defaultstate="collapsed" desc="getter and setter">

    public FmsStockRevaluationHistory(BigDecimal id) {
        this.id = id;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public BigDecimal getOldPrice() {
        return oldPrice;
    }

    public void setOldPrice(BigDecimal oldPrice) {
        this.oldPrice = oldPrice;
    }

    public BigDecimal getNewPrice() {
        return newPrice;
    }

    public void setNewPrice(BigDecimal newPrice) {
        this.newPrice = newPrice;
    }

    public String getRevaluedBy() {
        return revaluedBy;
    }

    public void setRevaluedBy(String revaluedBy) {
        this.revaluedBy = revaluedBy;
    }

    public Date getRevaluationDate() {
        return revaluationDate;
    }

    public void setRevaluationDate(Date revaluationDate) {
        this.revaluationDate = revaluationDate;
    }

    public FmsStockLedgerCard getSlcId() {
        return slcId;
    }

    public void setSlcId(FmsStockLedgerCard slcId) {
        this.slcId = slcId;
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
        if (!(object instanceof FmsStockRevaluationHistory)) {
            return false;
        }
        FmsStockRevaluationHistory other = (FmsStockRevaluationHistory) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.eep.fcms.entity.FmsStockRevaluationHistory[ id=" + id + " ]";
    }

}
