/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.entity.payroll;

import et.gov.eep.hrms.entity.organization.HrDepartments;
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
 * @author meles
 */
@Entity
@Table(name = "HR_PAYROLL_PL_PG_DEPT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HrPayrollPlPgDept.findAll", query = "SELECT h FROM HrPayrollPlPgDept h"),
    @NamedQuery(name = "HrPayrollPlPgDept.findById", query = "SELECT h FROM HrPayrollPlPgDept h WHERE h.id = :id"),
    @NamedQuery(name = "HrPayrollPlPgDept.findByDeptId", query = "SELECT h FROM HrPayrollPlPgDept h WHERE h.depId.depId = :depId"),
    @NamedQuery(name = "HrPayrollPlPgDept.findByRemark", query = "SELECT h FROM HrPayrollPlPgDept h WHERE h.remark = :remark"),
    @NamedQuery(name = "HrPayrollPlPgDept.findByPreparedBy", query = "SELECT h FROM HrPayrollPlPgDept h WHERE h.preparedBy = :preparedBy"),
    @NamedQuery(name = "HrPayrollPlPgDept.findByPreparedOn", query = "SELECT h FROM HrPayrollPlPgDept h WHERE h.preparedOn = :preparedOn")})
public class HrPayrollPlPgDept implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HR_PAYROLL_PL_PG_DEPT_SEQ")
    @SequenceGenerator(name = "HR_PAYROLL_PL_PG_DEPT_SEQ", sequenceName = "HR_PAYROLL_PL_PG_DEPT_SEQ", allocationSize = 1)
    private Integer id;
    @Size(max = 200)
    @Column(name = "REMARK")
    private String remark;
    @Size(max = 20)
    @Column(name = "PREPARED_BY")
    private String preparedBy;
    @Size(max = 20)
    @Column(name = "PREPARED_ON")
    private String preparedOn;
    
    @JoinColumn(name = "PAY_LOCATION_PAY_GROUP_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrPayrollPlPg payLocationPayGroupId;
    
    @JoinColumn(name = "DEP_ID", referencedColumnName = "DEP_ID")
    @ManyToOne
    private HrDepartments depId;

    public HrPayrollPlPgDept() {
    }

    public HrPayrollPlPgDept(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getPreparedBy() {
        return preparedBy;
    }

    public void setPreparedBy(String preparedBy) {
        this.preparedBy = preparedBy;
    }

    public String getPreparedOn() {
        return preparedOn;
    }

    public void setPreparedOn(String preparedOn) {
        this.preparedOn = preparedOn;
    }

    public HrPayrollPlPg getPayLocationPayGroupId() {
        return payLocationPayGroupId;
    }

    public void setPayLocationPayGroupId(HrPayrollPlPg payLocationPayGroupId) {
        this.payLocationPayGroupId = payLocationPayGroupId;
    }

    public HrDepartments getDepId() {
        return depId;
    }

    public void setDepId(HrDepartments depId) {
        this.depId = depId;
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
        if (!(object instanceof HrPayrollPlPgDept)) {
            return false;
        }
        HrPayrollPlPgDept other = (HrPayrollPlPgDept) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.eep.hrms.entity.payroll.HrPayrollPlPgDept[ id=" + id + " ]";
    }

}
