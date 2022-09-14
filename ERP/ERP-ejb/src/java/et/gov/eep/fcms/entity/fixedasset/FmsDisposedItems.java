/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.entity.fixedasset;

import et.gov.eep.mms.entity.MmsDisposalDtl;
import java.io.Serializable;
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
 * @author Me
 */
@Entity
@Table(name = "FMS_DISPOSED_ITEMS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FmsDisposedItems.findAll", query = "SELECT f FROM FmsDisposedItems f"),
    @NamedQuery(name = "FmsDisposedItems.findByDisposedItemsId", query = "SELECT f FROM FmsDisposedItems f WHERE f.disposedItemsId = :disposedItemsId"),
    @NamedQuery(name = "FmsDisposedItems.findByStatus", query = "SELECT f FROM FmsDisposedItems f WHERE f.status = :status")})
public class FmsDisposedItems implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "DISPOSED_ITEMS_ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FMS_DISPOSED_ITEMS_SEQ")
    @SequenceGenerator(name = "FMS_DISPOSED_ITEMS_SEQ", sequenceName = "FMS_DISPOSED_ITEMS_SEQ", allocationSize = 1)
    private Integer disposedItemsId;
    @Column(name = "STATUS")
    private BigInteger status;
    @JoinColumn(name = "FMS_DEPRECIATION_FK", referencedColumnName = "DPR_OFFICE_ID")
    @ManyToOne
    private FmsDprOfficeAsset fmsDepreciationFk;
    @JoinColumn(name = "MMS_DISPOSED_FK", referencedColumnName = "DISP_DTL_ID")
    @ManyToOne
    private MmsDisposalDtl mmsDisposedFk;

    public FmsDisposedItems() {
    }

    public FmsDisposedItems(Integer disposedItemsId) {
        this.disposedItemsId = disposedItemsId;
    }

    public Integer getDisposedItemsId() {
        return disposedItemsId;
    }

    public void setDisposedItemsId(Integer disposedItemsId) {
        this.disposedItemsId = disposedItemsId;
    }

    public BigInteger getStatus() {
        return status;
    }

    public void setStatus(BigInteger status) {
        this.status = status;
    }

    public FmsDprOfficeAsset getFmsDepreciationFk() {
        return fmsDepreciationFk;
    }

    public void setFmsDepreciationFk(FmsDprOfficeAsset fmsDepreciationFk) {
        this.fmsDepreciationFk = fmsDepreciationFk;
    }

    public MmsDisposalDtl getMmsDisposedFk() {
        return mmsDisposedFk;
    }

    public void setMmsDisposedFk(MmsDisposalDtl mmsDisposedFk) {
        this.mmsDisposedFk = mmsDisposedFk;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (disposedItemsId != null ? disposedItemsId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FmsDisposedItems)) {
            return false;
        }
        FmsDisposedItems other = (FmsDisposedItems) object;
        if ((this.disposedItemsId == null && other.disposedItemsId != null) || (this.disposedItemsId != null && !this.disposedItemsId.equals(other.disposedItemsId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.eep.fcms.entity.FmsDisposedItems[ disposedItemsId=" + disposedItemsId + " ]";
    }
    
}
