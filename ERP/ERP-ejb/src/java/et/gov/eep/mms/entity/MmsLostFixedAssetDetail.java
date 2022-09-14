
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
@Table(name = "MMS_LOST_FIXED_ASSET_DETAIL")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MmsLostFixedAssetDetail.findAll", query = "SELECT m FROM MmsLostFixedAssetDetail m"),
    @NamedQuery(name = "MmsLostFixedAssetDetail.findByLostItemDetailId", query = "SELECT m FROM MmsLostFixedAssetDetail m WHERE m.lostItemDetailId = :lostItemDetailId"),
    @NamedQuery(name = "MmsLostFixedAssetDetail.findByBookValue", query = "SELECT m FROM MmsLostFixedAssetDetail m WHERE m.bookValue = :bookValue"),
    @NamedQuery(name = "MmsLostFixedAssetDetail.findByDescription", query = "SELECT m FROM MmsLostFixedAssetDetail m WHERE m.description = :description"),
    @NamedQuery(name = "MmsLostFixedAssetDetail.findByDuration", query = "SELECT m FROM MmsLostFixedAssetDetail m WHERE m.duration = :duration"),
    @NamedQuery(name = "MmsLostFixedAssetDetail.findByItemName", query = "SELECT m FROM MmsLostFixedAssetDetail m WHERE m.itemName = :itemName"),
    @NamedQuery(name = "MmsLostFixedAssetDetail.findByRemark", query = "SELECT m FROM MmsLostFixedAssetDetail m WHERE m.remark = :remark"),
    @NamedQuery(name = "MmsLostFixedAssetDetail.findByTagNo", query = "SELECT m FROM MmsLostFixedAssetDetail m WHERE m.tagNo = :tagNo")})
public class MmsLostFixedAssetDetail implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "LOSTFXASSETDETSEQ")
    @SequenceGenerator(name = "LOSTFXASSETDETSEQ", sequenceName = "LOSTFXASSETDETSEQ", allocationSize = 1)
    @Basic(optional = false)
    @NotNull
    @Column(name = "LOST_ITEM_DETAIL_ID", nullable = false, precision = 38, scale = 0)
    private Integer lostItemDetailId;
    @Column(name = "BOOK_VALUE")
    private BigDecimal bookValue;
    @Size(max = 255)
    @Column(name = "DESCRIPTION", length = 255)
    private String description;
    @Size(max = 255)
    @Column(name = "DURATION", length = 255)
    private String duration;
    @Size(max = 255)
    @Column(name = "ITEM_NAME", length = 255)
    private String itemName;
    @Size(max = 255)
    @Column(name = "REMARK", length = 255)
    private String remark;
    @Size(max = 255)
    @Column(name = "TAG_NO", length = 255)
    private String tagNo;
    @Size(max = 50)
    @Column(name = "ACCOUNT_CODE", length = 50)
    private String accountCode;
    
    @JoinColumn(name = "LOST_ITEM_ID", referencedColumnName = "LOST_ITEM_ID")
    @ManyToOne
    private MmsLostFixedAsset lostItemId;

    public MmsLostFixedAssetDetail() {
    }

    public MmsLostFixedAssetDetail(Integer lostItemDetailId) {
        this.lostItemDetailId = lostItemDetailId;
    }

    public Integer getLostItemDetailId() {
        return lostItemDetailId;
    }

    public void setLostItemDetailId(Integer lostItemDetailId) {
        this.lostItemDetailId = lostItemDetailId;
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

    public String getAccountCode() {
        return accountCode;
    }

    public void setAccountCode(String accountCode) {
        this.accountCode = accountCode;
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

    public String getTagNo() {
        return tagNo;
    }

    public void setTagNo(String tagNo) {
        this.tagNo = tagNo;
    }

    public MmsLostFixedAsset getLostItemId() {
        return lostItemId;
    }

    public void setLostItemId(MmsLostFixedAsset lostItemId) {
        this.lostItemId = lostItemId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (lostItemDetailId != null ? lostItemDetailId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        
        if (!(object instanceof MmsLostFixedAssetDetail)) {
            return false;
        }
        MmsLostFixedAssetDetail other = (MmsLostFixedAssetDetail) object;
        if ((this.lostItemDetailId == null && other.lostItemDetailId != null) || (this.lostItemDetailId != null && !this.lostItemDetailId.equals(other.lostItemDetailId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return tagNo;
    }

}
