/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.entity.budget;

import et.gov.eep.fcms.entity.Vocher.FmsCashPaymentOrder;
import et.gov.eep.fcms.entity.Vocher.FmsChequePaymentVoucher;
import et.gov.eep.fcms.entity.admin.FmsCostCenter;
import et.gov.eep.fcms.entity.admin.FmsGeneralLedger;
import et.gov.eep.fcms.entity.admin.FmsSubsidiaryLedger;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Me
 */
@Entity
@Table(name = "FMS_BUDGET_CONTROL")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FmsBudgetControl.findAll", query = "SELECT f FROM FmsBudgetControl f"),
    @NamedQuery(name = "FmsBudgetControl.findByBudgetControlId", query = "SELECT f FROM FmsBudgetControl f WHERE f.budgetControlId = :budgetControlId"),
    @NamedQuery(name = "FmsBudgetControl.findByPaidAmount", query = "SELECT f FROM FmsBudgetControl f WHERE f.paidAmount = :paidAmount")})
public class FmsBudgetControl implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "BUDGET_CONTROL_ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FMS_BUDGET_CONTROL_SEQ")
    @SequenceGenerator(name = "FMS_BUDGET_CONTROL_SEQ", sequenceName = "FMS_BUDGET_CONTROL_SEQ", allocationSize = 1)
    private Integer budgetControlId;
    @Column(name = "PAID_AMOUNT")
    private BigDecimal paidAmount;    
    @JoinColumn(name = "CASH_V_ID", referencedColumnName = "CASH_PAYMENT_ORDER_ID")
    @ManyToOne
    private FmsCashPaymentOrder cashVId;
    @JoinColumn(name = "CHEQUE_V_ID", referencedColumnName = "CHEQUE_PAYMENT_VOUCHER_ID")
    @ManyToOne
    private FmsChequePaymentVoucher chequeVId;
    @JoinColumn(name = "COST_CENTER_ID", referencedColumnName = "COST_CENTER_ID")
    @ManyToOne
    private FmsCostCenter costCenterId;
    @JoinColumn(name = "GENERAL_LEDGER_ID", referencedColumnName = "GENERAL_LEDGER_ID")
    @ManyToOne
    private FmsGeneralLedger generalLedgerId;
    @JoinColumn(name = "OB_TASK_ID", referencedColumnName = "O_B_TASK_ID")
    @ManyToOne
    private FmsOperatingBudgetTasks obTaskId;
    @JoinColumn(name = "CB_TASK_ID", referencedColumnName = "C_B_TASKS_ID")
    @ManyToOne
    private FmsCapitalBudgetTasks cbTaskId;
    @JoinColumn(name = "SUBSIDIARY_ID", referencedColumnName = "SUBSIDIARY_ID")
    @ManyToOne
    private FmsSubsidiaryLedger subsidiaryId;
    @Size(max = 20)
    @Column(name = "MONTH_NAME")
    private String monthName;

    private BigDecimal totalPaidAmount;

    private BigDecimal utilization;

    public FmsBudgetControl() {
    }

    public BigDecimal getTotalPaidAmount() {
        return totalPaidAmount;
    }

    public void setTotalPaidAmount(BigDecimal totalPaidAmount) {
        this.totalPaidAmount = totalPaidAmount;
    }

    public BigDecimal getUtilization() {
        return utilization;
    }

    public void setUtilization(BigDecimal utilization) {
        this.utilization = utilization;
    }

    public FmsBudgetControl(Integer budgetControlId) {
        this.budgetControlId = budgetControlId;
    }

    public Integer getBudgetControlId() {
        return budgetControlId;
    }

    public void setBudgetControlId(Integer budgetControlId) {
        this.budgetControlId = budgetControlId;
    }

    public FmsOperatingBudgetTasks getObTaskId() {
        return obTaskId;
    }

    public void setObTaskId(FmsOperatingBudgetTasks obTaskId) {
        this.obTaskId = obTaskId;
    }

    public FmsCapitalBudgetTasks getCbTaskId() {
        return cbTaskId;
    }

    public void setCbTaskId(FmsCapitalBudgetTasks cbTaskId) {
        this.cbTaskId = cbTaskId;
    }

    public BigDecimal getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(BigDecimal paidAmount) {
        this.paidAmount = paidAmount;
    }

    public FmsCashPaymentOrder getCashVId() {
        return cashVId;
    }

    public void setCashVId(FmsCashPaymentOrder cashVId) {
        this.cashVId = cashVId;
    }

    public FmsChequePaymentVoucher getChequeVId() {
        return chequeVId;
    }

    public void setChequeVId(FmsChequePaymentVoucher chequeVId) {
        this.chequeVId = chequeVId;
    }

    public FmsCostCenter getCostCenterId() {
        return costCenterId;
    }

    public void setCostCenterId(FmsCostCenter costCenterId) {
        this.costCenterId = costCenterId;
    }

    public FmsGeneralLedger getGeneralLedgerId() {
        return generalLedgerId;
    }

    public void setGeneralLedgerId(FmsGeneralLedger generalLedgerId) {
        this.generalLedgerId = generalLedgerId;
    }

    public FmsSubsidiaryLedger getSubsidiaryId() {
        return subsidiaryId;
    }

    public void setSubsidiaryId(FmsSubsidiaryLedger subsidiaryId) {
        this.subsidiaryId = subsidiaryId;
    }

    public String getMonthName() {
        return monthName;
    }

    public void setMonthName(String monthName) {
        this.monthName = monthName;
    }


    @Override
    public int hashCode() {
        int hash = 0;
        hash += (budgetControlId != null ? budgetControlId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FmsBudgetControl)) {
            return false;
        }
        FmsBudgetControl other = (FmsBudgetControl) object;
        if ((this.budgetControlId == null && other.budgetControlId != null) || (this.budgetControlId != null && !this.budgetControlId.equals(other.budgetControlId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.eep.fcms.entity.budget.FmsBudgetControl[ budgetControlId=" + budgetControlId + " ]";
    }

}
