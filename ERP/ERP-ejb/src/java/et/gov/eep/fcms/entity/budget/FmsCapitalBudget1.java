/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.entity.budget;

import et.gov.eep.commonApplications.entity.WfFcmsProcessed;
import et.gov.eep.fcms.entity.FmsLuBudgetYear;
import et.gov.eep.fcms.entity.admin.FmsCostcSystemJunction;
import et.gov.eep.pms.entity.PmsWorkAuthorization;
//import et.gov.eep.fcms.entity.workFlow.WfFcmsProcessed;
import java.io.Serializable;
import java.math.BigDecimal;
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
 * @author Me
 */
@Entity
@Table(name = "FMS_CAPITAL_BUDGET1")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FmsCapitalBudget1.findAll", query = "SELECT f FROM FmsCapitalBudget1 f WHERE f.status =17 OR f.status =18 ORDER BY f.capitalBudgetId ASC"),
    @NamedQuery(name = "FmsCapitalBudget1.findRequestForApproval", query = "SELECT f from FmsCapitalBudget1 f WHERE f.status = 17 OR f.status = 18 OR f.status = 2 OR f.status = 0 OR f.status = 4"),
    @NamedQuery(name = "FmsCapitalBudget1.findRequestForConsApproval", query = "SELECT f.budgetYear ,f.ccSsJunction from FmsCapitalBudget1 f WHERE f.status = 1 OR f.status = 4 OR f.status = 3 OR f.status = 11 GROUP BY f.budgetYear , f.ccSsJunction HAVING COUNT (f) >= 1 "),
    @NamedQuery(name = "FmsCapitalBudget1.findByCapitalBudgetId", query = "SELECT f FROM FmsCapitalBudget1 f WHERE f.capitalBudgetId = :capitalBudgetId"),
    @NamedQuery(name = "FmsCapitalBudget1.findByCostCenter", query = "SELECT f FROM FmsCapitalBudget1 f WHERE f.ccSsJunction = :ccSsJunction"),
    @NamedQuery(name = "FmsCapitalBudget1.findByRequestedBy", query = "SELECT f FROM FmsCapitalBudget1 f WHERE f.requestedBy = :requestedBy"),
    @NamedQuery(name = "FmsCapitalBudget1.findByBudgetYearSystem", query = "SELECT f FROM FmsCapitalBudget1 f WHERE f.budgetYear.budgetYear = :budgetYear AND f.ccSsJunction.fmsLuSystem = :fmsLuSystem AND f.status = 17 OR f.status = 18 OR f.status = 2 OR f.status = 0"),
    @NamedQuery(name = "FmsCapitalBudget1.findByBudgetYearSystemCons", query = "SELECT f FROM FmsCapitalBudget1 f WHERE f.budgetYear.budgetYear = :budgetYear AND f.ccSsJunction.fmsLuSystem = :fmsLuSystem AND f.status = 1 OR f.status = 4 OR f.status = 3 OR f.status = 11"),
    @NamedQuery(name = "FmsCapitalBudget1.findByTotalReqAmount", query = "SELECT f FROM FmsCapitalBudget1 f WHERE f.totalReqAmount = :totalReqAmount"),
    @NamedQuery(name = "FmsCapitalBudget1.findByRequestDate", query = "SELECT f FROM FmsCapitalBudget1 f WHERE f.requestDate = :requestDate"),
    @NamedQuery(name = "FmsCapitalBudget1.findByBudgetYear", query = "SELECT f FROM FmsCapitalBudget1 f WHERE f.budgetYear.budgetYear = :budgetYear AND f.status = 17 OR f.status = 18 ORDER BY f.capitalBudgetId ASC"),
    @NamedQuery(name = "FmsCapitalBudget1.findByBudgetYearAndSystem", query = "SELECT f FROM FmsCapitalBudget1 f WHERE f.budgetYear.budgetYear = :budgetYear AND f.ccSsJunction.fmsLuSystem = :fmsLuSystem AND f.status = 17 OR f.status = 18 ORDER BY f.capitalBudgetId ASC"),
    @NamedQuery(name = "FmsCapitalBudget1.findByBudgetYearAndCostCenter", query = "SELECT f FROM FmsCapitalBudget1 f WHERE f.budgetYear.budgetYear = :budgetYear AND f.ccSsJunction.fmsCostCenter = :fmsCostCenter AND f.status = 17 OR f.status = 18 ORDER BY f.capitalBudgetId ASC"),
    @NamedQuery(name = "FmsCapitalBudget1.findByBudgetYearAndCostCenterAuthorized", query = "SELECT f FROM FmsCapitalBudget1 f WHERE f.budgetYear.budgetYear = :budgetYear AND f.ccSsJunction = :ccSsJunction AND f.status = 10 OR f.status = 11 ORDER BY f.capitalBudgetId ASC"),
    @NamedQuery(name = "FmsCapitalBudget1.findByCCSSJPMSBYRAuthorized", query = "SELECT f FROM FmsCapitalBudget1 f WHERE f.budgetYear = :budgetYear AND f.ccSsJunction = :ccSsJunction AND f.jobNo = :jobNo AND f.status = 10 ORDER BY f.capitalBudgetId ASC"),
    @NamedQuery(name = "FmsCapitalBudget1.findByRequestCodeOnRequest", query = "SELECT f FROM FmsCapitalBudget1 f WHERE f.requestCode = :requestCode"),
    @NamedQuery(name = "FmsCapitalBudget1.findBySystem", query = "SELECT f FROM FmsCapitalBudget1 f WHERE f.ccSsJunction.fmsLuSystem = :fmsLuSystem AND f.status = 17 OR f.status = 18 ORDER BY f.capitalBudgetId ASC"),
    @NamedQuery(name = "FmsCapitalBudget1.findBySystemAndCostCenter", query = "SELECT f FROM FmsCapitalBudget1 f WHERE f.ccSsJunction.fmsCostCenter = :costCenter AND f.status = 17 OR f.status = 18 ORDER BY f.capitalBudgetId ASC"),
    @NamedQuery(name = "FmsCapitalBudget1.findAllAuthorized", query = "SELECT f FROM FmsCapitalBudget1 f"),
    @NamedQuery(name = "FmsCapitalBudget1.findAuthorizedByBudgetYear", query = "SELECT f FROM FmsCapitalBudget1 f WHERE f.budgetYear.budgetYear = :budgetYear AND f.status = 10 ORDER BY f.capitalBudgetId ASC"),
    @NamedQuery(name = "FmsCapitalBudget1.findAuthorizedByBudgetYear", query = "SELECT f FROM FmsCapitalBudget1 f WHERE f.budgetYear.budgetYear = :budgetYear AND f.status = 10 ORDER BY f.capitalBudgetId ASC"),
    @NamedQuery(name = "FmsCapitalBudget1.findAuthorizedByBudgetYearAndSystem", query = "SELECT f FROM FmsCapitalBudget1 f WHERE f.budgetYear.budgetYear = :budgetYear AND f.ccSsJunction.fmsLuSystem = :fmsLuSystem AND f.status = 10 ORDER BY f.capitalBudgetId ASC"),
    @NamedQuery(name = "FmsCapitalBudget1.findAuthorizedByBudgetYearAndCostCenter", query = "SELECT f FROM FmsCapitalBudget1 f WHERE f.budgetYear.budgetYear = :budgetYear AND f.ccSsJunction.fmsCostCenter = :costCenter AND f.status = 10 ORDER BY f.capitalBudgetId ASC"),
    @NamedQuery(name = "FmsCapitalBudget1.findAuthorizedBySystem", query = "SELECT f FROM FmsCapitalBudget1 f WHERE f.ccSsJunction.fmsLuSystem = :fmsLuSystem AND f.status = 10 ORDER BY f.capitalBudgetId ASC"),
    @NamedQuery(name = "FmsCapitalBudget1.findAuthorizedBySystemAndCostCenter", query = "SELECT f FROM FmsCapitalBudget1 f WHERE f.ccSsJunction.fmsCostCenter = :costCenter AND f.status = 10 ORDER BY f.capitalBudgetId ASC")})
