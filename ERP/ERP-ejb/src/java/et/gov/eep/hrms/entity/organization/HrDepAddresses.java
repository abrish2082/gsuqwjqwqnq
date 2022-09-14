/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.entity.organization;

import et.gov.eep.hrms.entity.address.HrAddresses;
import java.io.Serializable;
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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author user
 */
@Entity
@Table(name = "HR_DEP_ADDRESSES")
@XmlRootElement
@NamedQueries//<editor-fold defaultstate="collapsed" desc="">
        ({
            @NamedQuery(name = "HrDepAddresses.findDepartmentsById", query = "SELECT h FROM HrDepAddresses h join h.depId s where s.depId=:depId"),
            @NamedQuery(name = "HrDepAddresses.findAll", query = "SELECT h FROM HrDepAddresses h"),
            @NamedQuery(name = "HrDepAddresses.findById", query = "SELECT h FROM HrDepAddresses h WHERE h.id = :id"),
            @NamedQuery(name = "HrDepAddresses.findByDeptId", query = "SELECT h FROM HrDepAddresses h WHERE h.depId.depId = :depId"),
            @NamedQuery(name = "HrDepAddresses.findByBlockNumber", query = "SELECT h FROM HrDepAddresses h WHERE h.blockNumber = :blockNumber"),
            @NamedQuery(name = "HrDepAddresses.findByTelOffice", query = "SELECT h FROM HrDepAddresses h WHERE h.telOffice = :telOffice"),
            @NamedQuery(name = "HrDepAddresses.findByTelExtension", query = "SELECT h FROM HrDepAddresses h WHERE h.telExtension = :telExtension"),
            @NamedQuery(name = "HrDepAddresses.findByMobile", query = "SELECT h FROM HrDepAddresses h WHERE h.mobile = :mobile"),
            @NamedQuery(name = "HrDepAddresses.findByHouseNumber", query = "SELECT h FROM HrDepAddresses h WHERE h.houseNumber = :houseNumber"),
            @NamedQuery(name = "HrDepAddresses.findByFloor", query = "SELECT h FROM HrDepAddresses h WHERE h.floor = :floor"),
            @NamedQuery(name = "HrDepAddresses.findByOfficeNumber", query = "SELECT h FROM HrDepAddresses h WHERE h.officeNumber = :officeNumber"),
            @NamedQuery(name = "HrDepAddresses.findByEMail", query = "SELECT h FROM HrDepAddresses h WHERE h.eMail = :eMail"),
            @NamedQuery(name = "HrDepAddresses.findByWebsite", query = "SELECT h FROM HrDepAddresses h WHERE h.website = :website")})
//</editor-fold>

public class HrDepAddresses implements Serializable {

    //<editor-fold defaultstate="collapsed" desc="Entity Attributes">
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HR_DEP_ADDRESSES_SEQ")
    @SequenceGenerator(name = "HR_DEP_ADDRESSES_SEQ", sequenceName = "HR_DEP_ADDRESSES_SEQ", allocationSize = 1)
    private Integer id;
    @Column(name = "BLOCK_NUMBER")
    private String blockNumber;
    @Column(name = "TEL_OFFICE")
    private String telOffice;
    @Column(name = "TEL_EXTENSION")
    private String telExtension;
    @Size(max = 20)
    @Column(name = "PREPARED_BY")
    private String preparedBy;
    @Size(max = 20)
    @Column(name = "PREPARED_ON")
    private String preparedOn;
    @Column(name = "MOBILE")
    private String mobile;
    @Column(name = "HOUSE_NUMBER")
    private String houseNumber;
    @Column(name = "FLOOR")
    private String floor;
    @Column(name = "OFFICE_NUMBER")
    private String officeNumber;
    @Column(name = "E_MAIL")
    private String eMail;
    @Column(name = "WEBSITE")
    private String website;
    @JoinColumn(name = "LOCATION_ID", referencedColumnName = "ADDRESS_ID")
    private HrAddresses locationId;
    @JoinColumn(name = "DEP_ID", referencedColumnName = "DEP_ID")
    @ManyToOne
    private HrDepartments depId;
//</editor-fold>

    
    //<editor-fold defaultstate="collapsed" desc="getter and setter">
    public HrDepAddresses() {
    }
    
    /**
     *
     * @param id
     */
    public HrDepAddresses(Integer id) {
        this.id = id;
    }
    
    /**
     *
     * @return
     */
    public Integer getId() {
        return id;
    }
    
    /**
     *
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }
    
    /**
     *
     * @return
     */
    public String getBlockNumber() {
        return blockNumber;
    }
    
    /**
     *
     * @param blockNumber
     */
    public void setBlockNumber(String blockNumber) {
        this.blockNumber = blockNumber;
    }
    
    /**
     *
     * @return
     */
    public String getTelOffice() {
        return telOffice;
    }
    
    /**
     *
     * @param telOffice
     */
    public void setTelOffice(String telOffice) {
        this.telOffice = telOffice;
    }
    
    /**
     *
     * @return
     */
    public String getTelExtension() {
        return telExtension;
    }
    
    /**
     *
     * @param telExtension
     */
    public void setTelExtension(String telExtension) {
        this.telExtension = telExtension;
    }
    
    /**
     *
     * @return
     */
    public String getMobile() {
        return mobile;
    }
    
    /**
     *
     * @param mobile
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
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
    public String getFloor() {
        return floor;
    }
    
    /**
     *
     * @param floor
     */
    public void setFloor(String floor) {
        this.floor = floor;
    }
    
    /**
     *
     * @return
     */
    public String getOfficeNumber() {
        return officeNumber;
    }
    
    /**
     *
     * @param officeNumber
     */
    public void setOfficeNumber(String officeNumber) {
        this.officeNumber = officeNumber;
    }
    
    /**
     *
     * @return
     */
    public String getEMail() {
        return eMail;
    }
    
    /**
     *
     * @param eMail
     */
    public void setEMail(String eMail) {
        this.eMail = eMail;
    }
    
    /**
     *
     * @return
     */
    public String getWebsite() {
        return website;
    }
    
    /**
     *
     * @param website
     */
    public void setWebsite(String website) {
        this.website = website;
    }
    
    /**
     *
     * @return
     */
    public HrAddresses getLocationId() {
        return locationId;
    }
    
    /**
     *
     * @param locationId
     */
    public void setLocationId(HrAddresses locationId) {
        this.locationId = locationId;
    }
    
    /**
     *
     * @return
     */
    public HrDepartments getDepId() {
        return depId;
    }
    
    /**
     *
     * @param depId
     */
    public void setDepId(HrDepartments depId) {
        this.depId = depId;
    }
    
    public String getPreparedBy() {
        return preparedBy;
    }
    
    public void setPreparedBy(String preparedBy) {
        this.preparedBy = preparedBy;
    }
    
    public String getPreparedOn() {
        return preparedOn;
    }
    
    public void setPreparedOn(String preparedOn) {
        this.preparedOn = preparedOn;
    }
    
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
        if (!(object instanceof HrDepAddresses)) {
            return false;
        }
        HrDepAddresses other = (HrDepAddresses) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }
    
    @Override
    public String toString() {
        return "et.gov.eep.hrms.entity.HrDepAddresses[ id=" + id + " ]";
    }
//</editor-fold>

}
