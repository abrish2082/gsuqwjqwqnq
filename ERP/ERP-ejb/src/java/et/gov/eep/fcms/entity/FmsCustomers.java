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
@Table(name = "fms_customers")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FmsCustomers.findAll", query = "SELECT f FROM FmsCustomers f"),
    @NamedQuery(name = "FmsCustomers.findByCustomerId", query = "SELECT f FROM FmsCustomers f WHERE f.customerId = :customerId"),
    @NamedQuery(name = "FmsCustomers.findByCustomerType", query = "SELECT f FROM FmsCustomers f WHERE f.customerType = :customerType")})
public class FmsCustomers implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
   @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FMS_CUSTOMERS_CUSTOMER_ID_SEQ")
    @SequenceGenerator(name = "FMS_CUSTOMERS_CUSTOMER_ID_SEQ", sequenceName = "FMS_CUSTOMERS_CUSTOMER_ID_SEQ", allocationSize = 1)
    @Basic(optional = false)
    @Column(name = "CUSTOMER_ID")
    private Integer customerId;
    @Size(max = 30)
    @Column(name = "CUSTOMER_TYPE")
    private String customerType;
    @JoinColumn(name = "PARTY_ID", referencedColumnName = "PARTY_ID")
    @ManyToOne(optional = false)
    private FmsParty partyId;

    /**
     *
     */
    public FmsCustomers() {
    }

    /**
     *
     * @param customerId
     */
    public FmsCustomers(Integer customerId) {
        this.customerId = customerId;
    }

    /**
     *
     * @return
     */
    public Integer getCustomerId() {
        return customerId;
    }

    /**
     *
     * @param customerId
     */
    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    /**
     *
     * @return
     */
    public String getCustomerType() {
        return customerType;
    }

    /**
     *
     * @param customerType
     */
    public void setCustomerType(String customerType) {
        this.customerType = customerType;
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
        hash += (customerId != null ? customerId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FmsCustomers)) {
            return false;
        }
        FmsCustomers other = (FmsCustomers) object;
        if ((this.customerId == null && other.customerId != null) || (this.customerId != null && !this.customerId.equals(other.customerId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.insa.erp.ibfms.entity.FmsCustomers[ customerId=" + customerId + " ]";
    }
    
}
