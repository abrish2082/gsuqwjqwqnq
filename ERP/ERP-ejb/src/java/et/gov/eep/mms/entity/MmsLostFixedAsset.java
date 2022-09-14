
package et.gov.eep.mms.entity;

import et.gov.eep.commonApplications.entity.MmsLuDmArchive;
import et.gov.eep.commonApplications.entity.WfMmsProcessed;
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
import javax.persistence.Lob;
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
@Table(name = "MMS_LOST_FIXED_ASSET")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MmsLostFixedAsset.findAll", query = "SELECT m FROM MmsLostFixedAsset m"),
    @NamedQuery(name = "MmsLostFixedAsset.findByLostItemId", query = "SELECT m FROM MmsLostFixedAsset m WHERE m.lostItemId = :lostItemId"),
    @NamedQuery(name = "MmsLostFixedAsset.findByAcceptedBy", query = "SELECT m FROM MmsLostFixedAsset m WHERE m.acceptedBy = :acceptedBy"),
    @NamedQuery(name = "MmsLostFixedAsset.findByAcceptedDate", query = "SELECT m FROM MmsLostFixedAsset m WHERE m.acceptedDate = :acceptedDate"),
    @NamedQuery(name = "MmsLostFixedAsset.findByApprovedDate", query = "SELECT m FROM MmsLostFixedAsset m WHERE m.approvedDate = :approvedDate"),
    @NamedQuery(name = "MmsLostFixedAsset.findByApproverName", query = "SELECT m FROM MmsLostFixedAsset m WHERE m.approverName = :approverName"),
    @NamedQuery(name = "MmsLostFixedAsset.findLostListForCheckerByWfStatus", query = "SELECT m FROM MmsLostFixedAsset m WHERE m.lostStatus=:prepared OR m.lostStatus=:approverReject"),
    @NamedQuery(name = "MmsLostFixedAsset.findAllByPreparerId", query = "SELECT m FROM MmsLostFixedAsset m WHERE m.employeeName = :employeeName"),
    @NamedQuery(name = "MmsLostFixedAsset.findLostListByWfStatus", query = "SELECT m FROM MmsLostFixedAsset m WHERE m.lostStatus= :lostStatus"),
    @NamedQuery(name = "MmsLostFixedAsset.findByLostItemNo", query = "SELECT m FROM MmsLostFixedAsset m WHERE m.lostItemNo = :lostItemNo"),
    @NamedQuery(name = "MmsLostFixedAsset.findByAllParameters", query = "SELECT m FROM MmsLostFixedAsset m WHERE m.lostItemNo LIKE :lostItemNo"),
    @NamedQuery(name = "MmsLostFixedAsset.findByAllParametersAndEmpName", query = "SELECT m FROM MmsLostFixedAsset m WHERE m.lostItemNo = :lostItemNo AND m.employeeName = :employeeName"),
    @NamedQuery(name = "MmsLostFixedAsset.findBylostItemIdMaximum", query = "SELECT m FROM MmsLostFixedAsset m WHERE m.lostItemId = (SELECT Max(c.lostItemId) FROM MmsLostFixedAsset c)"),
    @NamedQuery(name = "MmsLostFixedAsset.findByCostCenter", query = "SELECT m FROM MmsLostFixedAsset m WHERE m.costCenter = :costCenter"),
    @NamedQuery(name = "MmsLostFixedAsset.findByDepartment", query = "SELECT m FROM MmsLostFixedAsset m WHERE m.department = :department"),
    @NamedQuery(name = "MmsLostFixedAsset.findByEmployeeName", query = "SELECT m FROM MmsLostFixedAsset m WHERE m.employeeName = :employeeName"),
    @NamedQuery(name = "MmsLostFixedAsset.findByEvidenceAttachedId", query = "SELECT m FROM MmsLostFixedAsset m WHERE m.evidenceAttachedId = :evidenceAttachedId"),
    @NamedQuery(name = "MmsLostFixedAsset.findByLostItemNo", query = "SELECT m FROM MmsLostFixedAsset m WHERE m.lostItemNo = :lostItemNo"),
    @NamedQuery(name = "MmsLostFixedAsset.findByReasonOfWrittenOff", query = "SELECT m FROM MmsLostFixedAsset m WHERE m.reasonOfWrittenOff = :reasonOfWrittenOff"),
    @NamedQuery(name = "MmsLostFixedAsset.findByRegionName", query = "SELECT m FROM MmsLostFixedAsset m WHERE m.regionName = :regionName"),
    @NamedQuery(name = "MmsLostFixedAsset.findByRegistrationDate", query = "SELECT m FROM MmsLostFixedAsset m WHERE m.registrationDate = :registrationDate"),
    @NamedQuery(name = "MmsLostFixedAsset.findByRemark", query = "SELECT m FROM MmsLostFixedAsset m WHERE m.remark = :remark"),
    @NamedQuery(name = "MmsLostFixedAsset.findByStatus", query = "SELECT m FROM MmsLostFixedAsset m WHERE m.status = :status"),
    @NamedQuery(name = "MmsLostFixedAsset.findByStoreId", query = "SELECT m FROM MmsLostFixedAsset m WHERE m.storeId = :storeId"),
    @NamedQuery(name = "MmsLostFixedAsset.findByReferenceNumber", query = "SELECT m FROM MmsLostFixedAsset m WHERE m.referenceNumber=:referenceNumber")})
