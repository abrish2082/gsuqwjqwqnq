/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.mms.entity;

import et.gov.eep.commonApplications.entity.WfMmsProcessed;
import et.gov.eep.fcms.entity.admin.FmsCostcSystemJunction;
import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.prms.entity.PrmsContainerAgreement;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
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
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author kimmyo
 */
@Entity
@Table(name = "MMS_STORE_INFORMATION")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MmsStoreInformation.findAll", query = "SELECT p FROM MmsStoreInformation p"),
    @NamedQuery(name = "MmsStoreInformation.findAllByPreparerId", query = "SELECT p FROM MmsStoreInformation p WHERE p.ProcessedBy =:ProcessedBy"),
    @NamedQuery(name = "MmsStoreInformation.findByStoreId", query = "SELECT p FROM MmsStoreInformation p WHERE p.storeId = :storeId"),
    @NamedQuery(name = "MmsStoreInformation.findByDescription", query = "SELECT p FROM MmsStoreInformation p WHERE p.description = :description"),
    @NamedQuery(name = "MmsStoreInformation.findByRegion", query = "SELECT p FROM MmsStoreInformation p WHERE p.region = :region"),

    @NamedQuery(name = "MmsStoreInformation.findByStoreName", query = "SELECT p FROM MmsStoreInformation p WHERE p.storeName = :storeName"),
    @NamedQuery(name = "MmsStoreInformation.findByStoreNo", query = "SELECT p FROM MmsStoreInformation p WHERE p.storeNo LIKE :storeNo"),
    @NamedQuery(name = "MmsStoreInformation.findByStoreNoAndPreparerId", query = "SELECT p FROM MmsStoreInformation p WHERE p.storeNo LIKE :storeNo "),
    @NamedQuery(name = "MmsStoreInformation.findByStoreNoAndStoreNameAndPreparerId", query = "SELECT p FROM MmsStoreInformation p WHERE p.storeNo LIKE :storeNo AND p.storeName LIKE :storeName"),
    @NamedQuery(name = "MmsStoreInformation.findByStoreIdMaximum", query = "SELECT m FROM MmsStoreInformation m WHERE m.storeId = (SELECT Max(c.storeId)  from MmsStoreInformation c)"),
    @NamedQuery(name = "MmsStoreInformation.SearchByStoreName", query = "SELECT p FROM MmsStoreInformation p WHERE p.storeName LIKE :storeName"),
    @NamedQuery(name = "MmsStoreInformation.SearchByStoreNameAndUserId", query = "SELECT p FROM MmsStoreInformation p WHERE p.storeName LIKE :storeName "),
    @NamedQuery(name = "MmsStoreInformation.findByStoreNameAndStoreNo", query = "SELECT p FROM MmsStoreInformation p WHERE p.storeName = :storeNames AND p.storeNo = :storeNos"),
    @NamedQuery(name = "MmsStoreInformation.findByStoreAdmin", query = "SELECT p FROM MmsStoreInformation p WHERE p.storeAdmin = :storeAdmin")})

public class MmsStoreInformation implements Serializable {
    //@OneToMany(mappedBy = "storeType", fetch = FetchType.LAZY)

//    @OneToMany(mappedBy = "storeType", fetch = FetchType.LAZY)
//     private List<MmsLuStoreType> mmsLuStoreTypesList;
    @OneToMany(mappedBy = "requestStore")
    private List<MmsStorereq> mmsStorereqList;
    @OneToMany(mappedBy = "storeId")
    private List<MmsInspection> mmsInspectionList;
//    @Size(max = 45)
//    @Column(name = "STORE_TYPE")
//    private String storeType;
//    @OneToMany(mappedBy = "storeId", fetch = FetchType.LAZY)
//    private List<MmsItemRegistration> mmsItemRegistrationList;
    @OneToMany(mappedBy = "storeId")
    private List<MmsShelfInfo> mmsShelfInfoList;
//    @OneToMany(mappedBy = "storeId")
//    private List<MmsLocationInfo> mmsLocationInfoList;
    @OneToMany(mappedBy = "storeId")
    private List<MmsLocationInformation> papmsLocationInformationList;
//    @OneToMany(mappedBy = "storeId")
//    private List<PapmsLocationInformation> papmsLocationInformationList;
    //  @OneToMany(mappedBy = "warehouseId")
    // private List<MmsLuWareHouse> mmsLuWareHouses;
//      @OneToMany(mappedBy = "storeType")
//    private List<MmsLuStoreType> mmsLuStoreType;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MMS_STORE_INFO_SEQ")
    @SequenceGenerator(name = "MMS_STORE_INFO_SEQ", sequenceName = "MMS_STORE_INFO_SEQ", allocationSize = 1)
    @Column(name = "STORE_ID", nullable = false, precision = 38, scale = 0)
    private Integer storeId;
    @Size(max = 255)
    @Column(name = "DESCRIPTION", length = 255)
    private String description;
    @Size(max = 255)
    @Column(name = "REGION", length = 255)
    private String region;

