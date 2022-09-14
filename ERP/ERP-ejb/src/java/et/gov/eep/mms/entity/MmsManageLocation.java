/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.mms.entity;

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
 * @author Minab
 */
@Entity
@Table(name = "MMS_MANAGE_LOCATION", catalog = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MmsManageLocation.findAll", query = "SELECT m FROM MmsManageLocation m"),
    @NamedQuery(name = "MmsManageLocation.findById", query = "SELECT m FROM MmsManageLocation m WHERE m.id = :id"),
    @NamedQuery(name = "MmsManageLocation.findByProcessedBy", query = "SELECT m FROM MmsManageLocation m WHERE m.processedBy = :processedBy"),
    // @NamedQuery(name = "MmsManageLocation.findByMatId", query = "SELECT m FROM MmsManageLocation m WHERE m.materialId.materialId = :materialId"),
    // @NamedQuery(name = "MmsManageLocation.findByLocationId", query = "SELECT m FROM MmsManageLocation m WHERE m.locationId = :locationId"),
    @NamedQuery(name = "MmsManageLocation.findByMaterialName", query = "SELECT m FROM MmsManageLocation m WHERE m.materialName = :materialName"),
    @NamedQuery(name = "MmsManageLocation.findByStoreId", query = "SELECT m FROM MmsManageLocation m WHERE m.storeId.storeId = :storeId"),
    @NamedQuery(name = "MmsManageLocation.findByQuantity", query = "SELECT m FROM MmsManageLocation m WHERE m.remainingQuantity = :quantity")})
   // @NamedQuery(name = "MmsManageLocation.findByExpirationDate", query = "SELECT m FROM MmsManageLocation m WHERE m.expirationDate = :expirationDate")})
public class MmsManageLocation implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MMS_MANAGE_LOCATION_SEQ")
    @SequenceGenerator(name = "MMS_MANAGE_LOCATION_SEQ", sequenceName = "MMS_MANAGE_LOCATION_SEQ", allocationSize = 1)

    @Column(name = "ID", precision = 0, scale = -127)
    private Integer id;
//    @Column(name = "LOCATION_ID")
//    private Integer locationId;
    @Size(max = 30)
    @Column(name = "MATERIAL_NAME", length = 30)
    private String materialName;

    @Column(name = "SELECT_OPTION")
    private String selectOption;

    @Column(name = "REMANING_QUANTITY")
    private Integer remainingQuantity;
    @Column(name = "PROCESSED_BY")
    private Integer processedBy;
    @Size(max = 20)
    @Column(name = "PROCESSED_ON")
    private String processedOn;
//<editor-fold defaultstate="collapsed" desc="Dynamic Searching Transient Parameters">
    @Transient
    private String columnName;
    @Transient
    private String columnValue;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "manageLocationId")
    private List<MmsManageLocationDtl> mmsManageLocationDtlList;
    @JoinColumn(name = "GRN_ID", referencedColumnName = "GRN_ID")
    @ManyToOne
    private MmsGrn grnId;

    @JoinColumn(name = "SIV_ID", referencedColumnName = "SIV_ID")
    @ManyToOne
    private MmsSiv sivId;
    @JoinColumn(name = "STORE_ID", referencedColumnName = "STORE_ID")
    @ManyToOne
    private MmsStoreInformation storeId;
    @JoinColumn(name = "ISIV_RECEIVED_ID", referencedColumnName = "RECIEVING_ID")
    @ManyToOne
    private MmsIsivReceived isivReceivedId;

    @JoinColumn(name = "RMG_ID", referencedColumnName = "RMG_ID")
    @ManyToOne
    private MmsRmg rmgId;
