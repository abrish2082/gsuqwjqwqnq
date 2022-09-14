/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.entity.payroll;

import java.io.Serializable;
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
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author user
 */
@Entity
@Table(name = "HR_PAYROLL_TAX_RATES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HrPayrollTaxRates.checkRepetedUnlimitedUpdate", query = "SELECT h FROM HrPayrollTaxRates h where h.toAmount =:toAmount and h.rateId<>:rateId"),

    @NamedQuery(name = "HrPayrollTaxRates.checkRepetedUnlimited", query = "SELECT h FROM HrPayrollTaxRates h where h.toAmount =:toAmount"),
//    @NamedQuery(name = "HrPayrollTaxRates.checkOverlap", query = "SELECT h FROM HrPayrollTaxRates h where :fromAmount >= h.fromAmount and :toAmount<=h.toAmount"),

//     @NamedQuery(name = "HrPayrollTaxRates.checkOverlap", query = "SELECT h FROM HrPayrollTaxRates h where (:fromAmount >= h.fromAmount and :toAmount<=h.toAmount) or (:toAmount < h.fromAmount and h.toAmount<>:toNegAmt)"),//check it again with different senarios
    @NamedQuery(name = "HrPayrollTaxRates.checkOverlap", query = "SELECT h FROM HrPayrollTaxRates h where (:fromAmount < h.fromAmount and :fromAmount< h.toAmount and :toAmount < h.fromAmount)"),//check it again with different senarios

//     @NamedQuery(name = "HrPayrollTaxRates.checkOverlapUpdate", query = "SELECT h FROM HrPayrollTaxRates h where ((:fromAmount >= h.fromAmount and :toAmount<=h.toAmount) or (:toAmount >= h.fromAmount) ) and h.rateId<>:id"),
    @NamedQuery(name = "HrPayrollTaxRates.checkOverlapUpdate", query = "SELECT h FROM HrPayrollTaxRates h where (:fromAmount < h.fromAmount and :toAmount< h.toAmount and h.toAmount<>:toNegAmt) or (:fromAmount > h.fromAmount and h.toAmount<>:toNegAmt) and h.rateId<>:id"),

    @NamedQuery(name = "HrPayrollTaxRates.findAll", query = "SELECT h FROM HrPayrollTaxRates h"),
    @NamedQuery(name = "HrPayrollTaxRates.findByRateId", query = "SELECT h FROM HrPayrollTaxRates h WHERE h.rateId = :rateId"),
    @NamedQuery(name = "HrPayrollTaxRates.findByFromAmount", query = "SELECT h FROM HrPayrollTaxRates h WHERE h.fromAmount = :fromAmount"),
    @NamedQuery(name = "HrPayrollTaxRates.findBySalaryRange", query = "SELECT h FROM HrPayrollTaxRates h WHERE :amount >=h.fromAmount  AND :amount<= h.toAmount"),
    @NamedQuery(name = "HrPayrollTaxRates.findByToAmount", query = "SELECT h FROM HrPayrollTaxRates h WHERE h.toAmount = :toAmount"),
    @NamedQuery(name = "HrPayrollTaxRates.findByRateInPerercent", query = "SELECT h FROM HrPayrollTaxRates h WHERE h.rateInPerercent = :rateInPerercent"),
    @NamedQuery(name = "HrPayrollTaxRates.findByConstantAmount", query = "SELECT h FROM HrPayrollTaxRates h WHERE h.constantAmount = :constantAmount")})
public class HrPayrollTaxRates implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "RATE_ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HR_PAYROLL_TAX_RATES_SEQ") //
    @SequenceGenerator(name = "HR_PAYROLL_TAX_RATES_SEQ", sequenceName = "HR_PAYROLL_TAX_RATES_SEQ", allocationSize = 1)
    private String rateId;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "FROM_AMOUNT")
    private Double fromAmount;
    @Column(name = "TO_AMOUNT")
    private Double toAmount;
    @Column(name = "RATE_IN_PERERCENT")
    private Integer rateInPerercent;
    @Column(name = "CONSTANT_AMOUNT")
    private Double constantAmount;
    @JoinColumn(name = "ACTIVE_YEAR_ID", referencedColumnName = "ID")
    @ManyToOne(cascade = CascadeType.ALL)
    private HrPayrollTaxRateStatus activeYearId;

    public HrPayrollTaxRates() {
    }

    public HrPayrollTaxRates(String rateId) {
        this.rateId = rateId;
    }

    public String getRateId() {
        return rateId;
    }

    public void setRateId(String rateId) {
        this.rateId = rateId;
    }

    public Double getFromAmount() {
        return fromAmount;
    }

    public void setFromAmount(Double fromAmount) {
        this.fromAmount = fromAmount;
    }

    public Double getToAmount() {
        return toAmount;
    }

    public void setToAmount(Double toAmount) {
        this.toAmount = toAmount;
    }

    public Integer getRateInPerercent() {
        return rateInPerercent;
    }

    public void setRateInPerercent(Integer rateInPerercent) {
        this.rateInPerercent = rateInPerercent;
    }

    public Double getConstantAmount() {
        return constantAmount;
    }

    public void setConstantAmount(Double constantAmount) {
        this.constantAmount = constantAmount;
    }

    public HrPayrollTaxRateStatus getActiveYearId() {
        return activeYearId;
    }

    public void setActiveYearId(HrPayrollTaxRateStatus activeYearId) {
        this.activeYearId = activeYearId;
    }

    @Transient
    private String toAmoutVal;

    public String getToAmoutVal() {
        return toAmoutVal;
    }

    public void setToAmoutVal(String toAmoutVal) {

        if (getToAmount().toString().contains("-1")) {
            toAmoutVal = "unlimited";
        } else {
            toAmoutVal = getToAmount().toString();
        }
        this.toAmoutVal = toAmoutVal;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (rateId != null ? rateId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HrPayrollTaxRates)) {
            return false;
        }
        HrPayrollTaxRates other = (HrPayrollTaxRates) object;
        if ((this.rateId == null && other.rateId != null) || (this.rateId != null && !this.rateId.equals(other.rateId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.eep.hrms.entity.payroll.HrPayrollTaxRates[ rateId=" + rateId + " ]";
    }

}
