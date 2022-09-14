/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.entity.Vocher;

import et.gov.eep.fcms.entity.pettyCash.FmsDailyCashRegister;
import et.gov.eep.fcms.entity.budget.FmsBudgetControl;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author mora
 */
@Entity
@Table(name = "FMS_CASH_PAYMENT_ORDER")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FmsCashPaymentOrder.findAll", query = "SELECT f FROM FmsCashPaymentOrder f"),
    @NamedQuery(name = "FmsCashPaymentOrder.findByCashPaymentOrderId", query = "SELECT f FROM FmsCashPaymentOrder f WHERE f.cashPaymentOrderId = :cashPaymentOrderId"),
    @NamedQuery(name = "FmsCashPaymentOrder.findByAmountInFigure", query = "SELECT f FROM FmsCashPaymentOrder f WHERE f.amountInFigure = :amountInFigure"),
    @NamedQuery(name = "FmsCashPaymentOrder.findByAmountInWord", query = "SELECT f FROM FmsCashPaymentOrder f WHERE f.amountInWord = :amountInWord"),
    @NamedQuery(name = "FmsCashPaymentOrder.findByApprovedBy", query = "SELECT f FROM FmsCashPaymentOrder f WHERE f.approvedBy = :approvedBy"),
    @NamedQuery(name = "FmsCashPaymentOrder.findByApprovedDate", query = "SELECT f FROM FmsCashPaymentOrder f WHERE f.approvedDate = :approvedDate"),
    @NamedQuery(name = "FmsCashPaymentOrder.findByBanckAccountNumber", query = "SELECT f FROM FmsCashPaymentOrder f WHERE f.banckAccountNumber = :banckAccountNumber"),
    @NamedQuery(name = "FmsCashPaymentOrder.findByCertifiedBy", query = "SELECT f FROM FmsCashPaymentOrder f WHERE f.certifiedBy = :certifiedBy"),
    @NamedQuery(name = "FmsCashPaymentOrder.findByCertifiedDate", query = "SELECT f FROM FmsCashPaymentOrder f WHERE f.certifiedDate = :certifiedDate"),
    @NamedQuery(name = "FmsCashPaymentOrder.findByCheckedBy", query = "SELECT f FROM FmsCashPaymentOrder f WHERE f.checkedBy = :checkedBy"),
    @NamedQuery(name = "FmsCashPaymentOrder.findByCheckedDate", query = "SELECT f FROM FmsCashPaymentOrder f WHERE f.checkedDate = :checkedDate"),
    @NamedQuery(name = "FmsCashPaymentOrder.findByCpoNumber", query = "SELECT f FROM FmsCashPaymentOrder f WHERE f.cpoNumber = :cpoNumber"),
    @NamedQuery(name = "FmsCashPaymentOrder.findByPaidBy", query = "SELECT f FROM FmsCashPaymentOrder f WHERE f.paidBy = :paidBy"),
    @NamedQuery(name = "FmsCashPaymentOrder.findByPaidDate", query = "SELECT f FROM FmsCashPaymentOrder f WHERE f.paidDate = :paidDate"),
    @NamedQuery(name = "FmsCashPaymentOrder.findByPaidTo", query = "SELECT f FROM FmsCashPaymentOrder f WHERE f.paidTo = :paidTo"),
    @NamedQuery(name = "FmsCashPaymentOrder.findByPreparedBy", query = "SELECT f FROM FmsCashPaymentOrder f WHERE f.preparedBy = :preparedBy"),
    @NamedQuery(name = "FmsCashPaymentOrder.findByPreparedDate", query = "SELECT f FROM FmsCashPaymentOrder f WHERE f.preparedDate = :preparedDate"),
    @NamedQuery(name = "FmsCashPaymentOrder.findByReceivedBy", query = "SELECT f FROM FmsCashPaymentOrder f WHERE f.receivedBy = :receivedBy"),
    @NamedQuery(name = "FmsCashPaymentOrder.findByStatus", query = "SELECT f FROM FmsCashPaymentOrder f WHERE f.status = :status"),
    @NamedQuery(name = "FmsCashPaymentOrder.findByDailyCashStatus", query = "SELECT f FROM FmsCashPaymentOrder f WHERE f.dailCashStatus = :dailCashStatus"),
    @NamedQuery(name = "FmsCashPaymentOrder.findByCertifiedRemark", query = "SELECT f FROM FmsCashPaymentOrder f WHERE f.certifiedRemark = :certifiedRemark"),
    @NamedQuery(name = "FmsCashPaymentOrder.findByPreparedRemark", query = "SELECT f FROM FmsCashPaymentOrder f WHERE f.preparedRemark = :preparedRemark"),
    @NamedQuery(name = "FmsCashPaymentOrder.findByApprovedRemark", query = "SELECT f FROM FmsCashPaymentOrder f WHERE f.approvedRemark = :approvedRemark"),
    @NamedQuery(name = "FmsCashPaymentOrder.findByCheckRemark", query = "SELECT f FROM FmsCashPaymentOrder f WHERE f.checkRemark = :checkRemark"),
    @NamedQuery(name = "FmsCashPaymentOrder.findByWithhold", query = "SELECT f FROM FmsCashPaymentOrder f WHERE f.withhold = :withhold"),
    @NamedQuery(name = "FmsCashPaymentOrder.findByVats", query = "SELECT f FROM FmsCashPaymentOrder f WHERE f.vats = :vats"),
    @NamedQuery(name = "FmsCashPaymentOrder.findByVoucherId", query = "SELECT f FROM FmsCashPaymentOrder f WHERE f.fmsVOUCHERID = :fmsVOUCHERID"),
    @NamedQuery(name = "FmsCashPaymentOrder.findByTotalValue", query = "SELECT f FROM FmsCashPaymentOrder f WHERE f.totalValue = :totalValue")})
