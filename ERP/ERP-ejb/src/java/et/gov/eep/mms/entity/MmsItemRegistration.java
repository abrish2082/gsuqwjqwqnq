package et.gov.eep.mms.entity;

import et.gov.eep.commonApplications.entity.WfMmsProcessed;
import et.gov.eep.fcms.entity.admin.FmsGeneralLedger;
import et.gov.eep.ifrs.entity.IfrsDepreciationSetup;
import et.gov.eep.prms.entity.PrmsAwardDetail;
import et.gov.eep.prms.entity.PrmsBidDetail;
import et.gov.eep.prms.entity.PrmsBidDetailAmend;
import et.gov.eep.prms.entity.PrmsContractDetail;
import et.gov.eep.prms.entity.PrmsForeignExDetail1;
import et.gov.eep.prms.entity.PrmsPurOrderDetail;
import et.gov.eep.prms.entity.PrmsThechincalEvaluationDet;
import et.gov.eep.prms.entity.PrmsPurchasePlnDetail;
import et.gov.eep.prms.entity.PrmsPurchaseRequestDet;
import et.gov.eep.prms.entity.PrmsQuotationDetail;
import et.gov.eep.prms.entity.PrmsSpecification;
import et.gov.eep.prms.entity.PrmsSupplierSpecificationDt;
import et.gov.eep.prms.entity.PrmsContractAmendmentDt;
import et.gov.eep.prms.entity.PrmsFinancialEvlResultyDtl;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
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
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author user
 */
