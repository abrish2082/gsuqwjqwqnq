/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.mms.entity;

import et.gov.eep.commonApplications.entity.WfMmsProcessed;
import et.gov.eep.fcms.entity.admin.FmsCostCenter;
import et.gov.eep.fcms.entity.admin.FmsLuSystem;
import et.gov.eep.fcms.entity.fixedasset.FmsDprHydropower;
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
@Table(name = "MMS_FA_HYDROPOWER")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MmsFaHydropower.findAll", query = "SELECT m FROM MmsFaHydropower m"),
    @NamedQuery(name = "MmsFaHydropower.findByHydroPowerId", query = "SELECT m FROM MmsFaHydropower m WHERE m.hydroPowerId = :hydroPowerId"),
    @NamedQuery(name = "MmsFaHydropower.findByHpRegionNo", query = "SELECT m FROM MmsFaHydropower m WHERE m.hpRegionNo = :hpRegionNo"),
    @NamedQuery(name = "MmsFaHydropower.findByHpNetpointNorth", query = "SELECT m FROM MmsFaHydropower m WHERE m.hpNetpointNorth = :hpNetpointNorth"),

    @NamedQuery(name = "MmsFaHydropower.findByhydroPowerIdMaximum", query = "SELECT m FROM MmsFaHydropower m WHERE m.hydroPowerId = (SELECT Max(c.hydroPowerId) FROM MmsFaHydropower c)"),
    @NamedQuery(name = "MmsFaHydropower.findByhpNoLike", query = "SELECT m FROM MmsFaHydropower m WHERE m.hpNo LIKE :hpNo"),
    @NamedQuery(name = "MmsFaHydropower.findByhpNoandProcessedby", query = "SELECT m FROM MmsFaHydropower m WHERE m.hpNo = :hpNo AND m.hpPrepared = :hpPrepared "),
    @NamedQuery(name = "MmsFaHydropower.findByhpLocLike", query = "SELECT m FROM MmsFaHydropower m WHERE m.hpLocation LIKE :hpLocation"),
    @NamedQuery(name = "MmsFaHydropower.findAllByPreparerId", query = "SELECT m FROM MmsFaHydropower m WHERE m.hpPrepared = :hpPrepared"),

    @NamedQuery(name = "MmsFaHydropower.findByHpNetpointEast", query = "SELECT m FROM MmsFaHydropower m WHERE m.hpNetpointEast = :hpNetpointEast"),
    @NamedQuery(name = "MmsFaHydropower.findByHpLocation", query = "SELECT m FROM MmsFaHydropower m WHERE m.hpLocation = :hpLocation"),
    @NamedQuery(name = "MmsFaHydropower.findByHpInservice", query = "SELECT m FROM MmsFaHydropower m WHERE m.hpInservice = :hpInservice"),
    @NamedQuery(name = "MmsFaHydropower.findByHpUnitCost", query = "SELECT m FROM MmsFaHydropower m WHERE m.hpUnitCost = :hpUnitCost"),
    @NamedQuery(name = "MmsFaHydropower.findByHpServiceLife", query = "SELECT m FROM MmsFaHydropower m WHERE m.hpServiceLife = :hpServiceLife"),
    @NamedQuery(name = "MmsFaHydropower.findByHpNoOfUnits", query = "SELECT m FROM MmsFaHydropower m WHERE m.hpNoOfUnits = :hpNoOfUnits")})
public class MmsFaHydropower implements Serializable {

    @Column(name = "HP_SHEET_NO")
    private Long hpSheetNo;
    @Column(name = "HP_UNIT_COST")
    private BigDecimal hpUnitCost;
    @Column(name = "HP_ACCOUNT_NO")
    private Long hpAccountNo;
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
    @Column(name = "HP_STATUS")
    private Integer hpStatus;
    @OneToMany(mappedBy = "hpAssetId")
    private List<FmsDprHydropower> fmsDprHydropowerList;
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQUENCEHP")
    @SequenceGenerator(name = "SEQUENCEHP", sequenceName = "SEQUENCEHP", allocationSize = 1)

