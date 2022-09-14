/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.entity.Vocher;

import et.gov.eep.fcms.entity.FmsLuBankName;
import et.gov.eep.fcms.entity.FmsLuPaymentType;
import et.gov.eep.fcms.entity.FmsLuWithholdingRate;
import et.gov.eep.fcms.entity.budget.FmsBudgetControl;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
 * @author mora
 */
@Entity
@Table(name = "FMS_CHEQUE_PAYMENT_VOUCHER")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FmsChequePaymentVoucher.findAll", query = "SELECT f FROM FmsChequePaymentVoucher f"),
    @NamedQuery(name = "FmsChequePaymentVoucher.findByChequePaymentVoucherId", query = "SELECT f FROM FmsChequePaymentVoucher f WHERE f.chequePaymentVoucherId = :chequePaymentVoucherId"),
    @NamedQuery(name = "FmsChequePaymentVoucher.findByAmountInFigure", query = "SELECT f FROM FmsChequePaymentVoucher f WHERE f.amountInFigure = :amountInFigure"),
    @NamedQuery(name = "FmsChequePaymentVoucher.findByApprovedBy", query = "SELECT f FROM FmsChequePaymentVoucher f WHERE f.approvedBy = :approvedBy"),
    @NamedQuery(name = "FmsChequePaymentVoucher.findByApprovedDate", query = "SELECT f FROM FmsChequePaymentVoucher f WHERE f.approvedDate = :approvedDate"),
    @NamedQuery(name = "FmsChequePaymentVoucher.findByCertifiedBy", query = "SELECT f FROM FmsChequePaymentVoucher f WHERE f.certifiedBy = :certifiedBy"),
    @NamedQuery(name = "FmsChequePaymentVoucher.findByCertifiedDate", query = "SELECT f FROM FmsChequePaymentVoucher f WHERE f.certifiedDate = :certifiedDate"),
    @NamedQuery(name = "FmsChequePaymentVoucher.findByCheckedBy", query = "SELECT f FROM FmsChequePaymentVoucher f WHERE f.checkedBy = :checkedBy"),
    @NamedQuery(name = "FmsChequePaymentVoucher.findByCheckedDate", query = "SELECT f FROM FmsChequePaymentVoucher f WHERE f.checkedDate = :checkedDate"),
    @NamedQuery(name = "FmsChequePaymentVoucher.findByChequeNumber", query = "SELECT f FROM FmsChequePaymentVoucher f WHERE f.chequeNumber = :chequeNumber"),
    @NamedQuery(name = "FmsChequePaymentVoucher.findByPaidBy", query = "SELECT f FROM FmsChequePaymentVoucher f WHERE f.paidBy = :paidBy"),
    @NamedQuery(name = "FmsChequePaymentVoucher.findByPaidDate", query = "SELECT f FROM FmsChequePaymentVoucher f WHERE f.paidDate = :paidDate"),
    @NamedQuery(name = "FmsChequePaymentVoucher.findByPaidTo", query = "SELECT f FROM FmsChequePaymentVoucher f WHERE f.paidTo = :paidTo"),
    @NamedQuery(name = "FmsChequePaymentVoucher.findByPreparedBy", query = "SELECT f FROM FmsChequePaymentVoucher f WHERE f.preparedBy = :preparedBy"),
    @NamedQuery(name = "FmsChequePaymentVoucher.findByPreparedDate", query = "SELECT f FROM FmsChequePaymentVoucher f WHERE f.preparedDate = :preparedDate"),
    @NamedQuery(name = "FmsChequePaymentVoucher.findByReceivedBy", query = "SELECT f FROM FmsChequePaymentVoucher f WHERE f.receivedBy = :receivedBy"),
    @NamedQuery(name = "FmsChequePaymentVoucher.findByReferenceNumber", query = "SELECT f FROM FmsChequePaymentVoucher f WHERE f.referenceNumber = :referenceNumber"),
    @NamedQuery(name = "FmsChequePaymentVoucher.findByStatus", query = "SELECT f FROM FmsChequePaymentVoucher f WHERE f.status = :status"),
    @NamedQuery(name = "FmsChequePaymentVoucher.findByCertifiedRemark", query = "SELECT f FROM FmsChequePaymentVoucher f WHERE f.certifiedRemark = :certifiedRemark"),
    @NamedQuery(name = "FmsChequePaymentVoucher.findByPreparedRemark", query = "SELECT f FROM FmsChequePaymentVoucher f WHERE f.preparedRemark = :preparedRemark"),
    @NamedQuery(name = "FmsChequePaymentVoucher.findByApprovedRemark", query = "SELECT f FROM FmsChequePaymentVoucher f WHERE f.approvedRemark = :approvedRemark"),
    @NamedQuery(name = "FmsChequePaymentVoucher.findByCheckRemark", query = "SELECT f FROM FmsChequePaymentVoucher f WHERE f.checkRemark = :checkRemark"),
    @NamedQuery(name = "FmsChequePaymentVoucher.findByWithhold", query = "SELECT f FROM FmsChequePaymentVoucher f WHERE f.withhold = :withhold"),
    @NamedQuery(name = "FmsChequePaymentVoucher.findByVats", query = "SELECT f FROM FmsChequePaymentVoucher f WHERE f.vats = :vats"),
    @NamedQuery(name = "FmsChequePaymentVoucher.findByTotalValue", query = "SELECT f FROM FmsChequePaymentVoucher f WHERE f.totalValue = :totalValue"),
    @NamedQuery(name = "FmsChequePaymentVoucher.findByWithHoldStatus", query = "SELECT f FROM FmsChequePaymentVoucher f WHERE f.withHoldStatus = :withHoldStatus"),
    @NamedQuery(name = "FmsChequePaymentVoucher.findBySourceJeId", query = "SELECT f FROM FmsChequePaymentVoucher f WHERE f.sourceJeId = :sourceJeId"),
    @NamedQuery(name = "FmsChequePaymentVoucher.getByStatus", query = "SELECT f.fmsVOUCHERID FROM FmsChequePaymentVoucher f WHERE f.status = :status"),
    @NamedQuery(name = "FmsChequePaymentVoucher.getByStatusForCapitalbgt", query = "SELECT f.fmsVOUCHERID FROM FmsChequePaymentVoucher f WHERE f.status = :status and f.fmsVOUCHERID.purpose='capital budget'"),
    @NamedQuery(name = "FmsChequePaymentVoucher.getbyFmsvoucherbyStatus", query = "SELECT f.fmsVOUCHERID FROM FmsChequePaymentVoucher f WHERE f.status = :status"),
    @NamedQuery(name = "FmsChequePaymentVoucher.findByVOUCHERID", query = "SELECT f FROM FmsChequePaymentVoucher f WHERE f.fmsVOUCHERID = :fmsVOUCHERID"),
    @NamedQuery(name = "FmsChequePaymentVoucher.findByReferenceType", query = "SELECT f FROM FmsChequePaymentVoucher f WHERE f.referenceType = :referenceType")})
