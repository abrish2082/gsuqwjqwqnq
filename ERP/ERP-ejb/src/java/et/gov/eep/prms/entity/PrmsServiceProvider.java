/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.entity;

import et.gov.eep.commonApplications.entity.ComLuCountry;
import et.gov.eep.commonApplications.entity.WfPrmsProcessed;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name = "PRMS_SERVICE_PROVIDER")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PrmsServiceProvider.findAll", query = "SELECT p FROM PrmsServiceProvider p"),
    @NamedQuery(name = "PrmsServiceProvider.findByServiceProId", query = "SELECT p FROM PrmsServiceProvider p WHERE p.serviceProId = :serviceProId"),
    @NamedQuery(name = "PrmsServiceProvider.findByName", query = "SELECT p FROM PrmsServiceProvider p WHERE p.name = :name "),
    @NamedQuery(name = "PrmsServiceProvider.findServTypeInsurancesOnly", query = "SELECT p FROM PrmsServiceProvider p WHERE p.type LIKE 'Insurance'"),
    @NamedQuery(name = "PrmsServiceProvider.SearchName", query = "SELECT p FROM PrmsServiceProvider p WHERE p.name  LIKE :name ORDER BY P.name"),
    @NamedQuery(name = "PrmsServiceProvider.findByCountry", query = "SELECT p FROM PrmsServiceProvider p WHERE p.country = :country"),
    @NamedQuery(name = "PrmsServiceProvider.findByAddress", query = "SELECT p FROM PrmsServiceProvider p WHERE p.address = :address"),
    @NamedQuery(name = "PrmsServiceProvider.findByRemark", query = "SELECT p FROM PrmsServiceProvider p WHERE p.remark = :remark"),
    @NamedQuery(name = "PrmsServiceProvider.findByPreparedby", query = "SELECT p FROM PrmsServiceProvider p WHERE p.preparedby = :preparedby"),
    @NamedQuery(name = "PrmsServiceProvider.findByRequestForApproval", query = "SELECT p FROM PrmsServiceProvider p WHERE p.status=0"),
    @NamedQuery(name = "PrmsServiceProvider.findByMaxServProvideNo", query = "SELECT p FROM PrmsServiceProvider p WHERE p.serviceProId = (SELECT Max(d.serviceProId)  from PrmsServiceProvider d)"),
    @NamedQuery(name = "PrmsServiceProvider.findByType", query = "SELECT p FROM PrmsServiceProvider p WHERE p.type = :type"),
    @NamedQuery(name = "PrmsServiceProvider.findByPreparedDtae", query = "SELECT p FROM PrmsServiceProvider p WHERE p.preparedDtae = :preparedDtae"),
    @NamedQuery(name = "PrmsServiceProvider.findByServiceProNo", query = "SELECT p FROM PrmsServiceProvider p WHERE p.serviceProNo = :serviceProNo")})
public class PrmsServiceProvider implements Serializable {

    @Column(name = "STATUS")
    private Integer status;
    @Column(name = "CURRENT_STATUS")
    private Integer currentStatus;

