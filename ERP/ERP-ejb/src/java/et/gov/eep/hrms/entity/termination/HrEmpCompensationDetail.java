/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.entity.termination;

import et.gov.eep.hrms.entity.leave.HrLeaveBalance;
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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author User
 */
@Entity
@Table(name = "HR_EMP_COMPENSATION_DETAIL")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HrEmpCompensationDetail.findAll", query = "SELECT h FROM HrEmpCompensationDetail h"),
    @NamedQuery(name = "HrEmpCompensationDetail.findById", query = "SELECT h FROM HrEmpCompensationDetail h WHERE h.id = :id"),
    @NamedQuery(name = "HrEmpCompensationDetail.findByLeaveBalanceId", query = "SELECT h FROM HrEmpCompensationDetail h WHERE h.leaveBalaceId=:leaveBalanceId")})
public class HrEmpCompensationDetail implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
     @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HR_EMP_COMPENSATION_DETAIL_SEQ")
    @SequenceGenerator(name = "HR_EMP_COMPENSATION_DETAIL_SEQ", sequenceName = "HR_EMP_COMPENSATION_DETAIL_SEQ", allocationSize = 1)
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private BigDecimal id;
   
    @JoinColumn(name = "LEAVE_BALANCE_ID", referencedColumnName = "BALANCE_ID")
    @ManyToOne
    private HrLeaveBalance leaveBalaceId;
    @JoinColumn(name = "COMPENSATION_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrEmployeeCompensation compensationId;
    
    @Column(name = "ACCRUED_LEAVE_DAYS")
    private Double AccruedLeavedays;
       @Column(name = "ACCRUED_LEAV_AMOUNT")
    private Double accruedLeaveAmount;

    public HrEmpCompensationDetail() {
    }

    public HrEmpCompensationDetail(BigDecimal id) {
        this.id = id;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public HrLeaveBalance getLeaveBalaceId() {
        return leaveBalaceId;
    }

    public void setLeaveBalaceId(HrLeaveBalance leaveBalaceId) {
        this.leaveBalaceId = leaveBalaceId;
    }

    
    public HrEmployeeCompensation getCompensationId() {
        return compensationId;
    }

    public void setCompensationId(HrEmployeeCompensation compensationId) {
        this.compensationId = compensationId;
    }

    public Double getAccruedLeavedays() {
        return AccruedLeavedays;
    }

    public void setAccruedLeavedays(Double AccruedLeavedays) {
        this.AccruedLeavedays = AccruedLeavedays;
    }

    public Double getAccruedLeaveAmount() {
        return accruedLeaveAmount;
    }

    public void setAccruedLeaveAmount(Double accruedLeaveAmount) {
        this.accruedLeaveAmount = accruedLeaveAmount;
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
        if (!(object instanceof HrEmpCompensationDetail)) {
            return false;
        }
        HrEmpCompensationDetail other = (HrEmpCompensationDetail) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.eep.hrms.entity.termination.HrEmpCompensationDetail[ id=" + id + " ]";
    }
    
}
