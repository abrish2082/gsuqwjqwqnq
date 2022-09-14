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
@Table(name = "PRMS_AWARD_FILE_UPLOAD")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PrmsAwardFileUpload.findAll", query = "SELECT p FROM PrmsAwardFileUpload p"),
    @NamedQuery(name = "PrmsAwardFileUpload.findById", query = "SELECT p FROM PrmsAwardFileUpload p WHERE p.id = :id"),
    @NamedQuery(name = "PrmsAwardFileUpload.findByDocumentId", query = "SELECT p FROM PrmsAwardFileUpload p WHERE p.documentId = :documentId")})
public class PrmsAwardFileUpload implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PRMS_AWARD_FILE_UPLO")
    @SequenceGenerator(name = "PRMS_AWARD_FILE_UPLO", sequenceName = "PRMS_AWARD_FILE_UPLO", allocationSize = 1)
    @Column(name = "ID")
    private BigDecimal id;
   @Column(name = "DOCUMENT_ID")
    private Integer documentId;
    
  @JoinColumn(name = "AWARD_ID", referencedColumnName = "AWARD_ID")
    @ManyToOne
    private PrmsAward awardId;

    public PrmsAwardFileUpload() {
    }

    public PrmsAwardFileUpload(BigDecimal id) {
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

    

    public PrmsAward getAwardId() {
        return awardId;
    }

    public void setAwardId(PrmsAward awardId) {
        this.awardId = awardId;
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
        if (!(object instanceof PrmsAwardFileUpload)) {
            return false;
        }
        PrmsAwardFileUpload other = (PrmsAwardFileUpload) object;
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
