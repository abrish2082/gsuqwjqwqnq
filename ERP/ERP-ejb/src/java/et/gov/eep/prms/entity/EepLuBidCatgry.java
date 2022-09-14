
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author user
 */
@Entity
@Table(name = "EEP_LU_BID_CATGRY")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EepLuBidCatgry.findAll", query = "SELECT e FROM EepLuBidCatgry e"),
    @NamedQuery(name = "EepLuBidCatgry.findById", query = "SELECT e FROM EepLuBidCatgry e WHERE e.id = :id"),
    @NamedQuery(name = "EepLuBidCatgry.findByDescription", query = "SELECT e FROM EepLuBidCatgry e WHERE e.description = :description")})
public class EepLuBidCatgry implements Serializable {
   
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "ID")
    private String id;
    @Size(max = 20)
    @Column(name = "DESCRIPTION")
    private String description;
   

    /**
     *
     */
    public EepLuBidCatgry() {
    }

    /**
     *
     * @param id
     */
    public EepLuBidCatgry(String id) {
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
    public String getDescription() {
        return description;
    }

    /**
     *
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     *
     * @return
     */
  

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EepLuBidCatgry)) {
            return false;
        }
        EepLuBidCatgry other = (EepLuBidCatgry) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.eep.prms.entity.EepLuBidCatgry[ id=" + id + " ]";
    }

    /**
     *
     * @return
     */
   
    
}


