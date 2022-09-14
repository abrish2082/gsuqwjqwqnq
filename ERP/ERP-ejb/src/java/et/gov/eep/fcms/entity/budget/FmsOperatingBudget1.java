/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.entity.budget;

import et.gov.eep.commonApplications.entity.WfFcmsProcessed;
import et.gov.eep.fcms.entity.FmsLuBudgetYear;
import et.gov.eep.fcms.entity.admin.FmsCostCenter;
import et.gov.eep.fcms.entity.admin.FmsCostcSystemJunction;
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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Me
 */
@Entity
@Table(name = "FMS_OPERATING_BUDGET1")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FmsOperatingBudget1.findAll", query = "SELECT f FROM FmsOperatingBudget1 f WHERE f.status = 17 OR f.status =18 AND f.requestedBy = :requestedBy ORDER BY f.operatingBudgetId ASC"),    
    @NamedQuery(name = "FmsOperatingBudget1.findRequestForApproval", query = "SELECT f from FmsOperatingBudget1 f WHERE f.status = 17 OR f.status = 18 OR f.status = 2 OR f.status = 0 OR f.status = 4"),
    @NamedQuery(name = "FmsOperatingBudget1.findRequestForApprovalOld", query = "SELECT f.budgetYear ,f.ccSsJunction from FmsOperatingBudget1 f WHERE f.status = 17 OR f.status = 18 OR f.status = 2 OR f.status = 0 OR f.status = 4 GROUP BY f.budgetYear , f.ccSsJunction HAVING COUNT (f) >= 1"),
    @NamedQuery(name = "FmsOperatingBudget1.findRequestForConsApproval", query = "SELECT f.budgetYear ,f.ccSsJunction from FmsOperatingBudget1 f WHERE f.status = 1 OR f.status = 3 OR f.status = 11 GROUP BY f.budgetYear , f.ccSsJunction HAVING COUNT (f) >= 1 "),
    @NamedQuery(name = "FmsOperatingBudget1.findByOperatingBudgetId", query = "SELECT f FROM FmsOperatingBudget1 f WHERE f.operatingBudgetId = :operatingBudgetId"),
    @NamedQuery(name = "FmsOperatingBudget1.findByRequestDate", query = "SELECT f FROM FmsOperatingBudget1 f WHERE f.requestDate = :requestDate"),
    @NamedQuery(name = "FmsOperatingBudget1.findByStatus", query = "SELECT f FROM FmsOperatingBudget1 f WHERE f.status = :status"),
    @NamedQuery(name = "FmsOperatingBudget1.findByBudgetYearSystem", query = "SELECT f FROM FmsOperatingBudget1 f WHERE f.budgetYear.budgetYear = :budgetYear AND f.ccSsJunction = :ccSsJunction AND f.status = 17 OR f.status = 18 OR f.status = 0 OR f.status = 2 OR f.status = 4"),
    @NamedQuery(name = "FmsOperatingBudget1.findByBudgetYearSystemCons", query = "SELECT f FROM FmsOperatingBudget1 f WHERE f.budgetYear.budgetYear = :budgetYear AND f.ccSsJunction = :ccSsJunction"),
    @NamedQuery(name = "FmsOperatingBudget1.findByRemark", query = "SELECT f FROM FmsOperatingBudget1 f WHERE f.remark = :remark"),
    @NamedQuery(name = "FmsOperatingBudget1.findByRequestedBy", query = "SELECT f FROM FmsOperatingBudget1 f WHERE f.requestedBy = :requestedBy"),
    @NamedQuery(name = "FmsOperatingBudget1.findByRequestCodeOnRequest", query = "SELECT f FROM FmsOperatingBudget1 f WHERE f.requestCode = :requestCode"),
    @NamedQuery(name = "FmsOperatingBudget1.findByRequestCodeOnRequestOld", query = "SELECT f FROM FmsOperatingBudget1 f WHERE f.requestCode = :requestCode AND f.status = 17"),
    @NamedQuery(name = "FmsOperatingBudget1.fetchByRequestCode", query = "SELECT f FROM FmsOperatingBudget1 f WHERE f.requestCode = :requestCode AND f.status = 10 OR f.status = 11 ORDER BY f.operatingBudgetId ASC"),

