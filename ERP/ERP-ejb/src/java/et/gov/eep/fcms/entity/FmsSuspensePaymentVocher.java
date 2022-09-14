/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.entity;

import et.gov.eep.fcms.entity.Vocher.FmsVoucher;
import java.io.Serializable;
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
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author AB
 */
@Entity
@Table(name = "fms_suspense_payment_vocher")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = ".findAll", query = "SELECT f FROM FmsSuspensePaymentVocher f"),
    @NamedQuery(name = "FmsSuspensePaymentVocher.findByVoucherNumber", query = "SELECT f FROM FmsSuspensePaymentVocher f WHERE f.voucherNumber = :voucherNumber"),
    @NamedQuery(name = "FmsSuspensePaymentVocher.findBySourceJeId", query = "SELECT f FROM FmsSuspensePaymentVocher f WHERE f.sourceJeId = :sourceJeId"),
    @NamedQuery(name = "FmsSuspensePaymentVocher.findByCashierId", query = "SELECT f FROM FmsSuspensePaymentVocher f WHERE f.cashierId = :cashierId"),
    @NamedQuery(name = "FmsSuspensePaymentVocher.findByPaidTo", query = "SELECT f FROM FmsSuspensePaymentVocher f WHERE f.paidTo = :paidTo"),
    @NamedQuery(name = "FmsSuspensePaymentVocher.findByAmountInFigure", query = "SELECT f FROM FmsSuspensePaymentVocher f WHERE f.amountInFigure = :amountInFigure"),
    @NamedQuery(name = "FmsSuspensePaymentVocher.findByAmountInWord", query = "SELECT f FROM FmsSuspensePaymentVocher f WHERE f.amountInWord = :amountInWord"),
    @NamedQuery(name = "FmsSuspensePaymentVocher.findByPreparedBy", query = "SELECT f FROM FmsSuspensePaymentVocher f WHERE f.preparedBy = :preparedBy"),
    @NamedQuery(name = "FmsSuspensePaymentVocher.findByChekedBy", query = "SELECT f FROM FmsSuspensePaymentVocher f WHERE f.chekedBy = :chekedBy"),
    @NamedQuery(name = "FmsSuspensePaymentVocher.findByApprovedBy", query = "SELECT f FROM FmsSuspensePaymentVocher f WHERE f.approvedBy = :approvedBy"),
    @NamedQuery(name = "FmsSuspensePaymentVocher.findByPreparedDate", query = "SELECT f FROM FmsSuspensePaymentVocher f WHERE f.preparedDate = :preparedDate"),
    @NamedQuery(name = "FmsSuspensePaymentVocher.findByChekedDate", query = "SELECT f FROM FmsSuspensePaymentVocher f WHERE f.chekedDate = :chekedDate"),
    @NamedQuery(name = "FmsSuspensePaymentVocher.findByApprovedOn", query = "SELECT f FROM FmsSuspensePaymentVocher f WHERE f.approvedOn = :approvedOn"),
    @NamedQuery(name = "FmsSuspensePaymentVocher.findByReferenceNumber", query = "SELECT f FROM FmsSuspensePaymentVocher f WHERE f.referenceNumber = :referenceNumber"),
    @NamedQuery(name = "FmsSuspensePaymentVocher.findByStatus", query = "SELECT f FROM FmsSuspensePaymentVocher f WHERE f.status = :status"),
    @NamedQuery(name = "FmsSuspensePaymentVocher.findBySuspensepaymentvocherID", query = "SELECT f FROM FmsSuspensePaymentVocher f WHERE f.suspensepaymentvocherID = :suspensepaymentvocherID")})
public class FmsSuspensePaymentVocher implements Serializable {

