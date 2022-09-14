/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.entity;

import et.gov.eep.commonApplications.entity.WfPrmsProcessed;
import et.gov.eep.fcms.entity.FmsLuBudgetYear;
import et.gov.eep.mms.entity.MmsNeedAssessment;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Entity
 *
 * @author user
 */
@Entity
@Table(name = "PRMS_PURCHASE_PLAN")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PrmsPurchasePlan.findAll", query = "SELECT p FROM PrmsPurchasePlan p"),
    @NamedQuery(name = "PrmsPurchasePlan.findById", query = "SELECT p FROM PrmsPurchasePlan p WHERE p.id = :id"),
    @NamedQuery(name = "PrmsPurchasePlan.findByPlanNo", query = "SELECT p FROM PrmsPurchasePlan p WHERE p.planNo LIKE :planNo and p.preparedBy=:preparedBy"),
    @NamedQuery(name = "PrmsPurchasePlan.findByPlanNoLike", query = "SELECT p FROM PrmsPurchasePlan p WHERE p.planNo LIKE :planNo"),
    @NamedQuery(name = "PrmsPurchasePlan.findByPlanNoStatus", query = "SELECT p FROM PrmsPurchasePlan p WHERE p.status=0"),
    @NamedQuery(name = "PrmsPurchasePlan.findByPlanNos", query = "SELECT p FROM PrmsPurchasePlan p WHERE p.planNo = :planNo"),
    @NamedQuery(name = "PrmsPurchasePlan.findByMaxNo", query = "SELECT p FROM PrmsPurchasePlan p WHERE p.id = (SELECT Max(p.id)  from PrmsPurchasePlan p)"),
    @NamedQuery(name = "PrmsPurchasePlan.findByDateReg", query = "SELECT p FROM PrmsPurchasePlan p WHERE p.dateReg = :dateReg"),
    @NamedQuery(name = "PrmsPurchasePlan.findByAnnualPlanDesc", query = "SELECT p FROM PrmsPurchasePlan p WHERE p.annualPlanDesc = :annualPlanDesc"),
    @NamedQuery(name = "PrmsPurchasePlan.findByPreparedBy", query = "SELECT p FROM PrmsPurchasePlan p WHERE p.preparedBy = :preparedBy"),
    @NamedQuery(name = "PrmsPurchasePlan.findByStatus", query = "SELECT p FROM PrmsPurchasePlan p WHERE p.status = :status"),
    @NamedQuery(name = "PrmsPurchasePlan.findByRemark", query = "SELECT p FROM PrmsPurchasePlan p WHERE p.remark = :remark")})

public class PrmsPurchasePlan implements Serializable {

//    @OneToMany(mappedBy = "purchasePlanId")
//    private List<WfPrmsProcessed> wfPrmsProcessedLists;
    @OneToMany(mappedBy = "purchasePlanId")
    private List<PrmsActionPlan> prmsActionPlanList;
    @OneToMany(mappedBy = "planId")
    private List<PrmsBidDetail> prmsBidDetailList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "purchsePlanId")
    private List<PrmsAnnualPlanService> prmsAnnualPlanServiceList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "purchsePlanId")
    private List<PrmsPurchasePlnDetail> prmsPurchasePlnDetailList;
    @OneToMany(mappedBy = "planId")
    private List<PrmsBidDetailAmend> prmsBidDetailAmendList;
    @JoinColumn(name = "NEED_ASSMNT_ID", referencedColumnName = "ASSESSMETN_ID")
    @ManyToOne
    private MmsNeedAssessment needAssmntId;

    @JoinColumn(name = "BUDGET_YEAR", referencedColumnName = "LU_BUDGET_YEAR_ID")
    @ManyToOne
    private FmsLuBudgetYear budgetYearId;

    @OneToMany(mappedBy = "purchasePlanId", cascade = CascadeType.ALL)
    private List<WfPrmsProcessed> prmsWorkFlowProccedList;
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "ID", nullable = false, length = 20)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PRMS_PURCHASE_PLAN_SEQ")
    @SequenceGenerator(name = "PRMS_PURCHASE_PLAN_SEQ", sequenceName = "PRMS_PURCHASE_PLAN_SEQ", allocationSize = 1)
    private String id;
    @Size(max = 20)
    @Column(name = "PLAN_NO", length = 20)
    private String planNo;
