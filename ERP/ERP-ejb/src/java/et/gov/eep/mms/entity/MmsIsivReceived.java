/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.mms.entity;

import java.io.Serializable;
import java.math.BigDecimal;
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
@Table(name = "MMS_ISIV_RECEIVED")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MmsIsivReceived.findAll", query = "SELECT m FROM MmsIsivReceived m"),
    @NamedQuery(name = "MmsIsivReceived.findByApprovedStatusAndStoreId", query = "SELECT m FROM MmsIsivReceived m WHERE m.Status = :Status AND m.receivingStoreId.storeId = :toStore"),
    @NamedQuery(name = "MmsIsivReceived.findByReceivingStore", query = "SELECT m FROM MmsIsivReceived m WHERE m.receivingStoreId = :receivingStoreId"),
    @NamedQuery(name = "MmsIsivReceived.findByReceivingStoreAndProcessedBy", query = "SELECT m FROM MmsIsivReceived m WHERE m.receivingStoreId = :receivingStoreId  AND m.processedBy=:processedBy"),
    @NamedQuery(name = "MmsIsivReceived.findByIsivNoAndReceivingStoreId", query = "SELECT m FROM MmsIsivReceived m WHERE m.receivingStoreId = :receivingStoreId AND m.receivingTransferNo LIKE :receivingTransferNo"),
    @NamedQuery(name = "MmsIsivReceived.findByIsivNoAndReceivingStoreIdAndProcessedBy", query = "SELECT m FROM MmsIsivReceived m WHERE m.receivingStoreId = :receivingStoreId AND m.receivingTransferNo LIKE :receivingTransferNo AND m.processedBy=:processedBy"),
    @NamedQuery(name = "MmsIsivReceived.findByIsivIdMaximum", query = "SELECT m FROM MmsIsivReceived m WHERE m.recievingId = (SELECT Max(c.recievingId)  from MmsIsivReceived c)"),
    @NamedQuery(name = "MmsIsivReceived.findByProcessedBy", query = "SELECT m FROM MmsIsivReceived m WHERE  m.processedBy =:processedBy"),
    @NamedQuery(name = "MmsIsivReceived.findByStatus", query = "SELECT m FROM MmsIsivReceived m WHERE m.Status = :Status"),
    @NamedQuery(name = "MmsIsivReceived.findAllByPreparerId", query = "SELECT m FROM MmsIsivReceived m WHERE m.processedBy=:processedBy"),
    @NamedQuery(name = "MmsIsivReceived.findByRecievingId", query = "SELECT m FROM MmsIsivReceived m WHERE m.recievingId = :recievingId")})
public class MmsIsivReceived implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MMS_ISIV_RECEIVED_SEQ")
    @SequenceGenerator(name = "MMS_ISIV_RECEIVED_SEQ", sequenceName = "MMS_ISIV_RECEIVED_SEQ", allocationSize = 1)
    @Column(name = "RECIEVING_ID", nullable = false, precision = 0, scale = -127)
    private Integer recievingId;
    @JoinColumn(name = "TRANSFER_ID", referencedColumnName = "TRANSFER_ID")
    @ManyToOne
    private MmsIsiv transferId;
    @JoinColumn(name = "RECEIVING_STORE_ID", referencedColumnName = "STORE_ID")
    @ManyToOne
    private MmsStoreInformation receivingStoreId;
    @JoinColumn(name = "ISSUING_STORE_ID", referencedColumnName = "STORE_ID")
    @ManyToOne
    private MmsStoreInformation issuingStoreId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "receivingId")
    private List<MmsIsivReceivedDtl> mmsIsivReceivedDtlList;
    @OrderColumn(name = "PROCESSED_ID")
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "recievingId")
    private List<WfMmsProcessed> wfMmsProcessedList;
//    @Size(max = 20)
    @Column(name = "PROCESSED_BY")
    private Integer processedBy;
