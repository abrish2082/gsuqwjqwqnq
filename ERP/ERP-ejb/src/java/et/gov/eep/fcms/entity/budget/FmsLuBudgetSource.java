/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.entity.budget;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
 * @author PO
 */
@Entity
@Table(name = "FMS_LU_BUDGET_SOURCE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FmsLuBudgetSource.findAll", query = "SELECT f FROM FmsLuBudgetSource f"),
    @NamedQuery(name = "FmsLuBudgetSource.findByBudgetSourceId", query = "SELECT f FROM FmsLuBudgetSource f WHERE f.budgetSourceId = :budgetSourceId"),
    @NamedQuery(name = "FmsLuBudgetSource.findByBudgetSourceName", query = "SELECT f FROM FmsLuBudgetSource f WHERE f.budgetSourceName = :budgetSourceName")})
public class FmsLuBudgetSource implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "BUDGET_SOURCE_ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FMS_LU_BUDGET_SOURCE_SEQ")
    @SequenceGenerator( name = "FMS_LU_BUDGET_SOURCE_SEQ", sequenceName = "FMS_LU_BUDGET_SOURCE_SEQ", allocationSize = 1)
    private Integer budgetSourceId;
    @Size(max = 20)
    @Column(name = "BUDGET_SOURCE_NAME")
    private String budgetSourceName;    

    public FmsLuBudgetSource() {
    }

    public FmsLuBudgetSource(Integer budgetSourceId) {
        this.budgetSourceId = budgetSourceId;
    }

    public Integer getBudgetSourceId() {
        return budgetSourceId;
    }

    public void setBudgetSourceId(Integer budgetSourceId) {
        this.budgetSourceId = budgetSourceId;
    }

    public String getBudgetSourceName() {
        return budgetSourceName;
    }

    public void setBudgetSourceName(String budgetSourceName) {
        this.budgetSourceName = budgetSourceName;
    }    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (budgetSourceId != null ? budgetSourceId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FmsLuBudgetSource)) {
            return false;
        }
        FmsLuBudgetSource other = (FmsLuBudgetSource) object;
        if ((this.budgetSourceId == null && other.budgetSourceId != null) || (this.budgetSourceId != null && !this.budgetSourceId.equals(other.budgetSourceId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return budgetSourceName;
    }
    
}
