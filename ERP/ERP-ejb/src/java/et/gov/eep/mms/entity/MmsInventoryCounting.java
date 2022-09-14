
package et.gov.eep.mms.entity;

import et.gov.eep.commonApplications.entity.WfMmsProcessed;
import et.gov.eep.fcms.entity.admin.FmsAccountingPeriod;
import et.gov.eep.hrms.entity.committee.HrCommittees;
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
import javax.persistence.Transient;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author kimmyo
 */
@Entity
@Table(name = "MMS_INVENTORY_COUNTING", catalog = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MmsInventoryCounting.findAll", query = "SELECT m FROM MmsInventoryCounting m"),
    @NamedQuery(name = "MmsInventoryCounting.findByInventoryCountId", query = "SELECT m FROM MmsInventoryCounting m WHERE m.inventoryCountId = :inventoryCountId"),
    @NamedQuery(name = "MmsInventoryCounting.findByCheckedDate", query = "SELECT m FROM MmsInventoryCounting m WHERE m.checkedDate = :checkedDate"),
    @NamedQuery(name = "MmsInventoryCounting.findByCountedDate", query = "SELECT m FROM MmsInventoryCounting m WHERE m.countedDate = :countedDate"),
    @NamedQuery(name = "MmsInventoryCounting.findByApprovedBy", query = "SELECT m FROM MmsInventoryCounting m WHERE m.approvedBy = :approvedBy"),
    @NamedQuery(name = "MmsInventoryCounting.findByApprovedStatus", query = "SELECT m FROM MmsInventoryCounting m WHERE m.approvedStatus = :approvedStatus"),
    @NamedQuery(name = "MmsInventoryCounting.findByWfStatus", query = "SELECT m FROM MmsInventoryCounting m WHERE m.status = :status"),
    @NamedQuery(name = "MmsInventoryCounting.findByBudgetYear", query = "SELECT m FROM MmsInventoryCounting m WHERE m.budgetYear = :budgetYear"),
    @NamedQuery(name = "MmsInventoryCounting.findByInventoryNo", query = "SELECT m FROM MmsInventoryCounting m WHERE m.inventoryCountNo LIKE :inventoryCountNo"),
    @NamedQuery(name = "MmsInventoryCounting.findByInventoryNoAndStoreId", query = "SELECT m FROM MmsInventoryCounting m WHERE m.inventoryCountNo LIKE :inventoryCountNo AND m.workUnit=:workUnit AND m.processedBy=:processedBy"),
    @NamedQuery(name = "MmsInventoryCounting.findByInventoryNoAndStoreIdAndProcessedBy", query = "SELECT m FROM MmsInventoryCounting m WHERE m.inventoryCountNo LIKE :inventoryCountNo AND m.workUnit=:workUnit AND m.processedBy=:processedBy"),
    @NamedQuery(name = "MmsInventoryCounting.findByBudgetYearAndStoreId", query = "SELECT m FROM MmsInventoryCounting m WHERE m.budgetYear LIKE :budgetYear AND m.workUnit=:workUnit"),
    @NamedQuery(name = "MmsInventoryCounting.findByBudgetYearAndStoreIdAndProcessedBy", query = "SELECT m FROM MmsInventoryCounting m WHERE m.budgetYear LIKE :budgetYear AND m.workUnit=:workUnit AND m.processedBy=:processedBy"),
    @NamedQuery(name = "MmsInventoryCounting.findByStoreId", query = "SELECT m FROM MmsInventoryCounting m WHERE m.workUnit = :workUnit"),
    @NamedQuery(name = "MmsInventoryCounting.findByStoreIdAndProcessedBy", query = "SELECT m FROM MmsInventoryCounting m WHERE m.workUnit = :workUnit AND m.processedBy=:processedBy"),
    @NamedQuery(name = "MmsInventoryCounting.SearchByStoreNameAndBudgetYear", query = " SELECT m FROM MmsInventoryCounting m  JOIN m.workUnit s WHERE m.budgetYear = :budgetYear AND s.storeName=:storeName"),
    @NamedQuery(name = "MmsInventoryCounting.findByInventoryIdMaximum", query = "SELECT m FROM MmsInventoryCounting m WHERE m.inventoryCountId = (SELECT Max(c.inventoryCountId)  from MmsInventoryCounting c)"),
    @NamedQuery(name = "MmsInventoryCounting.findByApprovedDate", query = "SELECT m FROM MmsInventoryCounting m WHERE m.approvedDate = :approvedDate")})
