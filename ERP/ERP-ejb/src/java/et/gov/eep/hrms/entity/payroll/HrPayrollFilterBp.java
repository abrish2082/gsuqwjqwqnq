/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.entity.payroll;

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
 * @author user
 */
@Entity
@Table(name = "HR_PAYROLL_FILTER_BP")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HrPayrollFilterBp.SELL", query = "SELECT h FROM HrEmployees h "),
    @NamedQuery(name = "HrPayrollFilterBp.delete", query = "DELETE FROM HrPayrollFilterBp h where h.empId=:empId"),
    @NamedQuery(name = "HrPayrollFilterBp.findAll", query = "SELECT h FROM HrPayrollFilterBp h"),
    @NamedQuery(name = "HrPayrollFilterBp.findById", query = "SELECT h FROM HrPayrollFilterBp h WHERE h.id = :id"),
    @NamedQuery(name = "HrPayrollFilterBp.findByFromMonth", query = "SELECT h FROM HrPayrollFilterBp h WHERE h.fromMonth = :fromMonth"),
    @NamedQuery(name = "HrPayrollFilterBp.findByToMonth", query = "SELECT h FROM HrPayrollFilterBp h WHERE h.toMonth = :toMonth")})

public class HrPayrollFilterBp implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HR_PAYROLL_MAINT_BK_SEQ")
    @SequenceGenerator(name = "HR_PAYROLL_MAINT_BK_SEQ", sequenceName = "HR_PAYROLL_MAINT_BK_SEQ", allocationSize = 1)

    private BigDecimal id;
    @Size(max = 20)
    @Column(name = "FROM_MONTH")
    private String fromMonth;
    @Size(max = 20)
    @Column(name = "TO_MONTH")
    private String toMonth;
    @JoinColumn(name = "GROUP_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrPayrollGroups groupId;

    @Column(name = "EMP_ID")

    private BigDecimal empId;

    public HrPayrollFilterBp() {
    }

    public HrPayrollFilterBp(BigDecimal id) {
        this.id = id;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getFromMonth() {
        return fromMonth;
    }

    public void setFromMonth(String fromMonth) {
        this.fromMonth = fromMonth;
    }

    public String getToMonth() {
        return toMonth;
    }

    public void setToMonth(String toMonth) {
        this.toMonth = toMonth;
    }

    public BigDecimal getEmpId() {
        return empId;
    }

    public void setEmpId(BigDecimal empId) {
        this.empId = empId;
    }

    public HrPayrollGroups getGroupId() {
        return groupId;
    }

    public void setGroupId(HrPayrollGroups groupId) {
        this.groupId = groupId;
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
        if (!(object instanceof HrPayrollFilterBp)) {
            return false;
        }
        HrPayrollFilterBp other = (HrPayrollFilterBp) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.eep.hrms.entity.payroll.HrPayrollFilterBp[ id=" + id + " ]";
    }

}
