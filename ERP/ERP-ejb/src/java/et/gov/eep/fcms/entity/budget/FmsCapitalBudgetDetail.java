/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.entity.budget;

import et.gov.eep.pms.entity.PmsWorkAuthorization;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
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
@Table(name = "FMS_CAPITAL_BUDGET_DETAIL")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FmsCapitalBudgetDetail.findAll", query = "SELECT f FROM FmsCapitalBudgetDetail f"),
    @NamedQuery(name = "FmsCapitalBudgetDetail.findByCBId", query = "SELECT f FROM FmsCapitalBudgetDetail f WHERE f.capitalBudgetId = :capitalBudgetId"),
    @NamedQuery(name = "FmsCapitalBudgetDetail.findByCbDetailId", query = "SELECT f FROM FmsCapitalBudgetDetail f WHERE f.cbDetailId = :cbDetailId"),
    @NamedQuery(name = "FmsCapitalBudgetDetail.findByRequestedAmount", query = "SELECT f FROM FmsCapitalBudgetDetail f WHERE f.requestedAmount = :requestedAmount"),
    @NamedQuery(name = "FmsCapitalBudgetDetail.findByApprovedAmount", query = "SELECT f FROM FmsCapitalBudgetDetail f WHERE f.approvedAmount = :approvedAmount"),
    @NamedQuery(name = "FmsCapitalBudgetDetail.findByRemainingAmount", query = "SELECT f FROM FmsCapitalBudgetDetail f WHERE f.remainingAmount = :remainingAmount"),
    @NamedQuery(name = "FmsCapitalBudgetDetail.findByStatus", query = "SELECT f FROM FmsCapitalBudgetDetail f WHERE f.status = :status"),
    @NamedQuery(name = "FmsCapitalBudgetDetail.fetchCBDetail", query = "SELECT f FROM FmsCapitalBudgetDetail f WHERE f.capitalBudgetId.status = 10 AND f.capitalBudgetId.ccSsJunction.fmsCostCenter.costCenterId = :costCenterId AND f.capitalBudgetId.budgetYear.budgetYear = :budgetYear"),
    @NamedQuery(name = "FmsCapitalBudgetDetail.fetchCapBudgetDetail", query = "SELECT f FROM FmsCapitalBudgetDetail f WHERE f.capitalBudgetId.status = 10 AND f.capitalBudgetId.ccSsJunction.fmsCostCenter.costCenterId = :costCenterId AND f.capitalBudgetId.budgetYear.budgetYear = :budgetYear AND f.budgetCode.budgetCode = :budgetCode"),
    @NamedQuery(name = "FmsCapitalBudgetDetail.getCapBudgetDetail", query = "SELECT f FROM FmsCapitalBudgetDetail f WHERE f.capitalBudgetId.status = 10 AND f.capitalBudgetId.ccSsJunction = :ccSsJunction AND f.capitalBudgetId.budgetYear.budgetYear = :budgetYear AND f.budgetCode.budgetCode = :budgetCode"),
    @NamedQuery(name = "FmsCapitalBudgetDetail.findByRemark", query = "SELECT f FROM FmsCapitalBudgetDetail f WHERE f.remark = :remark")})
public class FmsCapitalBudgetDetail implements Serializable {

    @Column(name = "REQUESTED_AMOUNT")
    private BigDecimal requestedAmount;
    @Column(name = "APPROVED_AMOUNT")
    private BigDecimal approvedAmount;
    @Column(name = "REMAINING_AMOUNT")
    private BigDecimal remainingAmount;
    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "cBDetailFk")
    private List<FmsCapitalBudgetTasks> fmsCapitalBudgetTasksList;

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "CB_DETAIL_ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FMS_SEQ_CAPITALB_DETAIL")
    @SequenceGenerator(name = "FMS_SEQ_CAPITALB_DETAIL", sequenceName = "FMS_SEQ_CAPITALB_DETAIL", allocationSize = 1)
    private Integer cbDetailId;
    @Column(name = "STATUS")
    private String status;
    @Column(name = "REMARK")
    private String remark;
    @JoinColumn(name = "BUDGET_CODE", referencedColumnName = "BUDGET_ID")
    @ManyToOne(optional = false)
    private FmsBudgetCode budgetCode;
    @JoinColumn(name = "CAPITAL_BUDGET_ID", referencedColumnName = "CAPITAL_BUDGET_ID")
    @ManyToOne
    private FmsCapitalBudget1 capitalBudgetId;

    public FmsCapitalBudgetDetail() {
    }

    public FmsCapitalBudgetDetail(Integer cbDetailId) {
        this.cbDetailId = cbDetailId;
    }

    public Integer getCbDetailId() {
        return cbDetailId;
    }

    public void setCbDetailId(Integer cbDetailId) {
        this.cbDetailId = cbDetailId;
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

    public BigDecimal getRemainingAmount() {
        return remainingAmount;
    }

    public void setRemainingAmount(BigDecimal remainingAmount) {
        this.remainingAmount = remainingAmount;
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

    public FmsBudgetCode getBudgetCode() {
        return budgetCode;
    }

    public void setBudgetCode(FmsBudgetCode budgetCode) {
        this.budgetCode = budgetCode;
    }

    public FmsCapitalBudget1 getCapitalBudgetId() {
        return capitalBudgetId;
    }

    public void setCapitalBudgetId(FmsCapitalBudget1 capitalBudgetId) {
        this.capitalBudgetId = capitalBudgetId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cbDetailId != null ? cbDetailId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FmsCapitalBudgetDetail)) {
            return false;
        }
        FmsCapitalBudgetDetail other = (FmsCapitalBudgetDetail) object;
        if ((this.cbDetailId == null && other.cbDetailId != null) || (this.cbDetailId != null && !this.cbDetailId.equals(other.cbDetailId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return getBudgetCode().getBudgetCode();
    }

    @XmlTransient
    public List<FmsCapitalBudgetTasks> getFmsCapitalBudgetTasksList() {
        if (fmsCapitalBudgetTasksList == null) {
            fmsCapitalBudgetTasksList = new ArrayList<>();
        }
        return fmsCapitalBudgetTasksList;
    }

    public void setFmsCapitalBudgetTasksList(List<FmsCapitalBudgetTasks> fmsCapitalBudgetTasksList) {
        this.fmsCapitalBudgetTasksList = fmsCapitalBudgetTasksList;
    }

    public void addCapitalBudgetTasksList(FmsCapitalBudgetTasks fmsCapitalBudgetTasks) {
        if (fmsCapitalBudgetTasks.getCBDetailFk() != this) {
            this.getFmsCapitalBudgetTasksList().add(fmsCapitalBudgetTasks);
            fmsCapitalBudgetTasks.setCBDetailFk(this);
        }
    }

}
