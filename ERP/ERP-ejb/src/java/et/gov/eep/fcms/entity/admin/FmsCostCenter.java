/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.entity.admin;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import et.gov.eep.fcms.entity.budget.FmsBudgetControl;
import et.gov.eep.mms.entity.MmsFaBuilding;
import et.gov.eep.mms.entity.MmsFaGeothermal;
import et.gov.eep.mms.entity.MmsFaHydropower;
import et.gov.eep.mms.entity.MmsFaSubstation;
import javax.persistence.Transient;

/**
 *
 * @author user
 */
@Entity
@Table(name = "FMS_COST_CENTER")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FmsCostCenter.findAll", query = "SELECT f FROM FmsCostCenter f"),
    @NamedQuery(name = "FmsCostCenter.findByCostCenterId", query = "SELECT f FROM FmsCostCenter f WHERE f.costCenterId = :costCenterId"),
    @NamedQuery(name = "FmsCostCenter.findByDescription", query = "SELECT f FROM FmsCostCenter f WHERE f.description = :description"),
    @NamedQuery(name = "FmsCostCenter.findBySystemName", query = "SELECT f FROM FmsCostCenter f WHERE f.systemName = :systemName"),
    @NamedQuery(name = "FmsCostCenter.findBySystemNameLikeOnly", query = "SELECT f FROM FmsCostCenter f WHERE f.systemName Like :systemName"),
    @NamedQuery(name = "FmsCostCenter.findforBudgetRequest", query = "SELECT f FROM FmsCostCenter f  WHERE f.systemId = :systemId"),
    @NamedQuery(name = "FmsCostCenter.findBySystemId", query = "SELECT f FROM FmsCostCenter f WHERE f.systemId = :systemId"),
    @NamedQuery(name = "FmsCostCenter.searchBySystemId", query = "SELECT f FROM FmsCostCenter f WHERE f.systemId.systemId LIKE :systemId"),
    @NamedQuery(name = "FmsCostCenter.findByStatus", query = "SELECT f FROM FmsCostCenter f WHERE f.status = :status")})
public class FmsCostCenter implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "COST_CENTER_ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FMS_SEQ_COST_CENTER")
    @SequenceGenerator(name = "FMS_SEQ_COST_CENTER", sequenceName = "FMS_SEQ_COST_CENTER", allocationSize = 1)
    private Integer costCenterId;
    @Size(max = 200)
    @Column(name = "DESCRIPTION")
    private String description;
    @Size(max = 45)
    @Column(unique = true, name = "SYSTEM_NAME")
    @NotNull
    private String systemName;
    @Size(max = 45)
    @Column(name = "STATUS")
    private String status;
    @JoinColumn(name = "SYSTEM_ID", referencedColumnName = "SYSTEM_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private FmsLuSystem systemId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fmsCostCenter")
    private List<FmsCostcSystemJunction> fmsCostcSystemJunctionList;
    @OneToMany(mappedBy = "costCenterId")
    private List<FmsBudgetControl> fmsBudgetControlList;
    @OneToMany(mappedBy = "gtCostCenter")
    private List<MmsFaGeothermal> mmsFaGeothermalList;
    @OneToMany(mappedBy = "buCostCenter")
    private List<MmsFaBuilding> mmsFaBuildingList;
    @OneToMany(mappedBy = "ssCostCenter")
    private List<MmsFaSubstation> mmsFaSubstationList;
    @OneToMany(mappedBy = "hpCostCenter")
    private List<MmsFaHydropower> mmsFaHydropowerList;

    public FmsCostCenter() {
    }
    
    @Transient
    private String columnName;

    @Transient
    private String columnValue;
    
      public String getColumnValue() {
        return columnValue;
    }

    public void setColumnValue(String columnValue) {
        this.columnValue = columnValue;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public FmsCostCenter(Integer costCenterId) {
        this.costCenterId = costCenterId;
    }

    public Integer getCostCenterId() {
        return costCenterId;
    }

    public void setCostCenterId(Integer costCenterId) {
        this.costCenterId = costCenterId;
    }

    public FmsLuSystem getSystemId() {
        if (systemId == null) {
            systemId = new FmsLuSystem();
        }
        return systemId;
    }

    public void setSystemId(FmsLuSystem systemId) {
        this.systemId = systemId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (costCenterId != null ? costCenterId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FmsCostCenter)) {
            return false;
        }
        FmsCostCenter other = (FmsCostCenter) object;
        if ((this.costCenterId == null && other.costCenterId != null) || (this.costCenterId != null && !this.costCenterId.equals(other.costCenterId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return systemName;
    }

    @XmlTransient
    public List<MmsFaGeothermal> getMmsFaGeothermalList() {
        return mmsFaGeothermalList;
    }

    public void setMmsFaGeothermalList(List<MmsFaGeothermal> mmsFaGeothermalList) {
        this.mmsFaGeothermalList = mmsFaGeothermalList;
    }

    @XmlTransient
    public List<MmsFaBuilding> getMmsFaBuildingList() {
        return mmsFaBuildingList;
    }

    public void setMmsFaBuildingList(List<MmsFaBuilding> mmsFaBuildingList) {
        this.mmsFaBuildingList = mmsFaBuildingList;
    }

    @XmlTransient
    public List<MmsFaSubstation> getMmsFaSubstationList() {
        return mmsFaSubstationList;
    }

    public void setMmsFaSubstationList(List<MmsFaSubstation> mmsFaSubstationList) {
        this.mmsFaSubstationList = mmsFaSubstationList;
    }

    @XmlTransient
    public List<MmsFaHydropower> getMmsFaHydropowerList() {
        return mmsFaHydropowerList;
    }

    public void setMmsFaHydropowerList(List<MmsFaHydropower> mmsFaHydropowerList) {
        this.mmsFaHydropowerList = mmsFaHydropowerList;
    }

    @XmlTransient
    public List<FmsBudgetControl> getFmsBudgetControlList() {
        return fmsBudgetControlList;
    }

    public void setFmsBudgetControlList(List<FmsBudgetControl> fmsBudgetControlList) {
        this.fmsBudgetControlList = fmsBudgetControlList;
    }

    @XmlTransient
    public List<FmsCostcSystemJunction> getFmsCostcSystemJunctionList() {
        return fmsCostcSystemJunctionList;
    }

    public void setFmsCostcSystemJunctionList(List<FmsCostcSystemJunction> fmsCostcSystemJunctionList) {
        this.fmsCostcSystemJunctionList = fmsCostcSystemJunctionList;
    }
}
