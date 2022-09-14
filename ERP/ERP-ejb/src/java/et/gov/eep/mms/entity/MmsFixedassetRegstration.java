package et.gov.eep.mms.entity;

import et.gov.eep.commonApplications.entity.WfMmsProcessed;
import et.gov.eep.fcms.entity.admin.FmsLuSystem;
import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.entity.organization.HrDepartments;
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
@Table(name = "MMS_FIXEDASSET_REGSTRATION")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MmsFixedassetRegstration.findAll", query = "SELECT m FROM MmsFixedassetRegstration m"),
    @NamedQuery(name = "MmsFixedassetRegstration.findById", query = "SELECT m FROM MmsFixedassetRegstration m WHERE m.id = :id"),
    @NamedQuery(name = "MmsFixedassetRegstration.findByFarNo", query = "SELECT m FROM MmsFixedassetRegstration m WHERE m.farNo = :farNo"),
    @NamedQuery(name = "MmsFixedassetRegstration.findByDeliveryDate", query = "SELECT m FROM MmsFixedassetRegstration m WHERE m.deliveryDate = :deliveryDate"),
    @NamedQuery(name = "MmsFixedassetRegstration.findByAllParameters", query = "SELECT m FROM MmsFixedassetRegstration m WHERE m.farNo LIKE :farNo"),
    @NamedQuery(name = "MmsFixedassetRegstration.findFarListByWfStatus", query = "SELECT m FROM MmsFixedassetRegstration m WHERE m.faStatus =:faStatus"),
    @NamedQuery(name = "MmsFixedassetRegstration.findAllByPreparerId", query = "SELECT m FROM MmsFixedassetRegstration m WHERE m.approvedBy = :approvedBy"),
    @NamedQuery(name = "MmsFixedassetRegstration.findByApprovalDate", query = "SELECT m FROM MmsFixedassetRegstration m WHERE m.approvalDate = :approvalDate"),
    @NamedQuery(name = "MmsFixedassetRegstration.findByRecivedBy", query = "SELECT m FROM MmsFixedassetRegstration m WHERE m.recivedBy = :recivedBy"),
    @NamedQuery(name = "MmsFixedassetRegstration.findByDeliveredBy", query = "SELECT m FROM MmsFixedassetRegstration m WHERE m.deliveredBy = :deliveredBy"),
    @NamedQuery(name = "MmsFixedassetRegstration.findByDelivererRemark", query = "SELECT m FROM MmsFixedassetRegstration m WHERE m.delivererRemark = :delivererRemark"),
    @NamedQuery(name = "MmsFixedassetRegstration.findByApprovedBy", query = "SELECT m FROM MmsFixedassetRegstration m WHERE m.approvedBy = :approvedBy"),
    @NamedQuery(name = "MmsFixedassetRegstration.findByDept", query = "SELECT m FROM MmsFixedassetRegstration m WHERE m.department = :department"),
    @NamedQuery(name = "MmsFixedassetRegstration.findByFARIdMaximum", query = "SELECT m FROM MmsFixedassetRegstration m WHERE m.id = (SELECT Max(c.id)  from MmsFixedassetRegstration c)"),
    @NamedQuery(name = "MmsFixedassetRegstration.findByDeliveryStatus", query = "SELECT m FROM MmsFixedassetRegstration m WHERE m.deliveryStatus = :deliveryStatus")})
