/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.entity.employee;

import et.gov.eep.hrms.entity.lookup.HrLuEducLevels;
import et.gov.eep.hrms.entity.lookup.HrLuEducTypes;
import et.gov.eep.hrms.entity.address.HrAddresses;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author user
 */
@Entity
@Table(name = "HR_EMP_EDUCATIONS")
@XmlRootElement
@NamedQueries//<editor-fold defaultstate="collapsed" desc="">
        ({
            @NamedQuery(name = "HrEmpEducations.findAll", query = "SELECT h FROM HrEmpEducations h"),
            @NamedQuery(name = "HrEmpEducations.findById", query = "SELECT h FROM HrEmpEducations h WHERE h.id = :id"),
            @NamedQuery(name = "HrEmpEducations.findByInstitution", query = "SELECT h FROM HrEmpEducations h WHERE h.institution = :institution"),
            @NamedQuery(name = "HrEmpEducations.findByStartDate", query = "SELECT h FROM HrEmpEducations h WHERE h.startDate = :startDate"),
            @NamedQuery(name = "HrEmpEducations.findByEndDate", query = "SELECT h FROM HrEmpEducations h WHERE h.endDate = :endDate"),
            @NamedQuery(name = "HrEmpEducations.findByAward", query = "SELECT h FROM HrEmpEducations h WHERE h.award = :award"),
            @NamedQuery(name = "HrEmpEducations.findByResult", query = "SELECT h FROM HrEmpEducations h WHERE h.educResult = :result"),
            @NamedQuery(name = "HrEmpEducations.findByEmpID", query = "SELECT h FROM HrEmpEducations h WHERE h.empId.id = :empId"),
            @NamedQuery(name = "HrEmpEducations.findBySponseredBy", query = "SELECT h FROM HrEmpEducations h WHERE h.sponsoredBy = :sponseredBy")})
//</editor-fold>
public class HrEmpEducations implements Serializable {

    //<editor-fold defaultstate="collapsed" desc="Entity Attributes">
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HR_EMP_EDUCATIONS_SEQ")
    @SequenceGenerator(name = "HR_EMP_EDUCATIONS_SEQ", sequenceName = "HR_EMP_EDUCATIONS_SEQ", allocationSize = 1)
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private BigDecimal id;
    @Size(max = 100)
    @Column(name = "INSTITUTION")
    private String institution;
    @Size(max = 20)
    @Column(name = "START_DATE")
    private String startDate;
    @Size(max = 20)
    @Column(name = "END_DATE")
    private String endDate;
    @Size(max = 20)
    @Column(name = "AWARD")
    private String award;
    @Size(max = 20)
    @Column(name = "RESULT")
    private String educResult;
    @Size(max = 20)
    @Column(name = "SPONSERED_BY")
    private String sponsoredBy;
    @JoinColumn(name = "ADDRESS_ID", referencedColumnName = "ADDRESS_ID")
    @ManyToOne
    private HrAddresses addressId;
    @JoinColumn(name = "EMP_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrEmployees empId;
    @JoinColumn(name = "EDUCATION_LEVEL_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrLuEducLevels educLevelId;
    @JoinColumn(name = "EDUCATION_TYPE_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrLuEducTypes educTypeId;
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Getter and setter">
    
    public HrEmpEducations() {
    }
    
    public HrEmpEducations(BigDecimal id) {
        this.id = id;
    }
    
    public BigDecimal getId() {
        return id;
    }
    
    public void setId(BigDecimal id) {
        this.id = id;
    }
    
    public String getInstitution() {
        return institution;
    }
    
    public void setInstitution(String institution) {
        this.institution = institution;
    }
    
    public String getStartDate() {
        return startDate;
    }
    
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }
    
    public String getEndDate() {
        return endDate;
    }
    
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
    
    public String getAward() {
        return award;
    }
    
    public void setAward(String award) {
        this.award = award;
    }
    
    public String getEducResult() {
        return educResult;
    }
    
    public void setEducResult(String educResult) {
        this.educResult = educResult;
    }
    
    public String getSponsoredBy() {
        return sponsoredBy;
    }
    
    public void setSponsoredBy(String sponsoredBy) {
        this.sponsoredBy = sponsoredBy;
    }
    
    public HrAddresses getAddressId() {
        return addressId;
    }
    
    public void setAddressId(HrAddresses addressId) {
        this.addressId = addressId;
    }
    
    public HrEmployees getEmpId() {
        return empId;
    }
    
    public void setEmpId(HrEmployees empId) {
        this.empId = empId;
    }
    
    public HrLuEducLevels getEducLevelId() {
        return educLevelId;
    }
    
    public void setEducLevelId(HrLuEducLevels educLevelId) {
        this.educLevelId = educLevelId;
    }
    
    public HrLuEducTypes getEducTypeId() {
        return educTypeId;
    }
    
    public void setEducTypeId(HrLuEducTypes educTypeId) {
        this.educTypeId = educTypeId;
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Otherv Methods">
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }
    
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HrEmpEducations)) {
            return false;
        }
        HrEmpEducations other = (HrEmpEducations) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }
    
    @Override
    public String toString() {
        return id.toString();
    }
//</editor-fold>

}
