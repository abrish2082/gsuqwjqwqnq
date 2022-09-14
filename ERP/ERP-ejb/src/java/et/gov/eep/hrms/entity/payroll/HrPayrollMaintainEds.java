/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.entity.payroll;

import et.gov.eep.hrms.entity.employee.HrEmployees;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author user
 */
@Entity
@Table(name = "HR_PAYROLL_MAINTAIN_EDS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HrPayrollMaintainEds.IndividualOt", query = "SELECT h FROM HrPayrollMaintainEds h join h.earningDeductionCode s where h.empId=:empId and s.criterias='A type overtime' OR S.criterias='B type overtime' OR S.criterias='C type overtime' OR S.criterias='D type overtime'"),
    @NamedQuery(name = "HrPayrollMaintainEds.checkED", query = "SELECT h FROM HrPayrollMaintainEds h join h.earningDeductionCode s where s.code=:code and h.empId=:empId"),
    @NamedQuery(name = "HrPayrollMaintainEds.returnSavedEd", query = "SELECT h FROM HrPayrollMaintainEds h join h.earningDeductionCode s where  h.groupId=:groupId"),
    @NamedQuery(name = "HrPayrollMaintainEds.cheackRepeatedAllEmpEd", query = "SELECT h FROM HrPayrollMaintainEds h join h.earningDeductionCode s where s.code=:code and h.groupId=:groupId"),
    @NamedQuery(name = "HrPayrollMaintainEds.findEarnngDeduction", query = "SELECT h FROM HrPayrollMaintainEds h join h.earningDeductionCode ed  where ed.type=:type and h.empId.id=:id"),
    @NamedQuery(name = "HrPayrollMaintainEds.removeEarningDeductions", query = "DELETE FROM HrPayrollMaintainEds H WHERE H.groupId=:groupId AND H.empId.id=:id"),
    @NamedQuery(name = "HrPayrollMaintainEds.findOtTransactions", query = "SELECT h FROM HrPayrollMaintainEds h join h.earningDeductionCode ed  where ed.criterias='A type overtime' or ed.criterias='B type overtime' or ed.criterias='C type overtime' or ed.criterias='D type overtime'"),
    @NamedQuery(name = "HrPayrollMaintainEds.findMaintainedEarningDedDetails", query = "SELECT h FROM HrPayrollMaintainEds h join h.earningDeductionCode ed  where ed.code=:code"),
    @NamedQuery(name = "HrPayrollMaintainEds.findAll", query = "SELECT h FROM HrPayrollMaintainEds h"),
    @NamedQuery(name = "HrPayrollMaintainEds.findById", query = "SELECT h FROM HrPayrollMaintainEds h WHERE h.id = :id"),
    @NamedQuery(name = "HrPayrollMaintainEds.findByStartDate", query = "SELECT h FROM HrPayrollMaintainEds h WHERE h.startDate = :startDate"),
    @NamedQuery(name = "HrPayrollMaintainEds.findByEndDate", query = "SELECT h FROM HrPayrollMaintainEds h WHERE h.endDate = :endDate"),
    @NamedQuery(name = "HrPayrollMaintainEds.findByTotal", query = "SELECT h FROM HrPayrollMaintainEds h WHERE h.total = :total"),
    @NamedQuery(name = "HrPayrollMaintainEds.findByMonthlyAmount", query = "SELECT h FROM HrPayrollMaintainEds h WHERE h.monthlyAmount = :monthlyAmount"),
    @NamedQuery(name = "HrPayrollMaintainEds.findByRemark", query = "SELECT h FROM HrPayrollMaintainEds h WHERE h.remark = :remark"),
    @NamedQuery(name = "HrPayrollMaintainEds.findByPreparedBy", query = "SELECT h FROM HrPayrollMaintainEds h WHERE h.preparedBy = :preparedBy"),
    @NamedQuery(name = "HrPayrollMaintainEds.findByPreparedOn", query = "SELECT h FROM HrPayrollMaintainEds h WHERE h.preparedOn = :preparedOn"),
    @NamedQuery(name = "HrPayrollMaintainEds.findByPaymentIn", query = "SELECT h FROM HrPayrollMaintainEds h WHERE h.paymentIn = :paymentIn"),
    @NamedQuery(name = "HrPayrollMaintainEds.findByRespectivePaymentTypeAmt", query = "SELECT h FROM HrPayrollMaintainEds h WHERE h.respectivePaymentTypeAmt = :respectivePaymentTypeAmt"),
    @NamedQuery(name = "HrPayrollMaintainEds.findByRemainingMonth", query = "SELECT h FROM HrPayrollMaintainEds h WHERE h.remainingMonth = :remainingMonth"),
    @NamedQuery(name = "HrPayrollMaintainEds.findByDeductionBiginsAfter", query = "SELECT h FROM HrPayrollMaintainEds h WHERE h.deductionBiginsAfter = :deductionBiginsAfter"),
    @NamedQuery(name = "HrPayrollMaintainEds.findByDedBigins", query = "SELECT h FROM HrPayrollMaintainEds h WHERE h.dedBigins = :dedBigins"),
    @NamedQuery(name = "HrPayrollMaintainEds.findByDedEnds", query = "SELECT h FROM HrPayrollMaintainEds h WHERE h.dedEnds = :dedEnds"),
    @NamedQuery(name = "HrPayrollMaintainEds.findByRemainingMonForNew", query = "SELECT h FROM HrPayrollMaintainEds h WHERE h.remainingMonForNew = :remainingMonForNew"),
    @NamedQuery(name = "HrPayrollMaintainEds.findByFactor", query = "SELECT h FROM HrPayrollMaintainEds h WHERE h.factor = :factor"),
    @NamedQuery(name = "HrPayrollMaintainEds.findByNumberOfMonth", query = "SELECT h FROM HrPayrollMaintainEds h WHERE h.numberOfMonth = :numberOfMonth"),
    @NamedQuery(name = "HrPayrollMaintainEds.findByCountResult", query = "SELECT h FROM HrPayrollMaintainEds h WHERE h.countResult = :countResult"),
    @NamedQuery(name = "HrPayrollMaintainEds.findByStatus", query = "SELECT h FROM HrPayrollMaintainEds h WHERE h.status = :status"),
    @NamedQuery(name = "HrPayrollMaintainEds.findByCarryForwardAfter", query = "SELECT h FROM HrPayrollMaintainEds h WHERE h.carryForwardAfter = :carryForwardAfter"),
    @NamedQuery(name = "HrPayrollMaintainEds.findByCountCarryForward", query = "SELECT h FROM HrPayrollMaintainEds h WHERE h.countCarryForward = :countCarryForward"),
    @NamedQuery(name = "HrPayrollMaintainEds.findByCarryForward", query = "SELECT h FROM HrPayrollMaintainEds h WHERE h.carryForward = :carryForward"),
    @NamedQuery(name = "HrPayrollMaintainEds.findByCarryForwardAndNorT", query = "SELECT h FROM HrPayrollMaintainEds h WHERE h.carryForwardAndNorT = :carryForwardAndNorT")})
