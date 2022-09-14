/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.entity.perDiem;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author muller
 */
@Entity
@Table(name = "FMS_LU_ADDITIONAL_AMOUNT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FmsLuAdditionalAmount.findAll", query = "SELECT f FROM FmsLuAdditionalAmount f"),
    @NamedQuery(name = "FmsLuAdditionalAmount.findById", query = "SELECT f FROM FmsLuAdditionalAmount f WHERE f.id = :id"),
    @NamedQuery(name = "FmsLuAdditionalAmount.findByLodgingAdditional", query = "SELECT f FROM FmsLuAdditionalAmount f WHERE f.lodgingAdditional = :lodgingAdditional"),
    @NamedQuery(name = "FmsLuAdditionalAmount.findByLunchAdditional", query = "SELECT f FROM FmsLuAdditionalAmount f WHERE f.lunchAdditional = :lunchAdditional"),
    @NamedQuery(name = "FmsLuAdditionalAmount.findByLevelId", query = "SELECT f FROM FmsLuAdditionalAmount f WHERE f.levelId = :levelId"),
    @NamedQuery(name = "FmsLuAdditionalAmount.findByAllParameters", query = "SELECT f FROM FmsLuAdditionalAmount f WHERE f.levelId LIKE :levelId")})
   public class FmsLuAdditionalAmount implements Serializable {
   @Transient
    private String columnName;
    @Transient
    private String columnValue;
    private static final long serialVersionUID = 1L;
    //<editor-fold defaultstate="collapsed" desc="variable declaration">
    @Id
    @Basic(optional = false)
    @NotNull
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FMS_ADDITIONAL_SEQ")
    @SequenceGenerator(name = "FMS_ADDITIONAL_SEQ", sequenceName = "FMS_ADDITIONAL_SEQ", allocationSize = 1)
    @Column(name = "ID")
    private BigDecimal id;
    @Column(name = "LODGING_ADDITIONAL")
    private Integer lodgingAdditional;
    @Column(name = "LUNCH_ADDITIONAL")
    private Integer lunchAdditional;
    @Column(name = "LEVEL_ID")
    private String levelId;
//</editor-fold>

    public FmsLuAdditionalAmount() {
    }
//<editor-fold defaultstate="collapsed" desc="getter and setter">

    public FmsLuAdditionalAmount(BigDecimal id) {
        this.id = id;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public Integer getLodgingAdditional() {
        return lodgingAdditional;
    }

    public void setLodgingAdditional(Integer lodgingAdditional) {
        this.lodgingAdditional = lodgingAdditional;
    }

    public Integer getLunchAdditional() {
        return lunchAdditional;
    }

    public void setLunchAdditional(Integer lunchAdditional) {
        this.lunchAdditional = lunchAdditional;
    }

    public String getLevelId() {
        return levelId;
    }

    public void setLevelId(String levelId) {
        this.levelId = levelId;
    }
//</editor-fold>

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
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

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof FmsLuAdditionalAmount)) {
            return false;
        }
        FmsLuAdditionalAmount other = (FmsLuAdditionalAmount) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return id.toString();
    }

}