public class MmsInventoryCounting implements Serializable {

    @Size(max = 45)
    @Column(name = "REGION", length = 45)
    private String region;
    @OneToMany(mappedBy = "invCountId",cascade = CascadeType.ALL)
    private List<MmsInventoryBalanceSheet> mmsInventoryBalanceSheetList;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MMS_INV_COUNT_SEQ")
    @SequenceGenerator(name = "MMS_INV_COUNT_SEQ", sequenceName = "MMS_INV_COUNT_SEQ", allocationSize = 1)
    @Column(name = "INVENTORY_COUNT_ID", nullable = false)
    private Integer inventoryCountId;
    @Column(name = "STATUS")
    private Integer status;
    @Size(max = 45)
    @Column(name = "INVENTORY_COUNT_NO", length = 45)
    private String inventoryCountNo;
    @Size(max = 45)
    @Column(name = "BUDGET_YEAR", length = 45)
    private String budgetYear;
    @Column(name = "APPROVED_STATUS")
    private Integer approvedStatus;
    @JoinColumn(name = "COMMITTEE_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrCommittees committeeId;
    @JoinColumn(name = "ACTIVE_ACCOUNTING_PERIOD", referencedColumnName = "ACOUNTIG_PERIOD_ID")
    @ManyToOne
    private FmsAccountingPeriod activeAccountingPeriod;
     @Size(max = 50)
    @Column(name = "APPROVED_BY")
    private String approvedBy;
    @Size(max = 20)
    @Column(name = "CHECKED_DATE")
    private String checkedDate;
    @Size(max = 20)
    @Column(name = "COUNTED_DATE")
    private String countedDate;
    @Size(max = 20)
    @Column(name = "APPROVED_DATE")
    private String approvedDate;
    @Column(name = "COUNT_TYPE")
    private Integer countType;
    @Size(max = 255)
    @Column(name = "REMARK")
    private String remark;
    @Size(max = 255)
    @Column(name = "COMMITTEE_MEMBERS")
    private String committeMembers;
    @Column(name = "PROCESSED_BY")
    private Integer processedBy;
    @Column(name = "COMMENT_GIVEN")
    private String commentGiven;
    @Size(max = 20)
    @Column(name = "PROCESSED_ON")
    private String processedOn;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "inventoryCountId")
    private List<MmsInventoryCountDetail> mmsInventoryCountDetailList;
    @JoinColumn(name = "WORK_UNIT", referencedColumnName = "STORE_ID")
    @ManyToOne
    private MmsStoreInformation workUnit;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "inventoryCountId")
    private List<WfMmsProcessed> wfMmsProcessedList;
    @Transient
    private String columnName;
    @Transient
    private String columnValue;
    /**
     *
     */
    public MmsInventoryCounting() {
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

    /**
     *
     * @param inventoryCountId
     */
    public MmsInventoryCounting(Integer inventoryCountId) {
        this.inventoryCountId = inventoryCountId;
    }

    /**
     *
     * @return
     */
    public Integer getInventoryCountId() {
        return inventoryCountId;
    }

    /**
     *
     * @param inventoryCountId
     */
    public void setInventoryCountId(Integer inventoryCountId) {
        this.inventoryCountId = inventoryCountId;
    }

    /**
     *
     * @return
     */
    /**
     *
     * @return
     */
    public String getBudgetYear() {
        return budgetYear;
    }

    /**
     *
     * @param budgetYear
     */
    public void setBudgetYear(String budgetYear) {
        this.budgetYear = budgetYear;
    }

    /**
     *
     * @return
     */
    public String getCheckedDate() {
        return checkedDate;
    }

    /**
     *
     * @param checkedDate
     */
    public void setCheckedDate(String checkedDate) {
        this.checkedDate = checkedDate;
    }

    public HrCommittees getCommitteeId() {
        return committeeId;
    }

    public void setCommitteeId(HrCommittees committeeId) {
        this.committeeId = committeeId;
    }

    public FmsAccountingPeriod getActiveAccountingPeriod() {
        return activeAccountingPeriod;
    }

    public void setActiveAccountingPeriod(FmsAccountingPeriod activeAccountingPeriod) {
        this.activeAccountingPeriod = activeAccountingPeriod;
    }

    /**
     *
     * @return
     */
////
////    /**
////     *
////     * @param countedBy3
////     */
////    /**
////     *
////     * @return
////     */
//// 
////
////    /**
////     *
////     * @param countedBy2
////     */
////   
////
////    /**
////     *
////     * @return
////     */

////
////    /**
////     *
////     * @param countedBy
////     */
////    
//
//    /**
//     *
//     * @return
//     */
//    
//
//    /**
//     *
//     * @param checkedBy
//     */

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
    public String getCountedDate() {
        return countedDate;
    }

    /**
     *
     * @param countedDate
     */
    public void setCountedDate(String countedDate) {
        this.countedDate = countedDate;
    }

    /**
     *
     * @return
     */
    public String getApprovedDate() {
        return approvedDate;
    }

    /**
     *
     * @param approvedDate
     */
    public void setApprovedDate(String approvedDate) {
        this.approvedDate = approvedDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     *
     * @return
     */
    @XmlTransient
    public List<MmsInventoryCountDetail> getMmsInventoryCountDetailList() {
        if (mmsInventoryCountDetailList == null) {
            mmsInventoryCountDetailList = new ArrayList<>();
        }
        return mmsInventoryCountDetailList;
    }

    /**
     *
     * @param mmsInventoryCountDetailList
     */
    public void setMmsInventoryCountDetailList(List<MmsInventoryCountDetail> mmsInventoryCountDetailList) {
        this.mmsInventoryCountDetailList = mmsInventoryCountDetailList;
    }

    /**
     *
     * @return
     */
    public MmsStoreInformation getWorkUnit() {
        return workUnit;
    }

    /**
     *
     * @param workUnit
     */
    public void setWorkUnit(MmsStoreInformation workUnit) {
        this.workUnit = workUnit;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (inventoryCountId != null ? inventoryCountId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        
        if (!(object instanceof MmsInventoryCounting)) {
            return false;
        }
        MmsInventoryCounting other = (MmsInventoryCounting) object;
        if ((this.inventoryCountId == null && other.inventoryCountId != null) || (this.inventoryCountId != null && !this.inventoryCountId.equals(other.inventoryCountId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "CountId " + inventoryCountId;
    }

    /**
     *
     * @param InventoryCountDetail
     */
    public void addToInventoryCountDetail(MmsInventoryCountDetail InventoryCountDetail) {
        if (InventoryCountDetail.getInventoryCountId() != this) {
            this.getMmsInventoryCountDetailList().add(InventoryCountDetail);
            InventoryCountDetail.setInventoryCountId(this);
        }

    }

    /**
     *
     * @return
     */
    public String getRegion() {
        return region;
    }

    /**
     *
     * @param region
     */
    public void setRegion(String region) {
        this.region = region;
    }

    public String getInventoryCountNo() {
        return inventoryCountNo;
    }

    public void setInventoryCountNo(String inventoryCountNo) {
        this.inventoryCountNo = inventoryCountNo;
    }

    public Integer getCountType() {
        return countType;
    }

    public void setCountType(Integer countType) {
        this.countType = countType;
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

    
    public Integer getApprovedStatus() {
        return approvedStatus;
    }

    public void setApprovedStatus(Integer approvedStatus) {
        this.approvedStatus = approvedStatus;
    }

    public String getCommitteMembers() {
        return committeMembers;
    }

    public void setCommitteMembers(String committeMembers) {
        this.committeMembers = committeMembers;
    }

      public void addInventoryCountIdToWorkflow(WfMmsProcessed wfMmsProcessed) {
        if (wfMmsProcessed.getInventoryCountId() != this) {
            this.getWfMmsProcessedList().add(wfMmsProcessed);
            
            wfMmsProcessed.setInventoryCountId(this);
        }
    }
}
