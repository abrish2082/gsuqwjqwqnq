package et.gov.eep.prms.entity;

import et.gov.eep.commonApplications.entity.PrmsLuDmArchive;
import et.gov.eep.commonApplications.entity.WfPrmsProcessed;
import et.gov.eep.fcms.entity.FmsLuCurrency;
import java.io.Serializable;
import java.math.BigInteger;
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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name = "PRMS_AWARD")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PrmsAward.findAll", query = "SELECT p FROM PrmsAward p"),
    @NamedQuery(name = "PrmsAward.findByAwardId", query = "SELECT p FROM PrmsAward p WHERE p.awardId = :awardId"),
    @NamedQuery(name = "PrmsAward.findAllByStatus", query = "SELECT p FROM PrmsAward p WHERE p.status=0"),
    @NamedQuery(name = "PrmsAward.SearchAwardId", query = "SELECT p FROM PrmsAward p WHERE p.awardNo LIKE :awardNo ORDER BY P.awardId ASC"),
    @NamedQuery(name = "PrmsAward.findByRegDate", query = "SELECT p FROM PrmsAward p WHERE p.regDate = :regDate"),
    @NamedQuery(name = "PrmsAward.findPreparedBy", query = "SELECT p FROM PrmsAward p WHERE p.preparedBy = :preparedBy"),
    @NamedQuery(name = "PrmsAward.findByRemark", query = "SELECT p FROM PrmsAward p WHERE p.remark = :remark"),
    @NamedQuery(name = "PrmsAward.findByAwardNo", query = "SELECT p FROM PrmsAward p WHERE p.awardNo = :awardNo"),
    @NamedQuery(name = "PrmsAward.findByMaxBidOfferNum", query = "SELECT p FROM PrmsAward p WHERE p.awardId = (SELECT Max(c.awardId)  from PrmsAward c)"),

    @NamedQuery(name = "PrmsAward.findByBidId", query = "SELECT p FROM PrmsAward p WHERE p.bidId = :bidId"),
    @NamedQuery(name = "PrmsAward.findByApprovedBy", query = "SELECT p FROM PrmsAward p WHERE p.approvedBy = :approvedBy")})
public class PrmsAward implements Serializable {

    @Column(name = "FEXFILEREFNUMBER")
    private BigInteger fexfilerefnumber;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PRMS_AWARD_SEQ")
    @SequenceGenerator(name = "PRMS_AWARD_SEQ", sequenceName = "PRMS_AWARD_SEQ", allocationSize = 1)
    @Size(min = 1, max = 100)
    @Column(name = "AWARD_ID")
    private String awardId;
    @Column(name = "REG_DATE")
    @Temporal(TemporalType.DATE)
    private Date regDate;
    @Column(name = "AWARD_DATE")
    @Temporal(TemporalType.DATE)
    private Date awardDate;

    @Size(max = 100)
    @Column(name = "AWARD_NO")
    private String awardNo;

