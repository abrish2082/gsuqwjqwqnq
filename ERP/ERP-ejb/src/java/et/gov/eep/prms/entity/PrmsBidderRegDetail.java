package et.gov.eep.prms.entity;

import et.gov.eep.fcms.entity.Vocher.FmsCashReceiptVoucher;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author user
 */
@Entity
@Table(name = "PRMS_BIDDER_REG_DETAIL")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PrmsBidderRegDetail.findAll", query = "SELECT p FROM PrmsBidderRegDetail p"),
    @NamedQuery(name = "PrmsBidderRegDetail.findByBidderDetailId", query = "SELECT p FROM PrmsBidderRegDetail p WHERE p.bidderDetailId = :bidderDetailId"),
    @NamedQuery(name = "PrmsBidderRegDetail.findByDateSub", query = "SELECT p FROM PrmsBidderRegDetail p WHERE p.dateSub = :dateSub"),
    @NamedQuery(name = "PrmsBidderRegDetail.findByTimeSub", query = "SELECT p FROM PrmsBidderRegDetail p WHERE p.timeSub = :timeSub")})
public class PrmsBidderRegDetail implements Serializable {
//  @JoinColumn(name = "CASH_RECEIPT_VOUCHER_ID", referencedColumnName = "CASH_RECEIPT_VOUCHER_ID")
//    @ManyToOne(fetch = FetchType.LAZY)
//    private FmsCashReceiptVoucher cashReceiptVoucherId;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PRMS_BIDDER_DETAIL_SQUEN")
    @SequenceGenerator(name = "PRMS_BIDDER_DETAIL_SQUEN", sequenceName = "PRMS_BIDDER_DETAIL_SQUEN", allocationSize = 1)
    @Column(name = "BIDDER_DETAIL_ID")
    private BigDecimal bidderDetailId;
    @Column(name = "DATE_SUB")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateSub;
    @Column(name = "TIME_SUB")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timeSub;

    @Size(max = 50)
    @Column(name = "LOT_NAME")
    private String lotName;

    @JoinColumn(name = "BIDDER_REG_ID", referencedColumnName = "BIDDER_REG_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private PrmsBidderRegistration bidderRegId;
    @JoinColumn(name = "SUPP_ID", referencedColumnName = "ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private PrmsSupplyProfile suppId;
    @JoinColumn(name = "BID_DET_ID", referencedColumnName = "ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private PrmsBidDetail bidDetId;
    @JoinColumn(name = "BID_SALE_ID", referencedColumnName = "CASH_RECEIPT_VOUCHER_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private FmsCashReceiptVoucher cashReceiptVoucher;
//    @JoinColumn(name = "EMPLOYEE_ID", referencedColumnName = "ID")
//    @ManyToOne(fetch = FetchType.LAZY)
//    private HrEmployees employeeId;

    public PrmsBidderRegDetail() {
    }

//    public HrEmployees getEmployeeId() {
//        return employeeId;
//    }
//
//    public void setEmployeeId(HrEmployees employeeId) {
//        this.employeeId = employeeId;
//    }
    public PrmsBidderRegDetail(BigDecimal bidderDetailId) {
        this.bidderDetailId = bidderDetailId;
    }

    public BigDecimal getBidderDetailId() {
        return bidderDetailId;
    }

    public void setBidderDetailId(BigDecimal bidderDetailId) {
        this.bidderDetailId = bidderDetailId;
    }

    public Date getDateSub() {
        return dateSub;
    }

    public void setDateSub(Date dateSub) {
        this.dateSub = dateSub;
    }

    public Date getTimeSub() {
        return timeSub;
    }

    public void setTimeSub(Date timeSub) {
        this.timeSub = timeSub;
    }

    public PrmsBidderRegistration getBidderRegId() {
        return bidderRegId;
    }

    public void setBidderRegId(PrmsBidderRegistration bidderRegId) {
        this.bidderRegId = bidderRegId;
    }

    public PrmsSupplyProfile getSuppId() {
        return suppId;
    }

    public void setSuppId(PrmsSupplyProfile suppId) {
        this.suppId = suppId;
    }
//      public FmsCashReceiptVoucher getCashReceiptVoucherId() {
//        return cashReceiptVoucherId;
//    }
//
//    public void setCashReceiptVoucherId(FmsCashReceiptVoucher cashReceiptVoucherId) {
//        this.cashReceiptVoucherId = cashReceiptVoucherId;
//    }

    public FmsCashReceiptVoucher getCashReceiptVoucher() {
        return cashReceiptVoucher;
    }

    public void setCashReceiptVoucher(FmsCashReceiptVoucher cashReceiptVoucher) {
        this.cashReceiptVoucher = cashReceiptVoucher;
    }

    public String getLotName() {
        return lotName;
    }

    public void setLotName(String lotName) {
        this.lotName = lotName;
    }

    public PrmsBidDetail getBidDetId() {
        return bidDetId;
    }

    public void setBidDetId(PrmsBidDetail bidDetId) {
        this.bidDetId = bidDetId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (bidderDetailId != null ? bidderDetailId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PrmsBidderRegDetail)) {
            return false;
        }
        PrmsBidderRegDetail other = (PrmsBidderRegDetail) object;
        if ((this.bidderDetailId == null && other.bidderDetailId != null) || (this.bidderDetailId != null && !this.bidderDetailId.equals(other.bidderDetailId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.eep.prms.entity.PrmsBidderRegDetail[ bidderDetailId=" + bidderDetailId + " ]";
    }

}
