/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.entity.EmployeeBonus;

import et.gov.eep.commonApplications.entity.WfHrProcessed;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "HR_EMPLOYEES_BONUS" )
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HrEmployeesBonus.findAll", query = "SELECT h FROM HrEmployeesBonus h"),
    @NamedQuery(name = "HrEmployeesBonus.findById", query = "SELECT h FROM HrEmployeesBonus h WHERE h.id = :id"),
    @NamedQuery(name = "HrEmployeesBonus.statusactive", query = "SELECT h FROM HrEmployees h WHERE h.empStatus = :status OR h.empStatus =:status2"),
    @NamedQuery(name = "HrEmployeesBonus.findByBudgetYear", query = "SELECT h FROM HrEmployeesBonus h WHERE h.budgetYear = :budgetYear"),
    @NamedQuery(name = "HrEmployeesBonus.findByAmount", query = "SELECT h FROM HrEmployeesBonus h WHERE h.amount = :amount"),
    @NamedQuery(name = "HrEmployeesBonus.findByStatus", query = "SELECT h FROM HrEmployeesBonus h WHERE h.status = :status")})
public class HrEmployeesBonus implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HR_EMPLOYEES_BONUS_SQN")
    @SequenceGenerator(name = "HR_EMPLOYEES_BONUS_SQN", sequenceName = "HR_EMPLOYEES_BONUS_SQN", allocationSize = 1)
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private BigDecimal id;
    @Size(max = 20)
    @Column(name = "BUDGET_YEAR")
    private String budgetYear;
    @Size(max = 20)
    @Column(name = "APPROVED_DATE")
    private String approveddate;
    @Size(max = 100)
    @Column(name = "REMARK")
    private String remark;
    @Size(max = 100)
    @Column(name = "REFERENCE_NO")
    private String referenceNo;
    @Size(max = 100)
    @Column(name = "DATEOF_PERMISSION")
    private String dateOfPermission;
    @Size(max = 20)
    @Column(name = "AMOUNT")
    private String amount;
    @Size(max = 20)
    @Column(name = "APPROVED_BY")
    private String approvededby;
    @Size(max = 20)
    @Column(name = "REQUEST_DATE")
    private String requestdate;
    @Column(name = "STATUS")
    private Integer status;
    @Column(name = "EPERIANCE_MONTH")
    private Double experiancemonth;
    @OneToMany(mappedBy = "bonusId", cascade = CascadeType.ALL)
    private List<HrEmployeesBonusDetail> hrEmployeesBonusDetailList;
    @OneToMany(mappedBy = "bonusId", cascade = CascadeType.ALL)
    private List<WfHrProcessed> WfHrProcessedList;

    public HrEmployeesBonus() {
    }

    public HrEmployeesBonus(BigDecimal id) {
        this.id = id;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getBudgetYear() {
        return budgetYear;
    }

    public void setBudgetYear(String budgetYear) {
        this.budgetYear = budgetYear;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getApproveddate() {
        return approveddate;
    }

    public void setApproveddate(String approveddate) {
        this.approveddate = approveddate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getRequestdate() {
        return requestdate;
    }

    public String getApprovededby() {
        return approvededby;
    }

    public void setApprovededby(String approvededby) {
        this.approvededby = approvededby;
    }

    public void setRequestdate(String requestdate) {
        this.requestdate = requestdate;
    }

    public Double getExperiancemonth() {
        return experiancemonth;
    }

    public String getReferenceNo() {
        return referenceNo;
    }

    public void setReferenceNo(String referenceNo) {
        this.referenceNo = referenceNo;
    }

    public String getDateOfPermission() {
        return dateOfPermission;
    }

    public void setDateOfPermission(String dateOfPermission) {
        this.dateOfPermission = dateOfPermission;
    }

    public void setExperiancemonth(Double experiancemonth) {
        this.experiancemonth = experiancemonth;
    }

    @XmlTransient
    public List<HrEmployeesBonusDetail> getHrEmployeesBonusDetailList() {
        if (hrEmployeesBonusDetailList == null) {
            hrEmployeesBonusDetailList = new ArrayList<>();
        }
        return hrEmployeesBonusDetailList;
    }

    public void setHrEmployeesBonusDetailList(List<HrEmployeesBonusDetail> hrEmployeesBonusDetailList) {
        this.hrEmployeesBonusDetailList = hrEmployeesBonusDetailList;
    }

    @XmlTransient
    public List<WfHrProcessed> getWfHrProcessedList() {
        return WfHrProcessedList;
    }

    public void setWfHrProcessedList(List<WfHrProcessed> WfHrProcessedList) {
        this.WfHrProcessedList = WfHrProcessedList;
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
        if (!(object instanceof HrEmployeesBonus)) {
            return false;
        }
        HrEmployeesBonus other = (HrEmployeesBonus) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return id.toString();
    }

    public void addDetail(HrEmployeesBonusDetail hrEmployeesBonusDetail) {
        if (hrEmployeesBonusDetail.getBonusId() != this) {
            this.getHrEmployeesBonusDetailList().add(hrEmployeesBonusDetail);
            hrEmployeesBonusDetail.setBonusId(this);
        }

    }

}
