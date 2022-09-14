/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.entity.recruitment;

import et.gov.eep.hrms.entity.lookup.HrLuMediaTypes;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author user
 */
@Entity
@Table(name = "HR_MEDIAS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HrMedias.findAll", query = "SELECT h FROM HrMedias h"),
    @NamedQuery(name = "HrMedias.findById", query = "SELECT h FROM HrMedias h WHERE h.id = :id"),
    @NamedQuery(name = "HrMedias.findByOccurrence", query = "SELECT h FROM HrMedias h WHERE h.occurrence = :occurrence")})
public class HrMedias implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HR_MEDIAS_SEQ")
    @SequenceGenerator(name = "HR_MEDIAS_SEQ", sequenceName = "HR_MEDIAS_SEQ", allocationSize = 1)
    @Column(name = "ID")
    private BigDecimal id;
    @Size(max = 20)
    @Column(name = "OCCURRENCE")
    private String occurrence;
    @JoinColumn(name = "ADVERT_ID", referencedColumnName = "ID")
    @ManyToOne(cascade = CascadeType.ALL)
    private HrAdvertisements advertId;
    @JoinColumn(name = "MEDIA_TYPE_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrLuMediaTypes mediaTypeId;

    /**
     *
     */
    public HrMedias() {
    }

    /**
     *
     * @param id
     */
    public HrMedias(BigDecimal id) {
        this.id = id;
    }

    /**
     *
     * @return
     */
    public BigDecimal getId() {
        return id;
    }

    /**
     *
     * @param id
     */
    public void setId(BigDecimal id) {
        this.id = id;
    }

    /**
     *
     * @return
     */
    public String getOccurrence() {
        return occurrence;
    }

    /**
     *
     * @param occurrence
     */
    public void setOccurrence(String occurrence) {
        this.occurrence = occurrence;
    }

    /**
     *
     * @return
     */
    public HrAdvertisements getAdvertId() {
        return advertId;
    }

    /**
     *
     * @param advertId
     */
    public void setAdvertId(HrAdvertisements advertId) {
        this.advertId = advertId;
    }

    /**
     *
     * @return
     */
    public HrLuMediaTypes getMediaTypeId() {
        return mediaTypeId;
    }

    /**
     *
     * @param mediaTypeId
     */
    public void setMediaTypeId(HrLuMediaTypes mediaTypeId) {
        this.mediaTypeId = mediaTypeId;
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
        if (!(object instanceof HrMedias)) {
            return false;
        }
        HrMedias other = (HrMedias) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.HrMedias[ id=" + id + " ]";
    }

}
