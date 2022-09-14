/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.entity.recruitment;

import et.gov.eep.commonApplications.entity.WfHrProcessed;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author munir
 */
@Entity
@Table(name = "HR_CANDIDATE_SELECTED")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HrCandidateSelected.findAll", query = "SELECT h FROM HrCandidateSelected h"),
    @NamedQuery(name = "HrCandidateSelected.findByCandidateId", query = "SELECT h FROM HrCandidateSelected h WHERE h.candidateId = :candidateId"),
    @NamedQuery(name = "HrCandidateSelected.findByExamResult", query = "SELECT h FROM HrCandidateSelected h WHERE h.examResult = :examResult"),
    @NamedQuery(name = "HrCandidateSelected.findByInterviewResult", query = "SELECT h FROM HrCandidateSelected h WHERE h.interviewResult = :interviewResult"),
    @NamedQuery(name = "HrCandidateSelected.findByPracticalResult", query = "SELECT h FROM HrCandidateSelected h WHERE h.practicalResult = :practicalResult"),
    @NamedQuery(name = "HrCandidateSelected.findByOtherResult", query = "SELECT h FROM HrCandidateSelected h WHERE h.otherResult = :otherResult"),
    @NamedQuery(name = "HrCandidateSelected.findByShortlistRemark", query = "SELECT h FROM HrCandidateSelected h WHERE h.shortlistRemark = :shortlistRemark"),
    @NamedQuery(name = "HrCandidateSelected.findByShortlistRecommendation", query = "SELECT h FROM HrCandidateSelected h WHERE h.shortlistRecommendation = :shortlistRecommendation"),
    @NamedQuery(name = "HrCandidateSelected.findByShortlistBy", query = "SELECT h FROM HrCandidateSelected h WHERE h.shortlistBy = :shortlistBy"),
    @NamedQuery(name = "HrCandidateSelected.findByShortlistOn", query = "SELECT h FROM HrCandidateSelected h WHERE h.shortlistOn = :shortlistOn"),
    @NamedQuery(name = "HrCandidateSelected.findByFiliterRemark", query = "SELECT h FROM HrCandidateSelected h WHERE h.filiterRemark = :filiterRemark"),
    @NamedQuery(name = "HrCandidateSelected.findByFiliterRecommendation", query = "SELECT h FROM HrCandidateSelected h WHERE h.filiterRecommendation = :filiterRecommendation"),
    @NamedQuery(name = "HrCandidateSelected.findByFiliterBy", query = "SELECT h FROM HrCandidateSelected h WHERE h.filiterBy = :filiterBy"),
    @NamedQuery(name = "HrCandidateSelected.findByFiliterOn", query = "SELECT h FROM HrCandidateSelected h WHERE h.filiterOn = :filiterOn"),
    @NamedQuery(name = "HrCandidateSelected.findByStatus", query = "SELECT h FROM HrCandidateSelected h WHERE h.status = :status")})
