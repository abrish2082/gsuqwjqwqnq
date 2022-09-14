/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.entity.termination;

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
 * @author User
 */
@Entity
@Table(name = "HR_EMPLOYEE_COMPENSATION")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HrEmployeeCompensation.findAll", query = "SELECT h FROM HrEmployeeCompensation h"),
    @NamedQuery(name = "HrEmployeeCompensation.findById", query = "SELECT h FROM HrEmployeeCompensation h WHERE h.id = :id"),
    @NamedQuery(name = "HrEmployeeCompensation.findByEmployeId", query = "SELECT h FROM HrEmployeeCompensation h WHERE h.empId.id = :empid"),
    @NamedQuery(name = "HrEmployeeCompensation.findByAccruedLeaveDays", query = "SELECT h FROM HrEmployeeCompensation h WHERE h.accruedLeaveDays = :accruedLeaveDays"),
    @NamedQuery(name = "HrEmployeeCompensation.findByAccruedLeaveInBirr", query = "SELECT h FROM HrEmployeeCompensation h WHERE h.accruedLeaveInBirr = :accruedLeaveInBirr"),
    @NamedQuery(name = "HrEmployeeCompensation.findBySeveranceAmount", query = "SELECT h FROM HrEmployeeCompensation h WHERE h.severanceAmount = :severanceAmount"),
    @NamedQuery(name = "HrEmployeeCompensation.findByCompensationAmount", query = "SELECT h FROM HrEmployeeCompensation h WHERE h.compensationAmount = :compensationAmount"),
    @NamedQuery(name = "HrEmployeeCompensation.findByProcessDate", query = "SELECT h FROM HrEmployeeCompensation h WHERE h.processDate = :processDate"),
    @NamedQuery(name = "HrEmployeeCompensation.findByRemark", query = "SELECT h FROM HrEmployeeCompensation h WHERE h.remark = :remark")})
public class HrEmployeeCompensation implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HR_EMPLOYEE_COMPENSATION_SEQ")
    @SequenceGenerator(name = "HR_EMPLOYEE_COMPENSATION_SEQ", sequenceName = "HR_EMPLOYEE_COMPENSATION_SEQ", allocationSize = 1)
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private BigDecimal id;
    @Column(name = "ACCRUED_LEAVE_DAYS")
    private double accruedLeaveDays;
    @Column(name = "PREPARED_BY")
    private Integer preparedBy;
    @Column(name = "ACCRUED_LEAVE_IN_BIRR")
    private Double accruedLeaveInBirr;
    @Column(name = "SEVERANCE_AMOUNT")
    private Double severanceAmount;
    @Column(name = "FIRST_YEAR_SEV_AMOUNT")
    private Double first_year_sev_amount;
    @Column(name = "OTHER_YEARS_SEV_AMOUNT")
    private Double other_years_sev_amount;
    @Column(name = "MONTHS_SEV_AMOUNT")
    private Double months_sev_amount;
    @Column(name = "DAYS_SEV_AMOUNT")
    private Double days_sev_amount;
    @Column(name = "COMPENSATION_AMOUNT")
    private Double compensationAmount;
    @Size(max = 30)
    @Column(name = "PROCESS_DATE")
    private String processDate;
    @Size(max = 300)
    @Column(name = "REMARK")
    private String remark;
    @JoinColumn(name = "EMP_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrEmployees empId;

    @OneToMany(mappedBy = "compensationId", cascade = CascadeType.ALL)
    private List<HrEmpCompensationDetail> hrEmpCompensationDetailList;

    public HrEmployeeCompensation() {
    }

    public HrEmployeeCompensation(BigDecimal id) {
        this.id = id;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public double getAccruedLeaveDays() {
        return accruedLeaveDays;
    }

    public void setAccruedLeaveDays(double accruedLeaveDays) {
        this.accruedLeaveDays = accruedLeaveDays;
    }

    public Double getAccruedLeaveInBirr() {
        return accruedLeaveInBirr;
    }

    public void setAccruedLeaveInBirr(Double accruedLeaveInBirr) {
        this.accruedLeaveInBirr = accruedLeaveInBirr;
    }

    public Double getSeveranceAmount() {
        return severanceAmount;
    }

    public void setSeveranceAmount(Double severanceAmount) {
        this.severanceAmount = severanceAmount;
    }

    public Double getCompensationAmount() {
        return compensationAmount;
    }

    public void setCompensationAmount(Double compensationAmount) {
        this.compensationAmount = compensationAmount;
    }

    public String getProcessDate() {
        return processDate;
    }

    public void setProcessDate(String processDate) {
        this.processDate = processDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public HrEmployees getEmpId() {
        return empId;
    }

    public void setEmpId(HrEmployees empId) {
        this.empId = empId;
    }

    public Double getFirst_year_sev_amount() {
        return first_year_sev_amount;
    }

    public void setFirst_year_sev_amount(Double first_year_sev_amount) {
        this.first_year_sev_amount = first_year_sev_amount;
    }

    public Double getOther_years_sev_amount() {
        return other_years_sev_amount;
    }

    public void setOther_years_sev_amount(Double other_years_sev_amount) {
        this.other_years_sev_amount = other_years_sev_amount;
    }

    public Double getMonths_sev_amount() {
        return months_sev_amount;
    }

    public void setMonths_sev_amount(Double months_sev_amount) {
        this.months_sev_amount = months_sev_amount;
    }

    public Double getDays_sev_amount() {
        return days_sev_amount;
    }

    public void setDays_sev_amount(Double days_sev_amount) {
        this.days_sev_amount = days_sev_amount;
    }

    public Integer getPreparedBy() {
        return preparedBy;
    }

    public void setPreparedBy(Integer preparedBy) {
        this.preparedBy = preparedBy;
    }

    
    @XmlTransient
    public List<HrEmpCompensationDetail> getHrEmpCompensationDetailList() {
        if (hrEmpCompensationDetailList == null) {
            hrEmpCompensationDetailList = new ArrayList<>();
        }
        return hrEmpCompensationDetailList;
    }

    public void setHrEmpCompensationDetailList(List<HrEmpCompensationDetail> hrEmpCompensationDetailList) {
        this.hrEmpCompensationDetailList = hrEmpCompensationDetailList;
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
        if (!(object instanceof HrEmployeeCompensation)) {
            return false;
        }
        HrEmployeeCompensation other = (HrEmployeeCompensation) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.eep.hrms.entity.termination.HrEmployeeCompensation[ id=" + id + " ]";
    }

    public void adddetail(HrEmpCompensationDetail hrEmpCompensationDetail) {
        if (hrEmpCompensationDetail.getCompensationId() != this) {
            this.getHrEmpCompensationDetailList().add(hrEmpCompensationDetail);
            hrEmpCompensationDetail.setCompensationId(this);
        }
    }

}
