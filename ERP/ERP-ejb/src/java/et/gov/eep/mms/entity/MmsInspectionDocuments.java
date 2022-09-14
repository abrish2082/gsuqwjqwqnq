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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Sadik
 */
@Entity
@Table(name = "MMS_INSPECTION_DOCUMENTS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MmsInspectionDoruments.findAll", query = "SELECT m FROM MmsInspectionDocuments m"),
    @NamedQuery(name = "MmsInspectionDoruments.findById", query = "SELECT m FROM MmsInspectionDocuments m WHERE m.id = :id"),
    @NamedQuery(name = "MmsInspectionDoruments.findByDocumentId", query = "SELECT m FROM MmsInspectionDocuments m WHERE m.documentId = :documentId")})
public class MmsInspectionDocuments implements Serializable {
  private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MMS_INSPECTION_DOC_SEQ")
    @SequenceGenerator(name = "MMS_INSPECTION_DOC_SEQ", sequenceName = "MMS_INSPECTION_DOC_SEQ", allocationSize = 1)
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private Integer id;
    @Column(name = "DOCUMENT_ID")
    private Integer documentId;
    @JoinColumn(name = "INSPECTION_ID", referencedColumnName = "INSPECTION_ID")
    @ManyToOne
    private MmsInspection inspectionId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDocumentId() {
        return documentId;
    }

    public void setDocumentId(Integer documentId) {
        this.documentId = documentId;
    }

    public MmsInspection getInspectionId() {
        return inspectionId;
    }

    public void setInspectionId(MmsInspection inspectionId) {
        this.inspectionId = inspectionId;
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
        if (!(object instanceof MmsInspectionDocuments)) {
            return false;
        }
        MmsInspectionDocuments other = (MmsInspectionDocuments) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.eep.mms.entity.MmsInspectionDocuments[ id=" + id + " ]";
    }
    
}
