/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.entity.termination;

import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.entity.transfer.HrTransferRequests;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Ob
 */
@Entity
@Table(name = "HR_CLEARANCE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HrClearance.findAll", query = "SELECT h FROM HrClearance h"),
    @NamedQuery(name = "HrClearance.findById", query = "SELECT h FROM HrClearance h WHERE h.id = :id"),
    @NamedQuery(name = "HrClearance.findByPriority", query = "SELECT h FROM HrClearanceSetting h WHERE h.status = false"),

    @NamedQuery(name = "HrClearance.findByTerminationEmp", query = "SELECT h FROM HrClearance h where h.empId.id = :emp And h.terminationId.id = :terId And h.clearanceType = :clearanceType ORDER BY h.clearanceSettingId.priority ASC"),
    @NamedQuery(name = "HrClearance.findByRetirementEmp", query = "SELECT h FROM HrClearance h where h.empId.id = :emp And h.retirementId.id = :retId And h.clearanceType = :clearanceType  ORDER BY h.clearanceSettingId.priority ASC"),
    @NamedQuery(name = "HrClearance.findByTransferEmp", query = "SELECT h FROM HrClearance h where h.empId.id = :emp And h.transferId.id = :transferId And h.clearanceType = :clearanceType  ORDER BY h.clearanceSettingId.priority ASC"),

    @NamedQuery(name = "HrClearance.findByClearanceType", query = "SELECT h FROM HrClearance h WHERE h.clearanceType = :clearanceType"),
    @NamedQuery(name = "HrClearance.findByStatus", query = "SELECT h FROM HrClearance h WHERE h.status = :status"),
    @NamedQuery(name = "HrClearance.findByTerminationStatus", query = "SELECT h FROM HrTerminationRequests h WHERE h.status = 1"),
    @NamedQuery(name = "HrClearance.findByRemark", query = "SELECT h FROM HrClearance h WHERE h.remark = :remark")})
public class HrClearance implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HR_CLEARANCES_SEQ")
    @SequenceGenerator(name = "HR_CLEARANCES_SEQ", sequenceName = "HR_CLEARANCES_SEQ", allocationSize = 1)
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private Integer id;
    @Size(max = 20)
    @Column(name = "CLEARANCE_TYPE")
    private String clearanceType;
    @Column(name = "STATUS")
    private String status;
    @Size(max = 200)
    @Column(name = "REMARK")
    private String remark;

    @JoinColumn(name = "CLEARANCE_SETTING_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrClearanceSetting clearanceSettingId;

    @JoinColumn(name = "EMP_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrEmployees empId;

    @JoinColumn(name = "RETIREMENT_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrRetirementRequest retirementId;

    @JoinColumn(name = "TERMINATION_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrTerminationRequests terminationId;

    @JoinColumn(name = "TRANSFER_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrTransferRequests transferId;

    public HrClearance() {
    }

    public HrClearance(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getClearanceType() {
        return clearanceType;
    }

    public void setClearanceType(String clearanceType) {
        this.clearanceType = clearanceType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public HrClearanceSetting getClearanceSettingId() {
        return clearanceSettingId;
    }

    public void setClearanceSettingId(HrClearanceSetting clearanceSettingId) {
        this.clearanceSettingId = clearanceSettingId;
    }

    public HrEmployees getEmpId() {
        return empId;
    }

    public void setEmpId(HrEmployees empId) {
        this.empId = empId;
    }

    public HrRetirementRequest getRetirementId() {
        return retirementId;
    }

    public void setRetirementId(HrRetirementRequest retirementId) {
        this.retirementId = retirementId;
    }

    public HrTerminationRequests getTerminationId() {
        return terminationId;
    }

    public void setTerminationId(HrTerminationRequests terminationId) {
        this.terminationId = terminationId;
    }

    public HrTransferRequests getTransferId() {
        return transferId;
    }

    public void setTransferId(HrTransferRequests transferId) {
        this.transferId = transferId;
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
        if (!(object instanceof HrClearance)) {
            return false;
        }
        HrClearance other = (HrClearance) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.HrClearance[ id=" + id + " ]";
    }

}
