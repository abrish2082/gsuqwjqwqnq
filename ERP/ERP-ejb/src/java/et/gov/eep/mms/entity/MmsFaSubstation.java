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
import et.gov.eep.fcms.entity.fixedasset.FmsDprSubstation;
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

/**
 *
 * @author Binyam
 */
@Entity
@Table(name = "MMS_FA_SUBSTATION")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MmsFaSubstation.findAll", query = "SELECT m FROM MmsFaSubstation m"),
    @NamedQuery(name = "MmsFaSubstation.findBySubstationId", query = "SELECT m FROM MmsFaSubstation m WHERE m.substationId = :substationId"),
    @NamedQuery(name = "MmsFaSubstation.findBySsRegionNo", query = "SELECT m FROM MmsFaSubstation m WHERE m.ssRegionNo = :ssRegionNo"),
    @NamedQuery(name = "MmsFaSubstation.findBySsSheetNo", query = "SELECT m FROM MmsFaSubstation m WHERE m.ssSheetNo = :ssSheetNo"),
    @NamedQuery(name = "MmsFaSubstation.findBySsNetpointNorth", query = "SELECT m FROM MmsFaSubstation m WHERE m.ssNetpointNorth = :ssNetpointNorth"),

    @NamedQuery(name = "MmsFaSubstation.findByssNoLike", query = "SELECT m FROM MmsFaSubstation m WHERE m.ssNo LIKE :ssNo"),
    @NamedQuery(name = "MmsFaSubstation.findByssNoAndSubPrep", query = "SELECT m FROM MmsFaSubstation m WHERE m.ssNo = :ssNo AND m.ssPrepared = :ssPrepared"),
    @NamedQuery(name = "MmsFaSubstation.findBysubstationIdMaximum", query = "SELECT m FROM MmsFaSubstation m WHERE m.substationId = (SELECT Max(c.substationId) FROM MmsFaSubstation c)"),
    @NamedQuery(name = "MmsFaSubstation.findBySsLocationLike", query = "SELECT m FROM MmsFaSubstation m WHERE m.ssLocation LIKE :ssLocation"),
    @NamedQuery(name = "MmsFaSubstation.findBySsLocationAndSsPrep", query = "SELECT m FROM MmsFaSubstation m WHERE m.ssLocation = :ssLocation AND m.ssPrepared = :ssPrepared"),
    @NamedQuery(name = "MmsFaSubstation.findAllByPreparerId", query = "SELECT m FROM MmsFaSubstation m WHERE m.ssPrepared = :ssPrepared"),

    @NamedQuery(name = "MmsFaSubstation.findBySsNetpointEast", query = "SELECT m FROM MmsFaSubstation m WHERE m.ssNetpointEast = :ssNetpointEast"),
    @NamedQuery(name = "MmsFaSubstation.findBySsLocation", query = "SELECT m FROM MmsFaSubstation m WHERE m.ssLocation = :ssLocation"),
    @NamedQuery(name = "MmsFaSubstation.findBySsCostCenter", query = "SELECT m FROM MmsFaSubstation m WHERE m.ssCostCenter = :ssCostCenter"),
    @NamedQuery(name = "MmsFaSubstation.findBySsInservice", query = "SELECT m FROM MmsFaSubstation m WHERE m.ssInservice = :ssInservice"),
    @NamedQuery(name = "MmsFaSubstation.findBySsNoOfUnits", query = "SELECT m FROM MmsFaSubstation m WHERE m.ssNoOfUnits = :ssNoOfUnits"),
    @NamedQuery(name = "MmsFaSubstation.findBySsUnitCost", query = "SELECT m FROM MmsFaSubstation m WHERE m.ssUnitCost = :ssUnitCost"),
    @NamedQuery(name = "MmsFaSubstation.findBySsServiceLife", query = "SELECT m FROM MmsFaSubstation m WHERE m.ssServiceLife = :ssServiceLife")})
public class MmsFaSubstation implements Serializable {

    @OneToMany(mappedBy = "ssAssetId")
    private List<FmsDprSubstation> fmsDprSubstationList;
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQUENCESUB")
    @SequenceGenerator(name = "SEQUENCESUB", sequenceName = "SEQUENCESUB", allocationSize = 1)

    @Basic(optional = false)
    @NotNull
    @Column(name = "SUBSTATION_ID")
    private Integer substationId;
    @Column(name = "SS_REGION_NO")
    private BigInteger ssRegionNo;
    @Column(name = "SS_SHEET_NO")
    private BigInteger ssSheetNo;
    @Size(max = 20)
    @Column(name = "SS_NETPOINT_NORTH")
    private String ssNetpointNorth;
    @Size(max = 20)
    @Column(name = "SS_NETPOINT_EAST")
    private String ssNetpointEast;

