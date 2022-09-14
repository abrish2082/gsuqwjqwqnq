/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.mms.entity;

import et.gov.eep.commonApplications.entity.WfMmsProcessed;
import et.gov.eep.hrms.entity.committee.HrCommittees;
import et.gov.eep.prms.entity.PrmsContract;
import et.gov.eep.prms.entity.PrmsPurchaseOrder;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Sadik
 */
@Entity
@Table(name = "MMS_INSPECTION")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MmsInspection.findAll", query = "SELECT m FROM MmsInspection m"),
    @NamedQuery(name = "MmsInspection.findAllByProcessedBy", query = "SELECT m FROM MmsInspection m WHERE m.processedBy = :processedBy"),
    @NamedQuery(name = "MmsInspection.findByInspectionId", query = "SELECT m FROM MmsInspection m WHERE m.inspectionId = :inspectionId"),
    @NamedQuery(name = "MmsInspection.findByPurchaseONo", query = "SELECT m FROM MmsInspection m WHERE m.purchaseONo = :purchaseONo"),
    @NamedQuery(name = "MmsInspection.findByInspectionDate", query = "SELECT m FROM MmsInspection m WHERE m.inspectionDate = :inspectionDate"),
    @NamedQuery(name = "MmsInspection.findByApprovalDate", query = "SELECT m FROM MmsInspection m WHERE m.approvalDate = :approvalDate"),
    @NamedQuery(name = "MmsInspection.findByApprovedBy", query = "SELECT m FROM MmsInspection m WHERE m.approvedBy = :approvedBy"),
    @NamedQuery(name = "MmsInspection.findByApproverRemark", query = "SELECT m FROM MmsInspection m WHERE m.approverRemark = :approverRemark"),
    @NamedQuery(name = "MmsInspection.findByStoreName", query = "SELECT m FROM MmsInspection m WHERE m.storeName = :storeName"),
    @NamedQuery(name = "MmsInspection.findByInspectionNo", query = "SELECT m FROM MmsInspection m WHERE m.inspectionNo = :inspectionNo"),
    @NamedQuery(name = "MmsInspection.findByInspectionIdByInspNo", query = "SELECT m.inspectionId FROM MmsInspection m WHERE m.inspectionNo = :inspectionNo"),
    @NamedQuery(name = "MmsInspection.findByInspectionNumberLike", query = "SELECT m FROM MmsInspection m WHERE m.inspectionNo LIKE :inspectionNo"),
    @NamedQuery(name = "MmsInspection.findByInspectionNumberLikeAndProcessedBy", query = "SELECT m FROM MmsInspection m WHERE m.inspectionNo LIKE :inspectionNo AND m.processedBy=:processedBy"),
    @NamedQuery(name = "MmsInspection.finAllWithzero", query = "SELECT m FROM MmsInspection m WHERE m.status = :status"),
    @NamedQuery(name = "MmsInspection.findByInspectionIdMaximum", query = "SELECT m FROM MmsInspection m WHERE m.inspectionId = (SELECT Max(c.inspectionId)  from MmsInspection c)"),
    @NamedQuery(name = "MmsInspection.findByInspectyionNo", query = "SELECT m FROM MmsInspection m WHERE m.inspectionNo = :inspectionNo"),
    // @NamedQuery(name = "MmsInspection.findByProcessedBy", query = "SELECT m FROM MmsInspection m WHERE m.inspectionNo = :inspectionNo"),
    @NamedQuery(name = "MmsInspection.findByContractNo", query = "SELECT m FROM MmsInspection m WHERE m.contractNo = :contractNo"),
    @NamedQuery(name = "MmsInspection.findBySupplier", query = "SELECT m FROM MmsInspection m WHERE m.supplier = :supplier"),
    @NamedQuery(name = "MmsInspection.findByInspector2", query = "SELECT m FROM MmsInspection m WHERE m.inspector2 = :inspector2"),
    @NamedQuery(name = "MmsInspection.findByInspectorRemark", query = "SELECT m FROM MmsInspection m WHERE m.inspectorRemark = :inspectorRemark"),
    @NamedQuery(name = "MmsInspection.findByInspetor3", query = "SELECT m FROM MmsInspection m WHERE m.inspetor3 = :inspetor3"),
    @NamedQuery(name = "MmsInspection.findByPurchaseONo", query = "SELECT m FROM MmsInspection m WHERE m.purchaseONo = :purchaseONo"),
    @NamedQuery(name = "MmsInspection.findByStatus", query = "SELECT m FROM MmsInspection m WHERE m.status = :status"),
    @NamedQuery(name = "MmsInspection.findByStoreName", query = "SELECT m FROM MmsInspection m WHERE m.storeName = :storeName"),
    @NamedQuery(name = "MmsInspection.findBySupplier", query = "SELECT m FROM MmsInspection m WHERE m.supplier = :supplier"),
    @NamedQuery(name = "MmsInspection.findByContractDeliveryDate", query = "SELECT m FROM MmsInspection m WHERE m.contractDeliveryDate = :contractDeliveryDate"),
    @NamedQuery(name = "MmsInspection.findByWaybillArrivalDate", query = "SELECT m FROM MmsInspection m WHERE m.waybillArrivalDate = :waybillArrivalDate"),
    @NamedQuery(name = "MmsInspection.findByCommercialInvoiceNo", query = "SELECT m FROM MmsInspection m WHERE m.commercialInvoiceNo = :commercialInvoiceNo"),
