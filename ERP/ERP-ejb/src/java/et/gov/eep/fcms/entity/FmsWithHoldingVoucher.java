/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.entity;

import et.gov.eep.fcms.entity.Vocher.FmsChequePaymentVoucher;
import et.gov.eep.fcms.entity.Vocher.FmsVoucher;
import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author AB
 */
@Entity
@Table(name = "fms_with_holding_voucher")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FmsWithHoldingVoucher.findAll", query = "SELECT f FROM FmsWithHoldingVoucher f"),
    @NamedQuery(name = "FmsWithHoldingVoucher.findByAmountInFigure", query = "SELECT f FROM FmsWithHoldingVoucher f WHERE f.amountInFigure = :amountInFigure"),
    @NamedQuery(name = "FmsWithHoldingVoucher.findByPreparedBy", query = "SELECT f FROM FmsWithHoldingVoucher f WHERE f.preparedBy = :preparedBy"),
    @NamedQuery(name = "FmsWithHoldingVoucher.findByCheckeBy", query = "SELECT f FROM FmsWithHoldingVoucher f WHERE f.checkeBy = :checkeBy"),
    @NamedQuery(name = "FmsWithHoldingVoucher.findByApprovedBy", query = "SELECT f FROM FmsWithHoldingVoucher f WHERE f.approvedBy = :approvedBy"),
    @NamedQuery(name = "FmsWithHoldingVoucher.findByPreparedDate", query = "SELECT f FROM FmsWithHoldingVoucher f WHERE f.preparedDate = :preparedDate"),
    @NamedQuery(name = "FmsWithHoldingVoucher.findByCheckedDate", query = "SELECT f FROM FmsWithHoldingVoucher f WHERE f.checkedDate = :checkedDate"),
    @NamedQuery(name = "FmsWithHoldingVoucher.findByApprovedDate", query = "SELECT f FROM FmsWithHoldingVoucher f WHERE f.approvedDate = :approvedDate"),
    @NamedQuery(name = "FmsWithHoldingVoucher.findByReferenceNumber", query = "SELECT f FROM FmsWithHoldingVoucher f WHERE f.referenceNumber = :referenceNumber"),
    @NamedQuery(name = "FmsWithHoldingVoucher.findByStatus", query = "SELECT f FROM FmsWithHoldingVoucher f WHERE f.status = :status"),
//    @NamedQuery(name = "FmsWithHoldingVoucher.findByVoucherId", query = "SELECT f FROM FmsWithHoldingVoucher f WHERE f.voucherId LIKE :voucherId"),
    @NamedQuery(name = "FmsWithHoldingVoucher.findByWithHoldingId", query = "SELECT f FROM FmsWithHoldingVoucher f WHERE f.withHoldingId = :withHoldingId"),
    @NamedQuery(name = "FmsWithHoldingVoucher.findByAddressId", query = "SELECT f FROM FmsWithHoldingVoucher f WHERE f.addressId = :addressId")})
public class FmsWithHoldingVoucher implements Serializable {

    private static final long serialVersionUID = 1L;
    @Column(name = "AMOUNT_IN_FIGURE")
    private Long amountInFigure;
    @Size(max = 50)
    @Column(name = "PREPARED_BY", length = 50)
    private String preparedBy;
    @Size(max = 50)
    @Column(name = "SYSTEM_NO", length = 50)
    private String systemno;
    @Size(max = 50)
    @Column(name = "CHECKE_BY", length = 50)
    private String checkeBy;
    @Size(max = 50)
    @Column(name = "APPROVED_BY", length = 50)
    private String approvedBy;
    @Column(name = "PREPARED_DATE")
    @Temporal(TemporalType.DATE)
    private Date preparedDate;
    @Column(name = "CHECKED_DATE")
    @Temporal(TemporalType.DATE)
    private Date checkedDate;
    @Column(name = "APPROVED_DATE")
    @Temporal(TemporalType.DATE)
    private Date approvedDate;
    @Size(max = 50)
    @Column(name = "REFERENCE_NUMBER", length = 50)
    private String referenceNumber;
    @Column(name = "STATUS_")
    private String status;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FMS_WITH_HOLDING_VOUCHER_W_SEQ")
    @SequenceGenerator(name = "FMS_WITH_HOLDING_VOUCHER_W_SEQ", sequenceName = "FMS_WITH_HOLDING_VOUCHER_W_SEQ", allocationSize = 1)
    @Basic(optional = false)
    @Column(name = "WITH_HOLDING_ID", nullable = false)
    private Integer withHoldingId;
    @Column(name = "ADDRESS_ID")
    private Integer addressId;
    @Column(name = "WITH_HOLD_VALUE")
    private double withHoldValue;
//    @Size(max = 50)
//    @Column(name = "CHEQUE_PAYMENT_ID")
//    private double chequePayemtID;
    @Size(max = 100)
    @Column(name = "TAXPAYER", length = 100)
    private String taxPayer;
    @JoinColumn(name = "VOUCHER_ID", referencedColumnName = "VOUCHER_ID")
    @OneToOne(optional = false)
    private FmsVoucher fmsVOUCHERID;
   

