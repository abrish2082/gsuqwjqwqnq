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
@Table(name = "FMS_BANK_CASH_DEPOSIT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FmsBankCashDeposit.findAll", query = "SELECT f FROM FmsBankCashDeposit f"),
    @NamedQuery(name = "FmsBankCashDeposit.findByCashDepositId", query = "SELECT f FROM FmsBankCashDeposit f WHERE f.cashDepositId = :cashDepositId"),
    @NamedQuery(name = "FmsBankCashDeposit.findByAccountCode", query = "SELECT f FROM FmsBankCashDeposit f WHERE f.accountCode = :accountCode"),
    @NamedQuery(name = "FmsBankCashDeposit.findByAccountNumber", query = "SELECT f FROM FmsBankCashDeposit f WHERE f.accountNumber = :accountNumber"),
    @NamedQuery(name = "FmsBankCashDeposit.findTransactionByAccNoANDdate", query = "SELECT f FROM FmsBankCashDeposit f WHERE f.accountNumber = :accountNumber AND f.cashDepositDate = :cashDepositDate"),
    @NamedQuery(name = "FmsBankCashDeposit.findByAccNoANDdateANDtranRef", query = "SELECT f FROM FmsBankCashDeposit f WHERE f.accountNumber = :accountNumber AND f.cashDepositDate = :cashDepositDate AND f.tranRef = :tranRef"),
    @NamedQuery(name = "FmsBankCashDeposit.findByAmount", query = "SELECT f FROM FmsBankCashDeposit f WHERE f.amount = :amount"),
    @NamedQuery(name = "FmsBankCashDeposit.findByCashDepositDateLike", query = "SELECT f FROM FmsBankCashDeposit f WHERE f.cashDepositDate LIKE :cashDepositDate"),
    @NamedQuery(name = "FmsBankCashDeposit.findByCashDepositDate", query = "SELECT f FROM FmsBankCashDeposit f WHERE f.cashDepositDate = :cashDepositDate"),
    @NamedQuery(name = "FmsBankCashDeposit.findByTranRef", query = "SELECT f FROM FmsBankCashDeposit f WHERE f.tranRef = :tranRef"),
    @NamedQuery(name = "FmsBankCashDeposit.findByReason", query = "SELECT f FROM FmsBankCashDeposit f WHERE f.reason = :reason")})

public class FmsBankCashDeposit implements Serializable {

    private static final long serialVersionUID = 1L;
    //<editor-fold defaultstate="collapsed" desc="variable declaration">
    @Id
    @Basic(optional = false)
    @NotNull
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FMS_BANK_CASH_DEPOSIT_SEQ")
    @SequenceGenerator(name = "FMS_BANK_CASH_DEPOSIT_SEQ", sequenceName = "FMS_BANK_CASH_DEPOSIT_SEQ", allocationSize = 1)
    @Column(name = "CASH_DEPOSIT_ID")
    private Integer cashDepositId;
    @Column(name = "BRANCH_NAME")
    private String branchName;
    @Size(max = 45)
    @Column(name = "ACCOUNT_CODE")
    private String accountCode;
    @Column(name = "ACCOUNT_NUMBER")
    private String accountNumber;
    @Column(name = "AMOUNT")
    private Double amount;
    @Column(name = "CASH_DEPOSIT_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date cashDepositDate;
    @Column(name = "REASON")
    private String reason;
    @Column(name = "DEPOSITED_BY")
    private String depositedBy;
    @Column(name = "TRAN_REF")
    private String tranRef;
    @JoinColumn(name = "BANK_ID", referencedColumnName = "BANK_ID")
    @ManyToOne
    private FmsBank bankId;
    @Transient
    String depositedByOption;
    @Transient
    String display_conn;
//</editor-fold>

    public FmsBankCashDeposit() {
    }

    //<editor-fold defaultstate="collapsed" desc="getter and setter">
    public FmsBankCashDeposit(Integer cashDepositId) {
        this.cashDepositId = cashDepositId;
    }

    public Integer getCashDepositId() {
        return cashDepositId;
    }

    public void setCashDepositId(Integer cashDepositId) {
        this.cashDepositId = cashDepositId;
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

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Date getCashDepositDate() {
        return cashDepositDate;
    }

    public void setCashDepositDate(Date cashDepositDate) {
        this.cashDepositDate = cashDepositDate;
    }

    public String getReason() {
        return reason;
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

    public String getTranRef() {
        return tranRef;
    }

    public void setTranRef(String tranRef) {
        this.tranRef = tranRef;
    }

    public FmsBank getBankId() {
        return bankId;
    }

    public void setBankId(FmsBank bankId) {
        this.bankId = bankId;
    }

    public String getDisplay_conn() {
        return display_conn;
    }

    public void setDisplay_conn(String display_conn) {
        this.display_conn = display_conn;
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
        hash += (cashDepositId != null ? cashDepositId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof FmsBankCashDeposit)) {
            return false;
        }
        FmsBankCashDeposit other = (FmsBankCashDeposit) object;
        if ((this.cashDepositId == null && other.cashDepositId != null) || (this.cashDepositId != null && !this.cashDepositId.equals(other.cashDepositId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return cashDepositId.toString();
    }

}