@Entity
@Table(name = "MMS_ITEM_REGISTRATION")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MmsItemRegistration.findAll", query = "SELECT m FROM MmsItemRegistration m"),
//    @NamedQuery(name = "MmsItemRegistration.findAllByPreparerId", query = "SELECT m FROM MmsItemRegistration m WHERE m.processedBy=:processedBy"),
    @NamedQuery(name = "MmsItemRegistration.findByMatCategory", query = "SELECT m FROM MmsItemRegistration m WHERE m.matCategory = :matCategory"),
    @NamedQuery(name = "MmsItemRegistration.findByCategory", query = "SELECT DISTINCT m FROM MmsItemRegistration m WHERE m.matCategory IS NOT NULL"),
    @NamedQuery(name = "MmsItemRegistration.findByMatSubcategory", query = "SELECT m FROM MmsItemRegistration m WHERE m.matSubcategory = :matSubcategory"),
    @NamedQuery(name = "MmsItemRegistration.findByMatCode", query = "SELECT m FROM MmsItemRegistration m WHERE m.matCode = :matCode"),
    @NamedQuery(name = "MmsItemRegistration.findByItemName", query = "SELECT m FROM MmsItemRegistration m WHERE m.matCode = :itemName"),
    @NamedQuery(name = "MmsItemRegistration.findBymatnm", query = "SELECT m FROM MmsItemRegistration m WHERE m.matName IS NOT NULL"),
    @NamedQuery(name = "MmsItemRegistration.findByMatCodeLike", query = "SELECT m FROM MmsItemRegistration m WHERE m.matCode LIKE :matCode"),
    @NamedQuery(name = "MmsItemRegistration.findByStoreAndMatCodeLike", query = "SELECT m FROM MmsItemRegistration m WHERE m.matCode LIKE :matCode AND m.storeId= :storeId"),
    @NamedQuery(name = "MmsItemRegistration.findByStoreAndMatCodeLikeAndPreparerId", query = "SELECT m FROM MmsItemRegistration m WHERE m.matCode LIKE :matCode"),
    @NamedQuery(name = "MmsItemRegistration.findByStoreAndMatNameLike", query = "SELECT m FROM MmsItemRegistration m WHERE m.matName LIKE :matName AND m.storeId.storeId= :storeId AND m.matStatus= :matStatus"),
    @NamedQuery(name = "MmsItemRegistration.findByStoreItemCategoryAndMatNameLike", query = "SELECT m FROM MmsItemRegistration m WHERE m.matName LIKE :matName AND m.storeId= :storeId AND m.matStatus= :matStatus AND m.itemGroup= :itemGroup"),
    @NamedQuery(name = "MmsItemRegistration.findByMatNameOrMatcodePrefix", query = "SELECT m FROM MmsItemRegistration m WHERE  m.matCode LIKE :matCode"),
    @NamedQuery(name = "MmsItemRegistration.findByStoreItemCategoryAndGLID", query = "SELECT m FROM MmsItemRegistration m WHERE m.generalLedgerId = :fmsGeneralLedger "),
    @NamedQuery(name = "MmsItemRegistration.findByMatName", query = "SELECT m FROM MmsItemRegistration m WHERE m.matName LIKE :matName"),
    @NamedQuery(name = "MmsItemRegistration.findByStoreNameAndItemName", query = "SELECT m FROM MmsItemRegistration m WHERE m.storeId = :storeId AND m.matName LIKE :matName"),
    @NamedQuery(name = "MmsItemRegistration.findByStoreNameAndItemNamePreparerId", query = "SELECT m FROM MmsItemRegistration m WHERE  m.matName LIKE :matName"),
    @NamedQuery(name = "MmsItemRegistration.findByMatNameTest", query = "SELECT m FROM MmsItemRegistration m WHERE m.matName = :matName"),
    @NamedQuery(name = "MmsItemRegistration.findByIntendedPurpose", query = "SELECT m FROM MmsItemRegistration m WHERE m.intendedPurpose = :intendedPurpose"),
    @NamedQuery(name = "MmsItemRegistration.findByPartNumber", query = "SELECT m FROM MmsItemRegistration m WHERE m.partNumber = :partNumber"),
    @NamedQuery(name = "MmsItemRegistration.findByMatStatus", query = "SELECT m FROM MmsItemRegistration m WHERE m.matStatus = :matStatus"),
    @NamedQuery(name = "MmsItemRegistration.findByMatCodeMaximum", query = "SELECT m FROM MmsItemRegistration m WHERE m.matCode = (SELECT Max(c.matCode)  from MmsItemRegistration c WHERE  c.matCategory.catId=:catId AND c.matSubcategory.subCatId=:subcatId)"),
    @NamedQuery(name = "MmsItemRegistration.findByMaterialId", query = "SELECT m FROM MmsItemRegistration m WHERE m.materialId = :materialId"),
    @NamedQuery(name = "MmsItemRegistration.findByMatNameLike", query = "SELECT m FROM MmsItemRegistration m WHERE m.matName LIKE :matName"),
    @NamedQuery(name = "MmsItemRegistration.findByProcessedBy", query = "SELECT m FROM MmsItemRegistration m WHERE m.processedBy = :processedBy"),
    @NamedQuery(name = "MmsItemRegistration.findByUnitOfMeasure", query = "SELECT m FROM MmsItemRegistration m WHERE m.unitMeasure1 = :unitMeasure1"),
    @NamedQuery(name = "MmsItemRegistration.findByMaxReorderLevel", query = "SELECT m FROM MmsItemRegistration m WHERE m.maxReorderLevel = :maxReorderLevel"),
    @NamedQuery(name = "MmsItemRegistration.findByMinReorderLevel", query = "SELECT m FROM MmsItemRegistration m WHERE m.minReorderLevel = :minReorderLevel"),
    @NamedQuery(name = "MmsItemRegistration.findByItemType", query = "SELECT m FROM MmsItemRegistration m WHERE m.itemType = :itemType"),
    @NamedQuery(name = "MmsItemRegistration.findByStorId", query = "SELECT m FROM MmsItemRegistration m WHERE m.storeId = :storeId"),
    @NamedQuery(name = "MmsItemRegistration.findByStorIdAndPreparerId", query = "SELECT m FROM MmsItemRegistration m WHERE m.storeId = :storeId AND m.processedBy=:processedBy"),
    @NamedQuery(name = "MmsItemRegistration.findByAllParameters", query = "SELECT m FROM MmsItemRegistration m WHERE m.matCode LIKE :matCode AND m.matName LIKE :matName"),
    @NamedQuery(name = "MmsItemRegistration.findByAllParametersAndPreparerId", query = "SELECT m FROM MmsItemRegistration m WHERE m.matCode LIKE :matCode AND m.matName LIKE :matName"),
    @NamedQuery(name = "MmsItemRegistration.findByItemGroup", query = "SELECT m FROM MmsItemRegistration m WHERE m.itemGroup = :itemGroup"),})

