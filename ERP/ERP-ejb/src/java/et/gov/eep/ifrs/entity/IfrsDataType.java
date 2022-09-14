/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.ifrs.entity;

import et.gov.eep.mms.entity.IfrsFixedAssetAttribute;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
 * @author user
 */
@Entity
@Table(name = "IFRS_DATA_TYPE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "IfrsDataType.findAll", query = "SELECT i FROM IfrsDataType i"),
    @NamedQuery(name = "IfrsDataType.findById", query = "SELECT i FROM IfrsDataType i WHERE i.id = :id"),
    @NamedQuery(name = "IfrsDataType.findByDataTaypeName", query = "SELECT i FROM IfrsDataType i WHERE i.dataTaypeName = :dataTaypeName")})
public class IfrsDataType implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
     @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "IFRS_DATA_TYPE_SEQ")
    @SequenceGenerator(name = "IFRS_DATA_TYPE_SEQ", sequenceName = "IFRS_DATA_TYPE_SEQ", allocationSize = 1)
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private Long id;
    @Size(max = 50)
    @Column(name = "DATA_TAYPE_NAME")
    private String dataTaypeName;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "dtId")
    private List<IfrsFixedAssetAttribute> ifrsFixedAssetAttributeList;

    public IfrsDataType() {
    }

    public IfrsDataType(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDataTaypeName() {
        return dataTaypeName;
    }

    public void setDataTaypeName(String dataTaypeName) {
        this.dataTaypeName = dataTaypeName;
    }

    @XmlTransient
    public List<IfrsFixedAssetAttribute> getIfrsFixedAssetAttributeList() {
        return ifrsFixedAssetAttributeList;
    }

    public void setIfrsFixedAssetAttributeList(List<IfrsFixedAssetAttribute> ifrsFixedAssetAttributeList) {
        this.ifrsFixedAssetAttributeList = ifrsFixedAssetAttributeList;
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
        if (!(object instanceof IfrsDataType)) {
            return false;
        }
        IfrsDataType other = (IfrsDataType) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return dataTaypeName;
    }
    
}
