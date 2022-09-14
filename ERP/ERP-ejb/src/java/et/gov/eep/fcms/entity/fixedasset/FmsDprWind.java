/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.entity.fixedasset;
import et.gov.eep.fcms.entity.admin.FmsAccountingPeriod;
import et.gov.eep.fcms.entity.admin.FmsGeneralLedger;
import et.gov.eep.mms.entity.MmsFaWind;
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
@Table(name = "FMS_DPR_WIND")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FmsDprWind.findAll", query = "SELECT f FROM FmsDprWind f"),
    @NamedQuery(name = "FmsDprWind.findByDprWindId", query = "SELECT f FROM FmsDprWind f WHERE f.dprWindId = :dprWindId"),
    @NamedQuery(name = "FmsDprWind.findByRevaluationCost", query = "SELECT f FROM FmsDprWind f WHERE f.revaluationCost = :revaluationCost"),
    @NamedQuery(name = "FmsDprWind.findByAccumulatedDpr", query = "SELECT f FROM FmsDprWind f WHERE f.accumulatedDpr = :accumulatedDpr"),
    @NamedQuery(name = "FmsDprWind.findByNetBookValue", query = "SELECT f FROM FmsDprWind f WHERE f.netBookValue = :netBookValue"),
    @NamedQuery(name = "FmsDprWind.findByDprYear", query = "SELECT f FROM FmsDprWind f WHERE f.dprYear = :dprYear"),
    @NamedQuery(name = "FmsDprWind.findByStatus", query = "SELECT f FROM FmsDprWind f WHERE f.status = :status"),
    @NamedQuery(name = "FmsDprWind.findByRevaluationServiceYear", query = "SELECT f FROM FmsDprWind f WHERE f.revaluationServiceYear = :revaluationServiceYear"),
    @NamedQuery(name = "FmsDprWind.findByDataCardNo", query = "SELECT f FROM FmsDprWind f WHERE f.dataCardNo = :dataCardNo")})
public class FmsDprWind implements Serializable {
    @JoinColumn(name = "ACCOUNT_PERIOD", referencedColumnName = "ACOUNTIG_PERIOD_ID")
    @ManyToOne
    private FmsAccountingPeriod accountPeriod;
    @Column(name = "STATUS")
    private Integer status;    
    @JoinColumn(name = "ACCOUNT_NO", referencedColumnName = "GENERAL_LEDGER_ID")
    @ManyToOne
    private FmsGeneralLedger accountNo;
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "DPR_WIND_ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FMS_DPR_WIND_SEQ")
    @SequenceGenerator(name = "FMS_DPR_WIND_SEQ", sequenceName = "FMS_DPR_WIND_SEQ", allocationSize = 1)
    private Integer dprWindId;
    @Column(name = "REVALUATION_COST")
    private BigDecimal revaluationCost;
    @Column(name = "ACCUMULATED_DPR")
    private BigDecimal accumulatedDpr;
    @Column(name = "NET_BOOK_VALUE")
    private BigDecimal netBookValue;
    @Column(name = "DPR_YEAR")
    private BigDecimal dprYear;
    @Column(name = "REVALUATION_SERVICE_YEAR")
    private BigDecimal revaluationServiceYear;    
    @Column(name = "DATA_CARD_NO")
    private String dataCardNo;
    @JoinColumn(name = "WD_ASSET_ID", referencedColumnName = "WIND_ID")
    @ManyToOne
    private MmsFaWind wdAssetId;

    /**
     *
     */
    public FmsDprWind() {
    }

    /**
     *
     * @param dprWindId
     */
    public FmsDprWind(Integer dprWindId) {
        this.dprWindId = dprWindId;
    }

    /**
     *
     * @return
     */
    public Integer getDprWindId() {
        return dprWindId;
    }

    /**
     *
     * @param dprWindId
     */
    public void setDprWindId(Integer dprWindId) {
        this.dprWindId = dprWindId;
    }

    /**
     *
     * @return
     */
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
    public BigDecimal getRevaluationServiceYear() {
        return revaluationServiceYear;
    }

    /**
     *
     * @param revaluationServiceYear
     */
    public void setRevaluationServiceYear(BigDecimal revaluationServiceYear) {
        this.revaluationServiceYear = revaluationServiceYear;
    }
    
    /**
     *
     * @return
     */
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
    public MmsFaWind getWdAssetId() {
        return wdAssetId;
    }

    /**
     *
     * @param wdAssetId
     */
    public void setWdAssetId(MmsFaWind wdAssetId) {
        this.wdAssetId = wdAssetId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (dprWindId != null ? dprWindId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FmsDprWind)) {
            return false;
        }
        FmsDprWind other = (FmsDprWind) object;
        if ((this.dprWindId == null && other.dprWindId != null) || (this.dprWindId != null && !this.dprWindId.equals(other.dprWindId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.eep.fcms.entity.FmsDprWind[ dprWindId=" + dprWindId + " ]";
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public FmsGeneralLedger getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(FmsGeneralLedger accountNo) {
        this.accountNo = accountNo;
    }

    public FmsAccountingPeriod getAccountPeriod() {
        return accountPeriod;
    }

    public void setAccountPeriod(FmsAccountingPeriod accountPeriod) {
        this.accountPeriod = accountPeriod;
    }

    

    
    
}
