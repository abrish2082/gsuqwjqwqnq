/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.mms.entity;

import et.gov.eep.commonApplications.entity.WfMmsProcessed;
import et.gov.eep.fcms.entity.FmsLuBudgetYear;
import et.gov.eep.hrms.entity.organization.HrDepartments;
import et.gov.eep.mms.entity.MmsNeedAssessmentService;
import et.gov.eep.prms.entity.PrmsPurchasePlan;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Sadik
 */
@Entity
@Table(name = "MMS_NEED_ASSESSMENT", catalog = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MmsNeedAssessment.findAll", query = "SELECT m FROM MmsNeedAssessment m"),
    @NamedQuery(name = "MmsNeedAssessment.findByAssessmetnId", query = "SELECT m FROM MmsNeedAssessment m WHERE m.assessmetnId = :assessmetnId"),
    @NamedQuery(name = "MmsNeedAssessment.findByBudyear_Depid_budjtype", query = "SELECT m FROM MmsNeedAssessment m WHERE m.budgetYear = :budyear AND m.budgetType = :budjType AND m.departmentId = :depId"),
    @NamedQuery(name = "MmsNeedAssessment.findByParameterBudgetyearAndDepartment", query = "SELECT m FROM MmsNeedAssessment m WHERE m.budgetYear LIKE :budgetYear AND m.departmentId = :departmentId"),
    @NamedQuery(name = "MmsNeedAssessment.findByParameterBudgetTypeAndDepartmentAndProcessedBy", query = "SELECT m FROM MmsNeedAssessment m WHERE m.budgetType LIKE :budgetType AND m.departmentId = :departmentId AND m.processedBy=:processedBy"),
    @NamedQuery(name = "MmsNeedAssessment.findByParameterBudgetTypeDepartmentAndBudgetYear", query = "SELECT m FROM MmsNeedAssessment m WHERE m.budgetType LIKE :budgetType AND m.departmentId = :departmentId AND m.budgetYear LIKE :budgetYear"),

    @NamedQuery(name = "MmsNeedAssessment.findNsListByWfStatus", query = "SELECT m FROM MmsNeedAssessment m WHERE m.status=:status"),
    @NamedQuery(name = "MmsNeedAssessment.findNsListForCheckerByWfStatus", query = "SELECT m FROM MmsNeedAssessment m WHERE m.status=:prepared OR m.status=:approverReject"),
    @NamedQuery(name = "MmsNeedAssessment.findByParameterDepartmentIdAndProcessedBy", query = "SELECT m FROM MmsNeedAssessment m WHERE m.departmentId = :departmentId AND m.processedBy=:processedBy"),
    @NamedQuery(name = "MmsNeedAssessment.findByBudgetYearAndStore", query = "SELECT m FROM MmsNeedAssessment m WHERE m.budgetYear = :budgetYear AND m.storeId= :storeId"),
    @NamedQuery(name = "MmsNeedAssessment.findByBudgetYearAndStoreAndCheckedStatus", query = "SELECT m FROM MmsNeedAssessment m WHERE m.budgetYearId = :budgetYear AND m.storeId= :storeId AND m.checkedStatus=:checkedStatus AND m.status=:status AND m.purchaseType =:purchaseType"),
    @NamedQuery(name = "MmsNeedAssessment.findByBudgetYearAndCheckedStatus", query = "SELECT m FROM MmsNeedAssessment m WHERE m.budgetYearId = :budgetYear AND m.checkedStatus=:checkedStatus AND m.status=:status AND m.purchaseType =:purchaseType"),
    @NamedQuery(name = "MmsNeedAssessment.findByBudgetYears", query = "SELECT m FROM MmsNeedAssessment m WHERE m.budgetYear IS NOT NULL"),
    @NamedQuery(name = "MmsNeedAssessment.findByProcessedBy", query = "SELECT m FROM MmsNeedAssessment m WHERE m.processedBy = :processedBy"),
    @NamedQuery(name = "MmsNeedAssessment.findByPreparedDate", query = "SELECT m FROM MmsNeedAssessment m WHERE m.preparedDate = :preparedDate"),
    @NamedQuery(name = "MmsNeedAssessment.findByBudgetType", query = "SELECT m FROM MmsNeedAssessment m WHERE m.budgetType = :budgetType"),
    @NamedQuery(name = "MmsNeedAssessment.findByCheckdedDate", query = "SELECT m FROM MmsNeedAssessment m WHERE m.checkdedDate = :checkdedDate"),
    @NamedQuery(name = "MmsNeedAssessment.findByCheckdedBy", query = "SELECT m FROM MmsNeedAssessment m WHERE m.checkdedBy = :checkdedBy"),
    @NamedQuery(name = "MmsNeedAssessment.findByCheckedStatus", query = "SELECT m FROM MmsNeedAssessment m WHERE m.checkedStatus = :checkedStatus")})
