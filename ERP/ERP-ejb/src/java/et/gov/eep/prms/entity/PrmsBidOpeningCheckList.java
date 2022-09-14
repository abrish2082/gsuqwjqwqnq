/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.entity;

import et.gov.eep.commonApplications.entity.PrmsLuDmArchive;
import et.gov.eep.commonApplications.entity.WfPrmsProcessed;
import et.gov.eep.hrms.entity.committee.HrCommittees;
import java.io.Serializable;
import java.math.BigDecimal;
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

/**
 *
 * @author user
 */
@Entity
@Table(name = "PRMS_BID_OPENING_CHECK_LIST")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PrmsBidOpeningCheckList.findAll", query = "SELECT p FROM PrmsBidOpeningCheckList p"),
    @NamedQuery(name = "PrmsBidOpeningCheckList.findByBidOpenCheckListId", query = "SELECT p FROM PrmsBidOpeningCheckList p WHERE p.bidOpenCheckListId = :bidOpenCheckListId ORDER BY P.bidOpenCheckListId ASC"),
    @NamedQuery(name = "PrmsBidOpeningCheckList.findByExpirationOfBid", query = "SELECT p FROM PrmsBidOpeningCheckList p WHERE p.expirationOfBid = :expirationOfBid"),
    @NamedQuery(name = "PrmsBidOpeningCheckList.findByPreparedBy", query = "SELECT p FROM PrmsBidOpeningCheckList p WHERE p.preparedBy = :preparedBy"),
    @NamedQuery(name = "PrmsBidOpeningCheckList.findAllByStatus", query = "SELECT p FROM PrmsBidOpeningCheckList p WHERE p.status=0"),
    @NamedQuery(name = "PrmsBidOpeningCheckList.findByApprovedBy", query = "SELECT p FROM PrmsBidOpeningCheckList p WHERE p.approvedBy = :approvedBy"),
    @NamedQuery(name = "PrmsBidOpeningCheckList.findByDateRigisted", query = "SELECT p FROM PrmsBidOpeningCheckList p WHERE p.dateRigisted = :dateRigisted"),
    @NamedQuery(name = "PrmsBidOpeningCheckList.SearchByBidCheckListNo", query = "SELECT p FROM PrmsBidOpeningCheckList p WHERE p.bidCheckListNo LIKE :bidCheckListNo  ORDER BY P.bidOpenCheckListId ASC"),
    @NamedQuery(name = "PrmsBidOpeningCheckList.findByMaxCheckListNum", query = "SELECT p FROM PrmsBidOpeningCheckList p WHERE p.bidOpenCheckListId = (SELECT Max(c.bidOpenCheckListId)  from PrmsBidOpeningCheckList c)"),
    @NamedQuery(name = "PrmsBidOpeningCheckList.findByMaxSIVNo", query = "SELECT Max(p.bidCheckListNo) from PrmsBidOpeningCheckList p"),
    // @NamedQuery(name = "PrmsBidOpeningCheckList.serachByCustomer", query = "SELECT p FROM PrmsBidOpeningCheckList p INNER JOIN bidDocId b on p.bidDocId = p.bidDocId WHERE b.refNo LIKE :refNo"),
    @NamedQuery(name = "PrmsBidOpeningCheckList.findByMaxCheckListNo", query = "SELECT p FROM PrmsBidOpeningCheckList p WHERE p.bidCheckListNo = (SELECT Max(c.bidCheckListNo)  from PrmsBidOpeningCheckList c)"),
    @NamedQuery(name = "PrmsBidOpeningCheckList.findByBidCheckListNo", query = "SELECT p FROM PrmsBidOpeningCheckList p WHERE p.bidCheckListNo = :bidCheckListNo")})
