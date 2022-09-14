/**
 * *****************************************************************************
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates and open the template
 * in the editor.
 * ***************************************************************************
 */
package et.gov.eep.commonApplications.entity;

import et.gov.eep.prms.entity.PrmsAward;
import et.gov.eep.prms.entity.PrmsBankClearance;
import et.gov.eep.prms.entity.PrmsBid;
import et.gov.eep.prms.entity.PrmsBidAmend;
import et.gov.eep.prms.entity.PrmsBidOpeningCheckList;
import et.gov.eep.prms.entity.PrmsBidSubmission;
import et.gov.eep.prms.entity.PrmsBidderRegistration;
import et.gov.eep.prms.entity.PrmsContract;
import et.gov.eep.prms.entity.PrmsContractAmendment;
import et.gov.eep.prms.entity.PrmsFinancialEvalResult;
import et.gov.eep.prms.entity.PrmsFinancialEvaluation;
import et.gov.eep.prms.entity.PrmsForeignExchange;
import et.gov.eep.prms.entity.PrmsInsuranceRequisition;
import et.gov.eep.prms.entity.PrmsLcRigistration;
import et.gov.eep.prms.entity.PrmsLcRigistrationAmend;
import et.gov.eep.prms.entity.PrmsMarketAssessment;
import et.gov.eep.prms.entity.PrmsPaymentRequisition;
import et.gov.eep.prms.entity.PrmsPostQualification;
import et.gov.eep.prms.entity.PrmsGoodsEntrance;
import et.gov.eep.prms.entity.PrmsClaimRecordingForm;
import et.gov.eep.prms.entity.PrmsPortEntry;
import et.gov.eep.prms.entity.PrmsPreminilaryEvaluation;
import et.gov.eep.prms.entity.PrmsProjectPlan;
import et.gov.eep.prms.entity.PrmsPurchaseOrder;
import et.gov.eep.prms.entity.PrmsPurchasePlan;
import et.gov.eep.prms.entity.PrmsPurchaseRequest;
import et.gov.eep.prms.entity.PrmsQuotation;
import et.gov.eep.prms.entity.PrmsServiceProvider;
import et.gov.eep.prms.entity.PrmsSuppSpecification;
import et.gov.eep.prms.entity.PrmsSupplierPerformanceInfo;
import et.gov.eep.prms.entity.PrmsThechnicalEvaluation;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author mora1
 */
@Entity
@Table(name = "WF_PRMS_PROCESSED")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "WfPrmsProcessed.findAll", query = "SELECT w FROM WfPrmsProcessed w"),
    @NamedQuery(name = "WfPrmsProcessed.findByProcessedId", query = "SELECT w FROM WfPrmsProcessed w WHERE w.processedId = :processedId"),
    @NamedQuery(name = "WfPrmsProcessed.findByRequestId", query = "SELECT w FROM WfPrmsProcessed w WHERE w.requestId = :requestId"),
    @NamedQuery(name = "WfPrmsProcessed.findByProcessedBy", query = "SELECT w FROM WfPrmsProcessed w WHERE w.processedBy = :processedBy"),
    @NamedQuery(name = "WfPrmsProcessed.findByDecision", query = "SELECT w FROM WfPrmsProcessed w WHERE w.decision = :decision"),
    @NamedQuery(name = "WfPrmsProcessed.findByCommentGiven", query = "SELECT w FROM WfPrmsProcessed w WHERE w.commentGiven = :commentGiven"),
    @NamedQuery(name = "WfPrmsProcessed.findByRecordedBy", query = "SELECT w FROM WfPrmsProcessed w WHERE w.recordedBy = :recordedBy"),
    @NamedQuery(name = "WfPrmsProcessed.findByProcessedOn", query = "SELECT w FROM WfPrmsProcessed w WHERE w.processedOn = :processedOn"),
    @NamedQuery(name = "WfPrmsProcessed.findByTechnicalEvaluationId", query = "SELECT w FROM WfPrmsProcessed w WHERE w.technicalEvaluationId = :technicalEvaluationId"),
    @NamedQuery(name = "WfPrmsProcessed.findByPurchaseReqId", query = "SELECT w FROM WfPrmsProcessed w WHERE w.purchaseReqId.id = :purchaseReqId")})
