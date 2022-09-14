/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.mms.entity;

import java.io.Serializable;
import java.util.ArrayList;
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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import et.gov.eep.commonApplications.entity.WfMmsProcessed;
import javax.persistence.Transient;

/**
 *
 * @author Minab
 */
@Entity
@Table(name = "MMS_SIV")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MmsSiv.findAll", query = "SELECT m FROM MmsSiv m"),
    @NamedQuery(name = "MmsSiv.findBySivId", query = "SELECT m FROM MmsSiv m WHERE m.sivId LIKE :sivId and m.processedBy=:processedBy"),
    @NamedQuery(name = "MmsSiv.findBySivNo", query = "SELECT m FROM MmsSiv m WHERE m.sivNo = :sivNo"),
    @NamedQuery(name = "MmsSiv.findByReceivingDept", query = "SELECT m FROM MmsSiv m WHERE m.receivingDept = :receivingDept"),
    @NamedQuery(name = "MmsSiv.findBySrNo", query = "SELECT m FROM MmsSiv m WHERE m.srNo = :srNo"),
    @NamedQuery(name = "MmsSiv.findByStoreId", query = "SELECT m FROM MmsSiv m WHERE m.storeId = :storeId"),
    @NamedQuery(name = "MmsSiv.findByStoreIdAndProcessedBy", query = "SELECT m FROM MmsSiv m WHERE m.storeId = :storeId AND m.processedBy=:processedBy "),
    @NamedQuery(name = "MmsSiv.findByProcessedBy", query = "SELECT m FROM MmsSiv m WHERE m.processedBy=:processedBy "),
    @NamedQuery(name = "MmsSiv.findBySivNoAndStoreId", query = "SELECT m FROM MmsSiv m WHERE m.sivNo LIKE :sivNo AND m.storeId=:storeId"),
    @NamedQuery(name = "MmsSiv.findBySivNoAndStoreIdAndProcessedBy", query = "SELECT m FROM MmsSiv m WHERE m.sivNo LIKE :sivNo AND m.storeId=:storeId AND m.processedBy=:processedBy "),
    @NamedQuery(name = "MmsSiv.findByIssuedToAndStoreId", query = "SELECT m FROM MmsSiv m WHERE m.storeId.storeId = :storeId"),
    @NamedQuery(name = "MmsSiv.findSivListByWfStatus", query = "SELECT m FROM  MmsSiv m WHERE m.Status=:status"),
    @NamedQuery(name = "MmsSiv.findByApprovedStatusAndStore", query = "SELECT m FROM MmsSiv m WHERE m.Status =:status AND m.storeId.storeId =:storeId"),
    @NamedQuery(name = "MmsSiv.findBySivIdForSrNo", query = "SELECT m FROM MmsSiv m WHERE m.sivId =:sivId"),
    @NamedQuery(name = "MmsSiv.findBySivNoFixedSr", query = "SELECT m FROM MmsSiv m WHERE m.storeReqId.srType = 'fixed'"),
    @NamedQuery(name = "MmsSiv.findByIssuedToAndStoreIdAndProcessedBy", query = "SELECT m FROM MmsSiv m WHERE m.issuedTo = :issuedTo AND m.storeId = :storeId AND m.processedBy=:processedBy "),
    @NamedQuery(name = "MmsSiv.findByStoreId2", query = "SELECT m FROM MmsSiv m WHERE  m.storeId = :storeId"),
    @NamedQuery(name = "MmsSiv.findByStoreId2AndProcessedBy", query = "SELECT m FROM MmsSiv m WHERE  m.storeId = :storeId AND m.processedBy=:processedBy "),
    @NamedQuery(name = "MmsSiv.findByIssuedStoreName", query = "SELECT m FROM MmsSiv m WHERE m.issuedStoreName = :issuedStoreName"),
    @NamedQuery(name = "MmsSiv.findBySIVIdMaximum", query = "SELECT m FROM MmsSiv m WHERE m.sivId = (SELECT Max(c.sivId)  from MmsSiv c)"),
  
    @NamedQuery(name = "MmsSiv.findByApprovedStatuss", query = "SELECT m FROM MmsSiv m WHERE m.approvedStatus = :approvedStatus AND m.storeId.storeId=:storeId"),
    @NamedQuery(name = "MmsSiv.findSIVNosList", query = "SELECT m FROM MmsSiv m WHERE m.approvedStatus = :approvedStatus"),
    @NamedQuery(name = "MmsSiv.findByIssuedTo", query = "SELECT m FROM MmsSiv m WHERE m.issuedTo = :issuedTo"),
    @NamedQuery(name = "MmsSiv.findByApprovedBy", query = "SELECT m FROM MmsSiv m WHERE m.approvedBy = :approvedBy"),
    @NamedQuery(name = "MmsSiv.findByApprovedStatus", query = "SELECT m FROM MmsSiv m WHERE m.approvedStatus = :approvedStatus"),
    @NamedQuery(name = "MmsSiv.findByStatus", query = "SELECT m FROM MmsSiv m WHERE m.Status = :Status"),
    @NamedQuery(name = "MmsSiv.findByPricedBy", query = "SELECT m FROM MmsSiv m WHERE m.pricedBy = :pricedBy"),
    @NamedQuery(name = "MmsSiv.findBySivNoLike", query = "SELECT m FROM MmsSiv m WHERE m.sivNo LIKE :sivNo"),
    @NamedQuery(name = "MmsSiv.findByPreparedBy", query = "SELECT m FROM MmsSiv m WHERE m.preparedBy = :preparedBy"),
    @NamedQuery(name = "MmsSiv.findByIssuedRemark", query = "SELECT m FROM MmsSiv m WHERE m.issuedRemark = :issuedRemark"),
    @NamedQuery(name = "MmsSiv.findByApproverRemark", query = "SELECT m FROM MmsSiv m WHERE m.approverRemark = :approverRemark"),
    @NamedQuery(name = "MmsSiv.findByDates", query = "SELECT m FROM MmsSiv m WHERE m.dates = :dates")})
