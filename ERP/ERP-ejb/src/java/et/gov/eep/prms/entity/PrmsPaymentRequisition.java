/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.entity;

import et.gov.eep.commonApplications.entity.WfPrmsProcessed;
import et.gov.eep.fcms.entity.bank.FmsBank;
import et.gov.eep.hrms.entity.organization.HrDepartments;
import et.gov.eep.mms.entity.MmsGrn;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author user
 */
@Entity
@Table(name = "PRMS_PAYMENT_REQUISITION")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PrmsPaymentRequisition.findAll", query = "SELECT p FROM PrmsPaymentRequisition p"),
    @NamedQuery(name = "PrmsPaymentRequisition.findByPaymentReqId", query = "SELECT p FROM PrmsPaymentRequisition p WHERE p.paymentReqId = :paymentReqId"),
    @NamedQuery(name = "PrmsPaymentRequisition.findByPaymmentReqId\"entMethod", query = "SELECT p FROM PrmsPaymentRequisition p WHERE p.paymentReason = :paymentReason"),
    @NamedQuery(name = "PrmsPaymentRequisition.findByReqPaymentNo", query = "SELECT p FROM PrmsPaymentRequisition p WHERE p.reqPaymentNo = :reqPaymentNo"),
    @NamedQuery(name = "PrmsPaymentRequisition.findByRequestForApproval", query = "SELECT p FROM PrmsPaymentRequisition p WHERE p.status=0"),
    @NamedQuery(name = "PrmsPaymentRequisition.findByPaymentType", query = "SELECT p FROM PrmsPaymentRequisition p WHERE p.paymentType = :paymentType"),
    @NamedQuery(name = "PrmsPaymentRequisition.searchByPaymentType", query = "SELECT p FROM PrmsPaymentRequisition p WHERE p.paymentType LIKE :paymentType"),
    @NamedQuery(name = "PrmsPaymentRequisition.findByMaxPaymentId", query = "SELECT p FROM PrmsPaymentRequisition p WHERE p.paymentReqId = (SELECT Max(c.paymentReqId)  from PrmsPaymentRequisition c)"),
    @NamedQuery(name = "PrmsPaymentRequisition.SearchName", query = "SELECT p FROM PrmsPaymentRequisition p WHERE p.reqPaymentNo LIKE :reqPaymentNo AND p.preparedBy = :preparedBy ORDER BY p.paymentReqId ASC"),
    @NamedQuery(name = "PrmsPaymentRequisition.findBypaymentRecievedBy", query = "SELECT p FROM PrmsPaymentRequisition p WHERE p.paymentRecievedBy = :paymentRecievedBy"),
    @NamedQuery(name = "PrmsPaymentRequisition.findByIssueStatus", query = "SELECT p FROM PrmsPaymentRequisition p WHERE p.issueStatus = :issueStatus"),
    @NamedQuery(name = "PrmsPaymentRequisition.findByPreparedBy", query = "SELECT p FROM PrmsPaymentRequisition p WHERE p.preparedBy = :preparedBy"),
    @NamedQuery(name = "PrmsPaymentRequisition.findByPreparedRemark", query = "SELECT p FROM PrmsPaymentRequisition p WHERE p.preparedRemark = :preparedRemark"),
    @NamedQuery(name = "PrmsPaymentRequisition.findByIsuedDate", query = "SELECT p FROM PrmsPaymentRequisition p WHERE p.isuedDate = :isuedDate"),
    @NamedQuery(name = "PrmsPaymentRequisition.findByPaymentMode", query = "SELECT p FROM PrmsPaymentRequisition p WHERE p.paymentMode = :paymentMode"),
    @NamedQuery(name = "PrmsPaymentRequisition.findByApprovedDate", query = "SELECT p FROM PrmsPaymentRequisition p WHERE p.approvedDate = :approvedDate"),
    @NamedQuery(name = "PrmsPaymentRequisition.findByCreditInvoiceNo", query = "SELECT p FROM PrmsPaymentRequisition p WHERE p.creditInvoiceNo = :creditInvoiceNo"),
    @NamedQuery(name = "PrmsPaymentRequisition.findByApproverRemark", query = "SELECT p FROM PrmsPaymentRequisition p WHERE p.approverRemark = :approverRemark"),
    @NamedQuery(name = "PrmsPaymentRequisition.findByApprovedBy", query = "SELECT p FROM PrmsPaymentRequisition p WHERE p.approvedBy = :approvedBy"),
    @NamedQuery(name = "PrmsPaymentRequisition.findByPreparedDate", query = "SELECT p FROM PrmsPaymentRequisition p WHERE p.preparedDate = :preparedDate"),
    @NamedQuery(name = "PrmsPaymentRequisition.findByTotalAmount", query = "SELECT p FROM PrmsPaymentRequisition p WHERE p.totalAmount = :totalAmount"),
    @NamedQuery(name = "PrmsPaymentRequisition.findByRequestedAmount", query = "SELECT p FROM PrmsPaymentRequisition p WHERE p.requestedAmount=:requestedAmount"),
    @NamedQuery(name = "PrmsPaymentRequisition.findByPaidAmount", query = "SELECT p FROM PrmsPaymentRequisition p WHERE p.paidAmount=:paidAmount"),
    @NamedQuery(name = "PrmsPaymentRequisition.findByRemainingAmount", query = "SELECT p FROM PrmsPaymentRequisition p WHERE p.remainingAmount=:remainingAmount"),
    @NamedQuery(name = "PrmsPaymentRequisition.findByStatus", query = "SELECT p FROM PrmsPaymentRequisition p WHERE p.status=:status"),
    @NamedQuery(name = "PrmsPaymentRequisition.findByDepId", query = "SELECT p FROM PrmsPaymentRequisition p WHERE p.depId = :depId"),
    @NamedQuery(name = "PrmsPaymentRequisition.findByPoId", query = "SELECT p FROM PrmsPaymentRequisition p WHERE p.poId = :poId"),
    @NamedQuery(name = "PrmsPaymentRequisition.findBySupportiveDocument", query = "SELECT p FROM PrmsPaymentRequisition p WHERE p.supportiveDocument = :supportiveDocument"),
    @NamedQuery(name = "PrmsPaymentRequisition.findByPaymentIssuedBy", query = "SELECT p FROM PrmsPaymentRequisition p WHERE p.paymentIssuedBy = :paymentIssuedBy"),
    @NamedQuery(name = "PrmsPaymentRequisition.findByContractId", query = "SELECT p FROM PrmsPaymentRequisition p WHERE p.contractId = :contractId"),
    @NamedQuery(name = "PrmsPaymentRequisition.findByCommerciaInvoiceNo", query = "SELECT p FROM PrmsPaymentRequisition p WHERE p.commerciaInvoiceNo = :commerciaInvoiceNo"),
    @NamedQuery(name = "PrmsPaymentRequisition.findByBugetYear", query = "SELECT p FROM PrmsPaymentRequisition p WHERE p.bugetYear = :bugetYear"),
    @NamedQuery(name = "PrmsPaymentRequisition.findByBudgetTitle", query = "SELECT p FROM PrmsPaymentRequisition p WHERE p.budgetTitle = :budgetTitle"),
    @NamedQuery(name = "PrmsPaymentRequisition.findByPurchaseType", query = "SELECT p FROM PrmsPaymentRequisition p WHERE p.purchaseType = :purchaseType"),
    @NamedQuery(name = "PrmsPaymentRequisition.findByAgreementType", query = "SELECT p FROM PrmsPaymentRequisition p WHERE p.agreementType = :agreementType")})
