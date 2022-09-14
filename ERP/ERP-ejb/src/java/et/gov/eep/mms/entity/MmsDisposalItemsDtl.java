
package et.gov.eep.mms.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import javax.persistence.Basic;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author w_station
 */
@Entity
@Table(name = "MMS_DISPOSAL_ITEMS_DTL")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MmsDisposalItemsDtl.findAll", query = "SELECT m FROM MmsDisposalItemsDtl m"),
    @NamedQuery(name = "MmsDisposalItemsDtl.findByDisposalDtlId", query = "SELECT m FROM MmsDisposalItemsDtl m WHERE m.disposalDtlId = :disposalDtlId"),
    @NamedQuery(name = "MmsDisposalItemsDtl.findByBookValue", query = "SELECT m FROM MmsDisposalItemsDtl m WHERE m.bookValue = :bookValue"),
    @NamedQuery(name = "MmsDisposalItemsDtl.findByDescription", query = "SELECT m FROM MmsDisposalItemsDtl m WHERE m.description = :description"),
    @NamedQuery(name = "MmsDisposalItemsDtl.findByItemName", query = "SELECT m FROM MmsDisposalItemsDtl m WHERE m.itemName = :itemName"),
    @NamedQuery(name = "MmsDisposalItemsDtl.findByItemStatus", query = "SELECT m FROM MmsDisposalItemsDtl m WHERE m.itemStatus = :itemStatus"),
    @NamedQuery(name = "MmsDisposalItemsDtl.findByPurchasedDate", query = "SELECT m FROM MmsDisposalItemsDtl m WHERE m.purchasedDate = :purchasedDate"),
    @NamedQuery(name = "MmsDisposalItemsDtl.findByTagNo", query = "SELECT m FROM MmsDisposalItemsDtl m WHERE m.tagNo = :tagNo"),
    @NamedQuery(name = "MmsDisposalItemsDtl.findByDisposalId", query = "SELECT m FROM MmsDisposalItemsDtl m WHERE m.disposalId.disposalId=:disposalId"),
    @NamedQuery(name = "MmsDisposalItemsDtl.findByUnitPrice", query = "SELECT m FROM MmsDisposalItemsDtl m WHERE m.unitPrice = :unitPrice")})
public class MmsDisposalItemsDtl implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id

    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MMS_DISPOSAL_SEQ_DTL")
    @SequenceGenerator(name = "MMS_DISPOSAL_SEQ_DTL", sequenceName = "MMS_DISPOSAL_SEQ_DTL", allocationSize = 1)
    @Basic(optional = false)
    @NotNull
    @Column(name = "DISPOSAL_DTL_ID", nullable = false, precision = 38, scale = 0)
    private BigDecimal disposalDtlId;
    @Column(name = "BOOK_VALUE", length = 30)
    private Integer bookValue;
    @Size(max = 30)
    @Column(name = "DESCRIPTION", length = 30)
    private String description;
    @Size(max = 30)
    @Column(name = "ITEM_NAME", length = 30)
    private String itemName;
    @Size(max = 30)
    @Column(name = "ITEM_STATUS", length = 30)
    private String itemStatus;
    @Column(name = "PURCHASED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date purchasedDate;
    @Size(max = 190)
    @Column(name = "TAG_NO", length = 190)
    private String tagNo;
    @Column(name = "UNIT_PRICE")
    private BigDecimal unitPrice;
    @Column(name = "ITEM_CODE")
    private String itemCode;
    @Size(max = 50)
    @Column(name = "ACCOUNT_CODE", length = 50)
    private String accountCode;
    @Column(name = "QUANTITY")
    private Integer quantity;

    @JoinColumn(name = "DISPOSAL_ID", referencedColumnName = "DISPOSAL_ID")
    @ManyToOne
    private MmsDisposalItems disposalId;
    @JoinColumn(name = "ITEM_ID", referencedColumnName = "MATERIAL_ID")
    @ManyToOne
    private MmsItemRegistration itemId;

    public MmsDisposalItemsDtl() {
    }

    public MmsDisposalItemsDtl(BigDecimal disposalDtlId) {
        this.disposalDtlId = disposalDtlId;
    }

    public BigDecimal getDisposalDtlId() {
        return disposalDtlId;
    }

    public void setDisposalDtlId(BigDecimal disposalDtlId) {
        this.disposalDtlId = disposalDtlId;
    }

    public Integer getBookValue() {
        return bookValue;
    }

    public void setBookValue(Integer bookValue) {
        this.bookValue = bookValue;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public MmsItemRegistration getItemId() {
        return itemId;
    }

    public void setItemId(MmsItemRegistration itemId) {
        this.itemId = itemId;
    }

    public String getAccountCode() {
        return accountCode;
    }

    public void setAccountCode(String accountCode) {
        this.accountCode = accountCode;
    }

    public String getItemStatus() {
        return itemStatus;
    }

    public void setItemStatus(String itemStatus) {
        this.itemStatus = itemStatus;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public Date getPurchasedDate() {
        return purchasedDate;
    }

    public void setPurchasedDate(Date purchasedDate) {
        this.purchasedDate = purchasedDate;
    }

    public String getTagNo() {
        return tagNo;
    }

    public void setTagNo(String tagNo) {
        this.tagNo = tagNo;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public MmsDisposalItems getDisposalId() {
        return disposalId;
    }

    public void setDisposalId(MmsDisposalItems disposalId) {
        this.disposalId = disposalId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (disposalDtlId != null ? disposalDtlId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        
        if (!(object instanceof MmsDisposalItemsDtl)) {
            return false;
        }
        MmsDisposalItemsDtl other = (MmsDisposalItemsDtl) object;
        if ((this.disposalDtlId == null && other.disposalDtlId != null) || (this.disposalDtlId != null && !this.disposalDtlId.equals(other.disposalDtlId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return tagNo;
    }

}
