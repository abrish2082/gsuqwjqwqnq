/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.entity.massSalaryIncrement;

import java.io.Serializable;
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
 * @author Behailu
 */
@Entity
@Table(name = "HR_SALARY_INCREMENT_DETAILS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HrSalaryIncrementDetails.findAll", query = "SELECT h FROM HrSalaryIncrementDetails h"),
    @NamedQuery(name = "HrSalaryIncrementDetails.findById", query = "SELECT h FROM HrSalaryIncrementDetails h WHERE h.id = :id"),
    @NamedQuery(name = "HrSalaryIncrementDetails.findByEmpId", query = "SELECT h FROM HrSalaryIncrementDetails h WHERE h.empId = :empId"),
    @NamedQuery(name = "HrSalaryIncrementDetails.findByIncrementBy", query = "SELECT h FROM HrSalaryIncrementDetails h WHERE h.incrementBy = :incrementBy"),
    @NamedQuery(name = "HrSalaryIncrementDetails.findByIncrementByAndStatus", query = "SELECT  h FROM HrSalaryIncrementDetails h WHERE h.status = 0 "),
    @NamedQuery(name = "HrSalaryIncrementDetails.findByNewSalaryStep", query = "SELECT h FROM HrSalaryIncrementDetails h WHERE h.newSalaryStep = :newSalaryStep"),
    @NamedQuery(name = "HrSalaryIncrementDetails.findByNewSalary", query = "SELECT h FROM HrSalaryIncrementDetails h WHERE h.newSalary = :newSalary"),
    @NamedQuery(name = "HrSalaryIncrementDetails.findByStatus", query = "SELECT h FROM HrSalaryIncrementDetails h WHERE h.status = :status")})
public class HrSalaryIncrementDetails implements Serializable {

    
    
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HR_SALARY_INCREMENT_DETAILS_SEQ")
    @SequenceGenerator(name = "HR_SALARY_INCREMENT_DETAILS_SEQ", sequenceName = "HR_SALARY_INCREMENT_DETAILS_SEQ", allocationSize = 1)
    private Integer id;
    @Size(max = 20)
    @Column(name = "EMP_ID")
    private Integer empId;
    @Column(name = "INCREMENT_BY")
    private Integer incrementBy;
    @Column(name = "NEW_SALARY_STEP")
    private Integer newSalaryStep;
    @Size(max = 20)
    @Column(name = "NEW_SALARY")
    private Double newSalary;
    @Column(name = "STATUS")
    private Integer status;
    @JoinColumn(name = "SALARY_INCREMENT_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrSalaryIncrements salaryIncrementId;

    public HrSalaryIncrementDetails() {
    }

    public HrSalaryIncrementDetails(Integer id) {
        this.id = id;
    }

//<editor-fold defaultstate="collapsed" desc="getters & setters">
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getEmpId() {
        return empId;
    }

    public void setEmpId(Integer empId) {
        this.empId = empId;
    }

    public Integer getIncrementBy() {
        return incrementBy;
    }

    public void setIncrementBy(Integer incrementBy) {
        this.incrementBy = incrementBy;
    }

    public Integer getNewSalaryStep() {
        return newSalaryStep;
    }

    public void setNewSalaryStep(Integer newSalaryStep) {
        this.newSalaryStep = newSalaryStep;
    }

    public Double getNewSalary() {
        return newSalary;
    }

    public void setNewSalary(Double newSalary) {
        this.newSalary = newSalary;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public HrSalaryIncrements getSalaryIncrementId() {
        return salaryIncrementId;
    }

    public void setSalaryIncrementId(HrSalaryIncrements salaryIncrementId) {
        this.salaryIncrementId = salaryIncrementId;
    }
//</editor-fold>

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HrSalaryIncrementDetails)) {
            return false;
        }
        HrSalaryIncrementDetails other = (HrSalaryIncrementDetails) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.eep.hrms.entity.massSalaryIncrement.HrSalaryIncrementDetails[ id=" + id + " ]";
    }

    //<editor-fold defaultstate="collapsed" desc="static variables">
    public static final String ALLEMPLOYEES = "0";
    public static final String INCLUDED = "1";
    public static final String NON_INCLUDED = "2";
//</editor-fold>

    

}
