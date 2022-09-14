/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.mms.entity;

import et.gov.eep.commonApplications.entity.WfMmsProcessed;
import et.gov.eep.fcms.entity.admin.FmsLuSystem;
import et.gov.eep.fcms.entity.fixedasset.FmsDprTransmisson;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
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
@Table(name = "MMS_FA_TRANSMISSION")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MmsFaTransmission.findAll", query = "SELECT m FROM MmsFaTransmission m"),
    @NamedQuery(name = "MmsFaTransmission.findByTransmissionId", query = "SELECT m FROM MmsFaTransmission m WHERE m.transmissionId = :transmissionId"),
    @NamedQuery(name = "MmsFaTransmission.findByRegionNo", query = "SELECT m FROM MmsFaTransmission m WHERE m.regionNo = :regionNo"),
    @NamedQuery(name = "MmsFaTransmission.findByFromNorthP", query = "SELECT m FROM MmsFaTransmission m WHERE m.fromNorthP = :fromNorthP"),
    @NamedQuery(name = "MmsFaTransmission.findByFromEastP", query = "SELECT m FROM MmsFaTransmission m WHERE m.fromEastP = :fromEastP"),

    @NamedQuery(name = "MmsFaTransmission.findBytransmissionIdMaximum", query = "SELECT m FROM MmsFaTransmission m WHERE m.transmissionId = (SELECT Max(c.transmissionId) FROM MmsFaTransmission c)"),
    @NamedQuery(name = "MmsFaTransmission.findBytrNoLike", query = "SELECT m FROM MmsFaTransmission m WHERE m.trNo LIKE :trNo"),
    @NamedQuery(name = "MmsFaTransmission.findBytrNoAndTrPrep", query = "SELECT m FROM MmsFaTransmission m WHERE m.trNo = :trNo AND m.trPrepared = :trPrepared"),
    @NamedQuery(name = "MmsFaTransmission.findAllByPreparerId", query = "SELECT p FROM MmsFaTransmission p WHERE p.trPrepared = :trPrepared"),
    @NamedQuery(name = "MmsFaTransmission.findByFromName", query = "SELECT m FROM MmsFaTransmission m WHERE m.fromName = :fromName"),
    @NamedQuery(name = "MmsFaTransmission.findByFromNameAndTrPrep", query = "SELECT m FROM MmsFaTransmission m WHERE m.fromName = :fromName AND m.trPrepared = :trPrepared"),
    @NamedQuery(name = "MmsFaTransmission.findByFromNameLike", query = "SELECT m FROM MmsFaTransmission m WHERE m.fromName Like :fromName"),
    @NamedQuery(name = "MmsFaTransmission.findByToNorthP", query = "SELECT m FROM MmsFaTransmission m WHERE m.toNorthP = :toNorthP"),
    @NamedQuery(name = "MmsFaTransmission.findByToEastP", query = "SELECT m FROM MmsFaTransmission m WHERE m.toEastP = :toEastP"),
    @NamedQuery(name = "MmsFaTransmission.findByToName", query = "SELECT m FROM MmsFaTransmission m WHERE m.toName = :toName"),
    @NamedQuery(name = "MmsFaTransmission.findByTowerNo", query = "SELECT m FROM MmsFaTransmission m WHERE m.towerNo = :towerNo"),
    @NamedQuery(name = "MmsFaTransmission.findByTowerType", query = "SELECT m FROM MmsFaTransmission m WHERE m.towerType = :towerType"),
    @NamedQuery(name = "MmsFaTransmission.findByInservice", query = "SELECT m FROM MmsFaTransmission m WHERE m.inservice = :inservice"),
    @NamedQuery(name = "MmsFaTransmission.findByLineLength", query = "SELECT m FROM MmsFaTransmission m WHERE m.lineLength = :lineLength"),
    @NamedQuery(name = "MmsFaTransmission.findByGradingFactor", query = "SELECT m FROM MmsFaTransmission m WHERE m.gradingFactor = :gradingFactor"),
    @NamedQuery(name = "MmsFaTransmission.findByUnitCost", query = "SELECT m FROM MmsFaTransmission m WHERE m.unitCost = :unitCost"),
    @NamedQuery(name = "MmsFaTransmission.findByServiceLife", query = "SELECT m FROM MmsFaTransmission m WHERE m.serviceLife = :serviceLife")})
public class MmsFaTransmission implements Serializable {

