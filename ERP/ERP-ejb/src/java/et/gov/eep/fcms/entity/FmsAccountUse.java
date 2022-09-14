/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.entity;

import et.gov.eep.fcms.entity.Vocher.FmsVoucher;
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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author mora
 */
@Entity
@Table(name = "FMS_ACCOUNT_USE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FmsAccountUse.findAll", query = "SELECT f FROM FmsAccountUse f"),
    @NamedQuery(name = "FmsAccountUse.findByAccountId", query = "SELECT f FROM FmsAccountUse f WHERE f.fmsAccountUseId = :fmsAccountUseId"),
    @NamedQuery(name = "FmsAccountUse.findByCredit", query = "SELECT f FROM FmsAccountUse f WHERE f.credit = :credit"),
    @NamedQuery(name = "FmsAccountUse.findByDebit", query = "SELECT f FROM FmsAccountUse f WHERE f.debit = :debit"),
//            @NamedQuery(name = "FmsAccountUse.findByleger", query = "SELECT f FROM FmsAccountUse f WHERE f.subsidaryId = :subsidaryId and SUBSTR(f.VOUCHEID,1,4) =:subsidaryId"),
    @NamedQuery(name = "FmsAccountUse.findBySubsidaryId", query = "SELECT f FROM FmsAccountUse f WHERE f.subsidaryId = :subsidaryId"),
    @NamedQuery(name = "FmsAccountUse.findByVOUCHEIDAndAccId", query = "SELECT f FROM FmsAccountUse f WHERE f.VOUCHEID = :VOUCHEID AND f.fmsAccountUseId = :fmsAccountUseId"),
    @NamedQuery(name = "FmsAccountUse.findByStatus", query = "SELECT f FROM FmsAccountUse f WHERE f.status = :status")})
public class FmsAccountUse implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FMS_ACCOUNT_USE_SEQ")
    @SequenceGenerator(name = "FMS_ACCOUNT_USE_SEQ", sequenceName = "FMS_ACCOUNT_USE_SEQ", allocationSize = 1)
    @Basic(optional = false)
    @Column(name = "ACCOUNT_ID", nullable = false)
    private Integer fmsAccountUseId;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "CREDIT")
    private BigDecimal credit;
    @Column(name = "DEBIT")
    private BigDecimal debit;
    @Size(max = 50)
    @Column(name = "SUBSIDARY_ID")
    private String subsidaryId;
    @Column(name = "STATUS")
    private Integer status;
    @JoinColumn(name = "VOUCHER_ID", referencedColumnName = "VOUCHER_ID", nullable = false)
    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    private FmsVoucher VOUCHEID;

    public FmsAccountUse() {
    }
    @Transient
    double amt;

    public double getAmt() {
        return amt;
    }

    public void setAmt(double amt) {
        this.amt = amt;
    }

    public Integer getFmsAccountUseId() {
        return fmsAccountUseId;
    }

    public void setFmsAccountUseId(Integer fmsAccountUseId) {
        this.fmsAccountUseId = fmsAccountUseId;
    }

    public BigDecimal getCredit() {
        return credit;
    }

    public void setCredit(BigDecimal credit) {
        this.credit = credit;
    }

    public BigDecimal getDebit() {
        return debit;
    }

    public void setDebit(BigDecimal debit) {
        this.debit = debit;
    }

    public String getSubsidaryId() {
        return subsidaryId;
    }

    public void setSubsidaryId(String subsidaryId) {
        this.subsidaryId = subsidaryId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public FmsVoucher getVOUCHEID() {
        return VOUCHEID;
    }

    public void setVOUCHEID(FmsVoucher VOUCHEID) {
        this.VOUCHEID = VOUCHEID;
    }

    public String getDisplay_conn() {
        return display_conn;
    }

    public void setDisplay_conn(String display_conn) {
        this.display_conn = display_conn;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (fmsAccountUseId != null ? fmsAccountUseId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FmsAccountUse)) {
            return false;
        }
        FmsAccountUse other = (FmsAccountUse) object;
        if ((this.fmsAccountUseId == null && other.fmsAccountUseId != null) || (this.fmsAccountUseId != null && !this.fmsAccountUseId.equals(other.fmsAccountUseId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return VOUCHEID + "_" + fmsAccountUseId;
    }
    @Transient
    String display_conn;
    @Transient
    String date;
}
