/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.entity;

import et.gov.eep.commonApplications.entity.PrmsLuDmArchive;
import et.gov.eep.commonApplications.entity.WfPrmsProcessed;
import et.gov.eep.fcms.entity.FmsLuCurrency;
import et.gov.eep.pms.entity.PmsCreateProjects;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
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
@Table(name = "PRMS_BID_AMEND")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PrmsBidAmend.findAll", query = "SELECT p FROM PrmsBidAmend p"),
    @NamedQuery(name = "PrmsBidAmend.findById", query = "SELECT p FROM PrmsBidAmend p WHERE p.id = :id"),
    @NamedQuery(name = "PrmsBidAmend.findByGeneratedAmdNm", query = "SELECT p FROM PrmsBidAmend p WHERE p.id = (SELECT Max(p.id)  from PrmsBidAmend p)"),
    @NamedQuery(name = "PrmsBidAmend.findBySeqAmend", query = "SELECT p FROM PrmsBidAmend p WHERE p.amendNo LIKE :amendNo"),
    @NamedQuery(name = "PrmsBidAmend.findBySeqClar", query = "SELECT p FROM PrmsBidAmend p WHERE p.clarificationNo LIKE :clarificationNo"),
    @NamedQuery(name = "PrmsBidAmend.findByRefNo", query = "SELECT p FROM PrmsBidAmend p WHERE p.refNo LIKE :refNo AND p.preparedBy=:preparedBy"),
    @NamedQuery(name = "PrmsBidAmend.findByReq", query = "SELECT p FROM PrmsBidAmend p WHERE p.status = 0"),
    @NamedQuery(name = "PrmsBidAmend.findByBidType", query = "SELECT p FROM PrmsBidAmend p WHERE p.bidType = :bidType"),
    @NamedQuery(name = "PrmsBidAmend.findByBidCatId", query = "SELECT p FROM PrmsBidAmend p WHERE p.bidCatId = :bidCatId"),
    @NamedQuery(name = "PrmsBidAmend.findByPurchaseMethd", query = "SELECT p FROM PrmsBidAmend p WHERE p.purchaseMethd = :purchaseMethd"),
    @NamedQuery(name = "PrmsBidAmend.findByBidNoAndAmndNo", query = "SELECT p FROM PrmsBidAmend p WHERE P.amendNo LIKE :amend AND p.preparedBy=:preparedBy"),
    @NamedQuery(name = "PrmsBidAmend.findByBidDocPrice", query = "SELECT p FROM PrmsBidAmend p WHERE p.bidDocPrice = :bidDocPrice"),
    @NamedQuery(name = "PrmsBidAmend.findByBidBondPrice", query = "SELECT p FROM PrmsBidAmend p WHERE p.bidBondPrice = :bidBondPrice"),
    @NamedQuery(name = "PrmsBidAmend.findByBidDescr", query = "SELECT p FROM PrmsBidAmend p WHERE p.bidDescr = :bidDescr"),
    @NamedQuery(name = "PrmsBidAmend.findByBidOpenDateTime", query = "SELECT p FROM PrmsBidAmend p WHERE p.bidOpenDateTime = :bidOpenDateTime"),
    @NamedQuery(name = "PrmsBidAmend.findByBidCloseDateTime", query = "SELECT p FROM PrmsBidAmend p WHERE p.bidCloseDateTime = :bidCloseDateTime"),
    @NamedQuery(name = "PrmsBidAmend.findByMinuteNo", query = "SELECT p FROM PrmsBidAmend p WHERE p.minuteNo = :minuteNo"),
    @NamedQuery(name = "PrmsBidAmend.findByAmend", query = "SELECT p FROM PrmsBidAmend p WHERE p.amendNo = :amend"),
    @NamedQuery(name = "PrmsBidAmend.findByIssueDate", query = "SELECT p FROM PrmsBidAmend p WHERE p.issueDate = :issueDate"),
    @NamedQuery(name = "PrmsBidAmend.findByApprovedBy", query = "SELECT p FROM PrmsBidAmend p WHERE p.approvedBy = :approvedBy"),
    @NamedQuery(name = "PrmsBidAmend.findByApprovedDate", query = "SELECT p FROM PrmsBidAmend p WHERE p.approvedDate = :approvedDate"),
    @NamedQuery(name = "PrmsBidAmend.findByPreparedBy", query = "SELECT p FROM PrmsBidAmend p WHERE p.preparedBy = :preparedBy"),
    @NamedQuery(name = "PrmsBidAmend.findByBidSecurityType", query = "SELECT p FROM PrmsBidAmend p WHERE p.bidSecurityType = :bidSecurityType"),
    @NamedQuery(name = "PrmsBidAmend.findByBidSecurityPrice", query = "SELECT p FROM PrmsBidAmend p WHERE p.bidSecurityPrice = :bidSecurityPrice"),
    @NamedQuery(name = "PrmsBidAmend.findByBidRegDate", query = "SELECT p FROM PrmsBidAmend p WHERE p.bidRegDate = :bidRegDate"),
    @NamedQuery(name = "PrmsBidAmend.findByPreBidMtngOpDatTime", query = "SELECT p FROM PrmsBidAmend p WHERE p.preBidMtngOpDatTime = :preBidMtngOpDatTime"),
    @NamedQuery(name = "PrmsBidAmend.findByPreBidMtngCloDatTim", query = "SELECT p FROM PrmsBidAmend p WHERE p.preBidMtngCloDatTim = :preBidMtngCloDatTim"),
    @NamedQuery(name = "PrmsBidAmend.findByBidCriteriaId", query = "SELECT p FROM PrmsBidAmend p WHERE p.bidCriteriaId = :bidCriteriaId")})
