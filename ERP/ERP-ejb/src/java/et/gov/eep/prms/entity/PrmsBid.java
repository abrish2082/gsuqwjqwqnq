/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.entity;

import et.gov.eep.commonApplications.entity.PrmsLuDmArchive;
import et.gov.eep.commonApplications.entity.WfPrmsProcessed;
import et.gov.eep.fcms.entity.Vocher.FmsCashReceiptVoucher;
import et.gov.eep.fcms.entity.FmsLuCurrency;
import et.gov.eep.pms.entity.PmsCreateProjects;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name = "PRMS_BID")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PrmsBid.findAll", query = "SELECT p FROM PrmsBid p"),
    @NamedQuery(name = "PrmsBid.findAlls", query = "SELECT p FROM PrmsBid p WHERE p.status=3"),
    @NamedQuery(name = "PrmsBid.findById", query = "SELECT p FROM PrmsBid p WHERE p.id = :id"),
    @NamedQuery(name = "PrmsBid.findByBidNoAutoIncr", query = "SELECT p FROM PrmsBid p WHERE p.id = (SELECT MAX(p.id) FROM PrmsBid p)"),
    @NamedQuery(name = "PrmsBid.findByRefNo", query = "SELECT p FROM PrmsBid p WHERE p.refNo LIKE :refNo and p.preparedBy = :preparedBy"),
    @NamedQuery(name = "PrmsBid.findByRefNos", query = "SELECT p FROM PrmsBid p WHERE p.refNo = :refNo"),
    @NamedQuery(name = "PrmsBid.findByRefNoLike", query = "SELECT p FROM PrmsBid p WHERE p.refNo LIKE :refNos"),
    @NamedQuery(name = "PrmsBid.findByBidReq", query = "SELECT p FROM PrmsBid p WHERE p.status = 0"),
    @NamedQuery(name = "PrmsBid.findByBidType", query = "SELECT p FROM PrmsBid p WHERE p.bidType = :bidType"),
    @NamedQuery(name = "PrmsBid.findByPurchaseMethd", query = "SELECT p FROM PrmsBid p WHERE p.purchaseMethd = :purchaseMethd"),
    @NamedQuery(name = "PrmsBid.findByOpenBidPurchaseMethd", query = "SELECT p FROM PrmsBid p WHERE p.purchaseMethd LIKE 'Open Bid'"),
    @NamedQuery(name = "PrmsBid.findByBidDocPrice", query = "SELECT p FROM PrmsBid p WHERE p.bidDocPrice = :bidDocPrice"),
    @NamedQuery(name = "PrmsBid.findByBidBondPrice", query = "SELECT p FROM PrmsBid p WHERE p.bidBondPrice = :bidBondPrice"),
    @NamedQuery(name = "PrmsBid.findByPurchaseMethods", query = "SELECT p FROM PrmsBid p WHERE p.purchaseMethd = 'Restricted Bid' AND p.status=3"),
    @NamedQuery(name = "PrmsBid.findByBidDescr", query = "SELECT p FROM PrmsBid p WHERE p.bidDescr = :bidDescr"),
    @NamedQuery(name = "PrmsBid.findByBidCriteria", query = "SELECT p FROM PrmsBid p WHERE p.refNo = :bidCriteria"),
    @NamedQuery(name = "PrmsBid.findByBidCreteria", query = "SELECT p FROM PrmsBid p WHERE p.bidCriteriaId IS NOT NULL"),
    @NamedQuery(name = "PrmsBid.findByBidOpenDateTime", query = "SELECT p FROM PrmsBid p WHERE p.bidOpenDateTime = :bidOpenDateTime"),
    @NamedQuery(name = "PrmsBid.findByBidCloseDateTime", query = "SELECT p FROM PrmsBid p WHERE p.bidCloseDateTime = :bidCloseDateTime"),
    @NamedQuery(name = "PrmsBid.findByMinuteNo", query = "SELECT p FROM PrmsBid p WHERE p.minuteNo = :minuteNo"),
    @NamedQuery(name = "PrmsBid.findByIssueDate", query = "SELECT p FROM PrmsBid p WHERE p.issueDate = :issueDate"),
    @NamedQuery(name = "PrmsBid.findByApprovedBy", query = "SELECT p FROM PrmsBid p WHERE p.approvedBy = :approvedBy"),
    @NamedQuery(name = "PrmsBid.findByApprovedDate", query = "SELECT p FROM PrmsBid p WHERE p.approvedDate = :approvedDate"),
    @NamedQuery(name = "PrmsBid.findByPreparedBy", query = "SELECT p FROM PrmsBid p WHERE p.preparedBy = :preparedBy"),
    @NamedQuery(name = "PrmsBid.findByBidSecurityType", query = "SELECT p FROM PrmsBid p WHERE p.bidSecurityType = :bidSecurityType"),
    @NamedQuery(name = "PrmsBid.findByBidSecurityPrice", query = "SELECT p FROM PrmsBid p WHERE p.bidSecurityPrice = :bidSecurityPrice"),
    @NamedQuery(name = "PrmsBid.findByBidRegDate", query = "SELECT p FROM PrmsBid p WHERE p.bidRegDate = :bidRegDate")})

