package et.gov.eep.fcms.entity.stock;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author user
 */
@Entity
@Table(name = "FMS_STOCK_QUANTITY")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FmsStockQuantity.findAll", query = "SELECT f FROM FmsStockQuantity f"),
    @NamedQuery(name = "FmsStockQuantity.findById", query = "SELECT f FROM FmsStockQuantity f WHERE f.id = :id"),
    @NamedQuery(name = "FmsStockQuantity.findByQuantity", query = "SELECT f FROM FmsStockQuantity f WHERE f.quantity = :quantity")})
public class FmsStockQuantity implements Serializable {

    private static final long serialVersionUID = 1L;
    //<editor-fold defaultstate="collapsed" desc="variable declaration">
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private BigDecimal id;
    @Column(name = "QUANTITY")
    private BigInteger quantity;
    @JoinColumn(name = "TOTALSTOCKVALUE", referencedColumnName = "ID")
    @ManyToOne
    private FmsTotalStockValue totalstockvalue;
//</editor-fold>

    public FmsStockQuantity() {
    }

    //<editor-fold defaultstate="collapsed" desc="getter and setter">

    public FmsStockQuantity(BigDecimal id) {
        this.id = id;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public BigInteger getQuantity() {
        return quantity;
    }

    public void setQuantity(BigInteger quantity) {
        this.quantity = quantity;
    }

    public FmsTotalStockValue getTotalstockvalue() {
        return totalstockvalue;
    }

    public void setTotalstockvalue(FmsTotalStockValue totalstockvalue) {
        this.totalstockvalue = totalstockvalue;
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
        if (!(object instanceof FmsStockQuantity)) {
            return false;
        }
        FmsStockQuantity other = (FmsStockQuantity) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.eep.fcms.entity.stock.FmsStockQuantity[ id=" + id + " ]";
    }

}
