/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.entity.Bond;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author mora
 */
@Entity
@Table(name = "FMS_BOND_TYPE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FmsBondType.findAll", query = "SELECT f FROM FmsBondType f"),
    @NamedQuery(name = "FmsBondType.findByBondTypeId", query = "SELECT f FROM FmsBondType f WHERE f.BondTypeId = :BondTypeId"),
    @NamedQuery(name = "FmsBondType.findByBondTypeIdLike", query = "SELECT f FROM FmsBondType f WHERE f.BondTypeId LIKE :BondTypeId"),
    @NamedQuery(name = "FmsBondType.findByBondName", query = "SELECT f FROM FmsBondType f WHERE f.BondName = :BondName"),
    @NamedQuery(name = "FmsBondType.findByBondType", query = "SELECT f FROM FmsBondType f WHERE f.BondType = :BondType"),
    @NamedQuery(name = "FmsBondType.findByBondInterst", query = "SELECT f FROM FmsBondType f WHERE f.BondInterst = :BondInterst"),
    @NamedQuery(name = "FmsBondType.findByBondCurrency", query = "SELECT f FROM FmsBondType f WHERE f.BondCurrency = :BondCurrency")})
public class FmsBondType implements Serializable {
//<editor-fold defaultstate="collapsed" desc="variable declaration">

    @Column(name = "BOND_INTERST")
    private Double BondInterst;
    @OneToMany(mappedBy = "BondTypeId")
    private List<FmsBondApplication> fmsBondApplicationList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "BondTypeId")
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "BOND_TYPE_ID")
    private String BondTypeId;
    @Size(max = 20)
    @Column(name = "BOND_NAME")
    private String BondName;
    @Size(max = 20)
    @Column(name = "BOND_TYPE")
    private String BondType;
    @Size(max = 20)
    @Column(name = "BOND_CURRENCY")
    private String BondCurrency;
//</editor-fold>

    public FmsBondType() {
    }
//<editor-fold defaultstate="collapsed" desc="getter and setter">

    public FmsBondType(String BondTypeId) {
        this.BondTypeId = BondTypeId;
    }

    public String getBondTypeId() {
        return BondTypeId;
    }

    public void setBondTypeId(String BondTypeId) {
        this.BondTypeId = BondTypeId;
    }

    public String getBondName() {
        return BondName;
    }

    public void setBondName(String BondName) {
        this.BondName = BondName;
    }

    public String getBondType() {
        return BondType;
    }

    public void setBondType(String BondType) {
        this.BondType = BondType;
    }

    public Double getBondInterst() {
        return BondInterst;
    }

    public void setBondInterst(Double BondInterst) {
        this.BondInterst = BondInterst;
    }

    public String getBondCurrency() {
        return BondCurrency;
    }

    public void setBondCurrency(String BondCurrency) {
        this.BondCurrency = BondCurrency;
    }
//</editor-fold>

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (BondTypeId != null ? BondTypeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof FmsBondType)) {
            return false;
        }
        FmsBondType other = (FmsBondType) object;
        if ((this.BondTypeId == null && other.BondTypeId != null) || (this.BondTypeId != null && !this.BondTypeId.equals(other.BondTypeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return BondTypeId;
    }

}
