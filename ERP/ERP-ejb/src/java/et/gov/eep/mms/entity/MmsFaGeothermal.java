/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.mms.entity;

import et.gov.eep.commonApplications.entity.WfMmsProcessed;
import et.gov.eep.fcms.entity.admin.FmsCostCenter;
import et.gov.eep.fcms.entity.admin.FmsGeneralLedger;
import et.gov.eep.fcms.entity.admin.FmsLuSystem;
import et.gov.eep.fcms.entity.fixedasset.FmsDprGeothermal;

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
@Table(name = "MMS_FA_GEOTHERMAL")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MmsFaGeothermal.findAll", query = "SELECT m FROM MmsFaGeothermal m"),
    @NamedQuery(name = "MmsFaGeothermal.findByGeothermalId", query = "SELECT m FROM MmsFaGeothermal m WHERE m.geothermalId = :geothermalId"),
    @NamedQuery(name = "MmsFaGeothermal.findByRegionNo", query = "SELECT m FROM MmsFaGeothermal m WHERE m.regionNo = :regionNo"),
    @NamedQuery(name = "MmsFaGeothermal.findByNetPointNorth", query = "SELECT m FROM MmsFaGeothermal m WHERE m.netPointNorth = :netPointNorth"),
    @NamedQuery(name = "MmsFaGeothermal.findByNetPointEast", query = "SELECT m FROM MmsFaGeothermal m WHERE m.netPointEast = :netPointEast"),

    @NamedQuery(name = "MmsFaGeothermal.findBygtNoLike", query = "SELECT m FROM MmsFaGeothermal m WHERE m.gtNo LIKE :gtNo"),
    @NamedQuery(name = "MmsFaGeothermal.findBygtNoAndGtPrep", query = "SELECT m FROM MmsFaGeothermal m WHERE m.gtNo = :gtNo AND m.gtPrepared = :gtPrepared"),
    @NamedQuery(name = "MmsFaGeothermal.findBygeothermalIdMaximum", query = "SELECT m FROM MmsFaGeothermal m WHERE m.geothermalId = (SELECT Max(c.geothermalId) FROM MmsFaGeothermal c)"),
    @NamedQuery(name = "MmsFaGeothermal.findByAllParameters", query = "SELECT m FROM MmsFaGeothermal m WHERE m.stationName LIKE :stationName"),

    @NamedQuery(name = "MmsFaGeothermal.findByStationName", query = "SELECT m FROM MmsFaGeothermal m WHERE m.stationName = :stationName"),
    @NamedQuery(name = "MmsFaGeothermal.findByStationNameLike", query = "SELECT m FROM MmsFaGeothermal m WHERE m.stationName LIKE :stationName"),
    @NamedQuery(name = "MmsFaGeothermal.findByGtInService", query = "SELECT m FROM MmsFaGeothermal m WHERE m.gtInService = :gtInService"),
    @NamedQuery(name = "MmsFaGeothermal.findByGtNoOfUnits", query = "SELECT m FROM MmsFaGeothermal m WHERE m.gtNoOfUnits = :gtNoOfUnits"),
    @NamedQuery(name = "MmsFaGeothermal.findByGtGradingFactor", query = "SELECT m FROM MmsFaGeothermal m WHERE m.gtGradingFactor = :gtGradingFactor"),
    @NamedQuery(name = "MmsFaGeothermal.findByGtUnitCost", query = "SELECT m FROM MmsFaGeothermal m WHERE m.gtUnitCost = :gtUnitCost"),
    @NamedQuery(name = "MmsFaGeothermal.findByGtServiceLife", query = "SELECT m FROM MmsFaGeothermal m WHERE m.gtServiceLife = :gtServiceLife")})
public class MmsFaGeothermal implements Serializable {

    @OneToMany(mappedBy = "geoAssetId")
    private List<FmsDprGeothermal> fmsDprGeothermalList;
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id

    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQUENCEGEO")
    @SequenceGenerator(name = "SEQUENCEGEO", sequenceName = "SEQUENCEGEO", allocationSize = 1)

    @Basic(optional = false)
    @NotNull
    @Column(name = "GEOTHERMAL_ID")
    private Integer geothermalId;
    @Column(name = "REGION_NO")
    private BigInteger regionNo;
    @Size(max = 20)
    @Column(name = "NET_POINT_NORTH")
    private String netPointNorth;

