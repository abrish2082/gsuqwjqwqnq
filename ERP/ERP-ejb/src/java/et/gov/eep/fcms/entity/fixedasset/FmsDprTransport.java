/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.entity.fixedasset;
import et.gov.eep.fcms.entity.admin.FmsAccountingPeriod;
import et.gov.eep.mms.entity.MmsFaTransport;
import java.io.Serializable;
import java.math.BigDecimal;
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
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Binyam
 */
@Entity
@Table(name = "FMS_DPR_TRANSPORT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FmsDprTransport.findAll", query = "SELECT f FROM FmsDprTransport f"),
    @NamedQuery(name = "FmsDprTransport.findByDprTransportId", query = "SELECT f FROM FmsDprTransport f WHERE f.dprTransportId = :dprTransportId"),
    @NamedQuery(name = "FmsDprTransport.findByRevaluationCost", query = "SELECT f FROM FmsDprTransport f WHERE f.revaluationCost = :revaluationCost"),
    @NamedQuery(name = "FmsDprTransport.findByAccumulatedDpr", query = "SELECT f FROM FmsDprTransport f WHERE f.accumulatedDpr = :accumulatedDpr"),
    @NamedQuery(name = "FmsDprTransport.findByNetBookValue", query = "SELECT f FROM FmsDprTransport f WHERE f.netBookValue = :netBookValue"),
    @NamedQuery(name = "FmsDprTransport.findByDprYear", query = "SELECT f FROM FmsDprTransport f WHERE f.dprYear = :dprYear"),
    @NamedQuery(name = "FmsDprTransport.findByStatus", query = "SELECT f FROM FmsDprTransport f WHERE f.status = :status"),
    @NamedQuery(name = "FmsDprTransport.findByRevaluationServiceYear", query = "SELECT f FROM FmsDprTransport f WHERE f.revaluationServiceYear = :revaluationServiceYear"),
    @NamedQuery(name = "FmsDprTransport.findByDataCardNo", query = "SELECT f FROM FmsDprTransport f WHERE f.dataCardNo = :dataCardNo")})
public class FmsDprTransport implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "DPR_TRANSPORT_ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FMS_DPR_TRANSPORT_SEQ")
    @SequenceGenerator(name = "FMS_DPR_TRANSPORT_SEQ", sequenceName = "FMS_DPR_TRANSPORT_SEQ", allocationSize = 1)
    private Integer dprTransportId;
    @Column(name = "REVALUATION_COST")
    private BigDecimal revaluationCost;
    @Column(name = "ACCUMULATED_DPR")
    private BigDecimal accumulatedDpr;
    @Column(name = "NET_BOOK_VALUE")
    private BigDecimal netBookValue;
    @Column(name = "DPR_YEAR")
    private BigDecimal dprYear;
    @Column(name = "REVALUATION_SERVICE_YEAR")
    private Integer revaluationServiceYear;   
    @Column(name = "DATA_CARD_NO")
    private String dataCardNo;
    @JoinColumn(name = "TP_ASSET_ID", referencedColumnName = "TRANSPORT_ID")
    @ManyToOne
    private MmsFaTransport tpAssetId;
    @Column(name = "STATUS")
    private Integer status;
    @JoinColumn(name = "ACCOUNT_PERIOD", referencedColumnName = "ACOUNTIG_PERIOD_ID")
    @ManyToOne
    private FmsAccountingPeriod accountPeriod;

    /**
     *
     */
    public FmsDprTransport() {
    }

    /**
     *
     * @param dprTransportId
     */
    public FmsDprTransport(Integer dprTransportId) {
        this.dprTransportId = dprTransportId;
    }

    /**
     *
     * @return
     */
    public Integer getDprTransportId() {
        return dprTransportId;
    }

    /**
     *
     * @param dprTransportId
     */
    public void setDprTransportId(Integer dprTransportId) {
        this.dprTransportId = dprTransportId;
    }

    public FmsAccountingPeriod getAccountPeriod() {
        return accountPeriod;
    }

    public void setAccountPeriod(FmsAccountingPeriod accountPeriod) {
        this.accountPeriod = accountPeriod;
    }

    public BigDecimal getRevaluationCost() {
        return revaluationCost;
    }

    /**
     *
     * @param revaluationCost
     */
    public void setRevaluationCost(BigDecimal revaluationCost) {
        this.revaluationCost = revaluationCost;
    }

    /**
     *
     * @return
     */
    public BigDecimal getAccumulatedDpr() {
        return accumulatedDpr;
    }

    /**
     *
     * @param accumulatedDpr
     */
    public void setAccumulatedDpr(BigDecimal accumulatedDpr) {
        this.accumulatedDpr = accumulatedDpr;
    }

    /**
     *
     * @return
     */
    public BigDecimal getNetBookValue() {
        return netBookValue;
    }

    /**
     *
     * @param netBookValue
     */
    public void setNetBookValue(BigDecimal netBookValue) {
        this.netBookValue = netBookValue;
    }

    /**
     *
     * @return
     */
    public BigDecimal getDprYear() {
        return dprYear;
    }

    /**
     *
     * @param dprYear
     */
    public void setDprYear(BigDecimal dprYear) {
        this.dprYear = dprYear;
    }

    /**
     *
     * @return
     */
    public Integer getRevaluationServiceYear() {
        return revaluationServiceYear;
    }

    /**
     *
     * @param revaluationServiceYear
     */
    public void setRevaluationServiceYear(Integer revaluationServiceYear) {
        this.revaluationServiceYear = revaluationServiceYear;
    }

    public String getDataCardNo() {
        return dataCardNo;
    }

    /**
     *
     * @param dataCardNo
     */
    public void setDataCardNo(String dataCardNo) {
        this.dataCardNo = dataCardNo;
    }

    /**
     *
     * @return
     */
    public MmsFaTransport getTpAssetId() {
        return tpAssetId;
    }

    /**
     *
     * @param tpAssetId
     */
    public void setTpAssetId(MmsFaTransport tpAssetId) {
        this.tpAssetId = tpAssetId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (dprTransportId != null ? dprTransportId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FmsDprTransport)) {
            return false;
        }
        FmsDprTransport other = (FmsDprTransport) object;
        if ((this.dprTransportId == null && other.dprTransportId != null) || (this.dprTransportId != null && !this.dprTransportId.equals(other.dprTransportId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.eep.fcms.entity.FmsDprTransport[ dprTransportId=" + dprTransportId + " ]";
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