public class MmsItemRegistration implements Serializable {

    @Column(name = "STATUS")
    private Integer status;
    @Column(name = "MAX_REORDER_LEVEL")
    private Integer maxReorderLevel;
    @Column(name = "MIN_REORDER_LEVEL")
    private Integer minReorderLevel;
    @Column(name = "ITEM_GROUP")
    private Integer itemGroup;
    @Column(name = "ITEM_FLAG")
    private Integer itemFlag;
    @Column(name = "SERVICE_YEAR")
    private Integer serviceYear;
    @Column(name = "REORDER_LEVEL")
    private Integer reorderLevel;
    @Column(name = "SAFTEY_STOCK")
    private Integer safteyStock;
    @Column(name = "PREVIOUS_ITEM")
    private Integer previousItem;
    @Column(name = "PROCESSED_BY")
    private Integer processedBy;
    @Column(name = "COMMENT_GIVEN")
    private String commentGiven;
    @Column(name = "PROCESSED_ON")
    private String processedOn;
    //<editor-fold defaultstate="collapsed" desc="Dynamic Searching Transient Parameters">
    @Transient
    private String columnName;
    @Transient
    private String columnValue;
    //</editor-fold >
    @OneToMany(mappedBy = "materialId")
    private List<MmsManageLocationDtl> mmsManageLocationDtlDetList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "itemRegistrationId")
    private List<PrmsThechincalEvaluationDet> prmsThechincalEvaluationDetList;
    @Size(max = 50)
    @Column(name = "PREPARED_BY", length = 50)
    private String preparedBy;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "itemId")
    private List<MmsInspectionDetail> mmsInspectionDetailList;
    @OneToMany(mappedBy = "materialId")
    private List<PrmsForeignExDetail1> prmsForeignExDetail1List;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "materialId")
    private List<PrmsSpecification> prmsSpecifications;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "materialId")
    private List<PrmsSupplierSpecificationDt> prmsSupplierSpecificationDt;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "materialId")
    private List<PrmsAwardDetail> prmsAwardDetailList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "materialId")
    private List<PrmsContractAmendmentDt> prmsContractAmendmentDtList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "itemId")
    private List<PrmsFinancialEvlResultyDtl> prmsFinancialEvlResultyDtlList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "materialId")
    private List<WfMmsProcessed> wfMmsProcessedList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "itemCodeId")
    private List<PrmsPurchasePlnDetail> prmsPurchasePlnDetailList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "itemRegId")
    private List<PrmsBidDetail> prmsBidDetailList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "itemRegId")
    private List<PrmsBidDetailAmend> prmsBidDetailAmendList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "materialCodeId")
    private List<PrmsPurchaseRequestDet> prmsPurchaseRequestDetList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "materialCodeId")
    private List<PrmsQuotationDetail> prmsQuotationDetails;
    private static final long serialVersionUID = 1L;
    @Transient
    private BigDecimal unitPrice;
    @Transient
    private Integer quantity;
    @Size(max = 50)
    @Column(name = "MAT_CODE", length = 50)
    private String matCode;
    @Size(max = 50)
    @Column(name = "MAT_NAME", length = 50)
    private String matName;
    @Size(max = 250)
    @Column(name = "INTENDED_PURPOSE", length = 250)
    private String intendedPurpose;
    @Size(max = 10)
    @Column(name = "PART_NUMBER", length = 10)
    private String partNumber;
    @Column(name = "MAT_STATUS")
    private Integer matStatus;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MMS_ITEM_REGISTRATION_SEQ")
    @SequenceGenerator(name = "MMS_ITEM_REGISTRATION_SEQ", sequenceName = "MMS_ITEM_REGISTRATION_SEQ", allocationSize = 1)
    @Column(name = "MATERIAL_ID", nullable = false, precision = 22)
    private Integer materialId;
    @Size(max = 20)
    @Column(name = "UNIT_MEASURE_1", length = 20)
    private String unitMeasure1;
    @Size(max = 20)
    @Column(name = "UNIT_MEASURE_2", length = 20)
    private String unitMeasure2;
    @Size(max = 20)
    @Column(name = "ITEM_TYPE", length = 20)
    private String itemType;
    @JoinColumn(name = "STORE_ID", referencedColumnName = "STORE_ID")
    @ManyToOne
    private MmsStoreInformation storeId;
    
    @JoinColumn(name = "MAT_CATEGORY", referencedColumnName = "CAT_ID")
    @ManyToOne
    private MmsCategory matCategory;
    
    @JoinColumn(name = "MAT_SUBCATEGORY", referencedColumnName = "SUB_CAT_ID")
    @ManyToOne
    private MmsSubCat matSubcategory;
    
    @JoinColumn(name = "GL_ID", referencedColumnName = "GENERAL_LEDGER_ID")
    @ManyToOne
    private FmsGeneralLedger generalLedgerId;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "itemId")
    private List<MmsStorereqDetail> mmsStoreReqDetail;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "itemId")
    private List<MmsGrnDetail> mmsGrnDetail;
    private List<MmsFixedassetRegstDetail> mmsFixedassetRegstDetailList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "itemId")
    private List<MmsSivDetail> mmsSivDetailList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "itemId")
    private List<MmsIsivDetail> mmsIsivDetailList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "materialId")
    private List<MmsIsivReceivedDtl> mmsIsivReceivedDetailList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "itemId")
    private List<MmsRmgDetail> mmsRmgDetailList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "itemId")
    private List<PrmsPurOrderDetail> prmsPurOrderDetailList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "itemId")
    private List<PrmsContractDetail> prmsContractDetailList;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "materialId")
    private MmsBinCard mmsBinCard;
    @JoinColumn(name = "DEPRECIATION_ID", referencedColumnName = "ID")
    @ManyToOne
    private IfrsDepreciationSetup depreciationTypeId;

    public IfrsDepreciationSetup getDepreciationTypeId() {
        return depreciationTypeId;
    }

    public void setDepreciationTypeId(IfrsDepreciationSetup depreciationTypeId) {
        this.depreciationTypeId = depreciationTypeId;
    }

    /**
     *
     */
    public MmsItemRegistration() {
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getProcessedBy() {
        return processedBy;
    }

    public void setProcessedBy(Integer processedBy) {
        this.processedBy = processedBy;
    }

    public String getCommentGiven() {
        return commentGiven;
    }

    public void setCommentGiven(String commentGiven) {
        this.commentGiven = commentGiven;
    }

    public String getProcessedOn() {
        return processedOn;
    }

    public void setProcessedOn(String processedOn) {
        this.processedOn = processedOn;
    }

    /**
     *
     * @param materialId
     */
    public MmsItemRegistration(Integer materialId) {
        this.materialId = materialId;
    }

    /**
     *
     * @return
     */
    public MmsCategory getMatCategory() {
        return matCategory;
    }

    /**
     *
     * @param matCategory
     */
    public void setMatCategory(MmsCategory matCategory) {
        this.matCategory = matCategory;
    }

    public List<MmsGrnDetail> getMmsGrnDetail() {
        if (mmsGrnDetail == null) {
            mmsGrnDetail = new ArrayList<>();
        }
        return mmsGrnDetail;
    }

    public void setMmsGrnDetail(List<MmsGrnDetail> mmsGrnDetail) {
        this.mmsGrnDetail = mmsGrnDetail;
    }

    /**
     *
     * @return
     */
    public MmsSubCat getMatSubcategory() {
        return matSubcategory;
    }

    /**
     *
     * @param matSubcategory
     */
    public void setMatSubcategory(MmsSubCat matSubcategory) {
        this.matSubcategory = matSubcategory;
    }

    /**
     *
     * @return
     */
    public String getMatCode() {
        return matCode;
    }

    /**
     *
     * @param matCode
     */
    public void setMatCode(String matCode) {
        this.matCode = matCode;
    }

    /**
     *
     * @return
     */
    public String getMatName() {
        return matName;
    }

    /**
     *
     * @param matName
     */
    public void setMatName(String matName) {
        this.matName = matName;
    }

    /**
     *
     * @return
     */
    public String getIntendedPurpose() {
        return intendedPurpose;
    }

    /**
     *
     * @param intendedPurpose
     */
    public void setIntendedPurpose(String intendedPurpose) {
        this.intendedPurpose = intendedPurpose;
    }

    /**
     *
     * @return
     */
    public String getPartNumber() {
        return partNumber;
    }

    /**
     *
     * @param partNumber
     */
    public void setPartNumber(String partNumber) {
        this.partNumber = partNumber;
    }

    /**
     *
     * @return
     */
    public Integer getMatStatus() {
        return matStatus;
    }

    /**
     *
     * @param matStatus
     */
    public void setMatStatus(Integer matStatus) {
        this.matStatus = matStatus;
    }

    /**
     *
     * @return
     */
//
//    /**
//     *
//     * @param unitPrice
//     */
    /**
     *
     * @return
     */
    public Integer getMaterialId() {
        return materialId;
    }

    /**
     *
     * @param materialId
     */
    public void setMaterialId(Integer materialId) {
        this.materialId = materialId;
    }

    /**
     *
     * @return
     */
    public String getUnitOfMeasure() {
        return unitMeasure1;
    }

    /**
     *
     * @param unitOfMeasure
     */
    public void setUnitOfMeasure(String unitOfMeasure) {
        this.unitMeasure1 = unitOfMeasure;
    }

    /**
     *
     * @return
     */
    public Integer getMaxReorderLevel() {
        return maxReorderLevel;
    }

    /**
     *
     * @param maxReorderLevel
     */
    public void setMaxReorderLevel(Integer maxReorderLevel) {
        this.maxReorderLevel = maxReorderLevel;
    }

    /**
     *
     * @return
     */
    public Integer getMinReorderLevel() {
        return minReorderLevel;
    }

    /**
     *
     * @param minReorderLevel
     */
    public void setMinReorderLevel(Integer minReorderLevel) {
        this.minReorderLevel = minReorderLevel;
    }

    /**
     *
     * @return
     */
    public String getItemType() {
        return itemType;
    }

    /**
     *
     * @param itemType
     */
    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public Integer getItemGroup() {
        return itemGroup;
    }

    public void setItemGroup(Integer itemGroup) {
        this.itemGroup = itemGroup;
    }

    public MmsStoreInformation getStoreId() {
        return storeId;
    }

    public void setStoreId(MmsStoreInformation storeId) {
        this.storeId = storeId;
    }

    public Integer getSafteyStock() {
        return safteyStock;
    }

    public void setSafteyStock(Integer safteyStock) {
        this.safteyStock = safteyStock;
    }

    public MmsBinCard getMmsBinCard() {
        if (mmsBinCard == null) {
            mmsBinCard = new MmsBinCard();
        }
        return mmsBinCard;
    }

    public void setMmsBinCard(MmsBinCard mmsBinCard) {
        this.mmsBinCard = mmsBinCard;
    }

    public Integer getItemFlag() {
        return itemFlag;
    }

    public void setItemFlag(Integer itemFlag) {
        this.itemFlag = itemFlag;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (materialId != null ? materialId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {

        if (!(object instanceof MmsItemRegistration)) {
            return false;
        }
        MmsItemRegistration other = (MmsItemRegistration) object;
        if ((this.materialId == null && other.materialId != null) || (this.materialId != null && !this.materialId.equals(other.materialId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return matCode;
    }

    /**
     *
     * @return
     */
    @XmlTransient
    public List<PrmsPurchaseRequestDet> getPrmsPurchaseRequestDetList() {
        if (prmsPurchaseRequestDetList == null) {
            prmsPurchaseRequestDetList = new ArrayList<>();
        }
        return prmsPurchaseRequestDetList;
    }

    /**
     *
     * @param prmsPurchaseRequestDetList
     */
    public void setPrmsPurchaseRequestDetList(List<PrmsPurchaseRequestDet> prmsPurchaseRequestDetList) {
        this.prmsPurchaseRequestDetList = prmsPurchaseRequestDetList;
    }

    /**
     *
     * @return
     */
    @XmlTransient
    public List<PrmsBidDetail> getPrmsBidDetailList() {
        if (prmsBidDetailList == null) {
            prmsBidDetailList = new ArrayList<>();
        }
        return prmsBidDetailList;
    }

    /**
     *
     * @param prmsBidDetailList
     */
    public void setPrmsBidDetailList(List<PrmsBidDetail> prmsBidDetailList) {
        this.prmsBidDetailList = prmsBidDetailList;
    }

    /**
     *
     * @return
     */
    @XmlTransient
    public List<PrmsPurchasePlnDetail> getPrmsPurchasePlnDetailList() {
        if (prmsPurchasePlnDetailList == null) {
            prmsPurchasePlnDetailList = new ArrayList<>();
        }
        return prmsPurchasePlnDetailList;
    }

    /**
     *
     * @param prmsPurchasePlnDetailList
     */
    public void setPrmsPurchasePlnDetailList(List<PrmsPurchasePlnDetail> prmsPurchasePlnDetailList) {
        this.prmsPurchasePlnDetailList = prmsPurchasePlnDetailList;
    }

    public String getUnitMeasure2() {
        return unitMeasure2;
    }

    public void setUnitMeasure2(String unitMeasure2) {
        this.unitMeasure2 = unitMeasure2;
    }

    public Integer getReorderLevel() {
        return reorderLevel;
    }

    public void setReorderLevel(Integer reorderLevel) {
        this.reorderLevel = reorderLevel;
    }

    public Integer getServiceYear() {
        return serviceYear;
    }

    public void setServiceYear(Integer serviceYear) {
        this.serviceYear = serviceYear;
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

    public Integer getPreviousItem() {
        return previousItem;
    }

    public void setPreviousItem(Integer previousItem) {
        this.previousItem = previousItem;
    }

    public String getPreparedBy() {
        return preparedBy;
    }

    public void setPreparedBy(String preparedBy) {
        this.preparedBy = preparedBy;
    }

    @XmlTransient
    public List<MmsInspectionDetail> getMmsInspectionDetailList() {
        if (mmsInspectionDetailList == null) {
            mmsInspectionDetailList = new ArrayList<>();
        }
        return mmsInspectionDetailList;
    }

    public void setMmsInspectionDetailList(List<MmsInspectionDetail> mmsInspectionDetailList) {
        this.mmsInspectionDetailList = mmsInspectionDetailList;
    }

    @XmlTransient
    public List<PrmsQuotationDetail> getPrmsQuotationDetails() {
        if (prmsQuotationDetails == null) {
            prmsQuotationDetails = new ArrayList<>();
        }
        return prmsQuotationDetails;
    }

    public void setPrmsQuotationDetails(List<PrmsQuotationDetail> prmsQuotationDetails) {
        this.prmsQuotationDetails = prmsQuotationDetails;
    }

    /**
     * @return the mmsStoreReqDetail
     */
    public List<MmsStorereqDetail> getMmsStoreReqDetail() {
        if (mmsStoreReqDetail == null) {
            mmsStoreReqDetail = new ArrayList<>();
        }
        return mmsStoreReqDetail;
    }

    /**
     * @param mmsStoreReqDetail the mmsStoreReqDetail to set
     */
    public void setMmsStoreReqDetail(List<MmsStorereqDetail> mmsStoreReqDetail) {
        this.mmsStoreReqDetail = mmsStoreReqDetail;
    }

    @XmlTransient
    public List<MmsFixedassetRegstDetail> getMmsFixedassetRegstDetailList() {
        if (mmsFixedassetRegstDetailList == null) {
            mmsFixedassetRegstDetailList = new ArrayList<>();
        }
        return mmsFixedassetRegstDetailList;
    }

    public void setMmsFixedassetRegstDetailList(List<MmsFixedassetRegstDetail> mmsFixedassetRegstDetailList) {
        this.mmsFixedassetRegstDetailList = mmsFixedassetRegstDetailList;
    }

    public String getUnitMeasure1() {
        return unitMeasure1;
    }

    public void setUnitMeasure1(String unitMeasure1) {
        this.unitMeasure1 = unitMeasure1;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnValue() {
        return columnValue;
    }

    public void setColumnValue(String columnValue) {
        this.columnValue = columnValue;
    }

    @XmlTransient
    public List<MmsSivDetail> getMmsSivDetailList() {
        if (mmsSivDetailList == null) {
            mmsSivDetailList = new ArrayList<>();
        }
        return mmsSivDetailList;
    }

    public void setMmsSivDetailList(List<MmsSivDetail> mmsSivDetailList) {
        this.mmsSivDetailList = mmsSivDetailList;
    }

    @XmlTransient
    public List<MmsIsivDetail> getMmsIsivDetailList() {
        if (mmsIsivDetailList == null) {
            mmsIsivDetailList = new ArrayList<>();
        }
        return mmsIsivDetailList;
    }

    public void setMmsIsivDetailList(List<MmsIsivDetail> mmsIsivDetailList) {
        this.mmsIsivDetailList = mmsIsivDetailList;
    }

    @XmlTransient
    public List<MmsRmgDetail> getMmsRmgDetailList() {
        if (mmsRmgDetailList == null) {
            mmsRmgDetailList = new ArrayList<>();
        }
        return mmsRmgDetailList;
    }

    public void setMmsRmgDetailList(List<MmsRmgDetail> mmsRmgDetailList) {
        this.mmsRmgDetailList = mmsRmgDetailList;
    }

    @XmlTransient
    public List<PrmsPurOrderDetail> getPrmsPurOrderDetailList() {
        if (prmsPurOrderDetailList == null) {
            prmsPurOrderDetailList = new ArrayList<>();
        }
        return prmsPurOrderDetailList;
    }

    public void setPrmsPurOrderDetailList(List<PrmsPurOrderDetail> prmsPurOrderDetailList) {
        this.prmsPurOrderDetailList = prmsPurOrderDetailList;
    }

    @XmlTransient
    public List<PrmsContractDetail> getPrmsContractDetailList() {
        if (prmsContractDetailList == null) {
            prmsContractDetailList = new ArrayList<>();
        }
        return prmsContractDetailList;
    }

    public void setPrmsContractDetailList(List<PrmsContractDetail> prmsContractDetailList) {
        this.prmsContractDetailList = prmsContractDetailList;
    }

    @XmlTransient
    public List<PrmsThechincalEvaluationDet> getPrmsThechincalEvaluationDetList() {
        if (prmsThechincalEvaluationDetList == null) {
            prmsThechincalEvaluationDetList = new ArrayList<>();
        }
        return prmsThechincalEvaluationDetList;
    }

    public void setPrmsThechincalEvaluationDetList(List<PrmsThechincalEvaluationDet> prmsThechincalEvaluationDetList) {
        this.prmsThechincalEvaluationDetList = prmsThechincalEvaluationDetList;
    }

    @XmlTransient
    public List<PrmsForeignExDetail1> getPrmsForeignExDetail1List() {
        if (prmsForeignExDetail1List == null) {
            prmsForeignExDetail1List = new ArrayList<>();
        }
        return prmsForeignExDetail1List;
    }

    public void setPrmsForeignExDetail1List(List<PrmsForeignExDetail1> prmsForeignExDetail1List) {
        this.prmsForeignExDetail1List = prmsForeignExDetail1List;
    }

    @XmlTransient

    public List<PrmsBidDetailAmend> getPrmsBidDetailAmendList() {
        if (prmsBidDetailAmendList == null) {
            prmsBidDetailAmendList = new ArrayList<>();
        }
        return prmsBidDetailAmendList;
    }

    public void setPrmsBidDetailAmendList(List<PrmsBidDetailAmend> prmsBidDetailAmendList) {
        this.prmsBidDetailAmendList = prmsBidDetailAmendList;
    }

    @XmlTransient
    public List<PrmsSpecification> getPrmsSpecifications() {
        if (prmsSpecifications == null) {
            prmsSpecifications = new ArrayList<>();
        }
        return prmsSpecifications;
    }

    public void setPrmsSpecifications(List<PrmsSpecification> prmsSpecifications) {
        this.prmsSpecifications = prmsSpecifications;
    }

    @XmlTransient
    public List<PrmsSupplierSpecificationDt> getPrmsSupplierSpecificationDt() {
        if (prmsSupplierSpecificationDt == null) {
            prmsSupplierSpecificationDt = new ArrayList<>();
        }
        return prmsSupplierSpecificationDt;
    }

    public void setPrmsSupplierSpecificationDt(List<PrmsSupplierSpecificationDt> prmsSupplierSpecificationDt) {
        this.prmsSupplierSpecificationDt = prmsSupplierSpecificationDt;
    }

    @XmlTransient
    public List<MmsManageLocationDtl> getMmsManageLocationDtlDetList() {
        if (mmsManageLocationDtlDetList == null) {
            mmsManageLocationDtlDetList = new ArrayList<>();
        }
        return mmsManageLocationDtlDetList;
    }

    public void setMmsManageLocationDtlDetList(List<MmsManageLocationDtl> mmsManageLocationDtlDetList) {
        this.mmsManageLocationDtlDetList = mmsManageLocationDtlDetList;
    }

    @XmlTransient
    public List<MmsIsivReceivedDtl> getMmsIsivReceivedDetailList() {
        if (mmsIsivReceivedDetailList == null) {
            mmsIsivReceivedDetailList = new ArrayList<>();
        }
        return mmsIsivReceivedDetailList;
    }

    public void setMmsIsivReceivedDetailList(List<MmsIsivReceivedDtl> mmsIsivReceivedDetailList) {
        this.mmsIsivReceivedDetailList = mmsIsivReceivedDetailList;
    }

    @XmlTransient
    public List<PrmsAwardDetail> getPrmsAwardDetailList() {
        if (prmsAwardDetailList == null) {
            prmsAwardDetailList = new ArrayList<>();
        }
        return prmsAwardDetailList;
    }

    public void setPrmsAwardDetailList(List<PrmsAwardDetail> prmsAwardDetailList) {
        this.prmsAwardDetailList = prmsAwardDetailList;
    }

    @XmlTransient

    public List<PrmsContractAmendmentDt> getPrmsContractAmendmentDtList() {
        if (prmsContractAmendmentDtList == null) {
            prmsContractAmendmentDtList = new ArrayList<>();
        }
        return prmsContractAmendmentDtList;
    }

    public void setPrmsContractAmendmentDtList(List<PrmsContractAmendmentDt> prmsContractAmendmentDtList) {
        this.prmsContractAmendmentDtList = prmsContractAmendmentDtList;
    }

    public FmsGeneralLedger getGeneralLedgerId() {
        return generalLedgerId;
    }

    public void setGeneralLedgerId(FmsGeneralLedger generalLedgerId) {
        this.generalLedgerId = generalLedgerId;
    }

    @XmlTransient
    public List<PrmsFinancialEvlResultyDtl> getPrmsFinancialEvlResultyDtlList() {
        if (prmsFinancialEvlResultyDtlList == null) {
            prmsFinancialEvlResultyDtlList = new ArrayList<>();
        }
        return prmsFinancialEvlResultyDtlList;
    }

    public void setPrmsFinancialEvlResultyDtlList(List<PrmsFinancialEvlResultyDtl> prmsFinancialEvlResultyDtlList) {
        this.prmsFinancialEvlResultyDtlList = prmsFinancialEvlResultyDtlList;
    }

    @XmlTransient
    public List<WfMmsProcessed> getWfMmsProcessedList() {

        if (wfMmsProcessedList == null) {
            wfMmsProcessedList = new ArrayList<>();
        }
        return wfMmsProcessedList;
    }

    public void setWfMmsProcessedList(List<WfMmsProcessed> wfMmsProcessedList) {
        this.wfMmsProcessedList = wfMmsProcessedList;
    }

    public void addItemRegistrationIdToWorkflow(WfMmsProcessed wfMmsProcessed) {
        if (wfMmsProcessed.getMaterialId() != this) {
            this.getWfMmsProcessedList().add(wfMmsProcessed);

            wfMmsProcessed.setMaterialId(this);
        }
    }

}