    @Size(max = 255)
    @Column(name = "STORE_NAME", length = 255)
    private String storeName;
    //<editor-fold defaultstate="collapsed" desc="Dynamic Searching Transient Parameters">
    @Transient
    private String columnName;
    @Transient
    private String columnValue;
    //</editor-fold >
    @Size(max = 255)
    @Column(name = "STORE_NO", length = 255)
    private String storeNo;
    @Column(name = "STORE_STATUS")
    private Integer storeStatus;

    @Column(name = "PROCESSED_BY", length = 255)
    private Integer ProcessedBy;

    @Column(name = "COMMENT_GIVEN", length = 255)
    private String CommentGiven;
    @Column(name = "PROCESSED_ON", length = 255)
    private String ProcessedOn;
    @Column(name = "STATUS")
    private Integer status;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "storeId")
    private List<WfMmsProcessed> wfMmsProcessedList;

    @JoinColumn(name = "STORE_ADMIN", referencedColumnName = "ID")
    @ManyToOne
    private HrEmployees storeAdmin;
    @JoinColumn(name = "WAREHOUSE_ID", referencedColumnName = "ID")
    @ManyToOne
    private MmsLuWareHouse warehouseId;
    @JoinColumn(name = "STORE_TYPE", referencedColumnName = "ID")
    @ManyToOne
    private MmsLuStoreType storeType;
    @OneToMany(mappedBy = "workUnit")
    private List<MmsInventoryCounting> mmsInventoryCountingList;
    @OneToMany(mappedBy = "storeId")
    private List<MmsItemRegistration> mmsItemRegistrationList;
    @OneToMany(mappedBy = "storeId")
    private List<MmsBinCard> mmsBinCardList;
    @OneToMany(mappedBy = "storeId")
    private List<MmsFixedAssetReturn> mmsFixedAssetReturnList;
    @OneToMany(mappedBy = "storeId")
    private List<MmsNonFixedAssetReturn> mmsNonFixedAssetReturnList;
    @OneToMany(mappedBy = "storeId")
    private List<MmsStockDisposal> mmsStockDisposalList;
    @OneToMany(mappedBy = "storeId")
    private List<MmsDisposal> mmsDisposalList;
    @OneToMany(mappedBy = "storeId")
    private List<MmsDisposalItems> mmsDisposalItemsList;
    @OneToMany(mappedBy = "storeId")
    private List<MmsLostFixedAsset> mmsFaLostList;

    @OneToMany(mappedBy = "storeId", cascade = CascadeType.ALL)
    private List<MmsStockItemLost> mmsStockLostList;

    @OneToMany(mappedBy = "storeId")
    private List<MmsGrn> mmsGrnList;
    private List<MmsLuWareHouse> mmsLuWareHouseList;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     *
     */
    public MmsStoreInformation() {
    }

    /**
     *
     * @param storeId
     */
    public MmsStoreInformation(Integer storeId) {
        this.storeId = storeId;
    }

    /**
     *
     * @return
     */
    public Integer getStoreId() {
        return storeId;
    }

    /**
     *
     * @param storeId
     */
    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
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
     * @return
     */
    public String getRegion() {
        return region;
    }

    /**
     *
     * @param region
     */
    public void setRegion(String region) {
        this.region = region;
    }

    /**
     *
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    public MmsLuWareHouse getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(MmsLuWareHouse warehouseId) {
        this.warehouseId = warehouseId;
    }

    public List<MmsFixedAssetReturn> getMmsFixedAssetReturnList() {
        return mmsFixedAssetReturnList;
    }

    public void setMmsFixedAssetReturnList(List<MmsFixedAssetReturn> mmsFixedAssetReturnList) {
        this.mmsFixedAssetReturnList = mmsFixedAssetReturnList;
    }

    public List<MmsNonFixedAssetReturn> getMmsNonFixedAssetReturnList() {
        return mmsNonFixedAssetReturnList;
    }

    public void setMmsNonFixedAssetReturnList(List<MmsNonFixedAssetReturn> mmsNonFixedAssetReturnList) {
        this.mmsNonFixedAssetReturnList = mmsNonFixedAssetReturnList;
    }

    public List<MmsStockDisposal> getMmsStockDisposalList() {
        return mmsStockDisposalList;
    }

    public void setMmsStockDisposalList(List<MmsStockDisposal> mmsStockDisposalList) {
        this.mmsStockDisposalList = mmsStockDisposalList;
    }

    public List<MmsDisposal> getMmsDisposalList() {
        return mmsDisposalList;
    }

    public void setMmsDisposalList(List<MmsDisposal> mmsDisposalList) {
        this.mmsDisposalList = mmsDisposalList;
    }

    public List<MmsDisposalItems> getMmsDisposalItemsList() {
        return mmsDisposalItemsList;
    }

    public void setMmsDisposalItemsList(List<MmsDisposalItems> mmsDisposalItemsList) {
        this.mmsDisposalItemsList = mmsDisposalItemsList;
    }

    public List<MmsLostFixedAsset> getMmsFaLostList() {
        return mmsFaLostList;
    }

    public void setMmsFaLostList(List<MmsLostFixedAsset> mmsFaLostList) {
        this.mmsFaLostList = mmsFaLostList;
    }

    @XmlTransient
    public List<MmsStockItemLost> getMmsStockLostList() {
        if (mmsStockLostList == null) {
            mmsStockLostList = new ArrayList<>();
        }
        return mmsStockLostList;
    }

    public void setMmsStockLostList(List<MmsStockItemLost> mmsStockLostList) {
        this.mmsStockLostList = mmsStockLostList;
    }

    public Integer getProcessedBy() {
        return ProcessedBy;
    }

    public void setProcessedBy(Integer ProcessedBy) {
        this.ProcessedBy = ProcessedBy;
    }

    public String getCommentGiven() {
        return CommentGiven;
    }

    public void setCommentGiven(String CommentGiven) {
        this.CommentGiven = CommentGiven;
    }

    public String getProcessedOn() {
        return ProcessedOn;
    }

    public void setProcessedOn(String ProcessedOn) {
        this.ProcessedOn = ProcessedOn;
    }

    /**
     *
     * @return
     */
    public String getStoreName() {
        return storeName;
    }

    /**
     *
     * @param storeName
     */
    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }
