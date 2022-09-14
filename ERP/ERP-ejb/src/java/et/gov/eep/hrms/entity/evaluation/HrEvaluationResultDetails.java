/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.entity.evaluation;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
@Table(name = "HR_EVALUATION_RESULT_DETAILS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HrEvaluationResultDetails.findAll", query = "SELECT h FROM HrEvaluationResultDetails h"),
    @NamedQuery(name = "HrEvaluationResultDetails.findById", query = "SELECT h FROM HrEvaluationResultDetails h WHERE h.id = :id"),
    @NamedQuery(name = "HrEvaluationResultDetails.findByEvaluationResult", query = "SELECT h FROM HrEvaluationResultDetails h WHERE h.evaluationResult = :evaluationResult")})
public class HrEvaluationResultDetails implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HR_EVALUATION_RESULTDETAIL_SEQ")
    @SequenceGenerator(name = "HR_EVALUATION_RESULTDETAIL_SEQ", sequenceName = "HR_EVALUATION_RESULTDETAIL_SEQ", allocationSize = 1)
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private BigDecimal id;
    @Column(name = "EVALUATION_RESULT")
    private double evaluationResult;
    @Column(name = "EVALUATION_LEVEL")
    private String evaluationLevel;
    @Column(name = "EVALUATION_VALUE")
    private double evaluationValue;
    @Size(max = 400)
    @Column(name = "REASOn")
    private String reason;

    @JoinColumn(name = "EVALUATION_CRITERIA_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrEvaluationCriteria evaluationCriteriaId;

    @JoinColumn(name = "EVALUATION_RESULT_ID", referencedColumnName = "ID")
    @ManyToOne(cascade = CascadeType.ALL)
    private HrEvaluationResults evaluationResultId;

    public HrEvaluationResultDetails() {
    }

    public HrEvaluationResultDetails(BigDecimal id) {
        this.id = id;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public double getEvaluationResult() {
        return evaluationResult;
    }

    public void setEvaluationResult(double evaluationResult) {
        this.evaluationResult = evaluationResult;
    }

    public String getEvaluationLevel() {
        return evaluationLevel;
    }

    public void setEvaluationLevel(String evaluationLevel) {
        this.evaluationLevel = evaluationLevel;
    }

    public double getEvaluationValue() {
        return evaluationValue;
    }

    public void setEvaluationValue(double evaluationValue) {
        this.evaluationValue = evaluationValue;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public HrEvaluationCriteria getEvaluationCriteriaId() {
        return evaluationCriteriaId;
    }

    public void setEvaluationCriteriaId(HrEvaluationCriteria evaluationCriteriaId) {
        this.evaluationCriteriaId = evaluationCriteriaId;
    }

    public HrEvaluationResults getEvaluationResultId() {
        return evaluationResultId;
    }

    public void setEvaluationResultId(HrEvaluationResults evaluationResultId) {
        this.evaluationResultId = evaluationResultId;
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
        if (!(object instanceof HrEvaluationResultDetails)) {
            return false;
        }
        HrEvaluationResultDetails other = (HrEvaluationResultDetails) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.HrEvaluationResultDetails[ id=" + id + " ]";
    }

}
