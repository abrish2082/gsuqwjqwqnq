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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "HR_PAYROLL_BACK_PAYMENT_GROUPS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HrPayrollBackPaymentGroups.findAll", query = "SELECT h FROM HrPayrollBackPaymentGroups h"),
    @NamedQuery(name = "HrPayrollBackPaymentGroups.findById", query = "SELECT h FROM HrPayrollBackPaymentGroups h WHERE h.id = :id"),
    @NamedQuery(name = "HrPayrollBackPaymentGroups.findByGroupName", query = "SELECT h FROM HrPayrollBackPaymentGroups h WHERE h.groupName = :groupName"),
    @NamedQuery(name = "HrPayrollBackPaymentGroups.findByCreatedOn", query = "SELECT h FROM HrPayrollBackPaymentGroups h WHERE h.createdOn = :createdOn")})
public class HrPayrollBackPaymentGroups implements Serializable {
    @OneToMany(mappedBy = "backPayGroupId")
    private List<HrPayrollMaintainBackPay> hrPayrollMaintainBackPayList;
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HR_PAYROLL_MAIN_BK_PYMNT_SEQ")//
    @SequenceGenerator(name = "HR_PAYROLL_MAIN_BK_PYMNT_SEQ", sequenceName = "HR_PAYROLL_MAIN_BK_PYMNT_SEQ", allocationSize = 1)

    
    private BigDecimal id;
    @Size(max = 200)
    @Column(name = "GROUP_NAME")
    private String groupName;
    @Size(max = 20)
    @Column(name = "CREATED_ON")
    private String createdOn;
    
    
  
    @JoinColumn(name = "PAYROL_FROM", referencedColumnName = "ID")
    @ManyToOne
    private HrPayrollPeriods payrolFrom;
    @JoinColumn(name = "PAYROLL_TO", referencedColumnName = "ID")
     @ManyToOne
    private HrPayrollPeriods payrollTo;

    public HrPayrollBackPaymentGroups() {
    }

    public HrPayrollBackPaymentGroups(BigDecimal id) {
        this.id = id;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public HrPayrollPeriods getPayrolFrom() {
        return payrolFrom;
    }

    public void setPayrolFrom(HrPayrollPeriods payrolFrom) {
        this.payrolFrom = payrolFrom;
    }

    public HrPayrollPeriods getPayrollTo() {
        return payrollTo;
    }

    public void setPayrollTo(HrPayrollPeriods payrollTo) {
        this.payrollTo = payrollTo;
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
        if (!(object instanceof HrPayrollBackPaymentGroups)) {
            return false;
        }
        HrPayrollBackPaymentGroups other = (HrPayrollBackPaymentGroups) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.eep.hrms.entity.payroll.HrPayrollBackPaymentGroups[ id=" + id + " ]";
    }

    @XmlTransient
    public List<HrPayrollMaintainBackPay> getHrPayrollMaintainBackPayList() {
        return hrPayrollMaintainBackPayList;
    }

    public void setHrPayrollMaintainBackPayList(List<HrPayrollMaintainBackPay> hrPayrollMaintainBackPayList) {
        this.hrPayrollMaintainBackPayList = hrPayrollMaintainBackPayList;
    }
    
}
