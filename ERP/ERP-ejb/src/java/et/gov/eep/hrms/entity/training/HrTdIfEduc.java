/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.entity.training;

import et.gov.eep.hrms.entity.address.HrAddresses;
import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.entity.lookup.HrLuEducLevels;
import et.gov.eep.hrms.entity.lookup.HrLuEducTypes;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Behailu
 */
@Entity
@Table(name = "HR_TD_IF_EDUC")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HrTdIfEduc.findAll", query = "SELECT h FROM HrTdIfEduc h"),
    @NamedQuery(name = "HrTdIfEduc.findById", query = "SELECT h FROM HrTdIfEduc h WHERE h.id = :id"),
    @NamedQuery(name = "HrTdIfEduc.findByLocation", query = "SELECT h FROM HrTdIfEduc h WHERE h.location = :location"),
    @NamedQuery(name = "HrTdIfEduc.findByInstitution", query = "SELECT h FROM HrTdIfEduc h WHERE h.institution = :institution"),
    @NamedQuery(name = "HrTdIfEduc.findByProgramSponsor", query = "SELECT h FROM HrTdIfEduc h WHERE h.programSponsor = :programSponsor"),
    @NamedQuery(name = "HrTdIfEduc.findByProgramStartDate", query = "SELECT h FROM HrTdIfEduc h WHERE h.programStartDate = :programStartDate"),
    @NamedQuery(name = "HrTdIfEduc.findByProgramEndDate", query = "SELECT h FROM HrTdIfEduc h WHERE h.programEndDate = :programEndDate"),
    @NamedQuery(name = "HrTdIfEduc.findByProgramStartTime", query = "SELECT h FROM HrTdIfEduc h WHERE h.programStartTime = :programStartTime"),
    @NamedQuery(name = "HrTdIfEduc.findByLetterDate", query = "SELECT h FROM HrTdIfEduc h WHERE h.letterDate = :letterDate"),
    @NamedQuery(name = "HrTdIfEduc.findByLetterReferenceNo", query = "SELECT h FROM HrTdIfEduc h WHERE h.letterReferenceNo = :letterReferenceNo"),
    @NamedQuery(name = "HrTdIfEduc.findByRemark", query = "SELECT h FROM HrTdIfEduc h WHERE h.remark = :remark"),
    @NamedQuery(name = "HrTdIfEduc.findByPreparedOn", query = "SELECT h FROM HrTdIfEduc h WHERE h.preparedOn = :preparedOn")})
