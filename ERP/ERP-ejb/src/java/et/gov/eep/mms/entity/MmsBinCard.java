/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.mms.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
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
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author muller
 */
@Entity
@Table(name = "MMS_BIN_CARD")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MmsBinCard.findAll", query = "SELECT m FROM MmsBinCard m"),
    @NamedQuery(name = "MmsBinCard.findByCardNo", query = "SELECT m FROM MmsBinCard m WHERE m.cardNo = :cardNo"),
//    @NamedQuery(name = "MmsBinCard.findByItemId", query = "SELECT m FROM MmsBinCard m WHERE m.materialId.materialId =:materialId"),
    @NamedQuery(name = "MmsBinCard.findByItemCode", query = "SELECT m FROM MmsBinCard m WHERE m.itemCode = :itemCode"),
    @NamedQuery(name = "MmsBinCard.findByItemId", query = "SELECT m FROM MmsBinCard m WHERE m.materialId.materialId = :materialId"),
    @NamedQuery(name = "MmsBinCard.findByItemIdAndStoreId", query = "SELECT m FROM MmsBinCard m WHERE m.materialId.materialId = :materialId AND m.storeId.storeId=:storeId"),
    @NamedQuery(name = "MmsBinCard.findByStoreNo", query = "SELECT m FROM MmsBinCard m WHERE m.storeNo = :storeNo"),
    @NamedQuery(name = "MmsBinCard.findByLocationId", query = "SELECT m FROM MmsBinCard m WHERE m.locationId = :locationId"),
    @NamedQuery(name = "MmsBinCard.findByInitalQuantity", query = "SELECT m FROM MmsBinCard m WHERE m.initalQuantity = :initalQuantity"),
    @NamedQuery(name = "MmsBinCard.findByUnitPrice", query = "SELECT m FROM MmsBinCard m WHERE m.unitPrice = :unitPrice"),
    @NamedQuery(name = "MmsBinCard.findByMinimum", query = "SELECT m FROM MmsBinCard m WHERE m.minimumLevel = :minimumLevel"),
    @NamedQuery(name = "MmsBinCard.findByMaximum", query = "SELECT m FROM MmsBinCard m WHERE m.maximumLevel = :maximumLevel"),
    @NamedQuery(name = "MmsBinCard.findByStorId", query = "SELECT m FROM MmsBinCard m WHERE m.storeId.storeId = :storeId"),
    @NamedQuery(name = "MmsBinCard.findByReorderLevel", query = "SELECT m FROM MmsBinCard m WHERE m.reorderLevel = :reorderLevel"),
    @NamedQuery(name = "MmsBinCard.findByCurrentQuantity", query = "SELECT m FROM MmsBinCard m WHERE m.currentQuantity = :currentQuantity")})
public class MmsBinCard implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MMS_BINCARD_SEQ")
    @SequenceGenerator(name = "MMS_BINCARD_SEQ", sequenceName = "MMS_BINCARD_SEQ", allocationSize = 1)
    @Column(name = "CARD_NO")
    private Integer cardNo;
    @Basic(optional = false)

    @Size(min = 1, max = 20)
    @Column(name = "ITEM_CODE")
    private String itemCode;
    @Basic(optional = false)

    @Size(min = 1, max = 20)
    @Column(name = "STORE_NO")
    private String storeNo;
    @Size(max = 20)
    @Column(name = "LOCATION_ID")
    private String locationId;
    @Size(max = 20)
    @Column(name = "ITEM_TYPE")
    private String itemType;
    @Column(name = "SERVICE_YEAR")
    private Integer serviceYear;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "INITIAL_QUANTITY")
    private BigDecimal initalQuantity;
    @Column(name = "UNIT_PRICE")
    private BigDecimal unitPrice;
    @Column(name = "MINIMUM_LEVEL")
    private Integer minimumLevel;
    @Column(name = "MAXIMUM_LEVEL")
    private Integer maximumLevel;
    @Column(name = "REORDER_LEVEL")
    private Integer reorderLevel;
    @Column(name = "CURRENT_QUANTITY")
    private BigDecimal currentQuantity;

    @JoinColumn(name = "MATERIAL_ID", referencedColumnName = "MATERIAL_ID")
    @OneToOne
    private MmsItemRegistration materialId;
    @JoinColumn(name = "STORE_ID", referencedColumnName = "STORE_ID")
    @ManyToOne
    private MmsStoreInformation storeId;

    //<editor-fold defaultstate="collapsed" desc="Dynamic Searching Transient Parameters">
    @Transient
    private String columnName;
    @Transient
    private String columnValue;
    //</editor-fold >

    public MmsBinCard() {
    }

    public MmsBinCard(Integer cardNo) {
        this.cardNo = cardNo;
    }

    public MmsBinCard(Integer cardNo, String itemCode, String storeNo) {
        this.cardNo = cardNo;
        this.itemCode = itemCode;
        this.storeNo = storeNo;
    }

    public Integer getCardNo() {
        return cardNo;
    }

    public void setCardNo(Integer cardNo) {
        this.cardNo = cardNo;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getStoreNo() {
        return storeNo;
    }

    public void setStoreNo(String storeNo) {
        this.storeNo = storeNo;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnValue() {
        return columnValue;
    }

    public void setColumnValue(String columnValue) {
        this.columnValue = columnValue;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public BigDecimal getInitalQuantity() {
        return initalQuantity;
    }

    public void setInitalQuantity(BigDecimal initalQuantity) {
        this.initalQuantity = initalQuantity;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Integer getMinimumLevel() {
        return minimumLevel;
    }

    public void setMinimumLevel(Integer minimumLevel) {
        this.minimumLevel = minimumLevel;
    }

    public Integer getMaximumLevel() {
        return maximumLevel;
    }

    public void setMaximumLevel(Integer maximum) {
        this.maximumLevel = maximum;
    }

    public Integer getReorderLevel() {
        return reorderLevel;
    }

    public void setReorderLevel(Integer reorderLevel) {
        this.reorderLevel = reorderLevel;
    }

    public BigDecimal getCurrentQuantity() {
        return currentQuantity;
    }

    public void setCurrentQuantity(BigDecimal currentQuantity) {
        this.currentQuantity = currentQuantity;
    }

    public MmsItemRegistration getMaterialId() {
        return materialId;
    }

    public void setMaterialId(MmsItemRegistration materialId) {
        this.materialId = materialId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cardNo != null ? cardNo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MmsBinCard)) {
            return false;
        }
        MmsBinCard other = (MmsBinCard) object;
        if ((this.cardNo == null && other.cardNo != null) || (this.cardNo != null && !this.cardNo.equals(other.cardNo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return itemCode;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public Integer getServiceYear() {
        return serviceYear;
    }

    public void setServiceYear(Integer serviceYear) {
        this.serviceYear = serviceYear;
    }

    public MmsStoreInformation getStoreId() {
        return storeId;
    }

    public void setStoreId(MmsStoreInformation storeId) {
        this.storeId = storeId;
    }

}
