/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.entity.payroll;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
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
 * @author user
 */
@Entity
@Table(name = "HR_PAYROLL_PENSION_RATES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HrPayrollPensionRates.pensionUpdate", query = "UPDATE HrPayrollPensionRates H SET H.status='0'"),
    @NamedQuery(name = "HrPayrollPensionRates.findAll", query = "SELECT h FROM HrPayrollPensionRates h"),
    @NamedQuery(name = "HrPayrollPensionRates.findById", query = "SELECT h FROM HrPayrollPensionRates h WHERE h.id = :id"),
    @NamedQuery(name = "HrPayrollPensionRates.findByEmployeeCont", query = "SELECT h FROM HrPayrollPensionRates h WHERE h.employeeCont = :employeeCont"),
    @NamedQuery(name = "HrPayrollPensionRates.findByEmployerCont", query = "SELECT h FROM HrPayrollPensionRates h WHERE h.employerCont = :employerCont"),
    @NamedQuery(name = "HrPayrollPensionRates.findByYear", query = "SELECT h FROM HrPayrollPensionRates h WHERE h.year = :year"),
    @NamedQuery(name = "HrPayrollPensionRates.findByStatus", query = "SELECT h FROM HrPayrollPensionRates h WHERE h.status = :status")})
public class HrPayrollPensionRates implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HR_PAYROLL_PENSION_RATES_SEQ") //
    @SequenceGenerator(name = "HR_PAYROLL_PENSION_RATES_SEQ", sequenceName = "HR_PAYROLL_PENSION_RATES_SEQ", allocationSize = 1)

    private BigDecimal id;
    @Column(name = "EMPLOYEE_CONT")
    private BigInteger employeeCont;
    @Column(name = "EMPLOYER_CONT")
    private BigInteger employerCont;
    @NotNull
    @Size(max = 20)
    @Column(name = "YEAR")
    private String year;
    @Size(max = 20)
    @Column(name = "STATUS")
    private String status;

    /**
     *
     */
    public HrPayrollPensionRates() {
    }

    /**
     *
     * @param id
     */
    public HrPayrollPensionRates(BigDecimal id) {
        this.id = id;
    }

    /**
     *
     * @return
     */
    public BigDecimal getId() {
        return id;
    }

    /**
     *
     * @param id
     */
    public void setId(BigDecimal id) {
        this.id = id;
    }

    /**
     *
     * @return
     */
    public BigInteger getEmployeeCont() {
        return employeeCont;
    }

    /**
     *
     * @param employeeCont
     */
    public void setEmployeeCont(BigInteger employeeCont) {
        this.employeeCont = employeeCont;
    }

    /**
     *
     * @return
     */
    public BigInteger getEmployerCont() {
        return employerCont;
    }

    /**
     *
     * @param employerCont
     */
    public void setEmployerCont(BigInteger employerCont) {
        this.employerCont = employerCont;
    }

    /**
     *
     * @return
     */
    public String getYear() {
        return year;
    }

    /**
     *
     * @param year
     */
    public void setYear(String year) {
        this.year = year;
    }

    /**
     *
     * @return
     */
    public String getStatus() {
        if (status == null) {
            status = "No action";
        } else if (status.equalsIgnoreCase("1")) {
            status = "Active";
        } else if (status.equalsIgnoreCase("0")) {
            status = "Inactive";
        }

        return status;
    }

    /**
     *
     * @param status
     */
    public void setStatus(String status) {
        this.status = status;
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
        if (!(object instanceof HrPayrollPensionRates)) {
            return false;
        }
        HrPayrollPensionRates other = (HrPayrollPensionRates) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.eep.hrms.entity.payroll.HrPayrollPensionRates[ id=" + id + " ]";
    }
}
