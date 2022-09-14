/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.entity.leave;

import et.gov.eep.hrms.entity.employee.HrEmployees;
import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author user
 */
@Entity
@Table(name = "HR_LEAVE_SCHEDULE_TRANSFER")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HrLeaveScheduleTransfer.findAll", query = "SELECT h FROM HrLeaveScheduleTransfer h"),
    @NamedQuery(name = "HrLeaveScheduleTransfer.findAllRequests", query = "SELECT h FROM HrLeaveScheduleTransfer h WHERE h.status='0'"),
    @NamedQuery(name = "HrLeaveScheduleTransfer.findById", query = "SELECT h FROM HrLeaveScheduleTransfer h WHERE h.id = :id"),
    @NamedQuery(name = "HrLeaveScheduleTransfer.checkExistance", query = "SELECT h FROM HrLeaveScheduleTransfer h WHERE h.scheduleId.id = :sche AND h.status='0'"),
    @NamedQuery(name = "HrLeaveScheduleTransfer.findByMonthTo", query = "SELECT h FROM HrLeaveScheduleTransfer h WHERE h.monthTo = :monthTo"),
    @NamedQuery(name = "HrLeaveScheduleTransfer.findByRequestDate", query = "SELECT h FROM HrLeaveScheduleTransfer h WHERE h.requestDate = :requestDate"),
    @NamedQuery(name = "HrLeaveScheduleTransfer.findByApprovedDate", query = "SELECT h FROM HrLeaveScheduleTransfer h WHERE h.approvedDate = :approvedDate"),
    @NamedQuery(name = "HrLeaveScheduleTransfer.findByStatus", query = "SELECT h FROM HrLeaveScheduleTransfer h WHERE h.status = :status")})
public class HrLeaveScheduleTransfer implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    
    @Column(name = "MONTH_TO")
    private String monthTo;
    
    @Column(name = "REQUEST_DATE")
    @Temporal(TemporalType.DATE)    
    private Date requestDate;
    
    @Column(name = "APPROVED_DATE")
    @Temporal(TemporalType.DATE)
    private Date approvedDate;
    
    @Column(name = "STATUS")
    private String status;
    
    @Column(name = "DESCRIPTIONREQUESTER")
    private String descriptionrequester;
    
    @Column(name = "DESCRIPTIONAPPROVER")
    private String descriptionapprover;
    
    @JoinColumn(name = "REQUESTED_BY", referencedColumnName = "EMP_ID")
    @ManyToOne
    private HrEmployees requestedBy;
    
    @JoinColumn(name = "APPROVED_BY", referencedColumnName = "EMP_ID")
    @ManyToOne
    private HrEmployees approvedBy;
    
    @JoinColumn(name = "SCHEDULE_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrLeaveSchedule scheduleId;

    public HrLeaveScheduleTransfer() {
    }

    public HrLeaveScheduleTransfer(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMonthTo() {
        return monthTo;
    }

    public String getDescriptionrequester() {
        return descriptionrequester;
    }

    public void setDescriptionrequester(String descriptionrequester) {
        this.descriptionrequester = descriptionrequester;
    }

    public String getDescriptionapprover() {
        return descriptionapprover;
    }

    public void setDescriptionapprover(String descriptionapprover) {
        this.descriptionapprover = descriptionapprover;
    }

    public HrLeaveSchedule getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(HrLeaveSchedule scheduleId) {
        this.scheduleId = scheduleId;
    }

    public void setMonthTo(String monthTo) {
        this.monthTo = monthTo;
    }

    public Date getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(Date requestDate) {
        this.requestDate = requestDate;
    }

    public Date getApprovedDate() {
        return approvedDate;
    }

    public void setApprovedDate(Date approvedDate) {
        this.approvedDate = approvedDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public HrEmployees getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(HrEmployees approvedBy) {
        this.approvedBy = approvedBy;
    }

    public HrEmployees getRequestedBy() {
        return requestedBy;
    }

    public void setRequestedBy(HrEmployees requestedBy) {
        this.requestedBy = requestedBy;
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
        if (!(object instanceof HrLeaveScheduleTransfer)) {
            return false;
        }
        HrLeaveScheduleTransfer other = (HrLeaveScheduleTransfer) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return id + "--" + requestedBy.getFirstName() + " " + requestedBy.getMiddleName();
    }

}
