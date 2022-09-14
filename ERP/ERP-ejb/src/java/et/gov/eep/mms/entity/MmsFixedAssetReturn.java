
package et.gov.eep.mms.entity;

import et.gov.eep.commonApplications.entity.WfMmsProcessed;
import et.gov.eep.hrms.entity.employee.HrEmployees;
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
@Table(name = "MMS_FIXED_ASSET_RETURN")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MmsFixedAssetReturn.findAll", query = "SELECT m FROM MmsFixedAssetReturn m"),
    @NamedQuery(name = "MmsFixedAssetReturn.findByFarId", query = "SELECT m FROM MmsFixedAssetReturn m WHERE m.farId = :farId"),
    @NamedQuery(name = "MmsFixedAssetReturn.findByDeliverdBy", query = "SELECT m FROM MmsFixedAssetReturn m WHERE m.deliverdBy = :deliverdBy"),
    @NamedQuery(name = "MmsFixedAssetReturn.findByDepartment", query = "SELECT m FROM MmsFixedAssetReturn m WHERE m.department = :department"),
    @NamedQuery(name = "MmsFixedAssetReturn.findByFarNo", query = "SELECT m FROM MmsFixedAssetReturn m WHERE m.farNo = :farNo"),
    @NamedQuery(name = "MmsFixedAssetReturn.findFarListByWfStatus", query = "SELECT m FROM MmsFixedAssetReturn m WHERE m.status=:status"),
    @NamedQuery(name = "MmsFixedAssetReturn.findFarListForCheckerByWfStatus", query = "SELECT m FROM MmsFixedAssetReturn m WHERE m.status=:prepared OR m.status=:approverReject"),
    @NamedQuery(name = "MmsFixedAssetReturn.findAllByPreparerId", query = "SELECT m FROM MmsFixedAssetReturn m WHERE m.processedBy =:processedBy"),
    @NamedQuery(name = "MmsFixedAssetReturn.findByFarNoLike", query = "SELECT m FROM MmsFixedAssetReturn m WHERE m.farNo LIKE :farNo"),
    @NamedQuery(name = "MmsFixedAssetReturn.findByAllParameters", query = "SELECT m FROM MmsFixedAssetReturn m WHERE m.farNo LIKE :farNo"),
    @NamedQuery(name = "MmsFixedAssetReturn.findByAllParametersAndProcessedBy", query = "SELECT m FROM MmsFixedAssetReturn m WHERE m.farNo LIKE :farNo AND m.processedBy=:processedBy"),
    @NamedQuery(name = "MmsFixedAssetReturn.findByAllParameters2", query = "SELECT m FROM MmsFixedAssetReturn m WHERE m.department LIKE :department"),
    @NamedQuery(name = "MmsFixedAssetReturn.findByFarIdMaximum", query = "SELECT m FROM MmsFixedAssetReturn m WHERE m.farId = (SELECT Max(c.farId) FROM MmsFixedAssetReturn c)"),
    @NamedQuery(name = "MmsFixedAssetReturn.findByRecieveDate", query = "SELECT m FROM MmsFixedAssetReturn m WHERE m.recieveDate = :recieveDate"),
    @NamedQuery(name = "MmsFixedAssetReturn.findByRecievedBy", query = "SELECT m FROM MmsFixedAssetReturn m WHERE m.recievedBy = :recievedBy"),
    @NamedQuery(name = "MmsFixedAssetReturn.findByRemark", query = "SELECT m FROM MmsFixedAssetReturn m WHERE m.remark = :remark"),
    @NamedQuery(name = "MmsFixedAssetReturn.findByReturnDate", query = "SELECT m FROM MmsFixedAssetReturn m WHERE m.returnDate = :returnDate"),
    @NamedQuery(name = "MmsFixedAssetReturn.findByReturnStatus", query = "SELECT m FROM MmsFixedAssetReturn m WHERE m.returnStatus = :returnStatus"),
    @NamedQuery(name = "MmsFixedAssetReturn.findByReturnedBy", query = "SELECT m FROM MmsFixedAssetReturn m WHERE m.returnedBy = :returnedBy"),
    @NamedQuery(name = "MmsFixedAssetReturn.findByStoreId", query = "SELECT m FROM MmsFixedAssetReturn m WHERE m.storeId = :storeId")})
public class MmsFixedAssetReturn implements Serializable {

