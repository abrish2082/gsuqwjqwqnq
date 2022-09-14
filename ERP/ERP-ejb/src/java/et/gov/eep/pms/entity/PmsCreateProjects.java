/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.pms.entity;

import et.gov.eep.hrms.entity.allowance.PmsSourceType;
import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.prms.entity.PrmsBid;
import et.gov.eep.prms.entity.PrmsBidAmend;
import et.gov.eep.prms.entity.PrmsContract;
import et.gov.eep.prms.entity.PrmsContractAmendment;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
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
@Table(name = "PMS_CREATE_PROJECTS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PmsCreateProjects.findAll", query = "SELECT p FROM PmsCreateProjects p"),
    @NamedQuery(name = "PmsCreateProjects.findByProjectId", query = "SELECT p FROM PmsCreateProjects p WHERE p.projectId = :projectId"),
    @NamedQuery(name = "PmsCreateProjects.findByProjectName", query = "SELECT p FROM PmsCreateProjects p WHERE p.projectName = :projectName"),
//    @NamedQuery(name = "PmsCreateProjects.findByFundRaiser", query = "SELECT p FROM PmsCreateProjects p WHERE p.fundRaiser = :fundRaiser"),
    @NamedQuery(name = "PmsCreateProjects.findBySiteName", query = "SELECT p FROM PmsCreateProjects p WHERE p.siteName = :siteName"),
//    @NamedQuery(name = "PmsCreateProjects.findByProjectManager", query = "SELECT p FROM PmsCreateProjects p WHERE p.projectManager = :projectManager"),
    @NamedQuery(name = "PmsCreateProjects.findBySiteLocation", query = "SELECT p FROM PmsCreateProjects p WHERE p.siteLocation = :siteLocation"),
//    @NamedQuery(name = "PmsCreateProjects.findByProjectCreatedDate", query = "SELECT p FROM PmsCreateProjects p WHERE p.projectCreatedDate = :projectCreatedDate"),
    //  @NamedQuery(name = "PmsCreateProjects.findBySourceOfBudget", query = "SELECT p FROM PmsCreateProjects p WHERE p.sourceOfBudget = :sourceOfBudget"),
//    @NamedQuery(name = "PmsCreateProjects.findByAmountOfBudget", query = "SELECT p FROM PmsCreateProjects p WHERE p.estimatedProjectCost = :estimatedProjectCost"),
//    @NamedQuery(name = "PmsCreateProjects.findByCreatedBy", query = "SELECT p FROM PmsCreateProjects p WHERE p.createdBy = :createdBy"),
    @NamedQuery(name = "PmsCreateProjects.findByStartDate", query = "SELECT p FROM PmsCreateProjects p WHERE p.startDate = :startDate")})
//    @NamedQuery(name = "PmsCreateProjects.findByFinishDate", query = "SELECT p FROM PmsCreateProjects p WHERE p.finishDate = :finishDate")
public class PmsCreateProjects implements Serializable {

