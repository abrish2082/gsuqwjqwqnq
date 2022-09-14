/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.entity.organization;

import et.gov.eep.hrms.entity.allowance.HrAllowanceInJobTitle;
import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.entity.lookup.HrLuJobLevels;
import et.gov.eep.hrms.entity.recruitment.HrAdvertisedJobs;
import et.gov.eep.hrms.entity.recruitment.HrRecruitmentRequests;
import et.gov.eep.hrms.entity.succession.HrSmKmp;
import java.io.Serializable;
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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Baya
 */
@Entity
@Table(name = "HR_JOB_TYPES")
@XmlRootElement
@NamedQueries//<editor-fold defaultstate="collapsed" desc="">
        ({
            @NamedQuery(name = "HrJobTypes.findAll", query = "SELECT h FROM HrJobTypes h ORDER BY h.jobTitle ASC"),
            @NamedQuery(name = "HrJobTypes.findById", query = "SELECT h FROM HrJobTypes h WHERE h.id = :id"),
            @NamedQuery(name = "HrJobTypes.findByJobCode", query = "SELECT h FROM HrJobTypes h WHERE h.jobCode = :jobCode"),
            @NamedQuery(name = "HrJobTypes.findByDeptId", query = "SELECT h FROM HrJobTypes h WHERE h.depId = :depId"),
            @NamedQuery(name = "HrJobTypes.findByJobTitle", query = "SELECT h FROM HrJobTypes h WHERE h.jobTitle = :jobTitle"),
            @NamedQuery(name = "HrJobTypes.findByJobSummary", query = "SELECT h FROM HrJobTypes h WHERE h.jobSummary = :jobSummary"),
            @NamedQuery(name = "HrJobTypes.findByKeyResultArea", query = "SELECT h FROM HrJobTypes h WHERE h.keyResultArea = :keyResultArea"),
            @NamedQuery(name = "HrJobTypes.findByJobDescription", query = "SELECT h FROM HrJobTypes h WHERE h.jobDescription = :jobDescription"),
            @NamedQuery(name = "HrJobTypes.findByWorkingCondition", query = "SELECT h FROM HrJobTypes h WHERE h.workingCondition = :workingCondition"),
            @NamedQuery(name = "HrJobTypes.findByAdditionalSkill", query = "SELECT h FROM HrJobTypes h WHERE h.additionalSkill = :additionalSkill"),
            @NamedQuery(name = "HrJobTypes.findByRelevantExperiance", query = "SELECT h FROM HrJobTypes h WHERE h.relevantExperiance = :relevantExperiance"),
            @NamedQuery(name = "HrJobTypes.findByAttitudes", query = "SELECT h FROM HrJobTypes h WHERE h.attitudes = :attitudes"),
            @NamedQuery(name = "HrJobTypes.findByReportTo", query = "SELECT h FROM HrJobTypes h WHERE h.reportTo = :reportTo"),
            @NamedQuery(name = "HrJobTypes.findByNoEmpNeeded", query = "SELECT h FROM HrJobTypes h WHERE h.noEmpNeeded = :noEmpNeeded"),
            @NamedQuery(name = "HrJobTypes.findByJobTitleLike", query = "SELECT h FROM HrJobTypes h WHERE UPPER(h.jobTitle) LIKE UPPER(:jobTitle)"),
            @NamedQuery(name = "HrJobTypes.findDuplicateJobTitleOrJobCode", query = "SELECT h FROM HrJobTypes h WHERE h.jobTitle = :jobTitle or h.jobCode = :jobCode"),
            @NamedQuery(name = "HrJobTypes.findByJobCodeLike", query = "SELECT h FROM HrJobTypes h WHERE UPPER(h.jobCode) LIKE UPPER(:jobCode)")})
//</editor-fold>
public class HrJobTypes implements Serializable {

