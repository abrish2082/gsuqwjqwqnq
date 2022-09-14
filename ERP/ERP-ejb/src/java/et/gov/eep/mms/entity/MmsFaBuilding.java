/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.mms.entity;

import et.gov.eep.commonApplications.entity.WfMmsProcessed;
import et.gov.eep.fcms.entity.admin.FmsCostCenter;
import et.gov.eep.fcms.entity.admin.FmsLuSystem;
import et.gov.eep.fcms.entity.fixedasset.FmsDprBuilding;
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
 * @author w_station
 */
@Entity
@Table(name = "MMS_FA_BUILDING")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MmsFaBuilding.findAll", query = "SELECT m FROM MmsFaBuilding m"),
    @NamedQuery(name = "MmsFaBuilding.findByBuildingId", query = "SELECT m FROM MmsFaBuilding m WHERE m.buildingId = :buildingId"),
    @NamedQuery(name = "MmsFaBuilding.findByBuArea", query = "SELECT m FROM MmsFaBuilding m WHERE m.buArea = :buArea"),

    @NamedQuery(name = "MmsFaBuilding.findBybuNoLike", query = "SELECT m FROM MmsFaBuilding m WHERE m.buNo LIKE :buNo"),
    @NamedQuery(name = "MmsFaBuilding.findBybuNoAndProcessedBy", query = "SELECT m FROM MmsFaBuilding m WHERE m.buNo = :buNo AND m.buPrepared = :buPrepared"),
    @NamedQuery(name = "MmsFaBuilding.findByBuildingNameLike", query = "SELECT m FROM MmsFaBuilding m WHERE m.buildingName LIKE :buildingName"),
    @NamedQuery(name = "MmsFaBuilding.findByBuildingNameAndBuPrep", query = "SELECT m FROM MmsFaBuilding m WHERE m.buildingName = :buildingName AND m.buPrepared = :buPrepared "),
    @NamedQuery(name = "MmsFaBuilding.findByBuGradingFactor", query = "SELECT m FROM MmsFaBuilding m WHERE m.buGradingFactor = :buGradingFactor"),
    @NamedQuery(name = "MmsFaBuilding.findByBuInservice", query = "SELECT m FROM MmsFaBuilding m WHERE m.buInservice = :buInservice"),
    @NamedQuery(name = "MmsFaBuilding.findAllByPreparerId", query = "SELECT m FROM MmsFaBuilding m WHERE m.buPrepared = :buPrepared"),
    @NamedQuery(name = "MmsFaBuilding.findByBuLocation", query = "SELECT m FROM MmsFaBuilding m WHERE m.buLocation = :buLocation"),
    @NamedQuery(name = "MmsFaBuilding.findByBuNetpointEast", query = "SELECT m FROM MmsFaBuilding m WHERE m.buNetpointEast = :buNetpointEast"),
    @NamedQuery(name = "MmsFaBuilding.findByBuNetpointNorth", query = "SELECT m FROM MmsFaBuilding m WHERE m.buNetpointNorth = :buNetpointNorth"),
    @NamedQuery(name = "MmsFaBuilding.findByBuRegionNo", query = "SELECT m FROM MmsFaBuilding m WHERE m.buRegionNo = :buRegionNo"),
    @NamedQuery(name = "MmsFaBuilding.findByBuServiceLife", query = "SELECT m FROM MmsFaBuilding m WHERE m.buServiceLife = :buServiceLife"),

    @NamedQuery(name = "MmsFaBuilding.findBybuildingIdMaximum", query = "SELECT m FROM MmsFaBuilding m WHERE m.buildingId = (SELECT Max(c.buildingId) FROM MmsFaBuilding c)"),
    @NamedQuery(name = "MmsFaBuilding.findByAllParameters", query = "SELECT m FROM MmsFaBuilding m WHERE m.buildingName LIKE :buildingName"),

    @NamedQuery(name = "MmsFaBuilding.findByBuSheetNo", query = "SELECT m FROM MmsFaBuilding m WHERE m.buSheetNo = :buSheetNo"),
    @NamedQuery(name = "MmsFaBuilding.findByBuUnitCost", query = "SELECT m FROM MmsFaBuilding m WHERE m.buUnitCost = :buUnitCost"),
    @NamedQuery(name = "MmsFaBuilding.findByBuildingName", query = "SELECT m FROM MmsFaBuilding m WHERE m.buildingName = :buildingName"),
    @NamedQuery(name = "MmsFaBuilding.findByBuAccountNo", query = "SELECT m FROM MmsFaBuilding m WHERE m.buAccountNo = :buAccountNo"),
    @NamedQuery(name = "MmsFaBuilding.findByBuSysNo", query = "SELECT m FROM MmsFaBuilding m WHERE m.buSysNo = :buSysNo"),
    @NamedQuery(name = "MmsFaBuilding.findByBuCostCenter", query = "SELECT m FROM MmsFaBuilding m WHERE m.buCostCenter = :buCostCenter"),
    @NamedQuery(name = "MmsFaBuilding.findByBuNo", query = "SELECT m FROM MmsFaBuilding m WHERE m.buNo = :buNo"),
    @NamedQuery(name = "MmsFaBuilding.findByBuRegion", query = "SELECT m FROM MmsFaBuilding m WHERE m.buRegion = :buRegion"),
    @NamedQuery(name = "MmsFaBuilding.findByBuPrepared", query = "SELECT m FROM MmsFaBuilding m WHERE m.buPrepared = :buPrepared")})