public class MmsNeedAssessment implements Serializable {

    @OneToMany(mappedBy = "needAssmntId")
    private List<PrmsPurchasePlan> prmsPurchasePlanList;
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MMS_NEED_ASS")
    @SequenceGenerator(name = "MMS_NEED_ASS", sequenceName = "MMS_NEED_ASS", allocationSize = 1)
    @Column(name = "ASSESSMETN_ID", nullable = false, precision = 0, scale = -127)
    private Integer assessmetnId;
    @Size(max = 20)
    @Column(name = "BUDGET_YEAR", length = 20)
    private String budgetYear;
    @Size(max = 20)
    @Column(name = "PREPARED_BY", length = 20)
    private String preparedBy;
    @Size(max = 20)
    @Column(name = "PREPARED_DATE")

    private String preparedDate;
    @Size(max = 20)
    @Column(name = "BUDGET_TYPE", length = 20)
    private String budgetType;
    @Size(max = 20)
    @Column(name = "PURCHASE_TYPE", length = 20)
    private String purchaseType;
    @Size(max = 20)
    @Column(name = "CHECKDED_DATE")

    private String checkdedDate;
  //<editor-fold defaultstate="collapsed" desc="Dynamic Searching Transient Parameters">
    @Transient
    private String columnName;
    @Transient
    private String columnValue;
    //</editor-fold >
    @Column(name = "CHECKDED_BY")
    private Integer checkdedBy;
    @Size(max = 20)
    @Column(name = "CHECKED_STATUS", length = 20)
    private String checkedStatus;
    @Size(max = 50)
    @Column(name = "COMMENT_GIVEN")
    private String commentGiven;
    @Size(max = 20)
    @Column(name = "PROCESSED_ON")
    private String processedOn;

