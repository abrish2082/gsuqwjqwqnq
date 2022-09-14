/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.mms.entity;

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
import javax.persistence.OrderColumn;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import et.gov.eep.commonApplications.entity.WfMmsProcessed;
import et.gov.eep.hrms.entity.organization.HrDepartments;
import et.gov.eep.prms.entity.PrmsServiceProvider;
import javax.persistence.Transient;

/**
 *
 * @author user
 */
@Entity
@Table(name = "MMS_ISIV")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MmsIsiv.findAll", query = "SELECT m FROM MmsIsiv m"),
    @NamedQuery(name = "MmsIsiv.findByTransferId", query = "SELECT m FROM MmsIsiv m WHERE m.transferId = :transferId"),
    @NamedQuery(name = "MmsIsiv.findByApprovedBy", query = "SELECT m FROM MmsIsiv m WHERE m.approvedBy = :approvedBy"),
    @NamedQuery(name = "MmsIsiv.findByISivNo", query = "SELECT m FROM MmsIsiv m WHERE m.transferNo = :transferNo"),
    @NamedQuery(name = "MmsIsiv.findByReceivedBy", query = "SELECT m FROM MmsIsiv m WHERE m.receivedBy = :receivedBy"),
    @NamedQuery(name = "MmsIsiv.findByReceivedDate", query = "SELECT m FROM MmsIsiv m WHERE m.receivedDate = :receivedDate"),
    @NamedQuery(name = "MmsIsiv.findIsivListByWfStatus", query = "SELECT m FROM MmsIsiv m WHERE m.StatusWf = :StatusWf"),
    @NamedQuery(name = "MmsIsiv.findIsivListByWfStatusAndStoreId", query = "SELECT m FROM MmsIsiv m WHERE m.StatusWf = :StatusWf AND m.fromStore.storeId=:fromStore"),
    @NamedQuery(name = "MmsIsiv.findbyIsisvNumberLike", query = "SELECT m FROM MmsIsiv m WHERE m.transferNo LIKE :transferNo"),
    //for Sadik
    @NamedQuery(name = "MmsIsiv.findByApprovedStatus", query = "SELECT m FROM MmsIsiv m WHERE m.approvedStatus = :approvedStatus"),
    @NamedQuery(name = "MmsIsiv.findByApprovedStatusAndStoreId", query = "SELECT m FROM MmsIsiv m WHERE m.StatusWf = :StatusWf AND m.toStore.storeId = :toStore"),
    @NamedQuery(name = "MmsIsiv.findISIVNosList", query = "SELECT m FROM MmsIsiv m WHERE m.approvedStatus = :approvedStatus"),
    @NamedQuery(name = "MmsIsiv.findByToStore", query = "SELECT m FROM MmsIsiv m WHERE m.toStore = :toStore"),
    @NamedQuery(name = "MmsIsiv.findByFromStore", query = "SELECT m FROM MmsIsiv m WHERE m.fromStore = :fromStore"),
    @NamedQuery(name = "MmsIsiv.findByFromStoreAndProcessedBy", query = "SELECT m FROM MmsIsiv m WHERE m.fromStore = :fromStore AND m.processedBy=:processedBy"),
    @NamedQuery(name = "MmsIsiv.findByProcessedBy", query = "SELECT m FROM MmsIsiv m WHERE  m.processedBy=:processedBy"),
    @NamedQuery(name = "MmsIsiv.findByTransferDate", query = "SELECT m FROM MmsIsiv m WHERE m.transferDate = :transferDate"),
    @NamedQuery(name = "MmsIsiv.findByTransferNo", query = "SELECT m FROM MmsIsiv m WHERE m.transferNo = :transferNo"),
    @NamedQuery(name = "MmsIsiv.findByIsivNoAndFromStoreId", query = "SELECT m FROM MmsIsiv m WHERE  m.fromStore= :fromStore AND m.transferNo LIKE :transferNo"),
    @NamedQuery(name = "MmsIsiv.findByIsivNoAndFromStoreIdAndProcessedBy", query = "SELECT m FROM MmsIsiv m WHERE  m.fromStore= :fromStore AND m.transferNo LIKE :transferNo AND m.processedBy=:processedBy"),
    @NamedQuery(name = "MmsIsiv.findByIsivIdMaximum", query = "SELECT m FROM MmsIsiv m WHERE m.transferId = (SELECT Max(c.transferId)  from MmsIsiv c)"),
    @NamedQuery(name = "MmsIsiv.findByTransferedBy", query = "SELECT m FROM MmsIsiv m WHERE m.transferedBy = :transferedBy")})
public class MmsIsiv implements Serializable {

    //for Sadik
//    @Size(max = 20)
    @Column(name = "PROCESSED_BY")
    private Integer processedBy;
//    @Size(max = 50)
    @Column(name = "STATUS_WF")
    private Integer StatusWf;
    @Size(max = 20)
    @Column(name = "COMMENT_GIVEN")
    private String commentGiven;
    @Size(max = 20)
    @Column(name = "PROCESSED_ON")
    private String processedOn;
    @Size(max = 20)
    @Column(name = "ISIV_NO")
    private String isivNo;
    @Column(name = "APPROVED_STATUS")
    private Integer approvedStatus;
    @Size(max = 255)
    @Column(name = "REASON_OF_TRANSFER")
    private String reasonOfTransfer;

