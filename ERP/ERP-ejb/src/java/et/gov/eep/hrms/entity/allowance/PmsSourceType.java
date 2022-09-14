/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.entity.allowance;

import et.gov.eep.pms.entity.PmsCreateProjects;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author user
 */
@Entity
@Table(name = "PMS_SOURCE_TYPE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PmsSourceType.findAll", query = "SELECT p FROM PmsSourceType p"),
    @NamedQuery(name = "PmsSourceType.findBySourceTypeId", query = "SELECT p FROM PmsSourceType p WHERE p.sourceTypeId = :sourceTypeId"),
    @NamedQuery(name = "PmsSourceType.findBySourceTypeName", query = "SELECT p FROM PmsSourceType p WHERE p.sourceTypeName = :sourceTypeName")})
public class PmsSourceType implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SOURCE_TYPE_ID")
    private BigDecimal sourceTypeId;
    @Size(max = 100)
    @Column(name = "SOURCE_TYPE_NAME")
    private String sourceTypeName;
    @OneToMany(mappedBy = "sourceTypeId")
    private List<PmsCreateProjects> pmsCreateProjectsList;

    public PmsSourceType() {
    }

    public PmsSourceType(BigDecimal sourceTypeId) {
        this.sourceTypeId = sourceTypeId;
    }

    public BigDecimal getSourceTypeId() {
        return sourceTypeId;
    }

    public void setSourceTypeId(BigDecimal sourceTypeId) {
        this.sourceTypeId = sourceTypeId;
    }

    public String getSourceTypeName() {
        return sourceTypeName;
    }

    public void setSourceTypeName(String sourceTypeName) {
        this.sourceTypeName = sourceTypeName;
    }

    @XmlTransient
    public List<PmsCreateProjects> getPmsCreateProjectsList() {
        return pmsCreateProjectsList;
    }

    public void setPmsCreateProjectsList(List<PmsCreateProjects> pmsCreateProjectsList) {
        this.pmsCreateProjectsList = pmsCreateProjectsList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sourceTypeId != null ? sourceTypeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PmsSourceType)) {
            return false;
        }
        PmsSourceType other = (PmsSourceType) object;
        if ((this.sourceTypeId == null && other.sourceTypeId != null) || (this.sourceTypeId != null && !this.sourceTypeId.equals(other.sourceTypeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.eep.hrms.entity.allowance.PmsSourceType[ sourceTypeId=" + sourceTypeId + " ]";
    }
    
}
