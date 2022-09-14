/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.entity.fixedasset;




import et.gov.eep.fcms.entity.admin.FmsAccountingPeriod;
import et.gov.eep.fcms.entity.admin.FmsGeneralLedger;
import et.gov.eep.mms.entity.MmsFaGeothermal;
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
@Table(name = "FMS_DPR_GEOTHERMAL")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FmsDprGeothermal.findAll", query = "SELECT f FROM FmsDprGeothermal f"),
    @NamedQuery(name = "FmsDprGeothermal.findByDprGeothermalId", query = "SELECT f FROM FmsDprGeothermal f WHERE f.dprGeothermalId = :dprGeothermalId"),
    @NamedQuery(name = "FmsDprGeothermal.findByRevaluationCost", query = "SELECT f FROM FmsDprGeothermal f WHERE f.revaluationCost = :revaluationCost"),
    @NamedQuery(name = "FmsDprGeothermal.findByAccumulatedDpr", query = "SELECT f FROM FmsDprGeothermal f WHERE f.accumulatedDpr = :accumulatedDpr"),
    @NamedQuery(name = "FmsDprGeothermal.findByNetBookValue", query = "SELECT f FROM FmsDprGeothermal f WHERE f.netBookValue = :netBookValue"),
    @NamedQuery(name = "FmsDprGeothermal.findByDprYear", query = "SELECT f FROM FmsDprGeothermal f WHERE f.dprYear = :dprYear"),
    @NamedQuery(name = "FmsDprGeothermal.findByStatus", query = "SELECT f FROM FmsDprGeothermal f WHERE f.status = :status"),
    @NamedQuery(name = "FmsDprGeothermal.findByRevaluationServiceYear", query = "SELECT f FROM FmsDprGeothermal f WHERE f.revaluationServiceYear = :revaluationServiceYear"),
    @NamedQuery(name = "FmsDprGeothermal.findByDataCardNo", query = "SELECT f FROM FmsDprGeothermal f WHERE f.dataCardNo = :dataCardNo")})
public class FmsDprGeothermal implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "DPR_GEOTHERMAL_ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FMS_DPR_GEOTHERMAL_SEQ")
    @SequenceGenerator(name = "FMS_DPR_GEOTHERMAL_SEQ", sequenceName = "FMS_DPR_GEOTHERMAL_SEQ", allocationSize = 1)
    private Integer dprGeothermalId;
    @Column(name = "REVALUATION_COST")
    private BigDecimal revaluationCost;
    @Column(name = "ACCUMULATED_DPR")
    private BigDecimal accumulatedDpr;
    @Column(name = "NET_BOOK_VALUE")
    private BigDecimal netBookValue;
    @Column(name = "DPR_YEAR")
    private BigDecimal dprYear;   
    @JoinColumn(name = "ACCOUNT_PERIOD", referencedColumnName = "ACOUNTIG_PERIOD_ID")
    @ManyToOne
    private FmsAccountingPeriod accountPeriod;
    @JoinColumn(name = "ACCOUNT_NO", referencedColumnName = "GENERAL_LEDGER_ID")
    @ManyToOne
    private FmsGeneralLedger accountNo;    
    @Column(name = "REVALUATION_SERVICE_YEAR")
    private Integer revaluationServiceYear;    
    @Column(name = "DATA_CARD_NO")
    private String dataCardNo;
    @Column(name = "STATUS")
    private Integer status;
    @JoinColumn(name = "GEO_ASSET_ID", referencedColumnName = "GEOTHERMAL_ID")
    @ManyToOne
    private MmsFaGeothermal geoAssetId;

    /**
     *
     */
    public FmsDprGeothermal() {
    }

    /**
     *
     * @param dprGeothermalId
     */
    public FmsDprGeothermal(Integer dprGeothermalId) {
        this.dprGeothermalId = dprGeothermalId;
    }

    /**
     *
     * @return
     */
    public Integer getDprGeothermalId() {
        return dprGeothermalId;
    }

    /**
     *
     * @param dprGeothermalId
     */
    public void setDprGeothermalId(Integer dprGeothermalId) {
        this.dprGeothermalId = dprGeothermalId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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
    public MmsFaGeothermal getGeoAssetId() {
        return geoAssetId;
    }

    /**
     *
     * @param geoAssetId
     */
    public void setGeoAssetId(MmsFaGeothermal geoAssetId) {
        this.geoAssetId = geoAssetId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (dprGeothermalId != null ? dprGeothermalId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FmsDprGeothermal)) {
            return false;
        }
        FmsDprGeothermal other = (FmsDprGeothermal) object;
        if ((this.dprGeothermalId == null && other.dprGeothermalId != null) || (this.dprGeothermalId != null && !this.dprGeothermalId.equals(other.dprGeothermalId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.eep.fcms.entity.FmsDprGeothermal[ dprGeothermalId=" + dprGeothermalId + " ]";
    }
    
}
