/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.mms.entity;

import java.io.Serializable;
import java.math.BigDecimal;
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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Sadik
 */
@Entity
@Table(name = "MMS_ISIV_RECEIVED_DTL")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MmsIsivReceivedDtl.findAll", query = "SELECT m FROM MmsIsivReceivedDtl m"),
    @NamedQuery(name = "MmsIsivReceivedDtl.findByReceivingDtlId", query = "SELECT m FROM MmsIsivReceivedDtl m WHERE m.receivingDtlId = :receivingDtlId"),
    @NamedQuery(name = "MmsIsivReceivedDtl.findByQuantity", query = "SELECT m FROM MmsIsivReceivedDtl m WHERE m.quantity = :quantity")})
public class MmsIsivReceivedDtl implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MMS_ISIV_RECEIVED_DTL_SEQ")
    @SequenceGenerator(name = "MMS_ISIV_RECEIVED_DTL_SEQ", sequenceName = "MMS_ISIV_RECEIVED_DTL_SEQ", allocationSize = 1)
    @Column(name = "RECEIVING_DTL_ID", nullable = false, precision = 0, scale = -127)
    private Integer receivingDtlId;
    @Column(name = "QUANTITY")
    private BigDecimal quantity;
    @JoinColumn(name = "RECEIVING_ID", referencedColumnName = "RECIEVING_ID")
    @ManyToOne
    private MmsIsivReceived receivingId;
    @JoinColumn(name = "MATERIAL_ID", referencedColumnName = "MATERIAL_ID")
    @ManyToOne
    private MmsItemRegistration materialId;
    @Column(name = "UNIT_PRICE")
    private BigDecimal unitPrice;

    public MmsIsivReceivedDtl() {
    }

    public MmsIsivReceivedDtl(Integer receivingDtlId) {
        this.receivingDtlId = receivingDtlId;
    }

    public Integer getReceivingDtlId() {
        return receivingDtlId;
    }

    public void setReceivingDtlId(Integer receivingDtlId) {
        this.receivingDtlId = receivingDtlId;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public MmsIsivReceived getReceivingId() {
        return receivingId;
    }

    public void setReceivingId(MmsIsivReceived receivingId) {
        this.receivingId = receivingId;
    }

    public MmsItemRegistration getMaterialId() {
        return materialId;
    }

    public void setMaterialId(MmsItemRegistration materialId) {
        this.materialId = materialId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (receivingDtlId != null ? receivingDtlId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MmsIsivReceivedDtl)) {
            return false;
        }
        MmsIsivReceivedDtl other = (MmsIsivReceivedDtl) object;
        if ((this.receivingDtlId == null && other.receivingDtlId != null) || (this.receivingDtlId != null && !this.receivingDtlId.equals(other.receivingDtlId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.eep.mms.entity.MmsIsivReceivedDtl[ receivingDtlId=" + receivingDtlId + " ]";
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }
    
    
    
}