//<editor-fold defaultstate="collapsed" desc="getter and setter of Dynamic Searching Transient variable">

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

    //</editor-fold>

    /**
     *
     * @return
     */
    public String getStoreNo() {
        return storeNo;
    }

    /**
     *
     * @param storeNo
     */
    public void setStoreNo(String storeNo) {
        this.storeNo = storeNo;
    }

    /**
     *
     * @return
     */
    public HrEmployees getStoreAdmin() {
        return storeAdmin;
    }

    /**
     *
     * @param storeAdmin
     */
    public void setStoreAdmin(HrEmployees storeAdmin) {
        this.storeAdmin = storeAdmin;
    }

    public Integer getStoreStatus() {
        return storeStatus;
    }

    public void setStoreStatus(Integer storeStatus) {
        this.storeStatus = storeStatus;
    }

    public MmsLuStoreType getStoreType() {
        return storeType;
    }

    /**
     *
     * @return
     */
    public void setStorType(MmsLuStoreType storeType) {
        this.storeType = storeType;
    }

    @XmlTransient
    public List<MmsInventoryCounting> getMmsInventoryCountingList() {
        return mmsInventoryCountingList;
    }

    /**
     *
     * @param mmsInventoryCountingList
     */
    public void setMmsInventoryCountingList(List<MmsInventoryCounting> mmsInventoryCountingList) {
        this.mmsInventoryCountingList = mmsInventoryCountingList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (storeId != null ? storeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MmsStoreInformation)) {
            return false;
        }
        MmsStoreInformation other = (MmsStoreInformation) object;
        if ((this.storeId == null && other.storeId != null) || (this.storeId != null && !this.storeId.equals(other.storeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return storeName;
//"et.gov.insa.eep.pamps.entity.MmsStoreInformation[ storeId=" + storeId + " ]";
    }

//    @XmlTransient
//    public List<PapmsLocationInformation> getPapmsLocationInformationList() {
//        return papmsLocationInformationList;
//    }
//
//    public void setPapmsLocationInformationList(List<PapmsLocationInformation> papmsLocationInformationList) {
//        this.papmsLocationInformationList = papmsLocationInformationList;
//    }
    /**
     *
     * @return
     */
    @XmlTransient
    public List<MmsLocationInformation> getPapmsLocationInformationList() {
        return papmsLocationInformationList;
    }

    /**
     *
     * @param papmsLocationInformationList
     */
    public void setMmsLocationInformationList(List<MmsLocationInformation> papmsLocationInformationList) {
        this.papmsLocationInformationList = papmsLocationInformationList;
    }

    /**
     *
     * @return
     */
    @XmlTransient
    public List<MmsShelfInfo> getMmsShelfInfoList() {
        return mmsShelfInfoList;
    }

    /**
     *
     * @param mmsShelfInfoList
     */
    public void setMmsShelfInfoList(List<MmsShelfInfo> mmsShelfInfoList) {
        this.mmsShelfInfoList = mmsShelfInfoList;
    }

    /**
     *
     * @return
     */
//    @XmlTransient
//    public List<MmsLocationInfo> getMmsLocationInfoList() {
//        return mmsLocationInfoList;
//    }
//
//    /**
//     *
//     * @param mmsLocationInfoList
//     */
//    public void setMmsLocationInfoList(List<MmsLocationInfo> mmsLocationInfoList) {
//        this.mmsLocationInfoList = mmsLocationInfoList;
//    }
//    public String getStoreType() {
//        return storeType;
//    }
//
//    public void setStoreType(String storeType) {
//        this.storeType = storeType;
//    }
    @XmlTransient
    public List<MmsItemRegistration> getMmsItemRegistrationList() {
        return mmsItemRegistrationList;
    }

    public void setMmsItemRegistrationList(List<MmsItemRegistration> mmsItemRegistrationList) {
        this.mmsItemRegistrationList = mmsItemRegistrationList;
    }

    @XmlTransient
    public List<MmsGrn> getMmsGrnList() {
        return mmsGrnList;
    }

    public void setMmsGrnList(List<MmsGrn> mmsGrnList) {
        this.mmsGrnList = mmsGrnList;
    }

    @XmlTransient
    public List<MmsInspection> getMmsInspectionList() {
        return mmsInspectionList;
    }

    public void setMmsInspectionList(List<MmsInspection> mmsInspectionList) {
        this.mmsInspectionList = mmsInspectionList;
    }

    @XmlTransient
    public List<MmsStorereq> getMmsStorereqList() {
        return mmsStorereqList;
    }

    public void setMmsStorereqList(List<MmsStorereq> mmsStorereqList) {
        this.mmsStorereqList = mmsStorereqList;
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

    public void addStoreIdToWorkflow(WfMmsProcessed wfMmsProcessed) {
        if (wfMmsProcessed.getStoreId() != this) {
            this.getWfMmsProcessedList().add(wfMmsProcessed);

            wfMmsProcessed.setStoreId(this);
        }
    }

//  @XmlTransient
//    public List<MmsLuWareHouse> getMmsLuWareHouses() {
//        return mmsLuWareHouses;
//    }
//
//    /**
//     *
//     * @param mmsLuWareHouseList
//     *
//     */
//    public void setMmsLuWareHouseList(List<MmsLuWareHouse> mmsLuWareHouseList) {
//        this.mmsLuWareHouseList = mmsLuWareHouseList;
//    }
//    public List<MmsLuStoreType> getMmsLuStoreTypesList() {
//        return mmsLuStoreTypesList;
//    }
//
//    /**
//     *
//     * @param mmsLuStoreTypesList
//     */
//    
//    public void setMmsLuStoreTypesList(List<MmsLuStoreType> mmsLuStoreTypesList) {
//        this.mmsLuStoreTypesList = mmsLuStoreTypesList;
//    }
    /**
     *
     * @return
     */
//    @XmlTransient
//    public List<MmsLuStoreType> getMmsLuStoreType() {
//        return mmsLuStoreType;
//    }
//   /**
//   *
//     * @param mmsLuStoreType
//        */
//    public void setMmsLuStoreType(List<MmsLuStoreType> mmsLuStoreType) {
//        this.mmsLuStoreType = mmsLuStoreType;
//    }
}
