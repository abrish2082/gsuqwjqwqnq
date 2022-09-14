/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.entity.employee;

import et.gov.eep.hrms.entity.address.HrAddresses;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
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
@Table(name = "HR_EMP_REFERENCES")
@XmlRootElement
@NamedQueries//<editor-fold defaultstate="collapsed" desc="">
        ({
            @NamedQuery(name = "HrEmpReferences.findAll", query = "SELECT h FROM HrEmpReferences h"),
            @NamedQuery(name = "HrEmpReferences.findById", query = "SELECT h FROM HrEmpReferences h WHERE h.id = :id"),
            @NamedQuery(name = "HrEmpReferences.findByNameOfReferee", query = "SELECT h FROM HrEmpReferences h WHERE h.nameOfReferee = :nameOfReferee"),
            @NamedQuery(name = "HrEmpReferences.findByJobTitle", query = "SELECT h FROM HrEmpReferences h WHERE h.jobTitle = :jobTitle"),
            @NamedQuery(name = "HrEmpReferences.findByRelationshipType", query = "SELECT h FROM HrEmpReferences h WHERE h.relationshipType = :relationshipType"),
            @NamedQuery(name = "HrEmpReferences.findByPhoneNumber", query = "SELECT h FROM HrEmpReferences h WHERE h.phoneNumber = :phoneNumber"),
            @NamedQuery(name = "HrEmpReferences.findByEmail", query = "SELECT h FROM HrEmpReferences h WHERE h.email = :email"),
            @NamedQuery(name = "HrEmpReferences.findByRemark", query = "SELECT h FROM HrEmpReferences h WHERE h.remark = :remark")})
//</editor-fold>
public class HrEmpReferences implements Serializable {

    //<editor-fold defaultstate="collapsed" desc="Entity Attributes">
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HR_EMP_REFERENCES_SEQ")
    @SequenceGenerator(name = "HR_EMP_REFERENCES_SEQ", sequenceName = "HR_EMP_REFERENCES_SEQ", allocationSize = 1)
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private BigDecimal id;
    @Size(max = 20)
    @Column(name = "NAME_OF_REFEREE")
    private String nameOfReferee;
    @Size(max = 20)
    @Column(name = "JOB_TITLE")
    private String jobTitle;
    @Size(max = 20)
    @Column(name = "RELATIONSHIP_TYPE")
    private String relationshipType;
    @Size(max = 20)
    @Column(name = "PHONE_NUMBER")
    private String phoneNumber;
    @Size(max = 100)
    @Column(name = "OCCUPATION_ADDRESS")
    private String occupationAddress;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 20)
    @Column(name = "EMAIL")
    private String email;
    @Size(max = 20)
    @Column(name = "REMARK")
    private String remark;
    @JoinColumn(name = "EMP_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrEmployees empId;
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="getter and setter">
    public HrEmpReferences() {
    }
    
    public HrEmpReferences(BigDecimal id) {
        this.id = id;
    }
    
    public BigDecimal getId() {
        return id;
    }
    
    public void setId(BigDecimal id) {
        this.id = id;
    }
    
    public String getNameOfReferee() {
        return nameOfReferee;
    }
    
    public void setNameOfReferee(String nameOfReferee) {
        this.nameOfReferee = nameOfReferee;
    }
    
    public String getJobTitle() {
        return jobTitle;
    }
    
    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }
    
    public String getRelationshipType() {
        return relationshipType;
    }
    
    public void setRelationshipType(String relationshipType) {
        this.relationshipType = relationshipType;
    }
    
    public String getPhoneNumber() {
        return phoneNumber;
    }
    
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getRemark() {
        return remark;
    }
    
    public void setRemark(String remark) {
        this.remark = remark;
    }
    
    public HrEmployees getEmpId() {
        return empId;
    }
    
    public void setEmpId(HrEmployees empId) {
        this.empId = empId;
    }
    
    public String getOccupationAddress() {
        return occupationAddress;
    }
    
    public void setOccupationAddress(String occupationAddress) {
        this.occupationAddress = occupationAddress;
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
        if (!(object instanceof HrEmpReferences)) {
            return false;
        }
        HrEmpReferences other = (HrEmpReferences) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }
    
    @Override
    public String toString() {
        return "et.gov.eep.hrms.entity.HrEmpReferences[ id=" + id + " ]";
    }
//</editor-fold>

}