public class MmsFaBuilding implements Serializable {

    @Column(name = "BU_UNIT_COST")
    private BigDecimal buUnitCost;
    @Column(name = "DPR_YEAR")
    private BigDecimal dprYear;
    @Column(name = "NET_BOOK_VALUE")
    private BigDecimal netBookValue;
    @Column(name = "ACCUMULATED_DPR")
    private BigDecimal accumulatedDpr;
    @Column(name = "REVALUATION_SERVICE_YEAR")
    private Integer revaluationServiceYear;
    @Column(name = "REVALUATION_COST")
    private BigDecimal revaluationCost;

    @OneToMany(mappedBy = "buAssetId")
    private List<FmsDprBuilding> fmsDprBuildingList;
    private static final long serialVersionUID = 1L;

    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQUENCEBLDG")
    @SequenceGenerator(name = "SEQUENCEBLDG", sequenceName = "SEQUENCEBLDG", allocationSize = 1)
    @Basic(optional = false)
    @NotNull
    @Column(name = "BUILDING_ID", nullable = false, precision = 38, scale = 0)
    private Integer buildingId;
    @Column(name = "BU_AREA")
    private BigInteger buArea;
    @Column(name = "BU_GRADING_FACTOR")
    private BigInteger buGradingFactor;
    @Size(max = 255)
    @Column(name = "BU_INSERVICE", length = 255)
    private String buInservice;
    @Size(max = 255)
    @Column(name = "BU_LOCATION", length = 255)
    private String buLocation;
    @Size(max = 255)
    @Column(name = "BU_NETPOINT_EAST", length = 255)
    private String buNetpointEast;
    @Size(max = 255)
    @Column(name = "BU_NETPOINT_NORTH", length = 255)
    private String buNetpointNorth;
    @Column(name = "BU_REGION_NO")
    private BigInteger buRegionNo;
    @Column(name = "BU_SERVICE_LIFE")
    private Integer buServiceLife;
    @Column(name = "BU_STATUS")
    private Integer buStatus;
    @Column(name = "BU_SHEET_NO")
    private BigInteger buSheetNo;
    @Size(max = 255)
    @Column(name = "BUILDING_NAME", length = 255)
    private String buildingName;
    @Column(name = "BU_ACCOUNT_NO")
    private Long buAccountNo;
    @Size(max = 30)
    @Column(name = "BU_NO", length = 30)
    private String buNo;
    @Size(max = 30)
    @Column(name = "BU_REGION", length = 30)
    private String buRegion;

    @Column(name = "BU_PREPARED", length = 30)
    private Integer buPrepared;

    @OneToMany(mappedBy = "buildingId", cascade = CascadeType.ALL)
    private List<WfMmsProcessed> buildingList;

    @JoinColumn(name = "BU_COST_CENTER", referencedColumnName = "COST_CENTER_ID")
    @ManyToOne
    private FmsCostCenter buCostCenter;
    @JoinColumn(name = "BU_ASSET_TYPE", referencedColumnName = "ASSET_ID")
    @ManyToOne
    private MmsFaAssetType buAssetType;
    @JoinColumn(name = "BU_SYS_NO", referencedColumnName = "SYSTEM_ID")
    @ManyToOne
    private FmsLuSystem buSysNo;

