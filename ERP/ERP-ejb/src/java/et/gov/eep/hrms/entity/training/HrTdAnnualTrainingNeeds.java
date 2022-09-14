/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.entity.training;

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
@Table(name = "HR_TD_ANNUAL_TRAINING_NEEDS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HrTdAnnualTrainingNeeds.findAll", query = "SELECT h FROM HrTdAnnualTrainingNeeds h WHERE h.annualNeedRequestId.status = 'Approved'"),
    @NamedQuery(name = "HrTdAnnualTrainingNeeds.findAll1", query = "SELECT h FROM HrTdAnnualTrainingNeeds h"),
    @NamedQuery(name = "HrTdAnnualTrainingNeeds.findById", query = "SELECT h FROM HrTdAnnualTrainingNeeds h WHERE h.id = :id"),
    @NamedQuery(name = "HrTdAnnualTrainingNeeds.findByAnnualNeedRequestId", query = "SELECT h FROM HrTdAnnualTrainingNeeds h WHERE h.annualNeedRequestId.id = :annualNeedRequestId"),
    @NamedQuery(name = "HrTdAnnualTrainingNeeds.findByCourse", query = "SELECT h FROM HrTdAnnualTrainingNeeds h WHERE h.trainingId = :trainingId"),
    @NamedQuery(name = "HrTdAnnualTrainingNeeds.findByNoOfNominee", query = "SELECT h FROM HrTdAnnualTrainingNeeds h WHERE h.noOfNominee = :noOfNominee"),
    @NamedQuery(name = "HrTdAnnualTrainingNeeds.findByLocationType", query = "SELECT h FROM HrTdAnnualTrainingNeeds h WHERE h.locationType = :locationType"),
    @NamedQuery(name = "HrTdAnnualTrainingNeeds.findByTrainingRequest", query = "SELECT h FROM HrTdAnnualTrainingNeeds h WHERE h.annualNeedRequestId.id = :annualNeedRequestId"),
    @NamedQuery(name = "HrTdAnnualTrainingNeeds.findByTrainingRequestStatus", query = "SELECT h FROM HrTdAnnualTrainingNeeds h WHERE h.annualNeedRequestId.id = :annualNeedRequestId and h.status = '0'"),
    @NamedQuery(name = "HrTdAnnualTrainingNeeds.findByTentativeStartDate", query = "SELECT h FROM HrTdAnnualTrainingNeeds h WHERE h.tentativeStartDate = :tentativeStartDate"),
    @NamedQuery(name = "HrTdAnnualTrainingNeeds.findByTentativeEndDate", query = "SELECT h FROM HrTdAnnualTrainingNeeds h WHERE h.tentativeEndDate = :tentativeEndDate"),
    @NamedQuery(name = "HrTdAnnualTrainingNeeds.findByCostPerPerson", query = "SELECT h FROM HrTdAnnualTrainingNeeds h WHERE h.costPerPerson = :costPerPerson"),
    @NamedQuery(name = "HrTdAnnualTrainingNeeds.findBySourceOfNeed", query = "SELECT h FROM HrTdAnnualTrainingNeeds h WHERE h.sourceOfNeed = :sourceOfNeed"),
    @NamedQuery(name = "HrTdAnnualTrainingNeeds.findBySponsoredBy", query = "SELECT h FROM HrTdAnnualTrainingNeeds h WHERE h.sponsoredBy = :sponsoredBy"),
    @NamedQuery(name = "HrTdAnnualTrainingNeeds.findByRemark", query = "SELECT h FROM HrTdAnnualTrainingNeeds h WHERE h.remark = :remark"),
    @NamedQuery(name = "HrTdAnnualTrainingNeeds.findByStatus", query = "SELECT h FROM HrTdAnnualTrainingNeeds h WHERE h.status = :status")})
