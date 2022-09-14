/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.ifrs.entity;

import et.gov.eep.hrms.entity.employee.HrEmployees;
import java.io.Serializable;
import java.math.BigDecimal;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author User
 */
@Entity
@Table(name = "HR_EMPLOYEE_SEVERANCE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HrEmployeeSeverance.findAll", query = "SELECT h FROM HrEmployeeSeverance h"),
    @NamedQuery(name = "HrEmployeeSeverance.findById", query = "SELECT h FROM HrEmployeeSeverance h WHERE h.id = :id"),
    @NamedQuery(name = "HrEmployeeSeverance.findByBudgetYear", query = "SELECT h FROM HrEmployeeSeverance h WHERE h.budgetYear = :budgetYear"),
    @NamedQuery(name = "HrEmployeeSeverance.findBySeveranceAmount", query = "SELECT h FROM HrEmployeeSeverance h WHERE h.severanceAmount = :severanceAmount"),
    @NamedQuery(name = "HrEmployeeSeverance.findByExprience", query = "SELECT h FROM HrEmployeeSeverance h WHERE h.exprience = :exprience")})
public class HrEmployeeSeverance implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HR_EMPLOYEE_SEVERANCE_SEQ")
    @SequenceGenerator(name = "HR_EMPLOYEE_SEVERANCE_SEQ", sequenceName = "HR_EMPLOYEE_SEVERANCE_SEQ", allocationSize = 1)
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private BigDecimal id;
    @Size(max = 20)
    @Column(name = "BUDGET_YEAR")
    private String budgetYear;
    @Column(name = "SEVERANCE_AMOUNT")
    private Double severanceAmount;
    @Column(name = "EXPRIENCE")
    private Integer exprience;
     @JoinColumn(name = "EMP_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrEmployees employeeId;

    public HrEmployeeSeverance() {
    }

    public HrEmployeeSeverance(BigDecimal id) {
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

    public Double getSeveranceAmount() {
        return severanceAmount;
    }

    public void setSeveranceAmount(Double severanceAmount) {
        this.severanceAmount = severanceAmount;
    }

    public Integer getExprience() {
        return exprience;
    }

    public void setExprience(Integer exprience) {
        this.exprience = exprience;
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
        if (!(object instanceof HrEmployeeSeverance)) {
            return false;
        }
        HrEmployeeSeverance other = (HrEmployeeSeverance) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.eep.ifrs.entity.HrEmployeeSeverance[ id=" + id + " ]";
    }
    
}
