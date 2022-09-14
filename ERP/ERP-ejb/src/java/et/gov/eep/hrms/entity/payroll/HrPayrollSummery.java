/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.entity.payroll;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author memube
 */
@Entity
@Table(name = "HR_PAYROLL_SUMMERY")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HrPayrollSummery.findAll", query = "SELECT h FROM HrPayrollSummery h"),
    @NamedQuery(name = "HrPayrollSummery.findById", query = "SELECT h FROM HrPayrollSummery h WHERE h.id = :id"),
    @NamedQuery(name = "HrPayrollSummery.findByAccountCode", query = "SELECT h FROM HrPayrollSummery h WHERE h.accountCode = :accountCode"),
    @NamedQuery(name = "HrPayrollSummery.findByDebit", query = "SELECT h FROM HrPayrollSummery h WHERE h.debit = :debit"),
    @NamedQuery(name = "HrPayrollSummery.findByCredit", query = "SELECT h FROM HrPayrollSummery h WHERE h.credit = :credit"),
    @NamedQuery(name = "HrPayrollSummery.findByStatus", query = "SELECT h FROM HrPayrollSummery h WHERE h.status = :status"),
    @NamedQuery(name = "HrPayrollSummery.findByPayrollId", query = "SELECT h FROM HrPayrollSummery h WHERE h.payrollId = :payrollId"),
    @NamedQuery(name = "HrPayrollSummery.findByEdCode", query = "SELECT h FROM HrPayrollSummery h WHERE h.edCode = :edCode"),
    @NamedQuery(name = "HrPayrollSummery.findByPaymentLevel", query = "SELECT h FROM HrPayrollSummery h WHERE h.paymentLevel = :paymentLevel")})
public class HrPayrollSummery implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private BigDecimal id;
    @Size(max = 50)
    @Column(name = "ACCOUNT_CODE")
    private String accountCode;
    @Column(name = "DEBIT")
    private BigDecimal debit;
    @Column(name = "CREDIT")
    private BigDecimal credit;
    @Column(name = "STATUS")
    private Integer status;
    @Column(name = "PAYROLL_ID")
    private BigInteger payrollId;
    @Column(name = "ED_CODE")
    private BigInteger edCode;
    @Size(max = 20)
    @Column(name = "PAYMENT_LEVEL")
    private String paymentLevel;

    public HrPayrollSummery() {
    }

    public HrPayrollSummery(BigDecimal id) {
        this.id = id;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getAccountCode() {
        return accountCode;
    }

    public void setAccountCode(String accountCode) {
        this.accountCode = accountCode;
    }

    public BigDecimal getDebit() {
        return debit;
    }

    public void setDebit(BigDecimal debit) {
        this.debit = debit;
    }

    public BigDecimal getCredit() {
        return credit;
    }

    public void setCredit(BigDecimal credit) {
        this.credit = credit;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public BigInteger getPayrollId() {
        return payrollId;
    }

    public void setPayrollId(BigInteger payrollId) {
        this.payrollId = payrollId;
    }

    public BigInteger getEdCode() {
        return edCode;
    }

    public void setEdCode(BigInteger edCode) {
        this.edCode = edCode;
    }

    public String getPaymentLevel() {
        return paymentLevel;
    }

    public void setPaymentLevel(String paymentLevel) {
        this.paymentLevel = paymentLevel;
    }
    @Transient
    String display_conn;

    public String getDisplay_conn() {
        return display_conn;
    }

    public void setDisplay_conn(String display_conn) {
        this.display_conn = display_conn;
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
        if (!(object instanceof HrPayrollSummery)) {
            return false;
        }
        HrPayrollSummery other = (HrPayrollSummery) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.eep.hrms.entity.payroll.HrPayrollSummery[ id=" + id + " ]";
    }

}