//    @OrderColumn(name = "PROCESSED_ID")
//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "id")
//    private List<WfMmsProcessed> wfMmsProcessedList;
//    @JoinColumn(name = "LOCATION_ID", referencedColumnName = "LOCATION_ID")
//    @ManyToOne
//    private MmsLocationInfo locationId;
//    public MmsLocationInfo getLocationId() {
//        return locationId;
//    }
//
//    public void setLocationId(MmsLocationInfo locationId) {
//        this.locationId = locationId;
//    }

    public MmsManageLocation() {
    }

    public MmsManageLocation(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

//    public Integer getLocationId() {
//        return locationId;
//    }
//
//    public void setLocationId(Integer locationId) {
//        this.locationId = locationId;
//    }
    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public MmsStoreInformation getStoreId() {
        return storeId;
    }

//    public Integer getStoreId() {
//        return storeId;
//    }
//
//    public void setStoreId(Integer storeId) {
//        this.storeId = storeId;
    public void setStoreId(MmsStoreInformation storeId) {
        this.storeId = storeId;
    }

//    }
    public Integer getRemainingQuantity() {
        return remainingQuantity;
    }

    public void setRemainingQuantity(Integer quantity) {
        this.remainingQuantity = quantity;
    }

    public Integer getProcessedBy() {
        return processedBy;
    }

    public void setProcessedBy(Integer processedBy) {
        this.processedBy = processedBy;
    }

    public String getProcessedOn() {
        return processedOn;
    }

    public void setProcessedOn(String processedOn) {
        this.processedOn = processedOn;
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

    @XmlTransient
    public List<MmsManageLocationDtl> getMmsManageLocationDtlList() {
        if (mmsManageLocationDtlList == null) {
            mmsManageLocationDtlList = new ArrayList<>();
        }
        return mmsManageLocationDtlList;
    }

    public void setMmsManageLocationDtlList(List<MmsManageLocationDtl> mmsManageLocationDtlList) {
        this.mmsManageLocationDtlList = mmsManageLocationDtlList;
    }

    public MmsGrn getGrnId() {
        return grnId;
    }

    public void setGrnId(MmsGrn grnId) {
        this.grnId = grnId;
    }

    public MmsSiv getSivId() {
        return sivId;
    }

    public void setSivId(MmsSiv sivId) {
        this.sivId = sivId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MmsManageLocation)) {
            return false;
        }
        MmsManageLocation other = (MmsManageLocation) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return materialName;
    }

    public void addGrnDetail(MmsManageLocationDtl mmsManageLocationDtlEntity) {
        if (mmsManageLocationDtlEntity.getManageLocationId() != this) {
            this.getMmsManageLocationDtlList().add(mmsManageLocationDtlEntity);
            mmsManageLocationDtlEntity.setManageLocationId(this);
        }
    }

    public String getSelectOption() {
        return selectOption;
    }

    public void setSelectOption(String selectOption) {
        this.selectOption = selectOption;
    }

    public MmsIsivReceived getIsivReceivedId() {
        return isivReceivedId;
    }

    public void setIsivReceivedId(MmsIsivReceived isivReceivedId) {
        this.isivReceivedId = isivReceivedId;
    }

    public MmsRmg getRmgId() {
        return rmgId;
    }

    public void setRmgId(MmsRmg rmgId) {
        this.rmgId = rmgId;
    }

//    @XmlTransient
//    public List<WfMmsProcessed> getWfMmsProcessedList() {
//
//        if (wfMmsProcessedList == null) {
//            wfMmsProcessedList = new ArrayList<>();
//        }
//        return wfMmsProcessedList;
//    }
//
//    public void setWfMmsProcessedList(List<WfMmsProcessed> wfMmsProcessedList) {
//        this.wfMmsProcessedList = wfMmsProcessedList;
//    }
//    public void addReceivableIdToWorkflow(WfMmsProcessed wfMmsProcessed) {
//        if (wfMmsProcessed.getMlId() != this) {
//            this.getWfMmsProcessedList().add(wfMmsProcessed);
//
//            wfMmsProcessed.setMlId(this);
//        }
//    }
}
