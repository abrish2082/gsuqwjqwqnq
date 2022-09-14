
package et.gov.eep.mms.entity;

import et.gov.eep.commonApplications.entity.WfMmsProcessed;
import et.gov.eep.fcms.entity.fixedasset.FmsDisposedItems;
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
@Table(name = "MMS_DISPOSAL_ITEMS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MmsDisposalItems.findAll", query = "SELECT m FROM MmsDisposalItems m"),
    @NamedQuery(name = "MmsDisposalItems.findByDisposalId", query = "SELECT m FROM MmsDisposalItems m WHERE m.disposalId = :disposalId"),
    @NamedQuery(name = "MmsDisposalItems.findByApprovalDate", query = "SELECT m FROM MmsDisposalItems m WHERE m.approvalDate = :approvalDate"),
    @NamedQuery(name = "MmsDisposalItems.findByApprovedBy", query = "SELECT m FROM MmsDisposalItems m WHERE m.approvedBy = :approvedBy"),
    @NamedQuery(name = "MmsDisposalItems.findByDepartment", query = "SELECT m FROM MmsDisposalItems m WHERE m.department = :department"),
    @NamedQuery(name = "MmsDisposalItems.findByDisposalNoLike", query = "SELECT m FROM MmsDisposalItems m WHERE m.disposalNo LIKE :disposalNo"),
    @NamedQuery(name = "MmsDisposalItems.findByDisposalIdMaximum", query = "SELECT m FROM MmsDisposalItems m WHERE m.disposalId = (SELECT Max(c.disposalId) FROM MmsDisposalItems c)"),  
    @NamedQuery(name = "MmsDisposalItems.findAllByPreparerId", query = "SELECT m FROM MmsDisposalItems m WHERE m.requestedBy = :requestedBy"),
    @NamedQuery(name = "MmsDisposalItems.findDispListByWfStatus", query = "SELECT m FROM  MmsDisposalItems m WHERE m.dStatus = :dStatus"),
    @NamedQuery(name = "MmsDisposalItems.findByAllParameters", query = "SELECT m FROM MmsDisposalItems m WHERE m.disposalNo LIKE :disposalNo"),
    @NamedQuery(name = "MmsDisposalItems.findByAllParametersAndDipPrep", query = "SELECT m FROM MmsDisposalItems m WHERE m.disposalNo = :disposalNo AND m.requestedBy = :requestedBy"),
    @NamedQuery(name = "MmsDisposalItems.findByDisposalNo", query = "SELECT m FROM MmsDisposalItems m WHERE m.disposalNo = :disposalNo"),
    @NamedQuery(name = "MmsDisposalItems.findByDisposalStatus", query = "SELECT m FROM MmsDisposalItems m WHERE m.disposalStatus = :disposalStatus"),
    @NamedQuery(name = "MmsDisposalItems.findByRemark", query = "SELECT m FROM MmsDisposalItems m WHERE m.remark = :remark"),
    @NamedQuery(name = "MmsDisposalItems.findAllbyApproveStatus", query = "SELECT m FROM MmsDisposalItems m WHERE m.dStatus = :dStatus"),
    @NamedQuery(name = "MmsDisposalItems.findByRequestedBy", query = "SELECT m FROM MmsDisposalItems m WHERE m.requestedBy = :requestedBy"),
    @NamedQuery(name = "MmsDisposalItems.findByRequisitionDate", query = "SELECT m FROM MmsDisposalItems m WHERE m.requisitionDate = :requisitionDate"),
    @NamedQuery(name = "MmsDisposalItems.findByStoreId", query = "SELECT m FROM MmsDisposalItems m WHERE m.storeId = :storeId")})
public class MmsDisposalItems implements Serializable {

    private static final long serialVersionUID = 1L;   
    @Id

    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MMS_DISPOSAL_SEQ")
    @SequenceGenerator(name = "MMS_DISPOSAL_SEQ", sequenceName = "MMS_DISPOSAL_SEQ", allocationSize = 1)

    @Basic(optional = false)
    @NotNull
    @Column(name = "DISPOSAL_ID", nullable = false, precision = 38, scale = 0)
    private BigDecimal disposalId;
    @Column(name = "APPROVAL_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date approvalDate;
    @Size(max = 30)
    @Column(name = "APPROVED_BY", length = 30)
    private String approvedBy;
    @Size(max = 30)
    @Column(name = "DISPOSAL_NO", length = 30)
    private String disposalNo;
    @Size(max = 20)
    @Column(name = "DISPOSAL_STATUS", length = 20)
    private String disposalStatus;
    @Size(max = 90)
    @Column(name = "REMARK", length = 90)
    private String remark;
    @Column(name = "REQUESTED_BY", length = 30)
    private Integer requestedBy;
    @Column(name = "REQUISITION_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date requisitionDate;
    @Column(name = "SELECT_OPT")
    private Integer selectOpt;
    @Column(name = "D_STATUS")
    private Integer dStatus;
    @Size(max = 30)
    @Column(name = "APP_DATE")
    private String appDate;
    @Size(max = 30)
    @Column(name = "REQ_DATE")
    private String reqDate;
    @Size(max = 20)
    @Column(name = "PROCESSED_ON")
    private String processedOn;
    @OneToMany(mappedBy = "disposalId", cascade = CascadeType.ALL)
    private List<WfMmsProcessed> disposalItemList;
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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "disposalId")
    private List<MmsDisposalItemsDtl> mmsDisposalItemsDtlList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "disposalId")
    private List<WfMmsProcessed> wfMmsProcessedList;
    @OneToMany(mappedBy = "disposalItemId")
    private List<MmsDisposal> mmsDisposalList;
    public MmsDisposalItems() {
    }

