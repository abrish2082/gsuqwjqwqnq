
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
 * @author w_station
 */
@Entity
@Table(name = "MMS_FIXED_ASSET_TRANSFER")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MmsFixedAssetTransfer.findAll", query = "SELECT m FROM MmsFixedAssetTransfer m"),
    @NamedQuery(name = "MmsFixedAssetTransfer.findByTransferDepartment", query = "SELECT m FROM MmsFixedAssetTransfer m WHERE m.transferDepartment = :transferDepartment"),
    @NamedQuery(name = "MmsFixedAssetTransfer.findByTransferId", query = "SELECT m FROM MmsFixedAssetTransfer m WHERE m.transferId = :transferId"),
    @NamedQuery(name = "MmsFixedAssetTransfer.findByTransferNoLike", query = "SELECT m FROM MmsFixedAssetTransfer m WHERE m.transferNo LIKE :transferNo"),
    @NamedQuery(name = "MmsFixedAssetTransfer.findTrListByWfStatus", query = "SELECT m FROM MmsFixedAssetTransfer m WHERE m.trStatus= :trStatus"),
    @NamedQuery(name = "MmsFixedAssetTransfer.findAllByPreparerId", query = "SELECT m FROM MmsFixedAssetTransfer m WHERE m.preparedBy =:preparedBy"),
    @NamedQuery(name = "MmsFixedAssetTransfer.findTrListForCheckerByWfStatus", query = "SELECT m FROM MmsFixedAssetTransfer m WHERE m.trStatus=:prepared OR m.trStatus=:approverReject"),
    @NamedQuery(name = "MmsFixedAssetTransfer.findByTransferNo", query = "SELECT m FROM MmsFixedAssetTransfer m WHERE m.transferNo = :transferNo"),
    @NamedQuery(name = "MmsFixedAssetTransfer.findByAllParameters", query = "SELECT m FROM MmsFixedAssetTransfer m WHERE m.transferNo LIKE :transferNo"),
    @NamedQuery(name = "MmsFixedAssetTransfer.findByAllParametersAndTrPrep", query = "SELECT m FROM MmsFixedAssetTransfer m WHERE m.transferNo = :transferNo AND m.preparedBy = :preparedBy"),
    @NamedQuery(name = "MmsFixedAssetTransfer.findByTransferIdMaximum", query = "SELECT m FROM MmsFixedAssetTransfer m WHERE m.transferId = (SELECT Max(c.transferId)  from MmsFixedAssetTransfer c)"),
    @NamedQuery(name = "MmsFixedAssetTransfer.findByReceivingDepartment", query = "SELECT m FROM MmsFixedAssetTransfer m WHERE m.receivingDepartment = :receivingDepartment"),
    @NamedQuery(name = "MmsFixedAssetTransfer.findByTransferFrom", query = "SELECT m FROM MmsFixedAssetTransfer m WHERE m.transferFrom = :transferFrom"),
    @NamedQuery(name = "MmsFixedAssetTransfer.findByTransferTo", query = "SELECT m FROM MmsFixedAssetTransfer m WHERE m.transferTo = :transferTo"),
    @NamedQuery(name = "MmsFixedAssetTransfer.findByPreparedBy", query = "SELECT m FROM MmsFixedAssetTransfer m WHERE m.preparedBy = :preparedBy"),
    @NamedQuery(name = "MmsFixedAssetTransfer.findByPreparedDate", query = "SELECT m FROM MmsFixedAssetTransfer m WHERE m.preparedDate = :preparedDate"),
    @NamedQuery(name = "MmsFixedAssetTransfer.findByCheckedBy", query = "SELECT m FROM MmsFixedAssetTransfer m WHERE m.checkedBy = :checkedBy"),
    @NamedQuery(name = "MmsFixedAssetTransfer.findByCheckedDate", query = "SELECT m FROM MmsFixedAssetTransfer m WHERE m.checkedDate = :checkedDate"),
    @NamedQuery(name = "MmsFixedAssetTransfer.findByApprovedBy", query = "SELECT m FROM MmsFixedAssetTransfer m WHERE m.approvedBy = :approvedBy"),
    @NamedQuery(name = "MmsFixedAssetTransfer.findByApprovedDate", query = "SELECT m FROM MmsFixedAssetTransfer m WHERE m.approvedDate = :approvedDate"),
    @NamedQuery(name = "MmsFixedAssetTransfer.findByReceivedBy", query = "SELECT m FROM MmsFixedAssetTransfer m WHERE m.receivedBy = :receivedBy"),
    @NamedQuery(name = "MmsFixedAssetTransfer.findByReceivedDate", query = "SELECT m FROM MmsFixedAssetTransfer m WHERE m.receivedDate = :receivedDate")})