public class HrPayrollMaintainEds implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")

    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HR_PAYROLL_MAINTAIN_EDS_SEQ")
    @SequenceGenerator(name = "HR_PAYROLL_MAINTAIN_EDS_SEQ", sequenceName = "HR_PAYROLL_MAINTAIN_EDS_SEQ", allocationSize = 1)

    private BigDecimal id;
    @Size(max = 20)
    @Column(name = "START_DATE")
    private String startDate;
    @Size(max = 20)
    @Column(name = "END_DATE")
    private String endDate;
    @Column(name = "TOTAL")
    private Double total;
    @Column(name = "MONTHLY_AMOUNT")
    private Double monthlyAmount;
    @Size(max = 20)
    @Column(name = "REMARK")
    private String remark;
    @Size(max = 20)
    @Column(name = "PREPARED_BY")
    private String preparedBy;
    @Column(name = "PREPARED_ON")
    @Temporal(TemporalType.DATE)
    private Date preparedOn;
    @Size(max = 60)
    @Column(name = "PAYMENT_IN")
    private String paymentIn;
    @Column(name = "RESPECTIVE_PAYMENT_TYPE_AMT")
    private BigInteger respectivePaymentTypeAmt;
    @Size(max = 20)
    @Column(name = "REMAINING_MONTH")
    private String remainingMonth;
    @Size(max = 20)
    @Column(name = "DEDUCTION_BIGINS_AFTER")
    private String deductionBiginsAfter;
    @Size(max = 20)
    @Column(name = "DED_BIGINS")
    private String dedBigins;
    @Size(max = 20)
    @Column(name = "DED_ENDS")
    private String dedEnds;

    @Size(max = 20)
    @Column(name = "GROUP_ID")
    private String groupId;

    @Size(max = 20)
    @Column(name = "REMAINING_MON_FOR_NEW")
    private String remainingMonForNew;
    @Size(max = 20)
    @Column(name = "FACTOR")
    private String factor;
    @Size(max = 20)
    @Column(name = "NUMBER_OF_MONTH")
    private String numberOfMonth;
    @Size(max = 20)
    @Column(name = "COUNT_RESULT")
    private String countResult;
    @Size(max = 20)
    @Column(name = "STATUS")
    private String status;
    @Size(max = 20)
    @Column(name = "CARRY_FORWARD_AFTER")
    private String carryForwardAfter;
    @Size(max = 20)
    @Column(name = "COUNT_CARRY_FORWARD")
    private String countCarryForward;
    @Size(max = 20)
    @Column(name = "CARRY_FORWARD")
    private String carryForward;
    @Size(max = 20)
    @Column(name = "CARRY_FORWARD_AND_NOR_T")
    private String carryForwardAndNorT;
    @JoinColumn(name = "EMP_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrEmployees empId;
    @JoinColumn(name = "EARNING_DEDUCTION_CODE", referencedColumnName = "CODE")
    @ManyToOne
    private HrPayrollEarningDeductions earningDeductionCode;
    @JoinColumn(name = "START_OF_CARRY_FORWARD", referencedColumnName = "ID")
    @ManyToOne
    private HrPayrollPeriods startOfCarryForward;
    @JoinColumn(name = "PAYMENT_START_FROM", referencedColumnName = "ID")
    @ManyToOne
    private HrPayrollPeriods paymentStartFrom;
    @JoinColumn(name = "TRANSACTION_BEGINS_ON", referencedColumnName = "ID")
    @ManyToOne
    private HrPayrollPeriods transactionBeginsOn;

    public HrPayrollMaintainEds() {
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public HrPayrollMaintainEds(BigDecimal id) {
        this.id = id;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Double getMonthlyAmount() {
        return monthlyAmount;
    }

    public void setMonthlyAmount(Double monthlyAmount) {
        this.monthlyAmount = monthlyAmount;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getPreparedBy() {
        return preparedBy;
    }

    public void setPreparedBy(String preparedBy) {
        this.preparedBy = preparedBy;
    }

    public Date getPreparedOn() {
        return preparedOn;
    }

    public void setPreparedOn(Date preparedOn) {
        this.preparedOn = preparedOn;
    }

    public String getPaymentIn() {
        return paymentIn;
    }

    public void setPaymentIn(String paymentIn) {
        this.paymentIn = paymentIn;
    }

    public BigInteger getRespectivePaymentTypeAmt() {
        return respectivePaymentTypeAmt;
    }

    public void setRespectivePaymentTypeAmt(BigInteger respectivePaymentTypeAmt) {
        this.respectivePaymentTypeAmt = respectivePaymentTypeAmt;
    }

    public String getRemainingMonth() {
        return remainingMonth;
    }

    public void setRemainingMonth(String remainingMonth) {
        this.remainingMonth = remainingMonth;
    }

    public String getDeductionBiginsAfter() {
        return deductionBiginsAfter;
    }

    public void setDeductionBiginsAfter(String deductionBiginsAfter) {
        this.deductionBiginsAfter = deductionBiginsAfter;
    }

    public String getDedBigins() {
        return dedBigins;
    }

    public void setDedBigins(String dedBigins) {
        this.dedBigins = dedBigins;
    }

    public String getDedEnds() {
        return dedEnds;
    }

    public void setDedEnds(String dedEnds) {
        this.dedEnds = dedEnds;
    }

    public String getRemainingMonForNew() {
        return remainingMonForNew;
    }

    public void setRemainingMonForNew(String remainingMonForNew) {
        this.remainingMonForNew = remainingMonForNew;
    }

    public String getFactor() {
        return factor;
    }

    public void setFactor(String factor) {
        this.factor = factor;
    }

    public String getNumberOfMonth() {
        return numberOfMonth;
    }

    public void setNumberOfMonth(String numberOfMonth) {
        this.numberOfMonth = numberOfMonth;
    }

    public String getCountResult() {
        return countResult;
    }

    public void setCountResult(String countResult) {
        this.countResult = countResult;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCarryForwardAfter() {
        return carryForwardAfter;
    }

    public void setCarryForwardAfter(String carryForwardAfter) {
        this.carryForwardAfter = carryForwardAfter;
    }

    public String getCountCarryForward() {
        return countCarryForward;
    }

    public void setCountCarryForward(String countCarryForward) {
        this.countCarryForward = countCarryForward;
    }

    public String getCarryForward() {
        return carryForward;
    }

    public void setCarryForward(String carryForward) {
        this.carryForward = carryForward;
    }

    public String getCarryForwardAndNorT() {
        return carryForwardAndNorT;
    }

    public void setCarryForwardAndNorT(String carryForwardAndNorT) {
        this.carryForwardAndNorT = carryForwardAndNorT;
    }

    public HrEmployees getEmpId() {
        return empId;
    }

    public void setEmpId(HrEmployees empId) {
        this.empId = empId;
    }

    public HrPayrollEarningDeductions getEarningDeductionCode() {
        return earningDeductionCode;
    }

    public void setEarningDeductionCode(HrPayrollEarningDeductions earningDeductionCode) {
        this.earningDeductionCode = earningDeductionCode;
    }

    public HrPayrollPeriods getStartOfCarryForward() {
        return startOfCarryForward;
    }

    public void setStartOfCarryForward(HrPayrollPeriods startOfCarryForward) {
        this.startOfCarryForward = startOfCarryForward;
    }

    public HrPayrollPeriods getPaymentStartFrom() {
        return paymentStartFrom;
    }

    public void setPaymentStartFrom(HrPayrollPeriods paymentStartFrom) {
        this.paymentStartFrom = paymentStartFrom;
    }

    public HrPayrollPeriods getTransactionBeginsOn() {
        return transactionBeginsOn;
    }

    public void setTransactionBeginsOn(HrPayrollPeriods transactionBeginsOn) {
        this.transactionBeginsOn = transactionBeginsOn;
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
        if (!(object instanceof HrPayrollMaintainEds)) {
            return false;
        }
        HrPayrollMaintainEds other = (HrPayrollMaintainEds) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.eep.hrms.entity.payroll.HrPayrollMaintainEds[ id=" + id + " ]";
    }

}
