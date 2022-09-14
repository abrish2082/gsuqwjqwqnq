
package et.gov.eep.mms.entity;

import et.gov.eep.commonApplications.entity.WfMmsProcessed;
import java.io.Serializable;
import java.util.ArrayList;
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
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author kimmyo
 */
@Entity
@Table(name = "MMS_INVENTORY_BALANCE_SHEET", catalog = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MmsInventoryBalanceSheet.findAll", query = "SELECT m FROM MmsInventoryBalanceSheet m"),
    @NamedQuery(name = "MmsInventoryBalanceSheet.findByIbsId", query = "SELECT m FROM MmsInventoryBalanceSheet m WHERE m.ibsId = :ibsId"),
    @NamedQuery(name = "MmsInventoryBalanceSheet.findByIBSIdMaximum", query = "SELECT m FROM MmsInventoryBalanceSheet m WHERE m.ibsId = (SELECT Max(c.ibsId)  from MmsInventoryBalanceSheet c)"),
    @NamedQuery(name = "MmsInventoryBalanceSheet.findByRemark", query = "SELECT m FROM MmsInventoryBalanceSheet m WHERE m.remark = :remark"),
    @NamedQuery(name = "MmsInventoryBalanceSheet.findByOldValue", query = "SELECT m FROM MmsInventoryBalanceSheet m WHERE m.oldValue = :oldValue"),
    @NamedQuery(name = "MmsInventoryBalanceSheet.findByDiffrence", query = "SELECT m FROM MmsInventoryBalanceSheet m WHERE m.diffrence = :diffrence"),
    @NamedQuery(name = "MmsInventoryBalanceSheet.findByCountingValue", query = "SELECT m FROM MmsInventoryBalanceSheet m WHERE m.countingValue = :countingValue"),
    @NamedQuery(name = "MmsInventoryBalanceSheet.findByStatus", query = "SELECT m FROM MmsInventoryBalanceSheet m WHERE m.status = :status"),
    @NamedQuery(name = "MmsInventoryBalanceSheet.findByStoreIdAndStatus", query = "SELECT m FROM MmsInventoryBalanceSheet m WHERE m.storeId = :storeId AND m.status=1"),
    @NamedQuery(name = "MmsInventoryBalanceSheet.findByBudgetYear", query = "SELECT m FROM MmsInventoryBalanceSheet m WHERE m.budgetYear = :budgetYear"),
    @NamedQuery(name = "MmsInventoryBalanceSheet.findByParameterStoreId", query = "SELECT m FROM MmsInventoryBalanceSheet m WHERE m.storeId = :storeId"),
    @NamedQuery(name = "MmsInventoryBalanceSheet.findByParameterStoreIdAndProcessedBy", query = "SELECT m FROM MmsInventoryBalanceSheet m WHERE m.storeId.storeId = :storeId AND m.processedBy=:processedBy"),
    @NamedQuery(name = "MmsInventoryBalanceSheet.findByStoreIdAndBudgetYearAndProcessedBy", query = "SELECT m FROM MmsInventoryBalanceSheet m WHERE m.storeId=:storeId AND m.budgetYear LIKE :budgetYear AND m.processedBy=:processedBy"),
    @NamedQuery(name = "MmsInventoryBalanceSheet.findByStoreIdAndBudgetYear", query = "SELECT m FROM MmsInventoryBalanceSheet m WHERE m.storeId=:storeId AND m.budgetYear LIKE :budgetYear")})

