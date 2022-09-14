/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.entity.loan;

import et.gov.eep.fcms.entity.bank.FmsBankDebitAdvice;
import java.io.Serializable;
import java.math.BigDecimal;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author XX
 */
@Entity
@Table(name = "FMS_LOAN_PAYMENT_SUMMARY")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FmsLoanPaymentSummary.findAll", query = "SELECT f FROM FmsLoanPaymentSummary f"),
    @NamedQuery(name = "FmsLoanPaymentSummary.findByLpsid", query = "SELECT f FROM FmsLoanPaymentSummary f WHERE f.lpsid = :lpsid"),
    @NamedQuery(name = "FmsLoanPaymentSummary.findByPaymentDate", query = "SELECT f FROM FmsLoanPaymentSummary f WHERE f.paymentDate = :paymentDate"),
    @NamedQuery(name = "FmsLoanPaymentSummary.findByCapitalPayment", query = "SELECT f FROM FmsLoanPaymentSummary f WHERE f.capitalPayment = :capitalPayment"),
    @NamedQuery(name = "FmsLoanPaymentSummary.findByInterestPayment", query = "SELECT f FROM FmsLoanPaymentSummary f WHERE f.interestPayment = :interestPayment"),
    @NamedQuery(name = "FmsLoanPaymentSummary.findByDefaultInterestRate", query = "SELECT f FROM FmsLoanPaymentSummary f WHERE f.defaultInterestRate = :defaultInterestRate"),
    @NamedQuery(name = "FmsLoanPaymentSummary.findByDefaultInterestAmount", query = "SELECT f FROM FmsLoanPaymentSummary f WHERE f.defaultInterestAmount = :defaultInterestAmount"),
    @NamedQuery(name = "FmsLoanPaymentSummary.findByManagementFee", query = "SELECT f FROM FmsLoanPaymentSummary f WHERE f.managementFee = :managementFee"),
    @NamedQuery(name = "FmsLoanPaymentSummary.findByOtherPayment", query = "SELECT f FROM FmsLoanPaymentSummary f WHERE f.otherPayment = :otherPayment"),
    @NamedQuery(name = "FmsLoanPaymentSummary.findByTotalPayment", query = "SELECT f FROM FmsLoanPaymentSummary f WHERE f.totalPayment = :totalPayment"),
    @NamedQuery(name = "FmsLoanPaymentSummary.findByLetterReference", query = "SELECT f FROM FmsLoanPaymentSummary f WHERE f.letterReference = :letterReference"),
    @NamedQuery(name = "FmsLoanPaymentSummary.findByExchangeRate", query = "SELECT f FROM FmsLoanPaymentSummary f WHERE f.exchangeRate = :exchangeRate"),
    @NamedQuery(name = "FmsLoanPaymentSummary.findByLoanFk", query = "SELECT f FROM FmsLoanPaymentSummary f WHERE f.loanFk = :loanFk ORDER BY f.lpsid ASC"),
    @NamedQuery(name = "FmsLoanPaymentSummary.findByPaymentInBirr", query = "SELECT f FROM FmsLoanPaymentSummary f WHERE f.paymentInBirr = :paymentInBirr")})
