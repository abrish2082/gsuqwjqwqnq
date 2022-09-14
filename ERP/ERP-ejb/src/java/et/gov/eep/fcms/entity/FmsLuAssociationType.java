/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
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
 * @author user
 */
@Entity
@Table(name = "FMS_LU_ASSOCIATION_TYPE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FmsLuAssociationType.findAll", query = "SELECT f FROM FmsLuAssociationType f"),
    @NamedQuery(name = "FmsLuAssociationType.findById", query = "SELECT f FROM FmsLuAssociationType f WHERE f.id = :id"),
    @NamedQuery(name = "FmsLuAssociationType.findByName", query = "SELECT f FROM FmsLuAssociationType f WHERE f.name = :name"),
    @NamedQuery(name = "FmsLuAssociationType.findByRate", query = "SELECT f FROM FmsLuAssociationType f WHERE f.rate = :rate")})
public class FmsLuAssociationType implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "ID")
    private String id;
    @Size(max = 20)
    @Column(name = "NAME")
    private String name;
    @Size(max = 20)
    @Column(name = "RATE")
    private String rate;
    @OneToMany(mappedBy = "associationType")
    private List<FmsCreditAssocation> fmsCreditAssocationList;

    /**
     *
     */
    public FmsLuAssociationType() {
    }

    /**
     *
     * @param id
     */
    public FmsLuAssociationType(String id) {
        this.id = id;
    }

    /**
     *
     * @return
     */
    public String getId() {
        return id;
    }

    /**
     *
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return
     */
    public String getRate() {
        return rate;
    }

    /**
     *
     * @param rate
     */
    public void setRate(String rate) {
        this.rate = rate;
    }

    /**
     *
     * @return
     */
    @XmlTransient
    public List<FmsCreditAssocation> getFmsCreditAssocationList() {
        return fmsCreditAssocationList;
    }

    /**
     *
     * @param fmsCreditAssocationList
     */
    public void setFmsCreditAssocationList(List<FmsCreditAssocation> fmsCreditAssocationList) {
        this.fmsCreditAssocationList = fmsCreditAssocationList;
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
        if (!(object instanceof FmsLuAssociationType)) {
            return false;
        }
        FmsLuAssociationType other = (FmsLuAssociationType) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return getName();
    }

}
