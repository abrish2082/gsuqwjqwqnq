/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.entity.attendance;

import et.gov.eep.commonApplications.entity.WfHrProcessed;
import et.gov.eep.hrms.entity.employee.HrEmployees;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
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
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author meles
 */
@Entity
@Table(name = "HR_ATTENDANCES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HrAttendances.findAll", query = "SELECT h FROM HrAttendances h"),
    @NamedQuery(name = "HrAttendances.findById", query = "SELECT h FROM HrAttendances h WHERE h.id = :id"),
    @NamedQuery(name = "HrAttendances.findByYear", query = "SELECT h FROM HrAttendances h WHERE h.year = :year"),
    @NamedQuery(name = "HrAttendances.findByMonth", query = "SELECT h FROM HrAttendances h WHERE h.month = :month"),
    @NamedQuery(name = "HrAttendances.findByReportedDate", query = "SELECT h FROM HrAttendances h WHERE h.reportedDate = :reportedDate"),
    @NamedQuery(name = "HrAttendances.findByRemark", query = "SELECT h FROM HrAttendances h WHERE h.remark = :remark"),
    @NamedQuery(name = "HrAttendances.findByStatus", query = "SELECT h FROM HrAttendances h WHERE h.status = :status"),
    @NamedQuery(name = "HrAttendances.findByPreparedOn", query = "SELECT h FROM HrAttendances h WHERE h.preparedOn = :preparedOn")})
public class HrAttendances implements Serializable {

    @OneToMany(mappedBy = "attendanceId", cascade = CascadeType.ALL)
    private List<HrAttendanceDetails> hrAttendanceDetailsList;
     @OneToMany(mappedBy = "attendanceId", cascade = CascadeType.ALL)
    private List<WfHrProcessed> WfHrProcessedList;
    
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HR_ATTENDANCES_SEQ")
    @SequenceGenerator(name = "HR_ATTENDANCES_SEQ", sequenceName = "HR_ATTENDANCES_SEQ", allocationSize = 1)
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private BigDecimal id;
    @Column(name = "YEAR")
    private BigInteger year;
    @Size(max = 20)
    @Column(name = "MONTH")
    private String month;
    @Column(name = "STATUS")
    private Integer status;
    @Size(max = 20)
    @Column(name = "REPORTED_DATE")
    private String reportedDate;
    @Size(max = 20)
    @Column(name = "APPROVED_DATE")
    private String approvededDate;
    @Size(max = 20)
    @Column(name = "APPROVED_BY")
    private String approvededby;
    @Size(max = 200)
    @Column(name = "REMARK")
    private String remark;
    @Size(max = 20)
    @Column(name = "PREPARED_ON")
    private String preparedOn;
    @JoinColumn(name = "PREPARED_BY", referencedColumnName = "ID")
    @ManyToOne
    private HrEmployees preparedBy;

    public HrAttendances() {
    }

    public HrAttendances(BigDecimal id) {
        this.id = id;
    }

    public BigDecimal getId() {
        return id;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getApprovededDate() {
        return approvededDate;
    }

    public void setApprovededDate(String approvededDate) {
        this.approvededDate = approvededDate;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public BigInteger getYear() {
        return year;
    }

    public void setYear(BigInteger year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getReportedDate() {
        return reportedDate;
    }

    public void setReportedDate(String reportedDate) {
        this.reportedDate = reportedDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getApprovededby() {
        return approvededby;
    }

    public void setApprovededby(String approvededby) {
        this.approvededby = approvededby;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HrAttendances)) {
            return false;
        }
        HrAttendances other = (HrAttendances) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.eep.hrms.entity.attendance.HrAttendances[ id=" + id + " ]";
    }

    @XmlTransient
    public List<HrAttendanceDetails> getHrAttendanceDetailsList() {
        if (hrAttendanceDetailsList == null) {
            hrAttendanceDetailsList = new ArrayList<>();
        }
        return hrAttendanceDetailsList;
    }

    public void setHrAttendanceDetailsList(List<HrAttendanceDetails> hrAttendanceDetailsList) {
        this.hrAttendanceDetailsList = hrAttendanceDetailsList;
    }

    public void addDetail(HrAttendanceDetails hrsttendanceDetails) {
        if (hrsttendanceDetails.getAttendanceId() != this) {
            this.getHrAttendanceDetailsList().add(hrsttendanceDetails);
            hrsttendanceDetails.setAttendanceId(this);
        }
    }
    
     @XmlTransient

    public List<WfHrProcessed> getWfHrProcessedList() {
        return WfHrProcessedList;
    }

    public void setWfHrProcessedList(List<WfHrProcessed> WfHrProcessedList) {
        this.WfHrProcessedList = WfHrProcessedList;
    }
   

    }