//    @NamedQuery(name = "FmsOperatingBudget1.findAll", query = "SELECT f FROM FmsOperatingBudget1 f WHERE f.status = 17 ORDER BY f.operatingBudgetId ASC"),
    @NamedQuery(name = "FmsOperatingBudget1.findByBudgetYear", query = "SELECT f FROM FmsOperatingBudget1 f WHERE f.budgetYear.budgetYear = :budgetYear AND f.status = 17 ORDER BY f.operatingBudgetId ASC"),
    @NamedQuery(name = "FmsOperatingBudget1.findByBudgetYearAndSystem", query = "SELECT f FROM FmsOperatingBudget1 f WHERE f.budgetYear.budgetYear = :budgetYear AND f.ccSsJunction = :systemId AND f.status = 17 ORDER BY f.operatingBudgetId ASC"),
    @NamedQuery(name = "FmsOperatingBudget1.findByBudgetYearAndCostCenter", query = "SELECT f FROM FmsOperatingBudget1 f WHERE f.budgetYear.budgetYear = :budgetYear AND f.ccSsJunction = :costCenter AND f.status = 17 ORDER BY f.operatingBudgetId ASC"),
    @NamedQuery(name = "FmsOperatingBudget1.findBySystem", query = "SELECT f FROM FmsOperatingBudget1 f WHERE f.ccSsJunction = :systemId AND f.status = 17 ORDER BY f.operatingBudgetId ASC"),
    @NamedQuery(name = "FmsOperatingBudget1.findBySystemAndCostCenter", query = "SELECT f FROM FmsOperatingBudget1 f WHERE f.ccSsJunction = :costCenter AND f.status = 17 ORDER BY f.operatingBudgetId ASC"),
    @NamedQuery(name = "FmsOperatingBudget1.findByRequestCode", query = "SELECT f FROM FmsOperatingBudget1 f WHERE f.requestCode = :requestCode AND f.status = 17 ORDER BY f.operatingBudgetId ASC"),

    @NamedQuery(name = "FmsOperatingBudget1.findAllPrepared", query = "SELECT f FROM FmsOperatingBudget1 f WHERE f.status = 18 OR f.status = 0 ORDER BY f.operatingBudgetId ASC"),
    @NamedQuery(name = "FmsOperatingBudget1.findByBudgetYearPrepared", query = "SELECT f FROM FmsOperatingBudget1 f WHERE f.budgetYear.budgetYear = :budgetYear AND f.status = 18 OR f.status = 0 ORDER BY f.operatingBudgetId ASC"),
    @NamedQuery(name = "FmsOperatingBudget1.findByBudgetYearAndSystemPrepared", query = "SELECT f FROM FmsOperatingBudget1 f WHERE f.budgetYear.budgetYear = :budgetYear AND f.ccSsJunction = :systemId AND f.status = 18 OR f.status = 0 ORDER BY f.operatingBudgetId ASC"),
    @NamedQuery(name = "FmsOperatingBudget1.findByBudgetYearAndCostCenterPrepared", query = "SELECT f FROM FmsOperatingBudget1 f WHERE f.budgetYear.budgetYear = :budgetYear AND f.ccSsJunction = :costCenter AND f.status = 18 OR f.status = 0 ORDER BY f.operatingBudgetId ASC"),
    @NamedQuery(name = "FmsOperatingBudget1.findBySystemPrepared", query = "SELECT f FROM FmsOperatingBudget1 f WHERE f.ccSsJunction = :systemId AND f.status = 18 OR f.status = 0 ORDER BY f.operatingBudgetId ASC"),
    @NamedQuery(name = "FmsOperatingBudget1.findBySystemAndCostCenterPrepared", query = "SELECT f FROM FmsOperatingBudget1 f WHERE f.ccSsJunction = :costCenter AND f.status = 18 OR f.status = 0 ORDER BY f.operatingBudgetId ASC"),
    @NamedQuery(name = "FmsOperatingBudget1.findByRequestCodePrepared", query = "SELECT f FROM FmsOperatingBudget1 f WHERE f.requestCode = :requestCode AND f.status = 18 OR f.status = 0 ORDER BY f.operatingBudgetId ASC"),

    @NamedQuery(name = "FmsOperatingBudget1.findAllChecked", query = "SELECT f FROM FmsOperatingBudget1 f WHERE f.status = 1 OR f.status = 2 ORDER BY f.operatingBudgetId ASC"),
    @NamedQuery(name = "FmsOperatingBudget1.findByBudgetYearChecked", query = "SELECT f FROM FmsOperatingBudget1 f WHERE f.budgetYear.budgetYear = :budgetYear AND f.status = 1 OR f.status = 2 ORDER BY f.operatingBudgetId ASC"),
    @NamedQuery(name = "FmsOperatingBudget1.findByBudgetYearAndSystemChecked", query = "SELECT f FROM FmsOperatingBudget1 f WHERE f.budgetYear.budgetYear = :budgetYear AND f.ccSsJunction = :systemId AND f.status = 1 OR f.status = 2 ORDER BY f.operatingBudgetId ASC"),
    @NamedQuery(name = "FmsOperatingBudget1.findByBudgetYearAndCostCenterChecked", query = "SELECT f FROM FmsOperatingBudget1 f WHERE f.budgetYear.budgetYear = :budgetYear AND f.ccSsJunction = :costCenter AND f.status = 1 OR f.status = 2 ORDER BY f.operatingBudgetId ASC"),
    @NamedQuery(name = "FmsOperatingBudget1.findBySystemChecked", query = "SELECT f FROM FmsOperatingBudget1 f WHERE f.ccSsJunction = :systemId AND f.status = 1 OR f.status = 2 ORDER BY f.operatingBudgetId ASC"),
    @NamedQuery(name = "FmsOperatingBudget1.findBySystemAndCostCenterChecked", query = "SELECT f FROM FmsOperatingBudget1 f WHERE f.ccSsJunction = :costCenter AND f.status = 1 OR f.status = 2 ORDER BY f.operatingBudgetId ASC"),
    @NamedQuery(name = "FmsOperatingBudget1.findByRequestCodeChecked", query = "SELECT f FROM FmsOperatingBudget1 f WHERE f.requestCode = :requestCode AND f.status = 1 OR f.status = 2 ORDER BY f.operatingBudgetId ASC"),

    @NamedQuery(name = "FmsOperatingBudget1.findAllApproved", query = "SELECT f FROM FmsOperatingBudget1 f WHERE f.status = 3 OR f.status = 4 ORDER BY f.operatingBudgetId ASC"),
    @NamedQuery(name = "FmsOperatingBudget1.findByBudgetYearApproved", query = "SELECT f FROM FmsOperatingBudget1 f WHERE f.budgetYear.budgetYear = :budgetYear AND f.status = 3 OR f.status = 4 ORDER BY f.operatingBudgetId ASC"),
    @NamedQuery(name = "FmsOperatingBudget1.findByBudgetYearAndSystemApproved", query = "SELECT f FROM FmsOperatingBudget1 f WHERE f.budgetYear.budgetYear = :budgetYear AND f.ccSsJunction = :systemId AND f.status = 3 OR f.status = 4 ORDER BY f.operatingBudgetId ASC"),
    @NamedQuery(name = "FmsOperatingBudget1.findByBudgetYearAndCostCenterApproved", query = "SELECT f FROM FmsOperatingBudget1 f WHERE f.budgetYear.budgetYear = :budgetYear AND f.ccSsJunction = :costCenter AND f.status = 3 OR f.status = 4 ORDER BY f.operatingBudgetId ASC"),
    @NamedQuery(name = "FmsOperatingBudget1.findBySystemApproved", query = "SELECT f FROM FmsOperatingBudget1 f WHERE f.ccSsJunction = :systemId AND f.status = 3 OR f.status = 4 ORDER BY f.operatingBudgetId ASC"),
    @NamedQuery(name = "FmsOperatingBudget1.findBySystemAndCostCenterApproved", query = "SELECT f FROM FmsOperatingBudget1 f WHERE f.ccSsJunction = :costCenter AND f.status = 3 OR f.status = 4 ORDER BY f.operatingBudgetId ASC"),
    @NamedQuery(name = "FmsOperatingBudget1.findByRequestCodeApproved", query = "SELECT f FROM FmsOperatingBudget1 f WHERE f.requestCode = :requestCode AND f.status = 3 OR f.status = 4 ORDER BY f.operatingBudgetId ASC"),

    @NamedQuery(name = "FmsOperatingBudget1.findAllAuthorized", query = "SELECT f FROM FmsOperatingBudget1 f WHERE f.status = 10 OR f.status = 11 ORDER BY f.operatingBudgetId ASC"),
    @NamedQuery(name = "FmsOperatingBudget1.findByBudgetYearAuthorized", query = "SELECT f FROM FmsOperatingBudget1 f WHERE f.budgetYear.budgetYear = :budgetYear AND f.status = 10 OR f.status = 11 ORDER BY f.operatingBudgetId ASC"),
    @NamedQuery(name = "FmsOperatingBudget1.findByBudgetYearAndSystemAuthorized", query = "SELECT f FROM FmsOperatingBudget1 f WHERE f.budgetYear.budgetYear = :budgetYear AND f.ccSsJunction = :systemId AND f.status = 10 OR f.status = 11 ORDER BY f.operatingBudgetId ASC"),
    @NamedQuery(name = "FmsOperatingBudget1.findByBudgetYearAndCostCenterAuthorized", query = "SELECT f FROM FmsOperatingBudget1 f WHERE f.budgetYear.budgetYear = :budgetYear AND f.ccSsJunction = :ccSsJunction AND f.status = 10 OR f.status = 11 ORDER BY f.operatingBudgetId ASC"),
    @NamedQuery(name = "FmsOperatingBudget1.findBySystemAuthorized", query = "SELECT f FROM FmsOperatingBudget1 f WHERE f.ccSsJunction = :systemId AND f.status = 10 OR f.status = 11 ORDER BY f.operatingBudgetId ASC"),
    @NamedQuery(name = "FmsOperatingBudget1.findBySystemAndCostCenterAuthorized", query = "SELECT f FROM FmsOperatingBudget1 f WHERE f.ccSsJunction = :ccSsJunction AND f.status = 10 OR f.status = 11 ORDER BY f.operatingBudgetId ASC"),
    @NamedQuery(name = "FmsOperatingBudget1.findByRequestCodeAuthorized", query = "SELECT f FROM FmsOperatingBudget1 f WHERE f.requestCode = :requestCode AND f.status = 10 OR f.status = 11 ORDER BY f.operatingBudgetId ASC")
})
public class FmsOperatingBudget1 implements Serializable {

