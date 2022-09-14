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
import javax.persistence.OneToOne;
import javax.persistence.OrderColumn;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import et.gov.eep.commonApplications.entity.WfMmsProcessed;
import et.gov.eep.fcms.entity.admin.FmsGeneralLedger;
import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.entity.organization.HrDepartments;
import et.gov.eep.pms.entity.PmsWorkAuthorization;
import et.gov.eep.prms.entity.PrmsPurchaseRequestDet;
import javax.persistence.Transient;

/**
 *
 * @author minab
 */
@Entity
@Table(name = "MMS_STOREREQ")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MmsStorereq.findAll", query = "SELECT m FROM MmsStorereq m"),
    @NamedQuery(name = "MmsStorereq.findBySrNoAndStoreId", query = "SELECT m FROM MmsStorereq m WHERE m.srNo LIKE :srNo AND m.requestStore=:requestStore"),
    @NamedQuery(name = "MmsStorereq.findBySrNoAndStoreIdAndProcessedBy", query = "SELECT m FROM MmsStorereq m WHERE m.srNo LIKE :srNo AND m.requestStore=:requestStore AND m.processedBy=:processedBy"),
    @NamedQuery(name = "MmsStorereq.findByStoreReqId", query = "SELECT m FROM MmsStorereq m WHERE m.storeReqId = :storeReqId"),
    @NamedQuery(name = "MmsStorereq.findByRequestedBy", query = "SELECT m FROM MmsStorereq m WHERE m.requestedBy = :requestedBy"),
    @NamedQuery(name = "MmsStorereq.findSrListByWfStatus", query = "SELECT m FROM MmsStorereq m WHERE m.status=:status"),
    @NamedQuery(name = "MmsStorereq.findSrListForCheckerByWfStatus", query = "SELECT m FROM MmsStorereq m WHERE m.status=:prepared OR m.status=:approverReject"),
    @NamedQuery(name = "MmsStorereq.findByFor1", query = "SELECT m FROM MmsStorereq m WHERE m.for1 = :for1"),
    @NamedQuery(name = "MmsStorereq.findBySrNoFixedSr", query = "SELECT m FROM MmsStorereq m WHERE m.srType = 'fixed'"),
    @NamedQuery(name = "MmsStorereq.findByDepIdAndSrType", query = "SELECT m FROM MmsStorereq m WHERE m.deptId.depId=:depId"),
    @NamedQuery(name = "MmsStorereq.findByRequestedDate", query = "SELECT m FROM MmsStorereq m WHERE m.requestedDate = :requestedDate"),
    @NamedQuery(name = "MmsStorereq.findByJobNo", query = "SELECT m FROM MmsStorereq m WHERE m.jobNo = :jobNo"),
    @NamedQuery(name = "MmsStorereq.findByApprovedStatusAndStore", query = "SELECT m FROM MmsStorereq m WHERE m.status = :status AND m.requestStore.storeId=:requestStore AND m.toIsiv = NULL"),
    @NamedQuery(name = "MmsStorereq.findByApprovedStatusAndStoreForISIV", query = "SELECT m FROM MmsStorereq m WHERE m.status = :status AND m.requestStore.storeId=:requestStore AND m.toIsiv=:toIsiv"),
    @NamedQuery(name = "MmsStorereq.findByApprovedBy", query = "SELECT m FROM MmsStorereq m WHERE m.approvedBy = :approvedBy"),
    @NamedQuery(name = "MmsStorereq.findByApprovalDate", query = "SELECT m FROM MmsStorereq m WHERE m.approvalDate = :approvalDate"),
    @NamedQuery(name = "MmsStorereq.findBySrNo", query = "SELECT m FROM MmsStorereq m WHERE m.srNo = :srNo"),
    @NamedQuery(name = "MmsStorereq.findByStoreId", query = "SELECT m FROM MmsStorereq m WHERE m.requestStore = :requestStore ORDER BY m.approvalDate"),
    @NamedQuery(name = "MmsStorereq.findByStoreIdAndUserId", query = "SELECT m FROM MmsStorereq m WHERE m.requestStore = :requestStore AND m.processedBy=:processedBy ORDER BY m.approvalDate"),
    @NamedQuery(name = "MmsStorereq.findByProjectNo", query = "SELECT m FROM MmsStorereq m WHERE m.projectNo = :projectNo"),
    @NamedQuery(name = "MmsStorereq.findBySRNumberLike", query = "SELECT m FROM MmsStorereq m WHERE m.srNo LIKE :srNo"),
    @NamedQuery(name = "MmsStorereq.findBySReqId", query = "SELECT Max(m.storeReqId) from MmsStorereq m"),
    @NamedQuery(name = "MmsStorereq.findByProcessedBy", query = "SELECT m FROM MmsStorereq m WHERE m.processedBy= :processedBy"),
    @NamedQuery(name = "MmsStorereq.findByProjectName", query = "SELECT m FROM MmsStorereq m WHERE m.projectName = :projectName"),
    @NamedQuery(name = "MmsStorereq.findByContracrorName", query = "SELECT m FROM MmsStorereq m WHERE m.contracrorName = :contracrorName"),
    @NamedQuery(name = "MmsStorereq.findBySReqIdMaximum", query = "SELECT m FROM MmsStorereq m WHERE m.storeReqId = (SELECT Max(c.storeReqId)  from MmsStorereq c)"),
    @NamedQuery(name = "MmsStorereq.findByRequestStore", query = "SELECT m FROM MmsStorereq m WHERE m.requestStore = :requestStore"),
    @NamedQuery(name = "MmsStorereq.findByAssetType", query = "SELECT m FROM MmsStorereq m WHERE m.assetType = :assetType"),
    @NamedQuery(name = "MmsStorereq.findBySrType", query = "SELECT m FROM MmsStorereq m WHERE m.srType = :srType"),
    @NamedQuery(name = "MmsStorereq.findByAcceptedBy", query = "SELECT m FROM MmsStorereq m WHERE m.acceptedBy = :acceptedBy"),
    @NamedQuery(name = "MmsStorereq.findByPrepareRemark", query = "SELECT m FROM MmsStorereq m WHERE m.prepareRemark = :prepareRemark"),
    @NamedQuery(name = "MmsStorereq.findByApproverRemark", query = "SELECT m FROM MmsStorereq m WHERE m.approverRemark = :approverRemark"),
    @NamedQuery(name = "MmsStorereq.findBySrCase", query = "SELECT m FROM MmsStorereq m WHERE m.srCases = :srCase")})
