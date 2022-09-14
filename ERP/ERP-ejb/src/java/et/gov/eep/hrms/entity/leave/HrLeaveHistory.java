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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author user
 */
@Entity
@Table(name = "HR_LEAVE_HISTORY")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HrLeaveHistory.findAll", query = "SELECT h FROM HrLeaveHistory h"),
    @NamedQuery(name = "HrLeaveHistory.findById", query = "SELECT h FROM HrLeaveHistory h WHERE h.id = :id"),
    //@NamedQuery(name = "HrLeaveHistory.SearchLeaveHistory", query = "SELECT h FROM HrLeaveHistory h WHERE h.empId =: empId"),
    //@NamedQuery(name = "HrLeaveHistory.searchHistry", query = "SELECT h FROM HrLeaveHistory h WHERE h.empId = :empId"),
    
    @NamedQuery(name = "HrLeaveHistory.findByYear", query = "SELECT h FROM HrLeaveHistory h WHERE h.year = :year"),
    @NamedQuery(name = "HrLeaveHistory.findByFromDate", query = "SELECT h FROM HrLeaveHistory h WHERE h.fromDate = :fromDate"),
    @NamedQuery(name = "HrLeaveHistory.findByToDate", query = "SELECT h FROM HrLeaveHistory h WHERE h.toDate = :toDate"),
    @NamedQuery(name = "HrLeaveHistory.findByAvailableDay", query = "SELECT h FROM HrLeaveHistory h WHERE h.availableDay = :availableDay"),
    @NamedQuery(name = "HrLeaveHistory.findByStatus", query = "SELECT h FROM HrLeaveHistory h WHERE h.status = :status"),
    @NamedQuery(name = "HrLeaveHistory.findByApprovedBy", query = "SELECT h FROM HrLeaveHistory h WHERE h.approvedBy = :approvedBy"),
    @NamedQuery(name = "HrLeaveHistory.findByDescription", query = "SELECT h FROM HrLeaveHistory h WHERE h.description = :description"),
    @NamedQuery(name = "HrLeaveHistory.findByPayment", query = "SELECT h FROM HrLeaveHistory h WHERE h.payment = :payment"),
    @NamedQuery(name = "HrLeaveHistory.findByExpiry", query = "SELECT h FROM HrLeaveHistory h WHERE h.expiry = :expiry")})
public class HrLeaveHistory implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Size(max = 20)
    @Column(name = "YEAR")
    private String year;
    @Column(name = "FROM_DATE")
    @Temporal(TemporalType.DATE)
    private Date fromDate;
    @Column(name = "TO_DATE")
    @Temporal(TemporalType.DATE)
    private Date toDate;
    @Column(name = "AVAILABLE_DAY")
    private Integer availableDay;
    @Size(max = 20)
    @Column(name = "STATUS")
    private String status;
    @Size(max = 20)
    @Column(name = "APPROVED_BY")
    private String approvedBy;
    @Size(max = 20)
    @Column(name = "DESCRIPTION")
    private String description;
    @Size(max = 20)
    @Column(name = "PAYMENT")
    private String payment;
    @Size(max = 20)
    @Column(name = "EXPIRY")
    private String expiry;
    @Column(name = "REQUESTED_DATE")
    private Integer requestedDate;
    @JoinColumn(name = "EMP_ID", referencedColumnName = "EMP_ID")
    @ManyToOne
    private HrEmployees empId;
    @JoinColumn(name = "LEAVE_TYPE_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrLuLeaveTypes leaveTypeId;

    public HrLeaveHistory() {
    }

    public Integer getRequestedDate() {
        return requestedDate;
    }

    public void setRequestedDate(Integer requestedDate) {
        this.requestedDate = requestedDate;
    }

    public HrLeaveHistory(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public Integer getAvailableDay() {
        return availableDay;
    }

    public void setAvailableDay(Integer availableDay) {
        this.availableDay = availableDay;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(String approvedBy) {
        this.approvedBy = approvedBy;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public String getExpiry() {
        return expiry;
    }

    public void setExpiry(String expiry) {
        this.expiry = expiry;
    }

    public HrEmployees getEmpId() {
        return empId;
    }

    public void setEmpId(HrEmployees empId) {
        this.empId = empId;
    }

    public HrLuLeaveTypes getLeaveTypeId() {
        return leaveTypeId;
    }

    public void setLeaveTypeId(HrLuLeaveTypes leaveTypeId) {
        this.leaveTypeId = leaveTypeId;
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
        if (!(object instanceof HrLeaveHistory)) {
            return false;
        }
        HrLeaveHistory other = (HrLeaveHistory) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity2.HrLeaveHistory[ id=" + id + " ]";
    }

}
