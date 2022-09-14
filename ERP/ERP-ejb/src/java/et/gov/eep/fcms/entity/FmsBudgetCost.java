/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author beharuh
 */
@Entity
@Table(name = "fms_budget_cost")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FmsBudgetCost.findAll", query = "SELECT f FROM FmsBudgetCost f"),
    @NamedQuery(name = "FmsBudgetCost.findById", query = "SELECT f FROM FmsBudgetCost f WHERE f.id = :id"),
    @NamedQuery(name = "FmsBudgetCost.findByBudgetYear", query = "SELECT f FROM FmsBudgetCost f WHERE f.budgetYear = :budgetYear"),
    @NamedQuery(name = "FmsBudgetCost.findByBudgetYearSelectd", query = "SELECT DISTINCT f.budgetYear FROM FmsBudgetCost f WHERE f.budgetYear LIKE :budgetYear"),
    @NamedQuery(name = "FmsBudgetCost.findByProduct", query = "SELECT f FROM FmsBudgetCost f WHERE f.product = :product"),
    @NamedQuery(name = "FmsBudgetCost.findByProductAndYear", query = "SELECT f FROM FmsBudgetCost f WHERE f.budgetYear = :budgetYear and(UPPER(f.product) =:product or LOWER(f.product) =:product)"),
    @NamedQuery(name = "FmsBudgetCost.findByProductSelectd", query = "SELECT DISTINCT f.product FROM FmsBudgetCost f WHERE upper(f.product) LIKE :product or lower(f.product) LIKE :product"),
    @NamedQuery(name = "FmsBudgetCost.findByFobPricePm", query = "SELECT f FROM FmsBudgetCost f WHERE f.fobPricePm = :fobPricePm"),
    @NamedQuery(name = "FmsBudgetCost.findByPremium", query = "SELECT f FROM FmsBudgetCost f WHERE f.premium = :premium"),
    @NamedQuery(name = "FmsBudgetCost.findByCfUsd", query = "SELECT f FROM FmsBudgetCost f WHERE f.cfUsd = :cfUsd"),
    @NamedQuery(name = "FmsBudgetCost.findByExchRate", query = "SELECT f FROM FmsBudgetCost f WHERE f.exchRate = :exchRate"),
    @NamedQuery(name = "FmsBudgetCost.findByCfBirr", query = "SELECT f FROM FmsBudgetCost f WHERE f.cfBirr = :cfBirr")})
public class FmsBudgetCost implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer id;
    @Size(max = 45)
    @Column(name = "budget_year", length = 45)
    private String budgetYear;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(nullable = false, length = 45)
    private String product;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fob_price_pm", nullable = false)
    private double fobPricePm;
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private double premium;
    @Basic(optional = false)
    @NotNull
    @Column(name = "cf_usd", nullable = false)
    private double cfUsd;
    @Basic(optional = false)
    @NotNull
    @Column(name = "exch_rate", nullable = false)
    private double exchRate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "cf_birr", nullable = false)
    private double cfBirr;

    /**
     *
     */
    public FmsBudgetCost() {
    }

    /**
     *
     * @param id
     */
    public FmsBudgetCost(Integer id) {
        this.id = id;
    }

    /**
     *
     * @param id
     * @param product
     * @param fobPricePm
     * @param premium
     * @param cfUsd
     * @param exchRate
     * @param cfBirr
     */
    public FmsBudgetCost(Integer id, String product, double fobPricePm, double premium, double cfUsd, double exchRate, double cfBirr) {
        this.id = id;
        this.product = product;
        this.fobPricePm = fobPricePm;
        this.premium = premium;
        this.cfUsd = cfUsd;
        this.exchRate = exchRate;
        this.cfBirr = cfBirr;
    }

    /**
     *
     * @return
     */
    public Integer getId() {
        return id;
    }

    /**
     *
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     *
     * @return
     */
    public String getBudgetYear() {
        return budgetYear;
    }

    /**
     *
     * @param budgetYear
     */
    public void setBudgetYear(String budgetYear) {
        this.budgetYear = budgetYear;
    }

    /**
     *
     * @return
     */
    public String getProduct() {
        return product;
    }

    /**
     *
     * @param product
     */
    public void setProduct(String product) {
        this.product = product;
    }

    /**
     *
     * @return
     */
    public double getFobPricePm() {
        return fobPricePm;
    }

    /**
     *
     * @param fobPricePm
     */
    public void setFobPricePm(double fobPricePm) {
        this.fobPricePm = fobPricePm;
    }

    /**
     *
     * @return
     */
    public double getPremium() {
        return premium;
    }

    /**
     *
     * @param premium
     */
    public void setPremium(double premium) {
        this.premium = premium;
    }

    /**
     *
     * @return
     */
    public double getCfUsd() {
        return cfUsd;
    }

    /**
     *
     * @param cfUsd
     */
    public void setCfUsd(double cfUsd) {
        this.cfUsd = cfUsd;
    }

    /**
     *
     * @return
     */
    public double getExchRate() {
        return exchRate;
    }

    /**
     *
     * @param exchRate
     */
    public void setExchRate(double exchRate) {
        this.exchRate = exchRate;
    }

    /**
     *
     * @return
     */
    public double getCfBirr() {
        return cfBirr;
    }

    /**
     *
     * @param cfBirr
     */
    public void setCfBirr(double cfBirr) {
        this.cfBirr = cfBirr;
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
        if (!(object instanceof FmsBudgetCost)) {
            return false;
        }
        FmsBudgetCost other = (FmsBudgetCost) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return budgetYear;
    }

    /**
     *
     * @param listForcastedER
     */
    public void setExchRate(List<Double> listForcastedER) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
