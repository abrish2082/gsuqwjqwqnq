/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.entity;

import et.gov.eep.mms.entity.MmsItemRegistration;
import javax.persistence.Transient;
import java.io.Serializable;
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
@Table(name = "PRMS_PUR_ORDER_DETAIL")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PrmsPurOrderDetail.findAll", query = "SELECT p FROM PrmsPurOrderDetail p"),
    @NamedQuery(name = "PrmsPurOrderDetail.findByPoDeId", query = "SELECT p FROM PrmsPurOrderDetail p WHERE p.poDeId = :poDeId"),
//    @NamedQuery(name = "PrmsPurOrderDetail.findByCode", query = "SELECT p FROM PrmsPurOrderDetail p WHERE p.code = :code"),
//    @NamedQuery(name = "PrmsPurOrderDetail.findByItemName", query = "SELECT p FROM PrmsPurOrderDetail p WHERE p.itemName = :itemName"),
    @NamedQuery(name = "PrmsPurOrderDetail.findByQuantity", query = "SELECT p FROM PrmsPurOrderDetail p WHERE p.quantity = :quantity"),
    @NamedQuery(name = "PrmsPurOrderDetail.findByMaterialId", query = "SELECT p FROM PrmsPurOrderDetail p WHERE p.itemId = :itemId"),
    @NamedQuery(name = "PrmsPurOrderDetail.findByUnitMeasure", query = "SELECT p FROM PrmsPurOrderDetail p WHERE p.unitMeasure = :unitMeasure"),
    @NamedQuery(name = "PrmsPurOrderDetail.findByUnitPrice", query = "SELECT p FROM PrmsPurOrderDetail p WHERE p.unitPrice = :unitPrice")})
public class PrmsPurOrderDetail implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PRMS_PUR_ORDER_DETAIL_SEQ")
    @SequenceGenerator(name = "PRMS_PUR_ORDER_DETAIL_SEQ", sequenceName = "PRMS_PUR_ORDER_DETAIL_SEQ", allocationSize = 1)
    @Column(name = "PO_DE_ID")
    private Long poDeId;
//    @Size(max = 400)
//    @Column(name = "CODE")
//    private String code;
//    @Size(max = 400)
//    @Column(name = "ITEM_NAME")
//    private String itemName;
    @Column(name = "QUANTITY")
    private Long quantity;
    @Size(max = 20)
    @Column(name = "UNIT_MEASURE")
    private String unitMeasure;
    @Column(name = "UNIT_PRICE")
    private Long unitPrice;
    @JoinColumn(name = "ITEM_ID", referencedColumnName = "MATERIAL_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private MmsItemRegistration itemId;
     @JoinColumn(name = "SERVICE_ID", referencedColumnName = "SERV_AND_WORK_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private PrmsServiceAndWorkReg serviceId;
    @JoinColumn(name = "PO_ID", referencedColumnName = "PO_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private PrmsPurchaseOrder poId;

    @Transient
    Double totals;
    
    
    public PrmsPurOrderDetail() {
    }

    public Double getTotals() {
        return totals;
    }

    public void setTotals(Double totals) {
        this.totals = totals;
    }
    
    

    public PrmsPurOrderDetail(Long poDeId) {
        this.poDeId = poDeId;
    }

    public Long getPoDeId() {
        return poDeId;
    }

    public void setPoDeId(Long poDeId) {
        this.poDeId = poDeId;
    }

//    public String getCode() {
//        return code;
//    }
//
//    public void setCode(String code) {
//        this.code = code;
//    }
//
//    public String getItemName() {
//        return itemName;
//    }
//
//    public void setItemName(String itemName) {
//        this.itemName = itemName;
//    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public String getUnitMeasure() {
        return unitMeasure;
    }

    public void setUnitMeasure(String unitMeasure) {
        this.unitMeasure = unitMeasure;
    }

    public Long getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Long unitPrice) {
        this.unitPrice = unitPrice;
    }

    public PrmsPurchaseOrder getPoId() {
        return poId;
    }

    public void setPoId(PrmsPurchaseOrder poId) {
        this.poId = poId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (poDeId != null ? poDeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PrmsPurOrderDetail)) {
            return false;
        }
        PrmsPurOrderDetail other = (PrmsPurOrderDetail) object;
        if ((this.poDeId == null && other.poDeId != null) || (this.poDeId != null && !this.poDeId.equals(other.poDeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return String.valueOf(poDeId);
    }
    @Transient
    double total;

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    
    public MmsItemRegistration getItemId() {
        return itemId;
    }

    public void setItemId(MmsItemRegistration itemId) {
        this.itemId = itemId;
    }

    public PrmsServiceAndWorkReg getServiceId() {
        return serviceId;
    }

    public void setServiceId(PrmsServiceAndWorkReg serviceId) {
        this.serviceId = serviceId;
    }

}
