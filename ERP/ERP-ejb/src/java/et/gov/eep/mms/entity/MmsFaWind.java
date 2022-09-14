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
import et.gov.eep.fcms.entity.fixedasset.FmsDprWind;

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
@Table(name = "MMS_FA_WIND")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MmsFaWind.findAll", query = "SELECT m FROM MmsFaWind m"),
    @NamedQuery(name = "MmsFaWind.findByWindId", query = "SELECT m FROM MmsFaWind m WHERE m.windId = :windId"),
    @NamedQuery(name = "MmsFaWind.findByWdNetpointNorth", query = "SELECT m FROM MmsFaWind m WHERE m.wdNetpointNorth = :wdNetpointNorth"),
    @NamedQuery(name = "MmsFaWind.findByWdNetpointEast", query = "SELECT m FROM MmsFaWind m WHERE m.wdNetpointEast = :wdNetpointEast"),

    @NamedQuery(name = "MmsFaWind.findByAllParameters", query = "SELECT m FROM MmsFaWind m WHERE m.wdPlantName LIKE :wdPlantName"),
    @NamedQuery(name = "MmsFaWind.findBywdWindNoLike", query = "SELECT m FROM MmsFaWind m WHERE m.wdWindNo LIKE :wdWindNo"),
    @NamedQuery(name = "MmsFaWind.findBywdWindNoAndWdPrep", query = "SELECT m FROM MmsFaWind m WHERE m.wdWindNo = :wdWindNo AND m.wdPreparedBy = :wdPreparedBy"),
    @NamedQuery(name = "MmsFaWind.findBywindIdMaximum", query = "SELECT m FROM MmsFaWind m WHERE m.windId = (SELECT Max(c.windId) FROM MmsFaWind c)"),
    @NamedQuery(name = "MmsFaWind.findByWdPlantNameAndWdPrep", query = "SELECT m FROM MmsFaWind m WHERE m.wdPlantName = :wdPlantName AND m.wdPreparedBy = :wdPreparedBy"),
    @NamedQuery(name = "MmsFaWind.findAllByPreparerId", query = "SELECT m FROM MmsFaWind m WHERE m.wdPreparedBy = :wdPreparedBy"),

    @NamedQuery(name = "MmsFaWind.findByWdPlantName", query = "SELECT m FROM MmsFaWind m WHERE m.wdPlantName = :wdPlantName"),
    @NamedQuery(name = "MmsFaWind.findByWdDescription", query = "SELECT m FROM MmsFaWind m WHERE m.wdDescription = :wdDescription"),
    @NamedQuery(name = "MmsFaWind.findByWdInservice", query = "SELECT m FROM MmsFaWind m WHERE m.wdInservice = :wdInservice"),
    @NamedQuery(name = "MmsFaWind.findByWdQuantity", query = "SELECT m FROM MmsFaWind m WHERE m.wdQuantity = :wdQuantity"),
    @NamedQuery(name = "MmsFaWind.findByWdOriginalCost", query = "SELECT m FROM MmsFaWind m WHERE m.wdOriginalCost = :wdOriginalCost"),
    @NamedQuery(name = "MmsFaWind.findByWdServiceLife", query = "SELECT m FROM MmsFaWind m WHERE m.wdServiceLife = :wdServiceLife")})
public class MmsFaWind implements Serializable {

    @OneToMany(mappedBy = "wdAssetId")
    private List<FmsDprWind> fmsDprWindList;
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQUENCEWIND")
    @SequenceGenerator(name = "SEQUENCEWIND", sequenceName = "SEQUENCEWIND", allocationSize = 1)

    @Basic(optional = false)
    @NotNull
    @Column(name = "WIND_ID")
    private Integer windId;
    @Size(max = 20)
    @Column(name = "WD_NETPOINT_NORTH")
    private String wdNetpointNorth;

    @Size(max = 30)
    @Column(name = "WD_WIND_NO", length = 30)
    private String wdWindNo;

