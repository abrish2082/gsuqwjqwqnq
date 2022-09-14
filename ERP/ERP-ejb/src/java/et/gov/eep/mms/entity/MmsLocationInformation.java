/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.mms.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
 * @author kimmyo
 */
@Entity
@Table(name = "MMS_LOCATION_INFORMATION")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MmsLocationInformation.findAll", query = "SELECT p FROM MmsLocationInformation p"),
    @NamedQuery(name = "MmsLocationInformation.findByLocationId", query = "SELECT p FROM MmsLocationInformation p WHERE p.locationId = :locationId"),
    @NamedQuery(name = "MmsLocationInformation.findByDescription", query = "SELECT p FROM MmsLocationInformation p WHERE p.description = :description"),
    @NamedQuery(name = "MmsLocationInformation.SearchByStoreNameAndShelfNo", query = " SELECT p FROM MmsLocationInformation p  JOIN p.storeId s WHERE UPPER(p.shelfNo) LIKE :shelfNo AND s.storeName=:storeName"),
    @NamedQuery(name = "MmsLocationInformation.SearchForDuplication", query = " SELECT p FROM MmsLocationInformation p  JOIN p.storeId s WHERE UPPER(p.shelfNo) LIKE :shelfNo AND s.storeName=:storeName"),

    @NamedQuery(name = "MmsLocationInformation.findByShelfNo", query = "SELECT p FROM MmsLocationInformation p WHERE p.shelfNo = :shelfNo")})
public class MmsLocationInformation implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PAPMS_LOC_INFO_SEQ")
    @SequenceGenerator(name = "PAPMS_LOC_INFO_SEQ", sequenceName = "PAPMS_LOC_INFO_SEQ", allocationSize = 1)
    @Column(name = "LOCATION_ID", nullable = false)
    private Integer locationId;
    @Size(max = 255)
    @Column(name = "DESCRIPTION", length = 255)
    private String description;
    @Size(max = 255)
    @Column(name = "SHELF_NO", length = 255)
    private String shelfNo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "locationId", fetch = FetchType.LAZY)
    private List<MmsLocationInfoDetail> papmsLocationInfoDetailList;
    
    @JoinColumn(name = "STORE_ID", referencedColumnName = "STORE_ID")
    @ManyToOne
    private MmsStoreInformation storeId;

    /**
     *
     */
    public MmsLocationInformation() {
    }

    /**
     *
     * @param locationId
     */
    public MmsLocationInformation(Integer locationId) {
        this.locationId = locationId;
    }

    /**
     *
     * @return
     */
    public Integer getLocationId() {
        return locationId;
    }

    /**
     *
     * @param locationId
     */
    public void setLocationId(Integer locationId) {
        this.locationId = locationId;
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
    public String getShelfNo() {
        return shelfNo;
    }

    /**
     *
     * @param shelfNo
     */
    public void setShelfNo(String shelfNo) {
        this.shelfNo = shelfNo;
    }

    /**
     *
     * @return
     */
    @XmlTransient
    public List<MmsLocationInfoDetail> getPapmsLocationInfoDetailList() {
        if(papmsLocationInfoDetailList==null){
            papmsLocationInfoDetailList=new ArrayList<>();
        }
        return papmsLocationInfoDetailList;
    }

    /**
     *
     * @param papmsLocationInfoDetailList
     */
    public void setPapmsLocationInfoDetailList(List<MmsLocationInfoDetail> papmsLocationInfoDetailList) {
        this.papmsLocationInfoDetailList = papmsLocationInfoDetailList;
    }

    /**
     *
     * @return
     */
    public MmsStoreInformation getStoreId() {
        return storeId;
    }

    /**
     *
     * @param storeId
     */
    public void setStoreId(MmsStoreInformation storeId) {
        this.storeId = storeId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (locationId != null ? locationId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MmsLocationInformation)) {
            return false;
        }
        MmsLocationInformation other = (MmsLocationInformation) object;
        if ((this.locationId == null && other.locationId != null) || (this.locationId != null && !this.locationId.equals(other.locationId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return shelfNo;
    }
    /**
     * add PapmsLocationInfoDetail
     *
     * @param MmsLocationInfoDetail
     */
    public void addToLocationInfoDetail(MmsLocationInfoDetail MmsLocationInfoDetail) {
        if (MmsLocationInfoDetail.getLocationId() != this) {
            this.getPapmsLocationInfoDetailList().add(MmsLocationInfoDetail);
            MmsLocationInfoDetail.setLocationId(this);
        }
    }
}
