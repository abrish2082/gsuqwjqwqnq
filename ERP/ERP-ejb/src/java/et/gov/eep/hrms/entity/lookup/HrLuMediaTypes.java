/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.entity.lookup;

import et.gov.eep.hrms.entity.recruitment.HrMedias;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.Basic;
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
@Table(name = "HR_LU_MEDIA_TYPES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HrLuMediaTypes.findAll", query = "SELECT h FROM HrLuMediaTypes h"),
    @NamedQuery(name = "HrLuMediaTypes.findById", query = "SELECT h FROM HrLuMediaTypes h WHERE h.id = :id"),
    @NamedQuery(name = "HrLuMediaTypes.findByMediaType", query = "SELECT h FROM HrLuMediaTypes h WHERE h.mediaType = :mediaType"),
    @NamedQuery(name = "HrLuMediaTypes.findMediaTypeLike", query = "SELECT h FROM HrLuMediaTypes h WHERE UPPER(h.mediaType) LIKE :mediaType"),
    @NamedQuery(name = "HrLuMediaTypes.findByDescription", query = "SELECT h FROM HrLuMediaTypes h WHERE h.description = :description")})
public class HrLuMediaTypes implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HR_LU_MEDIA_TYPES_SEQ")
    @SequenceGenerator(name = "HR_LU_MEDIA_TYPES_SEQ", sequenceName = "HR_LU_MEDIA_TYPES_SEQ", allocationSize = 1)
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private BigDecimal id;
    @Size(max = 50)
    @Column(name = "MEDIA_TYPE")
    private String mediaType;
    @Size(max = 200)
    @Column(name = "DESCRIPTION")
    private String description;
    @OneToMany(mappedBy = "mediaTypeId")
    private List<HrMedias> hrMediasList;

    /**
     *
     */
    public HrLuMediaTypes() {
    }

    /**
     *
     * @param id
     */
    public HrLuMediaTypes(BigDecimal id) {
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
    public String getMediaType() {
        return mediaType;
    }

    /**
     *
     * @param mediaType
     */
    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
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
    public List<HrMedias> getHrMediasList() {
        return hrMediasList;
    }

    /**
     *
     * @param hrMediasList
     */
    public void setHrMediasList(List<HrMedias> hrMediasList) {
        this.hrMediasList = hrMediasList;
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
        if (!(object instanceof HrLuMediaTypes)) {
            return false;
        }
        HrLuMediaTypes other = (HrLuMediaTypes) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return mediaType;
    }

}