public class HrTdAnnualTrainingNeeds implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HR_TD_ANNUAL_TRA_NEEDS_SEQ")
    @SequenceGenerator(name = "HR_TD_ANNUAL_TRA_NEEDS_SEQ", sequenceName = "HR_TD_ANNUAL_TRA_NEEDS_SEQ", allocationSize = 1)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "NO_OF_NOMINEE")
    private Integer noOfNominee;
    @Column(name = "NO_OF_DAYS")
    private Integer noOfDays;
    @Size(max = 20)
    @Column(name = "LOCATION_TYPE")
    private String locationType;
    @Size(max = 20)
    @Column(name = "TENTATIVE_START_DATE")
    private String tentativeStartDate;
    @Size(max = 20)
    @Column(name = "TENTATIVE_END_DATE")
    private String tentativeEndDate;
    @Column(name = "COST_PER_PERSON")
    private Integer costPerPerson;
    @Size(max = 200)
    @Column(name = "SOURCE_OF_NEED")
    private String sourceOfNeed;
    @Size(max = 100)
    @Column(name = "SPONSORED_BY")
    private String sponsoredBy;
    @Size(max = 400)
    @Column(name = "REMARK")
    private String remark;
    @Column(name = "STATUS")
    private Integer status;

    @JoinColumn(name = "ANNUAL_NEED_REQUEST_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrTdAnnualNeedRequests annualNeedRequestId;

    @JoinColumn(name = "TRAINING_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrTdCourses trainingId;

    @JoinColumn(name = "INSTITUTION_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrTdTrainerProfiles institutionId;

    @OneToMany(mappedBy = "annTraNeedId", cascade = CascadeType.ALL)
    private List<HrTdAnnualTraParticipants> hrTdAnulTraPaticptsList = new ArrayList<>();

    public HrTdAnnualTrainingNeeds() {
    }

    public HrTdAnnualTrainingNeeds(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNoOfNominee() {
        return noOfNominee;
    }

    public void setNoOfNominee(Integer noOfNominee) {
        this.noOfNominee = noOfNominee;
    }

    public Integer getNoOfDays() {
        return noOfDays;
    }

    public void setNoOfDays(Integer noOfDays) {
        this.noOfDays = noOfDays;
    }

    public String getLocationType() {
        return locationType;
    }

    public void setLocationType(String locationType) {
        this.locationType = locationType;
    }

    public String getTentativeStartDate() {
        return tentativeStartDate;
    }

    public void setTentativeStartDate(String tentativeStartDate) {
        this.tentativeStartDate = tentativeStartDate;
    }

    public String getTentativeEndDate() {
        return tentativeEndDate;
    }

    public void setTentativeEndDate(String tentativeEndDate) {
        this.tentativeEndDate = tentativeEndDate;
    }

    public Integer getCostPerPerson() {
        return costPerPerson;
    }

    public void setCostPerPerson(Integer costPerPerson) {
        this.costPerPerson = costPerPerson;
    }

    public String getSourceOfNeed() {
        return sourceOfNeed;
    }

    public void setSourceOfNeed(String sourceOfNeed) {
        this.sourceOfNeed = sourceOfNeed;
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

    public HrTdAnnualNeedRequests getAnnualNeedRequestId() {
        return annualNeedRequestId;
    }

    public void setAnnualNeedRequestId(HrTdAnnualNeedRequests annualNeedRequestId) {
        this.annualNeedRequestId = annualNeedRequestId;
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
    public List<HrTdAnnualTraParticipants> getHrTdAnulTraPaticptsList() {
        if (hrTdAnulTraPaticptsList == null) {
            hrTdAnulTraPaticptsList = new ArrayList<>();
        }
        return hrTdAnulTraPaticptsList;
    }

    public void setHrTdAnulTraPaticptsList(List<HrTdAnnualTraParticipants> hrTdAnulTraPaticptsList) {
        this.hrTdAnulTraPaticptsList = hrTdAnulTraPaticptsList;
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
        if (!(object instanceof HrTdAnnualTrainingNeeds)) {
            return false;
        }
        HrTdAnnualTrainingNeeds other = (HrTdAnnualTrainingNeeds) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Transient
    private String listOfDepartment;
    @Transient
    private Integer totalParticipant;

    public String getListOfDepartment() {
        return listOfDepartment;
    }

    public void setListOfDepartment(String listOfDepartment) {
        this.listOfDepartment = listOfDepartment;
    }

    public Integer getTotalParticipant() {
        return totalParticipant;
    }

    public void setTotalParticipant(Integer totalParticipant) {
        this.totalParticipant = totalParticipant;
    }

    @Override
    public String toString() {
        return "entity.HrTdAnnualTrainingNeeds[ id=" + id + " ]";
    }

    public void addParticipantEmp(HrTdAnnualTraParticipants hrTdAnnualTraParticipants) {
        if (hrTdAnnualTraParticipants.getAnnTraNeedId() != this) {
            this.getHrTdAnulTraPaticptsList().add(hrTdAnnualTraParticipants);
            hrTdAnnualTraParticipants.setAnnTraNeedId(this);
        }
    }
}