    private static final long serialVersionUID = 1L;  
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MMS_FARETUN_SEQ")
    @SequenceGenerator(name = "MMS_FARETUN_SEQ", sequenceName = "MMS_FARETUN_SEQ", allocationSize = 1)
    @Basic(optional = false)
    @NotNull
    @Column(name = "FAR_ID", nullable = false, precision = 0, scale = -127)
    private BigDecimal farId;
    @Size(max = 30)
    @Column(name = "DELIVERD_BY", length = 30)
    private String deliverdBy;
    @Size(max = 30)
    @Column(name = "FAR_NO", length = 30)
    private String farNo;
    @Column(name = "RECIEVE_DATE")
    @Temporal(TemporalType.DATE)
    private Date recieveDate;
    @Size(max = 30)
    @Column(name = "RECIEVED_BY", length = 30)
    private String recievedBy;
    @Size(max = 30)
    @Column(length = 30)
    private String remark;
    @Column(name = "RETURN_DATE")
    @Temporal(TemporalType.DATE)
    private Date returnDate;
    @Size(max = 20)
    @Column(name = "RETURN_STATUS", length = 20)
    private String returnStatus;
     @Column(name = "RETURNED_BY")
    private Integer returnedBy;
    @Size(max = 20)
    @Column(name = "PROCESSED_ON")
    private String processedOn;
    @Size(max = 30)
    @Column(name = "RET_DATE")
    private String retDate;
    @Size(max = 30)
    @Column(name = "RCV_DATE")
    private String rcvDate;
    @Column(name = "PROCESSED_BY")
    private Integer processedBy;
    @Column(name = "STATUS")
    private Integer status;
    @Transient
    private String columnName;
    @Transient
    private String columnValue;
    @JoinColumn(name = "DEPARTMENT", referencedColumnName = "DEP_ID")
    @ManyToOne
    private HrDepartments department;
    @JoinColumn(name = "STORE_ID", referencedColumnName = "STORE_ID")
    @ManyToOne
    private MmsStoreInformation storeId;
    @JoinColumn(name = "FIXEDAR_DT_ID", referencedColumnName = "FAR_DET_ID")
    @ManyToOne
    private MmsFixedassetRegstDetail fixedARDtId;
    @OneToMany(mappedBy = "farId", cascade = CascadeType.ALL)
    private List<MmsFixedAssetReturnDtl> mmsFixedAssetReturnDtlList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "farId")
    private List<WfMmsProcessed> FixedAssetList;
    public MmsFixedAssetReturn() {
    }

    public MmsFixedAssetReturn(BigDecimal farId) {
        this.farId = farId;
    }

    public BigDecimal getFarId() {
        return farId;
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

    public void setProcessedBy(Integer processedBy) {
        this.processedBy = processedBy;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setFarId(BigDecimal farId) {
        this.farId = farId;
    }

    public String getDeliverdBy() {
        return deliverdBy;
    }

    public void setDeliverdBy(String deliverdBy) {
        this.deliverdBy = deliverdBy;
    }

    public String getFarNo() {
        return farNo;
    }

    public void setFarNo(String farNo) {
        this.farNo = farNo;
    }

    public HrDepartments getDepartment() {
        return department;
    }

    public void setDepartment(HrDepartments department) {
        this.department = department;
    }

    public String getRetDate() {
        return retDate;
    }

    public void setRetDate(String retDate) {
        this.retDate = retDate;
    }

    public String getRcvDate() {
        return rcvDate;
    }

    public void setRcvDate(String rcvDate) {
        this.rcvDate = rcvDate;
    }

    public Date getRecieveDate() {
        return recieveDate;
    }

    public void setRecieveDate(Date recieveDate) {
        this.recieveDate = recieveDate;
    }

    public String getRecievedBy() {
        return recievedBy;
    }

    public void setRecievedBy(String recievedBy) {
        this.recievedBy = recievedBy;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public String getReturnStatus() {
        return returnStatus;
    }

    public void setReturnStatus(String returnStatus) {
        this.returnStatus = returnStatus;
    }

    public MmsStoreInformation getStoreId() {
        return storeId;
    }

    public void setStoreId(MmsStoreInformation storeId) {
        this.storeId = storeId;
    }

    public MmsFixedassetRegstDetail getFixedARDtId() {
        return fixedARDtId;
    }

    public void setFixedARDtId(MmsFixedassetRegstDetail fixedARDtId) {
        this.fixedARDtId = fixedARDtId;
    }

    @XmlTransient
    public List<MmsFixedAssetReturnDtl> getMmsFixedAssetReturnDtlList() {
        if (mmsFixedAssetReturnDtlList == null) {
            mmsFixedAssetReturnDtlList = new ArrayList<>();
        }
        return mmsFixedAssetReturnDtlList;
    }

    public void setMmsFixedAssetReturnDtlList(List<MmsFixedAssetReturnDtl> mmsFixedAssetReturnDtlList) {
        this.mmsFixedAssetReturnDtlList = mmsFixedAssetReturnDtlList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (farId != null ? farId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof MmsFixedAssetReturn)) {
            return false;
        }
        MmsFixedAssetReturn other = (MmsFixedAssetReturn) object;
        if ((this.farId == null && other.farId != null) || (this.farId != null && !this.farId.equals(other.farId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return farNo;
    }

    public void addFixedAssetReturnDetail(MmsFixedAssetReturnDtl mmsFixedAssetReturnDetail) {

        if (mmsFixedAssetReturnDetail.getFarId() != this) {
            System.out.println("....add..........");
            this.getMmsFixedAssetReturnDtlList().add(mmsFixedAssetReturnDetail);
            mmsFixedAssetReturnDetail.setFarId(this);
            System.out.println(".....size....." + this.getMmsFixedAssetReturnDtlList().size());
        }
    }

    @XmlTransient
    public List<WfMmsProcessed> getFixedAssetList() {
        if (FixedAssetList == null) {
            FixedAssetList = new ArrayList<>();
        }
        return FixedAssetList;
    }

    public void setFixedAssetList(List<WfMmsProcessed> nonFaRetList) {
        this.FixedAssetList = nonFaRetList;
    }

    public Integer getReturnedBy() {
        return returnedBy;
    }

    public void setReturnedBy(Integer returnedBy) {
        this.returnedBy = returnedBy;
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

    public String getProposedBy() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
   
    public class ColumnNameResolver implements Serializable {
    String Col_Name_FromTable;
    String Parsed_Col_Name;

        public String getCol_Name_FromTable() {
            return Col_Name_FromTable;
        }

        public void setCol_Name_FromTable(String Col_Name_FromTable) {
            this.Col_Name_FromTable = Col_Name_FromTable;
        }

        public String getParsed_Col_Name() {
            return Parsed_Col_Name;
        }

        public void setParsed_Col_Name(String Parsed_Col_Name) {
            this.Parsed_Col_Name = Parsed_Col_Name;
        }

        
    
}
    
}