public class MmsSiv implements Serializable {

    @Column(name = "APPROVED_STATUS")
    private Integer approvedStatus;

    @OneToMany(mappedBy = "sivId")
    private List<MmsManageLocation> mmsManageLocationList;

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MMS_SIV_SEQ")
    @SequenceGenerator(name = "MMS_SIV_SEQ", sequenceName = "MMS_SIV_SEQ", allocationSize = 1)
    @Column(name = "SIV_ID")
    private Integer sivId;
    @Column(name = "STATUS")
    private Integer Status;
    @Size(max = 255)
    @Column(name = "SIV_NO")
    private String sivNo;
    //<editor-fold defaultstate="collapsed" desc="Dynamic Searching Transient Parameters">
    @Transient
    private String columnName;
    @Transient
    private String columnValue;
    //</editor-fold >
    @Size(max = 255)
    @Column(name = "RECEIVING_DEPT")
    private String receivingDept;
    @Size(max = 255)
    @Column(name = "SR_NO")
    private String srNo;
    @Size(max = 255)
    @Column(name = "ISSUED_STORE_NAME")
    private String issuedStoreName;
    @Size(max = 255)
    @Column(name = "ISSUED_TO")
    private String issuedTo;
    @Size(max = 255)
    @Column(name = "APPROVED_BY")
    private String approvedBy;
    @Size(max = 255)
    @Column(name = "PRICED_BY")
    private String pricedBy;
    @Size(max = 255)
    @Column(name = "PREPARED_BY")
    private String preparedBy;
    @Size(max = 255)
    @Column(name = "ISSUED_REMARK")
    private String issuedRemark;
    @Size(max = 255)
    @Column(name = "APPROVER_REMARK")
    private String approverRemark;
    @Column(name = "DATES")
    private String dates;
    @Size(max = 20)
    @Column(name = "PREPARED_DATE")
    private String preparddate;

    @Column(name = "PROCESSED_BY")
    private Integer processedBy;
    @Size(max = 20)
    @Column(name = "COMMENT_GIVEN")
    private String commentGiven;
    @Size(max = 20)
    @Column(name = "PROCESSED_ON")
    private String processedOn;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sivId")
    private List<MmsSivDetail> mmsSivDetailList;
    @OrderColumn(name = "PROCESSED_ID")
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sivId")
    private List<WfMmsProcessed> wfMmsProcessedList;

    @JoinColumn(name = "STORE_REQ_ID", referencedColumnName = "STORE_REQ_ID")
    @ManyToOne
    private MmsStorereq storeReqId;
    
    @JoinColumn(name = "STORE_ID", referencedColumnName = "STORE_ID")
    @ManyToOne
    private MmsStoreInformation storeId;