    @Column(name = "REQUESTED_BY")
    private Integer requestedBy;
    @Column(name = "TOTAL_REQ_AMOUNT")
    private BigDecimal totalReqAmount;
    @Column(name = "STATUS")
    private int status;

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "OPERATING_BUDGET_ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FMS_OPERATING_BUDGET1_SEQ")
    @SequenceGenerator(name = "FMS_OPERATING_BUDGET1_SEQ", sequenceName = "FMS_OPERATING_BUDGET1_SEQ", allocationSize = 1)
    private Integer operatingBudgetId;
    @Column(name = "REQUEST_DATE")
    private String requestDate;
    @Column(name = "REQUEST_CODE")
    @Size(max = 50)
    private String requestCode;
    @Column(name = "REMARK")
    private String remark;
    @JoinColumn(name = "BUDGET_YEAR", referencedColumnName = "LU_BUDGET_YEAR_ID")
    @ManyToOne
    private FmsLuBudgetYear budgetYear;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "operatingBudgetId")
    private List<FmsOperatingBudgetDetail> fmsOperatingBudgetDetailList;
    @OneToMany(mappedBy = "operatingBudgetId")
    private List<WfFcmsProcessed> wfFcmsProcessedList;
    @JoinColumn(name = "CC_SS_JUNCTION", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private FmsCostcSystemJunction ccSsJunction;

    @Transient
    String changedStatus;

    public FmsOperatingBudget1() {
    }

    public FmsOperatingBudget1(Integer operatingBudgetId) {
        this.operatingBudgetId = operatingBudgetId;
    }

    public Integer getOperatingBudgetId() {
        return operatingBudgetId;
    }

    public void setOperatingBudgetId(Integer operatingBudgetId) {
        this.operatingBudgetId = operatingBudgetId;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getChangedStatus() {
        return changedStatus;
    }

    public void setChangedStatus(String changedStatus) {
        this.changedStatus = changedStatus;
    }

    public Integer getRequestedBy() {
        return requestedBy;
    }

    public void setRequestedBy(Integer requestedBy) {
        this.requestedBy = requestedBy;
    }

    public FmsLuBudgetYear getBudgetYear() {
        return budgetYear;
    }

    public void setBudgetYear(FmsLuBudgetYear budgetYear) {
        this.budgetYear = budgetYear;
    }

    public BigDecimal getTotalReqAmount() {
        return totalReqAmount;
    }

    public void setTotalReqAmount(BigDecimal totalReqAmount) {
        this.totalReqAmount = totalReqAmount;
    }

    @XmlTransient
    public List<FmsOperatingBudgetDetail> getFmsOperatingBudgetDetailList() {
        if (fmsOperatingBudgetDetailList == null) {
            fmsOperatingBudgetDetailList = new ArrayList<>();
        }
        return fmsOperatingBudgetDetailList;
    }

    public void setFmsOperatingBudgetDetailList(List<FmsOperatingBudgetDetail> fmsOperatingBudgetDetailList) {
        this.fmsOperatingBudgetDetailList = fmsOperatingBudgetDetailList;
    }

    @XmlTransient
    public List<WfFcmsProcessed> getWfFcmsProcessedList() {
        return wfFcmsProcessedList;
    }

    public void setWfFcmsProcessedList(List<WfFcmsProcessed> wfFcmsProcessedList) {
        this.wfFcmsProcessedList = wfFcmsProcessedList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (operatingBudgetId != null ? operatingBudgetId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FmsOperatingBudget1)) {
            return false;
        }
        FmsOperatingBudget1 other = (FmsOperatingBudget1) object;
        if ((this.operatingBudgetId == null && other.operatingBudgetId != null) || (this.operatingBudgetId != null && !this.operatingBudgetId.equals(other.operatingBudgetId))) {
            return false;
        }
        return true;
    }

    public void addOperatingBudgetDetail(FmsOperatingBudgetDetail fmsOperatingBudgetDetail) {
        if (fmsOperatingBudgetDetail.getOperatingBudgetId() != this) {
            this.getFmsOperatingBudgetDetailList().add(fmsOperatingBudgetDetail);
            fmsOperatingBudgetDetail.setOperatingBudgetId(this);
        }
    }

    @Override
    public String toString() {
        return getRequestCode();
    }

    public FmsCostcSystemJunction getCcSsJunction() {
        return ccSsJunction;
    }

    public void setCcSsJunction(FmsCostcSystemJunction ccSsJunction) {
        this.ccSsJunction = ccSsJunction;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

}
