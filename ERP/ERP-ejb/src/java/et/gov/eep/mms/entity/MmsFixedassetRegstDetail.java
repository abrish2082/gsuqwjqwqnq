
package et.gov.eep.mms.entity;

import et.gov.eep.fcms.entity.admin.FmsGeneralLedger;
import et.gov.eep.fcms.entity.fixedasset.FmsDprOfficeAsset;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author w_station
 */
@Entity
@Table(name = "MMS_FIXEDASSET_REGST_DETAIL")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MmsFixedassetRegstDetail.findAll", query = "SELECT m FROM MmsFixedassetRegstDetail m"),
    @NamedQuery(name = "MmsFixedassetRegstDetail.findByFarDetId", query = "SELECT m FROM MmsFixedassetRegstDetail m WHERE m.farDetId = :farDetId"),
    @NamedQuery(name = "MmsFixedassetRegstDetail.findByRecivedBy", query = "SELECT m FROM MmsFixedassetRegstDetail m WHERE m.farId = :farId"),
    @NamedQuery(name = "MmsFixedassetRegstDetail.findByTagNo", query = "SELECT m FROM MmsFixedassetRegstDetail m WHERE m.tagNo = :tagNo"),
    @NamedQuery(name = "MmsFixedassetRegstDetail.findByItemName", query = "SELECT m FROM MmsFixedassetRegstDetail m WHERE m.itemName = :itemName"),
    @NamedQuery(name = "MmsFixedassetRegstDetail.findByDescription", query = "SELECT m FROM MmsFixedassetRegstDetail m WHERE m.description = :description"),
    @NamedQuery(name = "MmsFixedassetRegstDetail.findByItemName", query = "SELECT m FROM MmsFixedassetRegstDetail m WHERE m.itemName = :itemName"),
    @NamedQuery(name = "MmsFixedassetRegstDetail.findTAGNosList", query = "SELECT m FROM MmsFixedassetRegstDetail m WHERE m.selectOptRadio = :selectOptRadio"),
    @NamedQuery(name = "MmsFixedassetRegstDetail.findByItemStatus", query = "SELECT m FROM MmsFixedassetRegstDetail m WHERE m.itemStatus = :itemStatus"),
    @NamedQuery(name = "MmsFixedassetRegstDetail.findByPreviousCode", query = "SELECT m FROM MmsFixedassetRegstDetail m WHERE m.previousCode = :previousCode"),
    @NamedQuery(name = "MmsFixedassetRegstDetail.findByPurchasedDate", query = "SELECT m FROM MmsFixedassetRegstDetail m WHERE m.purchasedDate = :purchasedDate"),
    @NamedQuery(name = "MmsFixedassetRegstDetail.findBySivNo", query = "SELECT m FROM MmsFixedassetRegstDetail m WHERE m.sivNo = :sivNo"),
    @NamedQuery(name = "MmsFixedassetRegstDetail.findByreturendBy", query = "SELECT m FROM MmsFixedassetRegstDetail m WHERE m.requeistedBy = :requeistedBy"),
    @NamedQuery(name = "MmsFixedassetRegstDetail.findBySrNo", query = "SELECT m FROM MmsFixedassetRegstDetail m WHERE m.srNo = :srNo"),
    @NamedQuery(name = "MmsFixedassetRegstDetail.findByUnitPrice", query = "SELECT m FROM MmsFixedassetRegstDetail m WHERE m.unitPrice = :unitPrice"),
    @NamedQuery(name = "MmsFixedassetRegstDetail.findByWarranty", query = "SELECT m FROM MmsFixedassetRegstDetail m WHERE m.warranty = :warranty"),
    @NamedQuery(name = "MmsFixedassetRegstDetail.findByWarrantyPeriod", query = "SELECT m FROM MmsFixedassetRegstDetail m WHERE m.warrantyPeriod = :warrantyPeriod"),
    @NamedQuery(name = "MmsFixedassetRegstDetail.findByInService", query = "SELECT m FROM MmsFixedassetRegstDetail m WHERE m.inService = :inService"),
    @NamedQuery(name = "MmsFixedassetRegstDetail.findByTeranseferBy", query = "SELECT m FROM MmsFixedassetRegstDetail m WHERE m.requeistedBy = :requeistedBy"),
    @NamedQuery(name = "MmsFixedassetRegstDetail.findByItemID", query = "SELECT m FROM MmsFixedassetRegstDetail m WHERE m.itemId = :itemId"),
    @NamedQuery(name = "MmsFixedassetRegstDetail.findByDelivereyDate", query = "SELECT m FROM MmsFixedassetRegstDetail m WHERE m.delivereyDate = :delivereyDate")})
