/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.entity;

import et.gov.eep.commonApplications.entity.PrmsLuDmArchive;
import et.gov.eep.commonApplications.entity.WfPrmsProcessed;
import et.gov.eep.hrms.entity.organization.HrDepartments;
import java.io.Serializable;
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
 * @author user
 */
@Entity
@Table(name = "PRMS_PURCHASE_REQUEST")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PrmsPurchaseRequest.findAll", query = "SELECT p FROM PrmsPurchaseRequest p"),
    @NamedQuery(name = "PrmsPurchaseRequest.findById", query = "SELECT p FROM PrmsPurchaseRequest p WHERE p.id  = :id"),
    @NamedQuery(name = "PrmsPurchaseRequest.findByIdMax", query = "SELECT p FROM PrmsPurchaseRequest p WHERE p.id =(SELECT Max(p.id)  from PrmsPurchaseRequest p)"),
    @NamedQuery(name = "PrmsPurchaseRequest.findByApprovedBy", query = "SELECT p FROM PrmsPurchaseRequest p WHERE p.approvedBy = :approvedBy"),
    @NamedQuery(name = "PrmsPurchaseRequest.findByReqId", query = "SELECT p FROM PrmsPurchaseRequest p WHERE p.id = :reqId"),
    @NamedQuery(name = "PrmsPurchaseRequest.findByRequestForApproval", query = "SELECT p FROM PrmsPurchaseRequest p WHERE p.status=0"),
    @NamedQuery(name = "PrmsPurchaseRequest.findByStatusForView", query = "SELECT p FROM PrmsPurchaseRequest p WHERE p.status= 3 OR p.status =4"),
    @NamedQuery(name = "PrmsPurchaseRequest.findByPrDate", query = "SELECT p FROM PrmsPurchaseRequest p WHERE p.prDate = :prDate"),
    @NamedQuery(name = "PrmsPurchaseRequest.findByPreparedBy", query = "SELECT p FROM PrmsPurchaseRequest p WHERE p.preparedBy = :preparedBy"),
    @NamedQuery(name = "PrmsPurchaseRequest.findByStatus", query = "SELECT p FROM PrmsPurchaseRequest p WHERE p.status = :status"),
    @NamedQuery(name = "PrmsPurchaseRequest.findByPrNumber", query = "SELECT p FROM PrmsPurchaseRequest p WHERE p.prNumber LIKE :prNumber  and p.preparedBy=:preparedBy"),
    @NamedQuery(name = "PrmsPurchaseRequest.findByPrNumbers", query = "SELECT p FROM PrmsPurchaseRequest p WHERE p.prNumber = :prNumber"),
    @NamedQuery(name = "PrmsPurchaseRequest.findByPrNumberLike", query = "SELECT p FROM PrmsPurchaseRequest p WHERE p.prNumber LIKE :prNumbers"),
    @NamedQuery(name = "PrmsPurchaseRequest.findByPrNumForGoods", query = "SELECT p FROM PrmsPurchaseRequest p WHERE p.purchaseType = 'item'"),

    @NamedQuery(name = "PrmsPurchaseRequest.findByPurchaseType", query = "SELECT p FROM PrmsPurchaseRequest p WHERE p.purchaseType = :purchaseType"),
    @NamedQuery(name = "PrmsPurchaseRequest.findByPrNumForService", query = "SELECT p FROM PrmsPurchaseRequest p WHERE p.purchaseType = 'service'"),
    @NamedQuery(name = "PrmsPurchaseRequest.findByPrNumForWorks", query = "SELECT p FROM PrmsPurchaseRequest p WHERE p.purchaseType = 'work'"),
    @NamedQuery(name = "PrmsPurchaseRequest.findByPrNum", query = "SELECT p FROM PrmsPurchaseRequest p WHERE p.prNumber IS NOT NULL"),
    @NamedQuery(name = "PrmsPurchaseRequest.findByPrNumForWorks", query = "SELECT p FROM PrmsPurchaseRequest p WHERE p.purchaseType = 'work'"),
    @NamedQuery(name = "PrmsPurchaseRequest.findByDateRequested", query = "SELECT p FROM PrmsPurchaseRequest p WHERE p.dateRequested = :dateRequested"),
    @NamedQuery(name = "PrmsPurchaseRequest.findByRemark", query = "SELECT p FROM PrmsPurchaseRequest p WHERE p.remark = :remark"),
    @NamedQuery(name = "PrmsPurchaseRequest.findByDateApproved", query = "SELECT p FROM PrmsPurchaseRequest p WHERE p.dateApproved = :dateApproved"),
    @NamedQuery(name = "PrmsPurchaseRequest.findByFileRefNumber", query = "SELECT p FROM PrmsPurchaseRequest p WHERE p.fileRefNumber=:fileRefNumber"),
    @NamedQuery(name = "PrmsPurchaseRequest.findByRequistor", query = "SELECT p FROM PrmsPurchaseRequest p WHERE p.requistor = :requistor")})
