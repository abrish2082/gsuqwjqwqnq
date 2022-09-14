package et.gov.eep.fcms.entity.stock;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Terminal2
 */
@Entity
@Table(name = "FMS_TOTAL_STOCK_VALUE", catalog = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FmsTotalStockValue.findAll", query = "SELECT f FROM FmsTotalStockValue f"),
    @NamedQuery(name = "FmsTotalStockValue.findById", query = "SELECT f FROM FmsTotalStockValue f WHERE f.id = :id"),
    @NamedQuery(name = "FmsTotalStockValue.findByTotalQty", query = "SELECT f FROM FmsTotalStockValue f WHERE f.totalQty = :totalQty"),
    @NamedQuery(name = "FmsTotalStockValue.findByWeightAvgPric", query = "SELECT f FROM FmsTotalStockValue f WHERE f.weightAvgPric = :weightAvgPric"),
    @NamedQuery(name = "FmsTotalStockValue.findByMaterialCode", query = "SELECT f FROM FmsTotalStockValue f WHERE f.materialCode = :materialCode"),})
public class FmsTotalStockValue implements Serializable {

    private static final long serialVersionUID = 1L;
    //<editor-fold defaultstate="collapsed" desc="variable declaration">
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID", nullable = false, precision = 0, scale = -127)
    private BigDecimal id;
    @Column(name = "TOTAL_QTY")
    private BigInteger totalQty;
    @Column(name = "WEIGHT_AVG_PRIC")
    private BigDecimal weightAvgPric;
    @Size(max = 20)
    @Column(name = "MATERIAL_CODE", length = 20)
    private String materialCode;
//</editor-fold>

    public FmsTotalStockValue() {
    }

    //<editor-fold defaultstate="collapsed" desc="getter and setter">

    public FmsTotalStockValue(BigDecimal id) {
        this.id = id;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public BigInteger getTotalQty() {
        return totalQty;
    }

    public void setTotalQty(BigInteger totalQty) {
        this.totalQty = totalQty;
    }

    public BigDecimal getWeightAvgPric() {
        return weightAvgPric;
    }

    public void setWeightAvgPric(BigDecimal weightAvgPric) {
        this.weightAvgPric = weightAvgPric;
    }

    public String getMaterialCode() {
        return materialCode;
    }

    public void setMaterialCode(String materialCode) {
        this.materialCode = materialCode;
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
        if (!(object instanceof FmsTotalStockValue)) {
            return false;
        }
        FmsTotalStockValue other = (FmsTotalStockValue) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.eep.fcms.entity.stock.FmsTotalStockValue[ id=" + id + " ]";
    }

}