public class PrmsBid implements Serializable {

    @Size(max = 20)
    @Column(name = "AWARD_TYPE")
    private String awardType;
    @Column(name = "STATUS", length = 20)
    private Integer status;
    @Column(name = "FILE_UPLOAD_REFNO")
    private Integer fileUploadRefno;
    @OneToMany(mappedBy = "bidId", fetch = FetchType.LAZY)
    private List<PrmsPreminilaryEvaluation> prmsPreminilaryEvaluationCollection;
    @OneToMany(mappedBy = "bidId", fetch = FetchType.LAZY)
    private List<PrmsPostQualification> prmsPostQualificationCollection;
    @OneToMany(mappedBy = "bidId", fetch = FetchType.LAZY)
    private List<WfPrmsProcessed> wfPrmsProcessedLists;
    @OneToMany(mappedBy = "bidId", fetch = FetchType.LAZY)
    private List<FmsCashReceiptVoucher> fmsCashReceiptVoucherList;
    @OneToMany(mappedBy = "bidId", fetch = FetchType.LAZY)
    private List<FmsCashReceiptVoucher> fmsBidSaleList;
    @OneToMany(mappedBy = "bidId", fetch = FetchType.LAZY)
    private List<PrmsFinancialEvaluation> prmsFinancialEvaluationList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "bidId", fetch = FetchType.LAZY)
    private List<PrmsThechnicalEvaluation> prmsThechnicalEvaluationsList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "bidId", fetch = FetchType.LAZY)
    private List<PrmsBidSubmission> prmsBidSubmissions;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "bidId", fetch = FetchType.LAZY)
    private List<PrmsSpecification> prmsSpecifications;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "bidId", fetch = FetchType.LAZY)
    private List<PrmsSuppSpecification> prmsSuppSpecifications;
    @OneToMany(mappedBy = "bidId", fetch = FetchType.LAZY)
    private List<PrmsPaymentRequisition> prmsPaymentRequisitionList;

    @OneToMany(mappedBy = "bidId", cascade = CascadeType.ALL)
    private List<PrmsSupplierPerformanceInfo> prmsSupplierPerformanceInfoList;

    @OneToOne(mappedBy = "bidId")
    private PrmsPreminilaryEvaluation prmsPreminilaryEvaluationList;

    @OneToMany(mappedBy = "bidId")
    private List<PrmsBidAmend> prmsBidAmendList;
    @OneToMany(mappedBy = "bidId")
    private List<PrmsFinancialEvalResult> prmsFinancialEvalResultList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "bidId", fetch = FetchType.LAZY)
    private List<PrmsContractAmendment> prmsContractAmendmentList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "bidId", fetch = FetchType.LAZY)
    private List<PrmsContract> prmsContractList;
    @Size(max = 500)
    @Column(name = "BID_CRITERIA_ID")
    private String bidCriteriaId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "bidId", fetch = FetchType.LAZY)
    private List<PrmsBidOpeningCheckList> prmsBidOpeningCheckListList;