public class PrmsPurchaseRequest implements Serializable {

    @OneToMany(mappedBy = "purchaseReqId", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<WfPrmsProcessed> wfPrmsProcessedLists;
    @OneToMany(mappedBy = "purchaseRequestId")
    private List<PrmsPurchaseOrder> prmsPurchaseOrderList;
    @Size(max = 35)
    @Column(name = "PURCHASE_TYPE", length = 35)
    private String purchaseType;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "rquestId", fetch = FetchType.LAZY)
    private List<PrmsPaymentRequisition> prmsPaymentRequisitionList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "purchaseReqId", fetch = FetchType.LAZY)
    private List<PrmsPurchaseReqService> prmsPurchaseReqServiceList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "purchaseReqId", fetch = FetchType.LAZY)
    private List<PrmsPurchaseRequestDet> prmsPurchaseRequestDetList;

    @OneToMany(mappedBy = "prId")
    private List<PrmsBidDetail> prmsBidDetailList;
    @OneToMany(mappedBy = "prId")
    private List<PrmsBidDetailAmend> prmsBidDetailAmendList;
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @GeneratedValue(generator = "EEP_PURCHASE_REQ_SEQ_GENERATOR", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "EEP_PURCHASE_REQ_SEQ_GENERATOR", sequenceName = "EEP_PURCHASE_REQ_SEQ_GENERATOR", allocationSize = 1)
    @Column(name = "ID", nullable = false)
    private Long id;
    @Size(max = 255)
    @Column(name = "APPROVED_BY", length = 255)
    private String approvedBy;
    @Size(max = 255)
    @Column(name = "REQ_ID", length = 255)
    private String reqId;
    @Column(name = "PR_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date prDate;

    @Column(name = "PREPARED_BY")
    private Integer preparedBy;
    @Column(name = "STATUS")
    private Integer status;
    @Size(max = 20)
    @Column(name = "PR_NUMBER", length = 20)
    private String prNumber;
    @Transient
    private String columnName;
    @Transient
    private String columnValue;
    @Column(name = "DATE_REQUESTED")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateRequested;
    @Size(max = 200)
    @Column(name = "REMARK", length = 200)
    private String remark;

    @Column(name = "DATE_APPROVED")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateApproved;
    @Size(max = 20)
    @Column(name = "REQUISTOR", length = 20)
    private String requistor;
    @Column(name = "FILE_REF_NUMBER")
    private Integer fileRefNumber;

    @JoinColumn(name = "REQSTR_DEP_ID", referencedColumnName = "DEP_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private HrDepartments reqstrDepId;
    @JoinColumn(name = "DOCUMENT_ID", referencedColumnName = "DOCUMENT_ID")
    @ManyToOne
    private PrmsLuDmArchive documentId;

    public PrmsPurchaseRequest() {
    }

    public PrmsPurchaseRequest(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getPurchaseType() {
        return purchaseType;
    }

    public void setPurchaseType(String purchaseType) {
        this.purchaseType = purchaseType;
    }

    public String getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(String approvedBy) {
        this.approvedBy = approvedBy;
    }

    public String getReqId() {
        return reqId;
    }

    public void setReqId(String reqId) {
        this.reqId = reqId;
    }

    public Date getPrDate() {
        return prDate;
    }

    public void setPrDate(Date prDate) {
        this.prDate = prDate;
    }

    public Integer getPreparedBy() {
        return preparedBy;
    }

    public void setPreparedBy(Integer preparedBy) {
        this.preparedBy = preparedBy;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getPrNumber() {
        return prNumber;
    }

    public void setPrNumber(String prNumber) {
        this.prNumber = prNumber;
    }

    public Date getDateRequested() {
        return dateRequested;
    }

    public void setDateRequested(Date dateRequested) {
        this.dateRequested = dateRequested;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getDateApproved() {
        return dateApproved;
    }

    public void setDateApproved(Date dateApproved) {
        this.dateApproved = dateApproved;
    }

    public String getRequistor() {
        return requistor;
    }

    public void setRequistor(String requistor) {
        this.requistor = requistor;
    }

    public HrDepartments getReqstrDepId() {
        return reqstrDepId;
    }

    public void setReqstrDepId(HrDepartments reqstrDepId) {
        this.reqstrDepId = reqstrDepId;
    }

    public PrmsLuDmArchive getDocumentId() {
        return documentId;
    }

    public void setDocumentId(PrmsLuDmArchive documentId) {
        this.documentId = documentId;
    }

    /**
     * @return the fileRefNumber
     */
    public Integer getFileRefNumber() {
        return fileRefNumber;
    }

    /**
     * @param fileRefNumber the fileRefNumber to set
     */
    public void setFileRefNumber(Integer fileRefNumber) {
        this.fileRefNumber = fileRefNumber;
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
        if (!(object instanceof PrmsPurchaseRequest)) {
            return false;
        }
        PrmsPurchaseRequest other = (PrmsPurchaseRequest) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return prNumber;
    }

    @XmlTransient
    public List<PrmsBidDetail> getPrmsBidDetailList() {
        if (prmsBidDetailList == null) {
            prmsBidDetailList = new ArrayList<>();
        }
        return prmsBidDetailList;
    }

    public void setPrmsBidDetailList(List<PrmsBidDetail> prmsBidDetailList) {
        this.prmsBidDetailList = prmsBidDetailList;
    }

    @XmlTransient
    public List<PrmsPurchaseReqService> getPrmsPurchaseReqServiceList() {
        if (prmsPurchaseReqServiceList == null) {
            prmsPurchaseReqServiceList = new ArrayList<>();
        }
        return prmsPurchaseReqServiceList;
    }

    public void setPrmsPurchaseReqServiceList(List<PrmsPurchaseReqService> prmsPurchaseReqServiceList) {
        this.prmsPurchaseReqServiceList = prmsPurchaseReqServiceList;
    }

    @XmlTransient
    public List<PrmsPurchaseRequestDet> getPrmsPurchaseRequestDetList() {
        if (prmsPurchaseRequestDetList == null) {
            prmsPurchaseRequestDetList = new ArrayList<>();
        }
        return prmsPurchaseRequestDetList;
    }

    public void setPrmsPurchaseRequestDetList(List<PrmsPurchaseRequestDet> prmsPurchaseRequestDetList) {
        this.prmsPurchaseRequestDetList = prmsPurchaseRequestDetList;
    }

    public void add(PrmsPurchaseReqService prmsPurchaseReqService) {
        if (prmsPurchaseReqService.getPurchaseReqId() != this) {
            this.getPrmsPurchaseReqServiceList().add(prmsPurchaseReqService);
            prmsPurchaseReqService.setPurchaseReqId(this);
        }
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
    public List<PrmsBidDetailAmend> getPrmsBidDetailAmendList() {
        return prmsBidDetailAmendList;
    }

    public void setPrmsBidDetailAmendList(List<PrmsBidDetailAmend> prmsBidDetailAmendList) {
        this.prmsBidDetailAmendList = prmsBidDetailAmendList;
    }

    @XmlTransient
    public List<PrmsPurchaseOrder> getPrmsPurchaseOrderList() {
        return prmsPurchaseOrderList;
    }

    public void setPrmsPurchaseOrderList(List<PrmsPurchaseOrder> prmsPurchaseOrderList) {
        this.prmsPurchaseOrderList = prmsPurchaseOrderList;
    }

    @XmlTransient
    public List<WfPrmsProcessed> getWfPrmsProcessedLists() {
        if (wfPrmsProcessedLists == null) {
            wfPrmsProcessedLists = new ArrayList<>();
        }
        return wfPrmsProcessedLists;
    }

    public void setWfPrmsProcessedLists(List<WfPrmsProcessed> wfPrmsProcessedLists) {
        this.wfPrmsProcessedLists = wfPrmsProcessedLists;
    }

}
