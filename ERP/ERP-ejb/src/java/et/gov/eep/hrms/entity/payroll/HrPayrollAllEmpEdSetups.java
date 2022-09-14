/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.entity.payroll;

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
 * @author user
 */
@Entity
@Table(name = "HR_PAYROLL_ALL_EMP_ED_SETUPS")
@XmlRootElement
@NamedQueries({
     @NamedQuery(name = "HrPayrollAllEmpEdSetups.checkRepeatitionWtPaymentType", query = "SELECT h FROM HrPayrollAllEmpEdSetups h join h.earningDeductionCode e where e.code=:code"),
   @NamedQuery(name = "HrPayrollAllEmpEdSetups.checkRepeatition", query = "SELECT h FROM HrPayrollAllEmpEdSetups h join h.earningDeductionCode e where e.code=:code and h.paymentIn=:paymentIn"),
    @NamedQuery(name = "HrPayrollAllEmpEdSetups.findAll", query = "SELECT h FROM HrPayrollAllEmpEdSetups h"),
     @NamedQuery(name = "HrPayrollAllEmpEdSetups.findCheck", query = "SELECT h FROM HrPayrollAllEmpEdSetups h join h.earningDeductionCode e"),
    @NamedQuery(name = "HrPayrollAllEmpEdSetups.findById", query = "SELECT h FROM HrPayrollAllEmpEdSetups h WHERE h.id = :id "),
    @NamedQuery(name = "HrPayrollAllEmpEdSetups.findByPaymentIn", query = "SELECT h FROM HrPayrollAllEmpEdSetups h WHERE h.paymentIn = :paymentIn"),
    @NamedQuery(name = "HrPayrollAllEmpEdSetups.findByRespectiveValue", query = "SELECT h FROM HrPayrollAllEmpEdSetups h WHERE h.respectiveValue = :respectiveValue"),
    @NamedQuery(name = "HrPayrollAllEmpEdSetups.findByAppliedFrom", query = "SELECT h FROM HrPayrollAllEmpEdSetups h WHERE h.appliedFrom = :appliedFrom"),
    @NamedQuery(name = "HrPayrollAllEmpEdSetups.findByAppliedTo", query = "SELECT h FROM HrPayrollAllEmpEdSetups h WHERE h.appliedTo = :appliedTo")})
public class HrPayrollAllEmpEdSetups implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)//HR_PAYROLL_ALL_EMP_ED_SUPS_SEQ
    @NotNull
    @Column(name = "ID")
    
     @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HR_PAYROLL_ALL_EMP_ED_SUPS_SEQ")
    @SequenceGenerator(name = "HR_PAYROLL_ALL_EMP_ED_SUPS_SEQ", sequenceName = "HR_PAYROLL_ALL_EMP_ED_SUPS_SEQ", allocationSize = 1)
  
    
    private BigDecimal id;
    @Size(max = 20)
    @Column(name = "PAYMENT_IN")
    private String paymentIn;
    @Size(max = 20)
    @Column(name = "RESPECTIVE_VALUE")
    private String respectiveValue;
    @Size(max = 20)
    @Column(name = "APPLIED_FROM")
    private String appliedFrom;
    @Size(max = 20)
    @Column(name = "APPLIED_TO")
    private String appliedTo;
    @JoinColumn(name = "EARNING_DEDUCTION_CODE", referencedColumnName = "CODE")
    @ManyToOne
    private HrPayrollEarningDeductions earningDeductionCode;

    /**
     *
     */
    public HrPayrollAllEmpEdSetups() {
    }

    /**
     *
     * @param id
     */
    public HrPayrollAllEmpEdSetups(BigDecimal id) {
        this.id = id;
    }

    /**
     *
     * @return
     */
    public BigDecimal getId() {
        return id;
    }

    /**
     *
     * @param id
     */
    public void setId(BigDecimal id) {
        this.id = id;
    }

    /**
     *
     * @return
     */
    public String getPaymentIn() {
        return paymentIn;
    }

    /**
     *
     * @param paymentIn
     */
    public void setPaymentIn(String paymentIn) {
        this.paymentIn = paymentIn;
    }

    /**
     *
     * @return
     */
    public String getRespectiveValue() {
        return respectiveValue;
    }

    /**
     *
     * @param respectiveValue
     */
    public void setRespectiveValue(String respectiveValue) {
        this.respectiveValue = respectiveValue;
    }

    /**
     *
     * @return
     */
    public String getAppliedFrom() {
        return appliedFrom;
    }

    /**
     *
     * @param appliedFrom
     */
    public void setAppliedFrom(String appliedFrom) {
        this.appliedFrom = appliedFrom;
    }

    /**
     *
     * @return
     */
    public String getAppliedTo() {
        return appliedTo;
    }

    /**
     *
     * @param appliedTo
     */
    public void setAppliedTo(String appliedTo) {
        this.appliedTo = appliedTo;
    }

    /**
     *
     * @return
     */
    public HrPayrollEarningDeductions getEarningDeductionCode() {
        return earningDeductionCode;
    }

    /**
     *
     * @param earningDeductionCode
     */
    public void setEarningDeductionCode(HrPayrollEarningDeductions earningDeductionCode) {
        this.earningDeductionCode = earningDeductionCode;
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
        if (!(object instanceof HrPayrollAllEmpEdSetups)) {
            return false;
        }
        HrPayrollAllEmpEdSetups other = (HrPayrollAllEmpEdSetups) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.eep.hrms.entity.payroll.HrPayrollAllEmpEdSetups[ id=" + id + " ]";
    }
    
}
