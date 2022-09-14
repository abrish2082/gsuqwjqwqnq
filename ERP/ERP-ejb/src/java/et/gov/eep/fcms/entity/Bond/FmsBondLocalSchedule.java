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
@Table(name = "FMS_BOND_LOCAL_SCHEDULE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FmsBondLocalSchedule.findAll", query = "SELECT f FROM FmsBondLocalSchedule f"),
    @NamedQuery(name = "FmsBondLocalSchedule.findByLRepaymentSchId", query = "SELECT f FROM FmsBondLocalSchedule f WHERE f.lRepaymentSchId = :lRepaymentSchId"),
    @NamedQuery(name = "FmsBondLocalSchedule.findBySerialNo", query = "SELECT f FROM FmsBondLocalSchedule f WHERE f.serialNo = :serialNo"),
    @NamedQuery(name = "FmsBondLocalSchedule.findBySerialNoLike", query = "SELECT f FROM FmsBondLocalSchedule f WHERE f.serialNo LIKE :serialNo"),
    @NamedQuery(name = "FmsBondLocalSchedule.findByNoOfInstallmen", query = "SELECT f FROM FmsBondLocalSchedule f WHERE f.noOfInstallmen = :noOfInstallmen"),
    @NamedQuery(name = "FmsBondLocalSchedule.findByLocalBondId_instlment", query = "SELECT f FROM FmsBondLocalSchedule f WHERE f.localBondId = :localBondId and f.noOfInstallmen = :noOfInstallmen"),
    @NamedQuery(name = "FmsBondLocalSchedule.findBySerialNo_instlment", query = "SELECT f FROM FmsBondLocalSchedule f WHERE f.serialNo = :serialNo and f.noOfInstallmen = :noOfInstallmen"),
    @NamedQuery(name = "FmsBondLocalSchedule.findByInstallmentDueDate", query = "SELECT f FROM FmsBondLocalSchedule f WHERE f.installmentDueDate = :installmentDueDate"),
    @NamedQuery(name = "FmsBondLocalSchedule.findByInstallmentDueDatelike", query = "SELECT f FROM FmsBondLocalSchedule f WHERE f.installmentDueDate Like :installmentDueDate"),
    @NamedQuery(name = "FmsBondLocalSchedule.findByPrincipal", query = "SELECT f FROM FmsBondLocalSchedule f WHERE f.principal = :principal"),
    @NamedQuery(name = "FmsBondLocalSchedule.findByInterest", query = "SELECT f FROM FmsBondLocalSchedule f WHERE f.interest = :interest"),
    @NamedQuery(name = "FmsBondLocalSchedule.findByRemain", query = "SELECT f FROM FmsBondLocalSchedule f WHERE f.remain = :remain"),
    @NamedQuery(name = "FmsBondLocalSchedule.findByLocalBondId", query = "SELECT f FROM FmsBondLocalSchedule f WHERE f.localBondId = :localBondId"),
    @NamedQuery(name = "FmsBondLocalSchedule.findByDueDate", query = "SELECT f FROM FmsBondLocalSchedule f WHERE f.installmentDueDate between :StartDate and :EndDate"),
    @NamedQuery(name = "FmsBondLocalSchedule.findByStartEndDate", query = "SELECT f FROM FmsBondLocalSchedule f WHERE f.installmentDueDate between :startDate and :endDate"),
    @NamedQuery(name = "FmsBondLocalSchedule.findByStatusNotPayd", query = "SELECT f FROM FmsBondLocalSchedule f WHERE f.status = :status"),
    @NamedQuery(name = "FmsBondLocalSchedule.findByStatus", query = "SELECT f FROM FmsBondLocalSchedule f WHERE f.status = :status")})
public class FmsBondLocalSchedule implements Serializable {

