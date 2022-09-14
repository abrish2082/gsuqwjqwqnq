/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.entity;

import java.io.Serializable;
import java.math.BigDecimal;
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
@Table(name = "PRMS_MARKET_ASSESSMENT_LOAD")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PrmsMarketAssessmentLoad.findAll", query = "SELECT p FROM PrmsMarketAssessmentLoad p"),
    @NamedQuery(name = "PrmsMarketAssessmentLoad.findById", query = "SELECT p FROM PrmsMarketAssessmentLoad p WHERE p.id = :id"),
    @NamedQuery(name = "PrmsMarketAssessmentLoad.findByItemName", query = "SELECT p FROM PrmsMarketAssessmentLoad p WHERE p.itemName = :itemName"),
    @NamedQuery(name = "PrmsMarketAssessmentLoad.findByUnitOfMeasure", query = "SELECT p FROM PrmsMarketAssessmentLoad p WHERE p.unitOfMeasure = :unitOfMeasure"),
    @NamedQuery(name = "PrmsMarketAssessmentLoad.findByPrice", query = "SELECT p FROM PrmsMarketAssessmentLoad p WHERE p.price = :price"),
    @NamedQuery(name = "PrmsMarketAssessmentLoad.findByCurrency", query = "SELECT p FROM PrmsMarketAssessmentLoad p WHERE p.currency = :currency"),
    @NamedQuery(name = "PrmsMarketAssessmentLoad.findByCountry", query = "SELECT p FROM PrmsMarketAssessmentLoad p WHERE p.country = :country"),
    @NamedQuery(name = "PrmsMarketAssessmentLoad.findByAvailability", query = "SELECT p FROM PrmsMarketAssessmentLoad p WHERE p.availability = :availability"),
    @NamedQuery(name = "PrmsMarketAssessmentLoad.findBySourceOfAssessment", query = "SELECT p FROM PrmsMarketAssessmentLoad p WHERE p.sourceOfAssessment = :sourceOfAssessment")})
public class PrmsMarketAssessmentLoad implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID", nullable = false, precision = 0, scale = -127)
    private BigDecimal id;
    @Size(max = 20)
    @Column(name = "ITEM_NAME", length = 20)
    private String itemName;
    @Size(max = 20)
    @Column(name = "UNIT_OF_MEASURE", length = 20)
    private String unitOfMeasure;
    @Size(max = 20)
    @Column(name = "PRICE", length = 20)
    private String price;
    @Size(max = 20)
    @Column(name = "CURRENCY", length = 20)
    private String currency;
    @Size(max = 20)
    @Column(name = "COUNTRY", length = 20)
    private String country;
    @Size(max = 20)
    @Column(name = "AVAILABILITY", length = 20)
    private String availability;
    @Size(max = 20)
    @Column(name = "SOURCE_OF_ASSESSMENT", length = 20)
    private String sourceOfAssessment;

    public PrmsMarketAssessmentLoad() {
    }

    public PrmsMarketAssessmentLoad(BigDecimal id) {
        this.id = id;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getUnitOfMeasure() {
        return unitOfMeasure;
    }

    public void setUnitOfMeasure(String unitOfMeasure) {
        this.unitOfMeasure = unitOfMeasure;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public String getSourceOfAssessment() {
        return sourceOfAssessment;
    }

    public void setSourceOfAssessment(String sourceOfAssessment) {
        this.sourceOfAssessment = sourceOfAssessment;
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
        if (!(object instanceof PrmsMarketAssessmentLoad)) {
            return false;
        }
        PrmsMarketAssessmentLoad other = (PrmsMarketAssessmentLoad) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.eep.prms.entity.PrmsMarketAssessmentLoad[ id=" + id + " ]";
    }
    
}
