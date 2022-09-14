
package et.gov.eep.mms.entity;

import java.io.Serializable;
import java.math.BigDecimal;
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
@Table(name = "MMS_NON_FIXED_ASSET_RETURN_DTL")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MmsNonFixedAssetReturnDtl.findAll", query = "SELECT m FROM MmsNonFixedAssetReturnDtl m"),
    @NamedQuery(name = "MmsNonFixedAssetReturnDtl.findByNfaDttlId", query = "SELECT m FROM MmsNonFixedAssetReturnDtl m WHERE m.nfaDttlId = :nfaDttlId"),
    @NamedQuery(name = "MmsNonFixedAssetReturnDtl.findByItemCode", query = "SELECT m FROM MmsNonFixedAssetReturnDtl m WHERE m.itemCode = :itemCode"),
    @NamedQuery(name = "MmsNonFixedAssetReturnDtl.findByItemName", query = "SELECT m FROM MmsNonFixedAssetReturnDtl m WHERE m.itemName = :itemName"),
    @NamedQuery(name = "MmsNonFixedAssetReturnDtl.findByUnitOfMeasure", query = "SELECT m FROM MmsNonFixedAssetReturnDtl m WHERE m.unitOfMeasure = :unitOfMeasure"),
    @NamedQuery(name = "MmsNonFixedAssetReturnDtl.findByQuantity", query = "SELECT m FROM MmsNonFixedAssetReturnDtl m WHERE m.quantity = :quantity"),
    @NamedQuery(name = "MmsNonFixedAssetReturnDtl.findByUnitPrice", query = "SELECT m FROM MmsNonFixedAssetReturnDtl m WHERE m.unitPrice = :unitPrice")})
public class MmsNonFixedAssetReturnDtl implements Serializable {

    private static final long serialVersionUID = 1L;
     @Basic(optional = false)
    @NotNull
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MMS_NFAR_SEQQ_DTL")
    @SequenceGenerator(name = "MMS_NFAR_SEQQ_DTL", sequenceName = "MMS_NFAR_SEQQ_DTL", allocationSize = 1)
    @Column(name = "NFA_DTTL_ID", nullable = false, precision = 0, scale = -127)
    private BigDecimal nfaDttlId;
    @Size(max = 30)
    @Column(name = "ITEM_CODE", length = 30)
    private String itemCode;
    @Size(max = 30)
    @Column(name = "ITEM_NAME", length = 30)
    private String itemName;
    @Size(max = 30)
    @Column(name = "UNIT_OF_MEASURE", length = 30)
    private String unitOfMeasure;
    @Column(name = "QUANTITY")
    private BigDecimal quantity;
    @Size(max = 40)
    @Column(name = "ITEM_STATUS", length = 40)
    private String itemStatus;
    @Column(name = "UNIT_PRICE")
    private BigDecimal unitPrice;
    @JoinColumn(name = "NFA_ID", referencedColumnName = "NFA_ID")
    @ManyToOne
    private MmsNonFixedAssetReturn nfaId;
    @JoinColumn(name = "SIV_ID", referencedColumnName = "SIV_ID")
    @ManyToOne
    private MmsSiv sivId;
    @JoinColumn(name = "ITEM_ID", referencedColumnName = "MATERIAL_ID")
    @ManyToOne
    private MmsItemRegistration itemId;

    public MmsNonFixedAssetReturnDtl() {
    }

    public MmsNonFixedAssetReturnDtl(BigDecimal nfaDttlId) {
        this.nfaDttlId = nfaDttlId;
    }

    public BigDecimal getNfaDttlId() {
        return nfaDttlId;
    }

    public void setNfaDttlId(BigDecimal nfaDttlId) {
        this.nfaDttlId = nfaDttlId;
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

    public String getUnitOfMeasure() {
        return unitOfMeasure;
    }

    public void setUnitOfMeasure(String unitOfMeasure) {
        this.unitOfMeasure = unitOfMeasure;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public MmsNonFixedAssetReturn getNfaId() {
        return nfaId;
    }

    public void setNfaId(MmsNonFixedAssetReturn nfaId) {
        this.nfaId = nfaId;
    }

    public MmsSiv getSivId() {
        return sivId;
    }

    public void setSivId(MmsSiv sivId) {
        this.sivId = sivId;
    }
    

    public String getItemStatus() {
        return itemStatus;
    }

    public void setItemStatus(String itemStatus) {
        this.itemStatus = itemStatus;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (nfaDttlId != null ? nfaDttlId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
       
        if (!(object instanceof MmsNonFixedAssetReturnDtl)) {
            return false;
        }
        MmsNonFixedAssetReturnDtl other = (MmsNonFixedAssetReturnDtl) object;
        if ((this.nfaDttlId == null && other.nfaDttlId != null) || (this.nfaDttlId != null && !this.nfaDttlId.equals(other.nfaDttlId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.eep.mms.entity.MmsNonFixedAssetReturnDtl[ nfaDttlId=" + nfaDttlId + " ]";
    }

    public MmsItemRegistration getItemId() {
        return itemId;
    }

    public void setItemId(MmsItemRegistration itemId) {
        this.itemId = itemId;
    }

    
}
