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
@Table(name = "HR_EMP_ADDRESSES")
@XmlRootElement
@NamedQueries//<editor-fold defaultstate="collapsed" desc="">
        ({
            @NamedQuery(name = "HrEmpAddresses.findAll", query = "SELECT h FROM HrEmpAddresses h"),
            @NamedQuery(name = "HrEmpAddresses.findById", query = "SELECT h FROM HrEmpAddresses h WHERE h.id = :id"),
            @NamedQuery(name = "HrEmpAddresses.findByFamilyId", query = "SELECT h FROM HrEmpAddresses h WHERE h.familyId = :familyId"),
            @NamedQuery(name = "HrEmpAddresses.findByAddressType", query = "SELECT h FROM HrEmpAddresses h WHERE h.addressType = :addressType"),
            @NamedQuery(name = "HrEmpAddresses.findByHouseNumber", query = "SELECT h FROM HrEmpAddresses h WHERE h.houseNumber = :houseNumber"),
            @NamedQuery(name = "HrEmpAddresses.findByTelephoneResidence", query = "SELECT h FROM HrEmpAddresses h WHERE h.telephoneResidence = :telephoneResidence"),
            @NamedQuery(name = "HrEmpAddresses.findByTelephoneOffice", query = "SELECT h FROM HrEmpAddresses h WHERE h.telephoneOffice = :telephoneOffice"),
            @NamedQuery(name = "HrEmpAddresses.findByMobileNumber", query = "SELECT h FROM HrEmpAddresses h WHERE h.mobileNumber = :mobileNumber"),
            @NamedQuery(name = "HrEmpAddresses.findByEmail", query = "SELECT h FROM HrEmpAddresses h WHERE h.email = :email"),
            @NamedQuery(name = "HrEmpAddresses.findByPOBox", query = "SELECT h FROM HrEmpAddresses h WHERE h.pOBox = :pOBox")})
//</editor-fold>
public class HrEmpAddresses implements Serializable {

//<editor-fold defaultstate="collapsed" desc="Entity Attributes">
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HR_EMP_ADDRESSES_SEQ")
    @SequenceGenerator(name = "HR_EMP_ADDRESSES_SEQ", sequenceName = "HR_EMP_ADDRESSES_SEQ", allocationSize = 1)
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private BigDecimal id;
    @Size(max = 100)
    @Column(name = "FAMILY_ID")
    private String familyId;
    @Size(max = 100)
    @Column(name = "ADDRESS_TYPE")
    private String addressType;
    @Size(max = 100)
    @Column(name = "HOUSE_NUMBER")
    private String houseNumber;
    @Size(max = 50)
    @Column(name = "TELEPHONE_RESIDENCE")
    private String telephoneResidence;
    @Size(max = 50)
    @Column(name = "TELEPHONE_OFFICE")
    private String telephoneOffice;
    @Size(max = 50)
    @Column(name = "MOBILE_NUMBER")
    private String mobileNumber;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 50)
    @Column(name = "EMAIL")
    private String email;
    @Size(max = 100)
    @Column(name = "P_O_BOX")
    private String pOBox;
    @JoinColumn(name = "ADDRESS_ID", referencedColumnName = "ADDRESS_ID")
    @ManyToOne
    private HrAddresses addressId;
    @JoinColumn(name = "EMP_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrEmployees empId;
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="getter and setter">
    /**
     *
     */
    public HrEmpAddresses() {
    }
    
    /**
     *
     * @param id
     */
    public HrEmpAddresses(BigDecimal id) {
        this.id = id;
    }
    
    /**
     *
     * @return
     */
    public BigDecimal getId() {
        return id;
    }
    
    /**
     *
     * @param id
     */
    public void setId(BigDecimal id) {
        this.id = id;
    }
    
    /**
     *
     * @return
     */
    public String getFamilyId() {
        return familyId;
    }
    
    /**
     *
     * @param familyId
     */
    public void setFamilyId(String familyId) {
        this.familyId = familyId;
    }
    
    /**
     *
     * @return
     */
    public String getAddressType() {
        return addressType;
    }
    
    /**
     *
     * @param addressType
     */
    public void setAddressType(String addressType) {
        this.addressType = addressType;
    }
    
    /**
     *
     * @return
     */
    public String getHouseNumber() {
        return houseNumber;
    }
    
    /**
     *
     * @param houseNumber
     */
    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }
    
    /**
     *
     * @return
     */
    public String getTelephoneResidence() {
        return telephoneResidence;
    }
    
    /**
     *
     * @param telephoneResidence
     */
    public void setTelephoneResidence(String telephoneResidence) {
        this.telephoneResidence = telephoneResidence;
    }
    
    /**
     *
     * @return
     */
    public String getTelephoneOffice() {
        return telephoneOffice;
    }
    
    /**
     *
     * @param telephoneOffice
     */
    public void setTelephoneOffice(String telephoneOffice) {
        this.telephoneOffice = telephoneOffice;
    }
    
    /**
     *
     * @return
     */
    public String getMobileNumber() {
        return mobileNumber;
    }
    
    /**
     *
     * @param mobileNumber
     */
    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }
    
    /**
     *
     * @return
     */
    public String getEmail() {
        return email;
    }
    
    /**
     *
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }
    
    /**
     *
     * @return
     */
    public String getPOBox() {
        return pOBox;
    }
    
    /**
     *
     * @param pOBox
     */
    public void setPOBox(String pOBox) {
        this.pOBox = pOBox;
    }
    
    /**
     *
     * @return
     */
    public HrAddresses getAddressId() {
        return addressId;
    }
    
    /**
     *
     * @param addressId
     */
    public void setAddressId(HrAddresses addressId) {
        this.addressId = addressId;
    }
    
    /**
     *
     * @return
     */
    public HrEmployees getEmpId() {
        return empId;
    }
    
    /**
     *
     * @param empId
     */
    public void setEmpId(HrEmployees empId) {
        this.empId = empId;
    }
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Other methods">
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }
    
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HrEmpAddresses)) {
            return false;
        }
        HrEmpAddresses other = (HrEmpAddresses) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }
    
    @Override
    public String toString() {
        return "et.gov.eep.hrms.entity.HrEmpAddresses[ id=" + id + " ]";
    }
//</editor-fold>
}