public class MmsLostFixedAsset implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id

    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "LOSTFXASSETSQNew")
    @SequenceGenerator(name = "LOSTFXASSETSQNew", sequenceName = "LOSTFXASSETSQNew", allocationSize = 1)
    @Column(name = "LOST_ITEM_ID", nullable = false, precision = 38, scale = 0)
    private Integer lostItemId;
    @Size(max = 255)
    @Column(name = "ACCEPTED_BY", length = 255)
    private String acceptedBy;
    @Column(name = "ACCEPTED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date acceptedDate;
    @Column(name = "APPROVED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date approvedDate;
    @Size(max = 255)
    @Column(name = "APPROVER_NAME", length = 255)
    private String approverName;
    @Size(max = 255)
    @Column(name = "COST_CENTER", length = 255)
    private String costCenter;
    @Column(name = "EMPLOYEE_NAME", length = 255)
    private Integer employeeName;
    @Column(name = "LOST_STATUS")
    private Integer lostStatus;
    @Size(max = 255)
    @Column(name = "EVIDENCE_ATTACHED_ID", length = 255)
    private String evidenceAttachedId;
    @Size(max = 20)
    @Column(name = "LOST_ITEM_NO", length = 20)
    private String lostItemNo;
    @Size(max = 255)
    @Column(name = "REASON_OF_WRITTEN_OFF", length = 255)
    private String reasonOfWrittenOff;
    @Size(max = 255)
    @Column(name = "REGION_NAME", length = 255)
    private String regionName;
    @Column(name = "REGISTRATION_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date registrationDate;
    @Size(max = 255)
    @Column(name = "REMARK", length = 255)
    private String remark;
    @Size(max = 255)
    @Column(name = "STATUS", length = 255)
    private String status;
    @Size(max = 30)
    @Column(name = "ACCEPTED2_DATE")
    private String accepted2Date;
    @Size(max = 30)
    @Column(name = "APPROVED2_DATE")
    private String approved2Date;
    @Size(max = 30)
    @Column(name = "REG_DATE")
    private String regDate;
    @Lob
    @Column(name = "PHOTO")
    private byte[] photo;
    @JoinColumn(name = "REFERENCE_NUMBER",referencedColumnName = "DOCUMENT_ID")
    @ManyToOne()
    private MmsLuDmArchive referenceNumber;
    @OneToMany(mappedBy = "lostItemId", cascade = CascadeType.ALL)
    private List<WfMmsProcessed> faLostList;
    @JoinColumn(name = "DEPARTMENT", referencedColumnName = "DEP_ID")
    @ManyToOne
    private HrDepartments department;
    @JoinColumn(name = "STORE_ID", referencedColumnName = "STORE_ID")
    @ManyToOne
    private MmsStoreInformation storeId;
    @OneToMany(mappedBy = "lostId", cascade = CascadeType.ALL)
    private List<LostDmsUpload> lostDmsUpload;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "lostItemId")
    private List<MmsLostFixedAssetDetail> mmsLostFixedAssetDetailList;
    
    @Transient
    private String columnName;
    @Transient
    private String columnValue;
    public MmsLostFixedAsset() {
    }

    public MmsLostFixedAsset(Integer lostItemId) {
        this.lostItemId = lostItemId;
    }

    public Integer getLostItemId() {
        return lostItemId;
    }

    public void setLostItemId(Integer lostItemId) {
        this.lostItemId = lostItemId;
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

    public void setAcceptedDate(Date acceptedDate) {
        this.acceptedDate = acceptedDate;
    }

    public Date getApprovedDate() {
        return approvedDate;
    }

    public void setApprovedDate(Date approvedDate) {
        this.approvedDate = approvedDate;
    }

    public Integer getLostStatus() {
        return lostStatus;
    }

    public void setLostStatus(Integer lostStatus) {
        this.lostStatus = lostStatus;
    }

    public String getApproverName() {
        return approverName;
    }

    public void setApproverName(String approverName) {
        this.approverName = approverName;
    }

    public String getAccepted2Date() {
        return accepted2Date;
    }

    public void setAccepted2Date(String accepted2Date) {
        this.accepted2Date = accepted2Date;
    }

    public String getApproved2Date() {
        return approved2Date;
    }

    public void setApproved2Date(String approved2Date) {
        this.approved2Date = approved2Date;
    }

    public String getRegDate() {
        return regDate;
    }

    public void setRegDate(String regDate) {
        this.regDate = regDate;
    }

    public String getCostCenter() {
        return costCenter;
    }

    public void setCostCenter(String costCenter) {
        this.costCenter = costCenter;
    }

    public HrDepartments getDepartment() {
        return department;
    }

    public void setDepartment(HrDepartments department) {
        this.department = department;
    }

    public Integer getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(Integer employeeName) {
        this.employeeName = employeeName;
    }

    public MmsLuDmArchive getReferenceNumber() {
        return referenceNumber;
    }

    public void setReferenceNumber(MmsLuDmArchive referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

  

    public String getEvidenceAttachedId() {
        return evidenceAttachedId;
    }

    public void setEvidenceAttachedId(String evidenceAttachedId) {
        this.evidenceAttachedId = evidenceAttachedId;
    }

    public String getLostItemNo() {
        return lostItemNo;
    }

    public void setLostItemNo(String lostItemNo) {
        this.lostItemNo = lostItemNo;
    }

    public String getReasonOfWrittenOff() {
        return reasonOfWrittenOff;
    }

    public void setReasonOfWrittenOff(String reasonOfWrittenOff) {
        this.reasonOfWrittenOff = reasonOfWrittenOff;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public MmsStoreInformation getStoreId() {
        return storeId;
    }

    public void setStoreId(MmsStoreInformation storeId) {
        this.storeId = storeId;
    }

    @XmlTransient
    public List<LostDmsUpload> getLostDmsUpload() {
        if (lostDmsUpload == null) {
            lostDmsUpload = new ArrayList<>();
        }
        return lostDmsUpload;
    }

    public void setLostDmsUpload(List<LostDmsUpload> lostDmsUpload) {
        this.lostDmsUpload = lostDmsUpload;
    }

    @XmlTransient
    public List<WfMmsProcessed> getFaLostList() {
        if (faLostList == null) {
            faLostList = new ArrayList<>();
        }
        return faLostList;
    }

    public void setFaLostList(List<WfMmsProcessed> faLostList) {
        this.faLostList = faLostList;
    }

    @XmlTransient
    public List<MmsLostFixedAssetDetail> getMmsLostFixedAssetDetailList() {
        if (mmsLostFixedAssetDetailList == null) {
            mmsLostFixedAssetDetailList = new ArrayList<>();
        }

        return mmsLostFixedAssetDetailList;
    }

    public void setMmsLostFixedAssetDetailList(List<MmsLostFixedAssetDetail> mmsLostFixedAssetDetailList) {
        this.mmsLostFixedAssetDetailList = mmsLostFixedAssetDetailList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (lostItemId != null ? lostItemId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
      
        if (!(object instanceof MmsLostFixedAsset)) {
            return false;
        }
        MmsLostFixedAsset other = (MmsLostFixedAsset) object;
        if ((this.lostItemId == null && other.lostItemId != null) || (this.lostItemId != null && !this.lostItemId.equals(other.lostItemId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "LostItemNo=" + lostItemNo + "";
    }

    public void addlostItemDetail(MmsLostFixedAssetDetail mmsLostFixedAssetDetail) {

        if (mmsLostFixedAssetDetail.getLostItemId() != this) {
            this.getMmsLostFixedAssetDetailList().add(mmsLostFixedAssetDetail);
            mmsLostFixedAssetDetail.setLostItemId(this);
        }
    }

    public void add(LostDmsUpload lostDmsUpload) {

        if (lostDmsUpload.getLostId() != this) {
            this.getLostDmsUpload().add(lostDmsUpload);
            lostDmsUpload.setLostId(this);
        }
    }
}