    //<editor-fold defaultstate="collapsed" desc="Entity Attributes and Relations">
    @OneToMany(mappedBy = "jobTitleId")
    private List<HrAllowanceInJobTitle> hrAllowanceInJobTitleList;
    @OneToMany(mappedBy = "jobId")
    private List<HrSmKmp> hrSmKmpList;
    @OneToMany(mappedBy = "jobId", cascade = CascadeType.ALL)
    private List<HrRecruitmentRequests> hrRecruitmentRequestsList = new ArrayList();
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HR_JOB_TYPES_SEQ")
    @SequenceGenerator(name = "HR_JOB_TYPES_SEQ", sequenceName = "HR_JOB_TYPES_SEQ", allocationSize = 1)
    private Integer id;
    @Size(max = 20)
    @Column(name = "JOB_CODE")
    private String jobCode;
    @Column(name = "TYPE")
    private BigInteger type;
    @Column(name = "JOB_TITLE")
    private String jobTitle;
    @Size(max = 100)
    @Column(name = "JOB_SUMMARY")
    private String jobSummary;
    @Size(max = 100)
    @Column(name = "REMARK")
    private String remark;
    @Size(max = 100)
    @Column(name = "KEY_RESULT_AREA")
    private String keyResultArea;
    @Column(name = "JOB_DESCRIPTION")
    private String jobDescription;
    @Size(max = 100)
    @Column(name = "WORKING_CONDITION")
    private String workingCondition;
    @Size(max = 100)
    @Column(name = "ADDITIONAL_SKILL")
    private String additionalSkill;
    @Column(name = "RELEVANT_EXPERIANCE")
    private String relevantExperiance;
    @Size(max = 100)
    @Column(name = "ATTITUDES")
    private String attitudes;
    @Column(name = "REPORT_TO")
    private BigInteger reportTo;
    @Column(name = "NO_EMP_NEEDED")
    private BigInteger noEmpNeeded;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "jobId")
    private List<HrJobEducQualifications> hrJobEducQualificationsList = new ArrayList<>();
    @JoinColumn(name = "DEP_ID", referencedColumnName = "DEP_ID")
    @ManyToOne
    private HrDepartments depId;
    @JoinColumn(name = "JOB_LEVEL_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrLuJobLevels jobLevelId;
    @JoinColumn(name = "JOB_GRADE_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrSalaryScaleRanges jobGradeId;
    @OneToMany(mappedBy = "jobId")
    private List<HrEmployees> hrEmployeesList;
    @OneToMany(mappedBy = "jobId")
    private List<HrAdvertisedJobs> hrAdvertisedJobsList;
    
    @Transient
    private String Col_Value;

    public String getCol_Value() {
        return Col_Value;
    }

    public void setCol_Value(String Col_Value) {
        this.Col_Value = Col_Value;
    }

      //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="getter and setter">
    public HrJobTypes() {
    }

    /**
     *
     * @param id
     */
    public HrJobTypes(Integer id) {
        this.id = id;
    }

    /**
     *
     * @return
     */
    public Integer getId() {
        return id;
    }

    /**
     *
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     *
     * @return
     */
    public String getJobCode() {
        return jobCode;
    }

    /**
     *
     * @param jobCode
     */
    public void setJobCode(String jobCode) {
        this.jobCode = jobCode;
    }

    /**
     *
     * @return
     */
    public String getJobTitle() {
        return jobTitle;
    }

    /**
     *
     * @param jobTitle
     */
    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    /**
     *
     * @return
     */
    public String getJobSummary() {
        return jobSummary;
    }

    /**
     *
     * @param jobSummary
     */
    public void setJobSummary(String jobSummary) {
        this.jobSummary = jobSummary;
    }

    /**
     *
     * @return
     */
    public String getKeyResultArea() {
        return keyResultArea;
    }

    /**
     *
     * @param keyResultArea
     */
    public void setKeyResultArea(String keyResultArea) {
        this.keyResultArea = keyResultArea;
    }

    /**
     *
     * @return
     */
    public String getJobDescription() {
        return jobDescription;
    }

    /**
     *
     * @param jobDescription
     */
    public void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
    }

    /**
     *
     * @return
     */
    public String getWorkingCondition() {
        return workingCondition;
    }

    /**
     *
     * @param workingCondition
     */
    public void setWorkingCondition(String workingCondition) {
        this.workingCondition = workingCondition;
    }

    /**
     *
     * @return
     */
    public String getAdditionalSkill() {
        return additionalSkill;
    }

    /**
     *
     * @param additionalSkill
     */
    public void setAdditionalSkill(String additionalSkill) {
        this.additionalSkill = additionalSkill;
    }

    /**
     *
     * @return
     */
    public String getRelevantExperiance() {
        return relevantExperiance;
    }

    /**
     *
     * @param relevantExperiance
     */
    public void setRelevantExperiance(String relevantExperiance) {
        this.relevantExperiance = relevantExperiance;
    }

    /**
     *
     * @return
     */
    public String getAttitudes() {
        return attitudes;
    }

    /**
     *
     * @param attitudes
     */
    public void setAttitudes(String attitudes) {
        this.attitudes = attitudes;
    }

    /**
     *
     * @return
     */
    public BigInteger getReportTo() {
        return reportTo;
    }

    /**
     *
     * @param reportTo
     */
    public void setReportTo(BigInteger reportTo) {
        this.reportTo = reportTo;
    }

    /**
     *
     * @return
     */
    public BigInteger getNoEmpNeeded() {
        return noEmpNeeded;
    }

    /**
     *
     * @param noEmpNeeded
     */
    public void setNoEmpNeeded(BigInteger noEmpNeeded) {
        this.noEmpNeeded = noEmpNeeded;
    }

    public BigInteger getType() {
        return type;
    }

    public void setType(BigInteger type) {
        this.type = type;
    }

    /**
     *
     * @return
     */
    @XmlTransient
    public List<HrJobEducQualifications> getHrJobEducQualificationsList() {
        if (hrJobEducQualificationsList == null) {
            hrJobEducQualificationsList = new ArrayList<>();
        }
        return hrJobEducQualificationsList;
    }

    /**
     *
     * @param hrJobEducQualificationsList
     */
    public void setHrJobEducQualificationsList(List<HrJobEducQualifications> hrJobEducQualificationsList) {
        this.hrJobEducQualificationsList = hrJobEducQualificationsList;
    }

    /**
     *
     * @return
     */
    public HrDepartments getDepId() {
        return depId;
    }

    /**
     *
     * @param depId
     */
    public void setDepId(HrDepartments depId) {
        this.depId = depId;
    }

    /**
     *
     * @return
     */
    public HrLuJobLevels getJobLevelId() {
        return jobLevelId;
    }

    /**
     *
     * @param jobLevelId
     */
    public void setJobLevelId(HrLuJobLevels jobLevelId) {
        this.jobLevelId = jobLevelId;
    }

    /**
     *
     * @return
     */
    public HrSalaryScaleRanges getJobGradeId() {
        return jobGradeId;
    }

    /**
     *
     * @param jobGradeId
     */
    public void setJobGradeId(HrSalaryScaleRanges jobGradeId) {
        this.jobGradeId = jobGradeId;
    }

    /**
     *
     * @return
     */
    @XmlTransient
    public List<HrEmployees> getHrEmployeesList() {
        return hrEmployeesList;
    }

    /**
     *
     * @param hrEmployeesList
     */
    public void setHrEmployeesList(List<HrEmployees> hrEmployeesList) {
        this.hrEmployeesList = hrEmployeesList;
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
        if (!(object instanceof HrJobTypes)) {
            return false;
        }
        HrJobTypes other = (HrJobTypes) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return jobTitle;
    }

    /**
     *
     * @param hrJobEducQualifications
     */
    public void addJobEducQual(HrJobEducQualifications hrJobEducQualifications) {
        if (hrJobEducQualifications.getJobId() != this) {
            this.getHrJobEducQualificationsList().add(hrJobEducQualifications);
            hrJobEducQualifications.setJobId(this);
        }
    }

    /**
     *
     * @return
     */
    @XmlTransient
    public List<HrAdvertisedJobs> getHrAdvertisedJobsList() {
        return hrAdvertisedJobsList;
    }

    /**
     *
     * @param hrAdvertisedJobsList
     */
    public void setHrAdvertisedJobsList(List<HrAdvertisedJobs> hrAdvertisedJobsList) {
        this.hrAdvertisedJobsList = hrAdvertisedJobsList;
    }

    @XmlTransient
    public List<HrRecruitmentRequests> getHrRecruitmentRequestsList() {
        return hrRecruitmentRequestsList;
    }

    public void setHrRecruitmentRequestsList(List<HrRecruitmentRequests> hrRecruitmentRequestsList) {
        this.hrRecruitmentRequestsList = hrRecruitmentRequestsList;
    }

    @XmlTransient
    public List<HrSmKmp> getHrSmKmpList() {
        return hrSmKmpList;
    }

    public void setHrSmKmpList(List<HrSmKmp> hrSmKmpList) {
        this.hrSmKmpList = hrSmKmpList;
    }

    @XmlTransient
    public List<HrAllowanceInJobTitle> getHrAllowanceInJobTitleList() {
        return hrAllowanceInJobTitleList;
    }

    public void setHrAllowanceInJobTitleList(List<HrAllowanceInJobTitle> hrAllowanceInJobTitleList) {
        this.hrAllowanceInJobTitleList = hrAllowanceInJobTitleList;
    }
//</editor-fold>

}
