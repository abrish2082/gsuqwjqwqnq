/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.entity.leave;

import et.gov.eep.fcms.entity.FmsLuBudgetYear;
import et.gov.eep.hrms.entity.employee.HrEmployees;
import java.io.Serializable;
import java.math.BigInteger;
import javax.persistence.Basic;
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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author mora1
 */
@Entity
@Table(name = "HR_LEAVE_BALANCE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HrLeaveBalance.findAll", query = "SELECT h FROM HrLeaveBalance h"),
    @NamedQuery(name = "HrLeaveBalance.findByBalanceId", query = "SELECT h FROM HrLeaveBalance h WHERE h.balanceId = :balanceId"),
    @NamedQuery(name = "HrLeaveBalance.findByTotalDays", query = "SELECT h FROM HrLeaveBalance h WHERE h.totalDays = :totalDays"),
    @NamedQuery(name = "HrLeaveBalance.findByRemainingDays", query = "SELECT h FROM HrLeaveBalance h WHERE h.remainingDays = :remainingDays"),
    @NamedQuery(name = "HrLeaveBalance.findByStatus", query = "SELECT h FROM HrLeaveBalance h WHERE h.status = :status"),
    @NamedQuery(name = "HrLeaveBalance.findByRecordDate", query = "SELECT h FROM HrLeaveBalance h WHERE h.recordDate = :recordDate"),
    @NamedQuery(name = "HrLeaveBalance.findByTransferStatus", query = "SELECT h FROM HrLeaveBalance h WHERE h.transferStatus = :transferStatus"),@NamedQuery(name = "HrLeaveBalance.populateBalance", query = "SELECT h FROM HrLeaveBalance h INNER JOIN h.employeeId AS E INNER JOIN h.leaveTypeId AS L INNER JOIN h.leaveYearId AS K WHERE  E.id=:empid AND L.id=:leaveId and h.status=1 OR h.transferStatus =1 ORDER BY k.luBudgetYearId"),
    @NamedQuery(name = "HrLeaveBalance.populatActiveeBalance", query = "SELECT h FROM HrLeaveBalance h INNER JOIN h.employeeId AS E INNER JOIN h.leaveTypeId AS L INNER JOIN h.leaveYearId AS K WHERE  E.id=:empid AND  h.status=1 OR h.transferStatus =1 ORDER BY k.luBudgetYearId"),
    
    @NamedQuery(name = "HrLeaveBalance.populatActiveeBalance1", query = "SELECT h FROM HrLeaveBalance h INNER JOIN h.employeeId AS E INNER JOIN h.leaveTypeId AS L INNER JOIN h.leaveYearId AS K WHERE  E.id=:empid AND ( h.status=1 AND h.transferStatus =1 )ORDER BY h.leaveYearId"),
    @NamedQuery(name = "HrLeaveBalance.populateDistnictBalance", query = "SELECT DISTINCT (h.leaveYearId.luBudgetYearId) FROM HrLeaveBalance h ORDER BY (h.leaveYearId) "),

    @NamedQuery(name = "HrLeaveBalance.populateBalanceForComp", query = "SELECT h FROM HrLeaveBalance h INNER JOIN h.employeeId AS E INNER JOIN h.leaveTypeId AS L INNER JOIN h.leaveYearId AS K WHERE  E.id=:empid AND L.id=1 and h.status=1 ORDER BY k.luBudgetYearId"),

    @NamedQuery(name = "HrLeaveBalance.findLeaveBalanceByYearAndType", query = "SELECT h FROM HrLeaveBalance h INNER JOIN h.leaveTypeId AS L INNER JOIN h.leaveYearId AS Y INNER JOIN h.employeeId AS E WHERE L.id=:leaveId  and Y.luBudgetYearId=:acc AND E.empId=:employeeId "),
    @NamedQuery(name = "HrLeaveBalance.populateBalanceByLyAnLt", query = "SELECT h FROM HrLeaveBalance h INNER JOIN h.leaveTypeId AS L INNER JOIN h.leaveYearId AS Y WHERE L.id=:leaveId  and Y.luBudgetYearId=:acc"),
    @NamedQuery(name = "HrLeaveBalance.populateBalanceByBudgetYear", query = "SELECT h FROM HrLeaveBalance h INNER JOIN h.leaveTypeId AS L INNER JOIN h.leaveYearId AS Y WHERE  Y.luBudgetYearId=:acc"),