//    @NamedQuery(name = "MmsInspection.findByInvoiceNo", query = "SELECT m FROM MmsInspection m WHERE m.invoiceNo = :invoiceNo"),
    @NamedQuery(name = "MmsInspection.findAllByPreparerId", query = "SELECT m FROM MmsInspection m WHERE m.processedBy=:processedBy"),
    @NamedQuery(name = "MmsInspection.findInspectionListByWfStatus", query = "SELECT m FROM MmsInspection m WHERE m.status=:status"),
    @NamedQuery(name = "MmsInspection.findByInvoiceType", query = "SELECT m FROM MmsInspection m WHERE m.invoiceType = :invoiceType"),
    @NamedQuery(name = "MmsInspection.findByDonorName", query = "SELECT m FROM MmsInspection m WHERE m.donorName = :donorName"),
    @NamedQuery(name = "MmsInspection.findByProducerName", query = "SELECT m FROM MmsInspection m WHERE m.producerName = :producerName"),
    @NamedQuery(name = "MmsInspection.findBySelectOption", query = "SELECT m FROM MmsInspection m WHERE m.selectOption = :selectOption")})
public class MmsInspection implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MMS_INSPECTION_SEQ")
    @SequenceGenerator(name = "MMS_INSPECTION_SEQ", sequenceName = "MMS_INSPECTION_SEQ", allocationSize = 1)
    @Column(name = "INSPECTION_ID")
    private Integer inspectionId;
    @Size(max = 20)
    @Column(name = "INSPECTION_DATE")

    private String inspectionDate;
    @Size(max = 20)
    @Column(name = "APPROVAL_DATE")

    private String approvalDate;
    @Size(max = 255)
    @Column(name = "WORK_ORDER_NO")

    private String workOrderNo;
    @Size(max = 255)

    @Column(name = "APPROVED_BY", length = 255)
    private String approvedBy;
    @Size(max = 255)
    @Column(name = "APPROVER_REMARK", length = 255)
    private String approverRemark;
    @Size(max = 255)
    @Column(name = "CONTRACT_NO", length = 255)
    private String contractNo;

    @Size(max = 255)
    @Column(name = "INSPECTION_NO", length = 255)
    private String inspectionNo;
    @Size(max = 255)
    @Column(name = "INSPECTOR_1", length = 255)
    private String inspector1;
    @Size(max = 255)
    @Column(name = "INSPECTOR_2", length = 255)
    private String inspector2;
    @Size(max = 255)
    @Column(name = "INSPECTOR_REMARK", length = 255)
    private String inspectorRemark;
    @Size(max = 255)
    @Column(name = "INSPETOR_3", length = 255)
    private String inspetor3;
    @Size(max = 255)
    @Column(name = "PURCHASE_O_NO", length = 255)
    private String purchaseONo;
    @Column(name = "STATUS")
    private Integer status;
    @Size(max = 255)
    @Column(name = "STORE_NAME", length = 255)
    private String storeName;
    @Size(max = 255)
    @Column(name = "SUPPLIER", length = 255)
    private String supplier;
    @Size(max = 20)
    @Column(name = "CONTRACT_DELIVERY_DATE")

    private String contractDeliveryDate;
    @Size(max = 20)
    @Column(name = "WAYBILL_ARRIVAL_DATE")

    private String waybillArrivalDate;
    @Size(max = 50)
    @Column(name = "COMMERCIAL_INVOICE_NO", length = 50)
    private String commercialInvoiceNo;