public class WfPrmsProcessed implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "WF_PRMS_PROCESSED_SEQ")
    @SequenceGenerator(name = "WF_PRMS_PROCESSED_SEQ", sequenceName = "WF_PRMS_PROCESSED_SEQ", allocationSize = 1)
    @Column(name = "PROCESSED_ID", nullable = false)
    private Integer processedId;
    @Column(name = "REQUEST_ID")
    private Integer requestId;

    @Column(name = "PROCESSED_BY")
    private Integer processedBy;
    @Size(max = 20)
    @Column(name = "DECISION", length = 20)
    private String decision;
//      @Size(max = 50)
//    @Column(name = "PROCESSOR_NAME", length = 20)
//    private String processorName;
    @Size(max = 50)
    @Column(name = "COMMENT_GIVEN", length = 50)
    private String commentGiven;
    @Size(max = 20)
    @Column(name = "RECORDED_BY", length = 20)
    private String recordedBy;
    @Column(name = "PROCESSED_ON")
    @Temporal(TemporalType.TIMESTAMP)
    private Date processedOn;
    @Column(name = "TECHNICAL_EVALUATION_ID")
    private BigInteger technicalEvaluationId;
    @JoinColumn(name = "INSURANCE_REG_ID", referencedColumnName = "INSURANCE_REG_ID")
    @ManyToOne
    private PrmsInsuranceRequisition insuranceRegId;
    @JoinColumn(name = "CLEARANCE_ID", referencedColumnName = "CLEARANCE_ID")
    @ManyToOne
    private PrmsBankClearance clearanceId;
    @JoinColumn(name = "BID_ID", referencedColumnName = "ID")
    @ManyToOne
    private PrmsBid bidId;
    @JoinColumn(name = "POST_ID", referencedColumnName = "POST_ID")
    @ManyToOne
    private PrmsPostQualification postId;
    @JoinColumn(name = "MARKET_ID", referencedColumnName = "ID")
    @ManyToOne
    private PrmsMarketAssessment marketId;
    @JoinColumn(name = "PRELIMINARY_EVALUATION_ID", referencedColumnName = "ID")
    @ManyToOne
    private PrmsPreminilaryEvaluation preliminaryEvaluationId;
    @JoinColumn(name = "PURCHASE_ORDER_ID", referencedColumnName = "PO_ID")
    @ManyToOne
    private PrmsPurchaseOrder purchaseOrderId;
    @JoinColumn(name = "THECH_EVAL_ID", referencedColumnName = "EVALUATION_ID")
    @ManyToOne
    private PrmsThechnicalEvaluation thechEvalId;
    @JoinColumn(name = "PAYMENT_REQ_ID", referencedColumnName = "PAYMENT_REQ_ID")
    @ManyToOne
    private PrmsPaymentRequisition paymentReqId;
    @JoinColumn(name = "BID_AMED_ID", referencedColumnName = "ID")
    @ManyToOne
    private PrmsBidAmend bidAmedId;
    @JoinColumn(name = "CONTRACT_ID", referencedColumnName = "CONTRACT_ID")
    @ManyToOne
    private PrmsContract contractId;
    @JoinColumn(name = "CONTRACT_AMEND_ID", referencedColumnName = "CONTRACT_AMEND_ID")
    @ManyToOne
    private PrmsContractAmendment contractAmendId;
    @JoinColumn(name = "PURCHASE_REQ_ID", referencedColumnName = "ID")
    @ManyToOne
    private PrmsPurchaseRequest purchaseReqId;
    @JoinColumn(name = "PURCHASE_PLAN_ID", referencedColumnName = "ID")
    @ManyToOne
    private PrmsPurchasePlan purchasePlanId;
    @JoinColumn(name = "FINANCIAL_EVALUATION_ID", referencedColumnName = "ID")
    @ManyToOne
    private PrmsFinancialEvaluation financialEvaluationId;
    @JoinColumn(name = "FINANCIAL_EVALUATION_RESULT_ID", referencedColumnName = "ID")
    @ManyToOne
    private PrmsFinancialEvalResult financialEvaluationResultId;

    @JoinColumn(name = "PROJECT_PROCUREMENT_PLAN_ID", referencedColumnName = "ID")
    @ManyToOne
    private PrmsProjectPlan projectProcurementPlanId;

    @JoinColumn(name = "SUPP_SPEC_ID", referencedColumnName = "SUPP_SPEC_ID")
    @ManyToOne
    private PrmsSuppSpecification suppSpecId;

    @JoinColumn(name = "PORT_ID", referencedColumnName = "PORT_ID")
    @ManyToOne
    private PrmsPortEntry portId;
    @JoinColumn(name = "SUPP_PERFORMANCE_ID", referencedColumnName = "SUPP_INFO_ID")
    @ManyToOne
    private PrmsSupplierPerformanceInfo suppPerformanceId;
    @JoinColumn(name = "QUATATION_ID", referencedColumnName = "QUATATION_ID")
    @ManyToOne
    private PrmsQuotation quatationId;
    @JoinColumn(name = "SERVICE_PRO_ID", referencedColumnName = "SERVICE_PRO_ID")
    @ManyToOne
    private PrmsServiceProvider serviceProId;
    @JoinColumn(name = "BIDDER_REG_ID", referencedColumnName = "BIDDER_REG_ID")
    @ManyToOne
    private PrmsBidderRegistration bidderRegId;

    @JoinColumn(name = "AWARD_ID", referencedColumnName = "AWARD_ID")
    @ManyToOne
    private PrmsAward awardId;

    @JoinColumn(name = "FORIEN_ID", referencedColumnName = "ID")
    @ManyToOne
    private PrmsForeignExchange forienId;

    @JoinColumn(name = "BID_OPEN_CHECK_LIST_ID", referencedColumnName = "BID_OPEN_CHECK_LIST_ID")
    @ManyToOne
    private PrmsBidOpeningCheckList bidOpenCheckListId;

    @JoinColumn(name = "BID_SUB_ID", referencedColumnName = "BID_SUB_ID")
    @ManyToOne
    private PrmsBidSubmission bidSubId;
    @JoinColumn(name = "LC_ID", referencedColumnName = "LC_ID")
    @ManyToOne
    private PrmsLcRigistration lcId;
    @JoinColumn(name = "LC_AMENDMENT_ID", referencedColumnName = "ID")
    @ManyToOne
    private PrmsLcRigistrationAmend lcAmendmentId;
    @JoinColumn(name = "GOODS_ID", referencedColumnName = "ID")
    @ManyToOne
    private PrmsGoodsEntrance goodsId;
    @JoinColumn(name = "CLAIM_ID", referencedColumnName = "CLAIM_ID")
    @ManyToOne
    private PrmsClaimRecordingForm claimId;

    public PrmsClaimRecordingForm getClaimId() {
        return claimId;
    }

    public void setClaimId(PrmsClaimRecordingForm claimId) {
        this.claimId = claimId;
    }

    public PrmsGoodsEntrance getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(PrmsGoodsEntrance goodsId) {
        this.goodsId = goodsId;
    }

    public WfPrmsProcessed() {
    }

    public WfPrmsProcessed(Integer processedId) {
        this.processedId = processedId;
    }

    public Integer getProcessedId() {
        return processedId;
    }

    public void setProcessedId(Integer processedId) {
        this.processedId = processedId;
    }

    public Integer getRequestId() {
        return requestId;
    }

    public void setRequestId(Integer requestId) {
        this.requestId = requestId;
    }

    public Integer getProcessedBy() {
        return processedBy;
    }

    public void setProcessedBy(Integer processedBy) {
        this.processedBy = processedBy;
    }

    public String getDecision() {
        return decision;
    }

    public void setDecision(String decision) {
        this.decision = decision;
    }

