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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author PO
 */
@Entity
@Table(name = "FMS_OPERATING_BUDGET_TASKS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FmsOperatingBudgetTasks.findAll", query = "SELECT f FROM FmsOperatingBudgetTasks f"),
    @NamedQuery(name = "FmsOperatingBudgetTasks.findByOBTaskId", query = "SELECT f FROM FmsOperatingBudgetTasks f WHERE f.oBTaskId = :oBTaskId"),
    @NamedQuery(name = "FmsOperatingBudgetTasks.findByOBDtlAndSL", query = "SELECT f FROM FmsOperatingBudgetTasks f WHERE f.oBDetailFk = :oBDetailFk AND f.slId = :slId"),
    @NamedQuery(name = "FmsOperatingBudgetTasks.findByOBudgetReqCode", query = "SELECT f FROM FmsOperatingBudgetTasks f WHERE f.oBDetailFk.operatingBudgetId.requestCode = :requestCode"),
    @NamedQuery(name = "FmsOperatingBudgetTasks.findByOBudgetDetailId", query = "SELECT f FROM FmsOperatingBudgetTasks f WHERE f.oBDetailFk = :oBDetailFk"),
    @NamedQuery(name = "FmsOperatingBudgetTasks.findByTaskName", query = "SELECT f FROM FmsOperatingBudgetTasks f WHERE f.taskName = :taskName"),
    @NamedQuery(name = "FmsOperatingBudgetTasks.findByAllotedAmount", query = "SELECT f FROM FmsOperatingBudgetTasks f WHERE f.allotedAmount = :allotedAmount")})
public class FmsOperatingBudgetTasks implements Serializable {

    @Column(name = "ALLOTED_AMOUNT")
    private BigDecimal allotedAmount;
    @Column(name = "REMAINING_AMOUNT")
    private BigDecimal remainingAmount;
    @Column(name = "APPROVED_AMOUNT")
    private BigDecimal approvedAmount;
    @JoinColumn(name = "SL_ID", referencedColumnName = "SUBSIDIARY_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private FmsSubsidiaryLedger slId;
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "O_B_TASK_ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FMS_OB_TASKS_SEQ")
    @SequenceGenerator(name = "FMS_OB_TASKS_SEQ", sequenceName = "FMS_OB_TASKS_SEQ", allocationSize = 1)
    private Integer oBTaskId;
    @Size(max = 20)
    @Column(name = "TASK_NAME")
    private String taskName;
    @JoinColumn(name = "O_B_DETAIL_FK", referencedColumnName = "O_B_DETAIL_ID")
    @ManyToOne
    private FmsOperatingBudgetDetail oBDetailFk;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "obTaskId")
    private List<FmsObDisbursement> fmsObDisbursementList;
    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "fromObId")
    private List<FmsObTransferDetail> fmsObTransferDetailList;
    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "toObId")
    private List<FmsObTransferDetail> fmsObTransferDetailList1;
    @OneToMany(mappedBy = "obTaskId")
    private List<FmsBudgetControl> fmsBudgetControlList;

    public FmsOperatingBudgetTasks() {
    }

    public FmsOperatingBudgetTasks(Integer oBTaskId) {
        this.oBTaskId = oBTaskId;
    }

    public Integer getOBTaskId() {
        return oBTaskId;
    }

    public void setOBTaskId(Integer oBTaskId) {
        this.oBTaskId = oBTaskId;
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

    public FmsOperatingBudgetDetail getOBDetailFk() {
        return oBDetailFk;
    }

    public void setOBDetailFk(FmsOperatingBudgetDetail oBDetailFk) {
        this.oBDetailFk = oBDetailFk;
    }

    @XmlTransient
    public List<FmsObTransferDetail> getFmsObTransferDetailList() {
        if (fmsObTransferDetailList == null) {
            fmsObTransferDetailList = new ArrayList<>();
        }
        return fmsObTransferDetailList;
    }

    public void setFmsObTransferDetailList(List<FmsObTransferDetail> fmsObTransferDetailList) {
        this.fmsObTransferDetailList = fmsObTransferDetailList;
    }

    @XmlTransient
    public List<FmsObTransferDetail> getFmsObTransferDetailList1() {
        if (fmsObTransferDetailList1 == null) {
            fmsObTransferDetailList1 = new ArrayList<>();
        }
        return fmsObTransferDetailList1;
    }

    public void setFmsObTransferDetailList1(List<FmsObTransferDetail> fmsObTransferDetailList1) {
        this.fmsObTransferDetailList1 = fmsObTransferDetailList1;
    }

    @XmlTransient
    public List<FmsBudgetControl> getFmsBudgetControlList() {
        return fmsBudgetControlList;
    }

    public void setFmsBudgetControlList(List<FmsBudgetControl> fmsBudgetControlList) {
        this.fmsBudgetControlList = fmsBudgetControlList;
    }

    @XmlTransient
    public List<FmsObDisbursement> getFmsObDisbursementList() {
        return fmsObDisbursementList;
    }

    public void setFmsObDisbursementList(List<FmsObDisbursement> fmsObDisbursementList) {
        this.fmsObDisbursementList = fmsObDisbursementList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (oBTaskId != null ? oBTaskId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FmsOperatingBudgetTasks)) {
            return false;
        }
        FmsOperatingBudgetTasks other = (FmsOperatingBudgetTasks) object;
        if ((this.oBTaskId == null && other.oBTaskId != null) || (this.oBTaskId != null && !this.oBTaskId.equals(other.oBTaskId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.eep.fcms.entity.FmsOperatingBudgetTasks[ oBTaskId=" + oBTaskId + " ]";
    }

    public FmsSubsidiaryLedger getSlId() {
        return slId;
    }

    public void setSlId(FmsSubsidiaryLedger slId) {
        this.slId = slId;
    }

}
