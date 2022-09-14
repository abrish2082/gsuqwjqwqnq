
package et.gov.eep.mms.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
@Table(name = "MMS_STOCK_DISPOSAL_DTL")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MmsStockDisposalDtl.findAll", query = "SELECT m FROM MmsStockDisposalDtl m"),
    @NamedQuery(name = "MmsStockDisposalDtl.findByStockDtlId", query = "SELECT m FROM MmsStockDisposalDtl m WHERE m.stockDtlId = :stockDtlId"),
    @NamedQuery(name = "MmsStockDisposalDtl.findByItemCode", query = "SELECT m FROM MmsStockDisposalDtl m WHERE m.itemCode = :itemCode"),
    @NamedQuery(name = "MmsStockDisposalDtl.findByItemName", query = "SELECT m FROM MmsStockDisposalDtl m WHERE m.itemName = :itemName"),
    @NamedQuery(name = "MmsStockDisposalDtl.findByUnitMeasure", query = "SELECT m FROM MmsStockDisposalDtl m WHERE m.unitMeasure = :unitMeasure"),
    @NamedQuery(name = "MmsStockDisposalDtl.findByDescription", query = "SELECT m FROM MmsStockDisposalDtl m WHERE m.description = :description"),
    @NamedQuery(name = "MmsStockDisposalDtl.findByQuantity", query = "SELECT m FROM MmsStockDisposalDtl m WHERE m.quantity = :quantity"),
    @NamedQuery(name = "MmsStockDisposalDtl.findBySellingPrice", query = "SELECT m FROM MmsStockDisposalDtl m WHERE m.sellingPrice = :sellingPrice"),
    @NamedQuery(name = "MmsStockDisposalDtl.findByDisposalMethod", query = "SELECT m FROM MmsStockDisposalDtl m WHERE m.disposalMethod = :disposalMethod")})
public class MmsStockDisposalDtl implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MMS_STOCK_DISPOSAL_DET_SEQ")
    @SequenceGenerator(name = "MMS_STOCK_DISPOSAL_DET_SEQ", sequenceName = "MMS_STOCK_DISPOSAL_DET_SEQ", allocationSize = 1)
    @Basic(optional = false)
    @NotNull
    @Column(name = "STOCK_DTL_ID", nullable = false, precision = 0, scale = -127)
    private BigDecimal stockDtlId;
    @Size(max = 30)
    @Column(name = "ITEM_CODE", length = 30)
    private String itemCode;
    @Size(max = 30)
    @Column(name = "ITEM_NAME", length = 30)
    private String itemName;
    @Size(max = 30)
    @Column(name = "UNIT_MEASURE", length = 30)
    private String unitMeasure;
    @Size(max = 20)
    @Column(name = "DESCRIPTION", length = 20)
    private String description;
    @Column(name = "QUANTITY")
    private BigInteger quantity;
    @Column(name = "SELLING_PRICE", precision = 126)
    private Float sellingPrice;
    @Size(max = 30)
    @Column(name = "DISPOSAL_METHOD", length = 30)
    private String disposalMethod;
    @JoinColumn(name = "STOCK_ID", referencedColumnName = "STOCK_ID")
    @ManyToOne
    private MmsStockDisposal stockId;
    @JoinColumn(name = "ITEM_ID", referencedColumnName = "MATERIAL_ID")
    @ManyToOne
    private MmsItemRegistration itemId;
    public MmsStockDisposalDtl() {
    }

    public MmsStockDisposalDtl(BigDecimal stockDtlId) {
        this.stockDtlId = stockDtlId;
    }

    public BigDecimal getStockDtlId() {
        return stockDtlId;
    }

    public void setStockDtlId(BigDecimal stockDtlId) {
        this.stockDtlId = stockDtlId;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getUnitMeasure() {
        return unitMeasure;
    }

    public void setUnitMeasure(String unitMeasure) {
        this.unitMeasure = unitMeasure;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigInteger getQuantity() {
        return quantity;
    }

    public void setQuantity(BigInteger quantity) {
        this.quantity = quantity;
    }

    public Float getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(Float sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public String getDisposalMethod() {
        return disposalMethod;
    }

    public void setDisposalMethod(String disposalMethod) {
        this.disposalMethod = disposalMethod;
    }

    public MmsStockDisposal getStockId() {
        return stockId;
    }

    public void setStockId(MmsStockDisposal stockId) {
        this.stockId = stockId;
    }

    public MmsItemRegistration getItemId() {
        return itemId;
    }

    public void setItemId(MmsItemRegistration itemId) {
        this.itemId = itemId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (stockDtlId != null ? stockDtlId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        
        if (!(object instanceof MmsStockDisposalDtl)) {
            return false;
        }
        MmsStockDisposalDtl other = (MmsStockDisposalDtl) object;
        if ((this.stockDtlId == null && other.stockDtlId != null) || (this.stockDtlId != null && !this.stockDtlId.equals(other.stockDtlId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return itemCode;
    }

}
