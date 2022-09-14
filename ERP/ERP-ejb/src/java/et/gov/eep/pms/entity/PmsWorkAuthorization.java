/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.pms.entity;

import et.gov.eep.fcms.entity.admin.FmsSubsidiaryLedger;
import et.gov.eep.fcms.entity.admin.FmsSystemJobJunction;
import et.gov.eep.fcms.entity.budget.FmsCapitalBudget1;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Me
 */
@Entity
@Table(name = "PMS_WORK_AUTHORIZATION")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PmsWorkAuthorization.findAll", query = "SELECT p FROM PmsWorkAuthorization p"),
    @NamedQuery(name = "PmsWorkAuthorization.findByWorkAuthoId", query = "SELECT p FROM PmsWorkAuthorization p WHERE p.workAuthoId = :workAuthoId"),
    @NamedQuery(name = "PmsWorkAuthorization.findByRegionBranch", query = "SELECT p FROM PmsWorkAuthorization p WHERE p.regionBranch = :regionBranch"),
    @NamedQuery(name = "PmsWorkAuthorization.findByJobNo", query = "SELECT p FROM PmsWorkAuthorization p WHERE p.jobNo = :jobNo"),
    @NamedQuery(name = "PmsWorkAuthorization.findByControllingUnit", query = "SELECT p FROM PmsWorkAuthorization p WHERE p.controllingUnit = :controllingUnit"),
    @NamedQuery(name = "PmsWorkAuthorization.findByDescriptionWork", query = "SELECT p FROM PmsWorkAuthorization p WHERE p.descriptionWork = :descriptionWork"),
    @NamedQuery(name = "PmsWorkAuthorization.findBySystemCost", query = "SELECT p FROM PmsWorkAuthorization p WHERE p.systemCost = :systemCost"),
    @NamedQuery(name = "PmsWorkAuthorization.findByAuthoDate", query = "SELECT p FROM PmsWorkAuthorization p WHERE p.authoDate = :authoDate"),
    @NamedQuery(name = "PmsWorkAuthorization.findByReleaseDate", query = "SELECT p FROM PmsWorkAuthorization p WHERE p.releaseDate = :releaseDate"),
    @NamedQuery(name = "PmsWorkAuthorization.findByRecieptOrCheckNo", query = "SELECT p FROM PmsWorkAuthorization p WHERE p.recieptOrCheckNo = :recieptOrCheckNo")})
