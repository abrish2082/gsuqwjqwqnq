/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author user
 */
@Entity
@Table(name = "FMS_TRIAL_BALANCE_DETAIL")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FmsTrialBalanceDetail.findAll", query = "SELECT f FROM FmsTrialBalanceDetail f"),
    @NamedQuery(name = "FmsTrialBalanceDetail.findByTrBalanceCode", query = "SELECT f FROM FmsTrialBalanceDetail f WHERE f.trBalanceCode = :trBalanceCode"),
    @NamedQuery(name = "FmsTrialBalanceDetail.findBySubsidiaryId", query = "SELECT f FROM FmsTrialBalanceDetail f WHERE f.subsidiaryId = :subsidiaryId"),
    @NamedQuery(name = "FmsTrialBalanceDetail.findByDebit", query = "SELECT f FROM FmsTrialBalanceDetail f WHERE f.debit = :debit"),
    @NamedQuery(name = "FmsTrialBalanceDetail.findByCredit", query = "SELECT f FROM FmsTrialBalanceDetail f WHERE f.credit = :credit"),
    @NamedQuery(name = "FmsTrialBalanceDetail.findById", query = "SELECT f FROM FmsTrialBalanceDetail f WHERE f.id = :id")})
public class FmsTrialBalanceDetail implements Serializable {
    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "TR_BALANCE_CODE")
    private String trBalanceCode;
    @Column(name = "SUBSIDIARY_ID")
    private Long subsidiaryId;
    @Column(name = "DEBIT")
    private BigInteger debit;
    @Column(name = "CREDIT")
    private BigInteger credit;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private BigDecimal id;

    public FmsTrialBalanceDetail() {
    }

    public FmsTrialBalanceDetail(BigDecimal id) {
        this.id = id;
    }

    public FmsTrialBalanceDetail(BigDecimal id, String trBalanceCode) {
        this.id = id;
        this.trBalanceCode = trBalanceCode;
    }

    public String getTrBalanceCode() {
        return trBalanceCode;
    }

    public void setTrBalanceCode(String trBalanceCode) {
        this.trBalanceCode = trBalanceCode;
    }

    public Long getSubsidiaryId() {
        return subsidiaryId;
    }

    public void setSubsidiaryId(Long subsidiaryId) {
        this.subsidiaryId = subsidiaryId;
    }

    public BigInteger getDebit() {
        return debit;
    }

    public void setDebit(BigInteger debit) {
        this.debit = debit;
    }

    public BigInteger getCredit() {
        return credit;
    }

    public void setCredit(BigInteger credit) {
        this.credit = credit;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FmsTrialBalanceDetail)) {
            return false;
        }
        FmsTrialBalanceDetail other = (FmsTrialBalanceDetail) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.eep.fcms.entity.FmsTrialBalanceDetail[ id=" + id + " ]";
    }
    
}
