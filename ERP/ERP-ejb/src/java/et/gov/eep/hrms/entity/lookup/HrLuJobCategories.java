/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.entity.lookup;

import et.gov.eep.hrms.entity.lookup.HrLuJobLevels;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
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
 * @author Benin
 */
@Entity
@Table(name = "HR_LU_JOB_CATEGORIES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HrLuJobCategories.findAll", query = "SELECT h FROM HrLuJobCategories h"),
    @NamedQuery(name = "HrLuJobCategories.findById", query = "SELECT h FROM HrLuJobCategories h WHERE h.id = :id"),
    @NamedQuery(name = "HrLuJobCategories.findByJobCategory", query = "SELECT h FROM HrLuJobCategories h WHERE h.jobCategory = :jobCategory"),
    @NamedQuery(name = "HrLuJobCategories.findByDescription", query = "SELECT h FROM HrLuJobCategories h WHERE h.description = :description")})
public class HrLuJobCategories implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HR_LU_JOB_CATEGORIES_SEQ")
    @SequenceGenerator(name = "HR_LU_JOB_CATEGORIES_SEQ", sequenceName = "HR_LU_JOB_CATEGORIES_SEQ", allocationSize = 1)
    @Basic(optional = false)
    @Column(name = "ID")
    private BigDecimal id;
    @Column(name = "JOB_CATEGORY")
    private String jobCategory;
    @Column(name = "DESCRIPTION")
    private String description;
    
    public HrLuJobCategories() {
    }

    public HrLuJobCategories(BigDecimal id) {
        this.id = id;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getJobCategory() {
        return jobCategory;
    }

    public void setJobCategory(String jobCategory) {
        this.jobCategory = jobCategory;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
        if (!(object instanceof HrLuJobCategories)) {
            return false;
        }
        HrLuJobCategories other = (HrLuJobCategories) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.eep.hrms.entity.employee.HrLuJobCategories[ id=" + id + " ]";
    }
    
}