public class MmsStorereq implements Serializable {

    @OneToMany(mappedBy = "stDetailId")
    private List<PrmsPurchaseRequestDet> prmsPurchaseRequestDetList;
    @OneToMany(mappedBy = "storeReqId")
    private List<MmsSiv> mmsSivList;
    @Column(name = "STATUS")
    private Integer status;

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MMS_STORE_REQUISITION_SEQ")
    @SequenceGenerator(name = "MMS_STORE_REQUISITION_SEQ", sequenceName = "MMS_STORE_REQUISITION_SEQ", allocationSize = 1)
    @Column(name = "STORE_REQ_ID")
    private Integer storeReqId;
//    @Size(max = 50)
//    @Column(name = "REQUESTED_BY")
//    private String requestedBy;
    @Size(max = 255)
    @Column(name = "FOR_1")

    private String for1;

    @Size(max = 20)
    @Column(name = "REQUESTED_DATE")

    private String requestedDate;

    @Size(max = 255)
    @Column(name = "JOB_NO")
    private String jobNo;

    @Size(max = 255)
    @Column(name = "APPROVED_BY")
    private String approvedBy;

    @Size(max = 50)
    @Column(name = "REQUESTER_NAME")
    private String requesterName;

    @Size(max = 50)
    @Column(name = "COST_CENTER")
    private String costCenter;

    @Size(max = 20)
    @Column(name = "APPROVAL_DATE")
    private String approvalDate;

    @Size(max = 20)
    @Column(name = "ACCEPTED_DATE")
    private String acceptedDate;

    @Size(max = 255)
    @Column(name = "SR_NO")
    private String srNo;

    @Size(max = 255)
    @Column(name = "PROJECT_NO")
    private String projectNo;

    @Size(max = 255)
    @Column(name = "PROJECT_NAME")
    private String projectName;

    @Size(max = 255)
    @Column(name = "CONTRACROR_NAME")
    private String contracrorName;

    @Size(max = 20)
    @Column(name = "PROCESSED_ON")
    private String processedOn;

    @Column(name = "PROCESSED_BY")
    private Integer processedBy;

    @Column(name = "TO_ISIV")
    private Integer toIsiv;

    @Size(max = 20)
    @Column(name = "ASSET_TYPE")
    private String assetType;

    @Size(max = 255)
    @Column(name = "SR_TYPE")
    private String srType;

    @Size(max = 255)
    @Column(name = "ACCEPTED_BY")
    private String acceptedBy;

    @Size(max = 250)
    @Column(name = "PREPARE_REMARK")
    private String prepareRemark;

