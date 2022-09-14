package et.gov.eep.fcms.entity.perDiem;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author userPCAdmin
 */
@Entity
@Table(name = "FMS_LU_TYPE_FIELD_ALLOWANCE", catalog = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FmsLuTypeFieldAllowance.findAll", query = "SELECT f FROM FmsLuTypeFieldAllowance f"),
    @NamedQuery(name = "FmsLuTypeFieldAllowance.findById", query = "SELECT f FROM FmsLuTypeFieldAllowance f WHERE f.id = :id"),
    @NamedQuery(name = "FmsLuTypeFieldAllowance.findByDescription", query = "SELECT f FROM FmsLuTypeFieldAllowance f WHERE f.description = :description")})
public class FmsLuTypeFieldAllowance implements Serializable {

    private static final long serialVersionUID = 1L;
    //<editor-fold defaultstate="collapsed" desc="variable declaration">
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FMS_LU_TYPE_FIELD_ALLOWA_SEQ")
    @SequenceGenerator(name = "FMS_LU_TYPE_FIELD_ALLOWA_SEQ", sequenceName = "FMS_LU_TYPE_FIELD_ALLOWA_SEQ", allocationSize = 1)
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false, precision = 0, scale = -127)
    private BigDecimal id;
    @Size(max = 20)
    @Column(length = 20)
    private String description;
//</editor-fold>

    public FmsLuTypeFieldAllowance() {
    }
//<editor-fold defaultstate="collapsed" desc="getter and setter">

    public FmsLuTypeFieldAllowance(BigDecimal id) {
        this.id = id;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
        if (!(object instanceof FmsLuTypeFieldAllowance)) {
            return false;
        }
        FmsLuTypeFieldAllowance other = (FmsLuTypeFieldAllowance) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return description;
    }

}
