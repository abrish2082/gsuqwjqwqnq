/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.entity.payroll;

import et.gov.eep.hrms.entity.employee.HrEmployees;
import java.io.Serializable;
import java.math.BigDecimal;
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
@Table(name = "HR_PAYROLL_FILTER_ED")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HrPayrollFilterEd.findAll", query = "SELECT h FROM HrPayrollFilterEd h"),
    @NamedQuery(name = "HrPayrollFilterEd.findById", query = "SELECT h FROM HrPayrollFilterEd h WHERE h.id = :id"),
    @NamedQuery(name = "HrPayrollFilterEd.findByForTheMonthOf", query = "SELECT h FROM HrPayrollFilterEd h WHERE h.forTheMonthOf = :forTheMonthOf")})
public class HrPayrollFilterEd implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private BigDecimal id;
    @Size(max = 20)
    @Column(name = "FOR_THE_MONTH_OF")
    private String forTheMonthOf;
    @JoinColumn(name = "EMPLOYEE_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrEmployees employeeId;
    @JoinColumn(name = "EARNING_DED_CODE", referencedColumnName = "CODE")
    @ManyToOne
    private HrPayrollEarningDeductions earningDedCode;

    public HrPayrollFilterEd() {
    }

    public HrPayrollFilterEd(BigDecimal id) {
        this.id = id;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getForTheMonthOf() {
        return forTheMonthOf;
    }

    public void setForTheMonthOf(String forTheMonthOf) {
        this.forTheMonthOf = forTheMonthOf;
    }

    public HrEmployees getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(HrEmployees employeeId) {
        this.employeeId = employeeId;
    }

    public HrPayrollEarningDeductions getEarningDedCode() {
        return earningDedCode;
    }

    public void setEarningDedCode(HrPayrollEarningDeductions earningDedCode) {
        this.earningDedCode = earningDedCode;
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
        if (!(object instanceof HrPayrollFilterEd)) {
            return false;
        }
        HrPayrollFilterEd other = (HrPayrollFilterEd) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.eep.hrms.entity.payroll.HrPayrollFilterEd[ id=" + id + " ]";
    }
    
}
