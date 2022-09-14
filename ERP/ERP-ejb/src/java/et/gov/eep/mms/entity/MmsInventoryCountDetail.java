
package et.gov.eep.mms.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author kimmyo
 */
@Entity
@Table(name = "MMS_INVENTORY_COUNT_DETAIL", catalog = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MmsInventoryCountDetail.findAll", query = "SELECT m FROM MmsInventoryCountDetail m"),
    @NamedQuery(name = "MmsInventoryCountDetail.findByInventCountDetId", query = "SELECT m FROM MmsInventoryCountDetail m WHERE m.inventCountDetId = :inventCountDetId"),
    @NamedQuery(name = "MmsInventoryCountDetail.findByItemCode", query = "SELECT m FROM MmsInventoryCountDetail m WHERE m.itemCode = :item_code"),
    @NamedQuery(name = "MmsInventoryCountDetail.findByRemark", query = "SELECT m FROM MmsInventoryCountDetail m WHERE m.remark = :remark"),
    @NamedQuery(name = "MmsInventoryCountDetail.findByQuantity", query = "SELECT m FROM MmsInventoryCountDetail m WHERE m.quantity = :quantity"),
    @NamedQuery(name = "MmsInventoryCountDetail.findByTransferCondition", query = "SELECT m FROM MmsInventoryCountDetail m WHERE m.transferCondition = :transferCondition"),
    @NamedQuery(name = "MmsInventoryCountDetail.findByyear", query = "SELECT m.itemCode.matCode FROM MmsInventoryCountDetail m WHERE m.inventoryCountId.budgetYear = :budgetYear"),
    @NamedQuery(name = "MmsInventoryCountDetail.findListByyear", query = "SELECT m FROM MmsInventoryCountDetail m WHERE m.inventoryCountId.budgetYear = :budgetYear"),
    @NamedQuery(name = "MmsInventoryCountDetail.findByFunctionality", query = "SELECT m FROM MmsInventoryCountDetail m WHERE m.functionality = :functionality")})
public class MmsInventoryCountDetail implements Serializable {
    @JoinColumn(name = "ITEM_ID", referencedColumnName = "MATERIAL_ID")
    @ManyToOne
    private MmsItemRegistration itemCode;
    @Column(name = "RECIEVING_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date recievingDate;
    private static final long serialVersionUID = 1L;
     @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MMS_INV_COUNT_DETA_SEQ")
    @SequenceGenerator(name = "MMS_INV_COUNT_DETA_SEQ", sequenceName = "MMS_INV_COUNT_DETA_SEQ", allocationSize = 1)
    @Column(name = "INVENT_COUNT_DET_ID", nullable = false)
    private Integer inventCountDetId;
      @Size(max = 255)
    @Column(name = "REMARK", length = 255)
    private String remark;
    @Column(name = "QUANTITY")
    private Long quantity;
    @Size(max = 45)
    @Column(name = "TRANSFER_CONDITION", length = 45)
    private String transferCondition;
    @Size(max = 45)
    @Column(name = "FUNCTIONALITY", length = 45)
    private String functionality;
    @JoinColumn(name = "INVENTORY_COUNT_ID", referencedColumnName = "INVENTORY_COUNT_ID")
    @ManyToOne
    private MmsInventoryCounting inventoryCountId;
    @Transient
    private Integer binBalance;

    /**
     *
     */
    public MmsInventoryCountDetail() {
    }

    /**
     *
     * @param inventCountDetId
     */
    public MmsInventoryCountDetail(Integer inventCountDetId) {
        this.inventCountDetId = inventCountDetId;
    }

    /**
     *
     * @return
     */
    public Integer getInventCountDetId() {
        return inventCountDetId;
    }

    /**
     *
     * @param inventCountDetId
     */
    public void setInventCountDetId(Integer inventCountDetId) {
        this.inventCountDetId = inventCountDetId;
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
    public Long getQuantity() {
        return quantity;
    }

    /**
     *
     * @param quantity
     */
    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }
   
    /**
     *
     * @return
     */
    public String getTransferCondition() {
        return transferCondition;
    }

    /**
     *
     * @param transferCondition
     */
    public void setTransferCondition(String transferCondition) {
        this.transferCondition = transferCondition;
    }

    /**
     *
     * @return
     */
    public String getFunctionality() {
        return functionality;
    }

    /**
     *
     * @param functionality
     */
    public void setFunctionality(String functionality) {
        this.functionality = functionality;
    }

    /**
     *
     * @return
     */
    public MmsInventoryCounting getInventoryCountId() {
        return inventoryCountId;
    }

    /**
     *
     * @param inventoryCountId
     */
    public void setInventoryCountId(MmsInventoryCounting inventoryCountId) {
        this.inventoryCountId = inventoryCountId;
    }
    
    /**
     *
     * @return
     */
    public Date getRecievingDate() {
        return recievingDate;
    }

    /**
     *
     * @param recievingDate
     */
    public void setRecievingDate(Date recievingDate) {
        this.recievingDate = recievingDate;
    }

    /**
     *
     * @return
     */
    public MmsItemRegistration getItemCode() {
        return itemCode;
    }

    /**
     *
     * @param itemCode
     */
    public void setItemCode(MmsItemRegistration itemCode) {
        this.itemCode = itemCode;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (inventCountDetId != null ? inventCountDetId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        
        if (!(object instanceof MmsInventoryCountDetail)) {
            return false;
        }
        MmsInventoryCountDetail other = (MmsInventoryCountDetail) object;
        if ((this.inventCountDetId == null && other.inventCountDetId != null) || (this.inventCountDetId != null && !this.inventCountDetId.equals(other.inventCountDetId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return itemCode.getMatCode();
    }

    public Integer getBinBalance() {
        return binBalance;
    }

    public void setBinBalance(Integer binBalance) {
        this.binBalance = binBalance;
    }
 
}
