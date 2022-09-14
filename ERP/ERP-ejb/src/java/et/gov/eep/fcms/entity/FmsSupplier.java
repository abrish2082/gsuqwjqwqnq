/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.entity;

import java.io.Serializable;
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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author AB
 */
@Entity
@Table(name = "fms_supplier" )
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FmsSupplier.findAll", query = "SELECT f FROM FmsSupplier f"),
    @NamedQuery(name = "FmsSupplier.findBySupplierId", query = "SELECT f FROM FmsSupplier f WHERE f.supplierId = :supplierId"),
    @NamedQuery(name = "FmsSupplier.findBySupplierType", query = "SELECT f FROM FmsSupplier f WHERE f.supplierType = :supplierType")})
public class FmsSupplier implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
   @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FMS_SUPPLIER_SUPPLIER_ID_SEQ")
    @SequenceGenerator(schema ="ERP" ,name = "FMS_SUPPLIER_SUPPLIER_ID_SEQ", sequenceName = "FMS_SUPPLIER_SUPPLIER_ID_SEQ", allocationSize = 1)
    @Basic(optional = false)
    @Column(name = "SUPPLIER_ID")
    private Integer supplierId;
    @Size(max = 20)
    @Column(name = "SUPPLIER_TYPE")
    private String supplierType;
    @JoinColumn(name = "PARTY_ID", referencedColumnName = "PARTY_ID")
    @ManyToOne
    private FmsParty partyId;

    /**
     *
     */
    public FmsSupplier() {
    }

    /**
     *
     * @param supplierId
     */
    public FmsSupplier(Integer supplierId) {
        this.supplierId = supplierId;
    }

    /**
     *
     * @return
     */
    public Integer getSupplierId() {
        return supplierId;
    }

    /**
     *
     * @param supplierId
     */
    public void setSupplierId(Integer supplierId) {
        this.supplierId = supplierId;
    }

    /**
     *
     * @return
     */
    public String getSupplierType() {
        return supplierType;
    }

    /**
     *
     * @param supplierType
     */
    public void setSupplierType(String supplierType) {
        this.supplierType = supplierType;
    }

    /**
     *
     * @return
     */
    public FmsParty getPartyId() {
        return partyId;
    }

    /**
     *
     * @param partyId
     */
    public void setPartyId(FmsParty partyId) {
        this.partyId = partyId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (supplierId != null ? supplierId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FmsSupplier)) {
            return false;
        }
        FmsSupplier other = (FmsSupplier) object;
        if ((this.supplierId == null && other.supplierId != null) || (this.supplierId != null && !this.supplierId.equals(other.supplierId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.insa.erp.ibfms.entity.FmsSupplier[ supplierId=" + supplierId + " ]";
    }
    
}
