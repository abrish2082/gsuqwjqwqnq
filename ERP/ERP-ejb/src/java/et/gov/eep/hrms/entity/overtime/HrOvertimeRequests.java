/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.entity.overtime;

import et.gov.eep.commonApplications.entity.WfHrProcessed;
import et.gov.eep.fcms.entity.FmsLuBudgetYear;
import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.entity.organization.HrDepartments;
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
@Table(name = "HR_OVERTIME_REQUESTS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HrOvertimeRequests.findAll", query = "SELECT h FROM HrOvertimeRequests h"),
    @NamedQuery(name = "HrOvertimeRequests.findById", query = "SELECT h FROM HrOvertimeRequests h WHERE h.id = :id"),
    @NamedQuery(name = "HrOvertimeRequests.findByMonth", query = "SELECT h FROM HrOvertimeRequests h WHERE h.month = :month"),
    @NamedQuery(name = "HrOvertimeRequests.findByTypeOfWork", query = "SELECT h FROM HrOvertimeRequests h WHERE h.typeOfWork = :typeOfWork"),
    @NamedQuery(name = "HrOvertimeRequests.findByStatus", query = "SELECT h FROM HrAttendances h WHERE h.status = :status"),
    @NamedQuery(name = "HrOvertimeRequests.findByOvertimeReason", query = "SELECT h FROM HrOvertimeRequests h WHERE h.overtimeReason = :overtimeReason"),
    @NamedQuery(name = "HrOvertimeRequests.findByPreparedOn", query = "SELECT h FROM HrOvertimeRequests h WHERE h.preparedOn = :preparedOn")})
public class HrOvertimeRequests implements Serializable {

    @OneToMany(mappedBy = "overtimeId")
    private List<WfHrProcessed> wfHrProcessedList;

    @OneToMany(mappedBy = "overtimeRequestId", cascade = CascadeType.ALL)
    private List<HrOvertimeRequestDetails> hrOvertimeRequestDetailsList;
     @OneToMany(mappedBy = "overtimeId", cascade = CascadeType.ALL)
    private List<WfHrProcessed> WfHrProcessedList;
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HR_OVERTIME_PAYMENT_SEQ")
    @SequenceGenerator(name = "HR_OVERTIME_PAYMENT_SEQ", sequenceName = "HR_OVERTIME_PAYMENT_SEQ", allocationSize = 1)
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private Integer id;
    @Column(name = "MONTH")
    private Integer month;
    @Column(name = "STATUS")
    private Integer status;
    @Size(max = 100)
    @Column(name = "TYPE_OF_WORK")
    private String typeOfWork;
    @Size(max = 200)
    @Column(name = "OVERTIME_REASON")
    private String overtimeReason;
    @Size(max = 20)
    @Column(name = "PREPARED_ON")
    private String preparedOn;
    @Size(max = 20)
    @Column(name = "APPROVED_DATE")
    private String ApprovedDate;
    @Size(max = 20)
    @Column(name = "REMARK")
    private String Remark;
    @Size(max = 20)
    @Column(name = "APPROVED_BY")
    private String approvedby;
    @JoinColumn(name = "YEAR", referencedColumnName = "LU_BUDGET_YEAR_ID")
    @ManyToOne
    private FmsLuBudgetYear year;
    @JoinColumn(name = "DEPT_ID", referencedColumnName = "DEP_ID")
    @ManyToOne
    private HrDepartments deptId;
    @JoinColumn(name = "PREPARED_BY", referencedColumnName = "ID")
    @ManyToOne
    private HrEmployees preparedBy;

    public HrOvertimeRequests() {
    }

    public HrOvertimeRequests(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public String getTypeOfWork() {
        return typeOfWork;
    }

    public void setTypeOfWork(String typeOfWork) {
        this.typeOfWork = typeOfWork;
    }

    public String getOvertimeReason() {
        return overtimeReason;
    }

    public void setOvertimeReason(String overtimeReason) {
        this.overtimeReason = overtimeReason;
    }

    public String getPreparedOn() {
        return preparedOn;
    }

    public void setPreparedOn(String preparedOn) {
        this.preparedOn = preparedOn;
    }

    public HrDepartments getDeptId() {
        return deptId;
    }

    public void setDeptId(HrDepartments deptId) {
        this.deptId = deptId;
    }

    public HrEmployees getPreparedBy() {
        return preparedBy;
    }

    public void setPreparedBy(HrEmployees preparedBy) {
        this.preparedBy = preparedBy;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getApprovedDate() {
        return ApprovedDate;
    }

    public void setApprovedDate(String ApprovedDate) {
        this.ApprovedDate = ApprovedDate;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String Remark) {
        this.Remark = Remark;
    }

    public FmsLuBudgetYear getYear() {
        return year;
    }

    public void setYear(FmsLuBudgetYear year) {
        this.year = year;
    }

    public String getApprovedby() {
        return approvedby;
    }

    public void setApprovedby(String approvedby) {
        this.approvedby = approvedby;
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
        if (!(object instanceof HrOvertimeRequests)) {
            return false;
        }
        HrOvertimeRequests other = (HrOvertimeRequests) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.eep.hrms.entity.overtime.HrOvertimeRequests[ id=" + id + " ]";
    }

    @XmlTransient
    public List<HrOvertimeRequestDetails> getHrOvertimeRequestDetailsList() {
        if (hrOvertimeRequestDetailsList == null) {
            hrOvertimeRequestDetailsList = new ArrayList<>();
        }
        return hrOvertimeRequestDetailsList;
    }

    public void setHrOvertimeRequestDetailsList(List<HrOvertimeRequestDetails> hrOvertimeRequestDetailsList) {
        this.hrOvertimeRequestDetailsList = hrOvertimeRequestDetailsList;
    }

    public void addDetail(HrOvertimeRequestDetails hrOvertimeRequestDetails) {
        if (hrOvertimeRequestDetails.getOvertimeRequestId() != this) {
            this.getHrOvertimeRequestDetailsList().add(hrOvertimeRequestDetails);
            hrOvertimeRequestDetails.setOvertimeRequestId(this);
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