    @Size(max = 1500)
    @Column(name = "REMARK")
    private String remark;
    @Column(name = "STATUS")
    private Integer status;
    @Column(name = "CURRENT_STATUS")
    private Integer currentStatus;
    @Column(name = "PREPARED_BY")
    private Integer preparedBy;
    @Size(max = 20)
    @Column(name = "APPROVED_BY")
    private String approvedBy;
    @Transient
    private String columnName;
    @Transient
    private String columnValue;
    @JoinColumn(name = "BID_ID", referencedColumnName = "ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private PrmsBid bidId;
    @JoinColumn(name = "SUPP_ID", referencedColumnName = "ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private PrmsSupplyProfile suppId;
    @JoinColumn(name = "CURRENCY_ID", referencedColumnName = "CURRENCY_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private FmsLuCurrency currencyId;
    @OneToMany(mappedBy = "awardId")
    private List<PrmsClaimRecordingForm> prmsClaimRecordingFormList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "awardId", fetch = FetchType.LAZY)
    private List<PrmsContractAmendment> prmsContractAmendmentList;
    @Size(max = 100)
    @Column(name = "LETTER_OF_REF_NO")
    private String letterOfRefNo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "awardId", fetch = FetchType.LAZY)
    private List<PrmsContract> prmsContractList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "awardId", fetch = FetchType.LAZY)
    private List<PrmsPurchaseOrder> prmsPurchaseOrderList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "awardId", fetch = FetchType.LAZY)
    private List<PrmsAwardDetail> prmsAwardDetailList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "awardId", fetch = FetchType.LAZY)
    private List<PrmsFailsupplerAward> prmsFailsupplerAwardsList;
    @OneToMany(mappedBy = "awardId", cascade = CascadeType.ALL)
    private List<WfPrmsProcessed> prmsWorkFlowProccedList;
    @OneToMany(mappedBy = "awardId", cascade = CascadeType.ALL)
    private List<PrmsAwardFileUpload> prmsAwardFileUploads;

    @JoinColumn(name = "DOCUMENT_ID", referencedColumnName = "DOCUMENT_ID")
    @ManyToOne
    private PrmsLuDmArchive documentId;

    public PrmsAward() {
    }

    public PrmsAward(String awardId) {
        this.awardId = awardId;
    }

    public String getAwardId() {
        return awardId;
    }

    public void setAwardId(String awardId) {
        this.awardId = awardId;
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

    public FmsLuCurrency getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(FmsLuCurrency currencyId) {
        this.currencyId = currencyId;
    }

    public Date getRegDate() {
        return regDate;
    }

    public void setRegDate(Date regDate) {
        this.regDate = regDate;
    }

    public String getAwardNo() {
        return awardNo;
    }

    public void setAwardNo(String awardNo) {
        this.awardNo = awardNo;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(Integer currentStatus) {
        this.currentStatus = currentStatus;
    }

    public Integer getPreparedBy() {
        return preparedBy;
    }

    public void setPreparedBy(Integer preparedBy) {
        this.preparedBy = preparedBy;
    }

    public String getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(String approvedBy) {
        this.approvedBy = approvedBy;
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

    public String getLetterOfRefNo() {
        return letterOfRefNo;
    }

    public void setLetterOfRefNo(String letterOfRefNo) {
        this.letterOfRefNo = letterOfRefNo;
    }

    public Date getAwardDate() {
        return awardDate;
    }

    public void setAwardDate(Date awardDate) {
        this.awardDate = awardDate;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (awardId != null ? awardId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PrmsAward)) {
            return false;
        }
        PrmsAward other = (PrmsAward) object;
        if ((this.awardId == null && other.awardId != null) || (this.awardId != null && !this.awardId.equals(other.awardId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return awardNo;
    }

    public void addAwardDetialInfo(PrmsAwardDetail papmsAwardDetailobj) {
        if (papmsAwardDetailobj.getAwardId() != this) {
            this.getPrmsAwardDetailList().add(papmsAwardDetailobj);

            papmsAwardDetailobj.setAwardId(this);
        }

    }

    public void addFailedSupplierInf(PrmsFailsupplerAward prmsFailsupplerAward) {
        if (prmsFailsupplerAward.getAwardId() != this) {
            this.getPrmsFailsupplerAwardsList().add(prmsFailsupplerAward);
            prmsFailsupplerAward.setAwardId(this);
        }

    }

    @XmlTransient
    public List<PrmsAwardDetail> getPrmsAwardDetailList() {
        if (prmsAwardDetailList == null) {
            prmsAwardDetailList = new ArrayList<>();
        }
        return prmsAwardDetailList;
    }

    public void setPrmsAwardDetailList(List<PrmsAwardDetail> prmsAwardDetailList) {
        this.prmsAwardDetailList = prmsAwardDetailList;
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

    @XmlTransient
    public List<PrmsContract> getPrmsContractList() {
        if (prmsContractList == null) {
            prmsContractList = new ArrayList<>();
        }
        return prmsContractList;
    }

    public void setPrmsContractList(List<PrmsContract> prmsContractList) {
        this.prmsContractList = prmsContractList;
    }

    @XmlTransient
    public List<PrmsContractAmendment> getPrmsContractAmendmentList() {
        return prmsContractAmendmentList;
    }

    public void setPrmsContractAmendmentList(List<PrmsContractAmendment> prmsContractAmendmentList) {
        this.prmsContractAmendmentList = prmsContractAmendmentList;
    }

    @XmlTransient
    public List<PrmsClaimRecordingForm> getPrmsClaimRecordingFormList() {
        return prmsClaimRecordingFormList;
    }

    public void setPrmsClaimRecordingFormList(List<PrmsClaimRecordingForm> prmsClaimRecordingFormList) {
        this.prmsClaimRecordingFormList = prmsClaimRecordingFormList;
    }

    @XmlTransient

    public List<PrmsFailsupplerAward> getPrmsFailsupplerAwardsList() {
        if (prmsFailsupplerAwardsList == null) {
            prmsFailsupplerAwardsList = new ArrayList<>();
        }
        return prmsFailsupplerAwardsList;
    }

    public void setPrmsFailsupplerAwardsList(List<PrmsFailsupplerAward> prmsFailsupplerAwardsList) {
        this.prmsFailsupplerAwardsList = prmsFailsupplerAwardsList;
    }

    public BigInteger getFexfilerefnumber() {
        return fexfilerefnumber;
    }

    public void setFexfilerefnumber(BigInteger fexfilerefnumber) {
        this.fexfilerefnumber = fexfilerefnumber;
    }

    public List<WfPrmsProcessed> getPrmsWorkFlowProccedList() {
        if (prmsWorkFlowProccedList == null) {
            prmsWorkFlowProccedList = new ArrayList<>();
        }
        return prmsWorkFlowProccedList;
    }

    public void setPrmsWorkFlowProccedList(List<WfPrmsProcessed> prmsWorkFlowProccedList) {
        this.prmsWorkFlowProccedList = prmsWorkFlowProccedList;
    }

    @XmlTransient

    public List<PrmsAwardFileUpload> getPrmsAwardFileUploads() {
        if (prmsAwardFileUploads == null) {
            prmsAwardFileUploads = new ArrayList<>();
        }
        return prmsAwardFileUploads;
    }

    public void setPrmsAwardFileUploads(List<PrmsAwardFileUpload> prmsAwardFileUploads) {
        this.prmsAwardFileUploads = prmsAwardFileUploads;
    }

    public void add(PrmsAwardFileUpload prmsSuppSpecifiicationFileUpload) {

        if (prmsSuppSpecifiicationFileUpload.getAwardId() != this) {
            this.getPrmsAwardFileUploads().add(prmsSuppSpecifiicationFileUpload);
            prmsSuppSpecifiicationFileUpload.setAwardId(this);

        }
    }

    public PrmsLuDmArchive getDocumentId() {
        return documentId;
    }

    public void setDocumentId(PrmsLuDmArchive documentId) {
        this.documentId = documentId;
    }

}
