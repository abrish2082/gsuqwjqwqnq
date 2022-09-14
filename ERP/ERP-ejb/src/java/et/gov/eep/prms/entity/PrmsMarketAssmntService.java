/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
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
@Table(name = "PRMS_MARKET_ASSMNT_SERVICE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PrmsMarketAssmntService.findAll", query = "SELECT p FROM PrmsMarketAssmntService p"),
    @NamedQuery(name = "PrmsMarketAssmntService.findById", query = "SELECT p FROM PrmsMarketAssmntService p WHERE p.serviceId.serviceName = :id"),
    @NamedQuery(name = "PrmsMarketAssmntService.findByAvailabilty", query = "SELECT p FROM PrmsMarketAssmntService p WHERE p.availabilty = :availabilty"),
    @NamedQuery(name = "PrmsMarketAssmntService.findByServiceNameCon", query = "SELECT p FROM PrmsMarketAssmntService p WHERE p.serviceId.serviceType = 'consultancy' ORDER BY p.serviceId.serviceName"),
    @NamedQuery(name = "PrmsMarketAssmntService.findByServiceNameNonCon", query = "SELECT p FROM PrmsMarketAssmntService p WHERE p.serviceId.serviceType = 'non-consultancy' ORDER BY p.serviceId.serviceName"),
//    @NamedQuery(name = "PrmsMarketAssmntService.findByServiceNameNonCon", query = "SELECT p FROM PrmsMarketAssmntService p WHERE p.serviceId.serviceType = 'non-consultancy' ORDER BY p.serviceId.serviceName DESC"),
    @NamedQuery(name = "PrmsMarketAssmntService.findOtherByServName", query = "SELECT p FROM PrmsMarketAssmntService p WHERE p.serviceId=:serviceId"),
    @NamedQuery(name = "PrmsMarketAssmntService.worksName", query = "SELECT p FROM PrmsMarketAssmntService p WHERE p.serviceId.registerationType = 'work' ORDER BY p.serviceId.workName"),
    @NamedQuery(name = "PrmsMarketAssmntService.getOtherByWorkId", query = "SELECT p FROM PrmsMarketAssmntService p WHERE p.serviceId = :serviceId"),
    @NamedQuery(name = "PrmsMarketAssmntService.findByQuantity", query = "SELECT p FROM PrmsMarketAssmntService p WHERE p.quantity = :quantity"),
    @NamedQuery(name = "PrmsMarketAssmntService.findByUnitPrice", query = "SELECT p FROM PrmsMarketAssmntService p WHERE p.unitPrice = :unitPrice"),
    @NamedQuery(name = "PrmsMarketAssmntService.findByRemark", query = "SELECT p FROM PrmsMarketAssmntService p WHERE p.remark = :remark"),
    @NamedQuery(name = "PrmsMarketAssmntService.findByUnitMeasure", query = "SELECT p FROM PrmsMarketAssmntService p WHERE p.unitMeasure = :unitMeasure")})
public class PrmsMarketAssmntService implements Serializable {

//    @OneToMany(mappedBy = "marktAssServiceId")
//    private List<PrmsPurchaseReqService> prmsPurchaseReqServiceList;
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "ID", nullable = false, length = 20)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PRMS_MARKETASS_SERVICE_SEQ_GENERATOR")
    @SequenceGenerator(name = "PRMS_MARKETASS_SERVICE_SEQ_GENERATOR", sequenceName = "PRMS_MARKETASS_SERVICE_SEQ", allocationSize = 1)
    private String id;
    @Size(max = 20)
    @Column(name = "AVAILABILTY", length = 20)
    private String availabilty;
    @Column(name = "QUANTITY")
    private Integer quantity;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "UNIT_PRICE")
    private BigDecimal unitPrice;
    @Size(max = 20)
    @Column(name = "REMARK", length = 20)
    private String remark;
    @Size(max = 20)
    @Column(name = "UNIT_MEASURE", length = 20)
    private String unitMeasure;
    @Size(max = 20)
    @Column(name = "SOURCE_OF_ASSESSMENT", length = 20)
    private String sourceOfAssessment;
    @Size(max = 20)
    @Column(name = "SERVICE_TYPE", length = 20)
    private String serviceType;
    @Size(max = 20)
    @Column(name = "PURCHASE_TYPE", length = 20)
    private String purchaseType;
    @Column(name = "FROM_YEAR")
    @Temporal(TemporalType.DATE)
    private Date fromYear;
    @Column(name = "TO_YEAR")
    @Temporal(TemporalType.DATE)
    private Date toYear;
    @JoinColumn(name = "SERVICE_ID", referencedColumnName = "SERV_AND_WORK_ID")
    @ManyToOne
    private PrmsServiceAndWorkReg serviceId;
    @JoinColumn(name = "MARKET_ASSMNT_ID", referencedColumnName = "ID")
    @ManyToOne
    private PrmsMarketAssessment marketAssmntId;

    public PrmsMarketAssmntService() {
    }

    public PrmsMarketAssmntService(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAvailabilty() {
        return availabilty;
    }

    public void setAvailabilty(String availabilty) {
        this.availabilty = availabilty;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getUnitMeasure() {
        return unitMeasure;
    }

    public void setUnitMeasure(String unitMeasure) {
        this.unitMeasure = unitMeasure;
    }

    public PrmsServiceAndWorkReg getServiceId() {
        return serviceId;
    }

    public void setServiceId(PrmsServiceAndWorkReg serviceId) {
        this.serviceId = serviceId;
    }

    public PrmsMarketAssessment getMarketAssmntId() {
        return marketAssmntId;
    }

    public void setMarketAssmntId(PrmsMarketAssessment marketAssmntId) {
        this.marketAssmntId = marketAssmntId;
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
        if (!(object instanceof PrmsMarketAssmntService)) {
            return false;
        }
        PrmsMarketAssmntService other = (PrmsMarketAssmntService) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return serviceId.getServiceName();
    }

    public String getSourceOfAssessment() {
        return sourceOfAssessment;
    }

    public void setSourceOfAssessment(String sourceOfAssessment) {
        this.sourceOfAssessment = sourceOfAssessment;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getPurchaseType() {
        return purchaseType;
    }

    public void setPurchaseType(String purchaseType) {
        this.purchaseType = purchaseType;
    }

    public Date getFromYear() {
        return fromYear;
    }

    public void setFromYear(Date fromYear) {
        this.fromYear = fromYear;
    }

    public Date getToYear() {
        return toYear;
    }

    public void setToYear(Date toYear) {
        this.toYear = toYear;
    }
    

}
