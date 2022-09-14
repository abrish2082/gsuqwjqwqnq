/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.mms.entity;

import et.gov.eep.commonApplications.entity.WfMmsProcessed;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author w_station
 */
@Entity
@Table(name = "MMS_FA_LAND")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MmsFaLand.findAll", query = "SELECT m FROM MmsFaLand m"),
    @NamedQuery(name = "MmsFaLand.findByLandId", query = "SELECT m FROM MmsFaLand m WHERE m.landId = :landId"),
    @NamedQuery(name = "MmsFaLand.findByLandNo", query = "SELECT m FROM MmsFaLand m WHERE m.landNo = :landNo"),
    @NamedQuery(name = "MmsFaLand.findByName", query = "SELECT m FROM MmsFaLand m WHERE m.name = :name"),
    @NamedQuery(name = "MmsFaLand.findByNameAndLandPrep", query = "SELECT m FROM MmsFaLand m WHERE m.name = :name AND m.preparedBy = :preparedBy"),
    @NamedQuery(name = "MmsFaLand.findAllByPreparerId", query = "SELECT m FROM MmsFaLand m WHERE m.preparedBy = :preparedBy"),
    @NamedQuery(name = "MmsFaLand.findByRegion", query = "SELECT m FROM MmsFaLand m WHERE m.region = :region"),

    @NamedQuery(name = "MmsFaLand.findBylandIdMaximum", query = "SELECT m FROM MmsFaLand m WHERE m.landId = (SELECT Max(c.landId) FROM MmsFaLand c)"),
    @NamedQuery(name = "MmsFaLand.findByAllParameters", query = "SELECT m FROM MmsFaLand m WHERE m.landNo LIKE :landNo"),
    @NamedQuery(name = "MmsFaLand.findByAllParametersAndLandPrep", query = "SELECT m FROM MmsFaLand m WHERE m.landNo = :landNo AND m.preparedBy = :preparedBy"),

    @NamedQuery(name = "MmsFaLand.findBySubcity", query = "SELECT m FROM MmsFaLand m WHERE m.subcity = :subcity"),
    @NamedQuery(name = "MmsFaLand.findByRegDate", query = "SELECT m FROM MmsFaLand m WHERE m.regDate = :regDate"),
    @NamedQuery(name = "MmsFaLand.findByArea", query = "SELECT m FROM MmsFaLand m WHERE m.area = :area"),
    @NamedQuery(name = "MmsFaLand.findByRentalCertificate", query = "SELECT m FROM MmsFaLand m WHERE m.rentalCertificate = :rentalCertificate"),
    @NamedQuery(name = "MmsFaLand.findByDateOfAgreement", query = "SELECT m FROM MmsFaLand m WHERE m.dateOfAgreement = :dateOfAgreement"),
    @NamedQuery(name = "MmsFaLand.findByRefNo", query = "SELECT m FROM MmsFaLand m WHERE m.refNo = :refNo"),
    @NamedQuery(name = "MmsFaLand.findByServiceYear", query = "SELECT m FROM MmsFaLand m WHERE m.serviceYear = :serviceYear"),
    @NamedQuery(name = "MmsFaLand.findByRentAmount", query = "SELECT m FROM MmsFaLand m WHERE m.rentAmount = :rentAmount"),
    @NamedQuery(name = "MmsFaLand.findByPlateNo", query = "SELECT m FROM MmsFaLand m WHERE m.plateNo = :plateNo"),
    @NamedQuery(name = "MmsFaLand.findByLandGrade", query = "SELECT m FROM MmsFaLand m WHERE m.landGrade = :landGrade"),
    @NamedQuery(name = "MmsFaLand.findByRegNo", query = "SELECT m FROM MmsFaLand m WHERE m.regNo = :regNo"),
    @NamedQuery(name = "MmsFaLand.findByBuiltUpArea", query = "SELECT m FROM MmsFaLand m WHERE m.builtUpArea = :builtUpArea"),
    @NamedQuery(name = "MmsFaLand.findByTypeOfHolding", query = "SELECT m FROM MmsFaLand m WHERE m.typeOfHolding = :typeOfHolding"),
    @NamedQuery(name = "MmsFaLand.findByRemark", query = "SELECT m FROM MmsFaLand m WHERE m.remark = :remark"),
    @NamedQuery(name = "MmsFaLand.findByPreparedBy", query = "SELECT m FROM MmsFaLand m WHERE m.preparedBy = :preparedBy"),
    @NamedQuery(name = "MmsFaLand.findByWereda", query = "SELECT m FROM MmsFaLand m WHERE m.wereda = :wereda"),
    @NamedQuery(name = "MmsFaLand.findByBasemap", query = "SELECT m FROM MmsFaLand m WHERE m.basemap = :basemap"),
    @NamedQuery(name = "MmsFaLand.findByBuiltup", query = "SELECT m FROM MmsFaLand m WHERE m.builtup = :builtup")})
