/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author AB
 */
@Entity
@Table(name = "fms_party" )
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FmsParty.findAll", query = "SELECT f FROM FmsParty f"),
    @NamedQuery(name = "FmsParty.findByPartyId", query = "SELECT f FROM FmsParty f WHERE f.partyId = :partyId"),
    @NamedQuery(name = "FmsParty.findByPartyType", query = "SELECT f FROM FmsParty f WHERE f.partyType = :partyType")})
public class FmsParty implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FMS_PARTY_PARTY_ID_SEQ")
    @SequenceGenerator(name = "FMS_PARTY_PARTY_ID_SEQ", sequenceName = "FMS_PARTY_PARTY_ID_SEQ", allocationSize = 1)
    @Basic(optional = false)
    @Column(name = "PARTY_ID")
    private Integer partyId;
    @Size(max = 30)
    @Column(name = "PARTY_TYPE")
    private String partyType;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "partyId")
    private List<FmsCustomers> fmsCustomersList;
    @OneToMany(mappedBy = "partyId")
    private List<FmsEmployee> fmsEmployeeList;
//    @JoinColumn(name = "CHART_OF_ACCT_ID", referencedColumnName = "CHART_OF_ACCT_ID")
//    @ManyToOne
//    private FmsChartOfAccount chartOfAcctId;
//    @OneToMany(mappedBy = "partyId")
//    private List<FmsSupplier> fmsSupplierList;

    /**
     *
     */
    public FmsParty() {
    }

    /**
     *
     * @param partyId
     */
    public FmsParty(Integer partyId) {
        this.partyId = partyId;
    }

    /**
     *
     * @return
     */
    public Integer getPartyId() {
        return partyId;
    }

    /**
     *
     * @param partyId
     */
    public void setPartyId(Integer partyId) {
        this.partyId = partyId;
    }

    /**
     *
     * @return
     */
    public String getPartyType() {
        return partyType;
    }

    /**
     *
     * @param partyType
     */
    public void setPartyType(String partyType) {
        this.partyType = partyType;
    }

    /**
     *
     * @return
     */
    @XmlTransient
    public List<FmsCustomers> getFmsCustomersList() {
        return fmsCustomersList;
    }

    /**
     *
     * @param fmsCustomersList
     */
    public void setFmsCustomersList(List<FmsCustomers> fmsCustomersList) {
        this.fmsCustomersList = fmsCustomersList;
    }

    /**
     *
     * @return
     */
    @XmlTransient
    public List<FmsEmployee> getFmsEmployeeList() {
        return fmsEmployeeList;
    }

    /**
     *
     * @param fmsEmployeeList
     */
    public void setFmsEmployeeList(List<FmsEmployee> fmsEmployeeList) {
        this.fmsEmployeeList = fmsEmployeeList;
    }

//    public FmsChartOfAccount getChartOfAcctId() {
//        return chartOfAcctId;
//    }
//
//    public void setChartOfAcctId(FmsChartOfAccount chartOfAcctId) {
//        this.chartOfAcctId = chartOfAcctId;
//    }

//    @XmlTransient
//    public List<FmsSupplier> getFmsSupplierList() {
//        return fmsSupplierList;
//    }
//
//    public void setFmsSupplierList(List<FmsSupplier> fmsSupplierList) {
//        this.fmsSupplierList = fmsSupplierList;
//    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (partyId != null ? partyId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FmsParty)) {
            return false;
        }
        FmsParty other = (FmsParty) object;
        if ((this.partyId == null && other.partyId != null) || (this.partyId != null && !this.partyId.equals(other.partyId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.insa.erp.ibfms.entity.FmsParty[ partyId=" + partyId + " ]";
    }
    
}
