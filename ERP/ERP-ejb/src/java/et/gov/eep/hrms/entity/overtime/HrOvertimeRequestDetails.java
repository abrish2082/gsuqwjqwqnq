/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.entity.overtime;

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
@Table(name = "HR_OVERTIME_REQUEST_DETAILS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HrOvertimeRequestDetails.findAll", query = "SELECT h FROM HrOvertimeRequestDetails h"),
    @NamedQuery(name = "HrOvertimeRequestDetails.findById", query = "SELECT h FROM HrOvertimeRequestDetails h WHERE h.id = :id"),
    @NamedQuery(name = "HrOvertimeRequestDetails.findByfromDate", query = "SELECT h FROM HrOvertimeRequestDetails h WHERE h.fromDate = :fromDate"),
    @NamedQuery(name = "HrOvertimeRequestDetails.findBytoDate", query = "SELECT h FROM HrOvertimeRequestDetails h WHERE h.toDate = :toDate"),
    @NamedQuery(name = "HrOvertimeRequestDetails.findByWorkingHour", query = "SELECT h FROM HrOvertimeRequestDetails h WHERE h.workingHour = :workingHour")})
public class HrOvertimeRequestDetails implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HR_OVERTIME_REQUEST_DETAIL_SEQ")
    @SequenceGenerator(name = "HR_OVERTIME_REQUEST_DETAIL_SEQ", sequenceName = "HR_OVERTIME_REQUEST_DETAIL_SEQ", allocationSize = 1)
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private Integer id;
    @Size(max = 20)
    @Column(name = "FROM_DATE")
    private String fromDate;
    @Size(max = 20)
    @Column(name = "TO_DATE")
    private String toDate;
    @Column(name = "OT_AMOUNT")
    private double OtAmount;
    @Column(name = "WORKING_HOUR")
    private Integer workingHour;
    @JoinColumn(name = "EMP_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrEmployees empId;
    @JoinColumn(name = "OT_RATE_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrOvertimeRates otRateId;
    @JoinColumn(name = "OVERTIME_REQUEST_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrOvertimeRequests overtimeRequestId;

    public HrOvertimeRequestDetails() {
    }

    public HrOvertimeRequestDetails(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public Integer getWorkingHour() {
        return workingHour;
    }

    public void setWorkingHour(Integer workingHour) {
        this.workingHour = workingHour;
    }

    public HrEmployees getEmpId() {
        return empId;
    }

    public void setEmpId(HrEmployees empId) {
        this.empId = empId;
    }

    public HrOvertimeRates getOtRateId() {
        return otRateId;
    }

    public void setOtRateId(HrOvertimeRates otRateId) {
        this.otRateId = otRateId;
    }

    public HrOvertimeRequests getOvertimeRequestId() {
        return overtimeRequestId;
    }

    public void setOvertimeRequestId(HrOvertimeRequests overtimeRequestId) {
        this.overtimeRequestId = overtimeRequestId;
    }

    public double getOtAmount() {
        return OtAmount;
    }

    public void setOtAmount(double OtAmount) {
        this.OtAmount = OtAmount;
    }

    @Transient
    double OTPaymentamount;
    @Transient
    double Total;

    public double getTotal() {
        return Total;
    }

    public void setTotal(double Total) {
        this.Total = Total;
    }

    public double getOTPaymentamount() {
        return OTPaymentamount;
    }

    public void setOTPaymentamount(double OTPaymentamount) {
        this.OTPaymentamount = OTPaymentamount;
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
        if (!(object instanceof HrOvertimeRequestDetails)) {
            return false;
        }
        HrOvertimeRequestDetails other = (HrOvertimeRequestDetails) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.eep.hrms.entity.overtime.HrOvertimeRequestDetails[ id=" + id + " ]";
    }

}
