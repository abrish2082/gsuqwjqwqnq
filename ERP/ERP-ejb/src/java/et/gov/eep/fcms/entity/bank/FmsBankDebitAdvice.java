/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.entity.bank;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
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
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import et.gov.eep.fcms.entity.loan.FmsLoanPaymentSummary;

/**
 *
 * @author mubejbl
 */
@Entity
@Table(name = "FMS_BANK_DEBIT_ADVICE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FmsBankDebitAdvice.findAll", query = "SELECT f FROM FmsBankDebitAdvice f"),
    @NamedQuery(name = "FmsBankDebitAdvice.findByDebitAdviceId", query = "SELECT f FROM FmsBankDebitAdvice f WHERE f.debitAdviceId = :debitAdviceId"),
    @NamedQuery(name = "FmsBankDebitAdvice.findByTranRef", query = "SELECT f FROM FmsBankDebitAdvice f WHERE f.tranRef = :tranRef"),
    @NamedQuery(name = "FmsBankDebitAdvice.findByAccountNumber", query = "SELECT f FROM FmsBankDebitAdvice f WHERE f.accountNumber = :accountNumber"),
    @NamedQuery(name = "FmsBankDebitAdvice.findByAccNoANDdateANDtranRef", query = "SELECT f FROM FmsBankDebitAdvice f WHERE f.accountNumber = :accountNumber AND f.debitedDate = :debitedDate AND f.tranRef = :tranRef"),
    @NamedQuery(name = "FmsBankDebitAdvice.findTransactionByAccNoANDdate", query = "SELECT f FROM FmsBankDebitAdvice f WHERE f.accountNumber = :accountNumber AND f.debitedDate = :debitedDate"),
    @NamedQuery(name = "FmsBankDebitAdvice.findByAmount", query = "SELECT f FROM FmsBankDebitAdvice f WHERE f.amount = :amount"),
    @NamedQuery(name = "FmsBankDebitAdvice.findBySubsidiaryId", query = "SELECT f FROM FmsBankDebitAdvice f WHERE f.subsidiaryId = :subsidiaryId"),
    @NamedQuery(name = "FmsBankDebitAdvice.findByReason", query = "SELECT f FROM FmsBankDebitAdvice f WHERE f.reason = :reason"),
    @NamedQuery(name = "FmsBankDebitAdvice.findByDebitedDate", query = "SELECT f FROM FmsBankDebitAdvice f WHERE f.debitedDate = :debitedDate"),
    @NamedQuery(name = "FmsBankDebitAdvice.findByBankId", query = "SELECT f FROM FmsBankDebitAdvice f WHERE f.bankId = :bankId"),
    @NamedQuery(name = "FmsBankDebitAdvice.findByBranchName", query = "SELECT f FROM FmsBankDebitAdvice f WHERE f.branchName = :branchName")})
public class FmsBankDebitAdvice implements Serializable {

    private static final long serialVersionUID = 1L;
    //<editor-fold defaultstate="collapsed" desc="variable declaration">
    @Id
    @Basic(optional = false)
    @NotNull
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FMS_BANK_DEBIT_ADVICE_SEQ")
    @SequenceGenerator(name = "FMS_BANK_DEBIT_ADVICE_SEQ", sequenceName = "FMS_BANK_DEBIT_ADVICE_SEQ", allocationSize = 1)
    @Column(name = "DEBIT_ADVICE_ID")
    private Integer debitAdviceId;
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
    @Column(name = "DEBITED_DATE")
    @Temporal(TemporalType.DATE)
    private Date debitedDate;
    @JoinColumn(name = "BANK_ID", referencedColumnName = "BANK_ID")
    @ManyToOne
    private FmsBank bankId;
    @Size(max = 50)
    @Column(name = "SUBSIDIARY_ID")
    private String subsidiaryId;
    @Size(max = 20)
    @Column(name = "BRANCH_NAME")
    private String branchName;
    @OneToMany(mappedBy = "bankAdviceFk")
    private List<FmsLoanPaymentSummary> fmsLoanPaymentSummaryList;
    @Transient
    String display_conn;
//</editor-fold>

    public FmsBankDebitAdvice() {
    }

    //<editor-fold defaultstate="collapsed" desc="getter and setter">
    public FmsBankDebitAdvice(Integer debitAdviceId) {
        this.debitAdviceId = debitAdviceId;
    }

    public Integer getDebitAdviceId() {
        return debitAdviceId;
    }

    public void setDebitAdviceId(Integer debitAdviceId) {
        this.debitAdviceId = debitAdviceId;
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

    public String getSubsidiaryId() {
        return subsidiaryId;
    }

    public void setSubsidiaryId(String subsidiaryId) {
        this.subsidiaryId = subsidiaryId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Date getDebitedDate() {
        return debitedDate;
    }

    public void setDebitedDate(Date debitedDate) {
        this.debitedDate = debitedDate;
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
        hash += (debitAdviceId != null ? debitAdviceId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof FmsBankDebitAdvice)) {
            return false;
        }
        FmsBankDebitAdvice other = (FmsBankDebitAdvice) object;
        if ((this.debitAdviceId == null && other.debitAdviceId != null) || (this.debitAdviceId != null && !this.debitAdviceId.equals(other.debitAdviceId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return debitAdviceId.toString();
    }

    @XmlTransient
    public List<FmsLoanPaymentSummary> getFmsLoanPaymentSummaryList() {
        return fmsLoanPaymentSummaryList;
    }

    public void setFmsLoanPaymentSummaryList(List<FmsLoanPaymentSummary> fmsLoanPaymentSummaryList) {
        this.fmsLoanPaymentSummaryList = fmsLoanPaymentSummaryList;
    }

}
