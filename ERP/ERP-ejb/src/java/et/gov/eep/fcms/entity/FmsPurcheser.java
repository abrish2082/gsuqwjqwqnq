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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author AB
 */
@Entity
@Table(name = "fms_purcheser" )
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FmsPurcheser.findAll", query = "SELECT f FROM FmsPurcheser f"),
    @NamedQuery(name = "FmsPurcheser.findByPurcheserId", query = "SELECT f FROM FmsPurcheser f WHERE f.purcheserId = :purcheserId")})
public class FmsPurcheser implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
   @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FMS_PURCHESER_PURCHESER_ID_SEQ")
    @SequenceGenerator(schema ="ERP" ,name = "FMS_PURCHESER_PURCHESER_ID_SEQ", sequenceName = "FMS_PURCHESER_PURCHESER_ID_SEQ", allocationSize = 1)
    @Basic(optional = false)
    @Column(name = "PURCHESER_ID")
    private Integer purcheserId;
    @JoinColumn(name = "EMP_ID", referencedColumnName = "EMP_ID")
    @ManyToOne
    private FmsEmployee empId;

    /**
     *
     */
    public FmsPurcheser() {
    }

    /**
     *
     * @param purcheserId
     */
    public FmsPurcheser(Integer purcheserId) {
        this.purcheserId = purcheserId;
    }

    /**
     *
     * @return
     */
    public Integer getPurcheserId() {
        return purcheserId;
    }

    /**
     *
     * @param purcheserId
     */
    public void setPurcheserId(Integer purcheserId) {
        this.purcheserId = purcheserId;
    }

    /**
     *
     * @return
     */
    public FmsEmployee getEmpId() {
        return empId;
    }

    /**
     *
     * @param empId
     */
    public void setEmpId(FmsEmployee empId) {
        this.empId = empId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (purcheserId != null ? purcheserId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FmsPurcheser)) {
            return false;
        }
        FmsPurcheser other = (FmsPurcheser) object;
        if ((this.purcheserId == null && other.purcheserId != null) || (this.purcheserId != null && !this.purcheserId.equals(other.purcheserId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.insa.erp.ibfms.entity.FmsPurcheser[ purcheserId=" + purcheserId + " ]";
    }
    
}
