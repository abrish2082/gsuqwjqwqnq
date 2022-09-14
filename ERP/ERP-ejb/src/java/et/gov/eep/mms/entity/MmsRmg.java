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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
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
@Table(name = "MMS_RMG" )
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MmsRmg.findAll", query = "SELECT m FROM MmsRmg m"),
    @NamedQuery(name = "MmsRmg.findByRmgId", query = "SELECT m FROM MmsRmg m WHERE m.rmgId = :rmgId"),
    @NamedQuery(name = "MmsRmg.findByRmgNo", query = "SELECT m FROM MmsRmg m WHERE m.rmgNo = :rmgNo"),
    @NamedQuery(name = "MmsRmg.findByJobNo", query = "SELECT m FROM MmsRmg m WHERE m.jobNo = :jobNo"),
    @NamedQuery(name = "MmsRmg.findByWrNo", query = "SELECT m FROM MmsRmg m WHERE m.wrNo = :wrNo"),
    @NamedQuery(name = "MmsRmg.findByAccountNo", query = "SELECT m FROM MmsRmg m WHERE m.accountNo = :accountNo"),
    @NamedQuery(name = "MmsRmg.findByWorkshop", query = "SELECT m FROM MmsRmg m WHERE m.workshop = :workshop"),
    @NamedQuery(name = "MmsRmg.findByprocessedBy", query = "SELECT m FROM MmsRmg m WHERE m.processedBy = :processedBy"),
    @NamedQuery(name = "MmsRmg.findByReceivingStore", query = "SELECT m FROM MmsRmg m WHERE m.recevingStore = :receivingStore"),
    @NamedQuery(name = "MmsRmg.findByDates", query = "SELECT m FROM MmsRmg m WHERE m.dates = :dates"),
    @NamedQuery(name = "MmsRmg.findByrmgNo", query = "SELECT m FROM MmsRmg m WHERE m.rmgNo LIKE :rmgNo"),
    @NamedQuery(name = "MmsRmg.findByrmgNoAndProcessedBy", query = "SELECT m FROM MmsRmg m WHERE m.rmgNo LIKE :rmgNo and m.processedBy=:processedBy"),
    @NamedQuery(name = "MmsRmg.findmaximumId", query = "SELECT m FROM MmsRmg m WHERE m.rmgId = (SELECT Max(c.rmgId)  from MmsRmg c)"),
    @NamedQuery(name = "MmsRmg.findByDeliveredBy", query = "SELECT m FROM MmsRmg m WHERE m.deliveredBy = :deliveredBy"),
    @NamedQuery(name = "MmsRmg.findByReceivedBy", query = "SELECT m FROM MmsRmg m WHERE m.receivedBy = :receivedBy"),
    @NamedQuery(name = "MmsRmg.findIdMaximum", query = "SELECT m FROM MmsRmg m WHERE m.rmgId = (SELECT Max(c.rmgId)  from MmsRmg c)"),
    @NamedQuery(name = "MmsRmg.findByApprovedBy", query = "SELECT m FROM MmsRmg m WHERE m.approvedBy = :approvedBy"),
    @NamedQuery(name = "MmsRmg.findByApprovedStatus", query = "SELECT m FROM MmsRmg m WHERE m.approvedStatus = :approvedStatus"),
    @NamedQuery(name = "MmsRmg.findByStatusAndStore", query = "SELECT m FROM MmsRmg m WHERE m.status = :status AND m.recevingStore=:recevingStore"),
    @NamedQuery(name = "MmsRmg.findByStatusPrepared", query = "SELECt m FROM MmsRmg m WHERE m.status = 0"),
    @NamedQuery(name = "MmsRmg.findByStatus", query = "SELECt m FROM MmsRmg m WHERE m.status = :status"),
    @NamedQuery(name = "MmsRmg.findRmgListForCheckerByWfStatus", query = "SELECT m FROM MmsRmg m WHERE m.status=:prepared OR m.status=:approverReject"),
    @NamedQuery(name = "MmsRmg.findByWareHouse", query = "SELECT m FROM MmsRmg m WHERE m.wareHouse = :wareHouse")})
public class MmsRmg implements Serializable {

    @Size(max = 255)
    @Column(name = "INSPECTION_RESULT_NO")
    private String inspectionResultNo;

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MMS_RMG_SEQ")
    @SequenceGenerator(name = "MMS_RMG_SEQ", sequenceName = "MMS_RMG_SEQ", allocationSize = 1)
    @Column(name = "RMG_ID")
    private Integer rmgId;
    @Column(name = "STATUS")
    private Integer status;
    @Size(max = 255)
    @Column(name = "RMG_NO")
    private String rmgNo;
    @Size(max = 255)
    @Column(name = "JOB_NO")
    private String jobNo;
    @Size(max = 255)
    @Column(name = "WR_NO")
    private String wrNo;
    @Size(max = 255)
    @Column(name = "ACCOUNT_NO")
    private String accountNo;
    @Size(max = 50)
    @Column(name = "WORKSHOP")
    private String workshop;
    @Column(name = "PROCESSED_ON")
    private String processedOn;
    @Column(name = "COMMENT_GIVEN")
    private String commentGiven;

    @Column(name = "PROCESSED_BY")
    private Integer processedBy;
//<editor-fold defaultstate="collapsed" desc="Dynamic Searching Transient Parameters">
    @Transient
    private String columnName;
    @Transient
    private String columnValue;
    //</editor-fold >
    @JoinColumn(name = "RECEIVING_STORE", referencedColumnName = "STORE_ID")
    @ManyToOne
    private MmsStoreInformation recevingStore;

