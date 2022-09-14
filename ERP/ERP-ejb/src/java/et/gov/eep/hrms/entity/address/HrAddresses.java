/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.entity.address;

import et.gov.eep.hrms.entity.documentProvidingService.HrDocumentRequests;
import et.gov.eep.hrms.entity.organization.HrDepAddresses;
import et.gov.eep.hrms.entity.employee.HrEmpAddresses;
import et.gov.eep.hrms.entity.employee.HrEmpEducations;
import et.gov.eep.hrms.entity.employee.HrEmpTrainings;
import et.gov.eep.hrms.entity.recruitment.HrCandidateTrainings;
import et.gov.eep.hrms.entity.recruitment.HrCandidiateEducations;
import et.gov.eep.hrms.entity.recruitment.HrCandidiates;
import et.gov.eep.hrms.entity.training.HrTdPsvcTraineeDetails;
import et.gov.eep.prms.entity.PrmsForeignExchange;
import et.gov.eep.prms.entity.PrmsServiceProviderDetail;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author munir
 */
@Entity
@Table(name = "HR_ADDRESSES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HrAddresses.findAll", query = "SELECT h FROM HrAddresses h ORDER BY h.addressName ASC"),
    @NamedQuery(name = "HrAddresses.findByAddressId", query = "SELECT h FROM HrAddresses h WHERE h.addressId = :addressId"),
    @NamedQuery(name = "HrAddresses.findByAddressName", query = "SELECT h FROM HrAddresses h WHERE h.addressName = :addressName"),
    @NamedQuery(name = "HrAddresses.findByAddressNameLike", query = "SELECT h FROM HrAddresses h WHERE h.addressName LIKE :addressName"),
    @NamedQuery(name = "HrAddresses.findByAddressDescription", query = "SELECT h FROM HrAddresses h WHERE h.addressDescription = :addressDescription"),
    @NamedQuery(name = "HrAddresses.findByAddressType", query = "SELECT h FROM HrAddresses h WHERE h.addressType = :addressType"),
    @NamedQuery(name = "HrAddresses.findAllCountryFromDescription", query = "SELECT h FROM HrAddresses h WHERE h.addressType = 'Country'"),
    @NamedQuery(name = "HrAddresses.findByParentId", query = "SELECT h FROM HrAddresses h WHERE h.parentId = :parentId")})

public class HrAddresses implements Serializable {

