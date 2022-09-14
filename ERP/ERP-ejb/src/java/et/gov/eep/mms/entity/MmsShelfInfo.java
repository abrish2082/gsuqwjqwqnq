/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.mms.entity;

import et.gov.eep.commonApplications.entity.WfMmsProcessed;
import java.io.Serializable;
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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Sadik
 */
@Entity
@Table(name = "MMS_SHELF_INFO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MmsShelfInfo.findAll", query = "SELECT m FROM MmsShelfInfo m"),
    @NamedQuery(name = "MmsShelfInfo.findByShelfId", query = "SELECT m FROM MmsShelfInfo m WHERE m.shelfId = :shelfId"),
    @NamedQuery(name = "MmsShelfInfo.findByStoreId", query = "SELECT DISTINCT m.shadeName FROM MmsShelfInfo m  WHERE m.storeId = :storeId AND m.shadeName != 'not a Shade'"),
    @NamedQuery(name = "MmsShelfInfo.findByStoreIdAndShelfId", query = "SELECT m FROM MmsShelfInfo m  WHERE m.storeId = :storeId AND m.shelfId = :shelfId AND m.shadeName = :shadeName"),
    @NamedQuery(name = "MmsShelfInfo.findByStoreIdAndShelfIdAndShadeName", query = "SELECT m FROM MmsShelfInfo m  WHERE m.storeId = :storeId AND m.shelfId = :shelfId AND m.shadeName = :shadeName"),
    @NamedQuery(name = "MmsShelfInfo.findByStoreIds", query = "SELECT m FROM MmsShelfInfo m WHERE m.storeId.storeId = :storeId"),
    @NamedQuery(name = "MmsShelfInfo.findByparameterStoreId", query = "SELECT m FROM MmsShelfInfo m WHERE m.storeId = :storeId"),
    @NamedQuery(name = "MmsShelfInfo.findByProcessedBy", query = "SELECT m FROM MmsShelfInfo m WHERE m.ProcessedBy = :ProcessedBy"),
    @NamedQuery(name = "MmsShelfInfo.findWarehouseByStoreId", query = "SELECT m.shadeName FROM MmsShelfInfo m WHERE m.storeId = :storeId"),
    @NamedQuery(name = "MmsShelfInfo.findClosedShadeByStoreId", query = "SELECT DISTINCT m.shadeName FROM MmsShelfInfo m WHERE m.storeId = :storeId AND m.shadeName != 'not a Shade'"),
    @NamedQuery(name = "MmsShelfInfo.findByShadeNameAndStore", query = "SELECT m FROM MmsShelfInfo m  WHERE m.storeId = :storeId AND m.shadeName =:shadeName And m.rackOrOutdoor =:selectOption2"),
    @NamedQuery(name = "MmsShelfInfo.findInfoByShelfAndStore", query = "SELECT m FROM MmsShelfInfo m WHERE m.storeId.storeName LIKE :storeName AND m.rackCode LIKE :rackCode"),
    @NamedQuery(name = "MmsShelfInfo.findRackByStoreId", query = "SELECT m FROM MmsShelfInfo m  WHERE m.storeId = :storeId  AND m.rackOrOutdoor =:rackOrOutdoor"),
    @NamedQuery(name = "MmsShelfInfo.findclosedShadeRackByStoreId", query = "SELECT m FROM MmsShelfInfo m  WHERE m.storeId = :storeId AND m.hasArack  = :hasArack AND m.rackOrOutdoor =:rackOrOutdoor"),
    @NamedQuery(name = "MmsShelfInfo.findoutdoorCodesByparameters", query = "SELECT m FROM MmsShelfInfo m  WHERE m.storeId = :storeId AND m.outdoorType  = :outdoorType AND m.rackOrOutdoor =:rackOrOutdoor"),
    @NamedQuery(name = "MmsShelfInfo.findStoreRacksByparameters", query = "SELECT m FROM MmsShelfInfo m  WHERE m.storeId = :storeId AND m.rackOrOutdoor =:rackOrOutdoor"),

    @NamedQuery(name = "MmsShelfInfo.findClosedShadeRacksByparameters", query = "SELECT m FROM MmsShelfInfo m  WHERE m.storeId = :storeId AND m.rackOrOutdoor =:rackOrOutdoor AND m.hasArack=:hasArack"),
    @NamedQuery(name = "MmsShelfInfo.findOutdoorByStoreId", query = "SELECT m FROM MmsShelfInfo m  WHERE m.storeId.storeId = :storeId AND m.shadeName = :shadeName AND m.rackOrOutdoor =:selectOption2"),
    @NamedQuery(name = "MmsShelfInfo.findByStore", query = "SELECT m FROM MmsShelfInfo m WHERE m.storeId.storeName LIKE :storeName"),
    @NamedQuery(name = "MmsShelfInfo.findByWarehouseName", query = "SELECT m FROM MmsShelfInfo m WHERE m.shadeName = :shadeName")})
