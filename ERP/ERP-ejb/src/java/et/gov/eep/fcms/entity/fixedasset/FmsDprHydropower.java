/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.entity.fixedasset;

import et.gov.eep.fcms.entity.admin.FmsAccountingPeriod;
import et.gov.eep.fcms.entity.admin.FmsGeneralLedger;
import et.gov.eep.mms.entity.MmsFaHydropower;
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
@Table(name = "FMS_DPR_HYDROPOWER")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FmsDprHydropower.findAll", query = "SELECT f FROM FmsDprHydropower f"),
    @NamedQuery(name = "FmsDprHydropower.findByDprHydropowerId", query = "SELECT f FROM FmsDprHydropower f WHERE f.dprHydropowerId = :dprHydropowerId"),
    @NamedQuery(name = "FmsDprHydropower.findByRevaluationCost", query = "SELECT f FROM FmsDprHydropower f WHERE f.revaluationCost = :revaluationCost"),
    @NamedQuery(name = "FmsDprHydropower.findByAccumulatedDpr", query = "SELECT f FROM FmsDprHydropower f WHERE f.accumulatedDpr = :accumulatedDpr"),
    @NamedQuery(name = "FmsDprHydropower.findByNetBookValue", query = "SELECT f FROM FmsDprHydropower f WHERE f.netBookValue = :netBookValue"),
    @NamedQuery(name = "FmsDprHydropower.findByDprYear", query = "SELECT f FROM FmsDprHydropower f WHERE f.dprYear = :dprYear"),
    @NamedQuery(name = "FmsDprHydropower.findByStatus", query = "SELECT f FROM FmsDprHydropower f WHERE f.status = :status"),
    @NamedQuery(name = "FmsDprHydropower.findByRevaluationServiceYear", query = "SELECT f FROM FmsDprHydropower f WHERE f.revaluationServiceYear = :revaluationServiceYear"),
    @NamedQuery(name = "FmsDprHydropower.findByDataCardNo", query = "SELECT f FROM FmsDprHydropower f WHERE f.dataCardNo = :dataCardNo")})
public class FmsDprHydropower implements Serializable {

    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "DPR_HYDROPOWER_ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FMS_DPR_HYDROPOWER_SEQ")
    @SequenceGenerator(name = "FMS_DPR_HYDROPOWER_SEQ", sequenceName = "FMS_DPR_HYDROPOWER_SEQ", allocationSize = 1)
    private Integer dprHydropowerId;
    @Column(name = "REVALUATION_COST")
    private BigDecimal revaluationCost;
    @Column(name = "ACCUMULATED_DPR")
    private BigDecimal accumulatedDpr;
    @Column(name = "NET_BOOK_VALUE")
    private BigDecimal netBookValue;
    @Column(name = "DPR_YEAR")
    private BigDecimal dprYear;
    @Column(name = "STATUS")
    private Integer status;
    private static final long serialVersionUID = 1L;    
    @Column(name = "REVALUATION_SERVICE_YEAR")
    private Integer revaluationServiceYear;    
    @Column(name = "DATA_CARD_NO")
    private String dataCardNo;
    @JoinColumn(name = "HP_ASSET_ID", referencedColumnName = "HYDRO_POWER_ID")
    @ManyToOne
    private MmsFaHydropower hpAssetId;
    @JoinColumn(name = "ACCOUNT_PERIOD", referencedColumnName = "ACOUNTIG_PERIOD_ID")
    @ManyToOne
    private FmsAccountingPeriod accountPeriod;    
    @JoinColumn(name = "ACCOUNT_NO", referencedColumnName = "GENERAL_LEDGER_ID")
    @ManyToOne
    private FmsGeneralLedger accountNo;

    public FmsDprHydropower() {
    }

    public FmsDprHydropower(Integer dprHydropowerId) {
        this.dprHydropowerId = dprHydropowerId;
    }

    public FmsGeneralLedger getAccountNo() {
        return accountNo;
    }
    
    public FmsAccountingPeriod getAccountPeriod() {
        return accountPeriod;
    }

    public void setAccountPeriod(FmsAccountingPeriod accountPeriod) {
        this.accountPeriod = accountPeriod;
    }    

    public void setAccountNo(FmsGeneralLedger accountNo) {
        this.accountNo = accountNo;
    }

    public Integer getDprHydropowerId() {
        return dprHydropowerId;
    }

    public void setDprHydropowerId(Integer dprHydropowerId) {
        this.dprHydropowerId = dprHydropowerId;
    }

    public BigDecimal getRevaluationCost() {
        return revaluationCost;
    }

    public void setRevaluationCost(BigDecimal revaluationCost) {
        this.revaluationCost = revaluationCost;
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

    public MmsFaHydropower getHpAssetId() {
        return hpAssetId;
    }

    public void setHpAssetId(MmsFaHydropower hpAssetId) {
        this.hpAssetId = hpAssetId;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (dprHydropowerId != null ? dprHydropowerId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FmsDprHydropower)) {
            return false;
        }
        FmsDprHydropower other = (FmsDprHydropower) object;
        if ((this.dprHydropowerId == null && other.dprHydropowerId != null) || (this.dprHydropowerId != null && !this.dprHydropowerId.equals(other.dprHydropowerId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.eep.fcms.entity.FmsDprHydropower[ dprHydropowerId=" + dprHydropowerId + " ]";
    }

}
