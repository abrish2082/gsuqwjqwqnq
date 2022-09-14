/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.entity.lookup;

import et.gov.eep.hrms.entity.evaluation.HrEvaluationCriteria;
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
 * @author INSA
 */
@Entity
@Table(name = "HR_LU_EVALUATION_CATEGORY")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HrLuEvaluationCategory.findAll", query = "SELECT h FROM HrLuEvaluationCategory h"),
    @NamedQuery(name = "HrLuEvaluationCategory.findById", query = "SELECT h FROM HrLuEvaluationCategory h WHERE h.id = :id"),
    @NamedQuery(name = "HrLuEvaluationCategory.findByCategoryName", query = "SELECT h FROM HrLuEvaluationCategory h WHERE h.categoryName = :categoryName"),
    @NamedQuery(name = "HrLuEvaluationCategory.findByDescription", query = "SELECT h FROM HrLuEvaluationCategory h WHERE h.description = :description")})
public class HrLuEvaluationCategory implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private BigDecimal id;
    @Size(max = 100)
    @Column(name = "CATEGORY_NAME")
    private String categoryName;
    @Size(max = 100)
    @Column(name = "DESCRIPTION")
    private String description;
    @OneToMany(mappedBy = "categoryId")
    private List<HrEvaluationCriteria> hrEvaluationCriteriaList;

    public HrLuEvaluationCategory() {
    }

    public HrLuEvaluationCategory(BigDecimal id) {
        this.id = id;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @XmlTransient
    public List<HrEvaluationCriteria> getHrEvaluationCriteriaList() {
        return hrEvaluationCriteriaList;
    }

    public void setHrEvaluationCriteriaList(List<HrEvaluationCriteria> hrEvaluationCriteriaList) {
        this.hrEvaluationCriteriaList = hrEvaluationCriteriaList;
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
        if (!(object instanceof HrLuEvaluationCategory)) {
            return false;
        }
        HrLuEvaluationCategory other = (HrLuEvaluationCategory) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return categoryName;
    }
    
}
