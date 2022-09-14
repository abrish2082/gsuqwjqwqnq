/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.entity.training;

import et.gov.eep.hrms.entity.address.HrAddresses;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
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
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Benin
 */
@Entity
@Table(name = "HR_TD_TRAINER_PROFILES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HrTdTrainerProfiles.findAll", query = "SELECT h FROM HrTdTrainerProfiles h"),
    @NamedQuery(name = "HrTdTrainerProfiles.findById", query = "SELECT h FROM HrTdTrainerProfiles h WHERE h.id = :id"),
    @NamedQuery(name = "HrTdTrainerProfiles.findByInstitutionName", query = "SELECT h FROM HrTdTrainerProfiles h WHERE h.institutionName = :institutionName"),
    @NamedQuery(name = "HrTdTrainerProfiles.findByInstitutionNameLike", query = "SELECT h FROM HrTdTrainerProfiles h WHERE UPPER(h.institutionName) Like :institutionName"),
    @NamedQuery(name = "HrTdTrainerProfiles.findByAddressId", query = "SELECT h FROM HrTdTrainerProfiles h WHERE h.addressId = :addressId"),
    @NamedQuery(name = "HrTdTrainerProfiles.findByContactPerson", query = "SELECT h FROM HrTdTrainerProfiles h WHERE h.contactPerson = :contactPerson"),
    @NamedQuery(name = "HrTdTrainerProfiles.findByPhoneNumber", query = "SELECT h FROM HrTdTrainerProfiles h WHERE h.phoneNumber = :phoneNumber"),
    @NamedQuery(name = "HrTdTrainerProfiles.findByEmail", query = "SELECT h FROM HrTdTrainerProfiles h WHERE h.email = :email"),
    @NamedQuery(name = "HrTdTrainerProfiles.findByFax", query = "SELECT h FROM HrTdTrainerProfiles h WHERE h.fax = :fax"),
    @NamedQuery(name = "HrTdTrainerProfiles.findByWebSite", query = "SELECT h FROM HrTdTrainerProfiles h WHERE h.webSite = :webSite"),
    @NamedQuery(name = "HrTdTrainerProfiles.findByTinNumber", query = "SELECT h FROM HrTdTrainerProfiles h WHERE h.tinNumber = :tinNumber"),
    @NamedQuery(name = "HrTdTrainerProfiles.findByRemark", query = "SELECT h FROM HrTdTrainerProfiles h WHERE h.remark = :remark")})
public class HrTdTrainerProfiles implements Serializable {

    @OneToMany(mappedBy = "insId")
    private List<HrTdAnnualPlanGroups> hrTdAnnualPlanGroupsList;
    @OneToMany(mappedBy = "institutionId")
    private List<HrTdAnnualTrainingNeeds> hrTdAnnualTrainingNeedsList;

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HR_TD_TRAINER_PROFILES_SEQ")
    @SequenceGenerator(name = "HR_TD_TRAINER_PROFILES_SEQ", sequenceName = "HR_TD_TRAINER_PROFILES_SEQ", allocationSize = 1)
    @Column(name = "ID")
    private BigDecimal id;
    @Column(name = "INSTITUTION_NAME")
    private String institutionName;
    @JoinColumn(name = "ADDRESS_ID", referencedColumnName = "ADDRESS_ID")
    @ManyToOne
    private HrAddresses addressId;
    @Column(name = "CONTACT_PERSON")
    private String contactPerson;
    @Column(name = "PHONE_NUMBER")
    private String phoneNumber;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Column(name = "EMAIL")
    private String email;
    // @Pattern(regexp="^\\(?(\\d{3})\\)?[- ]?(\\d{3})[- ]?(\\d{4})$", message="Invalid phone/fax format, should be as xxx-xxx-xxxx")//if the field contains phone or fax number consider using this annotation to enforce field validation
    @Column(name = "FAX")
    private String fax;
    @Column(name = "WEB_SITE")
    private String webSite;
    @Column(name = "TIN_NUMBER")
    private String tinNumber;
    @Column(name = "REMARK")
    private String remark;

    public HrTdTrainerProfiles() {
    }

    public HrTdTrainerProfiles(BigDecimal id) {
        this.id = id;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getInstitutionName() {
        return institutionName;
    }

    public void setInstitutionName(String institutionName) {
        this.institutionName = institutionName;
    }

    public HrAddresses getAddressId() {
        return addressId;
    }

    public void setAddressId(HrAddresses addressId) {
        this.addressId = addressId;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
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

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getWebSite() {
        return webSite;
    }

    public void setWebSite(String webSite) {
        this.webSite = webSite;
    }

    public String getTinNumber() {
        return tinNumber;
    }

    public void setTinNumber(String tinNumber) {
        this.tinNumber = tinNumber;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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
        if (!(object instanceof HrTdTrainerProfiles)) {
            return false;
        }
        HrTdTrainerProfiles other = (HrTdTrainerProfiles) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @XmlTransient
    public List<HrTdAnnualTrainingNeeds> getHrTdAnnualTrainingNeedsList() {
        return hrTdAnnualTrainingNeedsList;
    }

    public void setHrTdAnnualTrainingNeedsList(List<HrTdAnnualTrainingNeeds> hrTdAnnualTrainingNeedsList) {
        this.hrTdAnnualTrainingNeedsList = hrTdAnnualTrainingNeedsList;
    }

    @XmlTransient
    public List<HrTdAnnualPlanGroups> getHrTdAnnualPlanGroupsList() {
        return hrTdAnnualPlanGroupsList;
    }

    public void setHrTdAnnualPlanGroupsList(List<HrTdAnnualPlanGroups> hrTdAnnualPlanGroupsList) {
        this.hrTdAnnualPlanGroupsList = hrTdAnnualPlanGroupsList;
    }

    @Override
    public String toString() {
        return institutionName;
    }

    }
