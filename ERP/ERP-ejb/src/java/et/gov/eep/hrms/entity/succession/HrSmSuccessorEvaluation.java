/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.entity.succession;

import et.gov.eep.commonApplications.entity.WfHrProcessed;
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
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Behailu
 */
@Entity
@Table(name = "HR_SM_SUCCESSOR_EVALUATION")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HrSmSuccessorEvaluation.findAll", query = "SELECT h FROM HrSmSuccessorEvaluation h"),
    @NamedQuery(name = "HrSmSuccessorEvaluation.findAllEvaluated", query = "SELECT h FROM HrSmSuccessorEvaluation h WHERE h.status =1"),
    @NamedQuery(name = "HrSmSuccessorEvaluation.findAllToBeEvaluated", query = "SELECT h FROM HrSmSuccessorEvaluation h WHERE h.status =0"),
    @NamedQuery(name = "HrSmSuccessorEvaluation.findById", query = "SELECT h FROM HrSmSuccessorEvaluation h WHERE h.id = :id"),
    @NamedQuery(name = "HrSmSuccessorEvaluation.findSuccesorBykmpId", query = "SELECT h FROM HrSmSuccessorEvaluation h WHERE h.status=0 AND h.kmpId.id = :kmpId"),
    @NamedQuery(name = "HrSmSuccessorEvaluation.findSuccesorBykmpIdToAprove", query = "SELECT h FROM HrSmSuccessorEvaluation h WHERE h.status=1 AND h.kmpId.id = :kmpId"),
    @NamedQuery(name = "HrSmSuccessorEvaluation.findSuccesorByEmpId", query = "SELECT h FROM HrSmSuccessorEvaluation h WHERE h.empId.empId = :empId"),
    @NamedQuery(name = "HrSmSuccessorEvaluation.findByEvaluationDate", query = "SELECT h FROM HrSmSuccessorEvaluation h WHERE h.evaluationDate = :evaluationDate"),
    @NamedQuery(name = "HrSmSuccessorEvaluation.findByRemark", query = "SELECT h FROM HrSmSuccessorEvaluation h WHERE h.remark = :remark"),
    @NamedQuery(name = "HrSmSuccessorEvaluation.findByStatus", query = "SELECT h FROM HrSmSuccessorEvaluation h WHERE h.status = :status"),
    @NamedQuery(name = "HrSmSuccessorEvaluation.findBykmpId", query = "SELECT h FROM HrSmSuccessorEvaluation h WHERE UPPER(h.kmpId.jobId.jobTitle) LIKE :jobTitle"),
    @NamedQuery(name = "HrSmSuccessorEvaluation.findBykmpId1", query = "SELECT h FROM HrSmSuccessorEvaluation h WHERE h.kmpId.jobId.jobTitle = :jobTitle1"),
    @NamedQuery(name = "HrSmSuccessorEvaluation.findByDuplicate", query = "SELECT h FROM HrSmSuccessorEvaluation h WHERE h.empId = :empId AND h.kmpId = :kmpId"),
    @NamedQuery(name = "HrSmSuccessorEvaluation.findByEmpIdLike", query = "SELECT h FROM HrEmployees h WHERE UPPER(h.empId) LIKE :empId"),
    @NamedQuery(name = "HrSmSuccessorEvaluation.findByEmpId", query = "SELECT h FROM HrEmployees h WHERE h.empId = :empId"),
    @NamedQuery(name = "HrSmSuccessorEvaluation.findByFirstName", query = "SELECT h FROM HrEmployees h WHERE h.firstName = :firstName"),
    @NamedQuery(name = "HrSmSuccessorEvaluation.findBykmpIdForApproval", query = "SELECT h FROM HrSmSuccessorEvaluation h WHERE h.evaluationDate IS NOT NULL AND h.remark IS NOT NULL AND UPPER(h.kmpId.jobId.jobTitle) LIKE :jobTitleforAproval"),
    @NamedQuery(name = "HrSmSuccessorEvaluation.findByname", query = "SELECT h FROM HrSmSuccessorEvaluation h WHERE UPPER(h.empId.firstName) LIKE :firstName"),
    @NamedQuery(name = "HrSmSuccessorEvaluation.findBynameForApproval", query = "SELECT h FROM HrSmSuccessorEvaluation h WHERE h.evaluationDate IS NOT NULL AND h.remark IS NOT NULL AND UPPER(h.empId.firstName) LIKE :firstNameForapproval")})
public class HrSmSuccessorEvaluation implements Serializable {

    @Column(name = "STATUS")
    private Integer status;
    @OneToMany(mappedBy = "successorEvaluationId", cascade = CascadeType.ALL)
    private List<HrSmSuccessionPlans> hrSmSuccessionPlansList = new ArrayList<>();
    @OneToMany(mappedBy = "successionEvaluationId", cascade = CascadeType.ALL)
    private List<WfHrProcessed> WfHrProcessedList;
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HR_SM_SUCCESSOR_EVALUATION_SEQ")
    @SequenceGenerator(name = "HR_SM_SUCCESSOR_EVALUATION_SEQ", sequenceName = "HR_SM_SUCCESSOR_EVALUATION_SEQ", allocationSize = 1)
    private Integer id;
    @Size(max = 20)
    @Column(name = "EVALUATION_DATE")
    private String evaluationDate;
    @Column(name = "APPROVED_DATE")
    private String approvedDate;
    @Size(max = 20)
    @Column(name = "REMARK")
    private String remark;
    @JoinColumn(name = "EVALUATOR_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrEmployees evaluatorId;
    @JoinColumn(name = "EMP_ID", referencedColumnName = "ID")
    @OneToOne
    private HrEmployees empId;
    @JoinColumn(name = "KMP_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrSmKmp kmpId;

    public HrSmSuccessorEvaluation() {
    }

    public HrSmSuccessorEvaluation(Integer id) {
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

    public String getApprovedDate() {
        return approvedDate;
    }

    public void setApprovedDate(String approvedDate) {
        this.approvedDate = approvedDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public HrSmKmp getKmpId() {
        return kmpId;
    }

    public void setKmpId(HrSmKmp kmpId) {
        this.kmpId = kmpId;
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
        if (!(object instanceof HrSmSuccessorEvaluation)) {
            return false;
        }
        HrSmSuccessorEvaluation other = (HrSmSuccessorEvaluation) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return id.toString();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @XmlTransient
    public List<HrSmSuccessionPlans> getHrSmSuccessionPlansList() {
        return hrSmSuccessionPlansList;
    }

    public void setHrSmSuccessionPlansList(List<HrSmSuccessionPlans> hrSmSuccessionPlansList) {
        this.hrSmSuccessionPlansList = hrSmSuccessionPlansList;
    }

    @XmlTransient

    public List<WfHrProcessed> getWfHrProcessedList() {
        return WfHrProcessedList;
    }

    public void setWfHrProcessedList(List<WfHrProcessed> WfHrProcessedList) {
        this.WfHrProcessedList = WfHrProcessedList;
    }
}
