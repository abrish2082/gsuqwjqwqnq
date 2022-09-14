/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.entity.payroll;

import et.gov.eep.fcms.entity.Vocher.FmsCashPaymentOrder;
import et.gov.eep.fcms.entity.Vocher.FmsChequePaymentVoucher;
import et.gov.eep.hrms.entity.employee.HrEmployees;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author user
 */
@Entity
@Table(name = "HR_PAYROLL_MON_TRANSACTIONS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HrPayrollMonTransactions.loadUsedPayrollDates", query = "SELECT DISTINCT(S) FROM HrPayrollMonTransactions h JOIN H.payrollPeriodId S"),
    @NamedQuery(name = "HrPayrollMonTransactions.findLeaveAdvanceOnly", query = "SELECT DISTINCT(S) FROM HrPayrollMonTransactions h JOIN H.payrollPeriodId S WHERE h.empId.id=:id and h.isLeaveAdvance='1'"),
   
     @NamedQuery(name = "HrPayrollMonTransactions.findLeaveAdvanceOnlyLA", query = "SELECT DISTINCT(S) FROM HrPayrollMonTransactions h JOIN H.payrollPeriodId S WHERE h.empId.id=:id and h.isLeaveAdvance='LA'"),
    @NamedQuery(name = "HrPayrollMonTransactions.closeLeaveAdvance", query = "UPDATE HrPayrollMonTransactions H SET H.isLeaveAdvance='2' WHERE H.empId=:empId AND H.payrollPeriodId=:payrollPeriodId"),
    @NamedQuery(name = "HrPayrollMonTransactions.findAll", query = "SELECT h FROM HrPayrollMonTransactions h"),

    @NamedQuery(name = "HrPayrollMonTransactions.findThePayedAmount", query = "SELECT s FROM HrPayrollMonTransactions h join h.empId s WHERE H.payrollPeriodId.id=:id and h.earningDedCode.code=:code"),
    @NamedQuery(name = "HrPayrollMonTransactions.findThePayedAmountDet", query = "SELECT h FROM HrPayrollMonTransactions h join h.empId s WHERE H.payrollPeriodId.id=:id and h.earningDedCode.code=:code"),
    @NamedQuery(name = "HrPayrollMonTransactions.findEarnings", query = "SELECT h FROM HrPayrollMonTransactions h  WHERE h.empId.id=:id"),

    @NamedQuery(name = "HrPayrollMonTransactions.findEarningsPay", query = "SELECT h FROM HrPayrollMonTransactions h where h.payrollPeriodId.id=:id and h.empId=:empId and (H.earningDedCode.criterias!='Total Earnings' and H.earningDedCode.criterias!='Total Deduction' and H.earningDedCode.criterias!='Total Taxable' and H.earningDedCode.criterias!='Tax' and H.earningDedCode.criterias!='Gross Salary' and H.earningDedCode.criterias!='Pension Addition' and H.earningDedCode.criterias!='Pension Deduction') and h.earningDedCode.type='Earning'"),
    @NamedQuery(name = "HrPayrollMonTransactions.findById", query = "SELECT h FROM HrPayrollMonTransactions h WHERE h.id = :id"),
    @NamedQuery(name = "HrPayrollMonTransactions.findByEmpIdAndPayrollPeriodId", query = "SELECT h FROM HrPayrollMonTransactions h WHERE h.empId = :empId And h.payrollPeriodId = :payrollPeriodId"),
    @NamedQuery(name = "HrPayrollMonTransactions.findByAmount", query = "SELECT h FROM HrPayrollMonTransactions h WHERE h.amount = :amount"),
    @NamedQuery(name = "HrPayrollMonTransactions.findByMonthlyEdId", query = "SELECT h FROM HrPayrollMonTransactions h WHERE h.monthlyEdId = :monthlyEdId"),
    @NamedQuery(name = "HrPayrollMonTransactions.findByIsLeaveAdvance", query = "SELECT h FROM HrPayrollMonTransactions h WHERE h.isLeaveAdvance = :isLeaveAdvance")})

