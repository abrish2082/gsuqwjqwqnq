/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.entity.training;

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
import javax.persistence.Transient;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Ob
 */
@Entity
@Table(name = "HR_TD_ANNUAL_NEED_REQUESTS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HrTdAnnualNeedRequests.findAll", query = "SELECT h FROM HrTdAnnualNeedRequests h"),
    @NamedQuery(name = "HrTdAnnualNeedRequests.findAllApproved", query = "SELECT h FROM HrTdAnnualNeedRequests h WHERE h.status = '3'"),
    @NamedQuery(name = "HrTdAnnualNeedRequests.checkDuplicate", query = "SELECT h FROM HrTdAnnualNeedRequests h WHERE h.deptId.depName =:depName and h.status = '0'"),
    @NamedQuery(name = "HrTdAnnualNeedRequests.findById", query = "SELECT h FROM HrTdAnnualNeedRequests h WHERE h.id = :id"),
    @NamedQuery(name = "HrTdAnnualNeedRequests.findByBudgetYear", query = "SELECT h FROM HrTdAnnualNeedRequests h WHERE h.budgetYear = :budgetYear ORDER BY h.preparedOn"),
    @NamedQuery(name = "HrTdAnnualNeedRequests.findApprovedYear", query = "SELECT DISTINCT (h.budgetYear) FROM HrTdAnnualNeedRequests h WHERE h.budgetYear = :budgetYear and h.status = '3'"),
    @NamedQuery(name = "HrTdAnnualNeedRequests.findApprovedByYear", query = "SELECT h FROM HrTdAnnualNeedRequests h WHERE h.budgetYear = :year and h.status = '3' ORDER BY h.preparedOn"),
    @NamedQuery(name = "HrTdAnnualNeedRequests.findByDepartment", query = "SELECT h FROM HrTdAnnualNeedRequests h WHERE h.deptId.depId = :deptId"),
    @NamedQuery(name = "HrTdAnnualNeedRequests.findByStatus", query = "SELECT h FROM HrTdAnnualNeedRequests h WHERE h.status = :status"),
    @NamedQuery(name = "HrTdAnnualNeedRequests.findByDepartment_Year", query = "SELECT h FROM HrTdAnnualNeedRequests h WHERE h.budgetYear = :budgetYear and  h.deptId.depId = :deptId"),
    @NamedQuery(name = "HrTdAnnualNeedRequests.findByPreparedOn", query = "SELECT h FROM HrTdAnnualNeedRequests h WHERE h.preparedOn = :preparedOn")})
public class HrTdAnnualNeedRequests implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HR_TD_ANNUAL_NEED_REQUESTS_SEQ")
    @SequenceGenerator(name = "HR_TD_ANNUAL_NEED_REQUESTS_SEQ", sequenceName = "HR_TD_ANNUAL_NEED_REQUESTS_SEQ", allocationSize = 1)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "BUDGET_YEAR")
    private Integer budgetYear;
    @Size(max = 20)
    @Column(name = "PREPARED_ON")
    private String preparedOn;
    @Size(max = 20)
    @Column(name = "STATUS")
    private String status;

    @Column(name = "PREPARED_BY")
    private Integer preparedBy;
    @OneToMany(mappedBy = "annualNeedRequestId", cascade = CascadeType.ALL)
    private List<HrTdAnnualTrainingNeeds> hrTdAnnualTrainingNeedsList;

    @JoinColumn(name = "DEPT_ID", referencedColumnName = "DEP_ID")
    @ManyToOne
    private HrDepartments deptId;

//    @JoinColumn(name = "PREPARED_BY", referencedColumnName = "ID")
//    @ManyToOne
//  private HrEmployees preparedBy;
    @OneToMany(mappedBy = "annualTrainingId", cascade = CascadeType.ALL)
    private List<WfHrProcessed> hrWorkFlowAnnualRequestList;

    public HrTdAnnualNeedRequests() {
    }

    public HrTdAnnualNeedRequests(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBudgetYear() {
        return budgetYear;
    }

    public void setBudgetYear(Integer budgetYear) {
        this.budgetYear = budgetYear;
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
    public List<WfHrProcessed> getHrWorkFlowAnnualRequestList() {
        if (hrWorkFlowAnnualRequestList == null) {
            hrWorkFlowAnnualRequestList = new ArrayList<>();
        }
        return hrWorkFlowAnnualRequestList;
    }

    public void setHrWorkFlowAnnualRequestList(List<WfHrProcessed> hrWorkFlowAnnualRequestList) {
        this.hrWorkFlowAnnualRequestList = hrWorkFlowAnnualRequestList;
    }

    @XmlTransient
    public List<HrTdAnnualTrainingNeeds> getHrTdAnnualTrainingNeedsList() {
        if (hrTdAnnualTrainingNeedsList == null) {
            hrTdAnnualTrainingNeedsList = new ArrayList<>();
        }
        return hrTdAnnualTrainingNeedsList;
    }

    public void setHrTdAnnualTrainingNeedsList(List<HrTdAnnualTrainingNeeds> hrTdAnnualTrainingNeedsList) {
        this.hrTdAnnualTrainingNeedsList = hrTdAnnualTrainingNeedsList;
    }

    public HrDepartments getDeptId() {
        return deptId;
    }

    public void setDeptId(HrDepartments deptId) {
        this.deptId = deptId;
    }

    public Integer getPreparedBy() {
        return preparedBy;
    }

    public void setPreparedBy(Integer preparedBy) {
        this.preparedBy = preparedBy;
    }

//    public HrEmployees getPreparedBy() {
//        return preparedBy;
//    }
//
//    public void setPreparedBy(HrEmployees preparedBy) {
//        this.preparedBy = preparedBy;
//    }
    @Transient
    String changedStatus;

    public String getChangedStatus() {
        return changedStatus;
    }

    public void setChangedStatus(String changedStatus) {
        this.changedStatus = changedStatus;
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
        if (!(object instanceof HrTdAnnualNeedRequests)) {
            return false;
        }
        HrTdAnnualNeedRequests other = (HrTdAnnualNeedRequests) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return id.toString();
    }

    public void addTrainingNeed(HrTdAnnualTrainingNeeds hrTdAnnualTrainingNeeds) {
        if (hrTdAnnualTrainingNeeds.getAnnualNeedRequestId() != this) {
            this.getHrTdAnnualTrainingNeedsList().add(hrTdAnnualTrainingNeeds);
            hrTdAnnualTrainingNeeds.setAnnualNeedRequestId(this);
        }
    }

}
