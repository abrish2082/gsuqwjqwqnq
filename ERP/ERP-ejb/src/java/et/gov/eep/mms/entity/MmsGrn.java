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
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import et.gov.eep.commonApplications.entity.WfMmsProcessed;
import et.gov.eep.hrms.entity.organization.HrDepartments;
import et.gov.eep.prms.entity.PrmsPaymentRequisition;
import javax.persistence.Transient;

/**
 *
 * @author Minab
 */
@Entity
@Table(name = "MMS_GRN")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MmsGrn.findAll", query = "SELECT m FROM MmsGrn m"),
    @NamedQuery(name = "MmsGrn.findByGrnId", query = "SELECT m FROM MmsGrn m WHERE m.grnId = :grnId"),
    @NamedQuery(name = "MmsGrn.findByStoreId", query = "SELECT m FROM MmsGrn m WHERE m.storeId = :storeId"),
    @NamedQuery(name = "MmsGrn.findByStoreIdAndProcessedBy", query = "SELECT m FROM MmsGrn m WHERE m.storeId = :storeId AND m.processedBy=:processedBy"),
    @NamedQuery(name = "MmsGrn.findByProcessedBy", query = "SELECT m FROM MmsGrn m WHERE m.processedBy = :processedBy"),
    @NamedQuery(name = "MmsGrn.findByStoreIdAndGrnNo", query = "SELECT m FROM MmsGrn m WHERE m.storeId = :storeId AND m.grnNo LIKE :grnNo"),
    @NamedQuery(name = "MmsGrn.findByStoreIdAndGrnNoProcessedBy", query = "SELECT m FROM MmsGrn m WHERE m.storeId = :storeId AND m.grnNo LIKE :grnNo AND m.processedBy=:processedBy"),
    @NamedQuery(name = "MmsGrn.findByRegDate", query = "SELECT m FROM MmsGrn m WHERE m.regDate = :regDate"),
    @NamedQuery(name = "MmsGrn.findByGrnNo", query = "SELECT m FROM MmsGrn m WHERE m.grnNo = :grnNo"),
    @NamedQuery(name = "MmsGrn.findinspectionResult", query = "SELECT m.inspectionResult FROM MmsGrn m WHERE m.grnNo = :grnNo"),
    @NamedQuery(name = "MmsGrn.findByGrnNoLike", query = "SELECT m FROM MmsGrn m WHERE m.grnNo LIKE :grnNo"),
    @NamedQuery(name = "MmsGrn.findByGRNIdMaximum", query = "SELECT m FROM MmsGrn m WHERE m.grnId = (SELECT Max(c.grnId)  from MmsGrn c)"),
    @NamedQuery(name = "MmsGrn.findGrnListByWfStatus", query = "SELECT m FROM MmsGrn m WHERE m.status=:status"),
    @NamedQuery(name = "MmsGrn.findByContractId", query = "SELECT m FROM MmsGrn m WHERE m.inspectionId.contractId = :contractId"),
    // @NamedQuery(name = "MmsGrn.findByPoInspectioId", query = "SELECT m FROM MmsGrn m WHERE m.inspectionId.purchaseOId=:purchaseOId"),
    @NamedQuery(name = "MmsGrn.findBySupplierName", query = "SELECT m FROM MmsGrn m WHERE m.supplierName = :supplierName"),
    @NamedQuery(name = "MmsGrn.findByPreparedBy", query = "SELECT m FROM MmsGrn m WHERE m.preparedBy = :preparedBy"),
    @NamedQuery(name = "MmsGrn.findByPreparedDate", query = "SELECT m FROM MmsGrn m WHERE m.preparedDate = :preparedDate"),
    @NamedQuery(name = "MmsGrn.findByApprovedStatus", query = "SELECT m FROM MmsGrn m WHERE m.approvedStatus = :approvedStatus AND m.storeId.storeId=:storeId"),
    @NamedQuery(name = "MmsGrn.findByStatus", query = "SELECT m FROM MmsGrn m WHERE m.status = :status AND m.storeId=:storeId"),
    //@NamedQuery(name = "MmsGrn.findByRemark", query = "SELECT m FROM MmsGrn m WHERE m.remark = :remark"),
    @NamedQuery(name = "MmsGrn.findByPrNo", query = "SELECT m FROM MmsGrn m WHERE m.prNo = :prNo")})
public class MmsGrn implements Serializable {

    @Column(name = "APPROVED_STATUS")
    private Integer approvedStatus;
    @OneToMany(mappedBy = "grnId")
    private List<MmsManageLocation> mmsManageLocationList;

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MMS_GRN_SEQ")
    @SequenceGenerator(name = "MMS_GRN_SEQ", sequenceName = "MMS_GRN_SEQ", allocationSize = 1)
    @Column(name = "GRN_ID")
    private Integer grnId;
    @Column(name = "STATUS")
    private Integer status;
    @Size(max = 20)
    @Column(name = "REG_DATE")
    private String regDate;
    @Size(max = 255)
    @Column(name = "GRN_NO")
    private String grnNo;

