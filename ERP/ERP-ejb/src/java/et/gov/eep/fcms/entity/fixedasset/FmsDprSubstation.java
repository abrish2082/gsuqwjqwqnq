/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.entity.fixedasset;


import et.gov.eep.fcms.entity.admin.FmsAccountingPeriod;
import et.gov.eep.fcms.entity.admin.FmsGeneralLedger;
import et.gov.eep.mms.entity.MmsFaSubstation;
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
@Table(name = "FMS_DPR_SUBSTATION")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FmsDprSubstation.findAll", query = "SELECT f FROM FmsDprSubstation f"),
    @NamedQuery(name = "FmsDprSubstation.findByDprSubstationId", query = "SELECT f FROM FmsDprSubstation f WHERE f.dprSubstationId = :dprSubstationId"),
    @NamedQuery(name = "FmsDprSubstation.findByRevaluationCost", query = "SELECT f FROM FmsDprSubstation f WHERE f.revaluationCost = :revaluationCost"),
    @NamedQuery(name = "FmsDprSubstation.findByAccumulatedDpr", query = "SELECT f FROM FmsDprSubstation f WHERE f.accumulatedDpr = :accumulatedDpr"),
    @NamedQuery(name = "FmsDprSubstation.findByNetBookValue", query = "SELECT f FROM FmsDprSubstation f WHERE f.netBookValue = :netBookValue"),
    @NamedQuery(name = "FmsDprSubstation.findByDprYear", query = "SELECT f FROM FmsDprSubstation f WHERE f.dprYear = :dprYear"),
    @NamedQuery(name = "FmsDprSubstation.findByStatus", query = "SELECT f FROM FmsDprSubstation f WHERE f.status = :status"),
    @NamedQuery(name = "FmsDprSubstation.findByRevaluationServiceYear", query = "SELECT f FROM FmsDprSubstation f WHERE f.revaluationServiceYear = :revaluationServiceYear"),
    @NamedQuery(name = "FmsDprSubstation.findByDataCardNo", query = "SELECT f FROM FmsDprSubstation f WHERE f.dataCardNo = :dataCardNo")})
public class FmsDprSubstation implements Serializable {
    @Column(name = "REVALUATION_COST")
    private BigDecimal revaluationCost;
    @Column(name = "ACCUMULATED_DPR")
    private BigDecimal accumulatedDpr;
    @Column(name = "NET_BOOK_VALUE")
    private BigDecimal netBookValue;
    @Column(name = "DPR_YEAR")
    private BigDecimal dprYear;    
    @Column(name = "STATUS")
    private int status;
    @JoinColumn(name = "ACCOUNT_NO", referencedColumnName = "GENERAL_LEDGER_ID")
    @ManyToOne
    private FmsGeneralLedger accountNo;
    @JoinColumn(name = "ACCOUNT_PERIOD", referencedColumnName = "ACOUNTIG_PERIOD_ID")
    @ManyToOne
    private FmsAccountingPeriod accountPeriod;
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "DPR_SUBSTATION_ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FMS_DPR_SUBSTATION_SEQ")
    @SequenceGenerator(name = "FMS_DPR_SUBSTATION_SEQ", sequenceName = "FMS_DPR_SUBSTATION_SEQ", allocationSize = 1)    
    private Integer dprSubstationId;
    @Column(name = "REVALUATION_SERVICE_YEAR")
    private Integer revaluationServiceYear;
    @Column(name = "DATA_CARD_NO")
    private String dataCardNo;
    @JoinColumn(name = "SS_ASSET_ID", referencedColumnName = "SUBSTATION_ID")
    @ManyToOne
    private MmsFaSubstation ssAssetId;

    /**
     *
     */
    public FmsDprSubstation() {
    }

    /**
     *
     * @param dprSubstationId
     */
    public FmsDprSubstation(Integer dprSubstationId) {
        this.dprSubstationId = dprSubstationId;
    }

    /**
     *
     * @return
     */
    public Integer getDprSubstationId() {
        return dprSubstationId;
    }

    /**
     *
     * @param dprSubstationId
     */
    public void setDprSubstationId(Integer dprSubstationId) {
        this.dprSubstationId = dprSubstationId;
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
    
    public MmsFaSubstation getSsAssetId() {
        return ssAssetId;
    }

    /**
     *
     * @param ssAssetId
     */
    public void setSsAssetId(MmsFaSubstation ssAssetId) {
        this.ssAssetId = ssAssetId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (dprSubstationId != null ? dprSubstationId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FmsDprSubstation)) {
            return false;
        }
        FmsDprSubstation other = (FmsDprSubstation) object;
        if ((this.dprSubstationId == null && other.dprSubstationId != null) || (this.dprSubstationId != null && !this.dprSubstationId.equals(other.dprSubstationId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.eep.fcms.entity.FmsDprSubstation[ dprSubstationId=" + dprSubstationId + " ]";
    }


    public FmsAccountingPeriod getAccountPeriod() {
        return accountPeriod;
    }

    public void setAccountPeriod(FmsAccountingPeriod accountPeriod) {
        this.accountPeriod = accountPeriod;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public FmsGeneralLedger getAccountNo() {
        if(accountNo == null){
            accountNo = new FmsGeneralLedger();
        }
        return accountNo;
    }

    public void setAccountNo(FmsGeneralLedger accountNo) {
        this.accountNo = accountNo;
    }
    
}
