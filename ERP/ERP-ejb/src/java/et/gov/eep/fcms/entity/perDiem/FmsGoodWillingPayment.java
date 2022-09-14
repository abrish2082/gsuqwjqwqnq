
package et.gov.eep.fcms.entity.perDiem;

import java.io.Serializable;
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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author muller
 */
@Entity
@Table(name = "FMS_GOOD_WILLING_PAYMENT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FmsGoodWillingPayment.findAll", query = "SELECT f FROM FmsGoodWillingPayment f"),
    @NamedQuery(name = "FmsGoodWillingPayment.findById", query = "SELECT f FROM FmsGoodWillingPayment f WHERE f.id = :id"),
    @NamedQuery(name = "FmsGoodWillingPayment.findBySingleCountryPayment", query = "SELECT f FROM FmsGoodWillingPayment f WHERE f.singleCountryPayment = :singleCountryPayment"),
    @NamedQuery(name = "FmsGoodWillingPayment.findByMultipleCountryPayment", query = "SELECT f FROM FmsGoodWillingPayment f WHERE f.multipleCountryPayment = :multipleCountryPayment"),
    @NamedQuery(name = "FmsGoodWillingPayment.findByReservePayment", query = "SELECT f FROM FmsGoodWillingPayment f WHERE f.reservePayment = :reservePayment")})
public class FmsGoodWillingPayment implements Serializable {
    private static final long serialVersionUID = 1L;
     //<editor-fold defaultstate="collapsed" desc="variable declaration">
    @Id
    @Basic(optional = false)
    @NotNull
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FMS_GOODWILL_SEQ")
    @SequenceGenerator(name = "FMS_GOODWILL_SEQ", sequenceName = "FMS_GOODWILL_SEQ", allocationSize = 1)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "SINGLE_COUNTRY_PAYMENT")
    private Double singleCountryPayment;
    @Column(name = "MULTIPLE_COUNTRY_PAYMENT")
    private Double multipleCountryPayment;
    @Column(name = "RESERVE_PAYMENT")
    private Double reservePayment;
//</editor-fold>
    public FmsGoodWillingPayment() {
    }
 //<editor-fold defaultstate="collapsed" desc="getter and setter">
    public FmsGoodWillingPayment(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getSingleCountryPayment() {
        return singleCountryPayment;
    }

    public void setSingleCountryPayment(Double singleCountryPayment) {
        this.singleCountryPayment = singleCountryPayment;
    }

    public Double getMultipleCountryPayment() {
        return multipleCountryPayment;
    }

    public void setMultipleCountryPayment(Double multipleCountryPayment) {
        this.multipleCountryPayment = multipleCountryPayment;
    }

    public Double getReservePayment() {
        return reservePayment;
    }

    public void setReservePayment(Double reservePayment) {
        this.reservePayment = reservePayment;
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
        if (!(object instanceof FmsGoodWillingPayment)) {
            return false;
        }
        FmsGoodWillingPayment other = (FmsGoodWillingPayment) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return  id.toString();
    }
    
}
