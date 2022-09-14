/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.entity.termination;

import et.gov.eep.commonApplications.entity.HrLuDmArchive;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author insa
 */
@Entity
@Table(name = "HR_TERMINATION_REQUEST_UPLOAD")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HrTerminationRequestUpload.findAll", query = "SELECT h FROM HrTerminationRequestUpload h"),
    @NamedQuery(name = "HrTerminationRequestUpload.findById", query = "SELECT h FROM HrTerminationRequestUpload h WHERE h.id = :id")
//    @NamedQuery(name = "HrTerminationRequestUpload.findByDocumentId", query = "SELECT h FROM HrTerminationRequestUpload h WHERE h.documentId = :documentId")
})
public class HrTerminationRequestUpload implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HR_TERMINATION_REQ_UPLOAD_SEQ")
    @SequenceGenerator(name = "HR_TERMINATION_REQ_UPLOAD_SEQ", sequenceName = "HR_TERMINATION_REQ_UPLOAD_SEQ", allocationSize = 1)
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private BigDecimal id;
    @JoinColumn(name = "DOCUMENT_ID", referencedColumnName = "DOCUMENT_ID")
    @ManyToOne
    private HrLuDmArchive documentId;
    @JoinColumn(name = "TERMINATION_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrTerminationRequests terminationId;

    public HrTerminationRequestUpload() {
    }

    public HrTerminationRequestUpload(BigDecimal id) {
        this.id = id;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public HrLuDmArchive getDocumentId() {
        return documentId;
    }

    public void setDocumentId(HrLuDmArchive documentId) {
        this.documentId = documentId;
    }

    public HrTerminationRequests getTerminationId() {
        return terminationId;
    }

    public void setTerminationId(HrTerminationRequests terminationId) {
        this.terminationId = terminationId;
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
        if (!(object instanceof HrTerminationRequestUpload)) {
            return false;
        }
        HrTerminationRequestUpload other = (HrTerminationRequestUpload) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.eep.hrms.entity.termination.HrTerminationRequestUpload[ id=" + id + " ]";
    }

}
