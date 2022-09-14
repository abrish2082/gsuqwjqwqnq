/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.mms.entity;

import et.gov.eep.prms.entity.PrmsPurchaseRequestDet;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author user
 */
@Entity
@Table(name = "MMS_SERVICE_REG")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MmsServiceReg.findAll", query = "SELECT m FROM MmsServiceReg m"),
    @NamedQuery(name = "MmsServiceReg.findByServiceId", query = "SELECT m FROM MmsServiceReg m WHERE m.serviceId = :serviceId"),
    @NamedQuery(name = "MmsServiceReg.findByType", query = "SELECT m FROM MmsServiceReg m WHERE m.type = :type"),
    @NamedQuery(name = "MmsServiceReg.findByServiceType", query = "SELECT DISTINCT m FROM MmsServiceReg m WHERE m.type IS NOT NULL"),
    @NamedQuery(name = "MmsServiceReg.findByNames", query = "SELECT m FROM MmsServiceReg m WHERE m.name = :nam"),
    @NamedQuery(name = "MmsServiceReg.findByRegistrationDate", query = "SELECT m FROM MmsServiceReg m WHERE m.registrationDate = :registrationDate"),
    @NamedQuery(name = "MmsServiceReg.findByServiceNo", query = "SELECT m FROM MmsServiceReg m WHERE m.serviceNo = :serviceNo"),
    @NamedQuery(name = "MmsServiceReg.findByName", query = "SELECT m FROM MmsServiceReg m WHERE m.name = :name"),
    @NamedQuery(name = "MmsServiceReg.findByDescription", query = "SELECT m FROM MmsServiceReg m WHERE m.description = :description"),
    @NamedQuery(name = "MmsServiceReg.findByServiceNameLike", query = "SELECT m FROM MmsServiceReg m WHERE m.name LIKE :name"),
    @NamedQuery(name = "MmsServiceReg.findBySpecificationRemark", query = "SELECT m FROM MmsServiceReg m WHERE m.specificationRemark = :specificationRemark"),
    @NamedQuery(name = "MmsServiceReg.findByPreparedBy", query = "SELECT m FROM MmsServiceReg m WHERE m.preparedBy = :preparedBy")})
public class MmsServiceReg implements Serializable {

   

    
    @OneToMany(mappedBy = "serviceId", fetch = FetchType.LAZY)
    private List<PrmsPurchaseRequestDet> prmsPurchaseRequestDetList;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MMS_SERVICE_RED_SEQ")
    @SequenceGenerator(name = "MMS_SERVICE_RED_SEQ", sequenceName = "MMS_SERVICE_RED_SEQ", allocationSize = 1)
    @Column(name = "SERVICE_ID")
    private BigDecimal serviceId;
    @Size(max = 20)
    @Column(name = "TYPE")
    private String type;
    @Size(max = 50)
    @Column(name = " SERVICE_TYPE")
    private String serviceType;
    @Column(name = "REGISTRATION_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date registrationDate;
    @Size(max = 20)
    @Column(name = "SERVICE_NO")
    private String serviceNo;
    @Size(max = 250)
    @Column(name = "NAME")
    private String name;
    @Size(max = 250)
    @Column(name = "DESCRIPTION")
    private String description;
    @Size(max = 250)
    @Column(name = "SPECIFICATION_REMARK")
    private String specificationRemark;
    @Size(max = 50)
    @Column(name = "PREPARED_BY")
    private String preparedBy;

    public MmsServiceReg() {
    }

    public MmsServiceReg(BigDecimal serviceId) {
        this.serviceId = serviceId;
    }

    public BigDecimal getServiceId() {
        return serviceId;
    }

    public void setServiceId(BigDecimal serviceId) {
        this.serviceId = serviceId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getServiceNo() {
        return serviceNo;
    }

    public void setServiceNo(String serviceNo) {
        this.serviceNo = serviceNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSpecificationRemark() {
        return specificationRemark;
    }

    public void setSpecificationRemark(String specificationRemark) {
        this.specificationRemark = specificationRemark;
    }

    public String getPreparedBy() {
        return preparedBy;
    }

    public void setPreparedBy(String preparedBy) {
        this.preparedBy = preparedBy;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (serviceId != null ? serviceId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MmsServiceReg)) {
            return false;
        }
        MmsServiceReg other = (MmsServiceReg) object;
        if ((this.serviceId == null && other.serviceId != null) || (this.serviceId != null && !this.serviceId.equals(other.serviceId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return name;
    }

    @XmlTransient
    public List<PrmsPurchaseRequestDet> getPrmsPurchaseRequestDetList() {
        return prmsPurchaseRequestDetList;
    }

    public void setPrmsPurchaseRequestDetList(List<PrmsPurchaseRequestDet> prmsPurchaseRequestDetList) {
        this.prmsPurchaseRequestDetList = prmsPurchaseRequestDetList;
    }

    

   
}
