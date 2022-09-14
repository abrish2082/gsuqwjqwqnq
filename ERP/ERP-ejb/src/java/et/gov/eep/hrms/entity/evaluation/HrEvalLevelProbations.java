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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Ob
 */
@Entity
@Table(name = "HR_EVAL_LEVEL_PROBATIONS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HrEvalLevelProbations.findAll", query = "SELECT h FROM HrEvalLevelProbations h"),
    @NamedQuery(name = "HrEvalLevelProbations.findById", query = "SELECT h FROM HrEvalLevelProbations h WHERE h.id = :id"),
    @NamedQuery(name = "HrEvalLevelProbations.findByEvaluationLevel", query = "SELECT h FROM HrEvalLevelProbations h WHERE h.evaluationLevel = :evaluationLevel"),
    @NamedQuery(name = "HrEvalLevelProbations.findByEvaluationLevelLike", query = "SELECT h FROM HrEvalLevelProbations h WHERE h.evaluationLevel Like :evaluationLevel"),
    @NamedQuery(name = "HrEvalLevelProbations.findByDescription", query = "SELECT h FROM HrEvalLevelProbations h WHERE h.description = :description"),
    @NamedQuery(name = "HrEvalLevelProbations.findByStatus", query = "SELECT h FROM HrEvalLevelProbations h WHERE h.status = :status")})
public class HrEvalLevelProbations implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HR_EVAL_LEVEL_PROBATION_SEQ")
    @SequenceGenerator(name = "HR_EVAL_LEVEL_PROBATION_SEQ", sequenceName = "HR_EVAL_LEVEL_PROBATION_SEQ", allocationSize = 1)
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "EVALUATION_LEVEL")
    private String evaluationLevel;
    @Size(max = 200)
    @Column(name = "DESCRIPTION")
    private String description;
    @Basic(optional = false)
    @NotNull
    @Column(name = "STATUS")
    private String status;

    public HrEvalLevelProbations() {
    }

    public HrEvalLevelProbations(Integer id) {
        this.id = id;
    }

    public HrEvalLevelProbations(Integer id, String evaluationLevel, String status) {
        this.id = id;
        this.evaluationLevel = evaluationLevel;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEvaluationLevel() {
        return evaluationLevel;
    }

    public void setEvaluationLevel(String evaluationLevel) {
        this.evaluationLevel = evaluationLevel;
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
        if (!(object instanceof HrEvalLevelProbations)) {
            return false;
        }
        HrEvalLevelProbations other = (HrEvalLevelProbations) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return id.toString();
    }
    
}
