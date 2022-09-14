
package et.gov.eep.mms.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import et.gov.eep.fcms.entity.admin.FmsCostCenter;
import et.gov.eep.fcms.entity.admin.FmsSubsidiaryLedger;
import et.gov.eep.ifrs.entity.IfrsDepreciationSetup;
import et.gov.eep.ifrs.entity.IfrsFaRevaluationHistory;
import et.gov.eep.ifrs.entity.IfrsFixedAssetAtValue;

/**
 *
 * @author user
 */
@Entity
@Table(name = "IFRS_FIXED_ASSET")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "IfrsFixedAsset.findAll", query = "SELECT i FROM IfrsFixedAsset i"),
    @NamedQuery(name = "IfrsFixedAsset.findById", query = "SELECT i FROM IfrsFixedAsset i WHERE i.id = :id"),
    @NamedQuery(name = "IfrsFixedAsset.findByFaName", query = "SELECT i FROM IfrsFixedAsset i WHERE i.faName = :faName"),
    @NamedQuery(name = "IfrsFixedAsset.findByFaCode", query = "SELECT i FROM IfrsFixedAsset i WHERE i.faCode = :faCode"),
    @NamedQuery(name = "IfrsFixedAsset.findByUseFullLifeTime", query = "SELECT i FROM IfrsFixedAsset i WHERE i.useFullLifeTime = :useFullLifeTime"),
    @NamedQuery(name = "IfrsFixedAsset.findByFixedAssetNo", query = "SELECT i FROM IfrsFixedAsset i WHERE i.fixedAssetNo = :fixedAssetNo"),
    @NamedQuery(name = "IfrsFixedAsset.findByNetPointNorth", query = "SELECT i FROM IfrsFixedAsset i WHERE i.netPointNorth = :netPointNorth"),
    @NamedQuery(name = "IfrsFixedAsset.findByLocationName", query = "SELECT i FROM IfrsFixedAsset i WHERE i.locationName = :locationName"),
    @NamedQuery(name = "IfrsFixedAsset.findBySystemNumber", query = "SELECT i FROM IfrsFixedAsset i WHERE i.systemNumber = :systemNumber"),
    @NamedQuery(name = "IfrsFixedAsset.findByInService", query = "SELECT i FROM IfrsFixedAsset i WHERE i.inService = :inService"),
    @NamedQuery(name = "IfrsFixedAsset.findByTypeName", query = "SELECT i FROM IfrsFixedAsset i WHERE i.typeName = :typeName"),
    @NamedQuery(name = "IfrsFixedAsset.findByUnitCost", query = "SELECT i FROM IfrsFixedAsset i WHERE i.initialCost = :initialCost"),
    @NamedQuery(name = "IfrsFixedAsset.findByRegion", query = "SELECT i FROM IfrsFixedAsset i WHERE i.region = :region"),
    @NamedQuery(name = "IfrsFixedAsset.findByDepreciationYear", query = "SELECT i FROM IfrsFixedAsset i WHERE i.depreciationYear = :depreciationYear"),
    @NamedQuery(name = "IfrsFixedAsset.findByRevalutionCost", query = "SELECT i FROM IfrsFixedAsset i WHERE i.revalutionCost = :revalutionCost"),
    @NamedQuery(name = "IfrsFixedAsset.findByInitialCost", query = "SELECT i FROM IfrsFixedAsset i WHERE i.initialCost = :initialCost"),
    @NamedQuery(name = "IfrsFixedAsset.findByPreparedBy", query = "SELECT i FROM IfrsFixedAsset i WHERE i.preparedBy = :preparedBy"),
    @NamedQuery(name = "IfrsFixedAsset.findByRegionNo", query = "SELECT i FROM IfrsFixedAsset i WHERE i.regionNo = :regionNo"),
    @NamedQuery(name = "IfrsFixedAsset.findByNetPointEast", query = "SELECT i FROM IfrsFixedAsset i WHERE i.netPointEast = :netPointEast"),
    @NamedQuery(name = "IfrsFixedAsset.findByCostCenter", query = "SELECT i FROM IfrsFixedAsset i WHERE i.costCenter = :costCenter"),
    @NamedQuery(name = "IfrsFixedAsset.findByNoOfUnits", query = "SELECT i FROM IfrsFixedAsset i WHERE i.noOfUnits = :noOfUnits"),
    @NamedQuery(name = "IfrsFixedAsset.findByTypeNo", query = "SELECT i FROM IfrsFixedAsset i WHERE i.typeNo = :typeNo"),
    @NamedQuery(name = "IfrsFixedAsset.findByDepreciationType", query = "SELECT i FROM IfrsFixedAsset i WHERE i.fagId = :depreciationSetup"),
    @NamedQuery(name = "IfrsFixedAsset.findByfaCode", query = "SELECT m FROM IfrsFixedAsset m WHERE m.faCode = :faCode"),
    @NamedQuery(name = "IfrsFixedAsset.findByNetBookValue", query = "SELECT i FROM IfrsFixedAsset i WHERE i.netBookValue = :netBookValue"),
    @NamedQuery(name = "IfrsFixedAsset.findByAccumulatedDepreciation", query = "SELECT i FROM IfrsFixedAsset i WHERE i.accumulatedDepreciation = :accumulatedDepreciation"),
    @NamedQuery(name = "IfrsFixedAsset.findByRevalutionServiceYear", query = "SELECT i FROM IfrsFixedAsset i WHERE i.revalutionServiceYear = :revalutionServiceYear")})
