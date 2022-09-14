/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.entity.budget;

import et.gov.eep.fcms.entity.admin.FmsGeneralLedger;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author user
 */
@Entity
@Table(name = "FMS_BUDGET_CODE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FmsBudgetCode.findAll", query = "SELECT f FROM FmsBudgetCode f"),
    @NamedQuery(name = "FmsBudgetCode.findByBudgetId", query = "SELECT f FROM FmsBudgetCode f WHERE f.budgetId = :budgetId"),
    @NamedQuery(name = "FmsBudgetCode.findByBudgetCode", query = "SELECT f FROM FmsBudgetCode f WHERE f.budgetCode = :budgetCode"),
    @NamedQuery(name = "FmsBudgetCode.findByBudgetCodeLike", query = "SELECT f FROM FmsBudgetCode f WHERE f.budgetCode LIKE :budgetCode"),
    @NamedQuery(name = "FmsBudgetCode.findByBudgetTitle", query = "SELECT f FROM FmsBudgetCode f WHERE f.budgetTitle = :budgetTitle"),
    @NamedQuery(name = "FmsBudgetCode.findByType", query = "SELECT f FROM FmsBudgetCode f WHERE f.type = :type")})
public class FmsBudgetCode implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "BUDGET_ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FMS_Budget_CODE")
    @SequenceGenerator(name = "FMS_Budget_CODE", sequenceName = "FMS_Budget_CODE_ID", allocationSize = 1)
    private int budgetId;
    @Column(name = "BUDGET_CODE")
    private String budgetCode;
    @Column(name = "BUDGET_TITLE")
    private String budgetTitle;
    @Column(name = "TYPE")
    private String type;
    @JoinColumn(name = "GENERAL_LEDGER", referencedColumnName = "GENERAL_LEDGER_ID")
    @ManyToOne
    private FmsGeneralLedger generalLedger;
    @OneToMany(mappedBy = "budgetCode", fetch = FetchType.LAZY)
    private List<FmsCapitalBudgetDetail> fmsCapitalBudgetDetailList;

    /**
     *
     */
    @Transient
    private String columnName;

    @Transient
    private String columnValue;

    public FmsBudgetCode() {
    }

    public String getColumnValue() {
        return columnValue;
    }

    public void setColumnValue(String columnValue) {
        this.columnValue = columnValue;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public FmsBudgetCode(int budgetId) {
        this.budgetId = budgetId;
    }

    public int getBudgetId() {
        return budgetId;
    }

    public void setBudgetId(int budgetId) {
        this.budgetId = budgetId;
    }

    public String getBudgetCode() {
        return budgetCode;
    }

    public void setBudgetCode(String budgetCode) {
        this.budgetCode = budgetCode;
    }

    public FmsGeneralLedger getGeneralLedger() {
        return generalLedger;
    }

    public void setGeneralLedger(FmsGeneralLedger generalLedger) {
        this.generalLedger = generalLedger;
    }

    public String getBudgetTitle() {
        return budgetTitle;
    }

    /**
     *
     * @param budgetTitle
     */
    public void setBudgetTitle(String budgetTitle) {
        this.budgetTitle = budgetTitle;
    }

    /**
     *
     * @return
     */
    public String getType() {
        return type;
    }

    /**
     *
     * @param type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     *
     * @return
     */
    @XmlTransient
    public List<FmsCapitalBudgetDetail> getFmsCapitalBudgetDetailList() {
        if (fmsCapitalBudgetDetailList == null) {
            fmsCapitalBudgetDetailList = new ArrayList<>();
        }
        return fmsCapitalBudgetDetailList;
    }

    public void setFmsCapitalBudgetDetailList(List<FmsCapitalBudgetDetail> fmsCapitalBudgetDetailList) {
        this.fmsCapitalBudgetDetailList = fmsCapitalBudgetDetailList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FmsBudgetCode)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return getBudgetCode();
    }

}
