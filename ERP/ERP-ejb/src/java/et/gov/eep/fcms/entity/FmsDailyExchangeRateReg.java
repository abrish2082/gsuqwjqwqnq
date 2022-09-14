/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author mora1
 */
@Entity
@Table(name = "FMS_DAILY_EXCHANGE_RATE_REG")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FmsDailyExchangeRateReg.findAll", query = "SELECT f FROM FmsDailyExchangeRateReg f"),
    @NamedQuery(name = "FmsDailyExchangeRateReg.findById", query = "SELECT f FROM FmsDailyExchangeRateReg f WHERE f.id = :id"),
    @NamedQuery(name = "FmsDailyExchangeRateReg.findByDateReg", query = "SELECT f FROM FmsDailyExchangeRateReg f WHERE f.dateReg = :dateReg"),
    @NamedQuery(name = "FmsDailyExchangeRateReg.findByAmount", query = "SELECT f FROM FmsDailyExchangeRateReg f WHERE f.amount = :amount"),
    @NamedQuery(name = "FmsDailyExchangeRateReg.findByRemark", query = "SELECT f FROM FmsDailyExchangeRateReg f WHERE f.remark = :remark"),
    @NamedQuery(name = "FmsDailyExchangeRateReg.findByBudgetYear", query = "SELECT f FROM FmsDailyExchangeRateReg f WHERE f.budgetYear = :budgetYear")})
public class FmsDailyExchangeRateReg implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private BigDecimal id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "DATE_REG")
    private String dateReg;
    @Column(name = "AMOUNT")
    private BigDecimal amount;
    @Size(max = 45)
    @Column(name = "REMARK")
    private String remark;
    @Size(max = 45)
    @Column(name = "BUDGET_YEAR")
    private String budgetYear;
    @JoinColumn(name = "CURENCY_ID", referencedColumnName = "CURRENCY_ID")
    @ManyToOne
    private FmsLuCurrency curencyId;

    public FmsDailyExchangeRateReg() {
    }

    public FmsDailyExchangeRateReg(BigDecimal id) {
        this.id = id;
    }

    public FmsDailyExchangeRateReg(BigDecimal id, String dateReg) {
        this.id = id;
        this.dateReg = dateReg;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getDateReg() {
        return dateReg;
    }

    public void setDateReg(String dateReg) {
        this.dateReg = dateReg;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getBudgetYear() {
        return budgetYear;
    }

    public void setBudgetYear(String budgetYear) {
        this.budgetYear = budgetYear;
    }

    public FmsLuCurrency getCurencyId() {
        return curencyId;
    }

    public void setCurencyId(FmsLuCurrency curencyId) {
        this.curencyId = curencyId;
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
        if (!(object instanceof FmsDailyExchangeRateReg)) {
            return false;
        }
        FmsDailyExchangeRateReg other = (FmsDailyExchangeRateReg) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.eep.fcms.entity.FmsDailyExchangeRateReg[ id=" + id + " ]";
    }
    
}
