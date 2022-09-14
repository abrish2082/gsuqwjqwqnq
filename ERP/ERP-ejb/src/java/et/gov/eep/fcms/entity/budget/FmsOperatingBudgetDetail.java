/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.entity.budget;

import et.gov.eep.fcms.entity.admin.FmsGeneralLedger;
import et.gov.eep.prms.entity.PrmsPurchaseReqService;
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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Me
 */
@Entity
@Table(name = "FMS_OPERATING_BUDGET_DETAIL")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FmsOperatingBudgetDetail.findAll", query = "SELECT f FROM FmsOperatingBudgetDetail f"),
    @NamedQuery(name = "FmsOperatingBudgetDetail.findByRequestedAmount", query = "SELECT f FROM FmsOperatingBudgetDetail f WHERE f.requestedAmount = :requestedAmount"),
    @NamedQuery(name = "FmsOperatingBudgetDetail.findByApprovedAmount", query = "SELECT f FROM FmsOperatingBudgetDetail f WHERE f.approvedAmount = :approvedAmount"),
    @NamedQuery(name = "FmsOperatingBudgetDetail.findByRemainingBalance", query = "SELECT f FROM FmsOperatingBudgetDetail f WHERE f.remainingBalance = :remainingBalance"),
    @NamedQuery(name = "FmsOperatingBudgetDetail.findByBudgetDetail", query = "SELECT f FROM FmsOperatingBudgetDetail f WHERE f.budgetDetail = :budgetDetail"),
    @NamedQuery(name = "FmsOperatingBudgetDetail.findByGeneralLedger", query = "SELECT f FROM FmsOperatingBudgetDetail f WHERE f.generalLedger.generalLedgerCode = :generalLedgerCode AND f.operatingBudgetId.requestCode = :requestCode"),
    @NamedQuery(name = "FmsOperatingBudgetDetail.findByStatus", query = "SELECT f FROM FmsOperatingBudgetDetail f WHERE f.status = :status"),
    @NamedQuery(name = "FmsOperatingBudgetDetail.findByRemark", query = "SELECT f FROM FmsOperatingBudgetDetail f WHERE f.remark = :remark"),
    @NamedQuery(name = "FmsOperatingBudgetDetail.findByOBId", query = "SELECT f FROM FmsOperatingBudgetDetail f WHERE f.operatingBudgetId = :operatingBudgetId"),
    @NamedQuery(name = "FmsOperatingBudgetDetail.findByOBDetailId", query = "SELECT f FROM FmsOperatingBudgetDetail f WHERE f.oBDetailId = :oBDetailId")})
public class FmsOperatingBudgetDetail implements Serializable {

    @Column(name = "REQUESTED_AMOUNT")
    private BigDecimal requestedAmount;
    @Column(name = "APPROVED_AMOUNT")
    private BigDecimal approvedAmount;
    @Column(name = "REMAINING_BALANCE")
    private BigDecimal remainingBalance;
    @Column(name = "BUDGET_SOURCE")
    private String budgetSource;
    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "oBDetailFk")
    private List<FmsOperatingBudgetTasks> fmsOperatingBudgetTasksList;
