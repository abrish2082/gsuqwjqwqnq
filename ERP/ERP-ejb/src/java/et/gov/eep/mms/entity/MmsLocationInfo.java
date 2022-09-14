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
 * @author minab
 */
@Entity
@Table(name = "MMS_LOCATION_INFO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MmsLocationInfo.findAll", query = "SELECT m FROM MmsLocationInfo m"),
    @NamedQuery(name = "MmsLocationInfo.findByLocationId", query = "SELECT m FROM MmsLocationInfo m WHERE m.locationId = :locationId"),
    @NamedQuery(name = "MmsLocationInfo.findByShelfId", query = "SELECT m FROM MmsLocationInfo m WHERE m.shelfId = :shelfId"),
    @NamedQuery(name = "MmsLocationInfo.findAllByPreparerId", query = "SELECT m FROM MmsLocationInfo m WHERE m.ProcessedBy = :ProcessedBy"),
    @NamedQuery(name = "MmsLocationInfo.findByRackAndShelfId", query = "SELECT m FROM MmsLocationInfo m WHERE m.shelfId = :shelfId AND m.cellRow=:cellRow"),
    //@NamedQuery(name = "MmsLocationInfo.findByCellCodeAndStoreID", query = "SELECT m FROM MmsLocationInfo m WHERE m.mmsLocationInfoDtlList.cellCode = :cellCode AND m.storeId = :storeId"),
//    @NamedQuery(name = "MmsLocationInfo.findByStoreIdandShelfId", query = "SELECT m FROM MmsLocationInfo m  WHERE m.storeId = :storeId AND m.shelfId = :shelfId" ),
    @NamedQuery(name = "MmsLocationInfo.findByStoreIdandShelfIdAndClosedShadename", query = "SELECT m FROM MmsLocationInfo m  WHERE m.shelfId.storeId = :storeId AND m.shelfId = :shelfId AND m.closedShadeName = :closedShadeName"),
    @NamedQuery(name = "MmsLocationInfo.findByWarehosueName", query = "SELECT m FROM MmsLocationInfo m WHERE m.closedShadeName = :closedShadeName")})
public class MmsLocationInfo implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MMS_LOCATION_SEQ")
    @SequenceGenerator(name = "MMS_LOCATION_SEQ", sequenceName = "MMS_LOCATION_SEQ", allocationSize = 1)
    @Column(name = "LOCATION_ID", nullable = false, precision = 0, scale = -127)
    private Integer locationId;
    @Size(max = 50)
    @Column(name = "CLOSED_SHADE_NAME", length = 50)
    private String closedShadeName;
    @Size(max = 45)
    @Column(name = "CELL_CODE", length = 45)
    private String cellCode;
    @Size(max = 45)
    @Column(name = "CELL_ROW", length = 45)
    private String cellRow;
    @JoinColumn(name = "SHELF_ID", referencedColumnName = "SHELF_ID")
    @ManyToOne
    private MmsShelfInfo shelfId;
    @Column(name = "CELL_TYPE")
    private Integer cellType;
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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "locationId")
    private List<WfMmsProcessed> wfMmsProcessedList;
//    @OneToMany(mappedBy = "locationId")
//    private List<MmsManageLocation> mmsManagedLocation;

//    public List<MmsManageLocation> getMmsManagedLocation() {
//        if (mmsManagedLocation == null) {
//            mmsManagedLocation = new ArrayList<>();
//        }
//        return mmsManagedLocation;
//    }
//
//    public void setMmsManagedLocation(List<MmsManageLocation> mmsManagedLocation) {
//        this.mmsManagedLocation = mmsManagedLocation;
//    }
//    @JoinColumn(name = "STORE_ID", referencedColumnName = "STORE_ID")
//    @ManyToOne
//    private MmsStoreInformation storeId;
//    @OneToMany(cascade = CascadeType.ALL,mappedBy = "locationInfoId", fetch = FetchType.LAZY)
//    private List<MmsLocationInfoDtl> mmsLocationInfoDtlList;
    /**
     *
     */
    public MmsLocationInfo() {
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
     * @param locationId
     */
    public MmsLocationInfo(Integer locationId) {
        this.locationId = locationId;
    }

    /**
     *
     * @return
     */
    public Integer getLocationId() {
        return locationId;
    }

    /**
     *
     * @param locationId
     */
    public void setLocationId(Integer locationId) {
        this.locationId = locationId;
    }

    public String getClosedShadeName() {
        return closedShadeName;
    }

    public void setClosedShadeName(String closedShadeName) {
        this.closedShadeName = closedShadeName;
    }

    /**
     *
     * @return
     */
    public MmsShelfInfo getShelfId() {
        return shelfId;
    }

    /**
     *
     * @param shelfId
     */
    public void setShelfId(MmsShelfInfo shelfId) {
        this.shelfId = shelfId;
    }

    public String getCellCode() {
        return cellCode;
    }

    public void setCellCode(String cellCode) {
        this.cellCode = cellCode;
    }

    public String getCellRow() {
        return cellRow;
    }

    public void setCellRow(String cellRow) {
        this.cellRow = cellRow;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (locationId != null ? locationId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MmsLocationInfo)) {
            return false;
        }
        MmsLocationInfo other = (MmsLocationInfo) object;
        if ((this.locationId == null && other.locationId != null) || (this.locationId != null && !this.locationId.equals(other.locationId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return closedShadeName;
    }

    public Integer getCellType() {
        return cellType;
    }

    public void setCellType(Integer cellType) {
        this.cellType = cellType;
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

    public void addCellIdToWorkflow(WfMmsProcessed wfMmsProcessed) {
        if (wfMmsProcessed.getLocationId() != this) {
            this.getWfMmsProcessedList().add(wfMmsProcessed);

            wfMmsProcessed.setLocationId(this);
        }
    }
}
