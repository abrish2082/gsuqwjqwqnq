/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.entity.Bond;

import java.io.Serializable;
import java.math.BigInteger;
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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author mz
 */
@Entity
@Table(name = "FMS_BOND_COUPON")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FmsBondCoupon.findAll", query = "SELECT f FROM FmsBondCoupon f"),
    @NamedQuery(name = "FmsBondCoupon.findByCouponId", query = "SELECT f FROM FmsBondCoupon f WHERE f.couponId = :couponId"),
    @NamedQuery(name = "FmsBondCoupon.findByNumberOfBond", query = "SELECT f FROM FmsBondCoupon f WHERE f.numberOfBond = :numberOfBond"),
    @NamedQuery(name = "FmsBondCoupon.findByBondValue", query = "SELECT f FROM FmsBondCoupon f WHERE f.BondValue = :BondValue"),
    @NamedQuery(name = "FmsBondCoupon.findByBondMaturity", query = "SELECT f FROM FmsBondCoupon f WHERE f.BondMaturity = :BondMaturity"),
    @NamedQuery(name = "FmsBondCoupon.findByGracePeriod", query = "SELECT f FROM FmsBondCoupon f WHERE f.gracePeriod = :gracePeriod"),
    @NamedQuery(name = "FmsBondCoupon.findByTotalBondValue", query = "SELECT f FROM FmsBondCoupon f WHERE f.totalBondValue = :totalBondValue"),
    @NamedQuery(name = "FmsBondCoupon.findByBondNo", query = "SELECT f FROM FmsBondCoupon f WHERE f.BondNo = :BondNo"),
    @NamedQuery(name = "FmsBondCoupon.findByInterest", query = "SELECT f FROM FmsBondCoupon f WHERE f.interest = :interest"),
    @NamedQuery(name = "FmsBondCoupon.findByInterestRate", query = "SELECT f FROM FmsBondCoupon f WHERE f.interestRate = :interestRate"),
    @NamedQuery(name = "FmsBondCoupon.findByBondIssuedDate", query = "SELECT f FROM FmsBondCoupon f WHERE f.BondIssuedDate = :BondIssuedDate"),
    @NamedQuery(name = "FmsBondCoupon.findByInterestRepaymentStartDate", query = "SELECT f FROM FmsBondCoupon f WHERE f.interestRepaymentStartDate = :interestRepaymentStartDate"),
    @NamedQuery(name = "FmsBondCoupon.findByInterestRepaymentEndDate", query = "SELECT f FROM FmsBondCoupon f WHERE f.interestRepaymentEndDate = :interestRepaymentEndDate"),
    @NamedQuery(name = "FmsBondCoupon.findByPrincipalRepaymentStartDate", query = "SELECT f FROM FmsBondCoupon f WHERE f.principalRepaymentStartDate = :principalRepaymentStartDate"),
    @NamedQuery(name = "FmsBondCoupon.findByPrincipalRepaymentEndDate", query = "SELECT f FROM FmsBondCoupon f WHERE f.principalRepaymentEndDate = :principalRepaymentEndDate"),
    @NamedQuery(name = "FmsBondCoupon.findByStatus", query = "SELECT f FROM FmsBondCoupon f WHERE f.status = :status")})
public class FmsBondCoupon implements Serializable {

    private static final long serialVersionUID = 1L;
    //<editor-fold defaultstate="collapsed" desc="variable declaration">
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "COUPON_ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FMS_BOND_COUPON_SEQ")
    @SequenceGenerator(name = "FMS_BOND_COUPON_SEQ", sequenceName = "FMS_BOND_COUPON_SEQ", allocationSize = 1)
    private Integer couponId;
    @Column(name = "BOND_NO")
    private String BondNo;
    @Column(name = "BOND_ISSUED_DATE")
    @Temporal(TemporalType.DATE)
    private Date BondIssuedDate;
    @Column(name = "NUMBER_OF_BOND")
    private Integer numberOfBond;
    @Column(name = "BOND_VALUE")
    private Double BondValue;
    @Column(name = "BOND_MATURITY")
    private Integer BondMaturity;
    @Column(name = "GRACE_PERIOD")
    private Integer gracePeriod;
    @Column(name = "TOTAL_BOND_VALUE")
    private Double totalBondValue;
    //<editor-fold defaultstate="collapsed" desc="Dynamic Searching Transient Parameters">
    @Transient
    private String columnName;
    @Transient
    private String columnValue;
    //</editor-fold >
    @Column(name = "INTEREST")
    private BigInteger interest;
    @Column(name = "INTEREST_RATE")
    private Double interestRate;
    @Column(name = "INTEREST_REPAYMENT_START_DATE")
    @Temporal(TemporalType.DATE)
    private Date interestRepaymentStartDate;
    @Column(name = "INTEREST_REPAYMENT_END_DATE")
    @Temporal(TemporalType.DATE)
    private Date interestRepaymentEndDate;
    @Column(name = "PRINCIPAL_REPAYMENT_START_DATE")
    @Temporal(TemporalType.DATE)
    private Date principalRepaymentStartDate;
    @Column(name = "PRINCIPAL_REPAYMENT_END_DATE")
    @Temporal(TemporalType.DATE)
    private Date principalRepaymentEndDate;
    @Column(name = "TERMINATION_REFERENCE_NO")
    private String terminationReferenceNo;
    @Column(name = "TERMINATION_DATE")
    @Temporal(TemporalType.DATE)
    private Date terminationDate;
    @Column(name = "STATUS")
    private String status;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "couponExtend")
    private List<FmsBondCouponExtend> fmsBondCouponExtendList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "BondCouponNo")
    private List<FmsBondCouponSchedule> fmsBondCouponScheduleList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "BondCouponId")
    private List<FmsBondSingleCoupon> fmsBondSingleCouponList;
    @OneToMany(mappedBy = "couponNo", cascade = CascadeType.PERSIST)
    private List<FmsBondCouponInterestPaymt> fmsBondCouponInterestPaymtList;
    @Transient
    int extendby;
    @Transient
    String ChangedStatus;

