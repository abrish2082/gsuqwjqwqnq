/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.mms.entity;

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
 * @author w_station
 */
@Entity
@Table(name = "LOST_DMS_UPLOAD")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "LostDmsUpload.findAll", query = "SELECT l FROM LostDmsUpload l"),
    @NamedQuery(name = "LostDmsUpload.findById", query = "SELECT l FROM LostDmsUpload l WHERE l.id = :id"),
    @NamedQuery(name = "LostDmsUpload.findByDocId", query = "SELECT l FROM LostDmsUpload l WHERE l.docId = :docId")})
public class LostDmsUpload implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "LOST_DMS_UPLOAD_SEQ")
    @SequenceGenerator(name = "LOST_DMS_UPLOAD_SEQ", sequenceName = "LOST_DMS_UPLOAD_SEQ", allocationSize = 1)
  

    @Column(name = "ID", nullable = false, precision = 0, scale = -127)
    private BigDecimal id;
    @Column(name = "DOC_ID")
    private Integer docId;
    @JoinColumn(name = "LOST_ID", referencedColumnName = "LOST_ITEM_ID")
    @ManyToOne
    private MmsLostFixedAsset lostId;

    public LostDmsUpload() {
    }

    public LostDmsUpload(BigDecimal id) {
        this.id = id;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public Integer getDocId() {
        return docId;
    }

    public void setDocId(Integer docId) {
        this.docId = docId;
    }

    public MmsLostFixedAsset getLostId() {
        return lostId;
    }

    public void setLostId(MmsLostFixedAsset lostId) {
        this.lostId = lostId;
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
        if (!(object instanceof LostDmsUpload)) {
            return false;
        }
        LostDmsUpload other = (LostDmsUpload) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.eep.mms.entity.LostDmsUpload[ id=" + id + " ]";
    }
    
}
