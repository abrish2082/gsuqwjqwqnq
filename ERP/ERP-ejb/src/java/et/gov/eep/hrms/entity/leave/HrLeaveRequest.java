/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.entity.leave;

import et.gov.eep.commonApplications.entity.WfHrProcessed;
import et.gov.eep.fcms.entity.admin.FmsAccountingPeriod;
import et.gov.eep.hrms.entity.employee.HrEmployees;
import java.util.List;
import java.util.ArrayList;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
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

/**
 *
 * @author user
 */
@Entity
@Table(name = "HR_LEAVE_REQUEST")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HrLeaveRequest.findAll", query = "SELECT h FROM HrLeaveRequest h"),
    @NamedQuery(name = "HrLeaveRequest.findAllPendingRequest", query = "SELECT h FROM HrLeaveRequest h where h.status1=0"),
    @NamedQuery(name = "HrLeaveRequest.findAllHRPendingRequest", query = "SELECT h FROM HrLeaveRequest h where h.status1=1 and h.status2=0"),
    @NamedQuery(name = "HrLeaveRequest.findAllEmpOnLeave", query = "SELECT h FROM HrLeaveRequest h where h.returnStatus=2"),
    @NamedQuery(name = "HrLeaveRequest.findOrderedEmployeeLeave", query = "SELECT h FROM HrLeaveRequest h INNER JOIN h.empId AS E  WHERE E.id=:emp ORDER BY h.leaveStart DESC "),
    @NamedQuery(name = "HrLeaveRequest.findById", query = "SELECT h FROM HrLeaveRequest h WHERE h.id = :id"),
    @NamedQuery(name = "HrLeaveRequest.findByUnApproveLeave", query = "SELECT h FROM HrLeaveRequest h WHERE h.empId = :empObj AND h.leaveTypesId = :leaveType AND h.status1 = :deptStatu"),

    @NamedQuery(name = "HrLeaveRequest.findByLeaveStart", query = "SELECT h FROM HrLeaveRequest h WHERE h.leaveStart = :leaveStart"),
    @NamedQuery(name = "HrLeaveRequest.findByLeaveEnd", query = "SELECT h FROM HrLeaveRequest h WHERE h.leaveEnd = :leaveEnd"),
    @NamedQuery(name = "HrLeaveRequest.findByRequestedDays", query = "SELECT h FROM HrLeaveRequest h WHERE h.requestedDays = :requestedDays"),
    @NamedQuery(name = "HrLeaveRequest.findByHalfOrFull", query = "SELECT h FROM HrLeaveRequest h WHERE h.halfOrFull = :halfOrFull"),
    @NamedQuery(name = "HrLeaveRequest.findByRequestDate", query = "SELECT h FROM HrLeaveRequest h WHERE h.requestDate = :requestDate"),
    @NamedQuery(name = "HrLeaveRequest.findByDescription", query = "SELECT h FROM HrLeaveRequest h WHERE h.description = :description"),
    @NamedQuery(name = "HrLeaveRequest.findByStatus1", query = "SELECT h FROM HrLeaveRequest h WHERE h.status1 = :status1"),
    @NamedQuery(name = "HrLeaveRequest.findByStatus2", query = "SELECT h FROM HrLeaveRequest h WHERE h.status2 = :status2"),
    @NamedQuery(name = "HrLeaveRequest.findByBalanceId", query = "SELECT h FROM HrLeaveRequest h WHERE h.balanceId = :balanceId"),
    @NamedQuery(name = "HrLeaveRequest.findByApprovedDays", query = "SELECT h FROM HrLeaveRequest h WHERE h.approvedDays = :approvedDays"),
    @NamedQuery(name = "HrLeaveRequest.findByEmpConfirm", query = "SELECT h FROM HrLeaveRequest h WHERE h.empConfirm = :empConfirm"),
    @NamedQuery(name = "HrLeaveRequest.findByUsedDays", query = "SELECT h FROM HrLeaveRequest h WHERE h.unUsedDays = :usedDays"),
    @NamedQuery(name = "HrLeaveRequest.findByReturnStatuss", query = "SELECT h FROM HrLeaveRequest h WHERE h.returnStatus = :returnStatus"),
    @NamedQuery(name = "HrLeaveRequest.searchEmpRequest", query = "SELECT h FROM HrLeaveRequest h INNER JOIN h.empId AS E INNER JOIN h.leaveTypesId AS L  WHERE E.empId=:empId AND L.id=:leaveId")})