    public MmsDisposalItems(BigDecimal disposalId) {
        this.disposalId = disposalId;
    }

    public BigDecimal getDisposalId() {
        return disposalId;
    }

    public void setDisposalId(BigDecimal disposalId) {
        this.disposalId = disposalId;
    }

    public String getProcessedOn() {
        return processedOn;
    }

    public void setProcessedOn(String processedOn) {
        this.processedOn = processedOn;
    }

    public Date getApprovalDate() {
        return approvalDate;
    }

    public void setApprovalDate(Date approvalDate) {
        this.approvalDate = approvalDate;
    }

    public String getApprovedBy() {
        return approvedBy;
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

    public void setApprovedBy(String approvedBy) {
        this.approvedBy = approvedBy;
    }

    public String getAppDate() {
        return appDate;
    }

    public void setAppDate(String appDate) {
        this.appDate = appDate;
    }

    public Integer getSelectOpt() {
        return selectOpt;
    }

    public void setSelectOpt(Integer selectOpt) {
        this.selectOpt = selectOpt;
    }

    public String getReqDate() {
        return reqDate;
    }

    public void setReqDate(String reqDate) {
        this.reqDate = reqDate;
    }

    public Integer getdStatus() {
        return dStatus;
    }

    public void setdStatus(Integer dStatus) {
        this.dStatus = dStatus;
    }

    public HrDepartments getDepartment() {
        return department;
    }

    public void setDepartment(HrDepartments department) {
        this.department = department;
    }

    public String getDisposalNo() {
        return disposalNo;
    }

    public void setDisposalNo(String disposalNo) {
        this.disposalNo = disposalNo;
    }

    public String getDisposalStatus() {
        return disposalStatus;
    }

    public void setDisposalStatus(String disposalStatus) {
        this.disposalStatus = disposalStatus;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getRequestedBy() {
        return requestedBy;
    }

    public void setRequestedBy(Integer requestedBy) {
        this.requestedBy = requestedBy;
    }

    public Date getRequisitionDate() {
        return requisitionDate;
    }

    public void setRequisitionDate(Date requisitionDate) {
        this.requisitionDate = requisitionDate;
    }

    public MmsStoreInformation getStoreId() {
        return storeId;
    }

    public void setStoreId(MmsStoreInformation storeId) {
        this.storeId = storeId;
    }

    @XmlTransient
    public List<WfMmsProcessed> getDisposalItemList() {
        if (disposalItemList == null) {
            disposalItemList = new ArrayList<>();
        }
        return disposalItemList;
    }

    public void setDisposalItemList(List<WfMmsProcessed> disposalItemList) {
        this.disposalItemList = disposalItemList;
    }

    @XmlTransient
    public List<MmsDisposalItemsDtl> getMmsDisposalItemsDtlList() {
        if (mmsDisposalItemsDtlList == null) {
            mmsDisposalItemsDtlList = new ArrayList<>();
        }
        return mmsDisposalItemsDtlList;
    }

    public void setMmsDisposalItemsDtlList(List<MmsDisposalItemsDtl> mmsDisposalItemsDtlList) {
        this.mmsDisposalItemsDtlList = mmsDisposalItemsDtlList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (disposalId != null ? disposalId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {     
        if (!(object instanceof MmsDisposalItems)) {
            return false;
        }
        MmsDisposalItems other = (MmsDisposalItems) object;
        if ((this.disposalId == null && other.disposalId != null) || (this.disposalId != null && !this.disposalId.equals(other.disposalId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DisposalNo " + disposalNo;
    }

    public void addDisposalItemDetail(MmsDisposalItemsDtl mmsDisposalItemsDtlList1) {

        if (mmsDisposalItemsDtlList1.getDisposalId() != this) {
            this.getMmsDisposalItemsDtlList().add(mmsDisposalItemsDtlList1);
            mmsDisposalItemsDtlList1.setDisposalId(this);
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

    @XmlTransient

    public List<MmsDisposal> getMmsDisposalList() {
        if (mmsDisposalList == null) {
            mmsDisposalList = new ArrayList<>();
        }
        return mmsDisposalList;
    }

    public void setMmsDisposalList(List<MmsDisposal> mmsDisposalList) {
        this.mmsDisposalList = mmsDisposalList;
    }

  

}