    public MmsFaAssetType getBuAssetType() {
        return buAssetType;
    }

    public void setBuAssetType(MmsFaAssetType buAssetType) {
        this.buAssetType = buAssetType;
    }

    public MmsFaBuilding() {
    }

    public MmsFaBuilding(Integer buildingId) {
        this.buildingId = buildingId;
    }

    public Integer getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(Integer buildingId) {
        this.buildingId = buildingId;
    }

    public BigInteger getBuArea() {
        return buArea;
    }

    public void setBuArea(BigInteger buArea) {
        this.buArea = buArea;
    }

    public FmsCostCenter getBuCostCenter() {
        return buCostCenter;
    }

    public void setBuCostCenter(FmsCostCenter buCostCenter) {
        this.buCostCenter = buCostCenter;
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

    public BigInteger getBuGradingFactor() {
        return buGradingFactor;
    }

    public void setBuGradingFactor(BigInteger buGradingFactor) {
        this.buGradingFactor = buGradingFactor;
    }

    public Integer getBuStatus() {
        return buStatus;
    }

    public void setBuStatus(Integer buStatus) {
        this.buStatus = buStatus;
    }

    public String getBuInservice() {
        return buInservice;
    }

    public void setBuInservice(String buInservice) {
        this.buInservice = buInservice;
    }

    public String getBuLocation() {
        return buLocation;
    }

    public void setBuLocation(String buLocation) {
        this.buLocation = buLocation;
    }

    public String getBuNetpointEast() {
        return buNetpointEast;
    }

    public void setBuNetpointEast(String buNetpointEast) {
        this.buNetpointEast = buNetpointEast;
    }

    public String getBuNetpointNorth() {
        return buNetpointNorth;
    }

    public void setBuNetpointNorth(String buNetpointNorth) {
        this.buNetpointNorth = buNetpointNorth;
    }

    public FmsLuSystem getBuSysNo() {
        return buSysNo;
    }

    public void setBuSysNo(FmsLuSystem buSysNo) {
        this.buSysNo = buSysNo;
    }

    public BigInteger getBuRegionNo() {
        return buRegionNo;
    }

    public void setBuRegionNo(BigInteger buRegionNo) {
        this.buRegionNo = buRegionNo;
    }

    public Integer getBuServiceLife() {
        return buServiceLife;
    }

    public void setBuServiceLife(Integer buServiceLife) {
        this.buServiceLife = buServiceLife;
    }

    public BigInteger getBuSheetNo() {
        return buSheetNo;
    }

    public void setBuSheetNo(BigInteger buSheetNo) {
        this.buSheetNo = buSheetNo;
    }

    public BigDecimal getBuUnitCost() {
        return buUnitCost;
    }

    public void setBuUnitCost(BigDecimal buUnitCost) {
        this.buUnitCost = buUnitCost;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public Long getBuAccountNo() {
        return buAccountNo;
    }

    public void setBuAccountNo(Long buAccountNo) {
        this.buAccountNo = buAccountNo;
    }

//    public Long getBuSysNo() {
//        return buSysNo;
//    }
//
//    public void setBuSysNo(Long buSysNo) {
//        this.buSysNo = buSysNo;
//    }
    public String getBuNo() {
        return buNo;
    }

    public void setBuNo(String buNo) {
        this.buNo = buNo;
    }

    public String getBuRegion() {
        return buRegion;
    }

    public void setBuRegion(String buRegion) {
        this.buRegion = buRegion;
    }

    public Integer getBuPrepared() {
        return buPrepared;
    }

    public void setBuPrepared(Integer buPrepared) {
        this.buPrepared = buPrepared;
    }

    @XmlTransient
    public List<WfMmsProcessed> getBuildingList() {
        if (buildingList == null) {
            buildingList = new ArrayList<>();
        }
        return buildingList;
    }

    public void setBuildingList(List<WfMmsProcessed> buildingList) {
        this.buildingList = buildingList;
    }

    @XmlTransient
    public List<FmsDprBuilding> getFmsDprBuildingList() {
        return fmsDprBuildingList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (buildingId != null ? buildingId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MmsFaBuilding)) {
            return false;
        }
        MmsFaBuilding other = (MmsFaBuilding) object;
        if ((this.buildingId == null && other.buildingId != null) || (this.buildingId != null && !this.buildingId.equals(other.buildingId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return buNo;
    }

}
