/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.entity;

import et.gov.eep.fcms.entity.pettyCash.FmsCasher;
import java.io.Serializable;
import java.util.List;
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
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author AB
 */
@Entity
@Table(name = "fms_employee")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FmsEmployee.findAll", query = "SELECT f FROM FmsEmployee f"),
    @NamedQuery(name = "FmsEmployee.findByEmpId", query = "SELECT f FROM FmsEmployee f WHERE f.empId = :empId")})
public class FmsEmployee implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
   @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FMS_EMPLOYEE_EMP_ID_SEQ")
    @SequenceGenerator(name = "FMS_EMPLOYEE_EMP_ID_SEQ", sequenceName = "FMS_EMPLOYEE_EMP_ID_SEQ", allocationSize = 1)
    @Basic(optional = false)
    @Column(name = "EMP_ID")
    private Integer empId;
    @OneToMany(mappedBy = "empId")
    private List<FmsCasher> fmsCasherList;
    @JoinColumn(name = "PARTY_ID", referencedColumnName = "PARTY_ID")
    @ManyToOne
    private FmsParty partyId;
    @OneToMany(mappedBy = "empId")
    private List<FmsPurcheser> fmsPurcheserList;

    /**
     *
     */
    public FmsEmployee() {
    }

    /**
     *
     * @param empId
     */
    public FmsEmployee(Integer empId) {
        this.empId = empId;
    }

    /**
     *
     * @return
     */
    public Integer getEmpId() {
        return empId;
    }

    /**
     *
     * @param empId
     */
    public void setEmpId(Integer empId) {
        this.empId = empId;
    }

    /**
     *
     * @return
     */
    @XmlTransient
    public List<FmsCasher> getFmsCasherList() {
        return fmsCasherList;
    }

    /**
     *
     * @param fmsCasherList
     */
    public void setFmsCasherList(List<FmsCasher> fmsCasherList) {
        this.fmsCasherList = fmsCasherList;
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

    /**
     *
     * @return
     */
    @XmlTransient
    public List<FmsPurcheser> getFmsPurcheserList() {
        return fmsPurcheserList;
    }

    /**
     *
     * @param fmsPurcheserList
     */
    public void setFmsPurcheserList(List<FmsPurcheser> fmsPurcheserList) {
        this.fmsPurcheserList = fmsPurcheserList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (empId != null ? empId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FmsEmployee)) {
            return false;
        }
        FmsEmployee other = (FmsEmployee) object;
        if ((this.empId == null && other.empId != null) || (this.empId != null && !this.empId.equals(other.empId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.insa.erp.ibfms.entity.FmsEmployee[ empId=" + empId + " ]";
    }
    
}
