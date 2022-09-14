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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author user
 */
@Entity
@Table(name = "HR_PAYROLL_MAINTAN_BACK_PYMT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HrPayrollMaintanBackPymt.findAll", query = "SELECT h FROM HrPayrollMaintanBackPymt h"),
    @NamedQuery(name = "HrPayrollMaintanBackPymt.findById", query = "SELECT h FROM HrPayrollMaintanBackPymt h WHERE h.id = :id"),
    @NamedQuery(name = "HrPayrollMaintanBackPymt.findByEmpId", query = "SELECT h FROM HrPayrollMaintanBackPymt h WHERE h.empId = :empId"),
    @NamedQuery(name = "HrPayrollMaintanBackPymt.findByPaymentMont", query = "SELECT h FROM HrPayrollMaintanBackPymt h WHERE h.paymentMont = :paymentMont")})
public class HrPayrollMaintanBackPymt implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "ID")
    private String id;
    @Column(name = "EMP_ID")
    private BigInteger empId;
    @Size(max = 20)
    @Column(name = "PAYMENT_MONT")
    private String paymentMont;
    @JoinColumn(name = "ID", referencedColumnName = "EMP_ID", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private HrEmployees hrEmployee;
    @JoinColumn(name = "EARNING_DED_CODE", referencedColumnName = "CODE")
    @ManyToOne
    private HrPayrollEarningDeductions earningDedCode;
    @JoinColumn(name = "PAYROLL_FROM", referencedColumnName = "ID")
    @ManyToOne
    private HrPayrollPeriods payrollFrom;
    @JoinColumn(name = "BACK_PAYROLL_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrPayrollPeriods backPayrollId;
    @JoinColumn(name = "PAYROLL_TO", referencedColumnName = "ID")
    @ManyToOne
    private HrPayrollPeriods payrollTo;

    /**
     *
     */
    public HrPayrollMaintanBackPymt() {
    }

    /**
     *
     * @param id
     */
    public HrPayrollMaintanBackPymt(String id) {
        this.id = id;
    }

    /**
     *
     * @return
     */
    public String getId() {
        return id;
    }

    /**
     *
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     *
     * @return
     */
    public BigInteger getEmpId() {
        return empId;
    }

    /**
     *
     * @param empId
     */
    public void setEmpId(BigInteger empId) {
        this.empId = empId;
    }

    /**
     *
     * @return
     */
    public String getPaymentMont() {
        return paymentMont;
    }

    /**
     *
     * @param paymentMont
     */
    public void setPaymentMont(String paymentMont) {
        this.paymentMont = paymentMont;
    }

    /**
     *
     * @return
     */
    public HrEmployees getHrEmployee() {
        return hrEmployee;
    }

    /**
     *
     * @param hrEmployee
     */
    public void setHrEmployee(HrEmployees hrEmployee) {
        this.hrEmployee = hrEmployee;
    }

    /**
     *
     * @return
     */
    public HrPayrollEarningDeductions getEarningDedCode() {
        return earningDedCode;
    }

    /**
     *
     * @param earningDedCode
     */
    public void setEarningDedCode(HrPayrollEarningDeductions earningDedCode) {
        this.earningDedCode = earningDedCode;
    }

    /**
     *
     * @return
     */
    public HrPayrollPeriods getPayrollFrom() {
        return payrollFrom;
    }

    /**
     *
     * @param payrollFrom
     */
    public void setPayrollFrom(HrPayrollPeriods payrollFrom) {
        this.payrollFrom = payrollFrom;
    }

    /**
     *
     * @return
     */
    public HrPayrollPeriods getBackPayrollId() {
        return backPayrollId;
    }

    /**
     *
     * @param backPayrollId
     */
    public void setBackPayrollId(HrPayrollPeriods backPayrollId) {
        this.backPayrollId = backPayrollId;
    }

    /**
     *
     * @return
     */
    public HrPayrollPeriods getPayrollTo() {
        return payrollTo;
    }

    /**
     *
     * @param payrollTo
     */
    public void setPayrollTo(HrPayrollPeriods payrollTo) {
        this.payrollTo = payrollTo;
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
        if (!(object instanceof HrPayrollMaintanBackPymt)) {
            return false;
        }
        HrPayrollMaintanBackPymt other = (HrPayrollMaintanBackPymt) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.eep.hrms.entity.payroll.HrPayrollMaintanBackPymt[ id=" + id + " ]";
    }
    
}
