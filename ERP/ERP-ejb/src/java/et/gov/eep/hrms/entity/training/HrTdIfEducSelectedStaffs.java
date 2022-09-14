/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.entity.training;

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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Behailu
 */
@Entity
@Table(name = "HR_TD_IF_EDUC_SELECTED_STAFFS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HrTdIfEducSelectedStaffs.findAll", query = "SELECT h FROM HrTdIfEducSelectedStaffs h"),
    @NamedQuery(name = "HrTdIfEducSelectedStaffs.findById", query = "SELECT h FROM HrTdIfEducSelectedStaffs h WHERE h.id = :id"),
    @NamedQuery(name = "HrTdIfEducSelectedStaffs.findByTotalResult", query = "SELECT h FROM HrTdIfEducSelectedStaffs h WHERE h.totalResult = :totalResult"),
    @NamedQuery(name = "HrTdIfEducSelectedStaffs.findByEmpId", query = "SELECT h FROM HrTdIfEducSelectedStaffs h WHERE h.empId = :EmpId"),
    @NamedQuery(name = "HrTdIfEducSelectedStaffs.findByStatus", query = "SELECT h FROM HrTdIfEducSelectedStaffs h WHERE h.status = :status")})
public class HrTdIfEducSelectedStaffs implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HR_TD_IF_EDU_STAFFS_SEQ")
    @SequenceGenerator(name = "HR_TD_IF_EDU_STAFFS_SEQ", sequenceName = "HR_TD_IF_EDU_STAFFS_SEQ", allocationSize = 1)
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private BigDecimal id;
    @Column(name = "TOTAL_RESULT")
    private Integer totalResult;
    @Column(name = "STATUS")
    private Integer status;
    @JoinColumn(name = "EMP_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrEmployees empId;
    @JoinColumn(name = "INLAND_FOREIGN_EDUC_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrTdIfEduc inlandForeignEducId;

    public HrTdIfEducSelectedStaffs() {
    }

    public HrTdIfEducSelectedStaffs(BigDecimal id) {
        this.id = id;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public Integer getTotalResult() {
        return totalResult;
    }

    public void setTotalResult(Integer totalResult) {
        this.totalResult = totalResult;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public HrEmployees getEmpId() {
        return empId;
    }

    public void setEmpId(HrEmployees empId) {
        this.empId = empId;
    }

    public HrTdIfEduc getInlandForeignEducId() {
        return inlandForeignEducId;
    }

    public void setInlandForeignEducId(HrTdIfEduc inlandForeignEducId) {
        this.inlandForeignEducId = inlandForeignEducId;
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
        if (!(object instanceof HrTdIfEducSelectedStaffs)) {
            return false;
        }
        HrTdIfEducSelectedStaffs other = (HrTdIfEducSelectedStaffs) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.eep.hrms.entity.training.HrTdIfEducSelectedStaffs[ id=" + id + " ]";
    }

}
