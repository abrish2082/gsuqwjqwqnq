/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.entity.Bond;

import java.io.Serializable;
import java.util.ArrayList;
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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author mz
 */
@Entity
@Table(name = "FMS_BOND_COUPON_SCHEDULE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FmsBondCouponSchedule.findAll", query = "SELECT f FROM FmsBondCouponSchedule f"),
    @NamedQuery(name = "FmsBondCouponSchedule.findByCouponScheduleId", query = "SELECT f FROM FmsBondCouponSchedule f WHERE f.couponScheduleId = :couponScheduleId"),
    @NamedQuery(name = "FmsBondCouponSchedule.findByInterest", query = "SELECT f FROM FmsBondCouponSchedule f WHERE f.interest = :interest"),
    @NamedQuery(name = "FmsBondCouponSchedule.findByPaidInterest", query = "SELECT f FROM FmsBondCouponSchedule f WHERE f.paidInterest = :paidInterest"),
    @NamedQuery(name = "FmsBondCouponSchedule.findByPaidAmount", query = "SELECT f FROM FmsBondCouponSchedule f WHERE f.paidAmount = :paidAmount"),
    @NamedQuery(name = "FmsBondCouponSchedule.findByInstallmentDueDate", query = "SELECT f FROM FmsBondCouponSchedule f WHERE f.installmentDueDate = :installmentDueDate"),
    @NamedQuery(name = "FmsBondCouponSchedule.findByBondNo", query = "SELECT f FROM FmsBondCouponSchedule f WHERE f.BondNo = :BondNo"),
    @NamedQuery(name = "FmsBondCouponSchedule.findByStatus", query = "SELECT f FROM FmsBondCouponSchedule f WHERE f.status = :status"),
    @NamedQuery(name = "FmsBondCouponSchedule.findByPrincipalRePaymentStartDate", query = "SELECT f FROM FmsBondCouponSchedule f WHERE f.principalRepaymentStartDate = :principalRepaymentStartDate"),
    @NamedQuery(name = "FmsBondCouponSchedule.findByPrincipalRePaymentEndDate", query = "SELECT f FROM FmsBondCouponSchedule f WHERE f.principalRepaymentEndDate = :principalRepaymentEndDate"),
    @NamedQuery(name = "FmsBondCouponSchedule.findByTotalPaidAmount", query = "SELECT f FROM FmsBondCouponSchedule f WHERE f.totalPaidAmount = :totalPaidAmount"),
    @NamedQuery(name = "FmsBondCouponSchedule.findByInstallmentNo", query = "SELECT f FROM FmsBondCouponSchedule f WHERE f.installmentNo = :installmentNo ORDER BY f.installmentNo"),
    @NamedQuery(name = "FmsBondCouponSchedule.findByPaymentDocumentDate", query = "SELECT f FROM FmsBondCouponSchedule f WHERE f.paymentDocumentDate = :paymentDocumentDate"),
    @NamedQuery(name = "FmsBondCouponSchedule.findByPaymentDocumentReference", query = "SELECT f FROM FmsBondCouponSchedule f WHERE f.paymentDocumentReference = :paymentDocumentReference"),
    @NamedQuery(name = "FmsBondCouponSchedule.findByBondCouponNo", query = "SELECT f FROM FmsBondCouponSchedule f WHERE f.BondCouponNo.couponId = :couponId")})
public class FmsBondCouponSchedule implements Serializable {

    private static final long serialVersionUID = 1L;
    //<editor-fold defaultstate="collapsed" desc="variable declaration">
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "COUPON_SCHEDULE_ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FMS_BOND_COUPON_SCHEDULE_SEQ")
    @SequenceGenerator(name = "FMS_BOND_COUPON_SCHEDULE_SEQ", sequenceName = "FMS_BOND_COUPON_SCHEDULE_SEQ", allocationSize = 1)
    private Integer couponScheduleId;
    @Column(name = "INTEREST")
    private Double interest;
    @Column(name = "PAID_INTEREST")
    private Double paidInterest;
    @Column(name = "PAID_AMOUNT")
    private Double paidAmount;
    @Column(name = "TOTAL_PAID_AMOUNT")
    private Double totalPaidAmount;
    @Column(name = "INSTALLMENT_NO")
    private Integer installmentNo;
    @Column(name = "REMAIN_INTEREST")
    private Double remainInterest;
    @Column(name = "REMAIN_PRINCIPAL")
    private Double remainPrincipal;
    @Column(name = "INSTALLMENT_DUE_DATE")
    @Temporal(TemporalType.DATE)
    private Date installmentDueDate;
    @Column(name = "BOND_NO")
    private String BondNo;
    @Column(name = "STATUS")
    private String status;
    @Size(max = 200)
    @Column(name = "REMARK")
    private String remark;
    @Column(name = "PRINCIPAL_REPAYMENT_START_DATE")
    @Temporal(TemporalType.DATE)
    private Date principalRepaymentStartDate;
    @Column(name = "PRINCIPAL_REPAYMENT_END_DATE")
    @Temporal(TemporalType.DATE)
    private Date principalRepaymentEndDate;
    @Column(name = "PAYMENT_DATE")
    @Temporal(TemporalType.DATE)
    private Date paymentDate;
    @Column(name = "PAYMENT_DOCUMENT_DATE")
    @Temporal(TemporalType.DATE)
    private Date paymentDocumentDate;
    @Column(name = "PAYMENT_DOCUMENT_REFERENCE")
    private String paymentDocumentReference;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "scheduleExtend")
    private List<FmsBondCouponExtend> fmsBondCouponExtendList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "couponScheduleNo")
    private List<FmsBondCouponInterestPaymt> fmsBondCouponInterestPaymtList;
    @JoinColumn(name = "BOND_COUPON_NO", referencedColumnName = "COUPON_ID")
    @ManyToOne
    private FmsBondCoupon BondCouponNo;
    @Transient
    String ChangeStatus;