//    @OneToMany(mappedBy = "obDetailId")
//    private List<FmsBudgetControl> fmsBudgetControlList;
    @OneToMany(mappedBy = "budgetCode")
    private List<PrmsPurchaseReqService> prmsPurchaseReqServiceList;
    private static final long serialVersionUID = 1L;

    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "O_B_DETAIL_ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FMS_OB_DETAIL_SEQ")
    @SequenceGenerator(name = "FMS_OB_DETAIL_SEQ", sequenceName = "FMS_OB_DETAIL_SEQ", allocationSize = 1)
    private Integer oBDetailId;
    @Column(name = "BUDGET_DETAIL")
    private String budgetDetail;
    @Column(name = "STATUS")
    private String status;
    @Column(name = "REMARK")
    private String remark;
    @JoinColumn(name = "GENERAL_LEDGER_ID", referencedColumnName = "GENERAL_LEDGER_ID")
    @ManyToOne(optional = false)
    private FmsGeneralLedger generalLedger;
    @JoinColumn(name = "OPERATING_BUDGET_ID", referencedColumnName = "OPERATING_BUDGET_ID")
    @ManyToOne
    private FmsOperatingBudget1 operatingBudgetId;

    public FmsOperatingBudgetDetail() {
    }

    public FmsOperatingBudgetDetail(Integer oBDetailId) {
        this.oBDetailId = oBDetailId;
    }

    public BigDecimal getRequestedAmount() {
        return requestedAmount;
    }

    public void setRequestedAmount(BigDecimal requestedAmount) {
        this.requestedAmount = requestedAmount;
    }

    public BigDecimal getApprovedAmount() {
        return approvedAmount;
    }

    public void setApprovedAmount(BigDecimal approvedAmount) {
        this.approvedAmount = approvedAmount;
    }

    public BigDecimal getRemainingBalance() {
        return remainingBalance;
    }

    public void setRemainingBalance(BigDecimal remainingBalance) {
        this.remainingBalance = remainingBalance;
    }

    public String getBudgetDetail() {
        return budgetDetail;
    }

    public void setBudgetDetail(String budgetDetail) {
        this.budgetDetail = budgetDetail;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getOBDetailId() {
        return oBDetailId;
    }

    public void setOBDetailId(Integer oBDetailId) {
        this.oBDetailId = oBDetailId;
    }

    public FmsGeneralLedger getGeneralLedger() {
        return generalLedger;
    }

    public void setGeneralLedger(FmsGeneralLedger generalLedger) {
        this.generalLedger = generalLedger;
    }

    public FmsOperatingBudget1 getOperatingBudgetId() {
        return operatingBudgetId;
    }

    public void setOperatingBudgetId(FmsOperatingBudget1 operatingBudgetId) {
        this.operatingBudgetId = operatingBudgetId;
    }

    public String getBudgetSource() {
        return budgetSource;
    }

    public void setBudgetSource(String budgetSource) {
        this.budgetSource = budgetSource;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (oBDetailId != null ? oBDetailId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FmsOperatingBudgetDetail)) {
            return false;
        }
        FmsOperatingBudgetDetail other = (FmsOperatingBudgetDetail) object;
        if ((this.oBDetailId == null && other.oBDetailId != null) || (this.oBDetailId != null && !this.oBDetailId.equals(other.oBDetailId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return getGeneralLedger().getGeneralLedgerCode();
    }

//    @XmlTransient
//    public List<FmsBudgetControl> getFmsBudgetControlList() {
//        return fmsBudgetControlList;
//    }
//
//    public void setFmsBudgetControlList(List<FmsBudgetControl> fmsBudgetControlList) {
//        this.fmsBudgetControlList = fmsBudgetControlList;
//    }

    @XmlTransient
    public List<PrmsPurchaseReqService> getPrmsPurchaseReqServiceList() {
        if (prmsPurchaseReqServiceList == null) {
            prmsPurchaseReqServiceList = new ArrayList<>();
        }
        return prmsPurchaseReqServiceList;
    }

    public void setPrmsPurchaseReqServiceList(List<PrmsPurchaseReqService> prmsPurchaseReqServiceList) {
        this.prmsPurchaseReqServiceList = prmsPurchaseReqServiceList;
    }

    @XmlTransient
    public List<FmsOperatingBudgetTasks> getFmsOperatingBudgetTasksList() {
        if (fmsOperatingBudgetTasksList == null) {
            fmsOperatingBudgetTasksList = new ArrayList<>();
        }
        return fmsOperatingBudgetTasksList;
    }

    public void setFmsOperatingBudgetTasksList(List<FmsOperatingBudgetTasks> fmsOperatingBudgetTasksList) {
        this.fmsOperatingBudgetTasksList = fmsOperatingBudgetTasksList;
    }

    public void addOperatingBudgetTasksList(FmsOperatingBudgetTasks fmsOperatingBudgetTasks) {
        if (fmsOperatingBudgetTasks.getOBDetailFk() != this) {
            this.getFmsOperatingBudgetTasksList().add(fmsOperatingBudgetTasks);
            fmsOperatingBudgetTasks.setOBDetailFk(this);
        }
    }

}
