/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.entity.leave;

import et.gov.eep.fcms.entity.FmsLuBudgetYear;
import et.gov.eep.hrms.entity.employee.HrEmployees;
import java.io.Serializable;
import java.util.ArrayList;
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

/**
 *
 * @author Desu
 */
@Entity
@Table(name = "HR_LEAVE_SCHEDULE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HrLeaveSchedule.findAll", query = "SELECT h FROM HrLeaveSchedule h"),
    @NamedQuery(name = "HrLeaveSchedule.findAllRequests", query = "SELECT h FROM HrLeaveSchedule h where h.status='Pending'"),
    @NamedQuery(name = "HrLeaveSchedule.findById", query = "SELECT h FROM HrLeaveSchedule h WHERE h.id = :schId"),
    @NamedQuery(name = "HrLeaveSchedule.findByBugdetYearAndEmp", query = "SELECT h FROM HrLeaveSchedule h WHERE h.leaveYearId = :fmsLuBudgetYear And h.employeeId = :employee"),
    @NamedQuery(name = "HrLeaveSchedule.findByEmployeeIdAndYear", query = "SELECT h FROM HrLeaveSchedule h WHERE h.employeeId.empId = :employeeId And h.leaveYearId.luBudgetYearId=:scheduleyear"),
    @NamedQuery(name = "HrLeaveSchedule.findByStatus", query = "SELECT h FROM HrLeaveSchedule h WHERE h.status = :status")})
public class HrLeaveSchedule implements Serializable {

    @JoinColumn(name = "EMP_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrEmployees employeeId;

    private static final long serialVersionUID = 1L;
    @Id

    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HR_LEAVE_SCHEDULE_SEQ")
    @SequenceGenerator(name = "HR_LEAVE_SCHEDULE_SEQ", sequenceName = "HR_LEAVE_SCHEDULE_SEQ", allocationSize = 1)
    private Integer id;
    @Size(max = 20)
    @Column(name = "STATUS")
    private String status;

    @Column(name = "DESCRIPTION")
    private String description;

    @OneToMany(mappedBy = "scheduleId", cascade = CascadeType.ALL)
    private List<HrLeaveScheduleDet> hrLeaveScheduleDetList;

    @JoinColumn(name = "LEAVE_YEAR_ID", referencedColumnName = "LU_BUDGET_YEAR_ID")
    @ManyToOne
    private FmsLuBudgetYear leaveYearId;
//    
//    @JoinColumn(name = "EMPLOYEE_ID", referencedColumnName = "ID")
//    @ManyToOne
//    private HrEmployees employeeId;
//    
    @OneToMany(mappedBy = "scheduleId")
    private List<HrLeaveScheduleTransfer> hrLeaveScheduleTransferList;

    public HrLeaveSchedule() {
    }

    public HrLeaveSchedule(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<HrLeaveScheduleTransfer> getHrLeaveScheduleTransferList() {
        return hrLeaveScheduleTransferList;
    }

    public void setHrLeaveScheduleTransferList(List<HrLeaveScheduleTransfer> hrLeaveScheduleTransferList) {
        this.hrLeaveScheduleTransferList = hrLeaveScheduleTransferList;
    }

    @XmlTransient
    public List<HrLeaveScheduleDet> getHrLeaveScheduleDetList() {
        if (hrLeaveScheduleDetList == null) {
            hrLeaveScheduleDetList = new ArrayList<>();
        }
        return hrLeaveScheduleDetList;
    }

    public void setHrLeaveScheduleDetList(List<HrLeaveScheduleDet> hrLeaveScheduleDetList) {
        this.hrLeaveScheduleDetList = hrLeaveScheduleDetList;
    }
//

    public FmsLuBudgetYear getLeaveYearId() {
        return leaveYearId;
    }

    public void setLeaveYearId(FmsLuBudgetYear leaveYearId) {
        this.leaveYearId = leaveYearId;
    }

    public HrEmployees getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(HrEmployees employeeId) {
        this.employeeId = employeeId;
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
        if (!(object instanceof HrLeaveSchedule)) {
            return false;
        }
        HrLeaveSchedule other = (HrLeaveSchedule) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return id.toString();
    }

    public void addToDetail(HrLeaveScheduleDet leaveScheduleDetile) {
        if (leaveScheduleDetile.getScheduleId() != this) {
            this.getHrLeaveScheduleDetList().add(leaveScheduleDetile);
            leaveScheduleDetile.setScheduleId(this);
        }
    }

}