    @Size(max = 45)
    @Column(name = "SUSPENSE_PYMT_TYPE", length = 45)
    private String suspensePymtType;
    @Size(max = 45)
    @Column(name = "RECEIVED_BY", length = 45)
    private String receivedBy;
    private static final long serialVersionUID = 1L;
    @Size(max = 20)
    @Column(name = "VOUCHER_NUMBER", length = 20)
    private String voucherNumber;
    @Size(max = 50)
    @Column(name = "SOURCE_JE_ID", length = 50)
    private String sourceJeId;
    @Size(max = 20)
    @Column(name = "CASHIER_ID", length = 20)
    private String cashierId;
    @Size(max = 20)
    @Column(name = "PAID_TO", length = 20)
    private String paidTo;
    @Column(name = "AMOUNT_IN_FIGURE")
    private Long amountInFigure;
    @Size(max = 500)
    @Column(name = "AMOUNT_IN_WORD", length = 500)
    private String amountInWord;
    @Size(max = 50)
    @Column(name = "PREPARED_BY", length = 50)
    private String preparedBy;
    @Size(max = 50)
    @Column(name = "CHEKED_BY", length = 50)
    private String chekedBy;
    @Size(max = 50)
    @Column(name = "APPROVED_BY", length = 50)
    private String approvedBy;
    @Column(name = "PREPARED_DATE")
    @Temporal(TemporalType.DATE)
    private Date preparedDate;
    @Column(name = "CHEKED_DATE")
    @Temporal(TemporalType.DATE)
    private Date chekedDate;
    @Column(name = "APPROVED_ON")
    @Temporal(TemporalType.DATE)
    private Date approvedOn;
    @Size(max = 20)
    @Column(name = "REFERENCE_NUMBER", length = 20)
    private String referenceNumber;
    @Column(name = "STATUS_")
    private Integer status;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FMS_SUSPENSE_PAYMENT_VOCHE_SEQ")
    @SequenceGenerator(name = "FMS_SUSPENSE_PAYMENT_VOCHE_SEQ", sequenceName = "FMS_SUSPENSE_PAYMENT_VOCHE_SEQ", allocationSize = 1)
    @Basic(optional = false)
    @Column(name = "suspense_payment_vocher_ID", nullable = false)
    private Integer suspensepaymentvocherID;
    @JoinColumn(name = "fms_voucher_VOUCHER_ID", referencedColumnName = "VOUCHER_ID", nullable = false)
    @OneToOne(optional = false)
    private FmsVoucher fmsVOUCHERID;//PREPARER_REMARK
    @Size(max = 45)
    @Column(name = "PREPARER_REMARK", length = 45)
    private String preparerRemark;
    
    /**
     *
     */
    public FmsSuspensePaymentVocher() {
    }

    /**
     *
     * @param suspensepaymentvocherID
     */
    public FmsSuspensePaymentVocher(Integer suspensepaymentvocherID) {
        this.suspensepaymentvocherID = suspensepaymentvocherID;
    }

    /**
     *
     * @return
     */
    public String getVoucherNumber() {
        return voucherNumber;
    }

    /**
     *
     * @param voucherNumber
     */
    public void setVoucherNumber(String voucherNumber) {
        this.voucherNumber = voucherNumber;
    }

    /**
     *
     * @return
     */
    public String getSourceJeId() {
        return sourceJeId;
    }

    /**
     *
     * @param sourceJeId
     */
    public void setSourceJeId(String sourceJeId) {
        this.sourceJeId = sourceJeId;
    }

    /**
     *
     * @return
     */
    public String getCashierId() {
        return cashierId;
    }

    /**
     *
     * @param cashierId
     */
    public void setCashierId(String cashierId) {
        this.cashierId = cashierId;
    }

    /**
     *
     * @return
     */
    public String getPaidTo() {
        return paidTo;
    }

    /**
     *
     * @param paidTo
     */
    public void setPaidTo(String paidTo) {
        this.paidTo = paidTo;
    }

    /**
     *
     * @return
     */
    public Long getAmountInFigure() {
        return amountInFigure;
    }

    /**
     *
     * @param amountInFigure
     */
    public void setAmountInFigure(Long amountInFigure) {
        this.amountInFigure = amountInFigure;
    }

    /**
     *
     * @return
     */
    public String getAmountInWord() {
        return amountInWord;
    }

