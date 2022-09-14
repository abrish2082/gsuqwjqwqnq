/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.commonApplications.entity;

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
 * @author insa
 */
@Entity
@Table(name = "LU_MENU_CATAGORY")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "LuMenuCatagory.findAll", query = "SELECT l FROM LuMenuCatagory l"),
    @NamedQuery(name = "LuMenuCatagory.findById", query = "SELECT l FROM LuMenuCatagory l WHERE l.id = :id"),
    @NamedQuery(name = "LuMenuCatagory.findByCatacoryName", query = "SELECT l FROM LuMenuCatagory l WHERE l.catacoryName = :catacoryName"),
    @NamedQuery(name = "LuMenuCatagory.findByDescription", query = "SELECT l FROM LuMenuCatagory l WHERE l.description = :description")})
public class LuMenuCatagory implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "LU_MENU_CATAGORY_SEQ")
    @SequenceGenerator(name = "LU_MENU_CATAGORY_SEQ", sequenceName = "LU_MENU_CATAGORY_SEQ", allocationSize = 1)
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private BigDecimal id;
    @Size(max = 20)
    @Column(name = "CATACORY_NAME")
    private String catacoryName;
    @Size(max = 20)
    @Column(name = "DESCRIPTION")
    private String description;
    @OneToMany(mappedBy = "menuCatagoryId")
    private List<MenuItems> menuItemsList;

    public LuMenuCatagory() {
    }

    public LuMenuCatagory(BigDecimal id) {
        this.id = id;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getCatacoryName() {
        return catacoryName;
    }

    public void setCatacoryName(String catacoryName) {
        this.catacoryName = catacoryName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @XmlTransient
    public List<MenuItems> getMenuItemsList() {
        return menuItemsList;
    }

    public void setMenuItemsList(List<MenuItems> menuItemsList) {
        this.menuItemsList = menuItemsList;
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
        if (!(object instanceof LuMenuCatagory)) {
            return false;
        }
        LuMenuCatagory other = (LuMenuCatagory) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.eep.commonApplications.entity.LuMenuCatagory[ id=" + id + " ]";
    }

}
