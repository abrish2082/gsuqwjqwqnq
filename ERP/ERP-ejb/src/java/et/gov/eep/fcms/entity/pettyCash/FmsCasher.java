package et.gov.eep.fcms.entity.pettyCash;

import java.io.Serializable;
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
import javax.xml.bind.annotation.XmlRootElement;
import et.gov.eep.fcms.entity.FmsEmployee;

/**
 *
 * @author AB
 */
@Entity
@Table(name = "fms_casher")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FmsCasher.findAll", query = "SELECT f FROM FmsCasher f"),
    @NamedQuery(name = "FmsCasher.findByCasheirId", query = "SELECT f FROM FmsCasher f WHERE f.casheirId = :casheirId")})
public class FmsCasher implements Serializable {

    private static final long serialVersionUID = 1L;
    //<editor-fold defaultstate="collapsed" desc="variable declaration">
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FMS_CASHER_CASHEIR_ID_SEQ")
    @SequenceGenerator(name = "FMS_CASHER_CASHEIR_ID_SEQ", sequenceName = "FMS_CASHER_CASHEIR_ID_SEQ", allocationSize = 1)
    @Basic(optional = false)
    @Column(name = "CASHEIR_ID")
    private Integer casheirId;
    @JoinColumn(name = "EMP_ID", referencedColumnName = "EMP_ID")
    @ManyToOne
    private FmsEmployee empId;
//</editor-fold>

    public FmsCasher() {
    }
//<editor-fold defaultstate="collapsed" desc="getter and setter">

    public FmsCasher(Integer casheirId) {
        this.casheirId = casheirId;
    }

    public Integer getCasheirId() {
        return casheirId;
    }

    public void setCasheirId(Integer casheirId) {
        this.casheirId = casheirId;
    }

    public FmsEmployee getEmpId() {
        return empId;
    }

    public void setEmpId(FmsEmployee empId) {
        this.empId = empId;
    }
//</editor-fold>

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (casheirId != null ? casheirId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof FmsCasher)) {
            return false;
        }
        FmsCasher other = (FmsCasher) object;
        if ((this.casheirId == null && other.casheirId != null) || (this.casheirId != null && !this.casheirId.equals(other.casheirId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.insa.erp.ibfms.entity.FmsCasher[ casheirId=" + casheirId + " ]";
    }

}
