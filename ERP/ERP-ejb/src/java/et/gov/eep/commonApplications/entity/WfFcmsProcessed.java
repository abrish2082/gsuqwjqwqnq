/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.commonApplications.entity;

import et.gov.eep.commonApplications.utility.date.StringDateManipulation;
import et.gov.eep.fcms.entity.pettyCash.FmsPettyCashReplenishment;
import et.gov.eep.fcms.entity.perDiem.FmsFieldAllowance;
import et.gov.eep.fcms.entity.perDiem.FmsFieldAllowanceForeign;
import et.gov.eep.fcms.entity.perDiem.FmsFieldAllowanceSettlement;
import et.gov.eep.fcms.entity.Vocher.FmsVoucher;
import et.gov.eep.fcms.entity.budget.FmsCapitalBudget1;
import et.gov.eep.fcms.entity.budget.FmsCbTransferDetail;
import et.gov.eep.fcms.entity.budget.FmsObTransferDetail;
import et.gov.eep.fcms.entity.budget.FmsOperatingBudget1;
import java.io.Serializable;
import java.math.BigInteger;
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
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author memube
 */
@Entity
@Table(name = "WF_FCMS_PROCESSED")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "WfFcmsProcessed.findAll", query = "SELECT w FROM WfFcmsProcessed w"),
    @NamedQuery(name = "WfFcmsProcessed.findByWfFcmsId", query = "SELECT w FROM WfFcmsProcessed w WHERE w.wfFcmsId = :wfFcmsId"),
    @NamedQuery(name = "WfFcmsProcessed.findByDecision", query = "SELECT w FROM WfFcmsProcessed w WHERE w.decision = :decision"),
    @NamedQuery(name = "WfFcmsProcessed.findByCommentGiven", query = "SELECT w FROM WfFcmsProcessed w WHERE w.commentGiven = :commentGiven"),
    @NamedQuery(name = "WfFcmsProcessed.findByProcessedBy", query = "SELECT w FROM WfFcmsProcessed w WHERE w.processedBy = :processedBy"),
    @NamedQuery(name = "WfFcmsProcessed.findByProcessedOn", query = "SELECT w FROM WfFcmsProcessed w WHERE w.processedOn = :processedOn")})
public class WfFcmsProcessed implements Serializable {

