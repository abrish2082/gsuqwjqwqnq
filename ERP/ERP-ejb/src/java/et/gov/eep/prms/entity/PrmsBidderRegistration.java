package et.gov.eep.prms.entity;

import et.gov.eep.commonApplications.entity.WfPrmsProcessed;
import et.gov.eep.fcms.entity.FmsBidSale;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
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
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name = "PRMS_BIDDER_REGISTRATION")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PrmsBidderRegistration.findAll", query = "SELECT p FROM PrmsBidderRegistration p"),
    @NamedQuery(name = "PrmsBidderRegistration.findAllByStatus", query = "SELECT p FROM PrmsBidderRegistration p WHERE p.status=0"),
    @NamedQuery(name = "PrmsBidderRegistration.findByBidderRegId", query = "SELECT p FROM PrmsBidderRegistration p WHERE p.bidderRegId = :bidderRegId"),
    @NamedQuery(name = "PrmsBidderRegistration.findByDateRegistered", query = "SELECT p FROM PrmsBidderRegistration p WHERE p.dateRegistered = :dateRegistered"),
    @NamedQuery(name = "PrmsBidderRegistration.findByMaxBidderNum", query = "SELECT p FROM PrmsBidderRegistration p WHERE p.bidderRegId = (SELECT Max(c.bidderRegId)  from PrmsBidderRegistration c)"),
  @NamedQuery(name = "PrmsBidderRegistration.findByPreparedBy", query = "SELECT p FROM PrmsBidderRegistration p WHERE p.preparedBy = :preparedBy"),
    @NamedQuery(name = "PrmsBidderRegistration.findByBidderNo", query = "SELECT p FROM PrmsBidderRegistration p WHERE p.bidderNo LIKE :bidderNo"),

    @NamedQuery(name = "PrmsBidderRegistration.findByBidderType", query = "SELECT p FROM PrmsBidderRegistration p WHERE p.bidderType = :bidderType")})
public class PrmsBidderRegistration implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PRMS_BIDDER_REG_SQUENCE")
    @SequenceGenerator(name = "PRMS_BIDDER_REG_SQUENCE", sequenceName = "PRMS_BIDDER_REG_SQUENCE", allocationSize = 1)
    @Column(name = "BIDDER_REG_ID")
    private BigDecimal bidderRegId;
    @Column(name = "DATE_REGISTERED")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateRegistered;
    @Size(max = 100)
    @Column(name = "BIDDER_NO")
    private String bidderNo;
    @Size(max = 100)
    @Column(name = "BIDDER_TYPE")
    private String bidderType;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "bidderRegId", fetch = FetchType.LAZY)
    private List<PrmsBidderRegDetail> prmsBidderRegDetailList;
    @JoinColumn(name = "BID_ID", referencedColumnName = "ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private PrmsBid bidId;
//    @JoinColumn(name = "BID_SALE_ID", referencedColumnName = "BID_SALE_ID")
//    @ManyToOne(fetch = FetchType.LAZY)
//    private FmsBidSale bidSaleId;
    @Transient
    private String columnName;
    @Transient
    private String columnValue;
    @Column(name = "FEXFILEREFNUMBER")
    private BigInteger fexfilerefnumber;
    @Column(name = "STATUS")
    private Integer status;
    @Column(name = "CURRENT_STATUS")
    private Integer currentStatus;
    @Column(name = "PREPARED_BY")
    private Integer preparedBy;
    @OneToMany(mappedBy = "bidderRegId", cascade = CascadeType.ALL)
    private List<WfPrmsProcessed> prmsWorkFlowProccedList;

    public PrmsBidderRegistration() {
    }

    public PrmsBidderRegistration(BigDecimal bidderRegId) {
        this.bidderRegId = bidderRegId;
    }

    public BigDecimal getBidderRegId() {
        return bidderRegId;
    }

    public void setBidderRegId(BigDecimal bidderRegId) {
        this.bidderRegId = bidderRegId;
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

    public Date getDateRegistered() {
        return dateRegistered;
    }

    public void setDateRegistered(Date dateRegistered) {
        this.dateRegistered = dateRegistered;
    }

    public String getBidderNo() {
        return bidderNo;
    }

    public void setBidderNo(String bidderNo) {
        this.bidderNo = bidderNo;
    }

    public String getBidderType() {
        return bidderType;
    }

    public void setBidderType(String bidderType) {
        this.bidderType = bidderType;
    }

    public PrmsBid getBidId() {
        return bidId;
    }

    public void setBidId(PrmsBid bidId) {
        this.bidId = bidId;
    }

    public Integer getPreparedBy() {
        return preparedBy;
    }

    public void setPreparedBy(Integer preparedBy) {
        this.preparedBy = preparedBy;
    }

//    public FmsBidSale getBidSaleId() {
//        return bidSaleId;
//    }
//
//    public void setBidSaleId(FmsBidSale bidSaleId) {
//        this.bidSaleId = bidSaleId;
//    }

    @XmlTransient
    public List<PrmsBidderRegDetail> getPrmsBidderRegDetailList() {
        if (prmsBidderRegDetailList == null) {
            prmsBidderRegDetailList = new ArrayList<>();
        }
        return prmsBidderRegDetailList;
    }

    public void setPrmsBidderRegDetailList(List<PrmsBidderRegDetail> prmsBidderRegDetailList) {
        this.prmsBidderRegDetailList = prmsBidderRegDetailList;
    }

    public BigInteger getFexfilerefnumber() {
        return fexfilerefnumber;
    }

    public void setFexfilerefnumber(BigInteger fexfilerefnumber) {
        this.fexfilerefnumber = fexfilerefnumber;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(Integer currentStatus) {
        this.currentStatus = currentStatus;
    }

    public List<WfPrmsProcessed> getPrmsWorkFlowProccedList() {
        if (prmsWorkFlowProccedList == null) {
            prmsWorkFlowProccedList = new ArrayList<>();
        }
        return prmsWorkFlowProccedList;
    }

    public void setPrmsWorkFlowProccedList(List<WfPrmsProcessed> prmsWorkFlowProccedList) {
        this.prmsWorkFlowProccedList = prmsWorkFlowProccedList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (bidderRegId != null ? bidderRegId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PrmsBidderRegistration)) {
            return false;
        }
        PrmsBidderRegistration other = (PrmsBidderRegistration) object;
        if ((this.bidderRegId == null && other.bidderRegId != null) || (this.bidderRegId != null && !this.bidderRegId.equals(other.bidderRegId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.eep.prms.entity.PrmsBidderRegistration[ bidderRegId=" + bidderRegId + " ]";
    }

    public void addBidderDDEtial(PrmsBidderRegDetail prmsBidderRegDetail) {
        if (prmsBidderRegDetail.getBidderRegId() != this) {
            this.getPrmsBidderRegDetailList().add(prmsBidderRegDetail);
            prmsBidderRegDetail.setBidderRegId(this);
        }

    }

}
