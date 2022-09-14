/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.mms.entity;

import et.gov.eep.prms.entity.PrmsContainerAgreement;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
 * @author Sadik
 */
@Entity
@Table(name = "MMS_LU_WARE_HOUSE", catalog = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MmsLuWareHouse.findAll", query = "SELECT m FROM MmsLuWareHouse m"),
    @NamedQuery(name = "MmsLuWareHouse.findById", query = "SELECT m FROM MmsLuWareHouse m WHERE m.id = :id"),
    @NamedQuery(name = "MmsLuWareHouse.findByName", query = "SELECT m FROM MmsLuWareHouse m WHERE m.name = :name"),
    @NamedQuery(name = "MmsLuWareHouse.findByDescription", query = "SELECT m FROM MmsLuWareHouse m WHERE m.description = :description")})
public class MmsLuWareHouse implements Serializable {

    @OneToMany(mappedBy = "warehouseId", fetch = FetchType.LAZY)
    private Collection<MmsStoreInformation> mmsStoreInformationCollection;
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MMS_LU_WAREHOUSE")
    @SequenceGenerator(name = "MMS_LU_WAREHOUSE", sequenceName = "MMS_LU_WAREHOUSE", allocationSize = 1)
    @Column(name = "ID", nullable = false, precision = 0, scale = -127)
    private Integer id;
    @Size(max = 45)
    @Column(name = "NAME", length = 45)
    private String name;
    @Size(max = 100)
    @Column(name = "DESCRIPTION", length = 100)
    private String description;
    @OneToMany(mappedBy = "warehouseId")
    private List<MmsStoreToHrDepMapper> mmsStoreToHrDepMapperList;
    @OneToMany(mappedBy = "warehouseLocation", fetch = FetchType.LAZY)
    private List<PrmsContainerAgreement> prmsContainerAgreementList;

    public MmsLuWareHouse() {
    }

    public MmsLuWareHouse(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @XmlTransient
    public List<MmsStoreToHrDepMapper> getMmsStoreToHrDepMapperList() {
        return mmsStoreToHrDepMapperList;
    }

    public void setMmsStoreToHrDepMapperList(List<MmsStoreToHrDepMapper> mmsStoreToHrDepMapperList) {
        this.mmsStoreToHrDepMapperList = mmsStoreToHrDepMapperList;
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
        if (!(object instanceof MmsLuWareHouse)) {
            return false;
        }
        MmsLuWareHouse other = (MmsLuWareHouse) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return name;
    }

    @XmlTransient
    public Collection<MmsStoreInformation> getMmsStoreInformationCollection() {
        return mmsStoreInformationCollection;
    }

    public void setMmsStoreInformationCollection(Collection<MmsStoreInformation> mmsStoreInformationCollection) {
        this.mmsStoreInformationCollection = mmsStoreInformationCollection;
    }

    @XmlTransient
    public List<PrmsContainerAgreement> getPrmsContainerAgreementList() {
        return prmsContainerAgreementList;
    }

    public void setPrmsContainerAgreementList(List<PrmsContainerAgreement> prmsContainerAgreementList) {
        this.prmsContainerAgreementList = prmsContainerAgreementList;
    }

}
