/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.entity.bank;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author mubejbl
 */
@Entity
@Table(name = "FMS_BANK_CHEQUE_DEPOSIT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FmsBankChequeDeposit.findAll", query = "SELECT f FROM FmsBankChequeDeposit f"),
    @NamedQuery(name = "FmsBankChequeDeposit.findByChequeDepositId", query = "SELECT f FROM FmsBankChequeDeposit f WHERE f.chequeDepositId = :chequeDepositId"),
    @NamedQuery(name = "FmsBankChequeDeposit.findByBankId", query = "SELECT f FROM FmsBankChequeDeposit f WHERE f.bankId = :bankId"),
    @NamedQuery(name = "FmsBankChequeDeposit.findByBranchName", query = "SELECT f FROM FmsBankChequeDeposit f WHERE f.branchName = :branchName"),
    @NamedQuery(name = "FmsBankChequeDeposit.findByAccountCode", query = "SELECT f FROM FmsBankChequeDeposit f WHERE f.accountCode = :accountCode"),
    @NamedQuery(name = "FmsBankChequeDeposit.findByAccountNumber", query = "SELECT f FROM FmsBankChequeDeposit f WHERE f.accountNumber = :accountNumber"),
    @NamedQuery(name = "FmsBankChequeDeposit.findByChequeNumber", query = "SELECT f FROM FmsBankChequeDeposit f WHERE f.chequeNumber = :chequeNumber"),
    @NamedQuery(name = "FmsBankChequeDeposit.findByAmount", query = "SELECT f FROM FmsBankChequeDeposit f WHERE f.amount = :amount"),
    @NamedQuery(name = "FmsBankChequeDeposit.findByChequeDepositDate", query = "SELECT f FROM FmsBankChequeDeposit f WHERE f.chequeDepositDate = :chequeDepositDate"),
    @NamedQuery(name = "FmsBankChequeDeposit.findByChequeDepositDateLike", query = "SELECT f FROM FmsBankChequeDeposit f WHERE f.chequeDepositDate LIKE :chequeDepositDate"),
    @NamedQuery(name = "FmsBankChequeDeposit.findTransactionByAccNoANDdate", query = "SELECT f FROM FmsBankChequeDeposit f WHERE f.accountNumber = :accountNumber AND f.chequeDepositDate = :chequeDepositDate"),
    @NamedQuery(name = "FmsBankChequeDeposit.findByAccNoANDdateANDchkNo", query = "SELECT f FROM FmsBankChequeDeposit f WHERE f.accountNumber = :accountNumber AND f.chequeDepositDate = :chequeDepositDate AND f.chequeNumber = :chequeNumber"),
    @NamedQuery(name = "FmsBankChequeDeposit.findByReason", query = "SELECT f FROM FmsBankChequeDeposit f WHERE f.reason = :reason")})
public class FmsBankChequeDeposit implements Serializable {

    private static final long serialVersionUID = 1L;
    //<editor-fold defaultstate="collapsed" desc="variable declaration">
    @Id
    @Basic(optional = false)
    @NotNull
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FMS_BANK_CHEQUE_DEPOSIT_SEQ")
    @SequenceGenerator(name = "FMS_BANK_CHEQUE_DEPOSIT_SEQ", sequenceName = "FMS_BANK_CHEQUE_DEPOSIT_SEQ", allocationSize = 1)
    @Column(name = "CHEQUE_DEPOSIT_ID")
    private Integer chequeDepositId;
    @JoinColumn(name = "BANK_ID", referencedColumnName = "BANK_ID")
    @ManyToOne
    private FmsBank bankId;
    @Column(name = "BRANCH_NAME")
    private String branchName;
    @Size(max = 45)
    @Column(name = "ACCOUNT_CODE")
    private String accountCode;
    @Column(name = "ACCOUNT_NUMBER")
    private String accountNumber;
    @Column(name = "CHEQUE_NUMBER")
    private String chequeNumber;
    @Column(name = "AMOUNT")
    private Double amount;
    @Column(name = "CHEQUE_DEPOSIT_DATE")
    @Temporal(TemporalType.DATE)
    private Date chequeDepositDate;
    @Column(name = "REASON")
    private String reason;
    @Column(name = "DEPOSITED_BY")
    private String depositedBy;
    @Transient
    String display_conn;
    @Transient
    String depositedByOption;

    //</editor-fold>

    public FmsBankChequeDeposit() {
    }

    //<editor-fold defaultstate="collapsed" desc="getter and setter">

    public FmsBankChequeDeposit(Integer chequeDepositId) {
        this.chequeDepositId = chequeDepositId;
    }

    public Integer getChequeDepositId() {
        return chequeDepositId;
    }

    public void setChequeDepositId(Integer chequeDepositId) {
        this.chequeDepositId = chequeDepositId;
    }

    public FmsBank getBankId() {
        return bankId;
    }

    public void setBankId(FmsBank bankId) {
        this.bankId = bankId;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getAccountCode() {
        return accountCode;
    }

    public void setAccountCode(String accountCode) {
        this.accountCode = accountCode;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getChequeNumber() {
        return chequeNumber;
    }

    public void setChequeNumber(String chequeNumber) {
        this.chequeNumber = chequeNumber;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Date getChequeDepositDate() {
        return chequeDepositDate;
    }

    public void setChequeDepositDate(Date chequeDepositDate) {
        this.chequeDepositDate = chequeDepositDate;
    }

    public String getReason() {
        return reason;
    }

    public String getDisplay_conn() {
        return display_conn;
    }

    public void setDisplay_conn(String display_conn) {
        this.display_conn = display_conn;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getDepositedBy() {
        return depositedBy;
    }

    public void setDepositedBy(String depositedBy) {
        this.depositedBy = depositedBy;
    }

    public String getDepositedByOption() {
        return depositedByOption;
    }

    public void setDepositedByOption(String depositedByOption) {
        this.depositedByOption = depositedByOption;
    }
//</editor-fold>

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (chequeDepositId != null ? chequeDepositId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof FmsBankChequeDeposit)) {
            return false;
        }
        FmsBankChequeDeposit other = (FmsBankChequeDeposit) object;
        if ((this.chequeDepositId == null && other.chequeDepositId != null) || (this.chequeDepositId != null && !this.chequeDepositId.equals(other.chequeDepositId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return chequeDepositId.toString();
    }

}