//    @Size(max = 50)
//    @Column(name = "INVOICE_NO", length = 50)
//    private String invoiceNo;
    @Size(max = 45)
    @Column(name = "INVOICE_TYPE", length = 45)
    private String invoiceType;
    @Size(max = 50)
    @Column(name = "DONOR_NAME", length = 50)
    private String donorName;
    @Size(max = 50)
    @Column(name = "PRODUCER_NAME", length = 50)
    private String producerName;
    @Column(name = "SELECT_OPTION")
    private Integer selectOption;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "inspectionId")
    private List<MmsInspectionDetail> mmsInspectionDetailList;
    @JoinColumn(name = "STORE_ID", referencedColumnName = "STORE_ID")
    @ManyToOne
    private MmsStoreInformation storeId;
    @JoinColumn(name = "CONTRACT_ID", referencedColumnName = "CONTRACT_ID")
    @ManyToOne
    private PrmsContract contractId;

    @JoinColumn(name = "COMMITTEE_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrCommittees committeeId;

    @Size(max = 255)
    @Column(name = "COMMITTEE_MEMBERS")
    private String committeMembers;
    @Column(name = "PROCESSED_ON")
    private String processedOn;
    @Column(name = "COMMENT_GIVEN")
    private String commentGiven;

    @Column(name = "PROCESSED_BY")
    private Integer processedBy;
    @JoinColumn(name = "PURCHASE_O_ID", referencedColumnName = "PO_ID")
    @ManyToOne
    private PrmsPurchaseOrder purchaseOId;
    @OneToMany(mappedBy = "inspectionId")
    private List<MmsGrn> mmsGrnList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "inspectionId")
    private List<WfMmsProcessed> wfMmsProcessedList;
    @OneToMany(mappedBy = "inspectionId", cascade = CascadeType.ALL)
    private List<MmsInspectionDocuments> inspectionDocumentsUploadList;
