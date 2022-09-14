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
@Table(name = "FMS_BANK_CREDIT_ADVICE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FmsBankCreditAdvice.findAll", query = "SELECT f FROM FmsBankCreditAdvice f"),
    @NamedQuery(name = "FmsBankCreditAdvice.findByCreditAdviceId", query = "SELECT f FROM FmsBankCreditAdvice f WHERE f.creditAdviceId = :creditAdviceId"),
    @NamedQuery(name = "FmsBankCreditAdvice.findByTranRef", query = "SELECT f FROM FmsBankCreditAdvice f WHERE f.tranRef = :tranRef"),
    @NamedQuery(name = "FmsBankCreditAdvice.findByAccNoANDdateANDtranRef", query = "SELECT f FROM FmsBankCreditAdvice f WHERE f.accountNumber = :accountNumber AND f.creditDate = :creditDate AND f.tranRef = :tranRef"),
    @NamedQuery(name = "FmsBankCreditAdvice.findTransactionByAccNoANDdate", query = "SELECT f FROM FmsBankCreditAdvice f WHERE f.accountNumber = :accountNumber AND f.creditDate = :creditDate"),
    @NamedQuery(name = "FmsBankCreditAdvice.findByAccountNumber", query = "SELECT f FROM FmsBankCreditAdvice f WHERE f.accountNumber = :accountNumber"),
    @NamedQuery(name = "FmsBankCreditAdvice.findByAmount", query = "SELECT f FROM FmsBankCreditAdvice f WHERE f.amount = :amount"),
    @NamedQuery(name = "FmsBankCreditAdvice.findBySubsidiaryId", query = "SELECT f FROM FmsBankCreditAdvice f WHERE f.subsidaryId = :subsidaryId"),
    @NamedQuery(name = "FmsBankCreditAdvice.findByReason", query = "SELECT f FROM FmsBankCreditAdvice f WHERE f.reason = :reason"),
    @NamedQuery(name = "FmsBankCreditAdvice.findByCreditDate", query = "SELECT f FROM FmsBankCreditAdvice f WHERE f.creditDate = :creditDate"),
    @NamedQuery(name = "FmsBankCreditAdvice.findByBankId", query = "SELECT f FROM FmsBankCreditAdvice f WHERE f.bankId = :bankId"),
    @NamedQuery(name = "FmsBankCreditAdvice.findByBranchName", query = "SELECT f FROM FmsBankCreditAdvice f WHERE f.branchName = :branchName")})
public class FmsBankCreditAdvice implements Serializable {

    private static final long serialVersionUID = 1L;
    //<editor-fold defaultstate="collapsed" desc="variable declaration">
    @Id
    @Basic(optional = false)
    @NotNull
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FMS_BANK_CREDIT_ADVICE_SEQ")
    @SequenceGenerator(name = "FMS_BANK_CREDIT_ADVICE_SEQ", sequenceName = "FMS_BANK_CREDIT_ADVICE_SEQ", allocationSize = 1)
    @Column(name = "CREDIT_ADVICE_ID")
    private Integer creditAdviceId;
    @Size(max = 20)
    @Column(name = "TRAN_REF")
    private String tranRef;
    @Size(max = 20)
    @Column(name = "ACCOUNT_NUMBER")
    private String accountNumber;
    @Column(name = "AMOUNT")
    private Double amount;
    @Size(max = 255)
    @Column(name = "REASON")
    private String reason;
    @Column(name = "CREDIT_DATE")
    @Temporal(TemporalType.DATE)
    private Date creditDate;
    @JoinColumn(name = "BANK_ID", referencedColumnName = "BANK_ID")
    @ManyToOne
    private FmsBank bankId;
    @Size(max = 50)
    @Column(name = "SUBSIDIARY_ID")
    private String subsidaryId;
    @Size(max = 20)
    @Column(name = "BRANCH_NAME")
    private String branchName;
    @Transient
    String display_conn;
//</editor-fold>

    public FmsBankCreditAdvice() {
    }

    //<editor-fold defaultstate="collapsed" desc="getter and setter">

    public FmsBankCreditAdvice(Integer creditAdviceId) {
        this.creditAdviceId = creditAdviceId;
    }

    public Integer getCreditAdviceId() {
        return creditAdviceId;
    }

    public void setCreditAdviceId(Integer creditAdviceId) {
        this.creditAdviceId = creditAdviceId;
    }

    public String getTranRef() {
        return tranRef;
    }

    public void setTranRef(String tranRef) {
        this.tranRef = tranRef;
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

    public String getSubsidaryId() {
        return subsidaryId;
    }

    public void setSubsidaryId(String subsidaryId) {
        this.subsidaryId = subsidaryId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Date getCreditDate() {
        return creditDate;
    }

    public void setCreditDate(Date creditDate) {
        this.creditDate = creditDate;
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
        hash += (creditAdviceId != null ? creditAdviceId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof FmsBankCreditAdvice)) {
            return false;
        }
        FmsBankCreditAdvice other = (FmsBankCreditAdvice) object;
        if ((this.creditAdviceId == null && other.creditAdviceId != null) || (this.creditAdviceId != null && !this.creditAdviceId.equals(other.creditAdviceId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return creditAdviceId.toString();
    }

}