    @Column(name = "ACCUMULATED_DPR")
    private BigDecimal accumulatedDpr;
    @Column(name = "NET_BOOK_VALUE")
    private BigDecimal netBookValue;
    @Column(name = "DPR_YEAR")
    private BigDecimal dprYear;

    @Column(name = "REVALUATION_COST")
    private BigDecimal revaluationCost;

    @Column(name = "REVALUATION_SERVICE_YEAR")
    private BigDecimal revaluationServiceYear;

    @Column(name = "WD_PREPARED_BY", length = 30)
    private Integer wdPreparedBy;

    @Size(max = 50)
    @Column(name = "REGION")
    private String region;

    @Size(max = 20)
    @Column(name = "WD_NETPOINT_EAST")
    private String wdNetpointEast;
    @Size(max = 20)
    @Column(name = "WD_PLANT_NAME")
    private String wdPlantName;
    @Size(max = 20)
    @Column(name = "WD_DESCRIPTION")
    private String wdDescription;

//    @Column(name = "WD_COST_CENTER")
//    private Integer wdCostCenter;
    @Size(max = 20)
    @Column(name = "WD_INSERVICE")
    private String wdInservice;
    @Size(max = 50)
    @Column(name = "WD_STATION_NAME")
    private String wdStationName;
    @Column(name = "WD_QUANTITY")
    private BigInteger wdQuantity;
    @Column(name = "WD_ORIGINAL_COST")
    private BigDecimal wdOriginalCost;
    @Column(name = "WD_SERVICE_LIFE")
    private Integer wdServiceLife;
    @Column(name = "WD_STATUS")
    private Integer wdStatus;

    @OneToMany(mappedBy = "windId", cascade = CascadeType.ALL)
    private List<WfMmsProcessed> windList;
    @JoinColumn(name = "WD_ACCOUNT_NO", referencedColumnName = "GENERAL_LEDGER_ID")
    @ManyToOne
    private FmsGeneralLedger wdAccountNo;
    @JoinColumn(name = "WD_ASSET_TYPE", referencedColumnName = "ASSET_ID")
    @ManyToOne
    private MmsFaAssetType wdAssetType;

    @JoinColumn(name = "WD_SYS_NO", referencedColumnName = "SYSTEM_ID")
    @ManyToOne
    private FmsLuSystem wdSysNo;

    @JoinColumn(name = "WD_COST_CENTER", referencedColumnName = "COST_CENTER_ID")
    @ManyToOne
    private FmsCostCenter wdCostCenter;

    public FmsCostCenter getWdCostCenter() {
        return wdCostCenter;
    }

    public void setWdCostCenter(FmsCostCenter wdCostCenter) {
        this.wdCostCenter = wdCostCenter;
    }

    /**
     *
     */
    public MmsFaWind() {
    }

    /**
     *
     * @param windId
     */
    public MmsFaWind(Integer windId) {
        this.windId = windId;
    }

    /**
     *
     * @return
     */
    public Integer getWindId() {
        return windId;
    }

    /**
     *
     * @param windId
     */
    public void setWindId(Integer windId) {
        this.windId = windId;
    }

    public Integer getWdStatus() {
        return wdStatus;
    }

    public void setWdStatus(Integer wdStatus) {
        this.wdStatus = wdStatus;
    }

    public FmsLuSystem getWdSysNo() {
        return wdSysNo;
    }

