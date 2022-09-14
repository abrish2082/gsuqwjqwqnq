/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.entity.employee;

import et.gov.eep.hrms.entity.address.HrAddresses;
import et.gov.eep.hrms.entity.training.HrTdEmpTrainings;
import java.io.Serializable;
import java.math.BigDecimal;
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
@Table(name = "HR_EMP_TRAININGS")
@XmlRootElement
@NamedQueries//<editor-fold defaultstate="collapsed" desc="">
        ({
            @NamedQuery(name = "HrEmpTrainings.findAll", query = "SELECT h FROM HrEmpTrainings h"),
            @NamedQuery(name = "HrEmpTrainings.findById", query = "SELECT h FROM HrEmpTrainings h WHERE h.id = :id"),
            @NamedQuery(name = "HrEmpTrainings.findByTrainingTitle", query = "SELECT h FROM HrEmpTrainings h WHERE h.trainingTitle = :trainingTitle"),
            @NamedQuery(name = "HrEmpTrainings.findByInstitution", query = "SELECT h FROM HrEmpTrainings h WHERE h.institution = :institution"),
            @NamedQuery(name = "HrEmpTrainings.findByStartDate", query = "SELECT h FROM HrEmpTrainings h WHERE h.startDate = :startDate"),
            @NamedQuery(name = "HrEmpTrainings.findByEndDate", query = "SELECT h FROM HrEmpTrainings h WHERE h.endDate = :endDate"),
            @NamedQuery(name = "HrEmpTrainings.findByEmpId", query = "SELECT h FROM HrEmpTrainings h WHERE h.empId = :empId"),
//    @NamedQuery(name = "HrEmpTrainings.findByEmpTraId", query = "SELECT h FROM HrEmpTrainings h WHERE h.empTraId = :empTraId"),
            @NamedQuery(name = "HrEmpTrainings.findBySponsoredBy", query = "SELECT h FROM HrEmpTrainings h WHERE h.sponsoredBy = :sponsoredBy")})
//</editor-fold>
public class HrEmpTrainings implements Serializable {

//<editor-fold defaultstate="collapsed" desc="Entity Attributes">
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HR_EMP_TRAINING_SEQ")
    @SequenceGenerator(name = "HR_EMP_TRAINING_SEQ", sequenceName = "HR_EMP_TRAINING_SEQ", allocationSize = 1)
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private BigDecimal id;
    @Size(max = 200)
    @Column(name = "TRAINING_TITLE")
    private String trainingTitle;
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
    @Column(name = "SPONSORED_BY")
    private String sponsoredBy;
    @JoinColumn(name = "ADDRESS_ID", referencedColumnName = "ADDRESS_ID")
    @ManyToOne
    private HrAddresses addressId;
    @JoinColumn(name = "EMP_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrEmployees empId;
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="getter and setter">
    
    public HrEmpTrainings() {
    }
    
    public HrEmpTrainings(BigDecimal id) {
        this.id = id;
    }
    
    public BigDecimal getId() {
        return id;
    }
    
    public void setId(BigDecimal id) {
        this.id = id;
    }
    
    public String getTrainingTitle() {
        return trainingTitle;
    }
    
    public void setTrainingTitle(String trainingTitle) {
        this.trainingTitle = trainingTitle;
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
    
//    public HrTdEmpTrainings getEmpTraId() {
//        return empTraId;
//    }
//
//    public void setEmpTraId(HrTdEmpTrainings empTraId) {
//        this.empTraId = empTraId;
//    }
//</editor-fold>

 //<editor-fold defaultstate="collapsed" desc="other Methods">
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }
    
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HrEmpTrainings)) {
            return false;
        }
        HrEmpTrainings other = (HrEmpTrainings) object;
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
