/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.entity.attendance;

import et.gov.eep.hrms.entity.employee.HrEmployees;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
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
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author meles
 */
@Entity
@Table(name = "HR_ATTENDANCE_DETAILS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HrAttendanceDetails.findAll", query = "SELECT h FROM HrAttendanceDetails h"),
    @NamedQuery(name = "HrAttendanceDetails.findById", query = "SELECT h FROM HrAttendanceDetails h WHERE h.id = :id"),
    @NamedQuery(name = "HrAttendanceDetails.findByNoOfDaysAbsent", query = "SELECT h FROM HrAttendanceDetails h WHERE h.noOfDaysAbsent = :noOfDaysAbsent"),
    @NamedQuery(name = "HrAttendanceDetails.findByDateOfAbsence", query = "SELECT h FROM HrAttendanceDetails h WHERE h.dateOfAbsence = :dateOfAbsence"),
    @NamedQuery(name = "HrAttendanceDetails.findByLateMinute", query = "SELECT h FROM HrAttendanceDetails h WHERE h.lateMinute = :lateMinute"),
    @NamedQuery(name = "HrAttendanceDetails.findByRemark", query = "SELECT h FROM HrAttendanceDetails h WHERE h.remark = :remark"),
    @NamedQuery(name = "HrAttendanceDetails.findByReasonForAbsence", query = "SELECT h FROM HrAttendanceDetails h WHERE h.reasonForAbsence = :reasonForAbsence"),
    @NamedQuery(name = "HrAttendanceDetails.findByStatus", query = "SELECT h FROM HrAttendanceDetails h WHERE h.status = :status")})
public class HrAttendanceDetails implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HR_ATTENDANCE_DETAILS_SEQ")
    @SequenceGenerator(name = "HR_ATTENDANCE_DETAILS_SEQ", sequenceName = "HR_ATTENDANCE_DETAILS_SEQ", allocationSize = 1)
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private BigDecimal id;
    @Column(name = "NO_OF_DAYS_ABSENT")
    private BigInteger noOfDaysAbsent;
    @Size(max = 200)
    @Column(name = "DATE_OF_ABSENCE")
    private String dateOfAbsence;
    @Size(max = 20)
    @Column(name = "LATE_MINUTE")
    private String lateMinute;
    @Size(max = 200)
    @Column(name = "REMARK")
    private String remark;
    @Size(max = 20)
    @Column(name = "REASON_FOR_ABSENCE")
    private String reasonForAbsence;
    @Column(name = "STATUS")
    private BigInteger status;
    @Column(name = "EARLY_OUT")
    private BigInteger earlyOut;
    @JoinColumn(name = "ATTENDANCE_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrAttendances attendanceId;
    @JoinColumn(name = "EMP_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrEmployees empId;

    public HrAttendanceDetails() {
    }

    public HrAttendanceDetails(BigDecimal id) {
        this.id = id;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public BigInteger getNoOfDaysAbsent() {
        return noOfDaysAbsent;
    }

    public void setNoOfDaysAbsent(BigInteger noOfDaysAbsent) {
        this.noOfDaysAbsent = noOfDaysAbsent;
    }

    public String getDateOfAbsence() {
        return dateOfAbsence;
    }

    public void setDateOfAbsence(String dateOfAbsence) {
        this.dateOfAbsence = dateOfAbsence;
    }

    public String getLateMinute() {
        return lateMinute;
    }

    public void setLateMinute(String lateMinute) {
        this.lateMinute = lateMinute;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getReasonForAbsence() {
        return reasonForAbsence;
    }

    public void setReasonForAbsence(String reasonForAbsence) {
        this.reasonForAbsence = reasonForAbsence;
    }

    public BigInteger getStatus() {
        return status;
    }

    public void setStatus(BigInteger status) {
        this.status = status;
    }

    public HrAttendances getAttendanceId() {
        return attendanceId;
    }

    public void setAttendanceId(HrAttendances attendanceId) {
        this.attendanceId = attendanceId;
    }

    public HrEmployees getEmpId() {
        return empId;
    }

    public void setEmpId(HrEmployees empId) {
        this.empId = empId;
    }

    public BigInteger getEarlyOut() {
        return earlyOut;
    }

    public void setEarlyOut(BigInteger earlyOut) {
        this.earlyOut = earlyOut;
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
        if (!(object instanceof HrAttendanceDetails)) {
            return false;
        }
        HrAttendanceDetails other = (HrAttendanceDetails) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.eep.hrms.entity.attendance.HrAttendanceDetails[ id=" + id + " ]";
    }

    }