//    @NamedQuery(name = "HrLeaveBalance.populateBalanceByLyAnLt", query = "SELECT h FROM HrLeaveBalance h INNER JOIN h.leaveTypeId AS L INNER JOIN h.leaveYearId AS Y WHERE L.id=:leaveId  and Y.luBudgetYearId=:acc"),
    @NamedQuery(name = "HrLeaveBalance.checkExistance", query = "SELECT h FROM HrLeaveBalance h INNER JOIN h.leaveYearId AS L INNER JOIN h.leaveTypeId AS T WHERE L.luBudgetYearId=:accp and T.id=:typeid ")})
public class HrLeaveBalance implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HR_LEAVE_BALANCE_SEQ")
    @SequenceGenerator(name = "HR_LEAVE_BALANCE_SEQ", sequenceName = "HR_LEAVE_BALANCE_SEQ", allocationSize = 1)
    @Basic(optional = false)
    @NotNull
    @Column(name = "BALANCE_ID")
    private Integer balanceId;
    @Column(name = "TOTAL_DAYS")
    private Integer totalDays;
    @Column(name = "REMAINING_DAYS")
    private Integer remainingDays;
    @Column(name = "STATUS")
    private Integer status;
    @Size(max = 20)
    @Column(name = "RECORD_DATE")
    private String recordDate;
    @Column(name = "TRANSFER_STATUS")
    private Integer transferStatus;
    @JoinColumn(name = "LEAVE_YEAR_ID", referencedColumnName = "LU_BUDGET_YEAR_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private FmsLuBudgetYear leaveYearId;
    @JoinColumn(name = "EMPLOYEE_ID", referencedColumnName = "ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private HrEmployees employeeId;
    @JoinColumn(name = "LEAVE_TYPE_ID", referencedColumnName = "ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private HrLuLeaveTypes leaveTypeId;

    public HrLeaveBalance() {
    }

    public HrLeaveBalance(Integer balanceId) {
        this.balanceId = balanceId;
    }

    public Integer getBalanceId() {
        return balanceId;
    }

    public void setBalanceId(Integer balanceId) {
        this.balanceId = balanceId;
    }

    public Integer getTotalDays() {
        return totalDays;
    }

    public void setTotalDays(Integer totalDays) {
        this.totalDays = totalDays;
    }

    public Integer getRemainingDays() {
        return remainingDays;
    }

    public void setRemainingDays(Integer remainingDays) {
        this.remainingDays = remainingDays;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(String recordDate) {
        this.recordDate = recordDate;
    }

    public Integer getTransferStatus() {
        return transferStatus;
    }

    public void setTransferStatus(Integer transferStatus) {
        this.transferStatus = transferStatus;
    }

    public FmsLuBudgetYear getLeaveYearId() {
        return leaveYearId;
    }

    public void setLeaveYearId(FmsLuBudgetYear leaveYearId) {
        this.leaveYearId = leaveYearId;
    }

    public HrEmployees getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(HrEmployees employeeId) {
        this.employeeId = employeeId;
    }

    public HrLuLeaveTypes getLeaveTypeId() {
        return leaveTypeId;
    }

    public void setLeaveTypeId(HrLuLeaveTypes leaveTypeId) {
        this.leaveTypeId = leaveTypeId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (balanceId != null ? balanceId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HrLeaveBalance)) {
            return false;
        }
        HrLeaveBalance other = (HrLeaveBalance) object;
        if ((this.balanceId == null && other.balanceId != null) || (this.balanceId != null && !this.balanceId.equals(other.balanceId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.eep.hrms.entity.leave.HrLeaveBalance[ balanceId=" + balanceId + " ]";
    }
    
}
