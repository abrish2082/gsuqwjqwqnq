/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author user
 */
@Entity
@Table(name = "PRMS_PURCHASE_TYPE_LOOKUP")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PrmsPurchaseTypeLookup.findAll", query = "SELECT p FROM PrmsPurchaseTypeLookup p"),
    @NamedQuery(name = "PrmsPurchaseTypeLookup.findByPurchaseTypeId", query = "SELECT p FROM PrmsPurchaseTypeLookup p WHERE p.purchaseTypeId = :purchaseTypeId"),
    @NamedQuery(name = "PrmsPurchaseTypeLookup.findByPurchaseType", query = "SELECT p FROM PrmsPurchaseTypeLookup p WHERE p.purchaseType = :purchaseType"),
    @NamedQuery(name = "PrmsPurchaseTypeLookup.findByTotalPriceAllowed", query = "SELECT p FROM PrmsPurchaseTypeLookup p WHERE p.totalPriceAllowed = :totalPriceAllowed")})
public class PrmsPurchaseTypeLookup implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "PURCHASE_TYPE_ID")
    private BigDecimal purchaseTypeId;
    @Size(max = 100)
    @Column(name = "PURCHASE_TYPE")
    private String purchaseType;
    @Column(name = "TOTAL_PRICE_ALLOWED")
    private BigInteger totalPriceAllowed;
    @OneToMany(mappedBy = "purchaseTypeId")
    private List<PrmsQuotationDetail> prmsQuotationDetailList;

    public PrmsPurchaseTypeLookup() {
    }

    public PrmsPurchaseTypeLookup(BigDecimal purchaseTypeId) {
        this.purchaseTypeId = purchaseTypeId;
    }

    public BigDecimal getPurchaseTypeId() {
        return purchaseTypeId;
    }

    public void setPurchaseTypeId(BigDecimal purchaseTypeId) {
        this.purchaseTypeId = purchaseTypeId;
    }

    public String getPurchaseType() {
        return purchaseType;
    }

    public void setPurchaseType(String purchaseType) {
        this.purchaseType = purchaseType;
    }

    public BigInteger getTotalPriceAllowed() {
        return totalPriceAllowed;
    }

    public void setTotalPriceAllowed(BigInteger totalPriceAllowed) {
        this.totalPriceAllowed = totalPriceAllowed;
    }

    @XmlTransient
    public List<PrmsQuotationDetail> getPrmsQuotationDetailList() {
        return prmsQuotationDetailList;
    }

    public void setPrmsQuotationDetailList(List<PrmsQuotationDetail> prmsQuotationDetailList) {
        this.prmsQuotationDetailList = prmsQuotationDetailList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (purchaseTypeId != null ? purchaseTypeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PrmsPurchaseTypeLookup)) {
            return false;
        }
        PrmsPurchaseTypeLookup other = (PrmsPurchaseTypeLookup) object;
        if ((this.purchaseTypeId == null && other.purchaseTypeId != null) || (this.purchaseTypeId != null && !this.purchaseTypeId.equals(other.purchaseTypeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.eep.prms.entity.PrmsPurchaseTypeLookup[ purchaseTypeId=" + purchaseTypeId + " ]";
    }
    
}