public class MmsShelfInfo implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MMS_SHELF_INFO_SEQ")
    @SequenceGenerator(name = "MMS_SHELF_INFO_SEQ", sequenceName = "MMS_SHELF_INFO_SEQ", allocationSize = 1)
    @Column(name = "SHELF_ID", nullable = false, precision = 0, scale = -127)
    private Integer shelfId;
    @Size(max = 45)
    @Column(name = "SHADE_NAME", length = 45)
    private String shadeName;
    @Size(max = 45)
    @Column(name = "RACK_CODE", length = 45)
    private String rackCode;
    @Size(max = 45)
    @Column(name = "OUTDOOR_CODE", length = 45)
    private String outdoorCode;

    @Size(max = 45)
    @Column(name = "OUTDOOR_TYPE", length = 45)
    private String outdoorType;

    @Size(max = 45)
    @Column(name = "CLOSED_SHADE_CODE", length = 45)
    private String closedShadeCode;

    @Column(name = "SELECT_OPTION_1")
    private Integer selectOption1;
    @Column(name = "RACK_OR_OUTDOOR")
    private Integer rackOrOutdoor;
    @Column(name = "HAS_A_RACK")
    private Integer hasArack;
    //<editor-fold defaultstate="collapsed" desc="Dynamic Searching Transient Parameters">
    @Transient
    private String columnName;
    @Transient
    private String columnValue;
    //</editor-fold >
    @Column(name = "PROCESSED_BY")
    private Integer ProcessedBy;
    @Size(max = 20)
    @Column(name = "COMMENT_GIVEN")
    private String CommentGiven;
    @Size(max = 20)
    @Column(name = "PROCESSED_ON")
    private String ProcessedOn;
    @Column(name = "STATUS")
    private Integer status;
    @JoinColumn(name = "STORE_ID", referencedColumnName = "STORE_ID")
    @ManyToOne
    private MmsStoreInformation storeId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "shelfId")
    private List<MmsLocationInfo> mmsLocationInfoList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "shelfId")
    private List<WfMmsProcessed> wfMmsProcessedList;

    /**
     *
     */
    public MmsShelfInfo() {
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     *
     * @param shelfId
     */
    public MmsShelfInfo(Integer shelfId) {
        this.shelfId = shelfId;
    }

    /**
     *
     * @return
     */
    public Integer getShelfId() {
        return shelfId;
    }

    /**
     *
     * @param shelfId
     */
    public void setShelfId(Integer shelfId) {
        this.shelfId = shelfId;
    }

    /**
     *
     * @return
     */
    public String getShadeName() {
        return shadeName;
    }

    /**
     *
     * @param shadeName
     */
    public void setShadeName(String shadeName) {
        this.shadeName = shadeName;
    }

    /**
     *
     * @return
     */
    public MmsStoreInformation getStoreId() {
        return storeId;
    }

    /**
     *
     * @param storeId
     */
    public void setStoreId(MmsStoreInformation storeId) {
        this.storeId = storeId;
    }

    public String getClosedShadeCode() {
        return closedShadeCode;
    }

    public void setClosedShadeCode(String closedShadeCode) {
        this.closedShadeCode = closedShadeCode;
    }

    public String getOutdoorType() {
        return outdoorType;
    }

    public void setOutdoorType(String outdoorType) {
        this.outdoorType = outdoorType;
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
    @XmlTransient
    public List<MmsLocationInfo> getMmsLocationInfoList() {
        if (mmsLocationInfoList == null) {
            mmsLocationInfoList = new ArrayList<>();
        }
        return mmsLocationInfoList;
    }

    /**
     *
     * @param mmsLocationInfoList
     */
    public void setMmsLocationInfoList(List<MmsLocationInfo> mmsLocationInfoList) {
        this.mmsLocationInfoList = mmsLocationInfoList;
    }

    /**
     *
     * @return
     */
//    @XmlTransient
//    public List<MmsShelfInfoDtl> getMmsShelfInfoDtlList() {
//         if(mmsShelfInfoDtlList==null){
//            mmsShelfInfoDtlList=new ArrayList<>();
//        }
//        return mmsShelfInfoDtlList;
//    }
//
//    /**
//     *
//     * @param mmsShelfInfoDtlList
//     */
//    public void setMmsShelfInfoDtlList(List<MmsShelfInfoDtl> mmsShelfInfoDtlList) {
//        this.mmsShelfInfoDtlList = mmsShelfInfoDtlList;
//    }
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (shelfId != null ? shelfId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MmsShelfInfo)) {
            return false;
        }
        MmsShelfInfo other = (MmsShelfInfo) object;
        if ((this.shelfId == null && other.shelfId != null) || (this.shelfId != null && !this.shelfId.equals(other.shelfId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.eep.mms.entity.MmsShelfInfo[ shelfId=" + shelfId + " ]";
    }

    public String getRackCode() {
        return rackCode;
    }

    public void setRackCode(String rackCode) {
        this.rackCode = rackCode;
    }

    public String getOutdoorCode() {
        return outdoorCode;
    }

    public void setOutdoorCode(String outdoorCode) {
        this.outdoorCode = outdoorCode;
    }

    public Integer getSelectOption1() {
        return selectOption1;
    }

    public void setSelectOption1(Integer selectOption1) {
        this.selectOption1 = selectOption1;
    }

    public Integer getRackOrOutdoor() {
        return rackOrOutdoor;
    }

    public void setRackOrOutdoor(Integer rackOrOutdoor) {
        this.rackOrOutdoor = rackOrOutdoor;
    }

    public Integer getHasArack() {
        return hasArack;
    }

    public void setHasArack(Integer hasArack) {
        this.hasArack = hasArack;
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

    public void addShelfIdToWorkflow(WfMmsProcessed wfMmsProcessed) {
        if (wfMmsProcessed.getShelfId() != this) {
            this.getWfMmsProcessedList().add(wfMmsProcessed);

            wfMmsProcessed.setShelfId(this);
        }
    }
}