public class IfrsFixedAsset implements Serializable {

    @OneToMany(mappedBy = "ifrsFaId")
    private List<IfrsFaRevaluationHistory> ifrsFaRevaluationHistoryList;
    @Column(name = "INITIAL_COST")
    private Double initialCost;

    @Column(name = "SALVAGE_COST")
    private Double salvageCost;
    @JoinColumn(name = "COST_CENTER", referencedColumnName = "COST_CENTER_ID")
    @ManyToOne
    private FmsCostCenter costCenter;
    @JoinColumn(name = "FAG_ID", referencedColumnName = "ID")
    @ManyToOne
    private IfrsDepreciationSetup fagId;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "IFRS_FIXED_ASSET_SETUP_SEQ")
    @SequenceGenerator(name = "IFRS_FIXED_ASSET_SETUP_SEQ", sequenceName = "IFRS_FIXED_ASSET_SETUP_SEQ", allocationSize = 1)
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private Long id;
    @Size(max = 50)
    @Column(name = "FA_NAME")
    private String faName;
    @Size(max = 50)
    @Column(name = "FA_CODE")
    private String faCode;
    @Column(name = "USE_FULL_LIFE_TIME")
    private Integer useFullLifeTime;
    @Size(max = 50)
    @Column(name = "FIXED_ASSET_NO")
    private String fixedAssetNo;
    @Size(max = 50)
    @Column(name = "NET_POINT_NORTH")
    private String netPointNorth;
    @Size(max = 50)
    @Column(name = "LOCATION_NAME")
    private String locationName;
    @Column(name = "SYSTEM_NUMBER")
    private BigInteger systemNumber;
    @Column(name = "IN_SERVICE")
    private String inService;
    @Size(max = 50)
    @Column(name = "TYPE_NAME")
    private String typeName;
    @Size(max = 126)
    @Column(name = "VALUTION_METHODE")
    private String valutionMethode;
    @Size(max = 20)
    @Column(name = "ASSET_STATUS")
    private String assetStatus;
    @Size(max = 20)
    @Column(name = "IMPAIREMENT_COST")
    private String impairementCost;
    @Size(max = 20)
    @Column(name = "REGION")
    private String region;

    @Size(max = 20)
    @Column(name = "DEPRECIATION_YEAR")
    private String depreciationYear;

    @Column(name = "REVALUTION_COST")
    private BigDecimal revalutionCost;

    @Size(max = 20)
    @Column(name = "PREPARED_BY")
    private String preparedBy;

    @Size(max = 20)
    @Column(name = "REGION_NO")
    private String regionNo;

    @Size(max = 20)
    @Column(name = "NET_POINT_EAST")
    private String netPointEast;
    @Column(name = "NO_OF_UNITS")
    private BigInteger noOfUnits;

    @Column(name = "TYPE_NO")
    private BigInteger typeNo;

    @Size(max = 20)
    @Column(name = "NET_BOOK_VALUE")
    private String netBookValue;
    @Size(max = 20)
    @Column(name = "ACCUMULATED_DEPRECIATION")
    private String accumulatedDepreciation;

    @Column(name = "REVALUTION_SERVICE_YEAR")
    private BigInteger revalutionServiceYear;
    @JoinColumn(name = "SUBSIDIARY_ID", referencedColumnName = "SUBSIDIARY_ID")
    @OneToOne
    private FmsSubsidiaryLedger subsidiaryId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "faId")
    private List<IfrsFixedAssetAtValue> ifrsFixedAssetAtValueList;
    public IfrsFixedAsset() {
    }

    public IfrsFixedAsset(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFaName() {
        return faName;
    }

    public void setFaName(String faName) {
        this.faName = faName;
    }

    public String getFaCode() {
        return faCode;
    }

    public void setFaCode(String faCode) {
        this.faCode = faCode;
    }

    public Integer getUseFullLifeTime() {
        return useFullLifeTime;
    }

    public void setUseFullLifeTime(Integer useFullLifeTime) {
        this.useFullLifeTime = useFullLifeTime;
    }

    public String getFixedAssetNo() {
        return fixedAssetNo;
    }

    public void setFixedAssetNo(String fixedAssetNo) {
        this.fixedAssetNo = fixedAssetNo;
    }

    public String getNetPointNorth() {
        return netPointNorth;
    }

    public void setNetPointNorth(String netPointNorth) {
        this.netPointNorth = netPointNorth;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public BigInteger getSystemNumber() {
        return systemNumber;
    }

    public void setSystemNumber(BigInteger systemNumber) {
        this.systemNumber = systemNumber;
    }

    public String getInService() {
        return inService;
    }

    public void setInService(String inService) {
        this.inService = inService;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getValutionMethode() {
        return valutionMethode;
    }

    public void setValutionMethode(String valutionMethode) {
        this.valutionMethode = valutionMethode;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getDepreciationYear() {
        return depreciationYear;
    }

    public void setDepreciationYear(String depreciationYear) {
        this.depreciationYear = depreciationYear;
    }

    public BigDecimal getRevalutionCost() {
        return revalutionCost;
    }

    public void setRevalutionCost(BigDecimal revalutionCost) {
        this.revalutionCost = revalutionCost;
    }

    public String getPreparedBy() {
        return preparedBy;
    }

    public void setPreparedBy(String preparedBy) {
        this.preparedBy = preparedBy;
    }

    public String getAssetStatus() {
        return assetStatus;
    }

    public void setAssetStatus(String assetStatus) {
        this.assetStatus = assetStatus;
    }

    public String getImpairementCost() {
        return impairementCost;
    }

    public void setImpairementCost(String impairementCost) {
        this.impairementCost = impairementCost;
    }

    public String getRegionNo() {
        return regionNo;
    }

    public void setRegionNo(String regionNo) {
        this.regionNo = regionNo;
    }

    public String getNetPointEast() {
        return netPointEast;
    }

    public void setNetPointEast(String netPointEast) {
        this.netPointEast = netPointEast;
    }

    public BigInteger getNoOfUnits() {
        return noOfUnits;
    }

    public void setNoOfUnits(BigInteger noOfUnits) {
        this.noOfUnits = noOfUnits;
    }

    public BigInteger getTypeNo() {
        return typeNo;
    }

    public void setTypeNo(BigInteger typeNo) {
        this.typeNo = typeNo;
    }

    public Double getSalvageCost() {
        return salvageCost;
    }

    public void setSalvageCost(Double salvageCost) {
        this.salvageCost = salvageCost;
    }

    public String getNetBookValue() {
        return netBookValue;
    }

    public void setNetBookValue(String netBookValue) {
        this.netBookValue = netBookValue;
    }

    public String getAccumulatedDepreciation() {
        return accumulatedDepreciation;
    }

    public void setAccumulatedDepreciation(String accumulatedDepreciation) {
        this.accumulatedDepreciation = accumulatedDepreciation;
    }

    public BigInteger getRevalutionServiceYear() {
        return revalutionServiceYear;
    }

    public void setRevalutionServiceYear(BigInteger revalutionServiceYear) {
        this.revalutionServiceYear = revalutionServiceYear;
    }

    @XmlTransient
    public List<IfrsFixedAssetAtValue> getIfrsFixedAssetAtValueList() {
        if (ifrsFixedAssetAtValueList == null) {
            ifrsFixedAssetAtValueList = new ArrayList<>();
        }
        return ifrsFixedAssetAtValueList;
    }

    public void setIfrsFixedAssetAtValueList(List<IfrsFixedAssetAtValue> ifrsFixedAssetAtValueList) {
        this.ifrsFixedAssetAtValueList = ifrsFixedAssetAtValueList;
    }

    public FmsSubsidiaryLedger getSubsidiaryId() {
        return subsidiaryId;
    }

    public void setSubsidiaryId(FmsSubsidiaryLedger subsidiaryId) {
        this.subsidiaryId = subsidiaryId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof IfrsFixedAsset)) {
            return false;
        }
        IfrsFixedAsset other = (IfrsFixedAsset) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return faName;
    }

    public void addToFixAttributevalue(IfrsFixedAssetAtValue fixedassetattributevalue) {
        System.out.println(" Test New add  " + fixedassetattributevalue.getValueName());
        if (fixedassetattributevalue.getFaId() != this) {
            System.out.println(" Test New add getFixedAssetId  " + fixedassetattributevalue.getFaId());
            this.getIfrsFixedAssetAtValueList().add(fixedassetattributevalue);
            fixedassetattributevalue.setFaId(this);
        }
    }

    @XmlTransient
    public List<IfrsFaRevaluationHistory> getIfrsFaRevaluationHistoryList() {
        return ifrsFaRevaluationHistoryList;
    }

    public void setIfrsFaRevaluationHistoryList(List<IfrsFaRevaluationHistory> ifrsFaRevaluationHistoryList) {
        this.ifrsFaRevaluationHistoryList = ifrsFaRevaluationHistoryList;
    }

    public IfrsDepreciationSetup getFagId() {
        return fagId;
    }

    public void setFagId(IfrsDepreciationSetup fagId) {
        this.fagId = fagId;
    }

    public Double getInitialCost() {
        return initialCost;
    }

    public void setInitialCost(Double initialCost) {
        this.initialCost = initialCost;
    }

    public FmsCostCenter getCostCenter() {
        return costCenter;
    }

    public void setCostCenter(FmsCostCenter costCenter) {
        this.costCenter = costCenter;
    }

}
