/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.entity.payroll;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author user
 */
@Entity
@Table(name = "HR_PAYROLL_TAX_RATE_STATUS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HrPayrollTaxRateStatus.findAll", query = "SELECT h FROM HrPayrollTaxRateStatus h"),
    @NamedQuery(name = "HrPayrollTaxRateStatus.findById", query = "SELECT h FROM HrPayrollTaxRateStatus h WHERE h.id = :id"),
    @NamedQuery(name = "HrPayrollTaxRateStatus.findByRateYear", query = "SELECT h FROM HrPayrollTaxRateStatus h WHERE h.rateYear = :rateYear"),
    @NamedQuery(name = "HrPayrollTaxRateStatus.findByStatus", query = "SELECT h FROM HrPayrollTaxRateStatus h WHERE h.status = :status"),
    @NamedQuery(name = "HrPayrollTaxRateStatus.findByRemark", query = "SELECT h FROM HrPayrollTaxRateStatus h WHERE h.remark = :remark")})
public class HrPayrollTaxRateStatus implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    
    //
     
         @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HR_PAYROLL_TAX_RATE_STATUS_SEQ") //
    @SequenceGenerator(name = "HR_PAYROLL_TAX_RATE_STATUS_SEQ", sequenceName = "HR_PAYROLL_TAX_RATE_STATUS_SEQ", allocationSize = 1)
    
    
    private BigDecimal id;
    @Size(max = 20)
    @Column(name = "RATE_YEAR")
    private String rateYear;
    @Size(max = 20)
    @Column(name = "STATUS")
    private String status;
    @Size(max = 20)
    @Column(name = "REMARK")
    private String remark;
    @OneToMany  (mappedBy = "activeYearId" )
    private List<HrPayrollTaxRates> hrPayrollTaxRatesList;

    public HrPayrollTaxRateStatus() {
    }

    public HrPayrollTaxRateStatus(BigDecimal id) {
        this.id = id;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getRateYear() {
        return rateYear;
    }

    public void setRateYear(String rateYear) {
        this.rateYear = rateYear;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @XmlTransient
    public List<HrPayrollTaxRates> getHrPayrollTaxRatesList() {
        return hrPayrollTaxRatesList;
    }

    public void setHrPayrollTaxRatesList(List<HrPayrollTaxRates> hrPayrollTaxRatesList) {
        this.hrPayrollTaxRatesList = hrPayrollTaxRatesList;
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
        if (!(object instanceof HrPayrollTaxRateStatus)) {
            return false;
        }
        HrPayrollTaxRateStatus other = (HrPayrollTaxRateStatus) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.eep.hrms.entity.payroll.HrPayrollTaxRateStatus[ id=" + id + " ]";
    }
    
}