    public MmsSiv() {
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

    /**
     *
     * @param sivId
     */
    public MmsSiv(Integer sivId) {
        this.sivId = sivId;
    }

    /**
     *
     * @return
     */
    public Integer getSivId() {
        return sivId;
    }

    /**
     *
     * @param sivId
     */
    public void setSivId(Integer sivId) {
        this.sivId = sivId;
    }

    /**
     *
     * @return
     */
    public String getSivNo() {
        return sivNo;
    }

    /**
     *
     * @param sivNo
     */
    public void setSivNo(String sivNo) {
        this.sivNo = sivNo;
    }

    /**
     *
     * @return
     */
    public String getReceivingDept() {
        return receivingDept;
    }

    /**
     *
     * @param receivingDept
     */
    public void setReceivingDept(String receivingDept) {
        this.receivingDept = receivingDept;
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
     * @return
     */
    public String getIssuedStoreName() {
        return issuedStoreName;
    }

    public void setIssuedStoreName(String issuedStoreName) {
        this.issuedStoreName = issuedStoreName;
    }

    /**
     *
     * @return
     */
    public String getIssuedTo() {
        return issuedTo;
    }

    public void setIssuedTo(String issuedTo) {
        this.issuedTo = issuedTo;
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

    public String getPricedBy() {
        return pricedBy;
    }

    public void setPricedBy(String pricedBy) {
        this.pricedBy = pricedBy;
    }

    /**
     *
     * @return
     */
    public String getPreparedBy() {
        return preparedBy;
    }

    /**
     *
     * @param preparedBy
     */
    public void setPreparedBy(String preparedBy) {
        this.preparedBy = preparedBy;
    }

    /**
     *
     * @return
     */
    public String getIssuedRemark() {
        return issuedRemark;
    }

    /**
     *
     * @param issuedRemark
     */
    public void setIssuedRemark(String issuedRemark) {
        this.issuedRemark = issuedRemark;
    }

    /**
     *
     * @return
     */
    public String getApproverRemark() {
        return approverRemark;
    }

    /**
     *
     * @param approverRemark
     */
    public void setApproverRemark(String approverRemark) {
        this.approverRemark = approverRemark;
    }

    /**
     *
     * @return
     */
    public String getDates() {
        return dates;
    }

    /**
     *
     * @param dates
     */
    public void setDates(String dates) {
        this.dates = dates;
    }

    public String getPreparddate() {
        return preparddate;
    }

    /**
     *
     * @param preparddate
     */
    public void setPreparddate(String preparddate) {
        this.preparddate = preparddate;
    }

    @XmlTransient
    public List<MmsSivDetail> getMmsSivDetailList() {
        if (mmsSivDetailList == null) {
            mmsSivDetailList = new ArrayList<>();
        }
        return mmsSivDetailList;
    }

    /**
     *
     * @param mmsSivDetailList
     */
    public void setMmsSivDetailList(List<MmsSivDetail> mmsSivDetailList) {
        this.mmsSivDetailList = mmsSivDetailList;
    }

    public MmsStorereq getStoreReqId() {
        return storeReqId;
    }

    public void setStoreReqId(MmsStorereq storeReqId) {
        this.storeReqId = storeReqId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sivId != null ? sivId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MmsSiv)) {
            return false;
        }
        MmsSiv other = (MmsSiv) object;
        if ((this.sivId == null && other.sivId != null) || (this.sivId != null && !this.sivId.equals(other.sivId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return sivNo;
        // return "et.gov.eep.mms.entity.MmsSiv[ sivId=" + sivId + " ]";
    }

    public void addSivDetialInfo(MmsSivDetail sivDetail) {
        if (sivDetail.getSivId() != this) {
            this.getMmsSivDetailList().add(sivDetail);
            sivDetail.setSivId(this);
        }
    }

    public void addSivIdToWorkflow(WfMmsProcessed wfMmsProcessed) {
        if (wfMmsProcessed.getSivId() != this) {
            this.getWfMmsProcessedList().add(wfMmsProcessed);

            wfMmsProcessed.setSivId(this);
        }
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
    public List<MmsManageLocation> getMmsManageLocationList() {
        return mmsManageLocationList;
    }

    /**
     *
     * @param mmsManageLocationList
     */
    public void setMmsManageLocationList(List<MmsManageLocation> mmsManageLocationList) {
        this.mmsManageLocationList = mmsManageLocationList;
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

    /**
     *
     * @return
     */
    public Integer getStatus() {
        return Status;
    }

    /**
     *
     * @param Status
     */
    public void setStatus(Integer Status) {
        this.Status = Status;
    }

    public MmsStoreInformation getStoreId() {
        return storeId;
    }

    public void setStoreId(MmsStoreInformation storeId) {
        this.storeId = storeId;
    }

}
