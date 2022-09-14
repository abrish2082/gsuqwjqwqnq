/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.entity.succession;

import et.gov.eep.hrms.entity.training.HrTdCourses;
import java.io.Serializable;
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
@Table(name = "HR_SM_SUCCESSION_PLAN_DETAILS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HrSmSuccessionPlanDetails.findAll", query = "SELECT h FROM HrSmSuccessionPlanDetails h"),
    @NamedQuery(name = "HrSmSuccessionPlanDetails.findById", query = "SELECT h FROM HrSmSuccessionPlanDetails h WHERE h.id = :id"),
    @NamedQuery(name = "HrSmSuccessionPlanDetails.findbyEmpId", query = "SELECT h FROM HrSmSuccessionPlanDetails h WHERE h.successionPlanId.successorEvaluationId.empId = :empid"),
    @NamedQuery(name = "HrSmSuccessionPlanDetails.findByStartDate", query = "SELECT h FROM HrSmSuccessionPlanDetails h WHERE h.startDate = :startDate"),
    @NamedQuery(name = "HrSmSuccessionPlanDetails.findByEndDate", query = "SELECT h FROM HrSmSuccessionPlanDetails h WHERE h.endDate = :endDate"),
    @NamedQuery(name = "HrSmSuccessionPlanDetails.findByStatus", query = "SELECT h FROM HrSmSuccessionPlanDetails h WHERE h.status = :status")})
public class HrSmSuccessionPlanDetails implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HR_SM_SUCC_PLAN_DETAILS_SEQ")
    @SequenceGenerator(name = "HR_SM_SUCC_PLAN_DETAILS_SEQ", sequenceName = "HR_SM_SUCC_PLAN_DETAILS_SEQ", allocationSize = 1)
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private Integer id;
    @Size(max = 20)
    @Column(name = "START_DATE")
    private String startDate;
    @Size(max = 20)
    @Column(name = "END_DATE")
    private String endDate;
    @Size(max = 20)
    @Column(name = "STATUS")
    private String status;
    @JoinColumn(name = "SUCCESSION_PLAN_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrSmSuccessionPlans successionPlanId;
    @JoinColumn(name = "TRAINING_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrTdCourses trainingId;

    public HrSmSuccessionPlanDetails() {
    }

    public HrSmSuccessionPlanDetails(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public HrSmSuccessionPlans getSuccessionPlanId() {
        return successionPlanId;
    }

    public void setSuccessionPlanId(HrSmSuccessionPlans successionPlanId) {
        this.successionPlanId = successionPlanId;
    }

    public HrTdCourses getTrainingId() {
        return trainingId;
    }

    public void setTrainingId(HrTdCourses trainingId) {
        this.trainingId = trainingId;
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
        if (!(object instanceof HrSmSuccessionPlanDetails)) {
            return false;
        }
        HrSmSuccessionPlanDetails other = (HrSmSuccessionPlanDetails) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.HrSmSuccessionPlanDetails[ id=" + id + " ]";
    }

}