    @OneToMany(mappedBy = "serviceProId")
    private List<PrmsLcRigistrationAmend> prmsLcRigistrationAmendList;
    @JoinColumn(name = "COUNTRY_ID", referencedColumnName = "ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private ComLuCountry countryId;
    @Size(max = 100)
    @Column(name = "CITY", length = 100)
    private String city;
    @OneToMany(mappedBy = "serviceProId")
    private List<PrmsLcRigistration> prmsLcRigistrationList;
    @OneToMany(mappedBy = "serviceProId")
    private List<PrmsLcRequest> prmsLcRequestList;
    @OneToMany(mappedBy = "serviceProId", fetch = FetchType.LAZY)
    private List<PrmsInsuranceNotToBank> prmsInsuranceNotToBankList;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "prmsServiceProvider")
    //  private PrmsLcRequest prmsLcRequest1;
    @OneToMany(mappedBy = "serviceProId", fetch = FetchType.LAZY)
    private List<PrmsInsuranceRequisition> prmsInsuranceRequisitionList;
    @OneToMany(mappedBy = "serviceProName", fetch = FetchType.LAZY)
    private List<PrmsInsuranceRequisition> prmsInsuranceRequisitionList1;
    @OneToMany(mappedBy = "serviceProId", cascade = CascadeType.ALL)
    private List<WfPrmsProcessed> prmsWorkFlowProccedList;

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id

    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PRMS_SERVICE_PRO_SEQ")
    @SequenceGenerator(name = "PRMS_SERVICE_PRO_SEQ", sequenceName = "PRMS_SERVICE_PRO_SEQ", allocationSize = 1)
    @Column(name = "SERVICE_PRO_ID")
    private BigDecimal serviceProId;
    @Size(max = 50)
    @Column(name = "NAME")
    private String name;
    @Size(max = 50)
    @Column(name = "COUNTRY")
    private String country;
    @Size(max = 50)
    @Column(name = "ADDRESS")
    private String address;
    @Size(max = 100)
    @Column(name = "REMARK")
    private String remark;
    @Transient
    private String columnName;
    @Transient
    private String columnValue;

