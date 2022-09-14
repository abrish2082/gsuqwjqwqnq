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
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author mora
 */
@Entity
@Table(name = "FMS_LU_BOND_TYPE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FmsLuBondType.findAll", query = "SELECT f FROM FmsLuBondType f"),
    @NamedQuery(name = "FmsLuBondType.findByName", query = "SELECT f FROM FmsLuBondType f WHERE f.name = :name"),
    @NamedQuery(name = "FmsLuBondType.findByNameLike", query = "SELECT f FROM FmsLuBondType f WHERE f.name LIKE :name"),
    @NamedQuery(name = "FmsLuBondType.findByDscribtion", query = "SELECT f FROM FmsLuBondType f WHERE f.dscribtion = :dscribtion")})
public class FmsLuBondType implements Serializable {

    //<editor-fold defaultstate="collapsed" desc="variable declaration">
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "BondName")
    private List<FmsBondForeign> fmsBondForeignList;
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "NAME")
    private String name;
    @Size(max = 20)
    @Column(name = "DSCRIBTION")
    private String dscribtion;
//</editor-fold>

    public FmsLuBondType() {
    }
//<editor-fold defaultstate="collapsed" desc="getter and setter">

    public FmsLuBondType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDscribtion() {
        return dscribtion;
    }

    public void setDscribtion(String dscribtion) {
        this.dscribtion = dscribtion;
    }
//</editor-fold>

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (name != null ? name.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof FmsLuBondType)) {
            return false;
        }
        FmsLuBondType other = (FmsLuBondType) object;
        if ((this.name == null && other.name != null) || (this.name != null && !this.name.equals(other.name))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return name;
    }

    @XmlTransient
    public List<FmsBondForeign> getFmsBondForeignList() {
        return fmsBondForeignList;
    }

    public void setFmsBondForeignList(List<FmsBondForeign> fmsBondForeignList) {
        this.fmsBondForeignList = fmsBondForeignList;
    }

}
