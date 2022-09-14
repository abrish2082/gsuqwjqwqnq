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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Ob
 */
@Entity
@Table(name = "HR_EVA_RESULT_DETAIL_PROBATION")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HrEvaResultDetailProbation.findAll", query = "SELECT h FROM HrEvaResultDetailProbation h"),
    @NamedQuery(name = "HrEvaResultDetailProbation.findById", query = "SELECT h FROM HrEvaResultDetailProbation h WHERE h.id = :id"),
    @NamedQuery(name = "HrEvaResultDetailProbation.findByProbationResult", query = "SELECT h FROM HrEvaResultDetailProbation h WHERE h.probationResult = :probationResult")})
public class HrEvaResultDetailProbation implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HR_EVA_RESULT_DETAIL_PROB_SEQ")
    @SequenceGenerator(name = "HR_EVA_RESULT_DETAIL_PROB_SEQ", sequenceName = "HR_EVA_RESULT_DETAIL_PROB_SEQ", allocationSize = 1)
    @Basic(optional = false)
    @Column(name = "ID")
    private BigDecimal id;
    @Column(name = "PROBATION_RESULT")
    private String probationResult;
    @Column(name = "DESCRIPTION")
    private String description;

    @JoinColumn(name = "EVA_CRITERIA_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrEvalCriteriaProbations evaCriteriaId;

    @JoinColumn(name = "EVA_RESULT_ID", referencedColumnName = "ID")
    @ManyToOne(cascade = CascadeType.PERSIST)
    private HrEvalResultProbations evaResultId;

    public HrEvaResultDetailProbation() {
    }

    public HrEvaResultDetailProbation(BigDecimal id) {
        this.id = id;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getProbationResult() {
        return probationResult;
    }

    public void setProbationResult(String probationResult) {
        this.probationResult = probationResult;
    }

    public HrEvalCriteriaProbations getEvaCriteriaId() {
        return evaCriteriaId;
    }

    public void setEvaCriteriaId(HrEvalCriteriaProbations evaCriteriaId) {
        this.evaCriteriaId = evaCriteriaId;
    }

    public HrEvalResultProbations getEvaResultId() {
        return evaResultId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setEvaResultId(HrEvalResultProbations evaResultId) {
        this.evaResultId = evaResultId;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
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
        if (!(object instanceof HrEvaResultDetailProbation)) {
            return false;
        }
        HrEvaResultDetailProbation other = (HrEvaResultDetailProbation) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.HrEvaResultDetailProbation[ id=" + id + " ]";
    }

}
