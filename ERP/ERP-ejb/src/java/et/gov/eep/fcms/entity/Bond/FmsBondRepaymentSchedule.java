/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.entity.Bond;

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
 * @author mora
 */
@Entity
@Table(name = "FMS_BOND_REPAYMENT_SCHEDULE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FmsBondRepaymentSchedule.findAll", query = "SELECT f FROM FmsBondRepaymentSchedule f"),
    @NamedQuery(name = "FmsBondRepaymentSchedule.findByRepaymentScheduleId", query = "SELECT f FROM FmsBondRepaymentSchedule f WHERE f.repaymentScheduleId = :repaymentScheduleId"),
    @NamedQuery(name = "FmsBondRepaymentSchedule.findByNoOfInstallmen", query = "SELECT f FROM FmsBondRepaymentSchedule f WHERE f.noOfInstallmen = :noOfInstallmen"),
    @NamedQuery(name = "FmsBondRepaymentSchedule.findByInstallmentDate", query = "SELECT f FROM FmsBondRepaymentSchedule f WHERE f.installmentDate = :installmentDate"),
    @NamedQuery(name = "FmsBondRepaymentSchedule.findByPrincipalPayment", query = "SELECT f FROM FmsBondRepaymentSchedule f WHERE f.principalPayment = :principalPayment"),
    @NamedQuery(name = "FmsBondRepaymentSchedule.findByPreparedBy", query = "SELECT f FROM FmsBondRepaymentSchedule f WHERE f.preparedBy = :preparedBy"),
    @NamedQuery(name = "FmsBondRepaymentSchedule.findByStatusLike", query = "SELECT f FROM FmsBondRepaymentSchedule f WHERE f.repaymentScheduleId LIKE :repaymentScheduleId and f.status='not'"),
    @NamedQuery(name = "FmsBondRepaymentSchedule.findByApprovedBy", query = "SELECT f FROM FmsBondRepaymentSchedule f WHERE f.approvedBy = :approvedBy"),
    @NamedQuery(name = "FmsBondRepaymentSchedule.findByPreparedByDate", query = "SELECT f FROM FmsBondRepaymentSchedule f WHERE f.preparedByDate = :preparedByDate"),
    @NamedQuery(name = "FmsBondRepaymentSchedule.findByStatus", query = "SELECT f FROM FmsBondRepaymentSchedule f WHERE f.status = :status")})
public class FmsBondRepaymentSchedule implements Serializable {

    private static final long serialVersionUID = 1L;
      //<editor-fold defaultstate="collapsed" desc="variable declaration">
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "REPAYMENT_SCHEDULE_ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FMS_BOND_REPAYMEN_SCHEDULE_SEQ")
    @SequenceGenerator(name = "FMS_BOND_REPAYMEN_SCHEDULE_SEQ", sequenceName = "FMS_BOND_REPAYMEN_SCHEDULE_SEQ", allocationSize = 1)
    private BigDecimal repaymentScheduleId;
    @Column(name = "NO_OF_INSTALLMEN")
    private int noOfInstallmen;
    @Column(name = "INSTALLMENT_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date installmentDate;
    @Column(name = "PRINCIPAL_PAYMENT")
    private Double principalPayment;
    @Size(max = 20)
    @Column(name = "PREPARED_BY")
    private String preparedBy;
    @Size(max = 20)
    @Column(name = "APPROVED_BY")
    private String approvedBy;
    @Column(name = "PREPARED_BY_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date preparedByDate;
    @Size(max = 20)
    @Column(name = "STATUS")
    private String status;
    @JoinColumn(name = "SERIAL_NO", referencedColumnName = "SERIAL_NO")
    @ManyToOne
    private FmsBondApplication BondId;
//</editor-fold>
    public FmsBondRepaymentSchedule() {
    }
  //<editor-fold defaultstate="collapsed" desc="Getter and Setter">
    public FmsBondRepaymentSchedule(BigDecimal repaymentScheduleId) {
        this.repaymentScheduleId = repaymentScheduleId;
    }

    public BigDecimal getRepaymentScheduleId() {
        return repaymentScheduleId;
    }

    public void setRepaymentScheduleId(BigDecimal repaymentScheduleId) {
        this.repaymentScheduleId = repaymentScheduleId;
    }

    public int getNoOfInstallmen() {
        return noOfInstallmen;
    }

    public void setNoOfInstallmen(int noOfInstallmen) {
        this.noOfInstallmen = noOfInstallmen;
    }

    public Date getInstallmentDate() {
        return installmentDate;
    }

    public void setInstallmentDate(Date installmentDate) {
        this.installmentDate = installmentDate;
    }

    public Double getPrincipalPayment() {
        return principalPayment;
    }

    public void setPrincipalPayment(Double principalPayment) {
        this.principalPayment = principalPayment;
    }

    public String getPreparedBy() {
        return preparedBy;
    }

    public void setPreparedBy(String preparedBy) {
        this.preparedBy = preparedBy;
    }

    public String getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(String approvedBy) {
        this.approvedBy = approvedBy;
    }

    public Date getPreparedByDate() {
        return preparedByDate;
    }

    public void setPreparedByDate(Date preparedByDate) {
        this.preparedByDate = preparedByDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public FmsBondApplication getBondId() {
        return BondId;
    }

    public void setBondId(FmsBondApplication BondId) {
        this.BondId = BondId;
    }
//</editor-fold>
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (repaymentScheduleId != null ? repaymentScheduleId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof FmsBondRepaymentSchedule)) {
            return false;
        }
        FmsBondRepaymentSchedule other = (FmsBondRepaymentSchedule) object;
        if ((this.repaymentScheduleId == null && other.repaymentScheduleId != null) || (this.repaymentScheduleId != null && !this.repaymentScheduleId.equals(other.repaymentScheduleId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return (String.valueOf(repaymentScheduleId));
    }

}
