/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.entity.training;

import et.gov.eep.hrms.entity.employee.HrEmployees;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
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
@Table(name = "HR_TD_IF_TRNG_SELECTED_STAFFS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HrTdIfTrngSelectedStaffs.findAll", query = "SELECT h FROM HrTdIfTrngSelectedStaffs h"),
    @NamedQuery(name = "HrTdIfTrngSelectedStaffs.findById", query = "SELECT h FROM HrTdIfTrngSelectedStaffs h WHERE h.id = :id"),
    @NamedQuery(name = "HrTdIfTrngSelectedStaffs.findByTotalResult", query = "SELECT h FROM HrTdIfTrngSelectedStaffs h WHERE h.totalResult = :totalResult"),
    @NamedQuery(name = "HrTdIfTrngSelectedStaffs.findByStatus", query = "SELECT h FROM HrTdIfTrngSelectedStaffs h WHERE h.status = :status")})
public class HrTdIfTrngSelectedStaffs implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HR_TD_IF_TRNG_STAFFS_SEQ")
    @SequenceGenerator(name = "HR_TD_IF_TRNG_STAFFS_SEQ", sequenceName = "HR_TD_IF_TRNG_STAFFS_SEQ", allocationSize = 1)
    
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
    @JoinColumn(name = "INLAND_FOREIGN_TRNG_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrTdIfTrng inlandForeignTrngId;

    public HrTdIfTrngSelectedStaffs() {
    }

    public HrTdIfTrngSelectedStaffs(BigDecimal id) {
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

    public HrTdIfTrng getInlandForeignTrngId() {
        return inlandForeignTrngId;
    }

    public void setInlandForeignTrngId(HrTdIfTrng inlandForeignTrngId) {
        this.inlandForeignTrngId = inlandForeignTrngId;
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
        if (!(object instanceof HrTdIfTrngSelectedStaffs)) {
            return false;
        }
        HrTdIfTrngSelectedStaffs other = (HrTdIfTrngSelectedStaffs) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.eep.hrms.entity.training.HrTdIfTrngSelectedStaffs[ id=" + id + " ]";
    }

    public void add(HrTdIfTrng hrTdIfTrng) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
