/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.entity.fixedasset;

import et.gov.eep.fcms.entity.admin.FmsAccountingPeriod;
import et.gov.eep.fcms.entity.admin.FmsGeneralLedger;
import et.gov.eep.mms.entity.MmsFaBuilding;
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
@Table(name = "FMS_DPR_BUILDING")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FmsDprBuilding.findAll", query = "SELECT f FROM FmsDprBuilding f"),
    @NamedQuery(name = "FmsDprBuilding.findByDprBuildingId", query = "SELECT f FROM FmsDprBuilding f WHERE f.dprBuildingId = :dprBuildingId"),
    @NamedQuery(name = "FmsDprBuilding.findByRevaluationCost", query = "SELECT f FROM FmsDprBuilding f WHERE f.revaluationCost = :revaluationCost"),
    @NamedQuery(name = "FmsDprBuilding.findByAccumulatedDpr", query = "SELECT f FROM FmsDprBuilding f WHERE f.accumulatedDpr = :accumulatedDpr"),
    @NamedQuery(name = "FmsDprBuilding.findByNetBookValue", query = "SELECT f FROM FmsDprBuilding f WHERE f.netBookValue = :netBookValue"),
    @NamedQuery(name = "FmsDprBuilding.findByDprYear", query = "SELECT f FROM FmsDprBuilding f WHERE f.dprYear = :dprYear"),
    @NamedQuery(name = "FmsDprBuilding.findByRevaluationServiceYear", query = "SELECT f FROM FmsDprBuilding f WHERE f.revaluationServiceYear = :revaluationServiceYear"),
    @NamedQuery(name = "FmsDprBuilding.findByStatus", query = "SELECT f FROM FmsDprBuilding f WHERE f.status = :status"),
    @NamedQuery(name = "FmsDprBuilding.findByDataCardNo", query = "SELECT f FROM FmsDprBuilding f WHERE f.dataCardNo = :dataCardNo")})
public class FmsDprBuilding implements Serializable {

    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "DPR_BUILDING_ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FMS_DPR_BUILDING_SEQ")
    @SequenceGenerator(name = "FMS_DPR_BUILDING_SEQ", sequenceName = "FMS_DPR_BUILDING_SEQ", allocationSize = 1)
    private Integer dprBuildingId;
    @Column(name = "ACCUMULATED_DPR")
    private BigDecimal accumulatedDpr;
    @Column(name = "NET_BOOK_VALUE")
    private BigDecimal netBookValue;
    @Column(name = "DPR_YEAR")
    private BigDecimal dprYear;
    @Column(name = "STATUS")
    private Integer status;
    private static final long serialVersionUID = 1L;
    @Column(name = "REVALUATION_COST")
    private BigDecimal revaluationCost;    
    @Column(name = "REVALUATION_SERVICE_YEAR")
    private Integer revaluationServiceYear;
    @Column(name = "DATA_CARD_NO")
    private String dataCardNo;
    @JoinColumn(name = "BU_ASSET_ID", referencedColumnName = "BUILDING_ID")
    @ManyToOne
    private MmsFaBuilding buAssetId;
    @JoinColumn(name = "ACCOUNT_PERIOD", referencedColumnName = "ACOUNTIG_PERIOD_ID")
    @ManyToOne
    private FmsAccountingPeriod accountPeriod;
    @JoinColumn(name = "ACCOUNT_NO", referencedColumnName = "GENERAL_LEDGER_ID")
    @ManyToOne
    private FmsGeneralLedger accountNo;


    public FmsDprBuilding() {
    }

   
    public FmsDprBuilding(Integer dprBuildingId) {
        this.dprBuildingId = dprBuildingId;
    }

    public Integer getDprBuildingId() {
        return dprBuildingId;
    }

    public void setDprBuildingId(Integer dprBuildingId) {
        this.dprBuildingId = dprBuildingId;
    }

    public BigDecimal getAccumulatedDpr() {
        return accumulatedDpr;
    }

    public void setAccumulatedDpr(BigDecimal accumulatedDpr) {
        this.accumulatedDpr = accumulatedDpr;
    }

    public BigDecimal getNetBookValue() {
        return netBookValue;
    }

    public void setNetBookValue(BigDecimal netBookValue) {
        this.netBookValue = netBookValue;
    }

    public BigDecimal getDprYear() {
        return dprYear;
    }

    public void setDprYear(BigDecimal dprYear) {
        this.dprYear = dprYear;
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

    public void setRevaluationCost(BigDecimal revaluationCost) {
        this.revaluationCost = revaluationCost;
    }

    public Integer getRevaluationServiceYear() {
        return revaluationServiceYear;
    }

    public void setRevaluationServiceYear(Integer revaluationServiceYear) {
        this.revaluationServiceYear = revaluationServiceYear;
    }

    public String getDataCardNo() {
        return dataCardNo;
    }

    public void setDataCardNo(String dataCardNo) {
        this.dataCardNo = dataCardNo;
    }

    public MmsFaBuilding getBuAssetId() {
        return buAssetId;
    }

    public void setBuAssetId(MmsFaBuilding buAssetId) {
        this.buAssetId = buAssetId;
    }

    public FmsAccountingPeriod getAccountPeriod() {
        return accountPeriod;
    }

    public void setAccountPeriod(FmsAccountingPeriod accountPeriod) {
        this.accountPeriod = accountPeriod;
    }

    public FmsGeneralLedger getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(FmsGeneralLedger accountNo) {
        this.accountNo = accountNo;
    }

    
    

    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (dprBuildingId != null ? dprBuildingId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FmsDprBuilding)) {
            return false;
        }
        FmsDprBuilding other = (FmsDprBuilding) object;
        if ((this.dprBuildingId == null && other.dprBuildingId != null) || (this.dprBuildingId != null && !this.dprBuildingId.equals(other.dprBuildingId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.eep.fcms.entity.FmsDprBuilding[ dprBuildingId=" + dprBuildingId + " ]";
    }

}
