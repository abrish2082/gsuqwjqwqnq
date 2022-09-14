/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.entity.succession;

import et.gov.eep.commonApplications.entity.WfHrProcessed;
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
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author insa
 */
@Entity
@Table(name = "HR_SM_SUCCESSION_PLANS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HrSmSuccessionPlans.findAll", query = "SELECT h FROM HrSmSuccessionPlans h"),
    @NamedQuery(name = "HrSmSuccessionPlans.findById", query = "SELECT h FROM HrSmSuccessionPlans h WHERE h.id = :id"),
    @NamedQuery(name = "HrSmSuccessionPlans.findBySuccessorStrength", query = "SELECT h FROM HrSmSuccessionPlans h WHERE h.successorStrength = :successorStrength"),
    @NamedQuery(name = "HrSmSuccessionPlans.findAllAproved", query = "SELECT h FROM HrSmSuccessorEvaluation h WHERE h.status =2"),
    @NamedQuery(name = "HrSmSuccessionPlans.findAllPlannAproved", query = "SELECT h FROM HrSmSuccessionPlans h WHERE h.status =0"),
    @NamedQuery(name = "HrSmSuccessionPlans.findBystates", query = "SELECT h FROM HrSmSuccessionPlans h WHERE h.status = :sid"),
    @NamedQuery(name = "HrSmSuccessionPlans.findByFistName", query = "SELECT h FROM HrSmSuccessionPlans h WHERE UPPER(h.successorEvaluationId.empId.firstName) LIKE :firstName"),
    @NamedQuery(name = "HrSmSuccessionPlans.findByCatagoyId", query = "SELECT h FROM HrTdCourses h WHERE h.categoryId.id = :categoryId"),
    @NamedQuery(name = "HrSmSuccessionPlans.findByCourseName", query = "SELECT h FROM HrTdCourses h WHERE h.courseName = :courseName"),
    @NamedQuery(name = "HrSmSuccessionPlans.findByDevelopmentNeed", query = "SELECT h FROM HrSmSuccessionPlans h WHERE h.developmentNeed = :developmentNeed"),
    @NamedQuery(name = "HrSmSuccessionPlans.findByDevelopmentPlan", query = "SELECT h FROM HrSmSuccessionPlans h WHERE h.developmentPlan = :developmentPlan"),
    @NamedQuery(name = "HrSmSuccessionPlans.findByRemark", query = "SELECT h FROM HrSmSuccessionPlans h WHERE h.remark = :remark"),
    @NamedQuery(name = "HrSmSuccessionPlans.findByReadinessTimeFrame", query = "SELECT h FROM HrSmSuccessionPlans h WHERE h.readinessTimeFrame = :readinessTimeFrame"),
    @NamedQuery(name = "HrSmSuccessionPlans.findByPreparedOn", query = "SELECT h FROM HrSmSuccessionPlans h WHERE h.preparedOn = :preparedOn"),
    @NamedQuery(name = "HrSmSuccessionPlans.findByStatus", query = "SELECT h FROM HrSmSuccessionPlans h WHERE h.status = :status")})