//    @Size(max = 50)
    @Column(name = "STATUS")
    private Integer Status;
    @Column(name = "COMMENT_GIVEN")
    private String commentGiven;
    @Size(max = 20)
    @Column(name = "PROCESSED_ON")
    private String processedOn;
    @Size(max = 50)
    @Column(name = "RECEIVED_BY")
    private String receivedBy;
    @Size(max = 50)
    @Column(name = "REMARK")
    private String remark;
    @Size(max = 20)
    @Column(name = "RECEIVED_DATE")
    private String receivedDate;
    @Size(max = 50)
    @Column(name = "APPROVED_BY")
    private String approvedBy;
    @Size(max = 20)
    @Column(name = "APPROVED_DATE")
    private String approvedDate;
    @Size(max = 100)
    @Column(name = "RECEIVING_TRANSFER_NO")
    private String receivingTransferNo;
    @Column(name = "APPROVED_STATUS")
    private Integer approvedStatus;
    //<editor-fold defaultstate="collapsed" desc="Dynamic Searching Transient Parameters">
    @Transient
    private String columnName;
    @Transient
    private String columnValue;

    //</editor-fold >
    public MmsIsivReceived() {
    }

    public Integer getStatus() {
        return Status;
    }

    public void setStatus(Integer Status) {
        this.Status = Status;
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

    public String getProcessedOn() {
        return processedOn;
    }

    public void setProcessedOn(String processedOn) {
        this.processedOn = processedOn;
    }

    public MmsIsivReceived(Integer recievingId) {
        this.recievingId = recievingId;
    }

    public Integer getRecievingId() {
        return recievingId;
    }

    public void setRecievingId(Integer recievingId) {
        this.recievingId = recievingId;
    }

    public MmsIsiv getTransferId() {
        return transferId;
    }

    public void setTransferId(MmsIsiv transferId) {
        this.transferId = transferId;
    }

    public MmsStoreInformation getReceivingStoreId() {
        return receivingStoreId;
    }

    public void setReceivingStoreId(MmsStoreInformation receivingStoreId) {
        this.receivingStoreId = receivingStoreId;
    }

    public MmsStoreInformation getIssuingStoreId() {
        return issuingStoreId;
    }

    public void setIssuingStoreId(MmsStoreInformation issuingStoreId) {
        this.issuingStoreId = issuingStoreId;
    }

    public String getReceivedBy() {
        return receivedBy;
    }

    public void setReceivedBy(String receivedBy) {
        this.receivedBy = receivedBy;
    }

    public String getReceivedDate() {
        return receivedDate;
    }

    public void setReceivedDate(String receivedDate) {
        this.receivedDate = receivedDate;
    }

    public String getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(String approvedBy) {
        this.approvedBy = approvedBy;
    }

    public String getApprovedDate() {
        return approvedDate;
    }

    public void setApprovedDate(String approvedDate) {
        this.approvedDate = approvedDate;
    }

    public String getReceivingTransferNo() {
        return receivingTransferNo;
    }

    public void setReceivingTransferNo(String receivingTransferNo) {
        this.receivingTransferNo = receivingTransferNo;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @XmlTransient
    public List<MmsIsivReceivedDtl> getMmsIsivReceivedDtlList() {
        if (mmsIsivReceivedDtlList == null) {
            mmsIsivReceivedDtlList = new ArrayList<>();
        }
        return mmsIsivReceivedDtlList;
    }

    public void setMmsIsivReceivedDtlList(List<MmsIsivReceivedDtl> mmsIsivReceivedDtlList) {
        this.mmsIsivReceivedDtlList = mmsIsivReceivedDtlList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (recievingId != null ? recievingId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MmsIsivReceived)) {
            return false;
        }
        MmsIsivReceived other = (MmsIsivReceived) object;
        if ((this.recievingId == null && other.recievingId != null) || (this.recievingId != null && !this.recievingId.equals(other.recievingId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return receivingTransferNo;
//        return "et.gov.eep.mms.entity.MmsIsivReceived[ recievingId=" + recievingId + " ]";
    }

    public void addIsivDetialInfo(MmsIsivReceivedDtl isivDetail) {
        if (isivDetail.getReceivingId() != this) {
            this.getMmsIsivReceivedDtlList().add(isivDetail);
            isivDetail.setReceivingId(this);

        }
    }

    public Integer getApprovedStatus() {
        return approvedStatus;
    }

    public void setApprovedStatus(Integer approvedStatus) {
        this.approvedStatus = approvedStatus;
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

    public void addIsivRecivedIdToWorkflow(WfMmsProcessed wfMmsProcessed) {
        if (wfMmsProcessed.getRecievingId() != this) {
            this.getWfMmsProcessedList().add(wfMmsProcessed);

            wfMmsProcessed.setRecievingId(this);
        }
    }
}
