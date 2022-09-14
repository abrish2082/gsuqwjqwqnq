/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.entity.evaluation;

import et.gov.eep.hrms.entity.lookup.HrLuEvaluationCategory;
import java.io.Serializable;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author INSA
 */
@Entity
@Table(name = "HR_EVALUATION_CRITERIA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HrEvaluationCriteria.findAll", query = "SELECT h FROM HrEvaluationCriteria h WHERE h.status = '1'"),
    @NamedQuery(name = "HrEvaluationCriteria.findById", query = "SELECT h FROM HrEvaluationCriteria h WHERE h.status = '1' and h.id = :id"),
    @NamedQuery(name = "HrEvaluationCriteria.findByCriteriaName", query = "SELECT h FROM HrEvaluationCriteria h WHERE h.criteriaName = :criteriaName"),
    @NamedQuery(name = "HrEvaluationCriteria.findByCriteriaNameLike", query = "SELECT h FROM HrEvaluationCriteria h WHERE UPPER(h.criteriaName) LIKE :criteriaName"),
    @NamedQuery(name = "HrEvaluationCriteria.findByDescription", query = "SELECT h FROM HrEvaluationCriteria h WHERE h.description = :description"),
    @NamedQuery(name = "HrEvaluationCriteria.findByWeight", query = "SELECT h FROM HrEvaluationCriteria h WHERE h.weight = :weight"),
    @NamedQuery(name = "HrEvaluationCriteria.findByStatus", query = "SELECT h FROM HrEvaluationCriteria h WHERE h.status = :status")})
public class HrEvaluationCriteria implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HR_EVALUATION_CRITERIA_SEQ")
    @SequenceGenerator(name = "HR_EVALUATION_CRITERIA_SEQ", sequenceName = "HR_EVALUATION_CRITERIA_SEQ", allocationSize = 1)
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private Integer id;
    @Size(max = 200)
    @Column(name = "CRITERIA_NAME")
    private String criteriaName;
    @Size(max = 200)
    @Column(name = "DESCRIPTION")
    private String description;
    @Column(name = "WEIGHT")
    private Integer weight;
    @Size(max = 20)
    @Column(name = "STATUS")
    private String status;
    @JoinColumn(name = "CATEGORY_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrLuEvaluationCategory categoryId;

    public HrEvaluationCriteria() {
    }

    public HrEvaluationCriteria(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCriteriaName() {
        return criteriaName;
    }

    public void setCriteriaName(String criteriaName) {
        this.criteriaName = criteriaName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public HrLuEvaluationCategory getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(HrLuEvaluationCategory categoryId) {
        this.categoryId = categoryId;
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
        if (!(object instanceof HrEvaluationCriteria)) {
            return false;
        }
        HrEvaluationCriteria other = (HrEvaluationCriteria) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return criteriaName;
    }

}
