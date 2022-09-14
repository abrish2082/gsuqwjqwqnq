/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.entity;

import java.io.Serializable;
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
 * @author beharu
 */
@Entity
@Table(name = "fms_product_budget" )
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FmsProductBudget.findAll", query = "SELECT f FROM FmsProductBudget f"),
    @NamedQuery(name = "FmsProductBudget.findByBudgetId", query = "SELECT f FROM FmsProductBudget f WHERE f.budgetId = :budgetId"),
    @NamedQuery(name = "FmsProductBudget.findAllByBudgetYear", query = "SELECT f FROM FmsProductBudget f WHERE f.budgetYear = :budgetYear"),
    @NamedQuery(name = "FmsProductBudget.findByBudgetYear", query = "SELECT f FROM FmsProductBudget f WHERE f.budgetYear LIKE :budgetYear"),
    @NamedQuery(name = "FmsProductBudget.findByProductId", query = "SELECT f FROM FmsProductBudget f WHERE f.productId = :productId"),
    @NamedQuery(name = "FmsProductBudget.findByMonth", query = "SELECT f FROM FmsProductBudget f WHERE f.month = :month"),
    @NamedQuery(name = "FmsProductBudget.findByQuantity", query = "SELECT f FROM FmsProductBudget f WHERE f.quantity = :quantity"),
    @NamedQuery(name = "FmsProductBudget.findByProduct", query = "SELECT f FROM FmsProductBudget f WHERE f.product = :product")})
public class FmsProductBudget implements Serializable {

    @Id
   @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FMS_PRODUCT_BUDGET_BUDGET__SEQ")
    @SequenceGenerator(name = "FMS_PRODUCT_BUDGET_BUDGET__SEQ", sequenceName = "FMS_PRODUCT_BUDGET_BUDGET__SEQ", allocationSize = 1)
    @Basic(optional = false)
    @Column(name = "budget_id", nullable = false)
    private Integer budgetId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "budget_year", nullable = false, length = 45)
    private String budgetYear;
    @Basic(optional = false)
    @NotNull
    @Column(name = "product_id", nullable = false)
    private int productId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(nullable = false, length = 45)
    private String month;
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private double quantity;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(nullable = false, length = 45)
    private String product;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "Acount_Code", nullable = false, length = 45)
    private String acountCode;
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private double amount;

    /**
     *
     */
        public FmsProductBudget() {
    }

    /**
     *
     * @param budgetId
     */
    public FmsProductBudget(Integer budgetId) {
        this.budgetId = budgetId;
    }

    /**
     *
     * @param budgetId
     * @param budgetYear
     * @param productId
     * @param month
     * @param quantity
     * @param product
     * @param acountCode
     * @param amount
     */
    public FmsProductBudget(Integer budgetId, String budgetYear, int productId, String month, double quantity, String product, String acountCode, double amount) {
        this.budgetId = budgetId;
        this.budgetYear = budgetYear;
        this.productId = productId;
        this.month = month;
        this.quantity = quantity;
        this.product = product;
        this.acountCode = acountCode;
        this.amount = amount;
    }

    /**
     *
     * @return
     */
    public Integer getBudgetId() {
        return budgetId;
    }

    /**
     *
     * @param budgetId
     */
    public void setBudgetId(Integer budgetId) {
        this.budgetId = budgetId;
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
    public int getProductId() {
        return productId;
    }

    /**
     *
     * @param productId
     */
    public void setProductId(int productId) {
        this.productId = productId;
    }

    /**
     *
     * @return
     */
    public String getMonth() {
        return month;
    }

    /**
     *
     * @param month
     */
    public void setMonth(String month) {
        this.month = month;
    }

    /**
     *
     * @return
     */
    public double getQuantity() {
        return quantity;
    }

    /**
     *
     * @param quantity
     */
    public void setQuantity(double quantity) {
        this.quantity = quantity;
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
    public String getAcountCode() {
        return acountCode;
    }

    /**
     *
     * @param acountCode
     */
    public void setAcountCode(String acountCode) {
        this.acountCode = acountCode;
    }

    /**
     *
     * @return
     */
    public double getAmount() {
        return amount;
    }

    /**
     *
     * @param amount
     */
    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (budgetId != null ? budgetId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FmsProductBudget)) {
            return false;
        }
        FmsProductBudget other = (FmsProductBudget) object;
        if ((this.budgetId == null && other.budgetId != null) || (this.budgetId != null && !this.budgetId.equals(other.budgetId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return budgetYear;
    }

}
