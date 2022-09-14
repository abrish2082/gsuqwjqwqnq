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
@Table(name = "FMS_LOAN_COMM_ACCOUNTS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FmsLoanCommAccounts.findAll", query = "SELECT f FROM FmsLoanCommAccounts f"),
    @NamedQuery(name = "FmsLoanCommAccounts.findByLoanCommAccountId", query = "SELECT f FROM FmsLoanCommAccounts f WHERE f.loanCommAccountId = :loanCommAccountId"),
    @NamedQuery(name = "FmsLoanCommAccounts.findByAmount", query = "SELECT f FROM FmsLoanCommAccounts f WHERE f.amount = :amount"),
    @NamedQuery(name = "FmsLoanCommAccounts.findByRemark", query = "SELECT f FROM FmsLoanCommAccounts f WHERE f.remark = :remark")})
public class FmsLoanCommAccounts implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "LOAN_COMM_ACCOUNT_ID")
    private BigDecimal loanCommAccountId;
    @Column(name = "AMOUNT")
    private BigInteger amount;
    @Size(max = 50)
    @Column(name = "REMARK")
    private String remark;
    @JoinColumn(name = "GENERAL_LEDGER_FK", referencedColumnName = "GENERAL_LEDGER_ID")
    @ManyToOne
    private FmsGeneralLedger generalLedgerFk;
    @JoinColumn(name = "LOAN_COMM_FK", referencedColumnName = "COMMITMENT_ID")
    @ManyToOne
    private FmsLoanCommitment loanCommFk;
    @JoinColumn(name = "SUBSIDIARY_LEDGER_FK", referencedColumnName = "SUBSIDIARY_ID")
    @ManyToOne
    private FmsSubsidiaryLedger subsidiaryLedgerFk;

    public FmsLoanCommAccounts() {
    }

    public FmsLoanCommAccounts(BigDecimal loanCommAccountId) {
        this.loanCommAccountId = loanCommAccountId;
    }

    public BigDecimal getLoanCommAccountId() {
        return loanCommAccountId;
    }

    public void setLoanCommAccountId(BigDecimal loanCommAccountId) {
        this.loanCommAccountId = loanCommAccountId;
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

    public FmsLoanCommitment getLoanCommFk() {
        return loanCommFk;
    }

    public void setLoanCommFk(FmsLoanCommitment loanCommFk) {
        this.loanCommFk = loanCommFk;
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
        hash += (loanCommAccountId != null ? loanCommAccountId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FmsLoanCommAccounts)) {
            return false;
        }
        FmsLoanCommAccounts other = (FmsLoanCommAccounts) object;
        if ((this.loanCommAccountId == null && other.loanCommAccountId != null) || (this.loanCommAccountId != null && !this.loanCommAccountId.equals(other.loanCommAccountId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.eep.fcms.entity.loan.FmsLoanCommAccounts[ loanCommAccountId=" + loanCommAccountId + " ]";
    }
    
}
