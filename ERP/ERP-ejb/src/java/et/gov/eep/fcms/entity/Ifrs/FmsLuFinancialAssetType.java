/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.entity.Ifrs;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author mz
 */
@Entity
@Table(name = "FMS_LU_FINANCIAL_ASSET_TYPE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FmsLuFmsLuFinancialAssetType.findAll", query = "SELECT f FROM FmsLuFinancialAssetType f"),
    @NamedQuery(name = "FmsLuFinancialAssetType.findByAssetTypeId", query = "SELECT f FROM FmsLuFinancialAssetType f WHERE f.assetTypeId = :assetTypeId"),
    @NamedQuery(name = "FmsLuFinancialAssetType.findByName", query = "SELECT f FROM FmsLuFinancialAssetType f WHERE f.name = :name"),
    @NamedQuery(name = "FmsLuFinancialAssetType.findByDescription", query = "SELECT f FROM FmsLuFinancialAssetType f WHERE f.description = :description")})
public class FmsLuFinancialAssetType implements Serializable {

    private static final long serialVersionUID = 1L;
    //<editor-fold defaultstate="collapsed" desc="variable declaration">
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ASSET_TYPE_ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FMS_LU_FINAN_ASSET_TYPE_SEQ")
    @SequenceGenerator(name = "FMS_LU_FINAN_ASSET_TYPE_SEQ", sequenceName = "FMS_LU_FINAN_ASSET_TYPE_SEQ", allocationSize = 1)
    private BigDecimal assetTypeId;
    @Size(max = 20)
    @Column(name = "NAME")
    private String name;
    @Size(max = 20)
    @Column(name = "DESCRIPTION")
    private String description;
    @OneToMany(mappedBy = "financialAssetType")
    private List<FinancialInstrumentRegister> financialInstrumentRegisterList;
//</editor-fold>

    public FmsLuFinancialAssetType() {
    }
//<editor-fold defaultstate="collapsed" desc="getter and setter">

    public FmsLuFinancialAssetType(BigDecimal assetTypeId) {
        this.assetTypeId = assetTypeId;
    }

    public BigDecimal getAssetTypeId() {
        return assetTypeId;
    }

    public void setAssetTypeId(BigDecimal assetTypeId) {
        this.assetTypeId = assetTypeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
//</editor-fold>

    @XmlTransient
    public List<FinancialInstrumentRegister> getFinancialInstrumentRegisterList() {
        return financialInstrumentRegisterList;
    }

    public void setFinancialInstrumentRegisterList(List<FinancialInstrumentRegister> financialInstrumentRegisterList) {
        this.financialInstrumentRegisterList = financialInstrumentRegisterList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (assetTypeId != null ? assetTypeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof FmsLuFinancialAssetType)) {
            return false;
        }
        FmsLuFinancialAssetType other = (FmsLuFinancialAssetType) object;
        if ((this.assetTypeId == null && other.assetTypeId != null) || (this.assetTypeId != null && !this.assetTypeId.equals(other.assetTypeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return name;
    }

}