//</editor-fold>

    public FmsBondCouponSchedule() {
    }

    //<editor-fold defaultstate="collapsed" desc="Getter and Setter">

    public FmsBondCouponSchedule(Integer couponScheduleId) {
        this.couponScheduleId = couponScheduleId;
    }

    public Integer getCouponScheduleId() {
        return couponScheduleId;
    }

    public void setCouponScheduleId(Integer couponScheduleId) {
        this.couponScheduleId = couponScheduleId;
    }

    public Double getInterest() {
        return interest;
    }

    public void setInterest(Double interest) {
        this.interest = interest;
    }

    public Double getPaidInterest() {
        return paidInterest;
    }

    public void setPaidInterest(Double paidInterest) {
        this.paidInterest = paidInterest;
    }

    public Double getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(Double paidAmount) {
        this.paidAmount = paidAmount;
    }

    public Date getInstallmentDueDate() {
        return installmentDueDate;
    }

    public void setInstallmentDueDate(Date installmentDueDate) {
        this.installmentDueDate = installmentDueDate;
    }

    public String getBondNo() {
        return BondNo;
    }

    public void setBondNo(String BondNo) {
        this.BondNo = BondNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getPrincipalRepaymentStartDate() {
        return principalRepaymentStartDate;
    }

    public void setPrincipalRepaymentStartDate(Date principalRepaymentStartDate) {
        this.principalRepaymentStartDate = principalRepaymentStartDate;
    }

    public Date getPrincipalRepaymentEndDate() {
        return principalRepaymentEndDate;
    }

    public void setPrincipalRepaymentEndDate(Date principalRepaymentEndDate) {
        this.principalRepaymentEndDate = principalRepaymentEndDate;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public Double getTotalPaidAmount() {
        return totalPaidAmount;
    }

    public void setTotalPaidAmount(Double totalPaidAmount) {
        this.totalPaidAmount = totalPaidAmount;
    }

    public Integer getInstallmentNo() {
        return installmentNo;
    }

    public void setInstallmentNo(Integer installmentNo) {
        this.installmentNo = installmentNo;
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

    public Double getRemainInterest() {
        return remainInterest;
    }

    public void setRemainInterest(Double remainInterest) {
        this.remainInterest = remainInterest;
    }

    public Double getRemainPrincipal() {
        return remainPrincipal;
    }

    public void setRemainPrincipal(Double remainPrincipal) {
        this.remainPrincipal = remainPrincipal;
    }

    public FmsBondCoupon getBondCouponNo() {
        return BondCouponNo;
    }

    public void setBondCouponNo(FmsBondCoupon BondCouponNo) {
        this.BondCouponNo = BondCouponNo;
    }

    public String getChangeStatus() {
        return ChangeStatus;
    }

    public void setChangeStatus(String ChangeStatus) {
        this.ChangeStatus = ChangeStatus;
    }
//</editor-fold>

    public void addToFmsBondCouponExtend(FmsBondCouponExtend fmsBondCouponExtend) {

        if (fmsBondCouponExtend.getScheduleExtend() != this) {
            this.getFmsBondCouponExtendList().add(fmsBondCouponExtend);
            fmsBondCouponExtend.setScheduleExtend(this);
        }
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (couponScheduleId != null ? couponScheduleId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof FmsBondCouponSchedule)) {
            return false;
        }
        FmsBondCouponSchedule other = (FmsBondCouponSchedule) object;
        if ((this.couponScheduleId == null && other.couponScheduleId != null) || (this.couponScheduleId != null && !this.couponScheduleId.equals(other.couponScheduleId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.eep.fcms.entity.Bond.FmsBondCouponSchedule[ couponScheduleId=" + couponScheduleId + " ]";
    }

    @XmlTransient
    public List<FmsBondCouponExtend> getFmsBondCouponExtendList() {
        if (fmsBondCouponExtendList == null) {
            fmsBondCouponExtendList = new ArrayList<>();
        }
        return fmsBondCouponExtendList;
    }

    public void setFmsBondCouponExtendList(List<FmsBondCouponExtend> fmsBondCouponExtendList) {
        this.fmsBondCouponExtendList = fmsBondCouponExtendList;
    }

    @XmlTransient
    public List<FmsBondCouponInterestPaymt> getFmsBondCouponInterestPaymtList() {
        if (fmsBondCouponInterestPaymtList == null) {
            fmsBondCouponInterestPaymtList = new ArrayList<>();
        }
        return fmsBondCouponInterestPaymtList;
    }

    public void setFmsBondCouponInterestPaymtList(List<FmsBondCouponInterestPaymt> fmsBondCouponInterestPaymtList) {
        this.fmsBondCouponInterestPaymtList = fmsBondCouponInterestPaymtList;
    }

    public void addToFmsBondCouponInterestPaymt(FmsBondCouponInterestPaymt fmsBondCouponInterestPaymtDetail) {
        if (fmsBondCouponInterestPaymtDetail.getCouponScheduleNo() != this) {
            this.getFmsBondCouponInterestPaymtList().add(fmsBondCouponInterestPaymtDetail);
            fmsBondCouponInterestPaymtDetail.setCouponScheduleNo(this);
        }
    }
}