public class MmsFaLand implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQUENCELAND")
    @SequenceGenerator(name = "SEQUENCELAND", sequenceName = "SEQUENCELAND", allocationSize = 1)

    @Basic(optional = false)
    @NotNull
    @Column(name = "LAND_ID", nullable = false, precision = 0, scale = -127)
    private BigDecimal landId;
    @Size(max = 30)
    @Column(name = "LAND_NO", length = 30)
    private String landNo;
    @Size(max = 30)
    @Column(name = "NAME", length = 30)
    private String name;
    @Size(max = 30)
    @Column(name = "REGION", length = 30)
    private String region;
    @Size(max = 30)
    @Column(name = "SUBCITY", length = 30)
    private String subcity;
    @Column(name = "REG_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date regDate;
    @Size(max = 20)
    @Column(name = "AREA", length = 20)
    private String area;
    @Size(max = 30)
    @Column(name = "RENTAL_CERTIFICATE", length = 30)
    private String rentalCertificate;
    @Column(name = "DATE_OF_AGREEMENT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateOfAgreement;
    @Size(max = 30)
    @Column(name = "REF_NO", length = 30)
    private String refNo;
    @Size(max = 30)
    @Column(name = "SERVICE_YEAR", length = 30)
    private String serviceYear;
    @Size(max = 30)
    @Column(name = "RENT_AMOUNT", length = 30)
    private String rentAmount;
    @Size(max = 30)
    @Column(name = "PLATE_NO", length = 30)
    private String plateNo;
    @Size(max = 30)
    @Column(name = "LAND_GRADE", length = 30)
    private String landGrade;
    @Size(max = 30)
    @Column(name = "REG_NO", length = 30)
    private String regNo;
    @Size(max = 30)
    @Column(name = "BUILT_UP_AREA", length = 30)
    private String builtUpArea;
    @Size(max = 30)
    @Column(name = "TYPE_OF_HOLDING", length = 30)
    private String typeOfHolding;
    @Size(max = 200)
    @Column(name = "REMARK", length = 200)
    private String remark;

    @Column(name = "PREPARED_BY")
    private Integer preparedBy;
    @Size(max = 30)
    @Column(name = "WEREDA", length = 30)
    private String wereda;
    @Size(max = 20)
    @Column(name = "BASEMAP", length = 20)
    private String basemap;
    @Size(max = 30)
    @Column(name = "BUILTUP", length = 30)
    private String builtup;
    @Column(name = "LD_STATUS")
    private Integer ldStatus;

    @Size(max = 30)
    @Column(name = "REG2_DATE")
    private String reg2Date;
    @Size(max = 30)
    @Column(name = "AGREEMENT2_DATE ")
    private String agreement2Date;

    @OneToMany(mappedBy = "landId", cascade = CascadeType.ALL)
    private List<WfMmsProcessed> landList;

    public MmsFaLand() {
    }

    public MmsFaLand(BigDecimal landId) {
        this.landId = landId;
    }

    public BigDecimal getLandId() {
        return landId;
    }

    public void setLandId(BigDecimal landId) {
        this.landId = landId;
    }

    public String getLandNo() {
        return landNo;
    }

    public void setLandNo(String landNo) {
        this.landNo = landNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getLdStatus() {
        return ldStatus;
    }

    public void setLdStatus(Integer ldStatus) {
        this.ldStatus = ldStatus;
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

    public String getReg2Date() {
        return reg2Date;
    }

    public void setReg2Date(String reg2Date) {
        this.reg2Date = reg2Date;
    }

    public String getAgreement2Date() {
        return agreement2Date;
    }

    public void setAgreement2Date(String agreement2Date) {
        this.agreement2Date = agreement2Date;
    }

    public Date getRegDate() {
        return regDate;
    }

    public void setRegDate(Date regDate) {
        this.regDate = regDate;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getRentalCertificate() {
        return rentalCertificate;
    }

    public void setRentalCertificate(String rentalCertificate) {
        this.rentalCertificate = rentalCertificate;
    }

    public Date getDateOfAgreement() {
        return dateOfAgreement;
    }

    public void setDateOfAgreement(Date dateOfAgreement) {
        this.dateOfAgreement = dateOfAgreement;
    }

    public String getRefNo() {
        return refNo;
    }

    public void setRefNo(String refNo) {
        this.refNo = refNo;
    }

    public String getServiceYear() {
        return serviceYear;
    }

    public void setServiceYear(String serviceYear) {
        this.serviceYear = serviceYear;
    }

    public String getRentAmount() {
        return rentAmount;
    }

    public void setRentAmount(String rentAmount) {
        this.rentAmount = rentAmount;
    }

    public String getPlateNo() {
        return plateNo;
    }

    public void setPlateNo(String plateNo) {
        this.plateNo = plateNo;
    }

    public String getLandGrade() {
        return landGrade;
    }

    public void setLandGrade(String landGrade) {
        this.landGrade = landGrade;
    }

    public String getRegNo() {
        return regNo;
    }

    public void setRegNo(String regNo) {
        this.regNo = regNo;
    }

    public String getBuiltUpArea() {
        return builtUpArea;
    }

    public void setBuiltUpArea(String builtUpArea) {
        this.builtUpArea = builtUpArea;
    }

    public String getTypeOfHolding() {
        return typeOfHolding;
    }

    public void setTypeOfHolding(String typeOfHolding) {
        this.typeOfHolding = typeOfHolding;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getPreparedBy() {
        return preparedBy;
    }

    public void setPreparedBy(Integer preparedBy) {
        this.preparedBy = preparedBy;
    }

    public String getWereda() {
        return wereda;
    }

    public void setWereda(String wereda) {
        this.wereda = wereda;
    }

    public String getBasemap() {
        return basemap;
    }

    public void setBasemap(String basemap) {
        this.basemap = basemap;
    }

    public String getBuiltup() {
        return builtup;
    }

    public void setBuiltup(String builtup) {
        this.builtup = builtup;
    }

    @XmlTransient
    public List<WfMmsProcessed> getLandList() {
        if (landList == null) {
            landList = new ArrayList<>();
        }
        return landList;
    }

    public void setLandList(List<WfMmsProcessed> landList) {
        this.landList = landList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (landId != null ? landId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MmsFaLand)) {
            return false;
        }
        MmsFaLand other = (MmsFaLand) object;
        if ((this.landId == null && other.landId != null) || (this.landId != null && !this.landId.equals(other.landId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.eep.mms.entity.MmsFaLand[ landId=" + landId + " ]";
    }

}
