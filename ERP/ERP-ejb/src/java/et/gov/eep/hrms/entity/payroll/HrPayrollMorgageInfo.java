/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.entity.payroll;

import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.entity.lookup.HrLuBankBranches;
import et.gov.eep.hrms.entity.lookup.HrLuBanks;
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
@Table(name = "HR_PAYROLL_MORGAGE_INFO")
@XmlRootElement
@NamedQueries({
    //    @NamedQuery(name = "HrPayrollMorgageInfo.findAll", query = "SELECT h FROM HrPayrollMorgageInfo h"),
    //    @NamedQuery(name = "HrPayrollMorgageInfo.findById", query = "SELECT h FROM HrPayrollMorgageInfo h WHERE h.id = :id"),
    //    @NamedQuery(name = "HrPayrollMorgageInfo.findByBankName", query = "SELECT h FROM HrPayrollMorgageInfo h WHERE h.bankName = :bankName"),
    //    @NamedQuery(name = "HrPayrollMorgageInfo.findByBankBranch", query = "SELECT h FROM HrPayrollMorgageInfo h WHERE h.bankBranch = :bankBranch"),
    //    @NamedQuery(name = "HrPayrollMorgageInfo.findByEmpId", query = "SELECT h FROM HrPayrollMorgageInfo h WHERE h.empId = :empId"),
    //    @NamedQuery(name = "HrPayrollMorgageInfo.findByAppliedFrom", query = "SELECT h FROM HrPayrollMorgageInfo h WHERE h.appliedFrom = :appliedFrom"),
    //    @NamedQuery(name = "HrPayrollMorgageInfo.findByAppliedTo", query = "SELECT h FROM HrPayrollMorgageInfo h WHERE h.appliedTo = :appliedTo"),
    //    @NamedQuery(name = "HrPayrollMorgageInfo.findByStatus", query = "SELECT h FROM HrPayrollMorgageInfo h WHERE h.status = :status"),
    //    @NamedQuery(name = "HrPayrollMorgageInfo.findByAccountNumber", query = "SELECT h FROM HrPayrollMorgageInfo h WHERE h.accountNumber = :accountNumber"),
    //    @NamedQuery(name = "HrPayrollMorgageInfo.findByReceiversName", query = "SELECT h FROM HrPayrollMorgageInfo h WHERE h.receiversName = :receiversName")})
    @NamedQuery(name = "HrPayrollMorgageInfo.findByEmpId", query = "SELECT h FROM HrPayrollMorgageInfo h where h.empId.id=:id"),
    @NamedQuery(name = "HrPayrollMorgageInfo.activeOnlyOne", query = "update  HrPayrollMorgageInfo h set h.status='Inactive' where h.earningDedCode.code=:code and h.empId.id=:id"),
    @NamedQuery(name = "HrPayrollMorgageInfo.findAll", query = "SELECT h FROM HrPayrollMorgageInfo h"),
    @NamedQuery(name = "HrPayrollMorgageInfo.findById", query = "SELECT h FROM HrPayrollMorgageInfo h WHERE h.id = :id"),
    @NamedQuery(name = "HrPayrollMorgageInfo.findByAppliedFrom", query = "SELECT h FROM HrPayrollMorgageInfo h WHERE h.appliedFrom = :appliedFrom"),
    @NamedQuery(name = "HrPayrollMorgageInfo.findByAppliedTo", query = "SELECT h FROM HrPayrollMorgageInfo h WHERE h.appliedTo = :appliedTo"),
    @NamedQuery(name = "HrPayrollMorgageInfo.findByStatus", query = "SELECT h FROM HrPayrollMorgageInfo h WHERE h.status = :status")})

public class HrPayrollMorgageInfo implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")

    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HR_PAYROLL_MORGAGE_INFO_SEQ")
    @SequenceGenerator(name = "HR_PAYROLL_MORGAGE_INFO_SEQ", sequenceName = "HR_PAYROLL_MORGAGE_INFO_SEQ", allocationSize = 1)

    private BigDecimal id;
//    @Column(name = "BANK_NAME")
//    private BigInteger bankName;
//    @Column(name = "BANK_BRANCH")
//    private BigInteger bankBranch;
//    @Column(name = "EMP_ID")
//    private BigInteger empId;

    @JoinColumn(name = "EMP_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrEmployees empId;

    @JoinColumn(name = "BANK_NAME", referencedColumnName = "ID")
    @NotNull
    @ManyToOne
    private HrLuBanks bankName;
    @JoinColumn(name = "BANK_BRANCH", referencedColumnName = "ID")

    @NotNull
    @ManyToOne
    private HrLuBankBranches bankBranch;

    @Size(max = 20)
    @Column(name = "APPLIED_FROM")
    private String appliedFrom;
    @Size(max = 20)
    @Column(name = "APPLIED_TO")
    private String appliedTo;
    @Size(max = 20)
    @Column(name = "STATUS")
    private String status;
    @Size(max = 50)
    @Column(name = "ACCOUNT_NUMBER")
    private String accountNumber;
    @Size(max = 100)
    @Column(name = "RECEIVERS_NAME")
    private String receiversName;
    @JoinColumn(name = "EARNING_DED_CODE", referencedColumnName = "CODE")
    @ManyToOne
    private HrPayrollEarningDeductions earningDedCode;

    @JoinColumn(name = "PL_PG", referencedColumnName = "ID")
    @ManyToOne
    private HrPayrollPlPg plPg;

    public HrPayrollMorgageInfo() {
    }

    public HrPayrollMorgageInfo(BigDecimal id) {
        this.id = id;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

//    public BigInteger getBankName() {
//        return bankName;
//    }
//
//    public void setBankName(BigInteger bankName) {
//        this.bankName = bankName;
//    }
//
//    public BigInteger getBankBranch() {
//        return bankBranch;
//    }
//
//    public void setBankBranch(BigInteger bankBranch) {
//        this.bankBranch = bankBranch;
//    }
//    public BigInteger getEmpId() {
//        return empId;
//    }
//
//    public void setEmpId(BigInteger empId) {
//        this.empId = empId;
//    }
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

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
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

    /**
     *
     * @return
     */
    public HrLuBanks getBankName() {
        return bankName;
    }

    /**
     *
     * @param bankName
     */
    public void setBankName(HrLuBanks bankName) {
        this.bankName = bankName;
    }

    /**
     *
     * @return
     */
    public HrLuBankBranches getBankBranch() {
        return bankBranch;
    }

    /**
     *
     * @param bankBranch
     */
    public void setBankBranch(HrLuBankBranches bankBranch) {
        this.bankBranch = bankBranch;
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
        if (!(object instanceof HrPayrollMorgageInfo)) {
            return false;
        }
        HrPayrollMorgageInfo other = (HrPayrollMorgageInfo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.eep.hrms.entity.payroll.HrPayrollMorgageInfo[ id=" + id + " ]";
    }

}
