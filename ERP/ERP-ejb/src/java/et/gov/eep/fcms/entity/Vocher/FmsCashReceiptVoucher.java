/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.entity.Vocher;

import et.gov.eep.fcms.entity.FmsLuPaymentType;
import et.gov.eep.hrms.entity.medical.HrLocalMedSettlements;
import et.gov.eep.prms.entity.PrmsBid;
import et.gov.eep.prms.entity.PrmsBidderRegDetail;
import et.gov.eep.prms.entity.PrmsSupplyProfile;
import et.gov.eep.prms.entity.PrmsLuVatTypeLookup;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.FetchType;
import javax.persistence.FieldResult;
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
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import et.gov.eep.prms.entity.PrmsSupplyProfile;
import et.gov.eep.prms.entity.PrmsBid;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author AB
 */
@Entity
@Table(name = "fms_cash_receipt_voucher")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FmsCashReceiptVoucher.findAll", query = "SELECT f FROM FmsCashReceiptVoucher f"),
    @NamedQuery(name = "FmsCashReceiptVoucher.findByVoucherNumber", query = "SELECT f FROM FmsCashReceiptVoucher f WHERE f.voucherNumber = :voucherNumber"),
    @NamedQuery(name = "FmsCashReceiptVoucher.findBySourceJeId", query = "SELECT f FROM FmsCashReceiptVoucher f WHERE f.sourceJeId = :sourceJeId"),
    @NamedQuery(name = "FmsCashReceiptVoucher.findByPreparedDate", query = "SELECT f FROM FmsCashReceiptVoucher f WHERE f.preparedDate = :preparedDate"),
    @NamedQuery(name = "FmsCashReceiptVoucher.findByChequeNumber", query = "SELECT f FROM FmsCashReceiptVoucher f WHERE f.chequeNumber = :chequeNumber"),
    @NamedQuery(name = "FmsCashReceiptVoucher.findByCashierId", query = "SELECT f FROM FmsCashReceiptVoucher f WHERE f.cashierId = :cashierId"),
    @NamedQuery(name = "FmsCashReceiptVoucher.findByAmountInFigure", query = "SELECT f FROM FmsCashReceiptVoucher f WHERE f.amountInFigure = :amountInFigure"),
    @NamedQuery(name = "FmsCashReceiptVoucher.findByAmountInWord", query = "SELECT f FROM FmsCashReceiptVoucher f WHERE f.amountInWord = :amountInWord"),
    @NamedQuery(name = "FmsCashReceiptVoucher.findByEmployeeId", query = "SELECT f FROM FmsCashReceiptVoucher f WHERE f.employeeId = :employeeId"),
    @NamedQuery(name = "FmsCashReceiptVoucher.findByCustomerId", query = "SELECT f FROM FmsCashReceiptVoucher f WHERE f.customerId = :customerId"),
    @NamedQuery(name = "FmsCashReceiptVoucher.findByOhterPayer", query = "SELECT f FROM FmsCashReceiptVoucher f WHERE f.ohterPayer = :ohterPayer"),
    @NamedQuery(name = "FmsCashReceiptVoucher.findByCategory", query = "SELECT f FROM FmsCashReceiptVoucher f WHERE f.category = :category"),
    @NamedQuery(name = "FmsCashReceiptVoucher.findByInvoiceNumber", query = "SELECT f FROM FmsCashReceiptVoucher f WHERE f.invoiceNumber = :invoiceNumber"),
    @NamedQuery(name = "FmsCashReceiptVoucher.findByPaymentType", query = "SELECT f FROM FmsCashReceiptVoucher f WHERE f.paymentType = :paymentType"),
    @NamedQuery(name = "FmsCashReceiptVoucher.findByReferenceNumber", query = "SELECT f FROM FmsCashReceiptVoucher f WHERE f.referenceNumber = :referenceNumber"),
    @NamedQuery(name = "FmsCashReceiptVoucher.findByStatus", query = "SELECT f.fmsVOUCHERID FROM FmsCashReceiptVoucher f WHERE f.status = :status"),
    @NamedQuery(name = "FmsCashReceiptVoucher.findByVOUCHERID", query = "SELECT f FROM FmsCashReceiptVoucher f WHERE f.fmsVOUCHERID = :voucherId"),
    @NamedQuery(name = "FmsCashReceiptVoucher.findByCashreceiptvoucherID", query = "SELECT f FROM FmsCashReceiptVoucher f WHERE f.cashreceiptvoucherID = :cashreceiptvoucherID")})
@SqlResultSetMapping(name = "OrderResultsCash",
        entities = {
            @EntityResult(entityClass = FmsCashReceiptVoucher.class, fields = {
                @FieldResult(name = "cashReceiptVoucherId", column = "crvIds"),
                @FieldResult(name = "chequeNumber", column = "cheqNos"),
                @FieldResult(name = "fmsVoucherVoucherId", column = "vouIds"),
                @FieldResult(name = "amountInFigure", column = "amounts"),})})
