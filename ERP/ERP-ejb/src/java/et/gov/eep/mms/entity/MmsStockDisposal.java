package et.gov.eep.mms.entity;

import et.gov.eep.commonApplications.entity.MmsLuDmArchive;
import et.gov.eep.commonApplications.entity.WfMmsProcessed;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author w_station
 */
@Entity
@Table(name = "MMS_STOCK_DISPOSAL")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MmsStockDisposal.findAll", query = "SELECT m FROM MmsStockDisposal m"),
    @NamedQuery(name = "MmsStockDisposal.findByStockId", query = "SELECT m FROM MmsStockDisposal m WHERE m.stockId = :stockId"),
    @NamedQuery(name = "MmsStockDisposal.findByStockNo", query = "SELECT m FROM MmsStockDisposal m WHERE m.stockNo = :stockNo"),
    @NamedQuery(name = "MmsStockDisposal.findByAllParameters", query = "SELECT m FROM MmsStockDisposal m WHERE m.stockNo LIKE :stockNo"),
    @NamedQuery(name = "MmsStockDisposal.findByAllParametersAndDispPrep", query = "SELECT m FROM MmsStockDisposal m WHERE m.stockNo LIKE :stockNo"),
    @NamedQuery(name = "MmsStockDisposal.findByAllParameters2", query = "SELECT m FROM MmsStockDisposal m WHERE m.storeId LIKE :storeId"),
    @NamedQuery(name = "MmsStockDisposal.findDispListByWfStatus", query = "SELECT m FROM MmsStockDisposal m WHERE m.stkStatus=:stkStatus"),
    @NamedQuery(name = "MmsStockDisposal.findDispListByWfStatus1", query = "SELECT m FROM MmsStockDisposal m WHERE m.stkStatus=:stkStatus"),
    @NamedQuery(name = "MmsStockDisposal.findAllByPreparerId", query = "SELECT m FROM MmsStockDisposal m WHERE m.preparedBy = :preparedBy"),
    @NamedQuery(name = "MmsStockDisposal.findByStockNoLike", query = "SELECT m FROM MmsStockDisposal m WHERE m.stockNo LIKE :stockNo"),
    @NamedQuery(name = "MmsStockDisposal.findByStockIdMaximum", query = "SELECT m FROM MmsStockDisposal m WHERE m.stockId = (SELECT Max(c.stockId) FROM MmsStockDisposal c)"),
    @NamedQuery(name = "MmsStockDisposal.findByStoreId", query = "SELECT m FROM MmsStockDisposal m WHERE m.storeId = :storeId"),
    @NamedQuery(name = "MmsStockDisposal.findByProposedDate", query = "SELECT m FROM MmsStockDisposal m WHERE m.proposedDate = :proposedDate"),
    @NamedQuery(name = "MmsStockDisposal.findByApprovalDate", query = "SELECT m FROM MmsStockDisposal m WHERE m.approvalDate = :approvalDate"),
    @NamedQuery(name = "MmsStockDisposal.findByDisposalStatus", query = "SELECT m FROM MmsStockDisposal m WHERE m.disposalStatus = :disposalStatus"),
    @NamedQuery(name = "MmsStockDisposal.findByPreparedBy", query = "SELECT m FROM MmsStockDisposal m WHERE m.preparedBy = :preparedBy"),
    @NamedQuery(name = "MmsStockDisposal.findByPreparerRemark", query = "SELECT m FROM MmsStockDisposal m WHERE m.preparerRemark = :preparerRemark"),
    @NamedQuery(name = "MmsStockDisposal.findByApproverRemark", query = "SELECT m FROM MmsStockDisposal m WHERE m.approverRemark = :approverRemark")})
