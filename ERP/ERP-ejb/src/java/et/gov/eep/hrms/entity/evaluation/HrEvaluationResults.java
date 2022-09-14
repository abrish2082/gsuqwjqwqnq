/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.entity.evaluation;

import et.gov.eep.hrms.entity.employee.HrEmployees;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
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
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
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
@Table(name = "HR_EVALUATION_RESULTS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HrEvaluationResults.findAll", query = "SELECT h FROM HrEvaluationResults h WHERE h.status = 0"),
    @NamedQuery(name = "HrEvaluationResults.findById", query = "SELECT h FROM HrEvaluationResults h WHERE h.id = :id"),
    @NamedQuery(name = "HrEvaluationResults.findByEmployeeId", query = "SELECT h FROM HrEvaluationResults h WHERE h.empId =:empId ORDER BY H.evaluationDate DESC"),
    @NamedQuery(name = "HrEvaluationResults.findByName", query = "SELECT h FROM HrEvaluationResults h WHERE UPPER(h.empId.firstName) LIKE :firstName and h.status =0"),
    @NamedQuery(name = "HrEvaluationResults.findByEmpId", query = "SELECT h FROM HrEvaluationResults h WHERE UPPER(h.empId.empId) LIKE :empId and h.status =0"),
    @NamedQuery(name = "HrEvaluationResults.findByTwo", query = "SELECT h FROM HrEvaluationResults h WHERE UPPER(h.empId.firstName) LIKE :firstName and UPPER(h.empId.empId) LIKE :empId and h.status =0"),
    @NamedQuery(name = "HrEvaluationResults.checkLevel", query = "SELECT h FROM HrEvaluationLevels h WHERE h.minimumPoint >= :result and h.maximumPoint <= :result"),
    @NamedQuery(name = "HrEvaluationResults.findByEvaluationDate", query = "SELECT h FROM HrEvaluationResults h WHERE h.evaluationDate = :evaluationDate"),
    @NamedQuery(name = "HrEvaluationResults.findByRemark", query = "SELECT h FROM HrEvaluationResults h WHERE h.remark = :remark"),
    @NamedQuery(name = "HrEvaluationResults.findByStatus", query = "SELECT h FROM HrEvaluationResults h WHERE h.status = :status")})
public class HrEvaluationResults implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HR_EVALUATION_RESULTS_SEQ")
    @SequenceGenerator(name = "HR_EVALUATION_RESULTS_SEQ", sequenceName = "HR_EVALUATION_RESULTS_SEQ", allocationSize = 1)
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private Integer id;
    @Size(max = 20)
    @Column(name = "EVALUATION_DATE")
    private String evaluationDate;
    @Size(max = 400)
    @Column(name = "REMARK")
    private String remark;
    @Column(name = "STATUS")
    private Integer status;

    @OneToMany(mappedBy = "evaluationResultId", cascade = CascadeType.ALL)
    private List<HrEvaluationResultDetails> hrEvaluationResultDetailsList = new ArrayList<>();

    @JoinColumn(name = "EVALUATOR_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrEmployees evaluatorId;

    @JoinColumn(name = "EMP_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrEmployees empId;

    @JoinColumn(name = "SESSION_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrEvaluationSessions sessionId;

    public HrEvaluationResults() {
    }

    public HrEvaluationResults(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEvaluationDate() {
        return evaluationDate;
    }

    public void setEvaluationDate(String evaluationDate) {
        this.evaluationDate = evaluationDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @XmlTransient
    public List<HrEvaluationResultDetails> getHrEvaluationResultDetailsList() {
        if (hrEvaluationResultDetailsList == null) {
            hrEvaluationResultDetailsList = new ArrayList<>();
        }
        return hrEvaluationResultDetailsList;
    }

    public void setHrEvaluationResultDetailsList(List<HrEvaluationResultDetails> hrEvaluationResultDetailsList) {
        this.hrEvaluationResultDetailsList = hrEvaluationResultDetailsList;
    }

    public void addEvaResultDetail(HrEvaluationResultDetails hrEvaluationResultDetails) {
        if (hrEvaluationResultDetails.getEvaluationResultId() != this) {
            this.getHrEvaluationResultDetailsList().add(hrEvaluationResultDetails);
            hrEvaluationResultDetails.setEvaluationResultId(this);
        }
    }

    public HrEmployees getEvaluatorId() {
        return evaluatorId;
    }

    public void setEvaluatorId(HrEmployees evaluatorId) {
        this.evaluatorId = evaluatorId;
    }

    public HrEmployees getEmpId() {
        return empId;
    }

    public void setEmpId(HrEmployees empId) {
        this.empId = empId;
    }

    public HrEvaluationSessions getSessionId() {
        return sessionId;
    }

    public void setSessionId(HrEvaluationSessions sessionId) {
        this.sessionId = sessionId;
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
        if (!(object instanceof HrEvaluationResults)) {
            return false;
        }
        HrEvaluationResults other = (HrEvaluationResults) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.HrEvaluationResults[ id=" + id + " ]";
    }

}