public class FmsCapitalBudget1 implements Serializable {

    @Column(name = "REQUESTED_BY")
    private Integer requestedBy;
    @Column(name = "TOTAL_REQ_AMOUNT")
    private BigDecimal totalReqAmount;
    @Column(name = "STATUS")
    private Integer status;
    @JoinColumn(name = "CC_SS_JUNCTION", referencedColumnName = "ID")
    @ManyToOne
    private FmsCostcSystemJunction ccSsJunction;
    @Column(name = "REQUEST_REMARK")
    private String requestRemark;

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "CAPITAL_BUDGET_ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FMS_SEQ_CAPITALB")
    @SequenceGenerator(name = "FMS_SEQ_CAPITALB", sequenceName = "FMS_SEQ_CAPITALB", allocationSize = 1)
    private Integer capitalBudgetId;
    @Column(name = "REQUEST_DATE")
    private String requestDate;
    @JoinColumn(name = "BUDGET_YEAR", referencedColumnName = "LU_BUDGET_YEAR_ID")
    @ManyToOne
    private FmsLuBudgetYear budgetYear;
    @Column(name = "REQUEST_CODE")
    @Size(max = 50)
    private String requestCode;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "capitalBudgetId")
    private List<FmsCapitalBudgetDetail> fmsCapitalBudgetDetailList;
    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "capitalBudgetId")
    private List<WfFcmsProcessed> wfFcmsProcessedList;
    @JoinColumn(name = "JOB_NO", referencedColumnName = "WORK_AUTHO_ID")
    @ManyToOne(optional = false)
    private PmsWorkAuthorization jobNo;
    @Transient
    String changedStatus;

    public FmsCapitalBudget1() {
    }

    public FmsCapitalBudget1(Integer capitalBudgetId) {
        this.capitalBudgetId = capitalBudgetId;
    }

    public Integer getCapitalBudgetId() {
        return capitalBudgetId;
    }

    public void setCapitalBudgetId(Integer capitalBudgetId) {
        this.capitalBudgetId = capitalBudgetId;
    }

    public Integer getRequestedBy() {
        return requestedBy;
    }

    public void setRequestedBy(Integer requestedBy) {
        this.requestedBy = requestedBy;
    }

    public BigDecimal getTotalReqAmount() {
        return totalReqAmount;
    }

    public void setTotalReqAmount(BigDecimal totalReqAmount) {
        this.totalReqAmount = totalReqAmount;
    }

    public String getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(String requestDate) {
        this.requestDate = requestDate;
    }

    public String getRequestCode() {
        return requestCode;
    }

    public void setRequestCode(String requestCode) {
        this.requestCode = requestCode;
    }

    public FmsLuBudgetYear getBudgetYear() {
        return budgetYear;
    }

    public void setBudgetYear(FmsLuBudgetYear budgetYear) {
        this.budgetYear = budgetYear;
    }

    public String getChangedStatus() {
        return changedStatus;
    }

    public void setChangedStatus(String changedStatus) {
        this.changedStatus = changedStatus;
    }

    public void addCapitalBudgetDetail(FmsCapitalBudgetDetail fmsCapitalBudgetDetail) {
        if (fmsCapitalBudgetDetail.getCapitalBudgetId() != this) {
            this.getFmsCapitalBudgetDetailList().add(fmsCapitalBudgetDetail);
            fmsCapitalBudgetDetail.setCapitalBudgetId(this);
        }
    }

    @XmlTransient
    public List<FmsCapitalBudgetDetail> getFmsCapitalBudgetDetailList() {
        if (fmsCapitalBudgetDetailList == null) {
            fmsCapitalBudgetDetailList = new ArrayList<>();
        }
        return fmsCapitalBudgetDetailList;
    }

    public void setFmsCapitalBudgetDetailList(List<FmsCapitalBudgetDetail> fmsCapitalBudgetDetailList) {
        this.fmsCapitalBudgetDetailList = fmsCapitalBudgetDetailList;
    }

    public PmsWorkAuthorization getJobNo() {
        return jobNo;
    }

    public void setJobNo(PmsWorkAuthorization jobNo) {
        this.jobNo = jobNo;
    }

    @XmlTransient
    public List<WfFcmsProcessed> getWfFcmsProcessedList() {
        if (wfFcmsProcessedList == null) {
            wfFcmsProcessedList = new ArrayList<>();
        }
        return wfFcmsProcessedList;
    }

    public void setWfFcmsProcessedList(List<WfFcmsProcessed> wfFcmsProcessedList) {
        this.wfFcmsProcessedList = wfFcmsProcessedList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (capitalBudgetId != null ? capitalBudgetId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FmsCapitalBudget1)) {
            return false;
        }
        FmsCapitalBudget1 other = (FmsCapitalBudget1) object;
        if ((this.capitalBudgetId == null && other.capitalBudgetId != null) || (this.capitalBudgetId != null && !this.capitalBudgetId.equals(other.capitalBudgetId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return getRequestCode();
    }

    public String getRequestRemark() {
        return requestRemark;
    }

    public void setRequestRemark(String requestRemark) {
        this.requestRemark = requestRemark;
    }

    public FmsCostcSystemJunction getCcSsJunction() {
        return ccSsJunction;
    }

    public void setCcSsJunction(FmsCostcSystemJunction ccSsJunction) {
        this.ccSsJunction = ccSsJunction;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

}
