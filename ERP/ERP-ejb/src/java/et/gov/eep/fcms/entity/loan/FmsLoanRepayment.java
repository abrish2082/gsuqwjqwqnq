/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.entity.loan;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author XX
 */
@Entity
@Table(name = "FMS_LOAN_REPAYMENT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FmsLoanRepayment.findAll", query = "SELECT f FROM FmsLoanRepayment f"),
    @NamedQuery(name = "FmsLoanRepayment.findByLoanRepaymentId", query = "SELECT f FROM FmsLoanRepayment f WHERE f.loanRepaymentId = :loanRepaymentId"),
    @NamedQuery(name = "FmsLoanRepayment.findByScheduleDate", query = "SELECT f FROM FmsLoanRepayment f WHERE f.scheduleDate = :scheduleDate"),
    @NamedQuery(name = "FmsLoanRepayment.findByLiborRate", query = "SELECT f FROM FmsLoanRepayment f WHERE f.liborRate = :liborRate"),
    @NamedQuery(name = "FmsLoanRepayment.findByOpeningBalance", query = "SELECT f FROM FmsLoanRepayment f WHERE f.openingBalance = :openingBalance"),
    @NamedQuery(name = "FmsLoanRepayment.findByClosingBalance", query = "SELECT f FROM FmsLoanRepayment f WHERE f.closingBalance = :closingBalance"),
    @NamedQuery(name = "FmsLoanRepayment.findByCapitalRepayment", query = "SELECT f FROM FmsLoanRepayment f WHERE f.capitalRepayment = :capitalRepayment"),
    @NamedQuery(name = "FmsLoanRepayment.findByInterestRepayment", query = "SELECT f FROM FmsLoanRepayment f WHERE f.interestRepayment = :interestRepayment")})
public class FmsLoanRepayment implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "LOAN_REPAYMENT_ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FMS_SEQ_LOAN_REPAYMENT")
    @SequenceGenerator(schema = "ERP", name = "FMS_SEQ_LOAN_REPAYMENT", sequenceName = "FMS_SEQ_LOAN_REPAYMENT", allocationSize = 1)
    private Integer loanRepaymentId;
    @Column(name = "SCHEDULE_DATE")
    @Temporal(TemporalType.DATE)
    private Date scheduleDate;
    @Column(name = "LIBOR_RATE")
    private Float liborRate;
    @Column(name = "MARGIN")
    private BigDecimal margin;
    @Column(name = "DAYS")
    private BigDecimal days;
    @Column(name = "OPENING_BALANCE")
    private BigDecimal openingBalance;
    @Column(name = "CLOSING_BALANCE")
    private BigDecimal closingBalance;
    @Column(name = "CAPITAL_REPAYMENT")
    private BigDecimal capitalRepayment;
    @Column(name = "INTEREST_REPAYMENT")
    private BigDecimal interestRepayment;
    @JoinColumn(name = "DISBURSEMENT_FK", referencedColumnName = "DISBURSEMENT_ID")
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private FmsLoanDisbursement disbursementFk;

    public FmsLoanRepayment() {
    }

    public FmsLoanRepayment(Integer loanRepaymentId) {
        this.loanRepaymentId = loanRepaymentId;
    }

    public Integer getLoanRepaymentId() {
        return loanRepaymentId;
    }

    public void setLoanRepaymentId(Integer loanRepaymentId) {
        this.loanRepaymentId = loanRepaymentId;
    }

    public Date getScheduleDate() {
        return scheduleDate;
    }

    public void setScheduleDate(Date scheduleDate) {
        this.scheduleDate = scheduleDate;
    }

    public Float getLiborRate() {
        return liborRate;
    }

    public void setLiborRate(Float liborRate) {
        this.liborRate = liborRate;
    }

    public BigDecimal getMargin() {
        return margin;
    }

    public void setMargin(BigDecimal margin) {
        this.margin = margin;
    }

    public BigDecimal getDays() {
        return days;
    }

    public void setDays(BigDecimal days) {
        this.days = days;
    }

    public BigDecimal getOpeningBalance() {
        return openingBalance;
    }

    public void setOpeningBalance(BigDecimal openingBalance) {
        this.openingBalance = openingBalance;
    }

    public BigDecimal getClosingBalance() {
        return closingBalance;
    }

    public void setClosingBalance(BigDecimal closingBalance) {
        this.closingBalance = closingBalance;
    }

    public BigDecimal getCapitalRepayment() {
        return capitalRepayment;
    }

    public void setCapitalRepayment(BigDecimal capitalRepayment) {
        this.capitalRepayment = capitalRepayment;
    }

    public BigDecimal getInterestRepayment() {
        return interestRepayment;
    }

    public void setInterestRepayment(BigDecimal interestRepayment) {
        this.interestRepayment = interestRepayment;
    }

    public FmsLoanDisbursement getDisbursementFk() {
        return disbursementFk;
    }

    public void setDisbursementFk(FmsLoanDisbursement disbursementFk) {
        this.disbursementFk = disbursementFk;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (loanRepaymentId != null ? loanRepaymentId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FmsLoanRepayment)) {
            return false;
        }
        FmsLoanRepayment other = (FmsLoanRepayment) object;
        if ((this.loanRepaymentId == null && other.loanRepaymentId != null) || (this.loanRepaymentId != null && !this.loanRepaymentId.equals(other.loanRepaymentId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.eep.fcms.entity.loan.FmsLoanRepayment[ loanRepaymentId=" + loanRepaymentId + " ]";
    }

}
