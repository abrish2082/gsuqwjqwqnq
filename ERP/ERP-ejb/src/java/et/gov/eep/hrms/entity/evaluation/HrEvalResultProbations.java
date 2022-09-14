/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.entity.evaluation;

import et.gov.eep.commonApplications.entity.WfHrProcessed;
import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.entity.organization.HrDepartments;
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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Ob
 */
@Entity
@Table(name = "HR_EVAL_RESULT_PROBATIONS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HrEvalResultProbations.findAll", query = "SELECT h FROM HrEvalResultProbations h WHERE h.status =0 ORDER BY h.evaluationDate DESC"),
    @NamedQuery(name = "HrEvalResultProbations.findById", query = "SELECT h FROM HrEvalResultProbations h WHERE h.id = :id"),

    @NamedQuery(name = "HrEvalResultProbations.findByName", query = "SELECT h FROM HrEvalResultProbations h WHERE UPPER(h.empId.firstName) LIKE :firstName"),
    @NamedQuery(name = "HrEvalResultProbations.findEmpName", query = "SELECT h FROM HrEmployees h WHERE UPPER(h.firstName) LIKE :firstName and h.empStatus = 6"),
    @NamedQuery(name = "HrEvalResultProbations.findEmployee", query = "SELECT h FROM HrEmployees h WHERE h.deptId.depId = :deptId and h.empStatus = 6"),
    @NamedQuery(name = "HrEvalResultProbations.findByDepName", query = "SELECT h FROM HrEvalResultProbations h WHERE h.deptId.depName = :depName and h.empId.empStatus = 6"),
    @NamedQuery(name = "HrEvalResultProbations.findByEmpId", query = "SELECT h FROM HrEvalResultProbations h WHERE UPPER(h.empId.empId) LIKE :empId and h.status =0 ORDER BY h.evaluationDate DESC"),
    @NamedQuery(name = "HrEvalResultProbations.findByFName", query = "SELECT h FROM HrEvalResultProbations h WHERE UPPER(h.empId.firstName) LIKE :firstName and h.status =0 ORDER BY h.evaluationDate DESC"),
    @NamedQuery(name = "HrEvalResultProbations.findByEmpIdAndName", query = "SELECT h FROM HrEvalResultProbations h WHERE UPPER(h.empId.empId) LIKE :empId AND UPPER(h.empId.firstName) LIKE :firstName and h.status =0 ORDER BY h.evaluationDate DESC"),

    @NamedQuery(name = "HrEvalResultProbations.findByEvaluationDate", query = "SELECT h FROM HrEvalResultProbations h WHERE h.evaluationDate = :evaluationDate"),
    @NamedQuery(name = "HrEvalResultProbations.findByRecommendation", query = "SELECT h FROM HrEvalResultProbations h WHERE h.empId.firstName = :firstName and h.recommendation = :recommendation"),
    @NamedQuery(name = "HrEvalResultProbations.findByRecommendationLike", query = "SELECT h FROM HrEvalResultProbations h WHERE h.recommendation Like :recommendation"),
    @NamedQuery(name = "HrEvalResultProbations.findByPrepared", query = "SELECT h FROM HrEvalResultProbations h WHERE h.status = 0 ORDER BY h.evaluationDate DESC"),
    @NamedQuery(name = "HrEvalResultProbations.findByRemark", query = "SELECT h FROM HrEvalResultProbations h WHERE h.remark = :remark")})
public class HrEvalResultProbations implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HR_EVAL_RESULT_PROBATION_SEQ")
    @SequenceGenerator(name = "HR_EVAL_RESULT_PROBATION_SEQ", sequenceName = "HR_EVAL_RESULT_PROBATION_SEQ", allocationSize = 1)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "EVALUATION_DATE")
    private String evaluationDate;
    @Column(name = "RECOMMENDATION")
    private String recommendation;
    @Column(name = "STATUS")
    private Integer status;
    @Column(name = "REMARK")
    private String remark;

    @OneToMany(mappedBy = "evaResultId", cascade = CascadeType.ALL)
    private List<HrEvaResultDetailProbation> hrEvaResultDetailProbationList = new ArrayList<>();

    @JoinColumn(name = "EMP_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrEmployees empId;

    @JoinColumn(name = "DEPT_ID", referencedColumnName = "DEP_ID")
    @ManyToOne
    private HrDepartments deptId;

    @JoinColumn(name = "EVALUATOR_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrEmployees evaluatorId;

    @OneToMany(mappedBy = "probationId", cascade = CascadeType.ALL)
    private List<WfHrProcessed> wfHrProcessedList = new ArrayList<>();

    public HrEvalResultProbations() {
    }

    public HrEvalResultProbations(Integer id) {
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

    public String getRecommendation() {
        return recommendation;
    }

    public void setRecommendation(String recommendation) {
        this.recommendation = recommendation;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @XmlTransient
    public List<HrEvaResultDetailProbation> getHrEvaResultDetailProbationList() {
        if (hrEvaResultDetailProbationList == null) {
            hrEvaResultDetailProbationList = new ArrayList<>();
        }
        return hrEvaResultDetailProbationList;
    }

    public void setHrEvaResultDetailProbationList(List<HrEvaResultDetailProbation> hrEvaResultDetailProbationList) {
        this.hrEvaResultDetailProbationList = hrEvaResultDetailProbationList;
    }

    public void addProbationDetail(HrEvaResultDetailProbation hrEvaResultDetailProbation) {
        if (hrEvaResultDetailProbation.getEvaResultId() != this) {
            this.getHrEvaResultDetailProbationList().add(hrEvaResultDetailProbation);
            hrEvaResultDetailProbation.setEvaResultId(this);
        }
    }

    public HrEmployees getEmpId() {
        return empId;
    }

    public void setEmpId(HrEmployees empId) {
        this.empId = empId;
    }

    public HrDepartments getDeptId() {
        return deptId;
    }

    public void setDeptId(HrDepartments deptId) {
        this.deptId = deptId;
    }

    public HrEmployees getEvaluatorId() {
        return evaluatorId;
    }

    public void setEvaluatorId(HrEmployees evaluatorId) {
        this.evaluatorId = evaluatorId;
    }

    public List<WfHrProcessed> getWfHrProcessedList() {
        return wfHrProcessedList;
    }

    public void setWfHrProcessedList(List<WfHrProcessed> wfHrProcessedList) {
        this.wfHrProcessedList = wfHrProcessedList;
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
        if (!(object instanceof HrEvalResultProbations)) {
            return false;
        }
        HrEvalResultProbations other = (HrEvalResultProbations) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return recommendation;
    }

}