public class MmsFixedassetRegstDetail implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MMS_FAR_DETAIL_SEQ")
    @SequenceGenerator(name = "MMS_FAR_DETAIL_SEQ", sequenceName = "MMS_FAR_DETAIL_SEQ", allocationSize = 1)
    @Column(name = "FAR_DET_ID")
    private Integer farDetId;
    @Size(min = 1, max = 190)
    @Column(name = "TAG_NO")
    private String tagNo;
    @Column(name = "DELIVEREY_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date delivereyDate;
    @Size(max = 50)
    @Column(name = "DESCRIPTION", length = 200)
    private String description;
    @Size(max = 50)
    @Column(name = "ITEM_NAME", length = 50)
    private String itemName;
    @Size(max = 20)
    @Column(name = "ITEM_STATUS", length = 20)
    private String itemStatus;
    @Size(max = 20)
    @Column(name = "PREVIOUS_CODE", length = 20)
    private String previousCode;
    @Column(name = "PURCHASED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date purchasedDate;
    @Size(max = 20)
    @Column(name = "SIV_NO", length = 20)
    private String sivNo;
    @Size(max = 20)
    @Column(name = "SR_NO", length = 20)
    private String srNo;
    @Column(name = "REQUEISTED_BY")
    private Integer requeistedBy;
    @Size(max = 20)
    @Column(name = "WARRANTY", length = 20)
    private String warranty;
    @Column(name = "SELECT_OPT_RADIO")
    private Integer selectOptRadio;
    @Size(max = 20)
    @Column(name = "WARRANTY_PERIOD", length = 20)
    private String warrantyPeriod;
    @Size(max = 30)
    @Column(name = "DELIV_DATE", length = 30)
    private String delivDate;
    @Column(name = "SERVICE_LIFE")
    private BigInteger serviceLife;
    @Size(max = 20)
    @Column(name = "IN_SERVICE", length = 20)
    private String inService;
    @Column(name = "REEVALUATION_COST")
    private BigDecimal reevaluationCost;
    @Column(name = "UNIT_PRICE")
    private BigDecimal unitPrice;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ofAssetId")
    private List<FmsDprOfficeAsset> fmsDprOfficeAssetList;
    
    @JoinColumn(name = "FAR_ID", referencedColumnName = "ID")
    @ManyToOne
    private MmsFixedassetRegstration farId;
    
    @JoinColumn(name = "ITEM_ID", referencedColumnName = "MATERIAL_ID")
    @ManyToOne
    private MmsItemRegistration itemId;
    @JoinColumn(name = "ACCOUNT_CODE", referencedColumnName = "GENERAL_LEDGER_ID")
    @ManyToOne
    private FmsGeneralLedger accountCode;
    @JoinColumn(name = "STORE_REQ_ID", referencedColumnName = "STORE_REQ_ID")
    @ManyToOne
    private MmsStorereq storeReqId;
    @JoinColumn(name = "SIV_ID", referencedColumnName = "SIV_ID")
    @ManyToOne()
    private MmsSiv sivId;
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "fixedARDtId")
    private List<MmsFixedAssetReturn> mmsFixedAssetReturnlList;
    public MmsFixedassetRegstDetail() {
    }

    public MmsFixedassetRegstDetail(Integer farDetId) {
        this.farDetId = farDetId;
    }

    public MmsFixedassetRegstDetail(Integer farDetId, String tagNo) {
        this.farDetId = farDetId;
        this.tagNo = tagNo;
    }

    public Integer getFarDetId() {
        return farDetId;
    }

    public void setFarDetId(Integer farDetId) {
        this.farDetId = farDetId;
    }

    public Integer getSelectOptRadio() {
        return selectOptRadio;
    }

    public void setSelectOptRadio(Integer selectOptRadio) {
        this.selectOptRadio = selectOptRadio;
    }

    public Date getDelivereyDate() {
        return delivereyDate;
    }

    public void setDelivereyDate(Date delivereyDate) {
        this.delivereyDate = delivereyDate;
    }

    public String getDelivDate() {
        return delivDate;
    }

    public void setDelivDate(String delivDate) {
        this.delivDate = delivDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public FmsGeneralLedger getAccountCode() {
        return accountCode;
    }

    public void setAccountCode(FmsGeneralLedger accountCode) {
        this.accountCode = accountCode;
    }

    public Integer getRequeistedBy() {
        return requeistedBy;
    }

    public void setRequeistedBy(Integer requeistedBy) {
        this.requeistedBy = requeistedBy;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemStatus() {
        return itemStatus;
    }

    public void setItemStatus(String itemStatus) {
        this.itemStatus = itemStatus;
    }

    public BigDecimal getReevaluationCost() {
        return reevaluationCost;
    }

    public void setReevaluationCost(BigDecimal reevaluationCost) {
        this.reevaluationCost = reevaluationCost;
    }

    public String getPreviousCode() {
        return previousCode;
    }

    public void setPreviousCode(String previousCode) {
        this.previousCode = previousCode;
    }

    public Date getPurchasedDate() {
        return purchasedDate;
    }

    public void setPurchasedDate(Date purchasedDate) {
        this.purchasedDate = purchasedDate;
    }

    public String getSivNo() {
        return sivNo;
    }

    public void setSivNo(String sivNo) {
        this.sivNo = sivNo;
    }

    public String getSrNo() {
        return srNo;
    }

    public void setSrNo(String srNo) {
        this.srNo = srNo;
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

    public String getWarranty() {
        return warranty;
    }

    public void setWarranty(String warranty) {
        this.warranty = warranty;
    }

    public String getWarrantyPeriod() {
        return warrantyPeriod;
    }

    public void setWarrantyPeriod(String warrantyPeriod) {
        this.warrantyPeriod = warrantyPeriod;
    }

    public BigInteger getServiceLife() {
        return serviceLife;
    }

    public void setServiceLife(BigInteger serviceLife) {
        this.serviceLife = serviceLife;
    }

    public String getInService() {
        return inService;
    }

    public void setInService(String inService) {
        this.inService = inService;
    }

    public MmsFixedassetRegstration getFarId() {
        return farId;
    }

    public void setFarId(MmsFixedassetRegstration farId) {
        this.farId = farId;
    }

    public MmsItemRegistration getItemId() {
        return itemId;
    }

    public void setItemId(MmsItemRegistration itemId) {
        this.itemId = itemId;
    }

    @XmlTransient
    public List<MmsFixedAssetReturn> getMmsFixedAssetReturnlList() {
        if (mmsFixedAssetReturnlList == null) {
            mmsFixedAssetReturnlList = new ArrayList<>();
        }
        return mmsFixedAssetReturnlList;
    }

    public void setMmsFixedAssetReturnlList(List<MmsFixedAssetReturn> mmsFixedAssetReturnlList) {
        this.mmsFixedAssetReturnlList = mmsFixedAssetReturnlList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (farDetId != null ? farDetId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {       
        if (!(object instanceof MmsFixedassetRegstDetail)) {
            return false;
        }
        MmsFixedassetRegstDetail other = (MmsFixedassetRegstDetail) object;
        if ((this.farDetId == null && other.farDetId != null) || (this.farDetId != null && !this.farDetId.equals(other.farDetId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return tagNo;
    }

    @XmlTransient
    public List<FmsDprOfficeAsset> getFmsDprOfficeAssetList() {
        return fmsDprOfficeAssetList;
    }

    public void setFmsDprOfficeAssetList(List<FmsDprOfficeAsset> fmsDprOfficeAssetList) {
        this.fmsDprOfficeAssetList = fmsDprOfficeAssetList;
    }

    /**
     * @return the storeReqId
     */
    public MmsStorereq getStoreReqId() {
        return storeReqId;
    }

    /**
     * @param storeReqId the storeReqId to set
     */
    public void setStoreReqId(MmsStorereq storeReqId) {
        this.storeReqId = storeReqId;
    }

    public MmsSiv getSivId() {
        return sivId;
    }

    public void setSivId(MmsSiv sivId) {
        this.sivId = sivId;
    }

}
