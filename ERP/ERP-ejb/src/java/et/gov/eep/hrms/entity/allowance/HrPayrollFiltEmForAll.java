/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.entity.allowance;

import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.entity.payroll.HrPayrollEarningDeductions;
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
@Table(name = "HR_PAYROLL_FILT_EM_FOR_ALL")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HrPayrollFiltEmForAll.findAll", query = "SELECT h FROM HrPayrollFiltEmForAll h"),
    @NamedQuery(name = "HrPayrollFiltEmForAll.findById", query = "SELECT h FROM HrPayrollFiltEmForAll h WHERE h.id = :id"),
    @NamedQuery(name = "HrPayrollFiltEmForAll.findByReason", query = "SELECT h FROM HrPayrollFiltEmForAll h WHERE h.reason = :reason"),
    
    @NamedQuery(name = "HrPayrollFiltEmForAll.checkFilteredEmployees", query = "SELECT h FROM HrPayrollFiltEmForAll h where h.earningDedCode.code=:code and h.empId.id=:id"),
    @NamedQuery(name = "HrPayrollFiltEmForAll.findFiltedEmpByEdCode", query = "SELECT h FROM HrPayrollFiltEmForAll h where h.earningDedCode.code=:code"),
    @NamedQuery(name = "HrPayrollFiltEmForAll.findByFilteredReason", query = "SELECT h FROM HrPayrollFiltEmForAll h WHERE h.filteredReason = :filteredReason")})

public class HrPayrollFiltEmForAll implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")

    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HR_PAYROLL_FILT_EM_FOR_ALL_SEQ")
    @SequenceGenerator(name = "HR_PAYROLL_FILT_EM_FOR_ALL_SEQ", sequenceName = "HR_PAYROLL_FILT_EM_FOR_ALL_SEQ", allocationSize = 1)

    private BigDecimal id;
    @Size(max = 20)
    @Column(name = "REASON")
    private String reason;
    @Size(max = 20)
    @Column(name = "FILTERED_REASON")
    private String filteredReason;
    @JoinColumn(name = "EMP_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrEmployees empId;
    @JoinColumn(name = "EARNING_DED_CODE", referencedColumnName = "CODE")
    @ManyToOne
    private HrPayrollEarningDeductions earningDedCode;

    public HrPayrollFiltEmForAll() {
    }

    public HrPayrollFiltEmForAll(BigDecimal id) {
        this.id = id;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getFilteredReason() {
        return filteredReason;
    }

    public void setFilteredReason(String filteredReason) {
        this.filteredReason = filteredReason;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HrPayrollFiltEmForAll)) {
            return false;
        }
        HrPayrollFiltEmForAll other = (HrPayrollFiltEmForAll) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.eep.hrms.entity.allowance.HrPayrollFiltEmForAll[ id=" + id + " ]";
    }

}
