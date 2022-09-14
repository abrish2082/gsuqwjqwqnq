
package et.gov.eep.mms.entity;

import et.gov.eep.commonApplications.entity.MmsLuDmArchive;
import et.gov.eep.commonApplications.entity.WfMmsProcessed;
import et.gov.eep.hrms.entity.committee.HrCommittees;
import et.gov.eep.hrms.entity.organization.HrDepartments;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author w_station
 */
@Entity
@Table(name = "MMS_STOCK_ITEM_LOST")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MmsStockItemLost.findAll", query = "SELECT m FROM MmsStockItemLost m"),
    @NamedQuery(name = "MmsStockItemLost.findByLostItemId", query = "SELECT m FROM MmsStockItemLost m WHERE m.lostStockId = :lostStockId"),
    @NamedQuery(name = "MmsStockItemLost.findByAcceptedBy", query = "SELECT m FROM MmsStockItemLost m WHERE m.acceptedBy = :acceptedBy"),
    @NamedQuery(name = "MmsStockItemLost.findByAcceptedDate", query = "SELECT m FROM MmsStockItemLost m WHERE m.acceptedDate = :acceptedDate"),
    @NamedQuery(name = "MmsStockItemLost.findByApproverDate", query = "SELECT m FROM MmsStockItemLost m WHERE m.approverDate = :approverDate"),
    @NamedQuery(name = "MmsStockItemLost.findByAllParameters", query = "SELECT m FROM MmsStockItemLost m WHERE m.lostItemNo LIKE :lostItemNo"),
    @NamedQuery(name = "MmsStockItemLost.findByAllParametersAndLotPrep", query = "SELECT m FROM MmsStockItemLost m WHERE m.lostItemNo = :lostItemNo AND m.processedBy = :processedBy"),
    @NamedQuery(name = "MmsStockItemLost.findBylostItemIdMaximum", query = "SELECT m FROM MmsStockItemLost m WHERE m.lostStockId = (SELECT Max(c.lostStockId) FROM MmsStockItemLost c)"),
    @NamedQuery(name = "MmsStockItemLost.findByStockNoAndStoreId", query = "SELECT m FROM MmsStockItemLost m WHERE m.lostItemNo LIKE :lostItemNo AND m.storeId=:storeId"),
    @NamedQuery(name = "MmsStockItemLost.findStkListByWfStatus", query = "SELECT m FROM MmsStockItemLost m WHERE m.stockStatus=:stockStatus"),
    @NamedQuery(name = "MmsStockItemLost.findStkListForCheckerByWfStatus", query = "SELECT m FROM MmsStockItemLost m WHERE m.stockStatus=:prepared OR m.stockStatus=:approverReject"),
    @NamedQuery(name = "MmsStockItemLost.findAllByPreparerId", query = "SELECT m FROM MmsStockItemLost m WHERE m.processedBy =:processedBy"),
    @NamedQuery(name = "MmsStockItemLost.findByApproverName", query = "SELECT m FROM MmsStockItemLost m WHERE m.approverName = :approverName"),
    @NamedQuery(name = "MmsStockItemLost.findByEmployeeName", query = "SELECT m FROM MmsStockItemLost m WHERE m.employeeName = :employeeName"),
    @NamedQuery(name = "MmsStockItemLost.findByEvidenceAttached", query = "SELECT m FROM MmsStockItemLost m WHERE m.evidenceAttached = :evidenceAttached"),
    @NamedQuery(name = "MmsStockItemLost.findByReasonOfWritten", query = "SELECT m FROM MmsStockItemLost m WHERE m.reasonOfWritten = :reasonOfWritten"),
    @NamedQuery(name = "MmsStockItemLost.findByLostItemNo", query = "SELECT m FROM MmsStockItemLost m WHERE m.lostItemNo = :lostItemNo"),
    @NamedQuery(name = "MmsStockItemLost.findByRegionName", query = "SELECT m FROM MmsStockItemLost m WHERE m.regionName = :regionName"),
    @NamedQuery(name = "MmsStockItemLost.findByRemark", query = "SELECT m FROM MmsStockItemLost m WHERE m.remark = :remark"),
    @NamedQuery(name = "MmsStockItemLost.findByRegistrationDate", query = "SELECT m FROM MmsStockItemLost m WHERE m.registrationDate = :registrationDate"),
    @NamedQuery(name = "MmsStockItemLost.findByStatus", query = "SELECT m FROM MmsStockItemLost m WHERE m.status = :status")})
