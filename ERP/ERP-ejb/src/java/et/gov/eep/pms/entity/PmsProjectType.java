/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.pms.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Me
 */
@Entity
@Table(name = "PMS_PROJECT_TYPE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PmsProjectType.findAll", query = "SELECT p FROM PmsProjectType p"),
    @NamedQuery(name = "PmsProjectType.findByProjectTypeId", query = "SELECT p FROM PmsProjectType p WHERE p.projectTypeId = :projectTypeId"),
    @NamedQuery(name = "PmsProjectType.findByProjectTypeName", query = "SELECT p FROM PmsProjectType p WHERE p.projectTypeName = :projectTypeName")})
public class PmsProjectType implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "PROJECT_TYPE_ID")
    private BigDecimal projectTypeId;
    @Column(name = "PROJECT_TYPE_NAME")
    private String projectTypeName;
    @OneToMany(mappedBy = "projectTypeId")
    private List<PmsCreateProjects> pmsCreateProjectsList;

    public PmsProjectType() {
    }

    public PmsProjectType(BigDecimal projectTypeId) {
        this.projectTypeId = projectTypeId;
    }

    public BigDecimal getProjectTypeId() {
        return projectTypeId;
    }

    public void setProjectTypeId(BigDecimal projectTypeId) {
        this.projectTypeId = projectTypeId;
    }

    public String getProjectTypeName() {
        return projectTypeName;
    }

    public void setProjectTypeName(String projectTypeName) {
        this.projectTypeName = projectTypeName;
    }

    @XmlTransient
    public List<PmsCreateProjects> getPmsCreateProjectsList() {
        if(pmsCreateProjectsList==null){
            pmsCreateProjectsList=new ArrayList<>();
        }
        return pmsCreateProjectsList;
    }

    public void setPmsCreateProjectsList(List<PmsCreateProjects> pmsCreateProjectsList) {
        this.pmsCreateProjectsList = pmsCreateProjectsList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (projectTypeId != null ? projectTypeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PmsProjectType)) {
            return false;
        }
        PmsProjectType other = (PmsProjectType) object;
        if ((this.projectTypeId == null && other.projectTypeId != null) || (this.projectTypeId != null && !this.projectTypeId.equals(other.projectTypeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "javaapplication1.PmsProjectType[ projectTypeId=" + projectTypeId + " ]";
    }
    
}