    @Size(max = 255)
    @Column(name = "SUPPLIER_NAME")
    private String supplierName;

    @Size(max = 255)
    @Column(name = "PREPARED_BY")
    private String preparedBy;

    @Size(max = 20)
    @Column(name = "PREPARED_DATE")

    private String preparedDate;
    @Size(max = 50)
    @Column(name = "COMMENT_GIVEN")
    private String commentGiven;

    @Column(name = "PROCESSED_BY")
    private Integer processedBy;
    @Size(max = 20)
    @Column(name = "PROCESSED_ON")
    private String processedOn;
    @Size(max = 50)

    @Column(name = "APPROVED_BY")
    private String approvedBy;
    @Size(max = 20)
    @Column(name = "APPROVED_DATE")

    private String approvedDate;

    @Column(name = "DELIVERY_TYPE")
    private Integer deliveryType;

    @Column(name = "GRN_TYPE")
    private Integer grnType;

    @Size(max = 50)
    @Column(name = "COMMERCIAL_INVOICE_NO")
    private String commercialInvoiceNo;

    @Size(max = 50)
    @Column(name = "ENTERY_TYPE")
    private String enteryType;
 //<editor-fold defaultstate="collapsed" desc="Dynamic Searching Transient Parameters">
    @Transient
    private String columnName;
    @Transient
    private String columnValue;
    //</editor-fold >
    @Size(max = 255)
//    @Column(name = "REMARK")
//    private String remark;
    @Column(name = "PR_NO")
    private String prNo;

    @Size(max = 255)

    @Column(name = "INSPECTION_RESULT")
    private String inspectionResult;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "grnId")
    private List<MmsGrnDetail> mmsGrnDetailCollection;
    @JoinColumn(name = "STORE_ID", referencedColumnName = "STORE_ID")
    @ManyToOne
    private MmsStoreInformation storeId;
    @JoinColumn(name = "DELIVERY_OPTION", referencedColumnName = "ID")
    @ManyToOne
    private mms_lu_delivery_option deliveryoption;
    @JoinColumn(name = "INSPECTION_ID", referencedColumnName = "INSPECTION_ID")
    @ManyToOne
    private MmsInspection inspectionId;
    @JoinColumn(name = "DEP_ID", referencedColumnName = "DEP_ID")
    @ManyToOne
    private HrDepartments depId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "grnId", fetch = FetchType.LAZY)
    private List<PrmsPaymentRequisition> prmsPaymentRequisitionList;
    @OrderColumn(name = "PROCESSED_ID")
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "grnId")
    private List<WfMmsProcessed> wfMmsProcessedList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "grnId")
    private List<MmsStorereqDetail> mmsStorereqDetailsLists;

    /**
     *
     */
    public MmsGrn() {
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public String getCommentGiven() {
        return commentGiven;
    }

    public void setCommentGiven(String commentGiven) {
        this.commentGiven = commentGiven;
    }

    /**
     *
     * @return
     */
    public String getInspectionResult() {
        return inspectionResult;
    }

    /**
     *
     * @param inspectionResult
     */
    public void setInspectionResult(String inspectionResult) {
        this.inspectionResult = inspectionResult;
    }

    /**
     *
     * @param grnId
     */
    public MmsGrn(Integer grnId) {
        this.grnId = grnId;
    }

    /**
     *
     * @return
     */
    public Integer getGrnId() {
        return grnId;
    }

    /**
     *
     * @param grnId
     */
    public void setGrnId(Integer grnId) {
        this.grnId = grnId;
    }

    /**
     *
     * @return
     */
    public String getRegDate() {
        return regDate;
    }

    /**
     *
     * @param regDate
     */
    public void setRegDate(String regDate) {
        this.regDate = regDate;
    }

    /**
     *
     * @return
     */
    public String getGrnNo() {
        return grnNo;
    }

    /**
     *
     * @param grnNo
     */
    public void setGrnNo(String grnNo) {
        this.grnNo = grnNo;
    }

    /**
     *
     * @return
     */
    public String getSupplierName() {
        return supplierName;
    }

    /**
     *
     * @param supplierName
     */
    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    /**
     *
     * @return
     */
    public String getPreparedBy() {
        return preparedBy;
    }

    /**
     *
     * @param preparedBy
     */
    public void setPreparedBy(String preparedBy) {
        this.preparedBy = preparedBy;
    }

    /**
     *
     * @return
     */
    public String getPreparedDate() {
        return preparedDate;
    }

    /**
     *
     * @param preparedDate
     */
    public void setPreparedDate(String preparedDate) {
        this.preparedDate = preparedDate;
    }

    public String getEnteryType() {
        return enteryType;
    }

    public void setEnteryType(String enteryType) {
        this.enteryType = enteryType;
    }

    /**
     *
     *
     * /**
     *
     * @return
     */
    public String getPrNo() {
        return prNo;
    }

    /**
     *
     * @param prNo
     */
    public void setPrNo(String prNo) {
        this.prNo = prNo;
    }

    /**
     *
     * @return
     */
    public Integer getApprovedStatus() {
        return approvedStatus;
    }

    /**
     *
     * @param approvedStatus
     */
    public void setApprovedStatus(Integer approvedStatus) {
        this.approvedStatus = approvedStatus;
    }

    /**
     *
     * @return
     */
    @XmlTransient
    public List<MmsGrnDetail> getMmsGrnDetailCollection() {
        if (mmsGrnDetailCollection == null) {
            mmsGrnDetailCollection = new ArrayList<>();
        }
        return mmsGrnDetailCollection;
    }

    /**
     *
     * @param mmsGrnDetailCollection
     */
    public void setMmsGrnDetailCollection(List<MmsGrnDetail> mmsGrnDetailCollection) {
        this.mmsGrnDetailCollection = mmsGrnDetailCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (grnId != null ? grnId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MmsGrn)) {
            return false;
        }
        MmsGrn other = (MmsGrn) object;
        if ((this.grnId == null && other.grnId != null) || (this.grnId != null && !this.grnId.equals(other.grnId))) {
            return false;
        }
        return true;
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return grnNo;
    }

    public void addGRnDetialInfo(MmsGrnDetail grnDetail) {
        if (grnDetail.getGrnId() != this) {
            this.getMmsGrnDetailCollection().add(grnDetail);
            grnDetail.setGrnId(this);
        }
    }

    /**
     *
     * @return
     */
    @XmlTransient
    public List<MmsManageLocation> getMmsManageLocationList() {
        return mmsManageLocationList;
    }

    /**
     *
     * @param mmsManageLocationList
     */
    public void setMmsManageLocationList(List<MmsManageLocation> mmsManageLocationList) {
        this.mmsManageLocationList = mmsManageLocationList;
    }

    public String getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(String approvedBy) {
        this.approvedBy = approvedBy;
    }

    public String getApprovedDate() {
        return approvedDate;
    }

    public void setApprovedDate(String approvedDate) {
        this.approvedDate = approvedDate;
    }

    public Integer getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(Integer deliveryType) {
        this.deliveryType = deliveryType;
    }

    public String getCommercialInvoiceNo() {
        return commercialInvoiceNo;
    }

    public void setCommercialInvoiceNo(String commercialInvoiceNo) {
        this.commercialInvoiceNo = commercialInvoiceNo;
    }

