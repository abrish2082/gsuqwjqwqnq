/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.entity.training;

import java.io.Serializable;
import java.math.BigDecimal;
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
 * @author insa
 */
@Entity
@Table(name = "HR_TD_PSVC_RESULT_DETAILS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HrTdPsvcResultDetails.findAll", query = "SELECT h FROM HrTdPsvcResultDetails h"),
    @NamedQuery(name = "HrTdPsvcResultDetails.findById", query = "SELECT h FROM HrTdPsvcResultDetails h WHERE h.id = :id"),
    @NamedQuery(name = "HrTdPsvcResultDetails.findByResult", query = "SELECT h FROM HrTdPsvcResultDetails h WHERE h.result = :result"),
    @NamedQuery(name = "HrTdPsvcResultDetails.findByRemark", query = "SELECT h FROM HrTdPsvcResultDetails h WHERE h.remark = :remark")})
public class HrTdPsvcResultDetails implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HR_TD_PSVC_RESULT_DETAILS_SEQ")
    @SequenceGenerator(name = "HR_TD_PSVC_RESULT_DETAILS_SEQ", sequenceName = "HR_TD_PSVC_RESULT_DETAILS_SEQ", allocationSize = 1)
    @NotNull
    @Column(name = "ID")
    private BigDecimal id;
    @Column(name = "RESULT")
    private Integer result;
    @Size(max = 200)
    @Column(name = "DECISION")
    private String decision;
    @Size(max = 20)
    @Column(name = "REMARK")
    private String remark;
    @JoinColumn(name = "TRAINING_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrTdPsvcCourses trainingId;
    @JoinColumn(name = "TRAINEE_RESULT_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrTdPsvcResults traineeResultId;

    public HrTdPsvcResultDetails() {
    }

    public HrTdPsvcResultDetails(BigDecimal id) {
        this.id = id;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    public String getDecision() {
        return decision;
    }

    public void setDecision(String decision) {
        this.decision = decision;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public HrTdPsvcCourses getTrainingId() {
        return trainingId;
    }

    public void setTrainingId(HrTdPsvcCourses trainingId) {
        this.trainingId = trainingId;
    }

    public HrTdPsvcResults getTraineeResultId() {
        return traineeResultId;
    }

    public void setTraineeResultId(HrTdPsvcResults traineeResultId) {
        this.traineeResultId = traineeResultId;
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
        if (!(object instanceof HrTdPsvcResultDetails)) {
            return false;
        }
        HrTdPsvcResultDetails other = (HrTdPsvcResultDetails) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.eep.hrms.entity.training.HrTdPsvcResultDetails[ id=" + id + " ]";
    }

}
