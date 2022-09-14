/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.mms.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author user
 */
@Entity
@Table(name = "MMS_STOREREQ_DETAIL")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MmsStorereqDetail.findAll", query = "SELECT m FROM MmsStorereqDetail m"),

    @NamedQuery(name = "MmsStorereqDetail.findByQuantity", query = "SELECT m FROM MmsStorereqDetail m WHERE m.quantity = :quantity"),
    @NamedQuery(name = "MmsStorereqDetail.findBySrNo", query = "SELECT m FROM MmsStorereqDetail m WHERE m.storeReqId.srNo = :srNo"),
    @NamedQuery(name = "MmsStorereqDetail.findByAction", query = "SELECT m FROM MmsStorereqDetail m WHERE m.action = :action"),
    @NamedQuery(name = "MmsStorereqDetail.frindBysrId", query = "SELECT m FROM MmsStorereqDetail m WHERE m.storeReqId = :storeReqId"),
    @NamedQuery(name = "MmsStorereqDetail.findByStoreReqDetId", query = "SELECT m FROM MmsStorereqDetail m WHERE m.storeReqDetId = :storeReqDetId")})
public class MmsStorereqDetail implements Serializable {

    @JoinColumn(name = "ITEM_ID", referencedColumnName = "MATERIAL_ID")
    @ManyToOne
    private MmsItemRegistration itemId;

    @Column(name = "UNIT_PRICE")
    private BigDecimal unitPrice;
    private static final long serialVersionUID = 1L;

    @Column(name = "QUANTITY")
    private BigDecimal quantity;
    @Column(name = "APPROVED_QUANTITY")
    private BigDecimal approvedQuantity;
    @Column(name = "PR_QUANTITY")
    private BigDecimal prQuantity;

    @Size(max = 250)
    @Column(name = "ACTION_TAKEN")
    private String action;
    
    @Size(max = 20)
    @Column(name = "PLATE_NO")
    private String plateNo;

    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MMS_STORE_REQ_DETAIL_SEQ")
    @SequenceGenerator(name = "MMS_STORE_REQ_DETAIL_SEQ", sequenceName = "MMS_STORE_REQ_DETAIL_SEQ", allocationSize = 1)
    @Column(name = "STORE_REQ_DET_ID")
    private Integer storeReqDetId;
    @JoinColumn(name = "STORE_REQ_ID", referencedColumnName = "STORE_REQ_ID")
    @ManyToOne
    private MmsStorereq storeReqId;
    
    @JoinColumn(name = "GRN_ID", referencedColumnName = "GRN_ID")
    @ManyToOne
    private MmsGrn grnId;
    @Transient
    private Integer approvedQuantityTemp;

    /**
     *
     */
    public MmsStorereqDetail() {
    }

    /**
     *
     * @param storeReqDetId
     */
    public MmsStorereqDetail(Integer storeReqDetId) {
        this.storeReqDetId = storeReqDetId;
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
    public String getAction() {
        return action;
    }

    /**
     *
     * @param action
     */
    public void setAction(String action) {
        this.action = action;
    }

    /**
     *
     * @return
     */
    public Integer getStoreReqDetId() {
        return storeReqDetId;
    }

    /**
     *
     * @param storeReqDetId
     */
    public void setStoreReqDetId(Integer storeReqDetId) {
        this.storeReqDetId = storeReqDetId;
    }

    /**
     *
     * @return
     */
    public MmsStorereq getStoreReqId() {
        return storeReqId;
    }

    /**
     *
     * @param storeReqId
     */
    public void setStoreReqId(MmsStorereq storeReqId) {
        this.storeReqId = storeReqId;
    }

    public MmsGrn getGrnId() {
        return grnId;
    }

    public void setGrnId(MmsGrn grnId) {
        this.grnId = grnId;
    }
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (storeReqDetId != null ? storeReqDetId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MmsStorereqDetail)) {
            return false;
        }
        MmsStorereqDetail other = (MmsStorereqDetail) object;
        if ((this.storeReqDetId == null && other.storeReqDetId != null) || (this.storeReqDetId != null && !this.storeReqDetId.equals(other.storeReqDetId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.eep.mms.entity.MmsStorereqDetail[ storeReqDetId=" + storeReqDetId + " ]";
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

    public BigDecimal getApprovedQuantity() {
        return approvedQuantity;
    }

    public void setApprovedQuantity(BigDecimal approvedQuantity) {
        this.approvedQuantity = approvedQuantity;
    }

    public BigDecimal getPrQuantity() {
        return prQuantity;
    }

    public void setPrQuantity(BigDecimal prQuantity) {
        this.prQuantity = prQuantity;
    }

    public MmsItemRegistration getItemId() {
        return itemId;
    }

    public void setItemId(MmsItemRegistration itemId) {
        this.itemId = itemId;
    }

    public String getPlateNo() {
        return plateNo;
    }

    public void setPlateNo(String plateNo) {
        this.plateNo = plateNo;
    }

    public Integer getApprovedQuantityTemp() {
        return approvedQuantityTemp;
    }

    public void setApprovedQuantityTemp(Integer approvedQuantityTemp) {
        this.approvedQuantityTemp = approvedQuantityTemp;
    }

}