    public FmsWithHoldingVoucher() {
    }

    public FmsVoucher getVoucherId() {

        return fmsVOUCHERID;
    }

    public void setVoucherId(FmsVoucher voucherId) {
        this.fmsVOUCHERID = voucherId;
    }

    public String getSystemno() {

        return systemno;
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
     */
    /**
     *
     * @return
     */
    public void setSystemno(String systemno) {
        this.systemno = systemno;
    }

    public String getTaxPayer() {
        return taxPayer;
    }

    /**
     *
     * @param taxPayer
     */
    public void setTaxPayer(String taxPayer) {
        this.taxPayer = taxPayer;
    }

    /**
     *
     * @return
     */
    public double getWithHoldValue() {
        return withHoldValue;
    }

    /**
     *
     * @param withHoldValue
     */
    public void setWithHoldValue(double withHoldValue) {
        this.withHoldValue = withHoldValue;
    }

//    public double getChequePayemtID() {
//        return chequePayemtID;
//    }
//
//    public void setChequePayemtID(double chequePayemtID) {
//        this.chequePayemtID = chequePayemtID;
//    }
    /**
     *
     * @return
     */
//    public FmsChequePaymentVoucher getChequePayemtID() {
//        return chequePayemtID;
//    }
//
//    /**
//     *
//     * @param chequePayemtID
//     */
//    public void setChequePayemtID(FmsChequePaymentVoucher chequePayemtID) {
//        this.chequePayemtID = chequePayemtID;
//    }
    /**
     *
     * @param withHoldingId
     */
    public FmsWithHoldingVoucher(Integer withHoldingId) {
        this.withHoldingId = withHoldingId;
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
    public String getCheckeBy() {
        return checkeBy;
    }

    /**
     *
     * @param checkeBy
     */
    public void setCheckeBy(String checkeBy) {
        this.checkeBy = checkeBy;
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

    public void setStatus(String status) {
        this.status = status;
    }

    /**
     *
     * @return //
     */
//public String getVoucherId() {
//        return voucherId;
//    }
    /**
     *
     * @param voucherId
     */
//    public void setVoucherId(String voucherId) {
//        this.voucherId = voucherId;
//    }
    /**
     *
     * @return
     */
    public Integer getWithHoldingId() {
        return withHoldingId;
    }

    /**
     *
     * @param withHoldingId
     */
    public void setWithHoldingId(Integer withHoldingId) {
        this.withHoldingId = withHoldingId;
    }

    /**
     *
     * @return
     */
    public Integer getAddressId() {
        return addressId;
    }

    /**
     *
     * @param addressId
     */
    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (withHoldingId != null ? withHoldingId.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return fmsVOUCHERID.getVoucherId();
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FmsWithHoldingVoucher)) {
            return false;
        }
        FmsWithHoldingVoucher other = (FmsWithHoldingVoucher) object;
        if ((this.withHoldingId == null && other.withHoldingId != null) || (this.withHoldingId != null && !this.withHoldingId.equals(other.withHoldingId))) {
            return false;
        }
        return true;
    }

//    @Override
//    public String toString() {
//        return voucherId;
//    }
}
