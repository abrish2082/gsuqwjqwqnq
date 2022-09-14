
package et.gov.eep.prms.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name = "PRMS_LU_VAT_TYPE_LOOKUP")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PrmsLuVatTypeLookup.findAll", query = "SELECT p FROM PrmsLuVatTypeLookup p"),
    @NamedQuery(name = "PrmsLuVatTypeLookup.findByVatTypeId", query = "SELECT p FROM PrmsLuVatTypeLookup p WHERE p.vatTypeId = :vatTypeId"),
    @NamedQuery(name = "PrmsLuVatTypeLookup.findByVatType", query = "SELECT p FROM PrmsLuVatTypeLookup p WHERE p.vatType = :vatType"),
    @NamedQuery(name = "PrmsLuVatTypeLookup.findByVatRate", query = "SELECT p FROM PrmsLuVatTypeLookup p WHERE p.vatRate = :vatRate"),
    @NamedQuery(name = "PrmsLuVatTypeLookup.findByDescribition", query = "SELECT p FROM PrmsLuVatTypeLookup p WHERE p.describition = :describition")})
public class PrmsLuVatTypeLookup implements Serializable {

  
    @Column(name = "VAT_RATE", precision = 126)
    private Double vatRate;
    @OneToMany(mappedBy = "vatTypeId")
    private List<PrmsSupplyProfile> prmsSupplyProfileList;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PRMS_VAT_TYPE_LOOKUP_SEQ")
    @SequenceGenerator(name = "PRMS_VAT_TYPE_LOOKUP_SEQ", sequenceName = "PRMS_VAT_TYPE_LOOKUP_SEQ", allocationSize = 1)
    @Column(name = "VAT_TYPE_ID", nullable = false, precision = 0, scale = -127)
    private BigDecimal vatTypeId;
    @Size(max = 100)
    @Column(name = "VAT_TYPE", length = 100)
    private String vatType;
    @Size(max = 100)
    @Column(name = "DESCRIBITION", length = 100)
    private String describition;

    public PrmsLuVatTypeLookup() {
    }

    public PrmsLuVatTypeLookup(BigDecimal vatTypeId) {
        this.vatTypeId = vatTypeId;
    }

    public BigDecimal getVatTypeId() {
        return vatTypeId;
    }

    public void setVatTypeId(BigDecimal vatTypeId) {
        this.vatTypeId = vatTypeId;
    }

    public String getVatType() {
        return vatType;
    }

    public void setVatType(String vatType) {
        this.vatType = vatType;
    }

//    public double getVatRate() {
//        return vatRate;
//    }
//
//    public void setVatRate(double vatRate) {
//        this.vatRate = vatRate;
//    }
    public String getDescribition() {
        return describition;
    }

    public void setDescribition(String describition) {
        this.describition = describition;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (vatTypeId != null ? vatTypeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PrmsLuVatTypeLookup)) {
            return false;
        }
        PrmsLuVatTypeLookup other = (PrmsLuVatTypeLookup) object;
        if ((this.vatTypeId == null && other.vatTypeId != null) || (this.vatTypeId != null && !this.vatTypeId.equals(other.vatTypeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.eep.prms.entity.PrmsVatTypeLookup[ vatTypeId=" + vatTypeId + " ]";
    }

    @XmlTransient
    public List<PrmsSupplyProfile> getPrmsSupplyProfileList() {
        if (prmsSupplyProfileList == null) {
            prmsSupplyProfileList = new ArrayList<>();
        }
        return prmsSupplyProfileList;
    }

    public void setPrmsSupplyProfileList(List<PrmsSupplyProfile> prmsSupplyProfileList) {
        this.prmsSupplyProfileList = prmsSupplyProfileList;
    }

    public Double getVatRate() {
        return vatRate;
    }

    public void setVatRate(Double vatRate) {
        this.vatRate = vatRate;
    }
}
