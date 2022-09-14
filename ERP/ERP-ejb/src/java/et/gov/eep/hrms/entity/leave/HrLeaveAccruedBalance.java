/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.entity.leave;

import et.gov.eep.hrms.entity.employee.HrEmployees;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
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
 * @author User
 */
@Entity
@Table(name = "HR_LEAVE_ACCRUED_BALANCE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HrLeaveAccruedBalance.findAll", query = "SELECT h FROM HrLeaveAccruedBalance h"),
    @NamedQuery(name = "HrLeaveAccruedBalance.findById", query = "SELECT h FROM HrLeaveAccruedBalance h WHERE h.id = :id"),
    @NamedQuery(name = "HrLeaveAccruedBalance.findByLeaveYear", query = "SELECT h FROM HrLeaveAccruedBalance h WHERE h.leaveYear = :leaveYear"),
    @NamedQuery(name = "HrLeaveAccruedBalance.findByRemainingLeaveDays", query = "SELECT h FROM HrLeaveAccruedBalance h WHERE h.accruedLeaveInDays = :accruedLeaveInDays"),
    @NamedQuery(name = "HrLeaveAccruedBalance.findByRemainingLeaveDaysInBirr", query = "SELECT h FROM HrLeaveAccruedBalance h WHERE h.accruedLeaveInBirr = :accruedLeaveInBirr")})
public class HrLeaveAccruedBalance implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HR_LEAVE_ACCRUED_BALANCE_SEQ")
    @SequenceGenerator(name = "HR_LEAVE_ACCRUED_BALANCE_SEQ", sequenceName = "HR_LEAVE_ACCRUED_BALANCE_SEQ", allocationSize = 1)
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private BigDecimal id;
   @Column(name = "LEAVE_YEAR")
    private String leaveYear;
   
    @Column(name = "ACCRUED_LEAVE_IN_DAYS")
    private Integer accruedLeaveInDays;
    @Column(name = "ACCRUED_LEAVE_IN_BIRR")
    private double accruedLeaveInBirr;
    @JoinColumn(name = "EMP_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrEmployees employeeId;

    public HrLeaveAccruedBalance() {
    }

    public HrLeaveAccruedBalance(BigDecimal id) {
        this.id = id;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getLeaveYear() {
        return leaveYear;
    }

    public void setLeaveYear(String leaveYear) {
        this.leaveYear = leaveYear;
    }

  

   
    public Integer getAccruedLeaveInDays() {
        return accruedLeaveInDays;
    }

    public void setAccruedLeaveInDays(Integer accruedLeaveInDays) {
        this.accruedLeaveInDays = accruedLeaveInDays;
    }

    public double getAccruedLeaveInBirr() {
        return accruedLeaveInBirr;
    }

    public void setAccruedLeaveInBirr(double accruedLeaveInBirr) {
        this.accruedLeaveInBirr = accruedLeaveInBirr;
    }

    public HrEmployees getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(HrEmployees employeeId) {
        this.employeeId = employeeId;
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
        if (!(object instanceof HrLeaveAccruedBalance)) {
            return false;
        }
        HrLeaveAccruedBalance other = (HrLeaveAccruedBalance) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.eep.hrms.entity.leave.HrLeaveAccruedBalance[ id=" + id + " ]";
    }

}
