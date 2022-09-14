/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.entity.employee;

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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author user
 */
@Entity
@Table(name = "HR_EMP_FAMILIES")
@XmlRootElement
@NamedQueries//<editor-fold defaultstate="collapsed" desc="">
        ({
            @NamedQuery(name = "HrEmpFamilies.findAll", query = "SELECT h FROM HrEmpFamilies h"),
            @NamedQuery(name = "HrEmpFamilies.findById", query = "SELECT h FROM HrEmpFamilies h WHERE h.id = :id"),
            @NamedQuery(name = "HrEmpFamilies.findByFirstName", query = "SELECT h FROM HrEmpFamilies h WHERE h.firstName = :firstName"),
            @NamedQuery(name = "HrEmpFamilies.findByMiddleName", query = "SELECT h FROM HrEmpFamilies h WHERE h.middleName = :middleName"),
            @NamedQuery(name = "HrEmpFamilies.findByLastName", query = "SELECT h FROM HrEmpFamilies h WHERE h.lastName = :lastName"),
            @NamedQuery(name = "HrEmpFamilies.findBySex", query = "SELECT h FROM HrEmpFamilies h WHERE h.sex = :sex"),
            @NamedQuery(name = "HrEmpFamilies.findByRelationshipType", query = "SELECT h FROM HrEmpFamilies h WHERE h.relationshipType = :relationshipType"),
            @NamedQuery(name = "HrEmpFamilies.findByDateOfBirth", query = "SELECT h FROM HrEmpFamilies h WHERE h.dateOfBirth = :dateOfBirth"),
            @NamedQuery(name = "HrEmpFamilies.findByEmergencyContact", query = "SELECT h FROM HrEmpFamilies h WHERE h.emergencyContact = :emergencyContact"),
            @NamedQuery(name = "HrEmpFamilies.findByStatus", query = "SELECT h FROM HrEmpFamilies h WHERE h.status = :status"),
            @NamedQuery(name = "HrEmpFamilies.findByHeir", query = "SELECT h FROM HrEmpFamilies h WHERE h.heir = :heir")})
//</editor-fold>
public class HrEmpFamilies implements Serializable {

    //<editor-fold defaultstate="collapsed" desc="Entity Attributes">
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HR_EMP_FAMILIES_SEQ")
    @SequenceGenerator(name = "HR_EMP_FAMILIES_SEQ", sequenceName = "HR_EMP_FAMILIES_SEQ", allocationSize = 1)
    @Column(name = "ID")
    @Basic(optional = false)
    @NotNull
    private BigDecimal id;
    @Size(max = 50)
    @Column(name = "FIRST_NAME")
    private String firstName;
    @Size(max = 50)
    @Column(name = "MIDDLE_NAME")
    private String middleName;
    @Size(max = 20)
    @Column(name = "LAST_NAME")
    private String lastName;
    @Size(max = 10)
    @Column(name = "SEX")
    private String sex;
    @Size(max = 50)
    @Column(name = "RELATIONSHIP_TYPE")
    private String relationshipType;
    @Column(name = "DATE_OF_BIRTH")
    private String dateOfBirth;
    @Size(max = 50)
    @Column(name = "EMERGENCY_CONTACT")
    private String emergencyContact;
    @Size(max = 20)
    @Column(name = "STATUS")
    private String status;
    @Size(max = 20)
    @Column(name = "PHONE_NUMBER")
    private String phoneNumber;
    @Size(max = 20)
    @Column(name = "HEIR")
    private String heir;
    @JoinColumn(name = "EMP_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrEmployees empId;
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="getter and setter">
    public HrEmpFamilies() {
    }
    
    public HrEmpFamilies(BigDecimal id) {
        this.id = id;
    }
    
    public BigDecimal getId() {
        return id;
    }
    
    public void setId(BigDecimal id) {
        this.id = id;
    }
    
    public String getFirstName() {
        return firstName;
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    public String getMiddleName() {
        return middleName;
    }
    
    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }
    
    public String getLastName() {
        return lastName;
    }
    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    public String getSex() {
        return sex;
    }
    
    public void setSex(String sex) {
        this.sex = sex;
    }
    
    public String getRelationshipType() {
        return relationshipType;
    }
    
    public void setRelationshipType(String relationshipType) {
        this.relationshipType = relationshipType;
    }
    
    public String getDateOfBirth() {
        return dateOfBirth;
    }
    
    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
    
    public String getEmergencyContact() {
        return emergencyContact;
    }
    
    public void setEmergencyContact(String emergencyContact) {
        this.emergencyContact = emergencyContact;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public String getHeir() {
        return heir;
    }
    
    public void setHeir(String heir) {
        this.heir = heir;
    }
    
    public HrEmployees getEmpId() {
        return empId;
    }
    
    public void setEmpId(HrEmployees empId) {
        this.empId = empId;
    }
    
//    public String getPlace() {
//        return place;
//    }
//
//    public void setPlace(String place) {
//        this.place = place;
//    }
    
    public String getPhoneNumber() {
        return phoneNumber;
    }
    
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
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
        if (!(object instanceof HrEmpFamilies)) {
            return false;
        }
        HrEmpFamilies other = (HrEmpFamilies) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }
    
    @Override
    public String toString() {
        return firstName;
    }
//</editor-fold>
}
