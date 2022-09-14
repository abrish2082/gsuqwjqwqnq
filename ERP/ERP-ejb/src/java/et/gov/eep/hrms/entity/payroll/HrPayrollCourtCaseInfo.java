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
@Table(name = "HR_PAYROLL_COURT_CASE_INFO")
@XmlRootElement
@NamedQueries({
//    @NamedQuery(name = "HrPayrollCourtCaseInfo.findAll", query = "SELECT h FROM HrPayrollCourtCaseInfo h"),
//    @NamedQuery(name = "HrPayrollCourtCaseInfo.findById", query = "SELECT h FROM HrPayrollCourtCaseInfo h WHERE h.id = :id"),
//    @NamedQuery(name = "HrPayrollCourtCaseInfo.findByEmpId", query = "SELECT h FROM HrPayrollCourtCaseInfo h WHERE h.empId = :empId"),
//    @NamedQuery(name = "HrPayrollCourtCaseInfo.findByAppliedFrom", query = "SELECT h FROM HrPayrollCourtCaseInfo h WHERE h.appliedFrom = :appliedFrom"),
//    @NamedQuery(name = "HrPayrollCourtCaseInfo.findByAppliedTo", query = "SELECT h FROM HrPayrollCourtCaseInfo h WHERE h.appliedTo = :appliedTo"),
//    @NamedQuery(name = "HrPayrollCourtCaseInfo.findByStatus", query = "SELECT h FROM HrPayrollCourtCaseInfo h WHERE h.status = :status"),
//    @NamedQuery(name = "HrPayrollCourtCaseInfo.findByRemark", query = "SELECT h FROM HrPayrollCourtCaseInfo h WHERE h.remark = :remark"),
//    @NamedQuery(name = "HrPayrollCourtCaseInfo.findByReceiversName", query = "SELECT h FROM HrPayrollCourtCaseInfo h WHERE h.receiversName = :receiversName")})
     @NamedQuery(name = "HrPayrollCourtCaseInfo.findByEmpId", query = "SELECT h FROM HrPayrollCourtCaseInfo h where h.empId.id=:id"),
    @NamedQuery(name = "HrPayrollCourtCaseInfo.activeOnlyOne", query = "update  HrPayrollCourtCaseInfo h set h.status='Inactive' where h.earningDedCode.code=:code and h.empId.id=:id"),
    @NamedQuery(name = "HrPayrollCourtCaseInfo.findAll", query = "SELECT h FROM HrPayrollCourtCaseInfo h"),
    @NamedQuery(name = "HrPayrollCourtCaseInfo.findById", query = "SELECT h FROM HrPayrollCourtCaseInfo h WHERE h.id = :id"),
    @NamedQuery(name = "HrPayrollCourtCaseInfo.findByAppliedFrom", query = "SELECT h FROM HrPayrollCourtCaseInfo h WHERE h.appliedFrom = :appliedFrom"),
    @NamedQuery(name = "HrPayrollCourtCaseInfo.findByAppliedTo", query = "SELECT h FROM HrPayrollCourtCaseInfo h WHERE h.appliedTo = :appliedTo"),
    @NamedQuery(name = "HrPayrollCourtCaseInfo.findByStatus", query = "SELECT h FROM HrPayrollCourtCaseInfo h WHERE h.status = :status")})
    
public class HrPayrollCourtCaseInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HR_PAYROLL_COURT_CASE_INFO_SEQ")
    @SequenceGenerator(name = "HR_PAYROLL_COURT_CASE_INFO_SEQ", sequenceName = "HR_PAYROLL_COURT_CASE_INFO_SEQ", allocationSize = 1)
    
    
    private BigDecimal id;
//    @Column(name = "EMP_ID")
//    private BigInteger empId;
    
     @JoinColumn(name = "EMP_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrEmployees empId;
    
    @Size(max = 20)
    @Column(name = "APPLIED_FROM")
    private String appliedFrom;
    @Size(max = 20)
    @Column(name = "APPLIED_TO")
    private String appliedTo;
    @Size(max = 20)
    @Column(name = "STATUS")
    private String status;
    @Size(max = 100)
    @Column(name = "REMARK")
    private String remark;
    @Size(max = 100)
    @Column(name = "RECEIVERS_NAME")
    private String receiversName;
    @JoinColumn(name = "EARNING_DED_CODE", referencedColumnName = "CODE")
    @ManyToOne
    private HrPayrollEarningDeductions earningDedCode;
    @JoinColumn(name = "PL_PG", referencedColumnName = "ID")
    @ManyToOne
    private HrPayrollPlPg plPg;

    public HrPayrollCourtCaseInfo() {
    }

    public HrPayrollCourtCaseInfo(BigDecimal id) {
        this.id = id;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }
    
     /**
     *
     * @return
     */
    public HrEmployees getEmpId() {
        return empId;
    }

    /**
     *
     * @param empId
     */
    public void setEmpId(HrEmployees empId) {
        this.empId = empId;
    }

//    public BigInteger getEmpId() {
//        return empId;
//    }
//
//    public void setEmpId(BigInteger empId) {
//        this.empId = empId;
//    }

    public String getAppliedFrom() {
        return appliedFrom;
    }

    public void setAppliedFrom(String appliedFrom) {
        this.appliedFrom = appliedFrom;
    }

    public String getAppliedTo() {
        return appliedTo;
    }

    public void setAppliedTo(String appliedTo) {
        this.appliedTo = appliedTo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getReceiversName() {
        return receiversName;
    }

    public void setReceiversName(String receiversName) {
        this.receiversName = receiversName;
    }

    public HrPayrollEarningDeductions getEarningDedCode() {
        return earningDedCode;
    }

    public void setEarningDedCode(HrPayrollEarningDeductions earningDedCode) {
        this.earningDedCode = earningDedCode;
    }

    public HrPayrollPlPg getPlPg() {
        return plPg;
    }

    public void setPlPg(HrPayrollPlPg plPg) {
        this.plPg = plPg;
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
        if (!(object instanceof HrPayrollCourtCaseInfo)) {
            return false;
        }
        HrPayrollCourtCaseInfo other = (HrPayrollCourtCaseInfo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.eep.hrms.entity.payroll.HrPayrollCourtCaseInfo[ id=" + id + " ]";
    }
    
}