public class MmsInventoryBalanceSheet implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MMS_INV_BALNCE_SEQ")
    @SequenceGenerator(name = "MMS_INV_BALNCE_SEQ", sequenceName = "MMS_INV_BALNCE_SEQ", allocationSize = 1)
    @Column(name = "IBS_ID", nullable = false)
    private Integer ibsId;
    @Size(max = 255)
    @Column(name = "REMARK", length = 255)
    private String remark;
    @Size(max = 20)
    @Column(name = "APPROVED_DATE")
    private String approvedDate;
    @Column(name = "OLD_VALUE")
    private Long oldValue;
    @Column(name = "DIFFRENCE")
    private int diffrence;
    @Column(name = "COUNTING_VALUE")
    private Long countingValue;
    @Column(name = "STATUS")
    private Integer status;
    @Size(max = 45)
    @Column(name = "BUDGET_YEAR", length = 45)
    private String budgetYear;
    @Column(name = "PROCESSED_BY")
    private Integer processedBy;
    @Size(max = 50)
    @Column(name = "COMMENT_GIVEN")
    private String commentGiven;
    @Size(max = 20)
    @Column(name = "PROCESSED_ON")
    private String processedOn;
    @JoinColumn(name = "MATERIAL_ID", referencedColumnName = "MATERIAL_ID")
    @ManyToOne
    private MmsItemRegistration materialId;
    @JoinColumn(name = "STORE_ID", referencedColumnName = "STORE_ID")
    @ManyToOne
    private MmsStoreInformation storeId;
    @JoinColumn(name = "INV_COUNT_ID", referencedColumnName = "INVENTORY_COUNT_ID")
    @ManyToOne
    private MmsInventoryCounting invCountId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ibsId")
    private List<WfMmsProcessed> wfMmsProcessedList;
    @Transient
    private String columnName;
    @Transient
    private String columnValue;
    /**
     *
     */
    public MmsInventoryBalanceSheet() {
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

    public void setProcessedOn(String processedOn) {
        this.processedOn = processedOn;
    }

    /**
     *
     * @param ibsId
     */
    public MmsInventoryBalanceSheet(Integer ibsId) {
        this.ibsId = ibsId;
    }

    /**
     *
     * @return
     */
    public Integer getIbsId() {
        return ibsId;
    }

    /**
     *
     * @param ibsId
     */
    public void setIbsId(Integer ibsId) {
        this.ibsId = ibsId;
    }

    /**
     *
     * @return
     */
    public String getRemark() {
        return remark;
    }

    /**
     *
     * @param remark
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     *
     * @return
     */
    public Long getOldValue() {
        return oldValue;
    }

    /**
     *
     * @param oldValue
     */
    public void setOldValue(Long oldValue) {
        this.oldValue = oldValue;
    }

    /**
     *
     * @return
     */
    public int getDiffrence() {
        return diffrence;
    }

    /**
     *
     * @param diffrence
     */
    public void setDiffrence(int diffrence) {
        this.diffrence = diffrence;
    }

    /**
     *
     * @return
     */
    public Long getCountingValue() {
        return countingValue;
    }

    /**
     *
     * @param countingValue
     */
    public void setCountingValue(Long countingValue) {
        this.countingValue = countingValue;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

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
    public MmsInventoryCounting getInvCountId() {
        return invCountId;
    }

    /**
     *
     * @param invCountId
     */
    public void setInvCountId(MmsInventoryCounting invCountId) {
        this.invCountId = invCountId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ibsId != null ? ibsId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
       
        if (!(object instanceof MmsInventoryBalanceSheet)) {
            return false;
        }
        MmsInventoryBalanceSheet other = (MmsInventoryBalanceSheet) object;
        if ((this.ibsId == null && other.ibsId != null) || (this.ibsId != null && !this.ibsId.equals(other.ibsId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.eep.mms.entity.MmsInventoryBalanceSheet[ ibsId=" + ibsId + " ]";
    }

    public MmsItemRegistration getMaterialId() {
        return materialId;
    }

    public void setMaterialId(MmsItemRegistration materialId) {
        this.materialId = materialId;
    }

    public MmsStoreInformation getStoreId() {
        return storeId;
    }

    public void setStoreId(MmsStoreInformation storeId) {
        this.storeId = storeId;
    }

    public String getApprovedDate() {
        return approvedDate;
    }

    public void setApprovedDate(String approvedDate) {
        this.approvedDate = approvedDate;
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

    public void addInventoryBalanceSheetIdToWorkflow(WfMmsProcessed wfMmsProcessed) {
        if (wfMmsProcessed.getIbsId() != this) {
            this.getWfMmsProcessedList().add(wfMmsProcessed);

            wfMmsProcessed.setIbsId(this);
        }
    }
        }
