/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.commonApplications.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Sahele
 */
@Entity
@Table(name = "com_lu_product")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ComLuProduct.findAll", query = "SELECT c FROM ComLuProduct c"),
    @NamedQuery(name = "ComLuProduct.findByProductId", query = "SELECT c FROM ComLuProduct c WHERE c.productId = :productId"),
    @NamedQuery(name = "ComLuProduct.findByName", query = "SELECT c FROM ComLuProduct c WHERE c.name = :name"),
    @NamedQuery(name = "ComLuProduct.findByDescription", query = "SELECT c FROM ComLuProduct c WHERE c.description = :description"),
    @NamedQuery(name = "ComLuProduct.findByCode", query = "SELECT c FROM ComLuProduct c WHERE c.code = :code")})
public class ComLuProduct implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "product_id")
    private Integer productId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "name")
    private String name;
    @Size(max = 200)
    @Column(name = "description")
    private String description;
    @Size(max = 20)
    @Column(name = "code")
    private String code;
    @JoinColumn(name = "product_type_id", referencedColumnName = "product_type_id")
    @ManyToOne(optional = false)
    private ComLuProductType productTypeId;

    /**
     *
     */
    public ComLuProduct() {
    }

    /**
     *
     * @param productId
     */
    public ComLuProduct(Integer productId) {
        this.productId = productId;
    }

    /**
     *
     * @param productId
     * @param name
     */
    public ComLuProduct(Integer productId, String name) {
        this.productId = productId;
        this.name = name;
    }

    /**
     *
     * @return
     */
    public Integer getProductId() {
        return productId;
    }

    /**
     *
     * @param productId
     */
    public void setProductId(Integer productId) {
        this.productId = productId;
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
    public String getCode() {
        return code;
    }

    /**
     *
     * @param code
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     *
     * @return
     */
    public ComLuProductType getProductTypeId() {
        return productTypeId;
    }

    /**
     *
     * @param productTypeId
     */
    public void setProductTypeId(ComLuProductType productTypeId) {
        this.productTypeId = productTypeId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (productId != null ? productId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ComLuProduct)) {
            return false;
        }
        ComLuProduct other = (ComLuProduct) object;
        if ((this.productId == null && other.productId != null) || (this.productId != null && !this.productId.equals(other.productId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return name;
    }
    
}
