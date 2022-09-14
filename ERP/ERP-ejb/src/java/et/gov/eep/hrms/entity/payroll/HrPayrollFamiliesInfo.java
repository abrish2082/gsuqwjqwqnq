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
@Table(name = "HR_PAYROLL_FAMILIES_INFO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HrPayrollFamiliesInfo.findByEmpId", query = "SELECT h FROM HrPayrollFamiliesInfo h where h.empId.id=:id"),

    @NamedQuery(name = "HrPayrollFamiliesInfo.activeOnlyOne", query = "update HrPayrollFamiliesInfo h set h.status='Inactive' where h.empId.id=:id and h.status='Active' and h.earningDedCode.code=:code"),

    @NamedQuery(name = "HrPayrollFamiliesInfo.findAll", query = "SELECT h FROM HrPayrollFamiliesInfo h"),
    @NamedQuery(name = "HrPayrollFamiliesInfo.findById", query = "SELECT h FROM HrPayrollFamiliesInfo h WHERE h.id = :id"),
    @NamedQuery(name = "HrPayrollFamiliesInfo.findByFirstName", query = "SELECT h FROM HrPayrollFamiliesInfo h WHERE h.firstName = :firstName"),
    @NamedQuery(name = "HrPayrollFamiliesInfo.findByMiddleName", query = "SELECT h FROM HrPayrollFamiliesInfo h WHERE h.middleName = :middleName"),
    @NamedQuery(name = "HrPayrollFamiliesInfo.findByLastName", query = "SELECT h FROM HrPayrollFamiliesInfo h WHERE h.lastName = :lastName"),
    @NamedQuery(name = "HrPayrollFamiliesInfo.findByAccountNumber", query = "SELECT h FROM HrPayrollFamiliesInfo h WHERE h.accountNumber = :accountNumber"),
    @NamedQuery(name = "HrPayrollFamiliesInfo.findByAppliedFrom", query = "SELECT h FROM HrPayrollFamiliesInfo h WHERE h.appliedFrom = :appliedFrom"),
    @NamedQuery(name = "HrPayrollFamiliesInfo.findByAppliedTo", query = "SELECT h FROM HrPayrollFamiliesInfo h WHERE h.appliedTo = :appliedTo"),
    @NamedQuery(name = "HrPayrollFamiliesInfo.findByStatus", query = "SELECT h FROM HrPayrollFamiliesInfo h WHERE h.status = :status"), //    @NamedQuery(name = "HrPayrollFamiliesInfo.findAll", query = "SELECT h FROM HrPayrollFamiliesInfo h"),
//    @NamedQuery(name = "HrPayrollFamiliesInfo.findById", query = "SELECT h FROM HrPayrollFamiliesInfo h WHERE h.id = :id"),
//    @NamedQuery(name = "HrPayrollFamiliesInfo.findByFirstName", query = "SELECT h FROM HrPayrollFamiliesInfo h WHERE h.firstName = :firstName"),
//    @NamedQuery(name = "HrPayrollFamiliesInfo.findByMiddleName", query = "SELECT h FROM HrPayrollFamiliesInfo h WHERE h.middleName = :middleName"),
//    @NamedQuery(name = "HrPayrollFamiliesInfo.findByLastName", query = "SELECT h FROM HrPayrollFamiliesInfo h WHERE h.lastName = :lastName"),
//    @NamedQuery(name = "HrPayrollFamiliesInfo.findByAccountNumber", query = "SELECT h FROM HrPayrollFamiliesInfo h WHERE h.accountNumber = :accountNumber"),
//    @NamedQuery(name = "HrPayrollFamiliesInfo.findByAppliedFrom", query = "SELECT h FROM HrPayrollFamiliesInfo h WHERE h.appliedFrom = :appliedFrom"),
//    @NamedQuery(name = "HrPayrollFamiliesInfo.findByAppliedTo", query = "SELECT h FROM HrPayrollFamiliesInfo h WHERE h.appliedTo = :appliedTo"),
//    @NamedQuery(name = "HrPayrollFamiliesInfo.findByStatus", query = "SELECT h FROM HrPayrollFamiliesInfo h WHERE h.status = :status"),
//    @NamedQuery(name = "HrPayrollFamiliesInfo.findByEmpId", query = "SELECT h FROM HrPayrollFamiliesInfo h WHERE h.empId = :empId")
})
public class HrPayrollFamiliesInfo implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HR_PAYROLL_MORGAGE_INFO_SEQ")
    @SequenceGenerator(name = "HR_PAYROLL_MORGAGE_INFO_SEQ", sequenceName = "HR_PAYROLL_MORGAGE_INFO_SEQ", allocationSize = 1)
    private BigDecimal id;
    @Size(max = 20)
    @Column(name = "FIRST_NAME")
    private String firstName;
    @Size(max = 20)
    @Column(name = "MIDDLE_NAME")
    private String middleName;
    @Size(max = 20)
    @Column(name = "LAST_NAME")
    private String lastName;
    @Size(max = 20)
    @Column(name = "ACCOUNT_NUMBER")
    private String accountNumber;
    @Size(max = 20)
    @Column(name = "APPLIED_FROM")
    private String appliedFrom;
    @Size(max = 20)
    @Column(name = "APPLIED_TO")
    private String appliedTo;
    @Size(max = 20)
    @Column(name = "STATUS")
    private String status;

    @JoinColumn(name = "EMP_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrEmployees empId;

//    @Column(name = "EMP_ID")
//    private BigInteger empId;
    @JoinColumn(name = "EARNING_DED_CODE", referencedColumnName = "CODE")
    @ManyToOne
    private HrPayrollEarningDeductions earningDedCode;

    @JoinColumn(name = "PL_PG", referencedColumnName = "ID")
    @ManyToOne
    private HrPayrollPlPg plPg;

    public HrPayrollFamiliesInfo() {
    }

    public HrPayrollFamiliesInfo(BigDecimal id) {
        this.id = id;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

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

//
//    public BigInteger getEmpId() {
//        return empId;
//    }
//
//    public void setEmpId(BigInteger empId) {
//        this.empId = empId;
//    }
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
        if (!(object instanceof HrPayrollFamiliesInfo)) {
            return false;
        }
        HrPayrollFamiliesInfo other = (HrPayrollFamiliesInfo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.eep.hrms.entity.payroll.HrPayrollFamiliesInfo[ id=" + id + " ]";
    }

}
