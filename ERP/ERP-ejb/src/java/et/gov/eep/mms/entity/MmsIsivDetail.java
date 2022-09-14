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
 * @author user
 */
@Entity
@Table(name = "MMS_ISIV_DETAIL")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MmsIsivDetail.findAll", query = "SELECT m FROM MmsIsivDetail m"),
    @NamedQuery(name = "MmsIsivDetail.findByMatTranDetId", query = "SELECT m FROM MmsIsivDetail m WHERE m.matTranDetId = :matTranDetId"),
    @NamedQuery(name = "MmsIsivDetail.findbyisivId", query = "SELECT m FROM MmsIsivDetail m WHERE m.transferId.transferId = :transferId"),
    @NamedQuery(name = "MmsIsivDetail.findByIsivDtlIdMaximum", query = "SELECT m FROM MmsIsivDetail m WHERE m.matTranDetId = (SELECT Max(c.matTranDetId)  from MmsIsivDetail c)"),
    @NamedQuery(name = "MmsIsivDetail.findByQuantity", query = "SELECT m FROM MmsIsivDetail m WHERE m.quantity = :quantity")})
public class MmsIsivDetail implements Serializable {

   
   
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MMS_ISIV_DETAIL_SEQ")
    @SequenceGenerator(name = "MMS_ISIV_DETAIL_SEQ", sequenceName = "MMS_ISIV_DETAIL_SEQ", allocationSize = 1)
    
    @Column(name = "MAT_TRAN_DET_ID")
    private int matTranDetId;
    
    @Column(name = "QUANTITY")
    private BigDecimal quantity;
    
    @Column(name = "UNIT_PRICE")
    private BigDecimal unitPrice;

    @JoinColumn(name = "TRANSFER_ID", referencedColumnName = "TRANSFER_ID")
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private MmsIsiv transferId;
    
     @JoinColumn(name = "ITEM_ID", referencedColumnName = "MATERIAL_ID")
    @ManyToOne
    private MmsItemRegistration itemId;

    /**
     *
     */
    public MmsIsivDetail() {
    }

    /**
     *
     * @param matTranDetId
     */
    public MmsIsivDetail(int matTranDetId) {
        this.matTranDetId = matTranDetId;
    }

    /**
     *
     * @return
     */
    public int getMatTranDetId() {
        return matTranDetId;
    }

    /**
     *
     * @param matTranDetId
     */
    public void setMatTranDetId(Integer matTranDetId) {
        this.matTranDetId = matTranDetId;
    }

    public MmsIsiv getTransferId() {
        return transferId;
    }

    public void setTransferId(MmsIsiv transferId) {
        this.transferId = transferId;
    }

    @Override
    public int hashCode() {
        int hash = 0;

        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MmsIsivDetail)) {
            return false;
        }
        MmsIsivDetail other = (MmsIsivDetail) object;

        return true;
    }

    @Override
    public String toString() {
        return "et.gov.eep.mms.entity.MmsIsivDetail[ matTranDetId=" + matTranDetId + " ]";
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
