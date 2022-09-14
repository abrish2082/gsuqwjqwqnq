/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.mms.entity;

import et.gov.eep.commonApplications.entity.WfMmsProcessed;
import et.gov.eep.fcms.entity.admin.FmsLuSystem;
import et.gov.eep.fcms.entity.fixedasset.FmsDprTransport;
import java.io.Serializable;

import java.math.BigDecimal;
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
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Binyam
 */
@Entity
@Table(name = "MMS_FA_TRANSPORT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MmsFaTransport.findAll", query = "SELECT m FROM MmsFaTransport m"),
    @NamedQuery(name = "MmsFaTransport.findByTransportId", query = "SELECT m FROM MmsFaTransport m WHERE m.transportId = :transportId"),
    @NamedQuery(name = "MmsFaTransport.findByTpPlateNo", query = "SELECT m FROM MmsFaTransport m WHERE m.tpPlateNo = :tpPlateNo"),
    @NamedQuery(name = "MmsFaTransport.findByTpType", query = "SELECT m FROM MmsFaTransport m WHERE m.tpType = :tpType"),

    @NamedQuery(name = "MmsFaTransport.findBytpNoLike", query = "SELECT m FROM MmsFaTransport m WHERE m.tpNo LIKE :tpNo"),
    @NamedQuery(name = "MmsFaTransport.findBytpNoAndTpPrep", query = "SELECT m FROM MmsFaTransport m WHERE m.tpNo = :tpNo AND m.tpPrepared = :tpPrepared"),
    @NamedQuery(name = "MmsFaTransport.findAllByPreparerId", query = "SELECT m FROM MmsFaTransport m WHERE m.tpPrepared = :tpPrepared"),
    @NamedQuery(name = "MmsFaTransport.findByAllParameters", query = "SELECT m FROM MmsFaTransport m WHERE m.tpPlateNo LIKE :tpPlateNo"),
    @NamedQuery(name = "MmsFaTransport.findBytransportIdMaximum", query = "SELECT m FROM MmsFaTransport m WHERE m.transportId = (SELECT Max(c.transportId) FROM MmsFaTransport c)"),

    @NamedQuery(name = "MmsFaTransport.findByTpModel", query = "SELECT m FROM MmsFaTransport m WHERE m.tpModel = :tpModel"),
    @NamedQuery(name = "MmsFaTransport.findByTpChasisNo", query = "SELECT m FROM MmsFaTransport m WHERE m.tpChasisNo = :tpChasisNo"),
    @NamedQuery(name = "MmsFaTransport.findByTpEngineNo", query = "SELECT m FROM MmsFaTransport m WHERE m.tpEngineNo = :tpEngineNo"),
    @NamedQuery(name = "MmsFaTransport.findByTpTrailerChasis", query = "SELECT m FROM MmsFaTransport m WHERE m.tpTrailerChasis = :tpTrailerChasis"),
    @NamedQuery(name = "MmsFaTransport.findByTpLibreNo", query = "SELECT m FROM MmsFaTransport m WHERE m.tpLibreNo = :tpLibreNo"),
    @NamedQuery(name = "MmsFaTransport.findByTpDgmGroup", query = "SELECT m FROM MmsFaTransport m WHERE m.tpDgmGroup = :tpDgmGroup"),
    @NamedQuery(name = "MmsFaTransport.findByTpLocationName", query = "SELECT m FROM MmsFaTransport m WHERE m.tpLocationName = :tpLocationName"),
    @NamedQuery(name = "MmsFaTransport.findByTpInservice", query = "SELECT m FROM MmsFaTransport m WHERE m.tpInservice = :tpInservice"),
    @NamedQuery(name = "MmsFaTransport.findByTpCost", query = "SELECT m FROM MmsFaTransport m WHERE m.tpCost = :tpCost"),
    @NamedQuery(name = "MmsFaTransport.findByTpWorkunit", query = "SELECT m FROM MmsFaTransport m WHERE m.tpWorkunit = :tpWorkunit")})
