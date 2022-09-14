/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.entity.employee;

import et.gov.eep.hrms.entity.address.HrAddresses;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
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
@Table(name = "HR_EMP_EXPERIENCES")
@XmlRootElement
@NamedQueries//<editor-fold defaultstate="collapsed" desc="">
        ({
            @NamedQuery(name = "HrEmpExperiences.findAll", query = "SELECT h FROM HrEmpExperiences h"),
            @NamedQuery(name = "HrEmpExperiences.findById", query = "SELECT h FROM HrEmpExperiences h WHERE h.id = :id"),
            @NamedQuery(name = "HrEmpExperiences.findByInstitution", query = "SELECT h FROM HrEmpExperiences h WHERE h.institution = :institution"),
            @NamedQuery(name = "HrEmpExperiences.findByJobTitle", query = "SELECT h FROM HrEmpExperiences h WHERE h.jobTitle = :jobTitle"),
            @NamedQuery(name = "HrEmpExperiences.findBySalary", query = "SELECT h FROM HrEmpExperiences h WHERE h.salary = :salary"),
            @NamedQuery(name = "HrEmpExperiences.findByStartDate", query = "SELECT h FROM HrEmpExperiences h WHERE h.startDate = :startDate"),
            @NamedQuery(name = "HrEmpExperiences.findByEndDate", query = "SELECT h FROM HrEmpExperiences h WHERE h.endDate = :endDate"),
            @NamedQuery(name = "HrEmpExperiences.findByEmpId", query = "SELECT h FROM HrEmpExperiences h WHERE h.empId = :empId"),
            @NamedQuery(name = "HrEmpExperiences.findByKeyDutyResponsibility", query = "SELECT h FROM HrEmpExperiences h WHERE h.keyDutyResponsibility = :keyDutyResponsibility"),
            @NamedQuery(name = "HrEmpExperiences.findByReasonForTermination", query = "SELECT h FROM HrEmpExperiences h WHERE h.reasonForTermination = :reasonForTermination")})
//</editor-fold>
public class HrEmpExperiences implements Serializable {
    //<editor-fold defaultstate="collapsed" desc="Entity Attributes">
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HR_EMP_EXPERIENCES_SEQ")
    @SequenceGenerator(name = "HR_EMP_EXPERIENCES_SEQ", sequenceName = "HR_EMP_EXPERIENCES_SEQ", allocationSize = 1)
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private BigDecimal id;
    @Size(max = 400)
    @Column(name = "INSTITUTION")
    private String institution;
    @Size(max = 50)
    @Column(name = "JOB_TITLE")
    private String jobTitle;
    @Column(name = "SALARY")
    private Float salary;
    @Size(max = 20)
    @Column(name = "START_DATE")
    private String startDate;
    @Size(max = 20)
    @Column(name = "END_DATE")
    private String endDate;
    @Size(max = 400)
    @Column(name = "KEY_DUTY_RESPONSIBILITY")
    private String keyDutyResponsibility;
    @Size(max = 400)
    @Column(name = "REASON_FOR_TERMINATION")
    private String reasonForTermination;
    @JoinColumn(name = "ADDRESS_ID", referencedColumnName = "ADDRESS_ID")
    @ManyToOne
    private HrAddresses addressId;
    @JoinColumn(name = "EMP_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrEmployees empId;
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="getter and setter">
    public HrEmpExperiences() {
    }
    
    public HrEmpExperiences(BigDecimal id) {
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
    
    public String getJobTitle() {
        return jobTitle;
    }
    
    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }
    
    public Float getSalary() {
        return salary;
    }
    
    public void setSalary(Float salary) {
        this.salary = salary;
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
    
    public String getKeyDutyResponsibility() {
        return keyDutyResponsibility;
    }
    
    public void setKeyDutyResponsibility(String keyDutyResponsibility) {
        this.keyDutyResponsibility = keyDutyResponsibility;
    }
    
    public String getReasonForTermination() {
        return reasonForTermination;
    }
    
    public void setReasonForTermination(String reasonForTermination) {
        this.reasonForTermination = reasonForTermination;
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
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Other Methods">
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }
    
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HrEmpExperiences)) {
            return false;
        }
        HrEmpExperiences other = (HrEmpExperiences) object;
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