public class MmsFixedassetRegstration implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MMS_FAR_SEQ")
    @SequenceGenerator(name = "MMS_FAR_SEQ", sequenceName = "MMS_FAR_SEQ", allocationSize = 1)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "SELECT_OPT")
    private Integer selectOpt;
    @Column(name = "FA_STATUS")
    private Integer faStatus;
    @Size(max = 50)
    @Column(name = "FAR_NO")
    private String farNo;
    @Column(name = "DELIVERY_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date deliveryDate;
    @Column(name = "APPROVAL_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date approvalDate;
    @Column(name = "APPROVED_BY")
    private Integer approvedBy;
    @Size(max = 20)
    @Column(name = "DELIVERED_BY")
    private String deliveredBy;
    @Size(max = 100)
    @Column(name = "DELIVERER_REMARK")
    private String delivererRemark;
    @Size(max = 20)
    @Column(name = "DELIVERY_STATUS")
    private String deliveryStatus;
    @Size(max = 30)
    @Column(name = "APP_DATE")
    private String appDate;
    @Size(max = 30)
    @Column(name = "DELIV_DATE")
    private String delivDate;
    @Size(max = 50)
    @Column(name = "PROCESSED_ON")
    private String processedOn;
    @Transient
    private String columnName;
    @Transient
    private String columnValue;
    
    @JoinColumn(name = "SYSTEM_NO", referencedColumnName = "SYSTEM_ID")
    @ManyToOne
    private FmsLuSystem systemNo;
    
    @JoinColumn(name = "DEPARTMENT", referencedColumnName = "DEP_ID")
    @ManyToOne
    private HrDepartments department;
    
    @JoinColumn(name = "ASSET_TYPE", referencedColumnName = "ASSET_ID")
    @ManyToOne
    private MmsFaAssetType assetType;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "farId")
    private List<MmsFixedassetRegstDetail> mmsFixedassetRegstDetailList;
    
    @OneToMany(mappedBy = "faRegstrationId", cascade = CascadeType.ALL)
    private List<WfMmsProcessed> faRegList;
    @JoinColumn(name = "RECIVED_BY", referencedColumnName = "ID")
    @ManyToOne
    private HrEmployees recivedBy;

    public MmsFixedassetRegstration() {
    }

    public MmsFixedassetRegstration(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSelectOpt() {
        return selectOpt;
    }

    public void setSelectOpt(Integer selectOpt) {
        this.selectOpt = selectOpt;
    }

    public Date getApprovalDate() {
        return approvalDate;
    }

    public void setApprovalDate(Date approvalDate) {
        this.approvalDate = approvalDate;
    }

    public Integer getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(Integer approvedBy) {
        this.approvedBy = approvedBy;
    }

    public String getProcessedOn() {
        return processedOn;
    }

    public void setProcessedOn(String processedOn) {
        this.processedOn = processedOn;
    }

    public Integer getFaStatus() {
        return faStatus;
    }

    public void setFaStatus(Integer faStatus) {
        this.faStatus = faStatus;
    }

    public String getDeliveredBy() {
        return deliveredBy;
    }

    public void setDeliveredBy(String deliveredBy) {
        this.deliveredBy = deliveredBy;
    }

    public String getAppDate() {
        return appDate;
    }

    public void setAppDate(String appDate) {
        this.appDate = appDate;
    }

    public String getDelivDate() {
        return delivDate;
    }

    public void setDelivDate(String delivDate) {
        this.delivDate = delivDate;
    }

    public String getDelivererRemark() {
        return delivererRemark;
    }

    public void setDelivererRemark(String delivererRemark) {
        this.delivererRemark = delivererRemark;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(String deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    public String getFarNo() {
        return farNo;
    }

    public void setFarNo(String farNo) {
        this.farNo = farNo;
    }

    public HrEmployees getRecivedBy() {
        return recivedBy;
    }

    public void setRecivedBy(HrEmployees hrEmployees) {
        this.recivedBy = recivedBy;
    }

    public FmsLuSystem getSystemNo() {
        return systemNo;
    }

    public void setSystemNo(FmsLuSystem systemNo) {
        this.systemNo = systemNo;
    }

    public HrDepartments getDepartment() {
        return department;
    }

    public void setDepartment(HrDepartments department) {
        this.department = department;
    }

    public MmsFaAssetType getAssetType() {
        return assetType;
    }

    public void setAssetType(MmsFaAssetType assetType) {
        this.assetType = assetType;
    }

    @XmlTransient
    public List<WfMmsProcessed> getFaRegList() {
        if (faRegList == null) {
            faRegList = new ArrayList<>();
        }
        return faRegList;
    }

    public void setFaRegList(List<WfMmsProcessed> faRegList) {
        this.faRegList = faRegList;
    }

    @XmlTransient
    public List<MmsFixedassetRegstDetail> getMmsFixedassetRegstDetailList() {
        if (mmsFixedassetRegstDetailList == null) {
            mmsFixedassetRegstDetailList = new ArrayList<>();
        }
        return mmsFixedassetRegstDetailList;
    }

    public void setMmsFixedassetRegstDetailList(List<MmsFixedassetRegstDetail> mmsFixedassetRegstDetailList) {
        this.mmsFixedassetRegstDetailList = mmsFixedassetRegstDetailList;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof MmsFixedassetRegstration)) {
            return false;
        }
        MmsFixedassetRegstration other = (MmsFixedassetRegstration) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    public void addToFixedAssetRegDetail(MmsFixedassetRegstDetail fixedAssetRegDtlEntity) {
        if (fixedAssetRegDtlEntity.getFarId() != this) {
            this.getMmsFixedassetRegstDetailList().add(fixedAssetRegDtlEntity);
            fixedAssetRegDtlEntity.setFarId(this);
        }
    }

}