    /**
     *
     * @param amountInWord
     */
    public void setAmountInWord(String amountInWord) {
        this.amountInWord = amountInWord;
    }

    /**
     *
     * @return
     */
    public String getPreparedBy() {
        return preparedBy;
    }

    /**
     *
     * @param preparedBy
     */
    public void setPreparedBy(String preparedBy) {
        this.preparedBy = preparedBy;
    }

    /**
     *
     * @return
     */
    public String getChekedBy() {
        return chekedBy;
    }

    /**
     *
     * @param chekedBy
     */
    public void setChekedBy(String chekedBy) {
        this.chekedBy = chekedBy;
    }

    /**
     *
     * @return
     */
    public String getApprovedBy() {
        return approvedBy;
    }

    /**
     *
     * @param approvedBy
     */
    public void setApprovedBy(String approvedBy) {
        this.approvedBy = approvedBy;
    }

    /**
     *
     * @return
     */
    public Date getPreparedDate() {
        return preparedDate;
    }

    /**
     *
     * @param preparedDate
     */
    public void setPreparedDate(Date preparedDate) {
        this.preparedDate = preparedDate;
    }

    /**
     *
     * @return
     */
    public Date getChekedDate() {
        return chekedDate;
    }

    /**
     *
     * @param chekedDate
     */
    public void setChekedDate(Date chekedDate) {
        this.chekedDate = chekedDate;
    }

    /**
     *
     * @return
     */
    public Date getApprovedOn() {
        return approvedOn;
    }

    /**
     *
     * @param approvedOn
     */
    public void setApprovedOn(Date approvedOn) {
        this.approvedOn = approvedOn;
    }

    /**
     *
     * @return
     */
    public String getReferenceNumber() {
        return referenceNumber;
    }

    /**
     *
     * @param referenceNumber
     */
    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    /**
     *
     * @return
     */
    public Integer getStatus() {
        return status;
    }

    /**
     *
     * @param status
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     *
     * @return
     */
    public Integer getSuspensepaymentvocherID() {
        return suspensepaymentvocherID;
    }

    /**
     *
     * @param suspensepaymentvocherID
     */
    public void setSuspensepaymentvocherID(Integer suspensepaymentvocherID) {
        this.suspensepaymentvocherID = suspensepaymentvocherID;
    }

    /**
     *
     * @return
     */
    public FmsVoucher getFmsVOUCHERID() {
        return fmsVOUCHERID;
    }

    /**
     *
     * @param fmsVOUCHERID
     */
    public void setFmsVOUCHERID(FmsVoucher fmsVOUCHERID) {
        this.fmsVOUCHERID = fmsVOUCHERID;
    }

    /**
     *
     * @return
     */
    public String getPreparerRemark() {
        return preparerRemark;
    }

    /**
     *
     * @param preparerRemark
     */
    public void setPreparerRemark(String preparerRemark) {
        this.preparerRemark = preparerRemark;
    }

    
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (suspensepaymentvocherID != null ? suspensepaymentvocherID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FmsSuspensePaymentVocher)) {
            return false;
        }
        FmsSuspensePaymentVocher other = (FmsSuspensePaymentVocher) object;
        if ((this.suspensepaymentvocherID == null && other.suspensepaymentvocherID != null) || (this.suspensepaymentvocherID != null && !this.suspensepaymentvocherID.equals(other.suspensepaymentvocherID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.insa.erp.ibfms.entity.FmsSuspensePaymentVocher[ suspensepaymentvocherID=" + suspensepaymentvocherID + " ]";
    }

    /**
     *
     * @return
     */
    public String getSuspensePymtType() {
        return suspensePymtType;
    }

    /**
     *
     * @param suspensePymtType
     */
    public void setSuspensePymtType(String suspensePymtType) {
        this.suspensePymtType = suspensePymtType;
    }

    /**
     *
     * @return
     */
    public String getReceivedBy() {
        return receivedBy;
    }

    /**
     *
     * @param receivedBy
     */
    public void setReceivedBy(String receivedBy) {
        this.receivedBy = receivedBy;
    }

}
