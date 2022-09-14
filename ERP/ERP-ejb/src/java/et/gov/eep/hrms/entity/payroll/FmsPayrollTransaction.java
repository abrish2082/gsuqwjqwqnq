/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.entity.payroll;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Hanoc
 */
@Entity
@Table(name = "FMS_PAYROLL_TRANSACTION")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FmsPayrollTransaction.findAll", query = "SELECT f FROM FmsPayrollTransaction f"),
    @NamedQuery(name = "FmsPayrollTransaction.findById", query = "SELECT f FROM FmsPayrollTransaction f WHERE f.id = :id"),
    @NamedQuery(name = "FmsPayrollTransaction.findByEmpId", query = "SELECT f FROM FmsPayrollTransaction f WHERE LOWER (f.empId) LIKE :empId AND f.payrollperiodDate = :payrollperiodDate AND f.status = :status"),
    @NamedQuery(name = "FmsPayrollTransaction.findPayTractionByEmpId", query = "SELECT f FROM FmsPayrollTransaction f WHERE LOWER (f.empId) = :empId "),
    @NamedQuery(name = "FmsPayrollTransaction.findByFullName", query = "SELECT f FROM FmsPayrollTransaction f WHERE f.fullName = :fullName"),
    @NamedQuery(name = "FmsPayrollTransaction.findBySalary", query = "SELECT f FROM FmsPayrollTransaction f WHERE f.salary = :salary"),
    @NamedQuery(name = "FmsPayrollTransaction.findByTransport", query = "SELECT f FROM FmsPayrollTransaction f WHERE f.transport = :transport"),
    @NamedQuery(name = "FmsPayrollTransaction.findByOtherEarning", query = "SELECT f FROM FmsPayrollTransaction f WHERE f.otherEarning = :otherEarning"),
    @NamedQuery(name = "FmsPayrollTransaction.findByTax", query = "SELECT f FROM FmsPayrollTransaction f WHERE f.tax = :tax"),
    @NamedQuery(name = "FmsPayrollTransaction.findByPension", query = "SELECT f FROM FmsPayrollTransaction f WHERE f.pension = :pension"),
    @NamedQuery(name = "FmsPayrollTransaction.findByOtherDeducation", query = "SELECT f FROM FmsPayrollTransaction f WHERE f.otherDeducation = :otherDeducation"),
    @NamedQuery(name = "FmsPayrollTransaction.findByTotalIncome", query = "SELECT f FROM FmsPayrollTransaction f WHERE f.totalIncome = :totalIncome"),
    @NamedQuery(name = "FmsPayrollTransaction.findByTotalDedaction", query = "SELECT f FROM FmsPayrollTransaction f WHERE f.totalDedaction = :totalDedaction"),
    @NamedQuery(name = "FmsPayrollTransaction.findByNetPay", query = "SELECT f FROM FmsPayrollTransaction f WHERE f.netPay = :netPay"),
    @NamedQuery(name = "FmsPayrollTransaction.findByDepName", query = "SELECT f FROM FmsPayrollTransaction f WHERE f.depName = :depName"),
    @NamedQuery(name = "FmsPayrollTransaction.findByPayrollperiodDate", query = "SELECT f FROM FmsPayrollTransaction f WHERE f.payrollperiodDate = :payrollperiodDate"),
    @NamedQuery(name = "FmsPayrollTransaction.findByStatus", query = "SELECT f FROM FmsPayrollTransaction f WHERE f.status = :status"),
    @NamedQuery(name = "FmsPayrollTransaction.findByDepId", query = "SELECT f FROM FmsPayrollTransaction f WHERE f.depId = :depId"),
    @NamedQuery(name = "FmsPayrollTransaction.findByEmploymenttype", query = "SELECT f FROM FmsPayrollTransaction f WHERE f.employmenttype = :employmenttype"),
    @NamedQuery(name = "FmsPayrollTransaction.findByTotalsalary", query = "SELECT f FROM FmsPayrollTransaction f WHERE f.totalsalary = :totalsalary")})
