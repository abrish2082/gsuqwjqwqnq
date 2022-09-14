
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.commonApplications.entity;

import java.io.Serializable;
import java.math.BigDecimal;
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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author insa
 */
@Entity
@Table(name = "MENU_ITEMS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MenuItems.findAll", query = "SELECT m FROM MenuItems m"),
    @NamedQuery(name = "MenuItems.findById", query = "SELECT m FROM MenuItems m WHERE m.id = :id"),
    @NamedQuery(name = "MenuItems.findByMenuName", query = "SELECT m FROM MenuItems m WHERE m.menuName = :menuName"),
    @NamedQuery(name = "MenuItems.findByResourceName", query = "SELECT m FROM MenuItems m WHERE m.resourceName = :resourceName"),
    @NamedQuery(name = "MenuItems.findByResourcePath", query = "SELECT m FROM MenuItems m WHERE m.resourcePath = :resourcePath"),
    @NamedQuery(name = "MenuItems.findByDescription", query = "SELECT m FROM MenuItems m WHERE m.description = :description")})
public class MenuItems implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MENU_ITEMS_SEQ")
    @SequenceGenerator(name = "MENU_ITEMS_SEQ", sequenceName = "MENU_ITEMS_SEQ", allocationSize = 1)
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private BigDecimal id;
    @Size(max = 50)
    @Column(name = "MENU_NAME")
    private String menuName;
    @Size(max = 50)
    @Column(name = "RESOURCE_NAME")
    private String resourceName;
    @Size(max = 255)
    @Column(name = "RESOURCE_PATH")
    private String resourcePath;
    @Size(max = 50)
    @Column(name = "DESCRIPTION")
    private String description;
    @JoinColumn(name = "MENU_CATAGORY_ID", referencedColumnName = "ID")
    @ManyToOne
    private LuMenuCatagory menuCatagoryId;

    public MenuItems() {
    }

    public MenuItems(BigDecimal id) {
        this.id = id;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public String getResourcePath() {
        return resourcePath;
    }

    public void setResourcePath(String resourcePath) {
        this.resourcePath = resourcePath;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LuMenuCatagory getMenuCatagoryId() {
        return menuCatagoryId;
    }

    public void setMenuCatagoryId(LuMenuCatagory menuCatagoryId) {
        this.menuCatagoryId = menuCatagoryId;
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
        if (!(object instanceof MenuItems)) {
            return false;
        }
        MenuItems other = (MenuItems) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.eep.commonApplications.entity.MenuItems[ id=" + id + " ]";
    }

}