    @Size(max = 30)
    @Column(name = "SS_NO", length = 30)
    private String ssNo;
    @Size(max = 30)
    @Column(name = "SS_REGION", length = 30)
    private String ssRegion;

    @Column(name = "SS_PREPARED", length = 30)
    private Integer ssPrepared;

    @Column(name = "SS_STATUS")
    private Integer ssStatus;

    @Column(name = "ACCUMULATED_DPR")
    private BigDecimal accumulatedDpr;
    @Column(name = "NET_BOOK_VALUE")
    private BigDecimal netBookValue;
    @Column(name = "DPR_YEAR")
    private BigDecimal dprYear;

    @Column(name = "REVALUATION_COST")
    private BigDecimal revaluationCost;

    @Column(name = "REVALUATION_SERVICE_YEAR")
    private int revaluationServiceYear;

    @Size(max = 20)
    @Column(name = "SS_LOCATION")
    private String ssLocation;
//    @Column(name = "SS_COST_CENTER")
//    private Integer ssCostCenter;

    @JoinColumn(name = "SS_COST_CENTER", referencedColumnName = "COST_CENTER_ID")
    @ManyToOne
    private FmsCostCenter ssCostCenter;

    public FmsCostCenter getSsCostCenter() {
        return ssCostCenter;
    }

    public void setSsCostCenter(FmsCostCenter ssCostCenter) {
        this.ssCostCenter = ssCostCenter;
    }

