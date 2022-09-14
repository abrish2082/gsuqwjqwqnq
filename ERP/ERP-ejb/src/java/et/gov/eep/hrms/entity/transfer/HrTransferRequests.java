/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.entity.transfer;

import et.gov.eep.commonApplications.entity.WfHrProcessed;
import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.entity.lookup.HrLuTransferTypes;
import et.gov.eep.hrms.entity.organization.HrDepartments;
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

/**
 *
 * @author INSA
 */
@Entity
@Table(name = "HR_TRANSFER_REQUESTS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HrTransferRequests.findAll", query = "SELECT h FROM HrTransferRequests h WHERE h.status=0"),
    @NamedQuery(name = "HrTransferRequests.findById", query = "SELECT h FROM HrTransferRequests h WHERE h.id = :id"),

    @NamedQuery(name = "HrTransferRequests.findByEmpId", query = "SELECT h FROM HrTransferRequests h WHERE UPPER(h.requesterId.empId) LIKE :empId and h.status =0"),
    @NamedQuery(name = "HrTransferRequests.findByName", query = "SELECT h FROM HrTransferRequests h WHERE UPPER(h.requesterId.firstName) LIKE :firstName and h.status =0"),
    @NamedQuery(name = "HrTransferRequests.findByOne", query = "SELECT h FROM HrTransferRequests h WHERE h.requesterId.firstName = :firstName or h.requesterId.empId = :empId and h.status =0"),
    @NamedQuery(name = "HrTransferRequests.findByTwo", query = "SELECT h FROM HrTransferRequests h WHERE UPPER(h.requesterId.firstName) LIKE :firstName and UPPER(h.requesterId.empId) LIKE :empId and h.status =0"),
    @NamedQuery(name = "HrTransferRequests.checkDuplicate", query = "SELECT h FROM HrTransferRequests h WHERE h.requesterId.empId =:empId and h.status = 0"),
    @NamedQuery(name = "HrTransferRequests.findApproved", query = "SELECT h FROM HrTransferRequests h WHERE h.requesterId.id = :empId and h.status =1 ORDER BY h.approveDate"),
    @NamedQuery(name = "HrTransferRequests.findByReason", query = "SELECT h FROM HrTransferRequests h WHERE h.reason = :reason"),
    @NamedQuery(name = "HrTransferRequests.findByStatus", query = "SELECT h FROM HrTransferRequests h WHERE h.status = :status"),
    @NamedQuery(name = "HrTransferRequests.findByStatusApprove", query = "SELECT h FROM HrTransferRequests h WHERE h.status != :status"),
    @NamedQuery(name = "HrTransferRequests.findPreparedList", query = "SELECT h FROM HrTransferRequests h WHERE h.status = 0 ORDER BY h.requestDate DESC"),
    @NamedQuery(name = "HrTransferRequests.findByStartDate", query = "SELECT h FROM HrTransferRequests h WHERE h.startDate = :startDate"),
    @NamedQuery(name = "HrTransferRequests.findByEndDate", query = "SELECT h FROM HrTransferRequests h WHERE h.endDate = :endDate"),
    @NamedQuery(name = "HrTransferRequests.findByDecision", query = "SELECT h FROM HrTransferRequests h WHERE h.status = :decision"),
    @NamedQuery(name = "HrTransferRequests.findByRemark", query = "SELECT h FROM HrTransferRequests h WHERE h.remark = :remark")})
public class HrTransferRequests implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HR_TRANSFER_REQUEST_SEQ")
    @SequenceGenerator(name = "HR_TRANSFER_REQUEST_SEQ", sequenceName = "HR_TRANSFER_REQUEST_SEQ", allocationSize = 1)
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private Integer id;
    @Column(name = "REQUEST_DATE")
    private String requestDate;
    @Size(max = 200)
    @Column(name = "REASON")
    private String reason;
    @Column(name = "STATUS")
    private Integer status;
    @Column(name = "START_DATE")
    private String startDate;
    @Column(name = "END_DATE")
    private String endDate;
    @Size(max = 20)
    @Column(name = "APPROVE_DATE")
    private String approveDate;
    @Column(name = "PREPARED_BY")
    private String preparedBy;
    @Size(max = 20)
    @Column(name = "EFFECTIVE_DATE")
    private String effectiveDate;
    @Size(max = 20)
    @Column(name = "REMARK")
    private String remark;
    @JoinColumn(name = "TRANSFER_TO", referencedColumnName = "DEP_ID")
    @ManyToOne
    private HrDepartments transferTo;
    @JoinColumn(name = "REQUESTER_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrEmployees requesterId;
    @JoinColumn(name = "TRANSFER_TYPE_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrLuTransferTypes transferTypeId;

    @OneToMany(mappedBy = "transferId", cascade = CascadeType.ALL)
    private List<WfHrProcessed> wfHrProcessedList = new ArrayList<>();

    public HrTransferRequests() {
    }

    public HrTransferRequests(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(String requestDate) {
        this.requestDate = requestDate;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getPreparedBy() {
        return preparedBy;
    }

    public void setPreparedBy(String preparedBy) {
        this.preparedBy = preparedBy;
    }
    
    public String getApproveDate() {
        return approveDate;
    }

    public void setApproveDate(String approveDate) {
        this.approveDate = approveDate;
    }

    public String getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(String effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public HrDepartments getTransferTo() {
        return transferTo;
    }

    public void setTransferTo(HrDepartments transferTo) {
        this.transferTo = transferTo;
    }

    public HrEmployees getRequesterId() {
        return requesterId;
    }

    public void setRequesterId(HrEmployees requesterId) {
        this.requesterId = requesterId;
    }

    public HrLuTransferTypes getTransferTypeId() {
        return transferTypeId;
    }

    public void setTransferTypeId(HrLuTransferTypes transferTypeId) {
        this.transferTypeId = transferTypeId;
    }

    public List<WfHrProcessed> getWfHrProcessedList() {
        if (wfHrProcessedList == null) {
            wfHrProcessedList = new ArrayList<>();
        }
        return wfHrProcessedList;
    }

    public void setWfHrProcessedList(List<WfHrProcessed> wfHrProcessedList) {
        this.wfHrProcessedList = wfHrProcessedList;
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
        if (!(object instanceof HrTransferRequests)) {
            return false;
        }
        HrTransferRequests other = (HrTransferRequests) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return id + " -- " + requesterId.getFirstName() + " " + requesterId.getMiddleName();
    }

}
