/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.entity;


import et.gov.eep.fcms.entity.admin.FmsSubsidiaryLedger;
import et.gov.eep.hrms.entity.HrBudget;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author beharuh
 */
@Entity
@Table(name = "FMS_HR_BUDGET2")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FmsHrBudget2.findAll", query = "SELECT f FROM FmsHrBudget2 f"),
    @NamedQuery(name = "FmsHrBudget2.findAllByHrBudgetId", query = "SELECT f FROM FmsHrBudget2 f WHERE f.hrBudgetId = :hrBudgetId"),
    @NamedQuery(name = "FmsHrBudget2.findAllByHrBudgetIdALL", query = "SELECT f FROM FmsHrBudget2 f WHERE f.hrBudgetId = :hrBudgetId"),
    @NamedQuery(name = "FmsHrBudget2.findByBudgetId", query = "SELECT f FROM FmsHrBudget2 f WHERE f.budgetId = :budgetId"),
    @NamedQuery(name = "FmsHrBudget2.findByHrBudgetId", query = "SELECT f FROM FmsHrBudget2 f WHERE f.hrBudgetId = :hrBudgetId"),
//    @NamedQuery(name = "FmsHrBudget2.findByAcctId", query = "SELECT f FROM FmsHrBudget2 f WHERE f.acctId = :acctId"),
    @NamedQuery(name = "FmsHrBudget2.findByApprovedAmt", query = "SELECT f FROM FmsHrBudget2 f WHERE f.approvedAmt = :approvedAmt"),
    @NamedQuery(name = "FmsHrBudget2.findByApprovedBy", query = "SELECT f FROM FmsHrBudget2 f WHERE f.approvedBy = :approvedBy"),
    @NamedQuery(name = "FmsHrBudget2.findByApprovedDate", query = "SELECT f FROM FmsHrBudget2 f WHERE f.approvedDate = :approvedDate"),
    @NamedQuery(name = "FmsHrBudget2.findByStatus", query = "SELECT f FROM FmsHrBudget2 f WHERE f.status = :status")})
public class FmsHrBudget2 implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "BUDGET_ID", nullable = false, precision = 0, scale = -127)
    private Integer budgetId;
//    @Column(name = "ACCT_ID")
//    private Integer acctId;
    @Column(name = "APPROVED_AMT")
    private double approvedAmt;
    @Column(name = "APPROVED_BY", length = 50)
    private String approvedBy;
    @Column(name = "APPROVED_DATE", length = 30)
    private String approvedDate;
    private Integer status;
    @Column(name = "APPROVAL_REMARK", length = 100)
    private String approvalRemark;
    @JoinColumn(name = "HR_BUDGET_ID", referencedColumnName = "BUDGET_ID")
    @ManyToOne
    private HrBudget hrBudgetId;

    @JoinColumn(name = "ACCT_ID", referencedColumnName = "SUBSIDIARY_ID")
    @ManyToOne
    private FmsSubsidiaryLedger subsidiaryLedger;

    @Transient
    boolean statusVal;

    /**
     *
     */
    public FmsHrBudget2() {
    }

    /**
     *
     * @param budgetId
     */
    public FmsHrBudget2(Integer budgetId) {
        this.budgetId = budgetId;
    }

    /**
     *
     * @return
     */
    public Integer getBudgetId() {
        return budgetId;
    }

    /**
     *
     * @param budgetId
     */
    public void setBudgetId(Integer budgetId) {
        this.budgetId = budgetId;
    }
//
//    public Integer getAcctId() {
//        return acctId;
//    }
//
//    public void setAcctId(Integer acctId) {
//        this.acctId = acctId;
//    }

    /**
     *
     * @return
     */
    public double getApprovedAmt() {
        return approvedAmt;
    }

    /**
     *
     * @param approvedAmt
     */
    public void setApprovedAmt(double approvedAmt) {
        this.approvedAmt = approvedAmt;
    }

    /**
     *
     * @return
     */
    public String getApprovedBy() {
        return approvedBy;
    }

    /**
     *
     * @param approvedBy
     */
    public void setApprovedBy(String approvedBy) {
        this.approvedBy = approvedBy;
    }

    /**
     *
     * @return
     */
    public String getApprovedDate() {
        return approvedDate;
    }

    /**
     *
     * @param approvedDate
     */
    public void setApprovedDate(String approvedDate) {
        this.approvedDate = approvedDate;
    }

    /**
     *
     * @return
     */
    public Integer getStatus() {
        return status;
    }

    /**
     *
     * @param status
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     *
     * @return
     */
    public String getApprovalRemark() {
        return approvalRemark;
    }

    /**
     *
     * @param approvalRemark
     */
    public void setApprovalRemark(String approvalRemark) {
        this.approvalRemark = approvalRemark;
    }

    /**
     *
     * @return
     */
    public HrBudget getHrBudgetId() {
        return hrBudgetId;
    }

    /**
     *
     * @param hrBudgetId
     */
    public void setHrBudgetId(HrBudget hrBudgetId) {
        this.hrBudgetId = hrBudgetId;
    }

    /**
     *
     * @return
     */
    public boolean isStatusVal() {
        return statusVal;
    }

    /**
     *
     * @param statusVal
     */
    public void setStatusVal(boolean statusVal) {
        this.statusVal = statusVal;
    }

    /**
     *
     * @return
     */
    public FmsSubsidiaryLedger getSubsidiaryLedger() {
        return subsidiaryLedger;
    }

    /**
     *
     * @param subsidiaryLedger
     */
    public void setSubsidiaryLedger(FmsSubsidiaryLedger subsidiaryLedger) {
        this.subsidiaryLedger = subsidiaryLedger;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (budgetId != null ? budgetId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FmsHrBudget2)) {
            return false;
        }
        FmsHrBudget2 other = (FmsHrBudget2) object;
        if ((this.budgetId == null && other.budgetId != null) || (this.budgetId != null && !this.budgetId.equals(other.budgetId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "javaapplication6.FmsHrBudget2[ budgetId=" + budgetId + " ]";
    }
}
