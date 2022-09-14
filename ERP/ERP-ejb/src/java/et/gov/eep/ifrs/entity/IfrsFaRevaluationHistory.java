/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.ifrs.entity;

import et.gov.eep.mms.entity.IfrsFixedAsset;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author memube
 */
@Entity
@Table(name = "IFRS_FA_REVALUATION_HISTORY")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "IfrsFaRevaluationHistory.findAll", query = "SELECT i FROM IfrsFaRevaluationHistory i"),
    @NamedQuery(name = "IfrsFaRevaluationHistory.findById", query = "SELECT i FROM IfrsFaRevaluationHistory i WHERE i.id = :id"),
    @NamedQuery(name = "IfrsFaRevaluationHistory.findByOldRevCost", query = "SELECT i FROM IfrsFaRevaluationHistory i WHERE i.oldRevCost = :oldRevCost"),
    @NamedQuery(name = "IfrsFaRevaluationHistory.findByNewRevCost", query = "SELECT i FROM IfrsFaRevaluationHistory i WHERE i.newRevCost = :newRevCost"),
    @NamedQuery(name = "IfrsFaRevaluationHistory.findByRevaluedBy", query = "SELECT i FROM IfrsFaRevaluationHistory i WHERE i.revaluedBy = :revaluedBy"),
    @NamedQuery(name = "IfrsFaRevaluationHistory.findByRevaluationDate", query = "SELECT i FROM IfrsFaRevaluationHistory i WHERE i.revaluationDate = :revaluationDate")})
public class IfrsFaRevaluationHistory implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "IFRS_FA_REVALUATION_HISTRY_SEQ")
    @SequenceGenerator(name = "IFRS_FA_REVALUATION_HISTRY_SEQ", sequenceName = "IFRS_FA_REVALUATION_HISTRY_SEQ", allocationSize = 1)
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private BigDecimal id;
    @Column(name = "OLD_REV_COST")
    private BigDecimal oldRevCost;
    @Column(name = "NEW_REV_COST")
    private BigDecimal newRevCost;
    @Size(max = 45)
    @Column(name = "REVALUED_BY")
    private String revaluedBy;
    @Column(name = "REVALUATION_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date revaluationDate;
    @JoinColumn(name = "IFRS_FA_ID", referencedColumnName = "ID")
    @ManyToOne
    private IfrsFixedAsset ifrsFaId;

    public IfrsFaRevaluationHistory() {
    }

    public IfrsFaRevaluationHistory(BigDecimal id) {
        this.id = id;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public BigDecimal getOldRevCost() {
        return oldRevCost;
    }

    public void setOldRevCost(BigDecimal oldRevCost) {
        this.oldRevCost = oldRevCost;
    }

    public BigDecimal getNewRevCost() {
        return newRevCost;
    }

    public void setNewRevCost(BigDecimal newRevCost) {
        this.newRevCost = newRevCost;
    }

    public String getRevaluedBy() {
        return revaluedBy;
    }

    public void setRevaluedBy(String revaluedBy) {
        this.revaluedBy = revaluedBy;
    }

    public Date getRevaluationDate() {
        return revaluationDate;
    }

    public void setRevaluationDate(Date revaluationDate) {
        this.revaluationDate = revaluationDate;
    }

    public IfrsFixedAsset getIfrsFaId() {
        return ifrsFaId;
    }

    public void setIfrsFaId(IfrsFixedAsset ifrsFaId) {
        this.ifrsFaId = ifrsFaId;
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
        if (!(object instanceof IfrsFaRevaluationHistory)) {
            return false;
        }
        IfrsFaRevaluationHistory other = (IfrsFaRevaluationHistory) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.eep.ifrs.entity.IfrsFaRevaluationHistory[ id=" + id + " ]";
    }
    
}
