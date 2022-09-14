
package et.gov.eep.mms.entity;

import et.gov.eep.fcms.entity.fixedasset.FmsDisposedItems;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
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
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author w_station
 */
@Entity
@Table(name = "MMS_DISPOSAL_DTL")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MmsDisposalDtl.findAll", query = "SELECT m FROM MmsDisposalDtl m"),
    @NamedQuery(name = "MmsDisposalDtl.findByDispDtlId", query = "SELECT m FROM MmsDisposalDtl m WHERE m.dispDtlId = :dispDtlId"),
    @NamedQuery(name = "MmsDisposalDtl.findByTagNo", query = "SELECT m FROM MmsDisposalDtl m WHERE m.tagNo = :tagNo"),
    @NamedQuery(name = "MmsDisposalDtl.findByItemName", query = "SELECT m FROM MmsDisposalDtl m WHERE m.itemName = :itemName"),
    @NamedQuery(name = "MmsDisposalDtl.findByItemLocation", query = "SELECT m FROM MmsDisposalDtl m WHERE m.itemLocation = :itemLocation"),
    @NamedQuery(name = "MmsDisposalDtl.findByQuantity", query = "SELECT m FROM MmsDisposalDtl m WHERE m.quantity = :quantity"),
    @NamedQuery(name = "MmsDisposalDtl.findBySellingPrice", query = "SELECT m FROM MmsDisposalDtl m WHERE m.sellingPrice = :sellingPrice"),
    @NamedQuery(name = "MmsDisposalDtl.findByBookValue", query = "SELECT m FROM MmsDisposalDtl m WHERE m.bookValue = :bookValue"),
    @NamedQuery(name = "MmsDisposalDtl.findByPurchasedPrice", query = "SELECT m FROM MmsDisposalDtl m WHERE m.purchasedPrice = :purchasedPrice"),
    @NamedQuery(name = "MmsDisposalDtl.findByDisposalMethod", query = "SELECT m FROM MmsDisposalDtl m WHERE m.disposalMethod = :disposalMethod"),
    @NamedQuery(name = "MmsDisposalDtl.findByDispGain", query = "SELECT m FROM MmsDisposalDtl m WHERE m.dispGain = :dispGain")})
public class MmsDisposalDtl implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MMS_DISP_DTL_SEQ")
    @SequenceGenerator(name = "MMS_DISP_DTL_SEQ", sequenceName = "MMS_DISP_DTL_SEQ", allocationSize = 1)
    @Basic(optional = false)
    @NotNull
    @Column(name = "DISP_DTL_ID", nullable = false, precision = 0, scale = -127)
    private Integer dispDtlId;
    @Size(max = 190)
    @Column(name = "TAG_NO", length = 190)
    private String tagNo;
    @Size(max = 30)
    @Column(name = "ITEM_NAME", length = 30)
    private String itemName;
    @Column(name = "PURCHASED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date purchasedDate;
    @Column(name = "ITEM_LOCATION", length = 30)
    private String itemLocation;
    @Column(name = "QUANTITY")
    private BigInteger quantity;
    @Column(name = "SELLING_PRICE", precision = 126)
    private BigDecimal sellingPrice;
    @Column(name = "BOOK_VALUE")
    private BigDecimal bookValue;
    @Column(name = "GAIN_OR_LOSS")
    private BigDecimal gainOrLoss;
    @Column(name = "PURCHASED_PRICE")
    private BigInteger purchasedPrice;
    @Size(max = 30)
    @Column(name = "DISPOSAL_METHOD", length = 30)
    private String disposalMethod;
    @Size(max = 30)
    @Column(name = "DISP_GAIN", length = 30)
    private String dispGain;
    @Size(max = 50)
    @Column(name = "ACCOUNT_CODE", length = 50)
    private String accountCode;
    @JoinColumn(name = "DISP_ID", referencedColumnName = "DISP_ID")
    @ManyToOne
    private MmsDisposal dispId;
    @OneToMany(mappedBy = "mmsDisposedFk")
    private List<FmsDisposedItems> fmsDisposedItemsList;
    public MmsDisposalDtl() {
    }

    public MmsDisposalDtl(Integer dispDtlId) {
        this.dispDtlId = dispDtlId;
    }

    public Integer getDispDtlId() {
        return dispDtlId;
    }

    public void setDispDtlId(Integer dispDtlId) {
        this.dispDtlId = dispDtlId;
    }

    public String getTagNo() {
        return tagNo;
    }

    public void setTagNo(String tagNo) {
        this.tagNo = tagNo;
    }

    public Date getPurchasedDate() {
        return purchasedDate;
    }

    public void setPurchasedDate(Date purchasedDate) {
        this.purchasedDate = purchasedDate;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemLocation() {
        return itemLocation;
    }

    public void setItemLocation(String itemLocation) {
        this.itemLocation = itemLocation;
    }

    public String getAccountCode() {
        return accountCode;
    }

    public void setAccountCode(String accountCode) {
        this.accountCode = accountCode;
    }
    

    public BigInteger getQuantity() {
        return quantity;
    }

    public void setQuantity(BigInteger quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(BigDecimal sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public BigDecimal getGainOrLoss() {
        return gainOrLoss;
    }

    public void setGainOrLoss(BigDecimal gainOrLoss) {
        this.gainOrLoss = gainOrLoss;
    }

    
    public BigDecimal getBookValue() {
        return bookValue;
    }

    public void setBookValue(BigDecimal bookValue) {
        this.bookValue = bookValue;
    }

    public BigInteger getPurchasedPrice() {
        return purchasedPrice;
    }

    public void setPurchasedPrice(BigInteger purchasedPrice) {
        this.purchasedPrice = purchasedPrice;
    }

    public String getDisposalMethod() {
        return disposalMethod;
    }

    public void setDisposalMethod(String disposalMethod) {
        this.disposalMethod = disposalMethod;
    }

    public String getDispGain() {
        return dispGain;
    }

    public void setDispGain(String dispGain) {
        this.dispGain = dispGain;
    }

    public MmsDisposal getDispId() {
        return dispId;
    }

    public void setDispId(MmsDisposal dispId) {
        this.dispId = dispId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (dispDtlId != null ? dispDtlId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MmsDisposalDtl)) {
            return false;
        }
        MmsDisposalDtl other = (MmsDisposalDtl) object;
        if ((this.dispDtlId == null && other.dispDtlId != null) || (this.dispDtlId != null && !this.dispDtlId.equals(other.dispDtlId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return tagNo;
    }

    @XmlTransient
    public List<FmsDisposedItems> getFmsDisposedItemsList() {
        return fmsDisposedItemsList;
    }

    public void setFmsDisposedItemsList(List<FmsDisposedItems> fmsDisposedItemsList) {
        this.fmsDisposedItemsList = fmsDisposedItemsList;
    }
}