    private static final long serialVersionUID = 1L;
     @Max(value=50)  @Min(value=0)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "WF_FCMS_PROCESSED_SEQ")
    @SequenceGenerator(name = "WF_FCMS_PROCESSED_SEQ", sequenceName = "WF_FCMS_PROCESSED_SEQ", allocationSize = 1)
    @Basic(optional = false)
    @Column(name = "WF_FCMS_ID")
    private Integer wfFcmsId;

    @Column(name = "DECISION")
    private Integer decision;
    @Size(max = 255)
    @Column(name = "COMMENT_GIVEN")
    private String commentGiven;
    @Column(name = "PROCESSED_BY")
    private BigInteger processedBy;
    @Size(max = 20)
    @Column(name = "PROCESSED_ON")
    private String processedOn;
    @JoinColumn(name = "CAPITAL_BUDGET_ID", referencedColumnName = "CAPITAL_BUDGET_ID")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private FmsCapitalBudget1 capitalBudgetId;
    @JoinColumn(name = "PERDIEM_REQUEST_LOCAL_ID", referencedColumnName = "ID")
    @ManyToOne
    private FmsFieldAllowance perdiemRequestLocalId;
    @JoinColumn(name = "PERDIEM_REQUEST_FOREIGN_ID", referencedColumnName = "ID")
    @ManyToOne
    private FmsFieldAllowanceForeign perdiemRequestForeignId;
    @JoinColumn(name = "PERDIEM_LOCAL_SETTLEMENT_ID", referencedColumnName = "ID")
    @ManyToOne
    private FmsFieldAllowanceSettlement perdiemLocalSettlementId;
    @JoinColumn(name = "OPERATING_BUDGET_ID", referencedColumnName = "OPERATING_BUDGET_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private FmsOperatingBudget1 operatingBudgetId;
    @JoinColumn(name = "WF_FCMS_ID", referencedColumnName = "ID", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private FmsPettyCashReplenishment fmsPettyCashReplenishment;
    @JoinColumn(name = "FMS_PETTY_CASH_REPLENISH_ID", referencedColumnName = "ID")
    @ManyToOne
    private FmsPettyCashReplenishment fmsPettyCashReplenishId;
    @JoinColumn(name = "FMS_VOUCHER_ID", referencedColumnName = "VOUCHER_ID")
    @ManyToOne
    private FmsVoucher fmsVoucherId;
    @JoinColumn(name = "OB_TRANSFER_DETAIL_ID", referencedColumnName = "TRANSFER_DTL_ID")
    @ManyToOne
    private FmsObTransferDetail obTransferDtlId;
    @JoinColumn(name = "CB_TRANSFER_DETAIL_ID", referencedColumnName = "TRANSFER_DTL_ID")
    @ManyToOne
    private FmsCbTransferDetail cbTransferDtlId;

    public WfFcmsProcessed() {
    }

    public WfFcmsProcessed(Integer wfFcmsId) {
        this.wfFcmsId = wfFcmsId;
    }

    public Integer getWfFcmsId() {
        return wfFcmsId;
    }

    public void setWfFcmsId(Integer wfFcmsId) {
        this.wfFcmsId = wfFcmsId;
    }

    public Integer getDecision() {
        return decision;
    }

    public void setDecision(Integer decision) {
        this.decision = decision;
    }

    public String getCommentGiven() {
        return commentGiven;
    }

    public void setCommentGiven(String commentGiven) {
        this.commentGiven = commentGiven;
    }

    public BigInteger getProcessedBy() {
        return processedBy;
    }

    public void setProcessedBy(BigInteger processedBy) {
        this.processedBy = processedBy;
    }

    public String getProcessedOn() {
        return processedOn = StringDateManipulation.toDayInEc();
    }

    public void setProcessedOn(String processedOn) {
        this.processedOn = processedOn;
    }

    public FmsCapitalBudget1 getCapitalBudgetId() {
        return capitalBudgetId;
    }

    public void setCapitalBudgetId(FmsCapitalBudget1 capitalBudgetId) {
        this.capitalBudgetId = capitalBudgetId;
    }

    public FmsFieldAllowance getPerdiemRequestLocalId() {
        return perdiemRequestLocalId;
    }

    public void setPerdiemRequestLocalId(FmsFieldAllowance perdiemRequestLocalId) {
        this.perdiemRequestLocalId = perdiemRequestLocalId;
    }

    public FmsFieldAllowanceForeign getPerdiemRequestForeignId() {
        return perdiemRequestForeignId;
    }

    public void setPerdiemRequestForeignId(FmsFieldAllowanceForeign perdiemRequestForeignId) {
        this.perdiemRequestForeignId = perdiemRequestForeignId;
    }

    public FmsFieldAllowanceSettlement getPerdiemLocalSettlementId() {
        return perdiemLocalSettlementId;
    }

    public void setPerdiemLocalSettlementId(FmsFieldAllowanceSettlement perdiemLocalSettlementId) {
        this.perdiemLocalSettlementId = perdiemLocalSettlementId;
    }

    public FmsOperatingBudget1 getOperatingBudgetId() {
        return operatingBudgetId;
    }

    public void setOperatingBudgetId(FmsOperatingBudget1 operatingBudgetId) {
        this.operatingBudgetId = operatingBudgetId;
    }

    public FmsPettyCashReplenishment getFmsPettyCashReplenishment() {
        return fmsPettyCashReplenishment;
    }

    public void setFmsPettyCashReplenishment(FmsPettyCashReplenishment fmsPettyCashReplenishment) {
        this.fmsPettyCashReplenishment = fmsPettyCashReplenishment;
    }

    public FmsPettyCashReplenishment getFmsPettyCashReplenishId() {
        return fmsPettyCashReplenishId;
    }

    public void setFmsPettyCashReplenishId(FmsPettyCashReplenishment fmsPettyCashReplenishId) {
        this.fmsPettyCashReplenishId = fmsPettyCashReplenishId;
    }

    public FmsVoucher getFmsVoucherId() {
        return fmsVoucherId;
    }

    public void setFmsVoucherId(FmsVoucher fmsVoucherId) {
        this.fmsVoucherId = fmsVoucherId;
    }

    public FmsObTransferDetail getObTransferDtlId() {
        return obTransferDtlId;
    }

    public void setObTransferDtlId(FmsObTransferDetail obTransferDtlId) {
        this.obTransferDtlId = obTransferDtlId;
    }

    public FmsCbTransferDetail getCbTransferDtlId() {
        return cbTransferDtlId;
    }

    public void setCbTransferDtlId(FmsCbTransferDetail cbTransferDtlId) {
        this.cbTransferDtlId = cbTransferDtlId;
    }
    @Transient
    String changedDecision;

    public String getChangedDecision() {
        return changedDecision;
    }

    public void setChangedDecision(String changedDecision) {
        this.changedDecision = changedDecision;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (wfFcmsId != null ? wfFcmsId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof WfFcmsProcessed)) {
            return false;
        }
        WfFcmsProcessed other = (WfFcmsProcessed) object;
        if ((this.wfFcmsId == null && other.wfFcmsId != null) || (this.wfFcmsId != null && !this.wfFcmsId.equals(other.wfFcmsId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.eep.fcms.entity.workFlow.WfFcmsProcessed[ wfFcmsId=" + wfFcmsId + " ]";
    }

}
