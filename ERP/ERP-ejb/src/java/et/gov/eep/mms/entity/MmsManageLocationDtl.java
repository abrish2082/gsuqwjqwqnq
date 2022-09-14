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
@Table(name = "MMS_MANAGE_LOCATION_DTL", catalog = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MmsManageLocationDtl.findAll", query = "SELECT m FROM MmsManageLocationDtl m"),
    @NamedQuery(name = "MmsManageLocationDtl.findById", query = "SELECT m FROM MmsManageLocationDtl m WHERE m.id = :id"),
    //@NamedQuery(name = "MmsManageLocationDtl.findByShelfCode", query = "SELECT m FROM MmsManageLocationDtl m WHERE m.shelfCode = :shelfCode"),
    @NamedQuery(name = "MmsManageLocationDtl.findByMatId", query = "SELECT m FROM MmsManageLocationDtl m WHERE m.materialId.materialId  = :materialId"),
    @NamedQuery(name = "MmsManageLocationDtl.findByCellId", query = "SELECT m FROM MmsManageLocationDtl m WHERE m.cellId = :cellId"),
    @NamedQuery(name = "MmsManageLocationDtl.findByMatIdAndStoreId", query = "SELECT m FROM MmsManageLocationDtl m WHERE m.materialId=:materialId AND m.manageLocationId.storeId=:storeId"),
    @NamedQuery(name = "MmsManageLocationDtl.findByLocationQuantity", query = "SELECT m FROM MmsManageLocationDtl m WHERE m.locationQuantity = :locationQuantity")})
public class MmsManageLocationDtl implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MMS_MANAGE_LOC_DTL_SEQ")
    @SequenceGenerator(name = "MMS_MANAGE_LOC_DTL_SEQ", sequenceName = "MMS_MANAGE_LOC_DTL_SEQ", allocationSize = 1)

    @Column(name = "ID", precision = 0, scale = -127)
    private Integer id;
    @JoinColumn(name = "SHELF_ID", referencedColumnName = "SHELF_ID")
    @ManyToOne
    private MmsShelfInfo shelfId;

    @JoinColumn(name = "CELL_ID", referencedColumnName = "LOCATION_ID")
    @ManyToOne
    private MmsLocationInfo cellId;
    @JoinColumn(name = "MATERIAL_ID", referencedColumnName = "MATERIAL_ID")
    @ManyToOne
    private MmsItemRegistration materialId;

    @Column(name = "SELECT_OPTION_1")
    private Integer selectOption1;
    @Column(name = "SELECT_OPTION_2")
    private Integer selectOption2;
    @Transient
    private String columnName;
    @Transient
    private String columnValue;
    @Column(name = "LOCATION_QUANTITY")
    private Integer locationQuantity;
    @JoinColumn(name = "MANAGE_LOCATION_ID", referencedColumnName = "ID")
    @ManyToOne
    private MmsManageLocation manageLocationId;
    @Size(max = 20)
    @Column(name = "EXPIRY_DATE")

    private String expiryDate;

    @Size(max = 20)
    @Column(name = "LOCATION_DATE")

    private String locationDate;

    @Transient
    private Integer deductedQuantity;
    @Transient
    private Integer QuantityAdjustment;
    @Transient
    private BigDecimal totalQuantity;

    public MmsManageLocationDtl() {
    }

    public MmsManageLocationDtl(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getLocationQuantity() {
        return locationQuantity;
    }

    public void setLocationQuantity(Integer locationQuantity) {
        this.locationQuantity = locationQuantity;
    }

    public MmsManageLocation getManageLocationId() {
        return manageLocationId;
    }

    public void setManageLocationId(MmsManageLocation manageLocationId) {
        this.manageLocationId = manageLocationId;
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
        if (!(object instanceof MmsManageLocationDtl)) {
            return false;
        }
        MmsManageLocationDtl other = (MmsManageLocationDtl) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.eep.mms.entity.MmsManageLocationDtl[ id=" + id + " ]";
    }

    public MmsShelfInfo getShelfId() {
        return shelfId;
    }

    public void setShelfId(MmsShelfInfo shelfId) {
        this.shelfId = shelfId;
    }

    public MmsLocationInfo getCellId() {
        return cellId;
    }

    public void setCellId(MmsLocationInfo cellId) {
        this.cellId = cellId;
    }

    public MmsItemRegistration getMaterialId() {
        return materialId;
    }

    public void setMaterialId(MmsItemRegistration materialId) {
        this.materialId = materialId;
    }

    public Integer getSelectOption1() {
        return selectOption1;
    }

    public void setSelectOption1(Integer selectOption1) {
        this.selectOption1 = selectOption1;
    }

    public Integer getSelectOption2() {
        return selectOption2;
    }

    public void setSelectOption2(Integer selectOption2) {
        this.selectOption2 = selectOption2;
    }

    public Integer getDeductedQuantity() {
        return deductedQuantity;
    }

    public void setDeductedQuantity(Integer deductedQuantity) {
        this.deductedQuantity = deductedQuantity;
    }

    public Integer getQuantityAdjustment() {
        return QuantityAdjustment;
    }

    public void setQuantityAdjustment(Integer QuantityAdjustment) {
        this.QuantityAdjustment = QuantityAdjustment;
    }

    public BigDecimal getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(BigDecimal totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getLocationDate() {
        return locationDate;
    }

    public void setLocationDate(String locationDate) {
        this.locationDate = locationDate;
    }

}
