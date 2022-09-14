
package et.gov.eep.mms.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author w_station
 */
@Entity
@Table(name = "MMS_STOCK_ITEM_LOST_DTL")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MmsStockItemLostDtl.findAll", query = "SELECT m FROM MmsStockItemLostDtl m"),
    @NamedQuery(name = "MmsStockItemLostDtl.findByLostItemDetail", query = "SELECT m FROM MmsStockItemLostDtl m WHERE m.lostItemDetail = :lostItemDetail"),
    @NamedQuery(name = "MmsStockItemLostDtl.findByBookValue", query = "SELECT m FROM MmsStockItemLostDtl m WHERE m.bookValue = :bookValue"),
    @NamedQuery(name = "MmsStockItemLostDtl.findByDescription", query = "SELECT m FROM MmsStockItemLostDtl m WHERE m.description = :description"),
    @NamedQuery(name = "MmsStockItemLostDtl.findByDuration", query = "SELECT m FROM MmsStockItemLostDtl m WHERE m.duration = :duration"),
    @NamedQuery(name = "MmsStockItemLostDtl.findByItemName", query = "SELECT m FROM MmsStockItemLostDtl m WHERE m.itemName = :itemName"),
    @NamedQuery(name = "MmsStockItemLostDtl.findByRemark", query = "SELECT m FROM MmsStockItemLostDtl m WHERE m.remark = :remark"),
    @NamedQuery(name = "MmsStockItemLostDtl.findByItemCode", query = "SELECT m FROM MmsStockItemLostDtl m WHERE m.itemCode = :itemCode")})
public class MmsStockItemLostDtl implements Serializable {

    private static final long serialVersionUID = 1L;
   
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQUENCE_STOCK_LOST_DTL")
    @SequenceGenerator(name = "SEQUENCE_STOCK_LOST_DTL", sequenceName = "SEQUENCE_STOCK_LOST_DTL", allocationSize = 1)
    @Basic(optional = false)
    @NotNull
    @Column(name = "LOST_ITEM_DETAIL", nullable = false, precision = 0, scale = -127)
    private BigDecimal lostItemDetail;
    @Column(name = "BOOK_VALUE") 
    private BigDecimal bookValue;
    @Size(max = 100)
    @Column(name = "DESCRIPTION", length = 100)
    private String description;
    @Size(max = 70)
    @Column(name = "DURATION", length = 70)
    private String duration;
    @Size(max = 70)
    @Column(name = "ITEM_NAME", length = 70)
    private String itemName;
    @Size(max = 20)
    @Column(name = "REMARK", length = 20)
    private String remark;
    @Size(max = 70)
    @Column(name = "ITEM_CODE", length = 70)
    private String itemCode;
    @Column(name = "QUANTITY") 
    private Integer quantity;   
    @JoinColumn(name = "LOST_ITEM_ID", referencedColumnName = "LOST_STOCK_ID")
    @ManyToOne
    private MmsStockItemLost lostStockId;

    public MmsStockItemLostDtl() {
    }

    public MmsStockItemLostDtl(BigDecimal lostItemDetail) {
        this.lostItemDetail = lostItemDetail;
    }

    public BigDecimal getLostItemDetail() {
        return lostItemDetail;
    }

    public void setLostItemDetail(BigDecimal lostItemDetail) {
        this.lostItemDetail = lostItemDetail;
    }

    public BigDecimal getBookValue() {
        return bookValue;
    }

    public void setBookValue(BigDecimal bookValue) {
        this.bookValue = bookValue;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public MmsStockItemLost getLostStockId() {
        return lostStockId;
    }

    public void setLostStockId(MmsStockItemLost lostStockId) {
        this.lostStockId = lostStockId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (lostItemDetail != null ? lostItemDetail.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {   
        if (!(object instanceof MmsStockItemLostDtl)) {
            return false;
        }
        MmsStockItemLostDtl other = (MmsStockItemLostDtl) object;
        if ((this.lostItemDetail == null && other.lostItemDetail != null) || (this.lostItemDetail != null && !this.lostItemDetail.equals(other.lostItemDetail))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.MmsStockItemLostDtl[ lostItemDetail=" + lostItemDetail + " ]";
    }

}