    @Column(name = "PROCESSED_BY")
    private Integer processedBy;
    @Column(name = "STATUS")
    private Integer status;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "assessmetId", fetch = FetchType.LAZY)
    private List<MmsNeedAssessmentDtl> mmsNeedAssessmentDtlList;
    @JoinColumn(name = "DEPARTMENT_ID", referencedColumnName = "DEP_ID")
    @ManyToOne
    private HrDepartments departmentId;
    @JoinColumn(name = "STORE_ID", referencedColumnName = "STORE_ID")
    @ManyToOne
    private MmsStoreInformation storeId;

    @JoinColumn(name = "BUDGET_YEAR_ID", referencedColumnName = "LU_BUDGET_YEAR_ID")
    @ManyToOne
    private FmsLuBudgetYear budgetYearId;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "assessmentId", fetch = FetchType.LAZY)
    private List<MmsNeedAssessmentService> mmsNeedAssessmentServiceList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "assessmetnId")
    private List<WfMmsProcessed> wfMmsProcessedList;

    public MmsNeedAssessment() {
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnValue() {
        return columnValue;
    }

    public void setColumnValue(String columnValue) {
        this.columnValue = columnValue;
    }

    public String getCommentGiven() {
        return commentGiven;
    }

    public void setCommentGiven(String commentGiven) {
        this.commentGiven = commentGiven;
    }

    public String getProcessedOn() {
        return processedOn;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setProcessedOn(String processedOn) {
        this.processedOn = processedOn;
    }

    public Integer getProcessedBy() {
        return processedBy;
    }

    public void setProcessedBy(Integer processedBy) {
        this.processedBy = processedBy;
    }

    public MmsNeedAssessment(Integer assessmetnId) {
        this.assessmetnId = assessmetnId;
    }

    public Integer getAssessmetnId() {
        return assessmetnId;
    }

    public void setAssessmetnId(Integer assessmetnId) {
        this.assessmetnId = assessmetnId;
    }

    public String getBudgetYear() {
        return budgetYear;
    }

    public void setBudgetYear(String budgetYear) {
        this.budgetYear = budgetYear;
    }

    public String getPurchaseType() {
        return purchaseType;
    }

    public void setPurchaseType(String purchaseType) {
        this.purchaseType = purchaseType;
    }

    public String getPreparedBy() {
        return preparedBy;
    }

    public void setPreparedBy(String preparedBy) {
        this.preparedBy = preparedBy;
    }

    public String getPreparedDate() {
        return preparedDate;
    }

    public void setPreparedDate(String preparedDate) {
        this.preparedDate = preparedDate;
    }

    public String getBudgetType() {
        return budgetType;
    }

    public void setBudgetType(String budgetType) {
        this.budgetType = budgetType;
    }

    public String getCheckdedDate() {
        return checkdedDate;
    }

    public void setCheckdedDate(String checkdedDate) {
        this.checkdedDate = checkdedDate;
    }

    public Integer getCheckdedBy() {
        return checkdedBy;
    }

    public void setCheckdedBy(Integer checkdedBy) {
        this.checkdedBy = checkdedBy;
    }

    public String getCheckedStatus() {
        return checkedStatus;
    }

    public void setCheckedStatus(String checkedStatus) {
        this.checkedStatus = checkedStatus;
    }

    @XmlTransient
    public List<MmsNeedAssessmentDtl> getMmsNeedAssessmentDtlList() {
        if (mmsNeedAssessmentDtlList == null) {
            mmsNeedAssessmentDtlList = new ArrayList<>();

        }
        return mmsNeedAssessmentDtlList;
    }

    public void setMmsNeedAssessmentDtlList(List<MmsNeedAssessmentDtl> mmsNeedAssessmentDtlList) {
        this.mmsNeedAssessmentDtlList = mmsNeedAssessmentDtlList;
    }

    public HrDepartments getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(HrDepartments departmentId) {
        this.departmentId = departmentId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (assessmetnId != null ? assessmetnId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MmsNeedAssessment)) {
            return false;
        }
        MmsNeedAssessment other = (MmsNeedAssessment) object;
        if ((this.assessmetnId == null && other.assessmetnId != null) || (this.assessmetnId != null && !this.assessmetnId.equals(other.assessmetnId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return budgetYear;
    }

    public void addToNeedAssessmentDtlInfo(MmsNeedAssessmentDtl mmsNeedAssessmentDtlObj) {

        if (mmsNeedAssessmentDtlObj.getAssessmetId() != this) {

            this.getMmsNeedAssessmentDtlList().add(mmsNeedAssessmentDtlObj);
            mmsNeedAssessmentDtlObj.setAssessmetId(this);
        }
    }

    public void addToNeedAssessmentServiceDtl(MmsNeedAssessmentService mmsNeedAssessmentDtlObj) {

        if (mmsNeedAssessmentDtlObj.getAssessmentId() != this) {
            this.getMmsNeedAssessmentServiceList().add(mmsNeedAssessmentDtlObj);

            mmsNeedAssessmentDtlObj.setAssessmentId(this);
        }
    }

    @XmlTransient
    public List<PrmsPurchasePlan> getPrmsPurchasePlanList() {
        return prmsPurchasePlanList;
    }

    public void setPrmsPurchasePlanList(List<PrmsPurchasePlan> prmsPurchasePlanList) {
        this.prmsPurchasePlanList = prmsPurchasePlanList;
    }

    public MmsStoreInformation getStoreId() {
        return storeId;
    }

    public void setStoreId(MmsStoreInformation storeId) {
        this.storeId = storeId;
    }

    @XmlTransient
    public List<MmsNeedAssessmentService> getMmsNeedAssessmentServiceList() {
        if (mmsNeedAssessmentServiceList == null) {
            mmsNeedAssessmentServiceList = new ArrayList<>();
        }
        return mmsNeedAssessmentServiceList;
    }

    public void setMmsNeedAssessmentServiceList(List<MmsNeedAssessmentService> mmsNeedAssessmentServiceList) {
        this.mmsNeedAssessmentServiceList = mmsNeedAssessmentServiceList;
    }

    public FmsLuBudgetYear getBudgetYearId() {
        return budgetYearId;
    }

    public void setBudgetYearId(FmsLuBudgetYear budgetYearId) {
        this.budgetYearId = budgetYearId;
    }

    @XmlTransient
    public List<WfMmsProcessed> getWfMmsProcessedList() {

        if (wfMmsProcessedList == null) {
            wfMmsProcessedList = new ArrayList<>();
        }
        return wfMmsProcessedList;
    }

    public void setWfMmsProcessedList(List<WfMmsProcessed> wfMmsProcessedList) {
        this.wfMmsProcessedList = wfMmsProcessedList;
    }

     public void addNeedAssesmentIdToWorkflow(WfMmsProcessed wfMmsProcessed) {
        if (wfMmsProcessed.getAssessmetnId() != this) {
            this.getWfMmsProcessedList().add(wfMmsProcessed);
            
            wfMmsProcessed.setAssessmetnId(this);
        }
    }
}
