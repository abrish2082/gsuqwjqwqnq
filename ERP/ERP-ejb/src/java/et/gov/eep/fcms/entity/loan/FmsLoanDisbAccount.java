/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.entity.loan;

import et.gov.eep.fcms.entity.admin.FmsGeneralLedger;
import et.gov.eep.fcms.entity.admin.FmsSubsidiaryLedger;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author PO
 */
@Entity
@Table(name = "FMS_LOAN_DISB_ACCOUNT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FmsLoanDisbAccount.findAll", query = "SELECT f FROM FmsLoanDisbAccount f"),
    @NamedQuery(name = "FmsLoanDisbAccount.findByLoanDisbAccountId", query = "SELECT f FROM FmsLoanDisbAccount f WHERE f.loanDisbAccountId = :loanDisbAccountId"),
    @NamedQuery(name = "FmsLoanDisbAccount.findByAmount", query = "SELECT f FROM FmsLoanDisbAccount f WHERE f.amount = :amount"),
    @NamedQuery(name = "FmsLoanDisbAccount.findByRemark", query = "SELECT f FROM FmsLoanDisbAccount f WHERE f.remark = :remark")})
public class FmsLoanDisbAccount implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "LOAN_DISB_ACCOUNT_ID")
    private BigDecimal loanDisbAccountId;
    @Column(name = "AMOUNT")
    private BigInteger amount;
    @Size(max = 50)
    @Column(name = "REMARK")
    private String remark;
    @JoinColumn(name = "GENERAL_LEDGER_FK", referencedColumnName = "GENERAL_LEDGER_ID")
    @ManyToOne
    private FmsGeneralLedger generalLedgerFk;
    @JoinColumn(name = "LOAN_DISB_FK", referencedColumnName = "DISBURSEMENT_ID")
    @ManyToOne
    private FmsLoanDisbursement loanDisbFk;
    @JoinColumn(name = "SUBSIDIARY_LEDGER_FK", referencedColumnName = "SUBSIDIARY_ID")
    @ManyToOne
    private FmsSubsidiaryLedger subsidiaryLedgerFk;

    public FmsLoanDisbAccount() {
    }

    public FmsLoanDisbAccount(BigDecimal loanDisbAccountId) {
        this.loanDisbAccountId = loanDisbAccountId;
    }

    public BigDecimal getLoanDisbAccountId() {
        return loanDisbAccountId;
    }

    public void setLoanDisbAccountId(BigDecimal loanDisbAccountId) {
        this.loanDisbAccountId = loanDisbAccountId;
    }

    public BigInteger getAmount() {
        return amount;
    }

    public void setAmount(BigInteger amount) {
        this.amount = amount;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public FmsGeneralLedger getGeneralLedgerFk() {
        return generalLedgerFk;
    }

    public void setGeneralLedgerFk(FmsGeneralLedger generalLedgerFk) {
        this.generalLedgerFk = generalLedgerFk;
    }

    public FmsLoanDisbursement getLoanDisbFk() {
        return loanDisbFk;
    }

    public void setLoanDisbFk(FmsLoanDisbursement loanDisbFk) {
        this.loanDisbFk = loanDisbFk;
    }

    public FmsSubsidiaryLedger getSubsidiaryLedgerFk() {
        return subsidiaryLedgerFk;
    }

    public void setSubsidiaryLedgerFk(FmsSubsidiaryLedger subsidiaryLedgerFk) {
        this.subsidiaryLedgerFk = subsidiaryLedgerFk;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (loanDisbAccountId != null ? loanDisbAccountId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FmsLoanDisbAccount)) {
            return false;
        }
        FmsLoanDisbAccount other = (FmsLoanDisbAccount) object;
        if ((this.loanDisbAccountId == null && other.loanDisbAccountId != null) || (this.loanDisbAccountId != null && !this.loanDisbAccountId.equals(other.loanDisbAccountId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.eep.fcms.entity.loan.FmsLoanDisbAccount[ loanDisbAccountId=" + loanDisbAccountId + " ]";
    }
    
}
