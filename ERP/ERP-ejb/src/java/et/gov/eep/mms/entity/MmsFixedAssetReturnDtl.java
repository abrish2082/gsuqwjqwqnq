
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
@Table(name = "MMS_FIXED_ASSET_RETURN_DTL")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MmsFixedAssetReturnDtl.findAll", query = "SELECT m FROM MmsFixedAssetReturnDtl m"),
    @NamedQuery(name = "MmsFixedAssetReturnDtl.findByFarDtlId", query = "SELECT m FROM MmsFixedAssetReturnDtl m WHERE m.farDtlId = :farDtlId"),
    @NamedQuery(name = "MmsFixedAssetReturnDtl.findByDescription", query = "SELECT m FROM MmsFixedAssetReturnDtl m WHERE m.description = :description"),
    @NamedQuery(name = "MmsFixedAssetReturnDtl.findByItemCode", query = "SELECT m FROM MmsFixedAssetReturnDtl m WHERE m.itemCode = :itemCode"),
    @NamedQuery(name = "MmsFixedAssetReturnDtl.findByItemName", query = "SELECT m FROM MmsFixedAssetReturnDtl m WHERE m.itemName = :itemName"),
    @NamedQuery(name = "MmsFixedAssetReturnDtl.findByRemark", query = "SELECT m FROM MmsFixedAssetReturnDtl m WHERE m.remark = :remark"),
    @NamedQuery(name = "MmsFixedAssetReturnDtl.findByItemStatus", query = "SELECT m FROM MmsFixedAssetReturnDtl m WHERE m.itemStatus = :itemStatus"),
    @NamedQuery(name = "MmsFixedAssetReturnDtl.findByNetBookValue", query = "SELECT m FROM MmsFixedAssetReturnDtl m WHERE m.netBookValue = :netBookValue")})
public class MmsFixedAssetReturnDtl implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MMS_FARETURN_DTL_SEQ")
    @SequenceGenerator(name = "MMS_FARETURN_DTL_SEQ", sequenceName = "MMS_FARETURN_DTL_SEQ", allocationSize = 1)
    @Basic(optional = false)
    @NotNull
    @Column(name = "FAR_DTL_ID", nullable = false, precision = 0, scale = -127)
    private BigDecimal farDtlId;
    @Size(max = 100)
    @Column(name = "DESCRIPTION", length = 100)
    private String description;
    @Size(max = 190)
    @Column(name = "ITEM_CODE", length = 190)
    private String itemCode;
    @Size(max = 30)
    @Column(name = "ITEM_NAME", length = 30)
    private String itemName;
    @Size(max = 100)
    @Column(name = "REMARK",length = 100)
    private String remark;
    @Size(max = 20)
    @Column(name = "ITEM_STATUS", length = 20)
    private String itemStatus;
    @Column(name = "NET_BOOK_VALUE")
    private BigDecimal netBookValue;
    @Size(max = 50)
    @Column(name = "ACCOUNT_CODE",length = 50)
    private String accountCode;
    @JoinColumn(name = "FAR_ID", referencedColumnName = "FAR_ID")
    @ManyToOne
    private MmsFixedAssetReturn farId;

    public MmsFixedAssetReturnDtl() {
    }

    public MmsFixedAssetReturnDtl(BigDecimal farDtlId) {
        this.farDtlId = farDtlId;
    }

    public BigDecimal getFarDtlId() {
        return farDtlId;
    }

    public void setFarDtlId(BigDecimal farDtlId) {
        this.farDtlId = farDtlId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
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

    public String getItemStatus() {
        return itemStatus;
    }

    public void setItemStatus(String itemStatus) {
        this.itemStatus = itemStatus;
    }

    public BigDecimal getNetBookValue() {
        return netBookValue;
    }

    public void setNetBookValue(BigDecimal netBookValue) {
        this.netBookValue = netBookValue;
    }

    public MmsFixedAssetReturn getFarId() {
        return farId;
    }

    public void setFarId(MmsFixedAssetReturn farId) {
        this.farId = farId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (farDtlId != null ? farDtlId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        
        if (!(object instanceof MmsFixedAssetReturnDtl)) {
            return false;
        }
        MmsFixedAssetReturnDtl other = (MmsFixedAssetReturnDtl) object;
        if ((this.farDtlId == null && other.farDtlId != null) || (this.farDtlId != null && !this.farDtlId.equals(other.farDtlId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.MmsFixedAssetReturnDtl[ farDtlId=" + farDtlId + " ]";
    }
    
}