//    public String getProcessorName() {
//        return processorName;
//    }
//
//    public void setProcessorName(String processorName) {
//        this.processorName = processorName;
//    }
    public BigInteger getTechnicalEvaluationId() {
        return technicalEvaluationId;
    }

    public void setTechnicalEvaluationId(BigInteger technicalEvaluationId) {
        this.technicalEvaluationId = technicalEvaluationId;
    }

    public String getCommentGiven() {
        return commentGiven;
    }

    public void setCommentGiven(String commentGiven) {
        this.commentGiven = commentGiven;
    }

    public String getRecordedBy() {
        return recordedBy;
    }

    public void setRecordedBy(String recordedBy) {
        this.recordedBy = recordedBy;
    }

    public Date getProcessedOn() {
        return processedOn;
    }

    public void setProcessedOn(Date processedOn) {
        this.processedOn = processedOn;
    }

//    public BigInteger getTechnicalEvaluationId() {
//        return technicalEvaluationId;
//    }
//
//    public void setTechnicalEvaluationId(BigInteger technicalEvaluationId) {
//        this.technicalEvaluationId = technicalEvaluationId;
//    }
    public PrmsInsuranceRequisition getInsuranceRegId() {
        return insuranceRegId;
    }

    public void setInsuranceRegId(PrmsInsuranceRequisition insuranceRegId) {
        this.insuranceRegId = insuranceRegId;
    }

    public PrmsBankClearance getClearanceId() {
        return clearanceId;
    }

    public void setClearanceId(PrmsBankClearance clearanceId) {
        this.clearanceId = clearanceId;
    }

    public PrmsBid getBidId() {
        return bidId;
    }

    public void setBidId(PrmsBid bidId) {
        this.bidId = bidId;
    }

    public PrmsPostQualification getPostId() {
        return postId;
    }

    public void setPostId(PrmsPostQualification postId) {
        this.postId = postId;
    }

    public PrmsMarketAssessment getMarketId() {
        return marketId;
    }

    public void setMarketId(PrmsMarketAssessment marketId) {
        this.marketId = marketId;
    }

    public PrmsPreminilaryEvaluation getPreliminaryEvaluationId() {
        return preliminaryEvaluationId;
    }

    public void setPreliminaryEvaluationId(PrmsPreminilaryEvaluation preliminaryEvaluationId) {
        this.preliminaryEvaluationId = preliminaryEvaluationId;
    }

    public PrmsPurchaseOrder getPurchaseOrderId() {
        return purchaseOrderId;
    }

    public void setPurchaseOrderId(PrmsPurchaseOrder purchaseOrderId) {
        this.purchaseOrderId = purchaseOrderId;
    }

    public PrmsThechnicalEvaluation getThechEvalId() {
        return thechEvalId;
    }

    public void setThechEvalId(PrmsThechnicalEvaluation thechEvalId) {
        this.thechEvalId = thechEvalId;
    }

    public PrmsPaymentRequisition getPaymentReqId() {
        return paymentReqId;
    }

    public void setPaymentReqId(PrmsPaymentRequisition paymentReqId) {
        this.paymentReqId = paymentReqId;
    }

    public PrmsBidAmend getBidAmedId() {
        return bidAmedId;
    }

    public void setBidAmedId(PrmsBidAmend bidAmedId) {
        this.bidAmedId = bidAmedId;
    }

    public PrmsContract getContractId() {
        return contractId;
    }

    public void setContractId(PrmsContract contractId) {
        this.contractId = contractId;
    }

    public PrmsContractAmendment getContractAmendId() {
        return contractAmendId;
    }

    public void setContractAmendId(PrmsContractAmendment contractAmendId) {
        this.contractAmendId = contractAmendId;
    }

    public PrmsPurchaseRequest getPurchaseReqId() {
        return purchaseReqId;
    }

    public void setPurchaseReqId(PrmsPurchaseRequest purchaseReqId) {
        this.purchaseReqId = purchaseReqId;
    }

    public PrmsPurchasePlan getPurchasePlanId() {
        return purchasePlanId;
    }

    public void setPurchasePlanId(PrmsPurchasePlan purchasePlanId) {
        this.purchasePlanId = purchasePlanId;
    }

    public PrmsFinancialEvaluation getFinancialEvaluationId() {
        return financialEvaluationId;
    }

    public void setFinancialEvaluationId(PrmsFinancialEvaluation financialEvaluationId) {
        this.financialEvaluationId = financialEvaluationId;
    }

    public PrmsFinancialEvalResult getFinancialEvaluationResultId() {
        return financialEvaluationResultId;
    }

    public void setFinancialEvaluationResultId(PrmsFinancialEvalResult financialEvaluationResultId) {
        this.financialEvaluationResultId = financialEvaluationResultId;
    }

    public PrmsProjectPlan getProjectProcurementPlanId() {
        return projectProcurementPlanId;
    }

    public void setProjectProcurementPlanId(PrmsProjectPlan projectProcurementPlanId) {
        this.projectProcurementPlanId = projectProcurementPlanId;
    }

    public PrmsSuppSpecification getSuppSpecId() {
        return suppSpecId;
    }

    public void setSuppSpecId(PrmsSuppSpecification suppSpecId) {
        this.suppSpecId = suppSpecId;
    }

    public PrmsSupplierPerformanceInfo getSuppPerformanceId() {
        return suppPerformanceId;
    }

    public void setSuppPerformanceId(PrmsSupplierPerformanceInfo suppPerformanceId) {
        this.suppPerformanceId = suppPerformanceId;
    }

    public PrmsQuotation getQuatationId() {
        return quatationId;
    }

    public void setQuatationId(PrmsQuotation quatationId) {
        this.quatationId = quatationId;
    }

    public PrmsForeignExchange getForienId() {
        return forienId;
    }

    public void setForienId(PrmsForeignExchange forienId) {
        this.forienId = forienId;
    }

    public PrmsLcRigistration getLcId() {
        return lcId;
    }

    public void setLcId(PrmsLcRigistration lcId) {
        this.lcId = lcId;
    }

    public PrmsLcRigistrationAmend getLcAmendmentId() {
        return lcAmendmentId;
    }

    public void setLcAmendmentId(PrmsLcRigistrationAmend lcAmendmentId) {
        this.lcAmendmentId = lcAmendmentId;
    }

    public PrmsServiceProvider getServiceProId() {
        return serviceProId;
    }

    public void setServiceProId(PrmsServiceProvider serviceProId) {
        this.serviceProId = serviceProId;
    }

    public PrmsBidderRegistration getBidderRegId() {
        return bidderRegId;
    }

    public void setBidderRegId(PrmsBidderRegistration bidderRegId) {
        this.bidderRegId = bidderRegId;
    }

    public PrmsAward getAwardId() {
        return awardId;
    }

    public void setAwardId(PrmsAward awardId) {
        this.awardId = awardId;
    }

    public PrmsBidOpeningCheckList getBidOpenCheckListId() {
        return bidOpenCheckListId;
    }

    public void setBidOpenCheckListId(PrmsBidOpeningCheckList bidOpenCheckListId) {
        this.bidOpenCheckListId = bidOpenCheckListId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (processedId != null ? processedId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof WfPrmsProcessed)) {
            return false;
        }
        WfPrmsProcessed other = (WfPrmsProcessed) object;

        if ((this.processedId == null && other.processedId != null)
                || (this.processedId != null
                && !this.processedId.equals(other.processedId))) {
            return false;
        }
        return true;
    }

    public PrmsBidSubmission getBidSubId() {
        return bidSubId;
    }

    public void setBidSubId(PrmsBidSubmission bidSubId) {
        this.bidSubId = bidSubId;
    }

    public PrmsPortEntry getPortId() {
        return portId;
    }

    public void setPortId(PrmsPortEntry portId) {
        this.portId = portId;
    }

    @Override
    public String toString() {
//        return processedId.toString();
        return "et.gov.eep.prms.entity.WfPrmsProcessed[ processedId=" + processedId + " ]";
    }
}
