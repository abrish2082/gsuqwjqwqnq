/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.entity;

import et.gov.eep.fcms.entity.Vocher.FmsVoucher;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author AB
 */
@Entity
@Table(name = "FMS_PETTY_CASH_PAYMENT_VOUCHER")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FmsPettyCashPaymentVoucher.findAll", query = "SELECT f FROM FmsPettyCashPaymentVoucher f"),
    @NamedQuery(name = "FmsPettyCashPaymentVoucher.findByVoucherNumber", query = "SELECT f FROM FmsPettyCashPaymentVoucher f WHERE f.voucherNumber = :voucherNumber"),
    @NamedQuery(name = "FmsPettyCashPaymentVoucher.findBySourceJeId", query = "SELECT f FROM FmsPettyCashPaymentVoucher f WHERE f.sourceJeId = :sourceJeId"),
    @NamedQuery(name = "FmsPettyCashPaymentVoucher.findByCashierId", query = "SELECT f FROM FmsPettyCashPaymentVoucher f WHERE f.cashierId = :cashierId"),
    @NamedQuery(name = "FmsPettyCashPaymentVoucher.findByPaidTo", query = "SELECT f FROM FmsPettyCashPaymentVoucher f WHERE f.paidTo = :paidTo"),
    @NamedQuery(name = "FmsPettyCashPaymentVoucher.findByAmountInWord", query = "SELECT f FROM FmsPettyCashPaymentVoucher f WHERE f.amountInWord = :amountInWord"),
    @NamedQuery(name = "FmsPettyCashPaymentVoucher.findByAmountInFigure", query = "SELECT f FROM FmsPettyCashPaymentVoucher f WHERE f.amountInFigure = :amountInFigure"),
    @NamedQuery(name = "FmsPettyCashPaymentVoucher.findByPreparedBy", query = "SELECT f FROM FmsPettyCashPaymentVoucher f WHERE f.preparedBy = :preparedBy"),
    @NamedQuery(name = "FmsPettyCashPaymentVoucher.findByChekedBy", query = "SELECT f FROM FmsPettyCashPaymentVoucher f WHERE f.chekedBy = :chekedBy"),
    @NamedQuery(name = "FmsPettyCashPaymentVoucher.findByCertifiedBy", query = "SELECT f FROM FmsPettyCashPaymentVoucher f WHERE f.certifiedBy = :certifiedBy"),
    @NamedQuery(name = "FmsPettyCashPaymentVoucher.findByApprovedBy", query = "SELECT f FROM FmsPettyCashPaymentVoucher f WHERE f.approvedBy = :approvedBy"),
    @NamedQuery(name = "FmsPettyCashPaymentVoucher.findByPreparedDate", query = "SELECT f FROM FmsPettyCashPaymentVoucher f WHERE f.preparedDate = :preparedDate"),
    @NamedQuery(name = "FmsPettyCashPaymentVoucher.findByCheckedDate", query = "SELECT f FROM FmsPettyCashPaymentVoucher f WHERE f.checkedDate = :checkedDate"),
    @NamedQuery(name = "FmsPettyCashPaymentVoucher.findByCertifiedDate", query = "SELECT f FROM FmsPettyCashPaymentVoucher f WHERE f.certifiedDate = :certifiedDate"),
    @NamedQuery(name = "FmsPettyCashPaymentVoucher.findByApprovedDate", query = "SELECT f FROM FmsPettyCashPaymentVoucher f WHERE f.approvedDate = :approvedDate"),
    @NamedQuery(name = "FmsPettyCashPaymentVoucher.findByPaidDate", query = "SELECT f FROM FmsPettyCashPaymentVoucher f WHERE f.paidDate = :paidDate"),
    @NamedQuery(name = "FmsPettyCashPaymentVoucher.findByReferenceNumber", query = "SELECT f FROM FmsPettyCashPaymentVoucher f WHERE f.referenceNumber = :referenceNumber"),

    @NamedQuery(name = "FmsPettyCashPaymentVoucher.getByStatus", query = "SELECT f.fmsVOUCHERID FROM FmsPettyCashPaymentVoucher f WHERE f.fmsVOUCHERID=:fmsVOUCHERID and f.status = :status"),

    @NamedQuery(name = "FmsPettyCashPaymentVoucher.findByStatus", query = "SELECT f.fmsVOUCHERID FROM FmsPettyCashPaymentVoucher f WHERE f.status = :status and f.fmsVOUCHERID.purpose='operating budget'"),
    @NamedQuery(name = "FmsPettyCashPaymentVoucher.findByStatusForCapoitalBgt", query = "SELECT f.fmsVOUCHERID FROM FmsPettyCashPaymentVoucher f WHERE f.status = :status and f.fmsVOUCHERID.purpose='capital budget'"),
    @NamedQuery(name = "FmsPettyCashPaymentVoucher.findByVOUCHERID", query = "SELECT f FROM FmsPettyCashPaymentVoucher f WHERE f.fmsVOUCHERID = :fmsVOUCHERID "),
    @NamedQuery(name = "FmsPettyCashPaymentVoucher.findByPettycashpaymentvoucherID", query = "SELECT f FROM FmsPettyCashPaymentVoucher f WHERE f.pettycashpaymentvoucherID = :pettycashpaymentvoucherID")})
