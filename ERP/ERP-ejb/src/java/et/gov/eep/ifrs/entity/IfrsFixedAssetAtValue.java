/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.ifrs.entity;

import et.gov.eep.mms.entity.IfrsFixedAsset;
import et.gov.eep.mms.entity.IfrsFixedAssetAttribute;
import java.io.Serializable;
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
 * @author user
 */
@Entity
@Table(name = "IFRS_FIXED_ASSET_AT_VALUE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "IfrsFixedAssetAtValue.findAll", query = "SELECT i FROM IfrsFixedAssetAtValue i"),
    @NamedQuery(name = "IfrsFixedAssetAtValue.findById", query = "SELECT i FROM IfrsFixedAssetAtValue i WHERE i.id = :id"),
    @NamedQuery(name = "IfrsFixedAssetAtValue.findByValue", query = "SELECT i FROM IfrsFixedAssetAtValue i WHERE i.valueName = :value")})
public class IfrsFixedAssetAtValue implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "IFRS_FIXED_ASSET_AT_VALUE_SEQ")
    @SequenceGenerator(name = "IFRS_FIXED_ASSET_AT_VALUE_SEQ", sequenceName = "IFRS_FIXED_ASSET_AT_VALUE_SEQ", allocationSize = 1)
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private Integer id;
    @Size(max = 50)
    @Column(name = "VALUE_NAME")
    private String valueName;

    @Column(name = "OPTIONVALUE_ID") 
    private String optionvalueId;

    @JoinColumn(name = "FA_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private IfrsFixedAsset faId;

    @JoinColumn(name = "ATTRIBUTE_ID", referencedColumnName = "ID")
    @ManyToOne
    private IfrsFixedAssetAttribute attributeId;

    public IfrsFixedAssetAtValue() {
    }

    public IfrsFixedAssetAtValue(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getValueName() {
        return valueName;
    }

    public void setValueName(String valueName) {
        this.valueName = valueName;
    }

    public IfrsFixedAsset getFaId() {
        return faId;
    }

    public void setFaId(IfrsFixedAsset faId) {
        this.faId = faId;
    }

    public IfrsFixedAssetAttribute getAttributeId() {
        return attributeId;
    }

    public void setAttributeId(IfrsFixedAssetAttribute attributeId) {
        this.attributeId = attributeId;
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
        if (!(object instanceof IfrsFixedAssetAtValue)) {
            return false;
        }
        IfrsFixedAssetAtValue other = (IfrsFixedAssetAtValue) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return valueName;
    }

    public String getOptionvalueId() {
        return optionvalueId;
    }

    public void setOptionvalueId(String optionvalueId) {
        this.optionvalueId = optionvalueId;
    }

}
