
package et.gov.eep.mms.entity;

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
 * @author minab
 */
@Entity
@Table(name = "MMS_NON_FIXED_ASSET_RETURN")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MmsNonFixedAssetReturn.findAll", query = "SELECT m FROM MmsNonFixedAssetReturn m"),
    @NamedQuery(name = "MmsNonFixedAssetReturn.findByNfaId", query = "SELECT m FROM MmsNonFixedAssetReturn m WHERE m.nfaId = :nfaId"),
    @NamedQuery(name = "MmsNonFixedAssetReturn.findByNfaNo", query = "SELECT m FROM MmsNonFixedAssetReturn m WHERE m.nfaNo = :nfaNo"),
    @NamedQuery(name = "MmsNonFixedAssetReturn.findByAllParameters", query = "SELECT m FROM MmsNonFixedAssetReturn m WHERE m.nfaNo LIKE :nfaNo"),
    @NamedQuery(name = "MmsNonFixedAssetReturn.findByStoreId", query = "SELECT m FROM MmsNonFixedAssetReturn m WHERE m.storeId = :storeId"),
    @NamedQuery(name = "MmsNonFixedAssetReturn.findByNfaIdMaximum", query = "SELECT m FROM MmsNonFixedAssetReturn m WHERE m.nfaId = (SELECT Max(c.nfaId) FROM MmsNonFixedAssetReturn c)"),
    @NamedQuery(name = "MmsNonFixedAssetReturn.findByNfaNoLike", query = "SELECT m FROM MmsNonFixedAssetReturn m WHERE m.nfaNo LIKE :nfaNo"),
    @NamedQuery(name = "MmsNonFixedAssetReturn.findNfaListByWfStatus", query = "SELECT m FROM MmsNonFixedAssetReturn m WHERE m.retStatus=:retStatus"),
    @NamedQuery(name = "MmsNonFixedAssetReturn.findNfaListForCheckerByWfStatus", query = "SELECT m FROM MmsNonFixedAssetReturn m WHERE m.retStatus=:prepared OR m.retStatus=:approverReject"),
    @NamedQuery(name = "MmsNonFixedAssetReturn.findByDepartment", query = "SELECT m FROM MmsNonFixedAssetReturn m WHERE m.department = :department"),
    @NamedQuery(name = "MmsNonFixedAssetReturn.findByReturnStatus", query = "SELECT m FROM MmsNonFixedAssetReturn m WHERE m.returnStatus = :returnStatus"),
    @NamedQuery(name = "MmsNonFixedAssetReturn.findByReturnDate", query = "SELECT m FROM MmsNonFixedAssetReturn m WHERE m.returnDate = :returnDate"),
    @NamedQuery(name = "MmsNonFixedAssetReturn.findByRecievedDate", query = "SELECT m FROM MmsNonFixedAssetReturn m WHERE m.recievedDate = :recievedDate"),
    @NamedQuery(name = "MmsNonFixedAssetReturn.findByReturnedBy", query = "SELECT m FROM MmsNonFixedAssetReturn m WHERE m.returnedBy = :returnedBy"),
    @NamedQuery(name = "MmsNonFixedAssetReturn.findByRecievedBy", query = "SELECT m FROM MmsNonFixedAssetReturn m WHERE m.recievedBy = :recievedBy"),
    @NamedQuery(name = "MmsNonFixedAssetReturn.findByDeliverdBy", query = "SELECT m FROM MmsNonFixedAssetReturn m WHERE m.deliverdBy = :deliverdBy"),
    @NamedQuery(name = "MmsNonFixedAssetReturn.findByRemark", query = "SELECT m FROM MmsNonFixedAssetReturn m WHERE m.remark = :remark")})
