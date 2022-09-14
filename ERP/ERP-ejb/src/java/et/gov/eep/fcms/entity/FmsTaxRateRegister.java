/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.entity;

import java.io.Serializable;
import java.math.BigInteger;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author kaleab
 */
@Entity
@Table(name = "FMS_TAX_RATE_REGISTER")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FmsTaxRateRegister.findAll", query = "SELECT f FROM FmsTaxRateRegister f"),
    @NamedQuery(name = "FmsTaxRateRegister.findByTaxType", query = "SELECT f FROM FmsTaxRateRegister f WHERE f.taxType = :taxType"),
    @NamedQuery(name = "FmsTaxRateRegister.findByTaxAmount", query = "SELECT f FROM FmsTaxRateRegister f WHERE f.taxAmount = :taxAmount"),
    @NamedQuery(name = "FmsTaxRateRegister.findByTaxId", query = "SELECT f FROM FmsTaxRateRegister f WHERE f.taxId = :taxId")})
public class FmsTaxRateRegister implements Serializable {

    private static final long serialVersionUID = 1L;
    @Size(max = 20)

    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FMS_TAX_RATE_REGISTER_SEQ")
    @SequenceGenerator(name = "FMS_TAX_RATE_REGISTER_SEQ", sequenceName = "FMS_TAX_RATE_REGISTER_SEQ", allocationSize = 1)
    @Column(name = "TAX_TYPE")
    private String taxType;
    @Column(name = "TAX_AMOUNT")
    private BigInteger taxAmount;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "TAX_ID")
    private String taxId;

    public FmsTaxRateRegister() {
    }

    public FmsTaxRateRegister(String taxId) {
        this.taxId = taxId;
    }

    public String getTaxType() {
        return taxType;
    }

    public void setTaxType(String taxType) {
        this.taxType = taxType;
    }

    public BigInteger getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(BigInteger taxAmount) {
        this.taxAmount = taxAmount;
    }

    public String getTaxId() {
        return taxId;
    }

    public void setTaxId(String taxId) {
        this.taxId = taxId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (taxId != null ? taxId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FmsTaxRateRegister)) {
            return false;
        }
        FmsTaxRateRegister other = (FmsTaxRateRegister) object;
        if ((this.taxId == null && other.taxId != null) || (this.taxId != null && !this.taxId.equals(other.taxId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.eep.fcms.entity.FmsTaxRateRegister[ taxId=" + taxId + " ]";
    }

}
