/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.entity.organization;

import et.gov.eep.hrms.entity.lookup.HrLuSalarySteps;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Ob
 */
@Entity
@Table(name = "HR_SALARY_SCALES")
@XmlRootElement
@NamedQueries//<editor-fold defaultstate="collapsed" desc="">
        ({
            @NamedQuery(name = "HrSalaryScales.findBasicSalary", query = "SELECT h FROM HrSalaryScales h where h.salaryRangeId.id=:id and h.salaryStepId.id=:id"),
            @NamedQuery(name = "HrSalaryScales.findBySalaryRangeId", query = "SELECT h FROM HrSalaryScales h WHERE h.salaryRangeId.id = :salaryRangeId ORDER BY h.salaryStepId.salaryStep ASC"),
            @NamedQuery(name = "HrSalaryScales.findAll", query = "SELECT h FROM HrSalaryScales h"),
            @NamedQuery(name = "HrSalaryScales.findById", query = "SELECT h FROM HrSalaryScales h WHERE h.id = :id"),
            @NamedQuery(name = "HrSalaryScales.findBySalary", query = "SELECT h FROM HrSalaryScales h WHERE h.salary = :salary")})
//</editor-fold>
public class HrSalaryScales implements Serializable {

    //<editor-fold defaultstate="collapsed" desc="Entity Attributes">
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HR_SALARY_SCALES_SEQ")
    @SequenceGenerator(name = "HR_SALARY_SCALES_SEQ", sequenceName = "HR_SALARY_SCALES_SEQ", allocationSize = 1)
    private Integer id;
    @Column(name = "SALARY")
    private Double salary;
    @JoinColumn(name = "SALARY_STEP_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrLuSalarySteps salaryStepId;
    @JoinColumn(name = "SALARY_RANGE_ID", referencedColumnName = "ID")
    @ManyToOne(cascade = CascadeType.ALL)
    private HrSalaryScaleRanges salaryRangeId;
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="getter and setter">
    
    public HrSalaryScales() {
    }
    
    public HrSalaryScales(Integer id) {
        this.id = id;
    }
    
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public Double getSalary() {
        return salary;
    }
    
    public void setSalary(Double salary) {
        this.salary = salary;
    }
    
    public HrLuSalarySteps getSalaryStepId() {
        return salaryStepId;
    }
    
    public void setSalaryStepId(HrLuSalarySteps salaryStepId) {
        this.salaryStepId = salaryStepId;
    }
    
    public HrSalaryScaleRanges getSalaryRangeId() {
        return salaryRangeId;
    }
    
    public void setSalaryRangeId(HrSalaryScaleRanges salaryRangeId) {
        this.salaryRangeId = salaryRangeId;
    }
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="other methods">
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }
    
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HrSalaryScales)) {
            return false;
        }
        HrSalaryScales other = (HrSalaryScales) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }
    
    @Override
    public String toString() {
        return  salaryStepId.getSalaryStep().toString();
    }
//</editor-fold>

}