//    @Size(max = 20)
//    @Column(name = "BUDGET_YEAR", length = 20)
//    private String budgetYear;
    @Column(name = "DATE_REG")
    @Temporal(TemporalType.DATE)
    private Date dateReg;
    @Size(max = 2000)
    @Column(name = "PURCHASE_TYPE", length = 2000)
    private String purchaseType;
    @Size(max = 2000)
    @Column(name = "ANNUAL_PLAN_DESC", length = 2000)
    private String annualPlanDesc;
    @Size(max = 20)
    @Column(name = "PREPARED_BY", length = 20)
    private String preparedBy;
    @Size(max = 20)
    @Column(name = "REMARK", length = 20)
    private String remark;
    private Integer status;
    @Transient
    private String columnName;
    @Transient
    private String columnValue;
    @Transient
    private String planType;

    public PrmsPurchasePlan() {
    }

    public PrmsPurchasePlan(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

//    public String getBudgetYear() {
//        return budgetYear;
//    }
//
//    public void setBudgetYear(String budgetYear) {
//        this.budgetYear = budgetYear;
//    }
    public String getPlanNo() {
        return planNo;
    }

    public void setPlanNo(String planNo) {
        this.planNo = planNo;
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

    public Date getDateReg() {
        return dateReg;
    }

    public void setDateReg(Date dateReg) {
        this.dateReg = dateReg;
    }

    public String getAnnualPlanDesc() {
        return annualPlanDesc;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setAnnualPlanDesc(String annualPlanDesc) {
        this.annualPlanDesc = annualPlanDesc;
    }

    public String getPreparedBy() {
        return preparedBy;
    }

    public void setPreparedBy(String preparedBy) {
        this.preparedBy = preparedBy;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @XmlTransient
    public List<PrmsPurchasePlnDetail> getPrmsPurchasePlnDetailList() {

        if (prmsPurchasePlnDetailList == null) {
            prmsPurchasePlnDetailList = new ArrayList<>();
        }
        return prmsPurchasePlnDetailList;
    }

    public void setPrmsPurchasePlnDetailList(List<PrmsPurchasePlnDetail> prmsPurchasePlnDetailList) {
        this.prmsPurchasePlnDetailList = prmsPurchasePlnDetailList;
    }

    public void add(PrmsPurchasePlnDetail eepPurchasePlnDetail) {

        if (eepPurchasePlnDetail.getPurchsePlanId() != this) {
            this.getPrmsPurchasePlnDetailList().add(eepPurchasePlnDetail);
            eepPurchasePlnDetail.setPurchsePlanId(this);
        }
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
        if (!(object instanceof PrmsPurchasePlan)) {
            return false;
        }
        PrmsPurchasePlan other = (PrmsPurchasePlan) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return planNo;
    }

    public MmsNeedAssessment getNeedAssmntId() {
        return needAssmntId;
    }

    public void setNeedAssmntId(MmsNeedAssessment needAssmntId) {
        this.needAssmntId = needAssmntId;
    }

    public FmsLuBudgetYear getBudgetYearId() {
        return budgetYearId;
    }

    public void setBudgetYearId(FmsLuBudgetYear budgetYearId) {
        this.budgetYearId = budgetYearId;
    }

    public String getPlanType() {
        return planType;
    }

    public void setPlanType(String plannType) {
        this.planType = plannType;
    }

    @XmlTransient
    public List<PrmsActionPlan> getPrmsActionPlanList() {
        return prmsActionPlanList;
    }

    public void setPrmsActionPlanList(List<PrmsActionPlan> prmsActionPlanList) {
        this.prmsActionPlanList = prmsActionPlanList;
    }

    @XmlTransient

    public List<PrmsBidDetailAmend> getPrmsBidDetailAmendList() {
        return prmsBidDetailAmendList;
    }

    public void setPrmsBidDetailAmendList(List<PrmsBidDetailAmend> prmsBidDetailAmendList) {
        this.prmsBidDetailAmendList = prmsBidDetailAmendList;
    }

    @XmlTransient

    public List<PrmsBidDetail> getPrmsBidDetailList() {
        return prmsBidDetailList;
    }

    public void setPrmsBidDetailList(List<PrmsBidDetail> prmsBidDetailList) {
        this.prmsBidDetailList = prmsBidDetailList;
    }

    @XmlTransient

    public List<PrmsAnnualPlanService> getPrmsAnnualPlanServiceList() {
        if (prmsAnnualPlanServiceList == null) {
            prmsAnnualPlanServiceList = new ArrayList<>();
        }
        return prmsAnnualPlanServiceList;
    }

    public void setPrmsAnnualPlanServiceList(List<PrmsAnnualPlanService> prmsAnnualPlanServiceList) {
        this.prmsAnnualPlanServiceList = prmsAnnualPlanServiceList;
    }

    @XmlTransient
    public List<WfPrmsProcessed> getPrmsWorkFlowProccedList() {
        if (prmsWorkFlowProccedList == null) {
            prmsWorkFlowProccedList = new ArrayList<>();
        }
        return prmsWorkFlowProccedList;
    }

    public void setHrWorkFlowProccedList(List<WfPrmsProcessed> prmsWorkFlowProccedList) {
        this.prmsWorkFlowProccedList = prmsWorkFlowProccedList;
    }

    public String getPurchaseType() {
        return purchaseType;
    }

    public void setPurchaseType(String purchaseType) {
        this.purchaseType = purchaseType;
    }

    public void add(PrmsAnnualPlanService prmsAnnualPlanService) {
        if (prmsAnnualPlanService.getPurchsePlanId() != this) {
            this.getPrmsAnnualPlanServiceList().add(prmsAnnualPlanService);
            prmsAnnualPlanService.setPurchsePlanId(this);
        }
    }
}