public class FmsPettyCashPaymentVoucher implements Serializable {

    private static final long serialVersionUID = 1L;
    @Size(max = 50)
    @Column(name = "VOUCHER_NUMBER", length = 50)
    private String voucherNumber;
    @Size(max = 50)
    @Column(name = "SOURCE_JE_ID", length = 50)
    private String sourceJeId;
    @Size(max = 50)
    @Column(name = "CASHIER_ID", length = 50)
    private String cashierId;
    @Size(max = 50)
    @Column(name = "PAID_TO", length = 50)
    private String paidTo;
    @Size(max = 500)
    @Column(name = "AMOUNT_IN_WORD", length = 500)
    private String amountInWord;
    @Column(name = "AMOUNT_IN_FIGURE")
    private Long amountInFigure;
    @Size(max = 50)
    @Column(name = "PREPARED_BY", length = 50)
    private String preparedBy;
    @Size(max = 50)
    @Column(name = "CHEKED_BY", length = 50)
    private String chekedBy;
    @Size(max = 50)
    @Column(name = "CERTIFIED_BY", length = 50)
    private String certifiedBy;
    @Size(max = 50)
    @Column(name = "APPROVED_BY", length = 50)
    private String approvedBy;
    @Column(name = "PREPARED_DATE")
    @Temporal(TemporalType.DATE)
    private Date preparedDate;
    @Column(name = "CHECKED_DATE")
    @Temporal(TemporalType.DATE)
    private Date checkedDate;
    @Column(name = "CERTIFIED_DATE")
    @Temporal(TemporalType.DATE)
    private Date certifiedDate;
    @Column(name = "APPROVED_DATE")
    @Temporal(TemporalType.DATE)
    private Date approvedDate;
    @Column(name = "PAID_DATE")
    @Temporal(TemporalType.DATE)
    private Date paidDate;
    @Size(max = 30)
    @Column(name = "REFERENCE_NUMBER", length = 30)
    private String referenceNumber;
    @Column(name = "STATUS")
    private String status;
    @Column(name = "DAIL_CASH_STATUS")
    private Integer dailyCashStatus;
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FMS_PETTY_CASH_PAYMENT_VOU_SEQ")
    @SequenceGenerator(name = "FMS_PETTY_CASH_PAYMENT_VOU_SEQ", sequenceName = "FMS_PETTY_CASH_PAYMENT_VOU_SEQ", allocationSize = 1)
    @Column(name = "petty_cash_payment_voucher_ID", nullable = false)
    private Integer pettycashpaymentvoucherID;

    @JoinColumn(name = "FMS_VOUCHER_VOUCHER_ID", referencedColumnName = "VOUCHER_ID")
    @OneToOne(optional = false)
    private FmsVoucher fmsVOUCHERID;

//     @OneToMany(mappedBy = "pettyCashId")
//    private List<FmsDailyCashRegister> fmsDailyCashRegisterList;
//     
//    @OneToOne(cascade = CascadeType.ALL, mappedBy = "pettyCashId")
//    private FmsDailyCashRegister fmsDailyCashRegisterList;
    @OneToMany(mappedBy = "pettyChequeId")
    private List<FmsPettyCheqPaymControl> fmsPettyCheqPaymControlList;
    @Size(max = 500)
    @Column(name = "CERTIFIED_REMARK", length = 500)
    private String certifiedRemark;
    @Size(max = 500)
    @Column(name = "PREPARED_REMARK", length = 500)
    private String preparedRemark;
    @Size(max = 500)
    @Column(name = "APPROVED_REMARK", length = 500)
    private String approvedRemark;
    @Size(max = 500)
    @Column(name = "CHECK_REMARK", length = 500)
    private String checkRemark;

    /**
     *
     */
    public FmsPettyCashPaymentVoucher() {
    }

