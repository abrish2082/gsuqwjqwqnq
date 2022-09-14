/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.entity.payroll;

import et.gov.eep.hrms.entity.employee.HrEmployees;
import java.io.Serializable;
import java.math.BigInteger;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author user
 */
@Entity
@Table(name = "HR_PAYROLL_MAINTAIN_BACK_PAY")
@XmlRootElement
@NamedQueries({
    
        @NamedQuery(name = "HrPayrollMaintainBackPay.loadDistinctEmp", query = "SELECT h.id,h.empId,h.earningDedCode FROM HrPayrollMaintainBackPay h "),
    
    
    @NamedQuery(name = "HrPayrollMaintainBackPay.loadPaymentMadeForGroupEach", query = "SELECT h FROM HrPayrollMaintainBackPay h where h.paymentMadeFor='G' and h.empId=:empId and h.backPayGroupId=:backPayGroupId  order by h.prevPayrllId.paymentMadeForTheMonthOf ASC,h.earningDedCode.description asc"),
 @NamedQuery(name = "HrPayrollMaintainBackPay.loadPaymentMadeForGroup", query = "SELECT  h FROM HrPayrollMaintainBackPay h where h.paymentMadeFor='G'"),
    @NamedQuery(name = "HrPayrollMaintainBackPay.loadPaymentMadeForInd", query = "SELECT h FROM HrPayrollMaintainBackPay h where h.paymentMadeFor='I'"),
    @NamedQuery(name = "HrPayrollMaintainBackPay.findBkPaymentOfInd", query = "SELECT h FROM HrPayrollMaintainBackPay h where h.empId=:empId"),
    @NamedQuery(name = "HrPayrollMaintainBackPay.closeIndBk", query = "UPDATE HrPayrollMaintainBackPay H SET H.status='1' WHERE H.empId=:empId"),

    
    
    
    
    
    
    
    @NamedQuery(name = "HrPayrollMaintainBackPay.findAll", query = "SELECT h FROM HrPayrollMaintainBackPay h"),
    @NamedQuery(name = "HrPayrollMaintainBackPay.findById", query = "SELECT h FROM HrPayrollMaintainBackPay h WHERE h.id = :id"),
    @NamedQuery(name = "HrPayrollMaintainBackPay.findByPrevAmt", query = "SELECT h FROM HrPayrollMaintainBackPay h WHERE h.prevAmt = :prevAmt"),
    @NamedQuery(name = "HrPayrollMaintainBackPay.findByNewAmt", query = "SELECT h FROM HrPayrollMaintainBackPay h WHERE h.newAmt = :newAmt"),
    @NamedQuery(name = "HrPayrollMaintainBackPay.findByDifference", query = "SELECT h FROM HrPayrollMaintainBackPay h WHERE h.difference = :difference"),
    @NamedQuery(name = "HrPayrollMaintainBackPay.findByNetPayment", query = "SELECT h FROM HrPayrollMaintainBackPay h WHERE h.netPayment = :netPayment"),
    @NamedQuery(name = "HrPayrollMaintainBackPay.findByNewPayrollId", query = "SELECT h FROM HrPayrollMaintainBackPay h WHERE h.newPayrollId = :newPayrollId"),
    @NamedQuery(name = "HrPayrollMaintainBackPay.findByPaymentMadeFor", query = "SELECT h FROM HrPayrollMaintainBackPay h WHERE h.paymentMadeFor = :paymentMadeFor"),
    @NamedQuery(name = "HrPayrollMaintainBackPay.findByPayrollFrom", query = "SELECT h FROM HrPayrollMaintainBackPay h WHERE h.payrollFrom = :payrollFrom"),
    @NamedQuery(name = "HrPayrollMaintainBackPay.findByPayrollTo", query = "SELECT h FROM HrPayrollMaintainBackPay h WHERE h.payrollTo = :payrollTo"),
    @NamedQuery(name = "HrPayrollMaintainBackPay.findByBackPayPayrollId", query = "SELECT h FROM HrPayrollMaintainBackPay h WHERE h.backPayPayrollId = :backPayPayrollId"),
    @NamedQuery(name = "HrPayrollMaintainBackPay.findByStatus", query = "SELECT h FROM HrPayrollMaintainBackPay h WHERE h.status = :status"),
    @NamedQuery(name = "HrPayrollMaintainBackPay.findByNumberOfMonth", query = "SELECT h FROM HrPayrollMaintainBackPay h WHERE h.numberOfMonth = :numberOfMonth"),
    @NamedQuery(name = "HrPayrollMaintainBackPay.findByMonthlyAmount", query = "SELECT h FROM HrPayrollMaintainBackPay h WHERE h.monthlyAmount = :monthlyAmount"),
    @NamedQuery(name = "HrPayrollMaintainBackPay.findByTotalAmount", query = "SELECT h FROM HrPayrollMaintainBackPay h WHERE h.totalAmount = :totalAmount")})
