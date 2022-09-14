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
@Table(name = "PRMS_CHECKLIST_FILE_UPLOAD")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PrmsChecklistFileUpload.findAll", query = "SELECT p FROM PrmsChecklistFileUpload p"),
    @NamedQuery(name = "PrmsChecklistFileUpload.findById", query = "SELECT p FROM PrmsChecklistFileUpload p WHERE p.id = :id"),
    @NamedQuery(name = "PrmsChecklistFileUpload.findByDocumetId", query = "SELECT p FROM PrmsChecklistFileUpload p WHERE p.documetId = :documetId")})
public class PrmsChecklistFileUpload implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PRMS_CHECKLIST_FILE_UPLO")
    @SequenceGenerator(name = "PRMS_CHECKLIST_FILE_UPLO", sequenceName = "PRMS_CHECKLIST_FILE_UPLO", allocationSize = 1)
    @Column(name = "ID")
    private BigDecimal id;
    @Column(name = "DOCUMET_ID")
    private BigInteger documetId;
  @JoinColumn(name = "CHECKLIST_ID", referencedColumnName = "BID_OPEN_CHECK_LIST_ID")
    @ManyToOne
    private PrmsBidOpeningCheckList checklistId;
    public PrmsChecklistFileUpload() {
    }

    public PrmsChecklistFileUpload(BigDecimal id) {
        this.id = id;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public BigInteger getDocumetId() {
        return documetId;
    }

    public void setDocumetId(BigInteger documetId) {
        this.documetId = documetId;
    }

    public PrmsBidOpeningCheckList getChecklistId() {
        return checklistId;
    }

    public void setChecklistId(PrmsBidOpeningCheckList checklistId) {
        this.checklistId = checklistId;
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
        if (!(object instanceof PrmsChecklistFileUpload)) {
            return false;
        }
        PrmsChecklistFileUpload other = (PrmsChecklistFileUpload) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.eep.prms.entity.PrmsChecklistFileUpload[ id=" + id + " ]";
    }
    
}
