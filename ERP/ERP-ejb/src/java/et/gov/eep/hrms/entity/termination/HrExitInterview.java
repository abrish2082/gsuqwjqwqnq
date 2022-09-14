/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.entity.termination;

import et.gov.eep.hrms.entity.employee.HrEmployees;
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
@Table(name = "HR_EXIT_INTERVIEW")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HrExitInterview.findAll", query = "SELECT h FROM HrExitInterview h"),
    @NamedQuery(name = "HrExitInterview.findById", query = "SELECT h FROM HrExitInterview h WHERE h.id = :id"),

    @NamedQuery(name = "HrExitInterview.findByEmpId", query = "SELECT h FROM HrExitInterview h WHERE UPPER(h.terminationRequestId.empId.empId) LIKE :empId"),
    @NamedQuery(name = "HrExitInterview.findByName", query = "SELECT h FROM HrExitInterview h WHERE UPPER(h.terminationRequestId.empId.firstName) LIKE :firstName"),
    @NamedQuery(name = "HrExitInterview.findByEmpIdAndName", query = "SELECT h FROM HrExitInterview h WHERE UPPER(h.terminationRequestId.empId.firstName) LIKE :firstName and UPPER(h.terminationRequestId.empId.empId) LIKE :empId"),
    @NamedQuery(name = "HrExitInterview.findToCheckDuplicate", query = "SELECT h FROM HrExitInterview h WHERE h.terminationRequestId.empId = :empId OR h.leaveReason = :leaveReason AND h.otherReason = :otherReason AND h.preparedOn = :preparedOn AND h.remark = :remark"),

    @NamedQuery(name = "HrExitInterview.findByLeaveReason", query = "SELECT h FROM HrExitInterview h WHERE h.leaveReason = :leaveReason"),
    @NamedQuery(name = "HrExitInterview.findByOtherReason", query = "SELECT h FROM HrExitInterview h WHERE h.otherReason = :otherReason"),
    @NamedQuery(name = "HrExitInterview.findByPreparedDate", query = "SELECT h FROM HrExitInterview h WHERE h.preparedOn = :preparedOn"),
    @NamedQuery(name = "HrExitInterview.findByRemark", query = "SELECT h FROM HrExitInterview h WHERE h.remark = :remark")})
public class HrExitInterview implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HR_EXIT_INTERVIEW_SEQ")
    @SequenceGenerator(name = "HR_EXIT_INTERVIEW_SEQ", sequenceName = "HR_EXIT_INTERVIEW_SEQ", allocationSize = 1)
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private Integer id;
    @Size(max = 100)
    @Column(name = "LEAVE_REASON")
    private String leaveReason;
    @Size(max = 200)
    @Column(name = "OTHER_REASON")
    private String otherReason;
    @Size(max = 20)
    @Column(name = "PREPARED_ON")
    private String preparedOn;
    @Size(max = 200)
    @Column(name = "REMARK")
    private String remark;

    @JoinColumn(name = "PREPARED_BY", referencedColumnName = "ID")
    @ManyToOne
    private HrEmployees preparedBy;

    @JoinColumn(name = "TERMINATION_REQUEST_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrTerminationRequests terminationRequestId;

    public HrExitInterview() {
    }

    public HrExitInterview(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLeaveReason() {
        return leaveReason;
    }

    public void setLeaveReason(String leaveReason) {
        this.leaveReason = leaveReason;
    }

    public String getOtherReason() {
        return otherReason;
    }

    public void setOtherReason(String otherReason) {
        this.otherReason = otherReason;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getPreparedOn() {
        return preparedOn;
    }

    public void setPreparedOn(String preparedOn) {
        this.preparedOn = preparedOn;
    }

    public HrEmployees getPreparedBy() {
        return preparedBy;
    }

    public void setPreparedBy(HrEmployees preparedBy) {
        this.preparedBy = preparedBy;
    }

    public HrTerminationRequests getTerminationRequestId() {
        return terminationRequestId;
    }

    public void setTerminationRequestId(HrTerminationRequests terminationRequestId) {
        this.terminationRequestId = terminationRequestId;
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
        if (!(object instanceof HrExitInterview)) {
            return false;
        }
        HrExitInterview other = (HrExitInterview) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.HrExitInterview[ id=" + id + " ]";
    }

}
