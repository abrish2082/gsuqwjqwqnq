/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.mms.entity;

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
 * @author kimmyo
 */
@Entity
@Table(name = "MMS_LOCATION_INFO_DETAIL")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MmsLocationInfoDetail.findAll", query = "SELECT p FROM MmsLocationInfoDetail p"),
    @NamedQuery(name = "MmsLocationInfoDetail.findByLocationDetId", query = "SELECT p FROM MmsLocationInfoDetail p WHERE p.locationDetId = :locationDetId"),
    @NamedQuery(name = "MmsLocationInfoDetail.findByDescription", query = "SELECT p FROM MmsLocationInfoDetail p WHERE p.description = :description"),
    @NamedQuery(name = "MmsLocationInfoDetail.findByLocationCode", query = "SELECT p FROM MmsLocationInfoDetail p WHERE p.locationCode = :locationCode"),
    @NamedQuery(name = "MmsLocationInfoDetail.findByRowNumber", query = "SELECT p FROM MmsLocationInfoDetail p WHERE p.rowNumber = :rowNumber"),
    @NamedQuery(name = "MmsLocationInfoDetail.findByMaximumCapacity", query = "SELECT p FROM MmsLocationInfoDetail p WHERE p.maximumCapacity = :maximumCapacity"),
    @NamedQuery(name = "MmsLocationInfoDetail.findByLocationName", query = "SELECT p FROM MmsLocationInfoDetail p WHERE p.locationName = :locationName")})
public class MmsLocationInfoDetail implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PAPMS_LOCATION_INFO_DETAIL_SEQ")
    @SequenceGenerator(name = "PAPMS_LOCATION_INFO_DETAIL_SEQ", sequenceName = "PAPMS_LOCATION_INFO_DETAIL_SEQ", allocationSize = 1)
    @Column(name = "LOCATION_DET_ID", nullable = false)
    private Integer locationDetId;
    @Size(max = 255)
    @Column(name = "DESCRIPTION", length = 255)
    private String description;
    @Size(max = 255)
    @Column(name = "LOCATION_CODE", length = 255)
    private String locationCode;
    @Column(name = "ROW_NUMBER")
    private Long rowNumber;
    @Column(name = "MAXIMUM_CAPACITY")
    private Long maximumCapacity;
    @Size(max = 255)
    @Column(name = "LOCATION_NAME", length = 255)
    private String locationName;
    @JoinColumn(name = "LOCATION_ID", referencedColumnName = "LOCATION_ID")
    @ManyToOne
    private MmsLocationInformation locationId;

    /**
     *
     */
    public MmsLocationInfoDetail() {
    }

    /**
     *
     * @param locationDetId
     */
    public MmsLocationInfoDetail(Integer locationDetId) {
        this.locationDetId = locationDetId;
    }

    /**
     *
     * @return
     */
    public Integer getLocationDetId() {
        return locationDetId;
    }

    /**
     *
     * @param locationDetId
     */
    public void setLocationDetId(Integer locationDetId) {
        this.locationDetId = locationDetId;
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
    public String getLocationCode() {
        return locationCode;
    }

    /**
     *
     * @param locationCode
     */
    public void setLocationCode(String locationCode) {
        this.locationCode = locationCode;
    }

    /**
     *
     * @return
     */
    public Long getRowNumber() {
        return rowNumber;
    }

    /**
     *
     * @param rowNumber
     */
    public void setRowNumber(Long rowNumber) {
        this.rowNumber = rowNumber;
    }

    /**
     *
     * @return
     */
    public Long getMaximumCapacity() {
        return maximumCapacity;
    }

    /**
     *
     * @param maximumCapacity
     */
    public void setMaximumCapacity(Long maximumCapacity) {
        this.maximumCapacity = maximumCapacity;
    }

    /**
     *
     * @return
     */
    public String getLocationName() {
        return locationName;
    }

    /**
     *
     * @param locationName
     */
    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    /**
     *
     * @return
     */
    public MmsLocationInformation getLocationId() {
        return locationId;
    }

    /**
     *
     * @param locationId
     */
    public void setLocationId(MmsLocationInformation locationId) {
        this.locationId = locationId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (locationDetId != null ? locationDetId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MmsLocationInfoDetail)) {
            return false;
        }
        MmsLocationInfoDetail other = (MmsLocationInfoDetail) object;
        if ((this.locationDetId == null && other.locationDetId != null) || (this.locationDetId != null && !this.locationDetId.equals(other.locationDetId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.insa.eep.erp.pamps.entity.MmsLocationInfoDetail[ locationDetId=" + locationDetId + " ]";
    }
    
    
}
