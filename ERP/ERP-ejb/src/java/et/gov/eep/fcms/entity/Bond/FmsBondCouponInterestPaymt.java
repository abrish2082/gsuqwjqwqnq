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
 * @author mz
 */
@Entity
@Table(name = "FMS_BOND_COUPON_INTEREST_PAYMT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FmsBondCouponInterestPaymt.findAll", query = "SELECT f FROM FmsBondCouponInterestPaymt f"),
    @NamedQuery(name = "FmsBondCouponInterestPaymt.findByInterestPaymentId", query = "SELECT f FROM FmsBondCouponInterestPaymt f WHERE f.interestPaymentId = :interestPaymentId"),
    @NamedQuery(name = "FmsBondCouponInterestPaymt.findByBondNo", query = "SELECT f FROM FmsBondCouponInterestPaymt f WHERE f.BondNo = :BondNo"),
    @NamedQuery(name = "FmsBondCouponInterestPaymt.findByPaymentStartDate", query = "SELECT f FROM FmsBondCouponInterestPaymt f WHERE f.paymentStartDate = :paymentStartDate"),
    @NamedQuery(name = "FmsBondCouponInterestPaymt.findByPaymentEndDate", query = "SELECT f FROM FmsBondCouponInterestPaymt f WHERE f.paymentEndDate = :paymentEndDate"),
    @NamedQuery(name = "FmsBondCouponInterestPaymt.findByPaidAmount", query = "SELECT f FROM FmsBondCouponInterestPaymt f WHERE f.paidAmount = :paidAmount"),
    @NamedQuery(name = "FmsBondCouponInterestPaymt.findByRemainPrincipal", query = "SELECT f FROM FmsBondCouponInterestPaymt f WHERE f.remainPrincipal = :remainPrincipal"),
    @NamedQuery(name = "FmsBondCouponInterestPaymt.findByPaymentDocumentDate", query = "SELECT f FROM FmsBondCouponInterestPaymt f WHERE f.paymentDocumentDate = :paymentDocumentDate"),
    @NamedQuery(name = "FmsBondCouponInterestPaymt.findByPaymentDocumentReference", query = "SELECT f FROM FmsBondCouponInterestPaymt f WHERE f.paymentDocumentReference = :paymentDocumentReference"),
    @NamedQuery(name = "FmsBondCouponInterestPaymt.findByRemark", query = "SELECT f FROM FmsBondCouponInterestPaymt f WHERE f.remark = :remark"),
    @NamedQuery(name = "FmsBondCouponInterestPaymt.findByStatus", query = "SELECT f FROM FmsBondCouponInterestPaymt f WHERE f.status = :status"),
    @NamedQuery(name = "FmsBondCouponInterestPaymt.findByPaidInterestOfPayDate", query = "SELECT f FROM FmsBondCouponInterestPaymt f WHERE f.BondNo = :BondNo and (f.paymentDate between :date1 and :date2)"),
    @NamedQuery(name = "FmsBondCouponInterestPaymt.findByPaidInterestPayOfPayDate", query = "SELECT f FROM FmsBondCouponInterestPaymt f WHERE f.BondNo = :BondNo and (f.paymentDate <= :date2) "),
    @NamedQuery(name = "FmsBondCouponInterestPaymt.findByPaymentDate", query = "SELECT f FROM FmsBondCouponInterestPaymt f WHERE f.paymentDate = :paymentDate")})
public class FmsBondCouponInterestPaymt implements Serializable {

