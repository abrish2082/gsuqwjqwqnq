/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.entity.payroll;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
@Table(name = "HR_PAYROLL_PENSION_RATE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HrPayrollPensionRate.findAll", query = "SELECT h FROM HrPayrollPensionRate h"),
    @NamedQuery(name = "HrPayrollPensionRate.findById", query = "SELECT h FROM HrPayrollPensionRate h WHERE h.id = :id"),
    @NamedQuery(name = "HrPayrollPensionRate.findByEmployeeCont", query = "SELECT h FROM HrPayrollPensionRate h WHERE h.employeeCont = :employeeCont"),
    @NamedQuery(name = "HrPayrollPensionRate.findByEmployerCont", query = "SELECT h FROM HrPayrollPensionRate h WHERE h.employerCont = :employerCont"),
    @NamedQuery(name = "HrPayrollPensionRate.findByYear", query = "SELECT h FROM HrPayrollPensionRate h WHERE h.year = :year"),
    @NamedQuery(name = "HrPayrollPensionRate.findByStatus", query = "SELECT h FROM HrPayrollPensionRate h WHERE h.status = :status")})
public class HrPayrollPensionRate implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private BigDecimal id;
    @Column(name = "EMPLOYEE_CONT")
    private BigInteger employeeCont;
    @Column(name = "EMPLOYER_CONT")
    private BigInteger employerCont;
    @Column(name = "YEAR")
    @Temporal(TemporalType.DATE)
    private Date year;
    @Size(max = 20)
    @Column(name = "STATUS")
    private String status;

    /**
     *
     */
    public HrPayrollPensionRate() {
    }

    /**
     *
     * @param id
     */
    public HrPayrollPensionRate(BigDecimal id) {
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
    public Date getYear() {
        return year;
    }

    /**
     *
     * @param year
     */
    public void setYear(Date year) {
        this.year = year;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HrPayrollPensionRate)) {
            return false;
        }
        HrPayrollPensionRate other = (HrPayrollPensionRate) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.eep.hrms.entity.payroll.HrPayrollPensionRate[ id=" + id + " ]";
    }
    
}