public class MmsFaTransport implements Serializable {

    @OneToMany(mappedBy = "tpAssetId")
    private List<FmsDprTransport> fmsDprTransportList;
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id

    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQUENCETRANSPORT")
    @SequenceGenerator(name = "SEQUENCETRANSPORT", sequenceName = "SEQUENCETRANSPORT", allocationSize = 1)

    @Basic(optional = false)
    @NotNull
    @Column(name = "TRANSPORT_ID")
    private Integer transportId;

    @Column(name = "TP_PLATE_NO")
    private String tpPlateNo;

    @Column(name = "TP_TYPE")
    private String tpType;

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

    @Column(name = "TP_STATUS")
    private Integer tpStatus;

    @Column(name = "TP_NO")
    private String tpNo;

    @Column(name = "TP_PREPARED")
    private Integer tpPrepared;

    @Column(name = "TP_MODEL")
    private String tpModel;

    @Column(name = "TP_CHASIS_NO")
    private String tpChasisNo;

    @Column(name = "TP_ENGINE_NO")
    private String tpEngineNo;

    @Column(name = "TP_TRAILER_CHASIS")
    private String tpTrailerChasis;
    @Column(name = "TP_SERVICE_LIFE")
    private Integer tpServiceLife;

    @Column(name = "TP_LIBRE_NO")
    private String tpLibreNo;

    @Column(name = "TP_DGM_GROUP")
    private String tpDgmGroup;

    @Column(name = "TP_LOCATION_NAME")
    private String tpLocationName;

    @Column(name = "TP_INSERVICE")
    private String tpInservice;
    @Column(name = "TP_COST")
    private BigDecimal tpCost;

    @Column(name = "TP_WORKUNIT")
    private String tpWorkunit;

    @OneToMany(mappedBy = "transportId", cascade = CascadeType.ALL)
    private List<WfMmsProcessed> transportList;

    @JoinColumn(name = "TP_SYS_NO", referencedColumnName = "SYSTEM_ID")
    @ManyToOne
    private FmsLuSystem tpSysNo;

    @Transient
    private int tpServiceYear;

    /**
     *
     */
    public MmsFaTransport() {
    }

    /**
     *
     * @param transportId
     */
    public MmsFaTransport(Integer transportId) {
        this.transportId = transportId;
    }

    /**
     *
     * @return
     */
    public Integer getTransportId() {
        return transportId;
    }