    @Column(name = "PREPAREDBY")
    private Integer preparedby;
    @Size(max = 50)
    @Column(name = "TYPE")
    private String type;
    @Column(name = "PREPARED_DTAE")
    @Temporal(TemporalType.DATE)
    private Date preparedDtae;
    @Size(max = 50)
    @Column(name = "SERVICE_PRO_NO")
    private String serviceProNo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "serviceProId", fetch = FetchType.LAZY)
    private List<PrmsServiceProviderDetail> prmsServiceProviderDetailList;

    public PrmsServiceProvider() {
    }

    /**
     *
     * @param serviceProId
     */
    public PrmsServiceProvider(BigDecimal serviceProId) {
        this.serviceProId = serviceProId;
    }

    /**
     *
     * @return
     */
    public BigDecimal getServiceProId() {
        return serviceProId;
    }

    /**
     *
     * @param serviceProId
     */
    public void setServiceProId(BigDecimal serviceProId) {
        this.serviceProId = serviceProId;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnValue() {
        return columnValue;
    }

    public void setColumnValue(String columnValue) {
        this.columnValue = columnValue;
    }

    /**
     *
     * @return
     */
    
    
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    public List<WfPrmsProcessed> getPrmsWorkFlowProccedList() {
        if (prmsWorkFlowProccedList == null) {
            prmsWorkFlowProccedList = new ArrayList<>();
        }
        return prmsWorkFlowProccedList;
    }

    public void setPrmsWorkFlowProccedList(List<WfPrmsProcessed> prmsWorkFlowProccedList) {
        this.prmsWorkFlowProccedList = prmsWorkFlowProccedList;
    }

    /**
     *
     * @return
     */
    public String getCountry() {
        return country;
    }

    /**
     *
     * @param country
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     *
     * @return
     */
    public String getAddress() {
        return address;
    }

    /**
     *
     * @param address
     */
    public void setAddress(String address) {
        this.address = address;
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

    public Integer getPreparedby() {
        return preparedby;
    }

    public void setPreparedby(Integer preparedby) {
        this.preparedby = preparedby;
    }

    /**
     *
     * @return
     */
    /**
     *
     * @return
     */
    public String getType() {
        return type;
    }

    /**
     *
     * @param type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     *
     * @return
     */
    public Date getPreparedDtae() {
        return preparedDtae;
    }

    /**
     *
     * @param preparedDtae
     */
    public void setPreparedDtae(Date preparedDtae) {
        this.preparedDtae = preparedDtae;
    }

    /**
     *
     * @return
     */
    public String getServiceProNo() {
        return serviceProNo;
    }

    /**
     *
     * @param serviceProNo
     */
    public void setServiceProNo(String serviceProNo) {
        this.serviceProNo = serviceProNo;
    }

    /**
     *
     * @return
     */
    @XmlTransient
    public List<PrmsServiceProviderDetail> getPrmsServiceProviderDetailList() {
        if (prmsServiceProviderDetailList == null) {
            prmsServiceProviderDetailList = new ArrayList<>();
        }
        return prmsServiceProviderDetailList;
    }

    /**
     *
     * @param prmsServiceProviderDetailList
     */
    public void setPrmsServiceProviderDetailList(List<PrmsServiceProviderDetail> prmsServiceProviderDetailList) {
        this.prmsServiceProviderDetailList = prmsServiceProviderDetailList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (serviceProId != null ? serviceProId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PrmsServiceProvider)) {
            return false;
        }
        PrmsServiceProvider other = (PrmsServiceProvider) object;
        if ((this.serviceProId == null && other.serviceProId != null) || (this.serviceProId != null && !this.serviceProId.equals(other.serviceProId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return name;
    }

    /**
     *
     * @param papmsAwardDetailobj
     */
    public void addServiceProvideDetialInfo(PrmsServiceProviderDetail papmsAwardDetailobj) {
        if (papmsAwardDetailobj.getServiceProId() != this) {
            this.getPrmsServiceProviderDetailList().add(papmsAwardDetailobj);

            papmsAwardDetailobj.setServiceProId(this);
        }

    }

    @XmlTransient
    public List<PrmsInsuranceRequisition> getPrmsInsuranceRequisitionList() {
        return prmsInsuranceRequisitionList;
    }

    public void setPrmsInsuranceRequisitionList(List<PrmsInsuranceRequisition> prmsInsuranceRequisitionList) {
        this.prmsInsuranceRequisitionList = prmsInsuranceRequisitionList;
    }

    @XmlTransient

    public List<PrmsInsuranceRequisition> getPrmsInsuranceRequisitionList1() {
        if (prmsInsuranceRequisitionList1 == null) {
            prmsInsuranceRequisitionList1 = new ArrayList<>();
        }
        return prmsInsuranceRequisitionList1;
    }

    public void setPrmsInsuranceRequisitionList1(List<PrmsInsuranceRequisition> prmsInsuranceRequisitionList1) {
        this.prmsInsuranceRequisitionList1 = prmsInsuranceRequisitionList1;
    }

//    public PrmsLcRequest getPrmsLcRequest1() {
//        return prmsLcRequest1;
//    }
//
//    public void setPrmsLcRequest1(PrmsLcRequest prmsLcRequest1) {
//        this.prmsLcRequest1 = prmsLcRequest1;
//    }
    @XmlTransient
    public List<PrmsInsuranceNotToBank> getPrmsInsuranceNotToBankList() {
        return prmsInsuranceNotToBankList;
    }

    public void setPrmsInsuranceNotToBankList(List<PrmsInsuranceNotToBank> prmsInsuranceNotToBankList) {
        this.prmsInsuranceNotToBankList = prmsInsuranceNotToBankList;
    }

    @XmlTransient
    public List<PrmsLcRequest> getPrmsLcRequestList() {
        return prmsLcRequestList;
    }

    public void setPrmsLcRequestList(List<PrmsLcRequest> prmsLcRequestList) {
        this.prmsLcRequestList = prmsLcRequestList;
    }

    @XmlTransient
    public List<PrmsLcRigistration> getPrmsLcRigistrationList() {
        return prmsLcRigistrationList;
    }

    public void setPrmsLcRigistrationList(List<PrmsLcRigistration> prmsLcRigistrationList) {
        this.prmsLcRigistrationList = prmsLcRigistrationList;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public ComLuCountry getCountryId() {
        return countryId;
    }

    public void setCountryId(ComLuCountry countryId) {
        this.countryId = countryId;
    }

    @XmlTransient
    public List<PrmsLcRigistrationAmend> getPrmsLcRigistrationAmendList() {
        return prmsLcRigistrationAmendList;
    }

    public void setPrmsLcRigistrationAmendList(List<PrmsLcRigistrationAmend> prmsLcRigistrationAmendList) {
        this.prmsLcRigistrationAmendList = prmsLcRigistrationAmendList;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(Integer currentStatus) {
        this.currentStatus = currentStatus;
    }
}
