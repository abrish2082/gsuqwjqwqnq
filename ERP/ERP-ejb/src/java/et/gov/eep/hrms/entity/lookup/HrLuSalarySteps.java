/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.entity.lookup;

import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.entity.organization.HrSalaryScales;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Administrator
 */
@Entity
@Table(name = "HR_LU_SALARY_STEPS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HrLuSalarySteps.findAll", query = "SELECT h FROM HrLuSalarySteps h"),
    @NamedQuery(name = "HrLuSalarySteps.findById", query = "SELECT h FROM HrLuSalarySteps h WHERE h.id = :id"),
    @NamedQuery(name = "HrLuSalarySteps.findBySalaryStep", query = "SELECT h FROM HrLuSalarySteps h WHERE h.salaryStep = :salaryStep")})
public class HrLuSalarySteps implements Serializable {
    @OneToMany(mappedBy = "salaryStep", fetch = FetchType.LAZY)
    private List<HrEmployees> hrEmployeesList;
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private Integer id;
    @Column(name = "SALARY_STEP")
    private Integer salaryStep;
    @OneToMany(mappedBy = "salaryStepId")
    private List<HrSalaryScales> hrSalaryScalesList;

    /**
     *
     */
    public HrLuSalarySteps() {
    }

    /**
     *
     * @param id
     */
    public HrLuSalarySteps(Integer id) {
        this.id = id;
    }

    /**
     *
     * @return
     */
    public Integer getId() {
        return id;
    }

    /**
     *
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     *
     * @return
     */
    public Integer getSalaryStep() {
        return salaryStep;
    }

    /**
     *
     * @param salaryStep
     */
    public void setSalaryStep(Integer salaryStep) {
        this.salaryStep = salaryStep;
    }

    /**
     *
     * @return
     */
    @XmlTransient
    public List<HrSalaryScales> getHrSalaryScalesList() {
        return hrSalaryScalesList;
    }

    /**
     *
     * @param hrSalaryScalesList
     */
    public void setHrSalaryScalesList(List<HrSalaryScales> hrSalaryScalesList) {
        this.hrSalaryScalesList = hrSalaryScalesList;
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
        if (!(object instanceof HrLuSalarySteps)) {
            return false;
        }
        HrLuSalarySteps other = (HrLuSalarySteps) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return salaryStep.toString();
    }

    /**
     *
     * @return
     */
    @XmlTransient
    public List<HrEmployees> getHrEmployeesList() {
        return hrEmployeesList;
    }

    /**
     *
     * @param hrEmployeesList
     */
    public void setHrEmployeesList(List<HrEmployees> hrEmployeesList) {
        this.hrEmployeesList = hrEmployeesList;
    }
    
}