    @Size(max = 20)
    @Column(name = "SS_INSERVICE")
    private String ssInservice;
    @Column(name = "SS_NO_OF_UNITS")
    private BigInteger ssNoOfUnits;
    @Column(name = "SS_UNIT_COST")
    private BigDecimal ssUnitCost;
    @Column(name = "SS_SERVICE_LIFE")
    private Integer ssServiceLife;
    @JoinColumn(name = "SUBSTATION_ID", referencedColumnName = "COST_CENTER_ID", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private FmsCostCenter fmsCostCenter;
    @JoinColumn(name = "SS_ACCOUNT_NO", referencedColumnName = "GENERAL_LEDGER_ID")
    @ManyToOne
    private FmsGeneralLedger ssAccountNo;
    @JoinColumn(name = "SS_ASSET_TYPE", referencedColumnName = "ASSET_ID")
    @ManyToOne
    private MmsFaAssetType ssAssetType;
    @JoinColumn(name = "SS_SYS_NO", referencedColumnName = "SYSTEM_ID")
    @ManyToOne
    private FmsLuSystem ssSysNo;

    @OneToMany(mappedBy = "substationId", cascade = CascadeType.ALL)
    private List<WfMmsProcessed> substationList;

    /**
     *
     */
    public MmsFaSubstation() {
    }

    /**
     *
     * @param substationId
     */
    public MmsFaSubstation(Integer substationId) {
        this.substationId = substationId;
    }

    /**
     *
     * @return
     */
    public Integer getSubstationId() {
        return substationId;
    }

    /**
     *
     * @param substationId
     */
    public void setSubstationId(Integer substationId) {
        this.substationId = substationId;
    }

    public BigDecimal getAccumulatedDpr() {
        return accumulatedDpr;
    }

    public void setAccumulatedDpr(BigDecimal accumulatedDpr) {
        this.accumulatedDpr = accumulatedDpr;
    }

    public BigDecimal getRevaluationCost() {
        return revaluationCost;
    }

    public void setRevaluationCost(BigDecimal revaluationCost) {
        this.revaluationCost = revaluationCost;
    }

    public int getRevaluationServiceYear() {
        return revaluationServiceYear;
    }

    public void setRevaluationServiceYear(int revaluationServiceYear) {
        this.revaluationServiceYear = revaluationServiceYear;
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

    public String getSsNo() {
        return ssNo;
    }

    public void setSsNo(String ssNo) {
        this.ssNo = ssNo;
    }

    public FmsLuSystem getSsSysNo() {
        return ssSysNo;
    }

    public void setSsSysNo(FmsLuSystem ssSysNo) {
        this.ssSysNo = ssSysNo;
    }

    public Integer getSsStatus() {
        return ssStatus;
    }

    public void setSsStatus(Integer ssStatus) {
        this.ssStatus = ssStatus;
    }

    public String getSsRegion() {
        return ssRegion;
    }

    public void setSsRegion(String ssRegion) {
        this.ssRegion = ssRegion;
    }

    public Integer getSsPrepared() {
        return ssPrepared;
    }

    public void setSsPrepared(Integer ssPrepared) {
        this.ssPrepared = ssPrepared;
    }

    /**
     *
     * @return
     */
    public BigInteger getSsRegionNo() {
        return ssRegionNo;
    }

    /**
     *
     * @param ssRegionNo
     */
    public void setSsRegionNo(BigInteger ssRegionNo) {
        this.ssRegionNo = ssRegionNo;
    }

    /**
     *
     * @return
     */
    public BigInteger getSsSheetNo() {
        return ssSheetNo;
    }

    /**
     *
     * @param ssSheetNo
     */
    public void setSsSheetNo(BigInteger ssSheetNo) {
        this.ssSheetNo = ssSheetNo;
    }

    /**
     *
     * @return
     */
    public String getSsNetpointNorth() {
        return ssNetpointNorth;
    }

    /**
     *
     * @param ssNetpointNorth
     */
    public void setSsNetpointNorth(String ssNetpointNorth) {
        this.ssNetpointNorth = ssNetpointNorth;
    }

    /**
     *
     * @return
     */
    public String getSsNetpointEast() {
        return ssNetpointEast;
    }

    /**
     *
     * @param ssNetpointEast
     */
    public void setSsNetpointEast(String ssNetpointEast) {
        this.ssNetpointEast = ssNetpointEast;
    }

    /**
     *
     * @return
     */
    public String getSsLocation() {
        return ssLocation;
    }

    /**
     *
     * @param ssLocation
     */
    public void setSsLocation(String ssLocation) {
        this.ssLocation = ssLocation;
    }

    /**
     *
     * @return
     */
//    public Integer getSsCostCenter() {
//        return ssCostCenter;
//    }
//
//    /**
//     *
//     * @param ssCostCenter
//     */
//    public void setSsCostCenter(Integer ssCostCenter) {
//        this.ssCostCenter = ssCostCenter;
//    }
    /**
     *
     * @return
     */
    public String getSsInservice() {
        return ssInservice;
    }

    /**
     *
     * @param ssInservice
     */
    public void setSsInservice(String ssInservice) {
        this.ssInservice = ssInservice;
    }

    /**
     *
     * @return
     */
    public BigInteger getSsNoOfUnits() {
        return ssNoOfUnits;
    }

    /**
     *
     * @param ssNoOfUnits
     */
    public void setSsNoOfUnits(BigInteger ssNoOfUnits) {
        this.ssNoOfUnits = ssNoOfUnits;
    }

    /**
     *
     * @return
     */
    public BigDecimal getSsUnitCost() {
        return ssUnitCost;
    }

    /**
     *
     * @param ssUnitCost
     */
    public void setSsUnitCost(BigDecimal ssUnitCost) {
        this.ssUnitCost = ssUnitCost;
    }

    /**
     *
     * @return
     */
    public Integer getSsServiceLife() {
        return ssServiceLife;
    }

    /**
     *
     * @param ssServiceLife
     */
    public void setSsServiceLife(Integer ssServiceLife) {
        this.ssServiceLife = ssServiceLife;
    }

    /**
     *
     * @return
     */
    public FmsCostCenter getFmsCostCenter() {
        return fmsCostCenter;
    }

    /**
     *
     * @param fmsCostCenter
     */
    public void setFmsCostCenter(FmsCostCenter fmsCostCenter) {
        this.fmsCostCenter = fmsCostCenter;
    }

    /**
     *
     * @return
     */
    public FmsGeneralLedger getSsAccountNo() {
        return ssAccountNo;
    }

    /**
     *
     * @param ssAccountNo
     */
    public void setSsAccountNo(FmsGeneralLedger ssAccountNo) {
        this.ssAccountNo = ssAccountNo;
    }

    /**
     *
     * @return
     */
    public MmsFaAssetType getSsAssetType() {
        return ssAssetType;
    }

    /**
     *
     * @param ssAssetType
     */
    public void setSsAssetType(MmsFaAssetType ssAssetType) {
        this.ssAssetType = ssAssetType;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (substationId != null ? substationId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MmsFaSubstation)) {
            return false;
        }
        MmsFaSubstation other = (MmsFaSubstation) object;
        if ((this.substationId == null && other.substationId != null) || (this.substationId != null && !this.substationId.equals(other.substationId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return ssNo;
    }

    @XmlTransient
    public List<WfMmsProcessed> getSubstationList() {
        if (substationList == null) {
            substationList = new ArrayList<>();
        }
        return substationList;
    }

    public void setSubstationList(List<WfMmsProcessed> substationList) {
        this.substationList = substationList;
    }

    /**
     *
     * @return
     */
    @XmlTransient
    public List<FmsDprSubstation> getFmsDprSubstationList() {
        return fmsDprSubstationList;
    }

    /**
     *
     * @param fmsDprSubstationList
     */
    public void setFmsDprSubstationList(List<FmsDprSubstation> fmsDprSubstationList) {
        this.fmsDprSubstationList = fmsDprSubstationList;
    }

}