    @Size(max = 255)
    @Column(name = "APPROVER_REMARK")
    private String approverRemark;
    @Size(max = 255)
    @Column(name = "SR_CASE")
    private String srCases;

    //<editor-fold defaultstate="collapsed" desc="Dynamic Searching Transient Parameters">
    @Transient
    private String columnName;
    @Transient
    private String columnValue;
    //</editor-fold >

    @JoinColumn(name = "REQUEST_STORE", referencedColumnName = "STORE_ID")
    @ManyToOne
    private MmsStoreInformation requestStore;

    @JoinColumn(name = "TO_STORE", referencedColumnName = "STORE_ID")
    @ManyToOne
    private MmsStoreInformation toStore;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "storeReqId")
    private List<MmsStorereqDetail> mmsStorereqDetailList;
    @OrderColumn(name = "PROCESSED_ID")
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "storeReqId")
    private List<WfMmsProcessed> wfMmsProcessedList; 
    @JoinColumn(name = "DEPARTMENT", referencedColumnName = "DEP_ID")
    @ManyToOne
    private HrDepartments deptId;

    @JoinColumn(name = "REQUESTED_BY", referencedColumnName = "ID")
    @ManyToOne
    private HrEmployees requestedBy;

    @JoinColumn(name = "JOB_ID", referencedColumnName = "WORK_AUTHO_ID")
    @ManyToOne
    private PmsWorkAuthorization jobId;
    @JoinColumn(name = "GL_ID", referencedColumnName = "GENERAL_LEDGER_ID")
    @ManyToOne
    private FmsGeneralLedger generalLedger;

    /**
     *
     */
    public MmsStorereq() {
    }

    public String getCostCenter() {
        return costCenter;
    }

    public void setCostCenter(String costCenter) {
        this.costCenter = costCenter;
    }

    public Integer getToIsiv() {
        return toIsiv;
    }

    public void setToIsiv(Integer toIsiv) {
        this.toIsiv = toIsiv;
    }

    public MmsStoreInformation getToStore() {
        return toStore;
    }

    public void setToStore(MmsStoreInformation toStore) {
        this.toStore = toStore;
    }

    /**
     *
     * @param storeReqId
     */
    public MmsStorereq(Integer storeReqId) {
        this.storeReqId = storeReqId;
    }

    public String getProcessedOn() {
        return processedOn;
    }

    public void setProcessedOn(String processedOn) {
        this.processedOn = processedOn;
    }

    public Integer getProcessedBy() {
        return processedBy;
    }

    /**
     *
     * @return
     */
    public void setProcessedBy(Integer processedBy) {
        this.processedBy = processedBy;
    }

    public Integer getStoreReqId() {
        return storeReqId;
    }

    /**
     *
     * @param storeReqId
     */
    public void setStoreReqId(Integer storeReqId) {
        this.storeReqId = storeReqId;
    }

    /**
     *
     * @return
     */
    public HrEmployees getRequestedBy() {
        return requestedBy;
    }

    /**
     *
     * @param requestedBy
     */
    public void setRequestedBy(HrEmployees requestedBy) {
        this.requestedBy = requestedBy;
    }

    /**
     *
     * @return
     */
    public String getFor1() {
        return for1;
    }

    /**
     *
     * @param for1
     */
    public void setFor1(String for1) {
        this.for1 = for1;
    }

    /**
     *
     * @return
     */
    public String getRequestedDate() {
        return requestedDate;
    }

    /**
     *
     * @param requestedDate
     */
    public void setRequestedDate(String requestedDate) {
        this.requestedDate = requestedDate;
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
    public String getApprovalDate() {
        return approvalDate;
    }

    /**
     *
     * @param approvalDate
     */
    public void setApprovalDate(String approvalDate) {
        this.approvalDate = approvalDate;
    }

    public String getAcceptedDate() {
        return acceptedDate;
    }

    public void setAcceptedDate(String acceptedDate) {
        this.acceptedDate = acceptedDate;
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

    /**
     *
     * @return
     */
    public String getProjectNo() {
        return projectNo;
    }

    /**
     *
     * @param projectNo
     */
    public void setProjectNo(String projectNo) {
        this.projectNo = projectNo;
    }

    /**
     *
     * @return
     */
    public String getProjectName() {
        return projectName;
    }

    /**
     *
     * @param projectName
     */
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    /**
     *
     * @return
     */
    public String getContracrorName() {
        return contracrorName;
    }

    /**
     *
     * @param contracrorName
     */
    public void setContracrorName(String contracrorName) {
        this.contracrorName = contracrorName;
    }

    /**
     *
     * @return
     */
    public MmsStoreInformation getRequestStore() {
        return requestStore;
    }

    /**
     *
     * @param requestStore
     */
    public void setRequestStore(MmsStoreInformation requestStore) {
        this.requestStore = requestStore;
    }

    /**
     *
     * @return
     */
    public String getAssetType() {
        return assetType;
    }

    /**
     *
     * @param assetType
     */
    public void setAssetType(String assetType) {
        this.assetType = assetType;
    }

    /**
     *
     * @return
     */
    public String getAcceptedBy() {
        return acceptedBy;
    }

    /**
     *
     * @param acceptedBy
     */
    public void setAcceptedBy(String acceptedBy) {
        this.acceptedBy = acceptedBy;
    }

    /**
     *
     * @return
     */
    public String getPrepareRemark() {
        return prepareRemark;
    }

    /**
     *
     * @param prepareRemark
     */
    public void setPrepareRemark(String prepareRemark) {
        this.prepareRemark = prepareRemark;
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

    public String getSrCases() {
        return srCases;
    }

    public void setSrCases(String srCases) {
        this.srCases = srCases;
    }

    //<editor-fold defaultstate="collapsed" desc="getter and setter of Dynamic Searching Transient variable">
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
    //</editor-fold>

//    public Integer getSelectOpt() {
//        return selectOpt;
//    }
//
//    public void setSelectOpt(Integer selectOpt) {
//        this.selectOpt = selectOpt;
//    }
    /**
     *
     * @return
     */
    @XmlTransient
    public List<MmsStorereqDetail> getMmsStorereqDetailList() {
        if (mmsStorereqDetailList == null) {
            mmsStorereqDetailList = new ArrayList();
        }
        return mmsStorereqDetailList;
    }

    /**
     *
     * @param mmsStorereqDetailList
     */
    public void setMmsStorereqDetailList(List<MmsStorereqDetail> mmsStorereqDetailList) {
        this.mmsStorereqDetailList = mmsStorereqDetailList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (storeReqId != null ? storeReqId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MmsStorereq)) {
            return false;
        }
        MmsStorereq other = (MmsStorereq) object;
        if ((this.storeReqId == null && other.storeReqId != null) || (this.storeReqId != null && !this.storeReqId.equals(other.storeReqId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return srNo;
    }

    /**
     *
     * @param storereqDetail
     */
    public void addRequisitionDetialInfo(MmsStorereqDetail storereqDetail) {
        if (storereqDetail.getStoreReqId() != this) {
            this.getMmsStorereqDetailList().add(storereqDetail);//
            storereqDetail.setStoreReqId(this);
        }
    }

    /**
     *
     * @return
     */
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getSrType() {
        return srType;
    }

    public void setSrType(String srType) {
        this.srType = srType;
    }

    @XmlTransient
    public List<MmsSiv> getMmsSivList() {
        if (mmsSivList == null) {
            mmsSivList = new ArrayList<>();
        }
        return mmsSivList;
    }

    public void setMmsSivList(List<MmsSiv> mmsSivList) {
        this.mmsSivList = mmsSivList;
    }

    public HrDepartments getDeptId() {
        return deptId;
    }

    public void setDeptId(HrDepartments deptId) {
        this.deptId = deptId;
    }

    /**
     *
     * @param status
     */
//    public void setStatus(int status) {
//        this.status = status;
//    }
    @XmlTransient

    public List<PrmsPurchaseRequestDet> getPrmsPurchaseRequestDetList() {
        return prmsPurchaseRequestDetList;
    }

    public void setPrmsPurchaseRequestDetList(List<PrmsPurchaseRequestDet> prmsPurchaseRequestDetList) {
        this.prmsPurchaseRequestDetList = prmsPurchaseRequestDetList;
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

    public void addRequisitionIdToWorkflow(WfMmsProcessed wfMmsProcessed) {
        if (wfMmsProcessed.getStoreReqId() != this) {
            this.getWfMmsProcessedList().add(wfMmsProcessed);

            wfMmsProcessed.setStoreReqId(this);
        }
    }

    public String getRequesterName() {
        return requesterName;
    }

    public void setRequesterName(String requesterName) {
        this.requesterName = requesterName;
    }

    public FmsGeneralLedger getGeneralLedger() {
        return generalLedger;
    }

    public void setGeneralLedger(FmsGeneralLedger generalLedger) {
        this.generalLedger = generalLedger;
    }

    public PmsWorkAuthorization getJobId() {
        return jobId;
    }

    public void setJobId(PmsWorkAuthorization jobId) {
        this.jobId = jobId;
    }

}
