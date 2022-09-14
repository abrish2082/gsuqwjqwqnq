/**
 * *****************************************************************************
 *
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates and open the template
 * in the editor.
 *
 *****************************************************************************
 */
package et.gov.eep.prms.entity;

import et.gov.eep.fcms.entity.FmsLuCurrency;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import javax.persistence.Basic;
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
 * *****************************************************************************
 *
 * @author User
 * ****************************************************************************
 */
@Entity
@Table(name = "PRMS_CONTRACTAMEND_CURRENCY")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PrmsContractamendCurrency.findAll", query = "SELECT p FROM PrmsContractamendCurrency p"),
    @NamedQuery(name = "PrmsContractamendCurrency.findById", query = "SELECT p FROM PrmsContractamendCurrency p WHERE p.id = :id"),
    @NamedQuery(name = "PrmsContractamendCurrency.findByAmount", query = "SELECT p FROM PrmsContractamendCurrency p WHERE p.amount = :amount")})

public class PrmsContractamendCurrency implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
//    @Id
//    @Basic(optional = false)
//    @NotNull
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PRMS_CONTRACTAMEND_CURRENCYSEQ")
//    @SequenceGenerator(name = "PRMS_CONTRACTAMEND_CURRENCYSEQ", sequenceName = "PRMS_CONTRACTAMEND_CURRENCYSEQ", allocationSize = 1)
//    @Column(name = "ID")
//    private BigDecimal id;
//    @Column(name = "AMOUNT")
//    private double amount;
//    @JoinColumn(name = "CURRENCY_ID", referencedColumnName = "CURRENCY_ID")
//    @ManyToOne
//    private FmsLuCurrency currencyId;
//    @JoinColumn(name = "CONTRACT_ID", referencedColumnName = "CONTRACT_AMEND_ID")
//    @ManyToOne(fetch = FetchType.LAZY)
//    private PrmsContractAmendment contractId;
    @Id
    @GeneratedValue(generator = "PRMS_CONTRACTAMEND_CURRENCYSEQ", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "PRMS_CONTRACTAMEND_CURRENCYSEQ", sequenceName = "PRMS_CONTRACTAMEND_CURRENCYSEQ", allocationSize = 1)
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private BigDecimal id;
    @Column(name = "AMOUNT")
    private double amount;
    @JoinColumn(name = "CURRENCY_ID", referencedColumnName = "CURRENCY_ID")
    @ManyToOne
    private FmsLuCurrency currencyId;
    @JoinColumn(name = "CONTRACT_ID", referencedColumnName = "CONTRACT_AMEND_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private PrmsContractAmendment contractId;

    public PrmsContractamendCurrency() {
    }

    public PrmsContractamendCurrency(BigDecimal id) {
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

    public PrmsContractAmendment getContractId() {
        return contractId;
    }

    public void setContractId(PrmsContractAmendment contractId) {
        this.contractId = contractId;
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
        if (!(object instanceof PrmsContractamendCurrency)) {
            return false;
        }
        PrmsContractamendCurrency other = (PrmsContractamendCurrency) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.eep.prms.entity.PrmsContract[ contractId=" + id + " ]";
    }
}
