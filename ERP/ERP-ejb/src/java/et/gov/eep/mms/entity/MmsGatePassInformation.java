/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.mms.entity;

import et.gov.eep.commonApplications.entity.WfMmsProcessed;
import et.gov.eep.hrms.entity.organization.HrDepartments;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Sadik
 */
@Entity
@Table(name = "MMS_GATE_PASS_INFORMATION")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MmsGatePassInformation.findAll", query = "SELECT m FROM MmsGatePassInformation m"),
    @NamedQuery(name = "MmsGatePassInformation.findByGatePassId", query = "SELECT m FROM MmsGatePassInformation m WHERE m.gatePassId = :gatePassId"),
    @NamedQuery(name = "MmsGatePassInformation.findByGatePassNo", query = "SELECT m FROM MmsGatePassInformation m WHERE m.gatePassNo = :gatePassNo"),
//    @NamedQuery(name = "MmsGatePassInformation.findBySivNo", query = "SELECT m FROM MmsGatePassInformation m WHERE m.sivNo = :sivNo"),
//    @NamedQuery(name = "MmsGatePassInformation.findByIsivNo", query = "SELECT m FROM MmsGatePassInformation m WHERE m.isivNo = :isivNo"),
    @NamedQuery(name = "MmsGatePassInformation.findByRequsitionDate", query = "SELECT m FROM MmsGatePassInformation m WHERE m.requsitionDate = :requsitionDate"),
    @NamedQuery(name = "MmsGatePassInformation.findByTypeOfTransfer", query = "SELECT m FROM MmsGatePassInformation m WHERE m.typeOfTransfer = :typeOfTransfer"),
    @NamedQuery(name = "MmsGatePassInformation.findByModeOfTransport", query = "SELECT m FROM MmsGatePassInformation m WHERE m.modeOfTransport = :modeOfTransport"),
    @NamedQuery(name = "MmsGatePassInformation.findByPlateNo", query = "SELECT m FROM MmsGatePassInformation m WHERE m.plateNo = :plateNo"),
    @NamedQuery(name = "MmsGatePassInformation.findBySecurityOfficer", query = "SELECT m FROM MmsGatePassInformation m WHERE m.securityOfficer = :securityOfficer"),
    @NamedQuery(name = "MmsGatePassInformation.findByOfficerRemark", query = "SELECT m FROM MmsGatePassInformation m WHERE m.officerRemark = :officerRemark"),
    @NamedQuery(name = "MmsGatePassInformation.findByGatePassStatus", query = "SELECT m FROM MmsGatePassInformation m WHERE m.gatePassStatus = :gatePassStatus"),
    @NamedQuery(name = "MmsGatePassInformation.findByRequestedBy", query = "SELECT m FROM MmsGatePassInformation m WHERE m.requestedBy = :requestedBy"),
    @NamedQuery(name = "MmsGatePassInformation.findByRequesterRemark", query = "SELECT m FROM MmsGatePassInformation m WHERE m.requesterRemark = :requesterRemark"),
    @NamedQuery(name = "MmsGatePassInformation.findByApprovedBy", query = "SELECT m FROM MmsGatePassInformation m WHERE m.approvedBy = :approvedBy"),
    @NamedQuery(name = "MmsGatePassInformation.findByApprovedDate", query = "SELECT m FROM MmsGatePassInformation m WHERE m.approvedDate = :approvedDate"),
    @NamedQuery(name = "MmsGatePassInformation.findByApproverRemark", query = "SELECT m FROM MmsGatePassInformation m WHERE m.approverRemark = :approverRemark"),
    @NamedQuery(name = "MmsGatePassInformation.findByRecievedBy", query = "SELECT m FROM MmsGatePassInformation m WHERE m.recievedBy = :recievedBy"),
    @NamedQuery(name = "MmsGatePassInformation.findByAcceptedBy", query = "SELECT m FROM MmsGatePassInformation m WHERE m.acceptedBy = :acceptedBy"),
    @NamedQuery(name = "MmsGatePassInformation.findByAllParameters", query = "SELECT m FROM MmsGatePassInformation m WHERE m.gatePassNo LIKE :gatePassNo"),
    @NamedQuery(name = "MmsGatePassInformation.findByProcessedBy", query = "SELECT m FROM MmsGatePassInformation m WHERE m.processedBy =:processedBy"),
    @NamedQuery(name = "MmsGatePassInformation.findByAllParametersAndProcessedBy", query = "SELECT m FROM MmsGatePassInformation m WHERE m.gatePassNo LIKE :gatePassNo AND m.processedBy=:processedBy "),

    @NamedQuery(name = "MmsGatePassInformation.findByGatepassIdMaximum", query = "SELECT m FROM MmsGatePassInformation m WHERE m.gatePassId = (SELECT Max(c.gatePassId)  from MmsGatePassInformation c)"),
    @NamedQuery(name = "MmsGatePassInformation.findByMovementType", query = "SELECT m FROM MmsGatePassInformation m WHERE m.movementType = :movementType")})
