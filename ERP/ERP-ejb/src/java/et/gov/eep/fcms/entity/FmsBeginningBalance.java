/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.entity;

import et.gov.eep.fcms.entity.admin.FmsAccountingPeriod;
import et.gov.eep.fcms.entity.admin.FmsSubsidiaryLedger;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author user
 */
@Entity
@Table(name = "FMS_BEGINNING_BALANCE", catalog = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FmsBeginningBalance.findAll", query = "SELECT f FROM FmsBeginningBalance f"),
    @NamedQuery(name = "FmsBeginningBalance.findByBeginningBalanceId", query = "SELECT f FROM FmsBeginningBalance f WHERE f.beginningBalanceId = :beginningBalanceId"),
    @NamedQuery(name = "FmsBeginningBalance.findByAmount", query = "SELECT f FROM FmsBeginningBalance f WHERE f.amount = :amount"),
//    @NamedQuery(name = "FmsBeginningBalance.findByGlCode", query = "SELECT f FROM FmsBeginningBalance f WHERE f.glCode = :glCode"),
    @NamedQuery(name = "FmsBeginningBalance.findByGlCode_Period", query = "SELECT f FROM FmsBeginningBalance f WHERE f.slCode.generalLedgerId = :generalLedgerId and f.accountigPeriodId = :accountigPeriodId"),
    @NamedQuery(name = "FmsBeginningBalance.findByDebit", query = "SELECT f FROM FmsBeginningBalance f WHERE f.debit = :debit"),
    @NamedQuery(name = "FmsBeginningBalance.findBySLCode", query = "SELECT f FROM FmsBeginningBalance f WHERE f.slCode = :slCode"),
    @NamedQuery(name = "FmsBeginningBalance.findByCredit", query = "SELECT f FROM FmsBeginningBalance f WHERE f.credit = :credit"),
    @NamedQuery(name = "FmsBeginningBalance.findByAcountigPeriodId", query = "SELECT f FROM FmsBeginningBalance f WHERE f.accountigPeriodId = :accountigPeriodId")})
public class FmsBeginningBalance implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "BEGINNING_BALANCE_ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FMS_BEGINNING_BALANCE_SEQ")
    @SequenceGenerator(name = "FMS_BEGINNING_BALANCE_SEQ", sequenceName = "FMS_BEGINNING_BALANCE_SEQ", allocationSize = 1)
    private Long beginningBalanceId;
    @Column(name = "DEBIT")
    private BigDecimal debit;
    @Column(name = "CREDIT")
    private BigDecimal credit;
    @Column(name = "AMOUNT")
    private BigDecimal amount;
    @JoinColumn(name = "SL_CODE", referencedColumnName = "SUBSIDIARY_ID")
    @ManyToOne
    private FmsSubsidiaryLedger slCode;
    @JoinColumn(name = "ACCOUNTIG_PERIOD_ID", referencedColumnName = "ACOUNTIG_PERIOD_ID")
    @ManyToOne
    private FmsAccountingPeriod accountigPeriodId;

    public FmsBeginningBalance() {
    }

    public FmsBeginningBalance(Long beginningBalanceId) {
        this.beginningBalanceId = beginningBalanceId;
    }

//    public FmsBeginningBalance(Long beginningBalanceId, String glCode) {
//        this.beginningBalanceId = beginningBalanceId;
//        this.glCode = glCode;
//    }
    public Long getBeginningBalanceId() {
        return beginningBalanceId;
    }

    public void setBeginningBalanceId(Long beginningBalanceId) {
        this.beginningBalanceId = beginningBalanceId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
//
//    public String getGlCode() {
//        return glCode;
//    }
//
//    public void setGlCode(String glCode) {
//        this.glCode = glCode;
//    }

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

//            public FmsAccountingPeriod getAcountigPeriodId() {
//                        return acountigPeriodId;
//            }
//
//            public void setAcountigPeriodId(FmsAccountingPeriod acountigPeriodId) {
//                        this.acountigPeriodId = acountigPeriodId;
//            }
    public FmsAccountingPeriod getAccountigPeriodId() {
        return accountigPeriodId;
    }

    public void setAccountigPeriodId(FmsAccountingPeriod accountigPeriodId) {
        this.accountigPeriodId = accountigPeriodId;
    }

    public FmsSubsidiaryLedger getSlCode() {
        return slCode;
    }

    public void setSlCode(FmsSubsidiaryLedger slCode) {
        this.slCode = slCode;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (beginningBalanceId != null ? beginningBalanceId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FmsBeginningBalance)) {
            return false;
        }
        FmsBeginningBalance other = (FmsBeginningBalance) object;
        if ((this.beginningBalanceId == null && other.beginningBalanceId != null) || (this.beginningBalanceId != null && !this.beginningBalanceId.equals(other.beginningBalanceId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.eep.fcms.entity.FmsBeginningBalance[ beginningBalanceId=" + beginningBalanceId + " ]";
    }

}
