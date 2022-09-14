/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.entity.Bond;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author mora
 */
@Entity
@Table(name = "FMS_BOND_APPLICATION")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FmsBondApplication.findAll", query = "SELECT f FROM FmsBondApplication f"),
    @NamedQuery(name = "FmsBondApplication.findByBondApplicationId", query = "SELECT f FROM FmsBondApplication f WHERE f.BondApplicationId = :BondApplicationId"),
    @NamedQuery(name = "FmsBondApplication.findByBuyerFullName", query = "SELECT f FROM FmsBondApplication f WHERE f.buyerFullName = :buyerFullName"),
    @NamedQuery(name = "FmsBondApplication.findByApplicationDate", query = "SELECT f FROM FmsBondApplication f WHERE f.applicationDate = :applicationDate"),
    @NamedQuery(name = "FmsBondApplication.findByBondApplicationIdlike", query = "SELECT f FROM FmsBondApplication f WHERE f.BondApplicationId LIKE :BondApplicationId"),
    @NamedQuery(name = "FmsBondApplication.findByCountryBondIssued", query = "SELECT f FROM FmsBondApplication f WHERE f.countryBondIssued = :countryBondIssued"),
    @NamedQuery(name = "FmsBondApplication.findByAmount", query = "SELECT f FROM FmsBondApplication f WHERE f.amount = :amount"),
    @NamedQuery(name = "FmsBondApplication.findByModeOfPayment", query = "SELECT f FROM FmsBondApplication f WHERE f.modeOfPayment = :modeOfPayment"),
    @NamedQuery(name = "FmsBondApplication.findByGracePeriod", query = "SELECT f FROM FmsBondApplication f WHERE f.gracePeriod = :gracePeriod"),
    @NamedQuery(name = "FmsBondApplication.findByLastRepaymentSchedule", query = "SELECT f FROM FmsBondApplication f WHERE f.lastRepaymentSchedule = :lastRepaymentSchedule"),
    @NamedQuery(name = "FmsBondApplication.findByStatus", query = "SELECT f FROM FmsBondApplication f WHERE f.status = :status")})
public class FmsBondApplication implements Serializable {

    private static final long serialVersionUID = 1L;
    //<editor-fold defaultstate="collapsed" desc="variable declaration">
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "SERIAL_NO")
    private String BondApplicationId;
    @Size(max = 20)
    @Column(name = "BUYER_FULL_NAME")
    private String buyerFullName;
    @Column(name = "APPLICATION_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date applicationDate;
    @Size(max = 20)
    @Column(name = "COUNTRY_BOND_ISSUED")
    private String countryBondIssued;
    @Column(name = "AMOUNT")
    private Double amount;
    @Size(max = 20)
    @Column(name = "MODE_OF_PAYMENT")
    private String modeOfPayment;
    @Size(max = 20)
    @Column(name = "GRACE_PERIOD")
    private String gracePeriod;
    @Column(name = "LAST_REPAYMENT_SCHEDULE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastRepaymentSchedule;
    @Size(max = 20)
    @Column(name = "STATUS")
    private String status;
    @JoinColumn(name = "BOND_TYPE_ID", referencedColumnName = "BOND_TYPE_ID")
    @ManyToOne
    private FmsBondType BondTypeId;
    //</editor-fold>

    public FmsBondApplication() {
    }

    //<editor-fold defaultstate="collapsed" desc="Getter and Setter">

    public FmsBondApplication(String BondApplicationId) {
        this.BondApplicationId = BondApplicationId;
    }

    public String getBondApplicationId() {
        return BondApplicationId;
    }

    public void setBondApplicationId(String BondApplicationId) {
        this.BondApplicationId = BondApplicationId;
    }

    public String getBuyerFullName() {
        return buyerFullName;
    }

    public void setBuyerFullName(String buyerFullName) {
        this.buyerFullName = buyerFullName;
    }

    public Date getApplicationDate() {
        return applicationDate;
    }

    public void setApplicationDate(Date applicationDate) {
        this.applicationDate = applicationDate;
    }

    public String getCountryBondIssued() {
        return countryBondIssued;
    }

    public void setCountryBondIssued(String countryBondIssued) {
        this.countryBondIssued = countryBondIssued;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getModeOfPayment() {
        return modeOfPayment;
    }

    public void setModeOfPayment(String modeOfPayment) {
        this.modeOfPayment = modeOfPayment;
    }

    public String getGracePeriod() {
        return gracePeriod;
    }

    public void setGracePeriod(String gracePeriod) {
        this.gracePeriod = gracePeriod;
    }

    public Date getLastRepaymentSchedule() {
        return lastRepaymentSchedule;
    }

    public void setLastRepaymentSchedule(Date lastRepaymentSchedule) {
        this.lastRepaymentSchedule = lastRepaymentSchedule;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public FmsBondType getBondTypeId() {
        return BondTypeId;
    }

    public void setBondTypeId(FmsBondType BondTypeId) {
        this.BondTypeId = BondTypeId;
    }
//</editor-fold>

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (BondApplicationId != null ? BondApplicationId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof FmsBondApplication)) {
            return false;
        }
        FmsBondApplication other = (FmsBondApplication) object;
        if ((this.BondApplicationId == null && other.BondApplicationId != null) || (this.BondApplicationId != null && !this.BondApplicationId.equals(other.BondApplicationId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return BondApplicationId;
    }

}