public class FmsPayrollTransaction implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FMS_SEQ_PAYROLL_TRAN")//
    @SequenceGenerator(name = "FMS_SEQ_PAYROLL_TRAN", sequenceName = "FMS_SEQ_PAYROLL_TRAN", allocationSize = 1)
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 60)
    @Column(name = "ID")
    private String id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "EMP_ID")
    private String empId;
    @Size(max = 50)
    @Column(name = "FULL_NAME")
    private String fullName;
    @Size(max = 50)
    @Column(name = "SALARY")
    private String salary;
    @Size(max = 50)
    @Column(name = "TRANSPORT")
    private String transport;
    @Size(max = 50)
    @Column(name = "OTHER_EARNING")
    private String otherEarning;
    @Size(max = 50)
    @Column(name = "TAX")
    private String tax;
    @Size(max = 50)
    @Column(name = "PENSION")
    private String pension;
    @Size(max = 50)
    @Column(name = "OTHER_DEDUCATION")
    private String otherDeducation;
    @Size(max = 50)
    @Column(name = "TOTAL_INCOME")
    private String totalIncome;
    @Size(max = 50)
    @Column(name = "TOTAL_DEDACTION")
    private String totalDedaction;
    @Size(max = 50)
    @Column(name = "NET_PAY")
    private String netPay;
    @Size(max = 200)
    @Column(name = "DEP_NAME")
    private String depName;
    @Size(max = 20)
    @Column(name = "PAYROLLPERIOD_DATE")
    private String payrollperiodDate;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "STATUS")
    private String status;
    @Size(max = 20)
    @Column(name = "DEP_ID")
    private String depId;
    @Size(max = 20)
    @Column(name = "EMPLOYMENTTYPE")
    private String employmenttype;
    @Size(max = 20)
    @Column(name = "TOTALSALARY")
    private String totalsalary;

    /**
     *
     */
    public FmsPayrollTransaction() {
    }

    /**
     *
     * @param id
     */
    public FmsPayrollTransaction(String id) {
        this.id = id;
    }

    /**
     *
     * @param payrollperiodDate
     * @param employmenttype
     */
    public FmsPayrollTransaction(String payrollperiodDate, String employmenttype) {
        this.payrollperiodDate = payrollperiodDate;
        this.employmenttype = employmenttype;

    }

    /**
     *
     * @param id
     * @param empId
     * @param status
     */
    public FmsPayrollTransaction(String id, String empId, String status) {
        this.id = id;
        this.empId = empId;
        this.status = status;
    }

    /**
     *
     * @return
     */
    public String getId() {
        return id;
    }

    /**
     *
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     *
     * @return
     */
    public String getEmpId() {
        return empId;
    }

    /**
     *
     * @param empId
     */
    public void setEmpId(String empId) {
        this.empId = empId;
    }

    /**
     *
     * @return
     */
    public String getFullName() {
        return fullName;
    }

    /**
     *
     * @param fullName
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    /**
     *
     * @return
     */
    public String getSalary() {
        return salary;
    }

    /**
     *
     * @param salary
     */
    public void setSalary(String salary) {
        this.salary = salary;
    }

    /**
     *
     * @return
     */
    public String getTransport() {
        return transport;
    }

    /**
     *
     * @param transport
     */
    public void setTransport(String transport) {
        this.transport = transport;
    }

    /**
     *
     * @return
     */
    public String getOtherEarning() {
        return otherEarning;
    }

    /**
     *
     * @param otherEarning
     */
    public void setOtherEarning(String otherEarning) {
        this.otherEarning = otherEarning;
    }

    /**
     *
     * @return
     */
    public String getTax() {
        return tax;
    }

    /**
     *
     * @param tax
     */
    public void setTax(String tax) {
        this.tax = tax;
    }

    /**
     *
     * @return
     */
    public String getPension() {
        return pension;
    }

    /**
     *
     * @param pension
     */
    public void setPension(String pension) {
        this.pension = pension;
    }

    /**
     *
     * @return
     */
    public String getOtherDeducation() {
        return otherDeducation;
    }

    /**
     *
     * @param otherDeducation
     */
    public void setOtherDeducation(String otherDeducation) {
        this.otherDeducation = otherDeducation;
    }

    /**
     *
     * @return
     */
    public String getTotalIncome() {
        return totalIncome;
    }

    /**
     *
     * @param totalIncome
     */
    public void setTotalIncome(String totalIncome) {
        this.totalIncome = totalIncome;
    }

    /**
     *
     * @return
     */
    public String getTotalDedaction() {
        return totalDedaction;
    }

    /**
     *
     * @param totalDedaction
     */
    public void setTotalDedaction(String totalDedaction) {
        this.totalDedaction = totalDedaction;
    }

    /**
     *
     * @return
     */
    public String getNetPay() {
        return netPay;
    }

    /**
     *
     * @param netPay
     */
    public void setNetPay(String netPay) {
        this.netPay = netPay;
    }

    /**
     *
     * @return
     */
    public String getDepName() {
        return depName;
    }

    /**
     *
     * @param depName
     */
    public void setDepName(String depName) {
        this.depName = depName;
    }

    /**
     *
     * @return
     */
    public String getPayrollperiodDate() {
        return payrollperiodDate;
    }

    /**
     *
     * @param payrollperiodDate
     */
    public void setPayrollperiodDate(String payrollperiodDate) {
        this.payrollperiodDate = payrollperiodDate;
    }

    /**
     *
     * @return
     */
    public String getStatus() {
        return status;
    }

    /**
     *
     * @param status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     *
     * @return
     */
    public String getDepId() {
        return depId;
    }

    /**
     *
     * @param depId
     */
    public void setDepId(String depId) {
        this.depId = depId;
    }

    /**
     *
     * @return
     */
    public String getEmploymenttype() {
        return employmenttype;
    }

    /**
     *
     * @param employmenttype
     */
    public void setEmploymenttype(String employmenttype) {
        this.employmenttype = employmenttype;
    }

    /**
     *
     * @return
     */
    public String getTotalsalary() {
        return totalsalary;
    }

    /**
     *
     * @param totalsalary
     */
    public void setTotalsalary(String totalsalary) {
        this.totalsalary = totalsalary;
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
        if (!(object instanceof FmsPayrollTransaction)) {
            return false;
        }
        FmsPayrollTransaction other = (FmsPayrollTransaction) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return empId;
    }

}