    @Basic(optional = false)
    @NotNull
    @Column(name = "HYDRO_POWER_ID")
    private Integer hydroPowerId;
    @Column(name = "HP_REGION_NO")
    private BigInteger hpRegionNo;
    @Size(max = 20)
    @Column(name = "HP_NETPOINT_NORTH")
    private String hpNetpointNorth;
    @Size(max = 20)
    @Column(name = "HP_NETPOINT_EAST")
    private String hpNetpointEast;
    @Size(max = 20)
    @Column(name = "HP_LOCATION")
    private String hpLocation;

    @Size(max = 30)
    @Column(name = "HP_NO", length = 30)
    private String hpNo;
    @Size(max = 30)
    @Column(name = "HP_REGION", length = 30)
    private String hpRegion;

    @Column(name = "HP_PREPARED")
    private Integer hpPrepared;

    @Size(max = 20)
    @Column(name = "HP_INSERVICE")
    private String hpInservice;
    @Column(name = "HP_SERVICE_LIFE")
    private Integer hpServiceLife;
    @Column(name = "HP_NO_OF_UNITS")
    private BigInteger hpNoOfUnits;

    @OneToMany(mappedBy = "hydroPowerId", cascade = CascadeType.ALL)
    private List<WfMmsProcessed> hydroList;
    @JoinColumn(name = "HP_COST_CENTER", referencedColumnName = "COST_CENTER_ID")
    @ManyToOne
    private FmsCostCenter hpCostCenter;
    @JoinColumn(name = "HP_ASSET_TYPE", referencedColumnName = "ASSET_ID")
    @ManyToOne
    private MmsFaAssetType hpAssetType;

    @JoinColumn(name = "HP_SYS_NO", referencedColumnName = "SYSTEM_ID")
    @ManyToOne
    private FmsLuSystem hpSysNo;

    /**
     *
     */
    public MmsFaHydropower() {
    }

    /**
     *
     * @param hydroPowerId
     */
    public MmsFaHydropower(Integer hydroPowerId) {
        this.hydroPowerId = hydroPowerId;
    }

    /**
     *
     * @return
     */
    public Integer getHydroPowerId() {
        return hydroPowerId;
    }

