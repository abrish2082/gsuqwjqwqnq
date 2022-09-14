/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.entity.planning;

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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Ob
 */
@Entity
@Table(name = "HR_HRP_RECRUITMENTS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HrHrpRecruitments.findAll", query = "SELECT h FROM HrHrpRecruitments h"),
    @NamedQuery(name = "HrHrpRecruitments.findAllyears", query = "SELECT DISTINCT(h.planningYear) FROM HrHrpRecruitments h"),
    @NamedQuery(name = "HrHrpRecruitments.findById", query = "SELECT h FROM HrHrpRecruitments h WHERE h.id = :id"),
    @NamedQuery(name = "HrHrpRecruitments.findByYearandstatus", query = "SELECT h FROM HrHrpRecruitments h WHERE h.planningYear = :planningYear AND h.status=0"),
    @NamedQuery(name = "HrHrpRecruitments.findByDepIdAndPlanYear", query = "SELECT h FROM HrHrpRecruitments h WHERE h.planningYear = :planningYear AND h.deptId =:deptId"),
    @NamedQuery(name = "HrHrpRecruitments.findByYear", query = "SELECT h FROM HrHrpRecruitments h WHERE h.planningYear = :planningYear AND h.status=1"),
    @NamedQuery(name = "HrHrpRecruitments.findByDeptId", query = "SELECT h FROM HrHrpRecruitments h WHERE h.deptId.depId = :deptId"),
    @NamedQuery(name = "HrHrpRecruitments.findByDeptIdStatus", query = "SELECT h FROM HrHrpRecruitments h WHERE h.deptId.depId = :deptId and h.status = :status"),
    @NamedQuery(name = "HrHrpRecruitments.findByRecruitmentRemark", query = "SELECT h FROM HrHrpRecruitments h WHERE h.recruitmentRemark = :recruitmentRemark"),
    @NamedQuery(name = "HrHrpRecruitments.findByRequestDate", query = "SELECT h FROM HrHrpRecruitments h WHERE h.requestDate = :requestDate"),
    @NamedQuery(name = "HrHrpRecruitments.findByStatus", query = "SELECT h FROM HrHrpRecruitments h WHERE h.status = :status"),
    @NamedQuery(name = "HrHrpRecruitments.findByPromotionRemark", query = "SELECT h FROM HrHrpRecruitments h WHERE h.promotionRemark = :promotionRemark")})
public class HrHrpRecruitments implements Serializable {
    
    @OneToMany(mappedBy = "hrpRecruitmentId", cascade = CascadeType.PERSIST)
    private List<WfHrProcessed> WfHrProcessedList;

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HR_HRP_RECRUITMENTS_SEQ")
    @SequenceGenerator(name = "HR_HRP_RECRUITMENTS_SEQ", sequenceName = "HR_HRP_RECRUITMENTS_SEQ", allocationSize = 1)
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private Integer id;
    @Column(name = "PLANNING_YEAR")
    private Integer planningYear;
//    @Column(name = "REQUEST_TYPE")
//    private BigInteger requestType;
    @Size(max = 200)
    @Column(name = "RECRUITMENT_REMARK")
    private String recruitmentRemark;
    @Size(max = 20)
    @Column(name = "REQUEST_DATE")
    private String requestDate;
    @Column(name = "STATUS")
    private Integer status;
//    @Column(name = "STRATEGIC_PERIOD_ID")
//    private BigInteger strategicPeriodId;
    @Size(max = 200)
    @Column(name = "PROMOTION_REMARK")
    private String promotionRemark;

    @OneToMany(mappedBy = "recruitmentId", cascade = CascadeType.ALL)
    private List<HrHrpRecruitmentRequest> hrHrpRecruitmentRequestList;

    @JoinColumn(name = "REQUESTER_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrEmployees requesterId;

    @JoinColumn(name = "DEPT_ID", referencedColumnName = "DEP_ID")
    @ManyToOne
    private HrDepartments deptId;

    public HrHrpRecruitments() {
    }

    public HrHrpRecruitments(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPlanningYear() {
        return planningYear;
    }

    public void setPlanningYear(Integer planningYear) {
        this.planningYear = planningYear;
    }

    public HrDepartments getDeptId() {
        return deptId;
    }

    public void setDeptId(HrDepartments deptId) {
        this.deptId = deptId;
    }

//    public BigInteger getRequestType() {
//        return requestType;
//    }
//
//    public void setRequestType(BigInteger requestType) {
//        this.requestType = requestType;
//    }

    public String getRecruitmentRemark() {
        return recruitmentRemark;
    }

    public void setRecruitmentRemark(String recruitmentRemark) {
        this.recruitmentRemark = recruitmentRemark;
    }

    public String getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(String requestDate) {
        this.requestDate = requestDate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

//    public BigInteger getStrategicPeriodId() {
//        return strategicPeriodId;
//    }
//
//    public void setStrategicPeriodId(BigInteger strategicPeriodId) {
//        this.strategicPeriodId = strategicPeriodId;
//    }

    public String getPromotionRemark() {
        return promotionRemark;
    }

    public void setPromotionRemark(String promotionRemark) {
        this.promotionRemark = promotionRemark;
    }

    @XmlTransient
    public List<HrHrpRecruitmentRequest> getHrHrpRecruitmentRequestList() {
        if (hrHrpRecruitmentRequestList == null) {
            hrHrpRecruitmentRequestList = new ArrayList<>();
        }
        return hrHrpRecruitmentRequestList;
    }

    public void setHrHrpRecruitmentRequestList(List<HrHrpRecruitmentRequest> hrHrpRecruitmentRequestList) {
        this.hrHrpRecruitmentRequestList = hrHrpRecruitmentRequestList;
    }
      @XmlTransient

    public List<WfHrProcessed> getWfHrProcessedList() {
         if (WfHrProcessedList == null) {
            WfHrProcessedList = new ArrayList<>();
        }
        return WfHrProcessedList;
    }

    public void setWfHrProcessedList(List<WfHrProcessed> WfHrProcessedList) {
        this.WfHrProcessedList = WfHrProcessedList;
    }

    public HrEmployees getRequesterId() {
        return requesterId;
    }

    public void setRequesterId(HrEmployees requesterId) {
        this.requesterId = requesterId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    public void addNeedRequest(HrHrpRecruitmentRequest hrHrpRecruitmentRequest) {
        if (hrHrpRecruitmentRequest.getRecruitmentId() != this) {
            this.getHrHrpRecruitmentRequestList().add(hrHrpRecruitmentRequest);
            hrHrpRecruitmentRequest.setRecruitmentId(this);
        }
    }
    
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HrHrpRecruitments)) {
            return false;
        }
        HrHrpRecruitments other = (HrHrpRecruitments) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.HrHrpRecruitments[ id=" + id + " ]";
    }

}
