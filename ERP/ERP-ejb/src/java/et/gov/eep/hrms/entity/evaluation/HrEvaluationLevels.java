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
 * @author INSA
 */
@Entity
@Table(name = "HR_EVALUATION_LEVELS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HrEvaluationLevels.findAll", query = "SELECT h FROM HrEvaluationLevels h"),
    @NamedQuery(name = "HrEvaluationLevels.findById", query = "SELECT h FROM HrEvaluationLevels h WHERE h.id = :id"),
    @NamedQuery(name = "HrEvaluationLevels.findByEvaluationLevel", query = "SELECT h FROM HrEvaluationLevels h WHERE h.evaluationLevel = :evaluationLevel"),
    @NamedQuery(name = "HrEvaluationLevels.findByEvaluationPoint", query = "SELECT h FROM HrEvaluationLevels h WHERE h.minimumPoint = :minimumPoint"),
    @NamedQuery(name = "HrEvaluationLevels.findByDescription", query = "SELECT h FROM HrEvaluationLevels h WHERE h.description = :description"),
    @NamedQuery(name = "HrEvaluationLevels.findByStatus", query = "SELECT h FROM HrEvaluationLevels h WHERE h.status = :status")})

public class HrEvaluationLevels implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HR_EVALUATION_LEVELS_SEQ")
    @SequenceGenerator(name = "HR_EVALUATION_LEVELS_SEQ", sequenceName = "HR_EVALUATION_LEVELS_SEQ", allocationSize = 1)
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private Integer id;
    @Size(max = 100)
    @Column(name = "EVALUATION_LEVEL")
    private String evaluationLevel;
    @Column(name = "MINIMUM_POINT")
    private Integer minimumPoint;
    @Column(name = "MAXIMUM_POINT")
    private Integer maximumPoint;
    @Size(max = 200)
    @Column(name = "DESCRIPTION")
    private String description;
    @Size(max = 20)
    @Column(name = "STATUS")
    private String status;

    public HrEvaluationLevels() {
    }

    public HrEvaluationLevels(Integer id) {
        this.id = id;
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

    public Integer getMinimumPoint() {
        return minimumPoint;
    }

    public void setMinimumPoint(Integer minimumPoint) {
        this.minimumPoint = minimumPoint;
    }

    public Integer getMaximumPoint() {
        return maximumPoint;
    }

    public void setMaximumPoint(Integer maximumPoint) {
        this.maximumPoint = maximumPoint;
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
        if (!(object instanceof HrEvaluationLevels)) {
            return false;
        }
        HrEvaluationLevels other = (HrEvaluationLevels) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return evaluationLevel;
    }

}