public class HrTdIfEduc implements Serializable {
    @OneToMany(mappedBy = "inlandForeignEducId",cascade = CascadeType.ALL)
    private List<HrTdIfEducSelectedStaffs> hrTdIfEducSelectedStaffsList;
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
     @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HR_TD_IF_EDUC_SEQ")
    @SequenceGenerator(name = "HR_TD_IF_EDUC_SEQ", sequenceName = "HR_TD_IF_EDUC_SEQ", allocationSize = 1)
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private BigDecimal id;
    @Column(name = "LOCATION")
    private Integer location;
    @Size(max = 100)
    @Column(name = "INSTITUTION")
    private String institution;
    @Column(name = "PROGRAM_SPONSOR")
    private BigInteger programSponsor;
    @Size(max = 20)
    @Column(name = "PROGRAM_START_DATE")
    private String programStartDate;
    @Size(max = 20)
    @Column(name = "PROGRAM_END_DATE")
    private String programEndDate;
    @Size(max = 20)
    @Column(name = "PROGRAM_START_TIME") 
     private String  programStartTime;
    @Size(max = 20)
    @Column(name = "LETTER_DATE")
    private String letterDate;
    @Size(max = 20)
    @Column(name = "LETTER_REFERENCE_NO")
    private String letterReferenceNo;
    @Size(max = 200)
    @Column(name = "REMARK")
    private String remark;
    @Size(max = 20)
    @Column(name = "PREPARED_ON")
    private String preparedOn;
    @JoinColumn(name = "COUNTRY_ID", referencedColumnName = "ADDRESS_ID")
    @ManyToOne
    private HrAddresses countryId;
    @JoinColumn(name = "PREPARED_BY", referencedColumnName = "ID")
    @ManyToOne
    private HrEmployees preparedBy;
    @JoinColumn(name = "EDUC_LEVEL_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrLuEducLevels educLevelId;
    @JoinColumn(name = "EDUC_QUAL_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrLuEducTypes educQualId;

    public HrTdIfEduc() {
    }

    public HrTdIfEduc(BigDecimal id) {
        this.id = id;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public Integer getLocation() {
        return location;
    }

    public void setLocation(Integer location) {
        this.location = location;
    }

    public String getInstitution() {
        return institution;
    }

    public void setInstitution(String institution) {
        this.institution = institution;
    }

    public BigInteger getProgramSponsor() {
        return programSponsor;
    }

    public void setProgramSponsor(BigInteger programSponsor) {
        this.programSponsor = programSponsor;
    }

    public String getProgramStartDate() {
        return programStartDate;
    }

    public void setProgramStartDate(String programStartDate) {
        this.programStartDate = programStartDate;
    }

    public String getProgramEndDate() {
        return programEndDate;
    }

    public void setProgramEndDate(String programEndDate) {
        this.programEndDate = programEndDate;
    }

    public String getProgramStartTime() {
        return programStartTime;
    }

    public void setProgramStartTime(String programStartTime) {
        this.programStartTime = programStartTime;
    }

    public String getLetterDate() {
        return letterDate;
    }

    public void setLetterDate(String letterDate) {
        this.letterDate = letterDate;
    }

    public String getLetterReferenceNo() {
        return letterReferenceNo;
    }

    public void setLetterReferenceNo(String letterReferenceNo) {
        this.letterReferenceNo = letterReferenceNo;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getPreparedOn() {
        return preparedOn;
    }

    public void setPreparedOn(String preparedOn) {
        this.preparedOn = preparedOn;
    }

    public HrAddresses getCountryId() {
        return countryId;
    }

    public void setCountryId(HrAddresses countryId) {
        this.countryId = countryId;
    }

    public HrEmployees getPreparedBy() {
        return preparedBy;
    }

    public void setPreparedBy(HrEmployees preparedBy) {
        this.preparedBy = preparedBy;
    }

    public HrLuEducLevels getEducLevelId() {
        return educLevelId;
    }

    public void setEducLevelId(HrLuEducLevels educLevelId) {
        this.educLevelId = educLevelId;
    }

    public HrLuEducTypes getEducQualId() {
        return educQualId;
    }

    public void setEducQualId(HrLuEducTypes educQualId) {
        this.educQualId = educQualId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HrTdIfEduc)) {
            return false;
        }
        HrTdIfEduc other = (HrTdIfEduc) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.eep.hrms.entity.training.HrTdIfEduc[ id=" + id + " ]";
    }

    @XmlTransient
    public List<HrTdIfEducSelectedStaffs> getHrTdIfEducSelectedStaffsList() {
         if(hrTdIfEducSelectedStaffsList==null){
            hrTdIfEducSelectedStaffsList= new ArrayList<>();
        }
        return hrTdIfEducSelectedStaffsList;
    }

    public void setHrTdIfEducSelectedStaffsList(List<HrTdIfEducSelectedStaffs> hrTdIfEducSelectedStaffsList) {
        this.hrTdIfEducSelectedStaffsList = hrTdIfEducSelectedStaffsList;
    }

    public void add(HrTdIfEducSelectedStaffs hrTdIfEducSelectedStaffs) {
        if (hrTdIfEducSelectedStaffs.getInlandForeignEducId()!= this) {
            this.getHrTdIfEducSelectedStaffsList().add(hrTdIfEducSelectedStaffs);
            hrTdIfEducSelectedStaffs.setInlandForeignEducId(this);
        } 
    }
    
}
