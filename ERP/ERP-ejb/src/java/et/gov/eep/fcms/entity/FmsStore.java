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
@Table(name = "fms_store" )
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FmsStore.findAll", query = "SELECT f FROM FmsStore f"),
    @NamedQuery(name = "FmsStore.findByStoreId", query = "SELECT f FROM FmsStore f WHERE f.storeId = :storeId")})
public class FmsStore implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
   @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FMS_STORE_STORE_ID_SEQ")
    @SequenceGenerator(schema ="ERP" ,name = "FMS_STORE_STORE_ID_SEQ", sequenceName = "FMS_STORE_STORE_ID_SEQ", allocationSize = 1)
    @Basic(optional = false)
    @Column(name = "STORE_ID")
    private Integer storeId;
//    @JoinColumn(name = "CHART_OF_ACCT_ID", referencedColumnName = "CHART_OF_ACCT_ID")
//    @ManyToOne
//    private FmsChartOfAccount chartOfAcctId;

    /**
     *
     */
    public FmsStore() {
    }

    /**
     *
     * @param storeId
     */
    public FmsStore(Integer storeId) {
        this.storeId = storeId;
    }

    /**
     *
     * @return
     */
    public Integer getStoreId() {
        return storeId;
    }

    /**
     *
     * @param storeId
     */
    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

//    public FmsChartOfAccount getChartOfAcctId() {
//        return chartOfAcctId;
//    }
//
//    public void setChartOfAcctId(FmsChartOfAccount chartOfAcctId) {
//        this.chartOfAcctId = chartOfAcctId;
//    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (storeId != null ? storeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FmsStore)) {
            return false;
        }
        FmsStore other = (FmsStore) object;
        if ((this.storeId == null && other.storeId != null) || (this.storeId != null && !this.storeId.equals(other.storeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.insa.erp.ibfms.entity.FmsStore[ storeId=" + storeId + " ]";
    }
    
}
