/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.entity;

import et.gov.eep.fcms.entity.FmsLuCurrency;
import java.io.Serializable;
import java.math.BigDecimal;
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

/*******************************************************************************
 *
 * @author User
 ******************************************************************************/
@Entity
@Table(name = "PRMS_CONTRACT_CURRENCY_DETAIL")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PrmsContractCurrencyDetail.findAll", query = "SELECT p FROM PrmsContractCurrencyDetail p"),
    @NamedQuery(name = "PrmsContractCurrencyDetail.findById", query = "SELECT p FROM PrmsContractCurrencyDetail p WHERE p.id = :id"),
//@NamedQuery(name = "PrmsContractCurrencyDetail.findByCurrency", query = "SELECT p FROM PrmsContractCurrencyDetail p WHERE p.currencyId :currencyId"),
//@NamedQuery(name = "PrmsContractCurrencyDetail.findByContract", query = "SELECT p FROM PrmsContractCurrencyDetail p WHERE p.contractId :contractId"),
    @NamedQuery(name = "PrmsContractCurrencyDetail.findByAmount", query = "SELECT p FROM PrmsContractCurrencyDetail p WHERE p.amount = :amount")})
public class PrmsContractCurrencyDetail implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(generator = "PRMS_CONTRACT_CURRENCYSEQ", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "PRMS_CONTRACT_CURRENCYSEQ", sequenceName = "PRMS_CONTRACT_CURRENCYSEQ", allocationSize = 1)
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private BigDecimal id;
    @Column(name = "AMOUNT")
    private double amount;
    @JoinColumn(name = "CURRENCY_ID", referencedColumnName = "CURRENCY_ID")
    @ManyToOne
    private FmsLuCurrency currencyId;
    @JoinColumn(name = "CONTRACT_ID", referencedColumnName = "CONTRACT_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private PrmsContract contractId;

    public PrmsContractCurrencyDetail() {
    }

    public PrmsContractCurrencyDetail(BigDecimal id) {
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

    public PrmsContract getContractId() {
        return contractId;
    }

    public void setContractId(PrmsContract contractId) {
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
        if (!(object instanceof PrmsContractCurrencyDetail)) {
            return false;
        }
        PrmsContractCurrencyDetail other = (PrmsContractCurrencyDetail) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.eep.prms.entity.PrmsContractCurrencyDetail[ id=" + id + " ]";
    }

    /**
     * @return the currencyId
     */
    public FmsLuCurrency getCurrencyId() {
        return currencyId;
    }

    /**
     * @param currencyId the currencyId to set
     */
    public void setCurrencyId(FmsLuCurrency currencyId) {
        this.currencyId = currencyId;
    }
}