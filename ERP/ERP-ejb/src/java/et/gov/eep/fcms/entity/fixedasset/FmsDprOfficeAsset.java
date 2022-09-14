/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.entity.fixedasset;
import et.gov.eep.fcms.entity.admin.FmsAccountingPeriod;
import et.gov.eep.mms.entity.MmsFixedassetRegstDetail;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
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
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Binyam
 */
@Entity
@Table(name = "FMS_DPR_OFFICE_ASSET")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FmsDprOfficeAsset.findAll", query = "SELECT f FROM FmsDprOfficeAsset f"),
    @NamedQuery(name = "FmsDprOfficeAsset.findByDprOfficeId", query = "SELECT f FROM FmsDprOfficeAsset f WHERE f.dprOfficeId = :dprOfficeId"),
    @NamedQuery(name = "FmsDprOfficeAsset.findByRevaluationCost", query = "SELECT f FROM FmsDprOfficeAsset f WHERE f.revaluationCost = :revaluationCost"),
    @NamedQuery(name = "FmsDprOfficeAsset.findByAccumulatedDpr", query = "SELECT f FROM FmsDprOfficeAsset f WHERE f.accumulatedDpr = :accumulatedDpr"),
    @NamedQuery(name = "FmsDprOfficeAsset.findByNetBookValue", query = "SELECT f FROM FmsDprOfficeAsset f WHERE f.netBookValue = :netBookValue"),
    @NamedQuery(name = "FmsDprOfficeAsset.findByTagNoAndStatus", query = "SELECT f FROM FmsDprOfficeAsset f WHERE f.status = :status AND f.tagNo = :tagNo"),
    @NamedQuery(name = "FmsDprOfficeAsset.findByDprYear", query = "SELECT f FROM FmsDprOfficeAsset f WHERE f.dprYear = :dprYear"),
    @NamedQuery(name = "FmsDprOfficeAsset.findByRevaluationServiceYear", query = "SELECT f FROM FmsDprOfficeAsset f WHERE f.revaluationServiceYear = :revaluationServiceYear"),
    @NamedQuery(name = "FmsDprOfficeAsset.findByStatus", query = "SELECT f FROM FmsDprOfficeAsset f WHERE f.status = :status")})
public class FmsDprOfficeAsset implements Serializable {
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
    @Column(name = "STATUS")
    private Integer status;
    @OneToMany(mappedBy = "fmsDepreciationFk")
    private List<FmsDisposedItems> fmsDisposedItemsList;
    @Column(name = "SYSTEM_CODE")
    private String systemCode;
    @Column(name = "COST_CENTER")
    private String costCenter;
    @Size(max = 1000)
    @Column(name = "TAG_NO")
    private String tagNo;
    @JoinColumn(name = "ACCOUNT_PERIOD", referencedColumnName = "ACOUNTIG_PERIOD_ID")
    @ManyToOne
    private FmsAccountingPeriod accountPeriod;
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "DPR_OFFICE_ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FMS_DPR_OFFICE_ASSET_SEQ")
    @SequenceGenerator(name = "FMS_DPR_OFFICE_ASSET_SEQ", sequenceName = "FMS_DPR_OFFICE_ASSET_SEQ", allocationSize = 1)
    private Integer dprOfficeId;
    @JoinColumn(name = "OF_ASSET_ID", referencedColumnName = "FAR_DET_ID")
    @ManyToOne
    private MmsFixedassetRegstDetail ofAssetId;

    /**
     *
     */
    public FmsDprOfficeAsset() {
    }

    /**
     *
     * @param dprOfficeId
     */
    public FmsDprOfficeAsset(Integer dprOfficeId) {
        this.dprOfficeId = dprOfficeId;
    }

    /**
     *
     * @return
     */
    public Integer getDprOfficeId() {
        return dprOfficeId;
    }

    /**
     *
     * @param dprOfficeId
     */
    public void setDprOfficeId(Integer dprOfficeId) {
        this.dprOfficeId = dprOfficeId;
    }

    public BigDecimal getDprYear() {
        return dprYear;
    }

    public void setDprYear(BigDecimal dprYear) {
        this.dprYear = dprYear;
    }

    public Integer getRevaluationServiceYear() {
        return revaluationServiceYear;
    }

    public void setRevaluationServiceYear(Integer revaluationServiceYear) {
        this.revaluationServiceYear = revaluationServiceYear;
    }

    public String getSystemCode() {
        return systemCode;
    }

    public void setSystemCode(String systemCode) {
        this.systemCode = systemCode;
    }

    public String getCostCenter() {
        return costCenter;
    }

    public void setCostCenter(String costCenter) {
        this.costCenter = costCenter;
    }

    public String getTagNo() {
        return tagNo;
    }

    public void setTagNo(String tagNo) {
        this.tagNo = tagNo;
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
    
    public MmsFixedassetRegstDetail getOfAssetId() {
        return ofAssetId;
    }

    public void setOfAssetId(MmsFixedassetRegstDetail ofAssetId) {
        this.ofAssetId = ofAssetId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (dprOfficeId != null ? dprOfficeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FmsDprOfficeAsset)) {
            return false;
        }
        FmsDprOfficeAsset other = (FmsDprOfficeAsset) object;
        if ((this.dprOfficeId == null && other.dprOfficeId != null) || (this.dprOfficeId != null && !this.dprOfficeId.equals(other.dprOfficeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.eep.fcms.entity.FmsDprOfficeAsset[ dprOfficeId=" + dprOfficeId + " ]";
    }

    @XmlTransient
    public List<FmsDisposedItems> getFmsDisposedItemsList() {
        return fmsDisposedItemsList;
    }

    public void setFmsDisposedItemsList(List<FmsDisposedItems> fmsDisposedItemsList) {
        this.fmsDisposedItemsList = fmsDisposedItemsList;
    }
    
}