public class FmsCashReceiptVoucher implements Serializable {

    private static final long serialVersionUID = 1L;
    @Size(max = 18)
    @Column(name = "VOUCHER_NUMBER", length = 18)
    private String voucherNumber;
    @Size(max = 50)
    @Column(name = "SOURCE_JE_ID", length = 50)
    private String sourceJeId;
    @Size(max = 40)
    @Column(name = "CHEQUE_NUMBER", length = 40)
    private String chequeNumber;
    @Size(max = 20)
    @Column(name = "CASHIER_ID", length = 20)
    private String cashierId;
    @Column(name = "AMOUNT_IN_FIGURE")
    private double amountInFigure;
    @Size(max = 500)
    @Column(name = "AMOUNT_IN_WORD", length = 500)
    private String amountInWord;
    @Size(max = 20)
    @Column(name = "CITY", length = 500)
    private String city;
    @Size(max = 20)
    @Column(name = "REGION", length = 500)
    private String region;
    @Size(max = 20)
    @Column(name = "EMPLOYEE_ID", length = 20)
    private String employeeId;
    @Size(max = 20)
    @Column(name = "TIN", length = 20)
    private String tin;
    @Size(max = 20)
    @Column(name = "CUSTOMER_ID", length = 20)
    private String customerId;
    @Size(max = 50)
    @Column(name = "OHTER_PAYER", length = 50)
    private String ohterPayer;
    @Size(max = 20)
    @Column(length = 20)
    private String category;
    @Size(max = 30)
    @Column(name = "INVOICE_NUMBER", length = 30)
    private String invoiceNumber;
//    @Size(max = 30)
//    @Column(name = "PAYMENT_TYPE", length = 30)
//    private String paymentType;

    @Size(max = 30)
    @Column(name = "REFERENCE_NUMBER", length = 30)
    private String referenceNumber;
    @Column(name = "STATUS_")
    private Integer status;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FMS_CASH_RECEIPT_VOUCHER_C_SEQ")
    @SequenceGenerator(name = "FMS_CASH_RECEIPT_VOUCHER_C_SEQ", sequenceName = "FMS_CASH_RECEIPT_VOUCHER_C_SEQ", allocationSize = 1)
    @Basic(optional = false)
    @Column(name = "cash_receipt_voucher_ID", nullable = false)
    private Integer cashreceiptvoucherID;
    @Size(max = 300)
    @Column(name = "RECEIVED_FROM", length = 300)
    private String receivedFrom;
    //-------workflow------------