public class FmsChequePaymentVoucher implements Serializable {

    @OneToMany(mappedBy = "chequeVId")
    private List<FmsBudgetControl> fmsBudgetControlList;

    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FMS_CHEQUE_PAYMENT_VOUCHER_SEQ")
    @SequenceGenerator(name = "FMS_CHEQUE_PAYMENT_VOUCHER_SEQ", sequenceName = "FMS_CHEQUE_PAYMENT_VOUCHER_SEQ", allocationSize = 1)
    @Basic(optional = false)
    @Column(name = "CHEQUE_PAYMENT_VOUCHER_ID", nullable = false)
    private Long chequePaymentVoucherId;
    @Size(max = 50)
    @Column(name = "APPROVED_BY")
    private String approvedBy;
    @Column(name = "APPROVED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date approvedDate;
    @Size(max = 50)
    @Column(name = "CERTIFIED_BY")
    private String certifiedBy;
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
    @Column(name = "CHEQUE_NUMBER")
    private String chequeNumber;
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
    @Size(max = 100)
    @Column(name = "REFERENCE_NUMBER")
    private String referenceNumber;
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
    @Size(max = 20)
    @Column(name = "WITH_HOLD_STATUS")
    private String withHoldStatus;
    @Size(max = 20)
    @Column(name = "SOURCE_JE_ID")
    private String sourceJeId;
    @Size(max = 20)
    @Column(name = "REFERENCE_TYPE")
    private String referenceType;
    @Size(max = 500)
    @Column(name = "AMOUNT_IN_WORD", length = 500)
    private String amountInWord;
//    @Column(name = "VAT_IN_WORD", length = 500)
//    private String vatInWord;
//    @Column(name = "WITHOLD_IN_WORD", length = 500)
//    private String witholdInWord;
    @Column(name = "AMOUNT_IN_FIGURE")
    private BigDecimal amountInFigure;
    @JoinColumn(name = "LU_WITHHOLDING_RATE_ID", referencedColumnName = "LU_WITHHOLDING_RATE_ID")
    @ManyToOne
    private FmsLuWithholdingRate luWithholdingRateId;
    @JoinColumn(name = "LU_PAYMENT_TYPE_ID", referencedColumnName = "LU_PAYMENT_TYPE_ID")
    @ManyToOne
    private FmsLuPaymentType luPaymentTypeId;
    @JoinColumn(name = "LU_BANK_NAME_ID", referencedColumnName = "LU_BANK_NAME_ID")
    @ManyToOne
    private FmsLuBankName luBankNameId;
    @JoinColumn(name = "VOUCHER_ID", referencedColumnName = "VOUCHER_ID")
    @OneToOne(optional = false, cascade = CascadeType.ALL)
    private FmsVoucher fmsVOUCHERID;
//    @OneToMany(mappedBy = "chequePayemtID", cascade = CascadeType.PERSIST)
//    private List<FmsWithHoldingVoucher> fmsWithHoldingVouchersList;
//    @OneToMany(mappedBy = "chequePayemtID", cascade = CascadeType.PERSIST)
//    private List<FmsVatRecieptVoucher> fmsVatVouchersList;

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

    public FmsChequePaymentVoucher() {
    }

    public FmsLuWithholdingRate getLuWithholdingRateId() {
        return luWithholdingRateId;
    }

    public void setLuWithholdingRateId(FmsLuWithholdingRate luWithholdingRateId) {
        this.luWithholdingRateId = luWithholdingRateId;
    }

    public FmsLuPaymentType getLuPaymentTypeId() {
        if (luPaymentTypeId == null) {
            luPaymentTypeId = new FmsLuPaymentType();
        }
        return luPaymentTypeId;
    }

    public void setLuPaymentTypeId(FmsLuPaymentType luPaymentTypeId) {
        this.luPaymentTypeId = luPaymentTypeId;
    }

    public FmsLuBankName getLuBankNameId() {
        return luBankNameId;
    }

    public void setLuBankNameId(FmsLuBankName luBankNameId) {
        this.luBankNameId = luBankNameId;
    }

    public FmsChequePaymentVoucher(Long chequePaymentVoucherId) {
        this.chequePaymentVoucherId = chequePaymentVoucherId;
    }

    public Long getChequePaymentVoucherId() {
        return chequePaymentVoucherId;
    }

    public void setChequePaymentVoucherId(Long chequePaymentVoucherId) {
        this.chequePaymentVoucherId = chequePaymentVoucherId;
    }

    public String getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(String approvedBy) {
        this.approvedBy = approvedBy;
    }

    public Date getApprovedDate() {
        return approvedDate;
    }

    public void setApprovedDate(Date approvedDate) {
        this.approvedDate = approvedDate;
    }

    public String getCertifiedBy() {
        return certifiedBy;
    }

    public void setCertifiedBy(String certifiedBy) {
        this.certifiedBy = certifiedBy;
    }

    public Date getCertifiedDate() {
        return certifiedDate;
    }

    public void setCertifiedDate(Date certifiedDate) {
        this.certifiedDate = certifiedDate;
    }

    public String getCheckedBy() {
        return checkedBy;
    }

    public void setCheckedBy(String checkedBy) {
        this.checkedBy = checkedBy;
    }

    public Date getCheckedDate() {
        return checkedDate;
    }

    public void setCheckedDate(Date checkedDate) {
        this.checkedDate = checkedDate;
    }

    public String getChequeNumber() {
        return chequeNumber;
    }

    public void setChequeNumber(String chequeNumber) {
        this.chequeNumber = chequeNumber;
    }

    public String getPaidBy() {
        return paidBy;
    }

    public void setPaidBy(String paidBy) {
        this.paidBy = paidBy;
    }

    public Date getPaidDate() {
        return paidDate;
    }

    public void setPaidDate(Date paidDate) {
        this.paidDate = paidDate;
    }

    public String getPaidTo() {
        return paidTo;
    }

    public void setPaidTo(String paidTo) {
        this.paidTo = paidTo;
    }

    public String getPreparedBy() {
        return preparedBy;
    }

    public void setPreparedBy(String preparedBy) {
        this.preparedBy = preparedBy;
    }

    public Date getPreparedDate() {
        return preparedDate;
    }

    public void setPreparedDate(Date preparedDate) {
        this.preparedDate = preparedDate;
    }

    public String getReceivedBy() {
        return receivedBy;
    }

    public void setReceivedBy(String receivedBy) {
        this.receivedBy = receivedBy;
    }

    public String getReferenceNumber() {
        return referenceNumber;
    }

    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCertifiedRemark() {
        return certifiedRemark;
    }

    public void setCertifiedRemark(String certifiedRemark) {
        this.certifiedRemark = certifiedRemark;
    }

    public String getPreparedRemark() {
        return preparedRemark;
    }

    public void setPreparedRemark(String preparedRemark) {
        this.preparedRemark = preparedRemark;
    }

    public String getApprovedRemark() {
        return approvedRemark;
    }

    public void setApprovedRemark(String approvedRemark) {
        this.approvedRemark = approvedRemark;
    }

    public String getCheckRemark() {
        return checkRemark;
    }

    public void setCheckRemark(String checkRemark) {
        this.checkRemark = checkRemark;
    }

    public BigDecimal getWithhold() {
        return withhold;
    }

    public void setWithhold(BigDecimal withhold) {
        this.withhold = withhold;
    }

    public BigDecimal getVats() {
        return vats;
    }

    public void setVats(BigDecimal vats) {
        this.vats = vats;
    }

    public BigDecimal getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(BigDecimal totalValue) {
        this.totalValue = totalValue;
    }

    public String getWithHoldStatus() {
        return withHoldStatus;
    }

    public void setWithHoldStatus(String withHoldStatus) {
        this.withHoldStatus = withHoldStatus;
    }

    public String getSourceJeId() {
        return sourceJeId;
    }

    public void setSourceJeId(String sourceJeId) {
        this.sourceJeId = sourceJeId;
    }

    public String getReferenceType() {
        return referenceType;
    }

    public void setReferenceType(String referenceType) {
        this.referenceType = referenceType;
    }

    public FmsVoucher getVoucherId() {

        return fmsVOUCHERID;
    }

    /**
     *
     * @param voucherId
     */
    public void setVoucherId(FmsVoucher voucherId) {
        this.fmsVOUCHERID = voucherId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (chequePaymentVoucherId != null ? chequePaymentVoucherId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FmsChequePaymentVoucher)) {
            return false;
        }
        FmsChequePaymentVoucher other = (FmsChequePaymentVoucher) object;
        if ((this.chequePaymentVoucherId == null && other.chequePaymentVoucherId != null) || (this.chequePaymentVoucherId != null && !this.chequePaymentVoucherId.equals(other.chequePaymentVoucherId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return fmsVOUCHERID.getVoucherId();
    }

//    public void addToFmsWithHoldingDetail(FmsWithHoldingVoucher FmsWithHoldingDetail) {
//        if (FmsWithHoldingDetail.getChequePayemtID() != this) {
////            this.getFmsWithHoldingVoucherList().add(FmsWithHoldingDetail);
//            FmsWithHoldingDetail.setChequePayemtID(this);
//        }
//    }
//  public void addVatDetail(FmsVatRecieptVoucher FmsVatDetail) {
//        if (FmsVatDetail.getChequePayemtID() != this) {
////            this.getFmsVatVoucherList().add(FmsVatDetail);
//            FmsVatDetail.setChequePayemtID(this);
//        }
//    }
    public BigDecimal getAmountInFigure() {
        return amountInFigure;
    }

    public void setAmountInFigure(BigDecimal amountInFigure) {
        this.amountInFigure = amountInFigure;
    }

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

//    public String getVatInWord() {
//        return vatInWord;
//    }
//
//    public void setVatInWord(String vatInWord) {
//        this.vatInWord = vatInWord;
//    }
//
//    public String getWitholdInWord() {
//        return witholdInWord;
//    }
//
//    public void setWitholdInWord(String witholdInWord) {
//        this.witholdInWord = witholdInWord;
//    }
    @XmlTransient
    public List<FmsBudgetControl> getFmsBudgetControlList() {
        return fmsBudgetControlList;
    }

    public void setFmsBudgetControlList(List<FmsBudgetControl> fmsBudgetControlList) {
        this.fmsBudgetControlList = fmsBudgetControlList;
    }

//    @XmlTransient
//
//    public List<FmsWithHoldingVoucher> getFmsWithHoldingVouchersList() {
//        if (fmsWithHoldingVouchersList == null) {
//            fmsWithHoldingVouchersList = new ArrayList<>();
//        }
//        return fmsWithHoldingVouchersList;
//    }
//
//    public void setFmsWithHoldingVouchersList(List<FmsWithHoldingVoucher> fmsWithHoldingVouchersList) {
//        this.fmsWithHoldingVouchersList = fmsWithHoldingVouchersList;
//    }
//    @XmlTransient
//
//    public List<FmsVatVoucher> getFmsVatVouchersList(){
//
//    
//        if (fmsVatVouchersList == null) {
//            fmsVatVouchersList = new ArrayList<>();
//        }
//        return fmsVatVouchersList;
//    }
//
//    public void setFmsVatVouchersList(List<FmsVatVoucher> fmsVatVouchersList) {
//        this.fmsVatVouchersList = fmsVatVouchersList;
//    }
}