    /**
     *
     * @param transportId
     */
    public void setTransportId(Integer transportId) {
        this.transportId = transportId;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
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

    public Integer getTpStatus() {
        return tpStatus;
    }

    public void setTpStatus(Integer tpStatus) {
        this.tpStatus = tpStatus;
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

    public Integer getTpPrepared() {
        return tpPrepared;
    }

    public void setTpPrepared(Integer tpPrepared) {
        this.tpPrepared = tpPrepared;
    }

    public String getTpNo() {
        return tpNo;
    }

    public void setTpNo(String tpNo) {
        this.tpNo = tpNo;
    }

    /**
     *
     * @return
     */
    public String getTpPlateNo() {
        return tpPlateNo;
    }

    /**
     *
     * @param tpPlateNo
     */
    public void setTpPlateNo(String tpPlateNo) {
        this.tpPlateNo = tpPlateNo;
    }

    public Integer getTpServiceLife() {
        return tpServiceLife;
    }

    /**
     *
     * @param wdServiceLife
     */
    public void setTpServiceLife(Integer wdServiceLife) {
        this.tpServiceLife = tpServiceLife;
    }

    /**
     *
     * @return
     */
    public String getTpType() {
        return tpType;
    }

    /**
     *
     * @param tpType
     */
    public void setTpType(String tpType) {
        this.tpType = tpType;
    }

    /**
     *
     * @return
     */
    public String getTpModel() {
        return tpModel;
    }

    /**
     *
     * @param tpModel
     */
    public void setTpModel(String tpModel) {
        this.tpModel = tpModel;
    }

    /**
     *
     * @return
     */
    public String getTpChasisNo() {
        return tpChasisNo;
    }

    /**
     *
     * @param tpChasisNo
     */
    public void setTpChasisNo(String tpChasisNo) {
        this.tpChasisNo = tpChasisNo;
    }

    /**
     *
     * @return
     */
    public String getTpEngineNo() {
        return tpEngineNo;
    }

    /**
     *
     * @param tpEngineNo
     */
    public void setTpEngineNo(String tpEngineNo) {
        this.tpEngineNo = tpEngineNo;
    }

    /**
     *
     * @return
     */
    public String getTpTrailerChasis() {
        return tpTrailerChasis;
    }

    /**
     *
     * @param tpTrailerChasis
     */
    public void setTpTrailerChasis(String tpTrailerChasis) {
        this.tpTrailerChasis = tpTrailerChasis;
    }

    /**
     *
     * @return
     */
    public String getTpLibreNo() {
        return tpLibreNo;
    }

    /**
     *
     * @param tpLibreNo
     */
    public void setTpLibreNo(String tpLibreNo) {
        this.tpLibreNo = tpLibreNo;
    }

    /**
     *
     * @return
     */
    public String getTpDgmGroup() {
        return tpDgmGroup;
    }

    /**
     *
     * @param tpDgmGroup
     */
    public void setTpDgmGroup(String tpDgmGroup) {
        this.tpDgmGroup = tpDgmGroup;
    }

    /**
     *
     * @return
     */
    public String getTpLocationName() {
        return tpLocationName;
    }

    /**
     *
     * @param tpLocationName
     */
    public void setTpLocationName(String tpLocationName) {
        this.tpLocationName = tpLocationName;
    }

    /**
     *
     * @return
     */
    public String getTpInservice() {
        return tpInservice;
    }

    /**
     *
     * @param tpInservice
     */
    public void setTpInservice(String tpInservice) {
        this.tpInservice = tpInservice;
    }

    /**
     *
     * @return
     */
    public BigDecimal getTpCost() {
        return tpCost;
    }

    /**
     *
     * @param tpCost
     */
    public void setTpCost(BigDecimal tpCost) {
        this.tpCost = tpCost;
    }

    /**
     *
     * @return
     */
    public String getTpWorkunit() {
        return tpWorkunit;
    }

    /**
     *
     * @param tpWorkunit
     */
    public void setTpWorkunit(String tpWorkunit) {
        this.tpWorkunit = tpWorkunit;
    }

    /**
     *
     * @return
     */
    public FmsLuSystem getTpSysNo() {
        return tpSysNo;
    }

    /**
     *
     * @param tpSysNo
     */
    public void setTpSysNo(FmsLuSystem tpSysNo) {
        this.tpSysNo = tpSysNo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (transportId != null ? transportId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MmsFaTransport)) {
            return false;
        }
        MmsFaTransport other = (MmsFaTransport) object;
        if ((this.transportId == null && other.transportId != null) || (this.transportId != null && !this.transportId.equals(other.transportId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return tpNo;
    }

    @XmlTransient
    public List<WfMmsProcessed> getTransportList() {
        if (transportList == null) {
            transportList = new ArrayList<>();
        }
        return transportList;
    }

    public void setTransportList(List<WfMmsProcessed> transportList) {
        this.transportList = transportList;
    }

    /**
     *
     * @return
     */
    @XmlTransient
    public List<FmsDprTransport> getFmsDprTransportList() {
        return fmsDprTransportList;
    }

    /**
     *
     * @param fmsDprTransportList
     */
    public void setFmsDprTransportList(List<FmsDprTransport> fmsDprTransportList) {
        this.fmsDprTransportList = fmsDprTransportList;
    }

    public int getTpServiceYear() {
        return tpServiceYear;
    }

    public void setTpServiceYear(int tpServiceYear) {
        this.tpServiceYear = tpServiceYear;
    }

}
