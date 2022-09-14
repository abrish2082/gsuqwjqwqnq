/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.entity.admin;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import et.gov.eep.mms.entity.MmsFaBuilding;
import et.gov.eep.mms.entity.MmsFaHydropower;
import et.gov.eep.mms.entity.MmsFaTransmission;
import et.gov.eep.mms.entity.MmsFaTransport;
import et.gov.eep.mms.entity.MmsFixedassetRegstration;
import javax.persistence.Transient;

/**
 *
 * @author Binyam
 */
@Entity
@Table(name = "FMS_SYSTEM")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FmsLuSystem.findAll", query = "SELECT f FROM FmsLuSystem f"),
    @NamedQuery(name = "FmsLuSystem.findBySystemId", query = "SELECT f FROM FmsLuSystem f WHERE f.systemId = :systemId"),
    @NamedQuery(name = "FmsLuSystem.findByOprSystem", query = "SELECT f FROM FmsLuSystem f WHERE f.type <> 2"),
    @NamedQuery(name = "FmsLuSystem.findByProjSystem", query = "SELECT f FROM FmsLuSystem f WHERE f.type = 2"),
    @NamedQuery(name = "FmsLuSystem.findBySystemCode", query = "SELECT f FROM FmsLuSystem f WHERE f.systemCode = :systemCode"),
    @NamedQuery(name = "FmsLuSystem.findBySystemCodeLike", query = "SELECT f FROM FmsLuSystem f WHERE f.systemCode LIKE :systemCode"),
    @NamedQuery(name = "FmsLuSystem.findBySystemName", query = "SELECT f FROM FmsLuSystem f WHERE f.systemName = :systemName"),})
public class FmsLuSystem implements Serializable {

    @Size(max = 20)
    @Column(name = "TYPE")
    private String type;
    @Size(max = 10)
    @Column(name = "STATUS")
    private String status;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fmsLuSystem")
    private List<FmsCostcSystemJunction> fmsCostcSystemJunctionList;
    @OneToMany(mappedBy = "fmsLuSystem")
    private List<FmsSystemJobJunction> fmsSystemJobJunctionList;
    @OneToMany(mappedBy = "systemNo")
    private List<MmsFixedassetRegstration> mmsFixedassetRegstrationList;
    @OneToMany(mappedBy = "hpSysNo")
    private List<MmsFaHydropower> mmsFaHydropowerList;
    @OneToMany(mappedBy = "buSysNo")
    private List<MmsFaBuilding> mmsFaBuildingList;
    @OneToMany(mappedBy = "systemId")
    private List<FmsVouchersNoRange> fmsVouchersNoRangeList;
    @OneToMany(mappedBy = "systemNo")
    private List<MmsFaTransmission> mmsFaTransmissionList;
    @OneToMany(mappedBy = "tpSysNo")
    private List<MmsFaTransport> mmsFaTransportList;
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SYSTEM_ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FMS_SEQ_LU_SYSTEM")
    @SequenceGenerator(name = "FMS_SEQ_LU_SYSTEM", sequenceName = "FMS_SEQ_LU_SYSTEM", allocationSize = 1)
    private Integer systemId;
    @Size(max = 20)
    @Column(name = "SYSTEM_CODE", unique = true)
    @NotNull
    private String systemCode;
    @Size(max = 20)
    @Column(name = "SYSTEM_NAME")
    @NotNull
    private String systemName;
    @OneToMany(mappedBy = "systemId", cascade = CascadeType.ALL)
    private List<FmsCostCenter> fmsCostCenterList;