public class HrPayrollMonTransactions implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private Long id;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation

    @Column(name = "AMOUNT")
    private BigDecimal amount;
    @Column(name = "MONTHLY_ED_ID")
    private Long monthlyEdId;
    @Size(max = 20)
    @Column(name = "IS_LEAVE_ADVANCE")
    private String isLeaveAdvance;
    @Size(max = 20)
    @Column(name = "TAXABLE")
    private String taxable;
    @Size(max = 20)
    @Column(name = "EARNING_DED_TYPE")
    private String earningDedType;
    @Size(max = 20)
    @Column(name = "RESPECTIVE_AMOUNT")
    private String respectiveAmount;
    @Size(max = 20)
    @Column(name = "PAYMENT_IN")
    private String paymentIn;
    @Size(max = 20)
    @Column(name = "FACTOR")
    private String factor;

    @Size(max = 20)
    @Column(name = "REMAINING_MONTH")
    private String remainingMonth;

    @Size(max = 20)
    @Column(name = "GENERATED_FOR")
    private String generatedFor;
    @JoinColumn(name = "EMP_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrEmployees empId;
    @JoinColumn(name = "EARNING_DED_CODE", referencedColumnName = "CODE")
    @ManyToOne
    private HrPayrollEarningDeductions earningDedCode;
    @JoinColumn(name = "PAYROLL_PERIOD_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrPayrollPeriods payrollPeriodId;

    public HrPayrollMonTransactions() {
    }

    public HrPayrollMonTransactions(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Long getMonthlyEdId() {
        return monthlyEdId;
    }

    public void setMonthlyEdId(Long monthlyEdId) {
        this.monthlyEdId = monthlyEdId;
    }

    public String getIsLeaveAdvance() {
        return isLeaveAdvance;
    }

    public void setIsLeaveAdvance(String isLeaveAdvance) {
        this.isLeaveAdvance = isLeaveAdvance;
    }

    public String getTaxable() {
        return taxable;
    }

    public void setTaxable(String taxable) {
        this.taxable = taxable;
    }

    public String getEarningDedType() {
        return earningDedType;
    }

    public void setEarningDedType(String earningDedType) {
        this.earningDedType = earningDedType;
    }

    public String getRespectiveAmount() {
        return respectiveAmount;
    }

    public void setRespectiveAmount(String respectiveAmount) {
        this.respectiveAmount = respectiveAmount;
    }

    public String getPaymentIn() {
        return paymentIn;
    }

    public void setPaymentIn(String paymentIn) {
        this.paymentIn = paymentIn;
    }

    public String getFactor() {
        return factor;
    }

    public void setFactor(String factor) {
        this.factor = factor;
    }

    public String getGeneratedFor() {
        return generatedFor;
    }

    public void setGeneratedFor(String generatedFor) {
        this.generatedFor = generatedFor;
    }

    public HrEmployees getEmpId() {
        return empId;
    }

    public void setEmpId(HrEmployees empId) {
        this.empId = empId;
    }

    public HrPayrollEarningDeductions getEarningDedCode() {
        return earningDedCode;
    }

    public void setEarningDedCode(HrPayrollEarningDeductions earningDedCode) {
        this.earningDedCode = earningDedCode;
    }

    public HrPayrollPeriods getPayrollPeriodId() {
        return payrollPeriodId;
    }

    public void setPayrollPeriodId(HrPayrollPeriods payrollPeriodId) {
        this.payrollPeriodId = payrollPeriodId;
    }

    public String getRemainingMonth() {
        return remainingMonth;
    }

    public void setRemainingMonth(String remainingMonth) {
        this.remainingMonth = remainingMonth;
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
        if (!(object instanceof HrPayrollMonTransactions)) {
            return false;
        }
        HrPayrollMonTransactions other = (HrPayrollMonTransactions) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.eep.hrms.entity.payroll.HrPayrollMonTransactions[ id=" + id + " ]";
    }

}
