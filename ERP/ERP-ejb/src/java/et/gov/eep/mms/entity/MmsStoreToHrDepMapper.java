/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.mms.entity;

import et.gov.eep.hrms.entity.organization.HrDepartments;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Query;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Sadik
 */
@Entity
@Table(name = "MMS_STORE_TO_HR_DEP_MAPPER")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MmsStoreToHrDepMapper.findAll", query = "SELECT m FROM MmsStoreToHrDepMapper m"),
    @NamedQuery(name = "MmsStoreToHrDepMapper.findById", query = "SELECT m FROM MmsStoreToHrDepMapper m WHERE m.id = :id"),
    @NamedQuery(name = "MmsStoreToHrDepMapper.findByDepartmentIdAndWarehouseId", query = "SELECT m FROM MmsStoreToHrDepMapper m WHERE m.departmentId = :depId AND m.warehouseId = :warehouseId"),
    @NamedQuery(name = "MmsStoreToHrDepMapper.findByDescription", query = "SELECT m FROM MmsStoreToHrDepMapper m WHERE m.description = :description")})
public class MmsStoreToHrDepMapper implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MMS_STORE_TO_HR_DEP_MAPPER_SEQ")
    @SequenceGenerator(name = "MMS_STORE_TO_HR_DEP_MAPPER_SEQ", sequenceName = "MMS_STORE_TO_HR_DEP_MAPPER_SEQ", allocationSize = 1)
    @Column(name = "ID", nullable = false, precision = 0, scale = -127)
    private Integer id;

    //<editor-fold defaultstate="collapsed" desc="Dynamic Searching Transient Parameters">
    @Transient
    private String columnName;
    @Transient
    private String columnValue;
    //</editor-fold >
    @Size(max = 50)
    @Column(name = "DESCRIPTION", length = 50)
    private String description;
    @JoinColumn(name = "DEPARTMENT_ID", referencedColumnName = "DEP_ID")
    @ManyToOne
    private HrDepartments departmentId;
    @JoinColumn(name = "WAREHOUSE_ID", referencedColumnName = "ID")
    @ManyToOne
    private MmsLuWareHouse warehouseId;

    public MmsStoreToHrDepMapper() {
    }

    public MmsStoreToHrDepMapper(Integer id) {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public HrDepartments getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(HrDepartments departmentId) {
        this.departmentId = departmentId;
    }

    public MmsLuWareHouse getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(MmsLuWareHouse warehouseId) {
        this.warehouseId = warehouseId;
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
        if (!(object instanceof MmsStoreToHrDepMapper)) {
            return false;
        }
        MmsStoreToHrDepMapper other = (MmsStoreToHrDepMapper) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.eep.mms.entity.MmsStoreToHrDepMapper[ id=" + id + " ]";
    }

}