    private static final long serialVersionUID = 1L;
       //<editor-fold defaultstate="collapsed" desc="variable declaration">
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "INTEREST_PAYMENT_ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FMS_COUPON_INTEREST_REPAY_SEQ")
    @SequenceGenerator(name = "FMS_COUPON_INTEREST_REPAY_SEQ", sequenceName = "FMS_COUPON_INTEREST_REPAY_SEQ", allocationSize = 1)
    private Integer interestPaymentId;
    @Column(name = "PAYMENT_START_DATE")
    @Temporal(TemporalType.DATE)
    private Date paymentStartDate;
    @Column(name = "PAYMENT_END_DATE")
    @Temporal(TemporalType.DATE)
    private Date paymentEndDate;
    @Column(name = "PAID_AMOUNT")
    private Double paidAmount;
    @Column(name = "NUMBER_OF_DAYS")
    private Double numberOfDays;
    @Column(name = "REMAIN_PRINCIPAL")
    private Double remainPrincipal;
    @Column(name = "PAYMENT_DOCUMENT_DATE")
    @Temporal(TemporalType.DATE)
    private Date paymentDocumentDate;
    @Column(name = "PAYMENT_DOCUMENT_REFERENCE")
    private String paymentDocumentReference;
    @Size(max = 200)
    @Column(name = "REMARK")
    private String remark;
    @Size(max = 20)
    @Column(name = "STATUS")
    private String status;
    @Column(name = "INTEREST_RATE")
    private Double interestRate;
    @Column(name = "PAYMENT_DATE")
    @Temporal(TemporalType.DATE)
    private Date paymentDate;
    @Column(name = "BOND_NO")
    private String BondNo;
    @JoinColumn(name = "COUPON_NO", referencedColumnName = "COUPON_ID")
    @ManyToOne
    private FmsBondCoupon couponNo;
    @JoinColumn(name = "COUPON_SCHEDULE_NO", referencedColumnName = "COUPON_SCHEDULE_ID")
    @ManyToOne
    private FmsBondCouponSchedule couponScheduleNo;
//</editor-fold>
    
    public FmsBondCouponInterestPaymt() {
    }
   //<editor-fold defaultstate="collapsed" desc="Getter and Setter">
    public FmsBondCouponInterestPaymt(Integer interestPaymentId) {
        this.interestPaymentId = interestPaymentId;
    }

    public Integer getInterestPaymentId() {
        return interestPaymentId;
    }

    public void setInterestPaymentId(Integer interestPaymentId) {
        this.interestPaymentId = interestPaymentId;
    }

    public Date getPaymentStartDate() {
        return paymentStartDate;
    }

    public void setPaymentStartDate(Date paymentStartDate) {
        this.paymentStartDate = paymentStartDate;
    }

    public Date getPaymentEndDate() {
        return paymentEndDate;
    }

    public void setPaymentEndDate(Date paymentEndDate) {
        this.paymentEndDate = paymentEndDate;
    }

    public Double getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(Double paidAmount) {
        this.paidAmount = paidAmount;
    }

    public Double getNumberOfDays() {
        return numberOfDays;
    }

    public void setNumberOfDays(Double numberOfDays) {
        this.numberOfDays = numberOfDays;
    }

    public Double getRemainPrincipal() {
        return remainPrincipal;
    }

    public void setRemainPrincipal(Double remainPrincipal) {
        this.remainPrincipal = remainPrincipal;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(Double interestRate) {
        this.interestRate = interestRate;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getBondNo() {
        return BondNo;
    }

    public void setBondNo(String BondNo) {
        this.BondNo = BondNo;
    }

    public FmsBondCoupon getCouponNo() {
        return couponNo;
    }

    public void setCouponNo(FmsBondCoupon couponNo) {
        this.couponNo = couponNo;
    }

    public FmsBondCouponSchedule getCouponScheduleNo() {
        return couponScheduleNo;
    }

    public void setCouponScheduleNo(FmsBondCouponSchedule couponScheduleNo) {
        this.couponScheduleNo = couponScheduleNo;
    }
//</editor-fold>
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (interestPaymentId != null ? interestPaymentId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FmsBondCouponInterestPaymt)) {
            return false;
        }
        FmsBondCouponInterestPaymt other = (FmsBondCouponInterestPaymt) object;
        if ((this.interestPaymentId == null && other.interestPaymentId != null) || (this.interestPaymentId != null && !this.interestPaymentId.equals(other.interestPaymentId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.eep.fcms.entity.Bond.FmsBondCouponInterestPaymt[ interestPaymentId=" + interestPaymentId + " ]";
    }

}