//       @OneToMany(cascade = CascadeType.ALL, mappedBy = "bidId", fetch = FetchType.LAZY)
//    private List<FmsCashReceiptVoucher> cashReceiptVouchers;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "bidId", fetch = FetchType.LAZY)
    private List<PrmsBidderRegistration> prmsBidderRegistrations;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "bidId", fetch = FetchType.LAZY)
    private List<PrmsAward> prmsAwardList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "bidId")
    private List<PrmsPurchaseOrder> prmsPurchaseOrderList;

    @JoinColumn(name = "PR_ID", referencedColumnName = "ID")
    @ManyToOne

    private static final long serialVersionUID = 1L;
    @Id
    @Size(min = 1, max = 20)
    @Column(name = "ID", nullable = false, length = 20)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "EEP_BID_SEQ")
    @SequenceGenerator(name = "EEP_BID_SEQ", sequenceName = "EEP_BID_SEQ", allocationSize = 1)
    private String id;
    @Size(max = 50)
    @Column(name = "REF_NO", length = 50)
    private String refNo;
    @Size(max = 20)
    @Column(name = "BID_TYPE", length = 20)
    private String bidType;
    @Size(max = 20)
    @Column(name = "PURCHASE_METHD", length = 20)
    private String purchaseMethd;
    @Size(max = 200)
    @Column(name = "DESCRIPTION", length = 200)
    private String desription;
    @Column(name = "BID_DOC_PRICE", precision = 126)
    private Double bidDocPrice;
    @Size(max = 20)
    @Column(name = "BID_BOND_PRICE", length = 20)
    private String bidBondPrice;
    @Size(max = 20)
    @Column(name = "BID_DESCR", length = 20)
    private String bidDescr;
    @Column(name = "BID_OPEN_DATE_TIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date bidOpenDateTime;
    @Column(name = "BID_CLOSE_DATE_TIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date bidCloseDateTime;
    @Column(name = "MINUTE_NO")
    @Temporal(TemporalType.DATE)
    private Date minuteNo;
    @Column(name = "ISSUE_DATE")
    @Temporal(TemporalType.DATE)
    private Date issueDate;
    @Size(max = 20)
    @Column(name = "APPROVED_BY", length = 20)
    private String approvedBy;
    @Column(name = "APPROVED_DATE")
    @Temporal(TemporalType.DATE)
    private Date approvedDate;
    @Column(name = "PREPARED_BY")
    private Integer preparedBy;
    @Size(max = 20)
    @Column(name = "BID_SECURITY_TYPE", length = 20)
    private String bidSecurityType;
    @Column(name = "BID_SECURITY_PRICE", precision = 126)
    private Double bidSecurityPrice;
    @Column(name = "BID_REG_DATE")
    @Temporal(TemporalType.DATE)
    private Date bidRegDate;
    @Column(name = "SITE_VISIT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date siteVisit;
    @Column(name = "PRE_BID_MEETING_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date preBidMeetingDate;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "bidId", fetch = FetchType.LAZY)
    private List<PrmsBidDetail> prmsBidDetailList;
    @JoinColumn(name = "CURRENCY_ID", referencedColumnName = "CURRENCY_ID")
    @ManyToOne
    private FmsLuCurrency currencyId;
    @JoinColumn(name = "PROJECT_ID", referencedColumnName = "PROJECT_ID")
    @ManyToOne
    private PmsCreateProjects projectId;
    @JoinColumn(name = "DOCUMENT_ID", referencedColumnName = "DOCUMENT_ID")
    @ManyToOne
    private PrmsLuDmArchive documentId;
    @Size(max = 20)
    @Column(name = "BID_CATEGORY", length = 20)
    private String bidCategory;

    @Size(max = 20)
    @Column(name = "BID_VALIDITY")
    private String bidValidity;
    @Size(max = 20)
    @Column(name = "PRE_BID_METING_PLACE")
    private String prebidMetingplace;
    @Size(max = 20)
    @Column(name = "POST_QUALIFICATION")
    private String postQualification;
    @Transient
    private String columnName;
    @Transient
    private String columnValue;

    /**
     *
     */
    public PrmsBid() {
    }

    /**
     *
     * @param id
     */
    public PrmsBid(String id) {
        this.id = id;
    }

    /**
     *
     * @return
     */
    public String getId() {
        return id;
    }

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

    /**
     *
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     *
     * @return
     */
    public String getRefNo() {
        return refNo;
    }

    /**
     *
     * @param refNo
     */
    public void setRefNo(String refNo) {
        this.refNo = refNo;
    }

    /**
     *
     * @return
     */
    public String getBidType() {
        return bidType;
    }

    /**
     *
     * @param bidType
     */
    public void setBidType(String bidType) {
        this.bidType = bidType;
    }

    /**
     *
     * @return
     */
    public String getPurchaseMethd() {
        return purchaseMethd;
    }

    /**
     *
     * @param purchaseMethd
     */
    public void setPurchaseMethd(String purchaseMethd) {
        this.purchaseMethd = purchaseMethd;
    }

    /**
     *
     * @return
     */
    public Double getBidDocPrice() {
        return bidDocPrice;
    }

    /**
     *
     * @param bidDocPrice
     */
    public void setBidDocPrice(Double bidDocPrice) {
        this.bidDocPrice = bidDocPrice;
    }

    /**
     *
     * @return
     */
    public String getBidBondPrice() {
        return bidBondPrice;
    }

    /**
     *
     * @param bidBondPrice
     */
    public void setBidBondPrice(String bidBondPrice) {
        this.bidBondPrice = bidBondPrice;
    }

    /**
     *
     * @return
     */
    public String getBidDescr() {
        return bidDescr;
    }

    /**
     *
     * @param bidDescr
     */
    public void setBidDescr(String bidDescr) {
        this.bidDescr = bidDescr;
    }

    /**
     *
     * @return
     */
    public Date getBidOpenDateTime() {
        return bidOpenDateTime;
    }

    /**
     *
     * @param bidOpenDateTime
     */
    public void setBidOpenDateTime(Date bidOpenDateTime) {
        this.bidOpenDateTime = bidOpenDateTime;
    }

    /**
     *
     * @return
     */
    public Date getBidCloseDateTime() {
        return bidCloseDateTime;
    }

    /**
     *
     * @param bidCloseDateTime
     */
    public void setBidCloseDateTime(Date bidCloseDateTime) {
        this.bidCloseDateTime = bidCloseDateTime;
    }

    /**
     *
     * @return
     */
    public Date getMinuteNo() {
        return minuteNo;
    }

    /**
     *
     * @param minuteNo
     */
    public void setMinuteNo(Date minuteNo) {
        this.minuteNo = minuteNo;
    }

    /**
     *
     * @return
     */
    public Date getIssueDate() {
        return issueDate;
    }

    /**
     *
     * @param issueDate
     */
    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    /**
     *
     * @return
     */
    public String getApprovedBy() {
        return approvedBy;
    }

    /**
     *
     * @param approvedBy
     */
    public void setApprovedBy(String approvedBy) {
        this.approvedBy = approvedBy;
    }

    /**
     *
     * @return
     */
    public Date getApprovedDate() {
        return approvedDate;
    }

    /**
     *
     * @param approvedDate
     */
    public void setApprovedDate(Date approvedDate) {
        this.approvedDate = approvedDate;
    }

    /**
     *
     * @return
     */
    public Integer getPreparedBy() {
        return preparedBy;
    }

    /**
     *
     * @param preparedBy
     */
    public void setPreparedBy(Integer preparedBy) {
        this.preparedBy = preparedBy;
    }

    /**
     *
     * @return
     */
    public String getBidSecurityType() {
        return bidSecurityType;
    }

    /**
     *
     * @param bidSecurityType
     */
    public void setBidSecurityType(String bidSecurityType) {
        this.bidSecurityType = bidSecurityType;
    }

    /**
     *
     * @return
     */
    public Double getBidSecurityPrice() {
        return bidSecurityPrice;
    }

    /**
     *
     * @param bidSecurityPrice
     */
    public void setBidSecurityPrice(Double bidSecurityPrice) {
        this.bidSecurityPrice = bidSecurityPrice;
    }

    /**
     *
     * @return
     */
    public Date getBidRegDate() {
        return bidRegDate;
    }

    /**
     *
     * @param bidRegDate
     */
    public void setBidRegDate(Date bidRegDate) {
        this.bidRegDate = bidRegDate;
    }

    public List<WfPrmsProcessed> getWfPrmsProcessedLists() {
        return wfPrmsProcessedLists;
    }

    public void setWfPrmsProcessedLists(List<WfPrmsProcessed> wfPrmsProcessedLists) {
        this.wfPrmsProcessedLists = wfPrmsProcessedLists;
    }

    public Date getSiteVisit() {
        return siteVisit;
    }

    public void setSiteVisit(Date siteVisit) {
        this.siteVisit = siteVisit;
    }

    public Date getPreBidMeetingDate() {
        return preBidMeetingDate;
    }

    public void setPreBidMeetingDate(Date preBidMeetingDate) {
        this.preBidMeetingDate = preBidMeetingDate;
    }

    @XmlTransient
    public List<PrmsBidDetail> getPrmsBidDetailList() {
        if (prmsBidDetailList == null) {
            prmsBidDetailList = new ArrayList<>();
        }
        return prmsBidDetailList;
    }

    /**
     *
     * @param prmsBidDetailList
     */
    public void setPrmsBidDetailList(List<PrmsBidDetail> prmsBidDetailList) {
        this.prmsBidDetailList = prmsBidDetailList;
    }

    public void add(PrmsBidDetail eepBidDetail) {
        if (eepBidDetail.getBidId() != this) {
            this.getPrmsBidDetailList().add(eepBidDetail);
            eepBidDetail.setBidId(this);
        }
    }

    public String getBidCategory() {
        return bidCategory;
    }

    public void setBidCategory(String bidCategory) {
        this.bidCategory = bidCategory;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PrmsBid)) {
            return false;
        }
        PrmsBid other = (PrmsBid) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return refNo;
    }

//    public PrmsPurchaseRequest getPrId() {
//        return prId;
//    }
//
//    public void setPrId(PrmsPurchaseRequest prId) {
//        this.prId = prId;
//    }
    /**
     *
     * @return
     */
    @XmlTransient
    public List<PrmsAward> getPrmsAwardList() {
        if (prmsAwardList == null) {
            prmsAwardList = new ArrayList<>();
        }
        return prmsAwardList;
    }

    /**
     *
     * @param prmsAwardList
     */
    public void setPrmsAwardList(List<PrmsAward> prmsAwardList) {
        this.prmsAwardList = prmsAwardList;
    }

    /**
     *
     * @return
     */
    @XmlTransient
    public List<PrmsPurchaseOrder> getPrmsPurchaseOrderList() {
        if (prmsPurchaseOrderList == null) {
            prmsPurchaseOrderList = new ArrayList<>();
        }
        return prmsPurchaseOrderList;
    }

    /**
     *
     * @param prmsPurchaseOrderList
     */
    public void setPrmsPurchaseOrderList(List<PrmsPurchaseOrder> prmsPurchaseOrderList) {
        this.prmsPurchaseOrderList = prmsPurchaseOrderList;
    }

    /**
     *
     * @return
     */
    @XmlTransient
    public List<PrmsBidOpeningCheckList> getPrmsBidOpeningCheckListList() {
        if (prmsBidOpeningCheckListList == null) {
            prmsBidOpeningCheckListList = new ArrayList<>();
        }
        return prmsBidOpeningCheckListList;
    }

    /**
     *
     * @param prmsBidOpeningCheckListList
     */
    public void setPrmsBidOpeningCheckListList(List<PrmsBidOpeningCheckList> prmsBidOpeningCheckListList) {
        this.prmsBidOpeningCheckListList = prmsBidOpeningCheckListList;
    }

    /**
     *
     * @return
     */
    public String getBidCriteriaId() {
        return bidCriteriaId;
    }

    /**
     *
     * @param bidCriteriaId
     */
    public void setBidCriteriaId(String bidCriteriaId) {
        this.bidCriteriaId = bidCriteriaId;
    }

    /**
     *
     * @return
     */
    @XmlTransient
    public List<PrmsContract> getPrmsContractList() {
        if (prmsContractList == null) {
            prmsContractList = new ArrayList<>();
        }
        return prmsContractList;
    }

    /**
     *
     * @param prmsContractList
     */
    public void setPrmsContractList(List<PrmsContract> prmsContractList) {
        this.prmsContractList = prmsContractList;
    }

    @XmlTransient
    public List<PrmsContractAmendment> getPrmsContractAmendmentList() {
        if (prmsContractAmendmentList == null) {
            prmsContractAmendmentList = new ArrayList<>();
        }
        return prmsContractAmendmentList;
    }

    public void setPrmsContractAmendmentList(List<PrmsContractAmendment> prmsContractAmendmentList) {
        this.prmsContractAmendmentList = prmsContractAmendmentList;
    }

    @XmlTransient
    public List<PrmsBidAmend> getPrmsBidAmendList() {
        return prmsBidAmendList;
    }

    public void setPrmsBidAmendList(List<PrmsBidAmend> prmsBidAmendList) {
        this.prmsBidAmendList = prmsBidAmendList;
    }

    public PrmsPreminilaryEvaluation getPrmsPreminilaryEvaluationList() {
        if (prmsPreminilaryEvaluationList == null) {
            prmsPreminilaryEvaluationList = new PrmsPreminilaryEvaluation();
        }
        return prmsPreminilaryEvaluationList;
    }

    public void setPrmsPreminilaryEvaluationList(PrmsPreminilaryEvaluation prmsPreminilaryEvaluationList) {
        this.prmsPreminilaryEvaluationList = prmsPreminilaryEvaluationList;
    }

//    @XmlTransient
//    public List<PrmsPreminilaryEvaluation> getPrmsPreminilaryEvaluationList() {
//        return prmsPreminilaryEvaluationList;
//    }
//
//    public void setPrmsPreminilaryEvaluationList(List<PrmsPreminilaryEvaluation> prmsPreminilaryEvaluationList) {
//        this.prmsPreminilaryEvaluationList = prmsPreminilaryEvaluationList;
//    }
    @XmlTransient
    public List<PrmsBidSubmission> getPrmsBidSubmissions() {
        if (prmsBidSubmissions == null) {
            prmsBidSubmissions = new ArrayList<>();
        }
        return prmsBidSubmissions;
    }

    public void setPrmsBidSubmissions(List<PrmsBidSubmission> prmsBidSubmissions) {
        this.prmsBidSubmissions = prmsBidSubmissions;
    }

    @XmlTransient
    public List<PrmsPaymentRequisition> getPrmsPaymentRequisitionList() {
        if (prmsPaymentRequisitionList == null) {
            prmsPaymentRequisitionList = new ArrayList<>();
        }
        return prmsPaymentRequisitionList;
    }

    public void setPrmsPaymentRequisitionList(List<PrmsPaymentRequisition> prmsPaymentRequisitionList) {
        this.prmsPaymentRequisitionList = prmsPaymentRequisitionList;
    }

    @XmlTransient
    public List<PrmsSupplierPerformanceInfo> getPrmsSupplierPerformanceInfoList() {
        if (prmsSupplierPerformanceInfoList == null) {
            prmsSupplierPerformanceInfoList = new ArrayList<>();
        }
        return prmsSupplierPerformanceInfoList;
    }

    public void setPrmsSupplierPerformanceInfoList(List<PrmsSupplierPerformanceInfo> prmsSupplierPerformanceInfoList) {
        this.prmsSupplierPerformanceInfoList = prmsSupplierPerformanceInfoList;
    }

    @XmlTransient
    public List<PrmsThechnicalEvaluation> getPrmsThechnicalEvaluationsList() {
        return prmsThechnicalEvaluationsList;
    }

    public void setPrmsThechnicalEvaluationsList(List<PrmsThechnicalEvaluation> prmsThechnicalEvaluationsList) {
        this.prmsThechnicalEvaluationsList = prmsThechnicalEvaluationsList;
    }

    @XmlTransient
    public List<PrmsSuppSpecification> getPrmsSuppSpecifications() {
        return prmsSuppSpecifications;
    }

    public void setPrmsSuppSpecifications(List<PrmsSuppSpecification> prmsSuppSpecifications) {
        this.prmsSuppSpecifications = prmsSuppSpecifications;
    }

    @XmlTransient

    public List<PrmsSpecification> getPrmsSpecifications() {
        if (prmsSpecifications == null) {
            prmsSpecifications = new ArrayList<>();
        }
        return prmsSpecifications;
    }

    public void setPrmsSpecifications(List<PrmsSpecification> prmsSpecifications) {
        this.prmsSpecifications = prmsSpecifications;
    }

    public FmsLuCurrency getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(FmsLuCurrency currencyId) {
        this.currencyId = currencyId;
    }

    public String getBidValidity() {
        return bidValidity;
    }

    public void setBidValidity(String bidValidity) {
        this.bidValidity = bidValidity;
    }

    public String getPrebidMetingplace() {
        return prebidMetingplace;
    }

    public void setPrebidMetingplace(String prebidMetingplace) {
        this.prebidMetingplace = prebidMetingplace;
    }

    public String getPostQualification() {
        return postQualification;
    }

    public void setPostQualification(String postQualification) {
        this.postQualification = postQualification;
    }

    public String getDesription() {
        return desription;
    }

    public void setDesription(String desription) {
        this.desription = desription;
    }

    public PrmsLuDmArchive getDocumentId() {
        return documentId;
    }

    public void setDocumentId(PrmsLuDmArchive documentId) {
        this.documentId = documentId;
    }

//    @XmlTransient
//    public List<FmsBidSale> getFmsBidSaleList() {
//        return fmsBidSaleList;
//    }
//
//    public void setFmsBidSaleList(List<FmsBidSale> fmsBidSaleList) {
//        this.fmsBidSaleList = fmsBidSaleList;
//    }
    @XmlTransient

    public List<PrmsBidderRegistration> getPrmsBidderRegistrations() {
        return prmsBidderRegistrations;
    }

    public void setPrmsBidderRegistrations(List<PrmsBidderRegistration> prmsBidderRegistrations) {
        this.prmsBidderRegistrations = prmsBidderRegistrations;
    }

    @XmlTransient
    public List<FmsCashReceiptVoucher> getFmsBidSaleList() {
        return fmsBidSaleList;
    }

    public void setFmsBidSaleList(List<FmsCashReceiptVoucher> fmsBidSaleList) {
        this.fmsBidSaleList = fmsBidSaleList;
    }

    public PmsCreateProjects getProjectId() {
        return projectId;
    }

    public void setProjectId(PmsCreateProjects projectId) {
        this.projectId = projectId;
    }

    @XmlTransient

    public List<PrmsFinancialEvalResult> getPrmsFinancialEvalResultList() {
        return prmsFinancialEvalResultList;
    }

    public void setPrmsFinancialEvalResultList(List<PrmsFinancialEvalResult> prmsFinancialEvalResultList) {
        this.prmsFinancialEvalResultList = prmsFinancialEvalResultList;
    }

    @XmlTransient

    public List<PrmsFinancialEvaluation> getPrmsFinancialEvaluationList() {
        return prmsFinancialEvaluationList;
    }

    public void setPrmsFinancialEvaluationList(List<PrmsFinancialEvaluation> prmsFinancialEvaluationList) {
        this.prmsFinancialEvaluationList = prmsFinancialEvaluationList;
    }

    public Integer getFileUploadRefno() {
        return fileUploadRefno;
    }

    public void setFileUploadRefno(Integer fileUploadRefno) {
        this.fileUploadRefno = fileUploadRefno;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getAwardType() {
        return awardType;
    }

    public void setAwardType(String awardType) {
        this.awardType = awardType;
    }

    @XmlTransient
    public List<PrmsPreminilaryEvaluation> getPrmsPreminilaryEvaluationCollection() {
        return prmsPreminilaryEvaluationCollection;
    }

    public void setPrmsPreminilaryEvaluationCollection(List<PrmsPreminilaryEvaluation> prmsPreminilaryEvaluationCollection) {
        this.prmsPreminilaryEvaluationCollection = prmsPreminilaryEvaluationCollection;
    }

    @XmlTransient
    public List<PrmsPostQualification> getPrmsPostQualificationCollection() {
        return prmsPostQualificationCollection;
    }

    public void setPrmsPostQualificationCollection(List<PrmsPostQualification> prmsPostQualificationCollection) {
        this.prmsPostQualificationCollection = prmsPostQualificationCollection;
    }

    @XmlTransient
    public List<WfPrmsProcessed> getWfPrmsProcessedCollection() {
        return wfPrmsProcessedLists;
    }

    public void setWfPrmsProcessedCollection(List<WfPrmsProcessed> wfPrmsProcessedLists) {
        this.wfPrmsProcessedLists = wfPrmsProcessedLists;
    }

    @XmlTransient

    public List<FmsCashReceiptVoucher> getFmsCashReceiptVoucherList() {
        if (fmsCashReceiptVoucherList == null) {
            fmsCashReceiptVoucherList = new ArrayList<>();
        }
        return fmsCashReceiptVoucherList;
    }

    public void setFmsCashReceiptVoucherList(List<FmsCashReceiptVoucher> fmsCashReceiptVoucherList) {
        this.fmsCashReceiptVoucherList = fmsCashReceiptVoucherList;
    }

}