public class MmsStockDisposal implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MMS_STOCK_DISPOSAL_SEQ")
    @SequenceGenerator(name = "MMS_STOCK_DISPOSAL_SEQ", sequenceName = "MMS_STOCK_DISPOSAL_SEQ", allocationSize = 1)
    @Column(name = "STOCK_ID", nullable = false, precision = 0, scale = -127)
    private Integer stockId;
    @Size(max = 30)
    @Column(name = "STOCK_NO", length = 30)
    private String stockNo;
    @Column(name = "PROPOSED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date proposedDate;
    @Column(name = "APPROVAL_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date approvalDate;
    @JoinColumn(name = "REFERENCE_NUMBER", referencedColumnName = "DOCUMENT_ID")
    @ManyToOne
    private MmsLuDmArchive referenceNumber;
    @Size(max = 30)
    @Column(name = "DISPOSAL_STATUS", length = 30)
    private String disposalStatus;
    @Column(name = "PREPARED_BY", length = 30)
    private Integer preparedBy;
    @Size(max = 90)
    @Column(name = "PREPARER_REMARK", length = 90)
    private String preparerRemark;
    @Size(max = 90)
    @Column(name = "APPROVER_REMARK", length = 90)
    private String approverRemark;
    @Column(name = "STK_STATUS")
    private Integer stkStatus;
    @Size(max = 50)
    @Column(name = "PROCESSED_ON")
    private String processedOn;
    @Size(max = 30)
    @Column(name = "APP_DATE")
    private String appDate;
    @Size(max = 30)
    @Column(name = "PROP_DATE")
    private String propDate;
    @OneToMany(mappedBy = "stockId", cascade = CascadeType.ALL)
    private List<LostStockDisposalDmsUpload> lostStockDisposalDmsUploadList;
    @JoinColumn(name = "STORE_ID", referencedColumnName = "STORE_ID")
    @ManyToOne
    private MmsStoreInformation storeId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "stockId")
    private List<MmsStockDisposalDtl> mmsStockDisposalDtlList;
    @OneToMany(mappedBy = "stkId", cascade = CascadeType.ALL)
    private List<WfMmsProcessed> stockDispList;
    @Transient
    private String columnName;
    @Transient
    private String columnValue;
    public MmsStockDisposal() {
    }

    public MmsStockDisposal(Integer stockId) {
        this.stockId = stockId;
    }

    public Integer getStockId() {
        return stockId;
    }

    public void setStockId(Integer stockId) {
        this.stockId = stockId;
    }

    public String getStockNo() {
        return stockNo;
    }

    public void setStockNo(String stockNo) {
        this.stockNo = stockNo;
    }

    public MmsStoreInformation getStoreId() {
        return storeId;
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

    public MmsLuDmArchive getReferenceNumber() {
        return referenceNumber;
    }

    public void setReferenceNumber(MmsLuDmArchive referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    public void setStoreId(MmsStoreInformation storeId) {
        this.storeId = storeId;
    }

    public Date getProposedDate() {
        return proposedDate;
    }

    public void setProposedDate(Date proposedDate) {
        this.proposedDate = proposedDate;
    }

    public Date getApprovalDate() {
        return approvalDate;
    }

    public void setApprovalDate(Date approvalDate) {
        this.approvalDate = approvalDate;
    }

    public Integer getStkStatus() {
        return stkStatus;
    }

    public void setStkStatus(Integer stkStatus) {
        this.stkStatus = stkStatus;
    }

    public String getDisposalStatus() {
        return disposalStatus;
    }

    public void setDisposalStatus(String disposalStatus) {
        this.disposalStatus = disposalStatus;
    }

    public String getAppDate() {
        return appDate;
    }

    public void setAppDate(String appDate) {
        this.appDate = appDate;
    }

    public String getPropDate() {
        return propDate;
    }

    public void setPropDate(String propDate) {
        this.propDate = propDate;
    }

    public Integer getPreparedBy() {
        return preparedBy;
    }

    public void setPreparedBy(Integer preparedBy) {
        this.preparedBy = preparedBy;
    }

    public String getPreparerRemark() {
        return preparerRemark;
    }

    public void setPreparerRemark(String preparerRemark) {
        this.preparerRemark = preparerRemark;
    }

    public String getApproverRemark() {
        return approverRemark;
    }

    public void setApproverRemark(String approverRemark) {
        this.approverRemark = approverRemark;
    }

    @XmlTransient
    public List<LostStockDisposalDmsUpload> getLostStockDisposalDmsUploadList() {
        if (lostStockDisposalDmsUploadList == null) {
            lostStockDisposalDmsUploadList = new ArrayList<>();
        }
        return lostStockDisposalDmsUploadList;
    }

    public void setLostStockDisposalDmsUploadList(List<LostStockDisposalDmsUpload> lostStockDisposalDmsUploadList) {
        this.lostStockDisposalDmsUploadList = lostStockDisposalDmsUploadList;
    }

    public String getProcessedOn() {
        return processedOn;
    }

    public void setProcessedOn(String processedOn) {
        this.processedOn = processedOn;
    }

    @XmlTransient
    public List<WfMmsProcessed> getStockDispList() {
        if (stockDispList == null) {
            stockDispList = new ArrayList<>();
        }
        return stockDispList;
    }

    public void setStockDispList(List<WfMmsProcessed> stockDispList) {
        this.stockDispList = stockDispList;
    }

    @XmlTransient
    public List<MmsStockDisposalDtl> getMmsStockDisposalDtlList() {
        if (mmsStockDisposalDtlList == null) {
            mmsStockDisposalDtlList = new ArrayList<>();
        }
        return mmsStockDisposalDtlList;
    }

    public void setMmsStockDisposalDtlList(List<MmsStockDisposalDtl> mmsStockDisposalDtlList) {
        this.mmsStockDisposalDtlList = mmsStockDisposalDtlList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (stockId != null ? stockId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {

        if (!(object instanceof MmsStockDisposal)) {
            return false;
        }
        MmsStockDisposal other = (MmsStockDisposal) object;
        if ((this.stockId == null && other.stockId != null) || (this.stockId != null && !this.stockId.equals(other.stockId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return stockNo;
    }

    public void addStockDisposalDetail(MmsStockDisposalDtl mmsStockDisposalDetail) {

        if (mmsStockDisposalDetail.getStockId() != this) {
            this.getMmsStockDisposalDtlList().add(mmsStockDisposalDetail);
            mmsStockDisposalDetail.setStockId(this);
        }
    }

    public void add(LostStockDisposalDmsUpload lostDmsUpload) {
        if (lostDmsUpload.getStockId() != this) {
            this.getLostStockDisposalDmsUploadList().add(lostDmsUpload);
            lostDmsUpload.setStockId(this);
        }
    }

}