public class PmsWorkAuthorization implements Serializable {
    @Column(name = "APPROVED_BUDGET")
    private Double approvedBudget;
    @Column(name = "WORKFLOW_CURRENT_STATUS")
    private BigInteger workflowCurrentStatus;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "jobid")
    private List<FmsSubsidiaryLedger> fmsSubsidiaryLedgerList;
    @Column(name = "DATED")
    @Temporal(TemporalType.DATE)
    private Date dated;
    @Column(name = "DATE_OF_COMMENCE")
    @Temporal(TemporalType.DATE)
    private Date dateOfCommence;
    @Column(name = "SCHEDULE_IN_SERVICE")
    @Temporal(TemporalType.DATE)
    private Date scheduleInService;
    @Size(max = 200)
    @Column(name = "PREPARED_BY")
    private String preparedBy;
    @Size(max = 200)
    @Column(name = "CHECKED_BY")
    private String checkedBy;
    @Size(max = 200)
    @Column(name = "STR_MGT_AND_PRO_OFFICE")
    private String strMgtAndProOffice;
    @Size(max = 200)
    @Column(name = "COR_FIN_PLA_AND_BUD_CON_OFFICE")
    private String corFinPlaAndBudConOffice;
    @OneToMany(mappedBy = "workAuthoId")
    private List<PmsProjectPaymentRequest> pmsProjectPaymentReqList;
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id 
    @Basic(optional = false)
    @Column(name = "WORK_AUTHO_ID")
    private BigDecimal workAuthoId;
    @Column(name = "REGION_BRANCH")
    private String regionBranch;
    @Column(name = "JOB_NO")
    private String jobNo;
    @Column(name = "CONTROLLING_UNIT")
    private String controllingUnit;
    @Column(name = "DESCRIPTION_WORK")
    private String descriptionWork;
    @Column(name = "SYSTEM_COST")
    private String systemCost;
    @Column(name = "AUTHO_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date authoDate;
    @Column(name = "RELEASE_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date releaseDate;
    @Column(name = "RECIEPT_OR_CHECK_NO")
    private String recieptOrCheckNo;
    @JoinColumn(name = "PROJECT_ID", referencedColumnName = "PROJECT_ID")
    @ManyToOne
    private PmsCreateProjects projectId;
    @OneToMany(mappedBy = "jobNo", fetch = FetchType.LAZY)
    private List<FmsCapitalBudget1> fmsCapitalBudgetList;
    @OneToMany(mappedBy = "workAuthoFk", cascade = CascadeType.ALL)
    private List<FmsSystemJobJunction> fmsSystemJobJunctionList;
//    @OneToMany(mappedBy = "jobId")
//    private List<MmsStorereq> mmsStorereqList;
    

    public PmsWorkAuthorization() {
    }

    public PmsWorkAuthorization(BigDecimal workAuthoId) {
        this.workAuthoId = workAuthoId;
    }

    public BigDecimal getWorkAuthoId() {
        return workAuthoId;
    }

    public void setWorkAuthoId(BigDecimal workAuthoId) {
        this.workAuthoId = workAuthoId;
    }

    public String getRegionBranch() {
        return regionBranch;
    }

    public void setRegionBranch(String regionBranch) {
        this.regionBranch = regionBranch;
    }

    public String getJobNo() {
        return jobNo;
    }

    public void setJobNo(String jobNo) {
        this.jobNo = jobNo;
    }

    public String getControllingUnit() {
        return controllingUnit;
    }

    public void setControllingUnit(String controllingUnit) {
        this.controllingUnit = controllingUnit;
    }

    public String getDescriptionWork() {
        return descriptionWork;
    }

    public void setDescriptionWork(String descriptionWork) {
        this.descriptionWork = descriptionWork;
    }

    public String getSystemCost() {
        return systemCost;
    }

    public void setSystemCost(String systemCost) {
        this.systemCost = systemCost;
    }

    public Date getAuthoDate() {
        return authoDate;
    }

    public void setAuthoDate(Date authoDate) {
        this.authoDate = authoDate;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getRecieptOrCheckNo() {
        return recieptOrCheckNo;
    }

    public void setRecieptOrCheckNo(String recieptOrCheckNo) {
        this.recieptOrCheckNo = recieptOrCheckNo;
    }

    public PmsCreateProjects getProjectId() {
        return projectId;
    }

    public void setProjectId(PmsCreateProjects projectId) {
        this.projectId = projectId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (workAuthoId != null ? workAuthoId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PmsWorkAuthorization)) {
            return false;
        }
        PmsWorkAuthorization other = (PmsWorkAuthorization) object;
        if ((this.workAuthoId == null && other.workAuthoId != null) || (this.workAuthoId != null && !this.workAuthoId.equals(other.workAuthoId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return jobNo;
    }

    public Date getDated() {
        return dated;
    }

    public void setDated(Date dated) {
        this.dated = dated;
    }

    public Date getDateOfCommence() {
        return dateOfCommence;
    }

    public void setDateOfCommence(Date dateOfCommence) {
        this.dateOfCommence = dateOfCommence;
    }

    public Date getScheduleInService() {
        return scheduleInService;
    }

    public void setScheduleInService(Date scheduleInService) {
        this.scheduleInService = scheduleInService;
    }

    public String getPreparedBy() {
        return preparedBy;
    }

    public void setPreparedBy(String preparedBy) {
        this.preparedBy = preparedBy;
    }

    public String getCheckedBy() {
        return checkedBy;
    }

    public void setCheckedBy(String checkedBy) {
        this.checkedBy = checkedBy;
    }

    public String getStrMgtAndProOffice() {
        return strMgtAndProOffice;
    }

    public void setStrMgtAndProOffice(String strMgtAndProOffice) {
        this.strMgtAndProOffice = strMgtAndProOffice;
    }

    public String getCorFinPlaAndBudConOffice() {
        return corFinPlaAndBudConOffice;
    }

    public void setCorFinPlaAndBudConOffice(String corFinPlaAndBudConOffice) {
        this.corFinPlaAndBudConOffice = corFinPlaAndBudConOffice;
    }
    
    @XmlTransient
    public List<FmsCapitalBudget1> getFmsCapitalBudgetList() {
        return fmsCapitalBudgetList;
    }

    public void setFmsCapitalBudgetList(List<FmsCapitalBudget1> fmsCapitalBudgetList) {
        this.fmsCapitalBudgetList = fmsCapitalBudgetList;
    }    
    
    @XmlTransient
    public List<PmsProjectPaymentRequest> getPmsProjectPaymentReqList() {
        return pmsProjectPaymentReqList;
    }

    public void setPmsProjectPaymentReqList(List<PmsProjectPaymentRequest> pmsProjectPaymentReqList) {
        this.pmsProjectPaymentReqList = pmsProjectPaymentReqList;
    }

    @XmlTransient
    public List<FmsSubsidiaryLedger> getFmsSubsidiaryLedgerList() {
        return fmsSubsidiaryLedgerList;
    }

    public void setFmsSubsidiaryLedgerList(List<FmsSubsidiaryLedger> fmsSubsidiaryLedgerList) {
        this.fmsSubsidiaryLedgerList = fmsSubsidiaryLedgerList;
    }

    @XmlTransient
    public List<FmsSystemJobJunction> getFmsSystemJobJunctionList() {
        return fmsSystemJobJunctionList;
    }

    public void setFmsSystemJobJunctionList(List<FmsSystemJobJunction> fmsSystemJobJunctionList) {
        this.fmsSystemJobJunctionList = fmsSystemJobJunctionList;
    }

    public Double getApprovedBudget() {
        return approvedBudget;
    }

    public void setApprovedBudget(Double approvedBudget) {
        this.approvedBudget = approvedBudget;
    }
    
    public BigInteger getWorkflowCurrentStatus() {
        return workflowCurrentStatus;
    }

    public void setWorkflowCurrentStatus(BigInteger workflowCurrentStatus) {
        this.workflowCurrentStatus = workflowCurrentStatus;
    }
	
//	@XmlTransient
//    public List<MmsStorereq> getMmsStorereqList() {
//        return mmsStorereqList;
//    }
//
//    public void setMmsStorereqList(List<MmsStorereq> mmsStorereqList) {
//        this.mmsStorereqList = mmsStorereqList;
//    }
    
}
