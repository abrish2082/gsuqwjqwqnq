/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.entity.fixedasset;
import et.gov.eep.fcms.entity.admin.FmsAccountingPeriod;
import et.gov.eep.fcms.entity.admin.FmsGeneralLedger;
import et.gov.eep.mms.entity.MmsFaTransmission;
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
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Binyam
 */
@Entity
@Table(name = "FMS_DPR_TRANSMISSON")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FmsDprTransmisson.findAll", query = "SELECT f FROM FmsDprTransmisson f"),
    @NamedQuery(name = "FmsDprTransmisson.findByDprTransmissionId", query = "SELECT f FROM FmsDprTransmisson f WHERE f.dprTransmissionId = :dprTransmissionId"),
    @NamedQuery(name = "FmsDprTransmisson.findByRevaluationCost", query = "SELECT f FROM FmsDprTransmisson f WHERE f.revaluationCost = :revaluationCost"),
    @NamedQuery(name = "FmsDprTransmisson.findByAccumulatedDpr", query = "SELECT f FROM FmsDprTransmisson f WHERE f.accumulatedDpr = :accumulatedDpr"),
    @NamedQuery(name = "FmsDprTransmisson.findByNetBookValue", query = "SELECT f FROM FmsDprTransmisson f WHERE f.netBookValue = :netBookValue"),
    @NamedQuery(name = "FmsDprTransmisson.findByDprYear", query = "SELECT f FROM FmsDprTransmisson f WHERE f.dprYear = :dprYear"),
    @NamedQuery(name = "FmsDprTransmisson.findByRevaluationServiceYear", query = "SELECT f FROM FmsDprTransmisson f WHERE f.revaluationServiceYear = :revaluationServiceYear"),
    @NamedQuery(name = "FmsDprTransmisson.findByStatus", query = "SELECT f FROM FmsDprTransmisson f WHERE f.status = :status"),
    @NamedQuery(name = "FmsDprTransmisson.findBytrAssetIdLike", query = "SELECT f FROM FmsDprTransmisson f WHERE f.dprTransmissionId LIKE :dprTransmissionId"),
    @NamedQuery(name = "FmsDprTransmisson.findByDataCardNo", query = "SELECT f FROM FmsDprTransmisson f WHERE f.dataCardNo = :dataCardNo")})
public class FmsDprTransmisson implements Serializable {

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
    @JoinColumn(name = "ACCOUNT_NO_TRNS", referencedColumnName = "GENERAL_LEDGER_ID")
    @ManyToOne
    private FmsGeneralLedger accountNoTrns;    
    @JoinColumn(name = "ACCOUNTING_PERIOD", referencedColumnName = "ACOUNTIG_PERIOD_ID")
    @ManyToOne
    private FmsAccountingPeriod accountingPeriod;
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "DPR_TRANSMISSION_ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FMS_DPR_TRANSMISSION_SEQ")
    @SequenceGenerator(name = "FMS_DPR_TRANSMISSION_SEQ", sequenceName = "FMS_DPR_TRANSMISSION_SEQ", allocationSize = 1)
    private Integer dprTransmissionId;
    @Column(name = "REVALUATION_SERVICE_YEAR")
    private int revaluationServiceYear;
    @Size(max = 20)
    @Column(name = "DATA_CARD_NO")
    private String dataCardNo;
    @JoinColumn(name = "TR_ASSET_ID", referencedColumnName = "TRANSMISSION_ID")
    @OneToOne
    private MmsFaTransmission trAssetId;

    /**
     *
     */
    public FmsDprTransmisson() {
    }

    /**
     *
     * @param dprTransmissionId
     */
    public FmsDprTransmisson(Integer dprTransmissionId) {
        this.dprTransmissionId = dprTransmissionId;
    }

    /**
     *
     * @return
     */
    public Integer getDprTransmissionId() {
        return dprTransmissionId;
    }

    /**
     *
     * @param dprTransmissionId
     */
    public void setDprTransmissionId(Integer dprTransmissionId) {
        this.dprTransmissionId = dprTransmissionId;
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
    public int getRevaluationServiceYear() {
        return revaluationServiceYear;
    }

    /**
     *
     * @param revaluationServiceYear
     */
    public void setRevaluationServiceYear(int revaluationServiceYear) {
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
    public MmsFaTransmission getTrAssetId() {
        return trAssetId;
    }

    /**
     *
     * @param trAssetId
     */
    public void setTrAssetId(MmsFaTransmission trAssetId) {
        this.trAssetId = trAssetId;
    }

    public FmsAccountingPeriod getAccountingPeriod() {
        if (accountingPeriod == null) {
            accountingPeriod = new FmsAccountingPeriod();
        }
        return accountingPeriod;
    }

    public void setAccountingPeriod(FmsAccountingPeriod accountingPeriod) {
        this.accountingPeriod = accountingPeriod;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (dprTransmissionId != null ? dprTransmissionId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FmsDprTransmisson)) {
            return false;
        }
        FmsDprTransmisson other = (FmsDprTransmisson) object;
        if ((this.dprTransmissionId == null && other.dprTransmissionId != null) || (this.dprTransmissionId != null && !this.dprTransmissionId.equals(other.dprTransmissionId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.eep.fcms.entity.FmsDprTransmisson[ dprTransmissionId=" + dprTransmissionId + " ]";
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
    
    public FmsGeneralLedger getAccountNoTrns() {
        if(accountNoTrns == null){
            accountNoTrns = new FmsGeneralLedger();
        }
        return accountNoTrns;
    }

    public void setAccountNoTrns(FmsGeneralLedger accountNo) {
        this.accountNoTrns = accountNo;
    }

}
