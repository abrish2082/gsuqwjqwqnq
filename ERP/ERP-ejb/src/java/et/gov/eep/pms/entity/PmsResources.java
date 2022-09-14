/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.pms.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
@Table(name = "PMS_RESOURCES", catalog = "", schema = "ERP")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PmsResources.findAll", query = "SELECT p FROM PmsResources p"),
    @NamedQuery(name = "PmsResources.findByResourceId", query = "SELECT p FROM PmsResources p WHERE p.resourceId = :resourceId"),
    @NamedQuery(name = "PmsResources.findByResourceDept", query = "SELECT p FROM PmsResources p WHERE p.resourceDept = :resourceDept"),
    @NamedQuery(name = "PmsResources.findByResourceName", query = "SELECT p FROM PmsResources p WHERE p.resourceName = :resourceName"),
    @NamedQuery(name = "PmsResources.findByResourceType", query = "SELECT p FROM PmsResources p WHERE p.resourceType = :resourceType"),
    @NamedQuery(name = "PmsResources.findByQuantity", query = "SELECT p FROM PmsResources p WHERE p.quantity = :quantity"),
    @NamedQuery(name = "PmsResources.findByAvailablity", query = "SELECT p FROM PmsResources p WHERE p.availablity = :availablity"),
    @NamedQuery(name = "PmsResources.findByUniteCost", query = "SELECT p FROM PmsResources p WHERE p.uniteCost = :uniteCost"),
    @NamedQuery(name = "PmsResources.findByRemark", query = "SELECT p FROM PmsResources p WHERE p.remark = :remark"),
    @NamedQuery(name = "PmsResources.findByProjectType", query = "SELECT p FROM PmsResources p WHERE p.projectType = :projectType"),
    @NamedQuery(name = "PmsResources.findByJobType", query = "SELECT p FROM PmsResources p WHERE p.jobType = :jobType"),
    @NamedQuery(name = "PmsResources.findByDepId", query = "SELECT p FROM PmsResources p WHERE p.depId = :depId")})
public class PmsResources implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "RESOURCE_ID", nullable = false, precision = 38, scale = 0)
    private BigDecimal resourceId;
    @Column(name = "RESOURCE_DEPT")
    private BigInteger resourceDept;
    @Size(max = 100)
    @Column(name = "RESOURCE_NAME", length = 100)
    private String resourceName;
    @Size(max = 100)
    @Column(name = "RESOURCE_TYPE", length = 100)
    private String resourceType;
    @Column(name = "QUANTITY")
    private Integer quantity;
    @Size(max = 10)
    @Column(name = "AVAILABLITY", length = 10)
    private String availablity;
    @Column(name = "UNITE_COST")
    private Serializable uniteCost;
    @Size(max = 100)
    @Column(name = "REMARK", length = 100)
    private String remark;
    @Column(name = "PROJECT_TYPE")
    private BigInteger projectType;
    @Column(name = "JOB_TYPE")
    private BigInteger jobType;
    @Column(name = "DEP_ID")
    private BigInteger depId;

    public PmsResources() {
    }

    public PmsResources(BigDecimal resourceId) {
        this.resourceId = resourceId;
    }

    public BigDecimal getResourceId() {
        return resourceId;
    }

    public void setResourceId(BigDecimal resourceId) {
        this.resourceId = resourceId;
    }

    public BigInteger getResourceDept() {
        return resourceDept;
    }

    public void setResourceDept(BigInteger resourceDept) {
        this.resourceDept = resourceDept;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getAvailablity() {
        return availablity;
    }

    public void setAvailablity(String availablity) {
        this.availablity = availablity;
    }

    public Serializable getUniteCost() {
        return uniteCost;
    }

    public void setUniteCost(Serializable uniteCost) {
        this.uniteCost = uniteCost;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public BigInteger getProjectType() {
        return projectType;
    }

    public void setProjectType(BigInteger projectType) {
        this.projectType = projectType;
    }

    public BigInteger getJobType() {
        return jobType;
    }

    public void setJobType(BigInteger jobType) {
        this.jobType = jobType;
    }

    public BigInteger getDepId() {
        return depId;
    }

    public void setDepId(BigInteger depId) {
        this.depId = depId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (resourceId != null ? resourceId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PmsResources)) {
            return false;
        }
        PmsResources other = (PmsResources) object;
        if ((this.resourceId == null && other.resourceId != null) || (this.resourceId != null && !this.resourceId.equals(other.resourceId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.eep.pms.entity.PmsResources[ resourceId=" + resourceId + " ]";
    }
    
}
