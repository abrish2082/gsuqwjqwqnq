/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.entity;

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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author user
 */
@Entity
@Table(name = "PRMS_SUPPLY_PROFILE_DETAIL")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PrmsSupplyProfileDetail.findAll", query = "SELECT p FROM PrmsSupplyProfileDetail p"),
    @NamedQuery(name = "PrmsSupplyProfileDetail.findBySequenceNo", query = "SELECT p FROM PrmsSupplyProfileDetail p WHERE p.sequenceNo = :sequenceNo"),
        @NamedQuery(name = "PrmsSupplyProfileDetail.FindBySupplyIid", query = "SELECT p FROM PrmsSupplyProfileDetail p WHERE p.suppId.vendorName = :supplyId"),

    @NamedQuery(name = "PrmsSupplyProfileDetail.findByItemName", query = "SELECT p FROM PrmsSupplyProfileDetail p WHERE p.itemName = :itemName"),
    @NamedQuery(name = "PrmsSupplyProfileDetail.findByItemOrServiceName", query = "SELECT p FROM PrmsSupplyProfileDetail p WHERE p.itemOrServiceName = :itemOrServiceName"),
    @NamedQuery(name = "PrmsSupplyProfileDetail.findByDescription", query = "SELECT p FROM PrmsSupplyProfileDetail p WHERE p.description = :description"),
    @NamedQuery(name = "PrmsSupplyProfileDetail.findByQuantity", query = "SELECT p FROM PrmsSupplyProfileDetail p WHERE p.quantity = :quantity"),
    @NamedQuery(name = "PrmsSupplyProfileDetail.findByQuality", query = "SELECT p FROM PrmsSupplyProfileDetail p WHERE p.quality = :quality"),
    @NamedQuery(name = "PrmsSupplyProfileDetail.findByUnitPrice", query = "SELECT p FROM PrmsSupplyProfileDetail p WHERE p.unitPrice = :unitPrice"),
    @NamedQuery(name = "PrmsSupplyProfileDetail.findByUnitMeasure", query = "SELECT p FROM PrmsSupplyProfileDetail p WHERE p.unitMeasure = :unitMeasure"),
    @NamedQuery(name = "PrmsSupplyProfileDetail.findByExperiance", query = "SELECT p FROM PrmsSupplyProfileDetail p WHERE p.experiance = :experiance"),
    @NamedQuery(name = "PrmsSupplyProfileDetail.findByDetailId", query = "SELECT p FROM PrmsSupplyProfileDetail p WHERE p.detailId = :detailId")})
public class PrmsSupplyProfileDetail implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PRMS_SUPPLY_PROFILE_DET_SEQ")
    @SequenceGenerator(name = "PRMS_SUPPLY_PROFILE_DET_SEQ", sequenceName = "PRMS_SUPPLY_PROFILE_DET_SEQ", allocationSize = 1)
    @Column(name = "DETAIL_ID")
    private BigDecimal detailId;
    @Column(name = "SEQUENCE_NO")
    private String sequenceNo;

    @Column(name = "ITEM_NAME")
    private String itemName;
//    @Size(max = 20)
    @Column(name = "ITEM_OR_SERVICE_NAME")
    private String itemOrServiceName;

    @Column(name = "DESCRIPTION")
    private String description;
//    @Size(max = 20)
    @Column(name = "QUANTITY")
    private String quantity;
//    @Size(max = 20)
    @Column(name = "QUALITY")
    private String quality;
//    @Size(max = 20)
    @Column(name = "UNIT_PRICE")
    private String unitPrice;
////    @Size(max = 20)
    @Column(name = "UNIT_MEASURE")
    private String unitMeasure;
//    @Size(max = 20)
    @Column(name = "EXPERIANCE")
    private String experiance;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation

    @JoinColumn(name = "SUPP_ID", referencedColumnName = "ID")
    @ManyToOne(cascade = CascadeType.ALL)
    private PrmsSupplyProfile suppId;

    public PrmsSupplyProfileDetail() {
    }

    public PrmsSupplyProfileDetail(BigDecimal detailId) {
        this.detailId = detailId;
    }

    public PrmsSupplyProfileDetail(BigDecimal detailId, String sequenceNo) {
        this.detailId = detailId;
        this.sequenceNo = sequenceNo;
    }

    public String getSequenceNo() {
        return sequenceNo;
    }

    public void setSequenceNo(String sequenceNo) {
        this.sequenceNo = sequenceNo;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemOrServiceName() {
        return itemOrServiceName;
    }

    public void setItemOrServiceName(String itemOrServiceName) {
        this.itemOrServiceName = itemOrServiceName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    public String getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(String unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getUnitMeasure() {
        return unitMeasure;
    }

    public void setUnitMeasure(String unitMeasure) {
        this.unitMeasure = unitMeasure;
    }

    public String getExperiance() {
        return experiance;
    }

    public void setExperiance(String experiance) {
        this.experiance = experiance;
    }

    public BigDecimal getDetailId() {
        return detailId;
    }

    public void setDetailId(BigDecimal detailId) {
        this.detailId = detailId;
    }

    public PrmsSupplyProfile getSuppId() {
        return suppId;
    }

    public void setSuppId(PrmsSupplyProfile suppId) {
        this.suppId = suppId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (detailId != null ? detailId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PrmsSupplyProfileDetail)) {
            return false;
        }
        PrmsSupplyProfileDetail other = (PrmsSupplyProfileDetail) object;
        if ((this.detailId == null && other.detailId != null) || (this.detailId != null && !this.detailId.equals(other.detailId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ent.PrmsSupplyProfileDetail[ detailId=" + detailId + " ]";
    }

}
