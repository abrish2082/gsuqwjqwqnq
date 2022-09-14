/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.entity.budget;

import et.gov.eep.fcms.entity.admin.FmsSubsidiaryLedger;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
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
@Table(name = "FMS_CAPITAL_BUDGET_TASKS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FmsCapitalBudgetTasks.findAll", query = "SELECT f FROM FmsCapitalBudgetTasks f"),
    @NamedQuery(name = "FmsCapitalBudgetTasks.findByCBTasksId", query = "SELECT f FROM FmsCapitalBudgetTasks f WHERE f.cBTasksId = :cBTasksId"),
    @NamedQuery(name = "FmsCapitalBudgetTasks.findByTaskName", query = "SELECT f FROM FmsCapitalBudgetTasks f WHERE f.taskName = :taskName"),
    @NamedQuery(name = "FmsCapitalBudgetTasks.findByBgtDtl", query = "SELECT f FROM FmsCapitalBudgetTasks f WHERE f.cBDetailFk = :cBDetailFk"),
    @NamedQuery(name = "FmsCapitalBudgetTasks.findByBgtDtlAndSL", query = "SELECT f FROM FmsCapitalBudgetTasks f WHERE f.cBDetailFk = :cBDetailFk AND f.slId = :slId"),
    @NamedQuery(name = "FmsCapitalBudgetTasks.findByCBudgetReqCode", query = "SELECT f FROM FmsCapitalBudgetTasks f WHERE f.cBDetailFk.capitalBudgetId.requestCode = :requestCode"),
    @NamedQuery(name = "FmsCapitalBudgetTasks.findByAllotedAmount", query = "SELECT f FROM FmsCapitalBudgetTasks f WHERE f.allotedAmount = :allotedAmount")})
public class FmsCapitalBudgetTasks implements Serializable {

    @JoinColumn(name = "SL_ID", referencedColumnName = "SUBSIDIARY_ID")
    @ManyToOne
    private FmsSubsidiaryLedger slId;
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "C_B_TASKS_ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FMS_CB_TASKS_SEQ")
    @SequenceGenerator(name = "FMS_CB_TASKS_SEQ", sequenceName = "FMS_CB_TASKS_SEQ", allocationSize = 1)
    private Integer cBTasksId;
    @Size(max = 20)
    @Column(name = "TASK_NAME")
    private String taskName;
    @Column(name = "ALLOTED_AMOUNT")
    private BigDecimal allotedAmount;
    @Column(name = "REMAINING_AMOUNT")
    private BigDecimal remainingAmount;
    @Column(name = "APPROVED_AMOUNT")
    private BigDecimal approvedAmount;
    @Column(name = "BUDGET_SOURCE")
    private String budgetSource;
    @JoinColumn(name = "C_B_DETAIL_FK", referencedColumnName = "CB_DETAIL_ID")
    @ManyToOne
    private FmsCapitalBudgetDetail cBDetailFk;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cbTaskId")
    private List<FmsCbDisbursement> fmsCbDisbursementList;
    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "fromCbId")
    private List<FmsCbTransferDetail> fmsCbTransferDetailList;
    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "toCbId")
    private List<FmsCbTransferDetail> fmsCbTransferDetailList1;
    @OneToMany(mappedBy = "cbTaskId")
    private List<FmsBudgetControl> fmsBudgetControlList;

    public FmsCapitalBudgetTasks() {
    }

    public FmsCapitalBudgetTasks(Integer cBTasksId) {
        this.cBTasksId = cBTasksId;
    }

    public Integer getCBTasksId() {
        return cBTasksId;
    }

    public void setCBTasksId(Integer cBTasksId) {
        this.cBTasksId = cBTasksId;
    }

    public FmsCapitalBudgetDetail getcBDetailFk() {
        return cBDetailFk;
    }

    public void setcBDetailFk(FmsCapitalBudgetDetail cBDetailFk) {
        this.cBDetailFk = cBDetailFk;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public BigDecimal getAllotedAmount() {
        return allotedAmount;
    }

    public void setAllotedAmount(BigDecimal allotedAmount) {
        this.allotedAmount = allotedAmount;
    }

    public BigDecimal getRemainingAmount() {
        return remainingAmount;
    }

    public void setRemainingAmount(BigDecimal remainingAmount) {
        this.remainingAmount = remainingAmount;
    }

    public BigDecimal getApprovedAmount() {
        return approvedAmount;
    }

    public void setApprovedAmount(BigDecimal approvedAmount) {
        this.approvedAmount = approvedAmount;
    }

    public FmsCapitalBudgetDetail getCBDetailFk() {
        return cBDetailFk;
    }

    public void setCBDetailFk(FmsCapitalBudgetDetail cBDetailFk) {
        this.cBDetailFk = cBDetailFk;
    }

    public String getBudgetSource() {
        return budgetSource;
    }

    public void setBudgetSource(String budgetSource) {
        this.budgetSource = budgetSource;
    }

    @XmlTransient
    public List<FmsBudgetControl> getFmsBudgetControlList() {
        return fmsBudgetControlList;
    }

    public void setFmsBudgetControlList(List<FmsBudgetControl> fmsBudgetControlList) {
        this.fmsBudgetControlList = fmsBudgetControlList;
    }
    
    @XmlTransient
    public List<FmsCbDisbursement> getFmsCbDisbursementList() {
        return fmsCbDisbursementList;
    }

    public void setFmsCbDisbursementList(List<FmsCbDisbursement> fmsCbDisbursementList) {
        this.fmsCbDisbursementList = fmsCbDisbursementList;
    }

    @XmlTransient
    public List<FmsCbTransferDetail> getFmsCbTransferDetailList() {
        if (fmsCbTransferDetailList == null) {
            fmsCbTransferDetailList = new ArrayList<>();
        }
        return fmsCbTransferDetailList;
    }

    public void setFmsCbTransferDetailList(List<FmsCbTransferDetail> fmsCbTransferDetailList) {
        this.fmsCbTransferDetailList = fmsCbTransferDetailList;
    }

    @XmlTransient
    public List<FmsCbTransferDetail> getFmsCbTransferDetailList1() {
        if (fmsCbTransferDetailList1 == null) {
            fmsCbTransferDetailList1 = new ArrayList<>();
        }
        return fmsCbTransferDetailList1;
    }

    public void setFmsCbTransferDetailList1(List<FmsCbTransferDetail> fmsCbTransferDetailList1) {
        this.fmsCbTransferDetailList1 = fmsCbTransferDetailList1;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cBTasksId != null ? cBTasksId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FmsCapitalBudgetTasks)) {
            return false;
        }
        FmsCapitalBudgetTasks other = (FmsCapitalBudgetTasks) object;
        if ((this.cBTasksId == null && other.cBTasksId != null) || (this.cBTasksId != null && !this.cBTasksId.equals(other.cBTasksId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.eep.fcms.entity.FmsCapitalBudgetTasks[ cBTasksId=" + cBTasksId + " ]";
    }

    public FmsSubsidiaryLedger getSlId() {
        return slId;
    }

    public void setSlId(FmsSubsidiaryLedger slId) {
        this.slId = slId;
    }

}