    @Size(max = 50)
    @Column(name = "GT_NO")
    private String gtNo;

   
    @Column(name = "GT_PREPARED")
    private Integer gtPrepared;

    @Size(max = 50)
    @Column(name = "REGION")
    private String region;

    @Column(name = "ACCUMULATED_DPR")
    private BigDecimal accumulatedDpr;
    @Column(name = "NET_BOOK_VALUE")
    private BigDecimal netBookValue;
    @Column(name = "DPR_YEAR")
    private BigDecimal dprYear;

    @Column(name = "REVALUATION_COST")
    private BigDecimal revaluationCost;

    @Column(name = "REVALUATION_SERVICE_YEAR")
    private Integer revaluationServiceYear;

    @Column(name = "GT_SHEET_NO")
    private Integer gtSheetNo;
    @Size(max = 20)
    @Column(name = "NET_POINT_EAST")
    private String netPointEast;
    @Size(max = 20)
    @Column(name = "STATION_NAME")
    private String stationName;
    @Size(max = 20)
    @Column(name = "GT_IN_SERVICE")
    private String gtInService;
//    @Size(max = 50)
//    @Column(name = "GT_COST_CENTER")
//    private Integer gtCostCenter;
    @Column(name = "GT_NO_OF_UNITS")
    private BigInteger gtNoOfUnits;
    @Column(name = "GT_GRADING_FACTOR")
    private BigInteger gtGradingFactor;
    @Column(name = "GT_UNIT_COST")
    private BigDecimal gtUnitCost;
    @Column(name = "GT_SERVICE_LIFE")
    private Integer gtServiceLife;
    @Column(name = "GT_STATUS")
    private Integer gtStatus;

    @OneToMany(mappedBy = "geothermalId", cascade = CascadeType.ALL)
    private List<WfMmsProcessed> geothermalList;

    @JoinColumn(name = "GT_COST_CENTER", referencedColumnName = "COST_CENTER_ID")
    @ManyToOne
    private FmsCostCenter gtCostCenter;
    @JoinColumn(name = "GT_ACCOUNT_NO", referencedColumnName = "GENERAL_LEDGER_ID")
    @ManyToOne
    private FmsGeneralLedger gtAccountNo;
    @JoinColumn(name = "GT_ASSET_TYPE", referencedColumnName = "ASSET_ID")
    @ManyToOne
    private MmsFaAssetType gtAssetType;

    @JoinColumn(name = "GT_SYS_NO", referencedColumnName = "SYSTEM_ID")
    @ManyToOne
    private FmsLuSystem gtSysNo;

    /**
     *
     */
    public MmsFaGeothermal() {
    }

    /**
     *
     * @param geothermalId
     */
    public MmsFaGeothermal(Integer geothermalId) {
        this.geothermalId = geothermalId;
    }

    /**
     *
     * @return
     */
    public Integer getGeothermalId() {
        return geothermalId;
    }

    /**
     *
     * @param geothermalId
     */
    public void setGeothermalId(Integer geothermalId) {
        this.geothermalId = geothermalId;
    }

    public FmsLuSystem getGtSysNo() {
        return gtSysNo;
    }

    public void setGtSysNo(FmsLuSystem gtSysNo) {
        this.gtSysNo = gtSysNo;
    }

    public String getGtNo() {
        return gtNo;
    }

    public void setGtNo(String gtNo) {
        this.gtNo = gtNo;
    }

    public BigDecimal getAccumulatedDpr() {
        return accumulatedDpr;
    }

    public void setAccumulatedDpr(BigDecimal accumulatedDpr) {
        this.accumulatedDpr = accumulatedDpr;
    }

    public Integer getGtStatus() {
        return gtStatus;
    }

