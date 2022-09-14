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
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author mz
 */
@Entity
@Table(name = "FMS_BOND_FOREIGN_SCHEDULE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FmsBondForeignSchedule.findAll", query = "SELECT f FROM FmsBondForeignSchedule f"),
    @NamedQuery(name = "FmsBondForeignSchedule.findByLRepaymentSchId", query = "SELECT f FROM FmsBondForeignSchedule f WHERE f.lRepaymentSchId = :lRepaymentSchId"),
    @NamedQuery(name = "FmsBondForeignSchedule.findByBondForeignId_inslement", query = "SELECT f FROM FmsBondForeignSchedule f WHERE f.BondForeignId = :BondForeignId and f.noOfInstallmen = :noOfInstallmen"),
    @NamedQuery(name = "FmsBondForeignSchedule.findByserialNo_inslement", query = "SELECT f FROM FmsBondForeignSchedule f WHERE f.serialNo = :serialNo and f.noOfInstallmen = :noOfInstallmen"),
    @NamedQuery(name = "FmsBondForeignSchedule.findBySerialNoAndDueDate", query = "SELECT f FROM FmsBondForeignSchedule f WHERE f.serialNo = :serialNo and f.installmentDueDate between :StartDate and :EndDate"),
    @NamedQuery(name = "FmsBondForeignSchedule.findByBondForeignIdlike", query = "SELECT f FROM FmsBondForeignSchedule f WHERE f.BondForeignId LIKE :BondForeignId"),
    @NamedQuery(name = "FmsBondForeignSchedule.findBySerialNo", query = "SELECT f FROM FmsBondForeignSchedule f WHERE f.serialNo = :serialNo"),
    @NamedQuery(name = "FmsBondForeignSchedule.findByNoOfInstallmen", query = "SELECT f FROM FmsBondForeignSchedule f WHERE f.noOfInstallmen = :noOfInstallmen"),
    @NamedQuery(name = "FmsBondForeignSchedule.findByInstallmentDueDate", query = "SELECT f FROM FmsBondForeignSchedule f WHERE f.installmentDueDate = :installmentDueDate"),
    @NamedQuery(name = "FmsBondForeignSchedule.findByStartendDate", query = "SELECT f FROM FmsBondForeignSchedule f WHERE f.installmentDueDate between :StartDate and :EndDate "),
    @NamedQuery(name = "FmsBondForeignSchedule.findByInterest", query = "SELECT f FROM FmsBondForeignSchedule f WHERE f.interest = :interest"),
    @NamedQuery(name = "FmsBondForeignSchedule.findByBondForeignId", query = "SELECT f FROM FmsBondForeign f WHERE f.BondForeignId = :BondForeignId"),
    @NamedQuery(name = "FmsBondForeignSchedule.findByStatuslike", query = "SELECT f FROM FmsBondForeignSchedule f WHERE f.installmentDueDate >= :installmentDueDate and  f.status = :status"),
    @NamedQuery(name = "FmsBondForeignSchedule.findByStatusmuchrd", query = "SELECT f FROM FmsBondForeignSchedule f WHERE (f.installmentDueDate between :day and :installmentDueDate) and  f.status = :status"),
    @NamedQuery(name = "FmsBondForeignSchedule.findByStatusnotpayd", query = "SELECT f FROM FmsBondForeignSchedule f WHERE f.status = :status"),
    @NamedQuery(name = "FmsBondForeignSchedule.findByStatus", query = "SELECT f FROM FmsBondForeignSchedule f WHERE f.status = :status")})
public class FmsBondForeignSchedule implements Serializable {