public class PrmsBidOpeningCheckList implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PRMS_BID_OPENING_LIST_SEQ")
    @SequenceGenerator(name = "PRMS_BID_OPENING_LIST_SEQ", sequenceName = "PRMS_BID_OPENING_LIST_SEQ", allocationSize = 1)
    @Column(name = "BID_OPEN_CHECK_LIST_ID")
    private BigDecimal bidOpenCheckListId;

    @Size(max = 20)
    @Column(name = "EXPIRATION_OF_BID")
    private String expirationOfBid;

    @Size(max = 50)
    @Column(name = "APPROVED_BY")
    private String approvedBy;
    @Column(name = "DATE_RIGISTED")
    @Temporal(TemporalType.DATE)
    private Date dateRigisted;
    @Size(max = 100)
    @Column(name = "BID_CHECK_LIST_NO")
    private String bidCheckListNo;

    // <editor-fold defaultstate="collapsed" desc="Declare Variable for Dynamic Searching">
    @Transient
    private String columnName;

    @Transient
    private String columnValue;
    // </editor-fold>

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "bidCheckListId", fetch = FetchType.LAZY)
    private List<PrmsBidOpeningChecklstDt> prmsBidOpeningChecklstDtList;
    @OneToMany(mappedBy = "bidOpenCheckListId", cascade = CascadeType.ALL)
    private List<WfPrmsProcessed> prmsWorkFlowProccedList;
    @JoinColumn(name = "BID_ID", referencedColumnName = "ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private PrmsBid bidId;

    @JoinColumn(name = "DOCUMENT_ID", referencedColumnName = "DOCUMENT_ID")
    @ManyToOne
    private PrmsLuDmArchive documentId;

    @Column(name = "PREPARED_BY")
    private Integer preparedBy;
    @Column(name = "STATUS")
    private Integer status;
    @Column(name = "CURRENT_STATUS")
    private Integer currentStatus;

//    @JoinColumn(name = "BID_SUM_ID", referencedColumnName = "BID_SUB_ID")
//    @ManyToOne(fetch = FetchType.LAZY)
//    private PrmsBidSubmission bidSumId;
    @JoinColumn(name = "COMMITEE_ID", referencedColumnName = "ID")
    @ManyToOne()
    private HrCommittees commiteeId;

    @Size(max = 255)
    @Column(name = "COMMITTEE_MEMBERS")
    private String committeMembers;
    @OneToMany(mappedBy = "checklistId", cascade = CascadeType.ALL)
    private List<PrmsChecklistFileUpload> prmsChecklistFileUploads;

    public PrmsBidOpeningCheckList() {
    }

    /**
     *
     * @param bidOpenCheckListId
     */
    public PrmsBidOpeningCheckList(BigDecimal bidOpenCheckListId) {
        this.bidOpenCheckListId = bidOpenCheckListId;
    }

    public String getCommitteMembers() {
        return committeMembers;
    }

    public void setCommitteMembers(String committeMembers) {
        this.committeMembers = committeMembers;
    }

    /**
     *
     * @return
     */
    public BigDecimal getBidOpenCheckListId() {
        return bidOpenCheckListId;
    }

    /**
     *
     * @param bidOpenCheckListId
     */
    public void setBidOpenCheckListId(BigDecimal bidOpenCheckListId) {
        this.bidOpenCheckListId = bidOpenCheckListId;
    }

    public String getExpirationOfBid() {
        return expirationOfBid;
    }

    /**
     *
     * @param expirationOfBid
     */
    public void setExpirationOfBid(String expirationOfBid) {
        this.expirationOfBid = expirationOfBid;
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

    public Date getDateRigisted() {
        return dateRigisted;
    }

    public void setDateRigisted(Date dateRigisted) {
        this.dateRigisted = dateRigisted;
    }

    public String getBidCheckListNo() {
        return bidCheckListNo;
    }

    /**
     *
     * @param bidCheckListNo
     */
    public void setBidCheckListNo(String bidCheckListNo) {
        this.bidCheckListNo = bidCheckListNo;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (bidOpenCheckListId != null ? bidOpenCheckListId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PrmsBidOpeningCheckList)) {
            return false;
        }
        PrmsBidOpeningCheckList other = (PrmsBidOpeningCheckList) object;
        if ((this.bidOpenCheckListId == null && other.bidOpenCheckListId != null) || (this.bidOpenCheckListId != null && !this.bidOpenCheckListId.equals(other.bidOpenCheckListId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.eep.prms.entity.PrmsBidOpeningCheckList[ bidOpenCheckListId=" + bidOpenCheckListId + " ]";
    }

    /**
     *
     * @return
     */
    @XmlTransient
    public List<PrmsBidOpeningChecklstDt> getPrmsBidOpeningChecklstDtList() {
        if (prmsBidOpeningChecklstDtList == null) {
            prmsBidOpeningChecklstDtList = new ArrayList<>();
        }
        return prmsBidOpeningChecklstDtList;
    }

    /**
     *
     * @param prmsBidOpeningChecklstDtList
     */
    public void setPrmsBidOpeningChecklstDtList(List<PrmsBidOpeningChecklstDt> prmsBidOpeningChecklstDtList) {
        this.prmsBidOpeningChecklstDtList = prmsBidOpeningChecklstDtList;
    }

    /**
     *
     * @param prmsBidOpeningChecklstDt
     */
//    public HrEmployees getEmployeeId() {
//        return employeeId;
//    }
//    public void setEmployeeId(HrEmployees employeeId) {
//        this.employeeId = employeeId;
//    }
    public PrmsBid getBidId() {
        return bidId;
    }

    /**
     *
     * @param bidId
     */
    public void setBidId(PrmsBid bidId) {
        this.bidId = bidId;
    }

    public HrCommittees getCommiteeId() {
        return commiteeId;
    }

    public void setCommiteeId(HrCommittees commiteeId) {
        this.commiteeId = commiteeId;
    }

////    public PrmsBidSubmission getBidSumId() {
////        return bidSumId;
////    }
////
////    public void setBidSumId(PrmsBidSubmission bidSumId) {
////        this.bidSumId = bidSumId;
////    }
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

    public List<PrmsChecklistFileUpload> getPrmsChecklistFileUploads() {
        if (prmsChecklistFileUploads == null) {
            prmsChecklistFileUploads = new ArrayList<>();
        }
        return prmsChecklistFileUploads;
    }

    public void setPrmsChecklistFileUploads(List<PrmsChecklistFileUpload> prmsChecklistFileUploads) {
        this.prmsChecklistFileUploads = prmsChecklistFileUploads;
    }

    public void addBidCheckListDetail(PrmsBidOpeningChecklstDt prmsBidOpeningChecklstDt) {
        if (prmsBidOpeningChecklstDt.getBidCheckListId() != this) {
            this.getPrmsBidOpeningChecklstDtList().add(prmsBidOpeningChecklstDt);
            prmsBidOpeningChecklstDt.setBidCheckListId(this);
        }

    }

    public PrmsLuDmArchive getDocumentId() {
        return documentId;
    }

    public void setDocumentId(PrmsLuDmArchive documentId) {
        this.documentId = documentId;
    }

}