    public void setWdSysNo(FmsLuSystem wdSysNo) {
        this.wdSysNo = wdSysNo;
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

    public BigDecimal getRevaluationServiceYear() {
        return revaluationServiceYear;
    }

    public void setRevaluationServiceYear(BigDecimal revaluationServiceYear) {
        this.revaluationServiceYear = revaluationServiceYear;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public Integer getWdPreparedBy() {
        return wdPreparedBy;
    }

    public void setWdPreparedBy(Integer wdPreparedBy) {
        this.wdPreparedBy = wdPreparedBy;
    }

    public String getWdWindNo() {
        return wdWindNo;
    }

    public void setWdWindNo(String wdWindNo) {
        this.wdWindNo = wdWindNo;
    }

    /**
     *
     * @return
     */
    public String getWdNetpointNorth() {
        return wdNetpointNorth;
    }

    /**
     *
     * @param wdNetpointNorth
     */
    public void setWdNetpointNorth(String wdNetpointNorth) {
        this.wdNetpointNorth = wdNetpointNorth;
    }

    public String getWdStationName() {
        return wdStationName;
    }

    /**
     *
     * @param wdStationName
     */
    public void setWdStationName(String wdStationName) {
        this.wdStationName = wdStationName;
    }

    /**
     *
     * @return
     */
    public String getWdNetpointEast() {
        return wdNetpointEast;
    }

    /**
     *
     * @param wdNetpointEast
     */
    public void setWdNetpointEast(String wdNetpointEast) {
        this.wdNetpointEast = wdNetpointEast;
    }

    /**
     *
     * @return
     */
    public String getWdPlantName() {
        return wdPlantName;
    }

    /**
     *
     * @param wdPlantName
     */
    public void setWdPlantName(String wdPlantName) {
        this.wdPlantName = wdPlantName;
    }

    /**
     *
     * @return
     */
    public String getWdDescription() {
        return wdDescription;
    }

    /**
     *
     * @param wdDescription
     */
    public void setWdDescription(String wdDescription) {
        this.wdDescription = wdDescription;
    }

    /**
     *
     * @return
     */
    public String getWdInservice() {
        return wdInservice;
    }

    /**
     *
     * @param wdInservice
     */
    public void setWdInservice(String wdInservice) {
        this.wdInservice = wdInservice;
    }

    /**
     *
     * @return
     */
    public BigInteger getWdQuantity() {
        return wdQuantity;
    }

    /**
     *
     * @param wdQuantity
     */
    public void setWdQuantity(BigInteger wdQuantity) {
        this.wdQuantity = wdQuantity;
    }

    /**
     *
     * @return
     */
    public BigDecimal getWdOriginalCost() {
        return wdOriginalCost;
    }

    /**
     *
     * @param wdOriginalCost
     */
    public void setWdOriginalCost(BigDecimal wdOriginalCost) {
        this.wdOriginalCost = wdOriginalCost;
    }

    /**
     *
     * @return
     */
    public Integer getWdServiceLife() {
        return wdServiceLife;
    }

    /**
     *
     * @param wdServiceLife
     */
    public void setWdServiceLife(Integer wdServiceLife) {
        this.wdServiceLife = wdServiceLife;
    }

    /**
     *
     * @return
     */
    public FmsGeneralLedger getWdAccountNo() {
        return wdAccountNo;
    }

    /**
     *
     * @param wdAccountNo
     */
    public void setWdAccountNo(FmsGeneralLedger wdAccountNo) {
        this.wdAccountNo = wdAccountNo;
    }

    /**
     *
     * @return
     */
    public MmsFaAssetType getWdAssetType() {
        return wdAssetType;
    }

    /**
     *
     * @param wdAssetType
     */
    public void setWdAssetType(MmsFaAssetType wdAssetType) {
        this.wdAssetType = wdAssetType;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (windId != null ? windId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MmsFaWind)) {
            return false;
        }
        MmsFaWind other = (MmsFaWind) object;
        if ((this.windId == null && other.windId != null) || (this.windId != null && !this.windId.equals(other.windId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return wdWindNo;
    }

    @XmlTransient
    public List<WfMmsProcessed> getWindList() {
        if (windList == null) {
            windList = new ArrayList<>();
        }
        return windList;
    }

    public void setWindList(List<WfMmsProcessed> windList) {
        this.windList = windList;
    }

    /**
     *
     * @return
     */
    @XmlTransient
    public List<FmsDprWind> getFmsDprWindList() {
        return fmsDprWindList;
    }

    /**
     *
     * @param fmsDprWindList
     */
    public void setFmsDprWindList(List<FmsDprWind> fmsDprWindList) {
        this.fmsDprWindList = fmsDprWindList;
    }

}
