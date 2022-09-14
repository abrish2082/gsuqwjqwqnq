/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.entity.overtime;

import et.gov.eep.hrms.entity.payroll.HrPayrollMaintainEds;
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
 * @author meles
 */
@Entity
@Table(name = "HR_OT_PAYROLL_DETAIL")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HrOtPayrollDetail.findAll", query = "SELECT h FROM HrOtPayrollDetail h"),
    @NamedQuery(name = "HrOtPayrollDetail.findById", query = "SELECT h FROM HrOtPayrollDetail h WHERE h.id = :id")})
public class HrOtPayrollDetail implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HR_OT_PAYROLL_DETAIL_SEQ")
    @SequenceGenerator(name = "HR_OT_PAYROLL_DETAIL_SEQ", sequenceName = "HR_OT_PAYROLL_DETAIL_SEQ", allocationSize = 1)
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private BigDecimal id;
    @JoinColumn(name = "OT_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrOvertimeRequestDetails otId;
    @JoinColumn(name = "PAYROLL_MAINTIAN_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrPayrollMaintainEds payrollMaintianId;

    public HrOtPayrollDetail() {
    }

    public HrOtPayrollDetail(BigDecimal id) {
        this.id = id;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public HrOvertimeRequestDetails getOtId() {
        return otId;
    }

    public void setOtId(HrOvertimeRequestDetails otId) {
        this.otId = otId;
    }

    public HrPayrollMaintainEds getPayrollMaintianId() {
        return payrollMaintianId;
    }

    public void setPayrollMaintianId(HrPayrollMaintainEds payrollMaintianId) {
        this.payrollMaintianId = payrollMaintianId;
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
        if (!(object instanceof HrOtPayrollDetail)) {
            return false;
        }
        HrOtPayrollDetail other = (HrOtPayrollDetail) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.eep.hrms.entity.overtime.HrOtPayrollDetail[ id=" + id + " ]";
    }

}