    public FmsLuSystem() {
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

    /**
     *
     * @param systemId
     */
    public FmsLuSystem(Integer systemId) {
        this.systemId = systemId;
    }

    /**
     *
     * @return
     */
    public Integer getSystemId() {
        return systemId;
    }

    /**
     *
     * @param systemId
     */
    public void setSystemId(Integer systemId) {
        this.systemId = systemId;
    }

    /**
     *
     * @return
     */
    public String getSystemCode() {
        return systemCode;
    }

    /**
     *
     * @param systemCode
     */
    public void setSystemCode(String systemCode) {
        this.systemCode = systemCode;
    }

    /**
     *
     * @return
     */
    public String getSystemName() {
        return systemName;
    }

    /**
     *
     * @param systemName
     */
    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    /**
     *
     * @return
     */
    @XmlTransient
    public List<MmsFaTransport> getMmsFaTransportList() {
        return mmsFaTransportList;
    }

    /**
     *
     * @param mmsFaTransportList
     */
    public void setMmsFaTransportList(List<MmsFaTransport> mmsFaTransportList) {
        this.mmsFaTransportList = mmsFaTransportList;
    }

    @XmlTransient
    public List<FmsVouchersNoRange> getFmsVouchersNoRangeList() {
        return fmsVouchersNoRangeList;
    }

    public void setFmsVouchersNoRangeList(List<FmsVouchersNoRange> fmsVouchersNoRangeList) {
        this.fmsVouchersNoRangeList = fmsVouchersNoRangeList;
    }

    @XmlTransient
    public List<MmsFaHydropower> getMmsFaHydropowerList() {
        return mmsFaHydropowerList;
    }

    public void setMmsFaHydropowerList(List<MmsFaHydropower> mmsFaHydropowerList) {
        this.mmsFaHydropowerList = mmsFaHydropowerList;
    }

    @XmlTransient
    public List<MmsFaBuilding> getMmsFaBuildingList() {
        return mmsFaBuildingList;
    }

    public void setMmsFaBuildingList(List<MmsFaBuilding> mmsFaBuildingList) {
        this.mmsFaBuildingList = mmsFaBuildingList;
    }

    @XmlTransient
    public List<MmsFixedassetRegstration> getMmsFixedassetRegstrationList() {
        return mmsFixedassetRegstrationList;
    }

    public void setMmsFixedassetRegstrationList(List<MmsFixedassetRegstration> mmsFixedassetRegstrationList) {
        this.mmsFixedassetRegstrationList = mmsFixedassetRegstrationList;
    }

    @XmlTransient
    public List<FmsCostcSystemJunction> getFmsCostcSystemJunctionList() {
        if (fmsCostcSystemJunctionList == null) {
            fmsCostcSystemJunctionList = new ArrayList<>();
        }
        return fmsCostcSystemJunctionList;
    }

    public void setFmsCostcSystemJunctionList(List<FmsCostcSystemJunction> fmsCostcSystemJunctionList) {
        this.fmsCostcSystemJunctionList = fmsCostcSystemJunctionList;
    }

    @XmlTransient
    public List<FmsSystemJobJunction> getFmsSystemJobJunctionList() {
        if (fmsSystemJobJunctionList == null) {
            fmsSystemJobJunctionList = new ArrayList<>();
        }
        return fmsSystemJobJunctionList;
    }

    public void setFmsSystemJobJunctionList(List<FmsSystemJobJunction> fmsSystemJobJunctionList) {
        this.fmsSystemJobJunctionList = fmsSystemJobJunctionList;
    }

    @XmlTransient
    public List<FmsCostCenter> getFmsCostCenterList() {
        if (fmsCostCenterList == null) {
            fmsCostCenterList = new ArrayList<>();
        }
        return fmsCostCenterList;
    }

    /**
     *
     * @param fmsCostCenterList
     */
    public void setFmsCostCenterList(List<FmsCostCenter> fmsCostCenterList) {
        this.fmsCostCenterList = fmsCostCenterList;
    }

    @XmlTransient
    public List<MmsFaTransmission> getMmsFaTransmissionList() {
        return mmsFaTransmissionList;
    }

    /**
     *
     * @param mmsFaTransmissionList
     */
    public void setMmsFaTransmissionList(List<MmsFaTransmission> mmsFaTransmissionList) {
        this.mmsFaTransmissionList = mmsFaTransmissionList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (systemId != null ? systemId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof FmsLuSystem)) {
            return false;
        }
        FmsLuSystem other = (FmsLuSystem) object;
        if ((this.systemId == null && other.systemId != null) || (this.systemId != null && !this.systemId.equals(other.systemId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return systemCode;
    }

}
