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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Sadik
 */
@Entity
@Table(name = "MMS_INSPECTION_DETAIL")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MmsInspectionDetail.findAll", query = "SELECT m FROM MmsInspectionDetail m"),
    @NamedQuery(name = "MmsInspectionDetail.findByInspectionDetId", query = "SELECT m FROM MmsInspectionDetail m WHERE m.inspectionDetId = :inspectionDetId"),
    
    @NamedQuery(name = "MmsInspectionDetail.findByQuantity", query = "SELECT m FROM MmsInspectionDetail m WHERE m.quantity = :quantity"),
    @NamedQuery(name = "MmsInspectionDetail.findByRemark", query = "SELECT m FROM MmsInspectionDetail m WHERE m.remark = :remark"),
  
    @NamedQuery(name = "MmsInspectionDetail.findByUnitPrice", query = "SELECT m FROM MmsInspectionDetail m WHERE m.unitPrice = :unitPrice"),
    @NamedQuery(name = "MmsInspectionDetail.frindByInspId", query = "SELECT m FROM MmsInspectionDetail m WHERE m.inspectionId = :inspectionId"),
    @NamedQuery(name = "MmsInspectionDetail.findByRejectedQuantity", query = "SELECT m FROM MmsInspectionDetail m WHERE m.rejectedQuantity = :rejectedQuantity")})
public class MmsInspectionDetail implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MMS_INSPECTION_DET_SEQ")
    @SequenceGenerator(name = "MMS_INSPECTION_DET_SEQ", sequenceName = "MMS_INSPECTION_DET_SEQ", allocationSize = 1)
    @Column(name = "INSPECTION_DET_ID", nullable = false)
    private Integer inspectionDetId;
  
    @Column(name = "QUANTITY")
    private BigDecimal quantity;
    @Size(max = 255)
    @Column(name = "REMARK", length = 255)
    private String remark;
  
    @Column(name = "UNIT_PRICE")
    private double unitPrice;
    @Column(name = "REJECTED_QUANTITY")
    private BigDecimal rejectedQuantity;
    @JoinColumn(name = "INSPECTION_ID", referencedColumnName = "INSPECTION_ID")
    @ManyToOne
    private MmsInspection inspectionId;
    @JoinColumn(name = "ITEM_ID", referencedColumnName = "MATERIAL_ID")
    @ManyToOne
    private MmsItemRegistration itemId;
     @Transient
    private BigDecimal poQuantity;
      @Transient
    private Integer contractQuantity;
     @Transient
    private Integer RejectQuantity;

    public MmsInspectionDetail() {
    }

    public MmsInspectionDetail(Integer inspectionDetId) {
        this.inspectionDetId = inspectionDetId;
    }

    public Integer getInspectionDetId() {
        return inspectionDetId;
    }

    /**
     *
     * @param inspectionDetId
     */
    public void setInspectionDetId(Integer inspectionDetId) {
        this.inspectionDetId = inspectionDetId;
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

   

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public BigDecimal getRejectedQuantity() {
        return rejectedQuantity;
    }

    public void setRejectedQuantity(BigDecimal rejectedQuantity) {
        this.rejectedQuantity = rejectedQuantity;
    }

    public MmsInspection getInspectionId() {
        return inspectionId;
    }

    public void setInspectionId(MmsInspection inspectionId) {
        this.inspectionId = inspectionId;
    }

    public MmsItemRegistration getItemId() {
        return itemId;
    }

    public void setItemId(MmsItemRegistration itemId) {
        this.itemId = itemId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (inspectionDetId != null ? inspectionDetId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MmsInspectionDetail)) {
            return false;
        }
        MmsInspectionDetail other = (MmsInspectionDetail) object;
        if ((this.inspectionDetId == null && other.inspectionDetId != null) || (this.inspectionDetId != null && !this.inspectionDetId.equals(other.inspectionDetId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.eep.mms.entity.MmsInspectionDetail[ inspectionDetId=" + inspectionDetId + " ]";
    }
    
    public BigDecimal getQuantity() {
        return quantity;
}

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }
    public BigDecimal getPoQuantity() {
        return poQuantity;
    }

    public void setPoQuantity(BigDecimal poQuantity) {
        this.poQuantity = poQuantity;
    }

    public Integer getContractQuantity() {
        return contractQuantity;
    }

    public void setContractQuantity(Integer contractQuantity) {
        this.contractQuantity = contractQuantity;
    }
    
    

    public Integer getRejectQuantity() {
        return RejectQuantity;
    }

    public void setRejectQuantity(Integer RejectQuantity) {
        this.RejectQuantity = RejectQuantity;
    }
    
}