public class MmsNonFixedAssetReturn implements Serializable {

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @NotNull
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MMS_NFAR_SEQQ")
    @SequenceGenerator(name = "MMS_NFAR_SEQQ", sequenceName = "MMS_NFAR_SEQQ", allocationSize = 1)
    @Column(name = "NFA_ID", nullable = false, precision = 0, scale = -127)
    private BigDecimal nfaId;
    @Size(max = 30)
    @Column(name = "NFA_NO", length = 30)
    private String nfaNo;
    @Size(max = 20)
    @Column(name = "RETURN_STATUS", length = 20)
    private String returnStatus;
    @Column(name = "RETURN_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date returnDate;
    @Column(name = "RECIEVED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date recievedDate;
    @Size(max = 30)
    @Column(name = "RETURNED_BY", length = 30)
    private String returnedBy;
    @Column(name = "RECIEVED_BY")
    private Integer recievedBy;
    @Size(max = 30)
    @Column(name = "DELIVERD_BY", length = 30)
    private String deliverdBy;
    @Size(max = 30)
    @Column(name = "REMARK", length = 250)
    private String remark;
    @Column(name = "SELECT_OPT")
    private Integer selectOpt;
    @Size(max = 30)
    @Column(name = "RETURN2_DATE")
    private String return2Date;
    @Size(max = 30)
    @Column(name = "RECIEVED2_DATE")
    private String recieved2Date;
    @Size(max = 50)
    @Column(name = "PROCESSED_ON")
    private String processedOn;
    @Size(max = 50)
    @Column(name = "ACCOUNT_CODE")
    private String accountCode;
    @Column(name = "RET_STATUS")
    private Integer retStatus;
    @JoinColumn(name = "STORE_ID", referencedColumnName = "STORE_ID")
    @ManyToOne
    private MmsStoreInformation storeId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "nfaId")
    private List<MmsNonFixedAssetReturnDtl> mmsNonFixedAssetReturnDtlList;
    @OneToMany(mappedBy = "nfaId", cascade = CascadeType.ALL)
    private List<WfMmsProcessed> nonFaRetList;
    @JoinColumn(name = "DEPARTMENT", referencedColumnName = "DEP_ID")
    @ManyToOne
    private HrDepartments department;
    @Transient
    private String columnName;
    @Transient
    private String columnValue;

    public MmsNonFixedAssetReturn() {
    }

    public MmsNonFixedAssetReturn(BigDecimal nfaId) {
        this.nfaId = nfaId;
    }

    public BigDecimal getNfaId() {
        return nfaId;
    }

    public void setNfaId(BigDecimal nfaId) {
        this.nfaId = nfaId;
    }

    public String getNfaNo() {
        return nfaNo;
    }

    public void setNfaNo(String nfaNo) {
        this.nfaNo = nfaNo;
    }

    public HrDepartments getDepartment() {
        return department;
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

    public void setDepartment(HrDepartments department) {
        this.department = department;
    }

    public MmsStoreInformation getStoreId() {
        return storeId;
    }

    public void setStoreId(MmsStoreInformation storeId) {
        this.storeId = storeId;
    }

    public Integer getSelectOpt() {
        return selectOpt;
    }

    public void setSelectOpt(Integer selectOpt) {
        this.selectOpt = selectOpt;
    }

    public String getReturn2Date() {
        return return2Date;
    }

    public void setReturn2Date(String return2Date) {
        this.return2Date = return2Date;
    }

    public String getProcessedOn() {
        return processedOn;
    }

    public void setProcessedOn(String processedOn) {
        this.processedOn = processedOn;
    }

    public Integer getRetStatus() {
        return retStatus;
    }

    public void setRetStatus(Integer retStatus) {
        this.retStatus = retStatus;
    }

    public String getRecieved2Date() {
        return recieved2Date;
    }

    public void setRecieved2Date(String recieved2Date) {
        this.recieved2Date = recieved2Date;
    }

    public String getReturnStatus() {
        return returnStatus;
    }

    public void setReturnStatus(String returnStatus) {
        this.returnStatus = returnStatus;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public Date getRecievedDate() {
        return recievedDate;
    }

    public void setRecievedDate(Date recievedDate) {
        this.recievedDate = recievedDate;
    }

    public String getReturnedBy() {
        return returnedBy;
    }

    public void setReturnedBy(String returnedBy) {
        this.returnedBy = returnedBy;
    }

    public Integer getRecievedBy() {
        return recievedBy;
    }

    public void setRecievedBy(Integer recievedBy) {
        this.recievedBy = recievedBy;
    }

    public String getDeliverdBy() {
        return deliverdBy;
    }

    public void setDeliverdBy(String deliverdBy) {
        this.deliverdBy = deliverdBy;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @XmlTransient
    public List<WfMmsProcessed> getNonFaRetList() {
        if (nonFaRetList == null) {
            nonFaRetList = new ArrayList<>();
        }
        return nonFaRetList;
    }

    public void setNonFaRetList(List<WfMmsProcessed> nonFaRetList) {
        this.nonFaRetList = nonFaRetList;
    }

    @XmlTransient
    public List<MmsNonFixedAssetReturnDtl> getMmsNonFixedAssetReturnDtlList() {
        if (mmsNonFixedAssetReturnDtlList == null) {
            mmsNonFixedAssetReturnDtlList = new ArrayList<>();
        }
        return mmsNonFixedAssetReturnDtlList;
    }

    public void setMmsNonFixedAssetReturnDtlList(List<MmsNonFixedAssetReturnDtl> mmsNonFixedAssetReturnDtlList) {
        this.mmsNonFixedAssetReturnDtlList = mmsNonFixedAssetReturnDtlList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (nfaId != null ? nfaId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
       
        if (!(object instanceof MmsNonFixedAssetReturn)) {
            return false;
        }
        MmsNonFixedAssetReturn other = (MmsNonFixedAssetReturn) object;
        if ((this.nfaId == null && other.nfaId != null) || (this.nfaId != null && !this.nfaId.equals(other.nfaId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return  nfaNo;
    }

    public void addNonFixedAssetReturnDetail(MmsNonFixedAssetReturnDtl mmsNonFixedAssetReturnDetail) {

        if (mmsNonFixedAssetReturnDetail.getNfaId() != this) {
            this.getMmsNonFixedAssetReturnDtlList().add(mmsNonFixedAssetReturnDetail);
            mmsNonFixedAssetReturnDetail.setNfaId(this);
        }
    }

    public String getAccountCode() {
        return accountCode;
    }

    public void setAccountCode(String accountCode) {
        this.accountCode = accountCode;
    }
    
}