    @Size(max = 50)
    @Column(name = "EMPLOYEE_DEPARTMENT_ID")
    private String employeeDepartmentId;
    @Column(name = "WORKFLOW_CURRENT_STATUS")
    private BigInteger workflowCurrentStatus;
//    @Column(name = "LOCAL_ESTIMATED_COST")
//    private Double localEstimatedCost;
//    @Column(name = "FOREIGN_ESTIMATED_COST")
//    private Double foreignEstimatedCost;
    @Size(max = 100)
    @Column(name = "VOLTAGE_LEVEL")
    private String voltageLevel;
    @Size(max = 100)
    @Column(name = "DISTANCE")
    private String distance;
//    @Column(name = "OVERHEAD_ESTIMATED_COST")
//    private Double overheadEstimatedCost;
    @Size(max = 500)
    @Column(name = "CONTRACT_TYPE")
    private String contractType;
//    @Size(max = 20)
//    @Column(name = "SOURCE_OF_BUDGET")
//    private String sourceOfBudget;
//    @OneToMany(mappedBy = "projectName")
//    private List<PrmsProjectPlan> prmsProjectPlanList;

//    @Column(name = "STATUS")
//    private BigInteger status;
    @Column(name = "CLOSE_DATE")
    @Temporal(TemporalType.DATE)
    private Date closeDate;
    @Size(max = 20)
    @Column(name = "CLOSE_STATUS")
    private String closeStatus;
    @Size(max = 200)
    @Column(name = "CLOSE_REMARK")
    private String closeRemark;
    @OneToMany(mappedBy = "projectId")
    private List<PrmsContract> prmsContractList;
//    @OneToMany(mappedBy = "projectname")
//    private List<PrmsProjectPlan> prmsProjectPlanList;

//    @Size(max = 100)
//    @Column(name = "FINANCER")
//    private String financer;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
//    @Column(name = "ESTIMATED_PROJECT_COST")
//    private Double estimatedProjectCost;
    @Column(name = "COMPLETION_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date completionDate;
//    @Size(max = 50)
//    @Column(name = "LOAN_NUMBER")
//    private String loanNumber;
    @Size(max = 2000)
    @Column(name = "DESCRIPTION")
    private String description;
//    @JoinColumn(name = "CURRENCY_ID", referencedColumnName = "CURRENCY_ID")
//    @ManyToOne
//    private FmsLuCurrency currencyId;
    @JoinColumn(name = "SOURCE_TYPE_ID", referencedColumnName = "SOURCE_TYPE_ID")
    @ManyToOne
    private PmsSourceType sourceTypeId;
    @Column(name = "STATUS_CHANGED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date statusChangedDate;
    @OneToMany(mappedBy = "projectId")
    private List<PmsWorkAuthorization> pmsWorkAuthorizationList;
    @OneToMany(mappedBy = "projectId")
    private List<PrmsBidAmend> prmsBidAmendList;
    @OneToMany(mappedBy = "projectId")
    private List<PrmsBid> prmsBidList;
    @OneToMany(mappedBy = "projectId")
    private List<PmsProjectPaymentRequest> pmsProjectPaymentReqList;
    @OneToMany(mappedBy = "projectId", fetch = FetchType.LAZY)
    private List<PrmsContractAmendment> prmsContractAmendmentList;
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "PROJECT_ID")
    private String projectId;
    @Column(name = "PROJECT_NAME")
    private String projectName;
//    @Column(name = "FUND_RAISER")
//    private String fundRaiser;
    @Size(max = 500)
    @Column(name = "SITE_NAME")
    private String siteName;
//    @Column(name = "PROJECT_MANAGER")
//    private String projectManager;
    @Size(max = 500)
    @Column(name = "SITE_LOCATION")
    private String siteLocation;
//    @Column(name = "PROJECT_CREATED_DATE")
//    @Temporal(TemporalType.TIMESTAMP)
//    private Date projectCreatedDate;
//    @Column(name = "SOURCE_OF_BUDGET")
//    private String sourceOfBudget;
//    @Size(max = 50)
//    @Column(name = "CREATED_BY", length = 50)
//    private String createdBy;
    @Column(name = "START_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;
//    @Column(name = "FINISH_DATE")
//    @Temporal(TemporalType.TIMESTAMP)
//    private Date finishDate;
    @Size(max = 500)
    @Column(name = "CAPACITY", length = 500)
    private String capacity;
    @JoinColumn(name = "ID", referencedColumnName = "ID")
    @ManyToOne
    private HrEmployees id;
    @JoinColumn(name = "PORTFOLIO_ID", referencedColumnName = "PORTFOLIO_ID")
    @ManyToOne
    private PmsCreatePortfolio portfolioId;
    @JoinColumn(name = "PROGRAM_ID", referencedColumnName = "PROGRAM_ID")
    @ManyToOne
    private PmsMaintainProg programId;
    @JoinColumn(name = "PROJECT_TYPE_ID", referencedColumnName = "PROJECT_TYPE_ID")
    @ManyToOne
    private PmsProjectType projectTypeId;
    @JoinColumn(name = "STATUS_ID", referencedColumnName = "STATUS_ID")
    @ManyToOne
    private PmsStatus statusId;
    
    public PmsCreateProjects() {
    }

    public PmsCreateProjects(String projectId) {
        this.projectId = projectId;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

//    public String getFundRaiser() {
//        return fundRaiser;
//    }
//
//    public void setFundRaiser(String fundRaiser) {
//        this.fundRaiser = fundRaiser;
//    }
    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

//    public String getProjectManager() {
//        return projectManager;
//    }
//
//    public void setProjectManager(String projectManager) {
//        this.projectManager = projectManager;
//    }
    public String getSiteLocation() {
        return siteLocation;
    }

    public void setSiteLocation(String siteLocation) {
        this.siteLocation = siteLocation;
    }

//    public Date getProjectCreatedDate() {
//        return projectCreatedDate;
//    }
//
//    public void setProjectCreatedDate(Date projectCreatedDate) {
//        this.projectCreatedDate = projectCreatedDate;
//    }
//    public String getSourceOfBudget() {
//        return sourceOfBudget;
//    }
//
//    public void setSourceOfBudget(String sourceOfBudget) {
//        this.sourceOfBudget = sourceOfBudget;
//    }
//    public double getEstimatedProjectCost() {
//        return estimatedProjectCost;
//    }
//
//    public void setEstimatedProjectCost(double estimatedProjectCost) {
//        this.estimatedProjectCost = estimatedProjectCost;
//    }
//    public String getCreatedBy() {
//        return createdBy;
//    }
//
//    public void setCreatedBy(String createdBy) {
//        this.createdBy = createdBy;
//    }
    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getStatusChangedDate() {
        return statusChangedDate;
    }

    public void setStatusChangedDate(Date statusChangedDate) {
        this.statusChangedDate = statusChangedDate;
    }

    public String getVoltageLevel() {
        return voltageLevel;
    }

    public void setVoltageLevel(String voltageLevel) {
        this.voltageLevel = voltageLevel;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

//    public Date getFinishDate() {
//        return finishDate;
//    }
//
//    public void setFinishDate(Date finishDate) {
//        this.finishDate = finishDate;
//    }
    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public HrEmployees getId() {
        return id;
    }

    public void setId(HrEmployees id) {
        this.id = id;
    }

    public PmsCreatePortfolio getPortfolioId() {
        return portfolioId;
    }

    public void setPortfolioId(PmsCreatePortfolio portfolioId) {
        this.portfolioId = portfolioId;
    }

    public PmsMaintainProg getProgramId() {
        return programId;
    }

    public void setProgramId(PmsMaintainProg programId) {
        this.programId = programId;
    }

    public PmsProjectType getProjectTypeId() {
        return projectTypeId;
    }

    public void setProjectTypeId(PmsProjectType projectTypeId) {
        this.projectTypeId = projectTypeId;
    }

    public PmsStatus getStatusId() {
        return statusId;
    }

    public void setStatusId(PmsStatus statusId) {
        this.statusId = statusId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (projectId != null ? projectId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PmsCreateProjects)) {
            return false;
        }
        PmsCreateProjects other = (PmsCreateProjects) object;
        if ((this.projectId == null && other.projectId != null) || (this.projectId != null && !this.projectId.equals(other.projectId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return projectName;
    }

    @XmlTransient
    public List<PmsWorkAuthorization> getPmsWorkAuthorizationList() {
        return pmsWorkAuthorizationList;
    }

    public void setPmsWorkAuthorizationList(List<PmsWorkAuthorization> pmsWorkAuthorizationList) {
        this.pmsWorkAuthorizationList = pmsWorkAuthorizationList;
    }

    @XmlTransient
    public List<PrmsContractAmendment> getPrmsContractAmendmentList() {
        return prmsContractAmendmentList;
    }

    public void setPrmsContractAmendmentList(List<PrmsContractAmendment> prmsContractAmendmentList) {
        this.prmsContractAmendmentList = prmsContractAmendmentList;
    }

    @XmlTransient

    public List<PmsProjectPaymentRequest> getPmsProjectPaymentReqList() {
        return pmsProjectPaymentReqList;
    }

    public void setPmsProjectPaymentReqList(List<PmsProjectPaymentRequest> pmsProjectPaymentReqList) {
        this.pmsProjectPaymentReqList = pmsProjectPaymentReqList;
    }

    @XmlTransient

    public List<PrmsBid> getPrmsBidList() {
        return prmsBidList;
    }

    public void setPrmsBidList(List<PrmsBid> prmsBidList) {
        this.prmsBidList = prmsBidList;
    }

    @XmlTransient

    public List<PrmsBidAmend> getPrmsBidAmendList() {
        return prmsBidAmendList;
    }

    public void setPrmsBidAmendList(List<PrmsBidAmend> prmsBidAmendList) {
        this.prmsBidAmendList = prmsBidAmendList;
    }

//    public String getFinancer() {
//        return financer;
//    }
//
//    public void setFinancer(String financer) {
//        this.financer = financer;
//    }
//    public Double getEstimatedProjectCost() {
//        return estimatedProjectCost;
//    }
//
//    public void setEstimatedProjectCost(Double estimatedProjectCost) {
//        this.estimatedProjectCost = estimatedProjectCost;
//    }
    public Date getCompletionDate() {
        return completionDate;
    }

    public void setCompletionDate(Date completionDate) {
        this.completionDate = completionDate;
    }

//    public String getLoanNumber() {
//        return loanNumber;
//    }
//
//    public void setLoanNumber(String loanNumber) {
//        this.loanNumber = loanNumber;
//    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

//    public FmsLuCurrency getCurrencyId() {
//        return currencyId;
//    }
//
//    public void setCurrencyId(FmsLuCurrency currencyId) {
//        this.currencyId = currencyId;
//    }
    public PmsSourceType getSourceTypeId() {
        return sourceTypeId;
    }

    public void setSourceTypeId(PmsSourceType sourceTypeId) {
        this.sourceTypeId = sourceTypeId;
    }

    public String getEmployeeDepartmentId() {
        return employeeDepartmentId;
    }

    public void setEmployeeDepartmentId(String employeeDepartmentId) {
        this.employeeDepartmentId = employeeDepartmentId;
    }

    public BigInteger getWorkflowCurrentStatus() {
        return workflowCurrentStatus;
    }

    public void setWorkflowCurrentStatus(BigInteger workflowCurrentStatus) {
        this.workflowCurrentStatus = workflowCurrentStatus;
    }

    public String getContractType() {
        return contractType;
    }

    public void setContractType(String contractType) {
        this.contractType = contractType;
    }

    @XmlTransient
    public List<PrmsContract> getPrmsContractList() {
        return prmsContractList;
    }

    public void setPrmsContractList(List<PrmsContract> prmsContractList) {
        this.prmsContractList = prmsContractList;
    }

//    @XmlTransient
//    public List<PrmsProjectPlan> getPrmsProjectPlanList() {
//        return prmsProjectPlanList;
//    }
//
//    public void setPrmsProjectPlanList(List<PrmsProjectPlan> prmsProjectPlanList) {
//        this.prmsProjectPlanList = prmsProjectPlanList;
//    }
    public Date getCloseDate() {
        return closeDate;
    }

    public void setCloseDate(Date closeDate) {
        this.closeDate = closeDate;
    }

    public String getCloseStatus() {
        return closeStatus;
    }

    public void setCloseStatus(String closeStatus) {
        this.closeStatus = closeStatus;
    }

    public String getCloseRemark() {
        return closeRemark;
    }

    public void setCloseRemark(String closeRemark) {
        this.closeRemark = closeRemark;
    }

//    public BigInteger getStatus() {
//        return status;
//    }
//
//    public void setStatus(BigInteger status) {
//        this.status = status;
//    }
//    @XmlTransient
//    public List<PrmsProjectPlan> getPrmsProjectPlanList() {
//        return prmsProjectPlanList;
//    }
//
//    public void setPrmsProjectPlanList(List<PrmsProjectPlan> prmsProjectPlanList) {
//        this.prmsProjectPlanList = prmsProjectPlanList;
//    }
}
