/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.entity.payroll;

import java.io.Serializable;
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
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Hanoc
 */
@Entity
@Table(name = "FMS_PAYROLL_TAX_RATES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FmsPayrollTaxRates.findAll", query = "SELECT f FROM FmsPayrollTaxRates f ORDER BY f.constantAmount"),
    @NamedQuery(name = "FmsPayrollTaxRates.findByRateId", query = "SELECT f FROM FmsPayrollTaxRates f WHERE f.rateId = :rateId"),
    @NamedQuery(name = "FmsPayrollTaxRates.findByFromAmount", query = "SELECT f FROM FmsPayrollTaxRates f WHERE f.fromAmount = :fromAmount"),
    @NamedQuery(name = "FmsPayrollTaxRates.findByToAmount", query = "SELECT f FROM FmsPayrollTaxRates f WHERE f.toAmount = :toAmount"),
    @NamedQuery(name = "FmsPayrollTaxRates.findByRateInPerercent", query = "SELECT f FROM FmsPayrollTaxRates f WHERE f.rateInPerercent = :rateInPerercent"),
    @NamedQuery(name = "FmsPayrollTaxRates.findByConstantAmount", query = "SELECT f FROM FmsPayrollTaxRates f WHERE f.constantAmount = :constantAmount")})
public class FmsPayrollTaxRates implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FMS_SEQ_PAYROLL_TAXRATE") //
    @SequenceGenerator(name = "FMS_SEQ_PAYROLL_TAXRATE", sequenceName = "FMS_SEQ_PAYROLL_TAXRATE", allocationSize = 1)
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "RATE_ID")
    private String rateId;
    @Column(name = "FROM_AMOUNT")
    private Integer fromAmount;
    @Column(name = "TO_AMOUNT")
    private Integer toAmount;
    @Column(name = "RATE_IN_PERERCENT")
    private Integer rateInPerercent;
    @Column(name = "CONSTANT_AMOUNT")
    private Double constantAmount;

    /**
     *
     */
    public FmsPayrollTaxRates() {
    }

    /**
     *
     * @param rateId
     */
    public FmsPayrollTaxRates(String rateId) {
        this.rateId = rateId;
    }

    /**
     *
     * @return
     */
    public String getRateId() {
        return rateId;
    }

    /**
     *
     * @param rateId
     */
    public void setRateId(String rateId) {
        this.rateId = rateId;
    }

    /**
     *
     * @return
     */
    public Integer getFromAmount() {
        return fromAmount;
    }

    /**
     *
     * @param fromAmount
     */
    public void setFromAmount(Integer fromAmount) {
        this.fromAmount = fromAmount;
    }

    /**
     *
     * @return
     */
    public Integer getToAmount() {
        return toAmount;
    }

    /**
     *
     * @param toAmount
     */
    public void setToAmount(Integer toAmount) {
        this.toAmount = toAmount;
    }

    /**
     *
     * @return
     */
    public Integer getRateInPerercent() {
        return rateInPerercent;
    }

    /**
     *
     * @param rateInPerercent
     */
    public void setRateInPerercent(Integer rateInPerercent) {
        this.rateInPerercent = rateInPerercent;
    }

    /**
     *
     * @return
     */
    public Double getConstantAmount() {
        return constantAmount;
    }

    /**
     *
     * @param constantAmount
     */
    public void setConstantAmount(Double constantAmount) {
        this.constantAmount = constantAmount;
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
        if (!(object instanceof FmsPayrollTaxRates)) {
            return false;
        }
        FmsPayrollTaxRates other = (FmsPayrollTaxRates) object;
        if ((this.rateId == null && other.rateId != null) || (this.rateId != null && !this.rateId.equals(other.rateId))) {
            return false;
        }
        return true;
    }
    @Transient
    private String toAmoutVal;

    /**
     *
     * @return
     */
    public String getToAmoutVal() {
        return toAmoutVal;
    }

    /**
     *
     * @param toAmoutVal
     */
    public void setToAmoutVal(String toAmoutVal) {

        if (getToAmount().toString().contains("-1")) {
            toAmoutVal = "unlimited";
        } else {
            toAmoutVal = getToAmount().toString();
        }
        this.toAmoutVal = toAmoutVal;
    }

    @Override
    public String toString() {
        return "et.gov.insa.erp.ibfms.entity.FmsPayrollTaxRates[ rateId=" + rateId + " ]";
    }

}
