/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.commonApplications.entity;

//import et.gov.insa.epse.spras.entity.SprasDeliveryNote;
//import et.gov.insa.epse.spras.entity.SprasReceiptNote;
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
 * @author Sahele
 */
@Entity
@Table(name = "com_lu_product_type")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ComLuProductType.findAll", query = "SELECT c FROM ComLuProductType c"),
    @NamedQuery(name = "ComLuProductType.findByProductTypeId", query = "SELECT c FROM ComLuProductType c WHERE c.productTypeId = :productTypeId"),
    @NamedQuery(name = "ComLuProductType.findByName", query = "SELECT c FROM ComLuProductType c WHERE c.name = :name"),
    @NamedQuery(name = "ComLuProductType.findByDescription", query = "SELECT c FROM ComLuProductType c WHERE c.description = :description")})
public class ComLuProductType implements Serializable {
    
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "product_type_id")
    private Integer productTypeId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "name")
    private String name;
    @Size(max = 200)
    @Column(name = "description")
    private String description;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "productTypeId")
    private List<ComLuProduct> comLuProductList;
    
//    @OneToMany( mappedBy = "productTypeId")
//    private List<SprasDeliveryNote> sprasDeliveryNoteList;
//    @OneToMany(mappedBy = "productTypeId")
//    private List<SprasReceiptNote> sprasReceiptNoteList;

    /**
     *
     */
    
    public ComLuProductType() {
    }

    /**
     *
     * @param productTypeId
     */
    public ComLuProductType(Integer productTypeId) {
        this.productTypeId = productTypeId;
    }

    /**
     *
     * @param productTypeId
     * @param name
     */
    public ComLuProductType(Integer productTypeId, String name) {
        this.productTypeId = productTypeId;
        this.name = name;
    }

    /**
     *
     * @return
     */
    public Integer getProductTypeId() {
        return productTypeId;
    }

    /**
     *
     * @param productTypeId
     */
    public void setProductTypeId(Integer productTypeId) {
        this.productTypeId = productTypeId;
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
    @XmlTransient
    public List<ComLuProduct> getComLuProductList() {
        return comLuProductList;
    }

    /**
     *
     * @param comLuProductList
     */
    public void setComLuProductList(List<ComLuProduct> comLuProductList) {
        this.comLuProductList = comLuProductList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (productTypeId != null ? productTypeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ComLuProductType)) {
            return false;
        }
        ComLuProductType other = (ComLuProductType) object;
        if ((this.productTypeId == null && other.productTypeId != null) || (this.productTypeId != null && !this.productTypeId.equals(other.productTypeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return name;
    }

//    @XmlTransient
//    public List<SprasReceiptNote> getSprasReceiptNoteList() {
//        return sprasReceiptNoteList;
//    }
//
//    public void setSprasReceiptNoteList(List<SprasReceiptNote> sprasReceiptNoteList) {
//        this.sprasReceiptNoteList = sprasReceiptNoteList;
//    }
//
//    @XmlTransient
//    public List<SprasDeliveryNote> getSprasDeliveryNoteList() {
//        return sprasDeliveryNoteList;
//    }
//
//    public void setSprasDeliveryNoteList(List<SprasDeliveryNote> sprasDeliveryNoteList) {
//        this.sprasDeliveryNoteList = sprasDeliveryNoteList;
//    }
    
}