    @Size(max = 50)
    @Column(name = "PLATE_NO")
    private String plateNo;
    @Size(max = 20)
    @Column(name = "JOB_NO")
    private String jobNo;

    @Size(max = 50)
    @Column(name = "DRIVER_NAME")
    private String driverName;
//    @Size(max = 50)
//    @Column(name = "DEPARTMENT")
//    private String department;

    @JoinColumn(name = "TRANSPORTER", referencedColumnName = "SERVICE_PRO_ID")
    @ManyToOne
    private PrmsServiceProvider transporter;

    @JoinColumn(name = "DEPARTMENT", referencedColumnName = "DEP_ID")
    @ManyToOne
    private HrDepartments department;

    @Size(max = 50)
    @Column(name = "COST_CENTER")
    private String costCenter;
    @Size(max = 50)
    @Column(name = "REGION")
    private String region;
    @Size(max = 50)
    @Column(name = "STORE_LOCATION")
    private String storeLocation;
    @Size(max = 20)
    @Column(name = "SR_NO")
    private String srNo;

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MMS_ISIV_SEQ")
    @SequenceGenerator(name = "MMS_ISIV_SEQ", sequenceName = "MMS_ISIV_SEQ", allocationSize = 1)
    @Column(name = "TRANSFER_ID")
    private Integer transferId;
    @Size(max = 255)
    @Column(name = "APPROVED_BY")
    private String approvedBy;

    @JoinColumn(name = "FROM_STORE", referencedColumnName = "STORE_ID")
    @ManyToOne
    private MmsStoreInformation fromStore;
    //<editor-fold defaultstate="collapsed" desc="Dynamic Searching Transient Parameters">
    @Transient
    private String columnName;
    @Transient
    private String columnValue;
    //</editor-fold >
//    @Size(max = 255)
//    @Column(name = "FROM_STORE")
//    private String fromStore;
    @Size(max = 255)
    @Column(name = "RECEIVED_BY")
    private String receivedBy;
    @Size(max = 20)
    @Column(name = "RECEIVED_DATE")

    private String receivedDate;
//    @Size(max = 255)
//    @Column(name = "STATUS")
//    private String status;
    @JoinColumn(name = "TO_STORE", referencedColumnName = "STORE_ID")
    @ManyToOne
    private MmsStoreInformation toStore;

//    @Size(max = 255)
//    @Column(name = "TO_STORE")
//    private String toStore;
    @Size(max = 20)
    @Column(name = "TRANSFER_DATE")

    private String transferDate;
    @Size(max = 255)
    @Column(name = "TRANSFER_NO")
    private String transferNo;