//</editor-fold>
    public FmsBondCoupon() {
    }

    //<editor-fold defaultstate="collapsed" desc="Getter and Setter">
    public FmsBondCoupon(Integer couponId) {
        this.couponId = couponId;
    }

    public Integer getCouponId() {
        return couponId;
    }

    public void setCouponId(Integer couponId) {
        this.couponId = couponId;
    }

    public Integer getNumberOfBond() {
        return numberOfBond;
    }

    public void setNumberOfBond(Integer numberOfBond) {
        this.numberOfBond = numberOfBond;
    }

    public Double getBondValue() {
        return BondValue;
    }

    public void setBondValue(Double BondValue) {
        this.BondValue = BondValue;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnValue() {
        return columnValue;
    }

    public void setColumnValue(String columnValue) {
        this.columnValue = columnValue;
    }

    public Integer getBondMaturity() {
        return BondMaturity;
    }

    public void setBondMaturity(Integer BondMaturity) {
        this.BondMaturity = BondMaturity;
    }

    public Integer getGracePeriod() {
        return gracePeriod;
    }

    public void setGracePeriod(Integer gracePeriod) {
        this.gracePeriod = gracePeriod;
    }

    public Double getTotalBondValue() {
        return totalBondValue;
    }

    public void setTotalBondValue(Double totalBondValue) {
        this.totalBondValue = totalBondValue;
    }

    public String getBondNo() {
        return BondNo;
    }

    public void setBondNo(String BondNo) {
        this.BondNo = BondNo;
    }

    public Double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(Double interestRate) {
        this.interestRate = interestRate;
    }

    public Date getBondIssuedDate() {
        return BondIssuedDate;
    }

    public void setBondIssuedDate(Date BondIssuedDate) {
        this.BondIssuedDate = BondIssuedDate;
    }

    public Date getInterestRepaymentStartDate() {
        return interestRepaymentStartDate;
    }

    public void setInterestRepaymentStartDate(Date interestRepaymentStartDate) {
        this.interestRepaymentStartDate = interestRepaymentStartDate;
    }

    public Date getInterestRepaymentEndDate() {
        return interestRepaymentEndDate;
    }

    public void setInterestRepaymentEndDate(Date interestRepaymentEndDate) {
        this.interestRepaymentEndDate = interestRepaymentEndDate;
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

    public String getTerminationReferenceNo() {
        return terminationReferenceNo;
    }

    public void setTerminationReferenceNo(String terminationReferenceNo) {
        this.terminationReferenceNo = terminationReferenceNo;
    }

    public Date getTerminationDate() {
        return terminationDate;
    }

    public void setTerminationDate(Date terminationDate) {
        this.terminationDate = terminationDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getExtendby() {
        return extendby;
    }

    public void setExtendby(int extendby) {
        this.extendby = extendby;
    }

    public String getChangedStatus() {
        return ChangedStatus;
    }

    public void setChangedStatus(String ChangedStatus) {
        this.ChangedStatus = ChangedStatus;
    }
//</editor-fold>

    public void addToFmsBondCouponExtend(FmsBondCouponExtend fmsBondCouponExtendDetail) {
        this.getFmsBondCouponExtendList().add(fmsBondCouponExtendDetail);
        fmsBondCouponExtendDetail.setCouponExtend(this);
    }

    public void addToFmsBondSingleCoupon(FmsBondSingleCoupon fmsBondSingleCouponDetail) {
        if (fmsBondSingleCouponDetail.getBondCouponId() != this) {
            this.getFmsBondSingleCouponList().add(fmsBondSingleCouponDetail);
            fmsBondSingleCouponDetail.setBondCouponId(this);
        }
    }

    public void addToFmsBondCouponSchedule(FmsBondCouponSchedule fmsBondCouponScheduleDetail) {
        this.getFmsBondCouponScheduleList().add(fmsBondCouponScheduleDetail);
        fmsBondCouponScheduleDetail.setBondCouponNo(this);
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (couponId != null ? couponId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof FmsBondCoupon)) {
            return false;
        }
        FmsBondCoupon other = (FmsBondCoupon) object;
        if ((this.couponId == null && other.couponId != null) || (this.couponId != null && !this.couponId.equals(other.couponId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return BondNo;
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
    public List<FmsBondCouponSchedule> getFmsBondCouponScheduleList() {
        if (fmsBondCouponScheduleList == null) {
            fmsBondCouponScheduleList = new ArrayList<>();
        }
        return fmsBondCouponScheduleList;
    }

    public void setFmsBondCouponScheduleList(List<FmsBondCouponSchedule> fmsBondCouponScheduleList) {
        this.fmsBondCouponScheduleList = fmsBondCouponScheduleList;
    }

    @XmlTransient
    public List<FmsBondSingleCoupon> getFmsBondSingleCouponList() {
        if (fmsBondSingleCouponList == null) {
            fmsBondSingleCouponList = new ArrayList<>();
        }
        return fmsBondSingleCouponList;
    }

    public void setFmsBondSingleCouponList(List<FmsBondSingleCoupon> fmsBondSingleCouponList) {
        this.fmsBondSingleCouponList = fmsBondSingleCouponList;
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
}