    public void setGtStatus(Integer gtStatus) {
        this.gtStatus = gtStatus;
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

    public Integer getGtPrepared() {
        return gtPrepared;
    }

    public void setGtPrepared(Integer gtPrepared) {
        this.gtPrepared = gtPrepared;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    /**
     *
     * @return
     */
    public BigInteger getRegionNo() {
        return regionNo;
    }

    /**
     *
     * @param regionNo
     */
    public void setRegionNo(BigInteger regionNo) {
        this.regionNo = regionNo;
    }

    public Integer getGtSheetNo() {
        return gtSheetNo;
    }

    /**
     *
     * @param gtSheetNo
     */
    public void setGtSheetNo(Integer gtSheetNo) {
        this.gtSheetNo = gtSheetNo;
    }

    /**
     *
     * @return
     */
    public String getNetPointNorth() {
        return netPointNorth;
    }

    /**
     *
     * @param netPointNorth
     */
    public void setNetPointNorth(String netPointNorth) {
        this.netPointNorth = netPointNorth;
    }

    /**
     *
     * @return
     */
    public String getNetPointEast() {
        return netPointEast;
    }

    /**
     *
     * @param netPointEast
     */
    public void setNetPointEast(String netPointEast) {
        this.netPointEast = netPointEast;
    }

    /**
     *
     * @return
     */
    public String getStationName() {
        return stationName;
    }

    /**
     *
     * @param stationName
     */
    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    /**
     *
     * @return
     */
    public String getGtInService() {
        return gtInService;
    }

    /**
     *
     * @param gtInService
     */
    public void setGtInService(String gtInService) {
        this.gtInService = gtInService;
    }

    /**
     *
     * @return
     */
    public BigInteger getGtNoOfUnits() {
        return gtNoOfUnits;
    }

    /**
     *
     * @param gtNoOfUnits
     */
    public void setGtNoOfUnits(BigInteger gtNoOfUnits) {
        this.gtNoOfUnits = gtNoOfUnits;
    }

    /**
     *
     * @return
     */
    public BigInteger getGtGradingFactor() {
        return gtGradingFactor;
    }

    /**
     *
     * @param gtGradingFactor
     */
    public void setGtGradingFactor(BigInteger gtGradingFactor) {
        this.gtGradingFactor = gtGradingFactor;
    }

    /**
     *
     * @return
     */
    public BigDecimal getGtUnitCost() {
        return gtUnitCost;
    }

    /**
     *
     * @param gtUnitCost
     */
    public void setGtUnitCost(BigDecimal gtUnitCost) {
        this.gtUnitCost = gtUnitCost;
    }

    /**
     *
     * @return
     */
    public Integer getGtServiceLife() {
        return gtServiceLife;
    }

    /**
     *
     * @param gtServiceLife
     */
    public void setGtServiceLife(Integer gtServiceLife) {
        this.gtServiceLife = gtServiceLife;
    }

    /**
     *
     * @return
     */
    public FmsCostCenter getGtCostCenter() {
        return gtCostCenter;
    }

    /**
     *
     * @param gtCostCenter
     */
    public void setGtCostCenter(FmsCostCenter gtCostCenter) {
        this.gtCostCenter = gtCostCenter;
    }

    /**
     *
     * @return
     */
    public FmsGeneralLedger getGtAccountNo() {
        return gtAccountNo;
    }

    /**
     *
     * @param gtAccountNo
     */
    public void setGtAccountNo(FmsGeneralLedger gtAccountNo) {
        this.gtAccountNo = gtAccountNo;
    }

    /**
     *
     * @return
     */
    public MmsFaAssetType getGtAssetType() {
        return gtAssetType;
    }

    /**
     *
     * @param gtAssetType
     */
    public void setGtAssetType(MmsFaAssetType gtAssetType) {
        this.gtAssetType = gtAssetType;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (geothermalId != null ? geothermalId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MmsFaGeothermal)) {
            return false;
        }
        MmsFaGeothermal other = (MmsFaGeothermal) object;
        if ((this.geothermalId == null && other.geothermalId != null) || (this.geothermalId != null && !this.geothermalId.equals(other.geothermalId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return gtNo;
    }

    /**
     *
     * @return
     */
    @XmlTransient

    public List<WfMmsProcessed> getGeothermalList() {
        if (geothermalList == null) {
            geothermalList = new ArrayList<>();
        }
        return geothermalList;
    }

    public void setGeothermalList(List<WfMmsProcessed> geothermalList) {
        this.geothermalList = geothermalList;
    }

    @XmlTransient
    public List<FmsDprGeothermal> getFmsDprGeothermalList() {
        return fmsDprGeothermalList;
    }

    /**
     *
     * @param fmsDprGeothermalList
     */
    public void setFmsDprGeothermalList(List<FmsDprGeothermal> fmsDprGeothermalList) {
        this.fmsDprGeothermalList = fmsDprGeothermalList;
    }

}
