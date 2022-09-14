/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.mms.entity;

import java.io.Serializable;
import java.math.BigDecimal;
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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Sadik
 */
@Entity
@Table(name = "MMS_SIV_DETAIL")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MmsSivDetail.findAll", query = "SELECT m FROM MmsSivDetail m"),
    @NamedQuery(name = "MmsSivDetail.findBySivDetId", query = "SELECT m FROM MmsSivDetail m WHERE m.sivDetId = :sivDetId"),
    @NamedQuery(name = "MmsSivDetail.findbysivIdforItem", query = "SELECT m FROM MmsSivDetail m WHERE m.sivId.sivId =:sivId"),
    @NamedQuery(name = "MmsSivDetail.findByQuantity", query = "SELECT m FROM MmsSivDetail m WHERE m.quantity = :quantity"),
    @NamedQuery(name = "MmsSivDetail.findbysivIds", query = "SELECT m FROM MmsSivDetail m WHERE m.sivId.sivId = :sivId"),

    @NamedQuery(name = "MmsSivDetail.findByUnitPrice", query = "SELECT m FROM MmsSivDetail m WHERE m.unitPrice = :unitPrice"),
    @NamedQuery(name = "MmsSivDetail.findByItemId", query = "SELECT m FROM MmsSivDetail m WHERE m.itemId.materialId=:itemId"),

    //For Sadik
    @NamedQuery(name = "MmsSivDetail.findBySivNo", query = "SELECT m FROM MmsSivDetail m WHERE m.sivId.sivNo = :sivNo"),
    @NamedQuery(name = "MmsSivDetail.findBySivDtlIdMaximum", query = "SELECT m FROM MmsSivDetail m WHERE m.sivDetId = (SELECT Max(c.sivDetId)  from MmsSivDetail c)")})
public class MmsSivDetail implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MMS_SIV_DET_SEQ")
    @SequenceGenerator(name = "MMS_SIV_DET_SEQ", sequenceName = "MMS_SIV_DET_SEQ", allocationSize = 1)
    @Column(name = "SIV_DET_ID")
    private Integer sivDetId;

    @Column(name = "QUANTITY")
    private BigDecimal quantity;

    @Column(name = "UNIT_PRICE")
    private BigDecimal unitPrice;
    @Column(name = "TOTAL_PRICE")
    private BigDecimal totalPrice;

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }
    
    

    @JoinColumn(name = "SIV_ID", referencedColumnName = "SIV_ID")
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)

    private MmsSiv sivId;

    @JoinColumn(name = "ITEM_ID", referencedColumnName = "MATERIAL_ID")
    @ManyToOne
    private MmsItemRegistration itemId;
    
    @JoinColumn(name = "GRN_ID", referencedColumnName = "GRN_ID")
    @ManyToOne
    private MmsGrn grnId;

    public MmsGrn getGrnId() {
        return grnId;
    }

    public void setGrnId(MmsGrn grnId) {
        this.grnId = grnId;
    }

//    public Integer getUnitPrice() {
//        return unitPrice;
//    }
//
//    public void setUnitPrice(Integer unitPrice) {
//        this.unitPrice = unitPrice;
//    }
    /**
     *
     */
    public MmsSivDetail() {
    }

    /**
     *
     * @param sivDetId
     */
    public MmsSivDetail(Integer sivDetId) {
        this.sivDetId = sivDetId;
    }

    /**
     *
     * @return
     */
    public Integer getSivDetId() {
        return sivDetId;
    }

    /**
     *
     * @param sivDetId
     */
    public void setSivDetId(Integer sivDetId) {
        this.sivDetId = sivDetId;
    }

    /**
     *
     * @return
     */
    public BigDecimal getQuantity() {
        return quantity;
    }

    /**
     *
     * @param quantity
     */
    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    /**
     *
     * @return
     */
    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    /**
     *
     * @param unitPrice
     */
    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public MmsItemRegistration getItemId() {
        return itemId;
    }

    public void setItemId(MmsItemRegistration itemId) {
        this.itemId = itemId;
    }

    public MmsSiv getSivId() {
        return sivId;
    }

    public void setSivId(MmsSiv sivId) {
        this.sivId = sivId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sivDetId != null ? sivDetId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MmsSivDetail)) {
            return false;
        }
        MmsSivDetail other = (MmsSivDetail) object;
        if ((this.sivDetId == null && other.sivDetId != null) || (this.sivDetId != null && !this.sivDetId.equals(other.sivDetId))) {
            return false;
        }
        return true;
    }

//    @Override
//    public String toString() {
//        return itemId.getMatCode();
//    }

}
