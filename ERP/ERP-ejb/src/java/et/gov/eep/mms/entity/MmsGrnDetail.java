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
import javax.persistence.Transient;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author user
 */
@Entity
@Table(name = "MMS_GRN_DETAIL")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MmsGrnDetail.findAll", query = "SELECT m FROM MmsGrnDetail m"),
    @NamedQuery(name = "MmsGrnDetail.findByGrnDetailId", query = "SELECT m FROM MmsGrnDetail m WHERE m.grnDetailId = :grnDetailId"),

    @NamedQuery(name = "MmsGrnDetail.findByUnitPrice", query = "SELECT m FROM MmsGrnDetail m WHERE m.grnId.grnId = :grnId"),
    @NamedQuery(name = "MmsGrnDetail.findByReceivedQuantity", query = "SELECT m FROM MmsGrnDetail m WHERE m.receivedQuantity = :receivedQuantity"),
    @NamedQuery(name = "MmsGrnDetail.findByRemark", query = "SELECT m FROM MmsGrnDetail m WHERE m.remark = :remark"),
    @NamedQuery(name = "MmsGrnDetail.findbyGrnIds", query = "SELECT m FROM MmsGrnDetail m WHERE m.grnId.grnId = :grnId"),
    @NamedQuery(name = "MmsGrnDetail.findByGRNDtlIdMaximum", query = "SELECT m FROM MmsGrnDetail m WHERE m.grnDetailId = (SELECT Max(c.grnDetailId)  from MmsGrnDetail c)")})
public class MmsGrnDetail implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MMS_GRN_DETAIL_SEQ")
    @SequenceGenerator(name = "MMS_GRN_DETAIL_SEQ", sequenceName = "MMS_GRN_DETAIL_SEQ", allocationSize = 1)
    @Column(name = "GRN_DETAIL_ID")
    private Integer grnDetailId;

    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "UNIT_PRICE")
    private BigDecimal unitPrice;
//    @JoinColumn(name = "ITEM_ID", referencedColumnName = "MATERIAL_ID")
//    @ManyToOne
//    private MmsItemRegistration itemId;
    @Column(name = "RECEIVED_QUANTITY")
    private BigDecimal receivedQuantity;
    @Column(name = "REMAINING_QUANTITY")
    private Integer remainingQuantity;
    @Size(max = 254)
    @Column(name = "REMARK")
    private String remark;

    @JoinColumn(name = "GRN_ID", referencedColumnName = "GRN_ID")
    @ManyToOne()
    private MmsGrn grnId;

    @JoinColumn(name = "ITEM_ID", referencedColumnName = "MATERIAL_ID")
    @ManyToOne()
    private MmsItemRegistration itemId;

    public MmsItemRegistration getItemId() {
        return itemId;
    }

    public void setItemId(MmsItemRegistration itemId) {
        this.itemId = itemId;
    }

    //private MmsInspectionDetail inspectionResDtlId;
    @Transient
    MmsGrnDetail mmsdtl;

    /**
     *
     */
    public MmsGrnDetail() {
    }

    public MmsGrnDetail getMmsdtl() {
        return mmsdtl;
    }

    public void setMmsdtl(MmsGrnDetail mmsdtl) {
        this.mmsdtl = mmsdtl;
    }

    /**
     *
     * @param grnDetailId
     */
    public MmsGrnDetail(Integer grnDetailId) {
        this.grnDetailId = grnDetailId;
    }

    /**
     *
     * @return
     */
    public Integer getGrnDetailId() {
        return grnDetailId;
    }

    /**
     *
     * @param grnDetailId
     */
    public void setGrnDetailId(Integer grnDetailId) {
        this.grnDetailId = grnDetailId;
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
    public BigDecimal getReceivedQuantity() {
        return receivedQuantity;
    }

    /**
     *
     * @param receivedQuantity
     */
    public void setReceivedQuantity(BigDecimal receivedQuantity) {
        this.receivedQuantity = receivedQuantity;
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

    /**
     *
     * @return
     */
    public MmsGrn getGrnId() {
        return grnId;
    }

    /**
     *
     * @param grnId
     */
    public void setGrnId(MmsGrn grnId) {
        this.grnId = grnId;
    }

    @Override
    public int hashCode() {
        int hash = 0;

        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MmsGrnDetail)) {
            return false;
        }
        MmsGrnDetail other = (MmsGrnDetail) object;

        return true;
    }

    @Override
    public String toString() {
        return grnDetailId.toString();
    }

    public Integer getRemainingQuantity() {
        return remainingQuantity;
    }

    public void setRemainingQuantity(Integer remainingQuantity) {
        this.remainingQuantity = remainingQuantity;
    }

}
