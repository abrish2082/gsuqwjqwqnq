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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author mz
 */
@Entity
@Table(name = "FMS_BOND_SINGLE_COUPON")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FmsBondSingleCoupon.findAll", query = "SELECT f FROM FmsBondSingleCoupon f"),
    @NamedQuery(name = "FmsBondSingleCoupon.findBySingleCouponId", query = "SELECT f FROM FmsBondSingleCoupon f WHERE f.singleCouponId = :singleCouponId"),
    @NamedQuery(name = "FmsBondSingleCoupon.findBySerialNo", query = "SELECT f FROM FmsBondSingleCoupon f WHERE f.serialNo = :serialNo"),
    @NamedQuery(name = "FmsBondSingleCoupon.findByBondValue", query = "SELECT f FROM FmsBondSingleCoupon f WHERE f.BondValue = :BondValue"),
    @NamedQuery(name = "FmsBondSingleCoupon.findByBondMaturity", query = "SELECT f FROM FmsBondSingleCoupon f WHERE f.BondMaturity = :BondMaturity"),
    @NamedQuery(name = "FmsBondSingleCoupon.findByGracePeriod", query = "SELECT f FROM FmsBondSingleCoupon f WHERE f.gracePeriod = :gracePeriod"),
    @NamedQuery(name = "FmsBondSingleCoupon.findByBondIssuedDate", query = "SELECT f FROM FmsBondSingleCoupon f WHERE f.BondIssuedDate = :BondIssuedDate"),
    @NamedQuery(name = "FmsBondSingleCoupon.findByRepaymentStartDate", query = "SELECT f FROM FmsBondSingleCoupon f WHERE f.repaymentStartDate = :repaymentStartDate"),
    @NamedQuery(name = "FmsBondSingleCoupon.findByRepaymentEndDate", query = "SELECT f FROM FmsBondSingleCoupon f WHERE f.repaymentEndDate = :repaymentEndDate"),
    @NamedQuery(name = "FmsBondSingleCoupon.findByStatus", query = "SELECT f FROM FmsBondSingleCoupon f WHERE f.status = :status")})
public class FmsBondSingleCoupon implements Serializable {

    private static final long serialVersionUID = 1L;
      //<editor-fold defaultstate="collapsed" desc="variable declaration">
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SINGLE_COUPON_ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FMS_BOND_SINGLE_COUPON_SEQ")
    @SequenceGenerator(name = "FMS_BOND_SINGLE_COUPON_SEQ", sequenceName = "FMS_BOND_SINGLE_COUPON_SEQ", allocationSize = 1)
    private Integer singleCouponId;
    @Column(name = "SERIAL_NO")
    private String serialNo;
    @Column(name = "BOND_VALUE")
    private Double BondValue;
    @Column(name = "BOND_MATURITY")
    private Integer BondMaturity;
    @Column(name = "GRACE_PERIOD")
    private Integer gracePeriod;
    @Column(name = "BOND_ISSUED_DATE")
    @Temporal(TemporalType.DATE)
    private Date BondIssuedDate;
    @Column(name = "REPAYMENT_START_DATE")
    @Temporal(TemporalType.DATE)
    private Date repaymentStartDate;
    @Column(name = "REPAYMENT_END_DATE")
    @Temporal(TemporalType.DATE)
    private Date repaymentEndDate;
    @Column(name = "STATUS")
    private String status;
    @JoinColumn(name = "BOND_COUPON_ID", referencedColumnName = "COUPON_ID")
    @ManyToOne
    private FmsBondCoupon BondCouponId;
//</editor-fold>
    public FmsBondSingleCoupon() {
    }
  //<editor-fold defaultstate="collapsed" desc="Getter and Setter">
    public FmsBondSingleCoupon(Integer singleCouponId) {
        this.singleCouponId = singleCouponId;
    }

    public Integer getSingleCouponId() {
        return singleCouponId;
    }

    public void setSingleCouponId(Integer singleCouponId) {
        this.singleCouponId = singleCouponId;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public Double getBondValue() {
        return BondValue;
    }

    public void setBondValue(Double BondValue) {
        this.BondValue = BondValue;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public FmsBondCoupon getBondCouponId() {
        return BondCouponId;
    }

    public void setBondCouponId(FmsBondCoupon BondCouponId) {
        this.BondCouponId = BondCouponId;
    }
//</editor-fold>
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (singleCouponId != null ? singleCouponId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof FmsBondSingleCoupon)) {
            return false;
        }
        FmsBondSingleCoupon other = (FmsBondSingleCoupon) object;
        if ((this.singleCouponId == null && other.singleCouponId != null) || (this.singleCouponId != null && !this.singleCouponId.equals(other.singleCouponId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.eep.fcms.entity.Bond.FmsBondSingleCoupon[ singleCouponId=" + singleCouponId + " ]";
    }

}