    @Column(name = "PARENT_ID")
    private Integer parentId;
//    @OneToMany(mappedBy = "locationId")
//    private List<HrAllowanceInLevels> hrAllowanceInLevelsList;
//    @OneToMany(mappedBy = "locationId")
//    private List<HrAllowanceInLocations> hrAllowanceInLocationsList;
//    @OneToMany(mappedBy = "locationId")
//    private List<HrAllowanceInJobTitle> hrAllowanceInJobTitleList;
    @OneToMany(mappedBy = "countryofOrigin", fetch = FetchType.LAZY)
    private List<PrmsForeignExchange> prmsForeignExchangeList;
    @OneToMany(mappedBy = "country", fetch = FetchType.LAZY)
    private List<PrmsForeignExchange> prmsForeignExchangeList1;
    @OneToMany(mappedBy = "addressId")
    private List<HrCandidateTrainings> hrCandidateTrainingsList;
    @OneToMany(mappedBy = "addressId")
    private List<HrCandidiateEducations> hrCandidiateEducationsList;
    @OneToMany(mappedBy = "addressId")
    private List<HrCandidiates> hrCandidiatesList;
    @OneToMany(mappedBy = "addressId")
    private List<HrEmpEducations> hrEmpEducationsList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "addressId")
    private List<PrmsServiceProviderDetail> prmsServiceProviderDetails;
    @OneToMany(mappedBy = "addressId")
    private Collection<HrEmpAddresses> hrEmpAddressesCollection;
    @OneToMany(mappedBy = "AddressesId", cascade = CascadeType.ALL)
    private List<HrDocumentRequests> hrDocumentRequestsList;
    @Basic(optional = false)
    @Column(name = "STATUS")
    private BigInteger status;
    @OneToMany(mappedBy = "locationId")
    private List<HrDepAddresses> hrDepAddressesList;
    @OneToMany(mappedBy = "birthPlaceAddressId", cascade = CascadeType.ALL)
    private List<HrTdPsvcTraineeDetails> hrTdPsvcTraineeDetailsList;
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HR_ADDRESSES_SEQ")
    @SequenceGenerator(name = "HR_ADDRESSES_SEQ", sequenceName = "HR_ADDRESSES_SEQ", allocationSize = 1)
    @Basic(optional = false)
    @NotNull
    @Column(name = "ADDRESS_ID")
    private Integer addressId;
    @Size(max = 100)
    @Column(name = "ADDRESS_NAME")
    private String addressName;
    @Size(max = 200)
    @Column(name = "ADDRESS_DESCRIPTION")
    private String addressDescription;
    @Size(max = 20)
    @Column(name = "ADDRESS_TYPE")
    private String addressType;
    @OneToMany(mappedBy = "addressId")
    private List<HrEmpTrainings> empTraningList;

    /**
     *
     */
    public HrAddresses() {
    }

    /**
     *
     * @param addressId
     */
    public HrAddresses(Integer addressId) {
        this.addressId = addressId;
    }

    /**
     *
     * @return
     */
    public Integer getAddressId() {
        return addressId;
    }

    /**
     *
     * @param addressId
     */
    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
    }

    /**
     *
     * @return
     */
    public String getAddressName() {
        return addressName;
    }

    /**
     *
     * @param addressName
     */
    public void setAddressName(String addressName) {
        this.addressName = addressName;
    }

    /**
     *
     * @return
     */
    public String getAddressDescription() {
        return addressDescription;
    }

    /**
     *
     * @param addressDescription
     */
    public void setAddressDescription(String addressDescription) {
        this.addressDescription = addressDescription;
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
//
//    public Integer getParentId() {
//        return parentId;
//    }
//
//    public void setParentId(Integer parentId) {
//        this.parentId = parentId;
//    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (addressId != null ? addressId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HrAddresses)) {
            return false;
        }
        HrAddresses other = (HrAddresses) object;
        if ((this.addressId == null && other.addressId != null) || (this.addressId != null && !this.addressId.equals(other.addressId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return addressId + "--" + addressName;
    }

    /**
     *
     * @return
     */
    public Integer getParentId() {
        return parentId;
    }

    /**
     *
     * @param parentId
     */
    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    /**
     *
     * @return
     */
    public BigInteger getStatus() {
        return status;
    }

    /**
     *
     * @param status
     */
    public void setStatus(BigInteger status) {
        this.status = status;
    }

    /**
     *
     * @return
     */
    @XmlTransient
    public List<HrDepAddresses> getHrDepAddressesList() {
        return hrDepAddressesList;
    }

    /**
     *
     * @param hrDepAddressesList
     */
    public void setHrDepAddressesList(List<HrDepAddresses> hrDepAddressesList) {
        this.hrDepAddressesList = hrDepAddressesList;
    }

    /**
     *
     * @return
     */
    @XmlTransient
    public Collection<HrEmpAddresses> getHrEmpAddressesCollection() {
        return hrEmpAddressesCollection;
    }

    /**
     *
     * @param hrEmpAddressesCollection
     */
    public void setHrEmpAddressesCollection(Collection<HrEmpAddresses> hrEmpAddressesCollection) {
        this.hrEmpAddressesCollection = hrEmpAddressesCollection;
    }

    /**
     *
     * @return
     */
    @XmlTransient
    public List<HrEmpEducations> getHrEmpEducationsList() {
        return hrEmpEducationsList;
    }

    /**
     *
     * @param hrEmpEducationsList
     */
    public void setHrEmpEducationsList(List<HrEmpEducations> hrEmpEducationsList) {
        this.hrEmpEducationsList = hrEmpEducationsList;
    }

    /**
     *
     * @return
     */
    @XmlTransient
    public List<HrCandidiates> getHrCandidiatesList() {
        return hrCandidiatesList;
    }

    /**
     *
     * @param hrCandidiatesList
     */
    public void setHrCandidiatesList(List<HrCandidiates> hrCandidiatesList) {
        this.hrCandidiatesList = hrCandidiatesList;
    }

    /**
     *
     * @return
     */
    @XmlTransient
    public List<HrCandidiateEducations> getHrCandidiateEducationsList() {
        return hrCandidiateEducationsList;
    }

    /**
     *
     * @param hrCandidiateEducationsList
     */
    public void setHrCandidiateEducationsList(List<HrCandidiateEducations> hrCandidiateEducationsList) {
        this.hrCandidiateEducationsList = hrCandidiateEducationsList;
    }

    /**
     *
     * @return
     */
    @XmlTransient
    public List<HrCandidateTrainings> getHrCandidateTrainingsList() {
        return hrCandidateTrainingsList;
    }

    /**
     *
     * @param hrCandidateTrainingsList
     */
    public void setHrCandidateTrainingsList(List<HrCandidateTrainings> hrCandidateTrainingsList) {
        this.hrCandidateTrainingsList = hrCandidateTrainingsList;
    }

    @XmlTransient
    public List<PrmsForeignExchange> getPrmsForeignExchangeList() {
        if (prmsForeignExchangeList == null) {
            prmsForeignExchangeList = new ArrayList<>();
        }
        return prmsForeignExchangeList;
    }

    public void setPrmsForeignExchangeList(List<PrmsForeignExchange> prmsForeignExchangeList) {
        this.prmsForeignExchangeList = prmsForeignExchangeList;
    }

    @XmlTransient
    public List<PrmsForeignExchange> getPrmsForeignExchangeList1() {
        if (prmsForeignExchangeList1 == null) {
            prmsForeignExchangeList1 = new ArrayList<>();
        }
        return prmsForeignExchangeList1;
    }

    public void setPrmsForeignExchangeList1(List<PrmsForeignExchange> prmsForeignExchangeList1) {
        this.prmsForeignExchangeList1 = prmsForeignExchangeList1;
    }

    @XmlTransient
    public List<HrTdPsvcTraineeDetails> getHrTdPsvcTraineeDetailsList() {
        if (hrTdPsvcTraineeDetailsList == null) {
            hrTdPsvcTraineeDetailsList = new ArrayList<>();
        }
        return hrTdPsvcTraineeDetailsList;
    }

    public void setHrTdPsvcTraineeDetailsList(List<HrTdPsvcTraineeDetails> hrTdPsvcTraineeDetailsList) {
        this.hrTdPsvcTraineeDetailsList = hrTdPsvcTraineeDetailsList;
    }

    public List<PrmsServiceProviderDetail> getPrmsServiceProviderDetails() {
        if (prmsServiceProviderDetails == null) {
            prmsServiceProviderDetails = new ArrayList<>();
        }
        return prmsServiceProviderDetails;
    }

    public void setPrmsServiceProviderDetails(List<PrmsServiceProviderDetail> prmsServiceProviderDetails) {
        this.prmsServiceProviderDetails = prmsServiceProviderDetails;
    }

//    @XmlTransient
//    public List<HrAllowanceInLocations> getHrAllowanceInLocationsList() {
//        return hrAllowanceInLocationsList;
//    }
//
//    public void setHrAllowanceInLocationsList(List<HrAllowanceInLocations> hrAllowanceInLocationsList) {
//        this.hrAllowanceInLocationsList = hrAllowanceInLocationsList;
//    }
//    @XmlTransient
//    public List<HrAllowanceInJobTitle> getHrAllowanceInJobTitleList() {
//        return hrAllowanceInJobTitleList;
//    }
//
//    public void setHrAllowanceInJobTitleList(List<HrAllowanceInJobTitle> hrAllowanceInJobTitleList) {
//        this.hrAllowanceInJobTitleList = hrAllowanceInJobTitleList;
//    }
//    @XmlTransient
//    public List<HrAllowanceInLevels> getHrAllowanceInLevelsList() {
//        return hrAllowanceInLevelsList;
//    }
//
//    public void setHrAllowanceInLevelsList(List<HrAllowanceInLevels> hrAllowanceInLevelsList) {
//        this.hrAllowanceInLevelsList = hrAllowanceInLevelsList;
//    }
    @XmlTransient
    public List<HrDocumentRequests> getHrDocumentRequestsList() {
        return hrDocumentRequestsList;
    }

    public void setHrDocumentRequestsList(List<HrDocumentRequests> hrDocumentRequestsList) {
        this.hrDocumentRequestsList = hrDocumentRequestsList;
    }

    @XmlTransient
    public List<HrEmpTrainings> getEmpTraningList() {
        if (empTraningList == null) {
            empTraningList = new ArrayList<>();
        }
        return empTraningList;
    }

    public void setEmpTraningList(List<HrEmpTrainings> empTraningList) {
        this.empTraningList = empTraningList;
    }

}
