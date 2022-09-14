/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.entity.training;

import et.gov.eep.commonApplications.entity.WfHrProcessed;
import et.gov.eep.hrms.entity.organization.HrDepartments;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
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
 * @author Abdi_Pc
 */
@Entity
@Table(name = "HR_TD_UNPLAN_TRAINING_REQUEST")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HrTdUnplanTrainingRequest.findAll", query = "SELECT h FROM HrTdUnplanTrainingRequest h"),
    @NamedQuery(name = "HrTdUnplanTrainingRequest.findById", query = "SELECT h FROM HrTdUnplanTrainingRequest h WHERE h.id = :id"),
    @NamedQuery(name = "HrTdUnplanTrainingRequest.findBYActiveStatus", query = "SELECT h FROM HrTdUnplanTrainingRequest h WHERE h.status = :status1"),
    @NamedQuery(name = "HrTdUnplanTrainingRequest.findByBudgetYear", query = "SELECT DISTINCT (h.trainingId) FROM HrTdUnplanTrainingRequest h WHERE h.budgetYear = :budgetYear AND h.startDate >= :value1 AND h.startDate <= :value2"),
    @NamedQuery(name = "HrTdUnplanTrainingRequest.findByNoOfParticipant", query = "SELECT h FROM HrTdUnplanTrainingRequest h WHERE h.noOfParticipant = :noOfParticipant"),
    @NamedQuery(name = "HrTdUnplanTrainingRequest.findByLocationType", query = "SELECT h FROM HrTdUnplanTrainingRequest h WHERE h.locationType = :locationType"),
    @NamedQuery(name = "HrTdUnplanTrainingRequest.findByVenue", query = "SELECT h FROM HrTdUnplanTrainingRequest h WHERE h.venue = :venue"),
    @NamedQuery(name = "HrTdUnplanTrainingRequest.findByStartDate", query = "SELECT h FROM HrTdUnplanTrainingRequest h WHERE h.startDate = :startDate"),
    @NamedQuery(name = "HrTdUnplanTrainingRequest.findByEndDate", query = "SELECT h FROM HrTdUnplanTrainingRequest h WHERE h.endDate = :endDate"),
    @NamedQuery(name = "HrTdUnplanTrainingRequest.findByCostPerPerson", query = "SELECT h FROM HrTdUnplanTrainingRequest h WHERE h.costPerPerson = :costPerPerson"),
    @NamedQuery(name = "HrTdUnplanTrainingRequest.findBySponsoredBy", query = "SELECT h FROM HrTdUnplanTrainingRequest h WHERE h.sponsoredBy = :sponsoredBy"),
    @NamedQuery(name = "HrTdUnplanTrainingRequest.findByDeptNameLIKE", query = "SELECT h FROM HrTdUnplanTrainingRequest h WHERE UPPER(h.departmentId.depName)LIKE :detName"),
    @NamedQuery(name = "HrTdUnplanTrainingRequest.findByTrainingId", query = "SELECT h FROM HrTdUnplanTrainingRequest h WHERE h.trainingId = :trainingId"),
    @NamedQuery(name = "HrTdUnplanTrainingRequest.findByRemark", query = "SELECT h FROM HrTdUnplanTrainingRequest h WHERE h.remark = :remark"),
    @NamedQuery(name = "HrTdUnplanTrainingRequest.findByStatus", query = "SELECT h FROM HrTdUnplanTrainingRequest h WHERE h.status = :status"),
    @NamedQuery(name = "HrTdUnplanTrainingRequest.findByDescription", query = "SELECT h FROM HrTdUnplanTrainingRequest h WHERE h.description = :description")})