public class HrCandidateSelected implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "CANDIDATE_ID")
    private BigDecimal candidateId;

    @Column(name = "EXAM_RESULT")
    private Float examResult;

    @Column(name = "INTERVIEW_RESULT")
    private Float interviewResult;

    @Column(name = "PRACTICAL_RESULT")
    private Float practicalResult;

    @Column(name = "OTHER_RESULT")
    private Float otherResult;

    @Column(name = "SHORTLIST_RECOMMENDATION")
    private String shortlistRecommendation;

    @Column(name = "SHORTLIST_REMARK")
    private String shortlistRemark;

    @Column(name = "SHORTLIST_BY")
    private String shortlistBy;

    @Column(name = "SHORTLIST_ON")
    private String shortlistOn;

    @Column(name = "SHORTLIST_APPROVED_BY")
    private String shortlistApprovedBy;

    @Column(name = "SHORTLIST_APPROVED_ON")
    private String shortlistApprovedOn;

    @Column(name = "FILITER_RECOMMENDATION")
    private String filiterRecommendation;

    @Column(name = "FILITER_REMARK")
    private String filiterRemark;

    @Column(name = "FILITER_BY")
    private String filiterBy;

    @Column(name = "FILITER_ON")
    private String filiterOn;

    @Column(name = "FILITER_APPROVED_BY")
    private String filiterApprovedBy;

    @Column(name = "FILITER_APPROVED_ON")
    private String filiterApprovedOn;

    @Column(name = "STATUS")
    private Integer status;

    @OneToMany(mappedBy = "shortlistId", cascade = CascadeType.ALL)
    private List<WfHrProcessed> wfHrProcessedList = new ArrayList<>();

    public HrCandidateSelected() {
    }

    //<editor-fold defaultstate="collapsed" desc="getters & setters">
    public HrCandidateSelected(BigDecimal candidateId) {
        this.candidateId = candidateId;
    }

    public BigDecimal getCandidateId() {
        return candidateId;
    }

    public void setCandidateId(BigDecimal candidateId) {
        this.candidateId = candidateId;
    }

    public Float getExamResult() {
        return examResult;
    }

    public void setExamResult(Float examResult) {
        this.examResult = examResult;
    }

    public Float getInterviewResult() {
        return interviewResult;
    }

    public void setInterviewResult(Float interviewResult) {
        this.interviewResult = interviewResult;
    }

    public Float getPracticalResult() {
        return practicalResult;
    }

    public void setPracticalResult(Float practicalResult) {
        this.practicalResult = practicalResult;
    }

    public Float getOtherResult() {
        return otherResult;
    }

    public void setOtherResult(Float otherResult) {
        this.otherResult = otherResult;
    }

    public String getShortlistRemark() {
        return shortlistRemark;
    }

    public void setShortlistRemark(String shortlistRemark) {
        this.shortlistRemark = shortlistRemark;
    }

    public String getShortlistRecommendation() {
        return shortlistRecommendation;
    }

    public void setShortlistRecommendation(String shortlistRecommendation) {
        this.shortlistRecommendation = shortlistRecommendation;
    }

    public String getShortlistBy() {
        return shortlistBy;
    }

    public void setShortlistBy(String shortlistBy) {
        this.shortlistBy = shortlistBy;
    }

    public String getShortlistOn() {
        return shortlistOn;
    }

    public void setShortlistOn(String shortlistOn) {
        this.shortlistOn = shortlistOn;
    }

    public String getShortlistApprovedBy() {
        return shortlistApprovedBy;
    }

    public void setShortlistApprovedBy(String shortlistApprovedBy) {
        this.shortlistApprovedBy = shortlistApprovedBy;
    }

    public String getShortlistApprovedOn() {
        return shortlistApprovedOn;
    }

    public void setShortlistApprovedOn(String shortlistApprovedOn) {
        this.shortlistApprovedOn = shortlistApprovedOn;
    }

    public String getFiliterRemark() {
        return filiterRemark;
    }

    public void setFiliterRemark(String filiterRemark) {
        this.filiterRemark = filiterRemark;
    }

    public String getFiliterRecommendation() {
        return filiterRecommendation;
    }

    public void setFiliterRecommendation(String filiterRecommendation) {
        this.filiterRecommendation = filiterRecommendation;
    }

    public String getFiliterBy() {
        return filiterBy;
    }

    public void setFiliterBy(String filiterBy) {
        this.filiterBy = filiterBy;
    }

    public String getFiliterOn() {
        return filiterOn;
    }

    public void setFiliterOn(String filiterOn) {
        this.filiterOn = filiterOn;
    }

    public String getFiliterApprovedBy() {
        return filiterApprovedBy;
    }

    public void setFiliterApprovedBy(String filiterApprovedBy) {
        this.filiterApprovedBy = filiterApprovedBy;
    }

    public String getFiliterApprovedOn() {
        return filiterApprovedOn;
    }

    public void setFiliterApprovedOn(String filiterApprovedOn) {
        this.filiterApprovedOn = filiterApprovedOn;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<WfHrProcessed> getWfHrProcessedList() {
        if (wfHrProcessedList == null) {
            wfHrProcessedList = new ArrayList<>();
        }
        return wfHrProcessedList;
    }

    public void setWfHrProcessedList(List<WfHrProcessed> wfHrProcessedList) {
        this.wfHrProcessedList = wfHrProcessedList;
    }
    //</editor-fold>

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (candidateId != null ? candidateId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HrCandidateSelected)) {
            return false;
        }
        HrCandidateSelected other = (HrCandidateSelected) object;
        if ((this.candidateId == null && other.candidateId != null) || (this.candidateId != null && !this.candidateId.equals(other.candidateId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.eep.hrms.entity.recruitment.HrCandidateSelected[ candidateId=" + candidateId + " ]";
    }

    //<editor-fold defaultstate="collapsed" desc="transient">
    @Transient
    String firstName;

    @Transient
    String middleName;

    @Transient
    String lastName;

    @Transient
    String sex;

    @Transient
    String DateOfBirth;

    @Transient
    Integer exprience;

    @Transient
    String statusLabel;

    @Transient
    int age;

    @Transient
    Float totalResult;

    @Transient
    boolean approved;

    //<editor-fold defaultstate="collapsed" desc="getters & setters">
    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getDateOfBirth() {
        return DateOfBirth;
    }

    public void setDateOfBirth(String DateOfBirth) {
        this.DateOfBirth = DateOfBirth;
    }

    public Integer getExprience() {
        return exprience;
    }

    public void setExprience(Integer exprience) {
        this.exprience = exprience;
    }

    public String getStatusLabel() {
        return statusLabel;
    }

    public void setStatusLabel(String statusLabel) {
        this.statusLabel = statusLabel;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Float getTotalResult() {
        return totalResult;
    }

    public void setTotalResult(Float totalResult) {
        this.totalResult = totalResult;
    }
//</editor-fold>

//</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="status static variables">
    public static final int REGISTERED = 0;
    public static final int SHORTLIST_REQUEST = 1;//request to be on shortlisted list
    public static final int SHORTLISTED = 2;
    public static final int FILTERING_REQUEST = 3;//request to be on filtered list
    public static final int FILTERED = 4;
    public static final int SELECTED_FOR_RECRUITMENT = 5;
    public static final int EMPLOYEE = 6;
    public static final int REGISTERED_EMPLOYEE = 7;// all data transfers form candidate related tabels to employee relaed tables is completed
//</editor-fold>
}