public class HrSmSuccessionPlans implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HR_SM_SUCCESSION_PLANS_SEQ")
    @SequenceGenerator(name = "HR_SM_SUCCESSION_PLANS_SEQ", sequenceName = "HR_SM_SUCCESSION_PLANS_SEQ", allocationSize = 1)
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private Integer id;
    @Size(max = 400)
    @Column(name = "SUCCESSOR_STRENGTH")
    private String successorStrength;
    @Size(max = 400)
    @Column(name = "DEVELOPMENT_NEED")
    private String developmentNeed;
    @Size(max = 400)
    @Column(name = "DEVELOPMENT_PLAN")
    private String developmentPlan;
    @Size(max = 400)
    @Column(name = "REMARK")
    private String remark;
    @Size(max = 50)
    @Column(name = "READINESS_TIME_FRAME")
    private String readinessTimeFrame;
    @Size(max = 20)
    @Column(name = "PREPARED_ON")
    private String preparedOn;
    @Column(name = "PREPARED_BY")
    private Integer preparedBy;
    @Column(name = "STATUS")
    private String status;
    @OneToMany(mappedBy = "successionPlanId", cascade = CascadeType.ALL)
    private List<HrSmSuccessionPlanDetails> hrSmSuccessionPlanDetailsList;

    @OneToMany(mappedBy = "successionPlanningId", cascade = CascadeType.ALL)
    private List<WfHrProcessed> wfHrProcessedList;

    @JoinColumn(name = "SUCCESSOR_EVALUATION_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrSmSuccessorEvaluation successorEvaluationId;

    @Transient
    String ChangedStstus;

    public HrSmSuccessionPlans() {
    }

    public HrSmSuccessionPlans(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getChangedStstus() {
        return ChangedStstus;
    }

    public void setChangedStstus(String ChangedStstus) {
        this.ChangedStstus = ChangedStstus;
    }

    public String getSuccessorStrength() {
        return successorStrength;
    }

    public void setSuccessorStrength(String successorStrength) {
        this.successorStrength = successorStrength;
    }

    public String getDevelopmentNeed() {
        return developmentNeed;
    }

    public void setDevelopmentNeed(String developmentNeed) {
        this.developmentNeed = developmentNeed;
    }

    public String getDevelopmentPlan() {
        return developmentPlan;
    }

    public void setDevelopmentPlan(String developmentPlan) {
        this.developmentPlan = developmentPlan;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getReadinessTimeFrame() {
        return readinessTimeFrame;
    }

    public void setReadinessTimeFrame(String readinessTimeFrame) {
        this.readinessTimeFrame = readinessTimeFrame;
    }

    public String getPreparedOn() {
        return preparedOn;
    }

    public void setPreparedOn(String preparedOn) {
        this.preparedOn = preparedOn;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @XmlTransient
    public List<HrSmSuccessionPlanDetails> getHrSmSuccessionPlanDetailsList() {
        if (hrSmSuccessionPlanDetailsList == null) {
            hrSmSuccessionPlanDetailsList = new ArrayList<>();
        }
        return hrSmSuccessionPlanDetailsList;
    }

    public void setHrSmSuccessionPlanDetailsList(List<HrSmSuccessionPlanDetails> hrSmSuccessionPlanDetailsList) {
        this.hrSmSuccessionPlanDetailsList = hrSmSuccessionPlanDetailsList;
    }

    @XmlTransient
    public List<WfHrProcessed> getWfHrProcessedList() {
        return wfHrProcessedList;
    }

    public void setWfHrProcessedList(List<WfHrProcessed> wfHrProcessedList) {
        this.wfHrProcessedList = wfHrProcessedList;
    }

    public Integer getPreparedBy() {
        return preparedBy;
    }

    public void setPreparedBy(Integer preparedBy) {
        this.preparedBy = preparedBy;
    }

    public HrSmSuccessorEvaluation getSuccessorEvaluationId() {
        return successorEvaluationId;
    }

    public void setSuccessorEvaluationId(HrSmSuccessorEvaluation successorEvaluationId) {
        this.successorEvaluationId = successorEvaluationId;
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
        if (!(object instanceof HrSmSuccessionPlans)) {
            return false;
        }
        HrSmSuccessionPlans other = (HrSmSuccessionPlans) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.HrSmSuccessionPlans[ id=" + id + " ]";
    }

    public void addDetail(HrSmSuccessionPlanDetails hrSmSuccessionPlanDetails) {
        if (hrSmSuccessionPlanDetails.getSuccessionPlanId() != this) {
            this.getHrSmSuccessionPlanDetailsList().add(hrSmSuccessionPlanDetails);
            hrSmSuccessionPlanDetails.setSuccessionPlanId(this);
        }
    }

}
