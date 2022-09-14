/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.entity.Bond;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author mz
 */
@Entity
@Table(name = "FMS_BOND_COUPON_EXTEND")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FmsBondCouponExtend.findAll", query = "SELECT f FROM FmsBondCouponExtend f"),
    @NamedQuery(name = "FmsBondCouponExtend.findByCouponExtendId", query = "SELECT f FROM FmsBondCouponExtend f WHERE f.couponExtendId = :couponExtendId"),
    @NamedQuery(name = "FmsBondCouponExtend.findByNumberOfBond", query = "SELECT f FROM FmsBondCouponExtend f WHERE f.numberOfBond = :numberOfBond"),
    @NamedQuery(name = "FmsBondCouponExtend.findByTotalBondValue", query = "SELECT f FROM FmsBondCouponExtend f WHERE f.totalBondValue = :totalBondValue"),
    @NamedQuery(name = "FmsBondCouponExtend.findByNewSerialNo", query = "SELECT f FROM FmsBondCouponExtend f WHERE f.newSerialNo = :newSerialNo"),
    @NamedQuery(name = "FmsBondCouponExtend.findByBondNo", query = "SELECT f FROM FmsBondCouponExtend f WHERE f.BondNo = :BondNo"),
    @NamedQuery(name = "FmsBondCouponExtend.findByExtendDate", query = "SELECT f FROM FmsBondCouponExtend f WHERE f.extendDate = :extendDate"),
    @NamedQuery(name = "FmsBondCouponExtend.findByInterest", query = "SELECT f FROM FmsBondCouponExtend f WHERE f.interest = :interest"),
    @NamedQuery(name = "FmsBondCouponExtend.findByRemainBirr", query = "SELECT f FROM FmsBondCouponExtend f WHERE f.remainBirr = :remainBirr"),
    @NamedQuery(name = "FmsBondCouponExtend.findByStatus", query = "SELECT f FROM FmsBondCouponExtend f WHERE f.status = :status"),
    @NamedQuery(name = "FmsBondCouponExtend.findByBondIssuedDate", query = "SELECT f FROM FmsBondCouponExtend f WHERE f.BondIssuedDate = :BondIssuedDate"),
    @NamedQuery(name = "FmsBondCouponExtend.findByRepaymentStartDate", query = "SELECT f FROM FmsBondCouponExtend f WHERE f.repaymentStartDate = :repaymentStartDate"),
    @NamedQuery(name = "FmsBondCouponExtend.findByRepaymentEndDate", query = "SELECT f FROM FmsBondCouponExtend f WHERE f.repaymentEndDate = :repaymentEndDate"),
    @NamedQuery(name = "FmsBondCouponExtend.findByPaidInterest", query = "SELECT f FROM FmsBondCouponExtend f WHERE f.paidInterest = :paidInterest"),
    @NamedQuery(name = "FmsBondCouponExtend.findByExtendNumber", query = "SELECT f FROM FmsBondCouponExtend f WHERE f.extendNumber = :extendNumber")})
public class FmsBondCouponExtend implements Serializable {