    @Column(name = "UNIT_COST")
    private BigDecimal unitCost;
    @Column(name = "ACCOUNT_NO")
    private Long accountNo;
    @Column(name = "DPR_YEAR")
    private BigDecimal dprYear;
    @Column(name = "NET_BOOK_VALUE")
    private BigDecimal netBookValue;
    @Column(name = "ACCUMULATED_DPR")
    private BigDecimal accumulatedDpr;
    @Column(name = "REVALUATION_SERVICE_YEAR")
    private int revaluationServiceYear;
    @Column(name = "REVALUATION_COST")
    private BigDecimal revaluationCost;
    @Column(name = "LINE_LENGTH")
    private BigInteger lineLength;
    @Column(name = "REGION_NO")
    private Long regionNo;
    @Column(name = "SERVICE_LIFE")
    private Integer serviceLife;
    @Column(name = "TOWER_NO")
    private BigInteger towerNo;
    @Column(name = "TR_STATUS")
    private Integer trStatus;
    @OneToMany(mappedBy = "trAssetId")
    private List<FmsDprTransmisson> fmsDprTransmissonList;
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MMS_FA_TRANSMISSION_SEQ")
    @SequenceGenerator(name = "MMS_FA_TRANSMISSION_SEQ", sequenceName = "MMS_FA_TRANSMISSION_SEQ", allocationSize = 1)
    @NotNull
    @Column(name = "TRANSMISSION_ID")
    private Integer transmissionId;

    @Size(max = 20)
    @Column(name = "FROM_NORTH_P")
    private String fromNorthP;
    @Size(max = 20)
    @Column(name = "FROM_EAST_P")
    private String fromEastP;
    @Size(max = 20)
    @Column(name = "FROM_NAME")
    private String fromName;

    @Size(max = 30)
    @Column(name = "TR_NO", length = 30)
    private String trNo;
    @Size(max = 30)
    @Column(name = "TR_REGION", length = 30)
    private String trRegion;

    @Column(name = "TR_PREPARED", length = 30)
    private Integer trPrepared;
    @Size(max = 20)
    @Column(name = "TO_NORTH_P")
    private String toNorthP;
    @Size(max = 20)
    @Column(name = "TO_EAST_P")
    private String toEastP;
    @Size(max = 20)
    @Column(name = "TO_NAME")
    private String toName;
    @Size(max = 20)
    @Column(name = "TOWER_TYPE")
    private String towerType;
    @Size(max = 20)
    @Column(name = "INSERVICE")
    private String inservice;
    @Column(name = "GRADING_FACTOR")
    private BigInteger gradingFactor;

    @OneToMany(mappedBy = "transmissionId", cascade = CascadeType.ALL)
    private List<WfMmsProcessed> transmissionList;
    @JoinColumn(name = "SYSTEM_NO", referencedColumnName = "SYSTEM_ID")
    @ManyToOne
    private FmsLuSystem systemNo;
    @JoinColumn(name = "ASSET_TYPE_NO", referencedColumnName = "ASSET_ID")
    @ManyToOne
    private MmsFaAssetType assetTypeNo;

    /**
     *
     */
    public MmsFaTransmission() {
    }

    /**
     *
     * @param transmissionId
     */
    public MmsFaTransmission(Integer transmissionId) {
        this.transmissionId = transmissionId;
    }

    /**
     *
     * @return
     */
    public Integer getTransmissionId() {
        return transmissionId;
    }

