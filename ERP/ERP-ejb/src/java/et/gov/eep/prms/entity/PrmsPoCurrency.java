/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.entity;

import et.gov.eep.fcms.entity.FmsLuCurrency;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
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
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author mora1
 */
@Entity
@Table(name = "PRMS_PO_CURRENCY", catalog = "", schema = "ERP")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PrmsPoCurrency.findAll", query = "SELECT p FROM PrmsPoCurrency p"),
    @NamedQuery(name = "PrmsPoCurrency.findById", query = "SELECT p FROM PrmsPoCurrency p WHERE p.id = :id"),
    @NamedQuery(name = "PrmsPoCurrency.findByAmount", query = "SELECT p FROM PrmsPoCurrency p WHERE p.amount = :amount")})
public class PrmsPoCurrency implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Id
    @GeneratedValue(generator = "PRMS_PO_CURRENCYSEQ", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "PRMS_PO_CURRENCYSEQ", sequenceName = "PRMS_PO_CURRENCYSEQ", allocationSize = 1)
    @Column(name = "ID", nullable = false, precision = 0, scale = -127)
    private BigDecimal id;
    @Column(name = "AMOUNT")
    private double amount;
    @JoinColumn(name = "CURRENCY_ID", referencedColumnName = "CURRENCY_ID")
    @ManyToOne
    private FmsLuCurrency currencyId;
    @JoinColumn(name = "PO_ID", referencedColumnName = "PO_ID")
    @ManyToOne
    private PrmsPurchaseOrder poId;

    public PrmsPoCurrency() {
    }

    public PrmsPoCurrency(BigDecimal id) {
        this.id = id;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public FmsLuCurrency getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(FmsLuCurrency currencyId) {
        this.currencyId = currencyId;
    }

    public PrmsPurchaseOrder getPoId() {
        return poId;
    }

    public void setPoId(PrmsPurchaseOrder poId) {
        this.poId = poId;
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
        if (!(object instanceof PrmsPoCurrency)) {
            return false;
        }
        PrmsPoCurrency other = (PrmsPoCurrency) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.eep.prms.entity.PrmsPoCurrency[ id=" + id + " ]";
    }

}