public class PrmsBidAmend implements Serializable {

    @Size(max = 20)
    @Column(name = "AWARD_TYPE")
    private String awardType;
    @Size(max = 40)
    @Column(name = "DESCRIPTION")
    private String description;
    @Column(name = "STATUS")
    private Integer status;
    @Column(name = "FILE_UPLOAD_REFNO")
    private Integer fileUploadRefno;
    @OneToMany(mappedBy = "bidAmedId", fetch = FetchType.LAZY)
    private List<WfPrmsProcessed> wfPrmsProcessedLists;

    @Column(name = "PERCENT")
    private Double percent;

    @JoinColumn(name = "BID_ID", referencedColumnName = "ID")
    @ManyToOne
    private PrmsBid bidId;
    @JoinColumn(name = "DOCUMENT_ID", referencedColumnName = "DOCUMENT_ID")
    @ManyToOne
    private PrmsLuDmArchive documentId;
    @JoinColumn(name = "CURRENCY_ID", referencedColumnName = "CURRENCY_ID")
    @ManyToOne
    private FmsLuCurrency currencyId;
    @JoinColumn(name = "PROJECT_ID", referencedColumnName = "PROJECT_ID")
    @ManyToOne
    private PmsCreateProjects projectId;
    @Size(max = 20)
    @Column(name = "AMEND_NUM_REF", length = 20)
    private String amendNumRef;
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PRMS_BID_AMEND_SEQ")
    @SequenceGenerator(name = "PRMS_BID_AMEND_SEQ", sequenceName = "PRMS_BID_AMEND_SEQ", allocationSize = 1)
    private String id;
    @Size(max = 20)
    @Column(name = "REF_NO")
    private String refNo;
    @Size(max = 20)
    @Column(name = "AMEND_NO")
    private String amendNo;
    @Size(max = 20)
    @Column(name = "BID_TYPE")
    private String bidType;
    @Size(max = 20)
    @Column(name = "BID_CAT_ID")
    private String bidCatId;
    @Size(max = 20)
    @Column(name = "PURCHASE_METHD")
    private String purchaseMethd;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "BID_DOC_PRICE")
    private Double bidDocPrice;
    @Size(max = 20)
    @Column(name = "BID_BOND_PRICE")
    private String bidBondPrice;
    @Size(max = 20)
    @Column(name = "BID_DESCR")
    private String bidDescr;
    @Column(name = "BID_OPEN_DATE_TIME")
    @Temporal(TemporalType.DATE)
    private Date bidOpenDateTime;
    @Column(name = "BID_CLOSE_DATE_TIME")
    @Temporal(TemporalType.DATE)
    private Date bidCloseDateTime;
    @Column(name = "MINUTE_NO")
    @Temporal(TemporalType.DATE)
    private Date minuteNo;
    @Column(name = "SITE_VISIT_DATE")
    @Temporal(TemporalType.DATE)
    private Date siteVisitDate;
    @Column(name = "ISSUE_DATE")
    @Temporal(TemporalType.DATE)
    private Date issueDate;
    @Size(max = 20)
    @Column(name = "APPROVED_BY")
    private String approvedBy;
    @Column(name = "APPROVED_DATE")
    @Temporal(TemporalType.DATE)
    private Date approvedDate;
    @Column(name = "PREPARED_BY")
    private Integer preparedBy;
    @Size(max = 20)
    @Column(name = "BID_SECURITY_TYPE")
    private String bidSecurityType;
    @Column(name = "BID_SECURITY_PRICE")
    private Double bidSecurityPrice;
    @Column(name = "BID_REG_DATE")
    @Temporal(TemporalType.DATE)
    private Date bidRegDate;
    @Column(name = "PRE_BID_MTNG_OP_DAT_TIME")
    @Temporal(TemporalType.DATE)
    private Date preBidMtngOpDatTime;
    @Column(name = "PRE_BID_MTNG_CLO_DAT_TIM")
    @Temporal(TemporalType.DATE)
    private Date preBidMtngCloDatTim;
    @Size(max = 500)
    @Column(name = "BID_CRITERIA_ID")
    private String bidCriteriaId;
    //<editor-fold defaultstate="collapsed" desc="Declaring Variables for Dynamic Searching">
    @Transient
    private String columnName;
    @Transient
    private String columnValue;
    //</editor-fold>
    @Size(max = 20)
    @Column(name = "PRE_BID_METING_PLACE")
    private String prebidMetingplace;
    @Size(max = 20)
    @Column(name = "POST_QUALIFICATION")
    private String postQualification;
    @Size(max = 20)
    @Column(name = "BID_VALIDITY")
    private String bidValidity;

    @Size(max = 20)
    @Column(name = "CLARIFICATION_NO")
    private String clarificationNo;

    @Column(name = "CLARIFICATION_DATE")
    @Temporal(TemporalType.DATE)
    private Date clarificationDate;
    @Size(max = 200)
    @Column(name = "CLARIFICATION_REASON")
    private String ClarificationReason;
    @Size(max = 200)
    @Column(name = "AMENDMENT_REASON")
    private String amnedmentReason;
    @Size(max = 20)
    @Column(name = "TYPE")
    private String type;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "bidAmendId", fetch = FetchType.LAZY)
    private List<PrmsBidDetailAmend> prmsBidDetailAmendList = new ArrayList<>();

    public PrmsBidAmend() {
    }

    public PrmsBidAmend(String id) {
        this.id = id;
    }

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

    public void setId(String id) {
        this.id = id;
    }

    public String getRefNo() {
        return refNo;
    }

    public void setRefNo(String refNo) {
        this.refNo = refNo;
    }

    public String getBidType() {
        return bidType;
    }

    public void setBidType(String bidType) {
        this.bidType = bidType;
    }

    public String getBidCatId() {
        return bidCatId;
    }

    public void setBidCatId(String bidCatId) {
        this.bidCatId = bidCatId;
    }

    public String getPurchaseMethd() {
        return purchaseMethd;
    }

    public void setPurchaseMethd(String purchaseMethd) {
        this.purchaseMethd = purchaseMethd;
    }

    public Double getBidDocPrice() {
        return bidDocPrice;
    }

    public void setBidDocPrice(Double bidDocPrice) {
        this.bidDocPrice = bidDocPrice;
    }

    public String getBidBondPrice() {
        return bidBondPrice;
    }

    public void setBidBondPrice(String bidBondPrice) {
        this.bidBondPrice = bidBondPrice;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getBidDescr() {
        return bidDescr;
    }

    public void setBidDescr(String bidDescr) {
        this.bidDescr = bidDescr;
    }

    public Date getBidOpenDateTime() {
        return bidOpenDateTime;
    }

    public void setBidOpenDateTime(Date bidOpenDateTime) {
        this.bidOpenDateTime = bidOpenDateTime;
    }

    public Date getBidCloseDateTime() {
        return bidCloseDateTime;
    }

    public void setBidCloseDateTime(Date bidCloseDateTime) {
        this.bidCloseDateTime = bidCloseDateTime;
    }

    public Date getMinuteNo() {
        return minuteNo;
    }

    public void setMinuteNo(Date minuteNo) {
        this.minuteNo = minuteNo;
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    public String getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(String approvedBy) {
        this.approvedBy = approvedBy;
    }

    public Date getApprovedDate() {
        return approvedDate;
    }

    public void setApprovedDate(Date approvedDate) {
        this.approvedDate = approvedDate;
    }

    public Integer getPreparedBy() {
        return preparedBy;
    }

    public void setPreparedBy(Integer preparedBy) {
        this.preparedBy = preparedBy;
    }

    public String getBidSecurityType() {
        return bidSecurityType;
    }

    public void setBidSecurityType(String bidSecurityType) {
        this.bidSecurityType = bidSecurityType;
    }

    public Double getBidSecurityPrice() {
        return bidSecurityPrice;
    }

    public void setBidSecurityPrice(Double bidSecurityPrice) {
        this.bidSecurityPrice = bidSecurityPrice;
    }

    public Date getBidRegDate() {
        return bidRegDate;
    }

    public void setBidRegDate(Date bidRegDate) {
        this.bidRegDate = bidRegDate;
    }

    public String getAmendNo() {
        return amendNo;
    }

    public void setAmendNo(String amendNo) {
        this.amendNo = amendNo;
    }

    public Date getPreBidMtngOpDatTime() {
        return preBidMtngOpDatTime;
    }

    public void setPreBidMtngOpDatTime(Date preBidMtngOpDatTime) {
        this.preBidMtngOpDatTime = preBidMtngOpDatTime;
    }

    public Date getPreBidMtngCloDatTim() {
        return preBidMtngCloDatTim;
    }

    public void setPreBidMtngCloDatTim(Date preBidMtngCloDatTim) {
        this.preBidMtngCloDatTim = preBidMtngCloDatTim;
    }

    public String getBidCriteriaId() {
        return bidCriteriaId;
    }

    public void setBidCriteriaId(String bidCriteriaId) {
        this.bidCriteriaId = bidCriteriaId;
    }

    @XmlTransient
    public List<PrmsBidDetailAmend> getPrmsBidDetailAmendList() {
        return prmsBidDetailAmendList;
    }

    public void setPrmsBidDetailAmendList(List<PrmsBidDetailAmend> prmsBidDetailAmendList) {
        this.prmsBidDetailAmendList = prmsBidDetailAmendList;
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
        if (!(object instanceof PrmsBidAmend)) {
            return false;
        }
        PrmsBidAmend other = (PrmsBidAmend) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return amendNo;
    }

    public String getAmendNumRef() {
        return amendNumRef;
    }

    public void setAmendNumRef(String amendNumRef) {
        this.amendNumRef = amendNumRef;
    }

    public Date getSiteVisitDate() {
        return siteVisitDate;
    }

    public void setSiteVisitDate(Date siteVisitDate) {
        this.siteVisitDate = siteVisitDate;
    }

    public PrmsBid getBidId() {
        return bidId;
    }

    public void setBidId(PrmsBid bidId) {
        this.bidId = bidId;
    }

    public FmsLuCurrency getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(FmsLuCurrency currencyId) {
        this.currencyId = currencyId;
    }

    public PmsCreateProjects getProjectId() {
        return projectId;
    }

    public void setProjectId(PmsCreateProjects projectId) {
        this.projectId = projectId;
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

    public String getBidValidity() {
        return bidValidity;
    }

    public void setBidValidity(String bidValidity) {
        this.bidValidity = bidValidity;
    }

    public String getClarificationNo() {
        return clarificationNo;
    }

    public void setClarificationNo(String clarificationNo) {
        this.clarificationNo = clarificationNo;
    }

    public Date getClarificationDate() {
        return clarificationDate;
    }

    public void setClarificationDate(Date clarificationDate) {
        this.clarificationDate = clarificationDate;
    }

    public String getClarificationReason() {
        return ClarificationReason;
    }

    public void setClarificationReason(String ClarificationReason) {
        this.ClarificationReason = ClarificationReason;
    }

    public String getAmnedmentReason() {
        return amnedmentReason;
    }

    public void setAmnedmentReason(String amnedmentReason) {
        this.amnedmentReason = amnedmentReason;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getPercent() {
        return percent;
    }

    public void setPercent(Double percent) {
        this.percent = percent;
    }

    public Integer getFileUploadRefno() {
        return fileUploadRefno;
    }

    public void setFileUploadRefno(Integer fileUploadRefno) {
        this.fileUploadRefno = fileUploadRefno;
    }

    public String getAwardType() {
        return awardType;
    }

    public void setAwardType(String awardType) {
        this.awardType = awardType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @XmlTransient
    public List<WfPrmsProcessed> getWfPrmsProcessedCollection() {
        return wfPrmsProcessedLists;
    }

    public void setWfPrmsProcessedCollection(List<WfPrmsProcessed> wfPrmsProcessedLists) {
        this.wfPrmsProcessedLists = wfPrmsProcessedLists;
    }

    public PrmsLuDmArchive getDocumentId() {
        return documentId;
    }

    public void setDocumentId(PrmsLuDmArchive documentId) {
        this.documentId = documentId;
    }

}