    /**
     *
     * @param transmissionId
     */
    public void setTransmissionId(Integer transmissionId) {
        this.transmissionId = transmissionId;
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

    public BigDecimal getAccumulatedDpr() {
        return accumulatedDpr;
    }

    public void setAccumulatedDpr(BigDecimal accumulatedDpr) {
        this.accumulatedDpr = accumulatedDpr;
    }

    public Integer getTrStatus() {
        return trStatus;
    }

    public void setTrStatus(Integer trStatus) {
        this.trStatus = trStatus;
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

    public String getTrNo() {
        return trNo;
    }

    public void setTrNo(String trNo) {
        this.trNo = trNo;
    }

    public String getTrRegion() {
        return trRegion;
    }

    public void setTrRegion(String trRegion) {
        this.trRegion = trRegion;
    }

    public Integer getTrPrepared() {
        return trPrepared;
    }

    public void setTrPrepared(Integer trPrepared) {
        this.trPrepared = trPrepared;
    }

    /**
     *
     * @return
     */
    public Long getRegionNo() {
        return regionNo;
    }

    /**
     *
     * @param regionNo
     */
    public void setRegionNo(Long regionNo) {
        this.regionNo = regionNo;
    }

    /**
     *
     * @return
     */
    public String getFromNorthP() {
        return fromNorthP;
    }

    /**
     *
     * @param fromNorthP
     */
    public void setFromNorthP(String fromNorthP) {
        this.fromNorthP = fromNorthP;
    }

    /**
     *
     * @return
     */
    public String getFromEastP() {
        return fromEastP;
    }

    /**
     *
     * @param fromEastP
     */
    public void setFromEastP(String fromEastP) {
        this.fromEastP = fromEastP;
    }

    /**
     *
     * @return
     */
    public String getFromName() {
        return fromName;
    }

    /**
     *
     * @param fromName
     */
    public void setFromName(String fromName) {
        this.fromName = fromName;
    }

    /**
     *
     * @return
     */
    public String getToNorthP() {
        return toNorthP;
    }

    /**
     *
     * @param toNorthP
     */
    public void setToNorthP(String toNorthP) {
        this.toNorthP = toNorthP;
    }

    /**
     *
     * @return
     */
    public String getToEastP() {
        return toEastP;
    }

    /**
     *
     * @param toEastP
     */
    public void setToEastP(String toEastP) {
        this.toEastP = toEastP;
    }

    /**
     *
     * @return
     */
    public String getToName() {
        return toName;
    }

    /**
     *
     * @param toName
     */
    public void setToName(String toName) {
        this.toName = toName;
    }

    /**
     *
     * @return
     */
    public String getInservice() {
        return inservice;
    }

    /**
     *
     * @param inservice
     */
    public void setInservice(String inservice) {
        this.inservice = inservice;
    }

    /**
     *
     * @return
     */
    public BigInteger getLineLength() {
        return lineLength;
    }

    /**
     *
     * @param lineLength
     */
    public void setLineLength(BigInteger lineLength) {
        this.lineLength = lineLength;
    }

    /**
     *
     * @return
     */
    public BigInteger getGradingFactor() {
        return gradingFactor;
    }

    /**
     *
     * @param gradingFactor
     */
    public void setGradingFactor(BigInteger gradingFactor) {
        this.gradingFactor = gradingFactor;
    }

    public String getTowerType() {
        return towerType;
    }

    /**
     *
     * @param towerType
     */
    public void setTowerType(String towerType) {
        this.towerType = towerType;
    }

//    public FmsGeneralLedger getAccountNo() {
//        return accountNo;
//    }
    public Integer getServiceLife() {
        return serviceLife;
    }

    public void setServiceLife(Integer serviceLife) {
        this.serviceLife = serviceLife;
    }

    public BigInteger getTowerNo() {
        return towerNo;
    }

    public void setTowerNo(BigInteger towerNo) {
        this.towerNo = towerNo;
    }

    public BigDecimal getUnitCost() {
        return unitCost;
    }

    public void setUnitCost(BigDecimal unitCost) {
        this.unitCost = unitCost;
    }

//    public void setAccountNo(FmsGeneralLedger accountNo) {
//        this.accountNo = accountNo;
//    }
    /**
     *
     * @return
     */
    public FmsLuSystem getSystemNo() {
        return systemNo;
    }

    /**
     *
     * @param systemNo
     */
    public void setSystemNo(FmsLuSystem systemNo) {
        this.systemNo = systemNo;
    }

    /**
     *
     * @return
     */
    public MmsFaAssetType getAssetTypeNo() {
        return assetTypeNo;
    }

    /**
     *
     * @param assetTypeNo
     */
    public void setAssetTypeNo(MmsFaAssetType assetTypeNo) {
        this.assetTypeNo = assetTypeNo;
    }

    @XmlTransient
    public List<WfMmsProcessed> getTransmissionList() {
        if (transmissionList == null) {
            transmissionList = new ArrayList<>();
        }
        return transmissionList;
    }

    public void setTransmissionList(List<WfMmsProcessed> transmissionList) {
        this.transmissionList = transmissionList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (transmissionId != null ? transmissionId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MmsFaTransmission)) {
            return false;
        }
        MmsFaTransmission other = (MmsFaTransmission) object;
        if ((this.transmissionId == null && other.transmissionId != null) || (this.transmissionId != null && !this.transmissionId.equals(other.transmissionId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return trNo;
    }

    /**
     *
     * @return
     */
    @XmlTransient
    public List<FmsDprTransmisson> getFmsDprTransmissonList() {
        return fmsDprTransmissonList;
    }

    /**
     *
     * @param fmsDprTransmissonList
     */
    public void setFmsDprTransmissonList(List<FmsDprTransmisson> fmsDprTransmissonList) {
        this.fmsDprTransmissonList = fmsDprTransmissonList;
    }

    public Long getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(Long accountNo) {
        this.accountNo = accountNo;
    }

}