    @Size(max = 200)
    @Column(name = "PREPARE_REMARK", length = 200)
    private String prepareRemark;
    @Column(name = "PREPARED_DATE")
    @Temporal(TemporalType.DATE)
    private Date preparedDate;
    @Size(max = 50)
    @Column(name = "PREPARED_BY", length = 50)
    private String preparedBy;
    @Size(max = 50)
    @Column(name = "CHECKED_BY", length = 50)
    private String checkedBy;
    @Column(name = "CHECKED_DATE")
    @Temporal(TemporalType.DATE)
    private Date checkedDate;
    @Size(max = 500)
    @Column(name = "CHECK_REMARK", length = 500)
    private String checkRemark;
    @JoinColumn(name = "fms_voucher_VOUCHER_ID", referencedColumnName = "VOUCHER_ID", nullable = false)
    @OneToOne(optional = false)
    private FmsVoucher fmsVOUCHERID;
    @JoinColumn(name = "SUPP_ID", referencedColumnName = "ID")
    @ManyToOne
    private PrmsSupplyProfile suppId;
//    @JoinColumn(name = "BID_ID", referencedColumnName = "ID")
//    @ManyToOne
////    private PrmsBid bidId;
    @JoinColumn(name = "PAYMENT_TYPE", referencedColumnName = "LU_PAYMENT_TYPE_ID")
    @ManyToOne
    private FmsLuPaymentType paymentType;
//   @JoinColumn(name = "SUPP_ID", referencedColumnName = "ID")
//    @ManyToOne
//    private PrmsSupplyProfile suppId;
    @JoinColumn(name = "BID_ID", referencedColumnName = "ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private PrmsBid bidId;

    @OneToMany(mappedBy = "cashReceiptVoucher", fetch = FetchType.LAZY)
    private List<PrmsBidderRegDetail> fmsBidSaleList;

    public FmsCashReceiptVoucher() {
    }

    /**
     *
     * @return
     */
    public String getReceivedFrom() {
        return receivedFrom;
    }

    /**
     *
     * @param receivedFrom
     */
    public void setReceivedFrom(String receivedFrom) {
        this.receivedFrom = receivedFrom;
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
    public String getPrepareRemark() {
        return prepareRemark;
    }

    /**
     *
     * @param prepareRemark
     */
    public void setPrepareRemark(String prepareRemark) {
        this.prepareRemark = prepareRemark;
    }

    public String getTin() {
        return tin;
    }

    public void setTin(String tin) {
        this.tin = tin;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public FmsVoucher getFmsVOUCHERID() {
        return fmsVOUCHERID;
    }

    public void setFmsVOUCHERID(FmsVoucher fmsVOUCHERID) {
        this.fmsVOUCHERID = fmsVOUCHERID;
    }

    public PrmsSupplyProfile getSuppId() {
        return suppId;
    }

    public void setSuppId(PrmsSupplyProfile suppId) {
        this.suppId = suppId;
    }

    public PrmsBid getBidId() {
        return bidId;
    }

    public void setBidId(PrmsBid bidId) {
        this.bidId = bidId;
    }

    /**
     *
     * @return
     */
    public String getReceivedfrom() {
        return receivedFrom;
    }

    /**
     *
     * @param receivedfrom
     */
    public void setReceivedfrom(String receivedfrom) {
        this.receivedFrom = receivedfrom;
    }

    /**
     *
     * @param cashreceiptvoucherID
     */
    public FmsCashReceiptVoucher(Integer cashreceiptvoucherID) {
        this.cashreceiptvoucherID = cashreceiptvoucherID;
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
    public String getChequeNumber() {
        return chequeNumber;
    }

    /**
     *
     * @param chequeNumber
     */
    public void setChequeNumber(String chequeNumber) {
        this.chequeNumber = chequeNumber;
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
    public double getAmountInFigure() {
        return amountInFigure;
    }

    /**
     *
     * @param amountInFigure
     */
    public void setAmountInFigure(double amountInFigure) {
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
    public String getEmployeeId() {
        return employeeId;
    }

    /**
     *
     * @param employeeId
     */
    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    /**
     *
     * @return
     */
    public String getCustomerId() {
        return customerId;
    }

    /**
     *
     * @param customerId
     */
    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    /**
     *
     * @return
     */
    public String getOhterPayer() {
        return ohterPayer;
    }

    /**
     *
     * @param ohterPayer
     */
    public void setOhterPayer(String ohterPayer) {
        this.ohterPayer = ohterPayer;
    }

    /**
     *
     * @return
     */
    public String getCategory() {
        return category;
    }

    /**
     *
     * @param category
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     *
     * @return
     */
    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    /**
     *
     * @param invoiceNumber
     */
    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

//    public FmsLuPaymentType getPaymentType() {
//        return paymentType;
//    }
//
//    public void setPaymentType(FmsLuPaymentType paymentType) {
//        this.paymentType = paymentType;
//    }
    /**
     *
     * @return
     */
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
    public Integer getCashreceiptvoucherID() {
        return cashreceiptvoucherID;
    }

    /**
     *
     * @param cashreceiptvoucherID
     */
    public void setCashreceiptvoucherID(Integer cashreceiptvoucherID) {
        this.cashreceiptvoucherID = cashreceiptvoucherID;
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
     * @param fmsvoucherVOUCHERID
     */
    public void setFmsvoucherVOUCHERID(FmsVoucher fmsvoucherVOUCHERID) {
        this.fmsVOUCHERID = fmsvoucherVOUCHERID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cashreceiptvoucherID != null ? cashreceiptvoucherID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FmsCashReceiptVoucher)) {
            return false;
        }
        FmsCashReceiptVoucher other = (FmsCashReceiptVoucher) object;
        if ((this.cashreceiptvoucherID == null && other.cashreceiptvoucherID != null) || (this.cashreceiptvoucherID != null && !this.cashreceiptvoucherID.equals(other.cashreceiptvoucherID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.insa.erp.ibfms.entity.FmsCashReceiptVoucher[ cashreceiptvoucherID=" + cashreceiptvoucherID + " ]";
    }

    @Transient
    private Date collationDate;

    /**
     *
     * @return
     */
    public Date getCollationDate() {
        return collationDate;
    }

    /**
     *
     * @param collationDate
     */
    public void setCollationDate(Date collationDate) {
        this.collationDate = collationDate;
    }

//    public PrmsSupplyProfile getSuppId() {
//        return suppId;
//    }
//
//    public void setSuppId(PrmsSupplyProfile suppId) {
//        this.suppId = suppId;
//    }
//
//    public PrmsBid getBidId() {
//        return bidId;
//    }
//
//    public void setBidId(PrmsBid bidId) {
//        this.bidId = bidId;
//    }
//    public String getPaymentType() {
//        return paymentType;
//    }
//
//    public void setPaymentType(String paymentType) {
//        this.paymentType = paymentType;
//    }
//    
    @XmlTransient
    public List<PrmsBidderRegDetail> getFmsBidSaleList() {
        if (fmsBidSaleList == null) {
            fmsBidSaleList = new ArrayList<>();
        }
        return fmsBidSaleList;
    }

    public void setFmsBidSaleList(List<PrmsBidderRegDetail> fmsBidSaleList) {
        this.fmsBidSaleList = fmsBidSaleList;
    }

}
