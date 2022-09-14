/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.ifrs.entity;

import et.gov.eep.mms.entity.IfrsFixedAsset;
import java.io.Serializable;
import java.util.List;
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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author user
 */
@Entity
@Table(name = "IFRS_DEPRECIATION_SETUP")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "IfrsDepreciationSetup.findAll", query = "SELECT i FROM IfrsDepreciationSetup i"),
    @NamedQuery(name = "IfrsDepreciationSetup.findById", query = "SELECT i FROM IfrsDepreciationSetup i WHERE i.id = :id"),
    @NamedQuery(name = "IfrsDepreciationSetup.findAllFixedAssets", query = "SELECT i FROM IfrsDepreciationSetup i WHERE i.status = 'Fixed Asset'"),
    @NamedQuery(name = "IfrsDepreciationSetup.findAllStockAssets", query = "SELECT i FROM IfrsDepreciationSetup i WHERE i.status = 'Stock Managment'"),
    @NamedQuery(name = "IfrsDepreciationSetup.findByDepreciationType", query = "SELECT i FROM IfrsDepreciationSetup i WHERE i.depreciationType = :depreciationType"),
    @NamedQuery(name = "IfrsDepreciationSetup.findByStatus", query = "SELECT i FROM IfrsDepreciationSetup i WHERE i.status = :status"),
    @NamedQuery(name = "IfrsDepreciationSetup.findByGroupLevel", query = "SELECT i FROM IfrsDepreciationSetup i WHERE i.groupLevel = :groupLevel")})
public class IfrsDepreciationSetup implements Serializable {
    @Column(name = "GROUP_LEVEL")
    private Integer groupLevel;
    
    @OneToMany(mappedBy = "fagId",cascade = CascadeType.ALL)
    private List<IfrsFixedAsset> ifrsFixedAssetList;

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
//    @Basic(optional = false)
//    @NotNull
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "IFRS_DEPRECIATION_SETUP_SEQ")
    @SequenceGenerator(name = "IFRS_DEPRECIATION_SETUP_SEQ", sequenceName = "IFRS_DEPRECIATION_SETUP_SEQ", allocationSize = 1)
    @Column(name = "ID")
    private Integer id;
    @Size(max = 30)
    @Column(name = "DEPRECIATION_TYPE")
    private String depreciationType;
    @Size(max = 20)
    @Column(name = "STATUS")
    private String status;

    public IfrsDepreciationSetup() {
    }

    public IfrsDepreciationSetup(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDepreciationType() {
        return depreciationType;
    }

    public void setDepreciationType(String depreciationType) {
        this.depreciationType = depreciationType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getGroupLevel() {
        return groupLevel;
    }

    public void setGroupLevel(Integer groupLevel) {
        this.groupLevel = groupLevel;
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
        if (!(object instanceof IfrsDepreciationSetup)) {
            return false;
        }
        IfrsDepreciationSetup other = (IfrsDepreciationSetup) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return depreciationType;
    }

   

    @XmlTransient
    public List<IfrsFixedAsset> getIfrsFixedAssetList() {
        return ifrsFixedAssetList;
    }

    public void setIfrsFixedAssetList(List<IfrsFixedAsset> ifrsFixedAssetList) {
        this.ifrsFixedAssetList = ifrsFixedAssetList;
    }
    
}
