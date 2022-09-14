/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.entity.medical;

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
 * @author INSA
 */
@Entity
@Table(name = "HR_LOCAL_MED_COVERAGE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HrLocalMedCoverage.findAll", query = "SELECT h FROM HrLocalMedCoverage h"),
    @NamedQuery(name = "HrLocalMedCoverage.findById", query = "SELECT h FROM HrLocalMedCoverage h WHERE h.id = :id"),
    @NamedQuery(name = "HrLocalMedCoverage.findByCompanyShare", query = "SELECT h FROM HrLocalMedCoverage h WHERE h.companyShare = :companyShare"),
    @NamedQuery(name = "HrLocalMedCoverage.findByEmployeeShare", query = "SELECT h FROM HrLocalMedCoverage h WHERE h.employeeShare = :employeeShare"),
    @NamedQuery(name = "HrLocalMedCoverage.findByDescription", query = "SELECT h FROM HrLocalMedCoverage h WHERE h.description = :description"),
    @NamedQuery(name = "HrLocalMedCoverage.findByStatus", query = "SELECT h FROM HrLocalMedCoverage h WHERE h.status = :status")})
public class HrLocalMedCoverage implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HR_LOCAL_MED_COVERAGE_SEQ")
    @SequenceGenerator(name = "HR_LOCAL_MED_COVERAGE_SEQ", sequenceName = "HR_LOCAL_MED_COVERAGE_SEQ", allocationSize = 1)
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private BigDecimal id;
    @Column(name = "COMPANY_SHARE")
    private BigInteger companyShare;
    @Column(name = "EMPLOYEE_SHARE")
    private BigInteger employeeShare;
    @Size(max = 200)
    @Column(name = "DESCRIPTION")
    private String description;
    @Column(name = "STATUS")
    private Integer status;

    public HrLocalMedCoverage() {
    }

    public HrLocalMedCoverage(BigDecimal id) {
        this.id = id;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public BigInteger getCompanyShare() {
        return companyShare;
    }

    public void setCompanyShare(BigInteger companyShare) {
        this.companyShare = companyShare;
    }

    public BigInteger getEmployeeShare() {
        return employeeShare;
    }

    public void setEmployeeShare(BigInteger employeeShare) {
        this.employeeShare = employeeShare;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
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
        if (!(object instanceof HrLocalMedCoverage)) {
            return false;
        }
        HrLocalMedCoverage other = (HrLocalMedCoverage) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.eep.hrms.entity.medical.HrLocalMedCoverage[ id=" + id + " ]";
    }

}
