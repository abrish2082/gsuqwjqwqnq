/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.mms.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author w_station
 */
@Entity
@Table(name = "MMS_FA_ROAD")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MmsFaRoad.findAll", query = "SELECT m FROM MmsFaRoad m"),
    @NamedQuery(name = "MmsFaRoad.findByRoadId", query = "SELECT m FROM MmsFaRoad m WHERE m.roadId = :roadId"),
    @NamedQuery(name = "MmsFaRoad.findByRoadNo", query = "SELECT m FROM MmsFaRoad m WHERE m.roadNo = :roadNo"),
    @NamedQuery(name = "MmsFaRoad.findByName", query = "SELECT m FROM MmsFaRoad m WHERE m.name = :name"),

    @NamedQuery(name = "MmsFaRoad.findByroadIdMaximum", query = "SELECT m FROM MmsFaRoad m WHERE m.roadId = (SELECT Max(c.roadId) FROM MmsFaRoad c)"),
    @NamedQuery(name = "MmsFaRoad.findByAllParameters", query = "SELECT m FROM MmsFaRoad m WHERE m.roadNo LIKE :roadNo"),

    @NamedQuery(name = "MmsFaRoad.findByRegion", query = "SELECT m FROM MmsFaRoad m WHERE m.region = :region"),
    @NamedQuery(name = "MmsFaRoad.findBySubcity", query = "SELECT m FROM MmsFaRoad m WHERE m.subcity = :subcity"),
    @NamedQuery(name = "MmsFaRoad.findByWereda", query = "SELECT m FROM MmsFaRoad m WHERE m.wereda = :wereda"),
    @NamedQuery(name = "MmsFaRoad.findByKebele", query = "SELECT m FROM MmsFaRoad m WHERE m.kebele = :kebele"),
    @NamedQuery(name = "MmsFaRoad.findByRegDate", query = "SELECT m FROM MmsFaRoad m WHERE m.regDate = :regDate"),
    @NamedQuery(name = "MmsFaRoad.findByArea", query = "SELECT m FROM MmsFaRoad m WHERE m.area = :area"),
    @NamedQuery(name = "MmsFaRoad.findByLength", query = "SELECT m FROM MmsFaRoad m WHERE m.length = :length"),
    @NamedQuery(name = "MmsFaRoad.findByRoadType", query = "SELECT m FROM MmsFaRoad m WHERE m.roadType = :roadType"),
    @NamedQuery(name = "MmsFaRoad.findByCapitalInNeed", query = "SELECT m FROM MmsFaRoad m WHERE m.capitalInNeed = :capitalInNeed"),
    @NamedQuery(name = "MmsFaRoad.findByYearOfService", query = "SELECT m FROM MmsFaRoad m WHERE m.yearOfService = :yearOfService"),
    @NamedQuery(name = "MmsFaRoad.findByRemark", query = "SELECT m FROM MmsFaRoad m WHERE m.remark = :remark"),
    @NamedQuery(name = "MmsFaRoad.findByPreparedBy", query = "SELECT m FROM MmsFaRoad m WHERE m.preparedBy = :preparedBy")})
public class MmsFaRoad implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQUENCEROAD")
    @SequenceGenerator(name = "SEQUENCEROAD", sequenceName = "SEQUENCEROAD", allocationSize = 1)

    @Basic(optional = false)
    @NotNull
    @Column(name = "ROAD_ID", nullable = false, precision = 0, scale = -127)
    private BigDecimal roadId;
    @Size(max = 30)
    @Column(name = "ROAD_NO", length = 30)
    private String roadNo;
    @Size(max = 30)
    @Column(name = "NAME", length = 30)
    private String name;

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
    @Size(max = 30)
    @Column(name = "REGION", length = 30)
    private String region;
    @Size(max = 30)
    @Column(name = "SUBCITY", length = 30)
    private String subcity;
    @Size(max = 30)
    @Column(name = "WEREDA", length = 30)
    private String wereda;
    @Size(max = 30)
    @Column(name = "KEBELE", length = 30)
    private String kebele;
    @Column(name = "REG_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date regDate;
    @Column(name = "AREA", precision = 126)
    private Double area;
    @Column(name = "LENGTH", precision = 126)
    private Double length;
    @Size(max = 30)
    @Column(name = "ROAD_TYPE", length = 30)
    private String roadType;
    @Column(name = "CAPITAL_IN_NEED", precision = 126)
    private Double capitalInNeed;
    @Column(name = "YEAR_OF_SERVICE")
    private BigInteger yearOfService;
    @Size(max = 50)
    @Column(name = "REMARK", length = 50)
    private String remark;
    @Size(max = 30)
    @Column(name = "PREPARED_BY", length = 30)
    private String preparedBy;

    @Size(max = 30)
    @Column(name = "REG2_DATE")
    private String reg2Date;

    public MmsFaRoad() {
    }

    public MmsFaRoad(BigDecimal roadId) {
        this.roadId = roadId;
    }

    public BigDecimal getRoadId() {
        return roadId;
    }

    public void setRoadId(BigDecimal roadId) {
        this.roadId = roadId;
    }

    public String getRoadNo() {
        return roadNo;
    }

    public void setRoadNo(String roadNo) {
        this.roadNo = roadNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getSubcity() {
        return subcity;
    }

    public void setSubcity(String subcity) {
        this.subcity = subcity;
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

    public String getReg2Date() {
        return reg2Date;
    }

    public void setReg2Date(String reg2Date) {
        this.reg2Date = reg2Date;
    }

    
    public BigDecimal getRevaluationServiceYear() {
        return revaluationServiceYear;
    }

    public void setRevaluationServiceYear(BigDecimal revaluationServiceYear) {
        this.revaluationServiceYear = revaluationServiceYear;
    }

    public String getWereda() {
        return wereda;
    }

    public void setWereda(String wereda) {
        this.wereda = wereda;
    }

    public String getKebele() {
        return kebele;
    }

    public void setKebele(String kebele) {
        this.kebele = kebele;
    }

    public Date getRegDate() {
        return regDate;
    }

    public void setRegDate(Date regDate) {
        this.regDate = regDate;
    }

    public Double getArea() {
        return area;
    }

    public void setArea(Double area) {
        this.area = area;
    }

    public Double getLength() {
        return length;
    }

    public void setLength(Double length) {
        this.length = length;
    }

    public String getRoadType() {
        return roadType;
    }

    public void setRoadType(String roadType) {
        this.roadType = roadType;
    }

    public Double getCapitalInNeed() {
        return capitalInNeed;
    }

    public void setCapitalInNeed(Double capitalInNeed) {
        this.capitalInNeed = capitalInNeed;
    }

    public BigInteger getYearOfService() {
        return yearOfService;
    }

    public void setYearOfService(BigInteger yearOfService) {
        this.yearOfService = yearOfService;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getPreparedBy() {
        return preparedBy;
    }

    public void setPreparedBy(String preparedBy) {
        this.preparedBy = preparedBy;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (roadId != null ? roadId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MmsFaRoad)) {
            return false;
        }
        MmsFaRoad other = (MmsFaRoad) object;
        if ((this.roadId == null && other.roadId != null) || (this.roadId != null && !this.roadId.equals(other.roadId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.eep.mms.entity.MmsFaRoad[ roadId=" + roadId + " ]";
    }

}