//    public String getRemark() {
//        return remark;
//    }
//
//    public void setRemark(String remark) {
//        this.remark = remark;
//    }
    public Integer getGrnType() {
        return grnType;
    }

    public void setGrnType(Integer grnType) {
        this.grnType = grnType;
    }

    public MmsStoreInformation getStoreId() {
        return storeId;
    }

    public void setStoreId(MmsStoreInformation storeId) {
        this.storeId = storeId;
    }

    public MmsInspection getInspectionId() {
        return inspectionId;
    }

    public void setInspectionId(MmsInspection inspectionId) {
        this.inspectionId = inspectionId;
    }

    public HrDepartments getDepId() {
        return depId;
    }

    public void setDepId(HrDepartments depId) {
        this.depId = depId;
    }

    @XmlTransient
    public List<PrmsPaymentRequisition> getPrmsPaymentRequisitionList() {
        if (prmsPaymentRequisitionList == null) {
            prmsPaymentRequisitionList = new ArrayList<>();
        }
        return prmsPaymentRequisitionList;
    }

    public void setPrmsPaymentRequisitionList(List<PrmsPaymentRequisition> prmsPaymentRequisitionList) {
        this.prmsPaymentRequisitionList = prmsPaymentRequisitionList;
    }

    @XmlTransient
    public List<MmsStorereqDetail> getMmsStorereqDetailsLists() {
        if (mmsStorereqDetailsLists == null) {
            mmsStorereqDetailsLists = new ArrayList<>();
        }
        return mmsStorereqDetailsLists;
    }

    public void setMmsStorereqDetailsLists(List<MmsStorereqDetail> mmsStorereqDetailsLists) {
        this.mmsStorereqDetailsLists = mmsStorereqDetailsLists;
    }

    public mms_lu_delivery_option getDeliveryoption() {
        return deliveryoption;
    }

    public void setDeliveryoption(mms_lu_delivery_option deliveryoption) {
        this.deliveryoption = deliveryoption;
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

    public void addGRNIdToWorkflow(WfMmsProcessed wfMmsProcessed) {
        if (wfMmsProcessed.getGrnId() != this) {
            this.getWfMmsProcessedList().add(wfMmsProcessed);

            wfMmsProcessed.setGrnId(this);
        }
    }

}