    @Size(max = 255)
    @Column(name = "TRANSFERED_BY")
    private String transferedBy;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "transferId")
    private List<MmsIsivDetail> mmsIsivDetailList;

    @JoinColumn(name = "STORE_REQ_ID", referencedColumnName = "STORE_REQ_ID")
    @ManyToOne
    private MmsStorereq storeReqId;

    @OrderColumn(name = "PROCESSED_ID")
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "transferId")
    private List<WfMmsProcessed> wfMmsProcessedList;

    /**
     *
     */
    public MmsIsiv() {
    }

    public Integer getStatusWf() {
        return StatusWf;
    }

    public void setStatusWf(Integer StatusWf) {
        this.StatusWf = StatusWf;
    }

    public Integer getProcessedBy() {
        return processedBy;
    }

    public void setProcessedBy(Integer processedBy) {
        this.processedBy = processedBy;
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
     * @param transferId
     */
    public MmsIsiv(Integer transferId) {
        this.transferId = transferId;
    }

    /**
     *
     * @return
     */
    public Integer getTransferId() {
        return transferId;
    }

    /**
     *
     * @param transferId
     */
    public void setTransferId(Integer transferId) {
        this.transferId = transferId;
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
    public MmsStoreInformation getFromStore() {
        return fromStore;
    }

    /**
     *
     * @param fromStore
     */
    public void setFromStore(MmsStoreInformation fromStore) {
        this.fromStore = fromStore;
    }

    /**
     *
     * @return
     */
    public String getReceivedBy() {
        return receivedBy;
    }

    /**
     *
     * @param receivedBy
     */
    public void setReceivedBy(String receivedBy) {
        this.receivedBy = receivedBy;
    }

    /**
     *
     * @return
     */
    public String getReceivedDate() {
        return receivedDate;
    }

    /**
     *
     * @param receivedDate
     */
    public void setReceivedDate(String receivedDate) {
        this.receivedDate = receivedDate;
    }

    /**
     *
     * @return
     */
    public MmsStoreInformation getToStore() {
        return toStore;
    }

    /**
     *
     * @param toStore
     */
    public void setToStore(MmsStoreInformation toStore) {
        this.toStore = toStore;
    }

    /**
     *
     * @return
     */
    public String getTransferDate() {
        return transferDate;
    }

    /**
     *
     * @param transferDate
     */
    public void setTransferDate(String transferDate) {
        this.transferDate = transferDate;
    }

    /**
     *
     * @return
     */
    public String getTransferNo() {
        return transferNo;
    }

    /**
     *
     * @param transferNo
     */
    public void setTransferNo(String transferNo) {
        this.transferNo = transferNo;
    }

    /**
     *
     * @return
     */
    public String getTransferedBy() {
        return transferedBy;
    }

    /**
     *
     * @param transferedBy
     */
    public void setTransferedBy(String transferedBy) {
        this.transferedBy = transferedBy;
    }

    public String getIsivNo() {
        return isivNo;
    }

    public void setIsivNo(String isivNo) {
        this.isivNo = isivNo;
    }

    /**
     *
     * @return
     */
    public Integer getApprovedStatus() {
        return approvedStatus;
    }

    /**
     *
     * @param approvedStatus
     */
    public void setApprovedStatus(Integer approvedStatus) {
        this.approvedStatus = approvedStatus;
    }

    /**
     *
     * @return
     */
    @XmlTransient
    public List<MmsIsivDetail> getMmsIsivDetailList() {
        if (mmsIsivDetailList == null) {
            mmsIsivDetailList = new ArrayList<>();
        }
        return mmsIsivDetailList;
    }

    /**
     *
     * @param mmsIsivDetailList
     */
    public void setMmsIsivDetailList(List<MmsIsivDetail> mmsIsivDetailList) {
        this.mmsIsivDetailList = mmsIsivDetailList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (transferId != null ? transferId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MmsIsiv)) {
            return false;
        }
        MmsIsiv other = (MmsIsiv) object;
        if ((this.transferId == null && other.transferId != null) || (this.transferId != null && !this.transferId.equals(other.transferId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return transferNo;
    }

    /**
     *
     * @param isivDetail
     */
    public void addIsIvInfo(MmsIsivDetail isivDetail) {
        if (isivDetail.getTransferId() != this) {
            this.getMmsIsivDetailList().add(isivDetail);
            isivDetail.setTransferId(this);
        }
    }

    /**
     *
     * @return
     */
    public String getReasonOfTransfer() {
        return reasonOfTransfer;
    }

    /**
     *
     * @param reasonOfTransfer
     */
    public void setReasonOfTransfer(String reasonOfTransfer) {
        this.reasonOfTransfer = reasonOfTransfer;
    }

    public PrmsServiceProvider getTransporter() {
        return transporter;
    }

    public void setTransporter(PrmsServiceProvider transporter) {
        this.transporter = transporter;
    }

    /**
     *
     * @return
     */
    public String getPlateNo() {
        return plateNo;
    }

    /**
     *
     * @param plateNo
     */
    public void setPlateNo(String plateNo) {
        this.plateNo = plateNo;
    }

    /**
     *
     * @return
     */
    public String getJobNo() {
        return jobNo;
    }

    /**
     *
     * @param jobNo
     */
    public void setJobNo(String jobNo) {
        this.jobNo = jobNo;
    }

    /**
     *
     * @return
     */
    public HrDepartments getDepartment() {
        return department;
    }

    /**
     *
     * @param department
     */
    public void setDepartment(HrDepartments department) {
        this.department = department;
    }

    /**
     *
     * @return
     */
    public String getCostCenter() {
        return costCenter;
    }

    /**
     *
     * @param costCenter
     */
    public void setCostCenter(String costCenter) {
        this.costCenter = costCenter;
    }

    /**
     *
     * @return
     */
    public String getRegion() {
        return region;
    }

    /**
     *
     * @param region
     */
    public void setRegion(String region) {
        this.region = region;
    }

    /**
     *
     * @return
     */
    public String getStoreLocation() {
        return storeLocation;
    }

    /**
     *
     * @param storeLocation
     */
    public void setStoreLocation(String storeLocation) {
        this.storeLocation = storeLocation;
    }

    /**
     *
     * @return
     */
    public String getSrNo() {
        return srNo;
    }

    /**
     *
     * @param srNo
     */
    public void setSrNo(String srNo) {
        this.srNo = srNo;
    }

    public MmsStorereq getStoreReqId() {
        return storeReqId;
    }

    public void setStoreReqId(MmsStorereq storeReqId) {
        this.storeReqId = storeReqId;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    @XmlTransient
    public List<WfMmsProcessed> getWfMmsProcessedList() {

        if (wfMmsProcessedList == null) {
            wfMmsProcessedList = new ArrayList<>();
        }
        return wfMmsProcessedList;
    }

    public void setWfMmsProcessedList(List<WfMmsProcessed> wfMmsProcessedList) {
        this.wfMmsProcessedList = wfMmsProcessedList;
    }

    public void addIsivIdToWorkflow(WfMmsProcessed wfMmsProcessed) {
        if (wfMmsProcessed.getTransferId() != this) {
            this.getWfMmsProcessedList().add(wfMmsProcessed);

            wfMmsProcessed.setTransferId(this);
        }
    }

}