    /**
     *
     * @return
     */
    public FmsVoucher getFmsVOUCHERID() {
        if (fmsVOUCHERID == null) {
            fmsVOUCHERID = new FmsVoucher();
        }
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
    public String getCertifiedRemark() {
        return certifiedRemark;
    }

    /**
     *
     * @param certifiedRemark
     */
    public void setCertifiedRemark(String certifiedRemark) {
        this.certifiedRemark = certifiedRemark;
    }

    /**
     *
     * @return
     */
    public String getPreparedRemark() {
        return preparedRemark;
    }

    /**
     *
     * @param preparedRemark
     */
    public void setPreparedRemark(String preparedRemark) {
        this.preparedRemark = preparedRemark;
    }

    /**
     *
     * @return
     */
    public String getApprovedRemark() {
        return approvedRemark;
    }

    /**
     *
     * @param approvedRemark
     */
    public void setApprovedRemark(String approvedRemark) {
        this.approvedRemark = approvedRemark;
    }

    /**
     *
     * @return
     */
    public String getCheckRemark() {
        return checkRemark;
    }

    /**
     *
     * @param checkRemark
     */
    public void setCheckRemark(String checkRemark) {
        this.checkRemark = checkRemark;
    }

    /**
     *
     * @param pettycashpaymentvoucherID
     */
    public FmsPettyCashPaymentVoucher(Integer pettycashpaymentvoucherID) {
        this.pettycashpaymentvoucherID = pettycashpaymentvoucherID;
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
    public String getCertifiedBy() {
        return certifiedBy;
    }

    /**
     *
     * @param certifiedBy
     */
    public void setCertifiedBy(String certifiedBy) {
        this.certifiedBy = certifiedBy;
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
    public Date getCheckedDate() {
        return checkedDate;
    }

    /**
     *
     * @param checkedDate
     */
    public void setCheckedDate(Date checkedDate) {
        this.checkedDate = checkedDate;
    }

    /**
     *
     * @return
     */
    public Date getCertifiedDate() {
        return certifiedDate;
    }

    /**
     *
     * @param certifiedDate
     */
    public void setCertifiedDate(Date certifiedDate) {
        this.certifiedDate = certifiedDate;
    }

    /**
     *
     * @return
     */
    public Date getApprovedDate() {
        return approvedDate;
    }

    /**
     *
     * @param approvedDate
     */
    public void setApprovedDate(Date approvedDate) {
        this.approvedDate = approvedDate;
    }

    /**
     *
     * @return
     */
    public Date getPaidDate() {
        return paidDate;
    }

    /**
     *
     * @param paidDate
     */
    public void setPaidDate(Date paidDate) {
        this.paidDate = paidDate;
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
    public String getStatus() {
        return status;
    }

    /**
     *
     * @param status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     *
     * @return
     */
    public Integer getPettycashpaymentvoucherID() {
        return pettycashpaymentvoucherID;
    }

    /**
     *
     * @param pettycashpaymentvoucherID
     */
    public void setPettycashpaymentvoucherID(Integer pettycashpaymentvoucherID) {
        this.pettycashpaymentvoucherID = pettycashpaymentvoucherID;
    }

    /**
     *
     * @return
     */
    public FmsVoucher getFmsvoucherVOUCHERID() {
        return fmsVOUCHERID;
    }

    /**
     *
     * @param fmsVOUCHERIDx
     */
    public void setFmsvoucherVOUCHERID(FmsVoucher fmsVOUCHERIDx) {
        this.fmsVOUCHERID = fmsVOUCHERIDx;
    }

    /**
     * @return the dailyCashStatus
     */
    public Integer getDailyCashStatus() {
        return dailyCashStatus;
    }

    /**
     * @param dailyCashStatus the dailyCashStatus to set
     */
    public void setDailyCashStatus(Integer dailyCashStatus) {
        this.dailyCashStatus = dailyCashStatus;
    }

    @XmlTransient
    public List<FmsPettyCheqPaymControl> getFmsPettyCheqPaymControlList() {
        return fmsPettyCheqPaymControlList;
    }

    /**
     *
     * @param fmsPettyCheqPaymControlList
     */
    public void setFmsPettyCheqPaymControlList(List<FmsPettyCheqPaymControl> fmsPettyCheqPaymControlList) {
        this.fmsPettyCheqPaymControlList = fmsPettyCheqPaymControlList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pettycashpaymentvoucherID != null ? pettycashpaymentvoucherID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FmsPettyCashPaymentVoucher)) {
            return false;
        }
        FmsPettyCashPaymentVoucher other = (FmsPettyCashPaymentVoucher) object;
        if ((this.pettycashpaymentvoucherID == null && other.pettycashpaymentvoucherID != null) || (this.pettycashpaymentvoucherID != null && !this.pettycashpaymentvoucherID.equals(other.pettycashpaymentvoucherID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return fmsVOUCHERID.getVoucherId();
    }

}