public class FmsCashPaymentOrder implements Serializable {
    @Column(name = "DAIL_CASH_STATUS")
    private Integer dailCashStatus;
    @OneToMany(mappedBy = "cashVId")
    private List<FmsBudgetControl> fmsBudgetControlList;

    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FMS_CASH_PAYMENT_ORDER_SEQ")
    @SequenceGenerator(name = "FMS_CASH_PAYMENT_ORDER_SEQ", sequenceName = "FMS_CASH_PAYMENT_ORDER_SEQ", allocationSize = 1)
    @Basic(optional = false)
    @NotNull
    @Column(name = "CASH_PAYMENT_ORDER_ID")
    private Long cashPaymentOrderId;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "AMOUNT_IN_FIGURE")
    private BigDecimal amountInFigure;
    @Size(max = 500)
    @Column(name = "AMOUNT_IN_WORD")
    private String amountInWord;
    @Size(max = 50)
    @Column(name = "APPROVED_BY")
    private String approvedBy;
    @Column(name = "APPROVED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date approvedDate;
    @Size(max = 50)
    @Column(name = "BANCK_ACCOUNT_NUMBER")
    private String banckAccountNumber;
    @Size(max = 50)
    @Column(name = "CERTIFIED_BY")
    private String certifiedBy;
    @Column(name = "REFERENCE_NO")
    @Size(max = 100)
    private String referenceNo;
    @Column(name = "CERTIFIED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date certifiedDate;
    @Size(max = 50)
    @Column(name = "CHECKED_BY")
    private String checkedBy;
    @Column(name = "CHECKED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date checkedDate;
    @Size(max = 50)
    @Column(name = "CPO_NUMBER")
    private String cpoNumber;
    @Size(max = 50)
    @Column(name = "PAID_BY")
    private String paidBy;
    @Column(name = "PAID_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date paidDate;
    @Size(max = 50)
    @Column(name = "PAID_TO")
    private String paidTo;
    @Size(max = 50)
    @Column(name = "PREPARED_BY")
    private String preparedBy;
    @Column(name = "PREPARED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date preparedDate;
    @Size(max = 50)
    @Column(name = "RECEIVED_BY")
    private String receivedBy;
    @Size(max = 20)
    @Column(name = "STATUS")
    private String status;
    @Size(max = 500)
    @Column(name = "CERTIFIED_REMARK")
    private String certifiedRemark;
    @Size(max = 500)
    @Column(name = "PREPARED_REMARK")
    private String preparedRemark;
    @Size(max = 500)
    @Column(name = "APPROVED_REMARK")
    private String approvedRemark;
    @Size(max = 500)
    @Column(name = "CHECK_REMARK")
    private String checkRemark;
    @Column(name = "WITHHOLD")
    private BigDecimal withhold;
    @Column(name = "VATS")
    private BigDecimal vats;
    @Column(name = "TOTAL_VALUE")
    private BigDecimal totalValue;
    @JoinColumn(name = "VOUCHER_ID", referencedColumnName = "VOUCHER_ID")
    @OneToOne(optional = false)
    private FmsVoucher fmsVOUCHERID;
    @OneToOne(mappedBy = "pettyCashId")
    private FmsDailyCashRegister fmsDailyCashRegisterList;
    
    
    /**
     *
     */
    public FmsCashPaymentOrder() {
    }

    /**
     *
     * @param cashPaymentOrderId
     */
    public FmsCashPaymentOrder(Long cashPaymentOrderId) {
        this.cashPaymentOrderId = cashPaymentOrderId;
    }

    /**
     *
     * @return
     */
    
    
    
    public Long getCashPaymentOrderId() {
        return cashPaymentOrderId;
    }

    /**
     *
     * @param cashPaymentOrderId
     */
    public void setCashPaymentOrderId(Long cashPaymentOrderId) {
        this.cashPaymentOrderId = cashPaymentOrderId;
    }

    /**
     *
     * @return
     */
    public BigDecimal getAmountInFigure() {
        return amountInFigure;
    }

    /**
     *
     * @param amountInFigure
     */
    public void setAmountInFigure(BigDecimal amountInFigure) {
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
    public String getBanckAccountNumber() {
        return banckAccountNumber;
    }

    /**
     *
     * @param banckAccountNumber
     */
    public void setBanckAccountNumber(String banckAccountNumber) {
        this.banckAccountNumber = banckAccountNumber;
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
    public String getCheckedBy() {
        return checkedBy;
    }

    /**
     *
     * @param checkedBy
     */
    public void setCheckedBy(String checkedBy) {
        this.checkedBy = checkedBy;
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
    public String getCpoNumber() {
        return cpoNumber;
    }

    /**
     *
     * @param cpoNumber
     */
    public void setCpoNumber(String cpoNumber) {
        this.cpoNumber = cpoNumber;
    }

    /**
     *
     * @return
     */
    public String getPaidBy() {
        return paidBy;
    }

    /**
     *
     * @param paidBy
     */
    public void setPaidBy(String paidBy) {
        this.paidBy = paidBy;
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

    public Integer getDailCashStatus() {
        return dailCashStatus;
    }

    public void setDailCashStatus(Integer dailCashStatus) {
        this.dailCashStatus = dailCashStatus;
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
     * @return
     */
    public BigDecimal getWithhold() {
        return withhold;
    }

    /**
     *
     * @param withhold
     */
    public void setWithhold(BigDecimal withhold) {
        this.withhold = withhold;
    }

    /**
     *
     * @return
     */
    public BigDecimal getVats() {
        return vats;
    }

    /**
     *
     * @param vats
     */
    public void setVats(BigDecimal vats) {
        this.vats = vats;
    }

    /**
     *
     * @return
     */
    public BigDecimal getTotalValue() {
        return totalValue;
    }

    /**
     *
     * @param totalValue
     */
    public void setTotalValue(BigDecimal totalValue) {
        this.totalValue = totalValue;
    }

    public FmsVoucher getFmsVOUCHERID() {
        return fmsVOUCHERID;
    }

    public void setFmsVOUCHERID(FmsVoucher fmsVOUCHERID) {
        this.fmsVOUCHERID = fmsVOUCHERID;
    }

    public String getReferenceNo() {
        return referenceNo;
    }

    public void setReferenceNo(String referenceNo) {
        this.referenceNo = referenceNo;
    }

    public FmsDailyCashRegister getFmsDailyCashRegisterList() {
        return fmsDailyCashRegisterList;
    }

    /**
     *
     * @param fmsDailyCashRegisterList
     */
    public void setFmsDailyCashRegisterList(FmsDailyCashRegister fmsDailyCashRegisterList) {
        this.fmsDailyCashRegisterList = fmsDailyCashRegisterList;
    }


    @Override
    public String toString() {
        return fmsVOUCHERID.getVoucherId();
    }

    @XmlTransient
    public List<FmsBudgetControl> getFmsBudgetControlList() {
        return fmsBudgetControlList;
    }

    public void setFmsBudgetControlList(List<FmsBudgetControl> fmsBudgetControlList) {
        this.fmsBudgetControlList = fmsBudgetControlList;
    }
}
