/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.entity.bank;

import java.io.Serializable;
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
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import et.gov.eep.fcms.entity.admin.FmsSubsidiaryLedger;

/**
 *
 * @author mubejbl
 */
@Entity
@Table(name = "FMS_BANK_BRANCH_ACCOUNTS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FmsBankBranchAccounts.findAll", query = "SELECT f FROM FmsBankBranchAccounts f"),
    @NamedQuery(name = "FmsBankBranchAccounts.findByBranchAccountId", query = "SELECT f FROM FmsBankBranchAccounts f WHERE f.branchAccountId = :branchAccountId"),
    @NamedQuery(name = "FmsBankBranchAccounts.findByAccountNumber", query = "SELECT f FROM FmsBankBranchAccounts f WHERE f.accountNumber = :accountNumber"),
    @NamedQuery(name = "FmsBankBranchAccounts.findByBankAccountId", query = "SELECT f FROM FmsBankBranchAccounts f WHERE f.bankAccountId = :bankAccountId"),
    @NamedQuery(name = "FmsBankBranchAccounts.findByStatus", query = "SELECT f FROM FmsBankBranchAccounts f WHERE f.status = :status"),
    @NamedQuery(name = "FmsBankBranchAccounts.findByAccountNumberLike", query = "SELECT f FROM FmsBankBranchAccounts f WHERE f.accountNumber LIKE :accountNumber"),
    @NamedQuery(name = "FmsBankBranchAccounts.findBySubsidiaryId", query = "SELECT f FROM FmsBankBranchAccounts f WHERE f.subsidiaryId = :subsidiaryId"),
    @NamedQuery(name = "FmsBankBranchAccounts.findByBeginningBalance", query = "SELECT f FROM FmsBankBranchAccounts f WHERE f.beginningBalance = :beginningBalance")})

public class FmsBankBranchAccounts implements Serializable {

    private static final long serialVersionUID = 1L;
    //<editor-fold defaultstate="collapsed" desc="variable declaration">
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FMS_BANK_BRANCH_ACCOUNTS_SEQ")
    @SequenceGenerator(name = "FMS_BANK_BRANCH_ACCOUNTS_SEQ", sequenceName = "FMS_BANK_BRANCH_ACCOUNTS_SEQ", allocationSize = 1)
    @Column(name = "BRANCH_ACCOUNT_ID")
    private Integer branchAccountId;
    @Size(max = 22)
    @Column(name = "ACCOUNT_NUMBER")
    private String accountNumber;
    @Size(max = 20)
    @Column(name = "STATUS")
    private String status;
    @Column(name = "BEGINNING_BALANCE")
    @Digits(integer = 50, fraction = 2)
    private Double beginningBalance;
    @JoinColumn(name = "BANK_ACCOUNT_ID", referencedColumnName = "BANK_ACCOUNT_ID")
    @ManyToOne
    private FmsBankAccount bankAccountId;
    @JoinColumn(name = "SUBSIDIARY_ID", referencedColumnName = "SUBSIDIARY_ID")
    @OneToOne
    private FmsSubsidiaryLedger subsidiaryId;
    @Transient
    private String conc;
    @Transient
    String display_conn;
//</editor-fold>

    public FmsBankBranchAccounts() {
    }
//<editor-fold defaultstate="collapsed" desc="getter and setter">

    public String getConc() {
        return conc;
    }

    public void setConc(String conc) {
        this.conc = conc;
    }

    public FmsSubsidiaryLedger getSubsidiaryId() {
        if (subsidiaryId == null) {
            subsidiaryId = new FmsSubsidiaryLedger();
        }
        return subsidiaryId;
    }

    public void setSubsidiaryId(FmsSubsidiaryLedger subsidiaryId) {
        this.subsidiaryId = subsidiaryId;
    }

    public FmsBankBranchAccounts(Integer branchAccountId) {
        this.branchAccountId = branchAccountId;
    }

    public Integer getBranchAccountId() {
        return branchAccountId;
    }

    public void setBranchAccountId(Integer branchAccountId) {
        this.branchAccountId = branchAccountId;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getBeginningBalance() {
        return beginningBalance;
    }

    public void setBeginningBalance(Double beginningBalance) {
        this.beginningBalance = beginningBalance;
    }

    public FmsBankAccount getBankAccountId() {
        return bankAccountId;
    }

    public void setBankAccountId(FmsBankAccount bankAccountId) {
        this.bankAccountId = bankAccountId;
    }

    public String getDisplay_conn() {
        return display_conn;
    }

    public void setDisplay_conn(String display_conn) {
        this.display_conn = display_conn;
    }
//</editor-fold>

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (branchAccountId != null ? branchAccountId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof FmsBankBranchAccounts)) {
            return false;
        }
        FmsBankBranchAccounts other = (FmsBankBranchAccounts) object;
        if ((this.branchAccountId == null && other.branchAccountId != null) || (this.branchAccountId != null && !this.branchAccountId.equals(other.branchAccountId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return accountNumber;
    }

}
