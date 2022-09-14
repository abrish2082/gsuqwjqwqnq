/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.entity;

import et.gov.eep.hrms.entity.address.HrAddresses;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
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
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name = "PRMS_SERVICE_PROVIDER_DETAIL")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PrmsServiceProviderDetail.findAll", query = "SELECT p FROM PrmsServiceProviderDetail p"),
    //@NamedQuery(name = "PrmsServiceProviderDetail.findByServiceDtId", query = "SELECT p FROM PrmsServiceProviderDetail p WHERE p.serviceDtId = :serviceProId"),
    @NamedQuery(name = "PrmsServiceProviderDetail.findByServiceDtId", query = "SELECT p FROM PrmsServiceProviderDetail p WHERE p.serviceDtId = :serviceDtId"),
    @NamedQuery(name = "PrmsServiceProviderDetail.searchByServiceId", query = "SELECT p FROM PrmsServiceProviderDetail p WHERE p.serviceProId = :serviceProId"),

    @NamedQuery(name = "PrmsServiceProviderDetail.findByBranchName", query = "SELECT p FROM PrmsServiceProviderDetail p WHERE p.branchName = :branchName"),
    @NamedQuery(name = "PrmsServiceProviderDetail.findByBranchType", query = "SELECT p FROM PrmsServiceProviderDetail p WHERE p.branchType = :branchType"),
    @NamedQuery(name = "PrmsServiceProviderDetail.findByCity", query = "SELECT p FROM PrmsServiceProviderDetail p WHERE p.city = :city"),
    @NamedQuery(name = "PrmsServiceProviderDetail.findByRemark", query = "SELECT p FROM PrmsServiceProviderDetail p WHERE p.remark = :remark")})
public class PrmsServiceProviderDetail implements Serializable {

    @JoinColumn(name = "ADDRESS_ID", referencedColumnName = "ADDRESS_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private HrAddresses addressId;
    @Size(max = 100)
    @Column(name = "TELEPHONE_NO", length = 100)
    private String telephoneNo;
    @Size(max = 100)
    @Column(name = "FAX", length = 100)
    private String fax;
    @Size(max = 100)
    @Column(name = "EMAIL", length = 100)
    private String email;
    @Size(max = 100)
    @Column(name = "MOBILE_NO", length = 100)
    private String mobileNo;
    @Size(max = 100)
    @Column(name = "PO_BOX", length = 100)
    private String poBox;

    @OneToMany(mappedBy = "bankId", fetch = FetchType.LAZY)
    private List<PrmsForeignExchange> prmsForeignExchangeList;
    @OneToMany(mappedBy = "serviceDtId", fetch = FetchType.LAZY)
    private List<PrmsInsuranceNotToBank> prmsInsuranceNotToBankList;
    @OneToMany(mappedBy = "serviceDtId", fetch = FetchType.LAZY)
    private List<PrmsInsuranceRequisition> prmsInsuranceRequisitionList;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PRMS_SERVICE_DTSEQ")
    @SequenceGenerator(name = "PRMS_SERVICE_DTSEQ", sequenceName = "PRMS_SERVICE_DTSEQ", allocationSize = 1)
    @Column(name = "SERVICE_DT_ID")
    private BigDecimal serviceDtId;
    @Size(max = 50)
    @Column(name = "BRANCH_NAME")
    private String branchName;
    @Size(max = 50)
    @Column(name = "BRANCH_TYPE")
    private String branchType;
    @Size(max = 50)
    @Column(name = "CITY")
    private String city;
    @Size(max = 5000)
    @Column(name = "REMARK")
    private String remark;
    @JoinColumn(name = "SERVICE_PRO_ID", referencedColumnName = "SERVICE_PRO_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private PrmsServiceProvider serviceProId;

    /**
     *
     */
    public PrmsServiceProviderDetail() {
    }

    /**
     *
     * @param serviceDtId
     */
    public PrmsServiceProviderDetail(BigDecimal serviceDtId) {
        this.serviceDtId = serviceDtId;
    }

    /**
     *
     * @return
     */
    public BigDecimal getServiceDtId() {
        return serviceDtId;
    }

    /**
     *
     * @param serviceDtId
     */
    public void setServiceDtId(BigDecimal serviceDtId) {
        this.serviceDtId = serviceDtId;
    }

    /**
     *
     * @return
     */
    public String getBranchName() {
        return branchName;
    }

    /**
     *
     * @param branchName
     */
    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    /**
     *
     * @return
     */
    public String getBranchType() {
        return branchType;
    }

    /**
     *
     * @param branchType
     */
    public void setBranchType(String branchType) {
        this.branchType = branchType;
    }

    /**
     *
     * @return
     */
    public String getCity() {
        return city;
    }

    /**
     *
     * @param city
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     *
     * @return
     */
    public String getRemark() {
        return remark;
    }

    /**
     *
     * @param remark
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     *
     * @return
     */
    public PrmsServiceProvider getServiceProId() {
        return serviceProId;
    }

    /**
     *
     * @param serviceProId
     */
    public void setServiceProId(PrmsServiceProvider serviceProId) {
        this.serviceProId = serviceProId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (serviceDtId != null ? serviceDtId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PrmsServiceProviderDetail)) {
            return false;
        }
        PrmsServiceProviderDetail other = (PrmsServiceProviderDetail) object;
        if ((this.serviceDtId == null && other.serviceDtId != null) || (this.serviceDtId != null && !this.serviceDtId.equals(other.serviceDtId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return branchName;
    }

    @XmlTransient
    public List<PrmsInsuranceRequisition> getPrmsInsuranceRequisitionList() {
        return prmsInsuranceRequisitionList;
    }

    public void setPrmsInsuranceRequisitionList(List<PrmsInsuranceRequisition> prmsInsuranceRequisitionList) {
        this.prmsInsuranceRequisitionList = prmsInsuranceRequisitionList;
    }

    @XmlTransient
    public List<PrmsInsuranceNotToBank> getPrmsInsuranceNotToBankList() {
        return prmsInsuranceNotToBankList;
    }

    public void setPrmsInsuranceNotToBankList(List<PrmsInsuranceNotToBank> prmsInsuranceNotToBankList) {
        this.prmsInsuranceNotToBankList = prmsInsuranceNotToBankList;
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

    public String getTelephoneNo() {
        return telephoneNo;
    }

    public void setTelephoneNo(String telephoneNo) {
        this.telephoneNo = telephoneNo;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getPoBox() {
        return poBox;
    }

    public void setPoBox(String poBox) {
        this.poBox = poBox;
    }

    public HrAddresses getAddressId() {
        return addressId;
    }

    public void setAddressId(HrAddresses addressId) {
        this.addressId = addressId;
    }

}
