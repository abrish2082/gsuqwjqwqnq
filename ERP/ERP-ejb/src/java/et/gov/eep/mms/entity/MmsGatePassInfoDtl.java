/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.mms.entity;

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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Sadik
 */
@Entity
@Table(name = "MMS_GATE_PASS_INFO_DTL", catalog = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MmsGatePassInfoDtl.findAll", query = "SELECT m FROM MmsGatePassInfoDtl m"),
    @NamedQuery(name = "MmsGatePassInfoDtl.findByGatePassDtlId", query = "SELECT m FROM MmsGatePassInfoDtl m WHERE m.gatePassDtlId = :gatePassDtlId"),

    @NamedQuery(name = "MmsGatePassInfoDtl.findByQuantity", query = "SELECT m FROM MmsGatePassInfoDtl m WHERE m.quantity = :quantity"),

    @NamedQuery(name = "MmsGatePassInfoDtl.findByAllParameters", query = "SELECT m FROM MmsGatePassInfoDtl m WHERE m.gatePassId.gatePassNo LIKE :gatePassNo" ),
    // @NamedQuery(name = "MmsGatePassInfoDtl.findByAllParameters", query = "SELECT m FROM MmsGatePassInfoDtl m WHERE m.gatePassId.gatePassNo LIKE :gatePassNo AND m." ),
    @NamedQuery(name = "MmsGatePassInfoDtl.findByRemark", query = "SELECT m FROM MmsGatePassInfoDtl m WHERE m.remark = :remark")})
public class MmsGatePassInfoDtl implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MMS_GATE_PASS_DTL_SEQ")
    @SequenceGenerator(name = "MMS_GATE_PASS_DTL_SEQ", sequenceName = "MMS_GATE_PASS_DTL_SEQ", allocationSize = 1)
    @NotNull
    @Column(name = "GATE_PASS_DTL_ID", nullable = false)
    private Integer gatePassDtlId;
   
    @Basic(optional = false)
    @NotNull
    @Column(name = "QUANTITY", nullable = false)
    private Integer quantity;
    
  
    @Size(max = 20)
    @Column(name = "REMARK", length = 20)
    private String remark;
   
    @JoinColumn(name = "GATE_PASS_ID", referencedColumnName = "GATE_PASS_ID", nullable = false)
    @ManyToOne(optional = false)
    private MmsGatePassInformation gatePassId;
      @JoinColumn(name = "ITEM_ID", referencedColumnName = "MATERIAL_ID")
    @ManyToOne
        private MmsItemRegistration itemId;

    public MmsGatePassInfoDtl() {
    }

    public MmsGatePassInfoDtl(Integer gatePassDtlId) {
        this.gatePassDtlId = gatePassDtlId;
    }

    public MmsGatePassInfoDtl(Integer gatePassDtlId, String materialCode, Integer quantity, String unitOfMeasure) {
        this.gatePassDtlId = gatePassDtlId;
       
        this.quantity = quantity;
      
    }

    public Integer getGatePassDtlId() {
        return gatePassDtlId;
    }

    public void setGatePassDtlId(Integer gatePassDtlId) {
        this.gatePassDtlId = gatePassDtlId;
    }

  

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

  

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public MmsGatePassInformation getGatePassId() {
        return gatePassId;
    }

    public void setGatePassId(MmsGatePassInformation gatePassId) {
        this.gatePassId = gatePassId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (gatePassDtlId != null ? gatePassDtlId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MmsGatePassInfoDtl)) {
            return false;
        }
        MmsGatePassInfoDtl other = (MmsGatePassInfoDtl) object;
        if ((this.gatePassDtlId == null && other.gatePassDtlId != null) || (this.gatePassDtlId != null && !this.gatePassDtlId.equals(other.gatePassDtlId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.eep.mms.entity.MmsGatePassInfoDtl[ gatePassDtlId=" + gatePassDtlId + " ]";
    }

    

    

    public MmsItemRegistration getItemId() {
        return itemId;
    }

    public void setItemId(MmsItemRegistration itemId) {
        this.itemId = itemId;
    }
    
}
