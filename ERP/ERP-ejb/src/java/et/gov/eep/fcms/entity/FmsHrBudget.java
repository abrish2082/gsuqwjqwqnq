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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author beharu
 */
@Entity
@Table(name = "fms_hr_budget" )
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FmsHrBudget.findAll", query = "SELECT f FROM FmsHrBudget f"),
    @NamedQuery(name = "FmsHrBudget.findByBudgetId", query = "SELECT f FROM FmsHrBudget f WHERE f.budgetId = :budgetId"),
    @NamedQuery(name = "FmsHrBudget.findByBudgetYear", query = "SELECT f FROM FmsHrBudget f WHERE f.budgetYear = :budgetYear"),
    @NamedQuery(name = "FmsHrBudget.findByBudgetYearSelected", query = "SELECT f FROM FmsHrBudget f WHERE f.budgetYear like :budgetYear"),
    @NamedQuery(name = "FmsHrBudget.findByBudgetItem", query = "SELECT f FROM FmsHrBudget f WHERE f.budgetItem = :budgetItem"),
    @NamedQuery(name = "FmsHrBudget.findByAmount", query = "SELECT f FROM FmsHrBudget f WHERE f.amount = :amount"),
    @NamedQuery(name = "FmsHrBudget.findByAccountCode", query = "SELECT f FROM FmsHrBudget f WHERE f.accountCode = :accountCode"),
    @NamedQuery(name = "FmsHrBudget.findByRemark", query = "SELECT f FROM FmsHrBudget f WHERE f.remark = :remark"),
    @NamedQuery(name = "FmsHrBudget.findByDepartment", query = "SELECT f FROM FmsHrBudget f WHERE f.department = :department")})
public class FmsHrBudget implements Serializable {

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
    @Column(name = "budget_item", length = 45)
    private String budgetItem;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(precision = 22)
    private Double amount;
    @Size(max = 45)
    @Column(name = "account_code", length = 45)
    private String accountCode;
    @Size(max = 100)
    @Column(length = 100)
    private String remark;
    @Size(max = 45)
    @Column(length = 45)
    private String department;

    /**
     *
     */
    public FmsHrBudget() {
    }

    /**
     *
     * @param budgetId
     */
    public FmsHrBudget(Integer budgetId) {
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
    public String getBudgetItem() {
        return budgetItem;
    }

    /**
     *
     * @param budgetItem
     */
    public void setBudgetItem(String budgetItem) {
        this.budgetItem = budgetItem;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (budgetId != null ? budgetId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FmsHrBudget)) {
            return false;
        }
        FmsHrBudget other = (FmsHrBudget) object;
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