public class FmsLoanPaymentSummary implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "LPSID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FMS_SEQ_LOAN_SUMMARY")
    @SequenceGenerator(name = "FMS_SEQ_LOAN_SUMMARY", sequenceName = "FMS_SEQ_LOAN_SUMMARY", allocationSize = 1)
    private Integer lpsid;
    @Column(name = "PAYMENT_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date paymentDate;
    @Column(name = "CAPITAL_PAYMENT")
    private BigDecimal capitalPayment;
    @Column(name = "INTEREST_PAYMENT")
    private BigDecimal interestPayment;
    @Column(name = "DEFAULT_INTEREST_RATE")
    private BigDecimal defaultInterestRate;
    @Column(name = "DEFAULT_INTEREST_AMOUNT")
    private BigDecimal defaultInterestAmount;
    @Column(name = "MANAGEMENT_FEE")
    private BigDecimal managementFee;
    @Column(name = "OTHER_PAYMENT")
    private BigDecimal otherPayment;
    @Column(name = "TOTAL_PAYMENT")
    private BigDecimal totalPayment;
    @Column(name = "ESTIMATED_TOTAL_PAYMENT")
    private BigDecimal estimatedTotalPayment;
    @Column(name = "LETTER_REFERENCE")
    private String letterReference;
    @Column(name = "EXCHANGE_RATE")
    private BigDecimal exchangeRate;
    @Column(name = "PAYMENT_IN_BIRR")
    private BigDecimal paymentInBirr;
    @Column(name = "COMMITMENT_FEE")
    private BigDecimal commitmentFee;
    @JoinColumn(name = "BANK_ADVICE_FK", referencedColumnName = "DEBIT_ADVICE_ID")
    @ManyToOne
    private FmsBankDebitAdvice bankAdviceFk;
    @JoinColumn(name = "LOAN_FK", referencedColumnName = "LOAN_ID")
    @ManyToOne
    private FmsLoan loanFk;

    public FmsLoanPaymentSummary() {
    }

    public FmsLoanPaymentSummary(Integer lpsid) {
        this.lpsid = lpsid;
    }

    public Integer getLpsid() {
        return lpsid;
    }

    public void setLpsid(Integer lpsid) {
        this.lpsid = lpsid;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public BigDecimal getCapitalPayment() {
        return capitalPayment;
    }

    public void setCapitalPayment(BigDecimal capitalPayment) {
        this.capitalPayment = capitalPayment;
    }

    public BigDecimal getInterestPayment() {
        return interestPayment;
    }

    public void setInterestPayment(BigDecimal interestPayment) {
        this.interestPayment = interestPayment;
    }

    public BigDecimal getDefaultInterestRate() {
        return defaultInterestRate;
    }

    public void setDefaultInterestRate(BigDecimal defaultInterestRate) {
        this.defaultInterestRate = defaultInterestRate;
    }

    public BigDecimal getDefaultInterestAmount() {
        return defaultInterestAmount;
    }

    public void setDefaultInterestAmount(BigDecimal defaultInterestAmount) {
        this.defaultInterestAmount = defaultInterestAmount;
    }

    public BigDecimal getManagementFee() {
        return managementFee;
    }

    public void setManagementFee(BigDecimal managementFee) {
        this.managementFee = managementFee;
    }

    public BigDecimal getOtherPayment() {
        return otherPayment;
    }

    public void setOtherPayment(BigDecimal otherPayment) {
        this.otherPayment = otherPayment;
    }

    public BigDecimal getTotalPayment() {
        return totalPayment;
    }

    public void setTotalPayment(BigDecimal totalPayment) {
        this.totalPayment = totalPayment;
    }

    public BigDecimal getEstimatedTotalPayment() {
        return estimatedTotalPayment;
    }

    public void setEstimatedTotalPayment(BigDecimal estimatedTotalPayment) {
        this.estimatedTotalPayment = estimatedTotalPayment;
    }

    public BigDecimal getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(BigDecimal exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public BigDecimal getPaymentInBirr() {
        return paymentInBirr;
    }

    public void setPaymentInBirr(BigDecimal paymentInBirr) {
        this.paymentInBirr = paymentInBirr;
    }

    public BigDecimal getCommitmentFee() {
        return commitmentFee;
    }

    public void setCommitmentFee(BigDecimal commitmentFee) {
        this.commitmentFee = commitmentFee;
    }

    public String getLetterReference() {
        return letterReference;
    }

    public void setLetterReference(String letterReference) {
        this.letterReference = letterReference;
    }

    public FmsBankDebitAdvice getBankAdviceFk() {
        return bankAdviceFk;
    }

    public void setBankAdviceFk(FmsBankDebitAdvice bankAdviceFk) {
        this.bankAdviceFk = bankAdviceFk;
    }

    public FmsLoan getLoanFk() {
        return loanFk;
    }

    public void setLoanFk(FmsLoan loanFk) {
        this.loanFk = loanFk;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (lpsid != null ? lpsid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FmsLoanPaymentSummary)) {
            return false;
        }
        FmsLoanPaymentSummary other = (FmsLoanPaymentSummary) object;
        if ((this.lpsid == null && other.lpsid != null) || (this.lpsid != null && !this.lpsid.equals(other.lpsid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.eep.fcms.entity.loan.FmsLoanPaymentSummary[ lpsid=" + lpsid + " ]";
    }

}