    private static final long serialVersionUID = 1L;
    //<editor-fold defaultstate="collapsed" desc="variable declaration">
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "COUPON_EXTEND_ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FMS_BOND_COUPON_EXTEND_SEQ")
    @SequenceGenerator(name = "FMS_BOND_COUPON_EXTEND_SEQ", sequenceName = "FMS_BOND_COUPON_EXTEND_SEQ", allocationSize = 1)
    private BigDecimal couponExtendId;
    @Column(name = "NUMBER_OF_BOND")
    private BigInteger numberOfBond;
    @Column(name = "TOTAL_BOND_VALUE")
    private BigInteger totalBondValue;
    @Column(name = "NEW_SERIAL_NO")
    private String newSerialNo;
    @Column(name = "BOND_NO")
    private String BondNo;
    @Column(name = "EXTEND_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date extendDate;
    @Column(name = "INTEREST")
    private BigInteger interest;
    @Column(name = "REMAIN_BIRR")
    private BigInteger remainBirr;
    @Column(name = "STATUS")
    private String status;
    @Column(name = "BOND_ISSUED_DATE")
    @Temporal(TemporalType.DATE)
    private Date BondIssuedDate;
    @Column(name = "REPAYMENT_START_DATE")
    @Temporal(TemporalType.DATE)
    private Date repaymentStartDate;
    @Column(name = "REPAYMENT_END_DATE")
    @Temporal(TemporalType.DATE)
    private Date repaymentEndDate;
    @Column(name = "PAID_INTEREST")
    private Double paidInterest;
    @Column(name = "EXTEND_NUMBER")
    private String extendNumber;
    @Column(name = "EXTEND_BY")
    private Integer extendBy;
    @Column(name = "PAID_PRINCIPAL")
    private Double paidPrincipal;
    @Column(name = "REMAIN_INTEREST")
    private Double remainInterest;
    @Column(name = "REMAIN_PRINCIPAL")
    private Double remainPrincipal;
    @JoinColumn(name = "COUPON_EXTEND", referencedColumnName = "COUPON_ID")
    @ManyToOne
    private FmsBondCoupon couponExtend;
    @JoinColumn(name = "SCHEDULE_EXTEND", referencedColumnName = "COUPON_SCHEDULE_ID")
    @ManyToOne
    private FmsBondCouponSchedule scheduleExtend;
//</editor-fold>
    public FmsBondCouponExtend() {
    }
//<editor-fold defaultstate="collapsed" desc="Getter and Setter Methods">
    public FmsBondCouponExtend(BigDecimal couponExtendId) {
        this.couponExtendId = couponExtendId;
    }

    public BigDecimal getCouponExtendId() {
        return couponExtendId;
    }

    public void setCouponExtendId(BigDecimal couponExtendId) {
        this.couponExtendId = couponExtendId;
    }

    public BigInteger getNumberOfBond() {
        return numberOfBond;
    }

    public void setNumberOfBond(BigInteger numberOfBond) {
        this.numberOfBond = numberOfBond;
    }

    public BigInteger getTotalBondValue() {
        return totalBondValue;
    }

    public void setTotalBondValue(BigInteger totalBondValue) {
        this.totalBondValue = totalBondValue;
    }

    public String getNewSerialNo() {
        return newSerialNo;
    }

    public void setNewSerialNo(String newSerialNo) {
        this.newSerialNo = newSerialNo;
    }

    public String getBondNo() {
        return BondNo;
    }

    public void setBondNo(String BondNo) {
        this.BondNo = BondNo;
    }

    public Date getExtendDate() {
        return extendDate;
    }

    public void setExtendDate(Date extendDate) {
        this.extendDate = extendDate;
    }

    public BigInteger getInterest() {
        return interest;
    }

    public void setInterest(BigInteger interest) {
        this.interest = interest;
    }

    public BigInteger getRemainBirr() {
        return remainBirr;
    }

    public void setRemainBirr(BigInteger remainBirr) {
        this.remainBirr = remainBirr;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getBondIssuedDate() {
        return BondIssuedDate;
    }

    public void setBondIssuedDate(Date BondIssuedDate) {
        this.BondIssuedDate = BondIssuedDate;
    }

    public Date getRepaymentStartDate() {
        return repaymentStartDate;
    }

    public void setRepaymentStartDate(Date repaymentStartDate) {
        this.repaymentStartDate = repaymentStartDate;
    }

    public Date getRepaymentEndDate() {
        return repaymentEndDate;
    }

    public void setRepaymentEndDate(Date repaymentEndDate) {
        this.repaymentEndDate = repaymentEndDate;
    }

    public Integer getExtendBy() {
        return extendBy;
    }

    public void setExtendBy(Integer extendBy) {
        this.extendBy = extendBy;
    }

    public String getExtendNumber() {
        return extendNumber;
    }

    public void setExtendNumber(String extendNumber) {
        this.extendNumber = extendNumber;
    }

    public Double getPaidInterest() {
        return paidInterest;
    }

    public void setPaidInterest(Double paidInterest) {
        this.paidInterest = paidInterest;
    }

    public Double getPaidPrincipal() {
        return paidPrincipal;
    }

    public void setPaidPrincipal(Double paidPrincipal) {
        this.paidPrincipal = paidPrincipal;
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

    public FmsBondCoupon getCouponExtend() {
        return couponExtend;
    }

    public void setCouponExtend(FmsBondCoupon couponExtend) {
        this.couponExtend = couponExtend;
    }

    public FmsBondCouponSchedule getScheduleExtend() {
        return scheduleExtend;
    }

    public void setScheduleExtend(FmsBondCouponSchedule scheduleExtend) {
        this.scheduleExtend = scheduleExtend;
    }
//</editor-fold>
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (couponExtendId != null ? couponExtendId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof FmsBondCouponExtend)) {
            return false;
        }
        FmsBondCouponExtend other = (FmsBondCouponExtend) object;
        if ((this.couponExtendId == null && other.couponExtendId != null) || (this.couponExtendId != null && !this.couponExtendId.equals(other.couponExtendId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.eep.fcms.entity.Bond.FmsBondCouponExtend[ couponExtendId=" + couponExtendId + " ]";
    }

}
