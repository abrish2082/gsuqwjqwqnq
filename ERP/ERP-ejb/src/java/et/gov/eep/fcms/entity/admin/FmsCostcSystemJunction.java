/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.entity.admin;

import java.io.Serializable;
import java.util.List;
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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import et.gov.eep.fcms.entity.budget.FmsCapitalBudget1;
import et.gov.eep.fcms.entity.budget.FmsOperatingBudget1;
import et.gov.eep.hrms.entity.organization.HrDepartments;

/**
 *
 * @author Terminal2
 */
@Entity
@Table(name = "FMS_COSTC_SYSTEM_JUNCTION", catalog = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FmsCostcSystemJunction.findAll", query = "SELECT f FROM FmsCostcSystemJunction f"),
    @NamedQuery(name = "FmsCostcSystemJunction.findBySystemId", query = "SELECT f FROM FmsCostcSystemJunction f WHERE f.fmsLuSystem = :fmsLuSystem"),
    @NamedQuery(name = "FmsCostcSystemJunction.findByDeptId", query = "SELECT f FROM FmsCostcSystemJunction f WHERE f.depId.depId = :deptId"),
    @NamedQuery(name = "FmsCostcSystemJunction.findByDepId", query = "SELECT f FROM FmsCostcSystemJunction f WHERE f.depId = :depId"),
    @NamedQuery(name = "FmsCostcSystemJunction.findByCCandSS", query = "SELECT f FROM FmsCostcSystemJunction f WHERE f.fmsLuSystem = :fmsLuSystem AND f.fmsCostCenter = :fmsCostCenter"),
    @NamedQuery(name = "FmsCostcSystemJunction.findByCCandSSId", query = "SELECT f FROM FmsCostcSystemJunction f WHERE f.fmsLuSystem.systemId = :systemId AND f.fmsCostCenter.costCenterId = :costCenterId"),
    @NamedQuery(name = "FmsCostcSystemJunction.findById", query = "SELECT f FROM FmsCostcSystemJunction f WHERE f.id = :id"),})
public class FmsCostcSystemJunction implements Serializable {

    @OneToMany(mappedBy = "ssCcJunction")
    private List<FmsSubsidiaryLedger> fmsSubsidiaryLedgerList;
    @OneToMany(mappedBy = "ccSsJunction")
    private List<FmsCapitalBudget1> fmsCapitalBudget1List;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ccSsJunction")
    private List<FmsOperatingBudget1> fmsOperatingBudget1List;
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FMS_SEQ_COSTC_SYSTEM_JUNCTION")
    @SequenceGenerator(name = "FMS_SEQ_COSTC_SYSTEM_JUNCTION", sequenceName = "FMS_SEQ_COSTC_SYSTEM_JUNCTION", allocationSize = 1)
    private Integer id;
    @JoinColumn(name = "COST_CENTER_ID", referencedColumnName = "COST_CENTER_ID")
    @ManyToOne
    private FmsCostCenter fmsCostCenter;
    @JoinColumn(name = "SYSTEM_ID", referencedColumnName = "SYSTEM_ID")
    @ManyToOne
    private FmsLuSystem fmsLuSystem;
    @Column(name = "STATUS")
    private String status;
    @JoinColumn(name = "DEP_ID", referencedColumnName = "DEP_ID")
    @ManyToOne
    private HrDepartments depId;

    public FmsCostcSystemJunction() {
    }

    public FmsCostcSystemJunction(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public FmsCostCenter getFmsCostCenter() {
        return fmsCostCenter;
    }

    public void setFmsCostCenter(FmsCostCenter fmsCostCenter) {
        this.fmsCostCenter = fmsCostCenter;
    }

    public FmsLuSystem getFmsLuSystem() {
        return fmsLuSystem;
    }

    public void setFmsLuSystem(FmsLuSystem fmsLuSystem) {
        this.fmsLuSystem = fmsLuSystem;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public HrDepartments getDepId() {
        return depId;
    }

    public void setDepId(HrDepartments depId) {
        this.depId = depId;
    }

    @XmlTransient
    public List<FmsCapitalBudget1> getFmsCapitalBudget1List() {
        return fmsCapitalBudget1List;
    }

    public void setFmsCapitalBudget1List(List<FmsCapitalBudget1> fmsCapitalBudget1List) {
        this.fmsCapitalBudget1List = fmsCapitalBudget1List;
    }

    @XmlTransient
    public List<FmsOperatingBudget1> getFmsOperatingBudget1List() {
        return fmsOperatingBudget1List;
    }

    public void setFmsOperatingBudget1List(List<FmsOperatingBudget1> fmsOperatingBudget1List) {
        this.fmsOperatingBudget1List = fmsOperatingBudget1List;
    }

    @XmlTransient
    public List<FmsSubsidiaryLedger> getFmsSubsidiaryLedgerList() {
        return fmsSubsidiaryLedgerList;
    }

    public void setFmsSubsidiaryLedgerList(List<FmsSubsidiaryLedger> fmsSubsidiaryLedgerList) {
        this.fmsSubsidiaryLedgerList = fmsSubsidiaryLedgerList;
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
        if (!(object instanceof FmsCostcSystemJunction)) {
            return false;
        }
        FmsCostcSystemJunction other = (FmsCostcSystemJunction) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return getFmsLuSystem().getSystemCode() + " " + getFmsCostCenter().getSystemName();
    }

}