    @JoinColumn(name = "INSPECTION_ID", referencedColumnName = "INSPECTION_ID")
    @ManyToOne
    private MmsInspection inspectionId;

    @Size(max = 20)
    @Column(name = "DATES")

    private String dates;
    @Size(max = 255)
    @Column(name = "DELIVERED_BY")
    private String deliveredBy;
    @Size(max = 255)
    @Column(name = "RECEIVED_BY")
    private String receivedBy;
    @Size(max = 20)
    @Column(name = "APPROVED_BY")
    private String approvedBy;
    @Size(max = 50)
    @Column(name = "WARE_HOUSE")
    private String wareHouse;
    @Column(name = "APPROVED_STATUS")
    private Integer approvedStatus;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "rmgId")
    private List<MmsRmgDetail> mmsRmgDetailList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "rmgId")
    private List<WfMmsProcessed> wfMmsProcessedList;

    public Integer getProcessedBy() {
        return processedBy;
    }

    public void setProcessedBy(Integer processedBy) {
        this.processedBy = processedBy;
    }

    public String getProcessedOn() {
        return processedOn;
    }

    public void setProcessedOn(String processedOn) {
        this.processedOn = processedOn;
    }

    public String getCommentGiven() {
        return commentGiven;
    }

    public void setCommentGiven(String commentGiven) {
        this.commentGiven = commentGiven;
    }

    /**
     *
     */
    public MmsRmg() {
    }

    /**
     *
     * @param rmgId
     */
    public MmsRmg(Integer rmgId) {
        this.rmgId = rmgId;
    }

    /**
     *
     * @return
     */
    public Integer getRmgId() {
        return rmgId;
    }

    /**
     *
     * @param rmgId
     */
    public void setRmgId(Integer rmgId) {
        this.rmgId = rmgId;
    }

    /**
     *
     * @return
     */
    public String getRmgNo() {
        return rmgNo;
    }

    /**
     *
     * @param rmgNo
     */
    public void setRmgNo(String rmgNo) {
        this.rmgNo = rmgNo;
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
    public String getWrNo() {
        return wrNo;
    }

    /**
     *
     * @param wrNo
     */
    public void setWrNo(String wrNo) {
        this.wrNo = wrNo;
    }

    /**
     *
     * @return
     */
    public String getAccountNo() {
        return accountNo;
    }

    /**
     *
     * @param accountNo
     */
    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    /**
     *
     * @return
     */
    public String getWorkshop() {
        return workshop;
    }

    /**
     *
     * @param workshop
     */
    public void setWorkshop(String workshop) {
        this.workshop = workshop;
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

    /**
     *
     * @return
     */
    public String getDeliveredBy() {
        return deliveredBy;
    }

    /**
     *
     * @param deliveredBy
     */
    public void setDeliveredBy(String deliveredBy) {
        this.deliveredBy = deliveredBy;
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
    public String getWareHouse() {
        return wareHouse;
    }

    /**
     *
     * @param wareHouse
     */
    public void setWareHouse(String wareHouse) {
        this.wareHouse = wareHouse;
    }

    public MmsInspection getInspectionId() {
        return inspectionId;
    }

    public void setInspectionId(MmsInspection inspectionId) {
        this.inspectionId = inspectionId;
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
    @XmlTransient
    public List<MmsRmgDetail> getMmsRmgDetailList() {
        if (mmsRmgDetailList == null) {
            mmsRmgDetailList = new ArrayList<>();
        }
        return mmsRmgDetailList;
    }

    /**
     *
     * @param mmsRmgDetailList
     */
    public void setMmsRmgDetailList(List<MmsRmgDetail> mmsRmgDetailList) {
        this.mmsRmgDetailList = mmsRmgDetailList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (rmgId != null ? rmgId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MmsRmg)) {
            return false;
        }
        MmsRmg other = (MmsRmg) object;
        if ((this.rmgId == null && other.rmgId != null) || (this.rmgId != null && !this.rmgId.equals(other.rmgId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
//        return rmgNo;
        return rmgId.toString();

    }

    /**
     *
     * @param rmgDetail
     */
    public void addRmgDetialInfo(MmsRmgDetail rmgDetail) {
        if (rmgDetail.getRmgId() != this) {
            this.getMmsRmgDetailList().add(rmgDetail);
            rmgDetail.setRmgId(this);
        }
    }

    /**
     *
     * @return
     */
    public String getInspectionResultNo() {
        return inspectionResultNo;
    }

    /**
     *
     * @param inspectionResultNo
     */
    public void setInspectionResultNo(String inspectionResultNo) {
        this.inspectionResultNo = inspectionResultNo;
    }

    public Integer getApprovedStatus() {
        return approvedStatus;
    }

    public void setApprovedStatus(Integer approvedStatus) {
        this.approvedStatus = approvedStatus;
    }

    public MmsStoreInformation getRecevingStore() {
        return recevingStore;
    }

    public void setRecevingStore(MmsStoreInformation recevingStore) {
        this.recevingStore = recevingStore;
    }

    public void addRMGDetialInfo(MmsRmgDetail rmgDetail) {
        if (rmgDetail.getRmgId() != this) {
            this.getMmsRmgDetailList().add(rmgDetail);
            rmgDetail.setRmgId(this);

        }
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
     * @return the status
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    public void addRMGIdToWorkflow(WfMmsProcessed wfMmsProcessed) {
        if (wfMmsProcessed.getRmgId() != this) {
            this.getWfMmsProcessedList().add(wfMmsProcessed);

            wfMmsProcessed.setRmgId(this);
        }
    }
}
