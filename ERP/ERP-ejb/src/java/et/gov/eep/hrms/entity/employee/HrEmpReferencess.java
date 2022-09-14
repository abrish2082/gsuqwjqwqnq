/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.entity.employee;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author user
 */
@Entity
@Table(name = "HR_EMP_REFERENCESS")
@XmlRootElement
@NamedQueries//<editor-fold defaultstate="collapsed" desc="">
        ({
            @NamedQuery(name = "HrEmpReferencess.findAll", query = "SELECT h FROM HrEmpReferencess h"),
            @NamedQuery(name = "HrEmpReferencess.findById", query = "SELECT h FROM HrEmpReferencess h WHERE h.id = :id"),
            @NamedQuery(name = "HrEmpReferencess.findByFirstName", query = "SELECT h FROM HrEmpReferencess h WHERE h.firstName = :firstName"),
            @NamedQuery(name = "HrEmpReferencess.findByMiddleName", query = "SELECT h FROM HrEmpReferencess h WHERE h.middleName = :middleName"),
            @NamedQuery(name = "HrEmpReferencess.findByLastName", query = "SELECT h FROM HrEmpReferencess h WHERE h.lastName = :lastName"),
            @NamedQuery(name = "HrEmpReferencess.findByAddress", query = "SELECT h FROM HrEmpReferencess h WHERE h.address = :address"),
            @NamedQuery(name = "HrEmpReferencess.findByJobTitle", query = "SELECT h FROM HrEmpReferencess h WHERE h.jobTitle = :jobTitle"),
            @NamedQuery(name = "HrEmpReferencess.findByRelationship", query = "SELECT h FROM HrEmpReferencess h WHERE h.relationship = :relationship"),
            @NamedQuery(name = "HrEmpReferencess.findByPhone", query = "SELECT h FROM HrEmpReferencess h WHERE h.phone = :phone"),
            @NamedQuery(name = "HrEmpReferencess.findBySex", query = "SELECT h FROM HrEmpReferencess h WHERE h.sex = :sex")})
//</editor-fold>
public class HrEmpReferencess implements Serializable {
    //<editor-fold defaultstate="collapsed" desc="Entity Attributes">
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private Integer id;
    @Size(max = 50)
    @Column(name = "FIRST_NAME")
    private String firstName;
    @Size(max = 50)
    @Column(name = "MIDDLE_NAME")
    private String middleName;
    @Size(max = 50)
    @Column(name = "LAST_NAME")
    private String lastName;
    @Size(max = 50)
    @Column(name = "ADDRESS")
    private String address;
    @Size(max = 50)
    @Column(name = "JOB_TITLE")
    private String jobTitle;
    @Size(max = 20)
    @Column(name = "RELATIONSHIP")
    private String relationship;
    // @Pattern(regexp="^\\(?(\\d{3})\\)?[- ]?(\\d{3})[- ]?(\\d{4})$", message="Invalid phone/fax format, should be as xxx-xxx-xxxx")//if the field contains phone or fax number consider using this annotation to enforce field validation
    @Size(max = 20)
    @Column(name = "PHONE")
    private String phone;
    @Size(max = 10)
    @Column(name = "SEX")
    private String sex;
    @JoinColumn(name = "EMP_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrEmployees empId;
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="getter and setter">
    public HrEmpReferencess() {
    }
    
    public HrEmpReferencess(Integer id) {
        this.id = id;
    }
    
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
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
    
    public String getAddress() {
        return address;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }
    
    public String getJobTitle() {
        return jobTitle;
    }
    
    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }
    
    public String getRelationship() {
        return relationship;
    }
    
    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }
    
    public String getPhone() {
        return phone;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    public String getSex() {
        return sex;
    }
    
    public void setSex(String sex) {
        this.sex = sex;
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
        if (!(object instanceof HrEmpReferencess)) {
            return false;
        }
        HrEmpReferencess other = (HrEmpReferencess) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }
    
    @Override
    public String toString() {
        return "et.gov.eep.hrms.entity.HrEmpReferencess[ id=" + id + " ]";
    }
//</editor-fold>
    
}
