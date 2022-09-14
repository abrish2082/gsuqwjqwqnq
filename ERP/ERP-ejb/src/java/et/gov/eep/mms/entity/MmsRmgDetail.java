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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author user
 */
@Entity
@Table(name = "MMS_RMG_DETAIL")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MmsRmgDetail.findAll", query = "SELECT m FROM MmsRmgDetail m"),
    @NamedQuery(name = "MmsRmgDetail.findByRmgDetId", query = "SELECT m FROM MmsRmgDetail m WHERE m.rmgDetId = :rmgDetId"),
   
    @NamedQuery(name = "MmsRmgDetail.findbyrmgIds", query = "SELECT m FROM MmsRmgDetail m WHERE m.rmgId.rmgId = :rmgId"),
    
    @NamedQuery(name = "MmsRmgDetail.findByUnitPrice", query = "SELECT m FROM MmsRmgDetail m WHERE m.unitPrice = :unitPrice"),
    @NamedQuery(name = "MmsRmgDetail.findByQuantity", query = "SELECT m FROM MmsRmgDetail m WHERE m.quantity = :quantity"),
    @NamedQuery(name = "MmsRmgDetail.findByRemark", query = "SELECT m FROM MmsRmgDetail m WHERE m.remark = :remark")})
public class MmsRmgDetail implements Serializable {

    @Column(name = "UNIT_PRICE")
    private BigDecimal unitPrice;
    @Column(name = "QUANTITY")
    private BigDecimal quantity;

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MMS_RMG_DET_SEQ")
    @SequenceGenerator(name = "MMS_RMG_DET_SEQ", sequenceName = "MMS_RMG_DET_SEQ", allocationSize = 1)
    @Column(name = "RMG_DET_ID")
    private int rmgDetId;
   
    @Size(max = 20)
    @Column(name = "REMARK")
    private String remark;
    @JoinColumn(name = "RMG_ID", referencedColumnName = "RMG_ID")
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private MmsRmg rmgId;

     @JoinColumn(name = "ITEM_ID", referencedColumnName = "MATERIAL_ID")
    @ManyToOne
    private MmsItemRegistration itemId;
    /**
     *
     */
    public MmsRmgDetail() {
    }

    /**
     *
     * @param rmgDetId
     */
    public MmsRmgDetail(Integer rmgDetId) {
        this.rmgDetId = rmgDetId;
    }

    /**
     *
     * @return
     */
    public Integer getRmgDetId() {
        return rmgDetId;
    }

    /**
     *
     * @param rmgDetId
     */
    public void setRmgDetId(Integer rmgDetId) {
        this.rmgDetId = rmgDetId;
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

    /**
     *
     * @return
     */
    public String getRemark() {
        return remark;
    }

    /**
     *
     * @param remark
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    public MmsRmg getRmgId() {
        return rmgId;
    }

    public void setRmgId(MmsRmg rmgId) {
        this.rmgId = rmgId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        //  hash += (rmgDetId != null ? rmgDetId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MmsRmgDetail)) {
            return false;
        }

        return true;
    }

    @Override
    public String toString() {
        return "et.gov.eep.mms.entity.MmsRmgDetail[ rmgDetId=" + rmgDetId + " ]";
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

    public MmsItemRegistration getItemId() {
        return itemId;
    }

    public void setItemId(MmsItemRegistration itemId) {
        this.itemId = itemId;
    }

}