public class HrLeaveRequest implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HR_LEAVE_REQUEST_SEQ")
    @SequenceGenerator(name = "HR_LEAVE_REQUEST_SEQ", sequenceName = "HR_LEAVE_REQUEST_SEQ", allocationSize = 1)
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private Integer id;
    @Column(name = "ATTACHMENT")
    private int attachment;
    @Size(max = 25)
    @Column(name = "LEAVE_START")
    private String leaveStart;
    @Size(max = 25)
    @Column(name = "LEAVE_END")
    private String leaveEnd;
    @Column(name = "REQUESTED_DAYS")
    private Integer requestedDays;
    @Size(max = 250)
    @Column(name = "HALF_OR_FULL")
    private String halfOrFull;
    @Size(max = 25)
    @Column(name = "REQUEST_DATE")
    private String requestDate;
    @Size(max = 250)
    @Column(name = "DESCRIPTION")
    private String description;
  
    @Column(name = "STATUS1")
    private Integer status1;
    @Column(name = "STATUS2")
    private Integer status2;

    @Column(name = "BALANCE_ID")
    private Integer balanceId;
    @Column(name = "APPROVED_DAYS")
    private Integer approvedDays;
    @Size(max = 20)
    @Column(name = "EMP_CONFIRM")
    private String empConfirm;
    @Column(name = "UN_USED_DAYS")
    private Integer unUsedDays;
    @Column(name = "RETURN_STATUS")
    private Integer returnStatus;
    @JoinColumn(name = "EMP_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrEmployees empId;
    @JoinColumn(name = "LEAVE_TYPES_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrLuLeaveTypes leaveTypesId;
    @JoinColumn(name = "LEAVE_YEAR", referencedColumnName = "ACOUNTIG_PERIOD_ID")
    @ManyToOne
    private FmsAccountingPeriod leaveYear;

    @OneToMany(mappedBy = "leaveId")
    private List<WfHrProcessed> hrWorkFlowLeaveList = new ArrayList<>();

    public HrLeaveRequest() {
    }

    public HrLeaveRequest(Integer id) {
        this.id = id;
    }

    public int getAttachment() {
        return attachment;
    }

    public void setAttachment(int attachment) {
        this.attachment = attachment;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmpConfirm() {
        return empConfirm;
    }

    public void setEmpConfirm(String empConfirm) {
        this.empConfirm = empConfirm;
    }

    public String getLeaveStart() {
        return leaveStart;
    }

    public void setLeaveStart(String leaveStart) {
        this.leaveStart = leaveStart;
    }

    public String getLeaveEnd() {
        return leaveEnd;
    }

    public void setLeaveEnd(String leaveEnd) {
        this.leaveEnd = leaveEnd;
    }

    public Integer getApprovedDays() {
        return approvedDays;
    }

    public void setApprovedDays(Integer approvedDays) {
        this.approvedDays = approvedDays;
    }

    public Integer getRequestedDays() {
        return requestedDays;
    }

    public void setRequestedDays(Integer requestedDays) {
        this.requestedDays = requestedDays;
    }

    public String getHalfOrFull() {
        return halfOrFull;
    }

    public void setHalfOrFull(String halfOrFull) {
        this.halfOrFull = halfOrFull;
    }

    public String getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(String requestDate) {
        this.requestDate = requestDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getStatus1() {
        return status1;
    }

    public void setStatus1(Integer status1) {
        this.status1 = status1;
    }

    public Integer getStatus2() {
        return status2;
    }

    public void setStatus2(Integer status2) {
        this.status2 = status2;
    }

    public Integer getBalanceId() {
        return balanceId;
    }

    public void setBalanceId(Integer balanceId) {
        this.balanceId = balanceId;
    }

    public HrEmployees getEmpId() {
        return empId;
    }

    public void setEmpId(HrEmployees empId) {
        this.empId = empId;
    }

    public Integer getReturnStatus() {
        return returnStatus;
    }

    public void setReturnStatus(Integer returnStatus) {
        this.returnStatus = returnStatus;
    }

    public Integer getUnUsedDays() {
        return unUsedDays;
    }

    public void setUnUsedDays(Integer unUsedDays) {
        this.unUsedDays = unUsedDays;
    }

    public HrLuLeaveTypes getLeaveTypesId() {
        return leaveTypesId;
    }

    public void setLeaveTypesId(HrLuLeaveTypes leaveTypesId) {
        this.leaveTypesId = leaveTypesId;
    }

    public FmsAccountingPeriod getLeaveYear() {
        return leaveYear;
    }

    public void setLeaveYear(FmsAccountingPeriod leaveYear) {
        this.leaveYear = leaveYear;
    }

    @XmlTransient
    public List<WfHrProcessed> getHrWorkFlowLeaveList() {
        return hrWorkFlowLeaveList;
    }

    public void setHrWorkFlowLeaveList(List<WfHrProcessed> hrWorkFlowLeaveList) {
        this.hrWorkFlowLeaveList = hrWorkFlowLeaveList;
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
        if (!(object instanceof HrLeaveRequest)) {
            return false;
        }
        HrLeaveRequest other = (HrLeaveRequest) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "temp.HrLeaveRequest[ id=" + id + " ]";
    }

}