public class HrTdUnplanTrainingRequest implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HR_TD_UNPLAN_TRA_REQUEST_SEQ")
    @SequenceGenerator(name = "HR_TD_UNPLAN_TRA_REQUEST_SEQ", sequenceName = "HR_TD_UNPLAN_TRA_REQUEST_SEQ", allocationSize = 1)
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private Integer id;
    @Column(name = "BUDGET_YEAR")
    private Integer budgetYear;
    @Column(name = "NO_OF_PARTICIPANT")
    private Integer noOfParticipant;
    @Column(name = "LOCATION_TYPE")
    private Integer locationType;
    @Size(max = 100)
    @Column(name = "VENUE")
    private String venue;
    @Size(max = 20)
    @Column(name = "START_DATE")
    private String startDate;
    @Size(max = 20)
    @Column(name = "END_DATE")
    private String endDate;
    @Column(name = "COST_PER_PERSON")
    private BigInteger costPerPerson;
    @Size(max = 100)
    @Column(name = "SPONSORED_BY")
    private String sponsoredBy;
    @Size(max = 200)
    @Column(name = "REMARK")
    private String remark;
    @Column(name = "STATUS")
    private Integer status;
    @Size(max = 200)
    @Column(name = "DESCRIPTION")
    private String description;
    @Size(max = 200)
    @Column(name = "PREPARED_BY")
    private String preparedBy;
    @JoinColumn(name = "DEPARTMENT_ID", referencedColumnName = "DEP_ID")
    @ManyToOne
    private HrDepartments departmentId;
    @JoinColumn(name = "TRAINING_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrTdCourses trainingId;
    @JoinColumn(name = "INSTITUTION_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrTdTrainerProfiles institutionId;

    @OneToMany(mappedBy = "unpTraReqId", cascade = CascadeType.ALL)
    private List<HrTdUnplanTraParticipant> hrTdUnplanTraParticipantList;
    @OneToMany(mappedBy = "unplannedTrainingId", cascade = CascadeType.ALL)
    private List<WfHrProcessed> hrWorkFlowUnplannedList;

    public HrTdUnplanTrainingRequest() {
    }

    public HrTdUnplanTrainingRequest(Integer id) {
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

    public Integer getNoOfParticipant() {
        return noOfParticipant;
    }

    public void setNoOfParticipant(Integer noOfParticipant) {
        this.noOfParticipant = noOfParticipant;
    }

    public Integer getLocationType() {
        return locationType;
    }

    public void setLocationType(Integer locationType) {
        this.locationType = locationType;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
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

    public BigInteger getCostPerPerson() {
        return costPerPerson;
    }

    public void setCostPerPerson(BigInteger costPerPerson) {
        this.costPerPerson = costPerPerson;
    }

    public String getSponsoredBy() {
        return sponsoredBy;
    }

    public void setSponsoredBy(String sponsoredBy) {
        this.sponsoredBy = sponsoredBy;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPreparedBy() {
        return preparedBy;
    }

    public void setPreparedBy(String preparedBy) {
        this.preparedBy = preparedBy;
    }

    public HrDepartments getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(HrDepartments departmentId) {
        this.departmentId = departmentId;
    }

    public HrTdCourses getTrainingId() {
        return trainingId;
    }

    public void setTrainingId(HrTdCourses trainingId) {
        this.trainingId = trainingId;
    }

    public HrTdTrainerProfiles getInstitutionId() {
        return institutionId;
    }

    public void setInstitutionId(HrTdTrainerProfiles institutionId) {
        this.institutionId = institutionId;
    }

    @XmlTransient
    public List<HrTdUnplanTraParticipant> getHrTdUnplanTraParticipantList() {
        if (hrTdUnplanTraParticipantList == null) {
            hrTdUnplanTraParticipantList = new ArrayList<>();
        }
        return hrTdUnplanTraParticipantList;
    }

    public void setHrTdUnplanTraParticipantList(List<HrTdUnplanTraParticipant> hrTdUnplanTraParticipantList) {
        this.hrTdUnplanTraParticipantList = hrTdUnplanTraParticipantList;
    }

    @XmlTransient
    public List<WfHrProcessed> getHrWorkFlowUnplannedList() {
        if (hrWorkFlowUnplannedList == null) {
            hrWorkFlowUnplannedList = new ArrayList<>();
        }
        return hrWorkFlowUnplannedList;
    }

    public void setHrWorkFlowUnplannedList(List<WfHrProcessed> hrWorkFlowUnplannedList) {
        this.hrWorkFlowUnplannedList = hrWorkFlowUnplannedList;
    }

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
        if (!(object instanceof HrTdUnplanTrainingRequest)) {
            return false;
        }
        HrTdUnplanTrainingRequest other = (HrTdUnplanTrainingRequest) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return getTrainingId().getCourseName();//"entit.HrTdUnplanTrainingRequest[ id=" + id + " ]";
    }

    public void addToDataTable(HrTdUnplanTraParticipant hrTdUnplanTraParticipant) {
        if (hrTdUnplanTraParticipant.getUnpTraReqId() != this) {
            this.getHrTdUnplanTraParticipantList().add(hrTdUnplanTraParticipant);
            hrTdUnplanTraParticipant.setUnpTraReqId(this);
        }
    }

}
