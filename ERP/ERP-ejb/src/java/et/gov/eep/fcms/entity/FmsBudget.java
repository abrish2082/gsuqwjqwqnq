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
 * @author user
 */
@Entity
@Table(name = "FMS_BUDGET")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FmsBudget.findAll", query = "SELECT f FROM FmsBudget f"),
    @NamedQuery(name = "FmsBudget.findByBudgetId", query = "SELECT f FROM FmsBudget f WHERE f.budgetId = :budgetId"),
//    @NamedQuery(name = "FmsBudget.findByBudgetYear", query = "SELECT f FROM FmsBudget f,FmsCapitalBudget c WHERE f.budgetYear = :budgetYear"),
    @NamedQuery(name = "FmsBudget.searchByBudgetYear", query = "SELECT f FROM FmsBudget f WHERE f.budgetYear LIKE :budgetYear"),
    @NamedQuery(name = "FmsBudget.getOperatingBudgetByBudgetType", query = "SELECT f FROM FmsBudget f WHERE f.budgetType LIKE :budgetType"),
    @NamedQuery(name = "FmsBudget.findByBudgetType", query = "SELECT f FROM FmsBudget f WHERE f.budgetType = :budgetType")})
public class FmsBudget implements Serializable {   
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "BUDGET_ID")    
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BUDGET_ID")
    @SequenceGenerator(name = "FMS_BUDGET_ID", sequenceName = "FMS_BUDGET_ID", allocationSize = 1)
    private Integer budgetId; 
    @Size(max = 20)
    @Column(name = "BUDGET_YEAR")   
    private String budgetYear;
    @Size(max = 20)
    @Column(name = "BUDGET_TYPE")
    private String budgetType;  
    
    /**
     *
     */
        public FmsBudget() {
    }

    /**
     *
     * @param budgetId
     */
    public FmsBudget(Integer budgetId) {
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
    public String getBudgetType() {
        return budgetType;
    }

    /**
     *
     * @param budgetType
     */
    public void setBudgetType(String budgetType) {
        this.budgetType = budgetType;
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
        if (!(object instanceof FmsBudget)) {
            return false;
        }
        FmsBudget other = (FmsBudget) object;
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