//<editor-fold defaultstate="collapsed" desc="Dynamic Searching Transient Parameters">
    @Transient
    private String columnName;
    @Transient
    private String columnValue;

    //</editor-fold >
    public MmsInspection() {
    }

    public String getWorkOrderNo() {
        return workOrderNo;
    }

    public void setWorkOrderNo(String workOrderNo) {
        this.workOrderNo = workOrderNo;
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

    public Integer getProcessedBy() {
        return processedBy;
    }

    public void setProcessedBy(Integer processedBy) {
        this.processedBy = processedBy;
    }

    public MmsInspection(Integer inspectionId) {
        this.inspectionId = inspectionId;
    }

    public Integer getInspectionId() {
        return inspectionId;
    }

    public void setInspectionId(Integer inspectionId) {
        this.inspectionId = inspectionId;
    }

    public String getApprovalDate() {
        return approvalDate;
    }

    public void setApprovalDate(String approvalDate) {
        this.approvalDate = approvalDate;
    }

    public String getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(String approvedBy) {
        this.approvedBy = approvedBy;
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

    public String getApproverRemark() {
        return approverRemark;
    }

    public void setApproverRemark(String approverRemark) {
        this.approverRemark = approverRemark;
    }

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    public String getInspectionDate() {
        return inspectionDate;
    }

    public void setInspectionDate(String inspectionDate) {
        this.inspectionDate = inspectionDate;
    }

    public String getInspectionNo() {
        return inspectionNo;
    }

    public void setInspectionNo(String inspectionNo) {
        this.inspectionNo = inspectionNo;
    }

    public String getInspector1() {
        return inspector1;
    }

    public void setInspector1(String inspector1) {
        this.inspector1 = inspector1;
    }

    public String getInspector2() {
        return inspector2;
    }

    public void setInspector2(String inspector2) {
        this.inspector2 = inspector2;
    }

    public String getInspectorRemark() {
        return inspectorRemark;
    }

    public void setInspectorRemark(String inspectorRemark) {
        this.inspectorRemark = inspectorRemark;
    }

    public String getInspetor3() {
        return inspetor3;
    }

    public void setInspetor3(String inspetor3) {
        this.inspetor3 = inspetor3;
    }

    public String getPurchaseONo() {
        return purchaseONo;
    }

    public void setPurchaseONo(String purchaseONo) {
        this.purchaseONo = purchaseONo;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public String getContractDeliveryDate() {
        return contractDeliveryDate;
    }

    public void setContractDeliveryDate(String contractDeliveryDate) {
        this.contractDeliveryDate = contractDeliveryDate;
    }

    public String getWaybillArrivalDate() {
        return waybillArrivalDate;
    }

    public void setWaybillArrivalDate(String waybillArrivalDate) {
        this.waybillArrivalDate = waybillArrivalDate;
    }

    public String getCommercialInvoiceNo() {
        return commercialInvoiceNo;
    }

    public void setCommercialInvoiceNo(String commercialInvoiceNo) {
        this.commercialInvoiceNo = commercialInvoiceNo;
    }
//
//    public String getInvoiceNo() {
//        return invoiceNo;
//    }
//
//    public void setInvoiceNo(String invoiceNo) {
//        this.invoiceNo = invoiceNo;
//    }

    public String getInvoiceType() {
        return invoiceType;
    }

    public void setInvoiceType(String invoiceType) {
        this.invoiceType = invoiceType;
    }

    public String getDonorName() {
        return donorName;
    }

    public void setDonorName(String donorName) {
        this.donorName = donorName;
    }

    public String getProducerName() {
        return producerName;
    }

    public void setProducerName(String producerName) {
        this.producerName = producerName;
    }

    public Integer getSelectOption() {
        return selectOption;
    }

    public void setSelectOption(Integer selectOption) {
        this.selectOption = selectOption;
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

    public MmsStoreInformation getStoreId() {
        return storeId;
    }

    public void setStoreId(MmsStoreInformation storeId) {
        this.storeId = storeId;
    }

    public PrmsContract getContractId() {
        return contractId;
    }

    public void setContractId(PrmsContract contractId) {
        this.contractId = contractId;
    }

    public PrmsPurchaseOrder getPurchaseOId() {
        return purchaseOId;
    }

    public void setPurchaseOId(PrmsPurchaseOrder purchaseOId) {
        this.purchaseOId = purchaseOId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (inspectionId != null ? inspectionId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MmsInspection)) {
            return false;
        }
        MmsInspection other = (MmsInspection) object;
        if ((this.inspectionId == null && other.inspectionId != null) || (this.inspectionId != null && !this.inspectionId.equals(other.inspectionId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return inspectionNo;
    }

    public void addInspectionDetialInfo(MmsInspectionDetail inspectionDetail) {
        if (inspectionDetail.getInspectionId() != this) {
            this.getMmsInspectionDetailList().add(inspectionDetail);
            inspectionDetail.setInspectionId(this);

        }

    }

    public void addInspectionIdToWorkflow(WfMmsProcessed wfMmsProcessed) {
        if (wfMmsProcessed.getInspectionId() != this) {
            this.getWfMmsProcessedList().add(wfMmsProcessed);

            wfMmsProcessed.setInspectionId(this);
        }
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

    @XmlTransient
    public List<MmsInspectionDocuments> getInspectionDocumentsUploadList() {
        if (inspectionDocumentsUploadList == null) {
            inspectionDocumentsUploadList = new ArrayList<>();
        }
        return inspectionDocumentsUploadList;
    }

    public void setInspectionDocumentsUploadList(List<MmsInspectionDocuments> inspectionDocumentsUploadList) {
        this.inspectionDocumentsUploadList = inspectionDocumentsUploadList;
    }

    @XmlTransient

    public List<MmsGrn> getMmsGrnList() {
        return mmsGrnList;
    }

    public void setMmsGrnList(List<MmsGrn> mmsGrnList) {
        this.mmsGrnList = mmsGrnList;
    }

    public HrCommittees getCommitteeId() {
        return committeeId;
    }

    public void setCommitteeId(HrCommittees committeeId) {
        this.committeeId = committeeId;
    }

    public String getCommitteMembers() {
        return committeMembers;
    }

    public void setCommitteMembers(String committeMembers) {
        this.committeMembers = committeMembers;
    }

    public void Add(MmsInspectionDocuments inspectionDocuments) {
        if (inspectionDocuments.getInspectionId() != this) {
            this.getInspectionDocumentsUploadList().add(inspectionDocuments);
            inspectionDocuments.setInspectionId(this);
        }
    }
}