    private static final long serialVersionUID = 1L;
    //<editor-fold defaultstate="collapsed" desc="variable declaration">
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FMS_BOND_FOREIGN_SCH_SEQ")
    @SequenceGenerator(name = "FMS_BOND_FOREIGN_SCH_SEQ", sequenceName = "FMS_BOND_FOREIGN_SCH_SEQ", allocationSize = 1)
    @NotNull
    @Column(name = "L_REPAYMENT_SCH_ID")
    private BigDecimal lRepaymentSchId;
    @Size(max = 20)
    @Column(name = "SERIAL_NO")
    private String serialNo;
    @Column(name = "NO_OF_INSTALLMEN")
    private Integer noOfInstallmen;
    @Column(name = "INSTALLMENT_DUE_DATE")
    @Temporal(TemporalType.DATE)
    private Date installmentDueDate;
    @Column(name = "PAYMENT_DOCUMENT_DATE")
    @Temporal(TemporalType.DATE)
    private Date paymentDocumentDate;
    @Column(name = "PAYMENT_DOCUMENT_REFERENCE")
    private String paymentDocumentReference;
    @Column(name = "REMARK")
    private String remark;
    @Column(name = "INTEREST")
    private Double interest;
    @Column(name = "PRINCIPAL")
    private Double principal;
    @Column(name = "PAID_AMOUNT")
    private Double PaidAmount;
    @Size(max = 20)
    @Column(name = "STATUS")
    private String status;
    @JoinColumn(name = "BOND_FOREIGN_ID", referencedColumnName = "BOND_FOREIGN_ID")
    @ManyToOne
    private FmsBondForeign BondForeignId;
    @Transient
    Date StartDate;
    @Transient
    Date EndDate;
    @Transient
    String ChangedStatus;
    @Transient
    Double totalAmountDue;
//</editor-fold>
    
    public FmsBondForeignSchedule() {
    }
 //<editor-fold defaultstate="collapsed" desc="Getter and Setter">
    public Double getTotalAmountDue() {
        return totalAmountDue;
    }

    public void setTotalAmountDue(Double totalAmountDue) {
        this.totalAmountDue = totalAmountDue;
    }

    public String getChangedStatus() {
        return ChangedStatus;
    }

    public void setChangedStatus(String ChangedStatus) {
        this.ChangedStatus = ChangedStatus;
    }

    public FmsBondForeignSchedule(BigDecimal lRepaymentSchId) {
        this.lRepaymentSchId = lRepaymentSchId;
    }

    public BigDecimal getLRepaymentSchId() {
        return lRepaymentSchId;
    }

    public void setLRepaymentSchId(BigDecimal lRepaymentSchId) {
        this.lRepaymentSchId = lRepaymentSchId;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public Integer getNoOfInstallmen() {
        return noOfInstallmen;
    }

    public void setNoOfInstallmen(Integer noOfInstallmen) {
        this.noOfInstallmen = noOfInstallmen;
    }

    public Date getStartDate() {
        return StartDate;
    }

    public void setStartDate(Date StartDate) {
        this.StartDate = StartDate;
    }

    public Date getEndDate() {
        return EndDate;
    }

    public void setEndDate(Date EndDate) {
        this.EndDate = EndDate;
    }

    public Date getInstallmentDueDate() {
        return installmentDueDate;
    }

    public void setInstallmentDueDate(Date installmentDueDate) {
        this.installmentDueDate = installmentDueDate;
    }

    public Date getPaymentDocumentDate() {
        return paymentDocumentDate;
    }

    public void setPaymentDocumentDate(Date paymentDocumentDate) {
        this.paymentDocumentDate = paymentDocumentDate;
    }

    public String getPaymentDocumentReference() {
        return paymentDocumentReference;
    }

    public void setPaymentDocumentReference(String paymentDocumentReference) {
        this.paymentDocumentReference = paymentDocumentReference;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Double getPrincipal() {
        return principal;
    }

    public void setPrincipal(Double principal) {
        this.principal = principal;
    }

    public Double getPaidAmount() {
        return PaidAmount;
    }

    public void setPaidAmount(Double PaidAmount) {
        this.PaidAmount = PaidAmount;
    }

    public Double getInterest() {
        return interest;
    }

    public void setInterest(Double interest) {
        this.interest = interest;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public FmsBondForeign getBondForeignId() {
        return BondForeignId;
    }

    public void setBondForeignId(FmsBondForeign BondForeignId) {
        this.BondForeignId = BondForeignId;
    }
//</editor-fold>
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (lRepaymentSchId != null ? lRepaymentSchId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof FmsBondForeignSchedule)) {
            return false;
        }
        FmsBondForeignSchedule other = (FmsBondForeignSchedule) object;
        if ((this.lRepaymentSchId == null && other.lRepaymentSchId != null) || (this.lRepaymentSchId != null && !this.lRepaymentSchId.equals(other.lRepaymentSchId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.eep.fcms.entity.Bond.FmsBondForeignSchedule[ lRepaymentSchId=" + lRepaymentSchId + " ]";
    }

}
