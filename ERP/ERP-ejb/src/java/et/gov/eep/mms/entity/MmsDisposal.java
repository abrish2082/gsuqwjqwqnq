package et.gov.eep.mms.entity;

import et.gov.eep.commonApplications.entity.MmsLuDmArchive;
import et.gov.eep.commonApplications.entity.WfMmsProcessed;
import java.io.Serializable;
import java.math.BigDecimal;
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
@Table(name = "MMS_DISPOSAL")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MmsDisposal.findAll", query = "SELECT m FROM MmsDisposal m"),
    @NamedQuery(name = "MmsDisposal.findByDispId", query = "SELECT m FROM MmsDisposal m WHERE m.dispId = :dispId"),
    @NamedQuery(name = "MmsDisposal.findByDispNo", query = "SELECT m FROM MmsDisposal m WHERE m.dispNo = :dispNo"),
    @NamedQuery(name = "MmsDisposal.findByStoreId", query = "SELECT m FROM MmsDisposal m WHERE m.storeId = :storeId"),
    @NamedQuery(name = "MmsDisposal.findByDisposalStatus", query = "SELECT m FROM MmsDisposal m WHERE m.disposalStatus = :disposalStatus"),
    @NamedQuery(name = "MmsDisposal.findDisposalListByWfStatus", query = "SELECT m FROM MmsDisposal m WHERE m.dpStatus=:dpStatus"),
    @NamedQuery(name = "MmsDisposal.findAllByPreparerId", query = "SELECT m FROM MmsDisposal m WHERE m.proposedBy = :proposedBy"),
    @NamedQuery(name = "MmsDisposal.findByAllParameters", query = "SELECT m FROM MmsDisposal m WHERE m.dispNo LIKE :dispNo"),
    @NamedQuery(name = "MmsDisposal.findByAllParametersAndDispPrep", query = "SELECT m FROM MmsDisposal m WHERE m.dispNo = :dispNo AND m.proposedBy = :proposedBy"),
    @NamedQuery(name = "MmsDisposal.findByNfaIdMaximum", query = "SELECT m FROM MmsDisposal m WHERE m.dispId = (SELECT Max(c.dispId) FROM MmsDisposal c)"),
    @NamedQuery(name = "MmsDisposal.findByProposedDate", query = "SELECT m FROM MmsDisposal m WHERE m.proposedDate = :proposedDate"),
    @NamedQuery(name = "MmsDisposal.findByApprovedDate", query = "SELECT m FROM MmsDisposal m WHERE m.approvedDate = :approvedDate"),
    @NamedQuery(name = "MmsDisposal.findByProposedBy", query = "SELECT m FROM MmsDisposal m WHERE m.proposedBy = :proposedBy"),
    @NamedQuery(name = "MmsDisposal.findByProposalRemark", query = "SELECT m FROM MmsDisposal m WHERE m.proposalRemark = :proposalRemark"),
    @NamedQuery(name = "MmsDisposal.findByApprovedBy", query = "SELECT m FROM MmsDisposal m WHERE m.approvedBy = :approvedBy"),
    @NamedQuery(name = "MmsDisposal.findByApproverRemark", query = "SELECT m FROM MmsDisposal m WHERE m.approverRemark = :approverRemark")})
