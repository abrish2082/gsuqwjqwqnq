/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.entity;

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
 * @author user
 */
@Entity
@Table(name = "PRMS_TECHNICAL_FILE_UPLOAD")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PrmsTechnicalFileUpload.findAll", query = "SELECT p FROM PrmsTechnicalFileUpload p"),
    @NamedQuery(name = "PrmsTechnicalFileUpload.findById", query = "SELECT p FROM PrmsTechnicalFileUpload p WHERE p.id = :id"),
    @NamedQuery(name = "PrmsTechnicalFileUpload.findByDocumentId", query = "SELECT p FROM PrmsTechnicalFileUpload p WHERE p.documentId = :documentId")})
public class PrmsTechnicalFileUpload implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)

    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PRMS_THECH_EVAL_FILEUPLOAD_SEQ")
    @SequenceGenerator(name = "PRMS_THECH_EVAL_FILEUPLOAD_SEQ", sequenceName = "PRMS_THECH_EVAL_FILEUPLOAD_SEQ", allocationSize = 1)
    @Column(name = "ID")
    private BigDecimal id;
    @Column(name = "DOCUMENT_ID")
    private Integer documentId;
    @JoinColumn(name = "TECH_EV_ID", referencedColumnName = "EVALUATION_ID")
    @ManyToOne
    private PrmsThechnicalEvaluation techEvId;

    public PrmsTechnicalFileUpload() {
    }

    public PrmsTechnicalFileUpload(BigDecimal id) {
        this.id = id;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public Integer getDocumentId() {
        return documentId;
    }

    public void setDocumentId(Integer documentId) {
        this.documentId = documentId;
    }

    public PrmsThechnicalEvaluation getTechEvId() {
        return techEvId;
    }

    public void setTechEvId(PrmsThechnicalEvaluation techEvId) {
        this.techEvId = techEvId;
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
        if (!(object instanceof PrmsTechnicalFileUpload)) {
            return false;
        }
        PrmsTechnicalFileUpload other = (PrmsTechnicalFileUpload) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.eep.prms.entity.PrmsTechnicalFileUpload[ id=" + id + " ]";
    }

}