public class MmsFixedAssetTransfer implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MMS_FIXEDASSETTRANSFER_SEQ")
    @SequenceGenerator(name = "MMS_FIXEDASSETTRANSFER_SEQ", sequenceName = "MMS_FIXEDASSETTRANSFER_SEQ", allocationSize = 1)
    @Basic(optional = false)
    @NotNull
    @Column(name = "TRANSFER_ID", nullable = false, precision = 0, scale = -127)
    private BigDecimal transferId;
    @Size(max = 30)
    @Column(name = "TRANSFER_NO", length = 30)
    private String transferNo;
    @Column(name = "SELECT_OPT")
    private Integer selectOpt;
    @Column(name = "TDEPARTMENT", length = 90)
    private String tDepartment;
    @Column(name = "RDEPARTMENT", length = 90)
    private String rDepartment;
    @Size(max = 30)
    @Column(name = "TRANSFER_FROM", length = 30)
    private String transferFrom;
    @Size(max = 30)
    @Column(name = "TRANSFER_TO", length = 30)
    private String transferTo;
    @Column(name = "PREPARED_BY", length = 30)
    private Integer preparedBy;
    @Column(name = "PREPARED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date preparedDate;
    @Size(max = 30)
    @Column(name = "CHECKED_BY", length = 30)
    private String checkedBy;
    @Column(name = "CHECKED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date checkedDate;
    @Size(max = 30)
    @Column(name = "APPROVED_BY", length = 30)
    private String approvedBy;
    @Column(name = "APPROVED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date approvedDate;
    @Size(max = 30)
    @Column(name = "RECEIVED_BY", length = 30)
    private String receivedBy;
    @Column(name = "RECEIVED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date receivedDate;
    @Size(max = 30)
    @Column(name = "RCV_DATE")
    private String rcvDate;
    @Size(max = 30)
    @Column(name = "APP_DATE")
    private String appDate;
    @Size(max = 30)
    @Column(name = "CHCK_DATE")
    private String chckDate;
    @Size(max = 30)
    @Column(name = "RECEIV_DATE")
    private String receivDate;
    @Column(name = "TR_STATUS")
    private Integer trStatus;
    @JoinColumn(name = "TRANSFER_DEPARTMENT", referencedColumnName = "DEP_ID")
    @ManyToOne
    private HrDepartments transferDepartment;
    @JoinColumn(name = "RECEIVING_DEPARTMENT", referencedColumnName = "DEP_ID")
    @ManyToOne
    private HrDepartments receivingDepartment;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "transferId")
    private List<MmsFixedAssetTransferDtl> mmsFixedAssetTransferDtlList;
    @OneToMany(mappedBy = "transferFaId", cascade = CascadeType.ALL)
    private List<WfMmsProcessed> faTransferList;
    @Transient
    private String columnName;
    @Transient
    private String columnValue;

    /**
     *
     */
    public MmsFixedAssetTransfer() {
    }

    /**
     *
     * @param transferId
     */
    public MmsFixedAssetTransfer(BigDecimal transferId) {
        this.transferId = transferId;
    }

    /**
     *
     * @return
     */

    public Integer getSelectOpt() {
        return selectOpt;
    }

    public void setSelectOpt(Integer selectOpt) {
        this.selectOpt = selectOpt;
    }

    /**
     *
     * @return
     */
    public BigDecimal getTransferId() {
        return transferId;
    }

    /**
     *
     * @param transferId
     */
    public void setTransferId(BigDecimal transferId) {
        this.transferId = transferId;
    }

    public String getRcvDate() {
        return rcvDate;
    }

    public void setRcvDate(String rcvDate) {
        this.rcvDate = rcvDate;
    }

    public Integer getTrStatus() {
        return trStatus;
    }

    public void setTrStatus(Integer trStatus) {
        this.trStatus = trStatus;
    }

    /**
     *
     * @return
     */
    public String getTransferNo() {
        return transferNo;
    }

    /**
     *
     * @param transferNo
     */
    public void setTransferNo(String transferNo) {
        this.transferNo = transferNo;
    }

    /**
     *
     * @return
     */
    public HrDepartments getTransferDepartment() {
        return transferDepartment;
    }

    public void setTransferDepartment(HrDepartments transferDepartment) {
        this.transferDepartment = transferDepartment;
    }

    public HrDepartments getReceivingDepartment() {
        return receivingDepartment;
    }

    public void setReceivingDepartment(HrDepartments receivingDepartment) {
        this.receivingDepartment = receivingDepartment;
    }

    /**
     *
     * @param receivingDepartment
     */

    public String getTDepartment() {
        return tDepartment;
    }

    /**
     *
     * @param tDepartment
     */
    public void setTDepartment(String tDepartment) {
        this.tDepartment = tDepartment;
    }

    public String getRDepartment() {
        return rDepartment;
    }

    /**
     *
     * @param rDepartment
     */
    public void setRDepartment(String rDepartment) {
        this.rDepartment = rDepartment;
    }

    /**
     *
     * @return
     */
    public String getTransferFrom() {
        return transferFrom;
    }

    /**
     *
     * @param transferFrom
     */
    public void setTransferFrom(String transferFrom) {
        this.transferFrom = transferFrom;
    }

    /**
     *
     * @return
     */
    public String getTransferTo() {
        return transferTo;
    }

    /**
     *
     * @param transferTo
     */
    public void setTransferTo(String transferTo) {
        this.transferTo = transferTo;
    }

    /**
     *
     * @return
     */
    public Integer getPreparedBy() {
        return preparedBy;
    }

    /**
     *
     * @param preparedBy
     */
    public void setPreparedBy(Integer preparedBy) {
        this.preparedBy = preparedBy;
    }

    public String getAppDate() {
        return appDate;
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

    public void setAppDate(String appDate) {
        this.appDate = appDate;
    }

    public String getChckDate() {
        return chckDate;
    }

    public void setChckDate(String chckDate) {
        this.chckDate = chckDate;
    }

    public String getReceivDate() {
        return receivDate;
    }

    public void setReceivDate(String receivDate) {
        this.receivDate = receivDate;
    }

    /**
     *
     * @return
     */
    public Date getPreparedDate() {
        return preparedDate;
    }

    /**
     *
     * @param preparedDate
     */
    public void setPreparedDate(Date preparedDate) {
        this.preparedDate = preparedDate;
    }

    /**
     *
     * @return
     */
    public String getCheckedBy() {
        return checkedBy;
    }

    /**
     *
     * @param checkedBy
     */
    public void setCheckedBy(String checkedBy) {
        this.checkedBy = checkedBy;
    }

    /**
     *
     * @return
     */
    public Date getCheckedDate() {
        return checkedDate;
    }

    /**
     *
     * @param checkedDate
     */
    public void setCheckedDate(Date checkedDate) {
        this.checkedDate = checkedDate;
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
    public Date getApprovedDate() {
        return approvedDate;
    }

    /**
     *
     * @param approvedDate
     */
    public void setApprovedDate(Date approvedDate) {
        this.approvedDate = approvedDate;
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
    public Date getReceivedDate() {
        return receivedDate;
    }

    /**
     *
     * @param receivedDate
     */
    public void setReceivedDate(Date receivedDate) {
        this.receivedDate = receivedDate;
    }

    @XmlTransient
    public List<WfMmsProcessed> getFaTransferList() {
        if (faTransferList == null) {
            faTransferList = new ArrayList<>();
        }
        return faTransferList;
    }

    public void setFaTransferList(List<WfMmsProcessed> faTransferList) {
        this.faTransferList = faTransferList;
    }

    /**
     *
     * @return
     */
    @XmlTransient
    public List<MmsFixedAssetTransferDtl> getMmsFixedAssetTransferDtlList() {
        if (mmsFixedAssetTransferDtlList == null) {
            mmsFixedAssetTransferDtlList = new ArrayList<>();
        }
        return mmsFixedAssetTransferDtlList;
    }

    /**
     *
     * @param mmsFixedAssetTransferDtlList
     */
    public void setMmsFixedAssetTransferDtlList(List<MmsFixedAssetTransferDtl> mmsFixedAssetTransferDtlList) {
        this.mmsFixedAssetTransferDtlList = mmsFixedAssetTransferDtlList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (transferId != null ? transferId.hashCode() : 0);
        return hash;
    }

    /**
     *
     * @param object
     * @return
     */
    @Override
    public boolean equals(Object object) {    
        if (!(object instanceof MmsFixedAssetTransfer)) {
            return false;
        }
        MmsFixedAssetTransfer other = (MmsFixedAssetTransfer) object;
        if ((this.transferId == null && other.transferId != null) || (this.transferId != null && !this.transferId.equals(other.transferId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "transferNo=" + transferNo;
    }

    public void addTransferDetail(MmsFixedAssetTransferDtl mmsTransferDetail) {

        if (mmsTransferDetail.getTransferId() != this) {
            this.getMmsFixedAssetTransferDtlList().add(mmsTransferDetail);
            mmsTransferDetail.setTransferId(this);
        }
    }

}