public class MmsGatePassInformation implements Serializable {

    @Column(name = "STATUS")
    private Integer status;
    @Size(max = 20)
    @Column(name = "RECIEVED_BY", length = 20)
    private String recievedBy;
    @Size(max = 50)
    @Column(name = "DRIVER_NAME", length = 50)
    private String driverName;
    @Size(max = 50)
    @Column(name = "LABOR_NAMES", length = 50)
    private String laborNames;
    @Size(max = 50)
    @Column(name = "RECIEVED_DATE")

    private String recievedDate;
    @Size(max = 50)
    @Column(name = "ACCEPTED_DATE")

    private String acceptedDate;
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MMS_GATE_PASS_INFO_SEQ")
    @SequenceGenerator(name = "MMS_GATE_PASS_INFO_SEQ", sequenceName = "MMS_GATE_PASS_INFO_SEQ", allocationSize = 1)

    @Column(name = "GATE_PASS_ID", nullable = false, precision = 0, scale = -127)
    private Integer gatePassId;
    @Basic(optional = false)

    @Column(name = "GATE_PASS_NO")
    private String gatePassNo;

    @Size(max = 20)
    @Column(name = "REQUSITION_DATE", nullable = false)

    private String requsitionDate;

    @Size(max = 50)
    @Column(name = "TYPE_OF_TRANSFER", length = 50)
    private String typeOfTransfer;

    @Size(max = 20)
    @Column(name = "MODE_OF_TRANSPORT", length = 20)
    private String modeOfTransport;
    @Size(max = 20)
    @Column(name = "PLATE_NO", length = 20)
    private String plateNo;

    @Size(max = 20)
    @Column(name = "SECURITY_OFFICER", length = 20)
    private String securityOfficer;
    @Size(max = 50)
    @Column(name = "OFFICER_REMARK", length = 50)
    private String officerRemark;
    @Size(max = 20)
    @Column(name = "GATE_PASS_STATUS", length = 20)
    private String gatePassStatus;

    @Size(max = 20)
    @Column(name = "REQUESTED_BY", length = 20)
    private String requestedBy;
    @Size(max = 50)
    @Column(name = "REQUESTER_REMARK", length = 50)
    private String requesterRemark;

    @Size(max = 50)
    @Column(name = "APPROVED_BY", length = 50)
    private String approvedBy;
    @Size(max = 20)
    @Column(name = "APPROVED_DATE")

