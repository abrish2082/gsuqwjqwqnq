
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
@Table(name = "MMS_FIXED_ASSET_TRANSFER_DTL")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MmsFixedAssetTransferDtl.findAll", query = "SELECT m FROM MmsFixedAssetTransferDtl m"),
    @NamedQuery(name = "MmsFixedAssetTransferDtl.findByTransferDtlId", query = "SELECT m FROM MmsFixedAssetTransferDtl m WHERE m.transferDtlId = :transferDtlId"),
    @NamedQuery(name = "MmsFixedAssetTransferDtl.findByTagNo", query = "SELECT m FROM MmsFixedAssetTransferDtl m WHERE m.tagNo = :tagNo"),
    @NamedQuery(name = "MmsFixedAssetTransferDtl.findByItemName", query = "SELECT m FROM MmsFixedAssetTransferDtl m WHERE m.itemName = :itemName"),
    @NamedQuery(name = "MmsFixedAssetTransferDtl.findByBookValue", query = "SELECT m FROM MmsFixedAssetTransferDtl m WHERE m.bookValue = :bookValue"),
    @NamedQuery(name = "MmsFixedAssetTransferDtl.findByDescription", query = "SELECT m FROM MmsFixedAssetTransferDtl m WHERE m.description = :description"),
    @NamedQuery(name = "MmsFixedAssetTransferDtl.findByRemark", query = "SELECT m FROM MmsFixedAssetTransferDtl m WHERE m.remark = :remark")})
public class MmsFixedAssetTransferDtl implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MMS_FIXEDASSETTRANSFER_DTL_SEQ")
    @SequenceGenerator(name = "MMS_FIXEDASSETTRANSFER_DTL_SEQ", sequenceName = "MMS_FIXEDASSETTRANSFER_DTL_SEQ", allocationSize = 1)
    @Basic(optional = false)
    @NotNull
    @Column(name = "TRANSFER_DTL_ID", nullable = false, precision = 0, scale = -127)
    private BigDecimal transferDtlId;
    @Size(max = 190)
    @Column(name = "TAG_NO", length = 190)
    private String tagNo;
    @Size(max = 30)
    @Column(name = "ITEM_NAME", length = 30)
    private String itemName;
    @Column(name = "BOOK_VALUE")
    private BigDecimal bookValue;
    @Size(max = 30)
    @Column(name = "DESCRIPTION", length = 30)
    private String description;
    @Size(max = 30)
    @Column(name = "REMARK", length = 30)
    private String remark;
    @Size(max = 190)
    @Column(name = "ACCOUNT_CODE", length = 190)
    private String accountCode;
    @JoinColumn(name = "TRANSFER_ID", referencedColumnName = "TRANSFER_ID")
    @ManyToOne
    private MmsFixedAssetTransfer transferId;

    /**
     *
     */
    public MmsFixedAssetTransferDtl() {
    }

    /**
     *
     * @param transferDtlId
     */
    public MmsFixedAssetTransferDtl(BigDecimal transferDtlId) {
        this.transferDtlId = transferDtlId;
    }

    /**
     *
     * @return
     */
    public BigDecimal getTransferDtlId() {
        return transferDtlId;
    }

    /**
     *
     * @param transferDtlId
     */
    public void setTransferDtlId(BigDecimal transferDtlId) {
        this.transferDtlId = transferDtlId;
    }

    /**
     *
     * @return
     */
    public String getTagNo() {
        return tagNo;
    }

    /**
     *
     * @param tagNo
     */
    public void setTagNo(String tagNo) {
        this.tagNo = tagNo;
    }

    /**
     *
     * @return
     */
    public String getItemName() {
        return itemName;
    }

    /**
     *
     * @param itemName
     */
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    /**
     *
     * @return
     */
    public BigDecimal getBookValue() {
        return bookValue;
    }

    /**
     *
     * @param bookValue
     */
    public void setBookValue(BigDecimal bookValue) {
        this.bookValue = bookValue;
    }

    public String getAccountCode() {
        return accountCode;
    }

    public void setAccountCode(String accountCode) {
        this.accountCode = accountCode;
    }

    
    
    /**
     *
     * @return
     */
    public String getDescription() {
        return description;
    }

    /**
     *
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
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
    public MmsFixedAssetTransfer getTransferId() {
        return transferId;
    }

    /**
     *
     * @param transferId
     */
    public void setTransferId(MmsFixedAssetTransfer transferId) {
        this.transferId = transferId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (transferDtlId != null ? transferDtlId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {      
        if (!(object instanceof MmsFixedAssetTransferDtl)) {
            return false;
        }
        MmsFixedAssetTransferDtl other = (MmsFixedAssetTransferDtl) object;
        if ((this.transferDtlId == null && other.transferDtlId != null) || (this.transferDtlId != null && !this.transferDtlId.equals(other.transferDtlId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.eep.mms.businessLogic.MmsFixedAssetTransferDtl[ transferDtlId=" + transferDtlId + " ]";
    }
    
}