public class PrmsPaymentRequisition implements Serializable {

    @Column(name = "TOTAL_AMOUNT")
    private Double totalAmount;
    @Column(name = "STATUS")
    private Integer status;
    @OneToMany(mappedBy = "paymentReqId", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<WfPrmsProcessed> wfPrmsProcessedLists;
    private static final long serialVersionUID = 1L;

    @JoinColumn(name = "RQUEST_ID", referencedColumnName = "ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private PrmsPurchaseRequest rquestId;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "paymentReqId")
    private List<PrmsPurchaseOrder> prmsPurchaseOrderList;
    @Size(max = 100)
    @Column(name = "COMMERCIA_INVOICE_NO")
    private String commerciaInvoiceNo;
    @JoinColumn(name = "CONTRACT_ID", referencedColumnName = "CONTRACT_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private PrmsContract contractId;
    @Size(max = 100)
    @Column(name = "PAYMENT_ISSUED_BY")
    private String paymentIssuedBy;
    @JoinColumn(name = "GRN_ID", referencedColumnName = "GRN_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private MmsGrn grnId;
    @JoinColumn(name = "SUPP_ID", referencedColumnName = "ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private PrmsSupplyProfile suppId;

    @Size(max = 100)
    @Column(name = "PAYMENT_RECIEVED_BY")
    private String paymentRecievedBy;
    @Size(max = 100)
    @Column(name = "SUPPORTIVE_DOCUMENT")
    private String supportiveDocument;
    @Size(max = 100)
    @Column(name = "BUGET_YEAR")
    private String bugetYear;
    @Size(max = 100)
    @Column(name = "BUDGET_TITLE")
    private String budgetTitle;
    @JoinColumn(name = "BID_ID", referencedColumnName = "ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private PrmsBid bidId;
    @JoinColumn(name = "PO_ID", referencedColumnName = "PO_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private PrmsPurchaseOrder poId;
    @JoinColumn(name = "BANK_ID", referencedColumnName = "BANK_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private FmsBank bankId;
    @JoinColumn(name = "DEP_ID", referencedColumnName = "DEP_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private HrDepartments depId;
    @Column(name = "PREPARED_DATE")
    @Temporal(TemporalType.DATE)
    private Date preparedDate;
    @Column(name = "REQUESTED_AMOUNT")
    private Double requestedAmount;
    @Column(name = "PAID_AMOUNT")
    private Double paidAmount;
    @Column(name = "REMAINING_AMOUNT")
    private Double remainingAmount;

    @Id
    @Basic(optional = false)
    @NotNull
    @GeneratedValue(generator = "PRMS_PAYMENT_REQUEST_SEQ", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "PRMS_PAYMENT_REQUEST_SEQ", sequenceName = "PRMS_PAYMENT_REQUEST_SEQ", allocationSize = 1)
    @Column(name = "PAYMENT_REQ_ID", nullable = false)
    private Integer paymentReqId;
    @Size(max = 255)
    @Column(name = "PAYMENT_REASON", length = 255)
    private String paymentReason;
    @Size(max = 255)
    @Column(name = "REQ_PAYMENT_NO", length = 255)
    private String reqPaymentNo;
    @Size(max = 255)
    @Column(name = "PAYMENT_TYPE", length = 255)
    private String paymentType;

    @Size(max = 100)
    @Column(name = "ISSUE_STATUS", length = 100)
    private String issueStatus;

    @Column(name = "PREPARED_BY")
    private Integer preparedBy;
    @Size(max = 200)
    @Column(name = "PREPARED_REMARK", length = 200)
    private String preparedRemark;
    @Column(name = "ISUED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date isuedDate;
    @Size(max = 20)
    @Column(name = "PAYMENT_MODE", length = 20)
    private String paymentMode;
    @Column(name = "APPROVED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date approvedDate;

    @Size(max = 20)
    @Column(name = "CREDIT_INVOICE_NO", length = 20)
    private String creditInvoiceNo;
    @Size(max = 20)
    @Column(name = "APPROVER_REMARK", length = 20)
    private String approverRemark;
    @Size(max = 20)
    @Column(name = "APPROVED_BY", length = 20)
    private String approvedBy;

    @Size(max = 35)
    @Column(name = "PURCHASE_TYPE", length = 35)
    private String purchaseType;
    @Size(max = 35)
    @Column(name = "AGREEMENT_TYPE", length = 35)
    private String agreementType;
    // <editor-fold defaultstate="collapsed" desc="Declare Variable for Dynamic Searching">
    @Transient
    private String columnName;

    @Transient
    private String columnValue;
    // </editor-fold>

    @JoinColumn(name = "QUATATION_ID", referencedColumnName = "QUATATION_ID")
    @ManyToOne
    private PrmsQuotation quatationId;

    public PrmsPaymentRequisition() {
    }

    public PrmsPaymentRequisition(Integer paymentReqId) {
        this.paymentReqId = paymentReqId;
    }

    public Integer getPaymentReqId() {
        return paymentReqId;
    }

    public void setPaymentReqId(Integer paymentReqId) {
        this.paymentReqId = paymentReqId;
    }

    public String getPaymentReason() {
        return paymentReason;
    }

    public void setPaymentReason(String paymentReason) {
        this.paymentReason = paymentReason;
    }

    public String getReqPaymentNo() {
        return reqPaymentNo;
    }

    public void setReqPaymentNo(String reqPaymentNo) {
        this.reqPaymentNo = reqPaymentNo;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getPaymentRecievedBy() {
        return paymentRecievedBy;
    }

    public void setPaymentRecievedBy(String paymentRecievedBy) {
        this.paymentRecievedBy = paymentRecievedBy;
    }

    public String getIssueStatus() {
        return issueStatus;
    }

    public void setIssueStatus(String issueStatus) {
        this.issueStatus = issueStatus;
    }

    public Integer getPreparedBy() {
        return preparedBy;
    }

    public void setPreparedBy(Integer preparedBy) {
        this.preparedBy = preparedBy;
    }

    public String getPreparedRemark() {
        return preparedRemark;
    }

    public void setPreparedRemark(String preparedRemark) {
        this.preparedRemark = preparedRemark;
    }

    public Date getIsuedDate() {
        return isuedDate;
    }

    public void setIsuedDate(Date isuedDate) {
        this.isuedDate = isuedDate;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public Date getApprovedDate() {
        return approvedDate;
    }

    public void setApprovedDate(Date approvedDate) {
        this.approvedDate = approvedDate;
    }

    public String getCreditInvoiceNo() {
        return creditInvoiceNo;
    }

    public void setCreditInvoiceNo(String creditInvoiceNo) {
        this.creditInvoiceNo = creditInvoiceNo;
    }

    public String getApproverRemark() {
        return approverRemark;
    }

    public void setApproverRemark(String approverRemark) {
        this.approverRemark = approverRemark;
    }

    public String getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(String approvedBy) {
        this.approvedBy = approvedBy;
    }

    public Date getPreparedDate() {
        return preparedDate;
    }

    public void setPreparedDate(Date preparedDate) {
        this.preparedDate = preparedDate;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Double getRequestedAmount() {
        return requestedAmount;
    }

    public void setRequestedAmount(Double requestedAmount) {
        this.requestedAmount = requestedAmount;
    }

    public Double getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(Double paidAmount) {
        this.paidAmount = paidAmount;
    }

    public Double getRemainingAmount() {
        return remainingAmount;
    }

    public void setRemainingAmount(Double remainingAmount) {
        this.remainingAmount = remainingAmount;
    }

    public FmsBank getBankId() {
        return bankId;
    }

    public void setBankId(FmsBank bankId) {
        this.bankId = bankId;
    }

    public HrDepartments getDepId() {
        return depId;
    }

    public void setDepId(HrDepartments depId) {
        this.depId = depId;
    }

    public PrmsPurchaseOrder getPoId() {
        return poId;
    }

    public void setPoId(PrmsPurchaseOrder poId) {
        this.poId = poId;
    }

    public String getSupportiveDocument() {
        return supportiveDocument;
    }

    public void setSupportiveDocument(String supportiveDocument) {
        this.supportiveDocument = supportiveDocument;
    }

    public PrmsBid getBidId() {
        return bidId;
    }

    public void setBidId(PrmsBid bidId) {
        this.bidId = bidId;
    }

    public PrmsSupplyProfile getSuppId() {
        return suppId;
    }

    public void setSuppId(PrmsSupplyProfile suppId) {
        this.suppId = suppId;
    }

    public String getCommerciaInvoiceNo() {
        return commerciaInvoiceNo;
    }

    public void setCommerciaInvoiceNo(String commerciaInvoiceNo) {
        this.commerciaInvoiceNo = commerciaInvoiceNo;
    }

    public String getBugetYear() {
        return bugetYear;
    }

    public void setBugetYear(String bugetYear) {
        this.bugetYear = bugetYear;
    }

    public String getBudgetTitle() {
        return budgetTitle;
    }

    public void setBudgetTitle(String budgetTitle) {
        this.budgetTitle = budgetTitle;
    }

    public String getPurchaseType() {
        return purchaseType;
    }

    public void setPurchaseType(String purchaseType) {
        this.purchaseType = purchaseType;
    }

    public String getAgreementType() {
        return agreementType;
    }

    public void setAgreementType(String agreementType) {
        this.agreementType = agreementType;
    }

    // <editor-fold defaultstate="collapsed" desc="getter and setter declared Variables for Dynamic Searching">
    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnValue() {
        return columnValue;
    }

    public void setColumnValue(String columnValue) {
        this.columnValue = columnValue;
    }
    // </editor-fold>

    public MmsGrn getGrnId() {
        return grnId;
    }

    public void setGrnId(MmsGrn grnId) {
        this.grnId = grnId;
    }

    public String getPaymentIssuedBy() {
        return paymentIssuedBy;
    }

    public void setPaymentIssuedBy(String paymentIssuedBy) {
        this.paymentIssuedBy = paymentIssuedBy;
    }

    public PrmsContract getContractId() {
        return contractId;
    }

    public void setContractId(PrmsContract contractId) {
        this.contractId = contractId;
    }

    public PrmsQuotation getQuatationId() {
        return quatationId;
    }

    public void setQuatationId(PrmsQuotation quatationId) {
        this.quatationId = quatationId;
    }

    @XmlTransient

    public List<PrmsPurchaseOrder> getPrmsPurchaseOrderList() {
        if (prmsPurchaseOrderList == null) {
            prmsPurchaseOrderList = new ArrayList<>();
        }
        return prmsPurchaseOrderList;
    }

    public void setPrmsPurchaseOrderList(List<PrmsPurchaseOrder> prmsPurchaseOrderList) {
        this.prmsPurchaseOrderList = prmsPurchaseOrderList;
    }

    public PrmsPurchaseRequest getRquestId() {
        return rquestId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (paymentReqId != null ? paymentReqId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PrmsPaymentRequisition)) {
            return false;
        }
        PrmsPaymentRequisition other = (PrmsPaymentRequisition) object;
        if ((this.paymentReqId == null && other.paymentReqId != null) || (this.paymentReqId != null && !this.paymentReqId.equals(other.paymentReqId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {

        //return paymentReqId.toString();
        return "et.gov.eep.prms.entity.PrmsPaymentRequisition[ paymentReqId=" + paymentReqId + " ]";
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @XmlTransient
    public List<WfPrmsProcessed> getWfPrmsProcessedLists() {
        if (wfPrmsProcessedLists == null) {
            wfPrmsProcessedLists = new ArrayList<>();
        }
        return wfPrmsProcessedLists;
    }

    public void setWfPrmsProcessedLists(List<WfPrmsProcessed> wfPrmsProcessedLists) {
        this.wfPrmsProcessedLists = wfPrmsProcessedLists;
    }

}
