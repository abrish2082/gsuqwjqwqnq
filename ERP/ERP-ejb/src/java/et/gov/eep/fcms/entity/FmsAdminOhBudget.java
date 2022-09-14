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
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author beharu
 */
@Entity
@Table(name = "fms_admin_oh_budget")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FmsAdminOhBudget.findAll", query = "SELECT f FROM FmsAdminOhBudget f"),
    @NamedQuery(name = "FmsAdminOhBudget.findByBudgetId", query = "SELECT f FROM FmsAdminOhBudget f WHERE f.budgetId = :budgetId"),
    @NamedQuery(name = "FmsAdminOhBudget.findByBudgetYear", query = "SELECT f FROM FmsAdminOhBudget f WHERE f.budgetYear like :budgetYear"),
    @NamedQuery(name = "FmsAdminOhBudget.findByBudgetYearSelected", query = "SELECT f FROM FmsAdminOhBudget f WHERE f.budgetYear = :budgetYear"),
    @NamedQuery(name = "FmsAdminOhBudget.findByItem", query = "SELECT f FROM FmsAdminOhBudget f WHERE f.item = :item"),
    @NamedQuery(name = "FmsAdminOhBudget.findByQuantity", query = "SELECT f FROM FmsAdminOhBudget f WHERE f.quantity = :quantity"),
    @NamedQuery(name = "FmsAdminOhBudget.findByAmount", query = "SELECT f FROM FmsAdminOhBudget f WHERE f.amount = :amount"),
    @NamedQuery(name = "FmsAdminOhBudget.findByRemark", query = "SELECT f FROM FmsAdminOhBudget f WHERE f.remark = :remark"),
    @NamedQuery(name = "FmsAdminOhBudget.findByDepartment", query = "SELECT f FROM FmsAdminOhBudget f WHERE f.department = :department"),
    @NamedQuery(name = "FmsAdminOhBudget.findByAccountCode", query = "SELECT f FROM FmsAdminOhBudget f WHERE f.accountCode = :accountCode")})
public class FmsAdminOhBudget implements Serializable {//accountCode

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "budget_id", nullable = false)
    private Integer budgetId;
    @Size(max = 45)
    @Column(name = "budget_year", length = 45)
    private String budgetYear;
    @Size(max = 45)
    @Column(length = 45)
    private String item;
    private Integer quantity;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(precision = 22)
    private Double amount;
    @Size(max = 100)
    @Column(length = 100)
    private String remark;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "account_code", nullable = true, length = 45)
    private String accountCode;

    private String department;

    /**
     *
     */
    public FmsAdminOhBudget() {
    }

    /**
     *
     * @param budgetId
     */
    public FmsAdminOhBudget(Integer budgetId) {
        this.budgetId = budgetId;
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
    public String getItem() {
        return item;
    }

    /**
     *
     * @param item
     */
    public void setItem(String item) {
        this.item = item;
    }

    /**
     *
     * @return
     */
    public Integer getQuantity() {
        return quantity;
    }

    /**
     *
     * @param quantity
     */
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    /**
     *
     * @return
     */
    public Double getAmount() {
        return amount;
    }

    /**
     *
     * @param amount
     */
    public void setAmount(Double amount) {
        this.amount = amount;
    }

    /**
     *
     * @return
     */
    public String getRemark() {
        return remark;
    }

    /**
     *
     * @param remark
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     *
     * @return
     */
    public String getDepartment() {
        return department;
    }

    /**
     *
     * @param department
     */
    public void setDepartment(String department) {
        this.department = department;
    }

    /**
     *
     * @return
     */
    public String getAccountCode() {
        return accountCode;
    }

    /**
     *
     * @param accountCode
     */
    public void setAccountCode(String accountCode) {
        this.accountCode = accountCode;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (budgetId != null ? budgetId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof FmsAdminOhBudget)) {
            return false;
        }
        FmsAdminOhBudget other = (FmsAdminOhBudget) object;
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
