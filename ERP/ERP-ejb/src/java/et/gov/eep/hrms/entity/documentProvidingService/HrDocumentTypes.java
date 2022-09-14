/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.entity.documentProvidingService;

import java.io.Serializable;
import java.util.Collection;
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
 * @author Baya
 */
@Entity
@Table(name = "HR_LU_DOCUMENT_TYPES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HrDocumentTypes.findAll", query = "SELECT h FROM HrDocumentTypes h"),
    @NamedQuery(name = "HrDocumentTypes.findById", query = "SELECT h FROM HrDocumentTypes h WHERE h.id = :id"),
    @NamedQuery(name = "HrDocumentTypes.findByDocumentType", query = "SELECT h FROM HrDocumentTypes h WHERE h.documentType = :documentType")})
public class HrDocumentTypes implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HR_DOCUMENT_TYPES_SEQ")
    @SequenceGenerator(name = "HR_DOCUMENT_TYPES_SEQ", sequenceName = "HR_DOCUMENT_TYPES_SEQ", allocationSize = 1)
    private Integer id;
    @Size(max = 40)
    @Column(name = "DOCUMENT_TYPE")
    private String documentType;
    @OneToMany(mappedBy = "documentType")
    private Collection<HrDocumentRequests> hrDocumentRequestsCollection;

    public HrDocumentTypes() {
    }

    public HrDocumentTypes(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    @XmlTransient
    public Collection<HrDocumentRequests> getHrDocumentRequestsCollection() {
        return hrDocumentRequestsCollection;
    }

    public void setHrDocumentRequestsCollection(Collection<HrDocumentRequests> hrDocumentRequestsCollection) {
        this.hrDocumentRequestsCollection = hrDocumentRequestsCollection;
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
        if (!(object instanceof HrDocumentTypes)) {
            return false;
        }
        HrDocumentTypes other = (HrDocumentTypes) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return id.toString();
    }
    
}
