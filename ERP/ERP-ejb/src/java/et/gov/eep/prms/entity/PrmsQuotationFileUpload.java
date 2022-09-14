/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.entity;

import java.io.Serializable;
import java.math.BigDecimal;
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
 * @author User
 */
@Entity
@Table(name = "PRMS_QUOTATION_FILE_UPLOAD")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PrmsQuotationFileUpload.findAll", query = "SELECT p FROM PrmsQuotationFileUpload p"),
    @NamedQuery(name = "PrmsQuotationFileUpload.findById", query = "SELECT p FROM PrmsQuotationFileUpload p WHERE p.id = :id"),
    @NamedQuery(name = "PrmsQuotationFileUpload.findByDocumentId", query = "SELECT p FROM PrmsQuotationFileUpload p WHERE p.documentId = :documentId")})
public class PrmsQuotationFileUpload implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PRMS_QUOTATION_FILE_UPLOAD_SEQ")
    @SequenceGenerator(name = "PRMS_QUOTATION_FILE_UPLOAD_SEQ", sequenceName = "PRMS_QUOTATION_FILE_UPLOAD_SEQ", allocationSize = 1)
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private BigDecimal id;
    @Column(name = "DOCUMENT_ID")
    private Integer documentId;
    @JoinColumn(name = "QUOTATION_ID", referencedColumnName = "QUATATION_ID")
    @ManyToOne
    private PrmsQuotation quotationId;

    public PrmsQuotationFileUpload() {
    }

    public PrmsQuotationFileUpload(BigDecimal id) {
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

    public PrmsQuotation getQuotationId() {
        return quotationId;
    }

    public void setQuotationId(PrmsQuotation quotationId) {
        this.quotationId = quotationId;
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
        if (!(object instanceof PrmsQuotationFileUpload)) {
            return false;
        }
        PrmsQuotationFileUpload other = (PrmsQuotationFileUpload) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ess.PrmsQuotationFileUpload[ id=" + id + " ]";
    }

}