public class MmsStockItemLost implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQUENCE_STOCK_LOST")
    @SequenceGenerator(name = "SEQUENCE_STOCK_LOST", sequenceName = "SEQUENCE_STOCK_LOST", allocationSize = 1)
    @Column(name = "LOST_STOCK_ID", nullable = false, precision = 0, scale = -127)
    private BigDecimal lostStockId;
    @Size(max = 70)
    @Column(name = "ACCEPTED_BY", length = 70)
    private String acceptedBy;
    @Column(name = "ACCEPTED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date acceptedDate;
    @Column(name = "APPROVER_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date approverDate;
    @Size(max = 70)
    @Column(name = "APPROVER_NAME", length = 70)
    private String approverName;
    @Size(max = 70)
    @Column(name = "EMPLOYEE_NAME")
    private String employeeName;
    @Column(name = "STOCK_STATUS")
    private Integer stockStatus;
    @Size(max = 70)
    @Column(name = "EVIDENCE_ATTACHED", length = 70)
    private String evidenceAttached;
    @Size(max = 70)
    @Column(name = "REASON_OF_WRITTEN", length = 70)
    private String reasonOfWritten;
    @Size(max = 70)
    @Column(name = "LOST_ITEM_NO", length = 70)
    private String lostItemNo;
    @Size(max = 20)
    @Column(name = "REGION_NAME", length = 20)
    private String regionName;
    @Size(max = 100)
    @Column(name = "REMARK", length = 100)
    private String remark;
    @Column(name = "REGISTRATION_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date registrationDate;
    @Size(max = 70)
    @Column(name = "STATUS", length = 70)
    private String status;
    @Size(max = 30)
    @Column(name = "ACCEPTED2_DATE")
    private String accepted2Date;
    @Size(max = 30)
    @Column(name = "APPROVER2_DATE")
    private String approver2Date;
    @Size(max = 30)
    @Column(name = "REG_DATE")
    private String regDate;
    @Column(name = "PROCESSED_BY")
    private Integer processedBy;
    @JoinColumn(name = "REFERENCE_NUMBER",referencedColumnName = "DOCUMENT_ID")
    @ManyToOne()
    private MmsLuDmArchive referenceNumber;
    @Size(max = 50)
    @Column(name = "COMMENT_GIVEN")
    private String commentGiven;
    @Size(max = 50)
    @Column(name = "PROCESSED_ON")
    private String processedOn;
    @Size(max = 255)
    @Column(name = "COMMITTEE_MEMBERS")
    private String committeMembers;
    @JoinColumn(name = "COMMITTEE_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrCommittees committeeId;
    @OneToMany(mappedBy = "lostStockId", cascade = CascadeType.ALL)
    private List<WfMmsProcessed> StockLostList;
    @OneToMany(mappedBy = "lostId", cascade = CascadeType.ALL)
    private List<LostStockDmsUpload> lostStockDmsUploadList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "lostStockId")
    private List<MmsStockItemLostDtl> mmsStockItemLostDtlList;
    @JoinColumn(name = "DEPARTMENT", referencedColumnName = "DEP_ID")
    @ManyToOne
    private HrDepartments department;
    @JoinColumn(name = "STORE_ID", referencedColumnName = "STORE_ID")
    @ManyToOne
    private MmsStoreInformation storeId;
    
     @Transient
    private String columnName;
    @Transient
    private String columnValue;
    public MmsStockItemLost() {
    }

    public BigDecimal getLostStockId() {
        return lostStockId;
    }

    public void setLostStockId(BigDecimal lostStockId) {
        this.lostStockId = lostStockId;
    }

    public String getAcceptedBy() {
        return acceptedBy;
    }

    public void setAcceptedBy(String acceptedBy) {
        this.acceptedBy = acceptedBy;
    }

    public Date getAcceptedDate() {
        return acceptedDate;
    }

    public void setAcceptedDate(Date acceptedDate) {
        this.acceptedDate = acceptedDate;
    }

    public Date getApproverDate() {
        return approverDate;
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

    public void setApproverDate(Date approverDate) {
        this.approverDate = approverDate;
    }

    public String getApproverName() {
        return approverName;
    }

    public void setApproverName(String approverName) {
        this.approverName = approverName;
    }

    public Integer getProcessedBy() {
        return processedBy;
    }

    public void setProcessedBy(Integer processedBy) {
        this.processedBy = processedBy;
    }

    public MmsLuDmArchive getReferenceNumber() {
        return referenceNumber;
    }

    public void setReferenceNumber(MmsLuDmArchive referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    public String getCommentGiven() {
        return commentGiven;
    }

    public void setCommentGiven(String commentGiven) {
        this.commentGiven = commentGiven;
    }

    public String getProcessedOn() {
        return processedOn;
    }

    public void setProcessedOn(String processedOn) {
        this.processedOn = processedOn;
    }

    public Integer getStockStatus() {
        return stockStatus;
    }

    public void setStockStatus(Integer stockStatus) {
        this.stockStatus = stockStatus;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getAccepted2Date() {
        return accepted2Date;
    }

    public void setAccepted2Date(String accepted2Date) {
        this.accepted2Date = accepted2Date;
    }

    public String getApprover2Date() {
        return approver2Date;
    }

    public void setApprover2Date(String approver2Date) {
        this.approver2Date = approver2Date;
    }

    public String getRegDate() {
        return regDate;
    }

    public void setRegDate(String regDate) {
        this.regDate = regDate;
    }

    public String getEvidenceAttached() {
        return evidenceAttached;
    }

    public void setEvidenceAttached(String evidenceAttached) {
        this.evidenceAttached = evidenceAttached;
    }

    public String getReasonOfWritten() {
        return reasonOfWritten;
    }

    public void setReasonOfWritten(String reasonOfWritten) {
        this.reasonOfWritten = reasonOfWritten;
    }

    public String getLostItemNo() {
        return lostItemNo;
    }

    public void setLostItemNo(String lostItemNo) {
        this.lostItemNo = lostItemNo;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCommitteMembers() {
        return committeMembers;
    }

    public void setCommitteMembers(String committeMembers) {
        this.committeMembers = committeMembers;
    }

    public HrCommittees getCommitteeId() {
        return committeeId;
    }

    public void setCommitteeId(HrCommittees committeeId) {
        this.committeeId = committeeId;
    }

    @XmlTransient
    public List<LostStockDmsUpload> getLostStockDmsUploadList() {
        if (lostStockDmsUploadList == null) {
            lostStockDmsUploadList = new ArrayList<>();
        }
        return lostStockDmsUploadList;
    }

    public void setLostStockDmsUploadList(List<LostStockDmsUpload> lostStockDmsUploadList) {
        this.lostStockDmsUploadList = lostStockDmsUploadList;
    }

    @XmlTransient
    public List<MmsStockItemLostDtl> getMmsStockItemLostDtlList() {
        if (mmsStockItemLostDtlList == null) {
            mmsStockItemLostDtlList = new ArrayList<>();
        }
        return mmsStockItemLostDtlList;
    }

    public void setMmsStockItemLostDtlList(List<MmsStockItemLostDtl> mmsStockItemLostDtlList) {
        this.mmsStockItemLostDtlList = mmsStockItemLostDtlList;
    }

    public HrDepartments getDepartment() {
        return department;
    }

    public void setDepartment(HrDepartments department) {
        this.department = department;
    }

    public MmsStoreInformation getStoreId() {
        return storeId;
    }

    public void setStoreId(MmsStoreInformation storeId) {
        this.storeId = storeId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (lostStockId != null ? lostStockId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
       
        if (!(object instanceof MmsStockItemLost)) {
            return false;
        }
        MmsStockItemLost other = (MmsStockItemLost) object;
        if ((this.lostStockId == null && other.lostStockId != null) || (this.lostStockId != null && !this.lostStockId.equals(other.lostStockId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return lostItemNo;
    }

    public void addStockDetail(MmsStockItemLostDtl mmsStockDtl) {

        if (mmsStockDtl.getLostStockId() != this) {
            this.getMmsStockItemLostDtlList().add(mmsStockDtl);
            mmsStockDtl.setLostStockId(this);
        }
    }

    public void add(LostStockDmsUpload lostDmsUpload) {
        if (lostDmsUpload.getLostId() != this) {
            this.getLostStockDmsUploadList().add(lostDmsUpload);
            lostDmsUpload.setLostId(this);
        }
    }

    @XmlTransient
    public List<WfMmsProcessed> getStockLostList() {
        if (StockLostList == null) {
            StockLostList = new ArrayList<>();
        }
        return StockLostList;
    }

    public void setStockLostList(List<WfMmsProcessed> StockLostList) {
        this.StockLostList = StockLostList;
    }
}