    private String approvedDate;
    @Size(max = 50)
    @Column(name = "APPROVER_REMARK", length = 50)
    private String approverRemark;
    //<editor-fold defaultstate="collapsed" desc="Dynamic Searching Transient Parameters">
    @Transient
    private String columnName;
    @Transient
    private String columnValue;
    //</editor-fold >
//    @Size(max = 20)
//    @Column(name = "DELIVERED_BY", length = 20)
//    private String deliveredBy;
    @Column(name = "PROCESSED_BY")
    private Integer processedBy;
    @Size(max = 20)
    @Column(name = "COMMENT_GIVEN")
    private String commentGiven;
    @Size(max = 20)
    @Column(name = "PROCESSED_ON")
    private String processedOn;
    @Size(max = 20)
    @Column(name = "ACCEPTED_BY", length = 20)
    private String acceptedBy;
    @Size(max = 20)
    @Column(name = "MOVEMENT_TYPE", length = 20)
    private String movementType;
    @JoinColumn(name = "DEPARTMENT", referencedColumnName = "DEP_ID")
    @ManyToOne
    private HrDepartments department;

    @JoinColumn(name = "ISIV_ID", referencedColumnName = "TRANSFER_ID")
    @ManyToOne
    private MmsIsiv isivId;

    @JoinColumn(name = "SIV_ID", referencedColumnName = "SIV_ID")
    @ManyToOne
    private MmsSiv sivId;
    @JoinColumn(name = "STORE_ID", referencedColumnName = "STORE_ID")
    @ManyToOne
    private MmsStoreInformation storeId;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "gatePassId", fetch = FetchType.LAZY)
    private List<MmsGatePassInfoDtl> mmsGatePassInfoDtlList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "gatePassId")
    private List<WfMmsProcessed> wfMmsProcessedList;

    public MmsGatePassInformation() {
    }

    public MmsGatePassInformation(Integer gatePassId) {
        this.gatePassId = gatePassId;
    }

    public MmsGatePassInformation(Integer gatePassId, String gatePassNo, String requsitionDate) {
        this.gatePassId = gatePassId;
        this.gatePassNo = gatePassNo;
        this.requsitionDate = requsitionDate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public Integer getGatePassId() {
        return gatePassId;
    }

    public void setGatePassId(Integer gatePassId) {
        this.gatePassId = gatePassId;
    }

    public String getGatePassNo() {

        return gatePassNo;
    }

    public void setGatePassNo(String gatePassNo) {
        this.gatePassNo = gatePassNo;
    }

    public String getRequsitionDate() {
        return requsitionDate;
    }

    public void setRequsitionDate(String requsitionDate) {
        this.requsitionDate = requsitionDate;
    }

    public String getTypeOfTransfer() {
        return typeOfTransfer;
    }

    public void setTypeOfTransfer(String typeOfTransfer) {
        this.typeOfTransfer = typeOfTransfer;
    }

    public String getModeOfTransport() {
        return modeOfTransport;
    }

    public void setModeOfTransport(String modeOfTransport) {
        this.modeOfTransport = modeOfTransport;
    }

    public String getPlateNo() {
        return plateNo;
    }

    public void setPlateNo(String plateNo) {
        this.plateNo = plateNo;
    }

    public String getSecurityOfficer() {
        return securityOfficer;
    }

    public void setSecurityOfficer(String securityOfficer) {
        this.securityOfficer = securityOfficer;
    }

    public String getOfficerRemark() {
        return officerRemark;
    }

    public void setOfficerRemark(String officerRemark) {
        this.officerRemark = officerRemark;
    }

    public String getGatePassStatus() {
        return gatePassStatus;
    }

    public void setGatePassStatus(String gatePassStatus) {
        this.gatePassStatus = gatePassStatus;
    }

    public String getRequestedBy() {
        return requestedBy;
    }

    public void setRequestedBy(String requestedBy) {
        this.requestedBy = requestedBy;
    }

    public String getRequesterRemark() {
        return requesterRemark;
    }

    public void setRequesterRemark(String requesterRemark) {
        this.requesterRemark = requesterRemark;
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

    public String getApproverRemark() {
        return approverRemark;
    }

    public void setApproverRemark(String approverRemark) {
        this.approverRemark = approverRemark;
    }

//    public String getDeliveredBy() {
//        return deliveredBy;
//    }
//
//    public void setDeliveredBy(String deliveredBy) {
//        this.deliveredBy = deliveredBy;
//    }
    public String getAcceptedBy() {
        return acceptedBy;
    }

    public void setAcceptedBy(String acceptedBy) {
        this.acceptedBy = acceptedBy;
    }

    public String getMovementType() {
        return movementType;
    }

    public void setMovementType(String movementType) {
        this.movementType = movementType;
    }

    public HrDepartments getDepartment() {
        return department;
    }

    public void setDepartment(HrDepartments department) {
        this.department = department;
    }

    @XmlTransient
    public List<MmsGatePassInfoDtl> getMmsGatePassInfoDtlList() {
        if (mmsGatePassInfoDtlList == null) {
            mmsGatePassInfoDtlList = new ArrayList<>();
        }
        return mmsGatePassInfoDtlList;
    }

    public void setMmsGatePassInfoDtlList(List<MmsGatePassInfoDtl> mmsGatePassInfoDtlList) {
        this.mmsGatePassInfoDtlList = mmsGatePassInfoDtlList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (gatePassId != null ? gatePassId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MmsGatePassInformation)) {
            return false;
        }
        MmsGatePassInformation other = (MmsGatePassInformation) object;
        if ((this.gatePassId == null && other.gatePassId != null) || (this.gatePassId != null && !this.gatePassId.equals(other.gatePassId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.eep.mms.entity.MmsGatePassInformation[ gatePassId=" + gatePassId + " ]";
    }

    public void addTOGatePassInfoDetail(MmsGatePassInfoDtl gatePassDtlEntity) {
        if (gatePassDtlEntity.getGatePassId() != this) {
            this.getMmsGatePassInfoDtlList().add(gatePassDtlEntity);
            gatePassDtlEntity.setGatePassId(this);
        }

    }

    public void addSivInfoDetail(MmsGatePassInfoDtl gatePassDtlEntity) {

        if (gatePassDtlEntity.getGatePassId() != this) {

            this.getMmsGatePassInfoDtlList().add(gatePassDtlEntity);
            gatePassDtlEntity.setGatePassId(this);
        }

    }

    public void addISivInfoDetail(MmsGatePassInfoDtl gatePassDtlEntity) {

        if (gatePassDtlEntity.getGatePassId() != this) {

            this.getMmsGatePassInfoDtlList().add(gatePassDtlEntity);
            gatePassDtlEntity.setGatePassId(this);
        }

    }

    public String getRecievedBy() {
        return recievedBy;
    }

    public void setRecievedBy(String recievedBy) {
        this.recievedBy = recievedBy;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getLaborNames() {
        return laborNames;
    }

    public void setLaborNames(String laborNames) {
        this.laborNames = laborNames;
    }

    public String getRecievedDate() {
        return recievedDate;
    }

    public void setRecievedDate(String recievedDate) {
        this.recievedDate = recievedDate;
    }

    public String getAcceptedDate() {
        return acceptedDate;
    }

    public void setAcceptedDate(String acceptedDate) {
        this.acceptedDate = acceptedDate;
    }

    public MmsIsiv getIsivId() {
        return isivId;
    }

    public void setIsivId(MmsIsiv isivId) {
        this.isivId = isivId;
    }

    public MmsSiv getSivId() {
        return sivId;
    }

    public void setSivId(MmsSiv sivId) {
        this.sivId = sivId;
    }

    public MmsStoreInformation getStoreId() {
        return storeId;
    }

    public void setStoreId(MmsStoreInformation storeId) {
        this.storeId = storeId;
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
    public List<WfMmsProcessed> getWfMmsProcessedList() {

        if (wfMmsProcessedList == null) {
            wfMmsProcessedList = new ArrayList<>();
        }
        return wfMmsProcessedList;
    }

    public void setWfMmsProcessedList(List<WfMmsProcessed> wfMmsProcessedList) {
        this.wfMmsProcessedList = wfMmsProcessedList;
    }

}