    private static final long serialVersionUID = 1L;
    //<editor-fold defaultstate="collapsed" desc="variable declaration">
    @Id
    @Basic(optional = false)
    @NotNull
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FMS_BOND_LOCAL_SCH_SEQ")
    @SequenceGenerator(name = "FMS_BOND_LOCAL_SCH_SEQ", sequenceName = "FMS_BOND_LOCAL_SCH_SEQ", allocationSize = 1)
    @Column(name = "L_REPAYMENT_SCH_ID")
    private BigDecimal lRepaymentSchId;
    @Column(name = "NO_OF_INSTALLMEN")
    private int noOfInstallmen;
    @Column(name = "INSTALLMENT_DUE_DATE")
    @Temporal(TemporalType.DATE)
    private Date installmentDueDate;
    @Column(name = "PRINCIPAL")
    private Double principal;
    @Column(name = "INTEREST")
    private Double interest;
    @Column(name = "PAYMENT_DOCUMENT_DATE")
    @Temporal(TemporalType.DATE)
    private Date paymentDocumentDate;
    @Column(name = "PAYMENT_DOCUMENT_REFERENCE")
    private String paymentDocumentReference;
    @Size(max = 100)
    @Column(name = "REMARK")
    private String remark;
    @Column(name = "PAID_AMOUNT")
    private Double paidAmount;
    @Column(name = "REMAIN")
    private Double remain;
    @Size(max = 20)
    @Column(name = "STATUS")
    private String status;
    @Size(min = 1, max = 20)
    @Column(name = "SERIAL_NO")
    private String serialNo;
    @JoinColumn(name = "LOCAL_BOND_ID", referencedColumnName = "LOCAL_BOND_ID")
    @ManyToOne
    private FmsBondLocal localBondId;
    @Transient
    Date StartDate;
    @Transient
    Date EndDate;
    @Transient
    String ChangeStatus;
//</editor-fold>

    public FmsBondLocalSchedule() {
    }

    //<editor-fold defaultstate="collapsed" desc="Getter and Setter">

    public String getChangeStatus() {
        return ChangeStatus;
    }

    public void setChangeStatus(String ChangeStatus) {
        this.ChangeStatus = ChangeStatus;
    }

    public FmsBondLocalSchedule(BigDecimal lRepaymentSchId) {
        this.lRepaymentSchId = lRepaymentSchId;
    }

    public BigDecimal getLRepaymentSchId() {
        return lRepaymentSchId;
    }

    public void setLRepaymentSchId(BigDecimal lRepaymentSchId) {
        this.lRepaymentSchId = lRepaymentSchId;
    }

    public int getNoOfInstallmen() {
        return noOfInstallmen;
    }

    public void setNoOfInstallmen(int noOfInstallmen) {
        this.noOfInstallmen = noOfInstallmen;
    }

    public Date getInstallmentDueDate() {
        return installmentDueDate;
    }

    public void setInstallmentDueDate(Date installmentDueDate) {
        this.installmentDueDate = installmentDueDate;
    }

    public Double getPrincipal() {
        return principal;
    }

    public void setPrincipal(Double principal) {
        this.principal = principal;
    }

    public Double getInterest() {
        return interest;
    }

    public void setInterest(Double interest) {
        this.interest = interest;
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

    public Double getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(Double paidAmount) {
        this.paidAmount = paidAmount;
    }

    public Double getRemain() {
        return remain;
    }

    public void setRemain(Double remain) {
        this.remain = remain;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public FmsBondLocal getLocalBondId() {
        return localBondId;
    }

    public void setLocalBondId(FmsBondLocal localBondId) {
        this.localBondId = localBondId;
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
//</editor-fold>

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (lRepaymentSchId != null ? lRepaymentSchId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof FmsBondLocalSchedule)) {
            return false;
        }
        FmsBondLocalSchedule other = (FmsBondLocalSchedule) object;
        if ((this.lRepaymentSchId == null && other.lRepaymentSchId != null) || (this.lRepaymentSchId != null && !this.lRepaymentSchId.equals(other.lRepaymentSchId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.eep.fcms.entity.Bond.FmsBondLocalSchedule[ lRepaymentSchId=" + lRepaymentSchId + " ]";
    }

}