public class MmsDisposal implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MMS_DISP_SEQ")
    @SequenceGenerator(name = "MMS_DISP_SEQ", sequenceName = "MMS_DISP_SEQ", allocationSize = 1)
    @Column(name = "DISP_ID", nullable = false, precision = 0, scale = -127)
    private BigDecimal dispId;
    @Size(max = 30)
    @Column(name = "DISP_NO", length = 30)
    private String dispNo;
    @Size(max = 20)
    @Column(name = "DISPOSAL_STATUS", length = 20)
    private String disposalStatus;
    @Column(name = "PROPOSED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date proposedDate;
    @Column(name = "APPROVED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date approvedDate;
    @Column(name = "PROPOSED_BY", length = 30)
    private Integer proposedBy;
    @JoinColumn(name = "REFERENCE_NUMBER", referencedColumnName = "DOCUMENT_ID")
    @ManyToOne
    private MmsLuDmArchive referenceNumber;
    @Size(max = 50)
    @Column(name = "DISP_COLLECTION", length = 30)
    private String dispCollection;
    @Size(max = 30)
    @Column(name = "PROPOSAL_REMARK", length = 30)
    private String proposalRemark;
    @Size(max = 30)
    @Column(name = "APPROVED_BY", length = 30)
    private String approvedBy;
    @Size(max = 30)
    @Column(name = "APPROVER_REMARK", length = 30)
    private String approverRemark;
    @Size(max = 30)
    @Column(name = "APP_DATE")
    private String appDate;
    @Size(max = 30)
    @Column(name = "PROP_DATE")
    private String propDate;
    @Size(max = 50)
    @Column(name = "PROCESSED_ON")
    private String processedOn;
    @Column(name = "DP_STATUS")
    private Integer dpStatus;
    //<editor-fold defaultstate="collapsed" desc="Dynamic Searching Transient Parameters">
    @Transient
    private String columnName;
    @Transient
    private String columnValue;
    //</editor-fold >
    @OneToMany(mappedBy = "dispId", cascade = CascadeType.ALL)
    private List<LostDisposalDmsUplod> lostDisposalDmsUplodList;
    @OneToMany(mappedBy = "dispId", cascade = CascadeType.ALL)
    private List<WfMmsProcessed> disposalList;
    @JoinColumn(name = "STORE_ID", referencedColumnName = "STORE_ID")
    @ManyToOne
    private MmsStoreInformation storeId;
    @JoinColumn(name = "DISPOSAL_ITEM_ID", referencedColumnName = "DISPOSAL_ID")
    @ManyToOne
    private MmsDisposalItems disposalItemId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "dispId")
    private List<MmsDisposalDtl> mmsDisposalDtlList;

    public MmsDisposal() {
    }

    public MmsDisposal(BigDecimal dispId) {
        this.dispId = dispId;
    }

    public BigDecimal getDispId() {
        return dispId;
    }

    public void setDispId(BigDecimal dispId) {
        this.dispId = dispId;
    }

    public String getDispNo() {
        return dispNo;
    }

    public void setDispNo(String dispNo) {
        this.dispNo = dispNo;
    }

    public String getProcessedOn() {
        return processedOn;
    }

    public void setProcessedOn(String processedOn) {
        this.processedOn = processedOn;
    }

    public MmsLuDmArchive getReferenceNumber() {
        return referenceNumber;
    }

    public void setReferenceNumber(MmsLuDmArchive referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    public String getDispCollection() {
        return dispCollection;
    }

    public void setDispCollection(String dispCollection) {
        this.dispCollection = dispCollection;
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

    public MmsStoreInformation getStoreId() {
        return storeId;
    }

    public void setStoreId(MmsStoreInformation storeId) {
        this.storeId = storeId;
    }

    public MmsDisposalItems getDisposalItemId() {
        return disposalItemId;
    }

    public void setDisposalItemId(MmsDisposalItems disposalItemId) {
        this.disposalItemId = disposalItemId;
    }

    public String getDisposalStatus() {
        return disposalStatus;
    }

    public void setDisposalStatus(String disposalStatus) {
        this.disposalStatus = disposalStatus;
    }

    public Date getProposedDate() {
        return proposedDate;
    }

    public void setProposedDate(Date proposedDate) {
        this.proposedDate = proposedDate;
    }

    public Date getApprovedDate() {
        return approvedDate;
    }

    public void setApprovedDate(Date approvedDate) {
        this.approvedDate = approvedDate;
    }

    public Integer getDpStatus() {
        return dpStatus;
    }

    public void setDpStatus(Integer dpStatus) {
        this.dpStatus = dpStatus;
    }

    public Integer getProposedBy() {
        return proposedBy;
    }

    public void setProposedBy(Integer proposedBy) {
        this.proposedBy = proposedBy;
    }

    public String getProposalRemark() {
        return proposalRemark;
    }

    public void setProposalRemark(String proposalRemark) {
        this.proposalRemark = proposalRemark;
    }

    public String getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(String approvedBy) {
        this.approvedBy = approvedBy;
    }

    public String getApproverRemark() {
        return approverRemark;
    }

    public void setApproverRemark(String approverRemark) {
        this.approverRemark = approverRemark;
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

    @XmlTransient
    public List<LostDisposalDmsUplod> getLostDisposalDmsUplodList() {
        if (lostDisposalDmsUplodList == null) {
            lostDisposalDmsUplodList = new ArrayList<>();
        }
        return lostDisposalDmsUplodList;
    }

    public void setLostDisposalDmsUplodList(List<LostDisposalDmsUplod> lostDisposalDmsUplodList) {
        this.lostDisposalDmsUplodList = lostDisposalDmsUplodList;
    }

    @XmlTransient
    public List<WfMmsProcessed> getDisposalList() {
        if (disposalList == null) {
            disposalList = new ArrayList<>();
        }
        return disposalList;
    }

    public void setDisposalList(List<WfMmsProcessed> disposalList) {
        this.disposalList = disposalList;
    }

    @XmlTransient
    public List<MmsDisposalDtl> getMmsDisposalDtlList() {
        if (mmsDisposalDtlList == null) {
            mmsDisposalDtlList = new ArrayList<>();
        }
        return mmsDisposalDtlList;
    }

    public void setMmsDisposalDtlList(List<MmsDisposalDtl> mmsDisposalDtlList) {
        this.mmsDisposalDtlList = mmsDisposalDtlList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (dispId != null ? dispId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {

        if (!(object instanceof MmsDisposal)) {
            return false;
        }
        MmsDisposal other = (MmsDisposal) object;
        if ((this.dispId == null && other.dispId != null) || (this.dispId != null && !this.dispId.equals(other.dispId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.eep.mms.entity.MmsDisposal[ dispId=" + dispId + " ]";
    }

    public void addDisposalDetail(MmsDisposalDtl mmsDisposalDtlList1) {

        if (mmsDisposalDtlList1.getDispId() != this) {
            this.getMmsDisposalDtlList().add(mmsDisposalDtlList1);
            mmsDisposalDtlList1.setDispId(this);
        }
    }

    public void add(LostDisposalDmsUplod lostDmsUpload) {
        if (lostDmsUpload.getDispId() != this) {
            this.getLostDisposalDmsUplodList().add(lostDmsUpload);
            lostDmsUpload.setDispId(this);
        }
    }

}
