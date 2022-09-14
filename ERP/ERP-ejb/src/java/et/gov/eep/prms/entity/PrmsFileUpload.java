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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author user
 */
@Entity
@Table(name = "PRMS_FILE_UPLOAD")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PrmsFileUpload.findAll", query = "SELECT p FROM PrmsFileUpload p"),
    @NamedQuery(name = "PrmsFileUpload.findById", query = "SELECT p FROM PrmsFileUpload p WHERE p.id = :id"),
    @NamedQuery(name = "PrmsFileUpload.findByDocumentId", query = "SELECT p FROM PrmsFileUpload p WHERE p.documentId = :documentId")})
public class PrmsFileUpload implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PRMS_FILE_UPLOAD_SEQ")
    @SequenceGenerator(name = "PRMS_FILE_UPLOAD_SEQ", sequenceName = "PRMS_FILE_UPLOAD_SEQ", allocationSize = 1)
    @Column(name = "ID")
    private BigDecimal id;
    @Size(max = 20)
    @Column(name = "DOCUMENT_ID")
    private Integer documentId;
    
    @JoinColumn(name = "LC_ID", referencedColumnName = "LC_ID")
    @ManyToOne
    private PrmsLcRigistration lcId;
    @JoinColumn(name = "LC_AMEND_ID", referencedColumnName = "ID")
    @ManyToOne
    private PrmsLcRigistrationAmend lcAmendId;
    @JoinColumn(name = "GOODS_ID", referencedColumnName = "ID")
    @ManyToOne
    private PrmsGoodsEntrance goodsId;

    public PrmsFileUpload() {
    }

    public PrmsFileUpload(BigDecimal id) {
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

    public PrmsLcRigistration getLcId() {
        return lcId;
    }

    public void setLcId(PrmsLcRigistration lcId) {
        this.lcId = lcId;
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
        if (!(object instanceof PrmsFileUpload)) {
            return false;
        }
        PrmsFileUpload other = (PrmsFileUpload) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.eep.prms.entity.PrmsFileUpload[ id=" + id + " ]";
    }

    /**
     * @return the lcAmendId
     */
    public PrmsLcRigistrationAmend getLcAmendId() {
        return lcAmendId;
    }

    /**
     * @param lcAmendId the lcAmendId to set
     */
    public void setLcAmendId(PrmsLcRigistrationAmend lcAmendId) {
        this.lcAmendId = lcAmendId;
    }

    /**
     * @return the goodsId
     */
    public PrmsGoodsEntrance getGoodsId() {
        return goodsId;
    }

    /**
     * @param goodsId the goodsId to set
     */
    public void setGoodsId(PrmsGoodsEntrance goodsId) {
        this.goodsId = goodsId;
    }
    
}
