/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.entity.pettyCash;

import java.io.Serializable;
import java.math.BigDecimal;
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
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author semira
 */
@Entity
@Table(name = "FMS_PETTY_CASH_REPLENISH_DTL")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FmsPettyCashReplenishDtl.findAll", query = "SELECT f FROM FmsPettyCashReplenishDtl f"),
    @NamedQuery(name = "FmsPettyCashReplenishDtl.findById", query = "SELECT f FROM FmsPettyCashReplenishDtl f WHERE f.id = :id")})
public class FmsPettyCashReplenishDtl implements Serializable {

    private static final long serialVersionUID = 1L;
    //<editor-fold defaultstate="collapsed" desc="variable declaration">
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FMS_PETTY_CASH_REPLEN_DTL_SEQ")
    @SequenceGenerator(name = "FMS_PETTY_CASH_REPLEN_DTL_SEQ", sequenceName = "FMS_PETTY_CASH_REPLEN_DTL_SEQ", allocationSize = 1)
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private BigDecimal id;
    @JoinColumn(name = "DAILY_CASH_REGISTER_ID", referencedColumnName = "ID")
    @OneToOne
    private FmsDailyCashRegister dailyCashRegisterId;
    @JoinColumn(name = "PETTY_CASH_REPLENISHMENT_ID", referencedColumnName = "ID")
    @ManyToOne
    private FmsPettyCashReplenishment pettyCashReplenishmentId;
//</editor-fold>

    public FmsPettyCashReplenishDtl() {
    }

    //<editor-fold defaultstate="collapsed" desc="getter and setter">

    public FmsPettyCashReplenishDtl(BigDecimal id) {
        this.id = id;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public FmsDailyCashRegister getDailyCashRegisterId() {
        return dailyCashRegisterId;
    }

    public void setDailyCashRegisterId(FmsDailyCashRegister dailyCashRegisterId) {
        this.dailyCashRegisterId = dailyCashRegisterId;
    }

    public FmsPettyCashReplenishment getPettyCashReplenishmentId() {
        return pettyCashReplenishmentId;
    }

    public void setPettyCashReplenishmentId(FmsPettyCashReplenishment pettyCashReplenishmentId) {
        this.pettyCashReplenishmentId = pettyCashReplenishmentId;
    }
//</editor-fold>

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof FmsPettyCashReplenishDtl)) {
            return false;
        }
        FmsPettyCashReplenishDtl other = (FmsPettyCashReplenishDtl) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.eep.fcms.entity.pettyCash.FmsPettyCashReplenishDtl[ id=" + id + " ]";
    }

}
