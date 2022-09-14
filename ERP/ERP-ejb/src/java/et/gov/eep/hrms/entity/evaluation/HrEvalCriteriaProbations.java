/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.entity.evaluation;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Ob
 */
@Entity
@Table(name = "HR_EVAL_CRITERIA_PROBATIONS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HrEvalCriteriaProbations.findAll", query = "SELECT h FROM HrEvalCriteriaProbations h"),
    @NamedQuery(name = "HrEvalCriteriaProbations.findById", query = "SELECT h FROM HrEvalCriteriaProbations h WHERE h.id = :id"),
    @NamedQuery(name = "HrEvalCriteriaProbations.findByCriteria", query = "SELECT h FROM HrEvalCriteriaProbations h WHERE h.criteriaName = :criteriaName"),
    @NamedQuery(name = "HrEvalCriteriaProbations.findByCriteriaName", query = "SELECT h FROM HrEvalCriteriaProbations h WHERE UPPER(h.criteriaName) LIKE :criteriaName"),
    @NamedQuery(name = "HrEvalCriteriaProbations.findByDescription", query = "SELECT h FROM HrEvalCriteriaProbations h WHERE h.description = :description"),
    @NamedQuery(name = "HrEvalCriteriaProbations.findByStatus", query = "SELECT h FROM HrEvalCriteriaProbations h WHERE h.status = :status")})
public class HrEvalCriteriaProbations implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HR_EVAL_CRITERIA_PROBATION_SEQ")
    @SequenceGenerator(name = "HR_EVAL_CRITERIA_PROBATION_SEQ", sequenceName = "HR_EVAL_CRITERIA_PROBATION_SEQ", allocationSize = 1)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "CRITERIA_NAME")
    private String criteriaName;
    @Size(max = 200)
    @Column(name = "DESCRIPTION")
    private String description;
    @Size(max = 20)
    @Column(name = "STATUS")
    private String status;

    public HrEvalCriteriaProbations() {
    }

    public HrEvalCriteriaProbations(Integer id) {
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
        if (!(object instanceof HrEvalCriteriaProbations)) {
            return false;
        }
        HrEvalCriteriaProbations other = (HrEvalCriteriaProbations) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Transient
    String result;
    @Transient
    String reason;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getReason() {
        if (reason == null) {
            reason = "Write some description ...";
        }
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @Override
    public String toString() {
        return criteriaName;
    }

}