    /**
     *
     * @param hydroPowerId
     */
    public void setHydroPowerId(Integer hydroPowerId) {
        this.hydroPowerId = hydroPowerId;
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

    public Integer getHpStatus() {
        return hpStatus;
    }

    public void setHpStatus(Integer hpStatus) {
        this.hpStatus = hpStatus;
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

    public String getHpNo() {
        return hpNo;
    }

    public void setHpNo(String hpNo) {
        this.hpNo = hpNo;
    }

    public FmsLuSystem getHpSysNo() {
        return hpSysNo;
    }

    public void setHpSysNo(FmsLuSystem hpSysNo) {
        this.hpSysNo = hpSysNo;
    }

    public String getHpRegion() {
        return hpRegion;
    }

    public void setHpRegion(String hpRegion) {
        this.hpRegion = hpRegion;
    }

    public Integer getHpPrepared() {
        return hpPrepared;
    }

    public void setHpPrepared(Integer hpPrepared) {
        this.hpPrepared = hpPrepared;
    }

    /**
     *
     * @return
     */
    public BigInteger getHpRegionNo() {
        return hpRegionNo;
    }

    /**
     *
     * @param hpRegionNo
     */
    public void setHpRegionNo(BigInteger hpRegionNo) {
        this.hpRegionNo = hpRegionNo;
    }

    /**
     *
     * @return
     */
    public String getHpNetpointNorth() {
        return hpNetpointNorth;
    }

    /**
     *
     * @param hpNetpointNorth
     */
    public void setHpNetpointNorth(String hpNetpointNorth) {
        this.hpNetpointNorth = hpNetpointNorth;
    }

    /**
     *
     * @return
     */
    public String getHpNetpointEast() {
        return hpNetpointEast;
    }

    /**
     *
     * @param hpNetpointEast
     */
    public void setHpNetpointEast(String hpNetpointEast) {
        this.hpNetpointEast = hpNetpointEast;
    }

    /**
     *
     * @return
     */
    public String getHpLocation() {
        return hpLocation;
    }

    /**
     *
     * @param hpLocation
     */
    public void setHpLocation(String hpLocation) {
        this.hpLocation = hpLocation;
    }

    /**
     *
     * @return
     */
    public String getHpInservice() {
        return hpInservice;
    }

    /**
     *
     * @param hpInservice
     */
    public void setHpInservice(String hpInservice) {
        this.hpInservice = hpInservice;
    }

    /**
     *
     * @return
     */
    public BigDecimal getHpUnitCost() {
        return hpUnitCost;
    }

    /**
     *
     * @param hpUnitCost
     */
    public void setHpUnitCost(BigDecimal hpUnitCost) {
        this.hpUnitCost = hpUnitCost;
    }

    /**
     *
     * @return
     */
    public Integer getHpServiceLife() {
        return hpServiceLife;
    }

    /**
     *
     * @param hpServiceLife
     */
    public void setHpServiceLife(Integer hpServiceLife) {
        this.hpServiceLife = hpServiceLife;
    }

    /**
     *
     * @return
     */
    public BigInteger getHpNoOfUnits() {
        return hpNoOfUnits;
    }

    /**
     *
     * @param hpNoOfUnits
     */
    public void setHpNoOfUnits(BigInteger hpNoOfUnits) {
        this.hpNoOfUnits = hpNoOfUnits;
    }

    /**
     *
     * @return
     */
    public FmsCostCenter getHpCostCenter() {
        return hpCostCenter;
    }

    /**
     *
     * @param hpCostCenter
     */
    public void setHpCostCenter(FmsCostCenter hpCostCenter) {
        this.hpCostCenter = hpCostCenter;
    }

    public MmsFaAssetType getHpAssetType() {
        return hpAssetType;
    }

    /**
     *
     * @param hpAssetType
     */
    public void setHpAssetType(MmsFaAssetType hpAssetType) {
        this.hpAssetType = hpAssetType;
    }

    public Long getHpSheetNo() {
        return hpSheetNo;
    }

    public void setHpSheetNo(Long hpSheetNo) {
        this.hpSheetNo = hpSheetNo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (hydroPowerId != null ? hydroPowerId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MmsFaHydropower)) {
            return false;
        }
        MmsFaHydropower other = (MmsFaHydropower) object;
        if ((this.hydroPowerId == null && other.hydroPowerId != null) || (this.hydroPowerId != null && !this.hydroPowerId.equals(other.hydroPowerId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return hpNo;
    }

    @XmlTransient
    public List<WfMmsProcessed> getHydroList() {
        if (hydroList == null) {
            hydroList = new ArrayList<>();
        }
        return hydroList;
    }

    public void setHydroList(List<WfMmsProcessed> hydroList) {
        this.hydroList = hydroList;
    }

    /**
     *
     * @return
     */
    @XmlTransient
    public List<FmsDprHydropower> getFmsDprHydropowerList() {
        return fmsDprHydropowerList;
    }

    /**
     *
     * @param fmsDprHydropowerList
     */
    public void setFmsDprHydropowerList(List<FmsDprHydropower> fmsDprHydropowerList) {
        this.fmsDprHydropowerList = fmsDprHydropowerList;
    }

    public Long getHpAccountNo() {
        return hpAccountNo;
    }

    public void setHpAccountNo(Long hpAccountNo) {
        this.hpAccountNo = hpAccountNo;
    }

}