public class HrPayrollMaintainBackPay implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "ID")
    private String id;
    @Column(name = "PREV_AMT")
    private BigInteger prevAmt;
    @Column(name = "NEW_AMT")
    private BigInteger newAmt;
    @Column(name = "DIFFERENCE")
    private BigInteger difference;
    @Column(name = "NET_PAYMENT")
    private BigInteger netPayment;
    @Column(name = "NEW_PAYROLL_ID")
    private BigInteger newPayrollId;
    @Size(max = 20)
    @Column(name = "PAYMENT_MADE_FOR")
    private String paymentMadeFor;
    @Size(max = 20)
    @Column(name = "PAYROLL_FROM")
    private String payrollFrom;
    @Size(max = 20)
    @Column(name = "PAYROLL_TO")
    private String payrollTo;
    @Size(max = 20)
    @Column(name = "BACK_PAY_PAYROLL_ID")
    private String backPayPayrollId;
    @Size(max = 20)
    @Column(name = "STATUS")
    private String status;
    @Size(max = 20)
    @Column(name = "NUMBER_OF_MONTH")
    private String numberOfMonth;
    @Size(max = 20)
    @Column(name = "MONTHLY_AMOUNT")
    private String monthlyAmount;
    @Size(max = 20)
    @Column(name = "TOTAL_AMOUNT")
    private String totalAmount;
    @JoinColumn(name = "EMP_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrEmployees empId;
    @JoinColumn(name = "BACK_PAY_GROUP_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrPayrollBackPaymentGroups backPayGroupId;
    @JoinColumn(name = "EARNING_DED_CODE", referencedColumnName = "CODE")
    @ManyToOne
    private HrPayrollEarningDeductions earningDedCode;
    @JoinColumn(name = "PREV_PAYRLL_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrPayrollPeriods prevPayrllId;

    public HrPayrollMaintainBackPay() {
    }

    public HrPayrollMaintainBackPay(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BigInteger getPrevAmt() {
        return prevAmt;
    }

    public void setPrevAmt(BigInteger prevAmt) {
        this.prevAmt = prevAmt;
    }

    public BigInteger getNewAmt() {
        return newAmt;
    }

    public void setNewAmt(BigInteger newAmt) {
        this.newAmt = newAmt;
    }

    public BigInteger getDifference() {
        return difference;
    }

    public void setDifference(BigInteger difference) {
        this.difference = difference;
    }

    public BigInteger getNetPayment() {
        return netPayment;
    }

    public void setNetPayment(BigInteger netPayment) {
        this.netPayment = netPayment;
    }

    public BigInteger getNewPayrollId() {
        return newPayrollId;
    }

    public void setNewPayrollId(BigInteger newPayrollId) {
        this.newPayrollId = newPayrollId;
    }

    public String getPaymentMadeFor() {
        return paymentMadeFor;
    }

    public void setPaymentMadeFor(String paymentMadeFor) {
        this.paymentMadeFor = paymentMadeFor;
    }

    public String getPayrollFrom() {
        return payrollFrom;
    }

    public void setPayrollFrom(String payrollFrom) {
        this.payrollFrom = payrollFrom;
    }

    public String getPayrollTo() {
        return payrollTo;
    }

    public void setPayrollTo(String payrollTo) {
        this.payrollTo = payrollTo;
    }

    public String getBackPayPayrollId() {
        return backPayPayrollId;
    }

    public void setBackPayPayrollId(String backPayPayrollId) {
        this.backPayPayrollId = backPayPayrollId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNumberOfMonth() {
        return numberOfMonth;
    }

    public void setNumberOfMonth(String numberOfMonth) {
        this.numberOfMonth = numberOfMonth;
    }

    public String getMonthlyAmount() {
        return monthlyAmount;
    }

    public void setMonthlyAmount(String monthlyAmount) {
        this.monthlyAmount = monthlyAmount;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public HrEmployees getEmpId() {
        return empId;
    }

    public void setEmpId(HrEmployees empId) {
        this.empId = empId;
    }

    public HrPayrollBackPaymentGroups getBackPayGroupId() {
        return backPayGroupId;
    }

    public void setBackPayGroupId(HrPayrollBackPaymentGroups backPayGroupId) {
        this.backPayGroupId = backPayGroupId;
    }

    public HrPayrollEarningDeductions getEarningDedCode() {
        return earningDedCode;
    }

    public void setEarningDedCode(HrPayrollEarningDeductions earningDedCode) {
        this.earningDedCode = earningDedCode;
    }

    public HrPayrollPeriods getPrevPayrllId() {
        return prevPayrllId;
    }

    public void setPrevPayrllId(HrPayrollPeriods prevPayrllId) {
        this.prevPayrllId = prevPayrllId;
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
        if (!(object instanceof HrPayrollMaintainBackPay)) {
            return false;
        }
        HrPayrollMaintainBackPay other = (HrPayrollMaintainBackPay) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.eep.hrms.entity.payroll.HrPayrollMaintainBackPay[ id=" + id + " ]";
    }
    
}
